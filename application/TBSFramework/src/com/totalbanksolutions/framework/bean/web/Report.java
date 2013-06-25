package com.totalbanksolutions.framework.bean.web;

import com.totalbanksolutions.framework.bean.util.AbstractBean;
import com.totalbanksolutions.framework.enums.JavaDataType;

public class Report extends AbstractBean<Report.Field> 
{
	public enum Field
	{
		 REPORT_ID
		,REPORT_NAME
		,DIRECTORY_NAME
		,FILE_NAME
		,IS_AVAILABLE
		;
	}
		
	public Report()
	{
		super();
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Java_DataType				Size
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.REPORT_ID,					JavaDataType.LONG,			0);
		super.addField(Field.REPORT_NAME,				JavaDataType.STRING,		0);
		super.addField(Field.DIRECTORY_NAME,			JavaDataType.STRING,		0);
		super.addField(Field.FILE_NAME,					JavaDataType.STRING,		0);
		super.addField(Field.IS_AVAILABLE,				JavaDataType.BOOLEAN,		0);
	}

	public Report(long id, String reportName, String directoryName, String fileName)
	{
		this();
		this.getField(Report.Field.REPORT_ID).setValue(id);
		this.getField(Report.Field.REPORT_NAME).setValue(reportName);
		this.getField(Report.Field.DIRECTORY_NAME).setValue(directoryName);
		this.getField(Report.Field.FILE_NAME).setValue(fileName);
	}
}
