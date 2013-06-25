package com.totalbanksolutions.framework.parse;

/**
 * @author vcatrini
 */
public class ParseColumn
{
	private String			columnName;
	private String			columnData;
	private int				recordSequence;
	private int				startPosition;
	private int				endPosition;
	private int				length;
	private ParseDataType	dataType;
	private boolean			isValid;
	private int				lineNumber;
	private String			outputColumnNameMap = "";
	private String			outputTransformation = "";
	private String			databaseFieldNameMapping;
	
	public ParseColumn() {	}

	public ParseColumn(ParseColumn col)
	{
		this.columnName = col.getColumnName();
		this.columnData = col.getColumnData();
		this.recordSequence = col.getRecordSequence();
		this.startPosition = col.getStartPosition();
		this.endPosition = col.getEndPosition();
		this.length = col.getLength();
		this.dataType = col.getDataType();
		this.isValid = col.isValid();
		this.lineNumber = col.getLineNumber();
		this.outputColumnNameMap = col.getOutputColumnNameMap();
		this.outputTransformation = col.getOutputTransformation();
	}
	
	public ParseColumn(String columnName, int startPosition, int endPosition, ParseDataType dataType) 
	{
		this.columnName = columnName;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.length = calculateLength();
		this.dataType = dataType;
	}

	public ParseColumn(String columnName, int startPosition, ParseDataType dataType) 
	{
		this.columnName = columnName;
		this.startPosition = startPosition;
		this.endPosition = startPosition;
		this.length = calculateLength();
		this.dataType = dataType;
	}

	public ParseColumn(String columnName, int startPosition, int endPosition, ParseDataType dataType, String defaultValue, String outputColumnNameMap, String outputTransformation) 
	{
		this.columnName = columnName;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.length = calculateLength();
		this.dataType = dataType;
		this.columnData = defaultValue;
		this.outputColumnNameMap = outputColumnNameMap;
		this.outputTransformation = outputTransformation;
	}

	public ParseColumn(String columnName, int startPosition, int endPosition, String defaultValue, String outputColumnNameMap, String outputTransformation) 
	{
		this.columnName = columnName;
		//this.recordSequence = recordSequence;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.length = calculateLength();		
		this.dataType = ParseDataType.STRING;
		this.columnData = defaultValue;
		this.outputColumnNameMap = outputColumnNameMap;
		this.outputTransformation = outputTransformation;
	}

	public ParseColumn(String columnName, String defaultValue, String outputColumnNameMap, String outputTransformation) 
	{
		this.columnName = columnName;
		this.columnData = defaultValue;
		this.outputColumnNameMap = outputColumnNameMap;
		this.outputTransformation = outputTransformation;
	}

	public int calculateLength()
	{
		return (this.endPosition - this.startPosition) + 1;
	}
	
	public String toString()
	{
		StringBuffer out = new StringBuffer();
		try
		{
			out.append("columnName=" + this.columnName)
			.append("; columnData=" + this.columnData)
			.append("; startPosition=" + this.startPosition)
			.append("; endPosition=" + this.endPosition)
			.append("; dataType=" + this.dataType)
			.append("; isValid=" + this.isValid)
			.append("; lineNumber=" + this.lineNumber)
			.append("; outputColumnNameMap=" + this.outputColumnNameMap)
			.append("; outputTransformation=" + this.outputTransformation);
		}
		catch(Exception e) {}
		return out.toString();
	}
	
	// ----------------------------------------------------------
	// Getters & Setters (auto-generated)
	// ----------------------------------------------------------	

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getColumnData() {
		return columnData;
	}

	public void setColumnData(String columnData) {
		this.columnData = columnData;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	public int getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getOutputColumnNameMap() {
		return outputColumnNameMap;
	}

	public void setOutputColumnNameMap(String outputColumnNameMap) {
		this.outputColumnNameMap = outputColumnNameMap;
	}
	
	public String getOutputTransformation()
	{
		return this.outputTransformation;
	}
	
	public void setOutputTransformation(String outputTransformation)
	{
		this.outputTransformation = outputTransformation;
	}

	public int getRecordSequence() {
		return recordSequence;
	}

	public void setRecordSequence(int recordSequence) {
		this.recordSequence = recordSequence;
	}

	public ParseDataType getDataType() {
		return dataType;
	}

	public void setDataType(ParseDataType dataType) {
		this.dataType = dataType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getDatabaseFieldNameMapping() {
		return databaseFieldNameMapping;
	}

	public void setDatabaseFieldNameMapping(String databaseFieldNameMapping) {
		this.databaseFieldNameMapping = databaseFieldNameMapping;
	}

	
}
