package com.totalbanksolutions.framework.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.process.util.ProcessConfig;

public abstract class AbstractProcessLauncher {

	protected abstract List<String>  getParameters();
	
	protected static final           Log log = LogFactory.getLog(AbstractProcessLauncher.class);
	protected ProcessConfig          config = new ProcessConfig();
	private String                   moduleDirectory;
	private List<String>             commands = new ArrayList<String>();
	private List<String>             parameters = new ArrayList<String>();
	
	public AbstractProcessLauncher(ProcessConfig config, String moduleDirectory, String moduleCommand) 
	{
		super();
		this.config = config;
		this.moduleDirectory = moduleDirectory;
		this.commands.add("cmd.exe");
		this.commands.add("/c");
		this.commands.add(moduleCommand);
		getParameters();
		for(String p : parameters)
		{
			this.commands.add(p);
		}
	}

	protected void startNonBlocking()
	{
		ProcessWorker worker = new ProcessWorker();
		Thread t = new Thread(worker);
		t.setName("ProcessWorkerThread");
		t.start();		
	}

	protected void addCommand(String param)
	{
		this.commands.add(param);
	}

	protected void start()
	{
		ProcessBuilder launcher = new ProcessBuilder();
	    Map<String, String> environment = launcher.environment();
	    launcher.redirectErrorStream(true); // output & error can be read from single inputstream
	    launcher.directory(new File(this.moduleDirectory));
	    environment.put("name", "var");
		launcher.command(commands);
	    
	    try
	    {
			Process p = launcher.start(); // And launch a new process
		    BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    String line;
		    while ((line = output.readLine()) != null)
		    	log.debug(line);
	
		    // The process should be done now, but wait to be sure.
		    p.waitFor();
			log.debug("Process Complete. ExitCode=" + p.exitValue());
		    if(p.exitValue() != 0)
		    {
		    	//isSuccess = false;
		    	String failureMessage = "An unexpected error prevented this step from completing.  Please contact Systems for support.";
		    	this.config.getDS().util.updateDailyProcessBatchStatusRunningToFailed(this.config.getBatchId(), this.config.getUserName(), failureMessage);
		    }		
	    }
	    catch(Exception e)
	    {
			log.error(ExceptionUtils.getStackTrace(e));
	    	throw new RuntimeException(e);
	    }
	}
	
	private class ProcessWorker implements Runnable
	{
		public void run()
		{
		    start();
		}
	}
	
	
}