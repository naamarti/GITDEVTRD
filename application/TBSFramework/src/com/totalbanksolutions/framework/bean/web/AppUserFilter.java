package com.totalbanksolutions.framework.bean.web;

import java.util.List;

import com.totalbanksolutions.framework.bean.util.AbstractBean;
import com.totalbanksolutions.framework.enums.JavaDataType;
import com.totalbanksolutions.framework.bean.web.ForecastFilter.Field;

public class AppUserFilter extends AbstractBean<AppUserFilter.Field>
{
	private List<Long>	allowedSourceInstitutionIdList 	= null;
	private List<Long>	allowedDepositInstitutionIdList = null;

	public enum Field
	{
		 USER_NAME
		,USER_TYPE
		,USER_TYPE_ID
		,ROLE_ID
		,SOURCE_INST_ID
		,DEPOSIT_INST_ID
		,FULL_NAME
		;
	}
		
	public AppUserFilter()
	{
		super();
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Java_DataType				Size
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.USER_NAME,					JavaDataType.STRING,		0);
		super.addField(Field.USER_TYPE,					JavaDataType.STRING,		0);
		super.addField(Field.USER_TYPE_ID,				JavaDataType.LONG,			0);
		super.addField(Field.ROLE_ID,					JavaDataType.LONG,			0);
		super.addField(Field.SOURCE_INST_ID,			JavaDataType.LONG,			0);
		super.addField(Field.DEPOSIT_INST_ID,			JavaDataType.LONG,			0);
		super.addField(Field.FULL_NAME,					JavaDataType.STRING,		0);
	}

	public List<Long> getAllowedSourceInstitutionIdList() {
		return allowedSourceInstitutionIdList;
	}

	public void setAllowedSourceInstitutionIdList(
			List<Long> allowedSourceInstitutionIdList) {
		this.allowedSourceInstitutionIdList = allowedSourceInstitutionIdList;
	}

	public List<Long> getAllowedDepositInstitutionIdList() {
		return allowedDepositInstitutionIdList;
	}

	public void setAllowedDepositInstitutionIdList(
			List<Long> allowedDepositInstitutionIdList) {
		this.allowedDepositInstitutionIdList = allowedDepositInstitutionIdList;
	}

	// Standard Getters
	public long getDepositInstitutionId()			{ return this.getField(Field.DEPOSIT_INST_ID).getLongValue(); }
	public long getRoleId()							{ return this.getField(Field.ROLE_ID).getLongValue(); }
	public long getSourceInstitutionId()			{ return this.getField(Field.SOURCE_INST_ID).getLongValue(); }
	public String getUserName()						{ return this.getField(Field.USER_NAME).getStringValue(); }
	public String getUserType()						{ return this.getField(Field.USER_TYPE).getStringValue(); }
	public long getUserTypeId()						{ return this.getField(Field.USER_TYPE_ID).getLongValue(); }

	// Web Binding Id's
	public String getDepositInstitutionIdWebId()	{ return this.getField(Field.DEPOSIT_INST_ID).getBindId(); }
	public String getRoleIdWebId() 					{ return this.getField(Field.ROLE_ID).getBindId(); }
	public String getSourceInstitutionIdWebId() 	{ return this.getField(Field.SOURCE_INST_ID).getBindId(); }
	public String getUserNameWebId() 				{ return this.getField(Field.USER_NAME).getBindId(); }
	public String getUserTypeIdWebId() 				{ return this.getField(Field.USER_TYPE_ID).getBindId(); }

	public void setUserName(String username)
	{
		this.getField(Field.USER_NAME).setValue(username);
	}
}
