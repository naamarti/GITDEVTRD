package com.totalbanksolutions.framework.web.bean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.JavaDataType;
import com.totalbanksolutions.framework.model.ModelColumnSpecification;
import com.totalbanksolutions.framework.web.enums.ValidationState;
import com.totalbanksolutions.framework.web.enums.WebControlType;
import com.totalbanksolutions.framework.web.enums.WebTableCellFormatType;

public class WebFormControl {

    protected final Log log = LogFactory.getLog(WebFormControl.class);
    
	private String id = "";
	private WebControlType controlType = WebControlType.INPUT_TEXT;
	private WebTableCellFormatType formatType = WebTableCellFormatType.FREE_FORM_TEXT;
	private String formatPattern = "";
	private String label = "";
	private int size = 0;
	private String dataType = JavaDataType.STRING.name();
	private String toolTip = "";
	private Object value = null;
	private Map<String,String> dropDownList = new LinkedHashMap<>();
	private boolean isRequired = false;
	private boolean isReadOnly = false;
	private double rangeMinimum = 0;
	private double rangeMaximum = 0;
	private ValidationState validationState = ValidationState.NONE;
	
	public WebFormControl()
	{
		super();
	}

	public WebFormControl( ModelColumnSpecification col, WebControlType controlType, Object value )
	{
		super();
		this.id = col.getColumnName();
		this.size = col.getSize();		
		this.dataType = col.getDataType().name();
		this.label = col.getLabel();
		this.toolTip = col.getDescription();
		this.value = value;
		this.controlType = controlType;
	}
	
	public void addDropDownValue(String id, String value)
	{
		this.dropDownList.put(id, value);
	}
	
	public void addDropDownValues( List<? extends AbstractDatabaseBean<?>> list, String idField, String valueField )
	{
		log.debug( "****listSize=" + list.size() );
		for( AbstractDatabaseBean<?> row : list )
		{
			String id = row.getField(idField).getStringValue();
			String value = row.getField(valueField).getStringValue();
			log.debug( "****id=" + id + "; value=" + value );
			this.addDropDownValue(id, value);
		}
	}
	
	// Getters and Setters
	public WebTableCellFormatType getFormatType() {
		return formatType;
	}
	public void setFormatType(WebTableCellFormatType cellFormatType) {
		this.formatType = cellFormatType;
	}
	
	public String getFormatPattern() {
		return this.formatPattern;
	}
	public void setFormatPattern(String formatPattern) {
		this.formatPattern = formatPattern;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getToolTip() {
		return toolTip;
	}
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public WebControlType getControlType() {
		return controlType;
	}
	public void setControlType(WebControlType controlType) {
		this.controlType = controlType;
	}

	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public Map<String, String> getDropDownList() {
		return dropDownList;
	}
	public void setDropDownList(Map<String, String> dropDownList) {
		this.dropDownList = dropDownList;
	}

	public boolean isRequired() {
		return isRequired;
	}
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public double getRangeMinimum() {
		return rangeMinimum;
	}
	public void setRangeMinimum(double rangeMinimum) {
		this.rangeMinimum = rangeMinimum;
	}

	public double getRangeMaximum() {
		return rangeMaximum;
	}
	public void setRangeMaximum(double rangeMaximum) {
		this.rangeMaximum = rangeMaximum;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public ValidationState getValidationState() {
		return validationState;
	}
	public void setValidationState(ValidationState validationState) {
		this.validationState = validationState;
	}

	
}
