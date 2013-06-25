package com.totalbanksolutions.framework.bean.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;


public class AutoDataRow implements BeanResultSetMapper, Bean
//extends AbstractDatabaseBean
{
	private Column rows = new Column();

	public AutoDataRow(){
		
	}	
	
	public BeanResultSetMapper newInstance(){
		return new AutoDataRow();
	}

	
	public void bindValuesFromResultset(ResultSet rs)
	{
		try
		{			
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			List<Object> columns = new ArrayList<Object>();
			
			for(int i = 1; i <= colCount; i++)
			{
				String colName = metaData.getColumnName(i);				
				int type = metaData.getColumnType(i);
				
												
				if(type == java.sql.Types.VARCHAR || 
						type == java.sql.Types.CHAR){
					
					columns.add(rs.getString(colName));
				}else{
					if(type == java.sql.Types.DECIMAL || 
						type == java.sql.Types.DOUBLE ||
						type == java.sql.Types.FLOAT || 
						type == java.sql.Types.NUMERIC){
					
						columns.add(rs.getDouble(colName));
					}else{
						if(type == java.sql.Types.TINYINT|| 
								type == java.sql.Types.SMALLINT){
						
							columns.add(rs.getInt(colName));
						}else{
							if(type == java.sql.Types.DATE|| 
									type == java.sql.Types.TIME ||
									type == java.sql.Types.TIMESTAMP){								 	
									
								columns.add(rs.getDate(colName));
							}
						}
					}
				}					
			}
			this.getRows().setList(columns);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public String[] getValues()
	{
		return null;
	}

	public Column getRows() {
		return rows;
	}

	public void setRows(Column rows) {
		this.rows = rows;
	}

	@Override
	public BeanDatabaseField getField(Enum fieldName)
	{
		return getField(fieldName.name());
	}
	
	@Override
	public BeanDatabaseField getField(String fieldName)
	{
		return null;
	}

	@Override
	public boolean containsField(String fieldName)
	{
		return false;
	}
	
	public String[] getBindList() {
		return null;
	}
	public void setBindList(String[] bindList) {
		
	}

}
