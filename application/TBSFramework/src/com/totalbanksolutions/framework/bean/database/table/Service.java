package com.totalbanksolutions.framework.bean.database.table;

import java.util.Date;

/**
 * @author vcatrini
 */
public class Service 
{	

	private long		serviceId = 1;
	private String		serviceName = "";
	private String		serviceDescription = "";
	private Date		startedDateTime;
	private Date		lastPingDateTime;
	private String		serverName = "";
	private String		commandDirectory = "";
	private String		startCommand = "";
	private String		stopCommand = "";
	private int			versionNumber;
	private Date		currentDatabaseDateTime;

	
	public boolean isRunning()
	{
		boolean isRunning = false;
		if(this.currentDatabaseDateTime.getTime() - this.lastPingDateTime.getTime() < 20000)
		{
			isRunning = true;
		}
		return isRunning;
	}
	
	// ----------------------------------------------------------
	// Getters & Setters (auto-generated)
	// ----------------------------------------------------------	
	
	public long getServiceId() {
		return serviceId;
	}
	public void setServiceId(long serviceId) {
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

	public Date getCurrentDatabaseDateTime() {
		return currentDatabaseDateTime;
	}

	public void setCurrentDatabaseDateTime(Date currentDatabaseDateTime) {
		this.currentDatabaseDateTime = currentDatabaseDateTime;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getCommandDirectory() {
		return commandDirectory;
	}

	public void setCommandDirectory(String commandDirectory) {
		this.commandDirectory = commandDirectory;
	}

	public String getStartCommand() {
		return startCommand;
	}

	public void setStartCommand(String startCommand) {
		this.startCommand = startCommand;
	}

	public String getStopCommand() {
		return stopCommand;
	}

	public void setStopCommand(String stopCommand) {
		this.stopCommand = stopCommand;
	}
	
}
