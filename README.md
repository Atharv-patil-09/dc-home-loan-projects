# BANKCORE — Distributed Home Loan Banking Platform

**A Complete, Unified, End-to-End Enterprise Distributed Banking Platform**  
*Department of Computer Engineering*

**Engineering Team**:
- **Atharv Patil** (UID: 2024300165)
- **Tanish Parakh** (UID: 2024300157)
- **Gitesh Patil** (UID: 2024300166)  
*Division: C | Batch: C1*

---

## 🏛️ Platform Architecture Overview

**BANKCORE** is a high-availability, fault-tolerant distributed banking platform designed for automated mortgage underwriting, multi-node ledger replication, client traffic shaping, regional portfolio risk analytics, and real-time distributed consensus across banking nodes.

The system integrates **9 core distributed systems paradigms** into a cohesive enterprise architecture:

```
[Borrower Clients] ➔ [Token Bucket Rate Limiter (Capacity: 10, Refill: 2/s)]
                   ➔ [Weighted Round-Robin Router (W: 5 : 3 : 2)]
                   ➔ [Bully Leader Consensus (BankNode 04 Master)]
                   ➔ [Cristian Monotonic Clock Sync (RTT/2)]
                   ➔ [Ricart-Agrawala Distributed Mutex Lock]
                   ➔ [Quorum Replicated Ledger Storage (W=2, R=2, N=3)]
                   ➔ [MPI Collective Parallel Underwriting (4 Ranks)]
                   ➔ [Distributed MapReduce Regional Risk Analytics]
                   ➔ [Socket RPC & Java RMI Client Finalization]
```

---

## 🚀 Quick Start Guide (Run on Any Machine)

### Option A: Windows (One-Click)
Double-click `start_bankcore_suite.bat` in the project root:
```bat
.\start_bankcore_suite.bat
```
Choose from:
- `[1]` Launch Master Interactive Console (Java Orchestrator)
- `[2]` Run Quick End-to-End Sanction Demonstration (Full Pipeline in ~2 seconds)
- `[3]` Start Unified Web Management Console (`http://localhost:3000`)
- `[4]-[8]` Launch Java Swing Desktop GUIs for individual distributed subsystems.

Or double-click `run_web.bat` to launch the Web Console instantly at `http://localhost:3000`.

---

### Option B: Linux / macOS
Grant execution permission and run:
```bash
chmod +x start_bankcore_suite.sh run_web.sh
./start_bankcore_suite.sh
```
Or start the web management console directly:
```bash
./run_web.sh
```

---

### Option C: Docker Deployment (Zero-Dependency)
Build and run the container:
```bash
docker build -t bankcore .
docker run -d -p 3000:3000 -p 5000:5000 --name bankcore-app bankcore
```
Access the web management console at `http://localhost:3000`.

---

### Option D: Direct CLI Execution (Terminal)
Run the automated end-to-end distributed sanction journey:
```bash
java MasterBankLauncher --demo
```
This processes a ₹75,00,000 mortgage application across all 9 distributed computing layers in ~2 seconds and prints the **Official Distributed Home Loan Sanction Certificate** with SHA-256 state hash verification.

---

## 💻 Web Management Console Modules (`http://localhost:3000`)

The web management console provides an interactive control center:

1. **Dashboard**: Executive KPI overview (Active loans, ₹13.2 Cr total portfolio across 18 loans, node heartbeats, live activity feed).
2. **Loans Register**: 18 diverse Indian home loan portfolios with real-time search, filtering, CIBIL credit scores, property locations, and interactive EMI repayment simulation.
3. **Customer Accounts & KYC**: 18 verified borrower profiles with branch locations, masked PAN numbers, annual income, and Aadhaar e-KYC / DigiLocker verification statuses.
4. **Transactions Ledger**: Real-time distributed ledger recording auto-debit payments, pre-payments, and settlements across NACH ECS, NEFT, IMPS, and RTGS rails.
5. **Replication & Storage**: Interactive Quorum Consistency ($W=2, R=2, N=3$), Strong Consistency ($W=3$), and Eventual Consistency with live ledger balances, transaction sequence numbers (`#TXN-1043`), and stale read detection.
6. **Gateway & Rate Limiter**: Interactive Token-Bucket visualizer (10 tokens, refill 2/sec) with Weighted Round-Robin routing (5:3:2) and HTTP 429 throttling under traffic bursts.
7. **Portfolio Risk Analytics**: Distributed MapReduce engine with 4 parallel mappers and 2 reducers aggregating 1,248 loans into regional risk summaries in 25 ms (3.82x speedup).
8. **Batch Underwriting**: High-performance collective computing using `MPI_Bcast`, `MPI_Scatter`, parallel EMI compute, `MPI_Gather`, and `MPI_Reduce` (`MPI_SUM` = ₹5.12 Cr) with a 6.8x speedup.
9. **Cluster Nodes & Failover**: Active-Passive primary failover with 2000 ms heartbeat PINGs and dynamic Bully election.

---

## 📁 Repository Directory Structure

| Directory / File | Description |
| :--- | :--- |
| `MasterBankLauncher.java` | **Unified Master Orchestrator**: CLI and automated runner integrating all 9 distributed phases |
| `start_bankcore_suite.bat` | One-click Windows interactive launcher |
| `start_bankcore_suite.sh` | One-click Linux / macOS interactive launcher |
| `run_web.bat` / `run_web.sh` | Direct one-click Web Console launcher |
| `Dockerfile` | Container configuration for cloud and zero-dependency deployments |
| `frontend/` | Complete Web Management Console (`index.html`, `app.js`, `style.css`) |
| `Exp1/` & Root | Socket RPC Server & Client on Port 5000 (`LoanServer.java`, `LoanClient.java`) |
| `Exp2/` & `RMI/` | Java RMI Remote Invocation & Thread Pools on Port 1099 (`LoanInterface.java`) |
| `Exp3/` | Cristian's Monotonic Clock Sync & Lamport Logical Clocks |
| `Exp4/` | Bully Consensus Leader Election Engine (`BullyElectionBanking.java`) |
| `Exp5/` | Primary-Backup Replication & Failover on Port 6000 (`BankCoreGUI.java`) |
| `Exp6/` | Multi-Node Quorum Consistency ($W+R>N$) (`BankConsistencyGUI.java`) |
| `Exp7/` | Token Bucket Rate Limiter & Weighted Load Balancer (`BankLoadBalancerGUI.java`) |
| `Exp8/` | Distributed MapReduce Portfolio Risk Engine (`BankMapReduceGUI.java`) |
| `Exp9/` | MPI Collective Communication Underwriting Engine (`BankMPICollectiveGUI.java`) |

---

## 📊 Distributed Computing Guarantees & Benchmarks

1. **Quorum Linearizability & Zero Stale Reads**: Guaranteed when $W + R > N$ ($2 + 2 > 3$). Writes are confirmed only after majority consensus; reads intersect with at least one updated replica.
2. **Zero-Data-Loss Failover**: Active-Passive heartbeat listener (2000 ms PING, 6000 ms timeout) automatically promotes the backup node to active primary upon primary crash.
3. **Consensus Stability**: Bully algorithm guarantees that the node with the highest operational ID (`BankNode 04`) assumes master coordination.
4. **Race Condition Prevention**: Monotonic Lamport timestamps and Cristian's clock synchronization ($RTT/2$) coupled with Ricart-Agrawala mutual exclusion locks prevent concurrent overdrafts.
5. **DDoS & Traffic Defense**: Token-Bucket rate limiting enforces a 10-token capacity, gracefully rejecting excess traffic with `HTTP 429 Too Many Requests`.
6. **Parallel Acceleration**: MPI Collectives scale with $\mathcal{O}(\log_2 N)$ binomial trees delivering **6.8x speedup** over point-to-point unicast loops; MapReduce delivers **3.82x speedup** on portfolio risk analytics.
