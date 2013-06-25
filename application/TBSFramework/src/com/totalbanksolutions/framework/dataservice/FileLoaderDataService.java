package com.totalbanksolutions.framework.dataservice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.Product;
import com.totalbanksolutions.framework.bean.database.table.StageAccount;
import com.totalbanksolutions.framework.bean.database.table.StageAccountNew;
import com.totalbanksolutions.framework.bean.database.table.StageAccountV2;
import com.totalbanksolutions.framework.bean.database.table.StageBankOptOut;
import com.totalbanksolutions.framework.bean.database.table.StageTransaction;
import com.totalbanksolutions.framework.bean.database.table.StageTransactionsFromBalances;
import com.totalbanksolutions.framework.bean.database.view.ViewAcctBalancesForTransLoader;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountBalance;
import com.totalbanksolutions.framework.bean.database.view.ViewOptOutTotals;
import com.totalbanksolutions.framework.bean.database.view.ViewProductMapping;
import com.totalbanksolutions.framework.bean.database.view.ViewProgramAccountTypeMapping;
import com.totalbanksolutions.framework.bean.database.view.ViewTransactionTotals;
import com.totalbanksolutions.framework.cache.CacheDataRetrievalMethod;
import com.totalbanksolutions.framework.cache.CacheDataRetrievalParams;
import com.totalbanksolutions.framework.cache.CacheManager;
import com.totalbanksolutions.framework.dao.FileLoaderDAO;
import com.totalbanksolutions.framework.dao.jdbc.FileLoaderJDBC;
import com.totalbanksolutions.framework.dao.util.DataBatchManager;
import com.totalbanksolutions.framework.dataservice.util.DataCacheType;

public class FileLoaderDataService 
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());
    private FileLoaderDAO fileLoaderDAO = null;
    private CacheManager cacheManager = null;
    private DataService ds = null;

    public FileLoaderDataService (DataSource dataSource, CacheManager cacheManager, DataService ds)
    {
    	this.fileLoaderDAO = new FileLoaderJDBC(dataSource);
    	this.cacheManager = cacheManager;
    	this.ds = ds;
    }
    
	public void close()
	{
		this.fileLoaderDAO.close();
		this.fileLoaderDAO = null;
		this.cacheManager = null;
		this.ds = null;
	}

	//============================================================================
	//
	// Loaders - Shared
	//
	//============================================================================
    public void deleteStageBatch(long programId, long sourceInstitutionId)
	{
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		fileLoaderDAO.deleteStageBatch(programDatabaseName, sourceInstitutionId);
	}

    public ViewOptOutTotals getOptOutTotalsForBatch(long programId, long batchId)
    {
	   	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileLoaderDAO.getOptOutTotalsForBatch(databaseName, batchId);
    }

    public ViewTransactionTotals getTransactionTotalsForBatch(long programId, long batchId)
    {
	   	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileLoaderDAO.getTransactionTotalsForBatch(databaseName, batchId);
    }

	//============================================================================
	//
	// Account Loaders
	//
	//============================================================================
	// TODO: VC 25 May 2011: 
	//       This method is only used by DavidsonTransactionsFileLoader. Instead it should model after other trans loaders
	// 	     that don't get a product in their file (see p_j_SaveIncomingTransactions).
	public Long getCustomerAccountProductId(long programId, String accountNumber)
    {
    	Map<String,Long> accountProductMap = this.getCustomerAccountProductMap(programId);
		Long productId = accountProductMap.get(accountNumber.toUpperCase());
    	return productId;
    }
	
    @SuppressWarnings("unchecked")
	public Map<String,Long> getCustomerAccountProductMap(long programId)
    {
    	final String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return fileLoaderDAO.getCustomerAccountProductMap(programDatabaseName);
    		}
    	};
    	CacheDataRetrievalParams params = new CacheDataRetrievalParams();
    	params.addValue("programDatabaseName", programDatabaseName);
    	return (Map<String,Long>)cacheManager.get(DataCacheType.CUSTOMER_ACCOUNT_PRODUCT_MAP, retrieveMethod, params);
    }

    public Product getDefaultProduct(long programId, long sourceInstitutionId)
    {
    	Product defaultProduct = null;
    	List<Product> items = this.ds.util.getProductList(programId, sourceInstitutionId);
		for(Product item : items)
		{
			boolean isDefaultProduct = item.isSourceInstitutionDefault();
			if(isDefaultProduct)
			{
				defaultProduct = item;
				break;
			}
		}
		return defaultProduct;
    }
    
    public void clearTefraAccounts(long programId)
    {
    	final String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		fileLoaderDAO.clearTefraAccounts(programDatabaseName);
    }
    
	/**
     * @param sourceInstitutionId
     * @param branchCode
     * @param sourceProductCode
     * @return This method will return the internal TBS product mapping for the supplied branch code and source product code.
     * The following hierarchical lookup logic will be used to determine a valid TBS product mapping:
     * 
     * 1.	Look for an exact match with Branch_From, Branch_To, and SourceProductCode (SPC)
     * 2.	If no match found, then look for an exact branch match AND a DEFAULT SPC match. 
     * 3.	If no match found, then look for a range match AND a SPC match. 
     * 4.	If no match found, then look for a DEFAULT range AND a SPC match.
     * 5.	If no match found, then look for a range match, and DEFAULT SPC
     * 6.	If no match found, look for the Source Institution default, which will have a DEFAULT range and a SPC of DEFAULT as well
     * 
     * NOTE: It must be done in this hierarchical manner, as there can be multiple entries that can provide a valid match for a given 
     * branch and sourceproductcode, but the more specific matches are preferred.
     */
    public Product getProductByBranchAndSourceProductCode( long programId, long sourceInstitutionId, String branchCode, String sourceProductCode )
    {
    	Product mappedProduct = null;
    	
    	List<ViewProductMapping> items = this.ds.util.getProductMappingList(sourceInstitutionId);

    	// If the branch passed in is NULL or "DEFAULT", then we simply look for a SourceProductCode match.  If a SPC match
    	// is not found, then we look for the SI default
    	if ( branchCode == null || branchCode.equalsIgnoreCase("DEFAULT"))
    	{
    		
        	for(ViewProductMapping item : items)
    		{
    			String branchFromMapping = item.getBranchFromName();
    			String branchToMapping = item.getBranchToName();
    			String spcMapping = item.getSourceProductCode();

    			if ( branchFromMapping.equalsIgnoreCase("DEFAULT") &&
    			     branchToMapping.equalsIgnoreCase("DEFAULT") &&
    				 spcMapping.equalsIgnoreCase(sourceProductCode) )
    			{
					long productId = item.getProductId();
					mappedProduct = this.ds.util.getProductByProductId(programId, sourceInstitutionId, productId);
					break;
    			}
    		}
    		
	    	if(mappedProduct == null)
	    	{
	        	for(ViewProductMapping item : items)
	    		{
	    			String branchFromMapping = item.getBranchFromName();
	    			String branchToMapping = item.getBranchToName();
	    			String spcMapping = item.getSourceProductCode();
	
	    			if ( branchFromMapping.equalsIgnoreCase("DEFAULT") &&
	       			     branchToMapping.equalsIgnoreCase("DEFAULT") &&
	    				 spcMapping.equalsIgnoreCase("DEFAULT") )
	    			{
						long productId = item.getProductId();
						mappedProduct = this.ds.util.getProductByProductId(programId, sourceInstitutionId, productId);
						break;
	    			}
	    		}
	    	}
    		
    	}
    	else
    	{
    		
	    	// 1.  look for exact branch code and source product code match 
	    	for(ViewProductMapping item : items)
			{
				String branchFromMapping = item.getBranchFromName();
				String branchToMapping = item.getBranchToName();
				String spcMapping = item.getSourceProductCode();
	
				if ( branchFromMapping.equalsIgnoreCase(branchCode) &&
					 branchToMapping.equalsIgnoreCase(branchCode) &&
					 spcMapping.equalsIgnoreCase(sourceProductCode) )
				{
					long productId = item.getProductId();
					mappedProduct = this.ds.util.getProductByProductId(programId, sourceInstitutionId, productId);
					break;
				}
			}

	    	// 2.  If no match found, then look for an exact branch match AND a DEFAULT SPC match
	    	if(mappedProduct == null)
	    	{
		    	for(ViewProductMapping item : items)
				{
					String branchFromMapping = item.getBranchFromName();
					String branchToMapping = item.getBranchToName();
					String spcMapping = item.getSourceProductCode();
		
					if ( branchFromMapping.equalsIgnoreCase(branchCode) &&
						 branchToMapping.equalsIgnoreCase(branchCode) &&
						 spcMapping.equalsIgnoreCase("DEFAULT") )
					{
						long productId = item.getProductId();
						mappedProduct = this.ds.util.getProductByProductId(programId, sourceInstitutionId, productId);
						break;
					}
				}
	    	}
	    	
	    	// 3.  If no match found, then look for a range match AND a SPC match 
	    	if(mappedProduct == null)
	    	{
	        	for(ViewProductMapping item : items)
	    		{
	    			String branchFromMapping = item.getBranchFromName();
	    			String branchToMapping = item.getBranchToName();
	    			String spcMapping = item.getSourceProductCode();
	
	    			if ( branchFromMapping.compareTo(branchCode) <= 0 &&
	    			     branchToMapping.compareTo(branchCode) >= 0 &&
	    				 spcMapping.equalsIgnoreCase(sourceProductCode) )
	    			{
						long productId = item.getProductId();
						mappedProduct = this.ds.util.getProductByProductId(programId, sourceInstitutionId, productId);
						break;
	    			}
	    		}
	    	}
	
	    	// 4.  If no match found, then look for a DEFAULT range AND a SPC match
	    	if(mappedProduct == null)
	    	{
	        	for(ViewProductMapping item : items)
	    		{
	    			String branchFromMapping = item.getBranchFromName();
	    			String branchToMapping = item.getBranchToName();
	    			String spcMapping = item.getSourceProductCode();
	
	    			if ( branchFromMapping.equalsIgnoreCase("DEFAULT") &&
	    			     branchToMapping.equalsIgnoreCase("DEFAULT") &&
	    				 spcMapping.equalsIgnoreCase(sourceProductCode) )
	    			{
						long productId = item.getProductId();
						mappedProduct = this.ds.util.getProductByProductId(programId, sourceInstitutionId, productId);
						break;
	    			}
	    		}
	    	}
	
	    	// 5.  If no match found, then look for a range match, and DEFAULT SPC
	    	if(mappedProduct == null)
	    	{
	        	for(ViewProductMapping item : items)
	    		{
	    			String branchFromMapping = item.getBranchFromName();
	    			String branchToMapping = item.getBranchToName();
	    			String spcMapping = item.getSourceProductCode();
	
	    			if ( branchFromMapping.compareTo(branchCode) <= 0 &&
	       			     branchToMapping.compareTo(branchCode) >= 0 &&
	    				 spcMapping.equalsIgnoreCase("DEFAULT") )
	    			{
						long productId = item.getProductId();
						mappedProduct = this.ds.util.getProductByProductId(programId, sourceInstitutionId, productId);
						break;
	    			}
	    		}
	    	}
	    	
	    	// 6.  If no match found, look for the Source Institution default, which will have a DEFAULT range 
	    	// and a SPC of DEFAULT as well
	    	if(mappedProduct == null)
	    	{
	        	for(ViewProductMapping item : items)
	    		{
	    			String branchFromMapping = item.getBranchFromName();
	    			String branchToMapping = item.getBranchToName();
	    			String spcMapping = item.getSourceProductCode();
	
	    			if ( branchFromMapping.equalsIgnoreCase("DEFAULT") &&
	       			     branchToMapping.equalsIgnoreCase("DEFAULT") &&
	    				 spcMapping.equalsIgnoreCase("DEFAULT") )
	    			{
						long productId = item.getProductId();
						mappedProduct = this.ds.util.getProductByProductId(programId, sourceInstitutionId, productId);
						break;
	    			}
	    		}
	    	}
    	}
    	
    	return mappedProduct;
    }
    
    @SuppressWarnings("unchecked")
	public Map<String,ViewProgramAccountTypeMapping> getProgramAccountTypeMap(final long programId, final long sourceInstitutionId)
    {
       	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return fileLoaderDAO.getProgramAccountTypeMap(programId, sourceInstitutionId); 
    		}
    	};
    	return (Map<String,ViewProgramAccountTypeMapping>)cacheManager.get(DataCacheType.PROGRAM_ACCOUNT_TYPE_MAP, retrieveMethod);
     }

    public int getSSNGeneratorRoot( long programId )
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileLoaderDAO.getSSNGeneratorRoot( programDatabaseName );
    }

    public void updateSSNGeneratorRoot( long programId, int newSSNRoot )
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
    	fileLoaderDAO.updateSSNGeneratorRoot( programDatabaseName, newSSNRoot );
    }

	//------------------------------------
	// StageAccounts
	//------------------------------------
	public void addStageAccount(DataBatchManager batchManager, StageAccount item)
	{
		fileLoaderDAO.addStageAccount(batchManager, item);
	}
    
    public DataBatchManager getStageAccountBatchManager(long programId)
	{
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		return fileLoaderDAO.getStageAccountBatchManager(programDatabaseName);		
	}

    public int saveStageAccountBalances(long programId, long batchId)
	{
		String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		int updateCount = fileLoaderDAO.saveStageAccountBalances(programDatabaseName, batchId);
		return updateCount;
	}

    public int saveStageAccounts(long programId, long sourceInstitutionId, Date businessDate, long batchId)
	{
		String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		int updateCount = fileLoaderDAO.saveStageAccounts(programDatabaseName, programId, sourceInstitutionId, businessDate, batchId);
		return updateCount;
	}    
    
	//------------------------------------
	// StageAccountsNew
	//------------------------------------
	public void addStageAccountNew(DataBatchManager batchManager, StageAccountNew item)
	{
		fileLoaderDAO.addStageAccountNew(batchManager, item);
	}
    
    public DataBatchManager getStageAccountNewBatchManager(long programId)
	{
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		return fileLoaderDAO.getStageAccountNewBatchManager(programDatabaseName);		
	}

    public int saveStageAccountNew(long programId, long sourceInstitutionId, Date businessDate, long batchId)
	{
		String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		int updateCount = fileLoaderDAO.saveStageAccountNew(programDatabaseName, programId, sourceInstitutionId, businessDate, batchId);
		return updateCount;
	}
    
    public int saveStageAccountNewAccountNumber(long programId, long sourceInstitutionId, long batchId)
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
    	int updateCount = fileLoaderDAO.saveStageAccountNewAccountNumber(programId, sourceInstitutionId, programDatabaseName, batchId);
    	return updateCount;
    }

    public int saveStageAccountNewProductCodes(long programId, Date businessDate, long batchId)
	{
		String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		int updateCount = fileLoaderDAO.saveStageAccountNewProductCodes(programDatabaseName, programId, businessDate, batchId);
		return updateCount;
	}

    public void saveStageAccountNewSetupErrors( long programId, long batchId )
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
    	fileLoaderDAO.saveStageAccountNewSetupErrors( programDatabaseName, batchId );
    }

	//------------------------------------
	// StageAccountV2
	//------------------------------------
	public void addStageAccountV2(DataBatchManager batchManager, StageAccountV2 item)
	{
		fileLoaderDAO.addStageAccountV2(batchManager, item);
	}
    
    public DataBatchManager getStageAccountV2BatchManager(long programId)
	{
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		return fileLoaderDAO.getStageAccountV2BatchManager(programDatabaseName);		
	}

    public int saveStageAccountV2(long programId, long sourceInstitutionId, Date businessDate, long batchId, boolean isManualFile, boolean isUpdateAccountType, boolean isUpdateBranchCode, boolean isUpdateOfficeCode, 
            boolean isUpdatePayoutType, boolean isUpdateProduct, boolean isUpdateRepCode, boolean isUpdateRegLine1, boolean isUpdateRegLine2, 
            boolean isUpdateRegLine3, boolean isUpdateRegLine4, boolean isUpdateRegLine5, boolean isUpdateRegLine6, boolean isUpdateRegLine7, boolean isUpdateRegLine8,
            boolean isUpdateTaxID, boolean isUpdateTaxID2, boolean isUpdateTefraWithheld)
	{
		String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		int updateCount = fileLoaderDAO.saveStageAccountV2(programDatabaseName, programId, sourceInstitutionId, businessDate, batchId, isManualFile,
				isUpdateAccountType, isUpdateBranchCode, isUpdateOfficeCode, isUpdatePayoutType, isUpdateProduct,  
				isUpdateRepCode, isUpdateRegLine1, isUpdateRegLine2, isUpdateRegLine3, isUpdateRegLine4, isUpdateRegLine5, isUpdateRegLine6, 
				isUpdateRegLine7, isUpdateRegLine8, isUpdateTaxID, isUpdateTaxID2, isUpdateTefraWithheld);

	    return updateCount;
	}

	//============================================================================
	//
	// Bank OptOut Loaders
	//
	//============================================================================
	public void addStageBankOptOut(DataBatchManager batchManager, StageBankOptOut item)
	{
		fileLoaderDAO.addStageBankOptOut(batchManager, item);
	}
	
	public DataBatchManager getStageBankOptOutBatchManager(long programId)
	{
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		return fileLoaderDAO.getStageBankOptOutAddBatchManager(programDatabaseName);		
	}
	
	public int saveStageBankOptOuts(long programId, long sourceInstitutionId, Date businessDate, long batchId, boolean isManualAppendingFile, boolean isUseTaxId)
	{
		String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);		
		return fileLoaderDAO.saveStageBankOptOuts(programDatabaseName, programId, sourceInstitutionId, businessDate, batchId, isManualAppendingFile, isUseTaxId);	
	}
	
	//============================================================================
    //
    // Transaction Loaders
    //
	//============================================================================
    public List<ViewCustomerAccountBalance> getCustomerAccountBalanceList(long programId, long sourceInstitutionId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileLoaderDAO.getCustomerAccountBalanceList(databaseName, programId, sourceInstitutionId, businessDate);
    }
    
    public Map<String, ViewAcctBalancesForTransLoader> getCustomerAccountBalanceForTranLoadMap(long programId, long sourceInstitutionId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileLoaderDAO.getCustomerAccountBalanceForTranLoadMap(databaseName, sourceInstitutionId);
    }

    public Map<String,ViewCustomerAccountBalance> getCustomerAccountBalanceMap(long programId, long sourceInstitutionId, Date businessDate)
    {
    	Map<String,ViewCustomerAccountBalance> dataMap = new HashMap<String,ViewCustomerAccountBalance>();
    	List<ViewCustomerAccountBalance> dataList = this.getCustomerAccountBalanceList(programId, sourceInstitutionId, businessDate);
		for(ViewCustomerAccountBalance balance : dataList)
		{
			String accountNumber = balance.getAccountNumber().toUpperCase();
			dataMap.put(accountNumber, balance);
		}
		return dataMap;
    }

    public Double getCustomerTransactionPreviousDayOffsetAmount(long programId, long sourceInstitutionId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	return fileLoaderDAO.getCustomerTransactionPreviousDayOffsetAmount(programId, sourceInstitutionId, databaseName);
    }

	//------------------------------------
    // StageTransactions
	//------------------------------------
	public void addStageTransaction(DataBatchManager batchManager, StageTransaction tran)
	{
		fileLoaderDAO.addStageTransaction(batchManager, tran);
	}

    public DataBatchManager getStageTransactionBatchManager(long programId)
	{
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		return fileLoaderDAO.getStageTransactionBatchManager(programDatabaseName);		
	}

    public int saveStageTransactions(long programId, long sourceInstitutionId, Date businessDate, boolean isLateProcessing, boolean isManualProcessing, long batchId)
	{
		String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		int updateCount = fileLoaderDAO.saveStageTransactions(programDatabaseName, programId, sourceInstitutionId, businessDate, isLateProcessing, isManualProcessing, batchId);
		return updateCount;
	}

    public int validateAndSaveStageTransactions(long programId, long sourceInstitutionId, Date businessDate, boolean isLateProcessing, long batchId)
	{
		String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		int updateCount = fileLoaderDAO.validateAndSaveStageTransactions(programDatabaseName, programId, sourceInstitutionId, businessDate, isLateProcessing, batchId);
		return updateCount;
	}

	//------------------------------------
    // StageTransactionsFromBalances - SIS
	//------------------------------------
	public void addStageTransactionsFromBalances(DataBatchManager batchManager, StageTransactionsFromBalances tran)
	{
		fileLoaderDAO.addStageTransactionsFromBalances(batchManager, tran);
	}

	public DataBatchManager getStageTransactionsFromBalancesBatchManager(long programId)
	{
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		return fileLoaderDAO.getStageTransactionsFromBalancesBatchManager(programDatabaseName);		
	}

    public int saveStageTransactionsFromBalances(long programId, long sourceInstitutionId, Date businessDate, boolean isLateProcessing, boolean isManualProcessing, long batchId)
	{
		String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);
		int updateCount = fileLoaderDAO.saveStageTransactionsFromBalances(programDatabaseName, programId, sourceInstitutionId, businessDate, isLateProcessing, isManualProcessing, batchId);
		return updateCount;
	}
    
}
