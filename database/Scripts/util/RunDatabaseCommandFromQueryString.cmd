REM -------------------------------------
REM PARAMETERS
REM   %1 = Database Name
REM   %2 = Query String
REM   %3 = Output File
REM -------------------------------------

@echo off

setlocal

set DATABASE_NAME=%1
set SQL_QUERY_STRING=%2
set SQL_OUTPUT_FILE=%3

if "%DMS_SQL_IS_INTEGRATED_LOGIN%"=="1" (
	set SQL_LOGIN=
) else ( 
	set SQL_LOGIN=-U%DMS_SQL_USER% -P%DMS_SQL_PASSWORD%
)

REM -- Remove double quotes
set SQL_QUERY_STRING=%SQL_QUERY_STRING:"=%

if not "%SQL_OUTPUT_FILE%" == "" (
	set SQL_OUTPUT_FILE_PARAM=-o %SQL_OUTPUT_FILE%
) 

set BATCH_EXIT_ON_ERROR=-b
set DISABLE_SYSTEM_COMMANDS=-X
set NO_HEADERS=-h -1
set NO_TRUNCATE=-y0

sqlcmd %SQL_LOGIN% -S%DMS_DEPLOY_TO_SQLSERVER% -d%DATABASE_NAME% %BATCH_EXIT_ON_ERROR% %DISABLE_SYSTEM_COMMANDS% -Q "%SQL_QUERY_STRING%" %SQL_OUTPUT_FILE_PARAM% %NO_HEADERS% %NO_TRUNCATE%

if ERRORLEVEL 1 (EXIT /b 1)
