package com.totalbanksolutions.framework.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.totalbanksolutions.framework.bean.database.table.AppConfiguration;
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
import com.totalbanksolutions.framework.bean.database.table.MailingCountry;
import com.totalbanksolutions.framework.bean.database.table.MailingState;
import com.totalbanksolutions.framework.bean.database.table.Processing_Directory;
import com.totalbanksolutions.framework.bean.database.table.Product;
import com.totalbanksolutions.framework.bean.database.table.Program;
import com.totalbanksolutions.framework.bean.database.table.Service;
import com.totalbanksolutions.framework.bean.database.table.SourceInstitution;
import com.totalbanksolutions.framework.bean.database.view.ViewDailyProcessSteps;
import com.totalbanksolutions.framework.bean.database.view.ViewDepositInstitutionMailingAddress;
import com.totalbanksolutions.framework.bean.database.view.ViewForecastBalances;
import com.totalbanksolutions.framework.bean.database.view.ViewProductMapping;
import com.totalbanksolutions.framework.bean.database.view.ViewProgramBankList;
import com.totalbanksolutions.framework.bean.database.view.ViewProgramDepositAccountList;
import com.totalbanksolutions.framework.bean.database.view.ViewTransactionTotals;
import com.totalbanksolutions.framework.dao.util.DAO;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountList;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountTransactionDelete;

/**
 * =================================================================================================
 * Created:   19 May 2005 VC
 * Modified:  07 Jun 2011 VC #905: Multiple MMDAs at single DI - Report Naming Convention
 * Modified:  14 Jul 2011 NM #443: Added Detailed checklist Log w/Undo's
 * Modified:  10 Aug 2011 VC #579: Create a new checklist for "Universal Reports" that span all program databases
 *            25 Sep 2012 NAM #1869: added getProgramWorkingDir and getProgramArchiveDir functions to get list of primary or secondary directories  
 *            23 Oct 2012 NAM #1949: updated for Sweep Activity Dashboard table
 *
 * Interface for the Utility data access methods.
 * 
 * =================================================================================================
 */
public interface UtilDAO extends DAO
{

    public void close();
	
	//============================================================================
	//
    // AppConfiguration
	//
    //============================================================================
    public AppConfiguration getAppConfiguration();

    
	//============================================================================
	//
    // Calendar_DMS
	//
    //============================================================================
    public String getWelcomeMessage(String calendarDate);
    
	//============================================================================
	//
    // CustomerAccount_Transaction
    //
	//============================================================================
    public ViewTransactionTotals getTransactionTotalsForSI(String databaseName, long sourceInstitutionId, Date programDate);

    //============================================================================
	//
    // CustomerAccount_Types
	//
    //============================================================================
    public List<CustomerAccountType> getCustomerAccountTypeList();
    
	//============================================================================
	//
    // DailyProcessBatch
	//
    //============================================================================
	public long addDailyProcessBatch(DailyProcessBatch batch);
	public void deleteDailyProcessBatch(long batchId, long statusId);
	public String getDailyProcessBatchError(long batchId);
	public Long getDailyProcessBatchStatus(long batchId);
    public void undoDailyProcessBatch(String databaseName, long programId, long sourceInstitutionId, String userName, int stepOrder, long lockNumber);
	public void updateDailyProcessBatchDetails(long batchId, String fileHeaderTimestamp, int totalRecords, String batchDetails);
	public void updateDailyProcessBatchPercentComplete(long batchId, String percComplete);
	public void updateDailyProcessBatchStatus(long batchId, long fromProcessStatusId, long toProcessStatusId, String userName);
	public void updateDailyProcessBatchStatus(long batchId, long fromProcessStatusId, long toProcessStatusId, String userName, String details);
	public boolean isChecklistAtBeginning(long programId);
	public boolean isRollDateComplete(long programId);
	
	//============================================================================
	//
	// DailyProcessDate
	//
	//============================================================================
    public DailyProcessDate getDailyProcessDate(long programId, Date calendarDate);
    public Date getDailyProcessDateCurrentBusinessDay(long programId);
    public Date getDailyProcessDateMonthStart(long programId, Date calendarDate);
    public Date getDailyProcessDateNextBusinessDay(long programId);
    public Date getDailyProcessDatePreviousBusinessDay(long programId);
    
    //============================================================================
	//
    // DailyProcessFileActionType
	//
    //============================================================================
    public List<DailyProcessFileActionType> getDailyProcessFileActionTypeList();

    //============================================================================
	//
    // DailyProcessFiles
	//
    //============================================================================
    public List<DailyProcessFile> getDailyProcessFiles(long stepId);
    
    //============================================================================
	//
    // DailyProcessLock
	//
    //============================================================================
    public DailyProcessLock getDailyProcessLock(long programId);
    public void dailyProcessLockAquire(long programId, String userName, long versionNumber, String details);
    public void dailyProcessLockRelease(long programId, String userName, long lockNumber, String details);
    
    //============================================================================
	//
    // DailyProcessOperationType
	//
    //============================================================================
    public List<DailyProcessOperationType> getDailyProcessOperationTypeList();

    //============================================================================
	//
    // DailyProcessStatus
	//
    //============================================================================
    public List<DailyProcessStatus> getDailyProcessStatusList();

    //============================================================================
	//
    // DailyProcessSteps
	//
    //============================================================================
    public List<DailyProcessStep> getDailyProcessStepList();
    public List<ViewDailyProcessSteps> getDailyProcessStepList(long programId, long sourceInstitutionId, Date businessDate);
    public List<ViewDailyProcessSteps> GetFullDailyAuditLog(long programId, Date businessDate);
	//============================================================================
	//
    // DailyProcessType
	//
    //============================================================================
    public List<DailyProcessType> getDailyProcessTypeList();

    //============================================================================
	//
    // DepositInstitutions
	//
    //============================================================================
    public List<DepositInstitution> getDepositInstitutionList();
    public Map<String,ViewDepositInstitutionMailingAddress> getDepositInstitutionMailingAddressMap();

	//============================================================================
	//
    // Forecast
    //
	//============================================================================
    public List<ViewForecastBalances> getForecastBalances(String databaseName, long programId, Date businessDate, boolean includeZeroBalances);
    
    //============================================================================
	//
    // MailingCountries
	//
    //============================================================================    
	public List<MailingCountry> getMailingCountryList();

    //============================================================================
	//
	// MailingStates
	//
	//============================================================================    
	public List<MailingState> getMailingStateList();

	//============================================================================
	//
	// Products
	//
	//============================================================================
    public List<Product> getProductList(long programId, long sourceInstitutionId);
    public List<ViewProductMapping> getProductMappingList(long sourceInstitutionId);

    //============================================================================
	//
    // ProgramDepositAccounts
    //
	//============================================================================
    public List<ViewProgramBankList> getProgramBankList(String databaseName, long programId);
    public List<ViewProgramBankList> getProgramBankList(String databaseName, long programId, Date businessDate);
    public List<ViewProgramDepositAccountList> getProgramDepositAccountList(String databaseName, long programId);
    public List<ViewProgramDepositAccountList> getNonZeroBalanceProgramDepositAccountList(String databaseName, long programId, Date businessDate);

	//============================================================================
	//
    // Programs
    //
	//============================================================================    
    public List<Program> getProgramList();
    public List<Program> getProgramListForChecklist();
    public void updateProgramBusinessDate( long programId, Date newDate );

    public List<Processing_Directory> getProgramWorkingDirList(long programId, boolean isPrimary);
    public List<Processing_Directory> getProgramArchiveDirList(long programId, boolean isPrimary);
    public String getProgramWorkingDirPrimary(long programId);
    public String getProgramArchiveDirPrimary(long programId);
    public String getProgramArchiveDirSecondary_01(long programId);  //todo: remove once queue is available
    public String getProgramArchiveDirSecondary_02(long programId);  //todo: remove once queue is available
    
	//============================================================================
    //
	// Services
    //
	//============================================================================
//	public List<Service> getServiceList();
	public void updateServicesPingTime();
    public void updateServicesStartTime();
	
	//============================================================================
	//
	// SourceInstitutions
	//
	//============================================================================
	public List<SourceInstitution> getSourceInstitutionList(); 
	public List<SourceInstitution> getSourceInstitutionList(long programId);
//============================================================================
	//
	// CustomerAccountTransactionDelete
	//
	//============================================================================
	public List<ViewCustomerAccountTransactionDelete> getCustomerAccountTransactionDeleteList(long programId, long sourceInstitutionId, String AccountNumber, String databaseName);
	public List<ViewCustomerAccountTransactionDelete> getCustomerAccountTransactionConfirmList(long programId, long sourceInstitutionId, String databaseName);
	public void updateCustomerAccountTransactionDelete(long programId, long sourceInstitutionId, List<Long> transactionId, String AccountNumber, String databaseName, long userId, boolean isComplete);
	public void updateCustomerAccountTransactionConfirm(long programId, long sourceInstitutionId, List<Long> transactionId, String UpdateType, String databaseName, long userId);
	public List<ViewCustomerAccountList> getAccountList(String databaseName, long programId, long sourceInstitutionId, String accountNumber);
	
}
