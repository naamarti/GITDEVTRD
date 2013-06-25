package com.totalbanksolutions.framework.model;

import java.sql.ResultSet;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.enums.JavaDataType;

public class DefaultModelColumn implements ModelColumn
{
    protected final Log log = LogFactory.getLog(DefaultModelColumn.class);
    
	private ModelColumnSpecification columnSpecification;
	private Object value;

	public DefaultModelColumn( ModelColumnSpecification columnSpecification )
	{
		super();
		this.columnSpecification = columnSpecification;
	}
	
	@Override
	public JavaDataType getDataType()
	{
		return this.columnSpecification.getDataType();
	}
	
	@Override
	public String getDescription()
	{
		return this.columnSpecification.getDescription();
	}

	@Override
	public String getLabel()
	{
		return this.columnSpecification.getLabel();
	}

	@Override
	public String getColumnName()
	{
		return this.columnSpecification.getColumnName();
	}

	@Override
	public int getSize()
	{
		return this.columnSpecification.getSize();
	}

	@Override
	public Object getValue()
	{
		if( this.getDataType() == JavaDataType.BOOLEAN )
		{
			return this.getValueAsBoolean();
		}
		else if( (this.getDataType() == JavaDataType.DATE) || (this.getDataType() == JavaDataType.DATETIME) )
		{
			return this.getValueAsDate();
		}
		else if( this.getDataType() == JavaDataType.DOUBLE )
		{
			return this.getValueAsDouble();
		}
		else if( this.getDataType() == JavaDataType.INT )
		{
			return this.getValueAsInteger();
		}
		else if( this.getDataType() == JavaDataType.LONG )
		{
			return this.getValueAsLong();
		}
		else if( this.getDataType() == JavaDataType.STRING )
		{
			return this.getValueAsString();
		}
		else
		{
			throw new RuntimeException( "Invalid JavaDataType specified for the column '" + this.getColumnName() + "' ." );
		}
	}
	
	@Override
	public Boolean getValueAsBoolean()
	{
		return this.defaultBoolean( this.value );
	}
	
	@Override
	public Date getValueAsDate() 
	{
		return this.defaultDate( this.value );
	}
	
	@Override
	public Double getValueAsDouble() 
	{
		return this.defaultDouble( this.value );
	}
	
	@Override
	public Integer getValueAsInteger() 
	{
		return this.defaultInteger( this.value );
	}
	
	@Override
	public Long getValueAsLong() 
	{
		return this.defaultLong( this.value );
	}
	
	@Override
	public String getValueAsString()
	{
		if( this.getDataType() == JavaDataType.BOOLEAN)
		{
			return this.getValueAsBoolean().toString();
		}
		else if( this.getDataType() == JavaDataType.DATE )
		{
			return DateFormatUtils.format( this.getValueAsDate(), "yyyyMMdd" );
		}
		else if( this.getDataType() == JavaDataType.DATETIME )
		{
			return DateFormatUtils.format( this.getValueAsDate(), "MM/dd/yyyy HH:mm:ss" );
		}
		else if( this.getDataType() == JavaDataType.DOUBLE )
		{
			return this.getValueAsDouble().toString();
		}
		else if( this.getDataType() == JavaDataType.INT )
		{
			return this.getValueAsInteger().toString();
		}
		else if( this.getDataType() == JavaDataType.LONG )
		{
			return this.getValueAsLong().toString();
		}
		else if( this.getDataType() == JavaDataType.STRING )
		{
			return this.defaultString( this.value );
		}
		else
		{
			throw new RuntimeException( "Invalid JavaDataType specified for the field." );
		}
	}
	
	@Override
	public void setValue( Object value )
	{
		try
		{
			if( this.getDataType() == JavaDataType.BOOLEAN )
			{
				if( value.toString().equalsIgnoreCase("on") )
				{
					this.value = true;
				}
				else
				{
					this.value = (Boolean)value;
				}
			}
			else if( (this.getDataType() == JavaDataType.DATE) || (this.getDataType() == JavaDataType.DATETIME) )
			{
				this.value = this.defaultDate( value );
			}
			else if( this.getDataType() == JavaDataType.DOUBLE )
			{
				this.value = this.defaultDouble ( value );
			}
			else if( this.getDataType() == JavaDataType.INT )
			{
				this.value = this.defaultInteger( value );
			}
			else if( this.getDataType() == JavaDataType.LONG )
			{
				this.value = this.defaultLong( value );
			}
			else if( this.getDataType() == JavaDataType.STRING )
			{
				String s = this.defaultString( value );
				if( this.getSize() > 0 )
				{
					s = StringUtils.substring( s, 0, this.getSize() );
				}
				this.value = s;
			}
			else
			{
				throw new RuntimeException( "Invalid JavaDataType specified for the column '" + this.getColumnName() + "' .");
			}
		}
		catch(Exception e)
		{
			String errorMessage = "DataType='" + this.getDataType() + "'. " + ExceptionUtils.getMessage(e);
			throw new RuntimeException(errorMessage , e);
		}		
	}
	
	public void setValueFromResultset( ResultSet rs, String databaseColumnName )
	{
		try
		{
			if( this.getDataType() == JavaDataType.BOOLEAN )
			{
				this.setValue( rs.getBoolean(databaseColumnName) );
			}
			else if( (this.getDataType() == JavaDataType.DATE) || (this.getDataType() == JavaDataType.DATETIME) )
			{
				this.setValue( rs.getTimestamp(databaseColumnName) );
			}
			else if( this.getDataType() == JavaDataType.DOUBLE )
			{
				this.setValue( rs.getDouble(databaseColumnName) );
			}
			else if( this.getDataType() == JavaDataType.INT )
			{
				this.setValue( rs.getInt(databaseColumnName) );
			}
			else if( this.getDataType() == JavaDataType.LONG )
			{
				this.setValue( rs.getLong(databaseColumnName) );
			}
			else if( this.getDataType() == JavaDataType.STRING )
			{
				this.setValue( StringUtils.defaultString(rs.getString(databaseColumnName)).trim() );
			}
			else
			{
				throw new RuntimeException( "Invalid JavaDataType specified for the column '" + databaseColumnName + "' ." );
			}		
		}
		catch( Exception e )
		{
			throw new RuntimeException(e);
		}						
	}
	
	public String toString()
	{
		StringBuffer b = new StringBuffer("")
		.append("value=").append(this.getValueAsString())
		.append("; ").append(this.columnSpecification.toString());
		return b.toString();
	}
	
	private Boolean defaultBoolean( Object obj )
	{
		if( obj == null )
		{
			return false;
		}
		return (Boolean)obj;
	}
	
	private Date defaultDate( Object obj )
	{
		if( obj == null )
		{
			try
			{
				String[] formats = new String[] {"dd-MMM-yyyy", "d-MMM-yyyy", "MM/dd/yyyy"};
				String date = "01-JAN-1900";
				return DateUtils.parseDate( date, formats );
			}
			catch(Exception e) {}
		}
		return (Date)obj;
	}
	
	private Double defaultDouble( Object obj )
	{
		if( obj == null )
		{
			return Double.parseDouble( "0" );
		}
		else if( obj instanceof String )
		{
			if( ((String)obj).length() == 0 ) obj = "0";
			String s = obj.toString();
			if( s.length() == 0 ) s = "0";
			s = s.replaceAll("\\$", "");
			s = s.replaceAll(",", "");
			return Double.parseDouble( s );
		}
		return (Double)obj;
	}

	private Integer defaultInteger( Object obj )
	{
		if( obj == null )
		{
			return Integer.parseInt( "0" );
		}
		else if( obj instanceof String )
		{
			String s = obj.toString();
			if( s.length() == 0 ) obj = "0";
			return Integer.parseInt( s );
		}
		return (Integer)obj;
	}

	private Long defaultLong( Object obj )
	{
		if( obj == null )
		{
			return Long.parseLong( "0" );
		}
		else if( obj instanceof String )
		{
			String s = obj.toString();
			if( s.length() == 0 ) obj = "0";
			return Long.parseLong( s );
		}
		return (Long)obj;
	}

	private String defaultString( Object obj )
	{
		if( obj == null )
		{
			return "";
		}
		return (String)obj;
	}
	
}
