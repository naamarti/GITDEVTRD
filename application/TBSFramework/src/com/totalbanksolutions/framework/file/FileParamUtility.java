package com.totalbanksolutions.framework.file;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.dataservice.util.DataServiceManager;
import com.totalbanksolutions.framework.util.AppUtility;

/**
 * =================================================================================================
 * 
 * Modified:  05 Oct 2011 VC #1022: Add DI Customer Balances Summary report (RG#101) to Universal Reports Checklist
 *            05 Oct 2011 VC #986:  Add DMS checklist steps for each S.I. in DBTCA to generate Over FDIC reports
 *            13 Oct 2011 NAM      : Fixed next Sequence Number not working when directory wasn't present
 *            25 Jan 2012 NAM #1230: FTP_Archive directory added
 *            01 Mar 2012 NAM #1206: Determine CommsDirectory added for request and Check steps
 *            22 Mar 2012 NAM #1444: Changed the way we assign Sequence number
 *            19 Apr 2012 NAM #1520: updated for confirmation of sent ftp files
 *            30 Jul 2012 NAM #1795: updated for FTPArchive_Backup directory
 *            25 Sep 2012 NAM #1869: updated for new Working and Archive dir substututions
 *               
 * =================================================================================================
 */
public class FileParamUtility 
{
	protected static final Log log = LogFactory.getLog(FileParamUtility.class);

	public static void main(String[] args)
	{
		DataService ds = new DataServiceManager().getDataService();
		
//		String test = "TestFileName.${IsMonthEnd,MONTHEND,MIDMONTH}.TXT";
//		String trueTest = substituteIsMonthEndParameters(true, test);
//		String falseTest = substituteIsMonthEndParameters(false, test);
//		System.out.println("IsMonthEnd = " + trueTest);
//		System.out.println("IsMonthEnd = " + falseTest);

		String test = "${ProgramArchiveDir}${BusinessDate=yyyy/yyyy.MM/MMMMM dd}/";
		String result = FileParamUtility.substituteBusinessDateParameters( new Date(), test);
		
		String test2 = "${ProgramWorkingDir}Files.ToSourceInstitution/";
		result = FileParamUtility.substituteProgramWorkingDirPrimaryParameters(ds, 31, test2);
		
	}
	
	public static String substituteBusinessDateParameters(Date businessDate, String input)
	{
		String output = "";
		if(businessDate != null && input != null && input.length() > 0)
		{
			output = new String(input);
			String[] params = StringUtils.substringsBetween(input, "${BusinessDate=", "}");
			if(params != null && params.length > 0)
			{
				for(String datePattern : params)
				{
					//log.debug("***** " + datePattern);
					String dateString = DateFormatUtils.format(businessDate, datePattern);
					output = StringUtils.replaceOnce(output, "${BusinessDate=" + datePattern + "}", dateString);
				}
				//log.debug("**** OUTPUT= " + output);
			}
		}
		return output;
	}

	public static String substitutePreviousBusinessDateParameters(Date previousBusinessDate, String input)
	{
		String output = "";
		if(previousBusinessDate != null && input != null && input.length() > 0)
		{
			output = new String(input);
			String[] params = StringUtils.substringsBetween(input, "${PreviousBusinessDate=", "}");
			if(params != null && params.length > 0)
			{
				for(String datePattern : params)
				{
					//log.debug("***** " + datePattern);
					String dateString = DateFormatUtils.format(previousBusinessDate, datePattern);
					output = StringUtils.replaceOnce(output, "${PreviousBusinessDate=" + datePattern + "}", dateString);
				}
				//log.debug("**** OUTPUT= " + output);
			}
		}
		return output;
	}

	public static String substituteCurrentDateParameters(Date currentDate, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			String[] params = StringUtils.substringsBetween(input, "${CurrentDate=", "}");
			if(params != null && params.length > 0)
			{
				for(String datePattern : params)
				{
					String dateString = DateFormatUtils.format(currentDate, datePattern);
					output = StringUtils.replaceOnce(output, "${CurrentDate=" + datePattern + "}", dateString);
				}
			}
		}
		return output;
	}

	public static String substituteProgramFolderNameParameters(DataService ds, long programId, String input)
	{
		String programFolderName = null;
		String programFolderNameVarToReplace = "${ProgramFolderName}";

		// if specific program specified, get folder for that program
		if( input.contains("${ProgramFolderName="))
		{
			String programName = StringUtils.substringBetween(input, "${ProgramFolderName=", "}"); 
			programId = ds.util.getProgramDataSet().getIdByName(programName);
			programFolderName = ds.util.getProgramFolderName(programId);
			programFolderNameVarToReplace = "${ProgramFolderName=" + programName + "}";
		}
		else
		{
			programFolderName = ds.util.getProgramFolderName(programId);
		}
		
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, programFolderNameVarToReplace, programFolderName);
		}
		return output;
	}
	
	public static String substituteProgramCodeParameters(DataService ds, long programId, String input)
	{
		String programCode = null;
		String programCodeVarToReplace = "${ProgramCode}";

		// if specific program specified, get folder for that program
		if( input.contains("${ProgramCode="))
		{
			String programName = StringUtils.substringBetween(input, "${ProgramCode=", "}"); 
			programId = ds.util.getProgramDataSet().getIdByName(programName);
			programCode = ds.util.getProgramFolderName(programId);
			programCodeVarToReplace = "${ProgramCode=" + programName + "}";
		}
		else
		{
			programCode = ds.util.getProgramCode(programId);
		}
		
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, programCodeVarToReplace, programCode);
		}
		return output;
	}
	
	public static String substituteSourceInstitutionFolderNameParameters(String sourceFolderName, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			if(sourceFolderName != null && sourceFolderName.length() > 0)
			{
				output = StringUtils.replace(output, "${SourceInstFolderName}", sourceFolderName);
			}
		}
		return output;
	}

	public static String substituteProgramWorkingDirPrimaryParameters(DataService ds, long programId, String input)
	{
		String programWorkingDir = null;
		String programWorkingDirVarToReplace = "${ProgramWorkingDir}";
		
		if( input.contains("${ProgramWorkingDir="))
		{
			String programName = StringUtils.substringBetween(input, "${ProgramWorkingDir=", "}"); 
			programId = ds.util.getProgramDataSet().getIdByName(programName);
			programWorkingDirVarToReplace = "${ProgramWorkingDir=" + programName + "}";

			programWorkingDir = ds.util.getProgramWorkingDirPrimary(programId);
			
			// must pass the program variable on if the program variable exists
			if( programWorkingDir.contains("${ProgramFolderName}"))
			{
				StringBuffer newDir = new StringBuffer();
				newDir.append( StringUtils.substringBefore(programWorkingDir, "${ProgramFolderName}") )
					  .append("${ProgramFolderName=")
					  .append(programName)
					  .append("}")
					  .append( StringUtils.substringAfter(programWorkingDir, "${ProgramFolderName}") );
				
				programWorkingDir = newDir.toString();
			}
		}
		else 
		{
			programWorkingDir = ds.util.getProgramWorkingDirPrimary(programId);
		}
		
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, programWorkingDirVarToReplace, programWorkingDir);
		}
		return output;
	}

	public static String substituteFTPArchiveDirParameters(String ftpArchiveDir, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${SweepFileMirrorDir}", ftpArchiveDir);
		}
		return output;
	}
	
	public static String substituteFTPArchiveDirBackupParameters(String ftpArchiveDirBackup, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${SweepFileMirrorDirBackup}", ftpArchiveDirBackup);
		}
		return output;
	}
	
	
	public static String substituteProgramTempDirParameters(String programTempDir, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${ProgramTempDir}", programTempDir);
		}
		return output;
	}
	

	public static String substituteProgramArchiveDirPrimaryParameters(String programArchiveDirPrimary, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${ProgramArchiveDir}", programArchiveDirPrimary);
		}
		return output;
	}

	public static String substituteProgramArchiveDirSecondary01Parameters(String programArchiveDirSecondary01, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${ProgramArchiveDirSecondary01}", programArchiveDirSecondary01);
		}
		return output;
	}
	
	public static String substituteProgramArchiveDirSecondary02Parameters(String programArchiveDirSecondary02, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${ProgramArchiveDirSecondary02}", programArchiveDirSecondary02);
		}
		return output;
	}
	
	
	public static String substituteDepositInstitutionNameParameters(String depositInstitutionName, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${DepositInstitutionName}", depositInstitutionName);
		}
		return output;
	}
	
	public static String substituteSettlementWireOutputDirParameters(String settlementWireOutputDir, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${SettlementWireOutputDir}", settlementWireOutputDir);
		}
		return output;
	}
	
	public static String substituteSourceInstitutionInputDirParameters(String sourceInstitutionInputDir, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${SourceInstInputDir}", sourceInstitutionInputDir);
		}
		return output;
	}

	public static String substituteSourceInstitutionOutputDirParameters(String sourceInstitutionOutputDir, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${SourceInstOutputDir}", sourceInstitutionOutputDir);
		}
		return output;
	}

	public static String substituteProcessorInputDirParameters(String processorInputDir, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${ProcessorInputDir}", processorInputDir);
		}
		return output;
	}

	public static String substituteProcessorOutputDirParameters(String processorOutputDir, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${ProcessorOutputDir}", processorOutputDir);
		}
		return output;
	}

	public static String substituteReportsOutputDirParameters(String reportsOutputDir, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${ReportsOutputDir}", reportsOutputDir);
		}
		return output;
	}

	public static String substituteStatementProviderOutputDirParameters(String statementProviderOutputDir, String input)
	{
		String output = "";
		if(input != null && input.length() > 0)
		{
			output = new String(input);
			output = StringUtils.replace(output, "${StatementProviderOutputDir}", statementProviderOutputDir);
		}
		return output;
	}

	public static String substituteLateFileParameters(boolean isLateFile, String fileName)
	{
		String newFileName = "";
		if(fileName != null && fileName.length() > 0)
		{
			newFileName = new String(fileName);
			String[] params = StringUtils.substringsBetween(fileName, "${LateFile=", "}");
			if(params != null && params.length > 0)
			{
				for(String lateFilePattern : params)
				{
					//log.debug("***** lateFilePattern= " + lateFilePattern);
					String lateFileString = "";
					if(isLateFile)
					{
						//log.debug("***** isLateFile");
						lateFileString = lateFilePattern;
					}
					newFileName = StringUtils.replaceOnce(newFileName, "${LateFile=" + lateFilePattern + "}", lateFileString);
				}
			}
		}
		return newFileName;
	}

	/*
	 * Expression:
	 *     ${IsMonthEnd, valueIfTrue, valueIfFalse}
	 *     
	 * Examples:
	 *     FileName:                     TestFile.${IsMonthEnd, MONTHEND, MIDMONTH}.TXT
	 *       When IsMonthEnd = True:     TestFile.MONTHEND.TXT
	 *       When IsMonthEnd = False:    TestFile.MIDMONTH.TXT
	 *         
	 */
	public static String substituteIsMonthEndParameters(boolean isMonthEnd, String fileName)
	{
		String newFileName = "";
		if(fileName != null && fileName.length() > 0)
		{
			newFileName = new String(fileName);
			String[] isMonthEndParams = StringUtils.substringsBetween(fileName, "${IsMonthEnd,", "}");
			if(isMonthEndParams != null && isMonthEndParams.length > 0)
			{
				for(String params : isMonthEndParams)
				{
					log.debug(params);
					String[] values = StringUtils.split(params, ",");
					String trueValue = values[0];
					String falseValue = values[1];
					log.debug("trueValue=" + trueValue + "; falseValue = " + falseValue);
					String replacementString = isMonthEnd ? trueValue : falseValue;
					log.debug("replacementString=" + replacementString);
					newFileName = StringUtils.replaceOnce(newFileName, "${IsMonthEnd," + params + "}", replacementString);
				}
			}
		}
		return newFileName;
	}

	public static String substituteLatestSequenceNumberParameters(String dir, String fileName)
	{
		String newFileName = fileName;
		String sequenceTag = "${SequenceNumber}";
		long latestSequenceNumber = -1;
		log.debug("DIR "+dir+" | "+fileName);
		if(fileName.contains(sequenceTag))
		{
			String stringBeforeTag = StringUtils.substringBefore(fileName, sequenceTag);
			String stringAfterTag = StringUtils.substringAfter(fileName, sequenceTag);
			log.debug("${LatestSequenceNumber}: stringBeforeTag - " + stringBeforeTag + ", stringAfterTag - " + stringAfterTag);
			
			File f = new File(dir);
			String[] fileList = f.list();
			if(fileList != null)
			{
				for(String fileToCheck : fileList)
				{
					log.debug("${LatestSequenceNumber} File To Check: " + fileToCheck);
					if(StringUtils.startsWithIgnoreCase(fileToCheck, stringBeforeTag) && StringUtils.endsWithIgnoreCase(fileToCheck, stringAfterTag))
					{
						String sequenceNumber = StringUtils.substringBetween(fileToCheck.toUpperCase(), stringBeforeTag.toUpperCase(), stringAfterTag.toUpperCase());

						if(sequenceNumber.equals("")&& stringAfterTag.equals("")){
							sequenceNumber = fileToCheck.substring(stringBeforeTag.length());

						}
						if(StringUtils.isNumeric(sequenceNumber) && Long.parseLong(sequenceNumber) > latestSequenceNumber)
						{
							latestSequenceNumber = Long.parseLong(sequenceNumber);
							newFileName = fileToCheck;
						}
					}
				}
			}
		}
		return newFileName;
	}
	
	public static String substituteNextSequenceNumberParameters(String dir, String fileName)
	{
		String newFileName = fileName;
		String sequenceTag = "${SequenceNumber}";
		long nextSequenceNumber = -1;
		if(fileName.contains(sequenceTag))
		{
			String stringBeforeTag = StringUtils.substringBefore(fileName, sequenceTag);
			String stringAfterTag = StringUtils.substringAfter(fileName, sequenceTag);
			log.debug("${NextSequenceNumber}: stringBeforeTag - " + stringBeforeTag + ", stringAfterTag - " + stringAfterTag);
			
			File f = new File(dir);
			String[] fileList = f.list();
			if(fileList != null)
			{
				String sequenceNumber = "";
				for(String fileToCheck : fileList)
				{
					log.debug("${NextSequenceNumber} File To Check: " + fileToCheck);
					if(StringUtils.startsWithIgnoreCase(fileToCheck, stringBeforeTag) && StringUtils.endsWithIgnoreCase(fileToCheck, stringAfterTag))
					{
						sequenceNumber = StringUtils.substringBetween(fileToCheck.toUpperCase(), stringBeforeTag.toUpperCase(), stringAfterTag.toUpperCase());
						log.debug("${SequenceNumber} found: " + sequenceNumber);
						if(sequenceNumber.equals("")&& stringAfterTag.equals("")){
							sequenceNumber = fileToCheck.substring(stringBeforeTag.length());

						}
						if(StringUtils.isNumeric(sequenceNumber) && Long.parseLong(sequenceNumber) > nextSequenceNumber)
						{
							nextSequenceNumber = Long.parseLong(sequenceNumber);
							newFileName = fileToCheck;
						}
					}
				}
				
				Long	lSeq = (nextSequenceNumber + 1);
				sequenceNumber = lSeq.toString();

				newFileName = stringBeforeTag + sequenceNumber + stringAfterTag;
			}
			else
			{
				//directory doesn't exist, so we are safe starting at sequence #1
				newFileName = stringBeforeTag + "1" + stringAfterTag;
			}
		}

		return newFileName;
	}

	public static String getDirectoryWithProperSuffix(String dir)
	{
		String properDir = dir;
		if(dir != null && dir.length() > 0)
		{
			if(!dir.endsWith("/"))
			{
				properDir = dir + "/";
			}
		}
		return properDir;
	}

	public static void createDirectoryIfNotExists(String dirName)
	{
		File dir = new File(dirName);
		if(!dir.exists())
		{
//			if(!dir.mkdir())
//			{
//				throw new RuntimeException("Unable to create directory, '" + dirName + "'.");
//			}
			File newDir;
			try
			{
				newDir = new File(dirName);
				FileUtils.forceMkdir(newDir);
			}
			catch (Exception e)
			{
				throw new RuntimeException("Unable to create directory, '" + dirName + "'.");
			}
			finally
			{
				newDir = null;
			}
		}
	}

	public static boolean directoryExists(String dirName)
	{
		File dir = new File(dirName);
		if(dir.exists())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static String convertUnixPathToWindows(String filePath)
	{
		String path = filePath;
		path = StringUtils.replace(path, "/", "\\");
		return path;
	}

	public static String determineCommsDir(String FileDir)
	{
		String commsDir = "";
		if(FileDir != null && FileDir.length() > 0)
		{
			commsDir = new String(FileDir);
			commsDir = StringUtils.replace(commsDir, "DataFiles", "Communication");
		}
		return commsDir;
	}

}
