package com.totalbanksolutions.framework.process;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProcessStreamHandler extends Thread
{
	protected static final Log log = LogFactory.getLog(ProcessStreamHandler.class);

	InputStream inputStream;
	ProcessMessageList messageList;
	
	ProcessStreamHandler(InputStream inputStream, ProcessMessageList messageList)
	{
		this.inputStream = inputStream;
		this.messageList = messageList;
	}
	
	public void run() 
	{
		try
		{
		    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		    String line;
		    while ( (line = br.readLine()) != null )
		    {
		    	log.debug(line);
		    	this.messageList.addMessage(line);
		    }
		}
		catch(Exception e)
		{
			log.error(ExceptionUtils.getStackTrace(e));
		}
	    
	  }

}
