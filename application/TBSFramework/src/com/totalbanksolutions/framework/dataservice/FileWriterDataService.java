package com.totalbanksolutions.framework.dataservice;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import com.totalbanksolutions.framework.cache.CacheManager;
import com.totalbanksolutions.framework.dao.FileWriterDAO;
import com.totalbanksolutions.framework.dao.jdbc.FileWriterJDBC;

/**
 * @author vcatrini
 *
 * Modified    	Apr-18-12  NAM #1538: Added batching of resultset
 * 			 	09 Jul 2012 DM #1717: File writing - Optimize for Davidson program
 *              20 Sep 2012 VC #1834: Add Warning(s) that appear on checklist, to the Forecast and Consolidation reports 
 * 
 */
public class FileWriterDataService 
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());
    private FileWriterDAO fileWriterDAO = null;
    private CacheManager cacheManager = null;
    private DataService ds = null;

    public FileWriterDataService (DataSource dataSource, CacheManager cacheManager, DataService ds)
    {
    	this.fileWriterDAO = new FileWriterJDBC(dataSource);
    	this.cacheManager = cacheManager;
    	this.ds = ds;
    }
    
	public void close()
	{
		this.fileWriterDAO.close();
		this.fileWriterDAO = null;
		this.cacheManager = null;
		this.ds = null;
	}

	//============================================================================
    //
    // Writers - Shared
    //
	//============================================================================
    public List<ViewCustomerAccountBalance> getCustomerAccountBalanceList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCustomerAccountBalanceList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex);
    }

    public Map<String,ViewCustomerAccount> getCustomerAccountsMap(long programId, long sourceInstitutionId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
		return fileWriterDAO.getCustomerAccountsMap(sourceInstitutionId, databaseName);
    }

	//============================================================================
    //
    // 1099 Writers
    //
	//============================================================================
	public List<ViewCustomer1099Report> getCustomer1099ReportList(long programId, long sourceInstitutionId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCustomer1099ReportList(databaseName, programId, sourceInstitutionId, businessDate);    	
    }

	//============================================================================
    //
	// Account Registration Errors Writers
    //
	//============================================================================    
	public List<CustomerAccountSetupErrors> getCustomerAccountSetupErrorsList(long programId, long sourceInstitutionId, Date fileDate, boolean isLateProcessing )
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCustomerAccountSetupErrorsList(databaseName, programId, sourceInstitutionId, fileDate, isLateProcessing);
    }
    
	//============================================================================
	//
	// Accrued Interest Writers
	//
	//============================================================================	
	public List<ViewCustomerAccruedInterest> getCustomerAccruedInterest(long programId, long sourceInstitutionId, Date businessDate)
	{
		String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		return fileWriterDAO.getCustomerAccruedInterest(programDatabaseName, sourceInstitutionId, businessDate);		
	}

    public List<ViewCustomerAccountInterest> getCustomerAccountAccruedInterestList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCustomerAccountAccruedInterestList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex);
    }
	
    public List<ViewSungardInterestTransactions> getSungardInterestTransactions(long programId, long sourceInstitutionId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getSungardInterestTransactions(databaseName, programId, sourceInstitutionId, businessDate);
    }
    
	//============================================================================
	//
	// Balance Writers
	//
	//============================================================================
    public List<ViewCustomerAccountBalance> getAccountBalance(long programID, long sourceInstitution, Date balanceDate)
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programID);
    	return fileWriterDAO.getAccountBalance(programDatabaseName, programID, sourceInstitution, balanceDate);
    }

    public List<ViewBankBalanceReport> getBankBalanceReportList(long programId, long sourceInstitutionId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getBankBalanceReportList(databaseName, sourceInstitutionId, businessDate);    	
    }
    
    public List<ViewCombinedBalanceReport> getCombinedBalanceReportList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCombinedBalanceReportList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex);   	
    }
    
    public List<ViewCustomerBalanceReport> getCustomerBalanceReportList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCustomerBalanceReportList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex);    	
    }

    public List<ViewCustomerBankBalanceReport> getCustomerBankBalanceReportList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCustomerBankBalanceReportList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex);    	
    }

    public List<ViewCustomerNOWBalanceReport> getCustomerNOWBalanceReportList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCustomerNOWBalanceReportList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex);    	
    }

    public List<ViewDailyBankBalancesByProduct> getDailyBankBalancesByProduct( long programId, long sourceInstitutionId, Date balanceDate )
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getDailyBankBalancesByProduct( programDatabaseName, sourceInstitutionId, balanceDate );
    }

    public List<ViewDepositBanksUsedInPeriod> getDepositBanksUsedInPeriod( long programId, long sourceInstitutionId, Date balanceDate )
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getDepositBanksUsedInPeriod( programDatabaseName, sourceInstitutionId, balanceDate );
    }

    public int getDepositBanksUsedInPeriodCount(long programId, long sourceInstitutionId, Date balanceDate )
    {
    	int count = 0;
    	List<ViewDepositBanksUsedInPeriod> list = getDepositBanksUsedInPeriod(programId, sourceInstitutionId, balanceDate );
    	ViewDepositBanksUsedInPeriod bank = list.get(0);
    	if(bank != null)
    	{
    		return bank.getBankCount();
    	}
    	return count;
    }
    
    public List<ViewCustomerStatementReport> getLegentCustomerBalanceReportList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getLegentCustomerBalanceReportList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex);
    }
    
    public List<ViewCustomerBalanceReport> getPershingCustomerBalance(long programId, long sourceInstitutionId, Date businessDate, String ibdCode, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getPershingCustomerBalance(databaseName, programId, sourceInstitutionId, businessDate, ibdCode, batchSize, batchIndex);    	
    }
    
    public List<ViewBankCustomerStatementReport> getPershingCustomerBalanceAtPE(long programId, long sourceInstitutionId, Date businessDate, String ibdCode, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getPershingCustomerBalanceAtPE(databaseName, programId, sourceInstitutionId, businessDate, ibdCode, batchSize, batchIndex);    	
    }

    public List<ViewBankCustomerStatementReport> getPershingCustomerBalanceAtPeriodStart(long programId, long sourceInstitutionId, Date businessDate, String ibdCode, int batchSize, int batchIndex){
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getPershingCustomerBalanceAtPeriodStart(databaseName, programId, sourceInstitutionId, businessDate, ibdCode, batchSize, batchIndex);
    }
    
    public List<ViewProductBalanceReport> getProductBalanceReportList(long programId, long sourceInstitutionId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getProductBalanceReportList(databaseName, programId, sourceInstitutionId, businessDate);
    }

    public List<ViewSourceInstitutionBalanceReport> getSourceInstitutionBalanceReportList(long programId, long sourceInstitutionId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getSourceInstitutionBalanceReportList(databaseName, programId, sourceInstitutionId, businessDate);
    }
    
    public List<ViewCustomerAverageBalance> getCustomerAverageBalancesReport(long programId, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCustomerAverageBalancesReport(databaseName, batchSize, batchIndex);
    }

    //============================================================================
	//
	// Confirm Writers
	//
	//============================================================================	
    public List<ViewAccountRegistrationError> getAccountRegistrationErrorView(long programId, long sourceInstitutionId, Date fileDate, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getAccountRegistrationErrorView(databaseName, programId, sourceInstitutionId, fileDate, batchSize, batchIndex );
    }

    public List<ViewCustomerConfirmReport> getBankConfirmReportList(long programId, long sourceInstitutionId, Date businessDate)
    {
	   	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getBankConfirmReportList(databaseName, programId, sourceInstitutionId, businessDate);    	
    }
    
    public List<ViewCustomerConfirmReport> getBETACustomerConfirmReportList(long programId, long sourceInstitutionId, Date businessDate)
    {
	   	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getBETACustomerConfirmReportList(databaseName, programId, sourceInstitutionId, businessDate);    	
    }
    
    public List<ViewCustomerConfirmReport> getCustomerConfirmReportList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
	   	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCustomerConfirmReportList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex);    	
    }

    public List<ViewCustomerConfirmReport> getUBSCustomerConfirmReportList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
	   	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getUBSCustomerConfirmReportList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex);    	
    }
    
    public List<ViewCustomerAccountTransaction> getCustomerTransactionList(long programId, long sourceInstitutionId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCustomerTransactionList(databaseName, programId, sourceInstitutionId, businessDate);
    }

    public List<ViewCustomerAccountTransaction> getCustomerTransactionPreviousDayLateActivityList(long programId, long sourceInstitutionId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCustomerTransactionPreviousDayLateActivityList(databaseName, programId, sourceInstitutionId);
    }

    public List<ViewRBCCustomerConfirms> getRBCCustomerConfirms(long programId, long sourceInstitutionId, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getRBCCustomerConfirms(databaseName, programId, sourceInstitutionId, batchSize, batchIndex);
    }

	//============================================================================
	//
	// Transaction Writers
	//
	//============================================================================	
	public List<ViewCustomerAccountTransaction> getTransactionDeletions(long programId, long sourceInstitutionId, Date businessDate) 
	{
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getTransactionDeletions(databaseName, programId, sourceInstitutionId, businessDate);
	}
    
    
	//============================================================================
    //
    // Consolidation and Forecast Writers
    //
	//============================================================================    
    public List<ViewConsolidationAccountBalances> getConsolidationAccountBalances(long programId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getConsolidationAccountBalances(databaseName);    	
    }

    public List<ViewConsolidationBanks> getConsolidationBanks(long programId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getConsolidationBanks(databaseName);    	
    }

    public int getConsolidationBanksCount(long programId)
    {
    	int count = 0;
    	List<ViewConsolidationBanks> list = this.getConsolidationBanks(programId);
    	ViewConsolidationBanks bank = list.get(0);
    	if(bank != null)
    	{
    		return bank.getBankCount();
    	}
    	return count;
    }
    
    public List<ViewConsolidationGroupBalances> getConsolidationGroupBalances(long programId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getConsolidationGroupBalances(databaseName);    	
    }
    
    public List<ViewForecastBalancesReport> getForecastBalancesReport(long programId, Date businessDate, boolean isWaterfall)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getForecastBalancesReport(databaseName, businessDate, isWaterfall);    	
    }

	public List<ViewForecastSummaryReport> getForecastSummaryReport(long programId)
	{
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getForecastSummaryReport(databaseName);    			
	}
	
	public String getForecastAndConsolidationWarningMessage(long programId)
	{
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getForecastAndConsolidationWarningMessage(databaseName, programId);    			
	}

	//============================================================================
    //
	// Fees and Revenue Writers
    //
	//============================================================================
    public List<ViewBalanceSummaryAndFeesByProduct> getBalanceSummaryAndFeesByProduct(Date date, long programId, long sourceInstitutionId)
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getBalanceSummaryAndFeesByProduct(programDatabaseName, date, programId, sourceInstitutionId);
    }
         
    public List<ViewDBTCAFees> getDBTCAFees(long sourceInstitutionId)
    {
    	return fileWriterDAO.getDBTCAFees(sourceInstitutionId);
    }
    
    public int getEffectiveSIPegAmount(long sourceInstitutionId, Date businessDate)
    {
    	return fileWriterDAO.getEffectiveSIPegAmount(sourceInstitutionId, businessDate);
    }

    public List<ViewRBCRevenue> getMonthlyAffiliatedBankRevenue(long programID, long sourceInstitution, Date balanceDate)
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programID);
    	return fileWriterDAO.getMonthlyAffiliatedBankRevenue(programDatabaseName, programID, sourceInstitution, balanceDate);
    }

    public List<ViewRBCRevenue> getMonthlyRevenue(long programID, long sourceInstitution, Date balanceDate)
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programID);
    	return fileWriterDAO.getMonthlyRevenue(programDatabaseName, programID, sourceInstitution, balanceDate);
    }

    public List<ViewPershingBankFees> getPershingBankFees(Date date)
    {
    	return fileWriterDAO.getPershingBankFees(date);
    }

	//============================================================================
	//
    // Initial Deposits Writers
    //
	//============================================================================
    public List<ViewCustomerAccountInitialDeposit> getCustomerAccountInitialDeposits(long programId, long sourceInstitutionId, Date startDate, Date endDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
		return fileWriterDAO.getCustomerAccountInitialDeposits(sourceInstitutionId, databaseName, startDate, endDate);
    }
    
	//============================================================================
    //
    // Statement Writers
    //
    //============================================================================
    public List<ViewBankCustomerStatementReport> getBankCustomerStatementReportList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getBankCustomerStatementReportList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex);
    }

    public List<ViewCustomerStatementReport> getBrokerStatementReportList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getBrokerStatementReportList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex);
    }
    
    public List<ViewCustomerStatementReport> getCustomerStatementReportList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex )
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getCustomerStatementReportList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex);    	
    }

    public List<ViewStatementContent> getStatementContents(long programID, long sourceInstitution, Date balanceDate)
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programID);
    	return fileWriterDAO.getStatementContents(programDatabaseName, programID, sourceInstitution, balanceDate);
    }

    public ViewStatementDates getStatementDates(long programId, Date businessDate)
    {
    	return fileWriterDAO.getStatementDates(programId, businessDate);
    }
    
    public List<ViewSungardCustomerStatementReport> getSungardCustomerStatementReportList(long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex )
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getSungardCustomerStatementReportList(databaseName, programId, sourceInstitutionId, businessDate, batchSize, batchIndex );    	
    }

	//============================================================================
    //
    // Wire Writers
    //
	//============================================================================
    public List<ViewWires> getWires(long programId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileWriterDAO.getWires(databaseName, programId, businessDate);    	
    }


    
}
