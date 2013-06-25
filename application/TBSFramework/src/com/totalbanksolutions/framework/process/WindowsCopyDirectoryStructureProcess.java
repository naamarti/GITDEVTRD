package com.totalbanksolutions.framework.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.file.IOUtility;

public class WindowsCopyDirectoryStructureProcess
{
	protected static final Log log = LogFactory.getLog(WindowsCopyDirectoryStructureProcess.class);
	
	// NOTE: This calls Windows ROBOCOPY command, which is a tool for keeping dirs in sync. 
	// It only copies files if timestamps or sizes have changed. fileName can be name or wildcards.
	// * No error if destDir does not exist. New dir will be created.
	// * No error if fileName (or wildcard) does not exist. (optional filename parameter) 

	public static boolean runProcess(String sourceDir, String destDir) throws Exception 
	{
		boolean isSuccess = true;
		sourceDir = IOUtility.getDirNameWithPathSepSuffix(sourceDir);
		destDir = IOUtility.getDirNameWithPathSepSuffix(destDir);
		if(!IOUtility.directoryExists(sourceDir))
		{
			throw new RuntimeException("Source directory '" + sourceDir + "' does not exist!");
		}

		ProcessMessageList messageList = new ProcessMessageList();
		ProcessBuilder launcher = new ProcessBuilder();
	    Map<String, String> environment = launcher.environment();
	    launcher.redirectErrorStream(true); // output & error can be read from single inputstream
	    launcher.directory(new File(System.getenv("DMS_HOME")+"/deployment/application/TBSFramework/scripts"));
	    environment.put("name", "var");
	    
		List<String> commands = new ArrayList<String>();
		commands.add("cmd.exe");
		commands.add("/c");
		commands.add("startCopyDirStructure.cmd");
	    commands.add(sourceDir);
	    commands.add(destDir);
		launcher.command(commands);
	    
	    Process p = launcher.start(); // And launch a new process
	    ProcessStreamHandler outputStreamHandler = new ProcessStreamHandler(p.getInputStream(), messageList);
	    outputStreamHandler.start();

	    // The process should be done now, but wait to be sure.
	    p.waitFor();
		log.debug("Process Complete. ExitCode=" + p.exitValue());
		
		// See ROBOCOPY documentation. Exit codes >= 8 are failures.
		if(p.exitValue() >= 8) 
		{
			isSuccess = false;
			String errorString = getErrorString(messageList);
			log.debug("Process Failed with Error(s): " + errorString);
			throw new RuntimeException(errorString);
		}

		return isSuccess;
	}
	
	private static String getErrorString(ProcessMessageList outputMessageCallback)
	{
		String error = "";
		boolean foundError = false;
		for(String msg : outputMessageCallback.getMessageList())
		{
			if(msg.contains("ERROR")) foundError = true;
			if(foundError && (msg.trim().length() == 0))
			{
				break; // once start reading error lines, stop at 1st blank line
			}
			if(foundError)
			{
				error += msg + "; ";
			}

		}
		return error;
	}

	// FOR TESTING
	public static void main(String args[])
	{
		boolean isSuccess = false;
		String sourceDir = System.getenv("DMS_HOME")+"/data/TestFileIO/TestMoveTo/";
		String destDir = System.getenv("DMS_HOME")+"/data/TestFileIO/TestCopyDirStruct/";
		
		try
		{
			isSuccess = runProcess(sourceDir, destDir);
		}
		catch(Exception e)
		{
			String errorMessage = "Processing Failed during runProcess. " + ExceptionUtils.getMessage(e);
			String errorStack = ExceptionUtils.getStackTrace(e);
			log.error(errorMessage + "\n" + errorStack);
		}
		log.debug("SUCCESS=" + isSuccess);
	}

}
