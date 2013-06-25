package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;

public class ContactDepositInstitutionRelationship 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "Contact_DepositInstitution_Relationship";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;	
}
