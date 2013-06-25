package com.totalbanksolutions.framework.process;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.totalbanksolutions.framework.process.util.ProcessConfig;

public class ReportGeneratorProcessLauncher extends AbstractProcessLauncher 
{
	private static final String moduleDirectory = System.getenv("DMS_HOME")+"/deployment/application/TBSFramework/scripts";
	private static final String moduleCommand   = "startReportGeneratorService.cmd";
	
	public ReportGeneratorProcessLauncher(ProcessConfig config) 
	{
		super(config, moduleDirectory, moduleCommand);
	}

	@Override
	protected List<String> getParameters() 
	{
		List<String> parameters = new ArrayList<String>();
		String businessDateString = DateFormatUtils.format(this.config.getBusinessDate(), "dd-MMM-yyyy");
		String startBusinessDateString = "";
		if (this.config.getStartBusinessDate() != null)
			startBusinessDateString = DateFormatUtils.format(this.config.getStartBusinessDate(), "dd-MMM-yyyy");
		this.addCommand("-BusinessDate" + businessDateString);
		this.addCommand("-StepId" + this.config.getStepId());
		this.addCommand("-BatchId" + this.config.getBatchId());
		this.addCommand("-UserName" + this.config.getUserName());
		this.addCommand("-ProgramId" + this.config.getProgramId());
		
		if(this.config.getReportId() > 0)
		{
			this.addCommand("-SourceInstitutionId" + this.config.getSourceInstitutionId());
			this.addCommand("-StartBusinessDate" + startBusinessDateString);
			this.addCommand("-ProcessType" + this.config.getProcessType());
			this.addCommand("-ProgramDepositAccountId" + this.config.getProgramDepositAccountId());
			this.addCommand("-FileDir" + this.config.getFileDir());
			this.addCommand("-FileName" + this.config.getFileName());
			this.addCommand("-DepositId" + this.config.getDepositId());
			this.addCommand("-ReportId" + this.config.getReportId());
		}
		return parameters;
	}
	
	public static void runProcess(ProcessConfig config) 
	{
		ReportGeneratorProcessLauncher process = new ReportGeneratorProcessLauncher(config);
		process.start();
	}

	public static void runProcessNonBlocking(ProcessConfig config) 
	{
		ReportGeneratorProcessLauncher process = new ReportGeneratorProcessLauncher(config);
		process.startNonBlocking();
	}
}
