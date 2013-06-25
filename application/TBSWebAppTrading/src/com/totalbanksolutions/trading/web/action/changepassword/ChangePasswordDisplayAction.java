package com.totalbanksolutions.trading.web.action.changepassword;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.bean.WebForm;
import com.totalbanksolutions.framework.web.bean.WebFormControl;
import com.totalbanksolutions.framework.web.enums.WebControlType;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.LoginData;
import com.totalbanksolutions.trading.web.model.LoginModelHelper;

public class ChangePasswordDisplayAction implements Action
{
	protected final Log log = LogFactory.getLog( ChangePasswordDisplayAction.class );
	
	private DataService ds;

	public ChangePasswordDisplayAction(DataService ds)
	{
		this.ds = ds;
	}
	
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("display change password...");
		
		LoginModelHelper h = new LoginModelHelper(modelAndView);

		LoginData loginData = h.getLoginData();
		WebForm loginForm = h.getLoginForm();
		
		h.setViewName("changePassword");
		h.setScreenName("Change Password");
		
		buildForm( loginForm, loginData, h.getUser().getUserName() );

		//int minPasswordLength = this.ds.security.getAppConfigPasswordMinimumLength();
		//int numCapsNeeded = this.ds.security.getAppConfigPasswordNumberOfCaps();
		//int numNumericNeeded = this.ds.security.getAppConfigPasswordNumberOfNumerics();
		//int numHistoricalRestrict = this.ds.security.getAppConfigPasswordNumberOfHistoricRestrict();
		
	    //webBean.getModel().put("minPasswordLength", minPasswordLength);
	    //webBean.getModel().put("numCapsNeeded", numCapsNeeded);
	    //webBean.getModel().put("numNumericNeeded", numNumericNeeded);
	    //webBean.getModel().put("numHistoricalRestrict", numHistoricalRestrict);
		
	    return true;
	}

	private void buildForm( WebForm f, LoginData t, String userName )
	{
		f.setModelTable(t);
		WebFormControl c = null;
		c = f.addControl( t.USER_NAME,					WebControlType.INPUT_TEXT );
		c.setValue(userName);
		c.setReadOnly(true);
		
		c = f.addControl( t.PASSWORD,					WebControlType.INPUT_PASSWORD );
		c.setLabel("New Password");
		c.setRequired(true);
		
		c = f.addControl( t.CONFIRM_PASSWORD,			WebControlType.INPUT_PASSWORD );
		c.setRequired(true);
	}
	
}
