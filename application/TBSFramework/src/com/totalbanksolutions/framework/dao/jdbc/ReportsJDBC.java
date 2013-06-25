package com.totalbanksolutions.framework.dao.jdbc;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.EnvelopeTypes;
import com.totalbanksolutions.framework.bean.database.table.Envelope_ReportType_Relationship;
import com.totalbanksolutions.framework.bean.database.table.Envelopes;
import com.totalbanksolutions.framework.bean.database.table.RG_Report;
import com.totalbanksolutions.framework.bean.database.table.ReportTypes;
import com.totalbanksolutions.framework.bean.database.view.ViewProgramBank;
import com.totalbanksolutions.framework.bean.database.view.ViewRBCActivitySummaryByDI;
import com.totalbanksolutions.framework.dao.ReportsDAO;
import com.totalbanksolutions.framework.dao.util.DatabaseHelper;
import com.totalbanksolutions.framework.dao.util.SQLParameters;

/**
 * =================================================================================================
 * Modified:  07 Nov 2011 NAM #1051: Pulling Report Type from DB to make the email senders generic
 * Modified:  23 Apr 2012 VC   #966: All Programs / Bank Centric checklist - add First Niagara quarterly report
 *            24 Aug 2012 NM #1838: find reports by envelope 
 * =================================================================================================
 */
public class ReportsJDBC implements ReportsDAO
{
	/** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    private DataSource dataSource;
    private DatabaseHelper databaseHelper;
    
	public ReportsJDBC(DataSource ds)
	{
		setDataSource(ds);
	}

	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
        this.databaseHelper = new DatabaseHelper(dataSource);
    }
    
    public void close()
    {
    	this.databaseHelper.close();
    	this.databaseHelper = null;
    	this.dataSource = null;
    }
    
    //============================================================================
	//
	// Reports - Shared
	//
	//============================================================================
    public List<ViewRBCActivitySummaryByDI> getRBCActivitySummaryByDI(String databaseName, Date businessDate, long sourceInstitutionId)
    {
	   	String sql = "EXEC " + databaseName + "..p_j_RBC_GetActivitySummaryByDI_Early @BusinessDate=?, @SourceInstitution_FK=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(businessDate);
		params.addValue(sourceInstitutionId);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewRBCActivitySummaryByDI());
    }
    
    public List<ViewProgramBank> getProgramBanksForCallReports( Date businessDate )
    {
	   	String sql = "EXEC TBS_Recon..p_j_GetProgramBanksForCallReports @BusinessDate=? ";
		SQLParameters params = new SQLParameters();
		params.addValue(businessDate);
		return this.databaseHelper.executeStoredProcedureForList(sql, params, new ViewProgramBank());    	
    }

    //============================================================================
	//
	// Reports - Email Sender
	//
	//============================================================================
    public List<ReportTypes> getReportTypes(long reportType)
	{
		String sql = "SELECT * " 
		+ " FROM " + ReportTypes.DATABASE_TABLE_NAME + " WITH (NOLOCK) "
		+ " WHERE ReportType_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(reportType);
		return this.databaseHelper.queryForList(sql, params, new ReportTypes());
	}
    
    public List<Envelope_ReportType_Relationship> getReportIDsByEnvelope(long envelopeId)
	{
		String sql = "SELECT * " 
		+ " FROM " + Envelope_ReportType_Relationship.DATABASE_TABLE_NAME + " WITH (NOLOCK) "
		+ " WHERE Envelope_FK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(envelopeId);
		return this.databaseHelper.queryForList(sql, params, new Envelope_ReportType_Relationship());
	}
    
    public List<ReportTypes> getReportTypesList()
	{
		String sql = "SELECT * FROM " + ReportTypes.DATABASE_TABLE_NAME + " WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		List<ReportTypes> dataList = this.databaseHelper.queryForList(sql, params, new ReportTypes());
		return dataList;
	}

    public List<Envelopes> getEnvelopes(long envelope)
	{
		String sql = "SELECT * " 
		+ " FROM " + ReportTypes.DATABASE_TABLE_NAME + " WITH (NOLOCK) "
		+ " WHERE ReportType_PK = ? ";
		SQLParameters params = new SQLParameters();
		params.addValue(envelope);
		return this.databaseHelper.queryForList(sql, params, new Envelopes());
	}
    
    public List<Envelopes> getEnvelopesList()
	{
		String sql = "SELECT * FROM " + Envelopes.DATABASE_TABLE_NAME + " WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		List<Envelopes> dataList = this.databaseHelper.queryForList(sql, params, new Envelopes());
		return dataList;
	}
    //============================================================================
	//
	// Reports - Web Delivery
	//
	//============================================================================    
    public List<RG_Report> getRG_Report()
	{
		String sql = "SELECT * FROM " + RG_Report.DATABASE_TABLE_NAME + " WITH (NOLOCK) ";
		SQLParameters params = new SQLParameters();
		return this.databaseHelper.queryForList(sql, params, new RG_Report());
	}

}
