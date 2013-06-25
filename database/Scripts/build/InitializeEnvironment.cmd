:: =================================================================================================
:: Created:   16 Jun 2012 VC
:: Modified:  
::
:: This script is called by the main DMS deployment script, when initializing  
:: a new environment after copying the database from Production.
:: 
:: =================================================================================================
@echo off

set DB_ROOT_DIR=%DMS_ENV_DIR%\database
set UTIL_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\util
set TEMP_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\temp
set ENVIRONMENT_SETUP_DIR=%DMS_ENV_DIR%\database\Scripts\Releases\EnvironmentSetup
set DEPLOY_FAILURE=0

if not exist %TEMP_SCRIPT_DIR% (mkdir %TEMP_SCRIPT_DIR%)

call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "INITENV: 01"
call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.TBS_Common:"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd TBS_Common %ENVIRONMENT_SETUP_DIR%\EnvironmentSetup.sql.%DMS_DEPLOY_TO_ENV%
if ERRORLEVEL 1 (
	call :DeployFailure
)

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd TBS_Common %ENVIRONMENT_SETUP_DIR%\EnvironmentSetup.Checklist.non_prod.sql
if ERRORLEVEL 1 (
	call :DeployFailure
)

if EXIST %TEMP_SCRIPT_DIR% (rmdir %TEMP_SCRIPT_DIR% /S /Q)

if "%DEPLOY_FAILURE%"=="1" (EXIT /b 1)

goto :eof

::---------------------------------------------------------------------------------------
:DeployFailure
::---------------------------------------------------------------------------------------
set DEPLOY_FAILURE=1
color FC
goto:eof

