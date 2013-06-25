package com.totalbanksolutions.framework.dataservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.data.EnvelopesDataSet;
import com.totalbanksolutions.framework.bean.database.data.RG_ReportDataSet;
import com.totalbanksolutions.framework.bean.database.data.ReportTypesDataSet;
import com.totalbanksolutions.framework.bean.database.table.DailyProcessType;
import com.totalbanksolutions.framework.bean.database.table.Envelope_ReportType_Relationship;
import com.totalbanksolutions.framework.bean.database.table.Envelopes;
import com.totalbanksolutions.framework.bean.database.table.Program;
import com.totalbanksolutions.framework.bean.database.table.RG_Report;
import com.totalbanksolutions.framework.bean.database.table.ReportTypes;
import com.totalbanksolutions.framework.bean.database.view.ViewProgramBank;
import com.totalbanksolutions.framework.bean.database.view.ViewRBCActivitySummaryByDI;
import com.totalbanksolutions.framework.bean.util.BeanSet;
import com.totalbanksolutions.framework.bean.web.Report;
import com.totalbanksolutions.framework.cache.CacheDataRetrievalMethod;
import com.totalbanksolutions.framework.cache.CacheManager;
import com.totalbanksolutions.framework.dao.ReportsDAO;
import com.totalbanksolutions.framework.dao.jdbc.ReportsJDBC;
import com.totalbanksolutions.framework.dataservice.util.DataCacheType;
import com.totalbanksolutions.framework.enums.ReportDocType;
import com.totalbanksolutions.framework.file.FileParamUtility;
import com.totalbanksolutions.framework.file.IOUtility;

/**
 * =================================================================================================
 * Created:   VC
 * Modified:  10 Aug 2011 VC #984:  Direct Settlement - Add DMS checklist step to generate direct settlement report
 *            05 Oct 2011 VC #1022: Add DI Customer Balances Summary report (RG#101) to Universal Reports Checklist
 *            07 Nov 2011 NAM #1051 - Pulling Report Type from DB to make the email senders generic
 *            06 Feb 2012 NAM #1274 - added new fee reports and changed RBC wire movement report to use new reportID 
 *									  only reference repID 118 for NONAFFILIATED and 
 *                                                   repID 116 for AFFILIATED reports
 *            23 Apr 2012 VC   #966: All Programs / Bank Centric checklist - add First Niagara quarterly report
 *            29 Apr 2012 VC  #1549: Add step to Universal Checklist - Recon Open Items report
 *            23 Aug 2012 VC  #1826: Add DMS Universal Checklist step to generate Bank rate report 
 *            23 Aug 2012 VC  #1827: Add DMS checklist step to Universal checklist for generation of Daily Summary report
 *            24 Aug 2012 NAM #1838: find reports by envelope 
 *            25 Sep 2012 NAM #1869: Switch to Primary and Secondary Processing Directory types
 * 
 * =================================================================================================
 */
public class ReportsDataService 
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());
    private ReportsDAO reportsDAO = null;
    private CacheManager cacheManager = null;
    private DataService ds = null;
    
    public ReportsDataService (DataSource dataSource, CacheManager cacheManager, DataService ds)
    {
    	this.reportsDAO = new ReportsJDBC(dataSource);
    	this.cacheManager = cacheManager;    	
    	this.ds = ds;
    }

	public void close()
	{
		this.reportsDAO.close();
		this.reportsDAO = null;
		this.cacheManager = null;
		this.ds = null;
	}

	//============================================================================
	//
	// Reports - Shared
	//
	//============================================================================
    public List<ViewRBCActivitySummaryByDI> getRBCActivitySummaryByDI(String databaseName, Date businessDate, long sourceInstitutionId)
    {
    	return reportsDAO.getRBCActivitySummaryByDI(databaseName, businessDate, sourceInstitutionId);
    }
    
    public List<ViewProgramBank> getProgramBanksForCallReports( Date businessDate )
    {
    	return reportsDAO.getProgramBanksForCallReports( businessDate );
    }
    
	//============================================================================
	//
	// Reports - Email Sender
	//
	//============================================================================
    public List<ReportTypes> getReportTypes(long reportType, long stepId, Date businessDate)
    {
    	List<ReportTypes> fileTypeList = reportsDAO.getReportTypes(reportType);
		if(fileTypeList != null && fileTypeList.size() > 0)
		{
			for(ReportTypes f : fileTypeList)
			{
				String FileDir = f.getFileDirectory();
				String FileName = f.getFileName();

				FileDir = FileParamUtility.getDirectoryWithProperSuffix(FileDir);
				FileDir = this.ds.util.substituteFileParameters(FileDir, stepId, businessDate);
				
				FileName = this.ds.util.substituteFileParameters(FileName, stepId, businessDate);
				FileName = FileParamUtility.substituteLatestSequenceNumberParameters(FileDir, FileName);
				f.setFileDirectory( FileDir );
				f.setFileName( FileName );
			}
		}
		return fileTypeList;
    }

    public List<Envelope_ReportType_Relationship> getReportIDsByEnvelope(long envelopeId)
	{
    	List<Envelope_ReportType_Relationship> reportIdList = reportsDAO.getReportIDsByEnvelope(envelopeId);
    	
		return reportIdList;    	
	}
    
	@SuppressWarnings("unchecked")
	public BeanSet<ReportTypes> getReportTypesDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new ReportTypesDataSet(reportsDAO.getReportTypesList());
    		}
    	};
    	return (BeanSet<ReportTypes>)cacheManager.get(DataCacheType.REPORT_TYPE_LIST, retrieveMethod);
    }

    public long getReportTypeId(String reportName)
    {
    	long id = this.getReportTypesDataSet().getIdByName(reportName);
    	return id;
    }
    
	@SuppressWarnings("unchecked")
	public BeanSet<Envelopes> getEnvelopesDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new EnvelopesDataSet(reportsDAO.getEnvelopesList());
    		}
    	};
    	return (BeanSet<Envelopes>)cacheManager.get(DataCacheType.ENVELOPE_LIST, retrieveMethod);
    }

    public long getEnvelopesId(String envelopeName)
    {
    	long id = this.getEnvelopesDataSet().getIdByName(envelopeName);
    	return id;
    }
    

    
    //============================================================================
	//
	// Reports - RG Report Generator
	//
	//============================================================================
    /**
     * This is a stub/temporary implementation for the eventual database version of this method which will lookup the 
     * Report Generator's report ID for a given source institution and process type.  The process type will map to a 
     * report category ( e.g. Sweep Report ).  Perhaps a function will be created by Adam which will return the appropriate
     * report ID given the two input parameters mentioned.  For now, this will suffice.
     * 
     * @param processType 			- String value representation of the DailyProcessType enum
     * @param programID 			- Program ID
     * @param sourceInstitutionID 	- Source Institution ID
	 * @param isLateProcess 		- Is this report a LATE processing report or not
     * @return Returns the ReportGenerator report ID for a given source institution, report type(process type)/category,
     * 		   and late/early processing indicator
     */
    public long getReportGeneratorReportID( String processType, long programId, long sourceInstitutionId, boolean isLateProcess )
    {
    	long reportId 					= 0;
    	String sourceInstitutionCode 	= this.ds.util.getSourceInstitutionInternalCode(sourceInstitutionId);
    	String programCode 				= this.ds.util.getProgramInternalCode(programId);
    	
		if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_ACCOUNT_STRATIFICATION.getId()))
		{
			reportId = 110;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_ACTIVITY_DETAIL.getId()))
		{
			if(programCode.equalsIgnoreCase(Program.Values.RBC.getId()))
			{
				if(isLateProcess)
				{
					reportId = 54;
				}
				else
				{
					reportId = 52;
				}
			}
			else
			{
				reportId = 85;				
			}
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_ACTIVITY_EXCEPTIONS.getId()))
		{
			if(programCode.equalsIgnoreCase(Program.Values.RBC.getId()))
			{
				if(isLateProcess)
				{
					reportId = 55;
				}
				else
				{
					reportId = 53;
				}
			}
			else
			{
				reportId = 95;				
			}			
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_ACTIVITY_SUMMARY.getId()))
		{
			reportId = 91;				
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_ALL_PROGRAM_SUMMARY.getId()))
		{
			reportId = 124;				
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_BALANCE_SUMMARY_BY_DI.getId()))
		{
			if(programCode.equalsIgnoreCase(Program.Values.RBC.getId()))
			{
				if(isLateProcess)
				{
					reportId = 49;
				}
				else
				{
					reportId = 56;
				}
			}
			else
			{
				reportId = 83;
			}
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_BALANCE_SUMMARY_BY_PRODUCT.getId()))
		{
			if(programCode.equalsIgnoreCase(Program.Values.RBC.getId()))
			{
				if(isLateProcess)
				{
					reportId = 48;
				}
				else
				{
					reportId = 47;
				}
			}
			else
			{
				reportId = 94;
			}
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_BAL_SUM_BY_PRD_BANK_DETAILS.getId()))
		{
			reportId = 100;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_BANK_BALANCES.getId()))
		{
			reportId = 50;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_BANK_BALANCES_FOR_RECON.getId()))
		{
			reportId = 88;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_DEPOSIT_ACTIVITY.getId()))
		{
			if(programCode.equalsIgnoreCase(Program.Values.RBC.getId()))
			{
				reportId = 63;
			}
			else
			{
				reportId = 87;
			}
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_DI_CUSTOMER_BALANCES_DETAIL.getId()))
		{
			reportId = 107;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_DI_CUSTOMER_BALANCES_SUMMARY.getId()))
		{
			reportId = 101;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_DI_TRANCHE_CAPACITY.getId()))
		{
			reportId = 123;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_DIRECT_SETTLEMENT_WIRE_ACTIVITY.getId()))
		{
			reportId = 114;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_FEES_RBC_AFFILIATED_BANKS.getId()))
		{
			reportId = 116;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_FEES_RBC_NONAFFILIATED_BANKS.getId()))
		{
				reportId = 118;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_FEES_WIRE_MOVEMENT.getId()))
		{
				reportId = 119;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_FEES_SI.getId()))
		{
			reportId = 90;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_MMDA_NOW_TICK_COUNT.getId()))
		{
			if(programCode.equalsIgnoreCase(Program.Values.RBC.getId()))
			{
				reportId = 62;
			}
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_MMDA_OFFSET_WINDOW_INSTR.getId()))
		{
			if(programCode.equalsIgnoreCase(Program.Values.RBC.getId()))
			{
				reportId = 61;
			}
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_MMDA_NOW_TRANSFER_WINDOW_INSTR.getId()))
		{
			if(programCode.equalsIgnoreCase(Program.Values.RBC.getId()))
			{
				reportId = 66;
			}
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_MONTHLY_CALL.getId()))
		{
			reportId = 120;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_OMNIBUS_ACTIVITY.getId()))
		{
			if(programCode.equalsIgnoreCase(Program.Values.RBC.getId()))
			{
				reportId = 77;
			}
			else if(programCode.equalsIgnoreCase(Program.Values.FOLIO.getId()))
			{
				reportId = 109;
			}
			else
			{
				reportId = 84;
			}
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_OVER_FDIC.getId()))
		{
			reportId = 89;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_OVER_FDIC_ACCT_DETAIL.getId()))
		{
			if(programCode.equalsIgnoreCase(Program.Values.DBTCA.getId()))
			{
				reportId = 117;				
			}
			else
			{
				reportId = 98;
			}
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_PROGRAM_SUMMARY.getId()))
		{
			reportId = 86;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_RECON_OPEN_ITEMS_ACCRUALS.getId()))
		{
			reportId = 122;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_RECON_OPEN_ITEMS_BALANCES.getId()))
		{
			reportId = 121;
		}
		else if(processType.equalsIgnoreCase(DailyProcessType.Values.REPORT_SETTLEMENT.getId()))
		{
			if(programCode.equalsIgnoreCase(Program.Values.DBTCA.getId()))
			{
				reportId = 75;
			}
			else if( programCode.equalsIgnoreCase(Program.Values.RBC.getId()) )
			{
				reportId = 42;
			}
			else 
			{
				reportId = 93;
			}
		}
		
		if(reportId == 0)
		{
			throw new RuntimeException("Cannot determine Report Generator report id from supplied parameters, programCode='" + programCode + "', ProcessType='" + processType + "'.");
		}

		return reportId;
    }

    //============================================================================
	//
	// Reports - Web Delivery
	//
	//============================================================================
    public Report getReport(long programId, Date businessDate, long reportId, int appType)
    {
    	Report report = new Report();
    	String appExtension = "XLSX";
    	if (appType == ReportDocType.PDF.ordinal()) 
    	{
    		appExtension = "PDF";
    	}
    	
    	long newReportId = reportId;
    	if ((reportId >= 1000) && (reportId < 2000))
    	{
    		newReportId = (reportId-1000);
    	}
    	else if ((reportId >= 2000) && (reportId < 3000))
    	{
    		newReportId = (reportId-2000);
    	}

    	String reportDescription = getRG_ReportDescription(newReportId);
    	boolean isLookupReportDir = false;
    	if(reportId == 1)
    	{
    		report = new Report(1, "Sweep Activity Summary Report", "Sweep activity/", "${BusinessDate=yyyy.MM.dd}.${ProgramFolderName}.Sweep activity summary.PDF");
    		isLookupReportDir = true;
    	}
    	else if(reportId == 2)
    	{
        	report = new Report(2, "Sweep Activity Detail Report", "Sweep activity/", "${BusinessDate=yyyy.MM.dd}.${ProgramFolderName}.Sweep activity detail.PDF");
    		isLookupReportDir = true;
    	}
    	else if(reportId == 3)
    	{
        	report = new Report(3, "Sweep Activity Detail Exceptions Report", "Sweep activity/", "${BusinessDate=yyyy.MM.dd}.${ProgramFolderName}.Sweep Activity Exceptions.PDF");
    		isLookupReportDir = true;
    	}
    	else if(reportId == 4)
    	{
        	report = new Report(4, "Early Settlement Report", "E Settlement Report/", "${BusinessDate=yyyy.MM.dd}.${ProgramFolderName}.Expected Settlement Report.PDF");
    		isLookupReportDir = true;
    	}
    	else if(reportId == 5)
    	{
        	report = new Report(5, "Settlement Report", "Settlement Report/", "${BusinessDate=yyyy.MM.dd}.${ProgramFolderName}.Settlement Report.PDF");
    		isLookupReportDir = true;
    	}
      	else if(reportId >= 1000)
    	{
        	String dynamicReportRootDir = System.getenv("DMS_HOME")+"/temp/${CurrentDate=yyyy.MM.dd}/";
        	report = new Report(reportId, reportDescription, dynamicReportRootDir, "${BusinessDate=yyyy.MM.dd}_${CurrentDate=kk.mm.ss}${ProgramFolderName}." + reportDescription + "." + appExtension);
    	}
    	
    	String reportDir = report.getField(Report.Field.DIRECTORY_NAME).getStringValue();
		reportDir = IOUtility.getDirNameWithPathSepSuffix(reportDir);
    	reportDir = substituteReportFileParameters(reportDir, programId, businessDate);

    	String fileName = report.getField(Report.Field.FILE_NAME).getStringValue();
    	fileName = substituteReportFileParameters(fileName, programId, businessDate);
		
		report.getField(Report.Field.DIRECTORY_NAME).setValue(reportDir);
		report.getField(Report.Field.FILE_NAME).setValue(fileName);

		if(isLookupReportDir)
		{
			setReportAvailable(report, programId, businessDate);
		}
		else if(reportDir.length() > 0)
		{
			FileParamUtility.createDirectoryIfNotExists(reportDir);
		}
    
		log.debug(report.getField(Report.Field.DIRECTORY_NAME).getStringValue() + "," + report.getField(Report.Field.FILE_NAME).getStringValue());
		return report;
    }
    
	public List<Report> getReconReportList(long programId, Date businessDate, int userType, int appType )
	{
		List<Report> reportList = new ArrayList<Report>();
		reportList.add(getReport(programId, businessDate, 6, appType));
		reportList.add(getReport(programId, businessDate, 10, appType));
		reportList.add(getReport(programId, businessDate, 1003, appType));
		reportList.add(getReport(programId, businessDate, 1064, appType));
		reportList.add(getReport(programId, businessDate, 1076, appType));
		return reportList;
	}
        
    public List<Report> getReportList(long programId, Date businessDate)
    {
    	int appType = ReportDocType.PDF.ordinal();
    	List<Report> reportList = new ArrayList<Report>();
    	reportList.add(getReport(programId, businessDate, 1, appType));
    	reportList.add(getReport(programId, businessDate, 2, appType));
    	reportList.add(getReport(programId, businessDate, 3, appType));
    	reportList.add(getReport(programId, businessDate, 4, appType));
    	reportList.add(getReport(programId, businessDate, 5, appType));
    	return reportList;
    }

    @SuppressWarnings("unchecked")
	public BeanSet<RG_Report> getRG_ReportDataSet()
    {
    	CacheDataRetrievalMethod retrieveMethod = new CacheDataRetrievalMethod() { 
    		public Object retrieveData() {
    			return new RG_ReportDataSet(reportsDAO.getRG_Report());
    		}
    	};
    	return (BeanSet<RG_Report>)cacheManager.get(DataCacheType.RG_REPORT_LIST, retrieveMethod);
    }
    
    public String getRG_ReportDescription(long reportId)
    {
    	String reportDescription = "";
    	RG_Report report = this.getRG_ReportDataSet().getBean(reportId);
    	if(report != null)
    	{
    		reportDescription = report.getDescription();
    	}    	
    	return reportDescription;
    }

    private void setReportAvailable(Report report, long programId, Date businessDate)
    {
    	String workingDir = "${ProgramWorkingDir}";
    	String archiveDir = "${ProgramArchiveDir}${BusinessDate=yyyy/yyyy.MM/MMMMM dd}/";

    	String reportDir = report.getField(Report.Field.DIRECTORY_NAME).getStringValue();
		String fileName = report.getField(Report.Field.FILE_NAME).getStringValue();

		//1. First Check Working directory
		String fullReportDir = workingDir + reportDir;
    	fullReportDir = substituteReportFileParameters(fullReportDir, programId, businessDate);
		if(IOUtility.fileExists(fullReportDir + fileName))
		{
			report.getField(Report.Field.DIRECTORY_NAME).setValue(fullReportDir);
			report.getField(Report.Field.IS_AVAILABLE).setValue(true);
			return;
		}

		//2. Second Check Working/Afternoon directory
		fullReportDir = workingDir + "Afternoon/" + reportDir;
    	fullReportDir = substituteReportFileParameters(fullReportDir, programId, businessDate);
		if(IOUtility.fileExists(fullReportDir + fileName))
		{
			report.getField(Report.Field.DIRECTORY_NAME).setValue(fullReportDir);
			report.getField(Report.Field.IS_AVAILABLE).setValue(true);
			return;
		}
		
		//3. Third Check Archive directory
		fullReportDir = archiveDir + reportDir;
    	fullReportDir = substituteReportFileParameters(fullReportDir, programId, businessDate);
		if(IOUtility.fileExists(fullReportDir + fileName))
		{
			report.getField(Report.Field.DIRECTORY_NAME).setValue(fullReportDir);
			report.getField(Report.Field.IS_AVAILABLE).setValue(true);
			return;
		}
		
		//4. Fourth Check Archive/Afternoon directory
		fullReportDir = archiveDir + "Afternoon/" + reportDir;
    	fullReportDir = substituteReportFileParameters(fullReportDir, programId, businessDate);
		if(IOUtility.fileExists(fullReportDir + fileName))
		{
			report.getField(Report.Field.DIRECTORY_NAME).setValue(fullReportDir);
			report.getField(Report.Field.IS_AVAILABLE).setValue(true);
			return;
		}
    }

    private String substituteReportFileParameters(String file, long programId, Date businessDate)
    {
    	String newFile = file;
		String programWorkingDir = this.ds.util.getProgramWorkingDirPrimary(programId);
		String programArchiveDir = this.ds.util.getProgramArchiveDirPrimary(programId);
		String programFolderName = this.ds.util.getProgramFolderName(programId);
		Date currentDate = new Date();

    	newFile = FileParamUtility.substituteProgramArchiveDirPrimaryParameters(programArchiveDir, newFile);
		newFile = FileParamUtility.substituteProgramWorkingDirPrimaryParameters(ds, programId, newFile);
		newFile = FileParamUtility.substituteProgramFolderNameParameters(ds, programId, newFile);
    	newFile = FileParamUtility.substituteBusinessDateParameters(businessDate, newFile);
    	newFile = FileParamUtility.substituteCurrentDateParameters(currentDate, newFile);
    	return newFile;
	}
    
}
