@echo off
:: forcing ansi formatting
set DMS_DEPLOY_TO_ENV=
set DMS_DEPLOY_TO_SQLSERVER=
set DMS_SQL_IS_INTEGRATED_LOGIN=
set DMS_SQL_USER=
set DMS_SQL_PASSWORD=
set TOMCAT_NAME=TOMCAT7

:: *************************************************************************************************
:CHOOSE_DEPLOY_TO_ENVIRONMENT
:: *************************************************************************************************
set CURRENT_STEP=CHOOSE_DEPLOY_TO_ENVIRONMENT
cls
echo --------------------------------------------------------------------------
echo %SCRIPT_TITLE%:
echo --------------------------------------------------------------------------
echo User Name:                     %USERNAME%
echo Computer Name:                 %COMPUTERNAME%
echo Source Control Environment:    %DMS_ENV%
echo Deployment Type:               %DEPLOY_CODE_TYPE%
echo --------------------------------------------------------------------------
echo.
echo Deploy To Environment:
echo.
echo Enter [1]  for LOCALHOST

echo Enter [3]  for DEV_A        on %DMS_SQLSERVER1_DEV_A%
echo Enter [4]  for DEV_B        on %DMS_SQLSERVER1_DEV_B%
echo Enter [5]  for DEV_C        on %DMS_SQLSERVER1_DEV_C%
echo Enter [6]  for DEV_D        on %DMS_SQLSERVER1_DEV_D%
echo Enter [7]  for DEV_E        on %DMS_SQLSERVER1_DEV_E%

echo Enter [8]  for QAT_A        on %DMS_SQLSERVER1_QAT_A%

echo Enter [9]  for UAT_A        on %DMS_SQLSERVER1_UAT_A%
echo Enter [10] for UAT_B        on %DMS_SQLSERVER1_UAT_B%

echo Enter [11] for PROD_A_PRI   on %DMS_SQLSERVER1_PRD_A_PRI%
echo Enter [12] for PROD_A_BAK   on %DMS_SQLSERVER1_PRD_A_BAK%
echo Enter [13] for PROD_B_PRI   on %DMS_SQLSERVER1_PRD_B_PRI%
echo Enter [14] for PROD_B_BAK   on %DMS_SQLSERVER1_PRD_B_BAK%

echo Enter [r] to return to previous screen
echo Enter [x] to exit
echo.
echo --------------------------------------------------------------------------

set CHOICE=
set /p CHOICE=?
if not "%CHOICE%"=="" set CHOICE=%CHOICE:~0,2%
if "%CHOICE%"=="1" (
	set DMS_DEPLOY_TO_ENV=local
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_LOCAL%
	set DEPLOY_TO_ENV_DESCR=LOCALHOST
	set DMS_DEPLOY_TO_SQLSERVER=LOCALHOST
	set DMS_DEPLOY_TO_APPSERVER=
	set TOMCAT_NAME=%DMS_APPNAME_DEV_A%
) else if "%CHOICE%"=="3" (
	set DMS_DEPLOY_TO_ENV=dev_a
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_DEV%
	set DEPLOY_TO_ENV_DESCR=DEVELOPMENT A on %DMS_SQLSERVER1_DEV_A%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_DEV_A%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_DEV_A%
	set TOMCAT_NAME=%DMS_APPNAME_DEV_A%
) else if "%CHOICE%"=="4" (
	set DMS_DEPLOY_TO_ENV=dev_b
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_DEV%
	set DEPLOY_TO_ENV_DESCR=DEVELOPMENT B on %DMS_SQLSERVER1_DEV_B%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_DEV_B%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_DEV_B%
	set TOMCAT_NAME=%DMS_APPNAME_DEV_B%
) else if "%CHOICE%"=="5" (
	set DMS_DEPLOY_TO_ENV=dev_c
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_DEV%
	set DEPLOY_TO_ENV_DESCR=DEVELOPMENT C on %DMS_SQLSERVER1_DEV_C%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_DEV_C%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_DEV_C%
	set TOMCAT_NAME=%DMS_APPNAME_DEV_C%
) else if "%CHOICE%"=="6" (
	set DMS_DEPLOY_TO_ENV=dev_d
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_DEV%
	set DEPLOY_TO_ENV_DESCR=DEVELOPMENT D on %DMS_SQLSERVER1_DEV_D%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_DEV_D%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_DEV_D%
	set TOMCAT_NAME=%DMS_APPNAME_DEV_D%
) else if "%CHOICE%"=="7" (
	set DMS_DEPLOY_TO_ENV=dev_e
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_DEV%
	set DEPLOY_TO_ENV_DESCR=DEVELOPMENT E on %DMS_SQLSERVER1_DEV_E%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_DEV_E%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_DEV_E%
	set TOMCAT_NAME=%DMS_APPNAME_DEV_E%
) else if "%CHOICE%"=="8" (
	set DMS_DEPLOY_TO_ENV=qat_a
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_QA%
	set DEPLOY_TO_ENV_DESCR=QAT A on %DMS_SQLSERVER1_QAT_A%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_QAT_A%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_QAT_A%
	set TOMCAT_NAME=%DMS_APPNAME_QAT_A%
) else if "%CHOICE%"=="9" (
	set DMS_DEPLOY_TO_ENV=uat_a
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_UAT%
	set DEPLOY_TO_ENV_DESCR=UAT A on %DMS_SQLSERVER1_UAT_A%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_UAT_A%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_UAT_A%
	set TOMCAT_NAME=%DMS_APPNAME_UAT_A%
) else if "%CHOICE%"=="10" (
	set DMS_DEPLOY_TO_ENV=uat_b
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_UAT%
	set DEPLOY_TO_ENV_DESCR=UAT B on %DMS_SQLSERVER1_UAT_B%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_UAT_B%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_UAT_B%
	set TOMCAT_NAME=%DMS_APPNAME_UAT_B%
) else if "%CHOICE%"=="11" (
	set DMS_DEPLOY_TO_ENV=prd_a_pri
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_PROD%
	set DEPLOY_TO_ENV_DESCR=PRODUCTION A PRIMARY on %DMS_SQLSERVER1_PRD_A_PRI%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_PRD_A_PRI%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_PRD_A_PRI%
	set TOMCAT_NAME=%DMS_APPNAME_PRD_A_PRI%
) else if "%CHOICE%"=="12" (
	set DMS_DEPLOY_TO_ENV=prd_a_bak
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_PROD%
	set DEPLOY_TO_ENV_DESCR=PRODUCTION A BACKUP on %DMS_SQLSERVER1_PRD_A_BAK%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_PRD_A_BAK%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_PRD_A_BAK%
	set TOMCAT_NAME=%DMS_APPNAME_PRD_A_BAK%
) else if "%CHOICE%"=="13" (
	set DMS_DEPLOY_TO_ENV=prd_b_pri
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_PROD%
	set DEPLOY_TO_ENV_DESCR=PRODUCTION B PRIMARY on %DMS_SQLSERVER1_PRD_B_PRI%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_PRD_B_PRI%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_PRD_B_PRI%
	set TOMCAT_NAME=%DMS_APPNAME_PRD_B_PRI%
) else if "%CHOICE%"=="14" (
	set DMS_DEPLOY_TO_ENV=prd_b_bak
	set DMS_DEPLOY_TO_ENV_ORDER=%DMS_ENV_ORDER_PROD%
	set DEPLOY_TO_ENV_DESCR=PRODUCTION B BACKUP on %DMS_SQLSERVER1_PRD_B_BAK%
	set DMS_DEPLOY_TO_SQLSERVER=%DMS_SQLSERVER1_PRD_B_BAK%
	set DMS_DEPLOY_TO_APPSERVER=%DMS_APPSERVER1_PRD_B_BAK%
	set TOMCAT_NAME=%DMS_APPNAME_PRD_B_BAK%
) else if "%CHOICE%"=="r" (
	call %DEPLOY_DIR%\deploy.cmd :CHOOSE_DEPLOYMENT_TYPE
	exit 0
) else if "%CHOICE%"=="x" (
	exit 0
) else (
	goto %CURRENT_STEP%
	goto:eof
)
call :ValidateDeployToEnv IsValidEnv
if "%IsValidEnv%"=="0" (
	goto %CURRENT_STEP%
	goto:eof
)


:: *************************************************************************************************
:CHOOSE_SQL_LOGIN_TYPE
:: *************************************************************************************************
set CURRENT_STEP=CHOOSE_SQL_LOGIN_TYPE
set PREVIOUS_STEP=CHOOSE_DEPLOY_TO_ENVIRONMENT
cls
echo --------------------------------------------------------------------------
echo %SCRIPT_TITLE%:
echo --------------------------------------------------------------------------
echo User Name:                     %USERNAME%
echo Computer Name:                 %COMPUTERNAME%
echo Source Control Environment:    %DMS_ENV%
echo Deployment Type:               %DEPLOY_CODE_TYPE%
echo Deploy To Environment:         %DEPLOY_TO_ENV_DESCR%
echo --------------------------------------------------------------------------
echo.
echo SQL Server Login Type:
echo.
echo Enter [1] for Windows Integrated Login
echo Enter [2] for SQL Server Login
echo Enter [r] to return to previous screen
echo Enter [x] to exit
echo.
echo --------------------------------------------------------------------------

set CHOICE=
set /p CHOICE=?
if not "%CHOICE%"=="" set CHOICE=%CHOICE:~0,1%
if "%CHOICE%"=="1" (
	set DMS_SQL_IS_INTEGRATED_LOGIN=1
	set DMS_SQL_USER=******
	set DMS_SQL_PASSWORD=******
) else if "%CHOICE%"=="2" (
	set DMS_SQL_IS_INTEGRATED_LOGIN=0
	set DMS_SQL_USER=tbsadmin
	set DMS_SQL_PASSWORD=tbsadmin
) else if "%CHOICE%"=="r" (
	goto %PREVIOUS_STEP%
	goto:eof
) else if "%CHOICE%"=="x" (
	exit 0
) else (
	goto %CURRENT_STEP%
	goto:eof
)

:: *************************************************************************************************
:CHOOSE_DATABASE_ACTION
:: *************************************************************************************************
set CURRENT_STEP=CHOOSE_DATABASE_ACTION
set PREVIOUS_STEP=CHOOSE_SQL_LOGIN_TYPE
cls
echo --------------------------------------------------------------------------
echo %SCRIPT_TITLE%:
echo --------------------------------------------------------------------------
echo User Name:                     %USERNAME%
echo Computer Name:                 %COMPUTERNAME%
echo Source Control Environment:    %DMS_ENV%
echo Deployment Type:               %DEPLOY_CODE_TYPE%
echo Deploy To Environment:         %DEPLOY_TO_ENV_DESCR%
echo Database Integrated Login:     %DMS_SQL_IS_INTEGRATED_LOGIN%
echo --------------------------------------------------------------------------
echo.
echo Database Action:
echo.
echo Enter [1] to Get BCP data files from Production
echo Enter [2] to Rebuild Databases with Production data
echo Enter [3] to Apply Release Scripts and Code
echo Enter [4] to Run #1, #2 and #3
echo.
echo Enter [r] to return to previous screen
echo Enter [x] to exit
echo.
echo --------------------------------------------------------------------------

set RUN_GETFILES=0
set RUN_REBUILD=0
set RUN_APPLY=0
set RUN_PATCH=0
set RUN_INITENV=0
set PROMPT_FOR_DATE=0

set CHOICE=
set /p CHOICE=?
if not "%CHOICE%"=="" set CHOICE=%CHOICE:~0,1%

if "%CHOICE%"=="1" (
	set DEPLOY_DATABASE_ACTION=GET_FILES
	set RUN_GETFILES=1
	set PROMPT_FOR_DATE=1
) else if "%CHOICE%"=="2" (
	set DEPLOY_DATABASE_ACTION=REBUILD_DATABASES
	set RUN_REBUILD=1
	set PROMPT_FOR_DATE=1
) else if "%CHOICE%"=="3" (
	set DEPLOY_DATABASE_ACTION=APPLY_RELEASES
	set RUN_APPLY=1
) else if "%CHOICE%"=="4" (
	set DEPLOY_DATABASE_ACTION=GET_FILES__REBUILD_DATABASES__APPLY_RELEASES
	set RUN_GETFILES=1
	set RUN_REBUILD=1
	set RUN_APPLY=1
	set PROMPT_FOR_DATE=1

) else if "%CHOICE%"=="r" (
	goto %PREVIOUS_STEP%
	goto:eof
) else if "%CHOICE%"=="x" (
	exit 0
) else (
	goto %CURRENT_STEP%
	goto:eof
)

:: *************************************************************************************************
:CHOOSE_DATE
:: *************************************************************************************************
cls
set DATABASE_BCP_DATE=
if "%PROMPT_FOR_DATE%"=="1" (
	call %DEPLOY_DIR%\deploy\database\getDate.cmd
) else (
	goto CHOOSE_DATABASES
)
if "%DATABASE_BCP_DATE%"=="" (
	goto %CURRENT_STEP%
	goto:eof
)

:: *************************************************************************************************
:CHOOSE_DATABASES
:: *************************************************************************************************
set CURRENT_STEP=CHOOSE_DATABASES
set PREVIOUS_STEP=CHOOSE_DATABASE_ACTION
cls
echo --------------------------------------------------------------------------
echo %SCRIPT_TITLE%:
echo --------------------------------------------------------------------------
echo User Name:                     %USERNAME%
echo Computer Name:                 %COMPUTERNAME%
echo Source Control Environment:    %DMS_ENV%
echo Deployment Type:               %DEPLOY_CODE_TYPE%
echo Deploy To Environment:         %DEPLOY_TO_ENV_DESCR%
echo Database Integrated Login:     %DMS_SQL_IS_INTEGRATED_LOGIN%
echo Database Action:               %DEPLOY_DATABASE_ACTION%
echo BCP Date:                      %DATABASE_BCP_DATE%
echo --------------------------------------------------------------------------
echo.
echo Select Databases:
echo.

echo Enter [1] for Trading

echo Enter [r] to return to previous screen
echo Enter [x] to exit
echo.
echo --------------------------------------------------------------------------

set CHOICE=
set /p CHOICE=?
if not "%CHOICE%"=="" set CHOICE=%CHOICE:~0,1%

if "%CHOICE%"=="1" (
	set DEPLOY_DATABASES=Trading
	set DEPLOY_DATABASE_COMMON=0
	set DEPLOY_DATABASE_RECON=0
	set DEPLOY_DATABASE_PROGRAM=0
	set DEPLOY_DATABASE_TRADING=1
) else if "%CHOICE%"=="r" (
	goto %PREVIOUS_STEP%
	goto:eof
) else if "%CHOICE%"=="x" (
	exit 0
) else (
	goto %CURRENT_STEP%
	goto:eof
)


:: *************************************************************************************************
:CHOOSE_BEGIN_CONFIRM
:: *************************************************************************************************
set CURRENT_STEP=CHOOSE_BEGIN_CONFIRM
set PREVIOUS_STEP=CHOOSE_DATABASES
cls
echo --------------------------------------------------------------------------
echo %SCRIPT_TITLE%:
echo --------------------------------------------------------------------------
echo User Name:                     %USERNAME%
echo Computer Name:                 %COMPUTERNAME%
echo Source Control Environment:    %DMS_ENV%
echo Deployment Type:               %DEPLOY_CODE_TYPE%
echo Deploy To Environment:         %DEPLOY_TO_ENV_DESCR%
echo Database Integrated Login:     %DMS_SQL_IS_INTEGRATED_LOGIN%
echo Database Action:               %DEPLOY_DATABASE_ACTION%
echo BCP Date:                      %DATABASE_BCP_DATE%
echo Databases:                     %DEPLOY_DATABASES%
echo --------------------------------------------------------------------------
echo.
echo Ready to begin:
echo.
echo Enter [1] to continue
echo Enter [r] to return to previous screen
echo Enter [x] to exit
echo.
echo --------------------------------------------------------------------------

set CHOICE=
set /p CHOICE=?
if not "%CHOICE%"=="" set CHOICE=%CHOICE:~0,1%
if "%CHOICE%"=="1" (
	set BEGIN_CONFIRM=1
) else if "%CHOICE%"=="r" (
	goto %PREVIOUS_STEP%
	goto:eof
) else if "%CHOICE%"=="x" (
	exit 0
) else (
	goto %CURRENT_STEP%
	goto:eof
)

cls
call %DEPLOY_UTIL_DIR%\SetDeployLogFileName.cmd

call %DEPLOY_LOGGER% "--------------------------------------------------------------------------"
call %DEPLOY_LOGGER% "%SCRIPT_TITLE%:"
call %DEPLOY_LOGGER% "--------------------------------------------------------------------------"
call %DEPLOY_LOGGER% "User Name:                     %USERNAME%"
call %DEPLOY_LOGGER% "Computer Name:                 %COMPUTERNAME%"
call %DEPLOY_LOGGER% "Source Control Environment:    %DMS_ENV%"
call %DEPLOY_LOGGER% "Deployment Type:               %DEPLOY_CODE_TYPE%"
call %DEPLOY_LOGGER% "Deploy To Environment:         %DEPLOY_TO_ENV_DESCR%"
call %DEPLOY_LOGGER% "Database Integrated Login:     %DMS_SQL_IS_INTEGRATED_LOGIN%"
call %DEPLOY_LOGGER% "Database Action:               %DEPLOY_DATABASE_ACTION%"
call %DEPLOY_LOGGER% "BCP Date:                      %DATABASE_BCP_DATE%"

call %DEPLOY_LOGGER% "Databases:                     %DEPLOY_DATABASES%"
call %DEPLOY_LOGGER% "--------------------------------------------------------------------------"
call %DEPLOY_LOGGER% ""
call %DEPLOY_LOGGER% "Running Deploy..."
call %DEPLOY_LOGGER% ""

if "%RUN_GETFILES%"=="1" (
	call %DMS_ENV_DIR%\database\scripts\build\GetAndUnCompressBackupFiles.cmd
	if ERRORLEVEL 1 (
		call %DEPLOY_LOGGER% "*** Failed in Get BCP Files"
		exit /b 1
	)	
)

if "%RUN_REBUILD%"=="1" (
	if "%DMS_DEPLOY_TO_SQLSERVER%"=="LOCALHOST" (
		call %DMS_ENV_DIR%\database\scripts\build\RecreateAllDatabases.cmd
	) else (
		call %DMS_ENV_DIR%\database\scripts\build\RebuildAllDatabases.cmd
	)
	if ERRORLEVEL 1 (
		call %DEPLOY_LOGGER% "*** Failed in Rebuild Databases"
		exit /b 1
	)	
)

if "%RUN_APPLY%"=="1" (
	call %DMS_ENV_DIR%\database\scripts\build\ApplyAllReleases.cmd
	if ERRORLEVEL 1 (
		call %DEPLOY_LOGGER% "*** Failed in Apply All"
		exit /b 1
	)	
) else if "%RUN_PATCH%"=="1" (
	call %DMS_ENV_DIR%\database\scripts\build\PatchRelease.cmd
	if ERRORLEVEL 1 (
		call %DEPLOY_LOGGER% "*** Failed in Patch Release"
		exit /b 1
	)	
) else if "%RUN_INITENV%"=="1" (
	call %DMS_ENV_DIR%\database\scripts\build\InitializeEnvironment.cmd
	if ERRORLEVEL 1 (
		call %DEPLOY_LOGGER% "*** Failed in Initialize Environment"
		exit /b 1
	)
)

if not "%DMS_DEPLOY_TO_ENV%" == "local" (
	call %DEPLOY_UTIL_DIR%\TomcatStop.cmd
	if ERRORLEVEL 1 (
		exit /b 1
	)

	call %DEPLOY_UTIL_DIR%\TomcatStart.cmd
	if ERRORLEVEL 1 (
		exit /b 1
	)
)

goto :eof


:: ---------------------------------------------------------------------------------------
:ValidateDeployToEnv
::    %~1: IsValid: Return Value (0 or 1)
:: ---------------------------------------------------------------------------------------
setlocal
set IsValid=1
if "%DMS_DEPLOY_TO_ENV_ORDER%" gtr "%DMS_ENV_ORDER%" (
	cls
	set IsValid=0
	echo.
	echo ***** WARNING! *****
	echo Invalid Environment Selection:
	echo.
	echo From PROD Source Control, you can deploy to PROD, UAT, QA, DEV, LOCAL.
	echo From QA   Source Control, you can deploy to .......... QA, DEV, LOCAL.
	echo From DEV  Source Control, you can deploy to .............. DEV, LOCAL.
	echo.	
	echo Press any key to reselect environment...
	echo.
	set /p CHOICE=?
)
endlocal&if not "%~1"=="" set "%~1=%IsValid%"
goto:eof

