package com.totalbanksolutions.framework.bean.database.table;

import java.util.Date;

/**
 * @author vcatrini
 */
public class Services 
{
	
	private int			serviceId;
	private String 		serviceName = "";
	private Date 		startedDateTime;
	private Date		lastPingDateTime;
	private int			versionNumber;
	
	public Services() {	}

	// ----------------------------------------------------------
	// Getters & Setters (auto-generated)
	// ----------------------------------------------------------	

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Date getStartedDateTime() {
		return startedDateTime;
	}

	public void setStartedDateTime(Date startedDateTime) {
		this.startedDateTime = startedDateTime;
	}

	public Date getLastPingDateTime() {
		return lastPingDateTime;
	}

	public void setLastPingDateTime(Date lastPingDateTime) {
		this.lastPingDateTime = lastPingDateTime;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	
}
