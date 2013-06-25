package com.totalbanksolutions.framework.web.workflow;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.totalbanksolutions.framework.dataservice.DataService;
import com.totalbanksolutions.framework.web.action.Action;
import com.totalbanksolutions.framework.web.model.ModelAndView;

/**
 * 
 * @author vcatrini
 *
 */
public interface Workflow
{	

	/*
	 Framework:
	 	Workflow:
	 		<firstAction>.processAction(HttpServletRequest request, ModelAndView mv)
	 	
	 Web:
	 	Controller:
	 		LoginWorkflow loginWorkflow = new LoginWorkflow();
	 		loginWorkflow.processWorkflow( request, mv );
	 	
	 	Workflow:
	 		LoginWorkFlow:
	 			process(request, mv):
	 				super().process( <initialActionToStart> );
	 				
	 			init:
	 									Action							onSuccess						onFailure
		 			w.registerAction( LoginValidateUserAction, 			LoginIsAccountLockedAction, 	LoginFailedAction(VIEW) )
		 			w.registerAction( LoginIsAccountLockedAction, LoginValidatePasswordAction, LoginFailedAction(VIEW) )
		 			w.registerAction( LoginValidatePasswordAction, LoginIsRequiresWhiteListAction, LoginFailedAction(VIEW) )
		 			w.registerAction( LoginIsRequiresWhiteListAction, LoginSuccessAction, LoginFailedAction(VIEW) )
		 			w.registerAction( LoginSuccessAction, LoginIsPasswordExpired, LoginFailedAction(VIEW) )
		 			
		 			w.registerAction( LoginIsPasswordExpired, LoginIsPasswordResetAction, LoginChangePasswordAction(VIEW) )
		 			w.registerAction( LoginIsPasswordReset, LoginIsSecretQuestionsSetAction, LoginChangePasswordAction(VIEW))
		 			w.registerAction( LoginIsSecretQuestionsSetAction, LoginIsMandatoryContactInfoSetAction, LoginChallengeQuestionsSetupAction(VIEW) )
		 			w.registerAction( LoginIsMandatoryContactInfoSetAction, LoginChallengeQuestionAction(VIEW), LoginContactInfoSetupAction(VIEW) )		 			

		 			w.registerAction( LoginChallengeQuestionAction(VIEW), WelcomeAction(VIEW), LoginFailedAction(VIEW) )

		 			
		 			// ===============================================================================		 			
		 			a = registerAction( LoginCheckMandatoryContactInfoAction );
		 			// ===============================================================================		 			
				 		a.setOnSuccessShowView( LoginChallengeQuestionViewAction );
				 		a.setOnFailureShowView( LoginContactInfoSetupViewAction );

		 			// ===============================================================================
		 			a = registerAction( LoginContactInfoSetupAction );
		 			// ===============================================================================		 			
				 		a.setOnSuccessRunAction( LoginChallengeQuestionAction );
				 		a.setOnFailureRunAction( LoginContactInfoSetupAction );

		 			// ===============================================================================		 			
		 			a = registerAction( LoginValidateChallengeQuestionAction );
		 			// ===============================================================================		 			
				 		a.setOnSuccessShowView( WelcomeViewAction );
				 		a.setOnFailureShowView( LoginFailedViewAction );

					
	
	
			LoginChangePassword
					LoginChangePasswordAction, validateAppUserSessionPreLogin, LoginFailedAction
					ValidateNewPassword
			
			LoginChallengeQuestionsSetup
					LoginChallengeQuestionsSetupAction, validateAppUserSessionPreLogin, LoginFailedAction
					updateUserChallengeQuestions
					
			ContactInfo
					LoginContactInfoSetupAction, validateAppUserSessionPreLogin, LoginFailedAction
					updateMandatoryContactInfo
					
			LoginChallengeQuestion
					LoginChallengeQuestionAction, validateAppUserSessionPreLogin, LoginFailedAction
					validateChallengeReponse, WelcomeAction, LoginFailedAction
					
					
	 */
	
	public void setDataService(DataService ds);
	ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException;
	public boolean processAction(HttpServletRequest request, ModelAndView mv)  throws ServletException, IOException;
	
	public void registerAction(Action action);
	
}
