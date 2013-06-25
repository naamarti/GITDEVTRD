package com.totalbanksolutions.trading.web.action.challengequestion;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.AppUserChallengeQuestionRelationship;
import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.framework.web.util.WebUtils;
import com.totalbanksolutions.trading.web.model.ModelHelper;

public class ChallengeQuestionSaveAction implements Action
{
	protected final Log log = LogFactory.getLog( ChallengeQuestionSaveAction.class );
	private DataService ds;
	
    public ChallengeQuestionSaveAction(DataService ds)
    {
    	this.ds = ds;
    }
    
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("Save Challenge Questions...");
		ModelHelper h = new ModelHelper(modelAndView);
		boolean status = true;

		AppUserChallengeQuestionRelationship[] challengeResponseList = new AppUserChallengeQuestionRelationship[5];
		challengeResponseList = getQuestionResponses(request, h.getUser().getUserId() );
		try
		{
			this.ds.trading.updateUserChallengeQuestions( h.getUser().getUserId(), challengeResponseList );
			h.getUser().setSecretQuestionsSet(true);
			h.setMessageSuccess( "Security Questions successfully changed." );
    	}
		catch(Exception e)
		{					
			h.setMessageError( "Unexpected error while saving Security Questions. Please try again.<br>" + ExceptionUtils.getMessage(e) );
			log.error( h.getMessageError() + "\n" + ExceptionUtils.getStackTrace(e) );
			status = false;
		}
		return status;
	}

	private AppUserChallengeQuestionRelationship[] getQuestionResponses( HttpServletRequest request, long userId )
	{
		AppUserChallengeQuestionRelationship[] returnList = new AppUserChallengeQuestionRelationship[5];
		for(int i = 0; i < 5; i++)
		{
			AppUserChallengeQuestionRelationship reply = new AppUserChallengeQuestionRelationship();
			reply.setUserId( userId );
			long questionId = WebUtils.getLongParameterValue(request, "question" + (i + 1));
			reply.setQuestionId( questionId );

			String challengeResponse = WebUtils.getStringParameterValue(request, "challengeResponse" + (i + 1));
			reply.setResponse( challengeResponse.trim() );
			returnList[i] = reply;
		}
		return returnList;
	}
	
}
