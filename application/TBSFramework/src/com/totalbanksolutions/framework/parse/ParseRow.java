package com.totalbanksolutions.framework.parse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vcatrini
 */
public class ParseRow
{
	
	private int					lineNumber;
	private String				rowData;
	private ParseRowType		rowType = new ParseRowType();
	private String				rowTypeIdentifier;
	private boolean				isValid;
	private List<String>		columnData = new ArrayList<String>();

	public ParseRow() {	}

	public ParseRow(ParseRow row)
	{
		this.lineNumber = row.lineNumber;
		this.rowData = row.rowData;
		this.rowType = row.rowType;
		this.rowTypeIdentifier = row.rowTypeIdentifier;
		this.isValid = row.isValid;
	}
	
	// ----------------------------------------------------------
	// Getters & Setters (auto-generated)
	// ----------------------------------------------------------	

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int rowNumber) {
		this.lineNumber = rowNumber;
	}

	public String getRowData() {
		return rowData;
	}

	public void setRowData(String rowData) {
		this.rowData = rowData;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getRowTypeIdentifier() {
		return rowTypeIdentifier;
	}

	public void setRowTypeIdentifier(String rowTypeIdentifier) {
		this.rowTypeIdentifier = rowTypeIdentifier;
	}

	public List<String> getColumnData() {
		return columnData;
	}

	public void setColumnData(List<String> columnData) {
		this.columnData = columnData;
	}

	public ParseRowType getRowType() {
		return rowType;
	}

	public void setRowType(ParseRowType rowType) {
		this.rowType = rowType;
	}

	
}
