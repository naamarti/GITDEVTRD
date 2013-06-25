@echo off

:: ===========================================================================
:: Total Bank Solutions
::
:: Total Bank Solutions Deployment Script
:: 
:: This script will be used as the main deployment script for TBS
:: applications.  Using menus it will determine which other scripts need to 
:: be run, and it will drive the entire deployment - both database and 
:: application code.
:: forcing ansi formatting
:: ===========================================================================

call .\util\DMS_SetEnvironment.cmd
if ERRORLEVEL 1 (goto :EXIT_FAILURE)

SET DEPLOY_SCRIPT_VERSION=1.0
if "%DEPLOYMENT_HOME%"=="C:\tbs_autorecon\deployment\util" (
	SET DEPLOY_SCRIPT_VERSION=1.0
) else if "%DEPLOYMENT_HOME%"=="D:\DMS\DPL.A\deployment\util" (
	SET DEPLOY_SCRIPT_VERSION=2.0
)

if "%DEPLOY_SCRIPT_VERSION%"=="1.0" (
	set DEPLOY_UTIL_DIR=%DEPLOYMENT_HOME%
	set DEPLOY_DIR=%DMS_ENV_DIR%\scripts
	set DEPLOY_LOG_PATH=%DEPLOYMENT_HOME%\..\logs\deployment
) else (
	set DEPLOY_UTIL_DIR=%DEPLOYMENT_HOME%
	set DEPLOY_DIR=%CD%
	set DEPLOY_LOG_PATH=%DEPLOYMENT_HOME%\..\logs\deployment
)

set DEPLOY_LOG_NAME=Deploy.log
set DEPLOY_LOGGER=%DEPLOY_UTIL_DIR%\deployLog.cmd
call %DEPLOY_UTIL_DIR%\GetTimeStamp_forLog.cmd
call %DEPLOY_UTIL_DIR%\GetTimeStamp.cmd

set DEPLOY_CODE_TYPE=JAVA
set DEPLOY_TO_ENV=LOCAL
set SCRIPT_TITLE=Total Bank Solutions Deployment Script

:: *************************************************************************************************
:CHOOSE_DEPLOYMENT_TYPE
:: *************************************************************************************************
cls
echo --------------------------------------------------------------------------
echo %SCRIPT_TITLE%:
echo --------------------------------------------------------------------------
echo User Name:                     %USERNAME%
echo Computer Name:                 %COMPUTERNAME%
echo Source Control Environment:    %DMS_ENV%
echo Deploy Script Version:         %DEPLOY_SCRIPT_VERSION%
echo Deploy Dir:                    %DEPLOY_DIR%
echo Deploy Util Dir:               %DEPLOY_UTIL_DIR%
echo Deploy Logs Dir:               %DEPLOY_LOG_PATH%
echo --------------------------------------------------------------------------
echo.
echo Deployment Type:
echo.
echo Enter [1] to deploy TRADING DATABASE
echo Enter [2] to deploy TRADING APPLICATION.JAVA
echo.
echo Enter [x] to exit
echo.
echo --------------------------------------------------------------------------

:SET_DEPLOYMENT_TYPE
set CHOICE=
set /p CHOICE=?
if not "%CHOICE%"=="" set CHOICE=%CHOICE:~0,1%
	
if "%CHOICE%"=="1" (
	set DEPLOY_CODE_TYPE=TRADING.DATABASE
	call %DEPLOY_DIR%\deploy\database\deployDatabase.cmd
	if ERRORLEVEL 1 (goto :EXIT_FAILURE)
) else if "%CHOICE%"=="2" (
	set DEPLOY_CODE_TYPE=TRADING.APPLICATION.JAVA
	call %DEPLOY_DIR%\deploy\java\deployJava.cmd
	if ERRORLEVEL 1 (goto :EXIT_FAILURE)
	
) else if "%CHOICE%"=="x" (
	exit 0
) else (
	goto :SET_DEPLOYMENT_TYPE
)


:: *************************************************************************************************
:EXIT_SUCCESS
:: *************************************************************************************************
color FA
set EXIT_MESSAGE=Completed Successfully.
goto :EXIT


:: *************************************************************************************************
:EXIT_FAILURE
:: *************************************************************************************************
color FC
set EXIT_MESSAGE=Process Failed!!


:: *************************************************************************************************
:EXIT
:: *************************************************************************************************
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "**********************************************"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "%SCRIPT_TITLE% - %DEPLOY_CODE_TYPE% - %EXIT_MESSAGE%"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "**********************************************"
call %DEPLOY_LOGGER% ""
pause

goto :eof

