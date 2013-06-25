package com.totalbanksolutions.framework.test;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.Program;
import com.totalbanksolutions.framework.bean.database.table.SourceInstitution;
import com.totalbanksolutions.framework.util.ConvertUtility;

/**
 * 
 * This class will run all the steps for the specified Program and SourceInstitution.
 * It starts with the current date & step that is next to run, as shown in the DMS UI.
 * It stops after processing thru the EndDate specified. 
 * If EndAtStep is specified, it will stop after processing that step on the EndDate.
 *  
 */
public class TestHCDenisonReloader extends TestReloader
{
	protected final Log log = LogFactory.getLog(getClass());
	
	public static void main(String[] args) 
	{
		String programCode = Program.Values.HCDENISON.getId();
		String sourceInstitutionCode = SourceInstitution.Values.HCDENISON.getId();
		String userName = "tbs_Val.Catrini";
		Date endDate = ConvertUtility.convertToDate(2009, 9, 18);

		TestHCDenisonReloader test = new TestHCDenisonReloader();
		test.setProgramCode(programCode);
		test.setSourceInstitutionCode(sourceInstitutionCode);
		test.setUserName(userName);
		test.setEndDate(endDate);
		//test.setEndAtStep(11);
		test.startReload();
	}

}