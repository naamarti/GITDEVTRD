package com.totalbanksolutions.framework.dao;

import com.totalbanksolutions.framework.dao.util.DAO;
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
public interface DeveloperExamplesDAO extends DAO
{

    public void close();
	
	//============================================================================
	//
    // DeveloperExamples_Configuration
    //
	//============================================================================
    public DeveloperExamplesConfigurationTable getDeveloperExamplesConfiguration();
    public DeveloperExamplesConfigurationTable getDeveloperExamplesConfigurationSQLError();
    public void updateDeveloperExamplesConfiguration(DeveloperExamplesConfigurationTable t);
    
	//============================================================================
	//
    // DeveloperExamples_Files
    //
	//============================================================================
    public ModelTable getDeveloperExamplesFiles();

	//============================================================================
	//
    // DeveloperExamples_Locations
    //
	//============================================================================
    public DeveloperExamplesLocationTable getDeveloperExamplesLocations();
    
	//============================================================================
	//
    // DeveloperExamples_Servers
    //
	//============================================================================
    public ModelTable getDeveloperExamplesServers();
    
    
}
