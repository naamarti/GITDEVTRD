package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.ModelHelper;

public class ErrorController extends AbstractControllerNew
{
	public ErrorController()
	{
		super(false, false); // isValidateUser=true, isSetMenuList=true
	}

	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		ModelHelper h = new ModelHelper(modelAndView);
		
		h.setViewName("error");
	}

}
