@echo off

setlocal

call %UTIL_SCRIPT_DIR%\GetBackupFile %1 %2
if ERRORLEVEL 1 (EXIT /b 1)

call %UTIL_SCRIPT_DIR%\UnCompressFile %1 %2
if ERRORLEVEL 1 (EXIT /b 1)

