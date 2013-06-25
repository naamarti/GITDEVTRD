package com.totalbanksolutions.framework.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.file.IOUtility;

public class WindowsAppendFileProcess
{
	protected static final Log log = LogFactory.getLog(WindowsAppendFileProcess.class);
	
	/**
	 *  NOTE: This calls the Windows TYPE command to effectively append one file to another.
	 *  For example, TYPE filename2 >> filename1 will append the contents of filename2
	 *  at the end of filename1
	 *  
	 *  The Windows COPY command was initially used for this task, but for some reason it 
	 *  was ending the appended content with some special character in addition to a carriage 
	 *  return and line feed character.  This character was offsetting the contents of the 
	 *  file, which would most likely cause problems with Sungard loading the file.
	 *  
	 */
	
	public static boolean runProcess(String fileOneDir, String fileOneName, String fileTwoDir, String fileTwoName) throws Exception 
	{
		boolean isSuccess = true;
		if(!IOUtility.directoryExists(fileOneDir))
		{
			throw new RuntimeException("Source directory '" + fileOneDir + "' does not exist!");
		}
		if(!IOUtility.fileExists(fileOneDir + fileOneName))
		{
			throw new RuntimeException("Source file '" + fileOneDir + fileOneName + "' does not exist!");
		}
		if(!IOUtility.directoryExists(fileTwoDir))
		{
			throw new RuntimeException("Source directory '" + fileTwoDir + "' does not exist!");
		}
		if(!IOUtility.fileExists(fileTwoDir + fileTwoName))
		{
			throw new RuntimeException("Source file '" + fileTwoDir + fileTwoName + "' does not exist!");
		}

		String windowsFileOne = IOUtility.getWindowsAbsolutePath(fileOneDir + fileOneName);
		String windowsFileTwo = IOUtility.getWindowsAbsolutePath(fileTwoDir + fileTwoName);
		
		ProcessMessageList messageList = new ProcessMessageList();
		ProcessBuilder launcher = new ProcessBuilder();
	    Map<String, String> environment = launcher.environment();
	    launcher.redirectErrorStream(true); // output & error can be read from single inputstream
	    launcher.directory(new File(System.getenv("DMS_HOME")+"/deployment/application/TBSFramework/scripts"));
	    environment.put("name", "var");
	    
		List<String> commands = new ArrayList<String>();
		commands.add("cmd.exe");
		commands.add("/c");
		commands.add("startAppendFile.cmd");
	    commands.add(windowsFileOne);
	    commands.add(windowsFileTwo);
	    launcher.command(commands);
	    
	    Process p = launcher.start(); // And launch a new process
	    ProcessStreamHandler outputStreamHandler = new ProcessStreamHandler(p.getInputStream(), messageList);
	    outputStreamHandler.start();

	    // The process should be done now, but wait to be sure.
	    p.waitFor();
		log.debug("Process Complete. ExitCode=" + p.exitValue());
		
		if(p.exitValue() > 0) 
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
		//String sourceDir = System.getenv("DMS_HOME")+"/data/";
		String sourceDir = "C:\\Users\\dmerkel\\Desktop\\penson\\";
		String destDir = "C:\\Users\\dmerkel\\Desktop\\penson\\";
		String fileNameOne = "TBSTOP3BAL_app";
		String fileNameTwo = "pen_TBSTOP3BAL";
		
		try
		{
			isSuccess = runProcess(sourceDir, fileNameOne, destDir, fileNameTwo);
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
