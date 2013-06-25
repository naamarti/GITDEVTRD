REM -------------------------------------
REM PARAMETERS
REM   %1 = Database Name
REM   %2 = FileName
REM -------------------------------------

@echo off

setlocal

set DATABASE_NAME=%1
set FILENAME=%2

set DB_DIR=%DB_ROOT_DIR%\%DATABASE_NAME%

IF %DATABASE_NAME:~0,12% == TBS_Program_ (
	set DB_DIR=%DB_ROOT_DIR%\TBS_Program_XXXXX
)

IF EXIST %FILENAME% (del /Q %FILENAME%)

echo. >  %FILENAME%

REM -------------------------------------
REM VIEWS
REM -------------------------------------

set VIEW_LIST=%TEMP_SCRIPT_DIR%\views.txt
if exist %DB_DIR%\views\*.sql (
	dir /b %DB_DIR%\views\*.sql > %VIEW_LIST%
	for /F %%F IN (%VIEW_LIST%) do (
		call %UTIL_SCRIPT_DIR%\WriteDatabaseDropStatementToFile.cmd %%F VIEW %FILENAME%
		call %UTIL_SCRIPT_DIR%\WriteDatabaseInterfaceCreateToFile.cmd %%F VIEW %FILENAME%
		if ERRORLEVEL 1 (EXIT /b 1)
	)
)

REM -------------------------------------
REM FUNCTIONS
REM -------------------------------------

set FUNCTION_LIST=%TEMP_SCRIPT_DIR%\functions.txt
if exist %DB_DIR%\functions\*.sql (
	dir /b %DB_DIR%\functions\*.sql > %FUNCTION_LIST%
	for /F %%F IN (%FUNCTION_LIST%) do (
		call %UTIL_SCRIPT_DIR%\WriteDatabaseDropStatementToFile.cmd %%F FUNCTION %FILENAME%
		call %UTIL_SCRIPT_DIR%\WriteDatabaseInterfaceCreateToFile.cmd %%F FUNCTION %FILENAME%
		if ERRORLEVEL 1 (EXIT /b 1)
	)
)

REM -------------------------------------
REM PROCEDURES
REM -------------------------------------

set PROCEDURE_LIST=%TEMP_SCRIPT_DIR%\procedures.txt
if exist %DB_DIR%\procedures\*.sql (
	dir /b %DB_DIR%\procedures\*.sql > %PROCEDURE_LIST%
	for /F %%F IN (%PROCEDURE_LIST%) do (
		call %UTIL_SCRIPT_DIR%\WriteDatabaseDropStatementToFile.cmd %%F PROCEDURE %FILENAME%
		call %UTIL_SCRIPT_DIR%\WriteDatabaseInterfaceCreateToFile.cmd %%F PROCEDURE %FILENAME%
		if ERRORLEVEL 1 (EXIT /b 1)
	)
)
