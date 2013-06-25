REM -------------------------------------
REM PARAMETERS
REM   %1 = Object Name
REM   %2 = Object Type
REM   %3 = File Name
REM -------------------------------------

@echo off

set CREATE_OBJECT_NAME=%1
set OBJECT_TYPE=%2
set FILENAME=%3

REM -- Remove ".sql" from name if exists
set CREATE_OBJECT_NAME=%CREATE_OBJECT_NAME:.sql=%

IF "%OBJECT_TYPE%" == "FUNCTION" set CREATE_SQL=CREATE FUNCTION %CREATE_OBJECT_NAME%() RETURNS int AS BEGIN RETURN 1 END
IF "%OBJECT_TYPE%" == "PROCEDURE" set CREATE_SQL=CREATE PROCEDURE %CREATE_OBJECT_NAME% AS SELECT 1
IF "%OBJECT_TYPE%" == "VIEW" set CREATE_SQL=CREATE VIEW %CREATE_OBJECT_NAME% AS SELECT TOP 1 * FROM Sys.Objects

echo. >> %FILENAME%
echo %CREATE_SQL% >> %FILENAME%
echo go >> %FILENAME%
echo. >> %FILENAME%
