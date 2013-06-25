package com.totalbanksolutions.framework.dataservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.data.CustomerAccountTypeDataSet;
import com.totalbanksolutions.framework.bean.database.data.DailyProcessFileActionTypeDataSet;
import com.totalbanksolutions.framework.bean.database.data.DailyProcessOperationTypeDataSet;
import com.totalbanksolutions.framework.bean.database.data.DailyProcessStatusDataSet;
import com.totalbanksolutions.framework.bean.database.data.DailyProcessStepDataSet;
import com.totalbanksolutions.framework.bean.database.data.DailyProcessTypeDataSet;
import com.totalbanksolutions.framework.bean.database.data.DepositInstitutionDataSet;
import com.totalbanksolutions.framework.bean.database.data.ProgramDataSet;
import com.totalbanksolutions.framework.bean.database.data.SourceInstitutionDataSet;
import com.totalbanksolutions.framework.bean.database.table.AppConfiguration;
import com.totalbanksolutions.framework.bean.database.table.ChallengeQuestion;
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
import com.totalbanksolutions.framework.bean.util.BeanSet;
import com.totalbanksolutions.framework.cache.CacheDataRetrievalMethod;
import com.totalbanksolutions.framework.cache.CacheManager;
import com.totalbanksolutions.framework.dao.UtilDAO;
import com.totalbanksolutions.framework.dao.jdbc.UtilJDBC;
import com.totalbanksolutions.framework.dataservice.util.DataCacheType;
import com.totalbanksolutions.framework.file.FileParamUtility;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountList;
import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountTransactionDelete;

/**
 * =================================================================================================
 * Created:   19 May 2005 VC
 * Modified:  07 Jun 2011 VC  #905:  Multiple MMDAs at single DI - Report Naming Convention
 * Modified:  14 Jul 2011 NAM #443:  Added Detailed checklist Log w/Undo's
 * Modified:  10 Aug 2011 VC  #579:  Create a new checklist for "Universal Reports" that span all program databases
 *            05 Oct 2011 VC  #1022: Add DI Customer Balances Summary report (RG#101) to Universal Reports Checklist
 *            05 Oct 2011 VC  #986:  Add DMS checklist steps for each S.I. in DBTCA to generate Over FDIC reports
 * 			  14 Nov 2011 NAM #1060: Add lookup of the current welcome message by today's date	
 *            22 Mar 2012 NAM #1444: Changed the way we assign Sequence number and broke out how we assess filenames
 *            23 Apr 2012 VC  #966: All Programs / Bank Centric checklist - add First Niagara quarterly report
 *            30 Jul 2012 NAM #1795: updated for FTPArchive_Backup directory
 *            25 Sep 2012 NAM #1869: updated for Working and Archive Primary/Secondary directories
 *            23 Oct 2012 NAM #1949: updated for Sweep Activity Dashboard table
 *
 * Utility data service methods.
 * 
 * =================================================================================================
 */
public class UtilDataService 
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());
    private UtilDAO utilDAO = null;
    private CacheManager cacheManager = null;
    private DataService ds = null;
    
    public UtilDataService (DataSource dataSource, CacheManager cacheManager, DataService ds)
    {
    	this.utilDAO = new UtilJDBC(dataSource);
    	this.cacheManager = cacheManager;    	
    	this.ds = ds;
    }

	public void close()
	{
		this.utilDAO.close();
		this.utilDAO = null;
		this.cacheManager = null;
		this.ds = null;
	}

	//============================================================================
	//
	// AppConfiguration
	//
	//============================================================================
    protected AppConfiguration getAppConfiguration()
    {
       	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return utilDAO.getAppConfiguration(); 
    		}
    	};
    	return (AppConfiguration)cacheManager.get(DataCacheType.APP_CONFIGURATION, retrieveMethod);
    }

    public String getAppConfigEnvironmentName()
    {
    	String environmentName = "TEST";
    	AppConfiguration appConfiguration = this.getAppConfiguration();
    	environmentName = appConfiguration.getEnvironmentName();
    	return environmentName;
    }

    public String getAppConfigVersionNumber()
    {
    	String version = "01.00.00";
    	AppConfiguration appConfiguration = this.getAppConfiguration();
    	version = appConfiguration.getVersionNumber();
    	return version;
    }

    public boolean getAppConfigIsTBSInternalTestMode()
    {
    	return this.getAppConfiguration().isTBSInternalTestMode();
    }
	
    
	//============================================================================
	//
    // Calendar_DMS
    //
	//============================================================================
    public String getWelcomeMessage()
    {
 		Calendar calendarDate = Calendar.getInstance();
    	String dateFormat = "yyyy-MM-dd";
    	SimpleDateFormat formattedDate = new SimpleDateFormat(dateFormat);

    	return utilDAO.getWelcomeMessage(formattedDate.format(calendarDate.getTime()));
    }

	//============================================================================
	//
    // CustomerAccount_Transaction
    //
	//============================================================================
    public ViewTransactionTotals getTransactionTotalsForSI(long programId, long sourceInstitutionId, Date programDate)
    {
	   	String databaseName = getProgramDatabaseName(programId);
    	return utilDAO.getTransactionTotalsForSI(databaseName, sourceInstitutionId, programDate);    	
    }

	//============================================================================
	//
	// CustomerAccount_Types
	//
	//============================================================================
    @SuppressWarnings("unchecked")
	public BeanSet<CustomerAccountType> getCustomerAccountTypeDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new CustomerAccountTypeDataSet(utilDAO.getCustomerAccountTypeList());
    		}
    	};
    	return (BeanSet<CustomerAccountType>)cacheManager.get(DataCacheType.CUSTOMER_ACCOUNT_TYPE_LIST, retrieveMethod);
    }

    public long getCustomerAccountTypeId(String accountType)
    {
    	long id = this.getCustomerAccountTypeDataSet().getIdByName(accountType);
    	return id;
    }
    
    public CustomerAccountType getCustomerAccountType(String accountType)
    {
    	return this.getCustomerAccountTypeDataSet().getBeanByName(accountType);
    }

	//============================================================================
	//
	// DailyProcessBatch
	//
	//============================================================================
    private long addDailyProcessBatch(Date businessDate, long stepId, String userName, long processStatusId, String details )
    {
		DailyProcessBatch batch = new DailyProcessBatch();
		batch.setBusinessDate(businessDate);
		batch.setProgramId(this.getDailyProcessStepProgramId(stepId));
		batch.setSourceInstitutionId(this.getDailyProcessStepSourceInstId(stepId));		
		batch.setStepOrder(this.getDailyProcessStepOrder(stepId));
		batch.setBatchSequenceNumber(0);
		batch.setProcessTypeId(this.getDailyProcessStepProcessTypeId(stepId));
		batch.setIsLateProcess(this.getDailyProcessStepIsLateProcess(stepId));
		batch.setProcessStatusId(processStatusId);
		batch.setDetails(details);
		batch.setUserName(userName);
		long batchId = utilDAO.addDailyProcessBatch(batch);
		return batchId;
    }

    public long addDailyProcessBatchAsRunning(Date businessDate, long stepId, String userName)
    {
		return addDailyProcessBatchAsRunning(businessDate, stepId, userName, "");
    }

    public long addDailyProcessBatchAsRunning(Date businessDate, long stepId, String userName, String details)
    {
		long processStatusId = DailyProcessStatus.Values.RUNNING.getId();
		return addDailyProcessBatch(businessDate, stepId, userName, processStatusId, details);
    }

    public long addDailyProcessBatchAsSkipped(Date businessDate, long stepId, String userName)
    {
		long processStatusId = DailyProcessStatus.Values.SKIPPED.getId();
		return addDailyProcessBatch(businessDate, stepId, userName, processStatusId, "Skipped");
    }

    public long addDailyProcessBatchAsSuccess(Date businessDate, long stepId, String userName)
    {
		long processStatusId = DailyProcessStatus.Values.SUCCESS.getId();
		return addDailyProcessBatch(businessDate, stepId, userName, processStatusId, "Done");
    }

	public void deleteDailyProcessBatchAsSkipped(long batchId)
	{
		long statusId = DailyProcessStatus.Values.SKIPPED.getId();
    	utilDAO.deleteDailyProcessBatch(batchId, statusId);
	}

	public String getDailyProcessBatchError(long batchId)
	{
		return utilDAO.getDailyProcessBatchError(batchId);
	}

	public Long getDailyProcessBatchStatus(long batchId)
	{
		return utilDAO.getDailyProcessBatchStatus(batchId);
	}
	
    public void undoDailyProcessBatch(long programId, long sourceInstitutionId, String userName, int stepOrder, long lockNumber)
    {
    	String programDatabaseName = getProgramDatabaseName(programId);
    	utilDAO.undoDailyProcessBatch(programDatabaseName, programId, sourceInstitutionId, userName, stepOrder, lockNumber);
    }
    
	public void updateDailyProcessBatchDetails(long batchId, String fileHeaderTimestamp, int totalRecords, String batchDetails)
	{
		utilDAO.updateDailyProcessBatchDetails(batchId, fileHeaderTimestamp, totalRecords, batchDetails);
	}

	public void updateDailyProcessBatchPercentComplete(long batchId, String batchPercentComplete)
	{
		utilDAO.updateDailyProcessBatchPercentComplete(batchId, batchPercentComplete);
	}

	public void updateDailyProcessBatchStatusRunningToCanceled(long batchId, String userName)
	{
		long fromProcessStatusId = DailyProcessStatus.Values.RUNNING.getId();
		long toProcessStatusId = DailyProcessStatus.Values.CANCELED.getId();
		utilDAO.updateDailyProcessBatchStatus(batchId, fromProcessStatusId, toProcessStatusId, userName);
	}
	
	public void updateDailyProcessBatchStatusRunningToFailed(long batchId, String userName, String errorDetails)
	{
		long fromProcessStatusId = DailyProcessStatus.Values.RUNNING.getId();
		long toProcessStatusId = DailyProcessStatus.Values.FAILED.getId();
		utilDAO.updateDailyProcessBatchStatus(batchId, fromProcessStatusId, toProcessStatusId, userName, errorDetails);
	}

	public void updateDailyProcessBatchStatusRunningToSuccess(long batchId, String userName)
	{
		long fromProcessStatusId = DailyProcessStatus.Values.RUNNING.getId();
		long toProcessStatusId = DailyProcessStatus.Values.SUCCESS.getId();
		utilDAO.updateDailyProcessBatchStatus(batchId, fromProcessStatusId, toProcessStatusId, userName);
	}

	public void updateDailyProcessBatchStatusRunningToSuccess(long batchId, String userName, String details)
	{
		long fromProcessStatusId = DailyProcessStatus.Values.RUNNING.getId();
		long toProcessStatusId = DailyProcessStatus.Values.SUCCESS.getId();
		utilDAO.updateDailyProcessBatchStatus(batchId, fromProcessStatusId, toProcessStatusId, userName, details);
	}

	public boolean isChecklistAtBeginning(long programId)
	{
		return utilDAO.isChecklistAtBeginning(programId);
	}
	
	public boolean isRollDateComplete(long programId)
	{
		return utilDAO.isRollDateComplete(programId);
	}
	
	//============================================================================
	//
	// DailyProcessDate
	//
	//============================================================================
    public DailyProcessDate getDailyProcessDate(long programId, Date calendarDate)
    {
    	return utilDAO.getDailyProcessDate(programId, calendarDate);
    }

    public Date getDailyProcessDateCurrentBusinessDay(long programId)
    {
    	return utilDAO.getDailyProcessDateCurrentBusinessDay(programId);
    }
    
    public Date getDailyProcessDateMonthStart(long programId, Date CalendarDate)
    {
    	return utilDAO.getDailyProcessDateMonthStart(programId, CalendarDate);
    }
    
    public Date getDailyProcessDateNextBusinessDay(long programId)
    {
    	return utilDAO.getDailyProcessDateNextBusinessDay(programId);
    }

    public Date getDailyProcessDatePreviousBusinessDay(long programId)
    {
    	return utilDAO.getDailyProcessDatePreviousBusinessDay(programId);
    }

	//============================================================================
	//
	// DailyProcessFileActionType
	//
	//============================================================================
    @SuppressWarnings("unchecked")
	public BeanSet<DailyProcessFileActionType> getDailyProcessFileActionTypeDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new DailyProcessFileActionTypeDataSet(utilDAO.getDailyProcessFileActionTypeList());
    		}
    	};
    	return (BeanSet<DailyProcessFileActionType>)cacheManager.get(DataCacheType.DAILY_PROCESS_FILE_ACTION_TYPE_LIST, retrieveMethod);
    }
    
	//============================================================================
	//
	// DailyProcessFiles
	//
	//============================================================================
    
    public void substituteFileSequenceNumbers( DailyProcessFile f )
    {
    	try{
			if(f != null )
			{
				String inputFileDir = f.getInputFileDir();
				String inputFileName = f.getInputFileName();
				String outputFileDir = f.getOutputFileDir();
				String outputFileName = f.getOutputFileName();

				//log.debug("1. inputFileDir : "+ inputFileDir + " | inputFileName : "+ inputFileName);
				inputFileName = FileParamUtility.substituteLatestSequenceNumberParameters(inputFileDir, inputFileName);
				//log.debug("2. inputFileDir : "+ inputFileDir + " | inputFileName : "+ inputFileName);

				//log.debug("1. outputFileDir : "+ outputFileDir + " | outputFileName : "+ outputFileName);
				outputFileName = FileParamUtility.substituteNextSequenceNumberParameters(outputFileDir, outputFileName);
				//log.debug("2. outputFileDir : "+ outputFileDir + " | outputFileName : "+ outputFileName);
				
				f.setInputFileName(inputFileName);
				f.setOutputFileName(outputFileName);			
			}
    	}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
    }

    public List<DailyProcessFile> getDailyProcessFiles( long stepId, Date businessDate )
    {
		long depositInstitutionId = 0;
		return getDailyProcessFiles( stepId, businessDate, depositInstitutionId );
    }
    
    public List<DailyProcessFile> getDailyProcessFiles( long stepId, Date businessDate, long depositInstitutionId )
    {
		DailyProcessStep dailyProcessStep = this.getDailyProcessStepDataSet().getBean( stepId );
    	long programId = dailyProcessStep.getProgramId();
		long sourceInstitutionId = dailyProcessStep.getSourceInstitutionId();
	    return getDailyProcessFiles( stepId, businessDate, programId, sourceInstitutionId, depositInstitutionId );
    }
    
    public List<DailyProcessFile> getDailyProcessFiles( long stepId, Date businessDate, long programId, long sourceInstitutionId, long depositInstitutionId )
    {
		DailyProcessStep dailyProcessStep = this.getDailyProcessStepDataSet().getBean( stepId );
//    	long programId = dailyProcessStep.getProgramId();
//		long sourceInstitutionId = dailyProcessStep.getSourceInstitutionId();
		boolean isLateProcess = dailyProcessStep.isLateProcess();
    	List<DailyProcessFile> fileList = utilDAO.getDailyProcessFiles( stepId );
    	try
    	{
			if( fileList != null && fileList.size() > 0 )
			{
				for( DailyProcessFile f : fileList )
				{
					String inputFileDir = f.getInputFileDir();
					String inputFileName = f.getInputFileName();
					String outputFileDir = f.getOutputFileDir();
					String outputFileName = f.getOutputFileName();
					boolean createMissingOutputDirectory = f.isCreateMissingOutputDir();
					
					inputFileDir = FileParamUtility.getDirectoryWithProperSuffix( inputFileDir );
					outputFileDir = FileParamUtility.getDirectoryWithProperSuffix( outputFileDir );
					
					inputFileDir = this.substituteFileParameters( inputFileDir, stepId, programId, sourceInstitutionId, depositInstitutionId, businessDate, isLateProcess );
			
					//log.debug("1. inputFileDir : "+ inputFileDir + " | inputFileName : "+ inputFileName);
					inputFileName = this.substituteFileParameters( inputFileName, stepId, programId, sourceInstitutionId, depositInstitutionId, businessDate, isLateProcess );
					outputFileName = this.substituteFileParameters( outputFileName, stepId, programId, sourceInstitutionId, depositInstitutionId, businessDate, isLateProcess );
					//log.debug("2. inputFileDir : "+ inputFileDir + " | inputFileName : "+ inputFileName);
	
					outputFileDir = this.substituteFileParameters( outputFileDir, stepId, programId, sourceInstitutionId, depositInstitutionId, businessDate, isLateProcess );
					//log.debug("1. outputFileDir : "+ outputFileDir + " | outputFileName : "+ outputFileName);
	
					
					if( !outputFileDir.equalsIgnoreCase("*") )
					{
						outputFileDir = this.substituteFileParameters( outputFileDir, stepId, programId, sourceInstitutionId, depositInstitutionId, businessDate, isLateProcess );
					}
					else
					{
						outputFileDir = inputFileDir;
					}
					
					if( !outputFileName.equalsIgnoreCase("*") )
					{
						outputFileName = this.substituteFileParameters( outputFileName, stepId, programId, sourceInstitutionId, depositInstitutionId, businessDate, isLateProcess );
					}
					else
					{
						outputFileName = inputFileName;
					}
					
					if( createMissingOutputDirectory && outputFileDir.length() > 0 )
					{
						FileParamUtility.createDirectoryIfNotExists( outputFileDir );
					}
					//log.debug("2. outputFileDir : "+ outputFileDir + " | outputFileName : "+ outputFileName);
					f.setInputFileDir( inputFileDir );
					f.setInputFileName( inputFileName );
					f.setOutputFileDir( outputFileDir );
					f.setOutputFileName( outputFileName );
				}
			}
    	}
		catch( Exception e )
		{
			throw new RuntimeException( e );
		}
		return fileList;
    }

	/*
	 * Substitute file parameters 
	 */
    protected String substituteFileParameters( String file, long stepId, Date businessDate )
    {
		DailyProcessStep dailyProcessStep	= this.getDailyProcessStepDataSet().getBean( stepId );
    	long programId 						= dailyProcessStep.getProgramId();
		long sourceInstitutionId 			= dailyProcessStep.getSourceInstitutionId();
		long depositInstitutionId 			= 0;
		boolean isLateProcess 				= dailyProcessStep.isLateProcess();
		return substituteFileParameters( file, stepId, programId, sourceInstitutionId, depositInstitutionId, businessDate, isLateProcess );
    }
    
	/*
	 * Substitute file parameters 
	 */
    protected String substituteFileParameters( String file, long stepId, long programId, long sourceInstitutionId, long depositInstitutionId, Date businessDate, boolean isLateProcess )
    {
    	String newFile						= file;
		String ftpArchiveDir 				= this.getFtpArchiveDir( programId );
		String ftpArchiveDirBackup			= this.getFtpArchiveDirBackup( programId );
		String programTempDir 				= this.getProgramTempDir( programId );
		String programArchiveDir			= this.getProgramArchiveDirPrimary( programId );
		String programArchiveDirSecondary01	= this.getProgramArchiveDirSecondary01( programId );
		String programArchiveDirSecondary02	= this.getProgramArchiveDirSecondary02( programId );
		String settlementWireOutputDir		= this.getSettlementWireOutputDir( programId );
		String sourceFolderName				= this.getSourceInstitutionFolderName(sourceInstitutionId);
		String sourceInputDir				= this.getSourceInstitutionInputDir(sourceInstitutionId);
		String sourceOutputDir				= this.getSourceInstitutionOutputDir(sourceInstitutionId);
		String processorInputDir			= this.getSourceInstitutionProcessorInputDir(sourceInstitutionId);
		String processorOutputDir			= this.getSourceInstitutionProcessorOutputDir(sourceInstitutionId);
		String reportsOutputDir				= this.getSourceInstitutionReportsOutputDir(sourceInstitutionId);
		String statementProviderOutputDir	= this.getSourceInstitutionStatementProviderOutputDir(sourceInstitutionId);
		Date previousBusinessDate			= this.getDailyProcessDatePreviousBusinessDay(programId);
		DailyProcessDate dailyProcessDate	= this.getDailyProcessDate(programId, businessDate);
		boolean isMonthEnd					= dailyProcessDate.isMonthEnd();
		Date currentDate					= new Date();
		
		newFile = FileParamUtility.substituteFTPArchiveDirParameters(ftpArchiveDir, newFile);
		newFile = FileParamUtility.substituteFTPArchiveDirBackupParameters(ftpArchiveDirBackup, newFile);
		newFile = FileParamUtility.substituteProgramTempDirParameters(programTempDir, newFile);
    	newFile = FileParamUtility.substituteProgramArchiveDirPrimaryParameters(programArchiveDir, newFile);
    	newFile = FileParamUtility.substituteProgramArchiveDirSecondary01Parameters(programArchiveDirSecondary01, newFile);
    	newFile = FileParamUtility.substituteProgramArchiveDirSecondary02Parameters(programArchiveDirSecondary02, newFile);
		newFile = FileParamUtility.substituteProgramWorkingDirPrimaryParameters(ds, programId, newFile);
		
		newFile = FileParamUtility.substituteSettlementWireOutputDirParameters(settlementWireOutputDir, newFile);		
    	newFile = FileParamUtility.substituteSourceInstitutionInputDirParameters(sourceInputDir, newFile);
    	newFile = FileParamUtility.substituteSourceInstitutionOutputDirParameters(sourceOutputDir, newFile);
    	newFile = FileParamUtility.substituteProcessorInputDirParameters(processorInputDir, newFile);
    	newFile = FileParamUtility.substituteProcessorOutputDirParameters(processorOutputDir, newFile);
    	newFile = FileParamUtility.substituteReportsOutputDirParameters(reportsOutputDir, newFile);
    	newFile = FileParamUtility.substituteStatementProviderOutputDirParameters(statementProviderOutputDir, newFile);
    	newFile = FileParamUtility.substituteProgramFolderNameParameters(ds, programId, newFile);
    	newFile = FileParamUtility.substituteProgramCodeParameters(ds, programId, newFile);
    	newFile = FileParamUtility.substituteSourceInstitutionFolderNameParameters(sourceFolderName, newFile);
    	newFile = FileParamUtility.substituteBusinessDateParameters(businessDate, newFile);
    	newFile = FileParamUtility.substitutePreviousBusinessDateParameters(previousBusinessDate, newFile);
    	newFile = FileParamUtility.substituteCurrentDateParameters(currentDate, newFile);
    	newFile = FileParamUtility.substituteLateFileParameters(isLateProcess, newFile);
    	newFile = FileParamUtility.substituteIsMonthEndParameters(isMonthEnd, newFile);
		if( depositInstitutionId > 0 )
		{
			String depositInstitutionName = this.getDepositInstitutionBankNameById( depositInstitutionId );
			newFile = FileParamUtility.substituteDepositInstitutionNameParameters( depositInstitutionName, newFile );
		}
		
    	return newFile;
	}

	//============================================================================
	//
	// DailyProcessLock
	//
	//============================================================================
    public DailyProcessLock getDailyProcessLock(long programId)
    {
    	return utilDAO.getDailyProcessLock(programId);
    }

    public void dailyProcessLockAquire(long programId, String userName, long versionNumber, String details)
    {
    	utilDAO.dailyProcessLockAquire(programId, userName, versionNumber, details);
    }

    public void dailyProcessLockRelease(long programId, String userName, long lockNumber, String details)
    {
    	utilDAO.dailyProcessLockRelease(programId, userName, lockNumber, details);
    }
    
	//============================================================================
	//
	// DailyProcessOperationType
	//
	//============================================================================
    @SuppressWarnings("unchecked")
	public BeanSet<DailyProcessOperationType> getDailyProcessOperationTypeDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new DailyProcessOperationTypeDataSet(utilDAO.getDailyProcessOperationTypeList());
    		}
    	};
    	return (BeanSet<DailyProcessOperationType>)cacheManager.get(DataCacheType.DAILY_PROCESS_OPERATION_TYPE_LIST, retrieveMethod);
    }
        
	//============================================================================
	//
	// DailyProcessStatus
	//
	//============================================================================
	@SuppressWarnings("unchecked")
	public BeanSet<DailyProcessStatus> getDailyProcessStatusDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new DailyProcessStatusDataSet(utilDAO.getDailyProcessStatusList());
    		}
    	};
    	return (BeanSet<DailyProcessStatus>)cacheManager.get(DataCacheType.DAILY_PROCESS_STATUS_LIST, retrieveMethod);
    }

	//============================================================================
	//
	// DailyProcessStep
	//
	//============================================================================
    @SuppressWarnings("unchecked")
	public BeanSet<DailyProcessStep> getDailyProcessStepDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new DailyProcessStepDataSet(utilDAO.getDailyProcessStepList());
    		}
    	};
    	return (BeanSet<DailyProcessStep>)cacheManager.get(DataCacheType.DAILY_PROCESS_STEP_LIST, retrieveMethod);
    }

    public List<ViewDailyProcessSteps> GetFullDailyAuditLog(long programId, Date businessDate)
    {
    	return utilDAO.GetFullDailyAuditLog(programId, businessDate);
    }
    
    public List<ViewDailyProcessSteps> getDailyProcessStepList(long programId, long sourceInstitutionId, Date businessDate)
    {
    	return utilDAO.getDailyProcessStepList(programId, sourceInstitutionId, businessDate);
    }

    public String getDailyProcessStepDatePattern(long stepId)
    {
    	DailyProcessStep step = this.getDailyProcessStepDataSet().getBean(stepId);
    	return step.getDatePattern();
    }
    
    public String getDailyProcessStepErrorReportTimeout(long stepId)
    {
    	DailyProcessStep step = this.getDailyProcessStepDataSet().getBean(stepId);
    	return step.getErrorReportTimeout();
    }
    
    public boolean getDailyProcessStepIsLateProcess(long stepId)
    {
    	DailyProcessStep step = this.getDailyProcessStepDataSet().getBean(stepId);
    	return step.isLateProcess();
    }
    
    public int getDailyProcessStepOrder(long stepId)
    {
    	DailyProcessStep step = this.getDailyProcessStepDataSet().getBean(stepId);
    	return step.getStepOrder();
    }

    public long getDailyProcessStepProcessTypeId(long stepId)
    {
    	DailyProcessStep step = this.getDailyProcessStepDataSet().getBean(stepId);
    	return step.getProcessTypeId();
    }

    public long getDailyProcessStepProgramId(long stepId)
    {
    	DailyProcessStep step = this.getDailyProcessStepDataSet().getBean(stepId);
    	return step.getProgramId();
    }

    public long getDailyProcessStepSourceInstId(long stepId)
    {
    	DailyProcessStep step = this.getDailyProcessStepDataSet().getBean(stepId);
    	return step.getSourceInstitutionId();
    }
    
    public String getDailyProcessStepStartTime(long stepId)
    {
    	DailyProcessStep step = this.getDailyProcessStepDataSet().getBean(stepId);
    	return step.getStartTime();
    }

    public String getDailyProcessStepTimeout(long stepId)
    {
    	DailyProcessStep step = this.getDailyProcessStepDataSet().getBean(stepId);
    	return step.getTimeout();
    }
    
    public String getDailyProcessStepTrailerPattern(long stepId)
    {
    	DailyProcessStep step = this.getDailyProcessStepDataSet().getBean(stepId);
    	return step.getTrailerPattern();
    }
    
	//============================================================================
	//
	// DailyProcessType
	//
	//============================================================================
    @SuppressWarnings("unchecked")
	public BeanSet<DailyProcessType> getDailyProcessTypeDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new DailyProcessTypeDataSet(utilDAO.getDailyProcessTypeList());
    		}
    	};
    	return (BeanSet<DailyProcessType>)cacheManager.get(DataCacheType.DAILY_PROCESS_TYPE_LIST, retrieveMethod);
    }

    public long getDailyProcessTypeId(String processTypeName)
    {
    	DailyProcessType processType = this.getDailyProcessTypeDataSet().getBeanByName(processTypeName);
    	return processType.getProcessTypeId();
    }

    public String getDailyProcessTypeName(long processTypeId)
    {
    	DailyProcessType processType = this.getDailyProcessTypeDataSet().getBean(processTypeId);
    	return processType.getProcessType();
    }

    public long getDailyProcessTypeOperationTypeId(long processTypeId)
    {
    	DailyProcessType processType = this.getDailyProcessTypeDataSet().getBean(processTypeId);
    	return processType.getOperationTypeId();
    }

	//============================================================================
	//
	// DepositInstitutions
	//
	//============================================================================
    @SuppressWarnings("unchecked")
	public BeanSet<DepositInstitution> getDepositInstitutionDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new DepositInstitutionDataSet(getDepositInstitutionList());
    		}
    	};
    	return (BeanSet<DepositInstitution>)cacheManager.get(DataCacheType.DEPOSIT_INSTITUTION_SET, retrieveMethod);
    }
    
	@SuppressWarnings("unchecked")
	public List<DepositInstitution> getDepositInstitutionList()
	{
		CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
			public Object retrieveData() {
				return utilDAO.getDepositInstitutionList(); 
			}
		};
		return (List<DepositInstitution>)cacheManager.get(DataCacheType.DEPOSIT_INSTITUTION_LIST, retrieveMethod);
	}

    public String getDepositInstitutionBankCodeById(long bankId)
    {
    	DepositInstitution depositInstitution = (DepositInstitution)this.getDepositInstitutionDataSet().getBean(bankId);
    	return depositInstitution.getCode();
    }
    
    public String getDepositInstitutionBankNameById(long bankId)
    {
    	DepositInstitution depositInstitution = (DepositInstitution)this.getDepositInstitutionDataSet().getBean(bankId);
    	return depositInstitution.getName();
    }

    public String getDepositInstitutionBankCodeByName(String bankName)
    {
    	DepositInstitution depositInstitution = (DepositInstitution)this.getDepositInstitutionDataSet().getBeanByName(bankName);
    	return depositInstitution.getCode();    	
    }

	public String getDepositInstitutionCity(String bankCode)
	{
		String city = "";
		ViewDepositInstitutionMailingAddress item = null;
		Map<String,ViewDepositInstitutionMailingAddress> addressMap = getDepositInstitutionMailingAddressMap();
		if(addressMap.containsKey(bankCode.toUpperCase()))
		{
			item = addressMap.get(bankCode.toUpperCase());
			city = item.getCityName();
		}
		return city;
	}
	
    public String getDepositInstitutionFDICCertNumber(String bankCode)
    {
    	DepositInstitution depositInstitution = (DepositInstitution)this.getDepositInstitutionDataSet().getBeanByCode(bankCode);
    	return depositInstitution.getFDICCertificateNumber();    	
    }
    
    public long getDepositInstitutionIdByBankCode(String bankCode)
    {
    	DepositInstitution depositInstitution = (DepositInstitution)this.getDepositInstitutionDataSet().getBeanByCode(bankCode);
    	return depositInstitution.getId();
    }

    @SuppressWarnings("unchecked")
	public Map<String,ViewDepositInstitutionMailingAddress> getDepositInstitutionMailingAddressMap()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return utilDAO.getDepositInstitutionMailingAddressMap(); 
    		}
    	};
    	return (Map<String,ViewDepositInstitutionMailingAddress>)cacheManager.get(DataCacheType.DEPOSIT_INSTITUTION_MAILINGADDRESS_MAP, retrieveMethod);
    }
    
	public String getDepositInstitutionStateCode(String bankCode)
	{
		String state = "";
		ViewDepositInstitutionMailingAddress item = null;
		Map<String,ViewDepositInstitutionMailingAddress> addressMap = getDepositInstitutionMailingAddressMap();
		if(addressMap.containsKey(bankCode.toUpperCase()))
		{
			item = addressMap.get(bankCode.toUpperCase());
			state = item.getStateCode();
		}
		return state;
	}
	
	//============================================================================
	//
	// Forecast
	//
	//============================================================================	
	public List<ViewForecastBalances> getForecastBalances(long programId, Date businessDate, boolean includeZeroBalances)
	{
    	String databaseName = getProgramDatabaseName(programId);
    	return utilDAO.getForecastBalances(databaseName, programId, businessDate, includeZeroBalances);    	
	}

	//============================================================================
	//
	// MailingCountries
	//
	//============================================================================
    @SuppressWarnings("unchecked")
	public List<MailingCountry> getMailingCountryList()
	{
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() {
    		public Object retrieveData() {
    	    	return utilDAO.getMailingCountryList();
    		}
    	};
    	return (List<MailingCountry>)cacheManager.get(DataCacheType.MAILING_COUNTRY_LIST, retrieveMethod);
	}

	//============================================================================
	//
	// MailingStates
	//
	//============================================================================
    @SuppressWarnings("unchecked")
	public List<MailingState> getMailingStateList()
	{
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() {
    		public Object retrieveData() {
    	    	return utilDAO.getMailingStateList();
    		}
    	};
    	return (List<MailingState>)cacheManager.get(DataCacheType.MAILING_STATE_LIST, retrieveMethod);
	}
    
	//============================================================================
	//
	// Products
	//
	//============================================================================
    @SuppressWarnings("unchecked")
	public List<Product> getProductList(final long programId, final long sourceInstitutionId)
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return utilDAO.getProductList(programId, sourceInstitutionId); 
    		}
    	};
    	return (List<Product>)cacheManager.get(DataCacheType.PRODUCT_LIST, retrieveMethod);
    }

    @SuppressWarnings("unchecked")
	public List<ViewProductMapping> getProductMappingList(final long sourceInstitutionId)
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return utilDAO.getProductMappingList(sourceInstitutionId); 
    		}
    	};
    	return (List<ViewProductMapping>)cacheManager.get(DataCacheType.PRODUCT_MAPPING_LIST, retrieveMethod);
    }

    public Product getProductByProductCode(long programId, long sourceInstitutionId, String productCode)
    {
    	Map<String,Product> dataMap = getProductMapByProductCode(programId, sourceInstitutionId);
    	return dataMap.get(productCode.toUpperCase());
    }

    public Product getProductByProductId(long programId, long sourceInstitutionId, long productId)
    {
    	Map<Long,Product> dataMap = getProductMapByProductId(programId, sourceInstitutionId);
    	return dataMap.get(productId);
    }

    @SuppressWarnings("unchecked")
	private Map<String,Product> getProductMapByProductCode(final long programId, final long sourceInstitutionId)
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return retrieveProductMapByProductCode(programId, sourceInstitutionId); 
    		}
    	};
    	return (Map<String,Product>)cacheManager.get(DataCacheType.PRODUCT_MAP_BY_PRODUCTCODE, retrieveMethod);
    }
    
    @SuppressWarnings("unchecked")
	private Map<Long,Product> getProductMapByProductId(final long programId, final long sourceInstitutionId)
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return retrieveProductMapByProductId(programId, sourceInstitutionId); 
    		}
    	};
    	return (Map<Long,Product>)cacheManager.get(DataCacheType.PRODUCT_MAP_BY_PRODUCTID, retrieveMethod);
    }

    /**
     * NOTE: This function will only work for those programs that have a 1-TO-1 mapping of their source 
     * product codes to a TBS product code.  If multiple source product codes are mapped to the same
     * TBS product code ( e.g. RBC ), a RuntimeException will be thrown.
     * 
     * @param tbsProductCode TBS Product code 
     * @return the source product code for which a mapping exists.  An exception if multiple are found
     * 
     */
    public String getSourceProductCodeByProductCode(long programId, long sourceInstitutionId, String tbsProductCode)
    {
    	Map<String,String> dataMap = getSourceProductMapByProductCode(programId, sourceInstitutionId);
    	return dataMap.get(tbsProductCode.toUpperCase());
    }
    
    @SuppressWarnings("unchecked")
	private Map<String,String> getSourceProductMapByProductCode(final long programId, final long sourceInstitutionId)
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return retrieveSourceProductMapByProductCode(programId, sourceInstitutionId); 
    		}
    	};
    	return (Map<String,String>)cacheManager.get(DataCacheType.SOURCEPRODUCT_MAP_BY_PRODUCTCODE, retrieveMethod);
    }
    
    private Map<String,Product> retrieveProductMapByProductCode(long programId, long sourceInstitutionId)
    {
		Map<String,Product> dataMap = new HashMap<String,Product>();
		List<Product> items = getProductList(programId, sourceInstitutionId);
		for(Product item : items)
		{
			String productCode = item.getProductName().toUpperCase();			
			dataMap.put(productCode, item);
		}
		return dataMap;
    }

    private Map<Long,Product> retrieveProductMapByProductId(long programId, long sourceInstitutionId)
    {
		Map<Long,Product> dataMap = new HashMap<Long,Product>();
		List<Product> items = getProductList(programId, sourceInstitutionId);
		for(Product item : items)
		{
			Long productId = item.getProductId();			
			dataMap.put(productId, item);
		}
		return dataMap;
    }

    private Map<String,String> retrieveSourceProductMapByProductCode(long programId, long sourceInstitutionId)
    {
		Map<String,String> dataMap = new HashMap<String,String>();
		List<ViewProductMapping> items = getProductMappingList(sourceInstitutionId);
		for(ViewProductMapping item : items)
		{
			long productId = item.getProductId();
			Product tbsProduct = getProductByProductId(programId, sourceInstitutionId, productId );
	    	String productCode = tbsProduct.getProductName().toUpperCase();			
	    	String sourceProductCode = item.getSourceProductCode();

	    	if(dataMap.containsKey(productCode))
	    	{
	    		throw new RuntimeException("This method is only valid for those SI's that have a 1-to-1 mapping from source product code to TBS product code");
	    	}
	    	dataMap.put(productCode, sourceProductCode);
		}
		return dataMap;
    }
       //============================================================================
	//
	// CustomerAccountTransactionDelete
	//
	//============================================================================
    public List<ViewCustomerAccountTransactionDelete> getCustomerAccountTransactionDeleteList(final long programId, final long sourceInstitutionId,final String AccountNumber )
    {
    	String databaseName = getProgramDatabaseName(programId);
    	return utilDAO.getCustomerAccountTransactionDeleteList(programId, sourceInstitutionId, AccountNumber, databaseName);
    }
    public List<ViewCustomerAccountTransactionDelete> getCustomerAccountTransactionConfirmList(final long programId, final long sourceInstitutionId)
    {
    	String databaseName = getProgramDatabaseName(programId);
    	return utilDAO.getCustomerAccountTransactionConfirmList(programId, sourceInstitutionId, databaseName);
    }
    
    public void updateCustomerAccountTransactionDelete(long programId, long sourceInstitutionId, List<Long>transactionId, String AccountNumber, long userId, boolean isComplete)
    {
    	String databaseName = getProgramDatabaseName(programId);
    	utilDAO.updateCustomerAccountTransactionDelete(programId, sourceInstitutionId, transactionId,AccountNumber, databaseName,userId, isComplete);
    	
    } 
    
    public void updateCustomerAccountTransactionConfirm(long programId, long sourceInstitutionId, List<Long>transactionId, String UpdateType,long userId)
    {
    	String databaseName = getProgramDatabaseName(programId);
    	utilDAO.updateCustomerAccountTransactionConfirm(programId, sourceInstitutionId, transactionId, UpdateType, databaseName, userId);
    	
    }
    
    public List<ViewCustomerAccountList> getAccountList(long programId, long sourceInstitutionId, String accountNumber)
    {	
		String databaseName = getProgramDatabaseName(programId);
		return utilDAO.getAccountList(databaseName, programId, sourceInstitutionId, accountNumber);
    }

	//============================================================================
	//
	// ProgramDepositAccounts
	//
	//============================================================================
    public List<ViewProgramBankList> getProgramBankList(long programId)
    {
    	String databaseName = getProgramDatabaseName(programId);
    	return utilDAO.getProgramBankList(databaseName, programId);    	
    }
    
    public List<ViewProgramBankList> getProgramBankList(long programId, Date businessDate)
    {
    	String databaseName = getProgramDatabaseName(programId);
    	return utilDAO.getProgramBankList(databaseName, programId, businessDate);    	
    }
    
	public List<ViewProgramDepositAccountList> getProgramDepositAccountList(long programId)
    {
    	String databaseName = getProgramDatabaseName(programId);
    	return utilDAO.getProgramDepositAccountList(databaseName, programId);    	
    }
	
	public List<ViewProgramDepositAccountList> getNonZeroBalanceProgramDepositAccountList(long programId, Date businessDate)
    {
    	String databaseName = getProgramDatabaseName(programId);
    	return utilDAO.getNonZeroBalanceProgramDepositAccountList(databaseName, programId, businessDate);    	
    }
	
    //============================================================================
	//
	// Programs
	//
	//============================================================================
    @SuppressWarnings("unchecked")
	public BeanSet<Program> getProgramDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new ProgramDataSet(getProgramListForChecklist());
    		}
    	};
    	return (BeanSet<Program>)cacheManager.get(DataCacheType.PROGRAM_SET, retrieveMethod);
    }

    @SuppressWarnings("unchecked")
	public List<Program> getProgramList()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return utilDAO.getProgramList();
    		}
    	};
    	return (List<Program>)cacheManager.get(DataCacheType.PROGRAM_LIST, retrieveMethod);
    }
    
    @SuppressWarnings("unchecked")
	public List<Program> getProgramListForChecklist()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return utilDAO.getProgramListForChecklist();
    		}
    	};
    	return (List<Program>)cacheManager.get(DataCacheType.PROGRAM_LIST_FOR_CHECKLIST, retrieveMethod);
    }
    
    public long getProgramSourceInstitutionId( long programId )
    {
    	long sourceInstitutionId = ((SourceInstitution)ds.util.getSourceInstitutionList( programId ).get(0)).getId();
    	return sourceInstitutionId;
    }

    public String getProgramArchiveDirPrimary(final long programId)
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() {
    		public Object retrieveData() {
    			return utilDAO.getProgramArchiveDirPrimary(programId);
    		}
    	};
    	return (String)cacheManager.get(DataCacheType.PROGRAM_ARCHIVE_DIRECTORY_PRIMARY, retrieveMethod);
    }
    
    public String getProgramArchiveDirSecondary01(final long programId)
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() {
    		public Object retrieveData() {
    			return utilDAO.getProgramArchiveDirSecondary_01(programId);
    		}
    	};
    	return (String)cacheManager.get(DataCacheType.PROGRAM_ARCHIVE_DIRECTORY_SECONDARY01, retrieveMethod);
    }
    
    public String getProgramArchiveDirSecondary02(final long programId)
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() {
    		public Object retrieveData() {
    			return utilDAO.getProgramArchiveDirSecondary_02(programId);
    		}
    	};
    	return (String)cacheManager.get(DataCacheType.PROGRAM_ARCHIVE_DIRECTORY_SECONDARY02, retrieveMethod);
    }
    
    @SuppressWarnings("unchecked")
	public List<Processing_Directory> getProgramArchiveDirSecondaryList(final long programId)
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() {
    		public Object retrieveData() {
    			return utilDAO.getProgramWorkingDirList(programId, false);
    		}
    	};
    	return (List<Processing_Directory>)cacheManager.get(DataCacheType.PROGRAM_ARCHIVE_DIRECTORY_SECONDARY_LIST, retrieveMethod);
    }
    
    public String getProgramName(long programId)
    {
    	String programName = "";
    	Program program = this.getProgramDataSet().getBean(programId);
    	if(program != null)
    	{
    		programName = program.getProgramName();
    	}    	
    	return programName;
    }

    public String getProgramDatabaseName(long programId)
    {
    	String programDatabaseName = "";
    	Program program = this.getProgramDataSet().getBean(programId);
    	if(program != null)
    	{
    		programDatabaseName = program.getProgramDatabaseName();
    	}    	
    	return programDatabaseName;
    }

    public String getProgramFolderName(long programId)
    {
    	String programFolderName = "";
    	Program program = this.getProgramDataSet().getBean(programId);
    	if(program != null)
    	{
    		programFolderName = program.getProgramFolderName();
    	}    	
    	return programFolderName;
    }

    public String getProgramCode(long programId)
    {
    	String programFolderCode = "";
    	Program program = this.getProgramDataSet().getBean(programId);
    	if(program != null)
    	{
    		programFolderCode = program.getProgramShortCode();
    	}    	
    	return programFolderCode;
    }
    
    public long getProgramId(String internalCode)
    {
    	long programId = 0;
    	Program program = this.getProgramDataSet().getBeanByCode(internalCode);
    	if(program != null)
    	{
    		programId = program.getProgramId();
    	}    	
    	return programId;
    }

    public String getProgramInternalCode(long programId)
    {
    	String internalCode = "";
    	Program program = this.getProgramDataSet().getBean(programId);
    	if(program != null)
    	{
    		internalCode = program.getInternalCode();
    	}    	
    	return internalCode;
    }
    
    public String getProgramModuleName(long programId)
    {
    	String programModuleName = "";
    	Program program = this.getProgramDataSet().getBean(programId);
    	if(program != null)
    	{
    		programModuleName = program.getProgramDatabaseName();
    		programModuleName = StringUtils.removeStart(programModuleName, "TBS_Program_");
    	}    	
    	if(programModuleName.equalsIgnoreCase("Ridge"))
    	{
    		programModuleName = "Broadridge";
    	}
    	return programModuleName;
    }
    
    public String getFtpArchiveDir(long programId)
    {
    	String ftpArchiveDir = "";
    	Program program = this.getProgramDataSet().getBean(programId);
    	if(program != null)
    	{
    		ftpArchiveDir = program.getFtpArchiveDir();
    	}    	
    	return ftpArchiveDir;
    }
	
    public String getFtpArchiveDirBackup(long programId)
    {
    	String ftpArchiveDirBackup = "";
    	Program program = this.getProgramDataSet().getBean(programId);
    	if(program != null)
    	{
    		ftpArchiveDirBackup = program.getFtpArchiveDirBackup();
    	}    	
    	return ftpArchiveDirBackup;
    }
    
    public String getProgramTempDir(long programId)
    {
    	String programTempDir = "";
		programTempDir = System.getenv("DMS_HOME")+"/temp/" + this.getProgramFolderName(programId) + "/";
		return programTempDir;
    }
    
    public String getProgramWorkingDirPrimary(final long programId)
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() {
    		public Object retrieveData() {
    			return utilDAO.getProgramWorkingDirPrimary(programId);
    		}
    	};
    	return (String)cacheManager.get(DataCacheType.PROGRAM_WORKING_DIRECTORY_PRIMARY, retrieveMethod);
    }
    
    @SuppressWarnings("unchecked")
	public List<Processing_Directory> getProgramWorkingDirSecondaryList(final long programId)
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() {
    		public Object retrieveData() {
    			return utilDAO.getProgramWorkingDirList(programId, false);
    		}
    	};
    	return (List<Processing_Directory>)cacheManager.get(DataCacheType.PROGRAM_WORKING_DIRECTORY_SECONDARY_LIST, retrieveMethod);
    }
    
    
    public String getSettlementWireOutputDir(long programId)
    {
    	String programSettlementWireOutputDir = "";
    	Program program = this.getProgramDataSet().getBean(programId);
    	if(program != null)
    	{
    		programSettlementWireOutputDir = program.getSettlementWireOutputDir();
    	}    	
    	return programSettlementWireOutputDir;
    }

    public void updateProgramBusinessDate( long programId, Date newDate )
    {
    	utilDAO.updateProgramBusinessDate(programId, newDate);
    }
    
    //============================================================================
	//
	// Services
	//
	//============================================================================
//	public List<Service> getServiceList()
//	{
//		return utilDAO.getServiceList();
//	}

	public void updateServicesPingTime()
	{
		utilDAO.updateServicesPingTime();
	}

    public void updateServicesStartTime()
	{
		utilDAO.updateServicesStartTime();
	}

	//============================================================================
	//
	// SourceInstitutions
	//
	//============================================================================
    @SuppressWarnings("unchecked")
	public BeanSet<SourceInstitution> getSourceInstitutionDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new SourceInstitutionDataSet(getSourceInstitutionList());
    		}
    	};
    	return (BeanSet<SourceInstitution>)cacheManager.get(DataCacheType.SOURCE_INSTITUTION_SET, retrieveMethod);
    }

    @SuppressWarnings("unchecked")
	public List<SourceInstitution> getSourceInstitutionList()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return utilDAO.getSourceInstitutionList();
    		}
    	};
    	return (List<SourceInstitution>)cacheManager.get(DataCacheType.SOURCE_INSTITUTION_LIST, retrieveMethod);
    }

	public List<SourceInstitution> getSourceInstitutionList(long programId)
	{
		List<SourceInstitution> data = new ArrayList<SourceInstitution>();
		data = utilDAO.getSourceInstitutionList(programId);
		if(this.getProgramInternalCode(programId).equalsIgnoreCase(Program.Values.DBTCA.getId()))
		{
			//Add main entry for DBTCA checklist
			long id = this.getSourceInstitutionIdByCode(SourceInstitution.Values.NONE.getId());
			SourceInstitution si = new SourceInstitution();
			si.setId(id);
			si.setCode("NONE");
			si.setName("(main)");
			data.add(0, si);
		}
		return data;
	}

	public int getSourceInstitutionAccountNumberLength(long sourceInstitutionId)
	{
		int AccountNumberLength = 0;
		SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			AccountNumberLength = source.getAccountNumberLength();
		}
		return AccountNumberLength;
	}
	
	public String getSourceInstitutionCode(long sourceInstitutionId)
	{
		String siCode = "";
		SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			siCode = source.getCode();
		}
		return siCode;
	}
	
	public long getSourceInstitutionDefaultPayoutType(long sourceInstitutionId)
	{
		long defaultPayoutTypeID = 1;
		SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			defaultPayoutTypeID = source.getDefaultPayoutTypeId();
		}
		return defaultPayoutTypeID;
	}

    public String getSourceInstitutionFolderName(long sourceInstitutionId)
    {
    	String folderName = "";
    	SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			folderName = source.getFolderName();
		}
    	return folderName;
    }

    public long getSourceInstitutionIdByCode(String internalCode)
    {
    	long sourceInstitutionId = 0;
    	SourceInstitution source = this.getSourceInstitutionDataSet().getBeanByCode(internalCode);
    	if(source != null)
    	{
    		sourceInstitutionId = source.getId();
    	}    	
    	return sourceInstitutionId;
    }
	
	public long getSourceInstitutionIdByName(String name)
	{
		return getSourceInstitutionDataSet().getIdByName(name);
	}

    public String getSourceInstitutionInputDir(long sourceInstitutionId)
    {
    	String s = "";
    	SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			s = source.getSIInputDir();
		}
    	return s;
    }

    public String getSourceInstitutionInternalCode(long sourceInstitutionId)
    {
    	String internalCode = "";
    	SourceInstitution sourceInstitution = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
    	if(sourceInstitution != null)
    	{
    		internalCode = sourceInstitution.getInternalCode();
    	}    	
    	return internalCode;
    }
	
	public boolean getSourceInstitutionIsPadAccountWhenShort(long sourceInstitutionId)
	{
		boolean isPadAccountWhenShort = false;
		SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			isPadAccountWhenShort = source.isPadAccountWhenShort();
		}
		return isPadAccountWhenShort;
	}

	public boolean getSourceInstitutionIsUseDefaultPayoutTypeAsOverride(long sourceInstitutionId)
	{
		boolean useDefaultPayoutTypeAsOverride = false;
		SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			useDefaultPayoutTypeAsOverride = source.isUseDefaultPayoutTypeAsOverride();
		}
		return useDefaultPayoutTypeAsOverride;
	}

	public boolean getSourceInstitutionIsUseFakeTaxId2(long sourceInstitutionId)
	{
		boolean useFakeTaxId2 = false;
		SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			useFakeTaxId2 = source.isUseFakeTaxId2();
		}
		return useFakeTaxId2;
	}
	
	public String getSourceInstitutionOffsetAccountNumber(long sourceInstitutionId)
	{
		String offsetAccountNumber = "";
		SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			offsetAccountNumber = source.getOffsetAccount();
		}
		return offsetAccountNumber;
	}
	
    public String getSourceInstitutionOutputDir(long sourceInstitutionId)
    {
    	String s = "";
    	SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			s = source.getSIOutputDir();
		}
    	return s;
    }

    public String getSourceInstitutionProcessorInputDir(long sourceInstitutionId)
    {
    	String s = "";
    	SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			s = source.getProcessorInputDir();
		}
    	return s;
    }

    public String getSourceInstitutionProcessorOutputDir(long sourceInstitutionId)
    {
    	String s = "";
    	SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			s = source.getProcessorOutputDir();
		}
    	return s;
    }
    
    public long getSourceInstitutionProgramId(long sourceInstitutionId)
    {
    	long programId = 0;
    	SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
    	if(source != null)
    	{
    		programId = source.getProgramId();
    	}
    	return programId;
    }

    public String getSourceInstitutionReportsOutputDir(long sourceInstitutionId)
    {
    	String s = "";
    	SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			s = source.getReportsOutputDir();
		}
    	return s;
    }

    public String getSourceInstitutionStatementProviderOutputDir(long sourceInstitutionId)
    {
    	String s = "";
    	SourceInstitution source = this.getSourceInstitutionDataSet().getBean(sourceInstitutionId);
		if(source != null)
		{
			s = source.getStatementProviderOutputDir();
		}
    	return s;
    }
    
}
