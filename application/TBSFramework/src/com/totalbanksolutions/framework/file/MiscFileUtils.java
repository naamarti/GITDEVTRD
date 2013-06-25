package com.totalbanksolutions.framework.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.DigestInputStream;
import java.util.Formatter;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.DailyProcessFile;
/**
 * =================================================================================================
 * 
 * Modified:  01 Mar 2012 NM #1206: added Checksum functions for request and Check steps
 *
 * =================================================================================================
 */
public class MiscFileUtils {
	
	protected static final Log log = LogFactory.getLog(MiscFileUtils.class);
	
	public int lineCount(String filename)
	{
		try{
		    InputStream is = new BufferedInputStream(new FileInputStream(filename));
		    byte[] c = new byte[1024];
		    int count = 0;
		    int readChars = 0;
		    while ((readChars = is.read(c)) != -1) {
		        for (int i = 0; i < readChars; ++i) {
		            if (c[i] == '\n')
		                ++count;
		        }
		    }
		    return count;
		}
		catch (Exception e)		{
			throw new RuntimeException(e);
		}
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
			inputFileName = FileParamUtility.substituteLatestSequenceNumberParameters(inputFileDir, inputFileName);
			if(outputFileName.contains("${LatestSequenceNumber}")) outputFileName = inputFileName;
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
