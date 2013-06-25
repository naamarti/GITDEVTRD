package com.totalbanksolutions.trading.web.action.challengequestion;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.AppUserChallengeQuestionRelationship;
import com.totalbanksolutions.framework.bean.database.table.ChallengeQuestion;
import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.ModelHelper;

public class ChallengeQuestionDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( ChallengeQuestionDisplayAction.class );
	
	private DataService ds;

	public ChallengeQuestionDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("display ChallengeQuestion Setup...");
		ModelHelper h = new ModelHelper(modelAndView);
		
		h.setViewName("challengeQuestions");
		h.setScreenName("Security Question Setup");

		List<ChallengeQuestion> challengeQuestionList = null;
		AppUserChallengeQuestionRelationship[] challengeResponseList = new AppUserChallengeQuestionRelationship[5];
		
		challengeResponseList = this.ds.trading.getChallengeResponseArray( h.getUser().getUserId() );
		challengeQuestionList = this.ds.trading.getChallengeQuestionList();
		
		h.put( "challengeQuestionList", challengeQuestionList );
		h.put( "challengeResponseList", challengeResponseList );
		
	    return true;
	}

}
