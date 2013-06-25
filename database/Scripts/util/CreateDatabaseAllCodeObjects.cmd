REM -------------------------------------
REM PARAMETERS
REM   %1 = Database Name
REM -------------------------------------

@echo off

setlocal

set DATABASE_NAME=%1
set FAILURE=0

set DB_DIR=%DB_ROOT_DIR%\%DATABASE_NAME%

IF %DATABASE_NAME:~0,12% == TBS_Program_ (
	set DB_DIR=%DB_ROOT_DIR%\TBS_Program_XXXXX
)

REM -------------------------------------
REM VIEWS
REM -------------------------------------

set VIEW_LIST=%TEMP_SCRIPT_DIR%\views.txt
if exist %DB_DIR%\views\*.sql (
	dir /b %DB_DIR%\views\*.sql > %VIEW_LIST%
	for /F %%F IN (%VIEW_LIST%) do (
		call %UTIL_SCRIPT_DIR%\DropDatabaseObject.cmd %DATABASE_NAME% %%F VIEW

		call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd %DATABASE_NAME% %DB_DIR%\views\%%F
		if ERRORLEVEL 1 (set FAILURE=1)
	)
)

REM -------------------------------------
REM FUNCTIONS
REM -------------------------------------

set FUNCTION_LIST=%TEMP_SCRIPT_DIR%\functions.txt
if exist %DB_DIR%\functions\*.sql (
	dir /b %DB_DIR%\functions\*.sql > %FUNCTION_LIST%
	for /F %%F IN (%FUNCTION_LIST%) do (
		call %UTIL_SCRIPT_DIR%\DropDatabaseObject.cmd %DATABASE_NAME% %%F FUNCTION
		
		call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd %DATABASE_NAME% %DB_DIR%\functions\%%F
		if ERRORLEVEL 1 (set FAILURE=1)
	)
)

REM -------------------------------------
REM TRIGGERS
REM -------------------------------------

set TRIGGER_LIST=%TEMP_SCRIPT_DIR%\triggers.txt
if exist %DB_DIR%\triggers\*.sql (
	dir /b %DB_DIR%\triggers\*.sql > %TRIGGER_LIST%
	for /F %%F IN (%TRIGGER_LIST%) do (
		call %UTIL_SCRIPT_DIR%\DropDatabaseObject.cmd %DATABASE_NAME% %%F TRIGGER
		
		call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd %DATABASE_NAME% %DB_DIR%\triggers\%%F
		if ERRORLEVEL 1 (set FAILURE=1)
	)
)

REM -------------------------------------
REM PROCEDURES
REM -------------------------------------

set PROCEDURE_LIST=%TEMP_SCRIPT_DIR%\procedures.txt
if exist %DB_DIR%\procedures\*.sql (
	dir /b %DB_DIR%\procedures\*.sql > %PROCEDURE_LIST%
	for /F %%F IN (%PROCEDURE_LIST%) do (
		call %UTIL_SCRIPT_DIR%\DropDatabaseObject.cmd %DATABASE_NAME% %%F PROCEDURE

		call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile.cmd %DATABASE_NAME% %DB_DIR%\procedures\%%F
		if ERRORLEVEL 1 (set FAILURE=1)
	)
)

if "%FAILURE%"=="1" (EXIT /b 1)
