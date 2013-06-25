package com.totalbanksolutions.framework.process.util;

public class ProcessStatus 
{
	private boolean isSuccess = true;
	private String	details = "";

	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}


}
