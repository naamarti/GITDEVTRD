package com.totalbanksolutions.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;

public class ConvertUtility {

    public static boolean intToboolean(int input)
    {
        boolean output = false;
    	if(input == 1)
    	{
    		output = true;
    	}
    	return output;
    }

    public static int booleanToint(boolean input)
    {
        int output = 0;
    	if(input == true)
    	{
    		output = 1;
    	}
    	return output;
    }

    public static Date convertToDate(int year, int month, int day)
    {
    	return new GregorianCalendar(year, month - 1, day).getTime();
    }

    public static Date convertStringToDate(String dateString, String formatString)
    {
	    SimpleDateFormat sim = new SimpleDateFormat(formatString);
	    Date dateOut = new java.util.Date();
	    try
	    {
	    	dateOut = sim.parse(dateString);
	    }
	    catch(Exception e)
	    {
	    	return null;
	    }
	    return dateOut;
    }
    
    public static String convertDateToString(Date dateIn, String formatString)
    {
	    SimpleDateFormat dmj = new SimpleDateFormat(formatString);	            
	    String thisDate = dmj.format(dateIn);
	    return thisDate;
    }
    
    public static Date parseDate(String dateString)
    {
		Date d = null;
		String[] formats = new String[] {"MM/dd/yyyy", "dd-MMM-yyyy", "d-MMM-yyyy"};
		try
		{
			d = DateUtils.parseDate(dateString, formats);
		}
	    catch(Exception e)
	    {
	    	throw new RuntimeException(e);
	    }		
    	return d;
    }
    
    public static String nullToEmptyString(String input)
    {
    	String output = "";
    	if(input != null)
    	{
    		output = input.trim();
    	}
    	return output;
    }

	public static boolean validateInteger(String num) 
	{
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
	public static double roundDouble(double num, int precision) 
	{
		double precisionFactor = Math.pow(10, precision);
		double result = num * precisionFactor;
		result = Math.round(result);
		result = result / precisionFactor;
		return result;
	}

	public static void main(String args[])
    {
    	//String test = null;
    	String after = null;//nullToEmptyString(test);
    	System.out.println("after = " + after);
    	if(after == null)
    	{
    		System.out.println("after = null");
    	}
    	
    	Date d = new Date();
    	String s = convertDateToString(d, "MMddyyyy_hh:mm:ss:SSSS");
    	System.out.println(s);
    	
    	double testValue = 0.003;
    	double roundedValue = roundDouble(testValue, 2);
    	System.out.println("Test value rounded is : " + roundedValue);

    	// Java double rounding error example
    	double one = 6677.0500;
    	double two = 526.3200;
    	double result = one - two;
    	System.out.println("Result is : " + result);
    	
    }
}
