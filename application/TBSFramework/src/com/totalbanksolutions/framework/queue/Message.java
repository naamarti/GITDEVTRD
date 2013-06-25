package com.totalbanksolutions.framework.queue;

import java.util.HashMap;
import java.util.Map;


/**
 * @author dmerkel
 *
 */
public class Message 
{

	private Map msg	= null;

	public void Message()
	{
		msg = new HashMap();
	}
	
	public void setMessage( Map messageContents ){};
	
	public Map getMessage()
	{
		return msg;
	}
	
	
	public String getMessageProperty( String fieldName )
	{
		if( msg.containsKey(fieldName) )
		{
			return (String)msg.get(fieldName);
		}
		else
		{
			return "";
		}
	}
	
}
