package com.totalbanksolutions.framework.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.totalbanksolutions.framework.bean.database.table.StageAccount;
import com.totalbanksolutions.framework.bean.database.table.StageAccountNew;
import com.totalbanksolutions.framework.bean.database.table.StageAccountV2;
import com.totalbanksolutions.framework.bean.database.table.StageBankOptOut;
import com.totalbanksolutions.framework.bean.database.table.StageTransaction;
import com.totalbanksolutions.framework.bean.database.table.StageTransactionsFromBalances;
import com.totalbanksolutions.framework.bean.database.view.ViewAcctBalancesForTransLoader;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountBalance;
import com.totalbanksolutions.framework.bean.database.view.ViewOptOutTotals;
import com.totalbanksolutions.framework.bean.database.view.ViewProgramAccountTypeMapping;
import com.totalbanksolutions.framework.bean.database.view.ViewTransactionTotals;
import com.totalbanksolutions.framework.dao.util.DAO;
import com.totalbanksolutions.framework.dao.util.DataBatchManager;

/**
 * @author vcatrini
 */
public interface FileLoaderDAO extends DAO
{

    public void close();
	
    //============================================================================
    //
    // Loaders - Shared
    //
	//============================================================================
	public void deleteStageBatch(String databaseName, long sourceInstitutionId);
    public ViewOptOutTotals getOptOutTotalsForBatch(String databaseName, long batchId);
    public ViewTransactionTotals getTransactionTotalsForBatch(String databaseName, long batchId);

    //============================================================================
    //
    // Account Loaders
    //
	//============================================================================
    public Map<String,Long> getCustomerAccountProductMap(String databaseName);
    public Map<String,ViewProgramAccountTypeMapping> getProgramAccountTypeMap(long programId, long sourceInstitutionId);    
    public int getSSNGeneratorRoot( String databaseName ); 
    public void updateSSNGeneratorRoot( String databaseName, int newSSNRoot );
    public void clearTefraAccounts( String databaseName );

	//------------------------------------
	// StageAccounts
	//------------------------------------
	public void addStageAccount(DataBatchManager batchManager, StageAccount item);    
	public DataBatchManager getStageAccountBatchManager(String databaseName);
	public int saveStageAccountBalances(String databaseName, long batchId);
	public int saveStageAccounts(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId);
    
	//------------------------------------
	// StageAccountsNew
	//------------------------------------
	public void addStageAccountNew(DataBatchManager batchManager, StageAccountNew item);
	public DataBatchManager getStageAccountNewBatchManager(String databaseName);
	public int saveStageAccountNew(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId);
	public int saveStageAccountNewAccountNumber(long programId, long sourceInstitutionId, String databaseName, long batchId);
	public int saveStageAccountNewProductCodes(String databaseName, long programId, Date businessDate, long batchId);
	public void saveStageAccountNewSetupErrors( String programDatabaseName, long batchId );
	
	//------------------------------------
	// StageAccountV2
	//------------------------------------
	public void addStageAccountV2(DataBatchManager batchManager, StageAccountV2 item);
	public DataBatchManager getStageAccountV2BatchManager(String databaseName);
	public int saveStageAccountV2(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId, boolean isManualFile, 
			boolean isUpdateAccountType, boolean isUpdateBranchCode, boolean isUpdateOfficeCode, boolean isUpdatePayoutType, boolean isUpdateProduct, 
			boolean isUpdateRepCode, boolean isUpdateRegLine1, boolean isUpdateRegLine2, boolean isUpdateRegLine3, 
			boolean isUpdateRegLine4, boolean isUpdateRegLine5, boolean isUpdateRegLine6, boolean isUpdateRegLine7, boolean isUpdateRegLine8,
			boolean isUpdateTaxID, boolean isUpdateTaxID2, boolean isUpdateTefraWithheld);
	
	
	//============================================================================
	//
	// Bank OptOut Loaders
	//
	//============================================================================
	public void addStageBankOptOut(DataBatchManager batchManager, StageBankOptOut item);
	public DataBatchManager getStageBankOptOutAddBatchManager(String databaseName);
	public int saveStageBankOptOuts(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId, boolean isManualAppendingFile, boolean isUseTaxId);

	//============================================================================
    //
    // Transaction Loaders
    //
	//============================================================================
    public List<ViewCustomerAccountBalance> getCustomerAccountBalanceList(String databaseName, long programId, long sourceInstitutionId, Date businessDate);
    public Map<String, ViewAcctBalancesForTransLoader> getCustomerAccountBalanceForTranLoadMap(String databaseName, long sourceInstitutionId);
    public Double getCustomerTransactionPreviousDayOffsetAmount(long programId, long sourceInstitutionId, String databaseName);

	//------------------------------------
    // StageTransactions
	//------------------------------------
	public void addStageTransaction(DataBatchManager batchManager, StageTransaction item);
	public DataBatchManager getStageTransactionBatchManager(String databaseName);
	public int saveStageTransactions(String databaseName, long programId, long sourceInstitutionId, Date businessDate, boolean isLateProcessing, boolean isManualProcessing, long batchId);
	public int validateAndSaveStageTransactions(String databaseName, long programId, long sourceInstitutionId, Date businessDate, boolean isLateProcessing, long batchId);

	//------------------------------------
    // StageTransactionsFromBalances - SIS
	//------------------------------------
	public void addStageTransactionsFromBalances(DataBatchManager batchManager, StageTransactionsFromBalances item);
	public DataBatchManager getStageTransactionsFromBalancesBatchManager(String databaseName);
	public int saveStageTransactionsFromBalances(String databaseName, long programId, long sourceInstitutionId, Date businessDate, boolean isLateProcessing, boolean isManualProcessing, long batchId);
	
}
