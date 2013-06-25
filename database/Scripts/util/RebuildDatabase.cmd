@echo off

setlocal

set DATABASE_NAME=%1
set BCP_DATA_IN=%2

set DB_SCRIPT_DIR=%DB_ROOT_DIR%\%DATABASE_NAME%\scripts

IF %DATABASE_NAME:~0,12% == TBS_Program_ (set DB_SCRIPT_DIR=%DB_ROOT_DIR%\TBS_Program_XXXXX\scripts)

call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "REBUILD: %DB_COUNTER%-a."
call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.%DATABASE_NAME%:"
call %DEPLOY_LOGGER% "    Dropping all Database Objects..."
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd %DATABASE_NAME% %UTIL_SCRIPT_DIR%\DropAllDatabaseCodeObjects.sql
if ERRORLEVEL 1 (EXIT /b 1)

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd %DATABASE_NAME% %UTIL_SCRIPT_DIR%\DropAllDatabaseTables.sql
if ERRORLEVEL 1 (EXIT /b 1)

call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "REBUILD: %DB_COUNTER%-b."
call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.%DATABASE_NAME%:"
call %DEPLOY_LOGGER% "    Creating Tables..."
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""

set SETUP_FILE=%DMS_ROOT_DIR%\database_backup\%DATABASE_NAME%\CreateAllDatabaseTables.sql
if not exist %SETUP_FILE% (set SETUP_FILE=%DB_SCRIPT_DIR%\CreateAllDatabaseTables.sql)

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd %DATABASE_NAME% %SETUP_FILE%
if ERRORLEVEL 1 (EXIT /b 1)

call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "REBUILD: %DB_COUNTER%-c."
call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.%DATABASE_NAME%:"
call %DEPLOY_LOGGER% "    BCP Data In..."
call %DEPLOY_LOGGER% "    BCP_DATA_IN Flag: %BCP_DATA_IN%"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""

if "%BCP_DATA_IN%"=="1" (
	call %UTIL_SCRIPT_DIR%\BcpAllDataIn.cmd %DATABASE_NAME%
	if ERRORLEVEL 1 (EXIT /b 1)
)

goto :eof

