@echo off
REM ---------------------------------------------------
REM -- %1 = Database Name
REM --
REM --
REM ---------------------------------------------------

setlocal

set DATABASE_NAME=%1
set DATABASE_INTERFACE_FILE=%TEMP_SCRIPT_DIR%\CreateAllCodeInterfaces.sql
set DATABASE_CODE_FILE=%TEMP_SCRIPT_DIR%\CreateAllCodeObjects.sql

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd %DATABASE_NAME% %UTIL_SCRIPT_DIR%\DropAllDatabaseCodeObjects.sql
if ERRORLEVEL 1 exit /b 1

call %UTIL_SCRIPT_DIR%\GenerateScriptDatabaseAllCodeInterfaces.cmd %DATABASE_NAME% %DATABASE_INTERFACE_FILE%
if ERRORLEVEL 1 exit /b 1
call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd %DATABASE_NAME% %DATABASE_INTERFACE_FILE%
if ERRORLEVEL 1 exit /b 1

call %UTIL_SCRIPT_DIR%\CreateDatabaseAllCodeObjects.cmd %DATABASE_NAME%
if ERRORLEVEL 1 exit /b 1

