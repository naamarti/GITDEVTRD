package com.totalbanksolutions.framework.dao.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.model.database.view.AppNavigationView;
import com.totalbanksolutions.framework.model.database.view.EmailSenderLogView;
import com.totalbanksolutions.framework.model.database.view.MandatoryContactInfoView;
import com.totalbanksolutions.framework.bean.database.table.AppNavigation;
import com.totalbanksolutions.framework.model.database.table.ContactsTable;
import com.totalbanksolutions.framework.bean.database.table.AppNavigationAppRoleRelationship;
import com.totalbanksolutions.framework.bean.database.table.AppNavigationGroup;
import com.totalbanksolutions.framework.bean.database.table.AppNavigationRole;
import com.totalbanksolutions.framework.bean.database.table.AppRole;
import com.totalbanksolutions.framework.bean.database.table.AppUser;
import com.totalbanksolutions.framework.bean.database.table.AppUserAppRoleRelationship;
import com.totalbanksolutions.framework.bean.database.table.AppUserChallengeQuestionRelationship;
import com.totalbanksolutions.framework.bean.database.table.AppUserDepositInstitutionRelationship;
import com.totalbanksolutions.framework.bean.database.table.AppUserHistory;
import com.totalbanksolutions.framework.bean.database.table.AppUserSourceInstitutionRelationship;
import com.totalbanksolutions.framework.bean.database.table.AppUserType;
import com.totalbanksolutions.framework.bean.database.table.ChallengeQuestion;
import com.totalbanksolutions.framework.bean.database.table.Contact;
import com.totalbanksolutions.framework.bean.database.table.ContactInstitutionType;
import com.totalbanksolutions.framework.bean.database.table.EnvelopeTypes;
import com.totalbanksolutions.framework.bean.database.table.Envelopes;
import com.totalbanksolutions.framework.bean.database.table.MailingAddress;
import com.totalbanksolutions.framework.bean.database.table.SourceInstitution;
import com.totalbanksolutions.framework.bean.database.view.ViewAppUserSession;
import com.totalbanksolutions.framework.bean.database.view.ViewContactEnvelopeRelationship;
import com.totalbanksolutions.framework.bean.web.AppUserFilter;
import com.totalbanksolutions.framework.dao.SecurityDAO;
import com.totalbanksolutions.framework.dao.util.DataBatchManager;
import com.totalbanksolutions.framework.dao.util.DatabaseHelper;
import com.totalbanksolutions.framework.dao.util.SQLParameters;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.util.AppUtility;
import com.totalbanksolutions.framework.util.ConvertUtility;
import com.totalbanksolutions.framework.util.EncryptionUtility;

/**
 * @author vcatrini
 * Modified: 28 Sep 2011 VC #1010: Modify the way DMS distinguishes between SECURE and NON-SECURE environments.
 */  
public class SecurityJDBC implements SecurityDAO
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    private DataSource dataSource;
    private DatabaseHelper databaseHelper;
    
	private final int MAX_ROWS = 100;
	
	public SecurityJDBC(DataSource ds)
	{
		setDataSource(ds);
	}
	
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
        this.databaseHelper = new DatabaseHelper(dataSource);
    }
    
    public void close()
    {
    	this.databaseHelper.close();
    	this.databaseHelper = null;
    	this.dataSource = null;
    }
    
	//============================================================================
	//
    // AppUser_History
    //
	//============================================================================
    public List<AppUserHistory> getAppUserHistory( long userID, int historicalRecordCount )
    {
		String sql = "SELECT TOP(?) H.* "
		+ " FROM " + AppUserHistory.DATABASE_TABLE_NAME + " H WITH (NOLOCK) "
		+ " LEFT JOIN " + AppUser.DATABASE_TABLE_NAME + " U WITH (NOLOCK) "  
		+ " ON U.User_PK = H.AppUser_FK " 
		+ " WHERE H.AppUser_FK = ? " 
		+ " ORDER BY H.DateAdded DESC";
		SQLParameters params = new SQLParameters();
		params.addValue(historicalRecordCount);
		params.addValue(userID);
		return this.databaseHelper.queryForList(sql, params, new AppUserHistory());
    }
    
    //============================================================================
	//
    // AppNavigation
    //
	//============================================================================	
	private void deleteAppNavigation(long navigationId)
	{
		String sql = "DELETE " + Databases.COMMON + "..AppNavigation_AppRole_Relationship WHERE Navigation_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(navigationId);
		this.databaseHelper.executeUpdate( sql, params );

		sql = "DELETE " + Databases.COMMON + "..AppNavigation WHERE Navigation_PK = ? ";
		params = new SQLParameters();
		params.addValue(navigationId);
		this.databaseHelper.executeUpdate( sql, params );
	}
	
    public List<AppNavigation> getAppNavigationList(long groupId)
	{
    	String secureURLPrefix = "";
    	return getAppNavigationList(groupId, secureURLPrefix);
	}
    
    public List<AppNavigation> getAppNavigationList(long groupId, String secureURLPrefix)
	{
		log.debug("getAppNavigationList, secureURLPrefix=" + secureURLPrefix);
    	List<AppNavigation> dataList = new ArrayList<AppNavigation>();
		String sql = "SELECT * FROM " + AppNavigation.DATABASE_TABLE_NAME + " WHERE NavigationGroup_FK = ? ORDER BY NavigationOrder ";
		SQLParameters params = new SQLParameters();
		params.addValue(groupId);
		dataList = this.databaseHelper.queryForList(sql, params, new AppNavigation());

		if(secureURLPrefix.length() > 0)
		{
			// https://recon.tbslink.com/CurrentRates.aspx    ==>  https://www.secure.recon.tbslink.com/Tests.aspx
			// https://www.tbslink.com/autorecon/welcome.htm  ==>  https://www.secure.tbslink.com/autorecon/welcome.htm
			for(AppNavigation item : dataList)
			{
				String url = item.getUrl();
				if(!url.contains(secureURLPrefix)) // don't update if the database url's already contain secure
				{
					String newURL = url.replace("https://recon.tbslink.com", "https://www." + secureURLPrefix + ".recon.tbslink.com");
					newURL = newURL.replace("https://www.tbslink.com", "https://www." + secureURLPrefix + ".tbslink.com");
					item.setUrl(newURL);
				}
			}
		}
		
		// if we are testing locally, we need to ensure we are using http://localhost
		else if(AppUtility.isLocalHostURLOverride())
		{
			StringBuffer localURLBase = new StringBuffer( "http://localhost:" ).append(AppUtility.getAppHttpPort() );
//			log.debug("### HTTP Port is : " + AppUtility.getAppHttpPort() + " ###");
//			log.debug("### localURLBase is : " + localURLBase + " ###");
			
			//https://recon.tbslink.net/Tests.aspx
			//https://www.tbslink.net/autorecon/challengeQuestions.htm
			for(AppNavigation item : dataList)
			{
				String url = item.getUrl();
				String newURL = null;
				
//				log.debug("Original URL : " + url );
				if(!url.contains("localhost")) // don't update if the database url's already contain localhost
				{
					if(url.contains("www.tbslink.net"))
					{
						newURL = url.replace("https://www.tbslink.net", localURLBase.toString() );
					}
					if(url.contains("www.tbslink.com"))
					{
						newURL = url.replace("https://www.tbslink.com", localURLBase.toString() );
					}
				} else 
				{
					// if URL from database contains http://localhost, make sure it has the correct port number as well
					if(!url.contains( new Integer( AppUtility.getAppHttpPort() ).toString() ))
					{
						// does it contain any port number designation?  if so, make sure it's the correct one
						if( url.contains("localhost:"))
						{
							int charCount = "localhost".length();
							String endURLWithPortNum = url.substring( url.indexOf("localhost" ) + charCount );
							String endURL = endURLWithPortNum.substring( endURLWithPortNum.indexOf('/'));
							newURL = localURLBase.toString() + endURL;
						} 
						else
						{
//							log.debug("### URL does NOT contain the HTTP port number ... INSERT it ###");
							newURL = url.replace("http://localhost", localURLBase.toString() );
						}
						
					} 
				
				}

				// if we modified the URL retrieved from the database, update it in cache now
				if(newURL != null)
				{
					log.debug("### URL was changed from [" + url + "] to [" + newURL + "] ###" );
					item.setUrl(newURL);
				}
			}
		}
		return dataList;
	}

	public AppNavigationView getAppUserNavigationList(long userId, String secureURLPrefix, String localHostPrefix)
	{
		log.debug("getUserNavigationList...");
		AppNavigationView t = new AppNavigationView();
		String sql = "EXEC " + Databases.COMMON + "..p_j_GetUserNavigationMenu @User_FK = ?, @SecureURLPrefix = ?, @LocalHostPrefix = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		params.addValue(secureURLPrefix);
		params.addValue(localHostPrefix);
		this.databaseHelper.executeStoredProcedureForModelTable( sql, params, t );
		return t;
	}

	public void updateAppNavigationList(List<AppNavigation> linkList, long groupId)
	{
		SQLParameters params = new SQLParameters();
		for( AppNavigation link : linkList )
		{
			if( link.isDeleted() && link.getId() > 0 )
			{
				deleteAppNavigation(link.getId());
			}
		}

		String sql = "UPDATE " + Databases.COMMON + "..AppNavigation SET "
		+ "NavigationGroup_FK = ?, NavigationName = ?, "
		+ "NavigationOrder = ?, NavigationURL = ? "
		+ "WHERE Navigation_PK = ? ";
		for(int i = 0; i < linkList.size(); i++)
		{
			AppNavigation link = linkList.get(i);
			if( !link.isDeleted() && link.getId() > 0 )
			{
				params = new SQLParameters();
				params.addValue( link.getGroupId() );
				params.addValue( link.getName() );
				params.addValue( i + 1 );
				params.addValue( link.getUrl() );
				params.addValue( link.getId() );
				this.databaseHelper.executeUpdate( sql, params );
			}
		}
		
		sql = "INSERT " + Databases.COMMON + "..AppNavigation (NavigationGroup_FK, NavigationName, NavigationOrder, NavigationURL) "
		+ "VALUES (?, ?, ?, ?)";
		for(int i = 0; i < linkList.size(); i++)
		{
			AppNavigation link = linkList.get(i);
			if( !link.isDeleted() && link.getId() <= 0 )
			{
				params = new SQLParameters();
				params.addValue( link.getGroupId() );
				params.addValue( link.getName() );
				params.addValue( i + 1 );
				params.addValue( link.getUrl());
				this.databaseHelper.executeUpdate( sql, params );
			}
		}
	}

    //============================================================================
	//
	// AppNavigation_AppRole_Relationship
	//
	//============================================================================
	public List<AppNavigationGroup> getAppNavigationRoleList(long roleId)
	{
		Map<AppNavigationRole,AppNavigationRole> roleMap = getAppNavigationRoleMap();
		List<AppNavigationGroup> groupList = getAppNavigationGroupList();
		for(int i = 0; i < groupList.size(); i++)
		{
			AppNavigationGroup group = groupList.get(i);
			List<AppNavigation> linkList = getAppNavigationList(group.getId());
			for(int x = 0; x < linkList.size(); x++)
			{
				AppNavigation link = linkList.get(x);
				AppNavigationRole navRole = new AppNavigationRole();
				AppNavigationRole navRole2 = new AppNavigationRole();
				boolean tempbyte_0 = false;
				boolean tempbyte_1 = true;
				navRole.setNavigationId(link.getId());
				navRole.setRoleId(roleId);
				navRole.setFullAccess(tempbyte_0);
				navRole2.setNavigationId(link.getId());
				navRole2.setRoleId(roleId);
				navRole2.setFullAccess(tempbyte_1);
				if(roleMap.containsKey(navRole))
				{
					link.setSelected(true);
					link.setFullAccess(false);
				}
				else if(roleMap.containsKey(navRole2))
				{
					link.setSelected(true);
					link.setFullAccess(true);
				}
				else
				{
					link.setFullAccess(false);
				}
			}
			group.getNavigationList().addAll(linkList);
		}		
		return groupList;
	}
	
	public void updateAppNavigationRoles(List<AppNavigationGroup> groupList, long roleId)
	{
		String sql = "DELETE " + AppNavigationAppRoleRelationship.DATABASE_TABLE_NAME + " WHERE Role_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(roleId);
		this.databaseHelper.executeUpdate( sql, params );
		
		sql = "INSERT " + AppNavigationAppRoleRelationship.DATABASE_TABLE_NAME + " (Navigation_FK, Role_FK, IsFullAccess) "
		+ "VALUES (?, ?, ?)";
		for(int i = 0; i < groupList.size(); i++)
		{
			AppNavigationGroup group = groupList.get(i);
			for( int x = 0; x < group.getNavigationList().size(); x++ )
			{
				AppNavigation link = group.getNavigationList().get(x);
				if( link.isSelected() )
				{
					params = new SQLParameters();
					params.addValue(link.getId());
					params.addValue(roleId);
					if( link.isFullAccess() )
					{
						params.addValue(1);
					}
					else
					{
						params.addValue(0);
					}
					this.databaseHelper.executeUpdate( sql, params );
				}
			}
		}		
	}
	
	private Map<AppNavigationRole,AppNavigationRole> getAppNavigationRoleMap()
	{
		String sql = "SELECT * FROM " + AppNavigationAppRoleRelationship.DATABASE_TABLE_NAME;
		SQLParameters params = new SQLParameters();
		List<AppNavigationAppRoleRelationship> roleList = this.databaseHelper.queryForList(sql, params, new AppNavigationAppRoleRelationship());

		Map<AppNavigationRole,AppNavigationRole> roleMap = new HashMap<AppNavigationRole,AppNavigationRole>();
		for(AppNavigationAppRoleRelationship role : roleList)
		{
			AppNavigationRole r = new AppNavigationRole();
			r.setNavigationId( role.getNavigationId() );
			r.setRoleId( role.getRoleId() );
			r.setFullAccess( role.isFullAccess() );
			roleMap.put(r, r);
		}
		return roleMap;
	}
	
	//============================================================================
	//
	// AppNavigationGroup
	//
	//============================================================================
	public List<AppNavigationGroup> getAppNavigationGroupList()
	{
		String sql = "SELECT * FROM " + AppNavigationGroup.DATABASE_TABLE_NAME + " WITH (NOLOCK) ORDER BY GroupOrder ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new AppNavigationGroup());
	}

	public void updateAppNavigationGroupList(List<AppNavigationGroup> groupList)
	{		
		String sql = "DELETE " + AppNavigationGroup.DATABASE_TABLE_NAME + " WHERE NavigationGroup_PK = ?";
		SQLParameters params = new SQLParameters();
		for( int i = 0; i < groupList.size(); i++ )
		{
			AppNavigationGroup group = groupList.get(i);
			if( group.isDeleted() && group.getId() > 0 )
			{
				params = new SQLParameters();
				params.addValue( group.getId() );
				this.databaseHelper.executeUpdate(sql, params);
			}
		}

		sql = "UPDATE " + AppNavigationGroup.DATABASE_TABLE_NAME + " SET GroupName = ?, GroupOrder = ? "
		+ "WHERE NavigationGroup_PK = ? ";
		for( int i = 0; i < groupList.size(); i++ )
		{
			AppNavigationGroup group = groupList.get(i);
			if( !group.isDeleted() && group.getId() > 0 )
			{
				params = new SQLParameters();
				params.addValue( group.getName() );
				params.addValue( i + 1 );
				params.addValue( group.getId() );
				this.databaseHelper.executeUpdate( sql, params );
			}
		}
		
		sql = "INSERT AppNavigationGroup ( GroupName, GroupOrder) VALUES (?, ?) ";
		for( int i = 0; i < groupList.size(); i++ )
		{
			AppNavigationGroup group = groupList.get(i);
			if( !group.isDeleted() && group.getId() <= 0 )
			{
				params = new SQLParameters();
				params.addValue( group.getName() );
				params.addValue( i + 1 );
				this.databaseHelper.executeUpdate( sql, params );
			}
		}
	} 
	
	/*
    //============================================================================
	//
	// AppNavigationApp
	//
	//============================================================================
	public List<App> getAppNavigationAppList() {
		List<App> dataList = new ArrayList<App>();
		String sql = "SELECT * FROM " + App.DATABASE_TABLE_NAME + " ORDER BY GroupOrder ";
		SQLParameters params = new SQLParameters();
		dataList = this.databaseHelper.queryForList(sql, params, new App());
		return dataList;  
	}

	//@Transactional
	public void updateAppNavigationAppList(List<App> appList) {
		String sql = "DELETE App WHERE NavigationGroup_PK = :NavigationGroup_PK";
		MapSqlParameterSource params;
		for(int i = 0; i < appList.size(); i++)
		{
			App group = appList.get(i);
			if(group.isDeleted() && group.getField(App.Field.APP).getLongValue() > 0)
			{
				params = new MapSqlParameterSource();
				params.addValue("App_PK", group.getField(App.Field.APP).getLongValue());
				this.jdbcTemplate.update(sql, params);
			}
		}

		sql = "UPDATE App SET GroupName = :GroupName, GroupOrder = :GroupOrder "
		+ "WHERE APP_PK = :APP_PK ";
		for(int i = 0; i < appList.size(); i++)
		{
			AppNavigationGroup group = appList.get(i);
			if(!group.isDeleted() && group.getField(AppNavigationGroup.Field.ID).getLongValue() > 0)
			{
				params = new MapSqlParameterSource();
				params.addValue("GroupName", group.getField(AppNavigationGroup.Field.NAME).getStringValue());
				params.addValue("GroupOrder", i + 1);
				params.addValue("NavigationGroup_PK", group.getField(AppNavigationGroup.Field.ID).getLongValue());
				this.jdbcTemplate.update(sql, params);
			}
		}
		
		sql = "INSERT AppNavigationGroup (GroupName, GroupOrder) "
		+ "VALUES (:GroupName, :GroupOrder)";
		for(int i = 0; i < groupList.size(); i++)
		{
			AppNavigationGroup group = groupList.get(i);
			if(!group.isDeleted() && group.getField(AppNavigationGroup.Field.ID).getLongValue() <= 0)
			{
				params = new MapSqlParameterSource();
				params.addValue("GroupName", group.getField(AppNavigationGroup.Field.NAME).getStringValue());
				params.addValue("GroupOrder", i + 1);
				this.jdbcTemplate.update(sql, params);
			}
		}
		*/
//	}

	
    //============================================================================
	//
	// AppRole
	//
	//============================================================================
    public List<AppRole> getAppRoleList()
	{
		String sql = "SELECT * FROM " + AppRole.DATABASE_TABLE_NAME + " WITH (NOLOCK) ORDER BY RoleName ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new AppRole());
	}
    
    //============================================================================
	//
    // AppSession
    //
	//============================================================================	
	public void activateAppSession(long userId, String sessionKey)
	{
		String sql = "EXEC " +  Databases.COMMON + "..p_j_ActivateUserSession @User_FK=?, @SessionKey=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		params.addValue(sessionKey);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);		
	}

	public String addAppSession(long userId)
	{
    	Date d = new Date();
    	String timestamp = ConvertUtility.convertDateToString(d, "MMddyyyy_hh:mm:ss:SSSS");
		String key = userId + "_" + timestamp;
		String encryptedKey = EncryptionUtility.getInstance().encrypt(key);

		String sql = "EXEC " +  Databases.COMMON + "..p_j_AddUserSession @User_FK=?, @SessionKey=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		params.addValue(encryptedKey);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);		
		return encryptedKey;
	}

	public void deactivateAppSession(long userId, String sessionKey)
	{
		String sql = "UPDATE AppSession SET IsDeleted = 1 WHERE User_FK = ? AND SessionKey = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		params.addValue(sessionKey);
		this.databaseHelper.executeUpdate(sql, params);
	}	

	public void deleteAppSession(DataBatchManager batchManager, long userId)
	{
		String sql = "DELETE AppSession WHERE User_FK = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		batchManager.executeUpdate(sql, params);
	}

	public void expireAppSessions(int expireInMinutes)
	{	
		String sql = "UPDATE AppSession SET SessionKey = '', IsDeleted = 1 "
		+ " WHERE DATEDIFF(minute, LastAccessDateTime, getdate()) > ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(expireInMinutes);
		this.databaseHelper.executeUpdate(sql, params);
	}

	public ViewAppUserSession validateAppUserSession(ViewAppUserSession user)
	{
		return validateAppUserSession(user, false);
	}
	
	public ViewAppUserSession validateAppUserSession(ViewAppUserSession user, boolean isLoginInProgress)
	{
		if(user == null || user.getUserId() == 0 || user.getSessionKey().length() == 0)
		{
			return null;
		}
    	String sql = "EXEC " + Databases.COMMON + "..p_j_ValidateUserSession @User_FK=?, @SessionKey=?, @WorkingProgram_FK=?, @WorkingSourceInstitution_FK=?, @WorkingDate=?, @LoginInProgress=? ";
		SQLParameters params = new SQLParameters();
		params.addValue( user.getUserId() );
		params.addValue( user.getSessionKey() );
		params.addValue( user.getWorkingProgramId() );
		params.addValue( user.getWorkingSourceInstitutionId() );
		params.addValue( user.getWorkingDate() );
		params.addValue( isLoginInProgress );
		ViewAppUserSession item = (ViewAppUserSession)this.databaseHelper.executeStoredProcedureForObject(sql, params, new ViewAppUserSession());
		if(item != null)
		{// The passed in user has the menu ID that we want, take it from there rather than what's in the database table
			item.setNavigationId( user.getNavigationId() );
		}
		return item;
	}

	//============================================================================
	//
	// AppUser
	//
	//============================================================================
    /*
     * (non-Javadoc)
     * @see com.totalbanksolutions.framework.dao.ServicesDAO#addAppUser(com.totalbanksolutions.framework.bean.database.table.AppUser, java.lang.String)
     * 
     * Need to validate that both the user ID meets the security standards, and also the password.
     * For now, user ID and password security criteria is enforced at the system level, and stored 
     * within the AppConfiguration database table.
     * 
     */
	public long addAppUser(AppUser user, String userPassword)
	{
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		long userId = -1;
		try
		{
			batchManager.beginTransaction();

			long contactId = addDefaultContact(batchManager, user);
			String password = EncryptionUtility.getInstance().encrypt(userPassword);
			
			user.getField(AppUser.Field.PASSWORD).setValue(password);
			user.getField(AppUser.Field.CONTACT_ID).setValue(contactId);
			user.getField(AppUser.Field.IS_PASSWORD_RESET).setValue(true);
			user.getField(AppUser.Field.IS_SECRET_QUESTIONS_SET).setValue(false);
			user.getField(AppUser.Field.VERSION_NUMBER).setValue(1);
			
			String sql = user.getSQLInsertStatementNew();
			SQLParameters params = user.getSQLInsertParameters();
			userId = batchManager.executeInsert(sql, params);			
			
			user.getField(AppUser.Field.USER_ID).setValue(userId);
			
			updateAppUserRole(batchManager, user);
			updateAppUserSourceInstitution(batchManager, user);
			updateAppUserDepositInstitution(batchManager, user);

			batchManager.commitTransaction();
		}
		finally
		{
			batchManager.close();
		}
		return userId;
	}

	public int deleteAppUser(long userId)
	{
		int rowsAffected = 0;
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		try
		{
		    AppUser user = getAppUserDetails(userId);
		    long contactId = user.getField(AppUser.Field.CONTACT_ID).getLongValue();
		    long mailingAddressId = user.getContact().getField(Contact.Field.MAILING_ADDRESS_ID).getLongValue();
		    
		    batchManager.beginTransaction();

			deleteUserChallengeQuestion(batchManager, userId);
			deleteAppUserRole(batchManager, userId);
			deleteAppUserDepositInstitution(batchManager, userId);
			deleteAppUserSourceInstitution(batchManager, userId);
			deleteAppSession(batchManager, userId);

			String sql = "DELETE FROM AppUser WHERE User_PK = ?";
			SQLParameters params = new SQLParameters();
			params.addValue(userId);
			rowsAffected = batchManager.executeUpdate(sql, params);
			if(contactId > 0)
			{
				deleteContact(batchManager, contactId, mailingAddressId);
			}
			
			batchManager.commitTransaction();
		}
		finally
		{
			batchManager.close();
		}
		return rowsAffected;
	}

	public AppUser getAppUserByUserName(String userName)
	{
		String sql = "SELECT a.*, b.FullName, b.MailingAddress_FK "
		+ "FROM " + AppUser.DATABASE_TABLE_NAME + " a WITH (NOLOCK)  "
		+ "LEFT JOIN " + Contact.DATABASE_TABLE_NAME + " b WITH (NOLOCK) ON a.Contact_FK = b.Contact_PK "
		+ " WHERE UserName = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userName);
		String[] bindList = {"Contact"};
		return (AppUser)this.databaseHelper.queryForObject(sql, params, new AppUser(bindList));
	}
	
    public AppUser getAppUserDetails(long userId)
	{
		String sql = "SELECT a.*, b.FullName, b.MailingAddress_FK "
		+ "FROM " + AppUser.DATABASE_TABLE_NAME + " a WITH (NOLOCK) "
		+ "LEFT JOIN " + Contact.DATABASE_TABLE_NAME + " b WITH (NOLOCK) ON a.Contact_FK = b.Contact_PK "
		+ " WHERE User_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		String[] bindList = {"Contact"};
		return (AppUser)this.databaseHelper.queryForObject(sql, params, new AppUser(bindList));
	}

	public List<AppUser> getAppUserList(AppUserFilter appUserFilter)
	{
		String userNameFilter = appUserFilter.getField(AppUserFilter.Field.USER_NAME).getStringValue();
		long appUserTypeIdFilter = appUserFilter.getField(AppUserFilter.Field.USER_TYPE_ID).getLongValue();
		long roleIdFilter = appUserFilter.getField(AppUserFilter.Field.ROLE_ID).getLongValue();
		long sourceInstitutionIdFilter = appUserFilter.getField(AppUserFilter.Field.SOURCE_INST_ID).getLongValue();
		long depositInstitutionIdFilter = appUserFilter.getField(AppUserFilter.Field.DEPOSIT_INST_ID).getLongValue();
		String sqlWhere = "";
		if(userNameFilter.length() > 0)
		{
			if(userNameFilter.equalsIgnoreCase("0 - 9"))
			{
				sqlWhere = "WHERE LEFT(a.UserName,1) BETWEEN '0' AND '9'";
			}
			else
			{
				sqlWhere = "WHERE LEFT(a.UserName,1) = '" + userNameFilter + "'";
			}
		}
		if(appUserTypeIdFilter > 0)
		{
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "a.UserType_FK = " + appUserTypeIdFilter + " ";
		}
		if(roleIdFilter > 0)
		{
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "EXISTS (SELECT 1 FROM " + AppUserAppRoleRelationship.DATABASE_TABLE_NAME + " "
			+ "WHERE User_FK = a.User_PK AND Role_FK = " + roleIdFilter + ") ";
		}
		if(sourceInstitutionIdFilter > 0)
		{
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "EXISTS (SELECT 1 FROM " + AppUserSourceInstitutionRelationship.DATABASE_TABLE_NAME + " "
			+ "WHERE User_FK = a.User_PK AND SourceInstitution_FK = " + sourceInstitutionIdFilter + ") ";
		}
		if(depositInstitutionIdFilter > 0)
		{
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "EXISTS (SELECT 1 FROM " + AppUserDepositInstitutionRelationship.DATABASE_TABLE_NAME + " "
			+ "WHERE User_FK = a.User_PK AND DepositInstitution_FK = " + depositInstitutionIdFilter + ") ";
		}
		if(appUserFilter.getAllowedSourceInstitutionIdList() != null)
		{
			String sourceInstitutionIds = Arrays.toString(appUserFilter.getAllowedSourceInstitutionIdList().toArray());
			sourceInstitutionIds = sourceInstitutionIds.replace("[", "");
			sourceInstitutionIds = sourceInstitutionIds.replace("]", "");
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "EXISTS (SELECT 1 FROM " + AppUserSourceInstitutionRelationship.DATABASE_TABLE_NAME + " "
			+ "WHERE User_FK = a.User_PK AND SourceInstitution_FK IN ( " + sourceInstitutionIds + ")) ";			
		}
		if(appUserFilter.getAllowedDepositInstitutionIdList() != null)
		{
			String depositInstitutionIds = Arrays.toString(appUserFilter.getAllowedDepositInstitutionIdList().toArray());
			depositInstitutionIds = depositInstitutionIds.replace("[", "");
			depositInstitutionIds = depositInstitutionIds.replace("]", "");
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "EXISTS (SELECT 1 FROM " + AppUserDepositInstitutionRelationship.DATABASE_TABLE_NAME + " "
			+ "WHERE User_FK = a.User_PK AND DepositInstitution_FK IN ( " + depositInstitutionIds + ")) ";			
		}
		String sql = "SELECT TOP (" + MAX_ROWS + ") a.User_PK, a.UserName, a.IsPasswordReset, a.IsSecretQuestionsSet, a.IsDeleted, "
		+ "b.FullName, b.BusinessPhone, b.EmailAddress, b.DepartmentName, c.UserType, a.IsMandatoryContactInfoSet, a.IsLocked "
		+ "FROM " + AppUser.DATABASE_TABLE_NAME + " a WITH (NOLOCK) "
		+ "LEFT JOIN " + Contact.DATABASE_TABLE_NAME + " b WITH (NOLOCK) ON a.Contact_FK = b.Contact_PK "
		+ "LEFT JOIN " + AppUserType.DATABASE_TABLE_NAME + " c WITH (NOLOCK) ON a.UserType_FK = c.UserType_PK "
		+ sqlWhere + " "
		+ "ORDER BY a.UserName ";
		SQLParameters params = new SQLParameters();
		String[] bindList = {"Contact", "AppUserType"};
		return this.databaseHelper.queryForList(sql, params, new AppUser(bindList));
	}

	public void resetAppUserFailedLoginAttempts( AppUser user )
	{
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		try
		{
			batchManager.beginTransaction();

			String sql = "UPDATE AppUser SET FailedLoginAttempts = 0, LastModifiedDateTime = getdate(), "
			+ "VersionNumber = ISNULL(VersionNumber,0) + 1 "
			+ "WHERE User_PK = ? ";				
			
			SQLParameters params = new SQLParameters();
			params.addValue(user.getField(AppUser.Field.USER_ID).getValue());
			
			batchManager.executeUpdate(sql, params);
			batchManager.commitTransaction();
		}
		finally
		{
			batchManager.close();
		}
	}

	public void resetAppUserPassword(long userId, String defaultUserPassword)
	{
		String password = EncryptionUtility.getInstance().encrypt(defaultUserPassword);
	    String sql = "UPDATE AppUser SET UserPassword = ?, IsPasswordReset = 1, "
	    + "PasswordChangeDateTime = getdate(), LoginFailedAttempts = 0 "
	    + "WHERE User_PK = ? ";
	    SQLParameters params = new SQLParameters();
		params.addValue(password);
		params.addValue(userId);
		databaseHelper.executeUpdate(sql, params);
	}

	public void unlockAppUser( long userId )
	{
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		try
		{
			batchManager.beginTransaction();

			String sql = "UPDATE AppUser SET " +
							"IsLocked = 0, " +
							"LoginFailedAttempts = 0, " +
							"LastModifiedDateTime = getdate(), " +
							"VersionNumber = ISNULL(VersionNumber,0) + 1 " +
							"WHERE User_PK = ? ";				
			
			SQLParameters params = new SQLParameters();
			params.addValue(userId);
			
			batchManager.executeUpdate(sql, params);
			batchManager.commitTransaction();
		}
		finally
		{
			batchManager.close();
		}
	}
	
	public void updateAppUser(AppUser user)
	{
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		try
		{
			batchManager.beginTransaction();

			String sql = "UPDATE AppUser SET UserName = ?, UserType_FK = ?, Contact_FK = ?, IsDeleted = ?, "
			+ "LastModifiedDateTime = getdate(), LastModifiedByUserName = ?, VersionNumber = ISNULL(VersionNumber,0) + 1 "
			+ "WHERE User_PK = ? ";				
			if(user.getField(AppUser.Field.CONTACT_ID).getLongValue() == new Long(0))
			{
				long contactId = addDefaultContact(batchManager, user);
				user.getField(AppUser.Field.CONTACT_ID).setValue(contactId);
			}
			SQLParameters params = new SQLParameters();
			params.addValue(user.getField(AppUser.Field.USER_NAME).getValue());
			params.addValue(user.getField(AppUser.Field.USER_TYPE_ID).getValue());
			params.addValue(user.getField(AppUser.Field.CONTACT_ID).getValue());
			params.addValue(user.getField(AppUser.Field.IS_DELETED).getValue());
			params.addValue(user.getField(AppUser.Field.LAST_MODIFIED_BY_USER).getValue());
			params.addValue(user.getField(AppUser.Field.USER_ID).getValue());
			
			batchManager.executeUpdate(sql, params);
			
			updateAppUserRole(batchManager, user);
			updateAppUserSourceInstitution(batchManager, user);
			updateAppUserDepositInstitution(batchManager, user);

			batchManager.commitTransaction();
		}
		finally
		{
			batchManager.close();
		}
	}

	public void updateAppUserPassword(long userId, String textPassword, int minPasswordLength)
	{
		if(textPassword.length() < minPasswordLength)
		{
			throw new RuntimeException("Password must be at least " + minPasswordLength + " characters in length.");
		}
		else
		{
		    String sql = "UPDATE AppUser SET UserPassword = ?, IsPasswordReset = 0, "
		    + "PasswordChangeDateTime = getdate(), LoginFailedAttempts = 0 "
		    + "WHERE User_PK = ? ";
	
			String password = EncryptionUtility.getInstance().encrypt(textPassword);
		    SQLParameters params = new SQLParameters();
			params.addValue(password);
			params.addValue(userId);
			databaseHelper.executeUpdate(sql, params);
		}
	}

	public ViewAppUserSession validateLogin(String userName, String textPassword)
	{
		String password = EncryptionUtility.getInstance().encrypt(textPassword);
    	String sql = "EXEC " + Databases.COMMON + "..p_j_ValidateUserLogin @UserName=?, @UserPassword=? ";
		log.debug("### validateLogin calls : " + sql + " ###");
		
    	SQLParameters params = new SQLParameters();
		params.addValue(userName);
		params.addValue(password);
		ViewAppUserSession item = (ViewAppUserSession)this.databaseHelper.executeStoredProcedureForObject(sql, params, new ViewAppUserSession());
		if(item == null)
		{
			return null;
		}
		return item;
	}

	//============================================================================
	//
	// AppUser_AppRole_Relationship
	//
	//============================================================================
	private void deleteAppUserRole(DataBatchManager batchManager, long userId)
	{
		String sql = "DELETE FROM AppUser_AppRole_Relationship WHERE User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		batchManager.executeUpdate(sql, params);
	}

	public List<Long> getAppUserRoleIdList(long userId)
	{
		List<Long> dataList = new ArrayList<Long>();
		String sql = "SELECT Role_FK "
		+ "FROM AppUser_AppRole_Relationship WITH (NOLOCK) "
		+ "WHERE User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		dataList = this.databaseHelper.queryForLongList(sql, params);
		return dataList;
	}

	private void updateAppUserRole(DataBatchManager batchManager, AppUser user)
	{
		//long userId = user.getLongValue(AppUser.Field.USER_ID);
		long userId = user.getField(AppUser.Field.USER_ID).getLongValue();
		
		deleteAppUserRole(batchManager, userId);
		String sql = "INSERT AppUser_AppRole_Relationship (User_FK, Role_FK) "
		+ "VALUES (?, ?)";
		batchManager.prepareSQL(sql);
		for(Long roleId : user.getRoleIdList())
		{
			SQLParameters params = new SQLParameters();
			params.addValue(userId);
			params.addValue(roleId);
			batchManager.addBatch(params);
		}		
		batchManager.executeBatch();
	}
	
	//============================================================================
	//
	// AppUser_DepositInstitution_Relationship
	//
	//============================================================================
	private void deleteAppUserDepositInstitution(DataBatchManager batchManager, long userId)
	{
		String sql = "DELETE AppUser_DepositInstitution_Relationship WHERE User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		batchManager.executeUpdate(sql, params);
	}
    
	public List<Long> getAppUserDepositInstitutionIdList(long userId)
	{
		List<Long> dataList = new ArrayList<Long>();
		String sql = "SELECT DepositInstitution_FK "
		+ "FROM AppUser_DepositInstitution_Relationship WITH (NOLOCK) "
		+ "WHERE User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		dataList = this.databaseHelper.queryForLongList(sql, params);
		return dataList;
	}

	private void updateAppUserDepositInstitution(DataBatchManager batchManager, AppUser user)
	{
		//long userId = user.getLongValue(AppUser.Field.USER_ID);
		long userId = user.getField(AppUser.Field.USER_ID).getLongValue();
		
		deleteAppUserDepositInstitution(batchManager, userId);
		if(user.getField(AppUser.Field.USER_TYPE_ID).getLongValue() == AppUserType.Values.BANK.getId())
		{
			String sql = "INSERT AppUser_DepositInstitution_Relationship (User_FK, DepositInstitution_FK) "
			+ "VALUES (?, ?)";
			batchManager.prepareSQL(sql);
			for(Long depositInstitutionId : user.getDepositInstitutionIdList())
			{
				SQLParameters params = new SQLParameters();
				params.addValue(userId);
				params.addValue(depositInstitutionId);
				batchManager.addBatch(params);
			}
			batchManager.executeBatch();
		}
	}

	//============================================================================
	//
	// AppUser_SourceInstitution_Relationship
	//
	//============================================================================
	private void deleteAppUserSourceInstitution(DataBatchManager batchManager, long userId)
	{
		String sql = "DELETE AppUser_SourceInstitution_Relationship WHERE User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		batchManager.executeUpdate(sql, params);
	}

	public List<Long> getAppUserProgramIdList(long userId)
	{
		String sql = "SELECT DISTINCT b.Program_FK "
		+ "FROM " + AppUserSourceInstitutionRelationship.DATABASE_TABLE_NAME + " a WITH (NOLOCK) "
		+ "INNER JOIN " + SourceInstitution.DATABASE_TABLE_NAME + " b WITH (NOLOCK) ON a.SourceInstitution_FK = b.SourceInstitution_PK "
		+ "WHERE a.User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		return this.databaseHelper.queryForLongList(sql, params);
	}

	public List<Long> getAppUserSourceInstitutionIdList(long userId)
	{
		String sql = "SELECT SourceInstitution_FK "
		+ "FROM " + AppUserSourceInstitutionRelationship.DATABASE_TABLE_NAME + " WITH (NOLOCK) "
		+ "WHERE User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		return this.databaseHelper.queryForLongList(sql, params);
	}

	private void updateAppUserSourceInstitution(DataBatchManager batchManager, AppUser user)
	{
		long userId = user.getField(AppUser.Field.USER_ID).getLongValue();
		deleteAppUserSourceInstitution(batchManager, userId);
		if(user.getField(AppUser.Field.USER_TYPE_ID).getLongValue() == AppUserType.Values.BROKER.getId())
		{
			String sql = "INSERT AppUser_SourceInstitution_Relationship (User_FK, SourceInstitution_FK) "
			+ "VALUES (?, ?)";
			batchManager.prepareSQL(sql);
			for(Long sourceInstitutionId : user.getSourceInstitutionIdList())
			{
				SQLParameters params = new SQLParameters();
				params.addValue(userId);
				params.addValue(sourceInstitutionId);
				batchManager.addBatch(params);
			}
			batchManager.executeBatch();
		}
	}
	
	//============================================================================
	//
	// AppUserType
	//
	//============================================================================
    public List<AppUserType> getAppUserTypeList()
	{
		String sql = "SELECT * FROM " + AppUserType.DATABASE_TABLE_NAME + " WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new AppUserType());
	}

	//============================================================================
	//
    // ChallengeQuestion
    //
	//============================================================================
	private int deleteUserChallengeQuestion(DataBatchManager batchManager, long userId)
	{
		String sql = "DELETE FROM " + AppUserChallengeQuestionRelationship.DATABASE_TABLE_NAME + " "
		+ "WHERE User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		int rowsAffected = batchManager.executeUpdate(sql, params);
		return rowsAffected;
	}

    public List<ChallengeQuestion> getChallengeQuestionList()
	{
		String sql = "SELECT * "
		+ " FROM " + ChallengeQuestion.DATABASE_TABLE_NAME + " WITH (NOLOCK) "
		+ " ORDER BY ChallengeQuestion_PK ";
		SQLParameters params = new SQLParameters();
		return (List<ChallengeQuestion>)this.databaseHelper.queryForList(sql, params, new ChallengeQuestion());
	}

    public AppUserChallengeQuestionRelationship[] getChallengeResponseArray(long userId)
	{
    	AppUserChallengeQuestionRelationship[] dataArray = new AppUserChallengeQuestionRelationship[5];
		List<AppUserChallengeQuestionRelationship> dataList = getChallengeResponseList(userId);
		for(int i = 0; i < dataList.size(); i++)
		{
			AppUserChallengeQuestionRelationship item = dataList.get(i);
			dataArray[i] = item;
			if(i == 4) break;
		}
		return dataArray;
	}

    public List<AppUserChallengeQuestionRelationship> getChallengeResponseList(long userId) 
	{
		String sql = "SELECT a.*, b.* "
		+ " FROM " + AppUserChallengeQuestionRelationship.DATABASE_TABLE_NAME + " a WITH (NOLOCK) "
		+ " INNER JOIN " + ChallengeQuestion.DATABASE_TABLE_NAME + " b WITH (NOLOCK) ON a.ChallengeQuestion_FK = b.ChallengeQuestion_PK "
		+ " WHERE a.User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		String[] bindList = {"ChallengeQuestion"};
		return (List<AppUserChallengeQuestionRelationship>)this.databaseHelper.queryForList(sql, params, new AppUserChallengeQuestionRelationship(bindList));
	}
    
    public ChallengeQuestion getRandomChallengeQuestion(long userId)
	{
		String sql = "EXEC " + ChallengeQuestion.DATABASE_NAME + "..p_j_GetRandomChallengeQuestion @User_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		return (ChallengeQuestion)this.databaseHelper.executeStoredProcedureForObject(sql, params, new ChallengeQuestion());
	}
    
	public void resetChallengeQuestions(long userId)
	{
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		try
		{
			batchManager.beginTransaction();

			String sql = "UPDATE " + AppUser.DATABASE_TABLE_NAME + " SET IsSecretQuestionsSet = 0 WHERE User_PK = ?";
			batchManager.prepareSQL(sql);
			SQLParameters params = new SQLParameters();
			params.addValue(userId);
			batchManager.addBatch(params);
	
			sql = "DELETE FROM " + AppUserChallengeQuestionRelationship.DATABASE_TABLE_NAME + " WHERE User_FK = ?";
			batchManager.prepareSQL(sql);
			params = new SQLParameters();
			params.addValue(userId);
			batchManager.addBatch(params);

			batchManager.executeBatch();
			batchManager.commitTransaction();
		}
		finally
		{
			batchManager.close();
		}
	}

	public void updateUserChallengeQuestions(long userId, AppUserChallengeQuestionRelationship[] questions)
	{
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		try
		{
			batchManager.beginTransaction();

			String sql = "UPDATE " + AppUser.DATABASE_TABLE_NAME + " SET IsSecretQuestionsSet = 1 WHERE User_PK = ? ";
			batchManager.prepareSQL(sql);
			SQLParameters params = new SQLParameters();
			params.addValue(userId);
			batchManager.addBatch(params);

			sql = "DELETE FROM " + AppUserChallengeQuestionRelationship.DATABASE_TABLE_NAME + " WHERE User_FK = ? ";
			batchManager.prepareSQL(sql);
			params = new SQLParameters();
			params.addValue(userId);
			batchManager.addBatch(params);
			
			for(AppUserChallengeQuestionRelationship question : questions)
			{
				boolean isInitialized = false;
				if(!isInitialized)
				{
					sql = question.getSQLInsertStatementNew();
					batchManager.prepareSQL(sql);
					isInitialized = true;
				}
				params = question.getSQLInsertParameters();
				batchManager.addBatch(params);
			}

			batchManager.executeBatch();
			batchManager.commitTransaction();
		}
		finally
		{
			batchManager.close();
		}
	}

	public boolean validateChallengeReponse(long userId, long questionId, String questionResponse)
	{
		boolean status = false;
		List<AppUserChallengeQuestionRelationship> responseList = getChallengeResponseList(userId);
		if(responseList != null)
		{
			for(AppUserChallengeQuestionRelationship response : responseList)
			{
				if(response.getQuestionId() == questionId)
				{
					if(response.getResponse().equalsIgnoreCase(questionResponse))
					{
						status = true;
					}
					break;
				}
			}
		}
		return status;
	}

	//============================================================================
	//
	// Contact
	//
	//============================================================================
	public long addContact(Contact contact)
	{
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		long contactId = -1;
		try
		{
			batchManager.beginTransaction();
			contactId = addContact(batchManager, contact);
			batchManager.commitTransaction();
		}
		finally
		{
			batchManager.close();
		}
		return contactId;
	}
		public double addContactNew(Contact contact)
	{
	
		double contactId = -1;
		try
		{

			String sql = "EXEC " + "TBS_Common" + "..p_GetContacts @Action=?,"
			+ " @FullName = ?, @CompanyName = ?, @DepartmentName = ?, "
		    + " @JobTitle = ?, @BusinessPhone = ?, @BusinessFax = ?, @MobilePhone = ?, "
	        + "@EmailAddress = ?, @MailingAddress_FK = ?, @Comments = ?, @IsDeleted = ?, "
	        + "@LastModifiedDateTime = '', @LastModifiedByUserName = ?, @VersionNumber = 0, "
	        + "@ContactInstitutionType_FK = ?, @DepositInstitution_FK = ?, @SourceInstitution_FK = ?, @Line1 = ?, "
            + "@Line2 = ?, @Line3 = ?, @Line4 = ?, @City = ?, @State_FK = ?,"
            + "@ZipCode = ?, @Country_FK = ?";
			SQLParameters params = new SQLParameters();
			params.addValue("Insert");
			params.addValue(contact.getField(Contact.Field.FULL_NAME).getStringValue());
			params.addValue(contact.getField(Contact.Field.COMPANY_NAME).getStringValue());
			params.addValue(contact.getField(Contact.Field.DEPARTMENT_NAME).getStringValue());
			params.addValue(contact.getField(Contact.Field.JOB_TITLE).getStringValue());
			params.addValue(contact.getField(Contact.Field.BUSINESS_PHONE).getStringValue());
			params.addValue(contact.getField(Contact.Field.BUSINESS_FAX).getStringValue());
			params.addValue(contact.getField(Contact.Field.MOBILE_PHONE).getStringValue());
			params.addValue(contact.getField(Contact.Field.EMAIL_ADDRESS).getStringValue());
			params.addValue(contact.getField(Contact.Field.MAILING_ADDRESS_ID).getLongValue());
			params.addValue(contact.getField(Contact.Field.COMMENTS).getStringValue());
			params.addValue(contact.getField(Contact.Field.IS_DELETED).getBooleanValue());
			params.addValue(contact.getField(Contact.Field.LAST_MODIFIED_BY_USER).getStringValue());
			params.addValue(contact.getField(Contact.Field.CONTACTINSTITUTION_TYPE).getLongValue());
			params.addValue(contact.getField(Contact.Field.DEPOSIT_INSTITUTION).getLongValue());
			params.addValue(contact.getField(Contact.Field.SOURCE_INSTITUTION).getLongValue());
			params.addValue(contact.getMailingAddress().getLine1());
			params.addValue(contact.getMailingAddress().getLine2());
			params.addValue(contact.getMailingAddress().getLine3());
			params.addValue(contact.getMailingAddress().getLine4());
			params.addValue(contact.getMailingAddress().getCityName());
			params.addValue(contact.getMailingAddress().getStateId());
			params.addValue(contact.getMailingAddress().getZipCode());
			params.addValue(contact.getMailingAddress().getCountryId());
			
			log.debug("addContactNewTest");
			contactId = this.databaseHelper.executeStoredProcedureForDouble(sql, params);
			log.debug("addContactNewContactID=" + contactId);
		}
		finally
		{
		
		}
		return contactId;
	}
	private long addContact(DataBatchManager batchManager, Contact contact)
	{
		long mailingAddressId = addMailingAddress(batchManager, contact);
		contact.getField(Contact.Field.MAILING_ADDRESS_ID).setValue(mailingAddressId);

		String sql = contact.getSQLInsertStatementNew();
		SQLParameters params = contact.getSQLInsertParameters();
		long contactId = batchManager.executeInsert(sql, params);
		return contactId;
	}

	private long addDefaultContact(DataBatchManager batchManager, AppUser user)
	{
		Contact contact = new Contact();
		contact.getField(Contact.Field.FULL_NAME).setValue(user.getField(AppUser.Field.USER_NAME).getStringValue());
		contact.getField(Contact.Field.LAST_MODIFIED_BY_USER).setValue(user.getField(AppUser.Field.LAST_MODIFIED_BY_USER).getStringValue());
		contact.getMailingAddress().setStateId( new Long(30) ); // 30 = NJ
		contact.getMailingAddress().setCountryId( new Long(1) ); // 1 = US
		long contactId = addContact(batchManager, contact);
		return contactId;
	}
	
	public int deleteContact(long contactId)
	{
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		int rowsAffected = 0;
		try
		{
		    Contact contact = getContactDetails(contactId);
		    long mailingAddressId = contact.getField(Contact.Field.MAILING_ADDRESS_ID).getLongValue();
			batchManager.beginTransaction();
			rowsAffected = deleteContact(batchManager, contactId, mailingAddressId);
			batchManager.commitTransaction();
		}
		finally
		{
			batchManager.close();
		}
		return rowsAffected;
	}

	private int deleteContact(DataBatchManager batchManager, long contactId, long mailingAddressId)
	{
		int rowsAffected = 0;
		String sql = "DELETE FROM Contacts WHERE Contact_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(contactId);
		rowsAffected = batchManager.executeUpdate(sql, params);
		deleteMailingAddress(batchManager, mailingAddressId);
		return rowsAffected;
	}
	
	public Contact getContactDetails(long contactId) 
	{
		String sql = "SELECT a.*, b.User_PK, b.UserName, c.* "
		+ "FROM " + Contact.DATABASE_TABLE_NAME + " a WITH (NOLOCK) "
		+ "LEFT JOIN " + AppUser.DATABASE_TABLE_NAME + " b WITH (NOLOCK) ON a.Contact_PK = b.Contact_FK "
		+ "LEFT JOIN " + MailingAddress.DATABASE_TABLE_NAME + " c WITH (NOLOCK) ON a.MailingAddress_FK = c.MailingAddress_PK "
		+ "WHERE a.Contact_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(contactId);
		String[] bindList = {"AppUser", "MailingAddress"};
		return (Contact)this.databaseHelper.queryForObject(sql, params, new Contact(bindList));
	}

	public List<Contact> getContactList(AppUserFilter appUserFilter)
	{
		String fullNameFilter = appUserFilter.getField(AppUserFilter.Field.FULL_NAME).getStringValue();
		long appUserTypeIdFilter = appUserFilter.getField(AppUserFilter.Field.USER_TYPE_ID).getLongValue();		
		String sqlWhere = ""; 
		if(fullNameFilter.length() > 0)
		{
			if(fullNameFilter.equalsIgnoreCase("0 - 9"))
			{
				sqlWhere = "WHERE LEFT(a.FullName,1) BETWEEN '0' AND '9'";
			}
			else
			{
				sqlWhere = "WHERE LEFT(a.FullName,1) = '" + fullNameFilter + "'";
			}
		}
		if(appUserTypeIdFilter > 0)
		{
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "b.UserType_FK = " + appUserTypeIdFilter + " ";
		}
		if(appUserFilter.getAllowedSourceInstitutionIdList() != null)
		{
			String sourceInstitutionIds = Arrays.toString(appUserFilter.getAllowedSourceInstitutionIdList().toArray());
			sourceInstitutionIds = sourceInstitutionIds.replace("[", "");
			sourceInstitutionIds = sourceInstitutionIds.replace("]", "");
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "EXISTS (SELECT 1 FROM " + AppUserSourceInstitutionRelationship.DATABASE_TABLE_NAME + " "
			+ "WHERE User_FK = b.User_PK AND SourceInstitution_FK IN ( " + sourceInstitutionIds + ")) ";			
		}
		if(appUserFilter.getAllowedDepositInstitutionIdList() != null)
		{
			String depositInstitutionIds = Arrays.toString(appUserFilter.getAllowedDepositInstitutionIdList().toArray());
			depositInstitutionIds = depositInstitutionIds.replace("[", "");
			depositInstitutionIds = depositInstitutionIds.replace("]", "");
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "EXISTS (SELECT 1 FROM " + AppUserDepositInstitutionRelationship.DATABASE_TABLE_NAME + " "
			+ "WHERE User_FK = b.User_PK AND DepositInstitution_FK IN ( " + depositInstitutionIds + ")) ";			
		}		
		String sql = "SELECT TOP (" + MAX_ROWS + ") a.Contact_PK, a.FullName, a.CompanyName, a.DepartmentName, a.BusinessPhone, a.EmailAddress, a.IsDeleted, b.UserName "
		+ "FROM " + Contact.DATABASE_TABLE_NAME + " a WITH (NOLOCK) "
		+ "LEFT JOIN " + AppUser.DATABASE_TABLE_NAME + " b WITH (NOLOCK) ON a.Contact_PK = b.Contact_FK "
		+ sqlWhere + " "
		+ "ORDER BY a.FullName ";
		SQLParameters params = new SQLParameters();
		String[] bindList = {"AppUser"};
		return this.databaseHelper.queryForList(sql, params, new Contact(bindList));
	}
	
    public List<ContactInstitutionType> getContactInstitutionTypeList() 
	{
    	log.debug("getContactInstitutionTypeList");
		String sql = "SELECT * FROM " + ContactInstitutionType.DATABASE_TABLE_NAME + " WITH (NOLOCK) ORDER BY ContactInstitutionType ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new ContactInstitutionType());
	}	
	
	public void insertContactToEnvelopeType(Contact contact)
    {
		String sql = "EXEC " + "TBS_Common" + "..p_j_GetContactEnvelopeRelationship @Contact_fk=?, @Envelope_FK=?, @Action=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(contact.getField(Contact.Field.CONTACT_ID).getLongValue());;
		//params.addValue(EnvelopeId);
		params.addValue("Insert");
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
    }
	 
	public void updateContact(Contact contact)
	{
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		try
		{
			batchManager.beginTransaction();

			if(contact.getField(Contact.Field.MAILING_ADDRESS_ID).getLongValue() == 0)
			{
				long mailingAddressId = addMailingAddress(batchManager, contact);
				contact.getField(Contact.Field.MAILING_ADDRESS_ID).setValue(mailingAddressId);
			}
			else
			{
				updateMailingAddress(batchManager, contact);
			}
			
			String sql = "UPDATE " + Contact.DATABASE_TABLE_NAME + " SET FullName = ?, CompanyName = ?, DepartmentName = ?, "
		    + " JobTitle = ?, BusinessPhone = ?, BusinessFax = ?, MobilePhone = ?, "
	        + "EmailAddress = ?, MailingAddress_FK = ?, Comments = ?, IsDeleted = ?, "
	        + "LastModifiedDateTime = getdate(), LastModifiedByUserName = ?, VersionNumber = ISNULL(VersionNumber,0) + 1, "
	        + "ContactInstitutionType_FK = ?, DepositInstitution_FK = ?, SourceInstitution_FK = ? "
			+ "WHERE Contact_PK = ? ";
			//log.debug(Contact.Field.FULL_NAME + " | " + Contact.Field.DEPOSIT_INSTITUTION + " | "+ Contact.Field.SOURCE_INSTITUTION + " | "+ Contact.Field.CONTACTINSTITUTION_TYPE + " | ");
			SQLParameters params = new SQLParameters();
			params.addValue(contact.getField(Contact.Field.FULL_NAME).getStringValue());
			params.addValue(contact.getField(Contact.Field.COMPANY_NAME).getStringValue());
			params.addValue(contact.getField(Contact.Field.DEPARTMENT_NAME).getStringValue());
			params.addValue(contact.getField(Contact.Field.JOB_TITLE).getStringValue());
			params.addValue(contact.getField(Contact.Field.BUSINESS_PHONE).getStringValue());
			params.addValue(contact.getField(Contact.Field.BUSINESS_FAX).getStringValue());
			params.addValue(contact.getField(Contact.Field.MOBILE_PHONE).getStringValue());
			params.addValue(contact.getField(Contact.Field.EMAIL_ADDRESS).getStringValue());
			params.addValue(contact.getField(Contact.Field.MAILING_ADDRESS_ID).getLongValue());
			params.addValue(contact.getField(Contact.Field.COMMENTS).getStringValue());
			params.addValue(contact.getField(Contact.Field.IS_DELETED).getBooleanValue());
			params.addValue(contact.getField(Contact.Field.LAST_MODIFIED_BY_USER).getStringValue());
			params.addValue(contact.getField(Contact.Field.CONTACTINSTITUTION_TYPE).getLongValue());
			params.addValue(contact.getField(Contact.Field.DEPOSIT_INSTITUTION).getLongValue());
			params.addValue(contact.getField(Contact.Field.SOURCE_INSTITUTION).getLongValue());
			params.addValue(contact.getField(Contact.Field.CONTACT_ID).getLongValue());
			batchManager.executeUpdate(sql, params);

			batchManager.commitTransaction();
		}
		finally
		{
			batchManager.close();
		}
	}

	public void updateContactToEnvelopeType(Contact contact, List<Long> EnvelopeId)
    {
		String sql = "EXEC " + "TBS_Common" + "..p_j_GetContactEnvelopeRelationship @Contact_fk=?, @Action=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(contact.getField(Contact.Field.CONTACT_ID).getLongValue());
	    params.addValue("ClearOld");
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
		
		log.debug("contactIdUpdateEnvelope = "+contact);
		int count = 0;
		for(Long item : EnvelopeId )
		{
			log.debug("EnvelopeIdUpdateEnvelope3" + count + " = ");
			log.debug("EnvelopeIdUpdateEnvelope" + count + " = "+ item);
			count++;
		 
			if(item != 0)
			{
				sql = "EXEC " + "TBS_Common" + "..p_j_GetContactEnvelopeRelationship @Contact_fk=?, @Envelope_FK=?, @Action=? ";
				params = new SQLParameters();
				params.addValue(contact.getField(Contact.Field.CONTACT_ID).getLongValue());
				params.addValue(item);
				params.addValue("Insert");
				this.databaseHelper.executeStoredProcedureUpdate(sql, params);
			}
		}
	}

	public ContactsTable getContacts(AppUserFilter appUserFilter)
    {
		log.debug("testjdbc");
		/*
		String fullNameFilter = appUserFilter.getField(AppUserFilter.Field.USER_NAME).getStringValue();
		Long SI_PK = appUserFilter.getField(AppUserFilter.Field.SOURCE_INST_ID).getLongValue();
		Long DI_PK = appUserFilter.getField(AppUserFilter.Field.DEPOSIT_INST_ID).getLongValue();
		
		log.debug("getContactsApp" + fullNameFilter + "SI:" + SI_PK + "DI:" + DI_PK);
		//String fullNameFilter = appUserFilter.getField(AppUserFilter.Field.FULL_NAME).getStringValue();
		//String fullNameFilter = appUserFilter.getField(AppUserFilter.Field.FULL_NAME).getStringValue();
		
    	ContactsTable t = new ContactsTable();
		String sql = "EXEC " + Databases.COMMON + "..p_GetContacts @Action=?, @Name=?, @SourceInstitution_PK=?, @DepositInstitution_PK=?  ";
		SQLParameters params = new SQLParameters();
		params.addValue("Select");
		params.addValue(fullNameFilter);
		params.addValue(SI_PK);
		params.addValue(DI_PK);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;*/
		ContactsTable t = new ContactsTable();
		String fullNameFilter = appUserFilter.getField(AppUserFilter.Field.USER_NAME).getStringValue();
		long sourceInstitutionIdFilter = appUserFilter.getField(AppUserFilter.Field.SOURCE_INST_ID).getLongValue();
		long depositInstitutionIdFilter = appUserFilter.getField(AppUserFilter.Field.DEPOSIT_INST_ID).getLongValue();
		
		String sqlWhere = ""; 
		log.debug("fullNameFilter" +fullNameFilter);
		if(fullNameFilter.length() > 0)
		{
			if(fullNameFilter.equalsIgnoreCase("0 - 9"))
			{
				sqlWhere = "WHERE LEFT(a.FullName,1) BETWEEN '0' AND '9'";
			}
			else
			{
				sqlWhere = "WHERE LEFT(a.FullName,1) = '" + fullNameFilter + "'";
			}
		}
		if(sourceInstitutionIdFilter > 0)
		{
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "EXISTS (SELECT 1 FROM " + "SourceInstitutions" + " "
			+ "WHERE  SourceInstitution_FK = " + sourceInstitutionIdFilter + ") ";
		}
		if(depositInstitutionIdFilter > 0)
		{
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "EXISTS (SELECT 1 FROM " + "DepositInstitutions" + " "
			+ "WHERE DepositInstitution_FK = " + depositInstitutionIdFilter + ") ";
		}
		if(appUserFilter.getAllowedSourceInstitutionIdList() != null)
		{
			String sourceInstitutionIds = Arrays.toString(appUserFilter.getAllowedSourceInstitutionIdList().toArray());
			sourceInstitutionIds = sourceInstitutionIds.replace("[", "");
			sourceInstitutionIds = sourceInstitutionIds.replace("]", "");
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "EXISTS (SELECT 1 FROM " + AppUserSourceInstitutionRelationship.DATABASE_TABLE_NAME + " "
			+ "WHERE User_FK = b.User_PK AND SourceInstitution_FK IN ( " + sourceInstitutionIds + ")) ";			
		}
		if(appUserFilter.getAllowedDepositInstitutionIdList() != null)
		{
			String depositInstitutionIds = Arrays.toString(appUserFilter.getAllowedDepositInstitutionIdList().toArray());
			depositInstitutionIds = depositInstitutionIds.replace("[", "");
			depositInstitutionIds = depositInstitutionIds.replace("]", "");
			sqlWhere += sqlWhere.length() == 0 ? "WHERE " : "AND ";
			sqlWhere += "EXISTS (SELECT 1 FROM " + AppUserDepositInstitutionRelationship.DATABASE_TABLE_NAME + " "
			+ "WHERE User_FK = b.User_PK AND DepositInstitution_FK IN ( " + depositInstitutionIds + ")) ";			
		}		
		
		String sql = "SELECT * "
		+ " FROM " + Databases.COMMON + "..Contacts" + " a WITH (NOLOCK) "
		+ "LEFT JOIN " + Databases.COMMON + "..AppUser" + " b WITH (NOLOCK) ON a.Contact_PK = b.Contact_FK "
		+ sqlWhere + " "
		+ "ORDER BY a.FullName ";
		SQLParameters params = new SQLParameters();
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
		
    }
	
	public ContactsTable getContacts()
    {
		//String fullNameFilter = appUserFilter.getField(AppUserFilter.Field.FULL_NAME).getStringValue();
		//String fullNameFilter = appUserFilter.getField(AppUserFilter.Field.FULL_NAME).getStringValue();
		//String fullNameFilter = appUserFilter.getField(AppUserFilter.Field.FULL_NAME).getStringValue();
		
    	ContactsTable t = new ContactsTable();
		String sql = "EXEC " + Databases.COMMON + "..p_GetContacts  @Action=?";
		SQLParameters params = new SQLParameters();
		params.addValue("Select");
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
	
	public MandatoryContactInfoView getMandatoryContactInfo( long contactId )
	{
		MandatoryContactInfoView t = new MandatoryContactInfoView();
		String sql = "SELECT Contact_PK, FullName, BusinessPhone, EmailAddress"
		+ " FROM " + Databases.COMMON + "..Contacts"
		+ " WHERE Contact_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(contactId);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;		
	}
	
	public void updateMandatoryContactInfo( MandatoryContactInfoView contact, long userId )
	{
		String sql = "UPDATE " + Databases.COMMON + "..Contacts"
		+ " SET FullName = ?, BusinessPhone = ?, EmailAddress = ?, "
        + " VersionNumber = ISNULL(VersionNumber,0) + 1 "
		+ " WHERE Contact_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue( contact.getRow().getColumn( contact.FULL_NAME ).getValueAsString() );
		params.addValue( contact.getRow().getColumn( contact.BUSINESS_PHONE ).getValueAsString() );
		params.addValue( contact.getRow().getColumn( contact.EMAIL_ADDRESS ).getValueAsString() );
		params.addValue( contact.getRow().getColumn( contact.CONTACT_ID).getValueAsLong() );
		databaseHelper.executeUpdate(sql, params);
		
		sql = "UPDATE " + Databases.COMMON + "..AppUser"
		+ " SET IsMandatoryContactInfoSet = 1, "
		+ " VersionNumber = ISNULL(VersionNumber,0) + 1 "
		+ " WHERE User_PK = ? ";
		params = new SQLParameters();
		params.addValue(userId);
		databaseHelper.executeUpdate(sql, params);
	}
	
	//============================================================================
	//
	// Envelopes
	//
	//============================================================================
    private List<Envelopes> getEnvelopeList(long envelopeTypeId)
	{
		String sql = "SELECT * FROM " + Envelopes.DATABASE_TABLE_NAME + " WITH (NOLOCK) WHERE EnvelopeType_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(envelopeTypeId);
		return this.databaseHelper.queryForList(sql, params, new Envelopes());
	}
    
    public List<Envelopes> getEnvelopesByDepositInstitutionList()
	{
		return getEnvelopeList(EnvelopeTypes.Values.DEPOSIT_INSTITUTION.getId());
	}
    
    public List<ViewContactEnvelopeRelationship> getEnvelopesByDepositInstitutionList(Contact contact)
    {            
		  log.debug("ContactGetEnvelopebyDI = "+ contact);
		        
		  String sql = "EXEC " + "TBS_Common" + "..p_j_GetContactEnvelopeRelationship @Contact_fk=?, @Envelope_FK=?, @Action=? ";
		  SQLParameters params = new SQLParameters();
		  params.addValue(contact.getField(Contact.Field.CONTACT_ID).getLongValue());
		  params.addValue("");
		  params.addValue("VIEW");
		  log.debug("ContactGetEnvelopebyDI2 = "+this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewContactEnvelopeRelationship()));
		  return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewContactEnvelopeRelationship());
    }
    public List<ViewContactEnvelopeRelationship> getEnvelopesBySourceInstitutionList(Contact contact)
    {            
		  log.debug("ContactGetEnvelopebySI = "+ contact);
		        
		  String sql = "EXEC " + "TBS_Common" + "..p_j_GetContactEnvelopeRelationship @Contact_fk=?, @Envelope_FK=?, @Action=? ";
		  SQLParameters params = new SQLParameters();
		  params.addValue(contact.getField(Contact.Field.CONTACT_ID).getLongValue());
		  params.addValue("");
		  params.addValue("VIEWSI");
		  log.debug("ContactGetEnvelopebySI2 = "+this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewContactEnvelopeRelationship()));
		  return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewContactEnvelopeRelationship());
    }
    public List<Envelopes> getEnvelopesBySourceInstitutionList()
	{
		return getEnvelopeList(EnvelopeTypes.Values.SOURCE_INSTITUTION.getId());
	}

	//============================================================================
	//
    // MailingAddress
    //
	//============================================================================       
    public EmailSenderLogView getEmailSenderLogView()
   {
		//String fullNameFilter = appUserFilter.getField(AppUserFilter.Field.FULL_NAME).getStringValue();
		//String fullNameFilter = appUserFilter.getField(AppUserFilter.Field.FULL_NAME).getStringValue();
		//String fullNameFilter = appUserFilter.getField(AppUserFilter.Field.FULL_NAME).getStringValue();
		
    	EmailSenderLogView t = new EmailSenderLogView();
		String sql = "EXEC " + Databases.COMMON + "..p_Get_EMS_Server_Log";
		SQLParameters params = new SQLParameters();
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
    
    
	//============================================================================
	//
    // MailingAddress
    //
	//============================================================================    
	private long addMailingAddress(DataBatchManager batchManager, Contact contact)
	{
		String sql = contact.getMailingAddress().getSQLInsertStatementNew();
		SQLParameters params = contact.getMailingAddress().getSQLInsertParameters();
		long mailingAddressId = batchManager.executeInsert(sql, params);
		return mailingAddressId;
	}
	
	private int deleteMailingAddress(DataBatchManager batchManager, long mailingAddressId)
	{
		String sql = "DELETE FROM MailingAddresses WHERE MailingAddress_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(mailingAddressId);
		int rowsAffected = batchManager.executeUpdate(sql, params);
		return rowsAffected;
	}
	
	private void updateMailingAddress(DataBatchManager batchManager, Contact contact)
	{
		MailingAddress m = contact.getMailingAddress();
		String sql = "UPDATE " + MailingAddress.DATABASE_TABLE_NAME + " SET "
        + "City = ?, Country_FK = ?, Line1 = ?, Line2 = ?, Line3 = ?, Line4 = ?, State_FK = ?, ZipCode = ? "
		+ "WHERE MailingAddress_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(m.getCityName());
		params.addValue(m.getCountryId());
		params.addValue(m.getLine1());
		params.addValue(m.getLine2());
		params.addValue(m.getLine3());
		params.addValue(m.getLine4());
		params.addValue(m.getStateId());
		params.addValue(m.getZipCode());
		params.addValue(m.getId());
		batchManager.executeUpdate(sql, params);
	}

}
