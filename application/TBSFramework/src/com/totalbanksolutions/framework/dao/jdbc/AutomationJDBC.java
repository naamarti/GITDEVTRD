package com.totalbanksolutions.framework.dao.jdbc;

import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dao.AutomationDAO;
import com.totalbanksolutions.framework.dao.util.DatabaseHelper;
import com.totalbanksolutions.framework.dao.util.SQLParameters;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.database.view.ServiceQueueView;
import com.totalbanksolutions.framework.queue.QueueTopic;

/**
 * =================================================================================================
 * Modified:  15 Sep 2012 VC #1859: Automate Late Processing : Service to Run Queued Jobs
 * =================================================================================================
 */
public class AutomationJDBC implements AutomationDAO
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    private DataSource dataSource;
    private DatabaseHelper databaseHelper;
    
	public AutomationJDBC(DataSource ds)
	{
		setDataSource(ds);
	}
	
    public void setDataSource(DataSource ds) {
		this.dataSource = ds;
        this.databaseHelper = new DatabaseHelper(dataSource);
    }
    
    public void close()
    {
    	this.databaseHelper.close();
    	this.databaseHelper = null;
    	this.dataSource = null;
    }
    
	//============================================================================
	//
    // Service_Queue
    //
	//============================================================================
    public ServiceQueueView getServiceQueue() 
	{
    	ServiceQueueView t = new ServiceQueueView();
		String sql = "select Q.*, T.Topic, S.Status "
		    + "from " + Databases.COMMON + "..ServiceQueue Q "
		    + "inner join " + Databases.COMMON + "..ServiceQueue_Topic T on T.ServiceQueueTopic_PK = Q.ServiceQueueTopic_FK "
		    + "inner join " + Databases.COMMON + "..ServiceQueue_Status S on S.ServiceQueueStatus_PK = Q.ServiceQueueStatus_FK "
		    + "order by ServiceQueue_PK ";
		    //+ "where S.Status = 'PENDING' "
		    //+ "and T.Topic not in ('ALERT') ";
		SQLParameters params = new SQLParameters();
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
	}

    public ServiceQueueView getNextQueueItem(Set<QueueTopic> topicSet, boolean exclusionBased) 
	{
    	StringBuffer topicList = new StringBuffer();
    	for( QueueTopic qc : topicSet )
    	{
    		topicList.append( "'" ).append( qc.name() ).append( "'").append(",");
    	}
    	topicList.deleteCharAt( topicList.length() - 1 );

    	log.debug("### topicList for queue retrieval is : " + topicList.toString() + " ###");
    	
    	String sql = "EXEC " + Databases.COMMON + "..p_j_ServiceQueue_GetNextItem @ExclusionBased=?, @TopicList=?";
		SQLParameters params = new SQLParameters();
		params.addValue(exclusionBased);
		params.addValue(topicList.toString());
    	ServiceQueueView t = new ServiceQueueView();
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
	}
    
}
