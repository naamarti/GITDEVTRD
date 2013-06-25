package com.totalbanksolutions.framework.web.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.totalbanksolutions.framework.model.ModelColumnSpecification;
import com.totalbanksolutions.framework.model.ModelRow;
import com.totalbanksolutions.framework.model.ModelTable;
import com.totalbanksolutions.framework.web.enums.ValidationState;
import com.totalbanksolutions.framework.web.enums.WebControlType;

public class WebForm {

	private ModelTable modelTable;
	private List<WebFormControl> controlsList = new ArrayList<>();
	private Map<String,String> invalidMap = new HashMap<>();
	private boolean isValidationsSet = false;
	private boolean isDisplayEditButton = false;
	
	public WebForm()
	{
		super();
	}
	
	public WebForm( ModelTable table )
	{
		this.modelTable = table;
	}

	public WebFormControl addControl( String columnName, WebControlType controlType )
	{
		ModelColumnSpecification col = this.modelTable.getColumn(columnName);
		ModelRow row = this.modelTable.getRow();
		Object value = null;
		if( row != null )
		{
			value = row.getColumn(columnName).getValue();
		}
		WebFormControl control = new WebFormControl( col, controlType, value );
		if( this.invalidMap.containsKey(columnName) )
		{
			control.setValidationState( ValidationState.INVALID );			
		}
		else if( isValidationsSet )
		{
			control.setValidationState( ValidationState.VALID );			
		}
		this.controlsList.add(control);
		return control;
	}

	public void setStateInvalid( String columnName )
	{
		this.invalidMap.put(columnName, columnName);
		isValidationsSet = true;
	}

	// Getters and Setters
	public List<WebFormControl> getControlsList() {
		return controlsList;
	}
	
	public ModelTable getModelTable() {
		return this.modelTable;
	}



	// Setters
	
	public boolean isDisplayEditButton() {
		return isDisplayEditButton;
	}

	public void setDisplayEditButton(boolean isDisplayEditButton) {
		this.isDisplayEditButton = isDisplayEditButton;
	}

	public void setModelTable(ModelTable modelTable) {
		this.modelTable = modelTable;
	}

	
	
}
