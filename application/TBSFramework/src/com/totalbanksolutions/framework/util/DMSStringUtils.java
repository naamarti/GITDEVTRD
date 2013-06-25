package com.totalbanksolutions.framework.util;

/*
 * =================================================================================================
 * Modified:  1 Oct 2012 DJM #1897: RBC Manual Transactions spreadsheet - strip out special characters, symbols & spaces from account number
 *
 * =================================================================================================
 */
public class DMSStringUtils {

	public enum CharType 
	{
		 CHAR_TYPE_NUMERIC
		,CHAR_TYPE_ALPHA
		,CHAR_TYPE_UPPER_CASE
		,CHAR_TYPE_LOWER_CASE
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String s = "xx2-33111-1-0";
		s = DMSStringUtils.stripNonAlphaNumericChars(s);
		
		System.out.println(s);
	}

	public static String stripNonAlphaNumericChars( String data )
	{
		return data.replaceAll("[^a-zA-Z0-9]", "");
	}
	
	
	public static int countNumericChars( String data )
	{
		return countCharType( data, CharType.CHAR_TYPE_NUMERIC );
	}

	public static int countAlphaChars( String data )
	{
		return countCharType( data, CharType.CHAR_TYPE_ALPHA );
	}
	
	public static int countUpperCaseChars( String data )
	{
		return countCharType( data, CharType.CHAR_TYPE_UPPER_CASE );
	}

	public static int countLowerCaseChars( String data )
	{
		return countCharType( data, CharType.CHAR_TYPE_LOWER_CASE );
	}
	
	private static int countCharType( String data, CharType type )
	{
		int count = 0;

		if (data == null )
			return count;
		
		for (int i = 0; i < data.length(); i++) 
		{
			if ( type == CharType.CHAR_TYPE_NUMERIC )
			{
				if (Character.isDigit( data.charAt(i) ))
					count++;
			}
			else if ( type == CharType.CHAR_TYPE_ALPHA )
			{
				if (Character.isLetter( data.charAt(i) ))
					count++;
			}
			else if ( type == CharType.CHAR_TYPE_UPPER_CASE )
			{
				if (Character.isUpperCase( data.charAt(i) ))
					count++;
			}
			else if ( type == CharType.CHAR_TYPE_LOWER_CASE )
			{
				if (Character.isLowerCase( data.charAt(i) ))
					count++;
			}
		}
		
		return count;
		
	}

}
