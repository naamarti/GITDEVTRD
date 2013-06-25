package com.totalbanksolutions.framework.dao.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.AppConfiguration;
import com.totalbanksolutions.framework.bean.database.table.Calendar_DMS;
import com.totalbanksolutions.framework.bean.database.table.CustomerAccountType;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessBatch;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessDate;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessFile;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessFileActionType;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessLock;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessOperationType;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessStatus;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessStep;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessType;
import com.totalbanksolutions.framework.bean.database.table.DepositInstitution;
import com.totalbanksolutions.framework.bean.database.table.MailingAddress;
import com.totalbanksolutions.framework.bean.database.table.MailingCountry;
import com.totalbanksolutions.framework.bean.database.table.MailingState;
import com.totalbanksolutions.framework.bean.database.table.Processing_Directory;
import com.totalbanksolutions.framework.bean.database.table.Product;
import com.totalbanksolutions.framework.bean.database.table.Program;
import com.totalbanksolutions.framework.bean.database.table.SourceInstitution;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountList;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountTransactionDelete;
import com.totalbanksolutions.framework.bean.database.view.ViewDailyProcessSteps;
import com.totalbanksolutions.framework.bean.database.view.ViewDepositInstitutionMailingAddress;
import com.totalbanksolutions.framework.bean.database.view.ViewForecastBalances;
import com.totalbanksolutions.framework.bean.database.view.ViewProductMapping;
import com.totalbanksolutions.framework.bean.database.view.ViewProgramBankList;
import com.totalbanksolutions.framework.bean.database.view.ViewProgramDepositAccountList;
import com.totalbanksolutions.framework.bean.database.view.ViewTransactionTotals;
import com.totalbanksolutions.framework.dao.UtilDAO;
import com.totalbanksolutions.framework.dao.util.DatabaseHelper;
import com.totalbanksolutions.framework.dao.util.SQLParameters;
import com.totalbanksolutions.framework.enums.Databases;

/**
 * =================================================================================================
 * Created:   19 May 2005 VC
 * Modified:  07 Jun 2011 VC #905: Multiple MMDAs at single DI - Report Naming Convention
 * Modified:  14 Jul 2011 NM #443: Added Detailed checklist Log w/Undo's
 * Modified:  10 Aug 2011 VC #579: Create a new checklist for "Universal Reports" that span all program databases
 *			  14 Mar 2012 DJM #1405 - SQL Deadlock Encountered While Running UNDO in Checklist
 *            25 Sep 2012 NAM #1869: added getProgramWorkingDir and getProgramArchiveDir functions to get list of primary or secondary directories  
 *            23 Oct 2012 NAM #1949: updated for Sweep Activity Dashboard table
 *
 * Implementation class for the Utility data access methods.
 * 
 * =================================================================================================
 */
public class UtilJDBC implements UtilDAO
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    private DataSource dataSource;
    private DatabaseHelper databaseHelper;
    
	public UtilJDBC(DataSource ds)
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
    // AppConfiguration
    //
	//============================================================================
    public AppConfiguration getAppConfiguration() 
	{
		String sql = "SELECT * FROM " + AppConfiguration.DATABASE_TABLE_NAME + " WITH (NOLOCK)";
		SQLParameters params = new SQLParameters();
		return (AppConfiguration)this.databaseHelper.queryForObject(sql, params, new AppConfiguration());
	}

	//============================================================================
	//
    // Calendar_DMS
    //
	//============================================================================
    public String getWelcomeMessage(String calendarDate) 
	{
    	log.debug("calendarDate : " + calendarDate);
		String sql = "SELECT WelcomeMessage "
			+ " FROM " + Calendar_DMS.DATABASE_TABLE_NAME + " WITH (NOLOCK) "
			+ " WHERE BusinessDate = ? ";
			SQLParameters params = new SQLParameters();
			params.addValue(calendarDate);
			String message = this.databaseHelper.queryForString(sql, params);
			return message;
	}
    
    
    
    //============================================================================
	//
    // CustomerAccount_Transaction
    //
	//============================================================================    
    public ViewTransactionTotals getTransactionTotalsForSI(String databaseName, long sourceInstitutionId, Date balanceDate)
    {
    	String sql = "EXEC " + databaseName + "..p_j_GetTransactionTotalsForSI @SourceInstitution_FK=?, @BalanceDate=?";
		SQLParameters params = new SQLParameters();
		params.addValue(sourceInstitutionId);
		params.addValue(balanceDate);
		return (ViewTransactionTotals)this.databaseHelper.executeStoredProcedureForObject(sql, params, new ViewTransactionTotals());
    }

    //============================================================================
	//
    // CustomerAccount_Types
    //
	//============================================================================
    public List<CustomerAccountType> getCustomerAccountTypeList()
	{
		String sql = "SELECT * FROM " + CustomerAccountType.DATABASE_TABLE_NAME + " WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		List<CustomerAccountType> dataList = this.databaseHelper.queryForList(sql, params, new CustomerAccountType());
		return dataList;
	}

    //============================================================================
	//
    // DailyProcessBatch
    //
	//============================================================================
	public long addDailyProcessBatch(DailyProcessBatch batch)
	{
		//deleteUnsuccessfulDailyProcessBatch(batch);
		long batchId = -1;
		String sql = batch.getSQLInsertStatementNew();
		SQLParameters params = batch.getSQLInsertParameters();
		batchId = this.databaseHelper.executeInsert(sql, params);
		return batchId;
	}
    
	public void deleteDailyProcessBatch(long batchId, long statusId)
	{
		String sql = "DELETE " + DailyProcessBatch.DATABASE_TABLE_NAME + " "
		+ " WHERE BatchNumber_PK = ? AND ProcessStatus_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		params.addValue(statusId);
		this.databaseHelper.executeUpdate(sql, params);
	}
    
	public String getDailyProcessBatchError(long batchId)
	{
		String sql = "SELECT Details "
		+ " FROM " + DailyProcessBatch.DATABASE_TABLE_NAME + " WITH (NOLOCK) "
		+ " WHERE BatchNumber_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		String details = this.databaseHelper.queryForString(sql, params);
		return details;
	}

	public Long getDailyProcessBatchStatus(long batchId)
	{
		String sql = "SELECT ProcessStatus_FK "
		+ " FROM " + DailyProcessBatch.DATABASE_TABLE_NAME + " WITH (NOLOCK) "
		+ " WHERE BatchNumber_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(batchId);
		Long processStatus = this.databaseHelper.queryForLong(sql, params);
		return processStatus;
	}

    public void undoDailyProcessBatch(String databaseName, long programId, long sourceInstitutionId, String userName, int stepOrder, long lockNumber)
    {
    	String sql = "EXEC " + databaseName + "..p_j_UndoCurrentDay @Program_FK=?, @SourceInstitution_FK=?, @UserName=?, @UndoToStep=?, @LockNumber=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(userName);
		params.addValue(stepOrder);
		params.addValue(lockNumber);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);		
    }

	public void updateDailyProcessBatchDetails(long batchId, String fileHeaderTimestamp, int totalRecords, String details)
	{
		String sql = "UPDATE " + DailyProcessBatch.DATABASE_TABLE_NAME + " SET "
		+ "FileHeaderTimestamp = ?, TotalRecords = ?, Details = ? "
		+ " WHERE BatchNumber_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(fileHeaderTimestamp);
		params.addValue(totalRecords);
		if(details.length() > 500) details = details.substring(0, 500);
		params.addValue(details);
		params.addValue(batchId);
		this.databaseHelper.executeUpdate(sql, params);
	}

	public void updateDailyProcessBatchPercentComplete(long batchId, String percComplete)
	{
		String sql = "UPDATE " + DailyProcessBatch.DATABASE_TABLE_NAME + " SET "
		+ "PercentComplete = ? "
		+ ",Details = ? "		
		+ " WHERE BatchNumber_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(percComplete);
		params.addValue(percComplete);
		params.addValue(batchId);
		this.databaseHelper.executeUpdate(sql, params);
	}
	
	public void updateDailyProcessBatchStatus(long batchId, long fromProcessStatusId, long toProcessStatusId, String userName)
	{
		String details = (toProcessStatusId == DailyProcessStatus.Values.SUCCESS.getId()) ? "Done" : "";
		updateDailyProcessBatchStatus(batchId, fromProcessStatusId, toProcessStatusId, userName, details);
	}
	
	public void updateDailyProcessBatchStatus(long batchId, long fromProcessStatusId, long toProcessStatusId, String userName, String details)
	{
		String sql = "UPDATE " + DailyProcessBatch.DATABASE_TABLE_NAME + " SET "
		+ "ProcessStatus_FK = ?, LastModifiedDateTime = getdate(), LastModifiedByUserName = ?, Details = ? "
		+ " WHERE BatchNumber_PK = ? AND ProcessStatus_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(toProcessStatusId);
		params.addValue(userName);
		if(details.length() > 500) details = details.substring(0, 500);
		params.addValue(details);
		params.addValue(batchId);
		params.addValue(fromProcessStatusId);
		this.databaseHelper.executeUpdate(sql, params);
	}

	public boolean isChecklistAtBeginning(long programId)
	{
    	String sql = "SELECT TBS_Common.dbo.f_IsChecklistOnFirstStep(" + programId + ")";
		boolean b = this.databaseHelper.queryForBoolean(sql, null);
		return b;
	}
	
	public boolean isRollDateComplete(long programId)
	{
    	String sql = "SELECT TBS_Common.dbo.f_IsRollDateComplete(" + programId + ")";
		boolean b = this.databaseHelper.queryForBoolean(sql, null);
		return b;
	}
	
	//============================================================================
	//
	// DailyProcessDate
	//
	//============================================================================
    public DailyProcessDate getDailyProcessDate(long programId, Date calendarDate)
	{
		String sql = "SELECT * FROM " + DailyProcessDate.DATABASE_TABLE_NAME + " WITH (NOLOCK) "
		+ " WHERE Program_FK = ? AND CalendarDate = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(calendarDate);
		DailyProcessDate item = (DailyProcessDate)this.databaseHelper.queryForObject(sql, params, new DailyProcessDate());
		return item;
	}

    public Date getDailyProcessDateCurrentBusinessDay(long programId)
    {
    	String sql = "EXEC " + DailyProcessDate.DATABASE_NAME + "..p_j_GetCurrentBusinessDate @Program_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		Date d = this.databaseHelper.executeStoredProcedureForDate(sql, params);
		return d;
    }

    public Date getDailyProcessDateMonthStart(long programId, Date calendarDate)
    {
    	String sql = "EXEC " + "TBS_Common" + "..p_j_GetMonthStartDate @BusinessDate=?, @Program_fk=?, @IsReturnAsResultset=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(calendarDate);
		params.addValue(programId);
		params.addValue(1);
		Date d = this.databaseHelper.executeStoredProcedureForDate(sql, params);
		return d;
    }
    
    public Date getDailyProcessDateNextBusinessDay(long programId)
    {
    	Date currentBusinessDate = getDailyProcessDateCurrentBusinessDay(programId);
    	DailyProcessDate d = getDailyProcessDate(programId, currentBusinessDate);
    	return d.getNextBusinessDate();
    }

    public Date getDailyProcessDatePreviousBusinessDay(long programId)
    {
    	Date currentBusinessDate = getDailyProcessDateCurrentBusinessDay(programId);
    	DailyProcessDate d = getDailyProcessDate(programId, currentBusinessDate);
    	return d.getPreviousBusinessDate();
    }
    
    //============================================================================
	//
    // DailyProcessFileActionType
    //
	//============================================================================
    public List<DailyProcessFileActionType> getDailyProcessFileActionTypeList()
    {
		String sql = "SELECT * FROM " + DailyProcessFileActionType.DATABASE_TABLE_NAME + " WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		List<DailyProcessFileActionType> dataList = this.databaseHelper.queryForList(sql, params, new DailyProcessFileActionType());
		return dataList;
    }
 
    //============================================================================
	//
    // DailyProcessFiles
    //
	//============================================================================
    public List<DailyProcessFile> getDailyProcessFiles(long stepId)
	{
		String sql = "SELECT * " 
		+ " FROM " + DailyProcessFile.DATABASE_TABLE_NAME + " WITH (NOLOCK) "
		+ " WHERE Step_FK = ? "
		+ " ORDER BY FileSequenceNumber ";
		SQLParameters params = new SQLParameters();
		params.addValue(stepId);
		return this.databaseHelper.queryForList(sql, params, new DailyProcessFile());
	}
    
    //============================================================================
	//
    // DailyProcessLock
	//
    //============================================================================
    public DailyProcessLock getDailyProcessLock(long programId) 
	{
		String sql = "SELECT * FROM " + DailyProcessLock.DATABASE_TABLE_NAME + " WITH (NOLOCK) WHERE Program_PK = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		return (DailyProcessLock)this.databaseHelper.queryForObject(sql, params, new DailyProcessLock());
	}

    public void dailyProcessLockAquire(long programId, String userName, long versionNumber, String details)
    {
		String sql = "EXEC TBS_Common..p_j_DailyProcessLock_Acquire @Program_PK=?, @UserName=?, @VersionNumber=?, @Details=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(userName);
		params.addValue(versionNumber);
		params.addValue(details);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
    }

    public void dailyProcessLockRelease(long programId, String userName, long lockNumber, String details)
    {
		String sql = "EXEC TBS_Common..p_j_DailyProcessLock_Release @Program_PK=?, @UserName=?, @LockNumber=?, @Details=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(userName);
		params.addValue(lockNumber);
		params.addValue(details);
		this.databaseHelper.executeStoredProcedureUpdate(sql, params);
    }
    
    //============================================================================
    //
    // DailyProcessOperationType
    //
	//============================================================================
	public List<DailyProcessOperationType> getDailyProcessOperationTypeList()
	{
		String sql = "SELECT * FROM " + DailyProcessOperationType.DATABASE_TABLE_NAME + " WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new DailyProcessOperationType());
	}

    //============================================================================
	//
	// DailyProcessStatus
	//
	//============================================================================
    public List<DailyProcessStatus> getDailyProcessStatusList() 
	{
		String sql = "SELECT * FROM " + DailyProcessStatus.DATABASE_TABLE_NAME + " WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new DailyProcessStatus());
	}

    //============================================================================
	//
    // DailyProcessSteps
    //
	//============================================================================
    public List<DailyProcessStep> getDailyProcessStepList()
	{
		String sql = "SELECT * FROM " + DailyProcessStep.DATABASE_TABLE_NAME + " WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new DailyProcessStep());
	}
    
    public List<ViewDailyProcessSteps> GetFullDailyAuditLog(long programId, Date businessDate)
	{
		String sql = "EXEC " + DailyProcessStep.DATABASE_NAME + "..p_j_GetFullDailyAuditLog @Program_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		List<ViewDailyProcessSteps> dataList = this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewDailyProcessSteps());
		return dataList;
	}
    
    public List<ViewDailyProcessSteps> getDailyProcessStepList(long programId, long sourceInstitutionId, Date businessDate)
	{
		String sql = "EXEC " + DailyProcessStep.DATABASE_NAME + "..p_j_GetDailyProcessSteps @Program_FK=?, @SourceInstitution_FK=?, @BusinessDate=?, @ShowAllSteps=0 ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(businessDate);
		List<ViewDailyProcessSteps> dataList = this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewDailyProcessSteps());
		boolean isFoundNextStep = false;
		boolean isFoundSkippedStep = false;
		int lastNonPendingStepOrder = 0;
		
		// Set Allowable Actions on the steps 
		if(dataList.size() > 0)
		{
			for(ViewDailyProcessSteps item : dataList)
			{
				int stepOrder = item.getStepOrder();
				if(item.getProcessStatusId() == DailyProcessStatus.Values.SUCCESS.getId())
				{
					item.setIsAllowUndo( true );
					lastNonPendingStepOrder = stepOrder;
				}
				else if(item.getProcessStatusId() == DailyProcessStatus.Values.SKIPPED.getId())
				{
					isFoundSkippedStep = true;
				}
				else if(item.getProcessStatusId() == DailyProcessStatus.Values.PENDING.getId())
				{
					if(!isFoundNextStep)
					{
						item.setIsNextStep( true );
						item.setIsAllowRunProcess( true );
						isFoundNextStep = true;
					}
					if(item.isSkippable())
					{
						item.setIsAllowSkip( true );
					}
				}
				else if(item.getProcessStatusId() == DailyProcessStatus.Values.ON_HOLD.getId())
				{
					if(!isFoundNextStep)
					{
						item.setIsNextStep( true );
						isFoundNextStep = true;
					}
				}
				else if(item.getProcessStatusId() == DailyProcessStatus.Values.RUNNING.getId())
				{
					item.setIsNextStep( true );
					item.setIsAllowStop( true );
					isFoundNextStep = true;
					lastNonPendingStepOrder = stepOrder;
				}
				else if(item.getProcessStatusId() == DailyProcessStatus.Values.CANCELED.getId()
					|| item.getProcessStatusId() == DailyProcessStatus.Values.FAILED.getId())
				{
					item.setIsNextStep( true );
					item.setIsAllowUndo( true );
					isFoundNextStep = true;
					lastNonPendingStepOrder = stepOrder;
				}
				else if(item.getProcessStatusId() == DailyProcessStatus.Values.WAITING.getId()
					|| item.getProcessStatusId() == DailyProcessStatus.Values.UNDOING.getId())
				{
					item.setIsNextStep( true );
					isFoundNextStep = true;
					lastNonPendingStepOrder = stepOrder;
				}
			}

			/*
			 * For Skipped steps, a few extra things have to be done to decide if they can be unSkipped:
			 * 1. All Skipped steps coming before the last Non-Pending state cannot be unSkipped.
			 *      * Since a later step has been run, you cannot enable an earlier step as it will cause things to run out of order. 
			 * 2. If there are no Non-Pending states (nothing has been run), then all Skipped steps can be unSkipped.
			 *      * Nothing has been run, so the order is not affected.
			 * 3. All Skipped steps coming after the Current step (next step to run) can be unSkipped.
			 *      * Enabling future steps does not affect the order of what has been run.
			 */
			if(isFoundSkippedStep)
			{
				for(ViewDailyProcessSteps item : dataList)
				{
					if(item.getProcessStatusId() == DailyProcessStatus.Values.SKIPPED.getId())
					{
						int stepOrder = item.getStepOrder();
						if(stepOrder > lastNonPendingStepOrder)
						{
							item.setIsAllowUnSkip( true );
						}
					}
				}
			}
		}
		return dataList;
	}

	//============================================================================
	//
    // DailyProcessType
    //
	//============================================================================
	public List<DailyProcessType> getDailyProcessTypeList()
	{
		String sql = "SELECT * FROM " + DailyProcessType.DATABASE_TABLE_NAME + " WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new DailyProcessType());
	}
    
	//============================================================================
	//
	// DepositInstitutions
	//
	//============================================================================
    public List<DepositInstitution> getDepositInstitutionList() 
	{
		String sql = "SELECT * FROM " + DepositInstitution.DATABASE_TABLE_NAME + " WITH (NOLOCK) ORDER BY Name ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new DepositInstitution());
	}

    public Map<String,ViewDepositInstitutionMailingAddress> getDepositInstitutionMailingAddressMap()
	{
		Map<String,ViewDepositInstitutionMailingAddress> dataMap = new HashMap<String,ViewDepositInstitutionMailingAddress>();
		List<ViewDepositInstitutionMailingAddress> dataList = new ArrayList<ViewDepositInstitutionMailingAddress>();
		String sql = "SELECT a.Code, b.City, c.State, c.PostalCode AS StateCode "
		+ "FROM " + DepositInstitution.DATABASE_TABLE_NAME + " a WITH (NOLOCK) "
		+ "INNER JOIN " + MailingAddress.DATABASE_TABLE_NAME + " b WITH (NOLOCK) ON a.MailingAddress_FK = b.MailingAddress_PK "
		+ "LEFT JOIN " + MailingState.DATABASE_TABLE_NAME + " c WITH (NOLOCK) ON b.State_FK = c.State_PK ";
		SQLParameters params = new SQLParameters();
		dataList = this.databaseHelper.queryForList(sql, params, new ViewDepositInstitutionMailingAddress());
		for(ViewDepositInstitutionMailingAddress item : dataList)
		{
			String key = item.getBankCode().toUpperCase();
			dataMap.put(key, item);
		}
		return dataMap;
	}

	//============================================================================
	//
    // Forecast
    //
	//============================================================================	
    public List<ViewForecastBalances> getForecastBalances(String databaseName, long programId, Date businessDate, boolean includeZeroBalances)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetForecastBalances @Program_FK=?, @BusinessDate=?, @IncludeZeroBalances=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		params.addValue(includeZeroBalances);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewForecastBalances());
    }

    //============================================================================
	//
    // MailingCountries
	//
    //============================================================================    
	public List<MailingCountry> getMailingCountryList()
	{
		String sql = "SELECT * FROM " + MailingCountry.DATABASE_TABLE_NAME + " WITH (NOLOCK) ORDER BY Country ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new MailingCountry());
	}
	
    //============================================================================
	//
	// MailingStates
	//
	//============================================================================    
	public List<MailingState> getMailingStateList()
	{
		String sql = "SELECT * FROM " + MailingState.DATABASE_TABLE_NAME + " WITH (NOLOCK) ORDER BY State ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new MailingState());
	}
    
	//============================================================================
	//
	// Products
	//
	//============================================================================
    public List<Product> getProductList( long programId, long sourceInstitutionId )
	{
		String sql = "SELECT * "
		+ "FROM " + Product.DATABASE_TABLE_NAME + " WITH (NOLOCK) "
		+ "WHERE Program_FK = ? "
		+ "AND SourceInstitution_FK = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		return this.databaseHelper.queryForList(sql, params, new Product());
	}

    public List<ViewProductMapping> getProductMappingList( long sourceInstitutionId )
	{
		String sql = "SELECT P.Product_FK, B.Name as 'BranchFromName', B2.Name as 'BranchToName', P.SourceProductCode, P.IsSourceInstDefault "
		+ "FROM " + Databases.COMMON + "..Product_Mapping P WITH (NOLOCK) "
		+ "JOIN " + Databases.COMMON + "..BranchCodes B WITH (NOLOCK) ON B.BranchCode_PK = P.BranchCode_FK_From "
		+ "JOIN " + Databases.COMMON + "..BranchCodes B2 WITH (NOLOCK) ON B2.BranchCode_PK = P.BranchCode_FK_To "
		+ "WHERE P.SourceInstitution_FK = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(sourceInstitutionId);
		return this.databaseHelper.queryForList(sql, params, new ViewProductMapping());
	}
    
    //============================================================================
	//
    // ProgramDepositAccounts
	//
    //============================================================================
    public List<ViewProgramBankList> getProgramBankList(String databaseName, long programId)
    {
		String sql = "EXEC TBS_Common..p_j_GetProgramBankList @Program_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewProgramBankList());
    }

    public List<ViewProgramBankList> getProgramBankList(String databaseName, long programId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetProgramBankList_BalanceFiltered @Program_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewProgramBankList());
    }
    
    public List<ViewProgramDepositAccountList> getProgramDepositAccountList(String databaseName, long programId)
    {
		String sql = "EXEC TBS_Common..p_j_GetProgramDepositAccountList @Program_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewProgramDepositAccountList());
    }
    
    public List<ViewProgramDepositAccountList> getNonZeroBalanceProgramDepositAccountList(String databaseName, long programId, Date businessDate)
    {
		String sql = "EXEC " + databaseName + "..p_j_GetNonZeroBalanceProgramDepositAccountList @Program_FK=?, @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewProgramDepositAccountList());
    }
    
    //============================================================================
	//
    // Programs
    //
	//============================================================================
    public List<Program> getProgramList() 
	{
		String sql = "SELECT P.*, Name AS ProgramName "
		+ "FROM " + Program.DATABASE_TABLE_NAME  + " P WITH (NOLOCK) "
		+ "INNER JOIN StatusTypes S WITH (NOLOCK) ON S.Status_PK = P.Status_FK "
		+ "WHERE S.Code = 'ACTIVE' "
		+ "ORDER BY Name ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new Program());
	}	

    public List<Program> getProgramListForChecklist() 
	{
		String sql = "SELECT P.*, CASE Name WHEN 'NONE' THEN '(Universal Reports)' ELSE Name END ProgramName "
		+ "FROM " + Program.DATABASE_TABLE_NAME  + " P WITH (NOLOCK) "
		+ "INNER JOIN StatusTypes S WITH (NOLOCK) ON S.Status_PK = P.Status_FK "
		+ "WHERE S.Code = 'ACTIVE' OR P.InternalCode = 'NONE' "
		+ "ORDER BY CASE Name WHEN 'NONE' THEN '(Universal Reports)' ELSE Name END ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new Program());
	}	

    public void updateProgramBusinessDate( long programId, Date newDate )
    {
		String sql = "UPDATE " + Program.DATABASE_TABLE_NAME + " SET "
				   + "	BusinessDate = ? " 
				   + "WHERE Program_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(newDate);
		params.addValue(programId);

		this.databaseHelper.executeUpdate(sql, params);
    }
    
    public List<Processing_Directory> getProgramWorkingDirList(long programId, boolean isPrimary)
    {
    	String sql = "SELECT Directory "
    			+ "FROM " + Processing_Directory.DATABASE_TABLE_NAME  + " PD WITH (NOLOCK) "
    			+ "INNER JOIN Processing_DirectoryType PDT WITH (NOLOCK) ON PDT.DirectoryType_PK = PD.DirectoryType_FK "
    			+ "WHERE PDT.DirectoryType = 'Working' AND PD.IsPrimary = isPrimary"
    			+ "ORDER BY Directory ";
    			SQLParameters params = new SQLParameters();
    			return this.databaseHelper.queryForList(sql, params, new Processing_Directory());    	
    }
    
    public List<Processing_Directory> getProgramArchiveDirList(long programId, boolean isPrimary)
    {
    	String sql = "SELECT Directory "
    			+ "FROM " + Processing_Directory.DATABASE_TABLE_NAME  + " PD WITH (NOLOCK) "
    			+ "INNER JOIN Processing_DirectoryType PDT WITH (NOLOCK) ON PDT.DirectoryType_PK = PD.DirectoryType_FK "
    			+ "WHERE PDT.DirectoryType = 'Archive' AND PD.IsPrimary = isPrimary"
    			+ "ORDER BY Directory ";
    			SQLParameters params = new SQLParameters();
    			return this.databaseHelper.queryForList(sql, params, new Processing_Directory());  
    }
        
    public String getProgramWorkingDirPrimary(long programId)
    {
    	String sql = "SELECT Directory "
    			+ "FROM " + Processing_Directory.DATABASE_TABLE_NAME  + " PD WITH (NOLOCK) "
    			+ "INNER JOIN Processing_DirectoryType PDT WITH (NOLOCK) ON PDT.DirectoryType_PK = PD.DirectoryType_FK "
    			+ "WHERE PDT.DirectoryType = 'Working' AND PD.IsPrimary = 1"
    			+ "ORDER BY Directory ";
    			SQLParameters params = new SQLParameters();
				String directory = this.databaseHelper.queryForString(sql, params);
				return directory;
    }
    
    public String getProgramArchiveDirPrimary(long programId)
    {
    	String sql = "SELECT Directory "
    			+ "FROM " + Processing_Directory.DATABASE_TABLE_NAME  + " PD WITH (NOLOCK) "
    			+ "INNER JOIN Processing_DirectoryType PDT WITH (NOLOCK) ON PDT.DirectoryType_PK = PD.DirectoryType_FK "
    			+ "WHERE PDT.DirectoryType = 'Archive' AND PD.IsPrimary = 1"
    			+ "ORDER BY Directory ";
				SQLParameters params = new SQLParameters();
				String directory = this.databaseHelper.queryForString(sql, params);
				return directory;
    }
    
    //TODO - remove Secondary01 and Secondary02 code once the queue is ready.  We will save to these directories differently then
    public String getProgramArchiveDirSecondary_01(long programId)
    {
    	String sql = "SELECT TOP 1 Directory "
    			+ "FROM " + Processing_Directory.DATABASE_TABLE_NAME  + " PD WITH (NOLOCK) "
    			+ "INNER JOIN Processing_DirectoryType PDT WITH (NOLOCK) ON PDT.DirectoryType_PK = PD.DirectoryType_FK "
    			+ "WHERE PDT.DirectoryType = 'Archive' AND PD.IsPrimary = 0 AND PD.Directory LIKE '%bkp01%'"
    			+ "ORDER BY Directory ";
				SQLParameters params = new SQLParameters();
				String directory = this.databaseHelper.queryForString(sql, params);
				return directory;
    }
    
    //TODO - remove Secondary01 and Secondary02 code once the queue is ready.  We will save to these directories differently then
    public String getProgramArchiveDirSecondary_02(long programId)
    {
    	String sql = "SELECT TOP 1 Directory "
    			+ "FROM " + Processing_Directory.DATABASE_TABLE_NAME  + " PD WITH (NOLOCK) "
    			+ "INNER JOIN Processing_DirectoryType PDT WITH (NOLOCK) ON PDT.DirectoryType_PK = PD.DirectoryType_FK "
    			+ "WHERE PDT.DirectoryType = 'Archive' AND PD.IsPrimary = 0 AND PD.Directory LIKE '%bkp02%'"
    			+ "ORDER BY Directory ";
				SQLParameters params = new SQLParameters();
				String directory = this.databaseHelper.queryForString(sql, params);
				return directory;
    }
    
    //============================================================================
	//
    // Services
	//
    //============================================================================	
    private String serviceName = "TBSMaintenanceService";
    
//	public List<Service> getServiceList() 
//	{
//		String sql = "SELECT Service_PK, ServiceName, ServiceDescription, StartedDateTime, LastPingDateTime, VersionNumber, getdate() AS CurrentDatabaseDateTime, "
//			+ "ServerName, CommandDirectory, StartCommand, StopCommand "
//			+ "FROM Services "
//			+ "ORDER BY ServiceName ";
//		MapSqlParameterSource params = new MapSqlParameterSource();
//		List<Service> serviceList = this.jdbcTemplate.query(sql, params, new ServiceRowMapper());
//		return serviceList;
//	}

	public void updateServicesPingTime()
	{
		String sql = "UPDATE Services SET LastPingDateTime = getdate(), VersionNumber = VersionNumber + 1 "
		+ " WHERE ServiceName = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(serviceName);
		this.databaseHelper.executeUpdate(sql, params);
	}	

	public void updateServicesStartTime()
	{
		String sql = "UPDATE Services SET StartedDateTime = getdate(), LastPingDateTime = getdate(), VersionNumber = VersionNumber + 1 "
		+ " WHERE ServiceName = ?";
		SQLParameters params = new SQLParameters();
		params.addValue(serviceName);
		this.databaseHelper.executeUpdate(sql, params);
	}	
	
//	private static final class ServiceRowMapper implements RowMapper {
//
//	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//			Service service = new Service();
//			service.setServiceId(rs.getLong("Service_PK"));
//			service.setServiceName(ConvertUtility.nullToEmptyString(rs.getString("ServiceName")));
//	    	service.setServiceDescription(ConvertUtility.nullToEmptyString(rs.getString("ServiceName")));
//			service.setStartedDateTime(rs.getTimestamp("StartedDateTime"));
//	    	service.setLastPingDateTime(rs.getTimestamp("LastPingDateTime"));
//	    	service.setCurrentDatabaseDateTime(rs.getTimestamp("CurrentDatabaseDateTime"));
//	    	service.setVersionNumber(rs.getInt("VersionNumber"));
//	    	service.setServerName(ConvertUtility.nullToEmptyString(rs.getString("ServerName")));
//	    	service.setCommandDirectory(ConvertUtility.nullToEmptyString(rs.getString("CommandDirectory")));
//	    	service.setStartCommand(ConvertUtility.nullToEmptyString(rs.getString("StartCommand")));
//	    	service.setStopCommand(ConvertUtility.nullToEmptyString(rs.getString("StopCommand")));
//	    	return service;
//	    }
//	}
	
	//============================================================================
	//
	// CustomerAccountTransactionDelete
	//
	//============================================================================

	public List<ViewCustomerAccountTransactionDelete> getCustomerAccountTransactionDeleteList(long programId, long sourceInstitutionId, String AccountNumber, String databaseName)
	{

		String sql = "EXEC " + databaseName + "..p_w_n_GetAccountTransactionsDeleteInfo @Action=?, @Program_PK=?, @SI_PK=?, @BalanceDate=?, @AccountNo=?, @Product_PK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue("SELECT");
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue("");
		params.addValue(AccountNumber);
		params.addValue(0);
		//"3003217217"
		log.debug("ViewCustomerAccountTransactionDelete" + this.databaseHelper.queryForList(sql, params, new ViewCustomerAccountTransactionDelete()));

		return this.databaseHelper.queryForList(sql, params, new ViewCustomerAccountTransactionDelete());



	}

	public List<ViewCustomerAccountTransactionDelete> getCustomerAccountTransactionConfirmList(long programId, long sourceInstitutionId, String databaseName)
	{
		/*log.debug("programID" + programId);
		log.debug("sourceInstitutionId" + sourceInstitutionId);
		log.debug("AccountNumber" + AccountNumber);*/

		String sql = "EXEC " + databaseName + "..p_w_n_GetAccountTransactionsConfirmInfo @Action=?, @Program_PK=?, @SI_PK=?, @BalanceDate=?,@Product_PK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue("SELECT");
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue("");
		params.addValue(0);
		//"3003217217"
		//3003771114
		log.debug("ViewCustomerAccountTransactionConfirm" + this.databaseHelper.queryForList(sql, params, new ViewCustomerAccountTransactionDelete()));

		return this.databaseHelper.queryForList(sql, params, new ViewCustomerAccountTransactionDelete());



	}

	public void updateCustomerAccountTransactionDelete(long programId, long sourceInstitutionId, List<Long> transactionId, String AccountNumber, String databaseName, long userId, boolean isComplete)
		    {
			 //log.debug("|programId "+ programId + "  |sourceInstitutionId "+ sourceInstitutionId +"  |transactionId "+ transactionId +"  |databaseName "+ databaseName);//dbg-nm
				
				if (isComplete)
				{
				log.debug("isComplete=true");
					String sql = "EXEC " + databaseName + "..p_w_n_UpdateAccountTransactionsDeleteInfo @Action=?, @Program_PK=?, @SI_PK=?, @AccountNo=?, @User_FK=?";
					SQLParameters params = new SQLParameters();
					params.addValue("Complete");
					params.addValue(programId);
					params.addValue(sourceInstitutionId);
					params.addValue(AccountNumber);
					params.addValue(userId);
					this.databaseHelper.executeStoredProcedureUpdate(sql, params);
				}
				else
				{
				log.debug("updtransdelete");
				String sql = "EXEC " + databaseName + "..p_w_n_UpdateAccountTransactionsDeleteInfo @Action=?, @Program_PK=?, @SI_PK=?, @AccountNo=?, @User_FK=?";
				SQLParameters params = new SQLParameters();
				params.addValue("Clear");
				params.addValue(programId);
				params.addValue(sourceInstitutionId);
				params.addValue(AccountNumber);
				params.addValue(userId);
				this.databaseHelper.executeStoredProcedureUpdate(sql, params);
				
				
				log.debug("updtransdelete2");
				int count = 0;
				for(Long item : transactionId )
				{
					
					log.debug("Transactionid " + count + " = "+ item);
					count++;
				 
					if(item != 0)
					{
						 sql = "EXEC " + databaseName + "..p_w_n_UpdateAccountTransactionsDeleteInfo @Action =?, @Transaction_PK=?,  @Program_PK=?, @SI_PK=?, @AccountNo=?, @User_FK=?";
						 params = new SQLParameters();
						params.addValue("Update");
						params.addValue(item);
						params.addValue(programId);
						params.addValue(sourceInstitutionId);
						params.addValue(AccountNumber);
						params.addValue(userId);
						this.databaseHelper.executeStoredProcedureUpdate(sql, params);
					}
				}
				}
		    }

	public void updateCustomerAccountTransactionConfirm(long programId, long sourceInstitutionId, List<Long> transactionId, String UpdateType, String databaseName, long userId)
		    {
			   
				
				String sql = "EXEC " + databaseName + "..p_w_n_UpdateAccountTransactionsConfirmInfo @Action=?, @Program_PK=?, @SI_PK=?, @User_FK=?";
				SQLParameters params = new SQLParameters();
				
				log.debug(UpdateType);
				int count = 0;
				if(UpdateType=="Remove")
				for(Long item : transactionId )
				{
					
					log.debug("Transactionid " + count + " = "+ item);
					count++;
				 
					if(item != 0)
					{
						 sql = "EXEC " + databaseName + "..p_w_n_UpdateAccountTransactionsConfirmInfo @Action =?, @Transaction_PK=? ";
						 params = new SQLParameters();
						params.addValue("Remove");
						params.addValue(item);
						this.databaseHelper.executeStoredProcedureUpdate(sql, params);
					}
				
				}
				else if (UpdateType=="Submit"){
					// sql = "EXEC " + databaseName + "..p_w_n_UpdateAccountTransactionsConfirmInfo @Action =?";
					 //params = new SQLParameters();
					params.addValue("Submit");			
					params.addValue(programId);
					params.addValue(sourceInstitutionId);
					params.addValue(userId);
					this.databaseHelper.executeStoredProcedureUpdate(sql, params);
				}
				
		    }

	public List<ViewCustomerAccountList> getAccountList(String databaseName, long programId, long sourceInstitutionId, String accountNumber)
	{
		String sql = "EXEC " + databaseName + "..p_w_n_GetAccountList @Action=?, @Program_PK=?, @SI_PK=?, @AccountNo=?, @Type=? ";
		SQLParameters params = new SQLParameters();
		params.addValue("SELECT");
		params.addValue(programId);
		params.addValue(sourceInstitutionId);
		params.addValue(accountNumber);
		params.addValue("3");
		return this.databaseHelper.queryForList(sql, params, new ViewCustomerAccountList());
	}

	
	
	//============================================================================
	//
	// SourceInstitutions
	//
	//============================================================================
    public List<SourceInstitution> getSourceInstitutionList() 
	{
		String sql = "SELECT S.* "
			+ "FROM " + SourceInstitution.DATABASE_TABLE_NAME + " S WITH (NOLOCK) "
			+ "INNER JOIN TBS_Common.dbo.StatusTypes ST WITH (NOLOCK) ON ST.Status_PK = S.Status_FK "
			+ "WHERE ST.Code = 'ACTIVE' "
			+ "ORDER BY S.Name ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new SourceInstitution());
	}

	public List<SourceInstitution> getSourceInstitutionList(long programId)
	{
		String sql = "SELECT S.SourceInstitution_PK, S.Code, S.Name "
			+ "FROM " + SourceInstitution.DATABASE_TABLE_NAME + " S WITH (NOLOCK) "
			+ "INNER JOIN TBS_Common.dbo.StatusTypes ST WITH (NOLOCK) ON ST.Status_PK = S.Status_FK "
			+ "WHERE S.Program_FK = ? "
			+ "AND ST.Code = 'ACTIVE' "
			+ "ORDER BY S.Name ";
		SQLParameters params = new SQLParameters();
		params.addValue(programId);
		return this.databaseHelper.queryForList(sql, params, new SourceInstitution());
	}
	
}
