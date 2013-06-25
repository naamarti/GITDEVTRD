package com.totalbanksolutions.framework.util;

//import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;

//import sun.misc.BASE64Encoder;

public class EncryptionUtility
{
	private static EncryptionUtility instance;

	private EncryptionUtility()
	{
	}

	public synchronized String encrypt(String plaintext)
	//throws SystemUnavailableException
	{
		String hash = "";
		try
		{
			MessageDigest md = null;
			md = MessageDigest.getInstance("SHA");
			md.update(plaintext.getBytes("UTF-8"));
			byte raw[] = md.digest();

			hash = byteArrayToHexString(raw);
			
			//hash = (new BASE64Encoder()).encode(raw);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hash;
	}
  
  	public static synchronized EncryptionUtility getInstance()
	{
  		if(instance == null)
  		{
  			instance = new EncryptionUtility(); 
  		} 
  		return instance;
	}

	/**
	* Convert a byte[] array to readable string format. This makes the "hex" readable!
	* @return result String buffer in String format 
	* @param in byte[] buffer to convert to string format
	*/
	static String byteArrayToHexString(byte in[]) 
	{
		byte ch = 0x00;
		int i = 0; 
		if (in == null || in.length <= 0)
			return null;
	        
		String pseudo[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
		StringBuffer out = new StringBuffer(in.length * 2);
	    
		while (i < in.length) 
		{
			ch = (byte) (in[i] & 0xF0);		// Strip off high nibble
			ch = (byte) (ch >>> 4);			// shift the bits down
			ch = (byte) (ch & 0x0F);		// must do this is high order bit is on!
			out.append(pseudo[ (int) ch]);	// convert the nibble to a String Character
			ch = (byte) (in[i] & 0x0F); 	// Strip off low nibble 
			out.append(pseudo[ (int) ch]);	// convert the nibble to a String Character
			i++;
		}
		String rslt = new String(out);
		return rslt;
	}
	
	public static void main(String ars[])
	{
		String textPassword = "5p33d";
		String password = EncryptionUtility.getInstance().encrypt(textPassword);
		System.out.println(textPassword + " = " + password);
		System.out.println();
	
		textPassword = "tbs";
		password = EncryptionUtility.getInstance().encrypt(textPassword);
		System.out.println(textPassword + " = " + password);
		System.out.println();

		textPassword = "VCatrini1";
		password = EncryptionUtility.getInstance().encrypt(textPassword);
		System.out.println(textPassword + " = " + password);
		System.out.println();

		/*
		VCatrini1 = 1B77BA92737B1B98E03D75593EBDA216F7CA54FB
		abc = A9993E364706816ABA3E25717850C26C9CD0D89D
		abc123 = 6367C48DD193D56EA7B0BAAD25B19455E529F5EE
		123456789 = F7C3BC1D808E04732ADF679965CCC34CA7AE3441
		h3ll0 = BD82A34CCD10354736FC4E08EEAF0D939AAB658E
		5p33d = F482A1F740C22EE38A3F2AE387C4518E9C4441EF
		TesTinG = 19AC619F1CC56C9D1540EDB11763B447C05C1047
		*/		 
	}

}
