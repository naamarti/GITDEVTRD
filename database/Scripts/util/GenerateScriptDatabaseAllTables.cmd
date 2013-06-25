REM -------------------------------------
REM PARAMETERS
REM   %1 = Database Name
REM -------------------------------------

@echo off

set DATABASE_NAME=%1
set DB_DIR=%DB_ROOT_DIR%\%DATABASE_NAME%

IF %DATABASE_NAME:~0,12% == TBS_Program_ (
	set DATABASE_NAME=TBS_Program_Clearview
	set DB_DIR=%DB_ROOT_DIR%\TBS_Program_XXXXX
)

set FILENAME=%DB_DIR%\scripts\CreateAllDatabaseTables.sql
IF EXIST %FILENAME% (attrib -R %FILENAME%)
IF EXIST %FILENAME% (del /Q %FILENAME%)

set TABLE_LIST=%TEMP_SCRIPT_DIR%\tables.txt
call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromInputFile %DATABASE_NAME% %UTIL_SCRIPT_DIR%\GetListOfTablesOrderedByDependency.sql %TABLE_LIST% 
if ERRORLEVEL 1 (EXIT /b 1)

echo. > %FILENAME%

REM -------------------------------------
REM TABLES
REM -------------------------------------

for /F %%T in (%TABLE_LIST%) do (
	call %UTIL_SCRIPT_DIR%\GenerateScript.cmd %%T Tables %TEMP_SCRIPT_DIR%
	if ERRORLEVEL 1 (exit /b 1)
	
	type %TEMP_SCRIPT_DIR%\%%T.sql >> %FILENAME%
	echo. >> %FILENAME%
	echo go >> %FILENAME%
	echo. >> %FILENAME%
)

