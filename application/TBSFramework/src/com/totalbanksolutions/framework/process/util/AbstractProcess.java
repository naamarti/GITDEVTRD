package com.totalbanksolutions.framework.process.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.DailyProcessDate;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessStep;
import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.dataservice.util.DataServiceManager;
import com.totalbanksolutions.framework.util.AppUtility;


public abstract class AbstractProcess
{
	protected static final Log log = LogFactory.getLog(AbstractProcess.class);

	public abstract ProcessStatus run(ProcessConfig processConfig);
	
	private 	ProcessConfig 	processConfig = new ProcessConfig();
	private 	String 			failureMessage = "";
	
	// Exit Codes:
	//   0 = Process ran with no unexpected errors. Any logical errors were saved to the database table.
	//   1 = Process failed and errors could not be written to the database table.
	protected void initialize(String args[]) 
	{
    	try
    	{
			if(!initializeAndRunProcess(args))
			{
				log.info("Exit code = 1.");
				System.exit(1);
			}
			log.info("Exit code = 0.");
			System.exit(0);
    	}
    	catch(Throwable t)
    	{
			String errorMessage = ExceptionUtils.getMessage(t);
			String errorStack = ExceptionUtils.getStackTrace(t);
			log.error(errorMessage + "\n" + errorStack);
			log.info("Exit code = 1.");
			System.exit(1);			
    	}
	}

	// Return:
	//   true  = Process ran with no unexpected errors. Any logical errors were saved to the database table.
	//   false = Process failed and errors could not be written to the database table.
	private boolean initializeAndRunProcess(String args[])
	{
		boolean isSuccess = false;
		boolean isAppInitialized = false;
		boolean isDatabaseInitialized = false;
		ProcessStatus returnStatus = new ProcessStatus();
		
		try
		{
			if(initializeDatabase())
			{
				isDatabaseInitialized = true;

				if(initializeApplicationSettings(args))
				{
					isAppInitialized = true;
				}
			}

			if(isAppInitialized)
			{
				try
				{
					returnStatus = run(processConfig);
				}
				catch(Exception e)
				{
					String errorMessage = "Process failed. " + ExceptionUtils.getMessage(e);
					String errorStack = ExceptionUtils.getStackTrace(e);
					log.error(errorMessage + "\n" + errorStack);
					this.failureMessage = errorMessage;
				}
			}

			if(this.failureMessage.length() > 0)
			{
				this.failureMessage = formatErrorText(this.failureMessage);
				log.debug("Process failed with error: " + failureMessage);
			}

			if(isDatabaseInitialized)
			{
				if(processConfig.getBatchId() > 0)
				{
					if(this.failureMessage.length() > 0)
					{
						processConfig.getDS().util.updateDailyProcessBatchStatusRunningToFailed(processConfig.getBatchId(), processConfig.getUserName(), this.failureMessage);
					}
					else
					{
						String details = returnStatus.getDetails();
						if(details.length() == 0) details = "Done";
						processConfig.getDS().util.updateDailyProcessBatchStatusRunningToSuccess(processConfig.getBatchId(), processConfig.getUserName(), details);
					}
					isSuccess = true;
				}
			}
		}
		catch(Exception e)
		{
			String errorMessage = "Processing Failed during runProcess. " + ExceptionUtils.getMessage(e);
			String errorStack = ExceptionUtils.getStackTrace(e);
			log.error(errorMessage + "\n" + errorStack);
		}
		return isSuccess;
	}
	
	private boolean initializeDatabase() 
	{
		boolean isSuccess = false;
		try
		{
			processConfig.setDS( new DataServiceManager().getDataService() );
			isSuccess = true;
		}
		catch(Exception e)
		{
			String errorMessage = "Unable to initialize database. " + ExceptionUtils.getMessage(e);
			String errorStack = ExceptionUtils.getStackTrace(e);
			log.error(errorMessage + "\n" + errorStack);
			this.failureMessage = errorMessage;
		}
		return isSuccess;
	}

	private boolean initializeApplicationSettings(String args[])
	{
		boolean isSuccess = false;
		try
		{
			getApplicationSettingsFromCommandLine(args);

			if(processConfig.getStepId() > 0)
			{
				if(!processConfig.getDS().util.getDailyProcessStepDataSet().contains(processConfig.getStepId()))
				{
					throw new RuntimeException("StepId '" + processConfig.getStepId() + "' does not reference a valid Step.");
				}
				DailyProcessStep step = processConfig.getDS().util.getDailyProcessStepDataSet().getBean(processConfig.getStepId());
				processConfig.setProgramId(step.getProgramId());
				processConfig.setSourceInstitutionId(step.getSourceInstitutionId());
				processConfig.setProcessTypeId(step.getProcessTypeId());
				processConfig.setProcessType(processConfig.getDS().util.getDailyProcessTypeName(processConfig.getProcessTypeId()));
				processConfig.setLateProcessing(step.isLateProcess());
			}
			
			processConfig.setSourceInstitutionCode(processConfig.getDS().util.getSourceInstitutionInternalCode(processConfig.getSourceInstitutionId()));
			
			if (processConfig.getProgramId() > 0)
			{
				processConfig.setProgramCode(processConfig.getDS().util.getProgramInternalCode(processConfig.getProgramId()));							
				DailyProcessDate dailyProcessDate = processConfig.getDS().util.getDailyProcessDate(processConfig.getProgramId(), processConfig.getBusinessDate());
				processConfig.setPeriodEnd(dailyProcessDate.isPeriodEnd());
				processConfig.setIsPeriodStart( dailyProcessDate.isPeriodStart() );
			}
			
			if(processConfig.isAutoGenerateBatchId())
			{
				processConfig.setBatchId(createBatchId());
			}
			isSuccess = true;
			
			log.debug("-------------------------------------------------------------------");
			log.debug("Startup Parameters:");
			log.debug("  BatchId                 =  " + processConfig.getBatchId());
			log.debug("  BusinessDate            =  " + processConfig.getBusinessDate());
			log.debug("  StartBusinessDate       =  " + processConfig.getStartBusinessDate());
			log.debug("  IsAutoRun               =  " + processConfig.isAutoRun());
			log.debug("  IsLateProcessing        =  " + processConfig.isLateProcessing());
			log.debug("  IsPeriodEnd             =  " + processConfig.isPeriodEnd());
			log.debug("  IsPeriodStart           =  " + processConfig.isPeriodStart());
			log.debug("  IsUndo                  =  " + processConfig.isUndo());
			log.debug("  ProcessType             =  " + processConfig.getProcessType());
			log.debug("  ProcessTypeId           =  " + processConfig.getProcessTypeId());
			log.debug("  ProgramCode             =  " + processConfig.getProgramCode());
			log.debug("  ProgramId               =  " + processConfig.getProgramId());
			log.debug("  SourceInstId            =  " + processConfig.getSourceInstitutionId());
			log.debug("  StepId                  =  " + processConfig.getStepId());
			log.debug("  UserName                =  " + processConfig.getUserName());
			log.debug("  VersionNumber           =  " + processConfig.getVersionNumber());
			log.debug("  FileDir                 =  " + processConfig.getFileDir());
			log.debug("  FileName                =  " + processConfig.getFileName());
			log.debug("  DepositId               =  " + processConfig.getDepositId());
			log.debug("  ProgramDepositAccountId =  " + processConfig.getProgramDepositAccountId());
			log.debug("  ReportId                =  " + processConfig.getReportId());
			log.debug("-------------------------------------------------------------------");
		}
		catch(Exception e)
		{
			String errorMessage = "Unable to initialize application settings. " + ExceptionUtils.getMessage(e);
			String errorStack = ExceptionUtils.getStackTrace(e);
			log.error(errorMessage + "\n" + errorStack);
			this.failureMessage = errorMessage;
		}
		return isSuccess;
	}
	
	private void getApplicationSettingsFromCommandLine(String args[])
	{
		try
		{
			for(String arg : args)
			{
				log.debug("Command line argument = " + arg);
				String command = arg;
				String argument = "";
				if(command.startsWith("-BatchId"))
				{
					argument = arg.substring("-BatchId".length());
					if(argument.equalsIgnoreCase("AUTO_GENERATE"))
					{
						processConfig.setAutoGenerateBatchId(true);
					}
					else
					{
						processConfig.setBatchId(Long.parseLong(argument));
					}
				}
				else if(command.startsWith("-BusinessDate"))
				{
					argument = arg.substring("-BusinessDate".length());
					String[] formats = new String[] {"dd-MMM-yyyy", "d-MMM-yyyy", "MM/dd/yyyy"};
					Date d = DateUtils.parseDate(argument, formats);
					processConfig.setBusinessDate(d);
				}
				else if(command.startsWith("-StartBusinessDate"))
				{
					argument = arg.substring("-StartBusinessDate".length());
					String[] formats = new String[] {"dd-MMM-yyyy", "d-MMM-yyyy", "MM/dd/yyyy"};
					Date d = DateUtils.parseDate(argument, formats);
					processConfig.setStartBusinessDate(d);
				}
				else if(command.startsWith("-IsAutoRun"))
				{
					argument = arg.substring("-IsAutoRun".length());
					if(argument.equalsIgnoreCase("TRUE") || argument.equalsIgnoreCase("1"))
					{
						processConfig.setAutoRun(true);
					}
				}
				else if(command.startsWith("-IsUndo"))
				{
					argument = arg.substring("-IsUndo".length());
					if(argument.equalsIgnoreCase("TRUE") || argument.equalsIgnoreCase("1"))
					{
						processConfig.setUndo(true);
					}
				}
				else if(command.startsWith("-ProgramId"))
				{
					argument = arg.substring("-ProgramId".length());
					processConfig.setProgramId(Long.parseLong(argument));
				}
				else if(command.startsWith("-SourceInstitutionId"))
				{
					argument = arg.substring("-SourceInstitutionId".length());
					processConfig.setSourceInstitutionId(Long.parseLong(argument));
				}
				else if(command.startsWith("-StepId"))
				{
					argument = arg.substring("-StepId".length());
					processConfig.setStepId(Long.parseLong(argument));
				}
				else if(command.startsWith("-StepOrder"))
				{
					argument = arg.substring("-StepOrder".length());
					processConfig.setStepOrder(Integer.parseInt(argument));
				}
				else if(command.startsWith("-UserName"))
				{
					argument = arg.substring("-UserName".length());
					processConfig.setUserName(argument);
				}
				else if(command.startsWith("-VersionNumber"))
				{
					argument = arg.substring("-VersionNumber".length());
					processConfig.setVersionNumber(Long.parseLong(argument));
				}
				else if(command.startsWith("-ProgramDepositAccountId"))
				{
					argument = arg.substring("-ProgramDepositAccountId".length());
					processConfig.setProgramDepositAccountId(Long.parseLong(argument));
				}
				else if(command.startsWith("-FileDir"))
				{
					argument = arg.substring("-FileDir".length());
					processConfig.setFileDir(argument);
				}
				else if(command.startsWith("-FileName"))
				{
					argument = arg.substring("-FileName".length());
					processConfig.setFileName(argument);
				}
				else if(command.startsWith("-DepositId"))
				{
					argument = arg.substring("-DepositId".length());
					processConfig.setDepositId(Long.parseLong(argument));
				}
				else if(command.startsWith("-ReportId"))
				{
					argument = arg.substring("-ReportId".length());
					processConfig.setReportId(Long.parseLong(argument));
				}
				else if(command.startsWith("-ProcessType"))
				{
					argument = arg.substring("-ProcessType".length());
					processConfig.setProcessType(argument);
				}
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private long createBatchId()
	{
		long batchId = processConfig.getDS().util.addDailyProcessBatchAsRunning(processConfig.getBusinessDate(), processConfig.getStepId(), processConfig.getUserName());
		return batchId;
	}
	
	protected void getApplicationSettingsFromFile()
	{    
		try
		{
//			String debugMode = AppUtility.getApplicationProperties().getProperty("DEBUG_MODE");
//			if(debugMode != null && debugMode.length() > 0)
//			{
//				this.debugMode = Boolean.parseBoolean(debugMode);
//			}
		} 
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	private String formatErrorText(String errorText)
	{
		String error = StringUtils.remove(errorText, "RuntimeException:");
		error = StringUtils.remove(error, "java.lang.");
		error = StringUtils.remove(error, "java.io.");
		error = StringUtils.remove(error, "com.microsoft.sqlserver.jdbc.SQLServerException:");
		error = StringUtils.remove(error, "java.sql.BatchUpdateException:");
		return error;
	}
	
	public void saveBatchProcessDetailsToDatabase(String details)
	{
		processConfig.getDS().util.updateDailyProcessBatchStatusRunningToSuccess(processConfig.getBatchId(), processConfig.getUserName(), details);
	}
}
