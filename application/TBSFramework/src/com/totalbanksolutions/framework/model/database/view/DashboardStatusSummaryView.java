package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  17 Oct 2012 NM #    : dataservice for all dashboard related items
 * =================================================================================================
 */
public class DashboardStatusSummaryView extends DefaultModelTable
{	
	//TODO: change all fields in this view to whatever your sproc needs
	
	public final String QUEUE_ID					= "queueId";
	public final String QUEUE_TOPIC_ID				= "queueTopicId";
	public final String QUEUE_TOPIC					= "queueTopic";
	public final String QUEUE_STATUS_ID				= "queueStatusId";
	public final String QUEUE_STATUS				= "queueStatus";
	public final String MESSAGE						= "message";
	public final String LAST_MODIFIED 				= "lastModified";

	public DashboardStatusSummaryView()
	{
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		
		this.addColumn( this.QUEUE_ID,				"ServiceQueue_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		"Queue ID",					"Queue ID");
		this.addColumn( this.QUEUE_TOPIC_ID,		"ServiceQueueTopic_FK",		DatabaseDataType.DECIMAL_LONGINT,	0,		"Topic ID",					"Topic ID of the queued job");
		this.addColumn( this.QUEUE_TOPIC,			"Topic",					DatabaseDataType.CHAR,				20,		"Topic",					"Topic of the queued job");
		this.addColumn( this.QUEUE_STATUS_ID,		"ServiceQueueStatus_FK",	DatabaseDataType.DECIMAL_LONGINT,	0,		"Status ID",				"Status ID of the queued job");
		this.addColumn( this.QUEUE_STATUS,			"Status",					DatabaseDataType.CHAR,				20,		"Status",					"Status of the queued job");
		this.addColumn( this.MESSAGE,				"Message",					DatabaseDataType.CHAR,				1000,	"Message",					"Message attributes of the queued job");
		this.addColumn( this.LAST_MODIFIED,			"LastModifiedDateTime",		DatabaseDataType.DATETIME2,			0,		"Last Modified",			"Last Modified Date and Time");	
	}

}


