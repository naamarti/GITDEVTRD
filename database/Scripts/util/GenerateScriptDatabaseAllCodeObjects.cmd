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
		echo PRINT 'Creating Object %%F ...' >> %FILENAME%
		echo. >> %FILENAME%
		echo go >> %FILENAME%
		echo. >> %FILENAME%
		type %DB_DIR%\views\%%F >> %FILENAME%
		echo. >> %FILENAME%
		echo go >> %FILENAME%
		echo. >> %FILENAME%
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
		echo PRINT 'Creating Object %%F ...' >> %FILENAME%
		echo. >> %FILENAME%
		echo go >> %FILENAME%
		echo. >> %FILENAME%
		type %DB_DIR%\functions\%%F >> %FILENAME%
		echo. >> %FILENAME%
		echo go >> %FILENAME%
		echo. >> %FILENAME%
		if ERRORLEVEL 1 (EXIT /b 1)
	)
)

REM -------------------------------------
REM TRIGGERS
REM -------------------------------------

set TRIGGER_LIST=%TEMP_SCRIPT_DIR%\triggers.txt
if exist %DB_DIR%\triggers\*.sql (
	dir /b %DB_DIR%\triggers\*.sql > %TRIGGER_LIST%
	for /F %%F IN (%TRIGGER_LIST%) do (
		call %UTIL_SCRIPT_DIR%\WriteDatabaseDropStatementToFile.cmd %%F TRIGGER %FILENAME%
		echo PRINT 'Creating Object %%F ...' >> %FILENAME%
		echo. >> %FILENAME%
		echo go >> %FILENAME%
		echo. >> %FILENAME%
		type %DB_DIR%\triggers\%%F >> %FILENAME%
		echo. >> %FILENAME%
		echo go >> %FILENAME%
		echo. >> %FILENAME%
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
		echo PRINT 'Creating Object %%F ...' >> %FILENAME%
		echo. >> %FILENAME%
		echo go >> %FILENAME%
		echo. >> %FILENAME%
		type %DB_DIR%\procedures\%%F >> %FILENAME%
		echo. >> %FILENAME%
		echo go >> %FILENAME%
		echo. >> %FILENAME%
		if ERRORLEVEL 1 (EXIT /b 1)
	)
)
