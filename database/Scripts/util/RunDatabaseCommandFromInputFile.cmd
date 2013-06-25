REM -------------------------------------
REM PARAMETERS
REM   %1 = Database Name
REM   %2 = Query File
REM   %3 = Output File
REM -------------------------------------

@echo off

setlocal

set DATABASE_NAME=%1
set SQL_QUERY_FILE=%2
set SQL_OUTPUT_FILE=%3
set FILENAME="%~nx2"

if "%DMS_SQL_IS_INTEGRATED_LOGIN%"=="1" (
	set SQL_LOGIN=
) else ( 
	set SQL_LOGIN=-U%DMS_SQL_USER% -P%DMS_SQL_PASSWORD%
)

if not "%SQL_OUTPUT_FILE%" == "" (
	set SQL_OUTPUT_FILE_PARAM=-o %SQL_OUTPUT_FILE%
) 

set BATCH_EXIT_ON_ERROR=-b
set DISABLE_SYSTEM_COMMANDS=-X
set SQL_INPUT_FILES=-i %SQL_QUERY_FILE%
set NO_HEADERS=-h -1
set NO_TRUNCATE=-y0

call %DEPLOY_LOGGER% "Running script: %FILENAME%"

>> %DEPLOY_LOG% sqlcmd %SQL_LOGIN% -S%DMS_DEPLOY_TO_SQLSERVER% -d%DATABASE_NAME% %BATCH_EXIT_ON_ERROR% %DISABLE_SYSTEM_COMMANDS% %SQL_INPUT_FILES% %SQL_OUTPUT_FILE_PARAM% %NO_HEADERS% %NO_TRUNCATE%

if ERRORLEVEL 1 (
	if not exist %SQL_QUERY_FILE% (
		call %DEPLOY_LOGGER% "*** Failed, script not found: %SQL_QUERY_FILE%"
	) else (
		call %DEPLOY_LOGGER% "*** Failed in script: %FILENAME%"
	)
	exit /b 1
)


goto :eof

