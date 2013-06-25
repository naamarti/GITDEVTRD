package com.totalbanksolutions.framework.test;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.DailyProcessOperationType;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessStatus;
import com.totalbanksolutions.framework.bean.database.view.ViewDailyProcessSteps;
import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.dataservice.util.DataServiceManager;
import com.totalbanksolutions.framework.process.DMSProcessLauncher;
import com.totalbanksolutions.framework.process.FileLoaderProcessLauncher;
import com.totalbanksolutions.framework.process.FileWriterProcessLauncher;
import com.totalbanksolutions.framework.process.FileIOProcessLauncher;
import com.totalbanksolutions.framework.process.ReportGeneratorProcessLauncher;
import com.totalbanksolutions.framework.process.util.ProcessConfig;
import com.totalbanksolutions.framework.util.AppUtility;

/**
 * 
 * This class will run all the steps for the specified Program and SourceInstitution.
 * It starts with the current date & step that is next to run, as shown in the DMS UI.
 * It stops after processing thru the EndDate specified. 
 * If EndAtStep is specified, it will stop after processing that step on the EndDate. 
 * 
 */
public class TestReloader 
{
	protected final Log log = LogFactory.getLog(getClass());

	private DataService ds = null;
	private long programId = 0;
	private String programCode = "";
	private long sourceInstitutionId = 0;
	private String sourceInstitutionCode = "";
	private String userName = null;
	private Date endDate = null;
	private int endAtStep = 0;
	
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

//	public void setProgramId(long programId) {
//		this.programId = programId;
//	}

	public void setSourceInstitutionCode(String sourceInstitutionCode) {
		this.sourceInstitutionCode = sourceInstitutionCode;
	}

//	public void setSourceInstitutionId(long sourceInstitutionId) {
//		this.sourceInstitutionId = sourceInstitutionId;
//	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEndAtStep(int endAtStep) {
		this.endAtStep = endAtStep;
	}

	public void startReload()
	{
		try
		{
			initialize();
			programId = ds.util.getProgramId(programCode);
			sourceInstitutionId = ds.util.getSourceInstitutionIdByCode(sourceInstitutionCode);
			if(programId == 0) throw new RuntimeException("Must set programId parameter!");
			if(sourceInstitutionId == 0) throw new RuntimeException("Must set sourceInstitutionId parameter!");
			if(userName == null) throw new RuntimeException("Must set userName parameter!");
			if(endDate == null) throw new RuntimeException("Must set endDate parameter!");
			
			List<ViewDailyProcessSteps> stepsList = null;
			Date currentDate = this.ds.util.getDailyProcessDateCurrentBusinessDay(this.programId);
			
			while(currentDate.getTime() <= this.endDate.getTime())
			{
				log.debug(currentDate.getTime());

				stepsList = ds.util.getDailyProcessStepList(this.programId, this.sourceInstitutionId, currentDate);
				long stepId = getNextStepToRun(stepsList);
				if(stepId == 0)
				{
					throw new RuntimeException("Cannot determine next step to run.");
				}
				int stepOrder = ds.util.getDailyProcessStepOrder(stepId);
				long processTypeId = ds.util.getDailyProcessStepProcessTypeId(stepId);
				long operationTypeId = ds.util.getDailyProcessTypeOperationTypeId(processTypeId);
				
				ProcessConfig config = new ProcessConfig();
				config.setDS(this.ds);
				config.setProgramId(this.programId);
				config.setSourceInstitutionId(this.sourceInstitutionId);
				config.setBusinessDate(currentDate);
				config.setStepId(stepId);
				config.setUserName(this.userName);
				
				if(operationTypeId == DailyProcessOperationType.Values.DMS_PROCESS.getId())
				{
					runDMSProcess(config);
				}
				else if(operationTypeId == DailyProcessOperationType.Values.FILE_IO.getId())
				{
					runFileIO(config);
				}
				else if(operationTypeId == DailyProcessOperationType.Values.FILE_LOADER.getId())
				{
					runFileLoader(config);
				}
				else if(operationTypeId == DailyProcessOperationType.Values.FILE_WRITER.getId())
				{
					runFileWriter(config);
				}
				else if(operationTypeId == DailyProcessOperationType.Values.MANUAL_CHECKOFF.getId())
				{
					runManualCheckOff(this.programId, this.sourceInstitutionId, currentDate, stepId, this.userName);
				}
				else if(operationTypeId == DailyProcessOperationType.Values.REPORT_WRITER.getId())
				{
					runReportWriterProcess(config);
				}
				else
				{
					throw new RuntimeException("Cannot determine process to run, operationTypeId='" + operationTypeId + "' .");
				}
				
				if(currentDate.getTime() == this.endDate.getTime())
				{
					if(this.endAtStep > 0 && this.endAtStep == stepOrder)
					{
						break;
					}
				}
				currentDate = this.ds.util.getDailyProcessDateCurrentBusinessDay(this.programId);
			}
		}
		catch(Exception e)
		{
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}

	private void initialize()
	{
		this.ds = new DataServiceManager().getDataService();
	}

	private void runFileIO(ProcessConfig config)
	{
		long batchId = ds.util.addDailyProcessBatchAsRunning(config.getBusinessDate(), config.getStepId(), config.getUserName());
		config.setBatchId(batchId);
		FileIOProcessLauncher.runProcess(config);
		checkForErrors(config);
	}

	private void runFileLoader(ProcessConfig config)
	{
		ds.fileLoader.deleteStageBatch(programId, sourceInstitutionId);
		long batchId = ds.util.addDailyProcessBatchAsRunning(config.getBusinessDate(), config.getStepId(), config.getUserName());
		config.setBatchId(batchId);
		FileLoaderProcessLauncher.runProcess(config);
		checkForErrors(config);
	}

	private void runFileWriter(ProcessConfig config)
	{
		ds.fileLoader.deleteStageBatch(programId, sourceInstitutionId);
		long batchId = ds.util.addDailyProcessBatchAsRunning(config.getBusinessDate(), config.getStepId(), config.getUserName());
		config.setBatchId(batchId);
		FileWriterProcessLauncher.runProcess(config);
		checkForErrors(config);
	}
	
	private void runDMSProcess(ProcessConfig config)
	{
		long batchId = ds.util.addDailyProcessBatchAsRunning(config.getBusinessDate(), config.getStepId(), config.getUserName());
		config.setBatchId(batchId);
		DMSProcessLauncher.runProcess(config);
		checkForErrors(config);
	}

	private void runManualCheckOff(long programId, long sourceInstitutionId, Date businessDate, long stepId, String user)
	{
		ds.util.addDailyProcessBatchAsSuccess(businessDate, stepId, user);
	}

	private void runReportWriterProcess(ProcessConfig config)
	{
		long batchId = config.getDS().util.addDailyProcessBatchAsRunning(config.getBusinessDate(), config.getStepId(), config.getUserName());
		config.setBatchId(batchId);
		ReportGeneratorProcessLauncher.runProcessNonBlocking(config);
		checkForErrors(config);
	}
	
	private void checkForErrors(ProcessConfig config)
	{
		long status = config.getDS().util.getDailyProcessBatchStatus(config.getBatchId());
		if (status != DailyProcessStatus.Values.SUCCESS.getId())
		{
			String error = config.getDS().util.getDailyProcessBatchError(config.getBatchId());
			throw new RuntimeException("Step failed, '" + config.getStepId() + "'. " + error);
		}
	}
	
	private long getNextStepToRun(List<ViewDailyProcessSteps> stepList)
	{
		long stepId = 0;
		if(stepList.size() > 0)
		{
			for(ViewDailyProcessSteps item : stepList)
			{
				if(item.isNextStep() == true)
				{
					if(item.isAllowRunProcess() == true)
					{
						stepId = item.getStepId();
					}
					break;
				}
			}
		}
		return stepId;
	}
	
}
