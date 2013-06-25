package com.totalbanksolutions.framework.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.totalbanksolutions.framework.bean.database.table.CustomerAccountSetupErrors;
import com.totalbanksolutions.framework.bean.database.view.ViewAccountRegistrationError;
import com.totalbanksolutions.framework.bean.database.view.ViewBalanceSummaryAndFeesByProduct;
import com.totalbanksolutions.framework.bean.database.view.ViewBankBalanceReport;
import com.totalbanksolutions.framework.bean.database.view.ViewBankCustomerStatementReport;
import com.totalbanksolutions.framework.bean.database.view.ViewCombinedBalanceReport;
import com.totalbanksolutions.framework.bean.database.view.ViewConsolidationAccountBalances;
import com.totalbanksolutions.framework.bean.database.view.ViewConsolidationBanks;
import com.totalbanksolutions.framework.bean.database.view.ViewConsolidationGroupBalances;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomer1099Report;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccount;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountBalance;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountInitialDeposit;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountInterest;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountTransaction;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccruedInterest;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAverageBalance;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerBalanceReport;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerBankBalanceReport;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerConfirmReport;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerNOWBalanceReport;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerStatementReport;
import com.totalbanksolutions.framework.bean.database.view.ViewDBTCAFees;
import com.totalbanksolutions.framework.bean.database.view.ViewDailyBankBalancesByProduct;
import com.totalbanksolutions.framework.bean.database.view.ViewDepositBanksUsedInPeriod;
import com.totalbanksolutions.framework.bean.database.view.ViewForecastBalancesReport;
import com.totalbanksolutions.framework.bean.database.view.ViewForecastSummaryReport;
import com.totalbanksolutions.framework.bean.database.view.ViewPershingBankFees;
import com.totalbanksolutions.framework.bean.database.view.ViewProductBalanceReport;
import com.totalbanksolutions.framework.bean.database.view.ViewRBCCustomerConfirms;
import com.totalbanksolutions.framework.bean.database.view.ViewRBCRevenue;
import com.totalbanksolutions.framework.bean.database.view.ViewSourceInstitutionBalanceReport;
import com.totalbanksolutions.framework.bean.database.view.ViewStatementContent;
import com.totalbanksolutions.framework.bean.database.view.ViewStatementDates;
import com.totalbanksolutions.framework.bean.database.view.ViewSungardCustomerStatementReport;
import com.totalbanksolutions.framework.bean.database.view.ViewSungardInterestTransactions;
import com.totalbanksolutions.framework.bean.database.view.ViewWires;
import com.totalbanksolutions.framework.dao.util.DAO;

/**
 * @author vcatrini
 * 
 * Modified:	Apr-18-12 NAM   #1538: Added batching of resultset
 * 				09 Jul 2012 DJM #1717: File writing - Optimize for Davidson program
 *              20 Sep 2012 VC #1834: Add Warning(s) that appear on checklist, to the Forecast and Consolidation reports 
 * 
 */
public interface FileWriterDAO extends DAO
{

    public void close();
	
    //============================================================================
    //
    // Writers - Shared
    //
	//============================================================================
    public List<ViewCustomerAccountBalance> getCustomerAccountBalanceList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex);
    public Map<String,ViewCustomerAccount> getCustomerAccountsMap(long sourceInstitutionId, String databaseName);

	//============================================================================
	//
    // 1099 Writers
    //
	//============================================================================
    public List<ViewCustomer1099Report> getCustomer1099ReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate);
    
	//============================================================================
    //
	// Account Registration Errors Writers
    //
	//============================================================================
    public List<CustomerAccountSetupErrors> getCustomerAccountSetupErrorsList(String databaseName, long programId, long sourceInstitutionId, Date fileDate, boolean isLateProcessing);

	//============================================================================
    //
	// Accrued Interest Writers
    //
	//============================================================================
	public List<ViewCustomerAccruedInterest> getCustomerAccruedInterest(String programDatabaseName, long sourceInstitutionId, Date businessDate);
    public List<ViewCustomerAccountInterest> getCustomerAccountAccruedInterestList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex);
    public List<ViewSungardInterestTransactions> getSungardInterestTransactions(String databaseName, long programId, long sourceInstitutionId, Date businessDate);

	//============================================================================
    //
	// Balance Writers
    //
	//============================================================================
	public List<ViewCustomerAccountBalance> getAccountBalance(String databaseName,long programID, long sourceInstitution, Date balanceDate);
    public List<ViewBankBalanceReport> getBankBalanceReportList(String databaseName, long sourceInstitutionId, Date businessDate);
    public List<ViewCombinedBalanceReport> getCombinedBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex);
    public List<ViewCustomerBalanceReport> getCustomerBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex);
    public List<ViewCustomerBankBalanceReport> getCustomerBankBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex);
    public List<ViewCustomerNOWBalanceReport> getCustomerNOWBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex);
	public List<ViewDailyBankBalancesByProduct> getDailyBankBalancesByProduct( String databaseName, long sourceInstitutionId, Date balanceDate );
    public List<ViewDepositBanksUsedInPeriod> getDepositBanksUsedInPeriod( String databaseName, long sourceInstitutionId, Date balanceDate );
	public List<ViewCustomerStatementReport> getLegentCustomerBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex);
    public List<ViewCustomerBalanceReport> getPershingCustomerBalance(String databaseName, long programId, long sourceInstitutionId, Date businessDate, String ibdCode, int batchSize, int batchIndex);
    public List<ViewBankCustomerStatementReport> getPershingCustomerBalanceAtPE(String databaseName, long programId, long sourceInstitutionId, Date businessDate, String ibdCode, int batchSize, int batchIndex);
    public List<ViewBankCustomerStatementReport> getPershingCustomerBalanceAtPeriodStart(String databaseName, long programId, long sourceInstitutionId, Date businessDate, String ibdCode, int batchSize, int batchIndex);
    public List<ViewProductBalanceReport> getProductBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate);
    public List<ViewSourceInstitutionBalanceReport> getSourceInstitutionBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate);
    public List<ViewCustomerAverageBalance> getCustomerAverageBalancesReport(String databaseName, int batchSize, int batchIndex);

	//============================================================================
    //
	// Confirm Writers
    //
	//============================================================================
    public List<ViewAccountRegistrationError> getAccountRegistrationErrorView(String databaseName, long programId, long sourceInstitutionId, Date fileDate, int batchSize, int batchIndex );
    public List<ViewCustomerConfirmReport> getBankConfirmReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate);
    public List<ViewCustomerConfirmReport> getBETACustomerConfirmReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate);    
    public List<ViewCustomerConfirmReport> getCustomerConfirmReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex);    
    public List<ViewCustomerAccountTransaction> getCustomerTransactionList(String databaseName, long programId, long sourceInstitutionId, Date businessDate);
    public List<ViewCustomerConfirmReport> getUBSCustomerConfirmReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex);    
    public List<ViewCustomerAccountTransaction> getCustomerTransactionPreviousDayLateActivityList(String databaseName, long programId, long sourceInstitutionId);
    public List<ViewRBCCustomerConfirms> getRBCCustomerConfirms(String databaseName, long programId, long sourceInstitutionId, int batchSize, int batchIndex);

	//============================================================================
    //
	// Transaction Writers
    //
	//============================================================================
	public List<ViewCustomerAccountTransaction> getTransactionDeletions(String databaseName, long programId, long sourceInstitutionId, Date businessDate);
    
    //============================================================================
    //
	// Consolidation and Forecast Writers
    //
	//============================================================================	
    public List<ViewConsolidationAccountBalances> getConsolidationAccountBalances(String databaseName);
	public List<ViewConsolidationBanks> getConsolidationBanks(String databaseName);
    public List<ViewConsolidationGroupBalances> getConsolidationGroupBalances(String databaseName);
    public List<ViewForecastBalancesReport> getForecastBalancesReport(String databaseName, Date businessDate, boolean isWaterfall);
	public List<ViewForecastSummaryReport> getForecastSummaryReport(String databaseName);
	public String getForecastAndConsolidationWarningMessage(String databaseName, long programId);
	
    //============================================================================
    //
	// Fees and Revenue Writers
    //
	//============================================================================	
    public List<ViewBalanceSummaryAndFeesByProduct> getBalanceSummaryAndFeesByProduct(String databaseName, Date businessDate, long programId, long sourceInstitutionId );
    public List<ViewDBTCAFees> getDBTCAFees(long sourceInstitutionId);
    public int getEffectiveSIPegAmount(long sourceInstitutionId, Date businessDate);
	public List<ViewRBCRevenue> getMonthlyAffiliatedBankRevenue(String databaseName,long programID, long sourceInstitution, Date balanceDate);
	public List<ViewRBCRevenue> getMonthlyRevenue(String databaseName,long programID, long sourceInstitution, Date balanceDate);
    public List<ViewPershingBankFees> getPershingBankFees(Date businessDate);
    
    //============================================================================
    //
	// Initial Deposits Writers
    //
	//============================================================================	
    public List<ViewCustomerAccountInitialDeposit> getCustomerAccountInitialDeposits(long sourceInstitutionId, String databaseName, Date startDate, Date endDate);

    //============================================================================
    //
	// Statement Writers
    //
	//============================================================================	
    public List<ViewBankCustomerStatementReport> getBankCustomerStatementReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex);
    public List<ViewCustomerStatementReport> getBrokerStatementReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex);
    public List<ViewCustomerStatementReport> getCustomerStatementReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex);
	public List<ViewStatementContent>getStatementContents(String programDatabaseName, long programID, long sourceInstitution, Date balanceDate);
    public ViewStatementDates getStatementDates(long programId, Date businessDate);
    public List<ViewSungardCustomerStatementReport> getSungardCustomerStatementReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex );

    //============================================================================
    //
	// Wire File Writers
    //
	//============================================================================	
    public List<ViewWires> getWires(String databaseName, long programId, Date businessDate);



}
