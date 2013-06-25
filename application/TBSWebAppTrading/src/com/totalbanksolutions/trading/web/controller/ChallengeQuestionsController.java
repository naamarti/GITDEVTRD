package com.totalbanksolutions.trading.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.action.challengequestion.ChallengeQuestionDisplayAction;
import com.totalbanksolutions.trading.web.action.challengequestion.ChallengeQuestionSaveAction;
import com.totalbanksolutions.trading.web.model.ModelHelper;

/**
 * @author Val Catrini
 */
public class ChallengeQuestionsController extends AbstractControllerNew
{    
	public ChallengeQuestionsController() 
    {
		super(true, true); // isValidateUser=true, isSetMenuList=true
	}

    @Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
    	ModelHelper h = new ModelHelper(modelAndView);
		if ( h.getSessionUserAction().equalsIgnoreCase("save") )
		{				
			new ChallengeQuestionSaveAction( this.ds ).processAction( request, modelAndView );
		}
		
		new ChallengeQuestionDisplayAction( this.ds ).processAction( request, modelAndView );
	}
}
