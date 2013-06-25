package com.totalbanksolutions.framework.dao.jdbc;

import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.CustomerAccount;
import com.totalbanksolutions.framework.bean.database.view.ViewProcessCashInterestTotals;
import com.totalbanksolutions.framework.dao.ProcessingDAO;
import com.totalbanksolutions.framework.dao.util.DataBatchManager;
import com.totalbanksolutions.framework.dao.util.DatabaseHelper;
import com.totalbanksolutions.framework.dao.util.SQLParameters;

/**
 * =================================================================================================
 * Created:   VC
 * Modified:  07 Jun 2011 NM #694: Add Cash Interest and opening balance in checklist screen
 * Modified:  09 Nov 2011 VC #1054: Eliminate omnibus MMDA withdrawals by reducing NOW balances where possible
 *			  23 Apr 2012 VC #1495: Forecast - separate waterfall forecast & generate reports steps from normal forecast
 *
 * =================================================================================================
 */
public class ProcessingJDBC implements ProcessingDAO
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    private DataSource dataSource;
    private DatabaseHelper databaseHelper;
    
	public ProcessingJDBC(DataSource ds)
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
    // AutoRecon
    //
	//============================================================================
    public void updateAutoReconData(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId, long userId)
    {
    	String sql = "EXEC " + databaseName + "..p_j_UpdateAllAutoRecondata @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchNumber_FK=?, @User_FK=?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchId);
		params.addValue(userId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
    }

    //============================================================================
	//
    // Commit Transactions
	//
    //============================================================================
    public void commitTransactions(String databaseName, long programId, long sourceInstitutionId, Date businessDate, boolean isLateProcessing, long batchId)
    {
    	String sql = "EXEC " + databaseName + "..p_j_CommitTransactions @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @IsLateProcessing=?, @BatchNumber_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(isLateProcessing);
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
    }

    //============================================================================
	//
    // Consolidation and Forecast
    //
	//============================================================================
    public void allocateMMDANOWBalances(long programId, long sourceInstitutionId, String databaseName, long batchId, Date businessDate)
    {    	
		String sql = "EXEC " + databaseName + "..p_j_RBC_Allocate_Balances_MMDA_NOW_Early @Program_FK=?,@SourceInstitution_FK = ?, @BusinessDate = ?,@BatchNumber_FK = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);		
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
    }

    public void allocateMMDANOWBankBalances(long programId, long sourceInstitutionId, String databaseName, long batchId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_RBC_Allocate_BankBalances_MMDA_NOW_Early @Program_FK=?,@SourceInstitution_FK = ?, @BusinessDate = ?,@BatchNumber_FK = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);		
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
    }

    public void allocateReduceNOWBalances(long programId, long sourceInstitutionId, String databaseName, Date businessDate, long batchId)
    {
		String sql = "EXEC " + databaseName + "..p_j_RBC_Allocate_NOW_Reductions @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchNumber_FK = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);		
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
    }

    public void consolidateAccounts(String databaseName, long programId, Date businessDate, long batchId)
	{
		String sql = "EXEC " + databaseName + "..p_j_ProcessConsolidation @Program_FK=?, @BusinessDate=?, @BatchNumber_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
	}

	public void runForecast(String databaseName, long programId, Date businessDate, long batchId)
	{
		String sql = "EXEC " + databaseName + "..p_j_ProcessForecast @Program_FK=?, @BusinessDate=?, @BatchNumber_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
	}

	public void runForecastWaterfall(String databaseName, long programId, Date businessDate, long batchId)
	{
		String sql = "EXEC " + databaseName + "..p_j_ProcessForecastWaterfall @Program_FK=?, @BusinessDate=?, @BatchNumber_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
	}
	
    //============================================================================
	//
	// Create Tax Groups
	//
	//============================================================================
    public void createAccountTaxIDGroups(long programId, long sourceInstitutionId, String databaseName, long batchId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_CreateAccountTaxGroups @Program_FK=?,@SourceInstitution_FK = ?, @BusinessDate = ?,@BatchNumber_FK = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);		
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
    }

    //============================================================================
	//
    // Email Sender
    //
	//============================================================================
	public void updateEMSFilesToSend(long batchId, long depositInstitutionId, long sourceInstitutionId, long program, long envelope, String ReportFileFullPath)
	{
		String sql = "INSERT TBS_Common..EMS_Server_ReportsToSend (BatchNumber_FK, DepositInstitution_FK, SourceInstitution_FK, Program_FK, Envelope_FK, ReportFileFullPath) "
		+ "VALUES (?, ?, ?, ?, ?, ?)";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		params.addValue(depositInstitutionId);
		params.addValue(sourceInstitutionId);
		params.addValue(program);
		params.addValue(envelope);
		params.addValue(ReportFileFullPath);
		this.databaseHelper.executeUpdate(sql, params);
	}
	
	
	//============================================================================
	//
	// Interest and Fees
	//
	//============================================================================
	public void calculateInterestAndFees(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId, boolean isLateProcessing)
	{
		String sql = "EXEC " + databaseName + "..p_j_ProcessInterestAndFees_Main @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?,  @BatchNumber_FK=?, @IsLateProcessing = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchId);
		params.addValue(isLateProcessing);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
	}

    public void processCashInterestPayments(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId)
    {
    	String sql = "EXEC " + databaseName + "..p_j_ProcessCashInterestPayments @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchNumber_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
    }

    public ViewProcessCashInterestTotals getCashInterestPaymentTotals(String databaseName, long programId, long sourceInstitutionId, Date businessDate)
    {
    	String sql = "EXEC " + databaseName + "..p_j_GetCashInterestPaymentTotals @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		return (ViewProcessCashInterestTotals)this.databaseHelper.executeStoredProcedureForObject(sql, params, new ViewProcessCashInterestTotals());
    }
    
    //============================================================================
	//
    // Roll to Next Day
    //
	//============================================================================
    public void rollDailyProcessDate(long programId, long sourceInstitutionId, String databaseName, long batchId)
    {
    	String sql = "EXEC " + databaseName + "..p_j_RollDailyProcessDate @Program_FK=?, @SourceInstitution_FK=?, @BatchNumber_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
    }
    
    //============================================================================
	//
    // Wires
    //
	//============================================================================
    public void processPrelimEarlyWireAmount(String databaseName, long programId, long sourceInstitutionId, Date businessDate, long batchId)
    {
    	String sql = "EXEC " + databaseName + "..p_j_ProcessPrelimEarlyWireAmounts @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @BatchNumber_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		params.addValue(batchId);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
    }
    
    
}
