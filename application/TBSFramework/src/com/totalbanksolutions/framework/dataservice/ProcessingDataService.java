package com.totalbanksolutions.framework.dataservice;

import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.view.ViewProcessCashInterestTotals;
import com.totalbanksolutions.framework.cache.CacheManager;
import com.totalbanksolutions.framework.dao.ProcessingDAO;
import com.totalbanksolutions.framework.dao.jdbc.ProcessingJDBC;

/**
 * =================================================================================================
 * Created:   VC
 * Modified:  07 Jun 2011 NM #694: Add Cash Interest and opening balance in checklist screen
 *            09 Nov 2011 VC #1054: Eliminate omnibus MMDA withdrawals by reducing NOW balances where possible
 *			  23 Apr 2012 VC #1495: Forecast - separate waterfall forecast & generate reports steps from normal forecast
 *
 * =================================================================================================
 */
public class ProcessingDataService 
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());
    private ProcessingDAO processingDAO = null;
    private CacheManager cacheManager = null;
    private DataService ds = null;

    public ProcessingDataService (DataSource dataSource, CacheManager cacheManager, DataService ds)
    {
    	this.processingDAO = new ProcessingJDBC(dataSource);
    	this.cacheManager = cacheManager;
    	this.ds = ds;
    }
    
	public void close()
	{
		this.processingDAO.close();
		this.processingDAO = null;
		this.cacheManager = null;
		this.ds = null;
	}

    //============================================================================
	//
	// AutoRecon
	//
	//============================================================================
    public void updateAutoReconData(long programId, long sourceInstitutionId, Date businessDate, long batchId, long userId )
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	processingDAO.updateAutoReconData(databaseName, programId, sourceInstitutionId, businessDate, batchId, userId);
    }

    //============================================================================
	//
    // Commit Transactions
	//
    //============================================================================
    public void commitTransactions(long programId, long sourceInstitutionId, Date businessDate, boolean isLateProcessing, long batchId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	processingDAO.commitTransactions(databaseName, programId, sourceInstitutionId, businessDate, isLateProcessing, batchId);
    }
    
    //============================================================================
	//
    // Consolidation and Forecast
    //
	//============================================================================
    public void allocateMMDANOWBalances(long programId, long sourceInstitutionId, long batchId, Date businessDate)
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);    	
    	processingDAO.allocateMMDANOWBalances(programId, sourceInstitutionId, programDatabaseName, batchId, businessDate);
    }
    
    public void allocateMMDANOWBankBalances(long programId, long sourceInstitutionId, long batchId, Date businessDate)
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);    	
    	processingDAO.allocateMMDANOWBankBalances(programId, sourceInstitutionId, programDatabaseName, batchId, businessDate);
    } 
    
    public void allocateReduceNOWBalances(long programId, long sourceInstitutionId, Date businessDate, long batchId)
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);    	
    	processingDAO.allocateReduceNOWBalances(programId, sourceInstitutionId, programDatabaseName, businessDate, batchId);
    } 

    public void consolidateAccounts(long programId, Date businessDate, long batchId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	processingDAO.consolidateAccounts(databaseName, programId, businessDate, batchId);
    }

    public void runForecast(long programId, Date businessDate, long batchId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	processingDAO.runForecast(databaseName, programId, businessDate, batchId);
    }

    public void runForecastWaterfall(long programId, Date businessDate, long batchId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	processingDAO.runForecastWaterfall(databaseName, programId, businessDate, batchId);
    }
    
    //============================================================================
	//
    // Create Tax Groups
	//
    //============================================================================
    public void createAccountTaxIDGroups(long programId, long sourceInstitutionId, long batchId, Date businessDate)
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);    	
    	processingDAO.createAccountTaxIDGroups(programId, sourceInstitutionId, programDatabaseName, batchId, businessDate);
    }

    //============================================================================
	//
    // Email Sender
    //
	//============================================================================
    public void setEMS_FilesToSend_List( long batchId, long depositInstitutionId, long sourceInstitutionId, long program, long envelope, String ReportFileFullPath )
    {
    	processingDAO.updateEMSFilesToSend(batchId, depositInstitutionId, sourceInstitutionId, program, envelope, ReportFileFullPath);
    }
     
    //============================================================================
	//
    // Interest and Fees
	//
	//============================================================================
    public void calculateInterestAndFees(long programId, long sourceInstitutionId, Date businessDate, long batchId, boolean isLateProcessing)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);
    	processingDAO.calculateInterestAndFees(databaseName, programId, sourceInstitutionId, businessDate, batchId, isLateProcessing);
    }
    
    public void processCashInterestPayments(long programId, long sourceInstitutionId, Date businessDate, long batchId)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);    	
    	processingDAO.processCashInterestPayments(databaseName, programId, sourceInstitutionId, businessDate, batchId);
    } 
    
    public ViewProcessCashInterestTotals getCashInterestPaymentTotals(long programId, long sourceInstitutionId, Date businessDate)
    {
    	String databaseName = this.ds.util.getProgramDatabaseName(programId);    	
    	return processingDAO.getCashInterestPaymentTotals(databaseName, programId, sourceInstitutionId, businessDate);
    } 
    
    //============================================================================
	//
    // Roll to Next Day
    //
	//============================================================================
    public void rollDailyProcessDate(long programId, long sourceInstitutionId, long batchId)
    {
    	String programDatabaseName = this.ds.util.getProgramDatabaseName(programId);    	
    	processingDAO.rollDailyProcessDate(programId, sourceInstitutionId, programDatabaseName, batchId);
    } 
    
    
}
