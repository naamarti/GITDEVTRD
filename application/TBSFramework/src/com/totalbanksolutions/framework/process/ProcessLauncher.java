package com.totalbanksolutions.framework.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProcessLauncher {

	protected static final Log log = LogFactory.getLog(ProcessLauncher.class);

	public static boolean runProcess(long programId, long sourceInstitutionId, long processTypeId, long batchId, 
									String userName, Date businessDate, String absolutePath) throws Exception 
	{
		boolean isSuccess = true;
	    ProcessBuilder launcher = new ProcessBuilder();
	    Map<String, String> environment = launcher.environment();
	    launcher.redirectErrorStream(true); // output & error can be read from single inputstream
	    //absolute path = System.getenv("DMS_HOME")+"/deployment/application/TBSFileCopy/scripts"
	    launcher.directory(new File(absolutePath));
	    
	    environment.put("name", "var");
	    
	    String businessDateString = DateFormatUtils.format(businessDate, "dd-MMM-yyyy");

	    List<String> commands = new ArrayList<String>();
	    commands.add("cmd.exe");
	    commands.add("/c");
	    commands.add("startProcess.cmd");
	    commands.add("-ProgramId" + programId);
	    commands.add("-SourceInstitutionId" + sourceInstitutionId);
	    commands.add("-ProcessTypeId" + processTypeId);	    
		commands.add("-BatchId" + batchId);
	    commands.add("-UserName" + userName);
	    commands.add("-BusinessDate" + businessDateString);
		launcher.command(commands);
	    
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
	    	isSuccess = false;
	    }
	    return isSuccess;
	  }
}
