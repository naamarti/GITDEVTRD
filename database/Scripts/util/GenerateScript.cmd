@echo off

setlocal

set OBJECT_NAME=%1
set OBJECT_TYPE=%2
set OBJECT_DIR=%3

set OUTPUT_FILE=%OBJECT_DIR%\%OBJECT_NAME%.sql

if "%OBJECT_TYPE%"=="Tables" (
	set QUERY_STRING=EXEC p_j_GenerateScriptForTable '%OBJECT_NAME%';
) else (
	set QUERY_STRING=EXEC p_j_GenerateScriptForModule '%OBJECT_NAME%';
)

echo Scripting %DMS_DEPLOY_TO_SQLSERVER%:%DATABASE_NAME%..%OBJECT_NAME% ...

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromQueryString.cmd %DATABASE_NAME% "%QUERY_STRING%" %OUTPUT_FILE%

if ERRORLEVEL 1 (EXIT /b 1)
