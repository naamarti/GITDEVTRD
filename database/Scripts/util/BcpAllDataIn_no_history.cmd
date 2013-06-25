@echo off

:: DelayedExpansion allows variables referenced as !VAR! to be expanded per iteration of for loops
SETLOCAL ENABLEDELAYEDEXPANSION

set DATABASE_NAME=%1

set DATA_DIR=%DMS_ROOT_DIR%\database_backup\%DATABASE_NAME%
set LOG_FILE=%DMS_ROOT_DIR%\database_backup\%DATABASE_NAME%\_bcp_in_info.txt
set TABLE_LIST=%TEMP_SCRIPT_DIR%\tables.txt

set BATCH_SIZE=1000
set RETAIN_IDENTITY_VALUES=-E
set CHECK_CONSTRAINTS=-h "CHECK_CONSTRAINTS"

echo START BCP INTO %DATABASE_NAME%... > %LOG_FILE%

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile %DATABASE_NAME% %UTIL_SCRIPT_DIR%\GetListOfTablesOrderedByDependency.sql %TABLE_LIST% 
if ERRORLEVEL 1 (EXIT /b 1)

echo.
echo Details written to log file: %LOG_FILE%
echo.

For /F %%T in (%TABLE_LIST%) do (
	set SKIP=0
	call :IsSkipTable %%T IS_SKIP_TABLE
	if "!IS_SKIP_TABLE!"=="1" (
		set SKIP=1
		echo Skip:   %DMS_DEPLOY_TO_SQLSERVER%.%DATABASE_NAME%..%%T
	)
	if "!SKIP!"=="0" (
		call %UTIL_SCRIPT_DIR%\BcpTableIn.cmd %%T
		if ERRORLEVEL 1 (EXIT /b 1)
		call %UTIL_SCRIPT_DIR%\CheckBCPLogForErrors.cmd %LOG_FILE%
		if ERRORLEVEL 1 (EXIT /b 1)
	)
)

:IsSkipTable         -- Skip BCP In
::                   -- %~1: Table Name
::                   -- %~2: Return Value (0 or 1)
SETLOCAL
set TableName=%1
set IsSkip=0
if %TableName:~-5%==_Undo (set IsSkip=1)
if %TableName:~-8%==_History (set IsSkip=1)
if %TableName:~-12%==_Transaction (set IsSkip=1)
if %TableName:~0,17%==ConsolidationTBS_ (set IsSkip=1)
ENDLOCAL&if not "%~2"=="" set "%~2=%IsSkip%"
goto:eof
