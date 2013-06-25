package com.totalbanksolutions.trading.web.action.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.ChallengeQuestion;
import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.bean.WebForm;
import com.totalbanksolutions.framework.web.bean.WebFormControl;
import com.totalbanksolutions.framework.web.enums.ValidationState;
import com.totalbanksolutions.framework.web.enums.WebControlType;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.LoginData;
import com.totalbanksolutions.trading.web.model.LoginModelHelper;

public class LoginDisplayChallengeQuestionAction implements Action
{
	protected final Log log = LogFactory.getLog( LoginDisplayChallengeQuestionAction.class );
	private final String PASSWORD_MASK = StringUtils.repeat("*", 25);
	
	private DataService ds;

	public LoginDisplayChallengeQuestionAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("display login page with ChallengeQuestion...");
		LoginModelHelper h = new LoginModelHelper(modelAndView);

		LoginData loginData = h.getLoginData();
		WebForm loginForm = h.getLoginForm();
		
		h.setViewName("login");
		h.setScreenName("TBS Units Trading Login");

		ChallengeQuestion challengeQuestion = this.ds.trading.getRandomChallengeQuestion( h.getUser().getUserId() );
		buildForm( loginForm, loginData, challengeQuestion, h.getUser().getUserName() );

	    return true;
	}

	private void buildForm( WebForm f, LoginData t, ChallengeQuestion challengeQuestion, String userName )
	{
		f.setModelTable(t);
		WebFormControl c = null;
		c = f.addControl( t.USER_NAME,					WebControlType.INPUT_TEXT );
		c.setValue(userName);
		c.setReadOnly(true);
		c.setValidationState(ValidationState.VALID);
		
		c = f.addControl( t.PASSWORD,					WebControlType.INPUT_PASSWORD );
		c.setValue( PASSWORD_MASK );
		c.setReadOnly(true);
		c.setValidationState(ValidationState.VALID);
		
		c = f.addControl( t.CHALLENGE_QUESTION,			WebControlType.INPUT_PASSWORD );
		c.setLabel( challengeQuestion.getQuestion() );
		c.setRequired(true);

		c = f.addControl( t.CHALLENGE_QUESTION_ID,		WebControlType.INPUT_HIDDEN);
		c.setValue( challengeQuestion.getQuestionId() );
	}
	
}
