package com.totalbanksolutions.framework.bean.web;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vcatrini
 */
public class UserSession 
{
	
	private String				requestType = "";
	private Map<String,Object>	requestMap = new HashMap<String,Object>();
	
	public UserSession() {	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Map<String,Object> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String,Object> requestMap) {
		this.requestMap = requestMap;
	}

	
}
