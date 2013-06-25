package com.totalbanksolutions.framework.process;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.time.DateFormatUtils;
import com.totalbanksolutions.framework.process.util.ProcessConfig;

public class FileIOProcessLauncher extends AbstractProcessLauncher 
{
	private static final String moduleDirectory = System.getenv("DMS_HOME")+"/deployment/application/TBSFramework/scripts";
	private static final String moduleCommand   = "startFileIOService.cmd";
	
	public FileIOProcessLauncher(ProcessConfig config) 
	{
		super(config, moduleDirectory, moduleCommand);
	}

	@Override
	protected List<String> getParameters() 
	{
		List<String> parameters = new ArrayList<String>();
		String businessDateString = DateFormatUtils.format(this.config.getBusinessDate(), "dd-MMM-yyyy");
		this.addCommand("-BusinessDate" + businessDateString);
		this.addCommand("-StepId" + this.config.getStepId());	    
		this.addCommand("-BatchId" + this.config.getBatchId());
		this.addCommand("-UserName" + this.config.getUserName());
		return parameters;
	}
	
	public static void runProcess(ProcessConfig config) 
	{
		FileIOProcessLauncher process = new FileIOProcessLauncher(config);
		process.start();
	}

	public static void runProcessNonBlocking(ProcessConfig config) 
	{
		FileIOProcessLauncher process = new FileIOProcessLauncher(config);
		process.startNonBlocking();
	}
 
}
