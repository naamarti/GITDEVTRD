package com.totalbanksolutions.framework.file;

import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

public class FileConversionUtility 
{

	public enum DecimalPointFormat
	{
		 HIDE
		,SHOW
	}
	
	public enum DecimalPlaces
	{
		 DECIMALS_0
		,DECIMALS_1
		,DECIMALS_2
		,DECIMALS_3
		,DECIMALS_4
		,DECIMALS_5
		,DECIMALS_6
		,DECIMALS_7
		,DECIMALS_8
		,DECIMALS_9
		,DECIMALS_10
		,DECIMALS_11
		,DECIMALS_12
		,DECIMALS_13	
	}

	public static Date convertDate(String date)
	{
		Date d = new Date();
		try
		{
			String[] formats = new String[] {"yyyyMMdd", "M/dd/yy", "MM/dd/yy", "MM/dd/yyyy", "yyMMdd",  "MM/dd/yyyy" };
			d = DateUtils.parseDate(date, formats);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		return d;
	}

	public static double convertRateToAPY(double rate)
	{
		Double apy = rate / new Double(100); 	// annual factor
		apy = apy / new Double(12);				// factor for the period (12 periods in year)
		apy = 1 + apy;							// 1 + period factor
		apy = Math.pow(apy, 12);				// Formula result
		apy = (apy - 1) * new Double(100);		// APY		
		return apy;
	}
	
	public static String formatAmountForLog(Double amount)
	{
		String amountString = "";
		DecimalFormat myFormatter = new DecimalFormat();
		myFormatter.setGroupingUsed(true);                 // 1000's separators
		myFormatter.setMinimumFractionDigits(2);
		myFormatter.setMaximumFractionDigits(2);
		myFormatter.setNegativePrefix("$(");
		myFormatter.setNegativeSuffix(")");
		myFormatter.setPositivePrefix("$");
		amountString = myFormatter.format(amount);
		return amountString;		
	}

	public static String formatAmount(String input, DecimalPlaces decimalPlaces, DecimalPointFormat decimalPointFormat)
	{
		int size = 0;
		return formatAmount(input, size, decimalPlaces, decimalPointFormat);
	}

	public static String formatAmount(String input, int size, DecimalPlaces decimalPlaces, DecimalPointFormat decimalPointFormat)
	{
		Double amount = Double.parseDouble(input);
		return formatAmount(amount, size, decimalPlaces, decimalPointFormat);
	}

	public static String formatAmount(double amount, DecimalPlaces decimalPlaces, DecimalPointFormat decimalPointFormat)
	{
		int size = 0;
		return formatAmount(amount, size, decimalPlaces, decimalPointFormat);
	}
	
	public static String formatAmount(double amount, int size, DecimalPlaces decimalPlaces, DecimalPointFormat decimalPointFormat)
	{
		String output = "";
		//boolean isNegative = amount < 0 ? true : false;
		int numberDecimals = decimalPlaces.ordinal();
		amount = Math.abs(amount);
		amount = amount*( Math.pow(10,numberDecimals) );
		amount = (double)(Math.round(amount));
		amount = amount/( Math.pow(10,numberDecimals) );
		String decimals = StringUtils.repeat("0", numberDecimals);
		DecimalFormat myFormatter = new DecimalFormat("###0." + decimals);
		output = myFormatter.format(amount);
		if(decimalPointFormat == DecimalPointFormat.HIDE)
		{
			output = StringUtils.remove(output, ".");
		}
		if(size > 0)
		{
			output = StringUtils.leftPad(output, size, "0");
		}
		//if(isNegative)
		//{
		//	output = "-" + output.substring(1);
		//}
		return output;
	}
	
	public static String formatCobolAmount(String input)
	{
		// SIGNED Cobol value; replace last character of Amount field with char shown below
		/*
			Value    Character 
			  +0={, +1=A, +2=B, +3=C, +4=D, +5=E, +6=F, +7=G, +8=H, +9=I
			  -0=}, -1=J, -2=K, -3=L, -4=M, -5=N, -6=O, -7=P, -8=Q, -9=R
		*/
		String output = "";
		boolean flag = false;
		flag = input.contains("-");
		String lastChar = StringUtils.right(input, 1);
		String newLastChar = lastChar;
		if(!flag){
			if(lastChar.equalsIgnoreCase("0"))
			{
				newLastChar = "{";
			}
			else if(lastChar.equalsIgnoreCase("1"))
			{
				newLastChar = "A";
			}		
			else if(lastChar.equalsIgnoreCase("2"))
			{
				newLastChar = "B";
			}				
			else if(lastChar.equalsIgnoreCase("3"))
			{
				newLastChar = "C";
			}		
			else if(lastChar.equalsIgnoreCase("4"))
			{
				newLastChar = "D";
			}		
			else if(lastChar.equalsIgnoreCase("5"))
			{
				newLastChar = "E";
			}		
			else if(lastChar.equalsIgnoreCase("6"))
			{
				newLastChar = "F";
			}		
			else if(lastChar.equalsIgnoreCase("7"))
			{
				newLastChar = "G";
			}		
			else if(lastChar.equalsIgnoreCase("8"))
			{
				newLastChar = "H";
			}		
			else if(lastChar.equalsIgnoreCase("9"))
			{
				newLastChar = "I";
			}
		}
		else{
			input = StringUtils.replaceOnce(input, "-", "0") ;
			if(lastChar.equalsIgnoreCase("0"))
			{
				newLastChar = "}";
			}
			else if(lastChar.equalsIgnoreCase("1"))
			{
				newLastChar = "J";
			}		
			else if(lastChar.equalsIgnoreCase("2"))
			{
				newLastChar = "K";
			}				
			else if(lastChar.equalsIgnoreCase("3"))
			{
				newLastChar = "L";
			}		
			else if(lastChar.equalsIgnoreCase("4"))
			{
				newLastChar = "M";
			}		
			else if(lastChar.equalsIgnoreCase("5"))
			{
				newLastChar = "N";
			}		
			else if(lastChar.equalsIgnoreCase("6"))
			{
				newLastChar = "O";
			}		
			else if(lastChar.equalsIgnoreCase("7"))
			{
				newLastChar = "P";
			}		
			else if(lastChar.equalsIgnoreCase("8"))
			{
				newLastChar = "Q";
			}		
			else if(lastChar.equalsIgnoreCase("9"))
			{
				newLastChar = "R";
			}
		}		
		output = StringUtils.chop(input) + newLastChar;
		return output;
	}
	
	public static String removeLeadingZeros(String input)
	{
		String output = input;
		while (output.length() > 1 && output.startsWith("0"))
		{
			output = output.substring(1);
		}
		return output;
	}

	
	public static String removeCommasInsideQuotes(String line){		
		String output="";
		StringBuffer buff1 = new StringBuffer();
		String[] sts = line.split(",");		
		for(String str:sts){
			if(!str.equals("\"\"")){				
				if(str.charAt(0) == '\"' && str.charAt(str.length()-1) == '\"'){
					buff1.append(str.substring(1, str.length()-1));
					buff1.append(",");
				}else if(str.charAt(0) == '\"' && !(str.charAt(str.length()-1) == '\"')){
					buff1.append(str.substring(1, str.length()));
					
				}else if(!(str.charAt(0) == '\"') && (str.charAt(str.length()-1) == '\"')){
					//System.out.println("str = "+str);
					buff1.append(str.substring(0, str.length()-1));
					buff1.append(",");
				}else{
					buff1.append(str);
					buff1.append(",");
				}
			}else{
				buff1.append(" ,");
			}
			
				
		}
		output = buff1.toString();
		//System.out.println("Output = "+output.substring(0, output.length()-1));
		return output.substring(0, output.length()-1);
	}
		
}

