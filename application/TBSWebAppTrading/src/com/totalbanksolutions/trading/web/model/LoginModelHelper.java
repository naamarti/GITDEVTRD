package com.totalbanksolutions.trading.web.model;

import com.totalbanksolutions.framework.web.bean.WebForm;
import com.totalbanksolutions.framework.web.model.ModelAndView;

public class LoginModelHelper extends ModelHelper {

	private final String LOGIN_DATA					= "loginData";
	private final String LOGIN_FORM					= "loginForm";
	private final String LOGIN_STEP					= "loginStep";
	private final String IS_DISCLAIMER_APPROVED		= "isDisclaimerApproved";
	
	public LoginModelHelper( ModelAndView mv )
	{
		super(mv);
		if( !mv.getModel().containsKey("LoginModelHelper") )
		{
			init();
			mv.getModel().put("LoginModelHelper", true);
		}
	}

	public void init()
	{
		this.setLoginData( new LoginData() );
		this.setLoginForm( new WebForm() );
		this.setLoginStep("");
		this.setIsDisclaimerApproved(false);
	}
	
	// Getters
	public LoginData getLoginData() {
		return (LoginData)this.get( this.LOGIN_DATA );
	}
	public WebForm getLoginForm() {
		return (WebForm)this.get( this.LOGIN_FORM );
	}
	public String getLoginStep() {
		return (String)this.get( this.LOGIN_STEP );
	}
	public boolean getIsDisclaimerApproved()
	{
		return (boolean)this.get( this.IS_DISCLAIMER_APPROVED );
	}
	
	// Setters
	public void setLoginData( LoginData loginData ) {
		this.put( this.LOGIN_DATA, loginData );
	}
	public void setLoginForm( WebForm loginForm ) {
		this.put( this.LOGIN_FORM, loginForm );
	}	
	public void setLoginStep( String loginStep ) {
		this.put( this.LOGIN_STEP, loginStep );
	}
	public void setIsDisclaimerApproved( boolean isDisclaimerApproved ) 
	{
		this.put( this.IS_DISCLAIMER_APPROVED, isDisclaimerApproved );
	}

}
