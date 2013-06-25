REM -------------------------------------
REM PARAMETERS
REM   %1 = Object Name
REM   %2 = Object Type
REM   %3 = File Name
REM -------------------------------------

@echo off

set DROP_OBJECT_NAME=%1
set OBJECT_TYPE=%2
set FILENAME=%3

REM -- Remove ".sql" from name if exists
set DROP_OBJECT_NAME=%DROP_OBJECT_NAME:.sql=%

set DROP_STATEMENT_SQL=IF EXISTS (SELECT * FROM sys.objects WHERE name = '%DROP_OBJECT_NAME%') DROP %OBJECT_TYPE% %DROP_OBJECT_NAME%

echo. >> %FILENAME%
echo %DROP_STATEMENT_SQL% >> %FILENAME%
echo go >> %FILENAME%
echo. >> %FILENAME%
