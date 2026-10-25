@echo off
title BANKCORE - Distributed Banking Master Suite
color 0B
echo ======================================================================
echo    BANKCORE DISTRIBUTED HOME LOAN PLATFORM — MASTER SUITE
echo ======================================================================
echo.
echo  Available Launch Modes:
echo    [1] Launch Master Interactive Console (Java Orchestrator)
echo    [2] Run Quick End-to-End Sanction Demonstration (Full Distributed Pipeline)
echo    [3] Start Unified Web Management Console (http://localhost:3000)
echo    [4] Launch Batch Underwriting Engine (MPI Collectives GUI)
echo    [5] Launch Portfolio Risk & Delinquency Engine (MapReduce GUI)
echo    [6] Launch Gateway Traffic Management (Load Balancer & Rate Limiter GUI)
echo    [7] Launch Replicated Ledger Storage (Quorum & Consistency GUI)
echo    [8] Launch Cluster High Availability (Failover & Mutex GUI)
echo    [9] Exit
echo.
set /p opt="Select an option (1-9): "

if "%opt%"=="1" (
    cls
    "C:\Users\Tanish\JDK\jdk-21.0.12\bin\java.exe" MasterBankLauncher
    pause
    goto end
)
if "%opt%"=="2" (
    cls
    "C:\Users\Tanish\JDK\jdk-21.0.12\bin\java.exe" MasterBankLauncher --demo
    pause
    goto end
)
if "%opt%"=="3" (
    cls
    echo Starting Web Console...
    start "" "http://localhost:3000"
    python -m http.server 3000 --directory "%~dp0frontend"
    goto end
)
if "%opt%"=="4" (
    start "" "C:\Users\Tanish\JDK\jdk-21.0.12\bin\java.exe" -cp Exp9 BankMPICollectiveGUI
    goto end
)
if "%opt%"=="5" (
    start "" "C:\Users\Tanish\JDK\jdk-21.0.12\bin\java.exe" -cp Exp8 BankMapReduceGUI
    goto end
)
if "%opt%"=="6" (
    start "" "C:\Users\Tanish\JDK\jdk-21.0.12\bin\java.exe" -cp Exp7 BankLoadBalancerGUI
    goto end
)
if "%opt%"=="7" (
    start "" "C:\Users\Tanish\JDK\jdk-21.0.12\bin\java.exe" -cp Exp6 BankConsistencyGUI
    goto end
)
if "%opt%"=="8" (
    start "" "C:\Users\Tanish\JDK\jdk-21.0.12\bin\java.exe" -cp Exp5 BankCoreGUI
    goto end
)

:end
