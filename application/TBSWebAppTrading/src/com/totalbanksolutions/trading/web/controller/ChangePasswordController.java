package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.changepassword.ChangePasswordDisplayAction;
import com.totalbanksolutions.trading.web.action.changepassword.ChangePasswordSaveAction;
import com.totalbanksolutions.trading.web.model.ModelHelper;

/**
 * @author Val Catrini
 */
public class ChangePasswordController extends AbstractControllerNew
{    
	public ChangePasswordController() 
    {
		super(true, true); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
    	ModelHelper h = new ModelHelper(modelAndView);
		if ( h.getSessionUserAction().equalsIgnoreCase("save") )
		{				
			new ChangePasswordSaveAction( this.ds ).processAction( request, modelAndView );
		}
		
		new ChangePasswordDisplayAction( this.ds ).processAction( request, modelAndView );
	}

}
