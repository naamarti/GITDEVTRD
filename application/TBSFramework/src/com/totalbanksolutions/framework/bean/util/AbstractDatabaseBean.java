package com.totalbanksolutions.framework.bean.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dao.util.SQLParameters;
import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.JavaDataType;

public abstract class AbstractDatabaseBean<E extends Enum<E>> implements Bean<E>, BeanResultSetMapper, Comparable<AbstractDatabaseBean<E>>
{
    protected final Log log = LogFactory.getLog(AbstractDatabaseBean.class);

    private String 								containerClassName;
    private String								databaseName;
	private String 								tableName;
	private Map<String,BeanDatabaseField>		fields = new LinkedHashMap<String,BeanDatabaseField>();
	private Map<String,List<BeanDatabaseField>>	databaseFields = new HashMap<String,List<BeanDatabaseField>>();
	private BeanDatabaseField					identityField = null;
	private String[]							bindList = new String[] {};

	protected AbstractDatabaseBean() { }
	
	protected AbstractDatabaseBean(String databaseName, String tableName)
	{
		super();
		this.containerClassName = ClassUtils.getShortClassName(this.getClass().getName());
		this.databaseName = databaseName;
		this.tableName = tableName;
	}
	
	public void addField(Enum<E> enumType, String databaseFieldName, DatabaseDataType databaseDataType, int size, boolean isIdentityAutoSequence)
	{
		addField(enumType.name(), databaseFieldName, databaseDataType, size, isIdentityAutoSequence);
	}

	public void addField(String fieldName, String databaseFieldName, DatabaseDataType databaseDataType, int size, boolean isIdentityAutoSequence)
	{
		BeanDatabaseField dataField = new BeanDatabaseField(this.containerClassName, fieldName, databaseFieldName, this.tableName, databaseDataType, JavaDataType.AUTO_FROM_DATABASE_TYPE, size, isIdentityAutoSequence);
		addField(dataField);
	}

	protected void addField(BeanDatabaseField field)
	{
		if(fields.containsKey(field.getName()))
		{
			throw new RuntimeException("Invalid API Usage. A field has already been defined with name = '" + field.getName() + "'.");
		}
		BeanDatabaseField newField = new BeanDatabaseField(field);
		fields.put(field.getName(), newField);

		if(newField.getDatabaseFieldName().length() > 0)
		{
			List<BeanDatabaseField> dbFieldsList;
			if(databaseFields.containsKey(newField.getDatabaseFieldName()))
			{
				dbFieldsList = databaseFields.get(newField.getDatabaseFieldName());
			}
			else
			{
				dbFieldsList = new ArrayList<BeanDatabaseField>();
			}
			dbFieldsList.add(newField);
			databaseFields.put(newField.getDatabaseFieldName(), dbFieldsList);
		}
		
		
		if(newField.isIdentityAutoSequence())
		{
			this.identityField = newField;
		}
	}

	@Override
	public BeanDatabaseField getField(Enum<E> fieldName)
	{
		return getField(fieldName.name());
	}

	@Override
	public BeanDatabaseField getField(String fieldName)
	{
		BeanDatabaseField field = fields.get(fieldName);
		if(field == null)
		{
			throw new RuntimeException("Invalid API Usage. No field defined with name = '" + fieldName + "'.");
		}
		return field;		
	}
	
	@Override
	public boolean containsField(String fieldName)
	{
		return fields.containsKey(fieldName);
	}
	
	public String toString()
	{
		String tmp = "";
		for(Map.Entry<String, BeanDatabaseField> fieldEntry : fields.entrySet())
		{
			BeanDatabaseField field = fieldEntry.getValue();
			tmp += field.getName() + "=" + field.getValue() + " ; ";
		}
		return tmp;
	}
	
	protected String getSQLInsertStatement()
	{
		return getSQLInsertStatement(this.databaseName);
	}
	
	public String getSQLInsertStatement(String databaseName)
	{
		String sql = "INSERT " + databaseName + ".." + this.tableName + " (";
		String fieldList = "";
		String valueList = "";
		for(Map.Entry<String, BeanDatabaseField> fieldEntry : fields.entrySet())
		{
			BeanDatabaseField field = fieldEntry.getValue();
			if(!field.isIdentityAutoSequence())
			{
				fieldList += field.getDatabaseFieldName() + ", ";
				valueList += ":" + field.getDatabaseFieldName() + ", ";
			}
		}
		fieldList = StringUtils.removeEnd(fieldList, ", ");
		valueList = StringUtils.removeEnd(valueList, ", ");
		sql += fieldList + ") ";
		sql += "VALUES (";
		sql += valueList + ") ";
		return sql;
	}

//	public MapSqlParameterSource getSQLParameters()
//	{
//		MapSqlParameterSource params = new MapSqlParameterSource();
//		for(Map.Entry<String, BeanDatabaseField> fieldEntry : fields.entrySet())
//		{
//			BeanDatabaseField field = fieldEntry.getValue();
//			if(!field.isIdentityAutoSequence())
//			{
//				params.addValue(field.getDatabaseFieldName(), field.getValue());
//			}
//		}
//		return params;
//	}
	
	public String getSQLInsertStatementNew()
	{
		return getSQLInsertStatementNew(this.databaseName);
	}

	public String getSQLInsertStatementNew(String databaseName)
	{
		String sql = "INSERT " + databaseName + ".." + this.tableName + " (";
		String fieldList = "";
		String valueList = "";
		for(Map.Entry<String, BeanDatabaseField> fieldEntry : fields.entrySet())
		{
			BeanDatabaseField field = fieldEntry.getValue();
			if(field.isIdentityAutoSequence())
			{
				// SKIP
			}
			else if(field.getDatabaseFieldName().equalsIgnoreCase("LastModifiedDateTime")
					|| field.getDatabaseFieldName().equalsIgnoreCase("CreatedDateTime"))
			{
				fieldList += field.getDatabaseFieldName() + ", ";
				valueList += "getdate() " + ", ";
			}
			else
			{
				fieldList += field.getDatabaseFieldName() + ", ";
				valueList += "? " + ", ";
			}
		}
		fieldList = StringUtils.removeEnd(fieldList, ", ");
		valueList = StringUtils.removeEnd(valueList, ", ");
		sql += fieldList + ") ";
		sql += "VALUES (";
		sql += valueList + ") ";
		return sql;
	}

	public SQLParameters getSQLInsertParameters()
	{
		SQLParameters params = new SQLParameters();
		for(Map.Entry<String, BeanDatabaseField> fieldEntry : fields.entrySet())
		{
			BeanDatabaseField field = fieldEntry.getValue();
			if(field.isIdentityAutoSequence())
			{
				// SKIP
			}
			else if(field.getDatabaseFieldName().equalsIgnoreCase("LastModifiedDateTime")
					|| field.getDatabaseFieldName().equalsIgnoreCase("CreatedDateTime"))
			{
				// SKIP
			}
			else
			{
				params.addValue(field.getValue());
			}
		}
		return params;
	}

	public String getSQLUpdateStatement()
	{
		return getSQLUpdateStatement(this.databaseName);
	}

	public String getSQLUpdateStatement(String databaseName)
	{
		String sql = "UPDATE " + databaseName + ".." + this.tableName + " SET ";
		String sqlWhere = "WHERE " + this.identityField.getDatabaseFieldName() + " = ? ";
		for(Map.Entry<String, BeanDatabaseField> fieldEntry : fields.entrySet())
		{
			BeanDatabaseField field = fieldEntry.getValue();
			if(field.isIdentityAutoSequence())
			{
				//SKIP
			}
			else if(field.getDatabaseFieldName().equalsIgnoreCase("CreatedDateTime"))
			{
				//SKIP
			}
			else if(field.getDatabaseFieldName().equalsIgnoreCase("LastModifiedDateTime"))
			{
				sql += field.getDatabaseFieldName() + " = getdate(), ";
			}
			else
			{
				sql += field.getDatabaseFieldName() + " = ?, ";				
			}
		}
		sql = StringUtils.removeEnd(sql, ", ");
		sql += " " + sqlWhere;
		return sql;
	}

	public SQLParameters getSQLUpdateParameters()
	{
		SQLParameters params = new SQLParameters();
		for(Map.Entry<String, BeanDatabaseField> fieldEntry : fields.entrySet())
		{
			BeanDatabaseField field = fieldEntry.getValue();
			if(field.isIdentityAutoSequence())
			{
				// SKIP
			}
			else if(field.getDatabaseFieldName().equalsIgnoreCase("LastModifiedDateTime")
					|| field.getDatabaseFieldName().equalsIgnoreCase("CreatedDateTime"))
			{
				// SKIP
			}
			else
			{
				params.addValue(field.getValue());
			}
		}
		params.addValue(this.identityField.getValue());
		return params;
	}
	
//	protected static RowMapper getRowMapper(final String className)
//	{
//    	RowMapper rowMapper = new RowMapper() 
//    	{
//		    @SuppressWarnings("unchecked")
//			public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
//		    {
//		    	Object obj = null;
//		    	try
//		    	{
//		    		obj = Class.forName(className).newInstance();
//		    	}
//		    	catch(Exception e)
//		    	{
//		    		throw new RuntimeException("Invalid Class.forName() instance, name invalid = '" + className + "'");
//		    	}
//		    	//AppUserType item = new AppUserType();
//		    	((AbstractDatabaseBean)obj).bindValuesFromResultset(rs);
//				return obj;
//		    }
//    	};
//    	return rowMapper;
//	}

	public BeanResultSetMapper newInstance()
	{
		BeanResultSetMapper mapper = null;
		String className = this.getClass().getName();
    	Object obj = null;
    	try
    	{
    		obj = Class.forName(className).newInstance();
    		mapper = (BeanResultSetMapper)obj;
    		mapper.setBindList(this.getBindList());
    	}
    	catch(Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    	return mapper; 
	}
	
	public void bindValuesFromResultset(ResultSet rs)
	{
		try
		{
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			for(int i = 1; i <= colCount; i++)
			{
				String colName = metaData.getColumnName(i);
				if(databaseFields.containsKey(colName))
				{
					List<BeanDatabaseField> dbFieldsList = databaseFields.get(colName);
					
					for( BeanDatabaseField field: dbFieldsList )
					{
						field.setValueFromResultset(rs);
					}
				}
			}
			for(String s: this.bindList)
			{
				BeanResultSetMapper bindObject = getBindObject(s);
				if(bindObject == null)
				{
					throw new RuntimeException("Class '" + this.getClass().getName() + "' must override method 'getBindObject()' and return object for value '" + s + "'");
				}
				bindObject.bindValuesFromResultset(rs);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public BeanResultSetMapper getBindObject(String s)
	{
		return null;
	}

	public void bindValuesFromParameterMap(Map<?,?> paramMap)
	{
		String prefix = this.containerClassName.toUpperCase() + "_";
		for(Map.Entry<?,?> entry : paramMap.entrySet())
		{
			String paramName = (String)entry.getKey();
			if(paramName.startsWith(prefix))
			{
				String fieldName = paramName.substring(prefix.length());
				if(fields.containsKey(fieldName))
				{
					String paramValue = ((String[])entry.getValue())[0];
					BeanDatabaseField field = fields.get(fieldName);
					field.setValue(paramValue);
				}
			}
		}
	}

	public void copyAllValues(AbstractDatabaseBean<E> dataRow)
	{
		for(Map.Entry<String, BeanDatabaseField> fieldEntry : dataRow.getFields().entrySet())
		{
			BeanDatabaseField copyFromField = fieldEntry.getValue();
			if(fields.containsKey(copyFromField.getName()))
			{
				BeanDatabaseField copyToField = fields.get(copyFromField.getName());
				copyToField.setValue(copyFromField.getValue());
			}
		}
	}

	public String[] getValues()
	{
		String[] dataArray = new String[fields.size()];
		int i = 0;
		for(Map.Entry<String, BeanDatabaseField> fieldEntry : this.fields.entrySet())
		{
			String value = fieldEntry.getValue().getValueAsString();
			dataArray[i] = value;
			i++;
		}
		return dataArray;
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if((obj == null) || (obj.getClass() != this.getClass())) return false;
		// object must be same type at this point
		AbstractDatabaseBean<E> item = (AbstractDatabaseBean<E>)obj;
		//log.debug("EQUALS: this(" + this.getIdentityField().getLongValue() + "); " + item.getIdentityField().getLongValue());
		return this.getSQLIdentityField().getLongValue() == item.getSQLIdentityField().getLongValue(); 
	}

	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + this.getSQLIdentityField().getLongValue().intValue();
		return hash;
	}

	public int compareTo(AbstractDatabaseBean<E> obj)
	{    
		return this.getSQLIdentityField().getLongValue().compareTo(obj.getSQLIdentityField().getLongValue());  
	}
	
	public List<BeanDatabaseField> getFieldList()
	{
		return new ArrayList<BeanDatabaseField>(fields.values());
	}

	// ----------------------------------------------------------
	// Getters & Setters (auto-generated)
	// ----------------------------------------------------------	
	
	public String getSQLTableName() {
		return tableName;
	}
	public void setSQLTableName(String name) {
		this.tableName = name;
	}
	public Map<String, BeanDatabaseField> getFields() {
		return fields;
	}
	public String getSQLDatabaseName() {
		return databaseName;
	}
	public void setSQLDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public BeanDatabaseField getSQLIdentityField() {
		return identityField;
	}
	public String[] getBindList() {
		return this.bindList;
	}
	public void setBindList(String[] bindList) {
		this.bindList = bindList;
	}

}