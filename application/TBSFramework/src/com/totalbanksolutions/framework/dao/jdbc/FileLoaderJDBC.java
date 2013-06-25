package com.totalbanksolutions.framework.dao.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.CustomerAccount;
import com.totalbanksolutions.framework.bean.database.table.CustomerAccountBalance;
import com.totalbanksolutions.framework.bean.database.table.ProgramAccountTypeMapping;
import com.totalbanksolutions.framework.bean.database.table.SSNGenerator;
import com.totalbanksolutions.framework.bean.database.table.StageAccount;
import com.totalbanksolutions.framework.bean.database.table.StageAccountNew;
import com.totalbanksolutions.framework.bean.database.table.StageAccountV2;
import com.totalbanksolutions.framework.bean.database.table.StageBankOptOut;
import com.totalbanksolutions.framework.bean.database.table.StageTransaction;
import com.totalbanksolutions.framework.bean.database.table.StageTransactionsFromBalances;
import com.totalbanksolutions.framework.bean.database.view.ViewAcctBalancesForTransLoader;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountBalance;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountProduct;
import com.totalbanksolutions.framework.bean.database.view.ViewOptOutTotals;
import com.totalbanksolutions.framework.bean.database.view.ViewProgramAccountTypeMapping;
import com.totalbanksolutions.framework.bean.database.view.ViewTransactionTotals;
import com.totalbanksolutions.framework.dao.FileLoaderDAO;
import com.totalbanksolutions.framework.dao.util.DataBatchManager;
import com.totalbanksolutions.framework.dao.util.DatabaseHelper;
import com.totalbanksolutions.framework.dao.util.SQLParameters;

/**
 * Created on May-19-2005.
 * @author vcatrini
 */
public class FileLoaderJDBC implements FileLoaderDAO
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    private DataSource dataSource;
    private DatabaseHelper databaseHelper;
    
	private final String encryptionKey = "TBSEnCrYpTi0N_K3y_S3cUr1ty";

	public FileLoaderJDBC(DataSource ds)
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
    // Loaders - Shared
    //
	//============================================================================
	public void deleteStageBatch(String databaseName, long sourceInstitutionId)
	{
		String sql = "EXEC " + databaseName + "..p_j_DeleteStageBatch @SourceInstitution_FK = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(sourceInstitutionId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);		
	}

	public ViewOptOutTotals getOptOutTotalsForBatch(String databaseName, long batchId)
    {
    	String sql = "EXEC " + databaseName + "..p_j_GetOptOutTotalsForBatch @BatchNumber_FK=?";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		return (ViewOptOutTotals)this.databaseHelper.executeStoredProcedureForObject(sql, params, new ViewOptOutTotals());
    }
    
    public ViewTransactionTotals getTransactionTotalsForBatch(String databaseName, long batchId)
    {
    	String sql = "EXEC " + databaseName + "..p_j_GetTransactionTotalsForBatch @BatchNumber_FK=?";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		return (ViewTransactionTotals)this.databaseHelper.executeStoredProcedureForObject(sql, params, new ViewTransactionTotals());
    }
    
    //============================================================================
	//
    // Account Loaders
    //
	//============================================================================
    public Map<String,Long> getCustomerAccountProductMap(String databaseName)
    {
		Map<String,Long> dataMap = new HashMap<String,Long>();
		String sql = "SELECT AccountNumber , Product_FK "
		+ " FROM " + databaseName + ".." + ViewCustomerAccountProduct.TABLE_NAME + " WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		List<ViewCustomerAccountProduct> dataList = this.databaseHelper.queryForList(sql, params, new ViewCustomerAccountProduct());
		for(ViewCustomerAccountProduct item : dataList)
		{
			String accountNumber = item.getAccountNumber().toUpperCase();
			Long productId = item.getProductId();
			dataMap.put(accountNumber, productId);
		}
		return dataMap;
    }

    public Map<String,ViewProgramAccountTypeMapping> getProgramAccountTypeMap(long programId, long sourceInstitutionId)
	{
		Map<String,ViewProgramAccountTypeMapping> dataMap = new HashMap<String,ViewProgramAccountTypeMapping>();
		List<ViewProgramAccountTypeMapping> dataList = new ArrayList<ViewProgramAccountTypeMapping>();
		String sql = "SELECT a.*,b.AccountType FROM " + ProgramAccountTypeMapping.DATABASE_TABLE_NAME + " a WITH (NOLOCK) "
		+ " join CustomerAccount_Types b WITH (NOLOCK) on a.CustomerAccountType_FK = b.CustomerAccount_Type_PK"
		+ " WHERE Program_FK = ? AND SourceInstitution_FK = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		dataList = this.databaseHelper.queryForList(sql, params, new ViewProgramAccountTypeMapping());
		for(ViewProgramAccountTypeMapping item : dataList)
		{
			String key = item.getSourceAccountType().toUpperCase();
			dataMap.put(key, item);
		}		
		return dataMap;
	}
    
    public int getSSNGeneratorRoot( String databaseName ) 
	{
    	Long ssnRoot = null;
		String sql = "SELECT SSNRoot " 
		+ "FROM " + databaseName + ".." + SSNGenerator.TABLE_NAME + " WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		ssnRoot = this.databaseHelper.queryForLong(sql, params);
		return ssnRoot.intValue();
	}
    
    public void updateSSNGeneratorRoot( String databaseName, int newRootValue )
    {
    	log.debug("### In updateSSNGeneratorRoot, nextGeneratedSSNSequence is : " + newRootValue + " ###");
		String sql = "UPDATE " + databaseName + ".." + SSNGenerator.TABLE_NAME + " "
			+ "SET SSNRoot = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(newRootValue);
		this.databaseHelper.executeUpdate(sql, params);
    }
    
    public void clearTefraAccounts(String databaseName)
    {
		String sql = "UPDATE " + databaseName + ".." + CustomerAccount.TABLE_NAME + " SET IsTefraWithheld = 0";
		SQLParameters params = new SQLParameters();
		this.databaseHelper.executeUpdate(sql, params);
    }
    
	//------------------------------------
	// StageAccounts
	//------------------------------------
	public void addStageAccount(DataBatchManager batchManager, StageAccount item)
	{
		SQLParameters params = item.getSQLInsertParameters();
		batchManager.addBatch(params);
	}
	
	public DataBatchManager getStageAccountBatchManager(String databaseName)
	{
		StageAccount item = new StageAccount();
		String sql = item.getSQLInsertStatementNew(databaseName);
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		batchManager.prepareSQL(sql);
		return batchManager;
	}

	public int saveStageAccounts(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId)
	{
		int updateCount = 0;
		String sql = "SELECT count(*) FROM " + databaseName + ".." + StageAccount.TABLE_NAME + " WITH (NOLOCK) WHERE BatchNumber_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		updateCount = this.databaseHelper.queryForInteger(sql, params);

		sql = "EXEC " +  databaseName + "..p_j_SaveIncomingAccounts @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchNumber_FK=?, @EncryptionKey=? ";
		params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchId);
		params.addValue(this.encryptionKey);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);		
		return updateCount;
	}
    
	public int saveStageAccountBalances(String databaseName, long batchId)
	{
		int updateCount = 0;
		String sql = "SELECT count(*) FROM " + databaseName + ".." + StageAccount.TABLE_NAME + " WITH (NOLOCK) WHERE BatchNumber_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		updateCount = this.databaseHelper.queryForInteger(sql, params);
		log.debug("updateCount = "+updateCount);
		log.debug ("key: "+ this.encryptionKey);
		sql = "EXEC " +  databaseName + "..p_j_SaveIncomingAccountBalances @BatchNumber_FK=? ";
		params = new SQLParameters();
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);		
		return updateCount;
	}

	//------------------------------------
	// StageAccountsNew
	//------------------------------------
	public void addStageAccountNew(DataBatchManager batchManager, StageAccountNew item)
	{
		SQLParameters params = item.getSQLInsertParameters();
		batchManager.addBatch(params);
	}
	
	public DataBatchManager getStageAccountNewBatchManager(String databaseName)
	{
		StageAccountNew item = new StageAccountNew();
		String sql = item.getSQLInsertStatementNew(databaseName);
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		batchManager.prepareSQL(sql);
		return batchManager;
	}

	public int saveStageAccountNew(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId)
	{
		int updateCount = 0;
		String sql = "SELECT count(*) FROM " + databaseName + ".." + StageAccountNew.TABLE_NAME + " WITH (NOLOCK) WHERE BatchNumber_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		updateCount = this.databaseHelper.queryForInteger(sql, params);

		sql = "EXEC " +  databaseName + "..p_j_SaveIncomingAccountsNew @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchNumber_FK=?, @EncryptionKey=? ";
		params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchId);
		params.addValue(this.encryptionKey);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);		
		return updateCount;
	}
    
    public int saveStageAccountNewAccountNumber(long programId, long sourceInstitutionId, String databaseName, long batchId)
    {
    	int updateCount = 0;
		String sql = "SELECT count(*) FROM " + databaseName + ".." + StageAccountNew.TABLE_NAME + " WITH (NOLOCK) WHERE BatchNumber_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		updateCount = this.databaseHelper.queryForInteger(sql, params);
    	
    	sql = "EXEC " + databaseName + "..p_j_BBNT_UpdateAccountNumber @Program_FK=?,@SourceInstitution_FK = ?, @BatchNumber_FK = ?";
		params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);		
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
		
		return updateCount;
    }

    public int saveStageAccountNewProductCodes(String databaseName, long programId, Date businessDate, long batchId)
	{
		int updateCount = 0;
		String sql = "SELECT count(*) FROM " + databaseName + ".." + StageAccountNew.TABLE_NAME + " WITH (NOLOCK) WHERE BatchNumber_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		updateCount = this.databaseHelper.queryForInteger(sql, params);

		sql = "EXEC " +  databaseName + "..p_j_SaveIncomingProductCodes @Program_FK=?, @BusinessDate=?, @BatchNumber_FK=?, @EncryptionKey=? ";
		params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		params.addValue(batchId);
		params.addValue(this.encryptionKey);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);		
		return updateCount;
	}
	
	public void saveStageAccountNewSetupErrors( String programDatabaseName, long batchId )
	{
		String sql = "EXEC " +  programDatabaseName + "..p_j_SaveAccountSetupErrors @BatchNumber_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);		
	}

	//------------------------------------
	// StageAccountV2
	//------------------------------------
	public void addStageAccountV2(DataBatchManager batchManager, StageAccountV2 item)
	{
		SQLParameters params = item.getSQLInsertParameters();
		batchManager.addBatch(params);
	}
	
	public DataBatchManager getStageAccountV2BatchManager(String databaseName)
	{
		StageAccountV2 item = new StageAccountV2();
		String sql = item.getSQLInsertStatementNew(databaseName);
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		batchManager.prepareSQL(sql);
		return batchManager;
	}

	public int saveStageAccountV2(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId, boolean isManualFile, 
								  boolean isUpdateAccountType, boolean isUpdateBranchCode, boolean isUpdateOfficeCode, boolean isUpdatePayoutType, boolean isUpdateProduct, 
								  boolean isUpdateRepCode, boolean isUpdateRegLine1, boolean isUpdateRegLine2, 
			                      boolean isUpdateRegLine3, boolean isUpdateRegLine4, boolean isUpdateRegLine5, boolean isUpdateRegLine6, boolean isUpdateRegLine7, 
			                      boolean isUpdateRegLine8, boolean isUpdateTaxID, boolean isUpdateTaxID2, boolean isUpdateTefraWithheld)
	{
		int updateCount = 0;
		String sql = "SELECT count(*) FROM " + databaseName + ".." + StageAccountV2.TABLE_NAME + " WITH (NOLOCK) WHERE BatchNumber_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		updateCount = this.databaseHelper.queryForInteger(sql, params);

		sql = "EXEC " +  databaseName + "..p_j_SaveIncomingAccountsV2_Main @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchNumber_FK=?, @EncryptionKey=?, @IsManualFile=?, @IsUpdateAccountType=?, "
		+ "@IsUpdateBranchCode=?, @IsUpdateOfficeCode=?, @IsUpdatePayoutType=?, @IsUpdateProduct=?, @IsUpdateRepCode=?, @IsUpdateRegLine1=?, @IsUpdateRegLine2=?, "
		+ "@IsUpdateRegLine3=?, @IsUpdateRegLine4=?, @IsUpdateRegLine5=?, @IsUpdateRegLine6=?, @IsUpdateRegLine7=?, @IsUpdateRegLine8=?, "
		+ "@IsUpdateTaxID=?, @IsUpdateTaxID2=?, @IsUpdateTefraWithheld=?";

		params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchId);
		params.addValue(this.encryptionKey);
		params.addValue(isManualFile);
		params.addValue(isUpdateAccountType);
		params.addValue(isUpdateBranchCode);
		params.addValue(isUpdateOfficeCode);
		params.addValue(isUpdatePayoutType);
		params.addValue(isUpdateProduct);
		params.addValue(isUpdateRepCode);
		params.addValue(isUpdateRegLine1);
		params.addValue(isUpdateRegLine2);
		params.addValue(isUpdateRegLine3);
		params.addValue(isUpdateRegLine4);
		params.addValue(isUpdateRegLine5);
		params.addValue(isUpdateRegLine6);
		params.addValue(isUpdateRegLine7);
		params.addValue(isUpdateRegLine8);
		params.addValue(isUpdateTaxID);
		params.addValue(isUpdateTaxID2);
		params.addValue(isUpdateTefraWithheld);
		
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);		
		return updateCount;
	}
	
	//============================================================================
	//
	// Bank OptOut Loaders
	//
	//============================================================================
	public void addStageBankOptOut(DataBatchManager batchManager, StageBankOptOut item)
	{
		SQLParameters params = item.getSQLInsertParameters();
		batchManager.addBatch(params);
	}
	
	public DataBatchManager getStageBankOptOutAddBatchManager(String databaseName)
	{
		StageBankOptOut item = new StageBankOptOut();
		String sql = item.getSQLInsertStatementNew(databaseName);		
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		batchManager.prepareSQL(sql);
		return batchManager;
	}
	
	public int saveStageBankOptOuts(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId, boolean isManualAppendingFile, boolean isUseTaxId)
	{
		int updateCount = 0;
		String sql = "SELECT count(*) FROM " + databaseName + ".." + StageBankOptOut.TABLE_NAME + " WITH (NOLOCK) WHERE BatchNumber_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		updateCount = this.databaseHelper.queryForInteger(sql, params);

		sql = "EXEC " +  databaseName + "..p_j_SaveIncomingBankOptOuts @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @isManualAppendingFile =?, @isUseTaxId =?,@BatchNumber_FK=?";
		params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(isManualAppendingFile);
		params.addValue(isUseTaxId);
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
		return updateCount;
	}

	//============================================================================
    //
    // Transaction Loaders
    //
	//============================================================================
	public List<ViewCustomerAccountBalance> getCustomerAccountBalanceList(String databaseName, long programId, long sourceInstitutionId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetCustomerBalances @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewCustomerAccountBalance());
    }

    public Map<String, ViewAcctBalancesForTransLoader> getCustomerAccountBalanceForTranLoadMap(String databaseName, long sourceInstitutionId)
    {
		String sql = "SELECT AccountNumber, PendingBalance " +
					 "FROM " + databaseName + ".." + CustomerAccountBalance.TABLE_NAME + " B WITH (NOLOCK) " +
					 "JOIN " + databaseName + ".." + CustomerAccount.TABLE_NAME + " A WITH (NOLOCK) " +
					 "ON A.CustomerAccount_PK = B.CustomerAccount_FK " + 
					 "WHERE B.PendingBalance > 0";
		
		SQLParameters params = new SQLParameters();
		List<ViewAcctBalancesForTransLoader> dataList = this.databaseHelper.queryForList(sql, params, new ViewAcctBalancesForTransLoader());

		Map<String, ViewAcctBalancesForTransLoader> dataMap = new HashMap<String, ViewAcctBalancesForTransLoader>();
		for(ViewAcctBalancesForTransLoader item : dataList)
		{
			String accountNumber = item.getAccountNumber().toUpperCase();
			dataMap.put(accountNumber, item);
		}
		return dataMap;
    }
    
    public Double getCustomerTransactionPreviousDayOffsetAmount(long programId, long sourceInstitutionId, String databaseName)
    {
    	String sql = "EXEC " + databaseName + "..p_j_GetPreviousDayOffsetAmount @Program_FK=?, @SourceInstitution_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		Double amount = this.databaseHelper.executeStoredProcedureForDouble(sql, params);
		if(amount == null)
		{
			return new Double(0);
		}
		return amount;
    }

	//------------------------------------
	// StageTransactions
	//------------------------------------
	public void addStageTransaction(DataBatchManager batchManager, StageTransaction tran)
	{
		SQLParameters params = tran.getSQLInsertParameters();
		batchManager.addBatch(params);
	}
	
	public DataBatchManager getStageTransactionBatchManager(String databaseName)
	{
		StageTransaction tran = new StageTransaction();
		String sql = tran.getSQLInsertStatementNew(databaseName);
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		batchManager.prepareSQL(sql);
		return batchManager;		
	}

	public int saveStageTransactions(String databaseName, long programId, long sourceInstitutionId, Date businessDate, boolean isLateProcessing, boolean isManualProcessing, long batchId)
	{
		int updateCount = 0;
//		String sql = "SELECT count(*) FROM " + databaseName + ".." + StageTransaction.TABLE_NAME + " WHERE BatchNumber_FK = ? ";
//		SQLParameters params = new SQLParameters();
//		params.addValue(batchId);
//		updateCount = this.databaseHelper.queryForInteger(sql, params);

		String sql = "EXEC " +  databaseName + "..p_j_SaveIncomingTransactions_Main @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @IsLateProcessing=?, @IsManualProcessing=?, @BatchNumber_FK=?, @EncryptionKey=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(isLateProcessing);
		params.addValue(isManualProcessing);
		params.addValue(batchId);
		params.addValue(this.encryptionKey);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
		return updateCount;
	}
	
	public int validateAndSaveStageTransactions(String databaseName, long programId, long sourceInstitutionId, Date businessDate, boolean isLateProcessing, long batchId)
	{
		int updateCount = 0;
//		String sql = "SELECT count(*) FROM " + databaseName + ".." + StageTransaction.TABLE_NAME + " WHERE BatchNumber_FK = ? ";
//		SQLParameters params = new SQLParameters();
//		params.addValue(batchId);
//		updateCount = this.databaseHelper.queryForInteger(sql, params);

		String sql = "EXEC " +  databaseName + "..p_j_SaveIncomingTransactions_PershingLateValidation @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchNumber_FK=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);		
		params.addValue(batchId);		
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
		return updateCount;
	}

	//------------------------------------
	// StageTransactionsFromBalances - SIS
	//------------------------------------
	public void addStageTransactionsFromBalances(DataBatchManager batchManager, StageTransactionsFromBalances tran)
	{
		SQLParameters params = tran.getSQLInsertParameters();
		batchManager.addBatch(params);
	}

	public DataBatchManager getStageTransactionsFromBalancesBatchManager(String databaseName)
	{
		StageTransactionsFromBalances tran = new StageTransactionsFromBalances();
		String sql = tran.getSQLInsertStatementNew(databaseName);
		DataBatchManager batchManager = this.databaseHelper.getBatchManager();
		batchManager.prepareSQL(sql);
		return batchManager;		
	}
	
	public int saveStageTransactionsFromBalances(String databaseName, long programId, long sourceInstitutionId, Date businessDate, boolean isLateProcessing, boolean isManualProcessing, long batchId)
	{

		// first, derive the transactions, and then add them to the StageTransactions table
		String sql = "EXEC " +  databaseName + "..p_j_SaveIncomingTransactions_CalculateTransactionsFromBalances @Program_FK=?, @BusinessDate=?, @BatchNumber_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);

		int updateCount = 0;
		sql = "SELECT count(*) FROM " + databaseName + ".." + StageTransaction.TABLE_NAME + " WITH (NOLOCK) WHERE BatchNumber_FK = ? ";
		params = new SQLParameters();
		params.addValue(batchId);
		updateCount = this.databaseHelper.queryForInteger(sql, params);

		// now, call the original sproc, which works off of the StageTransactions table and updates balances, etc
		sql = "EXEC " +  databaseName + "..p_j_SaveIncomingTransactions_Main @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @IsLateProcessing=?, @IsManualProcessing=?, @BatchNumber_FK=?, @EncryptionKey=? ";
		params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(isLateProcessing);
		params.addValue(isManualProcessing);
		params.addValue(batchId);
		params.addValue(this.encryptionKey);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
	
		return updateCount;
	}
	
}
