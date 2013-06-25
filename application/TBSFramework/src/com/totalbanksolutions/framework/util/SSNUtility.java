package com.totalbanksolutions.framework.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class SSNUtility 
{

	public static int SSN_VALID_LENGTH = 9;

	private static Logger log = Logger.getLogger(SSNUtility.class);
	
	private static Set<String> invalidSSNs = new HashSet<String>();

	static 
	{
		// Invalid Ascending Sequences
		invalidSSNs.add("012345678");
		invalidSSNs.add("123456789");
		invalidSSNs.add("234567890");
		invalidSSNs.add("345678901");
		invalidSSNs.add("456789012");
		invalidSSNs.add("567890123");
		invalidSSNs.add("678901234");
		invalidSSNs.add("789012345");
		invalidSSNs.add("890123456");
		invalidSSNs.add("912345678");

		// Invalid Repeats
		invalidSSNs.add("000000000");
		invalidSSNs.add("111111111");
		invalidSSNs.add("222222222");
		invalidSSNs.add("333333333");
		invalidSSNs.add("444444444");
		invalidSSNs.add("555555555");
		invalidSSNs.add("666666666");
		invalidSSNs.add("777777777");
		invalidSSNs.add("888888888");
		invalidSSNs.add("999999999");
	};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// invalid ascending
		boolean isValid = SSNUtility.isSSNValid( "123456789");
		isValid = SSNUtility.isSSNValid( "234567890");
		
		// invalid descending
		isValid = SSNUtility.isSSNValid( "987654321");
		
		// invalid null and empty
		isValid = SSNUtility.isSSNValid( null );
		isValid = SSNUtility.isSSNValid( " " );
		
		// invalid same character
		isValid = SSNUtility.isSSNValid( "111111111");
		isValid = SSNUtility.isSSNValid( "999999999");

		// invalid wrong number of characters
		isValid = SSNUtility.isSSNValid( "1111111");

		log.debug("isValid=" + isValid);
		
		// valid SSN
		isValid = SSNUtility.isSSNValid( "234234123");

		// test DESCRAMBLING of SSN
		String scrambledSSN = "076267711";
		log.debug("original scrambled SSN is : " + scrambledSSN );
		String descrambledSSN = SSNUtility.descrambleSSN(scrambledSSN);
		log.debug("descrambled it is " + descrambledSSN );

		// test SCRAMBLING of SSN
		scrambledSSN = SSNUtility.scrambleSSN(descrambledSSN);
		log.debug("After rescrambling it, the SSN is " + scrambledSSN );
		
	}

	public static boolean isSSNValid( String ssn )
	{
		boolean isValid = true;
		
		ssn = StringUtils.trim(ssn);
		
		// first, check null, empty string, or incorrect length conditions
		if ( ( ssn == null )
		 || ( ssn.length() != SSN_VALID_LENGTH ))
		 	return false;
		
		// then, check to make sure it is numeric
		if ( !StringUtils.isNumeric(ssn) )
			return false;
		
		// finally, check all registered invalid sequences
		if ( ( invalidSSNs.contains( ssn ) 
		 || ( invalidSSNs.contains( StringUtils.reverse(ssn))))) 
			return false;
		
		return isValid;
	}
	
	/**
	 * This method returns the descrambled version of a valid SSN.  This 
	 * method receives as input a SSN which has already been validated 
	 * to be numeric and 9 characters long.  Scramble algorithm is as 
	 * follows:
	 * 
	 * 	Char Position 0 – 2nd byte of primary SSN/Tax-ID
	 *	Char Position 1 – 9th byte of primary SSN/Tax-ID
	 *	Char Position 2 – 4th byte of primary SSN/Tax-ID
	 *	Char Position 3 – 7th byte of primary SSN/Tax-ID
	 * 	Char Position 4 – 6th byte of primary SSN/Tax-ID
	 * 	Char Position 5 – 5th byte of primary SSN/Tax-ID
	 * 	Char Position 6 – 8th byte of primary SSN/Tax-ID
	 *	Char Position 7 – 3rd byte of primary SSN/Tax-ID
	 *	Char Position 8 – 1st byte of primary SSN/Tax-ID 
	 * 
	 * @param scrambledSSN valid scrambled SSN 
	 * @return returns the descrambled SSN
	 * 
	 */
	public static String descrambleSSN( String scrambledSSN )
	{
		StringBuffer descrambledSSN = new StringBuffer();
		char [] scrambledSSNChars = scrambledSSN.toCharArray();

		descrambledSSN.append( scrambledSSNChars[8] )
					  .append( scrambledSSNChars[0] )
					  .append( scrambledSSNChars[7] )
					  .append( scrambledSSNChars[2] )
					  .append( scrambledSSNChars[5] )
					  .append( scrambledSSNChars[4] )
					  .append( scrambledSSNChars[3] )
					  .append( scrambledSSNChars[6] )
					  .append( scrambledSSNChars[1] );
		
//		log.debug("descrambled SSN is : " + descrambledSSN.toString() );
		
		return descrambledSSN.toString();
		
	}

	/**
	 *  Static utility method to scramble a valid SSN using the following scramble 
	 *  algorithm:
	 *  
	 * 	Char Position 0 – 2nd byte of primary SSN/Tax-ID
	 *	Char Position 1 – 9th byte of primary SSN/Tax-ID
	 *	Char Position 2 – 4th byte of primary SSN/Tax-ID
	 *	Char Position 3 – 7th byte of primary SSN/Tax-ID
	 * 	Char Position 4 – 6th byte of primary SSN/Tax-ID
	 * 	Char Position 5 – 5th byte of primary SSN/Tax-ID
	 * 	Char Position 6 – 8th byte of primary SSN/Tax-ID
	 *	Char Position 7 – 3rd byte of primary SSN/Tax-ID
	 *	Char Position 8 – 1st byte of primary SSN/Tax-ID 
	 * 
	 * @param ssn normal, valid SSN
	 * @return returns the scrambled SSN 
	 * 
	 */
	public static String scrambleSSN( String ssn )
	{
		StringBuffer scrambledSSN = new StringBuffer();
		char [] ssnChars = ssn.toCharArray();

		scrambledSSN.append( ssnChars[1] )
					.append( ssnChars[8] )
					.append( ssnChars[3] )
					.append( ssnChars[6] )
					.append( ssnChars[5] )
					.append( ssnChars[4] )
					.append( ssnChars[7] )
					.append( ssnChars[2] )
					.append( ssnChars[0] );
		
//		log.debug("scrambled SSN is : " + scrambledSSN.toString() );
		
		return scrambledSSN.toString();
		
	}
	public static String removeNonDigits( String ssn )
	{
		String newSSN = "";
		for (int i = 0; i < ssn.length(); i ++) 
		{
			if (ssn.charAt(i) != '-') newSSN += ssn.charAt(i);
		}
		return newSSN;		
	}
	
}
