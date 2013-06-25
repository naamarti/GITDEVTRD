package com.totalbanksolutions.framework.dao;

import java.util.Date;
import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.AppConfiguration;
import com.totalbanksolutions.framework.bean.database.table.AppUserChallengeQuestionRelationship;
import com.totalbanksolutions.framework.bean.database.table.AppUserHistory;
import com.totalbanksolutions.framework.bean.database.table.ChallengeQuestion;
import com.totalbanksolutions.framework.bean.database.view.ViewAppUserSession;
import com.totalbanksolutions.framework.dao.util.DAO;
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


/**
 * =================================================================================================
 * Created:  12 Dec 2012 DJM    
 * Modified: 24 Apr 2013 VAC #2420: TBS Units Trading System - Bug Updating Trade Status
 * 
 * Dataservice for TBS Units trading system
 * =================================================================================================
 */
public interface TradingDAO extends DAO
{

    public void close();
	
	//============================================================================
	//
    // TBS Units Trading
    //
	//============================================================================     
    public TradingMarketOrderView getTradingMarketOrderSummary(String databaseName, long userId, Date startDate, Date endDate);
    public TradingUserPositionView getTradingUserOpenPositions(String databaseName, long userId);
    public TradingAccountSummaryView getTradingAccountSummary(String databaseName, long userId);
    

	//============================================================================
	//
    // AppConfiguration
	//
    //============================================================================
    public AppConfiguration getAppConfiguration();
    
    
	//============================================================================
	//
    // AppUser_History
    //
	//============================================================================
    public List<AppUserHistory> getAppUserHistory( long userID, int historicalRecordCount );
    
	//============================================================================
	//
    // AppUser
	//
    //============================================================================
	public ViewAppUserSession validateLogin(String userName, String password);
	public void updateAppUserPassword(long userId, String textPassword, int minPasswordLength);

	//============================================================================
	//
	// AppUser_AppRole_Relationship
	//
	//============================================================================
	public List<Long> getAppUserRoleIdList(long userId);

	//============================================================================
	//
	// AppUser_SourceInsitution_Relationship
	//
	//============================================================================
	public List<Long> getAppUserSourceInstitutionIdList(long userId);
	
	//============================================================================
	//
	// AppSession
	//
	//============================================================================
	public void activateAppSession(long userId, String sessionKey);
	public String addAppSession(long userId);
	public void deactivateAppSession(long userId, String sessionKey);
	public void expireAppSessions(int expireInMinutes);
	public ViewAppUserSession validateAppUserSession(ViewAppUserSession user);
	public ViewAppUserSession validateAppUserSession(ViewAppUserSession user, boolean isLoginInProgress);

    //============================================================================
	//
    // ChallengeQuestion
    //
	//============================================================================
	public List<ChallengeQuestion> getChallengeQuestionList(); 
    public AppUserChallengeQuestionRelationship[] getChallengeResponseArray(long userId);
	public List<AppUserChallengeQuestionRelationship> getChallengeResponseList(long userId);
    public ChallengeQuestion getRandomChallengeQuestion(long userId);
//	public void resetChallengeQuestions(long userId);	
	public void updateUserChallengeQuestions(long userId, AppUserChallengeQuestionRelationship[] questions);
	public boolean validateChallengeReponse(long userId, long questionId, String questionResponse);

	//============================================================================
	//
    // Trading_UserSettings
	//
    //============================================================================
    public TradingUserSettings getTradingUserSettings(String databaseName, long userId);
	public void setDisclaimerApproved(String databaseName, long userId);
	public boolean isDisclaimerApproved(long userId);
	
	//============================================================================
	//
    // User_Orders
	//
    //============================================================================
    public TradingUserOrderView getTradingActiveUserOrders(String databaseName, long userId, Date startDate, Date endDate);
	public TradingUserOrderView getTradingUserOrder(String databaseName, long userId, String orderId);  
    public String insertUserOrder(String databaseName, TradingUserOrderView t, long userId);
    public FormValidationView validateUserOrder(String databaseName, TradingUserOrderView t, long userId);
	public void cancelUserOrder(String databaseName, String paddedOrderId);
	
	public TradingTransactionStatusTypesTable getTransactionStatusTypeList();

	public Date getNextOrderTradingDate(String databaseName);
	public boolean checkIsOrderLocked();

	//============================================================================
	//
	// Trade Execution
	//
	//============================================================================	
	public int executeTradeMatch();
	public TradingExecutionStatusView getTradingExecutionStatus();
	public TradingExecutionTransactionView getTradeExecutionTrades();

	//============================================================================
	//
	// Transactions
	//
	//============================================================================	
	public TradingUserTransactionView getTradingUserTransactions(long userId);
	public TradingUserTransactionView getTradingMarketTransactionHistory();
	public void updateTradingUserTransaction(String transactionId, long statusId);	

}
