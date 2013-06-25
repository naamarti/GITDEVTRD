package com.totalbanksolutions.framework.process;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import com.totalbanksolutions.framework.process.util.ProcessConfig;

public class DMSProcessLauncher extends AbstractProcessLauncher 
{
	private static final String moduleDirectory = System.getenv("DMS_HOME")+"/deployment/application/TBSFramework/scripts";
	private static final String moduleCommand   = "startDMSService.cmd";
	
	public DMSProcessLauncher(ProcessConfig config) 
	{
		super(config, moduleDirectory, moduleCommand);
	}

	@Override
	protected List<String> getParameters() 
	{
		List<String> parameters = new ArrayList<String>();
		String businessDateString = DateFormatUtils.format(this.config.getBusinessDate(), "dd-MMM-yyyy");
		this.addCommand("-BatchId" + this.config.getBatchId());
		this.addCommand("-BusinessDate" + businessDateString);
		this.addCommand("-IsAutoRun" + this.config.isAutoRun());
		this.addCommand("-IsUndo" + this.config.isUndo());
		this.addCommand("-ProgramId" + this.config.getProgramId());
		this.addCommand("-SourceInstitutionId" + this.config.getSourceInstitutionId());
		this.addCommand("-StepId" + this.config.getStepId());	    
		this.addCommand("-StepOrder" + this.config.getStepOrder());	    
		this.addCommand("-UserName" + this.config.getUserName());
		this.addCommand("-VersionNumber" + this.config.getVersionNumber());
		return parameters;
	}
	
	public static void runProcess(ProcessConfig config) 
	{
		DMSProcessLauncher process = new DMSProcessLauncher(config);
		process.start();
	}

	public static void runProcessNonBlocking(ProcessConfig config) 
	{
		DMSProcessLauncher process = new DMSProcessLauncher(config);
		process.startNonBlocking();
	}

}
