@echo off

setlocal

:: DelayedExpansion allows variables referenced as !VAR! to be expanded per iteration of for loops
setlocal ENABLEDELAYEDEXPANSION

set TIMESTAMP=%DATABASE_BCP_DATE%

set UTIL_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\util
set DATABASE_BACKUP_DIR=%DMS_ROOT_DIR%\database_backup
set ZIP_EXE_DIR=c:\program files\7-zip

if not exist %DATABASE_BACKUP_DIR% (mkdir %DATABASE_BACKUP_DIR%)

if "%DEPLOY_DATABASE_TRADING%"=="1" (
	call %DEPLOY_LOGGER% ""
	call %DEPLOY_LOGGER% "----------------------------------------------------------"
	call %DEPLOY_LOGGER% ""
	call %DEPLOY_LOGGER% "GET BCP FILES: 01 - TBS_Trading"
	call %DEPLOY_LOGGER% ""
	call %DEPLOY_LOGGER% "----------------------------------------------------------"
	call %DEPLOY_LOGGER% ""

	call %UTIL_SCRIPT_DIR%\GetAndUnCompressBackupFile %TIMESTAMP% TBS_Trading
	if ERRORLEVEL 1 (exit /b 1)
)

goto :eof

