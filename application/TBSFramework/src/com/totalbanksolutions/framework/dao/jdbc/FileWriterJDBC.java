package com.totalbanksolutions.framework.dao.jdbc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.CustomerAccount;
import com.totalbanksolutions.framework.bean.database.table.CustomerAccountSetupErrors;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessDate;
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
import com.totalbanksolutions.framework.dao.FileWriterDAO;
import com.totalbanksolutions.framework.dao.util.DatabaseHelper;
import com.totalbanksolutions.framework.dao.util.SQLParameters;

/**
 * Created on May-19-2005.
 * @author vcatrini
 * 
 * Modified:	Apr-18-12 NAM #1538: Added batching of resultset
 * 			 	09 Jul 2012 DM #1717: File writing - Optimize for Davidson program
 *              20 Sep 2012 VC #1834: Add Warning(s) that appear on checklist, to the Forecast and Consolidation reports 
 * 
 */
public class FileWriterJDBC implements FileWriterDAO
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    private final String encryptionKey = "TBSEnCrYpTi0N_K3y_S3cUr1ty";

    private DataSource dataSource;
    private DatabaseHelper databaseHelper;
    
	public FileWriterJDBC(DataSource ds)
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
    // Writers - Shared
    //
	//============================================================================
	public List<ViewCustomerAccountBalance> getCustomerAccountBalanceList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetCustomerBalances @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerAccountBalance());
    }

    public Map<String,ViewCustomerAccount> getCustomerAccountsMap(long sourceInstitutionId, String databaseName)
    {
    	Map<String,ViewCustomerAccount> dataMap = new HashMap<String,ViewCustomerAccount>();
		String sql = "EXEC " + databaseName + "..p_j_GetCustomerAccounts @SourceInstitution_FK=?, @EncryptionKey=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(sourceInstitutionId);
		params.addValue(this.encryptionKey);
		List<ViewCustomerAccount> dataList = this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerAccount());
		for(ViewCustomerAccount item : dataList)
		{
			String accountNumber = item.getAccountNumber().toUpperCase();
			dataMap.put(accountNumber, item);
		}
		return dataMap;
    }

    //============================================================================
	//
    // 1099 Writers
    //
	//============================================================================	
    public List<ViewCustomer1099Report> getCustomer1099ReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetCustomer1099Report @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomer1099Report());
    }

    //============================================================================
	//
    // Account Registration Errors Writers
	//
    //============================================================================	
    public List<CustomerAccountSetupErrors> getCustomerAccountSetupErrorsList(String databaseName, long programId, long sourceInstitutionId, Date fileDate, boolean isLateProcessing )
    {
		StringBuffer sql = new StringBuffer("SELECT * ")
	       .append( "FROM " + databaseName + ".." + CustomerAccountSetupErrors.TABLE_NAME + " WITH (NOLOCK) ") 
	       .append( "WHERE Program_FK=? " )
	       .append( "AND SourceInstitution_FK=? " )
	       .append( "AND FileDate=? " );
    	// sweep account files are processed only in late processing.  Manuals are entered during normal processing hours
    	if( isLateProcessing )
    	{
		       sql.append( "AND IsManual=0");
    	}
    	else 
    	{
		       sql.append( "AND IsManual=1");
    	}
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(fileDate);
		return this.databaseHelper.queryForList(sql.toString(), params, new CustomerAccountSetupErrors());
    }

	//============================================================================
	//
    // Accrued Interest Writers
    //
	//============================================================================
    public List<ViewCustomerAccruedInterest> getCustomerAccruedInterest(String programDatabaseName, long sourceInstitutionId, Date businessDate)
    {
    	String sql = "EXEC " + programDatabaseName + "..p_j_CIRCLE_GetCustomerInterest @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();		
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerAccruedInterest());
    }

    public List<ViewCustomerAccountInterest> getCustomerAccountAccruedInterestList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetCustomerAccountInterest @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerAccountInterest());
    }
    
    public List<ViewSungardInterestTransactions> getSungardInterestTransactions(String databaseName, long programId, long sourceInstitutionId, Date businessDate)
    {
    	String sql = "EXEC " + databaseName + "..p_j_Sungard_GetInterestTransactions @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewSungardInterestTransactions());
    }
    
	//============================================================================
	//
    // Balance Writers
	//
    //============================================================================
    public List<ViewCustomerAccountBalance> getAccountBalance(String databaseName,long programID, long sourceInstitution, Date balanceDate)
	{
		String sql = "SELECT b.AccountNumber,a.Balance, c.Name as ProductName, a.InterestAccrued_PTD, a.InterestPaid " 
		+ " FROM " + databaseName + ".." + ViewCustomerAccountBalance.TABLE_NAME + " a WITH (NOLOCK),"
		+ databaseName + "..CustomerAccounts b WITH (NOLOCK), TBS_Common..Products c WITH (NOLOCK)  "
		+ " WHERE a.Program_FK = ? and  a.SourceInstitution_FK = ? and a.BalanceDate = ? and a.CustomerAccount_FK = b.CustomerAccount_PK and b.Product_FK = c.Product_PK  ";
		//+ " WHERE a.Program_FK = 20 and  a.SourceInstitution_FK = 11 and a.BalanceDate = '03/13/09' and a.CustomerAccount_FK = b.CustomerAccount_PK and b.Product_FK = c.Product_PK and b.AccountNumber in ('9290001899','9290010999') ";
		log.debug("SQL = "+sql);
		//and b.AccountNumber in ('9290001899','9290010999')
		SQLParameters params = new SQLParameters();
		params.addValue(programID);
		params.addValue(sourceInstitution);
		params.addValue(balanceDate);
		return this.databaseHelper.queryForList(sql, params, new ViewCustomerAccountBalance());
	}

    public List<ViewBankBalanceReport> getBankBalanceReportList(String databaseName, long sourceInstitutionId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetBankBalancesReport @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewBankBalanceReport());
    }

    public List<ViewCombinedBalanceReport> getCombinedBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetCombinedBalancesReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCombinedBalanceReport());
    }

    public List<ViewCustomerBalanceReport> getCustomerBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetCustomerBalancesReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerBalanceReport());
    }
    
    public List<ViewCustomerBankBalanceReport> getCustomerBankBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetCustomerBankBalancesReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerBankBalanceReport());
    }
    
    public List<ViewCustomerNOWBalanceReport> getCustomerNOWBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_RBC_GetCustomerNOWBalancesReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @EncryptionKey=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(this.encryptionKey);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerNOWBalanceReport());
    }

    public List<ViewDailyBankBalancesByProduct> getDailyBankBalancesByProduct( String databaseName, long sourceInstitutionId, Date balanceDate )
    {
    	String sql = "EXEC " + databaseName + "..p_rpt_DailyBankBalanceSummaryByProduct @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(sourceInstitutionId);
		params.addValue(balanceDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewDailyBankBalancesByProduct());
    }
    
    public List<ViewDepositBanksUsedInPeriod> getDepositBanksUsedInPeriod( String databaseName, long sourceInstitutionId, Date balanceDate )
    {
    	String sql = "EXEC " + databaseName + "..p_rpt_DepositBanksUsedInCurrentPeriod @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(sourceInstitutionId);
		params.addValue(balanceDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewDepositBanksUsedInPeriod());
    
    }

    public List<ViewCustomerStatementReport> getLegentCustomerBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetLegentCustomerBalancesReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerStatementReport());
    }

    public List<ViewCustomerBalanceReport> getPershingCustomerBalance(String databaseName, long programId, long sourceInstitutionId, Date businessDate, String ibdCode, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_Pershing_GetCustomerBalancesReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @IBDCode=?, @BatchSize =?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(ibdCode);
		params.addValue(batchSize);
		params.addValue(batchIndex);		
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerBalanceReport());
    }
    
    public List<ViewBankCustomerStatementReport> getPershingCustomerBalanceAtPE(String databaseName, long programId, long sourceInstitutionId, Date businessDate, String ibdCode, int batchSize, int batchIndex)
    {    
		String sql = "EXEC " + databaseName + "..p_j_Pershing_GetCustomerBalancesReport_PE @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @IBDCode=?, @BatchSize =?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(ibdCode);		
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewBankCustomerStatementReport());
    }

    public List<ViewBankCustomerStatementReport> getPershingCustomerBalanceAtPeriodStart(String databaseName, long programId, long sourceInstitutionId, Date businessDate, String ibdCode, int batchSize, int batchIndex)
    {    
		String sql = "EXEC " + databaseName + "..p_j_Pershing_GetCustomerBalancesReport_PeriodStart @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @IBDCode=?, @BatchSize =?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(ibdCode);		
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewBankCustomerStatementReport());
    }

    public List<ViewProductBalanceReport> getProductBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetProductBalancesReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewProductBalanceReport());
    }

    public List<ViewSourceInstitutionBalanceReport> getSourceInstitutionBalanceReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetSourceInstitutionBalancesReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewSourceInstitutionBalanceReport());
    }

    public List<ViewCustomerAverageBalance> getCustomerAverageBalancesReport(String databaseName, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetCustomerAverageBalancesReport @BatchSize =?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerAverageBalance());
    }
    
    //============================================================================
	//
    // Confirm Writers
    //
	//============================================================================
    public List<ViewAccountRegistrationError> getAccountRegistrationErrorView( String databaseName, long programId, long sourceInstitutionId, Date fileDate, int batchSize, int batchIndex )
    {
		String sql = "EXEC " + databaseName + "..p_j_GetAccountRegistrationErrors @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(fileDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewAccountRegistrationError());
    }
    
    public List<ViewCustomerConfirmReport> getBankConfirmReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetBankConfirmsReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerConfirmReport());
    }

	public List<ViewCustomerConfirmReport> getBETACustomerConfirmReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetBETAConfirmsReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerConfirmReport());
    }
	
	public List<ViewCustomerConfirmReport> getCustomerConfirmReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetConfirmsReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerConfirmReport());
    }
	
	public List<ViewCustomerConfirmReport> getUBSCustomerConfirmReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetUBSConfirmsReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerConfirmReport());
    }
	
    public List<ViewCustomerAccountTransaction> getCustomerTransactionList(String databaseName, long programId, long sourceInstitutionId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetCustomerTransactions @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerAccountTransaction());
    }
    
    public List<ViewCustomerAccountTransaction> getCustomerTransactionPreviousDayLateActivityList(String databaseName, long programId, long sourceInstitutionId)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetPreviousDayLateActivity @Program_FK=?, @SourceInstitution_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerAccountTransaction());
    }
    
    public List<ViewRBCCustomerConfirms> getRBCCustomerConfirms(String databaseName, long programId, long sourceInstitutionId, int batchSize, int batchIndex){
    	
    	String sql = "EXEC " + databaseName + "..p_j_RBC_GetConfirmsReport @Program_FK=?, @SourceInstitution_FK=?, @BatchSize=?, @BatchIndex=?  ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewRBCCustomerConfirms());
    }
    
  
    //============================================================================
    //
    // Transaction Writers
    //
	//============================================================================	
	public List<ViewCustomerAccountTransaction> getTransactionDeletions(String databaseName, long programId, long sourceInstitutionId, Date businessDate)
	{    	
		String sql = "EXEC " + databaseName + "..p_j_GetTransactionsToDelete @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerAccountTransaction());
	}
    

    //============================================================================
    //
    // Consolidation and Forecast Writers
    //
	//============================================================================	
    public List<ViewConsolidationAccountBalances> getConsolidationAccountBalances(String databaseName)
    {
		String sql = "EXEC " + databaseName + "..p_rpt_ConsolidationAccountBalances ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewConsolidationAccountBalances());
    }

    public List<ViewConsolidationBanks> getConsolidationBanks(String databaseName)
    {
		String sql = "EXEC " + databaseName + "..p_rpt_ConsolidationBanks ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewConsolidationBanks());
    }

    public List<ViewConsolidationGroupBalances> getConsolidationGroupBalances(String databaseName)
    {
		String sql = "EXEC " + databaseName + "..p_rpt_ConsolidationGroupBalances ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewConsolidationGroupBalances());
    }

	public List<ViewForecastBalancesReport> getForecastBalancesReport(String databaseName, Date businessDate, boolean isWaterfall)
    {
		String sql = "EXEC " + databaseName + "..p_rpt_ForecastBalances @BusinessDate=?, @IsWaterfall=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(businessDate);
		params.addValue(isWaterfall);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewForecastBalancesReport());
    }

	public List<ViewForecastSummaryReport> getForecastSummaryReport(String databaseName)
    {
		String sql = "EXEC " + databaseName + "..p_rpt_ForecastSummary ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewForecastSummaryReport());
    }
	
	public String getForecastAndConsolidationWarningMessage(String databaseName, long programId)
	{
		String sql = "EXEC " + databaseName + "..p_j_GetForecastAndConsolidationWarning @Program_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		return this.databaseHelper.executeStoredProcedureForString(sql, params);
	}
	
    //============================================================================
    //
    // Fees and Revenue Writers
    //
	//============================================================================	
    public List<ViewBalanceSummaryAndFeesByProduct> getBalanceSummaryAndFeesByProduct(String databaseName, Date businessDate, long programID, long sourceInstitutionID)
    {
		String sql = "EXEC " + databaseName + "..p_rpt_BalanceAndFeeSummaryByProduct @BusinessDate=?, @Program_FK=?, @SourceInstitution_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(businessDate);
		params.addValue(programID);
		params.addValue(sourceInstitutionID);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewBalanceSummaryAndFeesByProduct());
    }

    public List<ViewDBTCAFees> getDBTCAFees(long sourceInstitutionId)
    {
		String sql = "EXEC TBS_Program_DBTCA..p_j_DBTCA_GetFees @SourceInstitution_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(sourceInstitutionId);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewDBTCAFees());
    }
    
    public int getEffectiveSIPegAmount(long sourceInstitutionId, Date businessDate)
    {
		String sql = "SELECT PegAmount " 
		+ "FROM TBS_Common..SourceInstitution_FeeTiers WITH (NOLOCK) "
		+ "WHERE Fee_FK IN ( "
		+ " SELECT TOP 1 Fee_PK "
		+ " FROM TBS_Common..SourceInstitution_Fees "
		+ " WHERE SourceInstitution_FK = ? "
		+ " AND EffectiveDate <= ? "
		+ " ORDER BY EffectiveDate DESC )";
		
		SQLParameters params = new SQLParameters();
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		Long pegAmount = this.databaseHelper.queryForLong(sql, params);
		return pegAmount.intValue();
    }
    
    public List<ViewRBCRevenue> getMonthlyAffiliatedBankRevenue(String databaseName,long programID, long sourceInstitution, Date balanceDate){
    	String sql = "EXEC " + databaseName + "..p_j_GetMonthlyAffiliatedBankRevenueFile @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(sourceInstitution);
		params.addValue(balanceDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewRBCRevenue());
    }

    public List<ViewRBCRevenue> getMonthlyRevenue(String databaseName,long programID, long sourceInstitution, Date balanceDate){
    	String sql = "EXEC " + databaseName + "..p_j_GetMonthlyRevenueFile @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(sourceInstitution);
		params.addValue(balanceDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewRBCRevenue());
    }
    
    public List<ViewPershingBankFees> getPershingBankFees(Date businessDate)
    {
		String sql = "EXEC TBS_Program_Pershing..p_j_PERSHING_GetFees @BalanceDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewPershingBankFees());
    }

    //============================================================================
	//
    // Initial Deposits Writers
    //
	//============================================================================	
    public List<ViewCustomerAccountInitialDeposit> getCustomerAccountInitialDeposits(long sourceInstitutionId, String databaseName, Date startDate, Date endDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetInitialDepositAccounts @SourceInstitution_FK=?, @EncryptionKey=?, @StartDate=?, @EndDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(sourceInstitutionId);
		params.addValue(this.encryptionKey);
		params.addValue(startDate);
		params.addValue(endDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerAccountInitialDeposit());
    }

    //============================================================================
	//
    // Statement Writers
    //
	//============================================================================	
    public List<ViewBankCustomerStatementReport> getBankCustomerStatementReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetBankCustomerStatementReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewBankCustomerStatementReport());
    }

    public List<ViewCustomerStatementReport> getBrokerStatementReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetBrokerStatementReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerStatementReport());
    }

    public List<ViewCustomerStatementReport> getCustomerStatementReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetCustomerStatementReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerStatementReport());
    }

    public List<ViewStatementContent>getStatementContents(String programDatabaseName, long programID, long sourceInstitution, Date balanceDate){
    	String sql = "EXEC " + programDatabaseName + "..p_j_GetContentOfStatementFile @Program_FK =?, @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programID);
		params.addValue(sourceInstitution);
		params.addValue(balanceDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewStatementContent());
    }
    
    public ViewStatementDates getStatementDates(long programId, Date businessDate)
	{
    	String sql = "EXEC " + DailyProcessDate.DATABASE_NAME + "..p_j_GetStatementDates @Program_FK=?, @BusinessDate=?, @IsReturnAsResultset=1 ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		ViewStatementDates obj = (ViewStatementDates)this.databaseHelper.executeStoredProcedureForObject(sql, params, new ViewStatementDates());
		return obj;
	}
    
    public List<ViewSungardCustomerStatementReport> getSungardCustomerStatementReportList(String databaseName, long programId, long sourceInstitutionId, Date businessDate, int batchSize, int batchIndex )
    {
		String sql = "EXEC " + databaseName + "..p_j_GetSungardCustomerStatementReport @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchSize=?, @BatchIndex=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchSize);
		params.addValue(batchIndex);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewSungardCustomerStatementReport());
    }
    
    //============================================================================
	//
    // Wire File Writers
    //
	//============================================================================	
    public List<ViewWires> getWires(String databaseName, long programId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + ".." + "p_rpt_GetWireOut @Program_FK=?, @BusinessDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewWires());
    }

}
