REM -------------------------------------
REM PARAMETERS
REM   %1 = Table Name
REM -------------------------------------

@echo off

setlocal

set TABLE_NAME=%1

if "%DMS_SQL_IS_INTEGRATED_LOGIN%"=="1" (
	set SQL_LOGIN=-T
) else ( 
	set SQL_LOGIN=-U%DMS_SQL_USER% -P%DMS_SQL_PASSWORD%
)

echo BCP Out: %DMS_DEPLOY_TO_SQLSERVER%.%DATABASE_NAME%..%TABLE_NAME%
echo.                                     >> %LOG_FILE%
echo ------------------------------------ >> %LOG_FILE%
echo %TABLE_NAME%                         >> %LOG_FILE%
echo ------------------------------------ >> %LOG_FILE%
bcp %DATABASE_NAME%..%TABLE_NAME% out %DATA_DIR%\%TABLE_NAME%.dat -n %SQL_LOGIN% -S%DMS_DEPLOY_TO_SQLSERVER% >> %LOG_FILE%

if ERRORLEVEL 1 (EXIT /b 1)
