package com.totalbanksolutions.framework.dao.jdbc;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dao.DeveloperExamplesDAO;
import com.totalbanksolutions.framework.dao.util.DatabaseHelper;
import com.totalbanksolutions.framework.dao.util.SQLParameters;
import com.totalbanksolutions.framework.enums.Databases;
import com.totalbanksolutions.framework.model.AutoMapModelTable;
import com.totalbanksolutions.framework.model.ModelTable;
import com.totalbanksolutions.framework.model.database.table.DeveloperExamplesConfigurationTable;
import com.totalbanksolutions.framework.model.database.table.DeveloperExamplesLocationTable;

/**
 * =================================================================================================
 * Created:   21 Apr 2010 VC
 * Modified:  
 *
 * =================================================================================================
 */
public class DeveloperExamplesJDBC implements DeveloperExamplesDAO
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    private DataSource dataSource;
    private DatabaseHelper databaseHelper;
    
	public DeveloperExamplesJDBC(DataSource ds)
	{
		setDataSource(ds);
	}

	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
        this.databaseHelper = new DatabaseHelper(dataSource);
    }
    
    public void close()
    {
    	this.databaseHelper.close();
    	this.databaseHelper = null;
    	this.dataSource = null;
    }
    
	//============================================================================
	//
    // DeveloperExamples_Configuration
    //
	//============================================================================
    public DeveloperExamplesConfigurationTable getDeveloperExamplesConfiguration() 
	{
		DeveloperExamplesConfigurationTable t = new DeveloperExamplesConfigurationTable();
		String sql = "SELECT * FROM " + Databases.COMMON + "..DeveloperExamples_Configuration ";
		SQLParameters params = new SQLParameters();
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
	}

    public DeveloperExamplesConfigurationTable getDeveloperExamplesConfigurationSQLError() 
	{
		DeveloperExamplesConfigurationTable t = new DeveloperExamplesConfigurationTable();
		String sql = "SELECT * FROM " + Databases.COMMON + "..DeveloperExamples_Configurationxx ";
		SQLParameters params = new SQLParameters();
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
	}
    
    public void updateDeveloperExamplesConfiguration(DeveloperExamplesConfigurationTable t) 
	{
		String sql = "UPDATE " + Databases.COMMON + "..DeveloperExamples_Configuration SET "
        + "CompanyName = ?, ApprovalCode = ?, MaximumTransactionAmount = ?, SessionTimeoutMinutes = ?, IsDisableLogins = ?, "
        + "SourceInstitution_FK = ?, DebugLevel = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue( t.getRow().getValue( t.COMPANY_NAME ) );
		params.addValue( t.getRow().getValue( t.APPROVAL_CODE ) );
		params.addValue( t.getRow().getValue( t.MAX_TRANS_AMOUNT ) );
		params.addValue( t.getRow().getValue( t.SESSION_TIMEOUT ) );
		params.addValue( t.getRow().getValue( t.IS_DISABLE_LOGINS ) );
		params.addValue( t.getRow().getValue( t.SETTLE_INST_ID ) );
		params.addValue( t.getRow().getValue( t.DEBUG_LEVEL ) );
		this.databaseHelper.executeUpdate(sql, params);
	}

	//============================================================================
	//
    // DeveloperExamples_Files
    //
	//============================================================================
    public ModelTable getDeveloperExamplesFiles() 
	{
		AutoMapModelTable t = new AutoMapModelTable();
		String sql = "SELECT * FROM " + Databases.COMMON + "..DeveloperExamples_Files ";
		SQLParameters params = new SQLParameters();
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
	}

	//============================================================================
	//
    // DeveloperExamples_Locations
    //
	//============================================================================
    public DeveloperExamplesLocationTable getDeveloperExamplesLocations()
    {
		DeveloperExamplesLocationTable t = new DeveloperExamplesLocationTable();
		String sql = "EXEC " + Databases.COMMON + "..p_devex_GetLocations ";
		SQLParameters params = new SQLParameters();
		this.databaseHelper.queryForModelTable(sql, params, t);
		return t;
    }
    
	//============================================================================
	//
    // DeveloperExamples_Servers
    //
	//============================================================================
    public ModelTable getDeveloperExamplesServers() 
	{
		AutoMapModelTable t = new AutoMapModelTable();
		String sql = "EXEC " + Databases.COMMON + "..p_devex_GetServers @Location = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue( "NY" );
		this.databaseHelper.executeStoredProcedureForModelTable(sql, params, t);
		return t;
	}
    
}
