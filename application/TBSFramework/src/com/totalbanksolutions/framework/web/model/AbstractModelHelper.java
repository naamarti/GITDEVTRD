package com.totalbanksolutions.framework.web.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.totalbanksolutions.framework.model.DefaultModelTable;
import com.totalbanksolutions.framework.model.database.view.AppNavigationView;
import com.totalbanksolutions.framework.model.database.view.SecondaryMenuView;
import com.totalbanksolutions.framework.web.bean.WebForm;
import com.totalbanksolutions.framework.web.bean.WebTable;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.framework.bean.database.view.ViewAppUserSession;

public abstract class AbstractModelHelper {

	protected ModelAndView mv						= null;
	
	private final String AJAX_QUERY_RESULTS			= "queryResults";
	private final String AJAX_QUERY_TYPE			= "ajaxQueryType";
	private final String AJAX_STATUS				= "ajaxStatus";
	private final String ENVIRONMENT_NAME			= "environmentName";
	private final String FORM_1						= "form1";
	private final String FORM_2						= "form2";
	private final String FORM_DATA_1				= "formData1";
	private final String FORM_DATA_2				= "formData2";
	private final String MENU_PRIMARY				= "menuPrimary";
	private final String MENU_SECONDARY_LEVEL1		= "menuSecondaryLevel1List";
	private final String MENU_SECONDARY_LEVEL2		= "menuSecondaryLevel2List";
	private final String SCREEN_NAME				= "screenName";
	private final String SESSION_MESSAGE_DEBUG		= "sessionMessageDebug";
	private final String SESSION_MESSAGE_ERROR		= "sessionMessageError";
	private final String SESSION_MESSAGE_INFO		= "sessionMessageInfo";
	private final String SESSION_MESSAGE_SUCCESS	= "sessionMessageSuccess";
	private final String SESSION_MESSAGE_WARNING	= "sessionMessageWarning";
	private final String SESSION_USER_ACTION		= "sessionUserAction";
	private final String TABLE_1					= "table1";
	private final String TABLE_2					= "table2";
	private final String USER						= "user";
	private final String VERSION_NUMBER				= "versionNumber";

	public AbstractModelHelper( ModelAndView mv )
	{
		this.mv = mv;
		if( !mv.getModel().containsKey("AbstractModelHelper") )
		{
			initialize();
			mv.getModel().put("AbstractModelHelper", true);
		}
	}

	private void initialize()
	{
		this.setAjaxQueryResults( new ArrayList<Map<String,String>>() );
		this.setAjaxQueryType("");
		this.setAjaxStatus(false);
		this.setEnvironmentName("");
		this.setForm1( new WebForm() );
		this.setForm2( new WebForm() );
		this.setFormData1(null);
		this.setFormData2(null);
		this.setMenuPrimary( new AppNavigationView() );
		this.setMenuSecondaryLevel1( new ArrayList<SecondaryMenuView>() );
		this.setMenuSecondaryLevel2( new ArrayList<SecondaryMenuView>() );
		this.setMessageDebug("");
		this.setMessageError("");
		this.setMessageInfo("");
		this.setMessageSuccess("");
		this.setMessageWarning("");
		this.setScreenName("");
		this.setSessionUserAction("");
		this.setTable1(null);
		this.setTable2(null);
		this.setUser( null );
		this.setVersionNumber("");
	}
	
	// Getters
	public Object get( String key )
	{
		return mv.getModel().get(key);
	}
	
	public List<Map<String, String>> getAjaxQueryResults() {
		return (List<Map<String, String>>)this.get( this.AJAX_QUERY_RESULTS );
	}
	
	public String getAjaxQueryType() {
		return (String)this.get( this.AJAX_QUERY_TYPE );
	}
	
	public boolean getAjaxStatus() {
		return (Boolean)this.get( this.AJAX_STATUS );
	}
	
	public String getEnvironmentName() {
		return (String)this.get( this.ENVIRONMENT_NAME );
	}

	public WebForm getForm1() {
		return (WebForm)this.get( this.FORM_1 );
	}

	public WebForm getForm2() {
		return (WebForm)this.get( this.FORM_2 );
	}

	public DefaultModelTable getFormData1() {
		return (DefaultModelTable)this.get( this.FORM_DATA_1 );
	}

	public DefaultModelTable getFormData2() {
		return (DefaultModelTable)this.get( this.FORM_DATA_2 );
	}
	
	public AppNavigationView getMenuPrimary() {
		return (AppNavigationView)this.get( this.MENU_PRIMARY );
	}
	
	public List<SecondaryMenuView> getMenuSecondaryLevel1() {
		return (List<SecondaryMenuView>)this.get( this.MENU_SECONDARY_LEVEL1 ) ;
	}
	
	public List<SecondaryMenuView> getMenuSecondaryLevel2() {
		return (List<SecondaryMenuView>)this.get( this.MENU_SECONDARY_LEVEL2 ) ;
	}
	
	public String getMessageDebug() {
		return (String)this.get( this.SESSION_MESSAGE_DEBUG );
	}
	
	public String getMessageError() {
		return (String)this.get( this.SESSION_MESSAGE_ERROR );
	}
	
	public String getMessageInfo() {
		return (String)this.get( this.SESSION_MESSAGE_INFO );
	}
	
	public String getMessageSuccess() {
		return (String)this.get( this.SESSION_MESSAGE_SUCCESS );
	}
	
	public String getMessageWarning() {
		return (String)this.get( this.SESSION_MESSAGE_WARNING );
	}
	
	public String getScreenName() {
		return (String)this.get( this.SCREEN_NAME );
	}
	
	public String getSessionUserAction() {
		return (String)this.get( this.SESSION_USER_ACTION );
	}

	public WebTable getTable1() {
		return (WebTable)this.get( this.TABLE_1 );
	}

	public WebTable getTable2() {
		return (WebTable)this.get( this.TABLE_2 );
	}
	
	public ViewAppUserSession getUser() {
		return (ViewAppUserSession)this.get( this.USER );
	}
		
	public String getVersionNumber() {
		return (String)this.get( this.VERSION_NUMBER );
	}
	
	public String getViewName() {
		return mv.getViewName();
	}
	
	
	// Setters
	public void put( String key, Object value )
	{
		mv.getModel().put( key, value );
	}
	
	public void addAjaxResultsRow( Map<String,String> row )
	{
		List<Map<String, String>> ajaxQueryResults = getAjaxQueryResults();
		ajaxQueryResults.add(row);
	}	
	
	public void setAjaxQueryResults( List<Map<String, String>> ajaxQueryResults ) {
		this.put( this.AJAX_QUERY_RESULTS, ajaxQueryResults );		
	}
	
	public void setAjaxQueryType( String ajaxQueryType ) {
		this.put( this.AJAX_QUERY_TYPE, ajaxQueryType );
	}
	
	public void setAjaxStatus( boolean ajaxStatus ) {
		this.put( this.AJAX_STATUS, ajaxStatus );
	}
	
	public void setEnvironmentName( String environmentName ) {
		this.put( this.ENVIRONMENT_NAME, environmentName );
	}

	public void setForm1( WebForm form ) {
		this.put( this.FORM_1, form );
	}	
	
	public void setForm2( WebForm form ) {
		this.put( this.FORM_2, form );
	}	
	
	public void setFormData1( DefaultModelTable formData ) {
		this.put( this.FORM_DATA_1, formData );
	}	
	
	public void setFormData2( DefaultModelTable formData ) {
		this.put( this.FORM_DATA_2, formData );
	}	
		
	public void setMenuPrimary( AppNavigationView menuPrimary ) {
		this.put( this.MENU_PRIMARY, menuPrimary );
	}
	
	public void setMenuSecondaryLevel1( List<SecondaryMenuView> menuSecondaryLevel1 ) {
		this.put( this.MENU_SECONDARY_LEVEL1, menuSecondaryLevel1 );
	}
	
	public void setMenuSecondaryLevel2( List<SecondaryMenuView> menuSecondaryLevel2 ) {
		this.put( this.MENU_SECONDARY_LEVEL2, menuSecondaryLevel2 );
	}
	
	public void setMessageDebug( String debugMessage ) {
		this.put( this.SESSION_MESSAGE_DEBUG, debugMessage );
	}
	
	public void setMessageError( String errorMessage ) {
		this.put( this.SESSION_MESSAGE_ERROR, errorMessage );
	}
	
	public void setMessageInfo( String infoMessage ) {
		this.put( this.SESSION_MESSAGE_INFO, infoMessage );
	}
	
	public void setMessageSuccess( String successMessage ) {
		this.put( this.SESSION_MESSAGE_SUCCESS, successMessage );
	}
	
	public void setMessageWarning( String warningMessage ) {
		this.put( this.SESSION_MESSAGE_WARNING, warningMessage );
	}
	
	public void setScreenName( String screenName ) {
		this.put( this.SCREEN_NAME, screenName );
	}
	
	public void setSessionUserAction( String sessionUserAction ) {
		this.put( this.SESSION_USER_ACTION, sessionUserAction );
	}
	
	public void setTable1( WebTable t ) {
		this.put( this.TABLE_1, t);
	}

	public void setTable2( WebTable t ) {
		this.put( this.TABLE_2, t);
	}

	public void setUser( ViewAppUserSession user ) {
		this.put( this.USER, user );
	}
	
	public void setVersionNumber( String versionNumber ) {
		this.put( this.VERSION_NUMBER, versionNumber );
	}
	
	public void setViewName( String viewName ) {
		mv.setViewName( viewName );
	}
	

}
