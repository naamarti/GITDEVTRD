package com.totalbanksolutions.framework.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.enums.DatabaseDataType;

public class AutoMapModelTable extends DefaultModelTable 
{
    protected final Log log = LogFactory.getLog( AutoMapModelTable.class );
    private boolean isInitialized = false;
    
	@Override
	public ModelRow addRow( ResultSet rs )
	{
		if( !isInitialized )
		{
			this.isInitialized = true;
			try
			{
				ResultSetMetaData metaData = rs.getMetaData();
				int colCount = metaData.getColumnCount();
				for(int i = 1; i <= colCount; i++)
				{
					addFieldDefinition( metaData.getColumnName(i), metaData.getColumnLabel(i), metaData.getColumnClassName(i), metaData.getColumnTypeName(i), metaData.getPrecision(i), metaData.getScale(i) );
				}
			}
			catch(Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		return super.addRow(rs);
	}
	
	private void addFieldDefinition( String colDatabaseName, String colLabel, String colClassName, String colTypeName, int colPrecision, int colScale )
	{
		log.debug( "***** NAME=" + colDatabaseName + "; LABEL=" + colLabel + "; CLASSNAME=" + colClassName + "; TYPENAME=" + colTypeName + "; PRECISION=" + colPrecision + "; SCALE=" + colScale );
		String columnName = colDatabaseName.substring(0, 1).toLowerCase() + colDatabaseName.substring(1);
		DatabaseDataType dataType = DatabaseDataType.CHAR;
		if( colTypeName.equalsIgnoreCase("bit") )
		{
			dataType = DatabaseDataType.BIT;
		}
		else if( colTypeName.equalsIgnoreCase("datetime") )
		{
			dataType = DatabaseDataType.DATETIME;
		}
		else if( colTypeName.equalsIgnoreCase("decimal") || colTypeName.equalsIgnoreCase("int") )
		{
			if( colScale > 0 )
			{
				dataType = DatabaseDataType.DECIMAL_AMOUNT;
			}
			else
			{
				dataType = DatabaseDataType.DECIMAL_LONGINT;
			}
		}
		super.addColumn( columnName, colDatabaseName, dataType, colPrecision );
	}
}
