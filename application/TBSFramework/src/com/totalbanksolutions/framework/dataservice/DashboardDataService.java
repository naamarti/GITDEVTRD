package com.totalbanksolutions.framework.dataservice;



import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.cache.CacheManager;
import com.totalbanksolutions.framework.dao.DashboardDAO;
import com.totalbanksolutions.framework.dao.jdbc.DashboardJDBC;
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
public class DashboardDataService 
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog( DashboardDataService.class );
    private DashboardDAO dashboardDAO = null;
    private CacheManager cacheManager = null;
    private DataService ds = null;

    public DashboardDataService ( DataSource dataSource, CacheManager cacheManager, DataService ds )
    {
    	this.dashboardDAO = new DashboardJDBC(dataSource);
    	this.cacheManager = cacheManager;
    	this.ds = ds;
    }
    
	public void close()
	{
		this.dashboardDAO.close();
		this.dashboardDAO = null;
		this.cacheManager = null;
		this.ds = null;
	}

    //============================================================================
	//
	// Dashboard
	//
	//============================================================================
    public DashboardStatusSummaryView getDashboardStatusSummary(long programId, long sourceInstitutionId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardStatusSummary(databaseName, programId, sourceInstitutionId, businessDate);
    }
    
    public DashboardSweepActivitySummaryView getDashboardSweepActivitySummary(long programId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardSweepActivitySummary(databaseName, programId, businessDate);
    }
	
    public DashboardSweepActivityTransactionView getDashboardSweepActivityTransactions(long programId, boolean isExceptions, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardSweepActivityTransactions(databaseName, programId, isExceptions, businessDate);
    }
    
    public DashboardProductsSummaryView getDashboardProductsSummary(long programId, Date businessDate, boolean isSummary, String productName)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardProductsSummary(databaseName, programId, businessDate, isSummary, productName);
    }

    public DashboardProductRatesView getDashboardProductRates(long programId, Date businessDate, String productName)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardProductRatesSummary(databaseName, programId, businessDate, productName);
    }

    public DashboardProductTiersView getDashboardProductTiers(long programId, Date businessDate, String productName)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardProductTiers(databaseName, programId, businessDate, productName);
    }
    
    public DashboardProductBanksView getDashboardProductBanks(long programId, Date businessDate, String productName)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardProductBanks(databaseName, programId, businessDate, productName);
    }
    
    public DashboardMoneyMovementSummaryView getDashboardMoneyMovementSummary(long programId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardMoneyMovementSummary(databaseName, businessDate);
    }

    public DashboardMoneyMovementDetailView getDashboardMoneyMovementDetail(long programId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardMoneyMovementDetail(databaseName, programId, businessDate);
    }

    public DashboardBankBalancesDetailView getDashboardBankBalancesDetail(long programId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardBankBalancesDetail(databaseName, programId, businessDate);
    }
    
    public DashboardBankBalancesSummaryView getBankBalancesSummary(long programId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getBankBalancesSummary(databaseName, businessDate);
    }
    
	public DashboardCustomerAccountGeneralInfoView getDashboardCustomerAccountGenInfo(long programId, String accountId, Date businessDate) 
	{
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getAccountInfoSummary(databaseName, accountId, businessDate);
	}	
	
    public DashboardBankGeneralInfoDetailView getDashboardBankGeneralInfo(long programId, String bankName)
    {
    	return dashboardDAO.getDashboardBankGeneralInfo(programId, bankName);
    }
    
	public DashboardBankMonitorSummaryView getBankMonitorSummary(String bankName)
	{
    	return dashboardDAO.getBankMonitorSummary(bankName);
	}
	public DashboardBankPDARatesView getDashboardBankPDARatesInfo(long programId, String bankName, Date balanceDate)
	{
    	return dashboardDAO.getDashboardBankPDARatesInfo(programId, bankName, balanceDate);
	}
	public DashboardContactInfoView getDashboardContactInfo(String bankName)
	{
    	return dashboardDAO.getDashboardContactInfo(bankName);
	}
	public boolean isAllocationComplete(long programId)
	{
    	return dashboardDAO.isAllocationComplete(programId);
	}
	public boolean isInterestComplete(long programId)
	{
    	return dashboardDAO.isInterestComplete(programId);
	}
	public boolean isSettlementComplete(long programId)
	{
    	return dashboardDAO.isSettlementComplete(programId);
	}
	
	public boolean areAllTranFilesLoaded(long programId, boolean isLateProcessing)
	{
    	return dashboardDAO.areAllTranFilesLoaded(programId, isLateProcessing);
	}

    
    //============================================================================
	//
	// Fees
	//
	//============================================================================
    public FeesByProgramBankView getFeesByProgramBank( long programId, Date businessDate )
    {
    	String databaseName = this.ds.util.getProgramDatabaseName( programId );
    	return dashboardDAO.getFeesByProgramBank( databaseName, programId, businessDate );
    }

    public FeesByProductView getFeesByProduct( long programId, Date businessDate )
    {
    	String databaseName = this.ds.util.getProgramDatabaseName( programId );
    	return dashboardDAO.getFeesByProduct( databaseName, programId, businessDate );
    }
    
    //============================================================================
	//
	// Account Info
	//
	//============================================================================
    
    public DashboardAccountInfoBankBalanceView getDashboardAccountBankBalancesInfo(long programId, String accountId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardAccountBankBalancesInfo(databaseName, programId, accountId);
    }
    public DashboardAccountInfoTransactionView getDashboardAccountTransactionInfo(long programId, String accountId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardAccountTransactionInfo(databaseName, programId, accountId);
    }
    public DashboardAccountInfoBalanceView getDashboardAccountBalanceInfo(long programId, String accountId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return dashboardDAO.getDashboardAccountBalanceInfo(databaseName, programId, accountId);
    }
}

