package com.totalbanksolutions.framework.queue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.totalbanksolutions.framework.bean.database.table.DailyProcessStatus;
import com.totalbanksolutions.framework.dataservice.DataService;


/**
 * @author dmerkel
 *
 */
public class QueueHelper 
{
	private DataService ds 					= null;
	private boolean inclusionBased			= false;
	private boolean exclusionBased			= false;
	private Set <QueueTopic> topicSet 		= null;
	
	/*
	 * V.Catrini
	 */
	public QueueHelper( DataService ds )
	{
		this.ds = ds;
		this.topicSet = new HashSet <QueueTopic>();
	}

	public void setTopic( QueueTopic topic ){};
	public void setBatchId( long batchId ){};
	public void setBusinessDate( Date d ){};
	public void setStepId( long step ){};
	public void setUserName( String user ){};
	public void setOperationType( long operationTypeId ){};
	public void addMessageField( String fieldName, String value ){};
	public void putMessage(){};
	public void updateState( long queueID, QueueStatus stateChange ){};
	public void updateBatchState( long queueID, DailyProcessStatus stateChange ){};

	/*****************************************************************
	 * V.Catrini
	 * This is how I envision to use it from WorkerService.
	 * Want to hide dirty details from usage code.
	 * 
	 * QueueHelper qh = new QueueHelper( ds );
	 * qh.setTopicToExclude( QueueTopic.ALERT );
	 * ...
	 * begin loop
	 *     ...
	 *     QueueItem item = qh.getNextItem();
	 *     Factory.getWorker( item.getTopic() );
	 *     ...
	 * end loop
	 *****************************************************************/

	/*
	 * V.Catrini
	 * Set topic to exclude.  
	 * QueueHelpers can be either inclusion based or exclusion based, but not both.
	 * 
	 */
	public void setTopicToExclude( QueueTopic topic ) 
	{ 
		if( inclusionBased )
		{
			throw new RuntimeException("This QueueHelper is already INCLUSION based.  No exclusions can be added once inclusions are already added.");
		}

		this.exclusionBased = true;
		this.topicSet.add(topic);
	}

	/*
	 * Set topic to include.  
	 * QueueHelpers can be either inclusion based or exclusion based, but not both.
	 * 
	 */
	public void setTopicToInclude( QueueTopic topic ) 
	{ 
		if( exclusionBased )
		{
			throw new RuntimeException("This QueueHelper is already EXCLUSION based.  No inclusions can be added once exclusions are already added.");
		}
		
		this.inclusionBased = true;
		this.topicSet.add(topic);
	}
	
	/*
	 * V.Catrini
	 * Get next item from queue
	 * That is based on inclusion/exclusion lists set in this Helper
	 * Gets PENDING items only
	 * Locks item by moving it to ASSIGNED
	 */
	public QueueItem getNextItem() 
	{ 
		QueueItem item = this.ds.automation.getNextQueueItem( this.topicSet, this.exclusionBased );
		return item;
	}
	
	public void updateQueueStatus( long queueID, QueueStatus status )
	{
		this.ds.automation.getServiceQueue();
	};

}
