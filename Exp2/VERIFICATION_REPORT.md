# Verification Report — Experiment 2 (DC Lab)

## Environment Specification
- **System**: Windows 11 (64-bit)
- **Java Platform**: OpenJDK 21
- **Experiment Title**: Implementation of Multithreading in a Distributed Banking System
- **Date of Execution**: August 11, 2026

---

## Execution Matrix

| Test ID | Feature / Test Case | Expected Behavior | Status |
| :--- | :--- | :--- | :---: |
| **EX2-COMP** | Compilation of `MultiThreadedLoanServer.java` & `LoanClient.java` | Clean compilation (`0` errors, `.class` generated) | **PASS** |
| **EX2-SRV** | Multithreaded Server Initialization | Server binds to TCP port 5000 and waits for clients | **PASS** |
| **EX2-CLI-1** | Client 1 Connection & Listener Thread | Spawns background listener thread and shows interactive menu | **PASS** |
| **EX2-CLI-2** | Client 2 Connection & Listener Thread | Client 2 connects concurrently, server assigns new thread | **PASS** |
| **EX2-SUB-1** | Client 1 Loan Application Submission | `HL1001` generated under thread lock (`synchronized`) | **PASS** |
| **EX2-SUB-2** | Client 2 Concurrent Loan Application Submission | `HL1002` generated concurrently without ID collision | **PASS** |
| **EX2-EMI** | Client 1 Concurrent EMI Payment | Balance for `HL1001` updated thread-safely (₹50L -> ₹45L) | **PASS** |
| **EX2-BCST** | Real-Time Async Broadcast Notification | Client 2 listener thread receives real-time broadcast of Client 1's transaction | **PASS** |
| **EX2-LOG** | Multithread Server Logging | Server displays `[THREAD <id>]` log tags for incoming client requests | **PASS** |

---

## Verification Summary
- **Total Tests**: 9
- **Passed**: 9
- **Failed**: 0
- **Result**: **PASS**
