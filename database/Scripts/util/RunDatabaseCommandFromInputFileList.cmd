REM -------------------------------------
REM PARAMETERS
REM   %1 = Database Name
REM   %2 = Input File List
REM -------------------------------------

@echo off

setlocal

set DATABASE_NAME=%1
set SQL_INPUT_FILE_LIST=%2
set FAILURE=0

if "%DMS_SQL_IS_INTEGRATED_LOGIN%"=="1" (
	set SQL_LOGIN=
) else ( 
	set SQL_LOGIN=-U%DMS_SQL_USER% -P%DMS_SQL_PASSWORD%
)

set BATCH_EXIT_ON_ERROR=-b
set DISABLE_SYSTEM_COMMANDS=-X

for /F %%F IN (%SQL_INPUT_FILE_LIST%) do (
	call %DEPLOY_LOGGER% "Running script: %%~nF"
	
	>> %DEPLOY_LOG% sqlcmd %SQL_LOGIN% -S%DMS_DEPLOY_TO_SQLSERVER% -d%DATABASE_NAME% %BATCH_EXIT_ON_ERROR% %DISABLE_SYSTEM_COMMANDS% -i %%F
	if ERRORLEVEL 1 (
		if not exist %%F (
			call %DEPLOY_LOGGER% "*** Failed, script not found: %%F"
		) else (
			call %DEPLOY_LOGGER% "*** Failed in script: %%F"
		)
		set FAILURE=1
	)
)

if "%FAILURE%"=="1" (exit /b 1)

goto :eof

