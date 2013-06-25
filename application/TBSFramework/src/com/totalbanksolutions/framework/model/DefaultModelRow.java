package com.totalbanksolutions.framework.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultModelRow implements ModelRow
{
    protected final Log log = LogFactory.getLog(DefaultModelRow.class);

	private Map<String,ModelColumnSpecification>	columnSpecMap = new LinkedHashMap<>();
	private Map<String,ModelColumn>					columnMap = new LinkedHashMap<>();
		
	protected DefaultModelRow( Map<String,ModelColumnSpecification> columnSpecMap )
	{
		super();
		this.columnSpecMap = columnSpecMap;
		init();
	}

	private void init()
	{
		for( ModelColumnSpecification f : this.columnSpecMap.values() )
		{
			this.addField( f.getColumnName() );
		}
	}

	private void addField( String name )
	{
		if( columnMap.containsKey(name) )
		{
			throw new RuntimeException( "A field has already been defined with name = '" + name + "'." );
		}
		ModelColumn f = new DefaultModelColumn( columnSpecMap.get(name) );
		this.columnMap.put( name, f );
	}
	
//	public void addField( String name, JavaDataType javaDataType, int size )
//	{
//		ModelField f = new DefaultModelField( name, javaDataType, size );
//		addField( f );
//	}

//	protected void addField( ModelField f )
//	{
//		if( fields.containsKey(f.getName()) )
//		{
//			throw new RuntimeException( "Invalid API Usage. A field has already been defined with name = '" + f.getName() + "'." );
//		}
//		fields.put( f.getName(), f );
//	}

//	@Override
//	public void bindValuesFromParameterMap( Map<?,?> paramMap )
//	{
//		for( ModelColumn f : getColumnList() )
//		{
//			if( paramMap.keySet().contains(f.getColumnName()) )
//			{
//				String paramValue = ((String[])paramMap.get(f.getColumnName()))[0];
//				f.setValue(paramValue);
//			}
//		}
//	}
	
	@Override
	public boolean containsColumn( String fieldName )
	{
		return columnMap.containsKey( fieldName );
	}
	
	@Override
	public Map<String,String> getAjaxResultsRow()
	{
		Map<String,String> row = new LinkedHashMap<>();
		for( ModelColumn f : getColumnList() )
		{
			row.put( f.getColumnName(), f.getValueAsString() );
		}
		return row;
	}

	@Override
	public ModelColumn getColumn( String fieldName )
	{
		ModelColumn field = columnMap.get( fieldName );
		if(field == null)
		{
			throw new RuntimeException("No column defined with name = '" + fieldName + "'.");
		}
		return field;		
	}
	
	@Override
	public List<ModelColumn> getColumnList()
	{
		return new ArrayList<ModelColumn>( columnMap.values() );
	}

	@Override
	public Map<String,ModelColumn> getColumnMap()
	{
		return this.columnMap;
	}
	
	@Override
	public Object getValue( String fieldName )
	{
		ModelColumn f = this.getColumn( fieldName );
		return f.getValue();
	}

	@Override
	public void setValue( String fieldName, Object value )
	{
		ModelColumn f = this.getColumn( fieldName );
		f.setValue( value );
	}
	
	public String toString()
	{
		StringBuffer b = new StringBuffer("");
		int colIndex = 0;
		for( ModelColumn col : this.getColumnList() )
		{
			b.append( "col-" + colIndex++ + ": " + col.toString() );
		}
		return b.toString();
	}
	
//	public String toString()
//	{
//		String tmp = "";
//		for( Map.Entry<String, ModelColumn> fieldEntry : fields.entrySet() )
//		{
//			ModelColumn field = fieldEntry.getValue();
//			tmp += field.getColumnName() + "=" + field.getValue() + " ; ";
//		}
//		return tmp;
//	}
	
}