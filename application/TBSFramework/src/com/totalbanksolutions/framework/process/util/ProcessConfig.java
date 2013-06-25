package com.totalbanksolutions.framework.process.util;

import java.util.Date;

import com.totalbanksolutions.framework.dataservice.DataService;

public class ProcessConfig 
{

	private DataService 	ds = null;
	private long 			stepId = 0;
	private int				stepOrder = 0;
	private long 			batchId = 0;
	private String 			userName = "";
	private Date 			startBusinessDate;
	private Date 			businessDate;
	private long 			programId = 0;
	private String			programCode;
	private long 			sourceInstitutionId = 0;
	private String			sourceInstitutionCode;
	private long 			processTypeId = 0;
	private String			processType;
	private boolean			isLateProcessing;
	private boolean 		isAutoGenerateBatchId = false;
	private boolean			isAutoRun;
	private boolean			isUndo;
	private boolean			isPeriodEnd;
	private boolean			isPeriodStart;
	private long			versionNumber;
	private long 			depositId = 0;
	private long 			programDepositAccountId = 0;
	private long 			reportId = 0;
	private String 			fileDir = "";
	private String 			fileName = "";
	

	public boolean isPeriodStart() {
		return isPeriodStart;
	}
	public void setIsPeriodStart(boolean isPeriodStart) {
		this.isPeriodStart = isPeriodStart;
	}
	public boolean isPeriodEnd() {
		return isPeriodEnd;
	}
	public void setPeriodEnd(boolean isPeriodEnd) {
		this.isPeriodEnd = isPeriodEnd;
	}
	public DataService getDS() {
		return ds;
	}
	public void setDS(DataService ds) {
	this.ds = ds;
	}
	public long getStepId() {
		return stepId;
	}
	public void setStepId(long stepId) {
		this.stepId = stepId;
	}	
	public int getStepOrder() {
		return stepOrder;
	}
	public void setStepOrder(int stepOrder) {
		this.stepOrder = stepOrder;
	}
	public void setPeriodStart(boolean isPeriodStart) {
		this.isPeriodStart = isPeriodStart;
	}
	public long getBatchId() {
		return batchId;
	}
	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getStartBusinessDate() {
		return startBusinessDate;
	}
	public void setStartBusinessDate(Date startBusinessDate) {
		this.startBusinessDate = startBusinessDate;
	}
	public Date getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	public long getProgramId() {
		return programId;
	}
	public void setProgramId(long programId) {
		this.programId = programId;
	}
	public long getSourceInstitutionId() {
		return sourceInstitutionId;
	}
	public void setSourceInstitutionId(long sourceInstitutionId) {
		this.sourceInstitutionId = sourceInstitutionId;
	}
	public long getProcessTypeId() {
		return processTypeId;
	}
	public void setProcessTypeId(long processTypeId) {
		this.processTypeId = processTypeId;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public boolean isAutoGenerateBatchId() {
		return isAutoGenerateBatchId;
	}
	public void setAutoGenerateBatchId(boolean isAutoGenerateBatchId) {
		this.isAutoGenerateBatchId = isAutoGenerateBatchId;
	}
	public boolean isLateProcessing() {
		return isLateProcessing;
	}
	public void setLateProcessing(boolean isLateProcessing) {
		this.isLateProcessing = isLateProcessing;
	}
	public boolean isAutoRun() {
		return isAutoRun;
	}
	public void setAutoRun(boolean isAutoRun) {
		this.isAutoRun = isAutoRun;
	}
	public String getProgramCode() {
		return programCode;
	}
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	public String getSourceInstitutionCode() {
		return sourceInstitutionCode;
	}
	public void setSourceInstitutionCode(String sourceInstitutionCode) {
		this.sourceInstitutionCode = sourceInstitutionCode;
	}
	public boolean isUndo() {
		return isUndo;
	}
	public void setUndo(boolean isUndo) {
		this.isUndo = isUndo;
	}
	public long getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}
	public long getDepositId() {
		return depositId;
	}
	public void setDepositId(long depositId) {
		this.depositId = depositId;
	}
	public long getReportId() {
		return reportId;
	}
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	public long getProgramDepositAccountId() {
		return programDepositAccountId;
	}
	public void setProgramDepositAccountId(long programDepositAccountId) {
		this.programDepositAccountId = programDepositAccountId;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
