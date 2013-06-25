package com.totalbanksolutions.framework.dao;

import java.util.Date;

import com.totalbanksolutions.framework.bean.database.view.ViewProcessCashInterestTotals;
import com.totalbanksolutions.framework.dao.util.DAO;

/**
 * =================================================================================================
 * Created:   VC
 * Modified:  07 Jun 2011 NM #694: Add Cash Interest and opening balance in checklist screen
 *            09 Nov 2011 VC #1054: Eliminate omnibus MMDA withdrawals by reducing NOW balances where possible
 *			  23 Apr 2012 VC #1495: Forecast - separate waterfall forecast & generate reports steps from normal forecast
 *
 * =================================================================================================
 */
public interface ProcessingDAO extends DAO
{

    public void close();
	
	//============================================================================
	//
    // AutoRecon
    //
	//============================================================================
    public void updateAutoReconData(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId, long userId);
    
    //============================================================================
	//
    // Commit Transactions
    //
	//============================================================================
    public void commitTransactions(String databaseName, long programId, long sourceInstitutionId, Date businessDate, boolean isLateProcessing, long batchId);

    //============================================================================
	//
    // Consolidation and Forecast
    //
	//============================================================================
	public void allocateMMDANOWBalances(long programId, long sourceInstitutionId, String databaseName, long batchId, Date businessDate);
	public void allocateMMDANOWBankBalances(long programId, long sourceInstitutionId, String databaseName, long batchId, Date businessDate);
	public void allocateReduceNOWBalances(long programId, long sourceInstitutionId, String databaseName, Date businessDate, long batchId);
	public void consolidateAccounts(String databaseName, long programId, Date businessDate, long batchId);
	public void runForecast(String databaseName, long programId, Date businessDate, long batchId);
	public void runForecastWaterfall(String databaseName, long programId, Date businessDate, long batchId);

    //============================================================================
	//
	// Create Tax Groups
	//
	//============================================================================
	public void createAccountTaxIDGroups(long programId, long sourceInstitutionId, String databaseName, long batchId, Date businessDate);

    //============================================================================
	//
	// Email Sender
	//
	//============================================================================
    public void updateEMSFilesToSend(long batchId, long depositInstitutionId, long sourceInstitutionId, long program, long envelope, String ReportFileFullPath);

	//============================================================================
	//
    // Interest and Fees
    //
	//============================================================================
	public void calculateInterestAndFees(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId, boolean isLateProcessing);
    public void processCashInterestPayments(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId);
    public ViewProcessCashInterestTotals getCashInterestPaymentTotals(String databaseName, long programId, long sourceInstitutionId, Date businessDate);

    //============================================================================
	//
    // Roll to Next Day
	//
    //============================================================================
    public void rollDailyProcessDate(long programId, long sourceInstitutionId, String databaseName, long batchId);

}
