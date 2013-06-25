package com.totalbanksolutions.framework.util;

import java.io.File; 
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import jxl.*; 
import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.totalbanksolutions.framework.bean.util.AutoDataRow;
import com.totalbanksolutions.framework.bean.util.Column;
import com.totalbanksolutions.framework.enums.Alignment;
import com.totalbanksolutions.framework.enums.Font;
import com.totalbanksolutions.framework.parse.ParseRow;

public class ExcelUtility 
{
	protected static Log log = LogFactory.getLog(ExcelUtility.class);
	
	public static void main(String[] args) 
	{
		testRead();
		//testWrite();
	}
	
	public static ArrayList<ParseRow> parseSheet(String fileName)
	{
		ArrayList<ParseRow> rows = new ArrayList<ParseRow>();
		Workbook workbook = null; 
		try
		{
			File f = new File(fileName);
			workbook = Workbook.getWorkbook(f); 
			Sheet sheet = workbook.getSheet(0);
			int rowCount = sheet.getRows();
			for(int i = 0; i < rowCount; i++)
			{
	        	ParseRow row = new ParseRow();
	        	row.setLineNumber(i + 1);
	        	String rowData = "";
				//System.out.print("ROW" + i + " | ");
				Cell[] cells = sheet.getRow(i);
				for(Cell cell : cells)
				{
					String contents = "";
					if(cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA)
					{
						double val = ((NumberCell)cell).getValue();						
						//contents = Double.toString(val);
						DecimalFormat myFormatter = new DecimalFormat();
						myFormatter.setDecimalSeparatorAlwaysShown(false);	// No decimal places so Text that looks like an Integer is not formatted as such (eg. "031" --> 31.0)
						myFormatter.setGroupingUsed(false);                 // No 1000's separators
						contents = myFormatter.format(val);						
					}
					else if(cell.getType() == CellType.LABEL)
					{
						contents = cell.getContents();
					}
					else
					{
						contents = cell.getContents();
					}
					contents = contents.trim();
					contents = removeNonPrintableChars(contents);
					//System.out.print(contents + " | ");
		        	row.getColumnData().add(contents);
		        	//rowData += contents + " | "; // VCatrini Apr-10-2009
				}
				row.setRowData(rowData);
	        	rows.add(row);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
			//e.printStackTrace();
		}
		finally
		{
			if(workbook != null) workbook.close();
		}		
		return rows;
	}
	
	public static void testRead()
	{
		Workbook workbook = null;
		int currentRow = 0;
		try
		{
			String fileName = "C:/tbs_autorecon/data/Working/Ridge/200901/20090126/TBS-SERVER/Working/Files.FromRidge/2009.01.26.ManualTransactions.xls";
			File f = new File(fileName);
			
			workbook = Workbook.getWorkbook(f); 
			Sheet sheet = workbook.getSheet(0);
			int rowCount = sheet.getRows();
			for(int i = 0; i < rowCount; i++)
			{
				currentRow = i;
				System.out.print("ROW" + i + " | ");
				Cell[] cells = sheet.getRow(i);
				for(Cell cell : cells)
				{
					//String contents = cell.getContents();
					String contents = "";
					String format = "";
					if(cell.getCellFormat() != null && cell.getCellFormat().getFormat().getFormatString() != null)
					{
						format = cell.getCellFormat().getFormat().getFormatString();
					}
					if(cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA)
					{
						double val = ((NumberCell)cell).getValue();						
						//contents = Double.toString(val);
						DecimalFormat myFormatter = new DecimalFormat();
						myFormatter.setDecimalSeparatorAlwaysShown(false);
						myFormatter.setGroupingUsed(false);
						contents = myFormatter.format(val);						
					}
					else if(cell.getType() == CellType.LABEL)
					{
						contents = cell.getContents();
					}
					else
					{
						contents = cell.getContents();
					}
					contents = contents.trim();
					contents = removeNonPrintableChars(contents);
					System.out.print(contents + "[" + cell.getType() + ";" + format + "] | ");
					
				}
				System.out.println("");
			}
		}
		catch(Exception e)
		{
			log.debug("Failed at row " + currentRow);
			e.printStackTrace();
		}
		finally
		{
			if(workbook != null) workbook.close();
		}
	}
	
	public static void testWrite()
	{
		WritableWorkbook workbook = null;
		try
		{
			String fileName = System.getenv("DMS_HOME")+"/data/Working/TestWrite.xls";
			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("en", "EN"));
			workbook = Workbook.createWorkbook(new File(fileName), ws);
	
		    WritableSheet sheet = workbook.createSheet("Sheet1", 0);
		    //WritableSheet sheet2 = workbook.createSheet("Sheet2", 1);
	
			//WritableFont wf = new WritableFont(WritableFont.ARIAL, 12);
			//wf.setItalic(true);
			
			//WritableCellFormat wcf = new WritableCellFormat(wf);
			
			//CellView cv = new CellView();
			//cv.setSize(25 * 256);
			//cv.setFormat(wcf);
			//sheet.setColumnView(0, cv);
			//sheet.setColumnView(1, 15);
			
		    Label l1 = new Label(0, 0, "Label-col0-row0");
		    sheet.addCell(l1);
		    
		    Label l2 = new Label(0, 1, "Label-col0-row1");
		    sheet.addCell(l2);

		    Label l3 = new Label(1, 0, "Label-col1-row0");
		    sheet.addCell(l3);

		    Label l4 = new Label(1, 1, "Label-col1-row1");
		    sheet.addCell(l4);

		    workbook.write();
		    workbook.close();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				if(workbook != null) workbook.close();
			}
			catch(Exception e) { }
		}
	}
	
	private static String removeNonPrintableChars(String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			char[] cArray = input.toCharArray();
			for(char c : cArray)
			{
				if(CharUtils.isAsciiPrintable(c))
				{
					output += c;
				}
			}
		}
		return output;
	}

    public static int exportToExcel(List<AutoDataRow> list, String headers, String fileName){
    	/*
    	 * Headers should be string of all headers separated by ","
    	 */
    	
    	try {
			UtilityExcel excel = new UtilityExcel(fileName);
			String[] header = headers.split(",");
			for (int i = 0; i < header.length; i++){
				excel.writeHeadings(i, 0, (String)header[i]);
			}
			for(int i =0; i<list.size(); i++ ){
				AutoDataRow c = (AutoDataRow)list.get(i);
				Column ls = c.getRows();		
				int size = ls.getList().size();
				
				for(int j = 0; j< size; j++){
					Object clas  = ls.getList().get(j);									
					if(clas instanceof java.util.Date)
					{
						excel.writeDate(j,i+1,(Date)clas);
					}					
					else if((clas instanceof Double) || (clas instanceof Long) )
					{											
						/**
						 * Defualts to 2 decimals
						 */
						excel.writeNumber(j,i+1,(Double)clas);																																					
					}					
					else if(clas instanceof Integer)
					{
						excel.writeNumber(j, i+1, (Integer)clas);
					}					
					else if(clas instanceof String)
					{
						excel.writeText(j,i+1,(String)clas);
					}
					
				}
			}
			excel.write();
			return 0;
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			return -1;			
		}
    }
	
    public static int writeHeading(UtilityExcel excel, Object clas, int rowPosition, int colPosition, boolean isUnderline, Font font, Alignment alignment){
    	if(clas instanceof String)
		{
			excel.writeHeadings(rowPosition,colPosition+1,(String)clas);
		}
    	return 0;
    }
    public static int writeNumberWithBackground(UtilityExcel excel, Object clas, int rowPosition, int colPosition, boolean isUnderline, Font font, Alignment alignment){
    	if(clas instanceof Double ||clas instanceof Long || clas instanceof Integer )
		{
			excel.writeNumberWithBackground(rowPosition,colPosition+1, (Double)clas);
		}
    	return 0;
    }
    
   public static int exportToExcel(UtilityExcel excel, Object clas, int rowPosition, int colPosition, boolean isUnderline, Font font, Alignment alignment){
    		   
	   if(clas instanceof java.util.Date)
		{
		   
			excel.writeDate(rowPosition,colPosition+1,(Date)clas);
		}					
		else if((clas instanceof Double) || (clas instanceof Long) )
		{											
			/**
			 * Defualts to 2 decimals
			 */
			excel.writeNumber(rowPosition,colPosition+1,(Double)clas);																																					
		}					
		else if(clas instanceof Integer)
		{
			excel.writeNumber(rowPosition,colPosition+1, (Integer)clas);
		}					
		else if(clas instanceof String)
		{
			excel.writeText(rowPosition,colPosition+1,(String)clas);
		}
	   return 0;
    }
}
