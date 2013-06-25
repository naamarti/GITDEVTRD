package com.totalbanksolutions.framework.model;

import java.util.List;
import java.util.Map;

public interface ModelRow
{	
//	void bindValuesFromParameterMap( Map<?,?> paramMap );

	Map<String,String> getAjaxResultsRow();
	
	boolean containsColumn( String columnName );
	ModelColumn getColumn( String columnName );
	List<ModelColumn> getColumnList();
	Map<String,ModelColumn> getColumnMap();
	
	Object getValue( String columnName );
	void setValue( String columnName, Object value );
}
