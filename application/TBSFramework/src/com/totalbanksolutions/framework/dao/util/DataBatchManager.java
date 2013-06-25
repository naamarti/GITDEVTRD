package com.totalbanksolutions.framework.dao.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataBatchManager 
{
    protected final Log log = LogFactory.getLog( DataBatchManager.class );
    //private final int BATCH_SIZE = 1000;

    private Connection con = null;
	private List<PreparedStatement> statementList = new ArrayList<PreparedStatement>();
	private PreparedStatement currentStatement = null;
	private boolean isInTransaction = false;
	
    public DataBatchManager(Connection con)
    {
    	this.con = con;
    }

    public void prepareSQL(String sql)
    {
		try
		{
			this.currentStatement = this.con.prepareStatement(sql);
			this.statementList.add(currentStatement);
		}
		catch(Exception e)
		{
			log.error( "Error in prepareSQL; " + ExceptionUtils.getMessage(e) + "; " + ExceptionUtils.getStackTrace(e) );
			throw new RuntimeException(e);
		}
	}
    
    public void prepareStoredProcedureSQL(String sql)
    {
		try
		{
			this.currentStatement = this.con.prepareCall(sql);
			this.statementList.add(currentStatement);
		}
		catch(Exception e)
		{
			log.error( "Error in prepareStoredProcedureSQL; " + ExceptionUtils.getMessage(e) + "; " + ExceptionUtils.getStackTrace(e) );
			throw new RuntimeException(e);
		}
	}

    public void addBatch(SQLParameters params)
    {
    	try
    	{
	    	this.currentStatement.clearParameters();
	    	DatabaseHelper.bindParameters(this.currentStatement, params);
	    	this.currentStatement.addBatch();
		}
		catch(Exception e)
		{
			log.error( "Error in addBatch; " + ExceptionUtils.getMessage(e) + "; " + ExceptionUtils.getStackTrace(e) );
			throw new RuntimeException(e);
		} 	
    }
    
    public void beginTransaction()
    {
    	try
    	{
    		this.con.setAutoCommit(false);
    		this.isInTransaction = true;
    	}
		catch(Exception e)
		{
			log.error( "Error in beginTransaction; " + ExceptionUtils.getMessage(e) + "; " + ExceptionUtils.getStackTrace(e) );
			throw new RuntimeException(e);
		}
    }
    
    public void commitTransaction()
    {
    	try
    	{
			this.con.commit();
    		this.con.setAutoCommit(true);
    		this.isInTransaction = false;
    	}
		catch(Exception e)
		{
			log.error( "Error in commitTransaction; " + ExceptionUtils.getMessage(e) + "; " + ExceptionUtils.getStackTrace(e) );
			throw new RuntimeException(e);
		}
    }

    public void rollbackTransaction()
    {
    	try
    	{
    		if(this.isInTransaction)
    		{
    			this.con.rollback();
        		this.con.setAutoCommit(true);
    		}
    		this.isInTransaction = false;
    	}
		catch(Exception e)
		{
			log.error( "Error in rollbackTransaction; " + ExceptionUtils.getMessage(e) + "; " + ExceptionUtils.getStackTrace(e) );
			throw new RuntimeException(e);
		}
    }

    public void executeBatch()
    {
    	try
    	{
    		for(PreparedStatement statement : statementList)
    		{
//    			int[] updateCounts = statement.executeBatch();
    			statement.executeBatch();
    		}
		}
		catch(Exception e)
		{
			log.error( "Error in executeBatch; " + ExceptionUtils.getMessage(e) + "; " + ExceptionUtils.getStackTrace(e) );
			throw new RuntimeException(e);
		} 	
    }

    public long executeInsert(String sql, SQLParameters params)
	{
		PreparedStatement statement = null;
    	ResultSet rs = null;
		long generatedId = 0;
		try
		{
			statement = this.con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			DatabaseHelper.bindParameters(statement, params);
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			if(rs.next())
			{
				String value = rs.getString(1);
				if(value != null)
				{
					generatedId = new Long(value);
				}
			}
		}
		catch(Exception e)
		{
			log.error( "Error in executeInsert; " + ExceptionUtils.getMessage(e) + "; " + ExceptionUtils.getStackTrace(e) );
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				if(rs != null) rs.close();
				if(statement != null) statement.close();
			}
			catch(Exception e) { }
		}
		return generatedId;
	}

    public int executeUpdate(String sql, SQLParameters params)
	{
		PreparedStatement statement = null;
		int updateCount = 0;
		try
		{
			statement = this.con.prepareStatement(sql);
			DatabaseHelper.bindParameters(statement, params);
			updateCount = statement.executeUpdate();
		}
		catch(Exception e)
		{
			log.error( "Error in executeUpdate; " + ExceptionUtils.getMessage(e) + "; " + ExceptionUtils.getStackTrace(e) );
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				if(statement != null) statement.close();
			}
			catch(Exception e) { }
		}
		return updateCount;
	}
    
    public void close()
	{
		try
		{
    		if(this.isInTransaction)
    		{
    			rollbackTransaction();
    		}
    		for(PreparedStatement statement : statementList)
    		{
        		if(statement != null) statement.close();
    		}
    		if(this.currentStatement != null)
    		{
    			this.currentStatement.close();
    			this.currentStatement = null;
    		}
    		this.statementList.clear();
    		this.statementList = null;
		}
		catch(Exception e) 
		{ 
			log.error( "Error in close; " + ExceptionUtils.getMessage(e) + "; " + ExceptionUtils.getStackTrace(e) );
		}
		finally
		{
			try
			{
				if(con != null) 
				{
		    		this.con.setAutoCommit(true);
					con.close();
				}
			}
			catch(Exception e) { }
		}
	}

}
