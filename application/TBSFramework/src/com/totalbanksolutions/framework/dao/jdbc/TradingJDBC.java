package com.totalbanksolutions.framework.dao.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.AppConfiguration;
import com.totalbanksolutions.framework.bean.database.table.AppUserChallengeQuestionRelationship;
import com.totalbanksolutions.framework.bean.database.table.AppUserHistory;
import com.totalbanksolutions.framework.bean.database.table.AppUserSourceInstitutionRelationship;
import com.totalbanksolutions.framework.bean.database.table.ChallengeQuestion;
import com.totalbanksolutions.framework.bean.database.view.ViewAppUserSession;
import com.totalbanksolutions.framework.dao.TradingDAO;
import com.totalbanksolutions.framework.dao.util.DataBatchManager;
import com.totalbanksolutions.framework.dao.util.DatabaseHelper;
import com.totalbanksolutions.framework.dao.util.SQLParameters;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.FormValidationView;
import com.totalbanksolutions.framework.model.database.table.TradingTransactionStatusTypesTable;
import com.totalbanksolutions.framework.model.database.view.TradingAccountSummaryView;
import com.totalbanksolutions.framework.model.database.view.TradingExecutionStatusView;
import com.totalbanksolutions.framework.model.database.view.TradingMarketOrderView;
import com.totalbanksolutions.framework.model.database.view.TradingExecutionTransactionView;
import com.totalbanksolutions.framework.model.database.view.TradingUserOrderView;
import com.totalbanksolutions.framework.model.database.view.TradingUserPositionView;
import com.totalbanksolutions.framework.model.database.view.TradingUserSettings;
import com.totalbanksolutions.framework.model.database.view.TradingUserTransactionView;
import com.totalbanksolutions.framework.util.ConvertUtility;
import com.totalbanksolutions.framework.util.EncryptionUtility;


/**
 * =================================================================================================
 * Created:  12 Dec 2012 DJM    
 * Modified: 24 Apr 2013 VAC #2420: TBS Units Trading System - Bug Updating Trade Status
 * 
 * JDBC interface for TBS Units trading system
 * =================================================================================================
 */
public class TradingJDBC implements TradingDAO
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    private DataSource dataSource;
    private DatabaseHelper databaseHelper;
    
	public TradingJDBC(DataSource ds)
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
    // Trading Units JDBC implements
    //
	//============================================================================


    
	public TradingMarketOrderView getTradingMarketOrderSummary(String databaseName, long userId,Date startDate, Date endDate)
    {
		TradingMarketOrderView t = new TradingMarketOrderView();
		String sql = "EXEC " + databaseName + "..p_Trading_GetMarketOrderSummary @User_FK=?, @StartDate=?, @EndDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		params.addValue(startDate);
		params.addValue(endDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }

    public TradingUserPositionView getTradingUserOpenPositions(String databaseName, long userId)
    {
		TradingUserPositionView t = new TradingUserPositionView();
		String sql = "EXEC " + databaseName + "..p_Trading_GetUserOpenPositions @User_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }

    public TradingAccountSummaryView getTradingAccountSummary(String databaseName, long userId)
    {
		TradingAccountSummaryView t = new TradingAccountSummaryView();
		String sql = "EXEC " + databaseName + "..p_Trading_GetAccountSummary @User_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
    
    public String insertUserOrder(String databaseName, TradingUserOrderView t, long userId)
    {
		String OrderNumber = "";
    	String sql = "EXEC " +  Databases.TRADING + "..p_Trading_InsertUserOrder @User_FK=?, @Security_FK=?, @Type_FK=?, @Quantity=?, @Price=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		params.addValue( t.getRow().getValue(t.SECURITY_ID) );
		params.addValue( t.getRow().getValue(t.ORDER_TYPE_ID) );
		params.addValue( t.getRow().getValue(t.QUANTITY) );
		params.addValue( t.getRow().getValue(t.PRICE) );
		OrderNumber = this.databaseHelper.executeStoredProcedureForString(sql, params);
		return OrderNumber;
    }

    public Date getNextOrderTradingDate(String databaseName)
    {
    	String sql = "SELECT TBS_Trading.dbo.f_Trading_GetNextExecutionDateForOrders(1)";
		Date d = this.databaseHelper.queryForDate(sql, null);
		return d;
    }    

    //============================================================================
	//
    // AppUser_History
    //
	//============================================================================
    public List<AppUserHistory> getAppUserHistory( long userID, int historicalRecordCount )
    {
		String sql = "SELECT TOP(?) H.* "
		+ " FROM " + Databases.TRADING + "..AppUser_History H WITH (NOLOCK) "
		+ " LEFT JOIN " + Databases.TRADING + "..AppUser U WITH (NOLOCK) "  
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
    // AppConfiguration
    //
	//============================================================================
    public AppConfiguration getAppConfiguration() 
	{
		String sql = "SELECT * FROM TBS_Trading.." + AppConfiguration.TABLE_NAME + " WITH (NOLOCK)";
		SQLParameters params = new SQLParameters();
		return (AppConfiguration)this.databaseHelper.queryForObject(sql, params, new AppConfiguration());
	}
    
    //============================================================================
	//
    // AppUser
    //
	//============================================================================	
    public ViewAppUserSession validateLogin(String userName, String textPassword)
	{
		String password = EncryptionUtility.getInstance().encrypt(textPassword);
    	String sql = "EXEC TBS_Trading..p_j_ValidateUserLogin @UserName=?, @UserPassword=? ";
		log.debug("### validateLogin calls : " + sql + " ###");
		log.debug("### password = : " + password+ " ###");
		
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
    
    public List<Long> getAppUserRoleIdList(long userId)
	{
		List<Long> dataList = new ArrayList<Long>();
		String sql = "SELECT Role_FK "
		+ "FROM TBS_Trading..AppUser_AppRole_Relationship WITH (NOLOCK) "
		+ "WHERE User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		dataList = this.databaseHelper.queryForLongList(sql, params);
		return dataList;
	}

	public void updateAppUserPassword(long userId, String textPassword, int minPasswordLength)
	{
		if(textPassword.length() < minPasswordLength)
		{
			throw new RuntimeException("Password must be at least " + minPasswordLength + " characters in length.");
		}
		else
		{
		    String sql = "UPDATE " + Databases.TRADING + "..AppUser SET UserPassword = ?, IsPasswordReset = 0, "
		    + "PasswordChangeDateTime = getdate(), LoginFailedAttempts = 0 "
		    + "WHERE User_PK = ? ";
	
			String password = EncryptionUtility.getInstance().encrypt(textPassword);
		    SQLParameters params = new SQLParameters();
			params.addValue(password);
			params.addValue(userId);
			databaseHelper.executeUpdate(sql, params);
		}
	}
	
	public List<Long> getAppUserSourceInstitutionIdList(long userId)
	{
		String sql = "SELECT SourceInstitution_FK "
		+ "FROM TBS_Trading.." + AppUserSourceInstitutionRelationship.TABLE_NAME + " WITH (NOLOCK) "
		+ "WHERE User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		return this.databaseHelper.queryForLongList(sql, params);
	}
	
    //============================================================================
	//
    // AppSession
    //
	//============================================================================	
	public void activateAppSession(long userId, String sessionKey)
	{
		String sql = "EXEC " +  Databases.TRADING + "..p_j_ActivateUserSession @User_FK=?, @SessionKey=? ";
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

		String sql = "EXEC " +  Databases.TRADING + "..p_j_AddUserSession @User_FK=?, @SessionKey=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		params.addValue(encryptedKey);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);		
		return encryptedKey;
	}

	public void deactivateAppSession(long userId, String sessionKey)
	{
		String sql = "UPDATE " + Databases.TRADING + "..AppSession SET IsDeleted = 1 WHERE User_FK = ? AND SessionKey = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		params.addValue(sessionKey);
		this.databaseHelper.executeUpdate(sql, params);
	}	

	public void deleteAppSession(DataBatchManager batchManager, long userId)
	{
		String sql = "DELETE " + Databases.TRADING + "..AppSession WHERE User_FK = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		batchManager.executeUpdate(sql, params);
	}

	public void expireAppSessions(int expireInMinutes)
	{	
		String sql = "UPDATE " + Databases.TRADING + "..AppSession SET SessionKey = '', IsDeleted = 1 "
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
    	String sql = "EXEC " + Databases.TRADING + "..p_j_ValidateUserSession @User_FK=?, @SessionKey=?, @WorkingProgram_FK=?, @WorkingSourceInstitution_FK=?, @WorkingDate=?, @LoginInProgress=? ";
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
    // ChallengeQuestion
    //
	//============================================================================
	private int deleteUserChallengeQuestion(DataBatchManager batchManager, long userId)
	{
		String sql = "DELETE FROM " + Databases.TRADING + "..AppUser_ChallengeQuestion_Relationship "
		+ "WHERE User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		int rowsAffected = batchManager.executeUpdate(sql, params);
		return rowsAffected;
	}

    public List<ChallengeQuestion> getChallengeQuestionList()
	{
		String sql = "SELECT * "
		+ " FROM " + Databases.TRADING + "..ChallengeQuestion WITH (NOLOCK) "
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
		+ " FROM " + Databases.TRADING + "..AppUser_ChallengeQuestion_Relationship a WITH (NOLOCK) "
		+ " INNER JOIN " + Databases.TRADING + "..ChallengeQuestion b WITH (NOLOCK) ON a.ChallengeQuestion_FK = b.ChallengeQuestion_PK "
		+ " WHERE a.User_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		String[] bindList = {"ChallengeQuestion"};
		return (List<AppUserChallengeQuestionRelationship>)this.databaseHelper.queryForList(sql, params, new AppUserChallengeQuestionRelationship(bindList));
	}
    
    public ChallengeQuestion getRandomChallengeQuestion(long userId)
	{
		String sql = "EXEC " + Databases.TRADING + "..p_j_GetRandomChallengeQuestion @User_FK=? ";
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

			String sql = "UPDATE " + Databases.TRADING + "..AppUser SET IsSecretQuestionsSet = 0 WHERE User_PK = ?";
			batchManager.prepareSQL(sql);
			SQLParameters params = new SQLParameters();
			params.addValue(userId);
			batchManager.addBatch(params);
	
			sql = "DELETE FROM " + Databases.TRADING + "..AppUser_ChallengeQuestion_Relationship WHERE User_FK = ?";
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

			String sql = "UPDATE " + Databases.TRADING  + "..AppUser SET IsSecretQuestionsSet = 1 WHERE User_PK = ? ";
			batchManager.prepareSQL(sql);
			SQLParameters params = new SQLParameters();
			params.addValue(userId);
			batchManager.addBatch(params);

			sql = "DELETE FROM " + Databases.TRADING + "..AppUser_ChallengeQuestion_Relationship WHERE User_FK = ? ";
			batchManager.prepareSQL(sql);
			params = new SQLParameters();
			params.addValue(userId);
			batchManager.addBatch(params);
			
			for(AppUserChallengeQuestionRelationship question : questions)
			{
				boolean isInitialized = false;
				if(!isInitialized)
				{
					//sql = question.getSQLInsertStatementNew();
					sql = "INSERT INTO " + Databases.TRADING + "..AppUser_ChallengeQuestion_Relationship ( User_FK, ChallengeQuestion_FK, Response ) VALUES (?, ?, ?) ";
					batchManager.prepareSQL(sql);
					isInitialized = true;
				}
				//params = question.getSQLInsertParameters();
				params = new SQLParameters();
				params.addValue(userId);
				params.addValue(question.getQuestionId());
				params.addValue(question.getResponse());
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
    // Trading_UserSettings
    //
	//============================================================================	
	public TradingUserSettings getTradingUserSettings(String databaseName, long userId)  
    {
		TradingUserSettings t = new TradingUserSettings();
		String sql = "EXEC " + databaseName + "..p_Trading_GetUserSettings @User_FK=?";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
	
	public void setDisclaimerApproved(String databaseName, long userId)
	{
		String sql = "EXEC " + databaseName + "..p_Trading_SetDisclaimerApproved @User_FK=?";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
	}

	public boolean isDisclaimerApproved(long userId)
	{
    	String sql = "SELECT TBS_Trading.dbo.f_IsDisclaimerApproved(" + userId + ")";
		boolean b = this.databaseHelper.queryForBoolean(sql, null);
		return b;
	}


	//============================================================================
	//
    // Open Orders: Bid /Ask
    //
	//============================================================================
	public TradingUserOrderView getTradingActiveUserOrders(String databaseName, long userId, Date startDate, Date endDate)  
    {
		TradingUserOrderView t = new TradingUserOrderView();
		String sql = "EXEC " + databaseName + "..p_Trading_GetActiveUserOrders @User_FK=?, @StartDate=?, @EndDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		params.addValue(startDate);
		params.addValue(endDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }

	public TradingUserOrderView getTradingUserOrder(String databaseName, long userId, String orderId)  
    {
		TradingUserOrderView t = new TradingUserOrderView();
		String sql = "EXEC " + databaseName + "..p_Trading_GetUserOrder @User_FK=?, @OrderId=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		params.addValue(orderId);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
	
	public FormValidationView validateUserOrder(String databaseName, TradingUserOrderView t, long userId)
    {
    	FormValidationView r = new FormValidationView();
		String sql = "EXEC " +  Databases.TRADING + "..p_Trading_ValidateUserOrder @User_FK=?, @Security_FK=?, @Type_FK=?, @Quantity=?, @Price=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		params.addValue( t.getRow().getValue(t.SECURITY_ID) );
		params.addValue( t.getRow().getValue(t.ORDER_TYPE_ID) );
		params.addValue( t.getRow().getValue(t.QUANTITY) );
		params.addValue( t.getRow().getValue(t.PRICE) );
		this.databaseHelper.queryForModelTable(sql, params, r);
		return r;
    }

	public void cancelUserOrder(String databaseName, String paddedOrderId)
	{
	    String sql = "SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'CANCELED'"; 
		long statusCancel = this.databaseHelper.queryForLong(sql, null);

    	sql = "SELECT TBS_Trading.dbo.f_GetUserOrderNumberPK('" + paddedOrderId + "')";
		long orderId = this.databaseHelper.queryForLong(sql, null);
		
	    sql = "UPDATE " + Databases.TRADING + "..User_Orders SET " 
	    	+ "Status_FK = ? "
	    	+ "WHERE Order_PK = ? ";
	    SQLParameters params = new SQLParameters();
		params.addValue(statusCancel);
		params.addValue(orderId);
		
		databaseHelper.executeUpdate(sql, params);
	}
	
	 public TradingTransactionStatusTypesTable getTransactionStatusTypeList()
	{
		 TradingTransactionStatusTypesTable t = new TradingTransactionStatusTypesTable();
		String sql = "SELECT H.* "
		+ " FROM " + Databases.TRADING + "..TransactionStatus_Types H WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }

	 public boolean checkIsOrderLocked()
	 {
		    	String sql = "SELECT TBS_Trading.dbo.f_IsOrdersLocked()";
				Boolean b = this.databaseHelper.queryForBoolean(sql, null);
				log.debug("JDBC ORDERLOCKED" + b);
				return b;
	}

	//============================================================================
	//
	// Trade Execution
	//
	//============================================================================	
	public int executeTradeMatch()
	{
		String sql = "EXEC " +  Databases.TRADING + "..p_Trading_CreateTransactions";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.executeStoredProcedureForInteger(sql, params);
	}

	public TradingExecutionStatusView getTradingExecutionStatus()  
    {
		TradingExecutionStatusView t = new TradingExecutionStatusView();
		String sql = "EXEC " + Databases.TRADING + "..p_Trading_GetTradeExecutionStatus ";
		SQLParameters params = new SQLParameters();
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
	
	public TradingExecutionTransactionView getTradeExecutionTrades()
    {
		log.debug("got here");
		TradingExecutionTransactionView t = new TradingExecutionTransactionView();
		String sql = "EXEC " + Databases.TRADING + "..p_Trading_GetTradeExecutionTransactions";
		SQLParameters params = new SQLParameters();
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
	

	//============================================================================
	//
	// Transactions
	//
	//============================================================================	
	public TradingUserTransactionView getTradingUserTransactions(long userId)  
    {
		TradingUserTransactionView t = new TradingUserTransactionView();
		String sql = "EXEC " + Databases.TRADING + "..p_Trading_GetUserTransactions @User_FK=?";
		SQLParameters params = new SQLParameters();
		params.addValue(userId);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }

	public TradingUserTransactionView getTradingMarketTransactionHistory()  
    {
		TradingUserTransactionView t = new TradingUserTransactionView();
		String sql = "EXEC " + Databases.TRADING + "..p_Trading_GetMarketTransactionHistory ";
		SQLParameters params = new SQLParameters();
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
	
	public void updateTradingUserTransaction(String transactionId, long statusId)
	{
		String sql = "EXEC " + Databases.TRADING + "..p_Trading_UpdateUserTransaction @TransactionID=?, @TransactionStatus_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(transactionId);
		params.addValue(statusId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
	 }
	
}
