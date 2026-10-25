@echo off
title BANKCORE Web Management Console
color 0A
echo ======================================================================
echo    Starting BANKCORE Web Console at http://localhost:3000
echo ======================================================================
start "" "http://localhost:3000"
python -m http.server 3000 --directory "%~dp0frontend"
pause
