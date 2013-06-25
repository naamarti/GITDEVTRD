package com.totalbanksolutions.trading.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.financialdocuments.FinancialDocumentsDisplayAction;
import com.totalbanksolutions.trading.web.model.ModelHelper;

/**
 * @author Val Catrini
 */
public class FinancialDocumentsController extends AbstractControllerNew 
{
	public FinancialDocumentsController()
	{
		super(true, true); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		
    	ModelHelper h = new ModelHelper(modelAndView);
		
		h.setViewName("financialDocuments");
		h.setScreenName("Financial Documents");

		String sessionUserAction = h.getSessionUserAction(); 
		log.debug("### FinancialDocumentsController sessionUserAction = " + sessionUserAction + " ###");

		if( sessionUserAction.length() > 0 )
		{
		    String reportDirName = getUserDocumentPath() + "Financial Documents\\";
		    
		    String reportFileName = sessionUserAction + ".pdf";
	    	response.setContentType("application/pdf");

	    	try
	    	{
	    	    Thread.sleep(15000); 
	    	    response.setHeader("Content-disposition", "attachment; filename=" + reportFileName);
	    	    writeFileToOutput(response, reportDirName + reportFileName);
	    		log.debug("clear sessionUserAction T");
	    		h.setSessionUserAction(""); 
	    	}
			catch(Exception e)
			{
				log.error(ExceptionUtils.getStackTrace(e));
				h.setMessageError( "Unexpected error while trying open PDF report.<br>" + ExceptionUtils.getMessage(e) );
	    		log.debug("clear sessionUserAction C");
	    		h.setSessionUserAction(""); 
			}
		}
		else
		{
			new FinancialDocumentsDisplayAction( this.ds ).processAction( request, modelAndView );
		}
	}

    private void writeFileToOutput(HttpServletResponse response, String fileName)
    {
    	BufferedInputStream  bis = null; 
    	BufferedOutputStream bos = null;
    	try 
    	{
    		File f = new File(fileName);    		
    		// Use Buffered Stream for reading/writing.
    		bis = new BufferedInputStream(new FileInputStream(f));
    		bos = new BufferedOutputStream(response.getOutputStream());
    		byte[] buff = new byte[2048];
    		int bytesRead;
    		// Simple read/write loop.
    		while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) 
    		{
    			bos.write(buff, 0, bytesRead);
    		}
    	}
		catch(Exception e)
		{
			log.error(ExceptionUtils.getStackTrace(e));
			throw new RuntimeException(e);
		}
    	finally 
    	{
    		try
    		{
	    		if (bis != null) bis.close();
	    		if (bos != null) bos.close();
    		}
    		catch(Exception e) {}
    	}	
    }
	
    private String getUserDocumentPath()
    {
    	String docPath = "\\\\intranet.totalbanksolutions.com\\TBS_Files\\website documents\\Trading\\";
    	return docPath;    	
    }
	
}
