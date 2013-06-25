@echo off

set SAVE_CURRENT_DIR=%cd%
set LAST_DIR=
set DMS_ROOT_DIR=
set DMS_ENV_DIR=
set DMS_ENV=
set DMS_ENV_ORDER=0

:FINDROOT
if "%cd%"=="%LAST_DIR%" (
  goto :NOTFOUND
) else if exist local\nul (
	set DMS_ROOT_DIR=%cd%
	set DMS_ENV_DIR=%LAST_DIR%
	goto :FOUND
) else if exist dev\nul (
	set DMS_ROOT_DIR=%cd%
	set DMS_ENV_DIR=%LAST_DIR%
	goto :FOUND
) else if exist dev_trading\nul (
	set DMS_ROOT_DIR=%cd%
	set DMS_ENV_DIR=%LAST_DIR%
	goto :FOUND
) else if exist qa\nul (
	set DMS_ROOT_DIR=%cd%
	set DMS_ENV_DIR=%LAST_DIR%
	goto :FOUND
) else if exist rbcqa\nul (
	set DMS_ROOT_DIR=%cd%
	set DMS_ENV_DIR=%LAST_DIR%
	goto :FOUND	
) else if exist uat\nul (
	set DMS_ROOT_DIR=%cd%
	set DMS_ENV_DIR=%LAST_DIR%
	goto :FOUND
) else if exist prod\nul (
	set DMS_ROOT_DIR=%cd%
	set DMS_ENV_DIR=%LAST_DIR%
	goto :FOUND
) else if exist prod_trading\nul (
	set DMS_ROOT_DIR=%cd%
	set DMS_ENV_DIR=%LAST_DIR%
	goto :FOUND
) else (
	set LAST_DIR=%cd%
	cd..
	goto :FINDROOT
)

:NOTFOUND
echo Error - DMS Environment Directory cannot be determined!
exit /b 1

:FOUND
call set DMS_ENV=%%DMS_ENV_DIR:%DMS_ROOT_DIR%\=%%
call %DMS_ENV_DIR%\scripts\util\DMS_GetEnvironmentInfo.cmd
set DMS_ENV_ORDER=DMS_ENV_ORDER_%DMS_ENV%
call set DMS_ENV_ORDER=%%%DMS_ENV_ORDER%%%

echo DMS_ROOT_DIR=%DMS_ROOT_DIR%
echo DMS_ENV_DIR=%DMS_ENV_DIR%
echo DMS_ENV=%DMS_ENV%
echo DMS_ENV_ORDER=%DMS_ENV_ORDER%
cd %SAVE_CURRENT_DIR%
