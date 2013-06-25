package com.totalbanksolutions.framework.dao;

import java.util.Date;
import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.Envelope_ReportType_Relationship;
import com.totalbanksolutions.framework.bean.database.table.Envelopes;
import com.totalbanksolutions.framework.bean.database.table.RG_Report;
import com.totalbanksolutions.framework.bean.database.table.ReportTypes;
import com.totalbanksolutions.framework.bean.database.view.ViewProgramBank;
import com.totalbanksolutions.framework.bean.database.view.ViewRBCActivitySummaryByDI;
import com.totalbanksolutions.framework.dao.util.DAO;

/**
 * =================================================================================================
 * Modified:  07 Nov 2011 NAM #1051: Pulling Report Type from DB to make the email senders generic
 * Modified:  23 Apr 2012 VC   #966: All Programs / Bank Centric checklist - add First Niagara quarterly report
 *            24 Aug 2012 NM #1838: find reports by envelope 
 * =================================================================================================
 */
public interface ReportsDAO extends DAO
{

    public void close();
	
    //============================================================================
	//
	// Reports - Shared
	//
	//============================================================================
    public List<ViewRBCActivitySummaryByDI> getRBCActivitySummaryByDI(String databaseName, Date businessDate, long sourceInstitutionId);
    public List<ViewProgramBank> getProgramBanksForCallReports( Date businessDate );

    //============================================================================
	//
	// Reports - Email Sender
	//
	//============================================================================
    public List<ReportTypes> getReportTypes(long reportType);
    public List<ReportTypes> getReportTypesList();
    public List<Envelopes> getEnvelopesList();
    public List<Envelope_ReportType_Relationship> getReportIDsByEnvelope(long envelopeId);
    
    //============================================================================
	//
	// Reports - Web Delivery
	//
	//============================================================================    
    public List<RG_Report> getRG_Report();

	



}
