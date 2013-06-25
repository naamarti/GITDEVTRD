@echo off

:GETDATE

set DEFAULT_DATE=20130325

set DATABASE_BCP_DATE=

echo.
set /P DATABASE_BCP_DATE=Enter the BCP date, in format YYYYMMDD (0 to exit, blank to accept default '%DEFAULT_DATE%'): 

IF "%DATABASE_BCP_DATE%"=="0" (
	SET DATABASE_BCP_DATE=
	goto :eof
)

IF "%DATABASE_BCP_DATE%"=="" (
	set DATABASE_BCP_DATE=%DEFAULT_DATE%
	goto :eof
)

set YEAR=%DATABASE_BCP_DATE:~0,4%
set MONTH=%DATABASE_BCP_DATE:~4,2%
set DAY=%DATABASE_BCP_DATE:~6,2%
set MONTHSTR=

IF "%YEAR%" LSS "2008" (
	echo Invalid Year Specified. 
	goto GETDATE
)

IF "%YEAR%" GTR "2020" (
	echo Invalid Year Specified. 
	goto GETDATE
)

IF "%MONTH%" LSS "01" (
	echo Invalid Month Specified. 
	goto GETDATE
)

IF "%MONTH%" GTR "12" (
	echo Invalid Month Specified. 
	goto GETDATE
)

IF "%DAY%" LSS "01" (
	echo Invalid Day Specified. 
	goto GETDATE
)

IF "%DAY%" GTR "31" (
	echo Invalid Day Specified. 
	goto GETDATE
)

