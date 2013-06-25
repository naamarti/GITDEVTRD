package com.totalbanksolutions.framework.web.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.totalbanksolutions.framework.enums.JavaDataType;
import com.totalbanksolutions.framework.model.ModelColumnSpecification;
import com.totalbanksolutions.framework.model.ModelRow;
import com.totalbanksolutions.framework.model.ModelTable;
import com.totalbanksolutions.framework.web.enums.Align;
import com.totalbanksolutions.framework.web.enums.WebScrollType;
import com.totalbanksolutions.framework.web.enums.WebTableCellType;

public class WebTable {

	private ModelTable modelTable;
	private Map<String,WebTableColumn> columnMap = new LinkedHashMap<>();
	private int displayRowCount = 10;
	private int containerWidth = 0;
	private int columnsWidth = 24; // rowNumber column
	private WebScrollType scrollTypeX = WebScrollType.AUTO;
	private WebScrollType scrollTypeY = WebScrollType.AUTO;
	private boolean isShowRowNumbers = true;
	private boolean hasTotalRow	= false;
	
	public WebTable( ModelTable table )
	{
		this.modelTable = table;
	}
	
	public void addAllColumns()
	{
		for( ModelColumnSpecification col : this.modelTable.getColumnList() )
		{
			int size = 150;
			Align align = Align.LEFT;
			if( col.getDataType() == JavaDataType.DOUBLE || col.getDataType() == JavaDataType.INT || col.getDataType() == JavaDataType.LONG )
			{
				align = Align.RIGHT;
			}
			if( col.getDataType() == JavaDataType.STRING )
			{
				size = 2 * col.getSize();
			}			
			WebTableColumn c = this.addColumn( col.getColumnName(), size, align );
			String label = col.getColumnName().substring(0, 1).toUpperCase() + col.getColumnName().substring(1);
			c.setLabel( label );			
		}
	}
	
	public WebTableColumn addColumn( String columnName, int width, Align alignment )
	{
		ModelColumnSpecification col = this.modelTable.getColumn(columnName);
		WebTableColumn tableColumn = new WebTableColumn(col);
		tableColumn.setWidth(width);
		tableColumn.setAlign(alignment);
		this.columnMap.put( columnName, tableColumn );
		this.columnsWidth += width;
		return tableColumn;
	}

	public WebTableColumn addWideColumnActionDelete()
	{
		String columnName = "actionDelete";
		int width = 60;
		WebTableColumn tableColumn = new WebTableColumn();
		tableColumn.setColumnName(columnName);
		tableColumn.setLabel("Delete");
		tableColumn.setWidth(width);
		tableColumn.setAlign(Align.CENTER);
		tableColumn.setCellType(WebTableCellType.IMAGE);
		tableColumn.setImage("trash1.gif");
		tableColumn.setImageWidth(11);
		tableColumn.setImageHeight(14);
//		tableColumn.setClickable(true);
		this.columnMap.put( columnName, tableColumn );
		this.columnsWidth += width;
		return tableColumn;
	}

	public WebTableColumn addColumnActionDelete()
	{
		String columnName = "actionDelete";
		int width = 33;
		WebTableColumn tableColumn = new WebTableColumn();
		tableColumn.setColumnName(columnName);
		tableColumn.setLabel("Del");
		tableColumn.setWidth(width);
		tableColumn.setAlign(Align.CENTER);
		tableColumn.setCellType(WebTableCellType.IMAGE);
		tableColumn.setImage("trash1.gif");
		tableColumn.setImageWidth(11);
		tableColumn.setImageHeight(14);
//		tableColumn.setClickable(true);
		this.columnMap.put( columnName, tableColumn );
		this.columnsWidth += width;
		return tableColumn;
	}
	
	public WebTableColumn addColumnActionEdit()
	{
		String columnName = "actionEdit";
		int width = 33;
		WebTableColumn tableColumn = new WebTableColumn();
		tableColumn.setColumnName(columnName);
		tableColumn.setLabel("Edit");
		tableColumn.setWidth(width);
		tableColumn.setAlign(Align.CENTER);
		tableColumn.setCellType(WebTableCellType.IMAGE);
		tableColumn.setImage("edit1_16.png");
		tableColumn.setImageWidth(14);
		tableColumn.setImageHeight(14);
//		tableColumn.setClickable(true);
		this.columnMap.put( columnName, tableColumn );
		this.columnsWidth += width;
		return tableColumn;
	}
	
	public WebTableColumn addColumnActionEditProductBanks()
	{
		String columnName = "actionEditProductBanks";
		int width = 39;
		WebTableColumn tableColumn = new WebTableColumn();
		tableColumn.setColumnName(columnName);
		tableColumn.setLabel("Banks");
		tableColumn.setWidth(width);
		tableColumn.setAlign(Align.CENTER);
		tableColumn.setCellType(WebTableCellType.IMAGE);
		tableColumn.setImage("bank1_24.png");
		tableColumn.setImageWidth(14);
		tableColumn.setImageHeight(14);
//		tableColumn.setClickable(true);
		this.columnMap.put( columnName, tableColumn );
		this.columnsWidth += width;
		return tableColumn;
	}
		
	public WebTableColumn addColumnImageLink(String columnName, String label, String image, int width, Align alignment, String link )
	{
		WebTableColumn tableColumn = new WebTableColumn();
		tableColumn.setColumnName(columnName);
		tableColumn.setLabel(label);
		tableColumn.setWidth(width);
		tableColumn.setAlign(alignment);
		tableColumn.setCellType(WebTableCellType.IMAGE);
		tableColumn.setImage(image);
		tableColumn.setImageWidth(14);
		tableColumn.setImageHeight(14);
		tableColumn.setLink(link);
		tableColumn.setIsLink(true);
		this.columnMap.put( columnName, tableColumn );
		this.columnsWidth += width;
		return tableColumn;
	}
	
	public WebTableColumn addColumnActionLink(String columnName, String columnlink, int width, Align alignment )
	{
		ModelColumnSpecification col = this.modelTable.getColumn(columnName);
		WebTableColumn tableColumn = new WebTableColumn(col);
		tableColumn.setWidth(width);
		tableColumn.setAlign(alignment);
		tableColumn.setIsLink(true);
		tableColumn.setLink(columnlink);
		this.columnMap.put( columnName, tableColumn );
		this.columnsWidth += width;
		return tableColumn;
	}
	
	public int getRowCount()
	{
		return this.modelTable.getRowList().size();
	}
	
	public List<WebTableColumn> getColumnList() 
	{
		return new ArrayList<WebTableColumn>( columnMap.values() );
	}
	
	public Map<String,WebTableColumn> getColumnMap()
	{
		return this.columnMap;
	}
	
	public int getColumnsWidth()
	{
		return this.columnsWidth;
	}

	public int getContainerWidth() 
	{
		if ( this.containerWidth > 0 )
		{
			return this.containerWidth;
		}
		else
		{
			return this.columnsWidth;
		}
	}
	
	public int getDisplayRowCount() 
	{
		return displayRowCount;
	}
	
	public List<ModelRow> getRowList()
	{
		return this.modelTable.getRowList();
	}
	
	public WebScrollType getScrollTypeX()
	{
		return this.scrollTypeX;
	}

	public WebScrollType getScrollTypeY()
	{
		return this.scrollTypeY;
	}
	
	public void setDisplayRowCount(int displayRowCount) 
	{
		this.displayRowCount = displayRowCount;
	}

	public void setContainerWidth( int containerWidth )
	{
		this.columnsWidth = containerWidth;
	}
	
	public void setScrollTypeX( WebScrollType x )
	{
		this.scrollTypeX = x;
	}
	
	public void setScrollTypeY( WebScrollType y )
	{
		this.scrollTypeY = y;
	}

	public boolean isShowRowNumbers() {
		return isShowRowNumbers;
	}

	public void setShowRowNumbers(boolean isShowRowNumbers) {
		this.isShowRowNumbers = isShowRowNumbers;
	}

	public boolean getHasTotalRow() {
		return this.hasTotalRow;
	}

	public void setHasTotalRow(boolean hasTotalRow){
		this.hasTotalRow = hasTotalRow;
	}
}
