package com.totalbanksolutions.framework.model.database.table;

import com.totalbanksolutions.framework.enums.DatabaseDataType;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.DefaultModelTable;

public class DeveloperExamplesLocationTable extends DefaultModelTable
{
	public final String ADDRESS						= "address";
	public final String LOCATION_ID					= "locationId";
	public final String LOCATION_NAME				= "locationName";

	public DeveloperExamplesLocationTable()
	{
		this.setDatabaseName( Databases.COMMON );
		this.setTableName( "DeveloperExamples_Locations" );
		initializeColumns();
	}
	
	private void initializeColumns()
	{
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		// 				 ColumnName					Database_ColumnName			Database_DataType                   Size    Label               		Description
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		this.addColumn( this.LOCATION_ID,			"Location_PK",				DatabaseDataType.DECIMAL_LONGINT,	0,		"Location ID",				"Location ID" );
		this.addColumn( this.LOCATION_NAME,			"LocationName",				DatabaseDataType.CHAR,				100,	"Location Name",			"The Location Name for this office." );
		this.addColumn( this.ADDRESS,				"Address",					DatabaseDataType.CHAR,				100,	"Address",					"Office Address." ); 
	}

}

