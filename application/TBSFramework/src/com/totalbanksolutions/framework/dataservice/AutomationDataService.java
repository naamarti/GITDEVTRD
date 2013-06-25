package com.totalbanksolutions.framework.dataservice;

import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.cache.CacheManager;
import com.totalbanksolutions.framework.dao.AutomationDAO;
import com.totalbanksolutions.framework.dao.jdbc.AutomationJDBC;
import com.totalbanksolutions.framework.model.database.view.ServiceQueueView;
import com.totalbanksolutions.framework.queue.QueueItem;
import com.totalbanksolutions.framework.queue.QueueStatus;
import com.totalbanksolutions.framework.queue.QueueTopic;

/**
 * =================================================================================================
 * Modified:  15 Sep 2012 VC #1859: Automate Late Processing : Service to Run Queued Jobs
 * =================================================================================================
 */
public class AutomationDataService 
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog( AutomationDataService.class );
    private AutomationDAO automationDAO = null;
    private CacheManager cacheManager = null;
    private DataService ds = null;

    public AutomationDataService ( DataSource dataSource, CacheManager cacheManager, DataService ds )
    {
    	this.automationDAO = new AutomationJDBC(dataSource);
    	this.cacheManager = cacheManager;
    	this.ds = ds;
    }
    
	public void close()
	{
		this.automationDAO.close();
		this.automationDAO = null;
		this.cacheManager = null;
		this.ds = null;
	}

    //============================================================================
	//
	// Service_Queue
	//
	//============================================================================
	public ServiceQueueView getServiceQueue()
	{
		return automationDAO.getServiceQueue();
	}

    public QueueItem getNextQueueItem( Set<QueueTopic> topicSet, boolean exclusionBased ) 
    {
    	QueueItem item = null;
    	ServiceQueueView t = automationDAO.getNextQueueItem(topicSet, exclusionBased);
    	log.debug( t.toString() );
    	
    	if( t.getRowList().size() > 0 )
    	{
        	item = new QueueItem();
	    	long id = t.getRow().getColumn( t.QUEUE_ID ).getValueAsLong();
	    	String topic = t.getRow().getColumn( t.QUEUE_TOPIC ).getValueAsString();
	    	String status = t.getRow().getColumn( t.QUEUE_STATUS ).getValueAsString();
	    	String message = t.getRow().getColumn( t.MESSAGE ).getValueAsString();
	
	    	if(topic.equalsIgnoreCase("ALERT"))
	    	{
	        	item.setTopic( QueueTopic.ALERT );
	    	}
	    	else if(topic.equalsIgnoreCase("DMS_PROCESS"))
	    	{
	        	item.setTopic( QueueTopic.DMS_PROCESS );
	    	}
	    	else if(topic.equalsIgnoreCase("FILE_IO"))
	    	{
	        	item.setTopic( QueueTopic.FILE_IO );
	    	}
	    	else if(topic.equalsIgnoreCase("FILE_WRITER"))
	    	{
	        	item.setTopic( QueueTopic.FILE_WRITER );
	    	}
	    	
	    	item.setQueueId(id);
    	}
    	return item;
    }
    
    public void updateQueueStatus( long queueID, QueueStatus status )
    {
    	
    }
	
}
