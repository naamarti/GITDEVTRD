REM -------------------------------------
REM PARAMETERS
REM   %1 = Database Name
REM   %2 = Object Name
REM   %3 = Object Type
REM -------------------------------------

@echo off

setlocal

set DATABASE_NAME=%1
set DROP_OBJECT_NAME=%2
set OBJECT_TYPE=%3

REM -- Remove ".sql" from name if exists
set DROP_OBJECT_NAME=%DROP_OBJECT_NAME:.sql=%

set QUERY_STRING=IF EXISTS (SELECT * FROM sys.objects WHERE name = '%DROP_OBJECT_NAME%') DROP %OBJECT_TYPE% %DROP_OBJECT_NAME%

call %UTIL_SCRIPT_DIR%\RunDatabaseCommandFromQueryString.cmd %DATABASE_NAME% "%QUERY_STRING%"
