package com.totalbanksolutions.framework.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.DateFormats;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.NumberRecord;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilityExcel {
	
	protected static Log log = LogFactory.getLog(UtilityExcel.class);
	private  WritableWorkbook workbook;
	private WritableSheet sheet ;
	
	public UtilityExcel(String fileName) throws IOException{
		WorkbookSettings ws = new WorkbookSettings();
		ws.setLocale(new Locale("en", "EN"));
		workbook = Workbook.createWorkbook(new File(fileName), ws);
		sheet = workbook.createSheet("Sheet1", 0);
	}
	
	public static class Num extends NumberRecord
	{
		private NumberFormat format;
		private double value;
		
		public Num(int row, int col, double number){
			super(row, col, number);			
		}
		
		public void setCellFormat(NumberFormat format){
			this.format = format;
		}
		
		public NumberFormat getFormat(){
			return format;
		}

		@Override
		public WritableCell copyTo(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		public double getValue() {
			return value;
		}

		public void setValue(double value) {
			this.value = value;
		}

		public void setFormat(NumberFormat format) {
			this.format = format;
		}
	}
	public void write(){
		try {
			workbook.write();
			workbook.close();
		} catch (WriteException e) {			
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	    
	}
	/**
	 * 
	 * DEFAULT 2 DECIMALS
	 */
	public void writeNumber(int row, int col, Double number)
	{									 			
		WritableCellFormat cf2 = new WritableCellFormat(NumberFormats.FORMAT3);
		
	    Number n = new Number(row,col,number,cf2);
	    try {
	    	sheet.setColumnView(row, 15);
			sheet.addCell(n);
		} catch (RowsExceededException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			} catch (IOException e1) {				
				e1.printStackTrace();
				try {
					workbook.close();
				} catch (WriteException e3) {				
					e3.printStackTrace();
				}catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		} catch (WriteException e) {			
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public void writeNumber(int row, int col, Integer number){
		WritableCellFormat cf2 = new WritableCellFormat(NumberFormats.INTEGER);
		Number n = new Number(row,col,number,cf2);
	    try {
	    	sheet.setColumnView(row, 15);
	    	sheet.addCell(n);
		} catch (RowsExceededException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			} catch (IOException e1) {				
				e1.printStackTrace();
				try {
					workbook.close();
				} catch (WriteException e3) {				
					e3.printStackTrace();
				}catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		} catch (WriteException e) {			
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	public void writeNumber(int row, int col, Double number, String pattern)
	{									 			
		NumberFormat dp3 = new NumberFormat(pattern);
	    WritableCellFormat dp3c = new WritableCellFormat(dp3);	    		
	    Number n = new Number(row,col,number,dp3c);	    
	    try {
	    	sheet.setColumnView(row, 15);
			sheet.addCell(n);
		} catch (RowsExceededException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		} catch (WriteException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}
	public void writeNumberWithBackground(int row, int col, Double str){
		WritableCellFormat cf2 = new WritableCellFormat(NumberFormats.FORMAT3);						
		try {
			Number n = new Number(row,col,str,cf2);
			cf2.setWrap(true);
			cf2.setBackground(Colour.GREY_25_PERCENT);			
			cf2.setAlignment(Alignment.RIGHT);
			sheet.setColumnView(row, 25);
			sheet.addCell(n);
		} catch (RowsExceededException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		} catch (WriteException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}
	public void writeHeadings(int row, int col, String str){		
		
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 
			      10, WritableFont.BOLD);
		
		WritableCellFormat cf = new WritableCellFormat(wf);						
		try {
			
			cf.setWrap(true);
			cf.setBackground(Colour.GREY_25_PERCENT);			
			cf.setAlignment(Alignment.RIGHT);
			Label cell = new Label(row, col, str, cf);			
			sheet.setColumnView(row, 30);
			sheet.addCell(cell);
		} catch (RowsExceededException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		} catch (WriteException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		
	}
	
	public void writeDate(int row, int col, Date str){
		WritableCellFormat cf1 = new WritableCellFormat(DateFormats.FORMAT9);
		DateTime dt = new DateTime(row,col,str, cf1, DateTime.GMT);
		try {
			sheet.addCell(dt);
		} catch (RowsExceededException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		} catch (WriteException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}
	public void writeText(int row, int col, String str){
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 
			      10, WritableFont.NO_BOLD);
		
		WritableCellFormat cf = new WritableCellFormat(wf);						
		try {
			cf.setWrap(true);
			cf.setAlignment(Alignment.RIGHT);
			Label cell = new Label(row, col, str, cf);
			sheet.setColumnView(row, 25);
			sheet.addCell(cell);
		} catch (RowsExceededException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		} catch (WriteException e) {
			e.printStackTrace();
			try {
				workbook.close();
			} catch (WriteException e1) {				
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		
	}
}
