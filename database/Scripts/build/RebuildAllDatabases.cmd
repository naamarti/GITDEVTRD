@echo off

set DB_ROOT_DIR=%DMS_ENV_DIR%\database
set BUILD_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\build
set UTIL_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\util
set RELEASE_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\Releases
set TEMP_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\temp
set DB_COUNTER=0

IF NOT EXIST %TEMP_SCRIPT_DIR% (mkdir %TEMP_SCRIPT_DIR%)

if "%DEPLOY_DATABASE_TRADING%"=="1" (
	call :IncrementDBCounter
	call %UTIL_SCRIPT_DIR%\RebuildDatabase.cmd TBS_Trading 1
	if ERRORLEVEL 1 (exit /b 1)
)

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd TBS_Common %RELEASE_SCRIPT_DIR%\EnvironmentSetup\EnvironmentSetup.sql.%DMS_DEPLOY_TO_ENV%
if ERRORLEVEL 1 (exit /b 1)

IF EXIST %TEMP_SCRIPT_DIR% (rmdir %TEMP_SCRIPT_DIR% /S /Q)

goto :eof


::---------------------------------------------------------------------------------------
:IncrementDBCounter
::---------------------------------------------------------------------------------------
set /A DB_COUNTER=%DB_COUNTER%+1
goto:eof
