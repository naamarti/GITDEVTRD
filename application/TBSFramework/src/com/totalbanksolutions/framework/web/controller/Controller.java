package com.totalbanksolutions.framework.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.model.ModelAndView;

/**
 * 
 * @author vcatrini
 *
 */
public interface Controller
{	

	public void setDataService(DataService ds);
	ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException;
	
}
