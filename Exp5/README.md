# Experiment 5 — Fault Tolerance in Distributed Systems (Primary-Backup Replication)

## Project Title
**Implementation of Fault Tolerance in a Java-based Distributed Banking Home Loan Management System using Primary-Backup (Passive) Replication**

---

## Experiment Aim
To implement Fault Tolerance in a Java-based Distributed Banking Home Loan Management System using Primary-Backup (Passive) Replication, ensuring that the loan database survives a coordinator/server crash without loss of data and without service interruption to connected clients.

---

## Theory & Architectural Principles

### 1. Fault Tolerance & Replication
Fault Tolerance is the property of a distributed system that allows it to continue operating correctly even when one or more components fail.
In banking, the loan server cannot be a single point of failure — if the coordinator crashes, in-flight loan submissions and EMI records must not be lost, and clients must still be able to reach a working server.

### 2. Replication Strategies
- **Active Replication**: Every replica independently processes every client request in parallel. Requires strict deterministic ordering (e.g. via Lamport clocks).
- **Passive / Primary-Backup Replication**: Only one replica (the Primary) processes client requests. After each state-changing operation, the Primary forwards (replicates) the update to Backup replicas. Backups stay passive until the Primary fails, at which point a Backup is promoted to become the new Primary.

### 3. Primary-Backup Mechanism
- **Primary Role**: Accepts client connections on port 5000, updates `loanDatabase`, and returns responses.
- **Write Propagation**: Immediately sends `REPLICATE|<req>` over dedicated replication port 6000 to the Backup, which updates its mirrored copy.
- **Heartbeat & Failure Detection**: Primary periodically sends `PING` heartbeats (every 2000ms). The Backup runs a monitor thread; if no heartbeat arrives within 6000ms, it assumes the Primary is DOWN.
- **Failover / Promotion**: On timeout, Backup promotes itself (`promoteToPrimary()`), opens client ServerSocket on port 5000, and serves clients with zero data loss.
- **Client-Side Failover**: `FailoverLoanClient` catches connection refused errors on Primary and automatically retries against the Backup.

---

## Folder Structure

```text
Exp5/
├── PrimaryBackupLoanServer.java     # Fault-Tolerant Server (Primary/Backup modes)
├── FailoverLoanClient.java          # Client with automatic failover reconnect
│
├── screenshots/                     # Real execution screenshots
│   ├── 01_Primary_Backup_Startup.png
│   ├── 02_Client_Submit_Replicated.png
│   ├── 03_Primary_Crash_Failover_Promotion.png
│   └── 04_Client_Failover_Reconnect.png
│
├── README.md                        # Lab manual & theory guide
├── VERIFICATION_REPORT.md           # Execution verification report
├── SCREENSHOT_RECOMMENDATION.md     # Screenshot submission guide
├── DC_Lab_Exp_5_FaultTolerance.docx # Word report in official SPIT template
└── DC_Lab_Exp_5_FaultTolerance.pdf  # PDF report in official SPIT template
```

---

## Compilation & Run Commands

```powershell
cd "C:\Users\Tanish\OneDrive\Desktop\DC Home Loan\Exp5"
$env:Path += ";C:\Users\Tanish\JDK\jdk-21.0.12\bin"
javac PrimaryBackupLoanServer.java FailoverLoanClient.java
```

### Running the Demonstration:
1. **Terminal 1 (Primary Server)**:
   ```powershell
   java PrimaryBackupLoanServer PRIMARY localhost
   ```
2. **Terminal 2 (Backup Server)**:
   ```powershell
   java PrimaryBackupLoanServer BACKUP localhost
   ```
3. **Terminal 3 (Client)**:
   ```powershell
   java FailoverLoanClient
   ```
4. **Simulate Crash**: Press `Ctrl+C` in Terminal 1 (Primary).
5. **Observe Failover**: Watch Terminal 2 promote itself after 6s timeout, and run option 3 in Terminal 3 (Client) to verify seamless reconnection!
