package com.totalbanksolutions.framework.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.JavaDataType;

public class DefaultModelColumnSpecification implements ModelColumnSpecification
{
	protected final Log log = LogFactory.getLog(DefaultModelColumnSpecification.class);

    private String				columnName;
	private String				description;
	private JavaDataType		javaDataType;
	private String				label;
	private int					size;

	private String				databaseColumnName;
	private DatabaseDataType 	databaseDataType;
	private boolean				databaseIsIdentityAutoSequence;

	public DefaultModelColumnSpecification()
	{
		super();
	}
	
	public DefaultModelColumnSpecification( DefaultModelColumnSpecification col )
	{
		this( col.getColumnName(), col.getDataType(), col.getSize(), col.getLabel(), col.getDescription() );
	}
		
	public DefaultModelColumnSpecification( String columnName, JavaDataType javaDataType, int size )
	{
		this( columnName, javaDataType, size, "", "" );
	}
	
	public DefaultModelColumnSpecification( String columnName, JavaDataType javaDataType, int size, String label, String description )
	{
		this.columnName = columnName;
		this.javaDataType = javaDataType;
		this.size = size;
		this.label = label;
		this.description = description;
	}

	public DefaultModelColumnSpecification( String columnName, JavaDataType javaDataType, String databaseColumnName, DatabaseDataType databaseDataType, int size, String label, String description )
	{
		this.columnName = columnName;
		this.javaDataType = javaDataType;
		this.databaseColumnName = databaseColumnName;
		this.databaseDataType = databaseDataType;
		this.size = size;
		this.label = label;
		this.description = description;
		if( javaDataType == JavaDataType.AUTO_FROM_DATABASE_TYPE )
		{
			this.javaDataType = getAutoJavaDataType();
		}
		
	}
	
	@Override
	public String getColumnName() 
	{
		return this.columnName;
	}

	@Override
	public JavaDataType getDataType() 
	{
		return javaDataType;
	}
	
	@Override
	public String getDescription() 
	{
		return description;
	}
	
	@Override
	public String getLabel() 
	{
		return label;
	}

	@Override
	public int getSize() 
	{
		return size;
	}

//	@Override
	protected String getDatabaseColumnName()
	{
		return this.databaseColumnName;
	}

//	@Override
	protected DatabaseDataType getDatabaseDataType()
	{
		return this.databaseDataType;
	}

//	@Override
	protected boolean getDatabaseIsIdentityAutoSequence()
	{
		return this.databaseIsIdentityAutoSequence;
	}
	
	protected void setColumnName( String columnName ) 
	{
		this.columnName = columnName;
	}

	protected void setDataType( JavaDataType javaDataType )
	{
		this.javaDataType = javaDataType;
	}
	
	protected void setDescription( String description ) 
	{
		this.description = description;
	}
	
	protected void setLabel( String label ) 
	{
		this.label = label;
	}

	protected void setSize( int size ) 
	{
		this.size = size;
	}

	public String toString()
	{
		StringBuffer b = new StringBuffer("")
		.append("columnName=").append(this.getColumnName())
		.append("; dataType=").append(this.getDataType())
		.append("; databaseColumnName=").append(this.getDatabaseColumnName())
		.append("; databaseDataType=").append(this.getDatabaseDataType())
		.append("; databaseIsIdentity=").append(this.getDatabaseIsIdentityAutoSequence())		
		.append("; size=").append(this.getSize())
		.append("; label=").append(this.getLabel())
		.append("; description=").append(this.getDescription());
		return b.toString();
	}

	private JavaDataType getAutoJavaDataType()
	{
		if( this.databaseDataType == DatabaseDataType.BIT )
		{
			return JavaDataType.BOOLEAN;
		}
		else if( this.databaseDataType == DatabaseDataType.CHAR )
		{
			return JavaDataType.STRING;
		}
		else if( this.databaseDataType == DatabaseDataType.DATETIME )
		{
			return JavaDataType.DATE;
		}
		else if( this.databaseDataType == DatabaseDataType.DATETIME2 )
		{
			return JavaDataType.DATETIME;
		}
		else if( this.databaseDataType == DatabaseDataType.DECIMAL_AMOUNT )
		{
			return JavaDataType.DOUBLE;
		}
		else if( this.databaseDataType == DatabaseDataType.DECIMAL_LONGINT )
		{
			return JavaDataType.LONG;
		}
		else if( this.databaseDataType == DatabaseDataType.INT )
		{
			return JavaDataType.INT;
		}
		else
		{
			throw new RuntimeException( "Invalid DatabaseDataType specified for the column." );
		}
	}
	
}
