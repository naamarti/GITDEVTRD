package com.totalbanksolutions.framework.dao.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.util.BeanResultSetMapper;
import com.totalbanksolutions.framework.model.ModelTable;

public class DatabaseHelper 
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    private DataSource dataSource;
    
    public DatabaseHelper(DataSource dataSource)
    {
    	this.dataSource = dataSource;
    }

    public void close()
    {
    	this.dataSource = null;
    }
    
    public DataBatchManager getBatchManager()
    {
    	DataBatchManager batchManager = null;
		try
		{
	    	Connection con = this.dataSource.getConnection();
			batchManager = new DataBatchManager(con);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		return batchManager;
	}

    public BeanResultSetMapper queryForObject(String sql, SQLParameters params, BeanResultSetMapper resultSetMapper)
	{
    	BeanResultSetMapper item = null;
    	Connection con = null;
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		try
		{
	    	con = this.dataSource.getConnection();
			statement = con.prepareStatement(sql);
			bindParameters(statement, params);
			rs = statement.executeQuery();
			while(rs.next())
			{
				BeanResultSetMapper mapper = resultSetMapper.newInstance();
				mapper.bindValuesFromResultset(rs);
				item = mapper;
				break;
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
				con.close();				
			}
			catch(Exception e) { }
		}
		return item;
	}

	@SuppressWarnings("unchecked")
	public <T extends BeanResultSetMapper> List<T> queryForList(String sql, SQLParameters params, T resultSetMapper)
	{
    	List<T> list = new ArrayList<T>();
    	Connection con = null;
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		try
		{
	    	con = this.dataSource.getConnection();
			statement = con.prepareStatement(sql);
			bindParameters(statement, params);
			rs = statement.executeQuery();
			while(rs.next())
			{
				T mapper = (T)resultSetMapper.newInstance();
				mapper.bindValuesFromResultset(rs);
				list.add(mapper);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
				con.close();				
			}
			catch(Exception e) { }
		}
		return list;
	}

	public void queryForModelTable( String sql, ModelTable t )
	{
    	Connection con = null;
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		try
		{
	    	con = this.dataSource.getConnection();
			statement = con.prepareStatement(sql);
			rs = statement.executeQuery();
			while(rs.next())
			{
				t.addRow(rs);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
				con.close();				
			}
			catch(Exception e) { }
		}
	}
	
	public void queryForModelTable( String sql, SQLParameters params, ModelTable t )
	{
    	Connection con = null;
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		try
		{
	    	con = this.dataSource.getConnection();
			statement = con.prepareStatement(sql);
			bindParameters(statement, params);
			rs = statement.executeQuery();
			while(rs.next())
			{
				t.addRow(rs);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
				con.close();				
			}
			catch(Exception e) { }
		}
	}
	
    public List<Long> queryForLongList(String sql, SQLParameters params)
	{
    	List<Long> list = new ArrayList<Long>();
    	Connection con = null;
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		try
		{
	    	con = this.dataSource.getConnection();
			statement = con.prepareStatement(sql);
			bindParameters(statement, params);
			rs = statement.executeQuery();
			while(rs.next())
			{
				Long item = rs.getLong(1);
				list.add(item);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
				con.close();				
			}
			catch(Exception e) { }
		}
		return list;
	}

    public Boolean queryForBoolean(String sql, SQLParameters params)
    {
		try
		{
			Object value = queryForScalarValue(sql, params, new Boolean(false));
	    	if(value == null)
	    	{
	    		return null;
	    	}
	    	else
	    	{
	    		return (Boolean)value;
	    	}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}			
	}
    
    public Date queryForDate(String sql, SQLParameters params)
    {
		try
		{
			Object value = queryForScalarValue(sql, params, new Date());
	    	if(value == null)
	    	{
	    		return null;
	    	}
	    	else
	    	{
	    		return (Date)value;
	    	}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}			
	}

    public Double queryForDouble(String sql, SQLParameters params)
    {
		try
		{
			Object value = queryForScalarValue(sql, params, new Double(0));
	    	if(value == null)
	    	{
	    		return null;
	    	}
	    	else
	    	{
	    		return (Double)value;
	    	}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}			
	}

    public Integer queryForInteger(String sql, SQLParameters params)
    {
		try
		{
			Object value = queryForScalarValue(sql, params, new Integer(0));
	    	if(value == null)
	    	{
	    		return null;
	    	}
	    	else
	    	{
	    		return (Integer)value;
	    	}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}			
	}

    public Long queryForLong(String sql, SQLParameters params)
    {
		try
		{
			Object value = queryForScalarValue(sql, params, new Long(0));
	    	if(value == null)
	    	{
	    		return null;
	    	}
	    	else
	    	{
	    		return (Long)value;
	    	}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}			
	}

    public String queryForString(String sql, SQLParameters params)
	{
		try
		{
			Object value = queryForScalarValue(sql, params, new String());
	    	if(value == null)
	    	{
	    		return null;
	    	}
	    	else
	    	{
	    		return StringUtils.defaultString((String)value).trim();
	    	}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}			
	}
    
    private Object queryForScalarValue(String sql, SQLParameters params, Object returnType)
	{
    	Object value = null;
    	Connection con = null;
		PreparedStatement statement = null;
    	ResultSet rs = null;
		try
		{
			con = this.dataSource.getConnection();
			statement = con.prepareStatement(sql);
			bindParameters(statement, params);
			rs = statement.executeQuery();
			value = getResultSetScalarValue(rs, returnType);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
				con.close();				
			}
			catch(Exception e) { }
		}    	
		return value;
	}

    public long executeInsert(String sql, SQLParameters params)
	{
    	Connection con = null;
		PreparedStatement statement = null;
    	ResultSet rs = null;
		long generatedId = 0;
		try
		{
	    	con = this.dataSource.getConnection();
			statement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			bindParameters(statement, params);
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
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
				con.close();
			}
			catch(Exception e) { }
		}
		return generatedId;
	}

    public int executeUpdate(String sql, SQLParameters params)
	{
    	Connection con = null;
		PreparedStatement statement = null;
		int updateCount = 0;
		try
		{
			con = this.dataSource.getConnection();
			statement = con.prepareStatement(sql);
			bindParameters(statement, params);
			updateCount = statement.executeUpdate();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				statement.close();
				con.close();				
			}
			catch(Exception e) { }
		}
		return updateCount;
	}

    public void executeStoredProcedureUpdate(String sql, SQLParameters params)
	{
    	Connection con = null;
		CallableStatement statement = null;
		try
		{
			con = this.dataSource.getConnection();
			statement = con.prepareCall(sql);
			bindParameters(statement, params);
			statement.execute();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				statement.close();
				con.close();				
			}
			catch(Exception e) { }
		}    
	}

    public BeanResultSetMapper executeStoredProcedureForObject(String sql, SQLParameters params, BeanResultSetMapper resultSetMapper)
	{
    	BeanResultSetMapper item = null;
    	Connection con = null;
		CallableStatement statement = null;
    	ResultSet rs = null;
		try
		{
			con = this.dataSource.getConnection();
			statement = con.prepareCall(sql);
			bindParameters(statement, params);
			rs = statement.executeQuery();
			while(rs.next())
			{
				BeanResultSetMapper mapper = resultSetMapper.newInstance();
				mapper.bindValuesFromResultset(rs);
				item = mapper;
				break;
			}			
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
				con.close();				
			}
			catch(Exception e) { }
		}    	
		return item;
	}

    @SuppressWarnings("unchecked")
	public <T extends BeanResultSetMapper> List<T> executeStoredProcedureForList(String sql, SQLParameters params, T resultSetMapper)
	{
    	List<T> list = new ArrayList<T>();
    	Connection con = null;
		CallableStatement statement = null;
    	ResultSet rs = null;
		try
		{
	    	con = this.dataSource.getConnection();
			statement = con.prepareCall(sql);
			bindParameters(statement, params);
			rs = statement.executeQuery();
			while(rs.next())
			{
				T mapper = (T)resultSetMapper.newInstance();
				mapper.bindValuesFromResultset(rs);
				list.add(mapper);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
				con.close();				
			}
			catch(Exception e) { }
		}
		return list;
	}

	public void executeStoredProcedureForModelTable( String sql, SQLParameters params, ModelTable t )
	{
    	Connection con = null;
		CallableStatement statement = null;
    	ResultSet rs = null;
		try
		{
	    	con = this.dataSource.getConnection();
			statement = con.prepareCall(sql);
			bindParameters(statement, params);
			rs = statement.executeQuery();
			while(rs.next())
			{
				t.addRow(rs);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
				con.close();				
			}
			catch(Exception e) { }
		}
	}
    
    public Boolean executeStoredProcedureForBoolean(String sql, SQLParameters params)
    {
		try
		{
			Object value = executeStoredProcedureForScalarValue(sql, params, new Boolean(false));
	    	if(value == null)
	    	{
	    		return null;
	    	}
	    	else
	    	{
	    		return (Boolean)value;
	    	}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}			
	}
    
    public Date executeStoredProcedureForDate(String sql, SQLParameters params)
    {
		try
		{
			Object value = executeStoredProcedureForScalarValue(sql, params, new Date());
	    	if(value == null)
	    	{
	    		return null;
	    	}
	    	else
	    	{
	    		return (Date)value;
	    	}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}			
	}

    public Double executeStoredProcedureForDouble(String sql, SQLParameters params)
    {
		try
		{
			Object value = executeStoredProcedureForScalarValue(sql, params, new Double(0));
	    	if(value == null)
	    	{
	    		return null;
	    	}
	    	else
	    	{
	    		return (Double)value;
	    	}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}			
	}

    public Integer executeStoredProcedureForInteger(String sql, SQLParameters params)
    {
		try
		{
			Object value = executeStoredProcedureForScalarValue(sql, params, new Integer(0));
	    	if(value == null)
	    	{
	    		return null;
	    	}
	    	else
	    	{
	    		return (Integer)value;
	    	}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}			
	}
    
    public String executeStoredProcedureForString(String sql, SQLParameters params)
	{
		try
		{
			Object value = executeStoredProcedureForScalarValue(sql, params, new String());
	    	if(value == null)
	    	{
	    		return null;
	    	}
	    	else
	    	{
	    		return StringUtils.defaultString((String)value).trim();
	    	}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}			
	}
    
    private Object executeStoredProcedureForScalarValue(String sql, SQLParameters params, Object returnType)
	{
    	Object value = null;
    	Connection con = null;
		CallableStatement statement = null;
    	ResultSet rs = null;
		try
		{
			con = this.dataSource.getConnection();
			statement = con.prepareCall(sql);
			bindParameters(statement, params);
			rs = statement.executeQuery();
			value = getResultSetScalarValue(rs, returnType);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
				con.close();				
			}
			catch(Exception e) { }
		}    	
		return value;
	}

    private static Object getResultSetScalarValue(ResultSet rs, Object returnType)
    {
    	Object value = null;
    	try
    	{
			if(rs.next())
			{
				if(returnType instanceof Boolean)
				{
					value = rs.getBoolean(1);
				}
				else if(returnType instanceof java.util.Date)
				{
					value = rs.getDate(1);
				}
				else if(returnType instanceof Double)
				{
					value = rs.getDouble(1);
				}
				else if(returnType instanceof Integer)
				{
					value = rs.getInt(1);
				}
				else if(returnType instanceof Long)
				{
					value = rs.getLong(1);
				}
				else if(returnType instanceof String)
				{
					value = rs.getString(1);
				}
			}	    	
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		return value;
    }
    
    public static void bindParameters(PreparedStatement statement, SQLParameters params)
    {
    	try
    	{
			if(params != null && params.getParamList().size() > 0)
			{
				int index = 1;
				for(Object param : params.getParamList())
				{
					if(param instanceof Boolean)
					{
						statement.setBoolean(index++, (Boolean)param);
					}					
					else if(param instanceof java.util.Date)
					{
						statement.setDate(index++, new java.sql.Date(((java.util.Date)param).getTime()));
					}					
					else if(param instanceof Double)
					{
						statement.setDouble(index++, (Double)param);
					}					
					else if(param instanceof Integer)
					{
						statement.setInt(index++, (Integer)param);
					}
					else if(param instanceof Long)
					{
						statement.setLong(index++, (Long)param);
					}
					else if(param instanceof String)
					{
						statement.setString(index++, (String)param);
					}					
				}
			}
    	}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
    }
    
}
