REM -------------------------------------
REM PARAMETERS
REM   %1 = Database Name
REM -------------------------------------

@echo off

setlocal

:: DelayedExpansion allows variables referenced as !VAR! to be expanded per iteration of for loops
SETLOCAL ENABLEDELAYEDEXPANSION

set DATABASE_NAME=%1
set DB_FOLDER_NAME=
call :GetDBFolderName %DATABASE_NAME% DB_FOLDER_NAME
set FAILURE=0

for /F %%F IN (%RELEASE_SCRIPT_FILE%) do (
	set DB_PREFIX=INVALID
	set OBJECT_TYPE=INVALID
	set OBJECT_NAME=INVALID
	set IS_VALID=0

	call :GetDBPrefix %%F DB_PREFIX	
	call :GetObjectType %%F OBJECT_TYPE
	call :GetObjectName %%F OBJECT_NAME	
	call :IsValidObject !DB_PREFIX! !OBJECT_TYPE! IS_VALID
	
	if "!IS_VALID!"=="1" (
		call %UTIL_SCRIPT_DIR%\DropDatabaseObject.cmd %DATABASE_NAME% !OBJECT_NAME! !OBJECT_TYPE!

		call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd %DATABASE_NAME% %DB_ROOT_DIR%\%DB_FOLDER_NAME%\!OBJECT_TYPE!s\!OBJECT_NAME!.sql
		if ERRORLEVEL 1 (set FAILURE=1)
	)
)


if "%FAILURE%"=="1" (EXIT /b 1)

goto :eof


::---------------------------------------------------------------------------------------
:GetDBFolderName     -- 
::---------------------------------------------------------------------------------------
::                   -- %~1: DatabaseName
::                   -- %~2: Return Value (DBFolderName)
SETLOCAL
set DatabaseName=%1
set DBFolderName=%DatabaseName%
if %DatabaseName:~0,12%==TBS_Program_ (set DBFolderName=TBS_Program_XXXXX)
ENDLOCAL&if not "%~2"=="" set "%~2=%DBFolderName%"
goto:eof


::---------------------------------------------------------------------------------------
:GetDBPrefix         -- 
::---------------------------------------------------------------------------------------
::                   -- %~1: Script File Path & Name
::                   -- %~2: Return Value (DBPrefix)
SETLOCAL
set FileName=%1
set DBPrefix=INVALID
for /f "tokens=1,2,3 delims=." %%a in ("%FileName%") do set DBPrefix=%%a
ENDLOCAL&if not "%~2"=="" set "%~2=%DBPrefix%"
goto:eof


::---------------------------------------------------------------------------------------
:GetObjectType       -- 
::---------------------------------------------------------------------------------------
::                   -- %~1: Script File Path & Name
::                   -- %~2: Return Value (ObjectType)
SETLOCAL
set FileName=%1
set ObjectType=INVALID
for /f "tokens=1,2,3 delims=." %%a in ("%FileName%") do set ObjectType=%%b
ENDLOCAL&if not "%~2"=="" set "%~2=%ObjectType%"
goto:eof


::---------------------------------------------------------------------------------------
:GetObjectName       -- 
::---------------------------------------------------------------------------------------
::                   -- %~1: Script File Path & Name
::                   -- %~2: Return Value (ObjectName)
SETLOCAL
set FileName=%1
set ObjectName=
for /f "tokens=1,2,3 delims=." %%a in ("%FileName%") do set ObjectName=%%c
ENDLOCAL&if not "%~2"=="" set "%~2=%ObjectName%"
goto:eof


::---------------------------------------------------------------------------------------
:IsValidObject       -- 
::---------------------------------------------------------------------------------------
::                   -- %~1: DBPrefix
::                   -- %~2: ObjectType
::                   -- %~3: Return Value (IsValid)
SETLOCAL
set DBPrefix=%1
set ObjectType=%2
set IsValid=0
set IsValidPrefix=0
set IsValidType=0
if "%DBPrefix%"=="common" if "%DB_FOLDER_NAME%"=="TBS_Common" (set IsValidPrefix=1)
if "%DBPrefix%"=="recon" if "%DB_FOLDER_NAME%"=="TBS_Recon" (set IsValidPrefix=1)
if "%DBPrefix%"=="program" if "%DB_FOLDER_NAME%"=="TBS_Program_XXXXX" (set IsValidPrefix=1)
if "%ObjectType%"=="function" (set IsValidType=1)
if "%ObjectType%"=="procedure" (set IsValidType=1)
if "%ObjectType%"=="trigger" (set IsValidType=1)
if "%ObjectType%"=="view" (set IsValidType=1)
if "%IsValidPrefix%"=="1" if "%IsValidType%"=="1" (set IsValid=1)
ENDLOCAL&if not "%~3"=="" set "%~3=%IsValid%"
goto:eof


