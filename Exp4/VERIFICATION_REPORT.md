# Verification Report — Experiment 4 (DC Lab)

## Environment Specification
- **System**: Windows 11 (64-bit)
- **Java Platform**: OpenJDK 21
- **Experiment Title**: Implementation of Election Algorithm (The Bully Algorithm)
- **Date of Execution**: August 24, 2026

---

## Execution Matrix

| Test ID | Feature / Test Case | Expected Behavior | Status |
| :--- | :--- | :--- | :---: |
| **EX4-COMP** | Compilation of `BullyElectionBanking.java` | Clean compilation (`0` errors, `.class` generated) | **PASS** |
| **EX4-INIT** | Initial Cluster State Verification | `BankNode-4` (highest ID: 4) initialized as active Coordinator | **PASS** |
| **EX4-CRASH**| Master Node Crash Simulation | `BankNode-4` marked as crashed/offline | **PASS** |
| **EX4-ELECT**| Election Trigger & Message Exchange | `BankNode-2` sends `ELECTION` to `BankNode-3` & `BankNode-4`; `BankNode-3` replies `OK` | **PASS** |
| **EX4-WIN**  | Election Winner & Broadcast | `BankNode-3` wins election and broadcasts `COORDINATOR` announcement | **PASS** |
| **EX4-RECOV**| Node Recovery & Re-Election ("Bully") | `BankNode-4` recovers, initiates election, and reclaims Coordinator role | **PASS** |

---

## Verification Summary
- **Total Tests**: 6
- **Passed**: 6
- **Failed**: 0
- **Result**: **PASS**
