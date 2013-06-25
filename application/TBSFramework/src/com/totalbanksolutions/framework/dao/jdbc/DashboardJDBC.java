package com.totalbanksolutions.framework.dao.jdbc;

import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dao.DashboardDAO;
import com.totalbanksolutions.framework.dao.util.DatabaseHelper;
import com.totalbanksolutions.framework.dao.util.SQLParameters;
import com.totalbanksolutions.framework.model.database.view.DashboardAccountInfoBalanceView;
import com.totalbanksolutions.framework.model.database.view.DashboardAccountInfoBankBalanceView;
import com.totalbanksolutions.framework.model.database.view.DashboardAccountInfoTransactionView;
import com.totalbanksolutions.framework.model.database.view.DashboardBankBalancesDetailView;
import com.totalbanksolutions.framework.model.database.view.DashboardBankBalancesSummaryView;
import com.totalbanksolutions.framework.model.database.view.DashboardBankGeneralInfoDetailView;
import com.totalbanksolutions.framework.model.database.view.DashboardBankMonitorSummaryView;
import com.totalbanksolutions.framework.model.database.view.DashboardBankPDARatesView;
import com.totalbanksolutions.framework.model.database.view.DashboardContactInfoView;
import com.totalbanksolutions.framework.model.database.view.DashboardCustomerAccountGeneralInfoView;
import com.totalbanksolutions.framework.model.database.view.DashboardMoneyMovementDetailView;
import com.totalbanksolutions.framework.model.database.view.DashboardMoneyMovementSummaryView;
import com.totalbanksolutions.framework.model.database.view.DashboardProductBanksView;
import com.totalbanksolutions.framework.model.database.view.DashboardProductRatesView;
import com.totalbanksolutions.framework.model.database.view.DashboardProductTiersView;
import com.totalbanksolutions.framework.model.database.view.DashboardProductsSummaryView;
import com.totalbanksolutions.framework.model.database.view.DashboardStatusSummaryView;
import com.totalbanksolutions.framework.model.database.view.DashboardSweepActivitySummaryView;
import com.totalbanksolutions.framework.model.database.view.DashboardSweepActivityTransactionView;
import com.totalbanksolutions.framework.model.database.view.FeesByProductView;
import com.totalbanksolutions.framework.model.database.view.FeesByProgramBankView;


/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all dashboard related items
 * =================================================================================================
 */
public class DashboardJDBC implements DashboardDAO
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    private DataSource dataSource;
    private DatabaseHelper databaseHelper;
    
	public DashboardJDBC(DataSource ds)
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
    // Summary: Dashboard Status
    //
	//============================================================================
	public DashboardStatusSummaryView getDashboardStatusSummary(String databaseName, long programId, long sourceInstitutionId, Date businessDate)
    {
		DashboardStatusSummaryView t = new DashboardStatusSummaryView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetStatusSummary @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
    
    
	//============================================================================
	//
    // Summary: Sweep Activity
    //
	//============================================================================
	public DashboardSweepActivityTransactionView getDashboardSweepActivityTransactions(String databaseName, long programId, boolean isExceptions, Date businessDate)
    {
		DashboardSweepActivityTransactionView t = new DashboardSweepActivityTransactionView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetSweepActivityTransactions @Program_FK=?, @isExceptions=?, @BusinessDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(isExceptions);
		params.addValue(businessDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }

    
	//============================================================================
	//
    // Summary: Sweep Activity Transactions
    //
	//============================================================================
	public DashboardSweepActivitySummaryView getDashboardSweepActivitySummary(String databaseName, long programId, Date businessDate)
    {
		DashboardSweepActivitySummaryView t = new DashboardSweepActivitySummaryView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetSweepActivitySummary @Program_FK=?, @BusinessDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }

	
	//============================================================================
	//
    // Summary: Products
    //
	//============================================================================
	public DashboardProductsSummaryView getDashboardProductsSummary(String databaseName, long programId, Date businessDate, boolean isSummary, String productName)
    {
		if (productName == "")
			log.debug("getting Product Summary with all products");
		else 
			log.debug("getting Product Summary for product: " + productName);
		
		DashboardProductsSummaryView t = new DashboardProductsSummaryView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetProductsSummary @Program_FK=?, @BusinessDate=?, @IsSummary=?, @ProductName=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
    	if( isSummary )
    		params.addValue("1");
    	else 
    		params.addValue("0");
    	params.addValue(productName);
    	this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
    

	public DashboardProductRatesView getDashboardProductRatesSummary(String databaseName, long programId, Date businessDate, String productName)
    {		
		DashboardProductRatesView t = new DashboardProductRatesView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetProductRates @Program_FK=?, @BusinessDate=?, @ProductName=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
       	params.addValue(productName);
    	this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
    
	public DashboardProductTiersView getDashboardProductTiers(String databaseName, long programId, Date businessDate, String productName)
    {		
		DashboardProductTiersView t = new DashboardProductTiersView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetProductTiers @Program_FK=?, @BusinessDate=?, @ProductName=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
       	params.addValue(productName);
    	this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
	
	public DashboardProductBanksView getDashboardProductBanks(String databaseName, long programId, Date businessDate, String productName)
    {		
		DashboardProductBanksView t = new DashboardProductBanksView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetProductBanks @Program_FK=?, @BusinessDate=?, @ProductName=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
       	params.addValue(productName);
    	this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
	
	
	//============================================================================
	//
    // Summary: Money Movement ( Settlement )
    //
	//============================================================================
	public DashboardMoneyMovementSummaryView getDashboardMoneyMovementSummary(String databaseName, Date businessDate)
    {
		DashboardMoneyMovementSummaryView t = new DashboardMoneyMovementSummaryView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetMoneyMovementSummary @BusinessDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(businessDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
    
	//============================================================================
	//
    // Detail: Money Movement ( Settlement )
    //
	//============================================================================
	public DashboardMoneyMovementDetailView getDashboardMoneyMovementDetail(String databaseName, long programId, Date businessDate)
    {
		DashboardMoneyMovementDetailView t = new DashboardMoneyMovementDetailView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetMoneyMovementDetail @Program_FK=?, @BusinessDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
    
	//============================================================================
	//
    // Summary: Bank Balances
    //
	//============================================================================
	public DashboardBankBalancesSummaryView getBankBalancesSummary(String databaseName, Date businessDate)
    {
		DashboardBankBalancesSummaryView t = new DashboardBankBalancesSummaryView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetBankBalancesSummary @BusinessDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(businessDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
	

	//============================================================================
	//
    // Detail: Bank Balances 
    //
	//============================================================================
	public DashboardBankBalancesDetailView getDashboardBankBalancesDetail(String databaseName, long programId, Date businessDate)
    {
		DashboardBankBalancesDetailView t = new DashboardBankBalancesDetailView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetBankBalancesDetail @Program_FK=?, @BusinessDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
	
	//============================================================================
	//
    // Summary: Account Info Summary
    //
	//============================================================================
	@Override
	public DashboardCustomerAccountGeneralInfoView getAccountInfoSummary(String databaseName, String accountId, Date businessDate)
	{
		DashboardCustomerAccountGeneralInfoView t = new DashboardCustomerAccountGeneralInfoView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetAccountInfoSummary @AccountID=?, @BusinessDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(accountId);
		params.addValue(businessDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
	}
    
	//============================================================================
	//
    // Bank Dashboard: Bank Monitor Info 
    //
	//============================================================================
	public DashboardBankMonitorSummaryView getBankMonitorSummary(String bankName)
    {
		DashboardBankMonitorSummaryView t = new DashboardBankMonitorSummaryView();
		String sql = "EXEC TBS_Common..p_Dashboard_GetBankMonitorSummary @BankName=?";
		SQLParameters params = new SQLParameters();
		params.addValue(bankName);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }


	//============================================================================
	//
    // Bank Dashboard: General Info 
    //
	//============================================================================
	public DashboardBankGeneralInfoDetailView getDashboardBankGeneralInfo(long programId, String bankName)
	{
		DashboardBankGeneralInfoDetailView t = new DashboardBankGeneralInfoDetailView();
		String sql = "EXEC TBS_Common..p_Dashboard_GetBankGeneralInfo @Program_FK=?, @BankName=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(bankName);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
	}

	public DashboardBankPDARatesView getDashboardBankPDARatesInfo(long programId, String bankName, Date balanceDate)
	{
		DashboardBankPDARatesView t = new DashboardBankPDARatesView();
		String sql = "EXEC TBS_Common..p_Dashboard_GetPDARateInfo @Program_FK=?, @BankName=?, @BalanceDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(bankName);
		params.addValue(balanceDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
	}
	
	public DashboardContactInfoView getDashboardContactInfo(String bankName)
	{
		DashboardContactInfoView t = new DashboardContactInfoView();
		String sql = "EXEC TBS_Common..p_Dashboard_GetContactInfo @BankName=?";
		SQLParameters params = new SQLParameters();
		params.addValue(bankName);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
	}
	
	public boolean isAllocationComplete(long programId)
	{
    	String sql = "SELECT TBS_Common.dbo.f_checklist_IsAllocationComplete(" + programId + ")";
		boolean b = this.databaseHelper.queryForBoolean(sql, null);
		return b;
	}

	public boolean isInterestComplete(long programId)
	{
    	String sql = "SELECT TBS_Common.dbo.f_checklist_IsInterestComplete(" + programId + ")";
		boolean b = this.databaseHelper.queryForBoolean(sql, null);
		return b;
	}

	public boolean isSettlementComplete(long programId)
	{
    	String sql = "SELECT TBS_Common.dbo.f_checklist_IsSettlementComplete(" + programId + ")";
		boolean b = this.databaseHelper.queryForBoolean(sql, null);
		return b;
	}

	public boolean areAllTranFilesLoaded(long programId, boolean isLateProcessing)
	{
		String lateProcessingBool = "";
    	if( isLateProcessing )
    		lateProcessingBool = "1";
    	else 
    		lateProcessingBool = "0";

    	String sql = "SELECT TBS_Common.dbo.f_checklist_AreAllTranFilesLoaded(" + programId + "," + lateProcessingBool + ")";
		boolean b = this.databaseHelper.queryForBoolean(sql, null);
		return b;
	}

    //============================================================================
	//
	// Fees
	//
	//============================================================================
    public FeesByProgramBankView getFeesByProgramBank( String databaseName, long programId, Date businessDate )
    {
    	FeesByProgramBankView t = new FeesByProgramBankView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetFeesByProgramBank @Program_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }

    public FeesByProductView getFeesByProduct( String databaseName, long programId, Date businessDate )
    {
    	FeesByProductView t = new FeesByProductView();
		String sql = "EXEC " + databaseName + "..p_Dashboard_GetFeesByProduct @Program_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
    
    //============================================================================
  	//
  	// Account Info
  	//
  	//============================================================================
    public DashboardAccountInfoBankBalanceView getDashboardAccountBankBalancesInfo(String databaseName, long programId, String accountId)
    {
    	log.debug("### JDBC ACCOUNT ID [" + accountId + "] ###");
    	DashboardAccountInfoBankBalanceView t = new DashboardAccountInfoBankBalanceView();
		String sql = "EXEC " + databaseName + "..p_w_n_MaintainAccountInfo @Action='BankJava', @AccountNumber=?";
		SQLParameters params = new SQLParameters();
		
		params.addValue(accountId);
		this.databaseHelper.queryForModelTable(sql, params, t);
		//log.debug("### JDBC ACCOUNT ID [" + t + "] ###");
		return t;
    }
    public DashboardAccountInfoTransactionView getDashboardAccountTransactionInfo(String databaseName, long programId, String accountId)
    {
    	log.debug("### JDBC ACCOUNT ID [" + accountId + "] ###");
    	DashboardAccountInfoTransactionView t = new DashboardAccountInfoTransactionView();
		String sql = "EXEC " + databaseName + "..p_Get_AccountTransactionsInfo @Program_PK=?, @AccountNo=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(accountId);
		this.databaseHelper.queryForModelTable(sql, params, t);
		//log.debug("### JDBC ACCOUNT ID [" + t + "] ###");
		return t;
    }
    public DashboardAccountInfoBalanceView getDashboardAccountBalanceInfo(String databaseName, long programId, String accountId)
    {
    	log.debug("### JDBC ACCOUNT ID BALANCE VIEW [" + accountId + "] ###");
    	DashboardAccountInfoBalanceView t = new DashboardAccountInfoBalanceView();
		String sql = "EXEC " + databaseName + "..p_w_n_Get_AccountBalancesInfo @Program_PK=?, @AccountNo=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(accountId);
		this.databaseHelper.queryForModelTable(sql, params, t);
		//log.debug("### JDBC ACCOUNT ID [" + t + "] ###");
		return t;
    }
    
}
