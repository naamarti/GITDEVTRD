package com.totalbanksolutions.framework.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.JavaDataType;

public class DefaultModelTable implements ModelTable
{
    protected final Log log = LogFactory.getLog(DefaultModelTable.class);

    private String										databaseName = "";
    private String										tableName = "";
    private Map<String,ModelColumnSpecification>		columnMap = new LinkedHashMap<>();
	private Map<String,List<String>>					databaseColumnMap = new HashMap<>();
	private List<ModelRow>								rowList = new ArrayList<>();
	private final String								newLine = System.getProperty("line.separator");
	
	protected DefaultModelTable()
	{
		super();
	}
	
	protected DefaultModelTable( String databaseName, String tableName )
	{
		super();
		this.databaseName = databaseName;
		this.tableName = tableName;
	}

	@Override
	public String getDatabaseName() 
	{
		return databaseName;
	}

	@Override
	public String getTableName() 
	{
		return tableName;
	}
	
	@Override
	public List<ModelColumnSpecification> getColumnList()
	{
		return new ArrayList<ModelColumnSpecification>( columnMap.values() );
	}
	
	@Override
	public ModelColumnSpecification getColumn( String columnName )
	{
		ModelColumnSpecification col = this.columnMap.get( columnName );
		if( col == null )
		{
			throw new RuntimeException( "No column defined with name = '" + columnName + "' ." );
		}
		return col;
	}
	
	@Override
	public String getColumnName( String columnName )
	{
		return this.getColumn( columnName ).getColumnName();
	}

	@Override
	public int getColumnSize( String columnName )
	{
		return this.getColumn( columnName ).getSize();
	}

	@Override
	public String getColumnLabel( String columnName )
	{
		return this.getColumn( columnName ).getLabel();
	}
	
	@Override
	public ModelRow addRow()
	{
		ModelRow row = new DefaultModelRow(columnMap);
		this.rowList.add(row);
		return row;
	}

	@Override
	public ModelRow addRow( ResultSet rs )
	{
		ModelRow row = this.addRow();
		this.bindValuesFromResultset( row, rs );
		return row;		
	}
	
	@Override
	public ModelRow addRow( Map<?,?> paramMap )
	{
		ModelRow row = this.addRow();
		this.bindValuesFromParameterMap( row, paramMap );
		return row;				
	}

	private void bindValuesFromResultset( ModelRow row, ResultSet rs )
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			for( int i = 1; i <= colCount; i++ )
			{
				String databaseColumnName = metaData.getColumnName(i);
				if( this.databaseColumnMap.containsKey(databaseColumnName) )
				{
					List<String> columnList = this.databaseColumnMap.get(databaseColumnName);
					for( String columnName: columnList )
					{
						row.getColumn(columnName).setValueFromResultset( rs, databaseColumnName );
					}
				}
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private void bindValuesFromParameterMap( ModelRow row, Map<?,?> paramMap )
	{
		for( ModelColumn col : row.getColumnList() )
		{
			if( paramMap.keySet().contains(col.getColumnName()) )
			{
				String paramValue = ((String[])paramMap.get(col.getColumnName()))[0];
				col.setValue(paramValue);
			}
		}
	}
	
	@Override
	public List<ModelRow> getRowList()
	{
		return this.rowList;
	}

	/**
	 * Return the first (or only) row
	 */
	public @Override ModelRow getRow()
	{
		if( rowList.size() > 0)
		{
			return this.rowList.get(0);
		}
		else
		{
			return null;
		}
	}
	
	protected void addColumn( String columnName, JavaDataType javaDataType, int size )
	{
		this.addToColumnMap( columnName, javaDataType, size );
	}

	protected void addColumn( String columnName, JavaDataType javaDataType, int size, String label, String description )
	{
		this.addToColumnMap( columnName, javaDataType, size, label, description );
	}
	
	protected void addColumn( String columnName, String databaseColumnName, DatabaseDataType databaseDataType, int size )
	{
		this.addToColumnMap( columnName, databaseColumnName, databaseDataType, size );	
		this.addToDatabaseMap( columnName, databaseColumnName );
	}

	protected void addColumn( String columnName, String databaseColumnName, DatabaseDataType databaseDataType, int size, String label, String description )
	{
		this.addToColumnMap( columnName, databaseColumnName, databaseDataType, size, label, description );	
		this.addToDatabaseMap( columnName, databaseColumnName );
	}
	
	private void addToColumnMap( String columnName, JavaDataType javaDataType, int size )
	{
		String label = "";
		String description = "";
		this.addToColumnMap( columnName, javaDataType, size, label, description );
	}	

	private void addToColumnMap( String columnName, JavaDataType javaDataType, int size, String label, String description )
	{
		String databaseColumnName = "";
		DatabaseDataType databaseDataType = DatabaseDataType.INT;
		this.addToColumnMap( columnName, javaDataType, databaseColumnName, databaseDataType, size, label, description );
	}	
	
	private void addToColumnMap( String columnName, String databaseColumnName, DatabaseDataType databaseDataType, int size )
	{
		String label = "";
		String description = "";
		this.addToColumnMap( columnName, databaseColumnName, databaseDataType, size, label, description );
	}

	private void addToColumnMap( String columnName, String databaseColumnName, DatabaseDataType databaseDataType, int size, String label, String description )
	{
		JavaDataType javaDataType = JavaDataType.AUTO_FROM_DATABASE_TYPE;
		this.addToColumnMap( columnName, javaDataType, databaseColumnName, databaseDataType, size, label, description );
	}
	
	private void addToColumnMap( String columnName, JavaDataType javaDataType, String databaseColumnName, DatabaseDataType databaseDataType, int size, String label, String description )
	{
		if( columnMap.containsKey(columnName) )
		{
			throw new RuntimeException( "A column has already been defined with name = '" + columnName + "'." );
		}
		ModelColumnSpecification col = new DefaultModelColumnSpecification( columnName, javaDataType, databaseColumnName, databaseDataType, size, label, description );
		this.columnMap.put( columnName, col );
	}

	private void addToDatabaseMap( String columnName, String databaseColumnName )
	{
		if( databaseColumnName.length() > 0 )
		{
			List<String> columnList;
			if( this.databaseColumnMap.containsKey(databaseColumnName) )
			{
				columnList = this.databaseColumnMap.get(databaseColumnName);
			}
			else
			{
				columnList = new ArrayList<String>();
			}
			columnList.add(columnName);
			this.databaseColumnMap.put( databaseColumnName, columnList );
		}
	}
	
	protected void setDatabaseName( String databaseName )
	{
		this.databaseName = databaseName;
	}
	
	protected void setTableName( String tableName )
	{
		this.tableName = tableName;
	}
	
	public String toString()
	{
		StringBuffer b = new StringBuffer("").append(newLine)
		.append("------------------------------------------------------------------------------").append(newLine)
		.append("DatabaseName = " + this.databaseName).append(newLine)
		.append("TableName = " + this.tableName).append(newLine)
		.append("    Column Specification:").append(newLine);
		int colIndex = 0;
		for( ModelColumnSpecification col : this.getColumnList() )
		{
			b.append( "        col-" + colIndex++ + ": " + col.toString() ).append(newLine);
		}
		int rowIndex = 0;
		for ( ModelRow row : this.getRowList() )
		{
			b.append( "    ROW-" + rowIndex++ + ": " ).append(newLine);
			colIndex = 0;
			for( ModelColumn col : row.getColumnList() )
			{
				b.append( "        col-" + colIndex++ + ": " + col.getColumnName() + " = " + col.getValueAsString() ).append(newLine);
			}
		}
		b.append("------------------------------------------------------------------------------");
		return b.toString();
	}
	
	
}
