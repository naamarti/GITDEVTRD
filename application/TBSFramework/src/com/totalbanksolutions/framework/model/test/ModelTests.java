package com.totalbanksolutions.framework.model.test;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.dataservice.util.DataServiceManager;
import com.totalbanksolutions.framework.model.ModelColumn;
import com.totalbanksolutions.framework.model.ModelColumnSpecification;
import com.totalbanksolutions.framework.model.ModelRow;
import com.totalbanksolutions.framework.model.ModelTable;
import com.totalbanksolutions.framework.model.database.table.DeveloperExamplesConfigurationTable;
import com.totalbanksolutions.framework.util.AppUtility;

public class ModelTests 
{
	private DataService ds = null;
	
	public static void main(String[] args) 
	{
		ModelTests m = new ModelTests();
		m.initialize();
		m.runTest1();
		m.runTest2();
	}
	
	public void runTest1()
	{
		System.out.println( "------------------------------------------------------------------------------------" );
		System.out.println( "" );
		System.out.println( "Run Test 1..." );
		System.out.println( "" );
		System.out.println( "------------------------------------------------------------------------------------" );

		System.out.println( "" );
		System.out.println( "------------------------------------------------------------------------------------" );
		System.out.println( " TABLE: " );		
		System.out.println( "------------------------------------------------------------------------------------" );

		System.out.println( "DeveloperExamplesConfiguration2Table t = new DeveloperExamplesConfiguration2Table();" );
		DeveloperExamplesConfigurationTable t = new DeveloperExamplesConfigurationTable();
		//t.
		
		System.out.println( "" );
		System.out.println( "t.getDatabaseName()                                                   = " + t.getDatabaseName() );
		System.out.println( "t.getTableName()                                                      = " + t.getTableName() );

		System.out.println( "" );
		System.out.println( "t.getColumnName( \"approvalCode\" )                                     = " + t.getColumnName( "approvalCode" ) );
		System.out.println( "t.getColumnName( \"companyName\" )                                      = " + t.getColumnName( "companyName" ) );

		System.out.println( "" );
		System.out.println( "t.getColumnSize( t.APPROVAL_CODE )                                    = " + t.getColumnSize( t.APPROVAL_CODE ) );
		System.out.println( "t.getColumnSize( t.COMPANY_NAME)                                      = " + t.getColumnSize( t.COMPANY_NAME) );

		System.out.println( "" );
		System.out.println( "t.getColumnLabel( t.APPROVAL_CODE )                                   = " + t.getColumnLabel( t.APPROVAL_CODE ) );
		System.out.println( "t.getColumnLabel( t.COMPANY_NAME )                                    = " + t.getColumnLabel( t.COMPANY_NAME ) );


		System.out.println( "" );
		System.out.println( "------------------------------------------------------------------------------------" );
		System.out.println( " TABLE COLUMN: " );		
		System.out.println( "------------------------------------------------------------------------------------" );
		//t.getColumn( t.APPROVAL_CODE ).

		System.out.println( "t.getColumn( \"approvalCode\" ).getColumnName()                         = " + t.getColumn( "approvalCode" ).getColumnName() );
		System.out.println( "t.getColumn( \"companyName\" ).getColumnName()                          = " + t.getColumn( "companyName" ).getColumnName() );

		System.out.println( "" );
		System.out.println( "t.getColumn( t.APPROVAL_CODE ).getLabel()                             = " + t.getColumn( t.APPROVAL_CODE ).getLabel() );
		System.out.println( "t.getColumn( t.COMPANY_NAME ).getLabel()                              = " + t.getColumn( t.COMPANY_NAME ).getLabel() );
		
		System.out.println( "" );
		String result = "";
		try {
			result = t.getColumn( "FAKE_COLUMN" ).getColumnName();
		}
		catch (Exception e)
		{
			result = e.getMessage();
		}
		System.out.println( "t.getColumn( \"FAKE_COLUMN\" ).getColumnName()                          = " + result );
				
		getColumnSpecs( t );
		
		System.out.println( "" );
		System.out.println( "------------------------------------------------------------------------------------" );
		System.out.println( " TABLE ROW: ( manually added) " );
		System.out.println( "------------------------------------------------------------------------------------" );

		System.out.println( "ModelRow row = t.addRow();" );
		System.out.println( "row.getColumn( t.APPROVAL_CODE ).setValue( \"1234\" );" );
		System.out.println( "row.getColumn( t.COMPANY_NAME ).setValue( \"TBS\" );" );
		ModelRow row = t.addRow();
		//row.
		row.getColumn( t.APPROVAL_CODE ).setValue( "1234" );
		row.getColumn( t.COMPANY_NAME ).setValue( "TBS" );

		System.out.println( "" );
		System.out.println( "row = t.addRow();" );
		System.out.println( "row.setValue( t.APPROVAL_CODE, \"abcd\" );" );
		row = t.addRow();
		row.setValue( t.APPROVAL_CODE, "abcd" );
		
		getRows( t );
	}

	public void runTest2()
	{
		System.out.println( "" );
		System.out.println( "" );
		System.out.println( "------------------------------------------------------------------------------------" );
		System.out.println( "" );
		System.out.println( "Run Test 2..." );
		System.out.println( "" );
		System.out.println( "------------------------------------------------------------------------------------" );

		System.out.println( "" );
		System.out.println( "DeveloperExamplesConfiguration2Table t = new DeveloperExamplesConfiguration2Table();" );
		System.out.println( "t = ds.developerExamples.getDeveloperExamplesConfiguration3();" );
		DeveloperExamplesConfigurationTable t = new DeveloperExamplesConfigurationTable();
		t = ds.developerExamples.getDeveloperExamplesConfiguration();		
		getRows( t );
	}
	
	public void getColumnSpecs( ModelTable table )
	{
		System.out.println( "" );
		System.out.println( "------------------------------------------------------------------------------------" );
		System.out.println( " TABLE COLUMN LIST: " );		
		System.out.println( "------------------------------------------------------------------------------------" );
		int colIndex = 0;
		for( ModelColumnSpecification col : table.getColumnList() )
		{
			//col.
			System.out.println( "col-" + colIndex++ + ": " + col.toString() );
		}
	}

	public void getRows( ModelTable table )
	{
		System.out.println( "" );
		System.out.println( "------------------------------------------------------------------------------------" );
		System.out.println( " TABLE ROW LIST: " );		
		System.out.println( "------------------------------------------------------------------------------------" );		
		int rowIndex = 0;
		for( ModelRow row : table.getRowList() )
		{
			System.out.println( "row-" + rowIndex++ + ": " );
			//row.

			int colIndex = 0;
			for( ModelColumn f : row.getColumnList() )
			{
				//f.
				System.out.println( "    col-" + colIndex++ + ": " + f.toString() );
			}
		
		}
	}

	private void initialize()
	{
		this.ds = new DataServiceManager().getDataService();
	}
	
}
