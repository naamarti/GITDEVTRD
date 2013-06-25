@echo off
:: forcing ansi formatting
set ANT_CMD="%JAVA_HOME%\jre\bin\java" -classpath "%ANT_HOME%\lib\ant-launcher.jar" "-Dant.home=%ANT_HOME%" org.apache.tools.ant.launch.Launcher

:: *************************************************************************************************
:CHOOSE_DEPLOY_TO_ENVIRONMENT
:: *************************************************************************************************
set CURRENT_STEP=CHOOSE_DEPLOY_TO_ENVIRONMENT
cls
echo --------------------------------------------------------------------------
echo %SCRIPT_TITLE%:
echo --------------------------------------------------------------------------
echo User Name:                     %USERNAME%
echo Computer Name:                 %COMPUTERNAME%
echo Source Control Environment:    %DMS_ENV%
echo Deployment Type:               %DEPLOY_CODE_TYPE%
echo --------------------------------------------------------------------------
echo.
echo Deploy To Environment:
echo.
echo Enter [1]  for LOCALHOST

echo Enter [3]  for DEV_A        on %DMS_APPSERVER1_DEV_A%
echo Enter [4]  for DEV_B        on %DMS_APPSERVER1_DEV_B%
echo Enter [5]  for DEV_C        on %DMS_APPSERVER1_DEV_C%
echo Enter [6]  for DEV_D        on %DMS_APPSERVER1_DEV_D%
echo Enter [7]  for DEV_E        on %DMS_APPSERVER1_DEV_E%

echo Enter [8]  for QAT_A        on %DMS_APPSERVER1_QAT_A%

echo Enter [9]  for UAT_A        on %DMS_APPSERVER1_UAT_A%
echo Enter [10] for UAT_B        on %DMS_APPSERVER1_UAT_B%

echo Enter [11] for PROD_A_PRI   on %DMS_APPSERVER1_PRD_A_PRI%
echo Enter [12] for PROD_A_BAK   on %DMS_APPSERVER1_PRD_A_BAK%
echo Enter [13] for PROD_B_PRI   on %DMS_APPSERVER1_PRD_B_PRI%
echo Enter [14] for PROD_B_BAK   on %DMS_APPSERVER1_PRD_B_BAK%

echo Enter [r] to return to previous screen
echo Enter [x] to exit
echo.
echo --------------------------------------------------------------------------

set DMS_DEPLOY_TO_ENV=
set DMS_DEPLOY_TO_SQLSERVER=
set DMS_DEPLOY_TO_APPSERVER=
set DMS_DEPLOY_TO_WEBSERVER.1=
set DMS_DEPLOY_TO_WEBSERVER.2=
set DMS_DEPLOY_TO_WEBSERVER.3=
set DMS_DEPLOY_TO_WEBSERVER_SHARE=
set DMS_DEPLOY_TO_APPSERVER_SHARE=

set CHOICE=
set /p CHOICE=?
if not "%CHOICE%"=="" set CHOICE=%CHOICE:~0,2%
if "%CHOICE%"=="1" (
	set DMS_DEPLOY_TO_ENV=local
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_LOCAL%
	set DMS_DEPLOY_TO_SQLSERVER=LOCALHOST
	set DMS_DEPLOY_TO_APPSERVER=C:
	set DMS_DEPLOY_TO_WEBSERVER.1=C:
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=tbs_autorecon
	set DMS_DEPLOY_TO_APPSERVER_SHARE=tbs_autorecon
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_LOCAL%
	set TOMCAT_NAME=%DMS_APPNAME_DEV_A%
) else if "%CHOICE%"=="3" (
	set DMS_DEPLOY_TO_ENV=dev_a
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_DEV%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_DEV_A%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_DEV_A%
	set DMS_DEPLOY_TO_WEBSERVER.1=%DMS_WEBSERVER1_DEV_A%
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=%DMS_WEBSERVER_JAVA_SHARE_DEV_A%
	set DMS_DEPLOY_TO_APPSERVER_SHARE=%DMS_APPSERVER_SHARE_DEV_A%
	set TOMCAT_NAME=%DMS_APPNAME_DEV_A%
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_DEV_A%
	set DMS_URL_BASEADDR=%DMS_URLBASEADDR_NET2_DEV_A%
) else if "%CHOICE%"=="4" (
	set DMS_DEPLOY_TO_ENV=dev_b
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_DEV%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_DEV_B%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_DEV_B%
	set DMS_DEPLOY_TO_WEBSERVER.1=%DMS_WEBSERVER1_DEV_B%
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=%DMS_WEBSERVER_JAVA_SHARE_DEV_B%
	set DMS_DEPLOY_TO_APPSERVER_SHARE=%DMS_APPSERVER_SHARE_DEV_B%
	set TOMCAT_NAME=%DMS_APPNAME_DEV_B%
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_DEV_B%
	set DMS_URL_BASEADDR=%DMS_URLBASEADDR_NET2_DEV_B%
) else if "%CHOICE%"=="5" (
	set DMS_DEPLOY_TO_ENV=dev_c
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_DEV%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_DEV_C%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_DEV_C%
	set DMS_DEPLOY_TO_WEBSERVER.1=%DMS_WEBSERVER1_DEV_C%
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=%DMS_WEBSERVER_JAVA_SHARE_DEV_C%
	set DMS_DEPLOY_TO_APPSERVER_SHARE=%DMS_APPSERVER_SHARE_DEV_C%
	set TOMCAT_NAME=%DMS_APPNAME_DEV_C%
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_DEV_C%
	set DMS_URL_BASEADDR=%DMS_URLBASEADDR_NET2_DEV_C%
) else if "%CHOICE%"=="6" (
	set DMS_DEPLOY_TO_ENV=dev_d
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_DEV%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_DEV_D%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_DEV_D%
	set DMS_DEPLOY_TO_WEBSERVER.1=%DMS_WEBSERVER1_DEV_D%
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=%DMS_WEBSERVER_JAVA_SHARE_DEV_D%
	set DMS_DEPLOY_TO_APPSERVER_SHARE=%DMS_APPSERVER_SHARE_DEV_D%
	set TOMCAT_NAME=%DMS_APPNAME_DEV_D%
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_DEV_D%
	set DMS_URL_BASEADDR=%DMS_URLBASEADDR_NET2_DEV_D%
) else if "%CHOICE%"=="7" (
	set DMS_DEPLOY_TO_ENV=dev_e
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_DEV%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_DEV_E%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_DEV_E%
	set DMS_DEPLOY_TO_WEBSERVER.1=%DMS_WEBSERVER1_DEV_E%
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=%DMS_WEBSERVER_JAVA_SHARE_DEV_E%
	set DMS_DEPLOY_TO_APPSERVER_SHARE=%DMS_APPSERVER_SHARE_DEV_E%
	set TOMCAT_NAME=%DMS_APPNAME_DEV_E%
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_DEV_E%
	set DMS_URL_BASEADDR=%DMS_URLBASEADDR_NET2_DEV_E%
) else if "%CHOICE%"=="8" (
	set DMS_DEPLOY_TO_ENV=qat_a
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_QA%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_QAT_A%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_QAT_A%
	set DMS_DEPLOY_TO_WEBSERVER.1=%DMS_WEBSERVER1_QAT_A%
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=%DMS_WEBSERVER_JAVA_SHARE_QAT_A%
	set DMS_DEPLOY_TO_APPSERVER_SHARE=%DMS_APPSERVER_SHARE_QAT_A%
	set TOMCAT_NAME=%DMS_APPNAME_QAT_A%
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_QAT_A%
	set DMS_URL_BASEADDR=%DMS_URLBASEADDR_NET2_QAT_A%
) else if "%CHOICE%"=="9" (
	set DMS_DEPLOY_TO_ENV=uat_a
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_UAT%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_UAT_A%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_UAT_A%
	set DMS_DEPLOY_TO_WEBSERVER.1=%DMS_WEBSERVER1_UAT_A%
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=%DMS_WEBSERVER_JAVA_SHARE_UAT_A%
	set DMS_DEPLOY_TO_APPSERVER_SHARE=%DMS_APPSERVER_SHARE_UAT_A%
	set TOMCAT_NAME=%DMS_APPNAME_UAT_A%
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_UAT_A%
	set DMS_URL_BASEADDR=%DMS_URLBASEADDR_NET2_UAT_A%
) else if "%CHOICE%"=="10" (
	set DMS_DEPLOY_TO_ENV=uat_b
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_UAT%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_UAT_B%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_UAT_B%
	set DMS_DEPLOY_TO_WEBSERVER.1=%DMS_WEBSERVER1_UAT_B%
	set DMS_DEPLOY_TO_WEBSERVER.2=%DMS_WEBSERVER2_UAT_B%
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=%DMS_WEBSERVER_JAVA_SHARE_UAT_B%
	set DMS_DEPLOY_TO_APPSERVER_SHARE=%DMS_APPSERVER_SHARE_UAT_B%
	set TOMCAT_NAME=%DMS_APPNAME_UAT_B%
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_UAT_B%
	set DMS_URL_BASEADDR=%DMS_URLBASEADDR_NET2_UAT_B%
) else if "%CHOICE%"=="11" (
	set DMS_DEPLOY_TO_ENV=prd_a_pri
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_PROD%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_PRD_A_PRI%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_PRD_A_PRI%
	set DMS_DEPLOY_TO_WEBSERVER.1=%DMS_WEBSERVER1_PRD_A_PRI%
	set DMS_DEPLOY_TO_WEBSERVER.2=%DMS_WEBSERVER2_PRD_A_PRI%
	set DMS_DEPLOY_TO_WEBSERVER.3=%DMS_WEBSERVER3_PRD_A_PRI%
	set DMS_DEPLOY_TO_WEBSERVER.4=%DMS_WEBSERVER4_PRD_A_PRI%
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=%DMS_WEBSERVER_JAVA_SHARE_PRD_A%
	set DMS_DEPLOY_TO_APPSERVER_SHARE=%DMS_APPSERVER_SHARE_PRD_A%
	set TOMCAT_NAME=%DMS_APPNAME_PRD_A_PRI%
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_PRD_A%
	set DMS_URL_BASEADDR=%DMS_URLBASEADDR_NET2_PRD_A%
) else if "%CHOICE%"=="12" (
	set DMS_DEPLOY_TO_ENV=prd_a_bak
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_PROD%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_PRD_A_BAK%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_PRD_A_BAK%
	set DMS_DEPLOY_TO_WEBSERVER.1=%DMS_WEBSERVER1_PRD_A_BAK%
	set DMS_DEPLOY_TO_WEBSERVER.2=%DMS_WEBSERVER2_PRD_A_BAK%
	set DMS_DEPLOY_TO_WEBSERVER.3=%DMS_WEBSERVER3_PRD_A_BAK%
	set DMS_DEPLOY_TO_WEBSERVER.4=%DMS_WEBSERVER4_PRD_A_BAK%
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=%DMS_WEBSERVER_JAVA_SHARE_PRD_A%
	set DMS_DEPLOY_TO_APPSERVER_SHARE=%DMS_APPSERVER_SHARE_PRD_A%
	set TOMCAT_NAME=%DMS_APPNAME_PRD_A_BAK%
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_PRD_A%
	set DMS_URL_BASEADDR=%DMS_URLBASEADDR_NET2_PRD_A%
) else if "%CHOICE%"=="13" (
	set DMS_DEPLOY_TO_ENV=prd_b_pri
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_PROD%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_PRD_B_PRI%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_PRD_B_PRI%
	set DMS_DEPLOY_TO_WEBSERVER.1=%DMS_WEBSERVER1_PRD_B_PRI%
	set DMS_DEPLOY_TO_WEBSERVER.2=%DMS_WEBSERVER2_PRD_B_PRI%
	set DMS_DEPLOY_TO_WEBSERVER.3=%DMS_WEBSERVER3_PRD_B_PRI%
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=%DMS_WEBSERVER_JAVA_SHARE_PRD_B%
	set DMS_DEPLOY_TO_APPSERVER_SHARE=%DMS_APPSERVER_SHARE_PRD_B%
	set TOMCAT_NAME=%DMS_APPNAME_PRD_B_PRI%
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_PRD_B%
	set DMS_URL_BASEADDR=%DMS_URLBASEADDR_NET2_PRD_B%
) else if "%CHOICE%"=="14" (
	set DMS_DEPLOY_TO_ENV=prd_b_bak
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_PROD%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_PRD_B_BAK%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_PRD_B_BAK%
	set DMS_DEPLOY_TO_WEBSERVER.1=%DMS_WEBSERVER1_PRD_B_BAK%
	set DMS_DEPLOY_TO_WEBSERVER.2=%DMS_WEBSERVER2_PRD_B_BAK%
	set DMS_DEPLOY_TO_WEBSERVER.3=%DMS_WEBSERVER3_PRD_B_BAK%
	set DMS_DEPLOY_TO_WEBSERVER_SHARE=%DMS_WEBSERVER_JAVA_SHARE_PRD_B%
	set DMS_DEPLOY_TO_APPSERVER_SHARE=%DMS_APPSERVER_SHARE_PRD_B%
	set TOMCAT_NAME=%DMS_APPNAME_PRD_B_BAK%
	set DMS_HOME_LOG=%DMS_LOG_BASEADDR_JAVA_PRD_B%
	set DMS_URL_BASEADDR=%DMS_URLBASEADDR_NET2_PRD_B%
) else if "%CHOICE%"=="r" (
	call %DEPLOY_DIR%\deploy.cmd :CHOOSE_DEPLOYMENT_TYPE
	exit 0
) else if "%CHOICE%"=="x" (
	exit 0
) else (
	goto %CURRENT_STEP%
	goto:eof
)
call :ValidateDeployToEnv IsValidEnv
if "%IsValidEnv%"=="0" (
	goto %CURRENT_STEP%
	goto:eof
)

:: *************************************************************************************************
:CHOOSE_SQL_LOGIN_TYPE
:: *************************************************************************************************
set CURRENT_STEP=CHOOSE_SQL_LOGIN_TYPE
set PREVIOUS_STEP=CHOOSE_DEPLOY_TO_ENVIRONMENT
cls
echo --------------------------------------------------------------------------
echo %SCRIPT_TITLE%:
echo --------------------------------------------------------------------------
echo User Name:                     %USERNAME%
echo Computer Name:                 %COMPUTERNAME%
echo Source Control Environment:    %DMS_ENV%
echo Deployment Type:               %DEPLOY_CODE_TYPE%
echo Deploy To Environment:         %DMS_DEPLOY_TO_ENV%
echo --------------------------------------------------------------------------
echo.
echo SQL Server Login Type:
echo.
echo Enter [1] for Windows Integrated Login
echo Enter [2] for SQL Server Login
echo Enter [r] to return to previous screen
echo Enter [x] to exit
echo.
echo --------------------------------------------------------------------------

set DMS_SQL_IS_INTEGRATED_LOGIN=
set DMS_SQL_USER=
set DMS_SQL_PASSWORD=
set DMS_SQL_JAVA_CONNECTION_STRING=

set CHOICE=
set /p CHOICE=?
if not "%CHOICE%"=="" set CHOICE=%CHOICE:~0,1%
if "%CHOICE%"=="1" (
	set DMS_SQL_IS_INTEGRATED_LOGIN=1
	set DMS_SQL_JAVA_CONNECTION_STRING=integratedSecurity=true
	set DMS_SQL_USER=******
	set DMS_SQL_PASSWORD=******
) else if "%CHOICE%"=="2" (
	set DMS_SQL_IS_INTEGRATED_LOGIN=0
	set DMS_SQL_JAVA_CONNECTION_STRING=integratedSecurity=false
	set DMS_SQL_USER=tbsadmin
	set DMS_SQL_PASSWORD=tbsadmin
) else if "%CHOICE%"=="r" (
	goto %PREVIOUS_STEP%
	goto:eof
) else if "%CHOICE%"=="x" (
	exit 0
) else (
	goto %CURRENT_STEP%
	goto:eof
)

:: *************************************************************************************************
:CHOOSE_DEPLOYMENT_ACTION
:: *************************************************************************************************
set CURRENT_STEP=CHOOSE_DEPLOYMENT_ACTION
set PREVIOUS_STEP=CHOOSE_SQL_LOGIN_TYPE
cls
echo --------------------------------------------------------------------------
echo %SCRIPT_TITLE%:
echo --------------------------------------------------------------------------
echo User Name:                     %USERNAME%
echo Computer Name:                 %COMPUTERNAME%
echo Source Control Environment:    %DMS_ENV%
echo Deployment Type:               %DEPLOY_CODE_TYPE%
echo Deploy To Environment:         %DMS_DEPLOY_TO_ENV%
echo Deploy To AppServer:           %DMS_DEPLOY_TO_APPSERVER%
echo Deploy To AppServer Share:     %DMS_DEPLOY_TO_APPSERVER_SHARE%
echo Deploy To WebServer(s):        %DMS_DEPLOY_TO_WEBSERVER.1%  %DMS_DEPLOY_TO_WEBSERVER.2%  %DMS_DEPLOY_TO_WEBSERVER.3%  %DMS_DEPLOY_TO_WEBSERVER.4%
echo Deploy To WebServer Share:     %DMS_DEPLOY_TO_WEBSERVER_SHARE%
echo Database Server:               %DMS_DEPLOY_TO_SQLSERVER%
echo Database Integrated Login:     %DMS_SQL_IS_INTEGRATED_LOGIN%
echo --------------------------------------------------------------------------
echo.
echo Deployment Action:
echo.
echo Enter [1] to Run Clean
echo Enter [2] to Run Deploy
echo Enter [3] to Run Clean and Deploy
echo Enter [r] to return to previous screen
echo Enter [x] to exit
echo.
echo --------------------------------------------------------------------------

set CHOICE=
set /p CHOICE=?
if not "%CHOICE%"=="" set CHOICE=%CHOICE:~0,1%
if "%CHOICE%"=="1" (
	set DEPLOYMENT_ACTION=CLEAN
	set RUN_CLEAN=1
	set RUN_DEPLOY=0
) else if "%CHOICE%"=="2" (
	set DEPLOYMENT_ACTION=DEPLOY
	set RUN_CLEAN=0
	set RUN_DEPLOY=1
) else if "%CHOICE%"=="3" (
	set DEPLOYMENT_ACTION=CLEAN_AND_DEPLOY
	set RUN_CLEAN=1
	set RUN_DEPLOY=1
) else if "%CHOICE%"=="r" (
	goto %PREVIOUS_STEP%
	goto:eof
) else if "%CHOICE%"=="x" (
	exit 0
) else (
	goto %CURRENT_STEP%
	goto:eof
)

:: *************************************************************************************************
:CHOOSE_BEGIN_CONFIRM
:: *************************************************************************************************
set CURRENT_STEP=CHOOSE_BEGIN_CONFIRM
set PREVIOUS_STEP=CHOOSE_DEPLOYMENT_ACTION
cls
echo --------------------------------------------------------------------------
echo %SCRIPT_TITLE%:
echo --------------------------------------------------------------------------
echo User Name:                     %USERNAME%
echo Computer Name:                 %COMPUTERNAME%
echo Source Control Environment:    %DMS_ENV%
echo Deployment Type:               %DEPLOY_CODE_TYPE%
echo Deploy To Environment:         %DMS_DEPLOY_TO_ENV%
echo Deploy To AppServer:           %DMS_DEPLOY_TO_APPSERVER%
echo Deploy To AppServer Share:     %DMS_DEPLOY_TO_APPSERVER_SHARE%
echo Deploy To WebServer(s):        %DMS_DEPLOY_TO_WEBSERVER.1%  %DMS_DEPLOY_TO_WEBSERVER.2%  %DMS_DEPLOY_TO_WEBSERVER.3%  %DMS_DEPLOY_TO_WEBSERVER.4%
echo Deploy To WebServer Share:     %DMS_DEPLOY_TO_WEBSERVER_SHARE%
echo Database Server:               %DMS_DEPLOY_TO_SQLSERVER%
echo Database Integrated Login:     %DMS_SQL_IS_INTEGRATED_LOGIN%
echo Deployment Action:             %DEPLOYMENT_ACTION%
echo --------------------------------------------------------------------------
echo.
echo Ready to begin:
echo.
echo Enter [1] to continue
echo Enter [r] to return to previous screen
echo Enter [x] to exit
echo.
echo --------------------------------------------------------------------------

set CHOICE=
set /p CHOICE=?
if not "%CHOICE%"=="" set CHOICE=%CHOICE:~0,1%
if "%CHOICE%"=="1" (
	set BEGIN_CONFIRM=1
) else if "%CHOICE%"=="r" (
	goto %PREVIOUS_STEP%
	goto:eof
) else if "%CHOICE%"=="x" (
	exit 0
) else (
	goto %CURRENT_STEP%
	goto:eof
)

cls
call %DEPLOY_UTIL_DIR%\SetDeployLogFileName.cmd

call %DEPLOY_LOGGER% "--------------------------------------------------------------------------"
call %DEPLOY_LOGGER% "%SCRIPT_TITLE%:"
call %DEPLOY_LOGGER% "--------------------------------------------------------------------------"
call %DEPLOY_LOGGER% "User Name:                     %USERNAME%"
call %DEPLOY_LOGGER% "Computer Name:                 %COMPUTERNAME%"
call %DEPLOY_LOGGER% "Source Control Environment:    %DMS_ENV%"
call %DEPLOY_LOGGER% "Deployment Type:               %DEPLOY_CODE_TYPE%"
call %DEPLOY_LOGGER% "Deploy To Environment:         %DEPLOY_TO_ENV%"
call %DEPLOY_LOGGER% "Deploy To AppServer:           %DMS_DEPLOY_TO_APPSERVER%"
call %DEPLOY_LOGGER% "Deploy To AppServer Share:     %DMS_DEPLOY_TO_APPSERVER_SHARE%"
call %DEPLOY_LOGGER% "Deploy To WebServer(s):        %DMS_DEPLOY_TO_WEBSERVER.1%  %DMS_DEPLOY_TO_WEBSERVER.2%  %DMS_DEPLOY_TO_WEBSERVER.3%  %DMS_DEPLOY_TO_WEBSERVER.4%"
call %DEPLOY_LOGGER% "Deploy To WebServer Share:     %DMS_DEPLOY_TO_WEBSERVER_SHARE%"
call %DEPLOY_LOGGER% "Database Server:               %DMS_DEPLOY_TO_SQLSERVER%"
call %DEPLOY_LOGGER% "Database Integrated Login:     %DMS_SQL_IS_INTEGRATED_LOGIN%"
call %DEPLOY_LOGGER% "Deployment Action:             %DEPLOYMENT_ACTION%"
call %DEPLOY_LOGGER% "Deployment Start Time:         %FORMATTED_TIMESTAMP_FOR_LOG%"
call %DEPLOY_LOGGER% "--------------------------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "Running Deploy..."
call %DEPLOY_LOGGER% ""

if not "%DMS_DEPLOY_TO_ENV%" == "local" (
	call %DEPLOY_UTIL_DIR%\TomcatStop.cmd
	if ERRORLEVEL 1 (
		exit /b 1
	)
)

echo No longer mapping to network drives... all connections now use WINDOWS AUTHENTICATION

cd %DMS_ENV_DIR%\application\TBSWebAppTrading

if "%RUN_CLEAN%"=="1" (
	call %ANT_CMD% clean
	if ERRORLEVEL 1 (
		call %DEPLOY_LOGGER% "*** Failed in Clean"
		exit /b 1
	)	
)

if "%RUN_DEPLOY%"=="1" (
	call %ANT_CMD% deploy
	if ERRORLEVEL 1 (
		call %DEPLOY_LOGGER% "*** Failed in Deploy"
		exit /b 1
	)	
)

if not "%DMS_DEPLOY_TO_ENV%" == "local" (
	call %DEPLOY_UTIL_DIR%\TomcatStart.cmd
	if ERRORLEVEL 1 (
		exit /b 1
	)
)

goto :eof


:: ---------------------------------------------------------------------------------------
:ValidateDeployToEnv
::    %~1: IsValid: Return Value (0 or 1)
:: ---------------------------------------------------------------------------------------
setlocal
set IsValid=1
if "%DMS_DEPLOY_TO_ENV_ORDER%" gtr "%DMS_ENV_ORDER%" (
	cls
	set IsValid=0
	echo.
	echo ***** WARNING! *****
	echo Invalid Environment Selection:
	echo.
	echo From PROD Source Control, you can deploy to PROD, UAT, QA, DEV, LOCAL.
	echo From QA   Source Control, you can deploy to .......... QA, DEV, LOCAL.
	echo From DEV  Source Control, you can deploy to .............. DEV, LOCAL.
	echo.	
	echo Press any key to reselect environment...
	echo.
	set /p CHOICE=?
)
endlocal&if not "%~1"=="" set "%~1=%IsValid%"
goto:eof

