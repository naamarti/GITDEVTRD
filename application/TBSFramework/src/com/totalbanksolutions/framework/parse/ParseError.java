package com.totalbanksolutions.framework.parse;

/**
 * @author vcatrini
 */
public class ParseError
{
	
	private int			lineNumber;
	private int			recordNumber;
	private String		columnName = "";
	private String		error = "";
	private String		columnData = "";
	private String		rowData = "";
	private boolean		isWarning;
	
	public ParseError() {	}

	public String toString()
	{
		StringBuffer out = new StringBuffer();
		try
		{
			out.append("lineNumber=" + this.lineNumber)
			.append("; recordNumber=" + this.recordNumber)
			.append("; columnName=" + this.columnName)
			.append("; error=" + this.error)
			.append("; columnData=" + this.columnData)
			.append("; rowData=" + this.rowData)
			.append("; isWarning=" + this.isWarning);
		}
		catch(Exception e) {}
		return out.toString();
	}
	
	// ----------------------------------------------------------
	// Getters & Setters (auto-generated)
	// ----------------------------------------------------------	

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(int recordNumber) {
		this.recordNumber = recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber.intValue();
	}

	public String getRowData() {
		return rowData;
	}

	public void setRowData(String rowData) {
		this.rowData = rowData;
	}

	public String getColumnData() {
		return columnData;
	}

	public void setColumnData(String columnData) {
		this.columnData = columnData;
	}

	public boolean isWarning() {
		return isWarning;
	}

	public void setWarning(boolean isWarning) {
		this.isWarning = isWarning;
	}	
	
}
