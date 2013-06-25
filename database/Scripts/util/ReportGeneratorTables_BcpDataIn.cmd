@echo off

setlocal

set DMS_DEPLOY_TO_SQLSERVER=%1
set DATABASE_NAME=TBS_Common

set DATA_DIR=%DB_ROOT_DIR%\%DATABASE_NAME%\data
set TABLE_LIST=%TEMP_SCRIPT_DIR%\tables.txt

call %DEPLOY_LOGGER% "Retrieving List of Report Tables..."

set QUERY_STRING=SET NOCOUNT ON; DECLARE @Prefix char(3); SET @Prefix='RG_'; SELECT Name FROM sys.Tables WHERE LEFT(Name,3) = @Prefix ORDER BY Name;
call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromQueryString.cmd %DATABASE_NAME% "%QUERY_STRING%" %TABLE_LIST%
if ERRORLEVEL 1 (exit /b 1)

call %DEPLOY_LOGGER% "Deleting Report Table data on Server %DMS_DEPLOY_TO_SQLSERVER%..."

For /F %%T in (%TABLE_LIST%) do (
	call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromQueryString.cmd %DATABASE_NAME% "SET NOCOUNT ON; DELETE %%T"
	if ERRORLEVEL 1 (exit /b 1)
)

call %DEPLOY_LOGGER% "BCP into %DATABASE_NAME% on Server %DMS_DEPLOY_TO_SQLSERVER%..."

call %DEPLOY_LOGGER% "Using BCP data files located at: %DB_ROOT_DIR%\%DATABASE_NAME%\data\RG_*.dat"

set TIMESTAMP=%date:~-4,4%%date:~-10,2%%date:~-7,2%

set LOG_FILE=%TEMP_SCRIPT_DIR%\_bcp_in_info.txt

set BATCH_SIZE=1000
set RETAIN_IDENTITY_VALUES=-E
set CHECK_CONSTRAINTS=-h "CHECK_CONSTRAINTS"

echo START BCP INTO %DATABASE_NAME%... 		    > %LOG_FILE%
echo. 										    >> %LOG_FILE%
echo SQL_SERVER=%DMS_DEPLOY_TO_SQLSERVER%	    >> %LOG_FILE%
echo DATABASE_NAME=%DATABASE_NAME%			    >> %LOG_FILE%
echo DATE=%TIMESTAMP%						    >> %LOG_FILE%
echo TIME=%time%							    >> %LOG_FILE%

call %DEPLOY_LOGGER% "Details written to log file: %LOG_FILE%"

For /F %%T in (%TABLE_LIST%) do (
	call %UTIL_SCRIPT_DIR%\BcpTableIn.cmd %%T
	if ERRORLEVEL 1 (EXIT /b 1)
	call %UTIL_SCRIPT_DIR%\CheckBCPLogForErrors.cmd %LOG_FILE%
	if ERRORLEVEL 1 (EXIT /b 1)
)

goto :eof

