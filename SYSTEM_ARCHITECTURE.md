# BANKCORE Distributed Banking Platform — System Architecture Specification

## Executive Summary

**BANKCORE** is an enterprise-grade, distributed home loan banking software platform designed to demonstrate the complete spectrum of distributed computing principles across **9 unified architectural phases**. 

The platform guarantees **fault tolerance, high throughput, data consistency, fair resource allocation, synchronized ordering, and scalable parallel analytics** across heterogeneous computing nodes.

---

## High-Level System Architecture Diagram

```
                                  [ Borrower Clients & Branch Terminals ]
                                                     │
                                                     ▼
                  ┌─────────────────────────────────────────────────────────────────────┐
                  │                 API GATEWAY & TRAFFIC SHAPER (Exp 7)                │
                  │   • Token-Bucket Rate Limiter (20 req/s, Burst: 10 Tokens, HTTP 429)│
                  └──────────────────────────────────┬──────────────────────────────────┘
                                                     │
                                                     ▼
                  ┌─────────────────────────────────────────────────────────────────────┐
                  │              CLUSTER LOAD BALANCER & ROUTER (Exp 7)                 │
                  │   • Weighted Round-Robin (Mumbai 5 : Delhi 3 : Bangalore 2)          │
                  │   • Dynamic Least-Connections Fail-Safe Routing                     │
                  └───────────────────┬─────────────────────────────┬───────────────────┘
                                      │                             │
                     Socket RPC (Port 5000) [Exp 1]        RMI Registry (Port 1099) [Exp 2]
                                      │                             │
                                      ▼                             ▼
       ┌────────────────────────────────────────────────────────────────────────────────────────┐
       │                       DISTRIBUTED CORE BANKING NODES (N = 4)                           │
       │                                                                                        │
       │   ┌────────────────────────┐  Heartbeat PING   ┌────────────────────────┐              │
       │   │  Bank Node 01 (Mumbai) │ ◄───────────────► │  Bank Node 02 (Delhi)  │              │
       │   │  Active Primary (5000) │                   │  Passive Backup (6000) │              │
       │   └───────────┬────────────┘                   └───────────┬────────────┘              │
       │               │                                            │                           │
       │               │ Cristian & Berkeley Clock Sync (Exp 3)     │ Bully Leader Election     │
       │               │ Ricart-Agrawala Mutex CS (Exp 5)           │ Coordinator = Node 4      │
       │               │                                            │ (Exp 4)                   │
       │               ▼                                            ▼                           │
       │   ┌────────────────────────┐                   ┌────────────────────────┐              │
       │   │ Bank Node 03 (Bangalore│ ◄───────────────► │  Bank Node 04 (Master) │              │
       │   │ Worker Evaluator       │                   │  Bully Consensus Leader│              │
       │   └────────────────────────┘                   └────────────────────────┘              │
       └───────────────────────────────────┬────────────────────────────────────────────────────┘
                                           │
                                           ▼
       ┌────────────────────────────────────────────────────────────────────────────────────────┐
       │                QUORUM REPLICATED TRANSACTION ENGINE (Exp 6)                            │
       │   • Quorum Configuration: N = 3 Replicas, Write Quorum W = 2, Read Quorum R = 2       │
       │   • Invariant: W + R > N (2 + 2 = 4 > 3) -> Strong Consistency & Fresh Reads           │
       │   • Durability: SHA-256 State Hashing across Replicas prevents silent bit rot          │
       └───────────────────────────────────┬────────────────────────────────────────────────────┘
                                           │
                                           ├────────────────────────────────────────┐
                                           ▼                                        ▼
    ┌──────────────────────────────────────────────────┐  ┌──────────────────────────────────────────────────┐
    │     MPI HIGH-PERFORMANCE UNDERWRITING (Exp 9)    │  │       MAPREDUCE PARALLEL ANALYTICS (Exp 8)       │
    │  • MPI_Bcast: Root broadcasts repo rate (6.50%)  │  │  • Input Split: 1,248 mortgages / 4 chunks       │
    │  • MPI_Scatter: Disjoint mortgage batching       │  │  • Map Phase: 4 Parallel Key-Value Mappers       │
    │  • Local Compute: Lock-free parallel EMI & DTI   │  │  • Shuffle & Sort Engine: Regional Key Hash      │
    │  • MPI_Gather: Consolidated sanction register    │  │  • Reduce Phase: Delinquency Risk Provisions     │
    │  • MPI_Reduce: Total Disbursed (MPI_SUM) & Peak  │  │  • Performance: 25.0 ms (3.82x Speedup)          │
    │  • Speedup: 6.8x vs sequential point-to-point    │  │                                                  │
    └──────────────────────────────────────────────────┘  └──────────────────────────────────────────────────┘
```

---

## The 9 Distributed Computing Phases Explained

| Phase | Distributed Paradigm | Real Banking Implementation in BANKCORE | Guarantee / Metric |
| :---: | :--- | :--- | :--- |
| **Exp 1** | **Client-Server RPC** | Multi-client TCP socket server (`Port 5000`) processing structured loan requests (`SUBMIT`, `STATUS`, `DETAILS`). | Sub-millisecond RPC round-trip. |
| **Exp 2** | **Remote Method Invocation (RMI) & Concurrency** | Dedicated `ClientHandler` threads and Java RMI Registry (`Port 1099`) with synchronized critical regions. | Zero race conditions under concurrent client load. |
| **Exp 3** | **Distributed Clock Synchronization** | **Cristian's Algorithm** ($T_{sync} = T_{server} + \frac{RTT}{2}$) and **Berkeley Time Daemon** skew adjustment. | Consistent physical time timestamps across all branches. |
| **Exp 4** | **Distributed Leader Election** | **Bully Algorithm** dynamically electing the highest active Node ID (Node 4) as Master Coordinator. | Automatic failover recovery when coordinator crashes. |
| **Exp 5** | **Distributed Mutual Exclusion & Failover** | **Ricart-Agrawala Tokenless CS Algorithm** & Primary-Backup replication with Heartbeat PING failover. | Mutual exclusion without centralized bottlenecks; 0 data loss. |
| **Exp 6** | **Data Consistency & Replication** | Comparative engine evaluating **Strong ($W=3$)**, **Quorum ($W=2, R=2, N=3$)**, and **Eventual ($W=1$)** consistency. | Stale read detection & SHA-256 state hash convergence. |
| **Exp 7** | **Adaptive Load Balancing & Rate Limiting** | **Weighted Round-Robin (5:3:2)** and **Token-Bucket Traffic Shaper** rejecting excess bursts with `HTTP 429`. | Fair capacity utilization & DDoS prevention. |
| **Exp 8** | **Distributed MapReduce Analytics** | Map $\to$ Shuffle/Sort $\to$ Reduce pipeline evaluating regional mortgage risks and audit log frequencies. | 3.82x speedup over sequential batch aggregation. |
| **Exp 9** | **MPI Collective Communication** | `MPI_Bcast`, `MPI_Scatter`, `MPI_Gather`, `MPI_Reduce`, and `MPI_Allgather` for bulk mortgage underwriting. | $\mathcal{O}(\log_2 N)$ scaling; 6.8x speedup over unicast loops. |

---

## End-to-End Life of a Home Loan Application

When borrower **Tanish Parakh (UID: CUS1001)** submits an application for **Rs. 75,00,000**:

1. **Traffic Admission Control (Exp 7)**:
   - The API Gateway checks the Token Bucket (Capacity: 10, Refill: 2/s).
   - A token is consumed; client throughput is within legal thresholds.
2. **Cluster Dispatch (Exp 7)**:
   - The Load Balancer executes Weighted Round-Robin ($5:3:2$).
   - The request is routed to **Bank Node 01** (Mumbai Central Gateway).
3. **Consensus Validation (Exp 4)**:
   - Node 01 queries Bully Leader **Bank Node 04** to confirm active membership.
4. **Logical & Physical Timestamping (Exp 3)**:
   - Cristian's algorithm calculates server offset ($RTT / 2 = 1.6\text{ ms}$).
   - A monotonic Lamport timestamp $L(e) = 1042$ is assigned.
5. **Distributed Mutex Lock (Exp 5)**:
   - Node 01 broadcasts `REQUEST(1042, Node 1)` to all nodes.
   - Upon receiving $(N-1)$ `OK` responses, Node 01 enters the Critical Section to debit borrower exposure and lock records.
6. **Quorum Replication (Exp 6)**:
   - Node 01 replicates the record to Node 02 and Node 03 using Quorum ($W=2, R=2, N=3$).
   - Replicas 1 and 2 write to disk and return ACK.
   - SHA-256 state hash matches across nodes (`84d74eae5b41...`).
7. **High-Performance Underwriting (Exp 9)**:
   - Root Rank 0 executes `MPI_Bcast` with the updated RBI Repo Rate (6.50%) and spread (2.15%).
   - Root executes `MPI_Scatter` to distribute mortgage batches across Ranks 1..3.
   - Workers calculate EMI (Rs. 65,818/mo) and DTI in parallel without lock contention.
   - Root executes `MPI_Gather` to consolidate approvals and `MPI_Reduce` to calculate aggregate risk liability (Rs. 5.12 Cr).
8. **Parallel Portfolio Profiling (Exp 8)**:
   - The MapReduce engine parses regional loan distribution and certifies compliance in 25 ms.
9. **Client Sanction Delivery (Exp 1 & 2)**:
   - Final approval certificate is returned over TCP socket port 5000 and registered in RMI registry port 1099.

---

## How to Run the Software Suite

### 1. One-Click Master Batch Launcher
Double-click `start_bankcore_suite.bat` in the root folder, or run in terminal:
```powershell
.\start_bankcore_suite.bat
```
This presents a menu to launch:
- Master Java Orchestrator
- Quick End-to-End Simulation
- Unified Web Management Console
- Any Java Swing GUI Dashboard (Exps 5, 6, 7, 8, 9)

### 2. Unified Web Management Console
Run from command prompt:
```powershell
cd frontend
.\start_frontend.bat
```
Visit `http://localhost:3000` in your web browser. You will have access to:
- Interactive **End-to-End Pipeline Simulator**
- Live **Data Consistency & Stale Read Lab**
- Real-time **Token Bucket & Load Balancer Simulator**
- Live **MapReduce Parallel Execution Visualizer**
- High-Performance **MPI Collective Communication Dashboard**
- Standard Core Banking views (Loans, Customers, Transactions, Ledgers)

### 3. Java Swing Desktop Dashboards
Launch any phase natively:
```powershell
# Exp 9: MPI Collective Communication GUI
java -cp Exp9 BankMPICollectiveGUI

# Exp 8: Distributed MapReduce GUI
java -cp Exp8 BankMapReduceGUI

# Exp 7: Load Balancer & Rate Limiter GUI
java -cp Exp7 BankLoadBalancerGUI

# Exp 6: Data Consistency GUI
java -cp Exp6 BankConsistencyGUI

# Exp 5: Mutual Exclusion GUI
java -cp Exp5 BankCoreGUI
```

### 4. Direct Java Orchestrator CLI
```powershell
java MasterBankLauncher --demo
```
Runs the full 9-phase demonstration in under 3 seconds with colored telemetry and sanction certificate generation.
