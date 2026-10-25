#!/usr/bin/env bash
# BANKCORE - Distributed Banking Master Suite Launcher (Linux / macOS)

clear
echo "======================================================================"
echo "   BANKCORE DISTRIBUTED HOME LOAN PLATFORM — MASTER SUITE"
echo "======================================================================"
echo ""
echo " Available Launch Modes:"
echo "   [1] Launch Master Interactive Console (Java Orchestrator)"
echo "   [2] Run Quick End-to-End Sanction Demonstration (Full Pipeline)"
echo "   [3] Start Unified Web Management Console (http://localhost:3000)"
echo "   [4] Launch Batch Underwriting Engine (MPI Collectives GUI)"
echo "   [5] Launch Portfolio Risk & Delinquency Engine (MapReduce GUI)"
echo "   [6] Launch Gateway Traffic Management (Load Balancer GUI)"
echo "   [7] Launch Replicated Ledger Storage (Quorum & Consistency GUI)"
echo "   [8] Launch Cluster High Availability (Failover & Mutex GUI)"
echo "   [9] Exit"
echo ""
read -p "Select an option (1-9): " opt

case $opt in
  1)
    java MasterBankLauncher
    ;;
  2)
    java MasterBankLauncher --demo
    ;;
  3)
    echo "Starting Web Console at http://localhost:3000..."
    if which xdg-open > /dev/null; then
      xdg-open "http://localhost:3000" &
    elif which open > /dev/null; then
      open "http://localhost:3000" &
    fi
    python3 -m http.server 3000 --directory frontend
    ;;
  4)
    java -cp Exp9 BankMPICollectiveGUI
    ;;
  5)
    java -cp Exp8 BankMapReduceGUI
    ;;
  6)
    java -cp Exp7 BankLoadBalancerGUI
    ;;
  7)
    java -cp Exp6 BankConsistencyGUI
    ;;
  8)
    java -cp Exp5 BankCoreGUI
    ;;
  *)
    exit 0
    ;;
esac
