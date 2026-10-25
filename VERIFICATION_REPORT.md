# Verification Report — Experiment 1 (DC Lab)

## Environment Specification
- **System**: Windows 11 (64-bit)
- **Java Platform**: OpenJDK 21
- **Communication Protocols**: TCP Sockets (Part A), Java Remote Method Invocation / RMI Registry (Part B)
- **Date of Execution**: August 10, 2026

---

## 1. Part A — Socket-based Client-Server (RPC Style)

| Test ID | Test Case / Description | Expected Output | Status |
| :--- | :--- | :--- | :---: |
| **RPC-COMP** | Compilation of `LoanServer.java` and `LoanClient.java` | Clean compilation (`.class` files generated, 0 errors) | **PASS** |
| **RPC-SRV** | Execution of `LoanServer` on port 5000 | Output: `Home Loan Server Started... Waiting for clients...` | **PASS** |
| **RPC-CLI-1** | Connection of Client 1 (`LoanClient`) | Connection accepted, main menu displayed | **PASS** |
| **RPC-CLI-2** | Connection of Client 2 (`LoanClient`) | Concurrent connection accepted, server logs new client | **PASS** |
| **RPC-SUB-1** | Client 1 SUBMIT operation (`C101`, `Tanish`, `5000000`, `20`, `8.5`) | `Loan ID: HL1001`, `Status: UNDER_REVIEW` returned | **PASS** |
| **RPC-SUB-2** | Client 2 SUBMIT operation (`C102`, `Rahul`, `3500000`, `15`, `9.0`) | `Loan ID: HL1002`, `Status: UNDER_REVIEW` returned | **PASS** |
| **RPC-STAT** | Client 1 STATUS query (`HL1001`) | `Loan ID: HL1001 \| Status: UNDER_REVIEW` returned | **PASS** |
| **RPC-DET** | Client 1 DETAILS query (`HL1001`) | Full details for `HL1001` retrieved from server | **PASS** |
| **RPC-MULTI** | Concurrent multi-client execution | Server maintains isolated streams & state for all clients | **PASS** |

---

## 2. Part B — Java Remote Method Invocation (RMI)

| Test ID | Test Case / Description | Expected Output | Status |
| :--- | :--- | :--- | :---: |
| **RMI-COMP** | Compilation of `LoanInterface`, `LoanServer`, `RMIServer`, `RMIClient` | Clean compilation (`.class` files generated, 0 errors) | **PASS** |
| **RMI-SRV** | Execution of `RMIServer` | RMI Registry created on port 1099, `LoanService` bound | **PASS** |
| **RMI-CLI-1** | Execution of `RMIClient` | Lookup succeeds via `rmi://localhost/LoanService` | **PASS** |
| **RMI-SUB** | Remote method call `submitLoan()` | Loan stored in memory, `HL1001` returned to client | **PASS** |
| **RMI-STAT** | Remote method call `getLoanStatus("HL1001")` | Remote status lookup succeeds (`UNDER_REVIEW`) | **PASS** |
| **RMI-DET** | Remote method call `getLoanDetails("HL1001")` | Full string details retrieved across RMI layer | **PASS** |
| **RMI-MULTI** | Dual RMI clients accessing shared `LoanService` | `UnicastRemoteObject` handles concurrent stub requests | **PASS** |

---

## 3. Summary of Verification

- **Total Test Cases**: 16
- **Passed**: 16
- **Failed**: 0
- **Overall Result**: **PASS**

All features specified in Experiment 1 were successfully implemented, compiled, executed, and verified.
