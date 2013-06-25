package com.totalbanksolutions.framework.file;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.parse.ParseColumn;
import com.totalbanksolutions.framework.parse.ParseDirectionType;
import com.totalbanksolutions.framework.parse.ParseError;
import com.totalbanksolutions.framework.parse.ParseFieldType;
import com.totalbanksolutions.framework.parse.ParseRecord;
import com.totalbanksolutions.framework.parse.ParseRecordType;
import com.totalbanksolutions.framework.parse.ParseRow;
import com.totalbanksolutions.framework.parse.ParseRowType;
import com.totalbanksolutions.framework.process.util.ProcessConfig;
import com.totalbanksolutions.framework.process.util.ProcessWorker;

public abstract class AbstractFileConverter implements ProcessWorker 
{
	protected static final Log log = LogFactory.getLog(AbstractFileConverter.class);

	public		abstract void						initializeSettings();
	public		abstract void 						initializeInputColumns();
	public		abstract void 						initializeOutputColumns();
	public		abstract void						beforeProcessingRecord(ParseRecord record);
	public		abstract void						processInputColumn(ParseRecord record, ParseColumn col);
	public		abstract void						processOutputColumn(ParseRecord record, ParseColumn col);
	public		abstract void						afterProcessingRecords(List<ParseRecord> records);
	public 		abstract int 						commitDatabaseChanges();

	protected	abstract ParseRow					getNextRow(); 

	protected	DataService							ds = null;
	protected	boolean								isEnforceUniqueOutputColumnNames = true;
	protected	boolean								isSuppressErrorMissingInputColumns = false;
	protected 	int 								currentLineNumber;
	protected 	int 								currentRecordNumber;
	protected	boolean 							headerProcessed = false;
	protected	boolean 							trailerProcessed = false;
	protected 	int 								trailerRecordTotal = 0;
	protected	long								batchNumber;
	protected	String								batchUserName = "";
	protected	Date								batchBusinessDate;
	protected	long								batchStepId;
	protected	long								batchSourceInstitutionId;
	protected	String								batchSourceInstitutionCode;
	protected	long								batchProgramId;
	protected	String								batchProgramCode;
	protected	String								batchProgramName = "";
	protected	long								batchProcessTypeId;
	protected	boolean								isBatchLateFile = false;
	protected	String								batchFileHeaderTimestamp = "";
	protected	String								batchLogDetails = "";
	protected	int									totalRecordsSaved = 0;
	
	protected	ParseRecord							headerRecord = null;
	protected	ParseRecord							trailerRecord = null;
	protected	List<ParseRecord> 					records = new ArrayList<ParseRecord>();
	protected 	List<ParseRow> 						rows = new ArrayList<ParseRow>();
	protected 	Map<ParseRowType,List<ParseColumn>>	columnDefinition = new HashMap<ParseRowType,List<ParseColumn>>();
	private		boolean								containsDefinitionOutputHeader = false;
	private		boolean								containsDefinitionOutputTrailer = false;
	
	protected   double								purchaseAmount = 0;
	protected   double 								redemptionAmount = 0;

	protected 	String 								invalidReason = null;

	public AbstractFileConverter() { }

	public AbstractFileConverter(ProcessConfig config) 
	{ 
		this.ds = config.getDS();
		this.batchStepId = config.getStepId();
		this.batchNumber = config.getBatchId();
		this.batchProgramId = config.getProgramId();
		this.batchProgramCode = config.getProgramCode();
		this.batchSourceInstitutionId = config.getSourceInstitutionId();
		this.batchSourceInstitutionCode = config.getSourceInstitutionCode();
		this.batchProcessTypeId = config.getProcessTypeId();
		this.batchBusinessDate = config.getBusinessDate();
		this.isBatchLateFile = config.isLateProcessing();
		this.batchUserName = config.getUserName();
	}
	
	public void addColumnDefinition(ParseRowType rowType, List<ParseColumn> columns) 
	{
		if(columnDefinition.containsKey(rowType))
		{
			throw new RuntimeException("Invalid API Usage. A ColumnDefinition already added for the specified rowType.");
		}		
		columnDefinition.put(rowType, columns);
		if(rowType.getRecordType() == ParseRecordType.HEADER && rowType.getDirectionType() == ParseDirectionType.INPUT)
		{
		}
		else if(rowType.getRecordType() == ParseRecordType.TRAILER && rowType.getDirectionType() == ParseDirectionType.INPUT)
		{
		}
		else if(rowType.getRecordType() == ParseRecordType.HEADER && rowType.getDirectionType() == ParseDirectionType.OUTPUT)
		{
			this.containsDefinitionOutputHeader = true;
		}
		else if(rowType.getRecordType() == ParseRecordType.TRAILER && rowType.getDirectionType() == ParseDirectionType.OUTPUT)
		{
			this.containsDefinitionOutputTrailer = true;
		}		
	}

	public List<ParseColumn> getColumnDefinitions(ParseRowType rowType) 
	{
		if(columnDefinition.containsKey(rowType))
		{
			return columnDefinition.get(rowType);
		}
		else
		{
			return new ArrayList<ParseColumn>();
		}
	}

	protected void removeNonOutputRecords()
	{
		for (Iterator<ParseRecord> it = this.records.iterator(); it.hasNext();) 
		{
			ParseRecord record = it.next();
			if (record.isSuppressOutput() || record.getRecordType() == ParseRecordType.SKIP) 
			{
				it.remove();
			}
		}		
	}
	
	// Returns null when no more records in File
	protected ParseRecord getNextRecord() 
	{
		//log.debug("getNextRecord()...");
		boolean isRecordComplete = false;
		boolean waitingForNextRowInSequence = false;
		ParseRow lastRow = new ParseRow();
		ParseRecord record = null;
		ParseRow row = getNextRow();
		while(row != null && !isRecordComplete)
		{
			//log.debug("getNextRow() #" + row.getLineNumber() + " = '" + row.getRowData() + "'.");
			ParseRowType rowType = row.getRowType();
			ParseRowType lastRowType = lastRow.getRowType();
			if(rowType.getRecordType() == ParseRecordType.HEADER)
			{
				if(waitingForNextRowInSequence)
				{
					isRecordComplete = true;
				}
				else
				{
					currentLineNumber++;
					record = new ParseRecord();
					record.setRecordType(ParseRecordType.HEADER);
					record.setRecordNumber(new Integer(-1));
					record.setLineNumber(currentLineNumber);
					record.addRow(row);
					isRecordComplete = true;
				}
			}
			else if(rowType.getRecordType() == ParseRecordType.TRAILER)
			{
				if(waitingForNextRowInSequence)
				{
					isRecordComplete = true;
				}
				else
				{
					currentLineNumber++;
					record = new ParseRecord();
					record.setRecordType(ParseRecordType.TRAILER);
					record.setRecordNumber(new Integer(-1));
					record.setLineNumber(currentLineNumber);
					record.addRow(row);
					isRecordComplete = true;
				}
			}
			else if(rowType.getRecordType() == ParseRecordType.SKIP)
			{
				if(waitingForNextRowInSequence)
				{
					isRecordComplete = true;
				}
				else
				{
					currentLineNumber++;
					record = new ParseRecord();
					record.setRecordType(ParseRecordType.SKIP);
					record.setRecordNumber(new Integer(-1));
					record.setLineNumber(currentLineNumber);
					record.addRow(row);
					record.setValid(false);
					isRecordComplete = true;				
				}					
			}
			else if(rowType.getRecordType() == ParseRecordType.DATA)
			{
				// First Data Row in Sequence Record (or single row record)
				if(rowType.getRecordSequenceNumber() == 0)
				{
					if(waitingForNextRowInSequence)
					{
						isRecordComplete = true;
					}
					else if(rowType.getRecordMaxSequenceNumber() == 0)
					{
						// First Row, Not part of a Sequence
						//log.debug("First Row, Not part of a Sequence. '" + row.getRowData() + "' .");
						currentLineNumber++;
						currentRecordNumber++;
						record = new ParseRecord();
						record.setRecordType(ParseRecordType.DATA);
						record.setRecordNumber(currentRecordNumber);
						record.setLineNumber(currentLineNumber);
						record.addRow(row);
						isRecordComplete = true;
					}
					else
					{
						// First row in Sequence
						//log.debug("First row in Sequence. '" + row.getRowData() + "' .");
						currentLineNumber++;
						currentRecordNumber++;
						record = new ParseRecord();
						record.setRecordType(ParseRecordType.DATA);
						record.setRecordNumber(currentRecordNumber);
						record.setLineNumber(currentLineNumber);
						record.addRow(row);
						isRecordComplete = false;
						waitingForNextRowInSequence = true;
					}
				}
				// Second or greater follow-up Data Row in Sequence
				else
				{
					if(waitingForNextRowInSequence)
					{
						// Valid follow-up sequence record
						if(	lastRowType.getRecordGroupNumber() == rowType.getRecordGroupNumber() 
							&& rowType.getRecordSequenceNumber() == lastRowType.getRecordSequenceNumber() + 1)
						{
							//log.debug("Second or greater follow-up Data Row in Sequence. Valid follow-up sequence record. '" + row.getRowData() + "' .");
							currentLineNumber++;
							record.addRow(row);
						}
						// Invalid follow-up sequence record
						else
						{
							//log.debug("Second or greater follow-up Data Row in Sequence. InValid follow-up sequence record.'");
							currentLineNumber++;
							throw new RuntimeException("Line# '" + row.getLineNumber() + "', Record not in the proper expected sequence(a). RowData='" + row.getRowData() + "', LastRowData='" + lastRow.getRowData() + "' .");
						}
					}
					else
					{
						// Invalid follow-up sequence record
						//log.debug("Second or greater follow-up Data Row in Sequence. InValid follow-up sequence record.'");
						currentLineNumber++;
						throw new RuntimeException("Line# '" + row.getLineNumber() + "', Record not in the proper expected sequence(b). RowData='" + row.getRowData() + "', LastRowData='" + lastRow.getRowData() + "' .");
					}
				}
			}
			else
			{
				if(waitingForNextRowInSequence)
				{
					isRecordComplete = true;
				}
				else
				{
					currentLineNumber++;
					throw new RuntimeException("Line# '" + row.getLineNumber() + "', Unknown Record Type. RowData='" + row.getRowData() + "'.");
				}
			}

			if(isRecordComplete && record.isValid())
			{
				if(lastRowType.getRecordSequenceNumber() < lastRowType.getRecordMinRequiredSequenceNumber())
				{
					throw new RuntimeException("Line# '" + row.getLineNumber() + "', The Record must contain a minimum of '" + (lastRowType.getRecordMinRequiredSequenceNumber() + 1) + "' sequence Rows. RowData='" + row.getRowData() + "'.");
				}
			}
			
			lastRow = new ParseRow(row);
			if(!isRecordComplete)
			{
				row = getNextRow();
			}
		}
		return record;
	}

	protected void processRecord(ParseRecord record)
	{
		if(record.isValid())
		{
			beforeProcessingRecord(record);
			processInputColumns(record);
			if(record.getRecordType() == ParseRecordType.HEADER)
			{
				this.headerProcessed = true;
				if(this.containsDefinitionOutputHeader)
				{
					processOutputColumns(record);
				}
			}
			else if(record.getRecordType() == ParseRecordType.TRAILER)
			{
				this.trailerProcessed = true;
				if(this.containsDefinitionOutputTrailer)
				{
					processOutputColumns(record);
				}
			}
			else if(record.getRecordType() == ParseRecordType.DATA)
			{
				processOutputColumns(record);
			}
			record.cleanUpAfterProcessOutput();
		}
	}

	private void processInputColumns(ParseRecord record)
	{
		ParseColumn currentColumn = new ParseColumn();
		for (Map.Entry<String,ParseColumn> columnEntry : record.getInputColumns().entrySet()) 
		{
		    ParseColumn column = columnEntry.getValue();
			try
			{
				currentColumn = column;
				ParseColumn col = new ParseColumn(column);
				processInputColumn(record, col);
			}
			catch(Exception e)
			{
				String errorMessage = ExceptionUtils.getMessage(e) + " Error processing input column '" + currentColumn.getColumnName() + "'.";
				String errorStack = ExceptionUtils.getStackTrace(e);
				log.error(errorMessage + "\n" + getDebugInfo(record) + "\n" + getDebugInfo(currentColumn) + "\n" + errorStack);
				throw new RuntimeException(e);
			}
		}
	}
	
	protected void processOutputColumns(ParseRecord record)
	{
		if(record.isSuppressOutput()) return;
		ParseColumn currentColumn = new ParseColumn();
		ParseRowType rowType = getOutputRowType(record);		
		int sequenceNumber = 0;
		boolean isDone = false;
		while(!isDone)
		{
			rowType.setRecordSequenceNumber(sequenceNumber);
			List<ParseColumn> columnDefinitions = getColumnDefinitions(rowType);
			if(columnDefinitions.size() == 0)
			{
				isDone = true;
			}
			else
			{
				for(ParseColumn columnDef : columnDefinitions)
				{
					try
					{
						currentColumn = columnDef;
						String value = columnDef.getColumnData();
						
//							if(value.equalsIgnoreCase("{RecordCount}"))
//							{
//								value = getRecordTotal();
//							}
//							else if(value.equalsIgnoreCase("{PurchaseAmount}"))
//							{
//								value = getPurchaseRecordAmount();
//							}							
//							else if(value.equalsIgnoreCase("{RedemptionAmount}"))
//							{
//								value = getRedemptionRecordAmount();
//							}
//							else

						if(columnDef.getOutputColumnNameMap().length() > 0)
						{
							value = getColumnValue(record.getInputColumns(), columnDef.getOutputColumnNameMap());
						}
						ParseColumn col = new ParseColumn(columnDef);
						col.setColumnData(value);
						currentColumn = col;
						processOutputColumn(record, col);
						col.setRecordSequence(sequenceNumber);
						record.addOutputColumn(col, this.isEnforceUniqueOutputColumnNames);
					}
					catch(Exception e)
					{
						String errorMessage = ExceptionUtils.getMessage(e) + " Error processing output column '" + currentColumn.getColumnName() + "'.";
						String errorStack = ExceptionUtils.getStackTrace(e);
						log.error(errorMessage + "\n" + getDebugInfo(record) + "\n" + getDebugInfo(currentColumn) + "\n" + errorStack);
						throw new RuntimeException(e);
					}
				}
				if(record.getRecordType() == ParseRecordType.DATA)
				{
					record.setOutputLines(record.getOutputLines() + 1);
				}
			}
			sequenceNumber++;
		}
	}
	
	// Can override if multiple groups of Data output formats
	protected ParseRowType getOutputRowType(ParseRecord record)
	{
		ParseRowType rowType = new ParseRowType();
		rowType.setDirectionType(ParseDirectionType.OUTPUT);
		rowType.setRecordType(record.getRecordType());
		rowType.setRecordGroupNumber(0);
		rowType.setRecordSequenceNumber(0);
		return rowType;
	}
	
	public String getPurchaseRecordAmount()
	{
		String total = "0";
		double amount = this.purchaseAmount;		
		total = new Double(amount).toString();
		return total;
	}
	
	public String getRedemptionRecordAmount()
	{
		String total = "0";
		double amount = this.redemptionAmount;
		total = new Double(amount).toString();
		return total;
	}
	
//	protected void getRecordColumnValues(ParseRecord record)
//	{
//		if(record != null && record.isValid())
//		{
//			for(ParseRow row : record.getRows())
//			{
//				Map<String,ParseColumn> columns = parseRow(row, getColumnDefinitions(row.getRowType()));
//				for(Map.Entry<String, ParseColumn> columnEntry : columns.entrySet())
//				{
//				    ParseColumn column =  columnEntry.getValue();
//				    ParseColumn col = new ParseColumn(column);
//					record.addInputColumn(col);
//				}
//			}
//		}
//	}

	protected String getColumnValue(Map<String,ParseColumn> columns, ParseFieldType field) 
	{
		return getColumnValue(columns, field.getFieldName());
	}
	
	protected String getColumnValue(Map<String,ParseColumn> columns, String columnName) 
	{
		String columnValue = "";
		ParseColumn column = columns.get(columnName);
		if(column != null)
		{
			columnValue = column.getColumnData();
		}
		else
		{
			if(!this.isSuppressErrorMissingInputColumns)
			{
				throw new RuntimeException("Cannot set value, there is no input column named '" + columnName + "'!");
			}
		}
		return columnValue;
	}

	public void saveBatchProcessDetailsToDatabase()
	{
		this.ds.util.updateDailyProcessBatchDetails(this.batchNumber, this.batchFileHeaderTimestamp, this.totalRecordsSaved, this.batchLogDetails);
	}
	
	public String checkAccountLengthAndPad(String accountNumber, int fixedAccountLength, boolean isPadAccountWhenShort)//, boolean skipAndReportError) 
	{
		if(accountNumber.length() == 0)
		{
		//	if (skipAndReportError)
			//{
				//this.invalidReason = "The AccountNumber cannot be blank";
			//}
			//else
		//	{
				throw new RuntimeException("The AccountNumber cannot be blank");
			//}
		}
		if( !isPadAccountWhenShort )
		{
			if(accountNumber.length() < fixedAccountLength)
			{
				//if (skipAndReportError)
				//{
			//		this.invalidReason = "The AccountNumber cannot be less than " + fixedAccountLength + " characters";
				//}
			//	else
				//{
					throw new RuntimeException("The AccountNumber cannot be less than " + fixedAccountLength + " characters!");
				//}
			}
			if(accountNumber.length() > fixedAccountLength && fixedAccountLength > 0)
			{
				accountNumber = accountNumber.substring(0, fixedAccountLength);
			}
		}
		else
		{				
			if(fixedAccountLength > 0)
			{
				accountNumber = StringUtils.leftPad(accountNumber, fixedAccountLength, "0");
			}
		}
		return accountNumber;
	}
	
	public String getInvalidReason()
	{
		return this.invalidReason;
	}
	
	protected String getDebugInfo(ParseRecord record)
	{
		StringBuffer debugInfo = new StringBuffer();
		try
		{
			if(record != null)
			{
				debugInfo.append(" Record: [");
				debugInfo.append(record.toString());
				debugInfo.append("] ");
			}
		}
		catch(Exception e) 
		{
			log.error("Error getting DebugInfo. " + ExceptionUtils.getStackTrace(e));			
		}
		return debugInfo.toString();
	}
	
	protected String getDebugInfo(ParseColumn column)
	{
		StringBuffer debugInfo = new StringBuffer();
		try
		{
			if(column != null)
			{
				debugInfo.append(" Column: [");
				debugInfo.append(column.toString());
				debugInfo.append("] ");
			}
		}
		catch(Exception e) 
		{
			log.error("Error getting DebugInfo. " + ExceptionUtils.getStackTrace(e));			
		}
		return debugInfo.toString();
	}
	
	protected String getDebugInfo(ParseError error)
	{
		StringBuffer debugInfo = new StringBuffer();
		try
		{
			if(error != null)
			{
				debugInfo.append(" Error: [");
				debugInfo.append(error.toString());
				debugInfo.append("] ");
			}
		}
		catch(Exception e) 
		{
			log.error("Error getting DebugInfo. " + ExceptionUtils.getStackTrace(e));			
		}
		return debugInfo.toString();
	}
	
	// --------------------------------------------------------------	
	// Getters & Setters
	// --------------------------------------------------------------
	
	public List<ParseRow> getRows() {
		return rows;
	}
	public void setRows(List<ParseRow> rows) {
		this.rows = rows;
	}
	public boolean isHeaderProcessed() {
		return headerProcessed;
	}
	public void setHeaderProcessed(boolean headerProcessed) {
		this.headerProcessed = headerProcessed;
	}
	public boolean isTrailerProcessed() {
		return trailerProcessed;
	}
	public void setTrailerProcessed(boolean trailerProcessed) {
		this.trailerProcessed = trailerProcessed;
	}
	public List<ParseRecord> getRecords() {
		return records;
	}
	public void setRecords(List<ParseRecord> records) {
		this.records = records;
	}
	public long getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(long batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getBatchUserName() {
		return batchUserName;
	}
	public void setBatchUserName(String batchUserName) {
		this.batchUserName = batchUserName;
	}
	public Date getBatchBusinessDate() {
		return batchBusinessDate;
	}
	public void setBatchBusinessDate(Date batchBusinessDate) {
		this.batchBusinessDate = batchBusinessDate;
	}
	public long getBatchProgramId() {
		return batchProgramId;
	}
	public void setBatchProgramId(long batchProgramId) {
		this.batchProgramId = batchProgramId;
	}
	public String getBatchProgramCode() {
		return batchProgramCode;
	}
	public void setBatchProgramCode(String batchProgramCode) {
		this.batchProgramCode = batchProgramCode;
	}
	public String getBatchProgramName() {
		return batchProgramName;
	}
	public void setBatchProgramName(String batchProgramName) {
		this.batchProgramName = batchProgramName;
	}
	public DataService getDS() {
		return ds;
	}
	public boolean isSuppressErrorMissingInputColumns() {
		return isSuppressErrorMissingInputColumns;
	}
	public void setSuppressErrorMissingInputColumns(
			boolean isSuppressErrorMissingInputColumns) {
		this.isSuppressErrorMissingInputColumns = isSuppressErrorMissingInputColumns;
	}
	public boolean isBatchLateFile() {
		return isBatchLateFile;
	}
	public void setBatchLateFile(boolean isBatchLateFile) {
		this.isBatchLateFile = isBatchLateFile;
	}
	public long getBatchProcessTypeId() {
		return batchProcessTypeId;
	}
	public void setBatchProcessTypeId(long batchProcessTypeId) {
		this.batchProcessTypeId = batchProcessTypeId;
	}
	public String getBatchFileHeaderTimestamp() {
		return batchFileHeaderTimestamp;
	}
	public void setBatchFileHeaderTimestamp(String batchFileHeaderTimestamp) {
		this.batchFileHeaderTimestamp = batchFileHeaderTimestamp;
	}
	public int getTotalRecordsSaved() {
		return totalRecordsSaved;
	}
	public void setTotalRecordsSaved(int totalRecordsSaved) {
		this.totalRecordsSaved = totalRecordsSaved;
	}
	public String getBatchLogDetails() {
		return batchLogDetails;
	}
	public void setBatchLogDetails(String batchLogDetails) {
		this.batchLogDetails = batchLogDetails;
	}
	public long getBatchSourceInstitutionId() {
		return batchSourceInstitutionId;
	}
	public void setBatchSourceInstitutionId(long batchSourceInstitutionId) {
		this.batchSourceInstitutionId = batchSourceInstitutionId;
	}
	public String getBatchSourceInstitutionCode() {
		return batchSourceInstitutionCode;
	}
	public void setBatchSourceInstitutionCode(String batchSourceInstitutionCode) {
		this.batchSourceInstitutionCode = batchSourceInstitutionCode;
	}
	public long getBatchStepId() {
		return batchStepId;
	}
	public void setBatchStepId(long batchStepId) {
		this.batchStepId = batchStepId;
	}
	public boolean isEnforceUniqueOutputColumnNames() {
		return isEnforceUniqueOutputColumnNames;
	}
	public void setEnforceUniqueOutputColumnNames(boolean isEnforceUniqueOutputColumnNames) 
	{
		this.isEnforceUniqueOutputColumnNames = isEnforceUniqueOutputColumnNames;
	}

}