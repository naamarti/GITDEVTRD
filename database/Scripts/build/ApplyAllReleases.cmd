:: =================================================================================================
:: Created:
:: Modified:  02 Apr 2012 NM 1042: log the deploy scripts
::
:: This script is called by the main DMS deployment script, when doing a
:: full release to the Production environment.
:: 
:: =================================================================================================
@echo off

:: DelayedExpansion allows variables referenced as !VAR! to be expanded per iteration of for loops
setlocal ENABLEDELAYEDEXPANSION

set DB_ROOT_DIR=%DMS_ENV_DIR%\database
set RELEASE_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\Releases
set UTIL_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\util
set TEMP_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\temp
set RELEASE_FILES=%TEMP_SCRIPT_DIR%\ReleaseFiles.txt
set ENVIRONMENT_SETUP_DIR=%DMS_ENV_DIR%\database\Scripts\Releases\EnvironmentSetup
set DB_COUNTER=0
set DEPLOY_FAILURE=0

if not exist %TEMP_SCRIPT_DIR% (mkdir %TEMP_SCRIPT_DIR%)

:: *************************************************************************************************
:DEPLOY_TRADING
:: *************************************************************************************************
if "%DEPLOY_DATABASE_TRADING%"=="0" goto :DEPLOY_EXIT
call :IncrementDBCounter

call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "APPLY: %DB_COUNTER%-a."
call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.TBS_TRADING:"
call %DEPLOY_LOGGER% "    Scripts that add Tables or modify Table structure"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd TBS_Trading %ENVIRONMENT_SETUP_DIR%\EnvironmentSetup.sql.%DMS_DEPLOY_TO_ENV%
if ERRORLEVEL 1 (
	call :DeployFailure
)

if exist %RELEASE_FILES% (del /Q %RELEASE_FILES%)
for /F %%F in (%RELEASE_SCRIPT_DIR%\ReleaseFileOrder.txt) do (
	call :WriteFileName %%F trading.Table %RELEASE_FILES%
)
if exist %RELEASE_FILES% (
	call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFileList.cmd TBS_Trading %RELEASE_FILES%
	if ERRORLEVEL 1 (
		call :DeployFailure
	)
)

call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "APPLY: %DB_COUNTER%-b."
call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.TBS_TRADING:"
call %DEPLOY_LOGGER% "    Drop and Recreate all Database Code objects"
call %DEPLOY_LOGGER% "    (Procedures, Views, Functions, Triggers)"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""

call %UTIL_SCRIPT_DIR%\DropAndRecreateAllDatabaseCodeObjects.cmd TBS_Trading
if ERRORLEVEL 1 (
	call :DeployFailure
)

call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "APPLY: %DB_COUNTER%-c."
call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.TBS_Trading:"
call %DEPLOY_LOGGER% "    Scripts that modify Data"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""

if exist %RELEASE_FILES% (del /Q %RELEASE_FILES%)
for /F %%F in (%RELEASE_SCRIPT_DIR%\ReleaseFileOrder.txt) do (
	call :WriteFileName %%F trading.Data %RELEASE_FILES%
)
if exist %RELEASE_FILES% (
	call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFileList.cmd TBS_Trading %RELEASE_FILES%
	if ERRORLEVEL 1 (
		call :DeployFailure
	)
)

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd TBS_Trading %ENVIRONMENT_SETUP_DIR%\EnvironmentSetup.sql.%DMS_DEPLOY_TO_ENV%
if ERRORLEVEL 1 (
	call :DeployFailure
)


:: *************************************************************************************************
:DEPLOY_EXIT
:: *************************************************************************************************

if EXIST %TEMP_SCRIPT_DIR% (rmdir %TEMP_SCRIPT_DIR% /S /Q)

if "%DEPLOY_FAILURE%"=="1" (EXIT /b 1)

goto :eof

::---------------------------------------------------------------------------------------
:DeployFailure
::---------------------------------------------------------------------------------------
set DEPLOY_FAILURE=1
color FC
goto:eof

::---------------------------------------------------------------------------------------
:IncrementDBCounter
::---------------------------------------------------------------------------------------
set /A DB_COUNTER=%DB_COUNTER%+1
goto:eof

::---------------------------------------------------------------------------------------
:WriteFileName
::---------------------------------------------------------------------------------------
setlocal
set FILENAME=%1
set PREFIX=%2
set OUTPUTFILE=%3
set INCLUDE=NO
if "%PREFIX%"=="trading.Table" (if %FILENAME:~0,13%==trading.Table (set INCLUDE=YES))
if "%PREFIX%"=="trading.Data" (if %FILENAME:~0,12%==trading.Data (set INCLUDE=YES))
if "%INCLUDE%"=="YES" (echo %RELEASE_SCRIPT_DIR%\%FILENAME% >> %OUTPUTFILE%)
endlocal
goto:eof

