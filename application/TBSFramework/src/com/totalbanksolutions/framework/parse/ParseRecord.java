package com.totalbanksolutions.framework.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vcatrini
 * A Record may span multiple rows of data
 */
public class ParseRecord
{
	
	private Integer						recordNumber = new Integer(0);
	private ParseRecordType				recordType;
	private int							lineNumber;
	private boolean						isValid = true;
	private boolean						isWarning;
	private boolean						isSuppressOutput;
	private String						rowTypeIdentifier = "";
	private List<ParseRow> 				rows = new ArrayList<ParseRow>();
	private Map<String,ParseColumn> 	inputColumns = new HashMap<String,ParseColumn>();
	//private List<ParseColumn> 		outputColumns = new ArrayList<ParseColumn>();
	private Map<String,ParseColumn> 	outputColumns = new LinkedHashMap<String,ParseColumn>();
	private ParseRowType				outputRowType = new ParseRowType();
	private Object						obj = null;
	private int							outputLines;
	private Integer						outputColumnNumber = 0;
	private int							queryRowIndex = 0;
	
	public ParseRecord() {	}
	
	//public ParseRecord(ParseRecord record)
	//{
		//this.recordNumber = record.recordNumber;
		//this.recordType = record.recordType;
		//this.lineNumber = record.lineNumber;
		//this.isValid = record.isValid;
		//this.isWarning = record.isWarning;
		//this.isSuppressOutput = record.isSuppressOutput;
		//this.rowTypeIdentifier = record.rowTypeIdentifier;
		//this.outputRowType = record.outputRowType;
		//this.inputColumns.putAll(record.inputColumns);
	//}
	
	public void addRow(ParseRow row)
	{
		this.rowTypeIdentifier = row.getRowTypeIdentifier();
		rows.add(row);
	}

	public void addInputColumn(ParseColumn col)
	{
		if(inputColumns.containsKey(col.getColumnName()))
		{
			throw new RuntimeException("Duplicate Input Column '" + col.getColumnName() + "' specified!");
		}
		inputColumns.put(col.getColumnName(), col);
	}

	public void addOutputColumn(ParseColumn col, boolean isEnforceUniqueOutputColumnNames)
	{
		String key = "";
		if(isEnforceUniqueOutputColumnNames)
		{
			if(outputColumns.containsKey(col.getColumnName()))
			{
				throw new RuntimeException("Duplicate Output Column '" + col.getColumnName() + "' specified!");
			}
			key = col.getColumnName();
		}
		else
		{
			this.outputColumnNumber++;
			key = this.outputColumnNumber.toString();
		}
		outputColumns.put(key, col);
	}
	
	public boolean containsInputField(ParseFieldType field)
	{
		return this.inputColumns.containsKey(field.getFieldName());
	}
	
	public boolean containsOutputField(ParseFieldType field)
	{
		return this.outputColumns.containsKey(field.getFieldName());
	}

	public void cleanUpAfterProcessOutput()
	{
		this.rows.clear();
		this.rows = null;
		this.inputColumns.clear();
		this.inputColumns = null;
	}

	public void cleanUpAfterWriteOutput()
	{
		this.outputColumns.clear();
		this.outputColumns = null;
	}

	public void cleanUpAfterSaveToDatabase()
	{
		this.obj = null;
	}

	public String getFirstRowData()
	{
		String out = "";
		try
		{
			ParseRow row = this.rows.get(0);
			out = row.getRowData();
		}
		catch(Exception e) {}
		return out;
	}

	public ParseColumn getInputColumn(ParseFieldType field) 
	{
		ParseColumn col = this.inputColumns.get(field.getFieldName());
		if(col == null)
		{
			throw new RuntimeException("Input Column '" + field.getFieldName() + "' not found!");
		}
		return col;
	}
	
	public ParseColumn getOutputColumn(ParseFieldType field) 
	{
		ParseColumn col = this.outputColumns.get(field.getFieldName());
		if(col == null)
		{
			throw new RuntimeException("Output Column '" + field.getFieldName() + "' not found!");
		}
		return col;
	}

	public ParseColumn getInputColumn(String field) 
	{
		ParseColumn col = this.inputColumns.get(field);
		if(col == null)
		{
			throw new RuntimeException("Input Column '" + field + "' not found!");
		}
		return col;
	}
	
	public ParseColumn getOutputColumn(String field) 
	{
		ParseColumn col = this.outputColumns.get(field);
		if(col == null)
		{
			throw new RuntimeException("Output Column '" + field + "' not found!");
		}
		return col;
	}
	
	public String toString()
	{
		StringBuffer out = new StringBuffer();
		try
		{
			out.append("recordNumber=" + this.recordNumber)
			.append("| recordType=" + this.recordType)
			.append("| lineNumber=" + this.lineNumber)
			.append("| isValid=" + this.isValid)
			.append("| isWarning=" + this.isWarning);
		}
		catch(Exception e) {}
		return out.toString();
	}
	
	// ----------------------------------------------------------
	// Getters & Setters (auto-generated)
	// ----------------------------------------------------------	
	
	public Integer getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public List<ParseRow> getRows() {
		return rows;
	}

	public void setRows(List<ParseRow> rows) {
		this.rows = rows;
	}

	public Map<String, ParseColumn> getInputColumns() {
		return inputColumns;
	}

	public void setInputColumns(Map<String, ParseColumn> inputColumns) {
		this.inputColumns = inputColumns;
	}

//	public List<ParseColumn> getOutputColumns() {
//		return outputColumns;
//	}
//
//	public void setOutputColumns(List<ParseColumn> outputColumns) {
//		this.outputColumns = outputColumns;
//	}

	public Map<String, ParseColumn> getOutputColumns() {
		return outputColumns;
	}

	public void setOutputColumns(Map<String, ParseColumn> outputColumns) {
		this.outputColumns = outputColumns;
	}

	public boolean isWarning() {
		return isWarning;
	}

	public void setWarning(boolean isWarning) {
		this.isWarning = isWarning;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public ParseRecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(ParseRecordType recordType) {
		this.recordType = recordType;
	}

	public ParseRowType getOutputRowType() {
		return outputRowType;
	}

	public void setRowType(ParseRowType outputRowType) {
		this.outputRowType = outputRowType;
	}

	public String getRowTypeIdentifier() {
		return rowTypeIdentifier;
	}

	public void setRowTypeIdentifier(String rowTypeIdentifier) {
		this.rowTypeIdentifier = rowTypeIdentifier;
	}

	public boolean isSuppressOutput() {
		return isSuppressOutput;
	}

	public void setSuppressOutput(boolean isSuppressOutput) {
		this.isSuppressOutput = isSuppressOutput;
	}

	public int getOutputLines() {
		return outputLines;
	}

	public void setOutputLines(int outputLines) {
		this.outputLines = outputLines;
	}

	public int getQueryRowIndex() {
		return queryRowIndex;
	}

	public void setQueryRowIndex(int queryRowIndex) {
		this.queryRowIndex = queryRowIndex;
	}

	
}
