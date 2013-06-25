package com.totalbanksolutions.framework.dao;

import java.util.Date;

import com.totalbanksolutions.framework.dao.util.DAO;
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
public interface DashboardDAO extends DAO
{

    public void close();
	
	//============================================================================
	//
    // Dashboard
    //
	//============================================================================     
    public DashboardStatusSummaryView getDashboardStatusSummary(String databaseName, long programId, long sourceInstitutionId, Date businessDate);
    public DashboardSweepActivitySummaryView getDashboardSweepActivitySummary(String databaseName, long programId, Date businessDate);
    public DashboardSweepActivityTransactionView getDashboardSweepActivityTransactions(String databaseName, long programId, boolean isExceptions, Date businessDate);
	public DashboardProductsSummaryView getDashboardProductsSummary(String databaseName, long programId, Date businessDate, boolean isSummary, String productName);
	public DashboardProductRatesView getDashboardProductRatesSummary(String databaseName, long programId, Date businessDate, String productName);
	public DashboardProductTiersView getDashboardProductTiers(String databaseName, long programId, Date businessDate, String productName);
	public DashboardProductBanksView getDashboardProductBanks(String databaseName, long programId, Date businessDate, String productName);
	public DashboardMoneyMovementSummaryView getDashboardMoneyMovementSummary(String databaseName, Date businessDate);
	public DashboardMoneyMovementDetailView getDashboardMoneyMovementDetail(String databaseName, long programId, Date businessDate);
	public DashboardBankBalancesDetailView getDashboardBankBalancesDetail(String databaseName, long programId, Date businessDate);
	public DashboardBankBalancesSummaryView getBankBalancesSummary(String databaseName, Date businessDate);
	public DashboardCustomerAccountGeneralInfoView getAccountInfoSummary(String databaseName, String accountId, Date businessDate);
	public DashboardBankMonitorSummaryView getBankMonitorSummary(String bankName);
	public DashboardBankGeneralInfoDetailView getDashboardBankGeneralInfo(long programId, String bankName);
	public DashboardBankPDARatesView getDashboardBankPDARatesInfo(long programId, String bankName, Date balanceDate);
	public DashboardContactInfoView getDashboardContactInfo(String bankName);
	public boolean isAllocationComplete(long programId);
	public boolean isInterestComplete(long programId);
	public boolean isSettlementComplete(long programId);
	public boolean areAllTranFilesLoaded(long programId, boolean isLateProcessing);


    //============================================================================
	//
	// Fees
	//
	//============================================================================
    public FeesByProgramBankView getFeesByProgramBank( String databaseName, long programId, Date businessDate );
    public FeesByProductView getFeesByProduct( String databaseName, long programId, Date businessDate );
  //============================================================================
  	//
  	// Account Info
  	//
  	//============================================================================
    public DashboardAccountInfoBankBalanceView getDashboardAccountBankBalancesInfo(String databaseName, long programId, String accountId);
    public DashboardAccountInfoTransactionView getDashboardAccountTransactionInfo(String databaseName, long programId, String accountId);
    public DashboardAccountInfoBalanceView getDashboardAccountBalanceInfo(String databaseName, long programId, String accountId);
	
    
    
}
