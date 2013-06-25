package com.totalbanksolutions.framework.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.DigestInputStream;
import java.util.Formatter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.DailyProcessFile;

/**
 * =================================================================================================
 * 
 * Modified:  01 Mar 2012 NM #1206: Created
 *
 * =================================================================================================
 */

public class FileVerificationUtility {
	
	protected static final Log log = LogFactory.getLog(FileVerificationUtility.class);
	
	
	public boolean validateChecksum(String transferFile, String checksum)
	{
		try{
			boolean checksumSuccess = false;
			
			if ( calculateChecksum(transferFile).equalsIgnoreCase(checksum) )
				checksumSuccess = true;
			return checksumSuccess;
		}
		catch (Exception e)		{
			throw new RuntimeException(e);
		}
	}
	
	public String calculateChecksum(String transferFile)
	{
		String checksum;
		try{
	         MessageDigest md5  = MessageDigest.getInstance("MD5");        
		     // MessageDigest sha1 = MessageDigest.getInstance("SHA1");	        
	         
	         checksum = calculateHash(md5, transferFile);
	         log.info("MD5 for " +transferFile+" is: "+ checksum);
		}
		catch (Exception e)		{
			throw new RuntimeException(e);
		}
		return checksum;
	}
	
	/////////////////////////////////////////////////////////////////////////////
	// Function: calculateHash() 
	// Author:   PPOW - http://www.javablogging.com/sha1-and-md5-checksums-in-java/
	// Description: calculate MD5 or SHA1 checksum for a file
	/////////////////////////////////////////////////////////////////////////////
    public static String calculateHash(MessageDigest algorithm, String fileName) 
    {
    	byte[] hash;
    	try{
	        FileInputStream     fileStream = new FileInputStream(fileName);
	        BufferedInputStream bufFileStream = new BufferedInputStream(fileStream);
	        DigestInputStream   digestInputStream = new DigestInputStream(bufFileStream, algorithm);
	
	        // read the file and update the hash calculation
	        while (digestInputStream.read() != -1);
	
	        // get the hash value as byte array
	        hash = algorithm.digest();
    	}
		catch (Exception e)		{
			throw new RuntimeException("Could not Calculate checksum hash on "+fileName +". ERROR: "+ e);
		}

        return byteArray2Hex(hash);
    }

    private static String byteArray2Hex(byte[] hash) 
    {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

	public boolean copyFilesWithRename_NoError(DailyProcessFile f)
	{
		try
		{
			boolean preserveFileDate = true;
			String inputFileDir = f.getInputFileDir();
			String inputFileName = f.getInputFileName();
			String outputFileDir = f.getOutputFileDir();
			String outputFileName = f.getOutputFileName();

			File inputFile = new File(inputFileDir + inputFileName);
			File outputFile = new File(outputFileDir + outputFileName);
			
			// Copy File with "TEMP_" prefix
			File tempOutputFile = new File(outputFileDir + "TEMP_" + outputFileName);
			FileUtils.copyFile(inputFile, tempOutputFile, preserveFileDate);
			
			// Move (Rename) file to remove "TEMP_" prefix
			FileUtils.deleteQuietly(outputFile);
			FileUtils.moveFile(tempOutputFile, outputFile);

			inputFile = null;
			tempOutputFile = null;
			outputFile = null;
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
