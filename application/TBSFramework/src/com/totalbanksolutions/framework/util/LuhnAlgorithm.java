package com.totalbanksolutions.framework.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Test harness for an implementation of the Luhn algorithm that checks the
 * validity of an account number.
 */
public class LuhnAlgorithm 
{

	/**
	 * Main entry point for the test harness.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		String validNum = null;
//		validNum = LuhnAlgorithm.getValidAccountNumber("098110491X");		
//		validNum = LuhnAlgorithm.getValidAccountNumber("101409741X");
//		validNum = LuhnAlgorithm.getValidAccountNumber("300022991X");
		validNum = LuhnAlgorithm.getValidAccountNumber("3AA000091X");
		System.out.println(validNum);
	}

	/**
	 * Checks whether a string of digits is a valid acount number according
	 * to the Luhn algorithm.
	 *
	 * 1. Starting with the second to last digit and moving left, double the
	 *    value of all the alternating digits. For any digits that thus become
	 *    10 or more, add their digits together. For example, 1111 becomes 2121,
	 *    while 8763 becomes 7733 (from (1+6)7(1+2)3).
	 *
	 * 2. Add all these digits together. For example, 1111 becomes 2121, then
	 *    2+1+2+1 is 6; while 8763 becomes 7733, then 7+7+3+3 is 20.
	 *
	 * 3. If the total ends in 0 (put another way, if the total modulus 10 is
	 *    0), then the number is valid according to the Luhn formula, else it is
	 *    not valid. So, 1111 is not valid (as shown above, it comes out to 6),
	 *    while 8763 is valid (as shown above, it comes out to 20).
	 *
	 * @param number the account number to validate.
	 * @return true if the number is valid, false otherwise.
	 */
	private static boolean isValidNumber(String number) {
		int sum = 0;

		boolean alternate = false;
		for (int i = number.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(number.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}

		return (sum % 10 == 0);
	}

	/**
	 * Given an account number in the form of 9 digits plus an 'X' character as
	 * the suffix (e.g. 101409741X), this method will return the entire Luhn 
	 * validated account number, deriving the final digit to satisfy the algorithm.
	 * 
	 * @param number the 10 digit account number with the last digit masked 
	 * 				 with an 'X' character. 
	 * @return the String representation of the entire valid account number with
	 * 		   the final digit derived using the Luhn algorithm.  NULL if no 
	 * 		   valid account number can be derived.
	 */
	public static String getValidAccountNumber( String number )
	{
		String subsitute = substituteAlphaNumeric(number);		
		// first, remove the 'X' char
		String partialNum = subsitute.substring(0, 9);		
		String validNum = null;

		// test all possible combinations, and return the valid combo
		for(int i = 0; i <= 9; i++)
		{
			String testNum = partialNum + new Integer(i).toString();
			
			if( LuhnAlgorithm.isValidNumber(testNum) )
			{
				validNum = testNum;
				break;
			}
		}
		String value = number.substring(0, 9) + validNum.charAt(validNum.length() - 1);
		return value;
	}
	
	public static String substituteAlphaNumeric(String number){
		Map<String,Integer> alphaMap = new HashMap<String, Integer>();
		alphaMap.put("A",1);
		alphaMap.put("B",2);
		alphaMap.put("C",3);alphaMap.put("D",4);alphaMap.put("E",5);
		alphaMap.put("F",6);alphaMap.put("G",7);alphaMap.put("H",8);
		alphaMap.put("I",9);alphaMap.put("J",1);alphaMap.put("K",2);
		alphaMap.put("L",3);alphaMap.put("M",4);alphaMap.put("N",5);
		alphaMap.put("O",6);alphaMap.put("P",7);alphaMap.put("Q",8);
		alphaMap.put("R",9);alphaMap.put("S",2);alphaMap.put("T",3);
		alphaMap.put("U",4);alphaMap.put("V",5);alphaMap.put("W",6);
		alphaMap.put("X",7);alphaMap.put("Y",8);alphaMap.put("Z",9);
		
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < number.length(); i++){
			char chars = number.charAt(i);			
			Integer value = alphaMap.get(new String(""+chars+""));
			if(value != null){
				buffer.append(String.valueOf(value));
			}else{
				buffer.append(chars);
			}			
		}
		return buffer.toString();
		
	}

}