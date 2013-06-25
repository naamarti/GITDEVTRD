@echo off

set LOG_FILE=%1

(Find /I /C "Error = " %LOG_FILE%) > NUL
if ERRORLEVEL 1 (EXIT /b 0) else (EXIT /b 1)

