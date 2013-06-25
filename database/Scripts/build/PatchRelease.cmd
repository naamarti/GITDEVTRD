:: =================================================================================================
:: Created:   04 May 2011 VC #792: Automatic Patch deployment process for Emergency Production Releases
:: Modified:  02 Apr 2012 NM 1042: log the deploy scripts
::
:: This script is called by the main DMS deployment script, when doing an  
:: incremental patch release to the Production environment.
:: 
:: =================================================================================================
@echo off

:: DelayedExpansion allows variables referenced as !VAR! to be expanded per iteration of for loops
setlocal ENABLEDELAYEDEXPANSION

set PROGRAM_LIST=%DMS_ENV_DIR%\database\scripts\build\PROGRAM_LIST.TXT
set DB_ROOT_DIR=%DMS_ENV_DIR%\database
set RELEASE_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\Releases\Patch
set RELEASE_SCRIPT_FILE=%RELEASE_SCRIPT_DIR%\ReleasePatchFiles.txt
set UTIL_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\util
set TEMP_SCRIPT_DIR=%DMS_ENV_DIR%\database\Scripts\temp
set RELEASE_FILES=%TEMP_SCRIPT_DIR%\ReleaseFiles.txt
set ENVIRONMENT_SETUP_DIR=%DMS_ENV_DIR%\database\Scripts\Releases\EnvironmentSetup
set DB_COUNTER=0
set DEPLOY_FAILURE=0

if not exist %TEMP_SCRIPT_DIR% (mkdir %TEMP_SCRIPT_DIR%)

:: *************************************************************************************************
:DEPLOY_COMMON
:: *************************************************************************************************
if "%DEPLOY_DATABASE_COMMON%"=="0" goto :DEPLOY_RECON
call :IncrementDBCounter

call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "PATCH: %DB_COUNTER%-a."
call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.TBS_Common: Scripts"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd TBS_Common %ENVIRONMENT_SETUP_DIR%\EnvironmentSetup.sql.%DMS_DEPLOY_TO_ENV%
if ERRORLEVEL 1 (
	call :DeployFailure
)

if exist %RELEASE_FILES% (del /Q %RELEASE_FILES%)
for /F %%F in (%RELEASE_SCRIPT_FILE%) do (
	call :WriteFileName %%F common.Patch %RELEASE_FILES%
)
if exist %RELEASE_FILES% (
	call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFileList.cmd TBS_Common %RELEASE_FILES%
	if ERRORLEVEL 1 (
		call :DeployFailure
	)
)

call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "PATCH: %DB_COUNTER%-b."
call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.TBS_Common: Objects"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""

call %UTIL_SCRIPT_DIR%\DropAndRecreatePatchDatabaseCodeObjects.cmd TBS_Common
if ERRORLEVEL 1 (
	call :DeployFailure
)

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd TBS_Common %ENVIRONMENT_SETUP_DIR%\EnvironmentSetup.sql.%DMS_DEPLOY_TO_ENV%
if ERRORLEVEL 1 (
	call :DeployFailure
)

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd TBS_Common %ENVIRONMENT_SETUP_DIR%\EnvironmentSetup.Checklist.non_prod.sql
if ERRORLEVEL 1 (
	call :DeployFailure
)

:: *************************************************************************************************
:DEPLOY_RECON
:: *************************************************************************************************
if "%DEPLOY_DATABASE_RECON%"=="0" goto :DEPLOY_PROGRAM
call :IncrementDBCounter

call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "PATCH: %DB_COUNTER%-a."
call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.TBS_Recon: Scripts"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""

if exist %RELEASE_FILES% (del /Q %RELEASE_FILES%)
for /F %%F in (%RELEASE_SCRIPT_FILE%) do (
	call :WriteFileName %%F recon.Patch %RELEASE_FILES%
)
if exist %RELEASE_FILES% (
	call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFileList.cmd TBS_Recon %RELEASE_FILES%
	if ERRORLEVEL 1 (
		call :DeployFailure
	)
)

call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "PATCH: %DB_COUNTER%-b."
call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.TBS_Recon: Objects"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "----------------------------------------------------------"
call %DEPLOY_LOGGER% ""

call %UTIL_SCRIPT_DIR%\DropAndRecreatePatchDatabaseCodeObjects.cmd TBS_Recon
if ERRORLEVEL 1 (
	call :DeployFailure
)



:: *************************************************************************************************
:DEPLOY_PROGRAM
:: *************************************************************************************************
if "%DEPLOY_DATABASE_PROGRAM%"=="0" goto :eof

if exist %RELEASE_FILES% (del /Q %RELEASE_FILES%)
for /F %%F in (%RELEASE_SCRIPT_FILE%) do (
	call :WriteFileName %%F program.Patch %RELEASE_FILES%
)

for /F "skip=1 delims=, tokens=1,2,3" %%A in (%PROGRAM_LIST%) do (
	call :IncrementDBCounter
	
	call %DEPLOY_LOGGER% ""
	call %DEPLOY_LOGGER% "----------------------------------------------------------"
	call %DEPLOY_LOGGER% ""
	call %DEPLOY_LOGGER% "PATCH: !DB_COUNTER!-a."
	call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.TBS_Program_%%A: Scripts"
	call %DEPLOY_LOGGER% ""
	call %DEPLOY_LOGGER% "----------------------------------------------------------"
	call %DEPLOY_LOGGER% ""
	
	if exist %RELEASE_FILES% (
		call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFileList.cmd TBS_Program_%%A %RELEASE_FILES%
		if ERRORLEVEL 1 (
			call :DeployFailure
		)
	)

	call %DEPLOY_LOGGER% ""
	call %DEPLOY_LOGGER% "----------------------------------------------------------"
	call %DEPLOY_LOGGER% ""
	call %DEPLOY_LOGGER% "PATCH: !DB_COUNTER!-b."
	call %DEPLOY_LOGGER% "    %DMS_DEPLOY_TO_SQLSERVER%.TBS_Program_%%A: Objects"
	call %DEPLOY_LOGGER% ""
	call %DEPLOY_LOGGER% "----------------------------------------------------------"
	call %DEPLOY_LOGGER% ""
	
	call %UTIL_SCRIPT_DIR%\DropAndRecreatePatchDatabaseCodeObjects.cmd TBS_Program_%%A
	if ERRORLEVEL 1 (
		call :DeployFailure
	)
)


if EXIST %TEMP_SCRIPT_DIR% (rmdir %TEMP_SCRIPT_DIR% /S /Q)

if "%DEPLOY_FAILURE%"=="1" (EXIT /b 1)

goto :eof

::---------------------------------------------------------------------------------------
:DeployFailure
::---------------------------------------------------------------------------------------
set DEPLOY_FAILURE=1
color FC
goto:eof

::---------------------------------------------------------------------------------------
:IncrementDBCounter
::---------------------------------------------------------------------------------------
set /A DB_COUNTER=%DB_COUNTER%+1
goto:eof

::---------------------------------------------------------------------------------------
:WriteFileName
::---------------------------------------------------------------------------------------
setlocal
set FILENAME=%1
set PREFIX=%2
set OUTPUTFILE=%3
set INCLUDE=NO
if "%PREFIX%"=="common.Patch" (if %FILENAME:~0,12%==common.Patch (set INCLUDE=YES))
if "%PREFIX%"=="recon.Patch" (if %FILENAME:~0,11%==recon.Patch (set INCLUDE=YES))
if "%PREFIX%"=="program.Patch" (if %FILENAME:~0,13%==program.Patch (set INCLUDE=YES))
if "%INCLUDE%"=="YES" (echo %RELEASE_SCRIPT_DIR%\%FILENAME% >> %OUTPUTFILE%)
endlocal
goto:eof

