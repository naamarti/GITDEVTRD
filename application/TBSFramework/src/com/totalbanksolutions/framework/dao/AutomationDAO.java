package com.totalbanksolutions.framework.dao;

import java.util.Set;

import com.totalbanksolutions.framework.dao.util.DAO;
import com.totalbanksolutions.framework.model.database.view.ServiceQueueView;
import com.totalbanksolutions.framework.queue.QueueItem;
import com.totalbanksolutions.framework.queue.QueueTopic;

/**
 * =================================================================================================
 * Modified:  15 Sep 2012 VC #1859: Automate Late Processing : Service to Run Queued Jobs
 * =================================================================================================
 */
public interface AutomationDAO extends DAO
{

    public void close();
	
	//============================================================================
	//
    // AutoRecon
    //
	//============================================================================
    public ServiceQueueView getServiceQueue();
    public ServiceQueueView getNextQueueItem( Set<QueueTopic> topicSet, boolean exclusionBased );
    
}
