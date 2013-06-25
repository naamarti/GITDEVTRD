package com.totalbanksolutions.trading.web.action.changepassword;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.totalbanksolutions.framework.bean.database.table.AppUserHistory;
import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.util.DMSStringUtils;
import com.totalbanksolutions.framework.util.EncryptionUtility;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.bean.WebForm;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.trading.web.model.LoginData;
import com.totalbanksolutions.trading.web.model.LoginModelHelper;

public class ChangePasswordSaveAction implements Action
{
	protected final Log log = LogFactory.getLog( ChangePasswordSaveAction.class );
	private DataService ds;
	private String validationError = "";
	
    public ChangePasswordSaveAction(DataService ds)
    {
    	this.ds = ds;
    }
    
	public boolean processAction( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		log.debug("Save Changed Password...");
		boolean status = true;
		
		LoginModelHelper h = new LoginModelHelper(modelAndView);

		LoginData loginData = h.getLoginData();
		WebForm loginForm = h.getLoginForm();
		
		loginData.addRow( request.getParameterMap() );
		log.debug( loginData.toString() );
		
		int minPasswordLength = this.ds.trading.getAppConfigPasswordMinimumLength();
		String newPassword = loginData.getRow().getColumn(loginData.PASSWORD).getValueAsString();
		String confirmPassword = loginData.getRow().getColumn(loginData.CONFIRM_PASSWORD).getValueAsString();
		if( ! newPassword.equals(confirmPassword) )
		{
//			loginForm.setStateValid( loginData.PASSWORD );
			loginForm.setStateInvalid( loginData.CONFIRM_PASSWORD );
			h.setMessageError( "'Password' and 'Confirm Password' fields do not match!" );
			status = false;
		}
		else if( validateNewPassword( h.getUser().getUserId(), newPassword ) )
		{
			try
			{
				this.ds.trading.updateAppUserPassword( h.getUser().getUserId(), newPassword, minPasswordLength );
				h.getUser().setPasswordChangeDateTime( new Date() );
				h.getUser().setPasswordReset(false);
				//loginData.getRow().getColumn( loginData.PASSWORD ).setValue("");
				//loginData.getRow().getColumn( loginData.CONFIRM_PASSWORD ).setValue("");
				h.setMessageSuccess( "Password successfully changed." );
			}
			catch( Exception e )
			{
				h.setMessageError( "Unexpected error while updating password. Please try again.<br>" + ExceptionUtils.getMessage(e) );
				log.error( h.getMessageError() + "\n" + ExceptionUtils.getStackTrace(e) );
				status = false;
			}
		}
		else
		{
			loginForm.setStateInvalid( loginData.PASSWORD );
			loginForm.setStateInvalid( loginData.CONFIRM_PASSWORD );
			h.setMessageError( validationError );
			status = false;
		}
			
		return status;
	}

	private boolean validateNewPassword( long userID, String newPassword )
	{
		log.debug("validateNewPassword...");
		log.debug("Password to validate is : " + newPassword);
		
		StringBuffer errorMessage = new StringBuffer();
		boolean isPasswordValid = true;

		int minPasswordLength = this.ds.trading.getAppConfigPasswordMinimumLength();
		int numCapsNeeded = this.ds.trading.getAppConfigPasswordNumberOfCaps();
		int numNumericNeeded = this.ds.trading.getAppConfigPasswordNumberOfNumerics();
		int numHistoricalRestrict = this.ds.trading.getAppConfigPasswordNumberOfHistoricRestrict();
		
		log.debug("### numCapsNeeded is : " + numCapsNeeded + " ###");
		log.debug("### numNumericNeeded is : " + numNumericNeeded + " ###");

		if( newPassword.length() < minPasswordLength)
		{
			errorMessage.append( "Password must be at least " + minPasswordLength + " characters in length." );
			isPasswordValid = false;
		}
		
		
		// Number of CAPS in password check is only enabled if numCapsNeeded is > 0
		if ( numCapsNeeded > 0 )
		{
			if ( DMSStringUtils.countUpperCaseChars(newPassword) < numCapsNeeded )
			{
				log.debug("### Password MUST contain at least " + numCapsNeeded + " capital letters. ###");
				if ( errorMessage.length() > 0 ) errorMessage.append( "<br>");
				errorMessage.append( "Password must contain at least " + numCapsNeeded )
							.append(  numCapsNeeded > 1 ? " capital letters." : " capital letter." );
				
				isPasswordValid = false;
			}
		}

		// Number of NUMERIC chars in password check is only enabled if numNumericNeeded is > 0
		if ( numNumericNeeded > 0 )
		{
			if ( DMSStringUtils.countNumericChars(newPassword) < numNumericNeeded )
			{
				log.debug("### Password MUST contain at least " + numNumericNeeded + " numeric characters. ###");
				if ( errorMessage.length() > 0 ) errorMessage.append( "<br>");
				errorMessage.append( "Password must contain at least " + numNumericNeeded ) 
							.append(  numNumericNeeded > 1 ? " numeric characters." : " numeric character." );
				isPasswordValid = false;
			}
		}
		
		// And finally, the HISTORIC password check is only enabled if numHistoricalRestrict is > 0
		if ( numHistoricalRestrict > 0 )
		{
			String newPwdEncrypted = EncryptionUtility.getInstance().encrypt( newPassword );
			boolean pwdReused = false;
			List<AppUserHistory> userHistoryList = this.ds.trading.getAppUserHistory(userID, numHistoricalRestrict);
			
			if (userHistoryList != null )
			{
				for(AppUserHistory item : userHistoryList)
				{
					String historicalPwd = item.getUserPassword();
					if ( newPwdEncrypted.equals(historicalPwd) )
					{
						log.debug("### PASSWORD INVALID HISTORICAL!! ###");
						pwdReused = true;
						break;
					}
				}

				if ( pwdReused )
				{
					if ( errorMessage.length() > 0 ) errorMessage.append( "<br>");
					errorMessage.append( "Password must not match " )
								.append(  numHistoricalRestrict > 1 ? "one of your last " + numHistoricalRestrict + " passwords." : "your last password.");
					isPasswordValid = false;
				}
			}
		}
		
		if ( !isPasswordValid )
			this.validationError = errorMessage.toString();

		return isPasswordValid;
	}
	

}
