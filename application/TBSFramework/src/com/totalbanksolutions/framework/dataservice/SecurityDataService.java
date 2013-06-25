package com.totalbanksolutions.framework.dataservice;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.data.AppRoleDataSet;
import com.totalbanksolutions.framework.bean.database.data.AppUserTypeDataSet;
import com.totalbanksolutions.framework.bean.database.table.AppNavigation;
import com.totalbanksolutions.framework.bean.database.table.AppNavigationGroup;
import com.totalbanksolutions.framework.bean.database.table.AppRole;
import com.totalbanksolutions.framework.bean.database.table.AppUser;
import com.totalbanksolutions.framework.bean.database.table.AppUserChallengeQuestionRelationship;
import com.totalbanksolutions.framework.bean.database.table.AppUserHistory;
import com.totalbanksolutions.framework.bean.database.table.AppUserType;
import com.totalbanksolutions.framework.bean.database.table.ChallengeQuestion;
import com.totalbanksolutions.framework.bean.database.table.Contact;
import com.totalbanksolutions.framework.bean.database.table.ContactInstitutionType;
import com.totalbanksolutions.framework.bean.database.table.Envelopes;
import com.totalbanksolutions.framework.bean.database.view.ViewAppUserSession;
import com.totalbanksolutions.framework.bean.database.view.ViewContactEnvelopeRelationship;
import com.totalbanksolutions.framework.bean.util.BeanSet;
import com.totalbanksolutions.framework.bean.web.AppUserFilter;
import com.totalbanksolutions.framework.cache.CacheDataRetrievalMethod;
import com.totalbanksolutions.framework.cache.CacheDataRetrievalParams;
import com.totalbanksolutions.framework.cache.CacheManager;
import com.totalbanksolutions.framework.dao.SecurityDAO;
import com.totalbanksolutions.framework.dao.jdbc.SecurityJDBC;
import com.totalbanksolutions.framework.dataservice.util.DataCacheType;
import com.totalbanksolutions.framework.dataservice.util.DataRetrievalType;
import com.totalbanksolutions.framework.model.database.view.AppNavigationView;
import com.totalbanksolutions.framework.model.database.view.EmailSenderLogView;
import com.totalbanksolutions.framework.model.database.view.MandatoryContactInfoView;
import com.totalbanksolutions.framework.model.database.view.SecondaryMenuView;
import com.totalbanksolutions.framework.util.AppUtility;
import com.totalbanksolutions.framework.model.database.table.ContactsTable;

/**
 * @author vcatrini
 * Modified: 28 Sep 2011 VC #1010: Modify the way DMS distinguishes between SECURE and NON-SECURE environments.
 */  
public class SecurityDataService 
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());
    private SecurityDAO securityDAO = null;
    private CacheManager cacheManager = null;
    private DataService ds = null;

    public SecurityDataService (DataSource dataSource, CacheManager cacheManager, DataService ds)
    {
    	this.securityDAO = new SecurityJDBC(dataSource);
    	this.cacheManager = cacheManager;
    	this.ds = ds;
    }
    
	public void clearUserCache(long userId)
	{
    	CacheDataRetrievalParams params = new CacheDataRetrievalParams();
    	params.addValue("userId", new Long(userId).toString());
		this.cacheManager.setExpired(DataCacheType.APP_USER_NAVIGATION_LIST, params);
    	this.cacheManager.setExpired(DataCacheType.APP_USER_ROLE_LIST, params);
	}
	
	public void close()
	{
		this.securityDAO.close();
		this.securityDAO = null;
		this.cacheManager = null;
		this.ds = null;
	}

	//============================================================================
	//
	// AppConfiguration
	//
	//============================================================================
    public int getAppConfigLoginAttemptsBeforeLockout()
    {
    	return this.ds.util.getAppConfiguration().getNumberOfLoginAttemptsBeforeLockout();
    }

    public String getAppConfigPasswordDefaultValue()
    {
    	return this.ds.util.getAppConfiguration().getDefaultUserPassword();
    }
    
    public int getAppConfigPasswordMinimumLength()
    {
    	return this.ds.util.getAppConfiguration().getPasswordMinimumLength();
    }

    public int getAppConfigPasswordNumberOfCaps()
    {
    	return this.ds.util.getAppConfiguration().getPasswordNumberOfCaps();
    }

    public int getAppConfigPasswordNumberOfHistoricRestrict()
    {
    	return this.ds.util.getAppConfiguration().getPasswordNumberOfHistoricRestrict();
    }

    public int getAppConfigPasswordNumberOfNumerics()
    {
    	return this.ds.util.getAppConfiguration().getPasswordNumberOfNumerics();
    }

    public int getAppConfigPasswordValidPeriodInDays()
    {
    	return this.ds.util.getAppConfiguration().getPasswordValidPeriodInDays();
    }
    
    public int getAppConfigUserNameMinimumLength()
    {
    	return this.ds.util.getAppConfiguration().getMinimumUserNameLength();
    }

    public boolean isAppConfigMandatoryContactInfoEnabled()
    {
    	return this.ds.util.getAppConfiguration().isMandatoryContactInfoEnabled();
    }

    //============================================================================
	//
    // AppUser_History
    //
	//============================================================================
    public List<AppUserHistory> getAppUserHistory( long userID, int historicalRecordCount )
    {
    	return securityDAO.getAppUserHistory(userID, historicalRecordCount);
    }
    
    //============================================================================
	//
    // AppNavigation
    //
	//============================================================================	
	public List<AppNavigation> getAppNavigationList(long groupId)
	{
		return securityDAO.getAppNavigationList(groupId);
	}

	public AppNavigationView getAppUserNavigationListNew(final long userId, final String secureURLPrefix)
	{
		final String localHostPrefix = AppUtility.isLocalHostURLOverride() ? "http://localhost:" + AppUtility.getAppHttpPort() : "";
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() {
    		public Object retrieveData() {
    			return securityDAO.getAppUserNavigationList(userId, secureURLPrefix, localHostPrefix);
    		}
    	};
    	CacheDataRetrievalParams params = new CacheDataRetrievalParams();
    	params.addValue("userId", new Long(userId).toString());
    	return (AppNavigationView)cacheManager.get(DataCacheType.APP_USER_NAVIGATION_LIST, retrieveMethod, params);
	}
	
	public void updateAppNavigationList(List<AppNavigation> linkList, long groupId)
	{
		securityDAO.updateAppNavigationList(linkList, groupId);
	}
	
	//============================================================================
	//
	// AppNavigation_AppRole_Relationship
	//
	//============================================================================	
	public List<AppNavigationGroup> getAppNavigationRoleList(int roleId)
	{
		return securityDAO.getAppNavigationRoleList(roleId);
	}
	
	public void updateAppNavigationRoles(List<AppNavigationGroup> groupList, long roleId)
	{
		securityDAO.updateAppNavigationRoles(groupList, roleId);
	}
	
	//============================================================================
	//
	// AppNavigationGroup
	//
	//============================================================================
	public List<AppNavigationGroup> getAppNavigationGroupList()
	{
       	//CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		//public Object retrieveData() {
    			return securityDAO.getAppNavigationGroupList(); 
    		//}
    	//};
    	//return (List<AppNavigationGroup>)cacheManager.get(DataCacheType.APP_NAVIGATION_GROUP_LIST, retrieveMethod);
	}

	public void updateAppNavigationGroupList(List<AppNavigationGroup> groupList)
	{
		securityDAO.updateAppNavigationGroupList(groupList);
	}

	/*
	//============================================================================
	//
	// AppNavigationApp
	//
	//============================================================================
	public List<App> getAppNavigationAppList()
	{
		return securityDAO.getAppNavigationAppList(); 
	}

	public void updateAppNavigationAppList(List<App> appList)
	{
		securityDAO.updateAppNavigationAppList(appList);
	}
	*/
		
	//============================================================================
	//
	// AppRole
	//
	//============================================================================    
	@SuppressWarnings("unchecked")
	public List<AppRole> getAppRoleList()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return securityDAO.getAppRoleList();
    		}
    	};
    	return (List<AppRole>)cacheManager.get(DataCacheType.APP_ROLE_LIST, retrieveMethod);
    }

	@SuppressWarnings("unchecked")
	public BeanSet<AppRole> getAppRoleDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new AppRoleDataSet(getAppRoleList());
    		}
    	};
    	return (BeanSet<AppRole>)cacheManager.get(DataCacheType.APP_ROLE_SET, retrieveMethod);
    }

	//============================================================================
	//
	// AppSession
	//
	//============================================================================
	public void activateAppSession(long userId, String sessionKey)
	{
		securityDAO.activateAppSession(userId, sessionKey);
	}
	
	public String addAppSession(long userId)
	{
		return securityDAO.addAppSession(userId);
	}
	
	public void deactivateAppSession(long userId, String sessionKey)
	{
		securityDAO.deactivateAppSession(userId, sessionKey);
	}

	public void expireAppSessions(int expireInMinutes)
	{
		securityDAO.expireAppSessions(expireInMinutes);
	}

	public ViewAppUserSession validateAppUserSession(ViewAppUserSession user)
	{
		return securityDAO.validateAppUserSession(user);
	}

	public ViewAppUserSession validateAppUserSessionPreLogin(ViewAppUserSession user)
	{
		boolean isLoginInProgress = true;
		return securityDAO.validateAppUserSession(user, isLoginInProgress);
	}
	
	//============================================================================
	//
	// AppUser
	//
	//============================================================================
	public long addAppUser(AppUser user, String defaultUserPassword)
	{
		return securityDAO.addAppUser(user, defaultUserPassword);
	}
	
	public int deleteAppUser(long userId)
	{
		return securityDAO.deleteAppUser(userId);
	}

	public AppUser getAppUserByUserName(String userName)
	{
		return securityDAO.getAppUserByUserName(userName);
	}
	
	public AppUser getAppUserDetails(long userId)
	{
		return securityDAO.getAppUserDetails(userId);
	}
	
	public List<AppUser> getAppUserList(AppUserFilter appUserFilter)
	{
		return securityDAO.getAppUserList(appUserFilter);
	}

	public void resetAppUserFailedLoginAttempts( AppUser user )
	{
		securityDAO.resetAppUserFailedLoginAttempts( user );
	}

	public void resetAppUserPassword(long userId, String defaultUserPassword)
	{
		securityDAO.resetAppUserPassword(userId, defaultUserPassword);
	}
	
	public void unlockAppUser( long userId )
	{
		securityDAO.unlockAppUser( userId );
	}
	
	public void updateAppUser(AppUser user)
	{
		securityDAO.updateAppUser(user);
	}
	
	public void updateAppUserPassword(long userId, String textPassword, int minPasswordLength)
	{
		securityDAO.updateAppUserPassword(userId, textPassword, minPasswordLength);
	}
	
	public ViewAppUserSession validateLogin(String userName, String password)
	{
		return securityDAO.validateLogin(userName, password);
	}
	
	//============================================================================
	//
	// AppUser_AppRole_Relationship
	//
	//============================================================================
	@SuppressWarnings("unchecked")
	public List<Long> getAppUserRoleIdList(final long userId, DataRetrievalType dataRetrievalType)
	{
//		if(dataRetrievalType == DataRetrievalType.ALWAYS_FROM_DATABASE)
//		{
			return securityDAO.getAppUserRoleIdList(userId);
//		}
//		else
//		{
//	       	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
//	    		public Object retrieveData() {
//	    			return securityDAO.getAppUserRoleIdList(userId);
//	    		}
//	    	};
//	    	CacheDataRetrievalParams params = new CacheDataRetrievalParams();
//	    	params.addValue("userId", new Long(userId).toString());
//	    	return (List<Long>)cacheManager.get(DataCacheType.APP_USER_ROLE_LIST, retrieveMethod, params);
//		}
	}

	public boolean isAppUserInRole(long userId, long roleId)
	{
		boolean isInRole = false;
		List<Long> userRoleIdList = getAppUserRoleIdList(userId, DataRetrievalType.IF_NOT_IN_CACHE_THEN_DATABASE);
		for(Long userRoleId : userRoleIdList)
		{
			if(userRoleId == roleId)
			{
				isInRole = true;
				break;
			}
		}
		return isInRole;
	}

	//============================================================================
	//
	// AppUser_DepositInstitution_Relationship
	//
	//============================================================================
	public List<Long> getAppUserDepositInstitutionIdList(long userId)
	{
		return securityDAO.getAppUserDepositInstitutionIdList(userId);
	}

	//============================================================================
	//
	// AppUser_SourceInstitution_Relationship
	//
	//============================================================================
	public List<Long> getAppUserProgramIdList(long userId)
	{
		return securityDAO.getAppUserProgramIdList(userId);
	}
	
	public List<Long> getAppUserSourceInstitutionIdList(long userId)
	{
		return securityDAO.getAppUserSourceInstitutionIdList(userId);
	}

	// ---------------------------------------------------------
	//
	// AppUserType
	//
    // ---------------------------------------------------------
    @SuppressWarnings("unchecked")
	public BeanSet<AppUserType> getAppUserTypeDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new AppUserTypeDataSet(getAppUserTypeList());
    		}
    	};
    	return (BeanSet<AppUserType>)cacheManager.get(DataCacheType.APP_USER_TYPE_SET, retrieveMethod);
    }

    @SuppressWarnings("unchecked")
	public List<AppUserType> getAppUserTypeList()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return securityDAO.getAppUserTypeList();
    		}
    	};
    	return (List<AppUserType>)cacheManager.get(DataCacheType.APP_USER_TYPE_LIST, retrieveMethod);
    }

    //============================================================================
	//
    // ChallengeQuestion
    //
	//============================================================================
	@SuppressWarnings("unchecked")
	public List<ChallengeQuestion> getChallengeQuestionList()
	{
       	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() {
    		public Object retrieveData() {
    			return securityDAO.getChallengeQuestionList();
    		}
    	};
    	return (List<ChallengeQuestion>)cacheManager.get(DataCacheType.CHALLENGE_QUESTION_LIST, retrieveMethod);
	}

    public AppUserChallengeQuestionRelationship[] getChallengeResponseArray(long userId)
    {
    	return securityDAO.getChallengeResponseArray(userId);
    }
	
	public List<AppUserChallengeQuestionRelationship> getChallengeResponseList(long userId)
	{
		return securityDAO.getChallengeResponseList(userId);
	}
	
    public ChallengeQuestion getRandomChallengeQuestion(long userId)
    {
    	return securityDAO.getRandomChallengeQuestion(userId);
    }
	
	public void resetChallengeQuestions(long userId)
	{
		securityDAO.resetChallengeQuestions(userId);	
	}

	public void updateUserChallengeQuestions(long userId, AppUserChallengeQuestionRelationship[] questions)
	{
		securityDAO.updateUserChallengeQuestions(userId, questions);
	}

	public boolean validateChallengeReponse(long userId, long questionId, String questionResponse)
	{
		return securityDAO.validateChallengeReponse(userId, questionId, questionResponse);
	}
	
	//============================================================================
	//
	// Contact
	//
	//============================================================================
	public long addContact(Contact contact)
	{
		return securityDAO.addContact(contact);
	}
	public double addContactNew(Contact contact)
	{
		return securityDAO.addContactNew(contact);
	}
	public int deleteContact(long contactId)
	{
		return securityDAO.deleteContact(contactId);
	}
	
	public Contact getContactDetails(long contactId)
	{
		return securityDAO.getContactDetails(contactId);
	}
	
    @SuppressWarnings("unchecked")
	public List<ContactInstitutionType> getContactInstitutionTypeList()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return securityDAO.getContactInstitutionTypeList();
    		}
    	};
    	return (List<ContactInstitutionType>)cacheManager.get(DataCacheType.CONTACTINSTITUTION_TYPE_LIST, retrieveMethod);
    }
   
	public List<Contact> getContactList(AppUserFilter appUserFilter)
	{
		return securityDAO.getContactList(appUserFilter);
	}

	public void updateContact(Contact contact)
	{
		securityDAO.updateContact(contact);
	}

	public void updateContactToEnvelopeType(Contact contact, List<Long> EnvelopeId)
	{
		securityDAO.updateContactToEnvelopeType(contact, EnvelopeId);
	}

	public ContactsTable getContacts()
	{
		return securityDAO.getContacts();
	}
	
	public ContactsTable getContacts(AppUserFilter appUserFilter)
	{	 
		return securityDAO.getContacts(appUserFilter);
	}

	public MandatoryContactInfoView getMandatoryContactInfo( long contactId )
	{
		return securityDAO.getMandatoryContactInfo(contactId);
	}
	
	public void updateMandatoryContactInfo( MandatoryContactInfoView contactData, long userId )
	{
		securityDAO.updateMandatoryContactInfo( contactData, userId );
	}
	

		//============================================================================
	//
	// Envelopes
	//
	//============================================================================
	@SuppressWarnings("unchecked")
	public List<Envelopes> getEnvelopesByDepositInstitutionList()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return securityDAO.getEnvelopesByDepositInstitutionList(); 
    		}
    	};
    	return (List<Envelopes>)cacheManager.get(DataCacheType.ENVELOPE_BY_DEPOSIT_INSTITUTION_LIST, retrieveMethod);
    }

	public List<ViewContactEnvelopeRelationship> getEnvelopesByDepositInstitutionList(final Contact contact)
    {
    	return securityDAO.getEnvelopesByDepositInstitutionList(contact); 
    }
	public List<ViewContactEnvelopeRelationship> getEnvelopesBySourceInstitutionList(final Contact contact)
    {
    	return securityDAO.getEnvelopesBySourceInstitutionList(contact); 
    }
	@SuppressWarnings("unchecked") 
	public List<Envelopes> getEnvelopesBySourceInstitutionList()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return securityDAO.getEnvelopesBySourceInstitutionList(); 
    		}
    	};
    	return (List<Envelopes>)cacheManager.get(DataCacheType.ENVELOPE_BY_SOURCE_INSTITUTION_LIST, retrieveMethod);
    }
	//============================================================================
		//
		// Email Sender
		
		//
		//============================================================================
	
	 public EmailSenderLogView getEmailSenderLogView()
	 {
		 
		 return securityDAO.getEmailSenderLogView();
	 }
	
	
	
	
	
	//============================================================================
	//
	// Menu Links
	// VC Mar-09-2012: This is just temporary until we start pulling the 
	//				   new lower level menu data from the new data model
	//
	//============================================================================
	public void setMenuSecondary(String currentView, List<SecondaryMenuView> level1List, List<SecondaryMenuView> level2List)
	{
		if(currentView.equalsIgnoreCase("automationQueue"))
		{
			level1List.add(new SecondaryMenuView("Service Queue", "automationQueue.htm", true));
		}
		else if(currentView.equalsIgnoreCase("brokerReports"))
		{
			level1List.add(new SecondaryMenuView("Broker Reports", "brokerReports.htm", true));
		}
		else if(currentView.equalsIgnoreCase("challengeQuestions"))
		{
			level1List.add(new SecondaryMenuView("Challenge Questions", "challengeQuestions.htm", true));
		}
		else if(currentView.equalsIgnoreCase("changePassword"))
		{
			level1List.add(new SecondaryMenuView("Change Password", "changePassword.htm", true));
		}
		else if(currentView.equalsIgnoreCase("checklist"))
		{
			level1List.add(new SecondaryMenuView("Checklist", "checklist.htm", true));
		}
		else if(currentView.equalsIgnoreCase("checklistDashboard"))
		{
			level1List.add(new SecondaryMenuView("Dashboard", "checklistDashboard.htm", true));
		}
		else if(currentView.equalsIgnoreCase("checklistMaintenanceDate"))
		{
			level1List.add(new SecondaryMenuView("Date", "checklistMaintenanceDate.htm", true));
			level1List.add(new SecondaryMenuView("Steps", "checklistMaintenanceSteps.htm"));
			level1List.add(new SecondaryMenuView("Files", "checklistMaintenanceFiles.htm"));
		}
		else if(currentView.equalsIgnoreCase("checklistMaintenanceSteps"))
		{
			level1List.add(new SecondaryMenuView("Date", "checklistMaintenanceDate.htm"));
			level1List.add(new SecondaryMenuView("Steps", "checklistMaintenanceSteps.htm", true));
			level1List.add(new SecondaryMenuView("Files", "checklistMaintenanceFiles.htm"));

			level2List.add(new SecondaryMenuView("Master steps", "checklistMaintenanceSteps.htm", true));
			level2List.add(new SecondaryMenuView("SI-specific steps", "checklistMaintenanceSteps.htm"));
		}
		else if(currentView.equalsIgnoreCase("checklistMaintenanceFiles"))
		{
			level1List.add(new SecondaryMenuView("Date", "checklistMaintenanceDate.htm"));
			level1List.add(new SecondaryMenuView("Steps", "checklistMaintenanceSteps.htm"));
			level1List.add(new SecondaryMenuView("Files", "checklistMaintenanceFiles.htm", true));

			level2List.add(new SecondaryMenuView("Standard Filenames", "checklistMaintenanceFiles.htm", true));
			level2List.add(new SecondaryMenuView("SI-specific Filenames", "checklistMaintenanceFiles.htm"));
			level2List.add(new SecondaryMenuView("Environment Paths", "checklistMaintenanceFiles.htm"));
		}
		else if(currentView.equalsIgnoreCase("contactInfo"))
		{
			level1List.add(new SecondaryMenuView("Contact Info", "contactInfo.htm", true));
		}		
		else if(currentView.equalsIgnoreCase("contacts"))
		{
			level1List.add(new SecondaryMenuView("Contacts", "contacts.htm", true));
		}		
		else if(currentView.equalsIgnoreCase("contactsForm"))
		{
			level1List.add(new SecondaryMenuView("Contacts", "contacts.htm", true));
		}		
		else if(currentView.equalsIgnoreCase("dailyChecklist"))
		{
			level1List.add(new SecondaryMenuView("Daily Checklist", "dailyChecklist.htm", true));
		}
		else if(currentView.equalsIgnoreCase("developerExamplesMain"))
		{
			level1List.add(new SecondaryMenuView("Examples", "developerExamplesMain.htm", true));
			level1List.add(new SecondaryMenuView("Forms", "developerExamplesForms1.htm"));
			level1List.add(new SecondaryMenuView("Tables", "developerExamplesTables1.htm"));
			
			level2List.add(new SecondaryMenuView("Overview", "developerExamplesMain.htm", true));
			level2List.add(new SecondaryMenuView("Error Handling", "developerExamplesErrors.htm"));
			level2List.add(new SecondaryMenuView("Status Bar", "developerExamplesStatusBar.htm"));
			level2List.add(new SecondaryMenuView("PopUp Dialog", "developerExamplesPopUp.htm"));
			level2List.add(new SecondaryMenuView("Ajax", "developerExamplesAjax.htm"));
			level2List.add(new SecondaryMenuView("Model", "developerExamplesModel.htm"));
			level2List.add(new SecondaryMenuView("Cache", "developerExamplesCache.htm"));
		}
		else if(currentView.equalsIgnoreCase("developerExamplesErrors"))
		{
			level1List.add(new SecondaryMenuView("Examples", "developerExamplesMain.htm", true));
			level1List.add(new SecondaryMenuView("Forms", "developerExamplesForms1.htm"));
			level1List.add(new SecondaryMenuView("Tables", "developerExamplesTables1.htm"));
			
			level2List.add(new SecondaryMenuView("Overview", "developerExamplesMain.htm"));
			level2List.add(new SecondaryMenuView("Error Handling", "developerExamplesErrors.htm", true));
			level2List.add(new SecondaryMenuView("Status Bar", "developerExamplesStatusBar.htm"));
			level2List.add(new SecondaryMenuView("PopUp Dialog", "developerExamplesPopUp.htm"));
			level2List.add(new SecondaryMenuView("Ajax", "developerExamplesAjax.htm"));
			level2List.add(new SecondaryMenuView("Model", "developerExamplesModel.htm"));
			level2List.add(new SecondaryMenuView("Cache", "developerExamplesCache.htm"));
		}
		else if(currentView.equalsIgnoreCase("developerExamplesStatusBar"))
		{
			level1List.add(new SecondaryMenuView("Examples", "developerExamplesMain.htm", true));
			level1List.add(new SecondaryMenuView("Forms", "developerExamplesForms1.htm"));
			level1List.add(new SecondaryMenuView("Tables", "developerExamplesTables1.htm"));
			
			level2List.add(new SecondaryMenuView("Overview", "developerExamplesMain.htm"));
			level2List.add(new SecondaryMenuView("Error Handling", "developerExamplesErrors.htm"));
			level2List.add(new SecondaryMenuView("Status Bar", "developerExamplesStatusBar.htm", true));
			level2List.add(new SecondaryMenuView("PopUp Dialog", "developerExamplesPopUp.htm"));
			level2List.add(new SecondaryMenuView("Ajax", "developerExamplesAjax.htm"));
			level2List.add(new SecondaryMenuView("Model", "developerExamplesModel.htm"));
			level2List.add(new SecondaryMenuView("Cache", "developerExamplesCache.htm"));
		}
		else if(currentView.equalsIgnoreCase("developerExamplesPopUp"))
		{
			level1List.add(new SecondaryMenuView("Examples", "developerExamplesMain.htm", true));
			level1List.add(new SecondaryMenuView("Forms", "developerExamplesForms1.htm"));
			level1List.add(new SecondaryMenuView("Tables", "developerExamplesTables1.htm"));
			
			level2List.add(new SecondaryMenuView("Overview", "developerExamplesMain.htm"));
			level2List.add(new SecondaryMenuView("Error Handling", "developerExamplesErrors.htm"));
			level2List.add(new SecondaryMenuView("Status Bar", "developerExamplesStatusBar.htm"));
			level2List.add(new SecondaryMenuView("PopUp Dialog", "developerExamplesPopUp.htm", true));
			level2List.add(new SecondaryMenuView("Ajax", "developerExamplesAjax.htm"));
			level2List.add(new SecondaryMenuView("Model", "developerExamplesModel.htm"));
			level2List.add(new SecondaryMenuView("Cache", "developerExamplesCache.htm"));
		}
		else if(currentView.equalsIgnoreCase("developerExamplesAjax"))
		{
			level1List.add(new SecondaryMenuView("Examples", "developerExamplesMain.htm", true));
			level1List.add(new SecondaryMenuView("Forms", "developerExamplesForms1.htm"));
			level1List.add(new SecondaryMenuView("Tables", "developerExamplesTables1.htm"));
			
			level2List.add(new SecondaryMenuView("Overview", "developerExamplesMain.htm"));
			level2List.add(new SecondaryMenuView("Error Handling", "developerExamplesErrors.htm"));
			level2List.add(new SecondaryMenuView("Status Bar", "developerExamplesStatusBar.htm"));
			level2List.add(new SecondaryMenuView("PopUp Dialog", "developerExamplesPopUp.htm"));
			level2List.add(new SecondaryMenuView("Ajax", "developerExamplesAjax.htm", true));
			level2List.add(new SecondaryMenuView("Model", "developerExamplesModel.htm"));
			level2List.add(new SecondaryMenuView("Cache", "developerExamplesCache.htm"));
		}
		else if(currentView.equalsIgnoreCase("developerExamplesModel"))
		{
			level1List.add(new SecondaryMenuView("Examples", "developerExamplesMain.htm", true));
			level1List.add(new SecondaryMenuView("Forms", "developerExamplesForms1.htm"));
			level1List.add(new SecondaryMenuView("Tables", "developerExamplesTables1.htm"));
			
			level2List.add(new SecondaryMenuView("Overview", "developerExamplesMain.htm"));
			level2List.add(new SecondaryMenuView("Error Handling", "developerExamplesErrors.htm"));
			level2List.add(new SecondaryMenuView("Status Bar", "developerExamplesStatusBar.htm"));
			level2List.add(new SecondaryMenuView("PopUp Dialog", "developerExamplesPopUp.htm"));
			level2List.add(new SecondaryMenuView("Ajax", "developerExamplesAjax.htm"));
			level2List.add(new SecondaryMenuView("Model", "developerExamplesModel.htm", true));
			level2List.add(new SecondaryMenuView("Cache", "developerExamplesCache.htm"));
		}
		else if(currentView.equalsIgnoreCase("developerExamplesCache"))
		{
			level1List.add(new SecondaryMenuView("Examples", "developerExamplesMain.htm", true));
			level1List.add(new SecondaryMenuView("Forms", "developerExamplesForms1.htm"));
			level1List.add(new SecondaryMenuView("Tables", "developerExamplesTables1.htm"));
			
			level2List.add(new SecondaryMenuView("Overview", "developerExamplesMain.htm"));
			level2List.add(new SecondaryMenuView("Error Handling", "developerExamplesErrors.htm"));
			level2List.add(new SecondaryMenuView("Status Bar", "developerExamplesStatusBar.htm"));
			level2List.add(new SecondaryMenuView("PopUp Dialog", "developerExamplesPopUp.htm"));
			level2List.add(new SecondaryMenuView("Ajax", "developerExamplesAjax.htm"));
			level2List.add(new SecondaryMenuView("Model", "developerExamplesModel.htm"));
			level2List.add(new SecondaryMenuView("Cache", "developerExamplesCache.htm", true));
		}
		else if(currentView.equalsIgnoreCase("developerExamplesForms1"))
		{
			level1List.add(new SecondaryMenuView("Examples", "developerExamplesMain.htm"));
			level1List.add(new SecondaryMenuView("Forms", "developerExamplesForms1.htm", true));
			level1List.add(new SecondaryMenuView("Tables", "developerExamplesTables1.htm"));
			
			level2List.add(new SecondaryMenuView("Forms1", "developerExamplesForms1.htm", true));
			level2List.add(new SecondaryMenuView("Forms2", "developerExamplesForms2.htm"));
		}
		else if(currentView.equalsIgnoreCase("developerExamplesForms2"))
		{
			level1List.add(new SecondaryMenuView("Examples", "developerExamplesMain.htm"));
			level1List.add(new SecondaryMenuView("Forms", "developerExamplesForms1.htm", true));
			level1List.add(new SecondaryMenuView("Tables", "developerExamplesTables1.htm"));
			
			level2List.add(new SecondaryMenuView("Forms1", "developerExamplesForms1.htm"));
			level2List.add(new SecondaryMenuView("Forms2", "developerExamplesForms2.htm", true));
		}
		else if(currentView.equalsIgnoreCase("developerExamplesTables1"))
		{
			level1List.add(new SecondaryMenuView("Examples", "developerExamplesMain.htm"));
			level1List.add(new SecondaryMenuView("Forms", "developerExamplesForms1.htm"));
			level1List.add(new SecondaryMenuView("Tables", "developerExamplesTables1.htm", true));
			
			level2List.add(new SecondaryMenuView("Tables1", "developerExamplesTables1.htm", true));
			level2List.add(new SecondaryMenuView("Tables2", "developerExamplesTables2.htm"));
			level2List.add(new SecondaryMenuView("Tables3", "developerExamplesTables3.htm"));
		}
		else if(currentView.equalsIgnoreCase("developerExamplesTables2"))
		{
			level1List.add(new SecondaryMenuView("Examples", "developerExamplesMain.htm"));
			level1List.add(new SecondaryMenuView("Forms", "developerExamplesForms1.htm"));
			level1List.add(new SecondaryMenuView("Tables", "developerExamplesTables1.htm", true));
			
			level2List.add(new SecondaryMenuView("Tables1", "developerExamplesTables1.htm"));
			level2List.add(new SecondaryMenuView("Tables2", "developerExamplesTables2.htm", true));
			level2List.add(new SecondaryMenuView("Tables3", "developerExamplesTables3.htm"));
		}
		else if( currentView.equalsIgnoreCase("developerExamplesTables3") || currentView.equalsIgnoreCase("developerExamplesTables3Form") )
		{
			level1List.add(new SecondaryMenuView("Examples", "developerExamplesMain.htm"));
			level1List.add(new SecondaryMenuView("Forms", "developerExamplesForms1.htm"));
			level1List.add(new SecondaryMenuView("Tables", "developerExamplesTables1.htm", true));
			
			level2List.add(new SecondaryMenuView("Tables1", "developerExamplesTables1.htm"));
			level2List.add(new SecondaryMenuView("Tables2", "developerExamplesTables2.htm"));
			level2List.add(new SecondaryMenuView("Tables3", "developerExamplesTables3.htm", true));
		}
		else if(currentView.equalsIgnoreCase("envelopeLog"))
		{
			level1List.add(new SecondaryMenuView("Email Sender Log", "envelopeLog.htm", true));
		}
		else if(currentView.equalsIgnoreCase("navigationGroup"))
		{
			level1List.add(new SecondaryMenuView("Navigation Groups", "navigationGroup.htm", true));
			level1List.add(new SecondaryMenuView("Navigation Links", "navigationLink.htm"));
			level1List.add(new SecondaryMenuView("Permissions", "navigationRoles.htm"));
		}		
		else if(currentView.equalsIgnoreCase("navigationLink"))
		{
			level1List.add(new SecondaryMenuView("Navigation Groups", "navigationGroup.htm"));
			level1List.add(new SecondaryMenuView("Navigation Links", "navigationLink.htm", true));
			level1List.add(new SecondaryMenuView("Permissions", "navigationRoles.htm"));
		}		
		else if(currentView.equalsIgnoreCase("navigationRoles"))
		{
			level1List.add(new SecondaryMenuView("Navigation Groups", "navigationGroup.htm"));
			level1List.add(new SecondaryMenuView("Navigation Links", "navigationLink.htm"));
			level1List.add(new SecondaryMenuView("Permissions", "navigationRoles.htm", true));
		}
		else if( currentView.equalsIgnoreCase("systemUsers") || currentView.equalsIgnoreCase("systemUsersForm") )
		{
			level1List.add(new SecondaryMenuView("Users", "systemUsers.htm", true));
		}
		else if(currentView.equalsIgnoreCase("transactionDelete"))
		{
			level1List.add(new SecondaryMenuView("Trans Delete", "transactionDelete.htm", true));
		}		
		else if(currentView.equalsIgnoreCase("transactionDeleteApproval"))
		{
			level1List.add(new SecondaryMenuView("Trans Delete Approval", "transactionDeleteApproval.htm", true));
		}		
		else if(currentView.equalsIgnoreCase("welcome"))
		{
			level1List.add(new SecondaryMenuView("Home", "welcome.htm", true));
		}
		else
		{
			level1List.add(new SecondaryMenuView("", currentView + ".htm", true));
		}
	}
	
}
