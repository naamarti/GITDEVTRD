package com.totalbanksolutions.framework.model.database.view;

import com.totalbanksolutions.framework.bean.database.Databases;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.model.DefaultModelTable;

/**
 * =================================================================================================
 * Modified:  15 Sep 2012 VC #1859: Automate Late Processing : Service to Run Queued Jobs
 * =================================================================================================
 */
public class ServiceQueueView extends DefaultModelTable
{	
	public final String QUEUE_ID					= "queueId";
	public final String QUEUE_TOPIC_ID				= "queueTopicId";
	public final String QUEUE_TOPIC					= "queueTopic";
	public final String QUEUE_STATUS_ID				= "queueStatusId";
	public final String QUEUE_STATUS				= "queueStatus";
	public final String MESSAGE						= "message";
	public final String LAST_MODIFIED 				= "lastModified";

	public ServiceQueueView()
	{
		this.setDatabaseName( Databases.TBS_Common );
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

