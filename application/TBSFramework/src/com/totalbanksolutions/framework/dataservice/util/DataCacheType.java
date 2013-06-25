package com.totalbanksolutions.framework.dataservice.util;

import com.totalbanksolutions.framework.cache.CacheType;

/**
 * =================================================================================================
 * Created:   VC
 * Modified:  10 Aug 2011 VC #579: Create a new checklist for "Universal Reports" that span all program databases
 *            07 Nov 2011 NAM #1051 - Pulling Report Type from DB to make the email senders generic
 *            25 Sep 2012 NAM #1869: Switch to Primary and Secondary Processing Directory types
 *            
 * Enumerations class to manage elements stored in the Cache Manager.
 * 
 * =================================================================================================
 */
public enum DataCacheType implements CacheType
{
	 APP_CONFIGURATION
	,APP_NAVIGATION_GROUP_LIST
	,APP_ROLE_LIST
	,APP_ROLE_SET
	,APP_USER_ROLE_LIST
	,APP_USER_NAVIGATION_LIST
	,APP_USER_TYPE_LIST
	,APP_USER_TYPE_SET
	,CHALLENGE_QUESTION_LIST
	,CONTACTINSTITUTION_TYPE_LIST
	,CONTACTINSTITUTION_TYPE_SET
	,CUSTOMER_ACCOUNT_TYPE_LIST
	,CUSTOMER_ACCOUNT_TYPE_MAP
	,CUSTOMER_ACCOUNT_PRODUCT_MAP
	,DAILY_PROCESS_FILE_ACTION_TYPE_LIST
	,DAILY_PROCESS_OPERATION_TYPE_LIST
	,DAILY_PROCESS_TYPE_LIST
	,DAILY_PROCESS_STATUS_LIST
	,DAILY_PROCESS_STEP_LIST
	,DEPOSIT_INSTITUTION_LIST
	,DEPOSIT_INSTITUTION_MAILINGADDRESS_MAP
	,DEPOSIT_INSTITUTION_SET
	,DEVEOPER_EXAMPLES_CONFIGURATION
	,ENVELOPE_BY_DEPOSIT_INSTITUTION_LIST
	,ENVELOPE_BY_SOURCE_INSTITUTION_LIST
	,ENVELOPE_LIST
	,IBD_MAPPED_PRODUCT_LIST
	,IBD_PRODUCT_MAP_BY_SOURCEPRODUCTCODE
	,MAILING_COUNTRY_LIST
	,MAILING_STATE_LIST
	,PRODUCT_LIST
	,PRODUCT_MAPPING_LIST
	,PRODUCT_MAP_BY_PRODUCTCODE
	,PRODUCT_MAP_BY_PRODUCTID
	,PRODUCT_MAP_BY_SOURCEPRODUCTCODE
	,PROGRAM_ACCOUNT_TYPE_MAP
	,PROGRAM_DEPOSIT_ACCOUNT_MAP
	,PROGRAM_ARCHIVE_DIRECTORY_SECONDARY_LIST
	,PROGRAM_WORKING_DIRECTORY_SECONDARY_LIST
	,PROGRAM_ARCHIVE_DIRECTORY_PRIMARY
	,PROGRAM_WORKING_DIRECTORY_PRIMARY
	,PROGRAM_ARCHIVE_DIRECTORY_SECONDARY01
	,PROGRAM_ARCHIVE_DIRECTORY_SECONDARY02
	,PROGRAM_LIST
	,PROGRAM_LIST_FOR_CHECKLIST
	,PROGRAM_SET
	,REPORT_TYPE_LIST
	,RBC_IBD_PRODUCT_MAP_BY_SOURCEPRODUCTCODE_LOWER
	,RBC_IBD_PRODUCT_MAP_BY_SOURCEPRODUCTCODE_UPPER
	,RG_REPORT_LIST
	,SOURCE_INSTITUTION_LIST
	,SOURCE_INSTITUTION_PROGRAM_LIST
	,SOURCE_INSTITUTION_SET
	,SOURCEPRODUCT_MAP_BY_PRODUCTCODE
	;

	public String getCacheName()
	{
		return this.name();
	}
}