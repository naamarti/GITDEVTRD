package com.totalbanksolutions.framework.dataservice;



import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.AppConfiguration;
import com.totalbanksolutions.framework.bean.database.table.AppUserChallengeQuestionRelationship;
import com.totalbanksolutions.framework.bean.database.table.AppUserHistory;
import com.totalbanksolutions.framework.bean.database.table.ChallengeQuestion;
import com.totalbanksolutions.framework.bean.database.view.ViewAppUserSession;
import com.totalbanksolutions.framework.cache.CacheDataRetrievalMethod;
import com.totalbanksolutions.framework.cache.CacheManager;
import com.totalbanksolutions.framework.dao.TradingDAO;
import com.totalbanksolutions.framework.dao.jdbc.TradingJDBC;
import com.totalbanksolutions.framework.dataservice.util.DataCacheType;
import com.totalbanksolutions.framework.dataservice.util.DataRetrievalType;
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


/**
 * =================================================================================================
 * Created:  12 Dec 2012 DJM    
 * Modified: 24 Apr 2013 VAC #2420: TBS Units Trading System - Bug Updating Trade Status
 * 
 * Dataservice for TBS Units trading system
 * =================================================================================================
 */
public class TradingDataService 
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog( TradingDataService.class );
    private TradingDAO tradingDAO = null;
    private CacheManager cacheManager = null;
    private DataService ds = null;

    public TradingDataService ( DataSource dataSource, CacheManager cacheManager, DataService ds )
    {
    	this.tradingDAO = new TradingJDBC(dataSource);
    	this.cacheManager = cacheManager;
    	this.ds = ds;
    }
    
	public void close()
	{
		this.tradingDAO.close();
		this.tradingDAO = null;
		this.cacheManager = null;
		this.ds = null;
	}
	
	
    //============================================================================
	//
	// Trading
	//
	//============================================================================
    public TradingMarketOrderView getTradingMarketOrderSummary(long userId, Date startDate, Date endDate)
    {
    	String databaseName = "TBS_Trading";
    	return tradingDAO.getTradingMarketOrderSummary(databaseName, userId, startDate, endDate);
    }

    public TradingUserPositionView getTradingUserOpenPositions(long userId)
    {
    	String databaseName = Databases.TRADING;
    	return tradingDAO.getTradingUserOpenPositions(databaseName, userId);
    }

    public TradingAccountSummaryView getTradingAccountSummary(long userId)
    {
    	String databaseName = Databases.TRADING;
    	return tradingDAO.getTradingAccountSummary(databaseName, userId);
    }
    
    public Date getNextOrderTradingDate()
    {
    	String databaseName = Databases.TRADING;
    	return tradingDAO.getNextOrderTradingDate(databaseName);
    }
	
    public boolean checkIsOrderLocked()
    {
    	String databaseName = Databases.TRADING;
    	return tradingDAO.checkIsOrderLocked();
    }
	
    //============================================================================
	//
	// AppUser_SourceInstitution_Relationship
	//
	//============================================================================
	public List<Long> getAppUserSourceInstitutionIdList(long userId)
	{
		return tradingDAO.getAppUserSourceInstitutionIdList(userId);
	}
	
	//============================================================================
	//
	// AppConfiguration
	//
	//============================================================================
    public String getAppConfigEnvironmentName()
    {
    	String environmentName = "TEST";
    	AppConfiguration appConfiguration = this.getAppConfiguration();
    	environmentName = appConfiguration.getEnvironmentName();
    	return environmentName;
    }

    public String getAppConfigVersionNumber()
    {
    	String version = "01.00.00";
    	AppConfiguration appConfiguration = this.getAppConfiguration();
    	version = appConfiguration.getVersionNumber();
    	return version;
    }

    public int getAppConfigLoginAttemptsBeforeLockout()
    {
    	return this.ds.trading.getAppConfiguration().getNumberOfLoginAttemptsBeforeLockout();
    }

    protected AppConfiguration getAppConfiguration()
    {
       	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return tradingDAO.getAppConfiguration(); 
    		}
    	};
    	return (AppConfiguration)cacheManager.get(DataCacheType.APP_CONFIGURATION, retrieveMethod);
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


    //============================================================================
	//
    // AppUser_History
    //
	//============================================================================
    public List<AppUserHistory> getAppUserHistory( long userID, int historicalRecordCount )
    {
    	return tradingDAO.getAppUserHistory(userID, historicalRecordCount);
    }
    
	//============================================================================
	//
	// AppUser
	//
	//============================================================================
    public ViewAppUserSession validateLogin(String userName, String password)
	{
		return tradingDAO.validateLogin(userName, password);
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
	
	public void updateAppUserPassword(long userId, String textPassword, int minPasswordLength)
	{
		tradingDAO.updateAppUserPassword(userId, textPassword, minPasswordLength);
	}

	//============================================================================
	//
	// AppUser_AppRole_Relationship
	//
	//============================================================================
	@SuppressWarnings("unchecked")
	public List<Long> getAppUserRoleIdList(final long userId, DataRetrievalType dataRetrievalType)
	{
		return tradingDAO.getAppUserRoleIdList(userId);
	}

	//============================================================================
	//
	// AppSession
	//
	//============================================================================
	public void activateAppSession(long userId, String sessionKey)
	{
		tradingDAO.activateAppSession(userId, sessionKey);
	}
	
	public String addAppSession(long userId)
	{
		return tradingDAO.addAppSession(userId);
	}
	
	public void deactivateAppSession(long userId, String sessionKey)
	{
		tradingDAO.deactivateAppSession(userId, sessionKey);
	}

	public void expireAppSessions(int expireInMinutes)
	{
		tradingDAO.expireAppSessions(expireInMinutes);
	}

	public ViewAppUserSession validateAppUserSession(ViewAppUserSession user)
	{
		return tradingDAO.validateAppUserSession(user);
	}

	public ViewAppUserSession validateAppUserSessionPreLogin(ViewAppUserSession user)
	{
		boolean isLoginInProgress = true;
		return tradingDAO.validateAppUserSession(user, isLoginInProgress);
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
    			return tradingDAO.getChallengeQuestionList();
    		}
    	};
    	return (List<ChallengeQuestion>)cacheManager.get(DataCacheType.CHALLENGE_QUESTION_LIST, retrieveMethod);
	}

    public AppUserChallengeQuestionRelationship[] getChallengeResponseArray(long userId)
    {
    	return tradingDAO.getChallengeResponseArray(userId);
    }
	
	public List<AppUserChallengeQuestionRelationship> getChallengeResponseList(long userId)
	{
		return tradingDAO.getChallengeResponseList(userId);
	}
	
    public ChallengeQuestion getRandomChallengeQuestion(long userId)
    {
    	return tradingDAO.getRandomChallengeQuestion(userId);
    }
	
//	public void resetChallengeQuestions(long userId)
//	{
//		tradingDAO.resetChallengeQuestions(userId);	
//	}

	public void updateUserChallengeQuestions(long userId, AppUserChallengeQuestionRelationship[] questions)
	{
		tradingDAO.updateUserChallengeQuestions(userId, questions);
	}

	public boolean validateChallengeReponse(long userId, long questionId, String questionResponse)
	{
		return tradingDAO.validateChallengeReponse(userId, questionId, questionResponse);
	}

	//============================================================================
	//
    // Trading_UserSettings
	//
    //============================================================================
    public TradingUserSettings getTradingUserSettings(long userId)
    {
    	String databaseName = Databases.TRADING;
    	return tradingDAO.getTradingUserSettings(databaseName, userId);
    }

	public void setDisclaimerApproved(long userId)
	{
    	String databaseName = Databases.TRADING;
    	tradingDAO.setDisclaimerApproved(databaseName, userId);
	}

    public boolean isDisclaimerApproved(long userId)
    {
    	String databaseName = Databases.TRADING;
    	return tradingDAO.isDisclaimerApproved(userId);
    }


	//============================================================================
	//
    // User_Orders
	//
    //============================================================================
    public String insertUserOrder( TradingUserOrderView t, long userId )
    {
    	String databaseName = Databases.TRADING;
    	return tradingDAO.insertUserOrder(databaseName, t, userId);
    }

    public TradingUserOrderView getTradingActiveUserOrders(long userId, Date startDate, Date endDate)
    {
    	String databaseName = Databases.TRADING;
    	return tradingDAO.getTradingActiveUserOrders(databaseName, userId, startDate, endDate);
    }
    
    public TradingUserOrderView getTradingUserOrder(long userId, String orderId)
    {
    	String databaseName = Databases.TRADING;
    	return tradingDAO.getTradingUserOrder(databaseName, userId, orderId);
    }
    
    public void cancelUserOrder(String paddedOrderId)
    {
    	String databaseName = Databases.TRADING;
    	tradingDAO.cancelUserOrder(databaseName, paddedOrderId);    	
    }
    
    public FormValidationView validateUserOrder( TradingUserOrderView t, long userId )
    {
    	String databaseName = Databases.TRADING;
    	return tradingDAO.validateUserOrder(databaseName, t, userId);    	
    }
    
    
    public TradingTransactionStatusTypesTable getTransactionStatusTypeList()
	{
    	return tradingDAO.getTransactionStatusTypeList();
	} 
    

	//============================================================================
	//
	// Trade Execution
	//
	//============================================================================	
    public int executeTradeMatch()
    {
    	return tradingDAO.executeTradeMatch();    
    }

    public TradingExecutionStatusView getTradingExecutionStatus()
    {
    	return tradingDAO.getTradingExecutionStatus();
    }

    public TradingExecutionTransactionView getTradeExecutionSummary()
    {
    	return tradingDAO.getTradeExecutionTrades();
    }

	//============================================================================
	//
	// Transactions
	//
	//============================================================================	
	public TradingUserTransactionView getTradingUserTransactions(long userId)  
    {
    	return tradingDAO.getTradingUserTransactions(userId);
    }

	public TradingUserTransactionView getTradingMarketTransactionHistory()
	{
		return tradingDAO.getTradingMarketTransactionHistory();
	}

	public void updateTradingUserTransaction(String transactionId, long statusId)
	{
		tradingDAO.updateTradingUserTransaction(transactionId, statusId);
	}

}
