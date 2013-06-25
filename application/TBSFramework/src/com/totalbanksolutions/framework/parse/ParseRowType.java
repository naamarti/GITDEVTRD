package com.totalbanksolutions.framework.parse;

/**
 * @author vcatrini
 */
public class ParseRowType 
{	
	private ParseRecordType		recordType;				  		// ParseConstants. RECORD_TYPE_HEADER | RECORD_TYPE_DATA | RECORD_TYPE_TRAILER
	private ParseDirectionType	directionType;			  		// ParseConstants. DIRECTION_INPUT | DIRECTION_OUTPUT
	private int					recordGroupNumber;		  		// there may be multiple types of DATA records, each with different parsing rules
	private int					recordSequenceNumber; 	  		// records may span multiple lines
	private int					recordMaxSequenceNumber;		// Last sequence number for this recordGroup	
	private int					recordMinRequiredSequenceNumber;// Must contain this many sequence rows to be a valid record
	
	public ParseRowType()
	{
		this.recordType = ParseRecordType.UNKNOWN;
		this.directionType = ParseDirectionType.INPUT;
		this.recordGroupNumber = 0;
		this.recordSequenceNumber = 0;
		this.recordMaxSequenceNumber = 0;
		this.recordMinRequiredSequenceNumber = 0;
	}

	public ParseRowType(ParseRecordType recordType, ParseDirectionType directionType)
	{
		this.recordType = recordType;
		this.directionType = directionType;
	}
	
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if((obj == null) || (obj.getClass() != this.getClass())) return false;
		// object must be same type at this point
		ParseRowType compare = (ParseRowType)obj;
		return this.recordType == compare.recordType
			&& this.recordGroupNumber == compare.recordGroupNumber
			&& this.recordSequenceNumber == compare.recordSequenceNumber
			&& this.recordMaxSequenceNumber == compare.recordMaxSequenceNumber
			&& this.recordMinRequiredSequenceNumber == compare.recordMinRequiredSequenceNumber
			&& this.directionType == compare.directionType;
			//&& (this.stringData == compare.stringData || (this.stringData != null && this.stringData.equals(compare.stringData)));
	}

	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + this.recordType.ordinal();
		hash = 31 * hash + this.recordGroupNumber;
		hash = 31 * hash + this.recordSequenceNumber;
		hash = 31 * hash + this.recordMaxSequenceNumber;
		hash = 31 * hash + this.recordMinRequiredSequenceNumber;
		hash = 31 * hash + this.directionType.ordinal();
		//hash = 31 * hash + (null == stringData ? 0 : stringData.hashCode());
		return hash;
	}

	public String toString()
	{
		StringBuffer out = new StringBuffer();
		out.append("recordType=" + this.recordType)
		.append("; recordGroupNumber=" + this.recordGroupNumber)
		.append("; recordSequenceNumber=" + this.recordSequenceNumber)
		.append("; recordMaxSequenceNumber=" + this.recordMaxSequenceNumber)
		.append("; recordMinRequiredSequenceNumber=" + this.recordMinRequiredSequenceNumber)		
		.append("; directionType=" + this.directionType);
		return out.toString();
	}
	
	// ----------------------------------------------------------
	// Getters & Setters (auto-generated)
	// ----------------------------------------------------------	
	
	public int getRecordGroupNumber() {
		return recordGroupNumber;
	}

	public void setRecordGroupNumber(int recordGroupNumber) {
		this.recordGroupNumber = recordGroupNumber;
	}

	public int getRecordSequenceNumber() {
		return recordSequenceNumber;
	}

	public void setRecordSequenceNumber(int recordSequenceNumber) {
		this.recordSequenceNumber = recordSequenceNumber;
	}

	public int getRecordMaxSequenceNumber() {
		return recordMaxSequenceNumber;
	}

	public void setRecordMaxSequenceNumber(int recordMaxSequenceNumber) {
		this.recordMaxSequenceNumber = recordMaxSequenceNumber;
	}

	public ParseDirectionType getDirectionType() {
		return directionType;
	}

	public void setDirectionType(ParseDirectionType directionType) {
		this.directionType = directionType;
	}

	public ParseRecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(ParseRecordType recordType) {
		this.recordType = recordType;
	}

	public int getRecordMinRequiredSequenceNumber() {
		return recordMinRequiredSequenceNumber;
	}

	public void setRecordMinRequiredSequenceNumber(int recordMinRequiredSequenceNumber) {
		this.recordMinRequiredSequenceNumber = recordMinRequiredSequenceNumber;
	}
	
}
