REM -------------------------------------
REM PARAMETERS
REM   %1 = Database Name
REM   %2 = FileName
REM -------------------------------------

set DATABASE_NAME=%1
set FILENAME=%2

@echo off
set TIMESTAMP=%date:~-4,4%%date:~-10,2%%date:~-7,2%

echo /*-----------------------------------------------------------------			> %FILENAME%
echo   Drop Databases																>> %FILENAME%
echo ------------------------------------------------------------------*/			>> %FILENAME%
echo.																				>> %FILENAME%

echo use master																		>> %FILENAME%
echo go																				>> %FILENAME%
echo IF  EXISTS (SELECT name FROM sys.databases WHERE name = '%DATABASE_NAME%')		>> %FILENAME%
echo   DROP DATABASE %DATABASE_NAME%												>> %FILENAME%
echo go																				>> %FILENAME%
echo.																				>> %FILENAME%

echo /*-----------------------------------------------------------------			>> %FILENAME%
echo   Create Databases																>> %FILENAME%
echo ------------------------------------------------------------------*/			>> %FILENAME%
echo use master																		>> %FILENAME%
echo go																				>> %FILENAME%
echo.																				>> %FILENAME%

echo CREATE DATABASE %DATABASE_NAME% ON PRIMARY										>> %FILENAME%
echo ( NAME = '%DATABASE_NAME%',													>> %FILENAME%
echo   FILENAME = '%SQLSERVER_DATA_DIR%\%DATABASE_NAME%.mdf',								>> %FILENAME%
echo   SIZE = 10MB,																	>> %FILENAME%
echo   MAXSIZE = UNLIMITED,															>> %FILENAME%
echo   FILEGROWTH = 10%% )															>> %FILENAME%
echo LOG ON																			>> %FILENAME%
echo ( NAME = '%DATABASE_NAME%_log',												>> %FILENAME%
echo   FILENAME = '%SQLSERVER_DATA_DIR%\%DATABASE_NAME%.ldf',								>> %FILENAME%
echo   SIZE = 2MB,																	>> %FILENAME%
echo   MAXSIZE = UNLIMITED,															>> %FILENAME%
echo   FILEGROWTH = 10%% )															>> %FILENAME%
echo go																				>> %FILENAME%
echo.																				>> %FILENAME%	

echo /*-----------------------------------------------------------------			>> %FILENAME%
echo   Configure Databases															>> %FILENAME%
echo ------------------------------------------------------------------*/			>> %FILENAME%
echo.																				>> %FILENAME%


echo /*-----------------------------------------------------------------			>> %FILENAME%
echo   Add Logins																	>> %FILENAME%
echo ------------------------------------------------------------------*/			>> %FILENAME%
echo.																				>> %FILENAME%


echo /*-----------------------------------------------------------------			>> %FILENAME%
echo   Add Database Users															>> %FILENAME%
echo ------------------------------------------------------------------*/			>> %FILENAME%
echo use %DATABASE_NAME%															>> %FILENAME%
echo go																				>> %FILENAME%
echo.																				>> %FILENAME%




