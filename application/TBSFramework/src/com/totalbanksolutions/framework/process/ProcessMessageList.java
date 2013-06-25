package com.totalbanksolutions.framework.process;

import java.util.ArrayList;
import java.util.List;

public class ProcessMessageList 
{
	private List<String> messageList = new ArrayList<String>();

	public void addMessage(String message)
	{
		this.messageList.add(message);
	}
	
	public List<String> getMessageList() {
		return messageList;
	}
	
}
