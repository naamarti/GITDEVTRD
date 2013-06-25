package com.totalbanksolutions.framework.dao;

import java.util.List;

import com.totalbanksolutions.framework.bean.database.table.AppNavigation;
import com.totalbanksolutions.framework.bean.database.table.AppNavigationGroup;
import com.totalbanksolutions.framework.bean.database.table.AppRole;
import com.totalbanksolutions.framework.bean.database.table.AppUser;
import com.totalbanksolutions.framework.bean.database.table.AppUserChallengeQuestionRelationship;
import com.totalbanksolutions.framework.bean.database.table.AppUserHistory;
import com.totalbanksolutions.framework.bean.database.table.AppUserType;
import com.totalbanksolutions.framework.bean.database.table.ChallengeQuestion;
import com.totalbanksolutions.framework.bean.database.table.Contact;
import com.totalbanksolutions.framework.bean.database.table.ContactInstitutionType;
import com.totalbanksolutions.framework.bean.database.table.Envelopes;
import com.totalbanksolutions.framework.bean.database.view.ViewAppUserSession;
import com.totalbanksolutions.framework.bean.database.view.ViewContactEnvelopeRelationship;
import com.totalbanksolutions.framework.bean.web.AppUserFilter;
import com.totalbanksolutions.framework.dao.util.DAO;
import com.totalbanksolutions.framework.model.database.view.AppNavigationView;
import com.totalbanksolutions.framework.model.database.view.EmailSenderLogView;
import com.totalbanksolutions.framework.model.database.view.MandatoryContactInfoView;
import com.totalbanksolutions.framework.model.database.table.ContactsTable;

/**
 * @author vcatrini
 * Modified: 28 Sep 2011 VC #1010: Modify the way DMS distinguishes between SECURE and NON-SECURE environments.
 */  
public interface SecurityDAO extends DAO
{

    public void close();
	
	//============================================================================
	//
    // AppUser_History
    //
	//============================================================================
    public List<AppUserHistory> getAppUserHistory( long userID, int historicalRecordCount );

    //============================================================================
	//
    // AppNavigation
    //
	//============================================================================	
	public List<AppNavigation> getAppNavigationList(long groupId);
	public AppNavigationView getAppUserNavigationList(long userId, String secureURLPrefix, String localHostPrefix);
	public void updateAppNavigationList(List<AppNavigation> linkList, long groupId);

    //============================================================================
	//
	// AppNavigation_AppRole_Relationship
	//
	//============================================================================	
	public List<AppNavigationGroup> getAppNavigationRoleList(long roleId);
	public void updateAppNavigationRoles(List<AppNavigationGroup> groupList, long roleId);
	
    //============================================================================
	//
	// AppNavigationGroup
	//
	//============================================================================
	public List<AppNavigationGroup> getAppNavigationGroupList();
	public void updateAppNavigationGroupList(List<AppNavigationGroup> groupList);

    //============================================================================
	//
	// AppNavigationApp
	//
	//============================================================================
	//	public List<App> getAppNavigationAppList();
	//	public void updateAppNavigationAppList(List<App> appList);
	
    //============================================================================
	//
	// AppRole
	//
	//============================================================================    
	public List<AppRole> getAppRoleList();
    
	//============================================================================
	//
	// AppSession
	//
	//============================================================================
	public void activateAppSession(long userId, String sessionKey);
	public String addAppSession(long userId);
	public void deactivateAppSession(long userId, String sessionKey);
	public void expireAppSessions(int expireInMinutes);
	public ViewAppUserSession validateAppUserSession(ViewAppUserSession user);
	public ViewAppUserSession validateAppUserSession(ViewAppUserSession user, boolean isLoginInProgress);

	//============================================================================
	//
	// AppUser
	//
	//============================================================================
	public long addAppUser(AppUser user, String defaultUserPassword);
	public int deleteAppUser(long userId);
	public AppUser getAppUserDetails(long userId);
	public AppUser getAppUserByUserName(String userName);
	public List<AppUser> getAppUserList(AppUserFilter appUserFilter);	
	public void resetAppUserFailedLoginAttempts(AppUser user);
	public void resetAppUserPassword(long userId, String defaultUserPassword);
	public void unlockAppUser( long userId );
	public void updateAppUser(AppUser user);
	public void updateAppUserPassword(long userId, String textPassword, int minPasswordLength);
	public ViewAppUserSession validateLogin(String userName, String password);

	//============================================================================
	//
	// AppUser_AppRole_Relationship
	//
	//============================================================================
	public List<Long> getAppUserRoleIdList(long userId);

	//============================================================================
	//
	// AppUser_DepositInstitution_Relationship
	//
	//============================================================================
	public List<Long> getAppUserDepositInstitutionIdList(long userId);

	//============================================================================
	//
	// AppUser_SourceInsitution_Relationship
	//
	//============================================================================
	public List<Long> getAppUserProgramIdList(long userId);
	public List<Long> getAppUserSourceInstitutionIdList(long userId);
	
	//============================================================================
	//
	// AppUserType
	//
	//============================================================================
    public List<AppUserType> getAppUserTypeList();

    //============================================================================
	//
    // ChallengeQuestion
    //
	//============================================================================
	public List<ChallengeQuestion> getChallengeQuestionList(); 
    public AppUserChallengeQuestionRelationship[] getChallengeResponseArray(long userId);
	public List<AppUserChallengeQuestionRelationship> getChallengeResponseList(long userId);
    public ChallengeQuestion getRandomChallengeQuestion(long userId);
	public void resetChallengeQuestions(long userId);	
	public void updateUserChallengeQuestions(long userId, AppUserChallengeQuestionRelationship[] questions);
	public boolean validateChallengeReponse(long userId, long questionId, String questionResponse);
    
	//============================================================================
	//
	// Contact
	//
	//============================================================================
	public long addContact(Contact contact);
	public double addContactNew(Contact contact);
	public int deleteContact(long contactId);
	public Contact getContactDetails(long contactId);
	public List<Contact> getContactList(AppUserFilter appUserFilter);
	public List<ContactInstitutionType> getContactInstitutionTypeList(); 
	public void updateContact(Contact contact);
	public void insertContactToEnvelopeType(Contact contact);
	public void updateContactToEnvelopeType(Contact contact, List<Long> EnvelopeId);
	public ContactsTable getContacts();
	public ContactsTable getContacts(AppUserFilter appUserFilter);
	public MandatoryContactInfoView getMandatoryContactInfo( long contactId );
	public void updateMandatoryContactInfo( MandatoryContactInfoView contactData, long userId );
	
	//============================================================================
	//
	// Envelopes
	//
	//============================================================================
	public List<Envelopes> getEnvelopesByDepositInstitutionList();
	public List<ViewContactEnvelopeRelationship> getEnvelopesByDepositInstitutionList(Contact contact);
	public List<ViewContactEnvelopeRelationship> getEnvelopesBySourceInstitutionList(Contact contact);
	public List<Envelopes> getEnvelopesBySourceInstitutionList();
	//============================================================================
	//
	// Email Sender
	//
	//============================================================================
	 public EmailSenderLogView getEmailSenderLogView();
}
