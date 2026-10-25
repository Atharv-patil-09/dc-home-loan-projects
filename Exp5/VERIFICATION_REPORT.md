# Verification Report — Experiment 5 (DC Lab)

## Environment Specification
- **System**: Windows 11 (64-bit)
- **Java Platform**: OpenJDK 21
- **Experiment Title**: Fault Tolerance via Primary-Backup Replication
- **Date of Execution**: September 28, 2026

---

## Execution Matrix

| Test ID | Feature / Test Case | Expected Behavior | Status |
| :--- | :--- | :--- | :---: |
| **EX5-COMP** | Compilation of Server & Client | Clean compilation (`0` errors, `.class` generated) | **PASS** |
| **EX5-START**| Primary & Backup Initialization | Primary binds to port 5000/6000; Backup starts replication listener on port 6000 | **PASS** |
| **EX5-HEART**| Heartbeat Monitoring | Primary sends `PING` every 2000ms; Backup updates `lastHeartbeatReceived` | **PASS** |
| **EX5-REPL** | Write Propagation | `SUBMIT` request on Primary triggers `REPLICATE` message to Backup; both databases updated | **PASS** |
| **EX5-FAIL** | Primary Crash & Promotion | Primary terminates; Backup detects 6000ms timeout, self-promotes, and opens port 5000 | **PASS** |
| **EX5-RECON**| Client-Side Failover | Client transparently catches connection refused on Primary and reconnects to promoted Backup | **PASS** |

---

## Verification Summary
- **Total Tests**: 6
- **Passed**: 6
- **Failed**: 0
- **Result**: **PASS**
