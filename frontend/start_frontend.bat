@echo off
title BANKCORE - Distributed Banking Web Console
echo ===================================================
echo  Starting BANKCORE Distributed Banking Frontend...
echo ===================================================
echo Opening web console at http://localhost:3000
start "" "http://localhost:3000"
python -m http.server 3000 --directory "%~dp0"
pause
