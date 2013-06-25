package com.totalbanksolutions.framework.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author vcatrini
 *
 */
public interface ModelTable
{	
	String getDatabaseName();
	String getTableName();

	List<ModelColumnSpecification> getColumnList();
	ModelColumnSpecification getColumn( String columnName );
	String getColumnName( String columnName );
	String getColumnLabel( String columnName );
	int getColumnSize( String columnName );
	
	/**
	 * Creates a new {@link ModelRow} and appends it to the end of the rows list.
	 * <p>
	 * @return {@link ModelRow}
	 */
	ModelRow addRow();
	
	/**
	 * Creates a new {@link ModelRow} and appends it to the end of the rows list.
	 * <p>
	 * The columns values will be bound to the passed in <code>rs</code>.
	 * <p>
	 * @param rs Database ResultSet
	 * @return {@link ModelRow}
	 */
	ModelRow addRow( ResultSet rs );

	ModelRow addRow( Map<?,?> paramMap );
	
	List<ModelRow> getRowList();
	
	ModelRow getRow();
	
}
