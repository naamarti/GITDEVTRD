@echo off

:: ===========================================================================
:: Total Bank Solutions
:: ===========================================================================

call .\util\DMS_SetEnvironment.cmd
if ERRORLEVEL 1 (goto :EXIT_FAILURE)

set ZIP_EXE=C:\program files\7-zip\7z.exe

cd ..

set DATESTAMP=%date:~-4,4%-%date:~-10,2%-%date:~-7,2%
set TIMESTAMP=%time:~-11,2%:%time:~-8,2%:%time:~-5,2%
set TS=%DATESTAMP%_%TIMESTAMP%
:: replace any blanks with "0"'s, for single digit hour, minute, etc.
set TS=%TS: =0%
set TS=%TS::=-%

set FILENAME=%DMS_ENV%_%TS%.7z

IF EXIST %FILENAME% (del /Q %FILENAME%)

"%ZIP_EXE%" a -t7z -mx3 %FILENAME% application\ database\ scripts\ -r -x!*.class

cls
echo.
echo Copying file to: \\intranet.totalbanksolutions.com\tbs_files\Associates\%USERNAME%\%FILENAME%
echo.

copy /B /Z %FILENAME% \\intranet.totalbanksolutions.com\tbs_files\Associates\%USERNAME%\

:EXIT_SUCCESS
set EXIT_MESSAGE=Completed Successfully.
goto :EXIT

:EXIT_FAILURE
set EXIT_MESSAGE=Process Failed!!

:EXIT
echo.
echo **********************************************
echo.
echo %EXIT_MESSAGE%
echo.
echo **********************************************
echo.
pause
