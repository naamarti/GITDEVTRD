package com.totalbanksolutions.framework.web.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.enums.JavaDataType;
import com.totalbanksolutions.framework.model.ModelColumnSpecification;
import com.totalbanksolutions.framework.web.enums.Align;
import com.totalbanksolutions.framework.web.enums.WebTableCellFormatType;
import com.totalbanksolutions.framework.web.enums.WebTableCellType;

public class WebTableColumn {

    protected final Log log = LogFactory.getLog(WebTableColumn.class);
    
	private String columnName = "";
	private String label = "";
	private int size = 0;
	private String dataType = JavaDataType.STRING.name();
	private String toolTip = "";
	private int width = 100;
	private Align align = Align.LEFT;
	private WebTableCellType cellType = WebTableCellType.TEXT;
	private WebTableCellFormatType cellFormatType = WebTableCellFormatType.FREE_FORM_TEXT;
	private String image = "";
	private int imageWidth = 0;
	private int imageHeight = 0;
//	private boolean isClickable = false;
	private boolean isZoomable = false;
	private boolean isLink = false;
	private boolean isDashedZero = false;
	private String link = "";
	private String cellDateTimeFormat = "dd-MMM-yyyy";

	public WebTableColumn()
	{
		super();
	}

	public WebTableColumn( ModelColumnSpecification col )
	{
		super();
		this.columnName = col.getColumnName();
		this.size = col.getSize();		
		this.dataType = col.getDataType().name();
		this.label = col.getLabel();
		this.toolTip = col.getDescription();
	}
	
	// Getters and Setters
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Align getAlign() {
		return align;
	}

	public void setAlign(Align align) {
		this.align = align;
	}
	
	public WebTableCellFormatType getCellFormatType() {
		return cellFormatType;
	}

	public void setCellFormatType(WebTableCellFormatType cellFormatType) {
		this.cellFormatType = cellFormatType;
	}
	
	public WebTableCellType getCellType() {
		return cellType;
	}

	public void setCellType(WebTableCellType cellType) {
		this.cellType = cellType;
	}

	public String getImage() {
		return image;
	}

	public String getCellDateTimeFormat()
	{
		return this.cellDateTimeFormat;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public boolean isZoomable() {
		return isZoomable;
	}

	public void setZoomable(boolean isZoomable) {
		this.isZoomable = isZoomable;
	}

	public boolean isLink() {
		return isLink;
	}

	public void setIsLink(boolean isLink) {
		this.isLink = isLink;
	}
	
	public boolean isDashedZero() {
		return isDashedZero;
	}

	public void setIsDashedZero(boolean isDashedZero) {
		this.isDashedZero = isDashedZero;
	}

	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	public void setCellDateTimeFormat(String format)
	{
		this.cellDateTimeFormat = format;
	}
	
//	public boolean isClickable() {
//		return isClickable;
//	}
//
//	public void setClickable(boolean isClickable) {
//		this.isClickable = isClickable;
//	}

	
	
}
