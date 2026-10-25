# Experiment 6: Data Consistency & Multi-Node Replication
## Distributed Banking Home Loan Management System

### Laboratory Information
- **Institution:** Sardar Patel Institute of Technology (SPIT), Munshi Nagar, Andheri West, Mumbai
- **Course:** Distributed Computing Laboratory (D.C. Lab)
- **Experiment No.:** 6
- **Topic:** Data Consistency & Replication in Distributed Systems
- **Student Details:**
  - Atharv Patil (UID: 2024300165)
  - Tanish Parakh (UID: 2024300157)
  - Gitesh Patil (UID: 2024300166)
- **Branch / Division / Batch:** Computer Engineering | Division C | Batch C1
- **Date:** 28/09/2026

---

### 1. Aim
To study how different replication strategies affect data consistency in the Distributed Home Loan Banking System, comparing Strong Consistency, Quorum Consistency, and Eventual Consistency, and analyzing write latency, stale reads, replica synchronization/convergence, ordering of updates with sequence buffering, behavior when replicas are delayed, and data durability during primary crash failures.

---

### 2. Multi-Node Cluster Architecture ($N = 3$)
The benchmark implements a distributed 3-node cluster running under OpenJDK 21:
1. **Node 1 (`BankNode-01`)**: Primary Coordinator listening on port `7001` with local execution latency ($0\text{ ms}$).
2. **Node 2 (`BankNode-02`)**: Fast Replica listening on port `7002` with simulated link transmission latency ($1000\text{ ms}$).
3. **Node 3 (`BankNode-03`)**: High-Latency WAN Replica listening on port `7003` with simulated WAN link latency ($2400\text{ ms}$).

---

### 3. Empirical Results & Benchmark Summary

| Replication Model | Write Quorum ($W$) | Read Quorum ($R$) | Write Latency | Stale Reads | Convergence Time | Durability under Primary Crash |
| :--- | :---: | :---: | :---: | :---: | :---: | :--- |
| **Strong Consistency** | $W = 3$ | $R = 1$ | $2496\text{ ms}$ | **0% (Zero)** | Immediate | **Zero Data Loss** (All nodes mirrored) |
| **Quorum Consistency** | $W = 2$ | $R = 2$ | $1012\text{ ms}$ | **0%** ($R+W > N$) | Fast ($1000\text{ ms}$) | **Zero Data Loss** (Surviving quorum has latest) |
| **Eventual Consistency**| $W = 1$ | $R = 1$ | **$5\text{ ms}$** | **Observed** (Lagging node) | Eventual ($3000\text{ ms}$) | **Data Loss** (Unpropagated writes lost) |

---

### 4. Generated Deliverables
- **Document (Word):** [`DC_Lab_Exp_6_DataConsistency.docx`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp6/DC_Lab_Exp_6_DataConsistency.docx)
- **Document (PDF):** [`DC_Lab_Exp_6_DataConsistency.pdf`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp6/DC_Lab_Exp_6_DataConsistency.pdf)
- **Backend Java Code:** [`DistributedLoanConsistencySystem.java`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp6/DistributedLoanConsistencySystem.java) (**Strictly backend code only in the report**)
- **Architecture Diagram:** [`architecture_diagram.png`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp6/architecture_diagram.png)
- **Frontend Engine (Java Swing):** [`BankConsistencyGUI.java`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp6/BankConsistencyGUI.java)
- **11 Output Figures Included in the Report:**
  - **Figure 1 (UI):** `01_Real_Java_UI_Consistency_Dashboard.png` -- Real-time Node Telemetry & Synchronized Loan Ledger
  - **Figure 2 (UI):** `02_Real_Java_UI_Strong_Consistency_Submit.png` -- Synchronous Loan Submission for HL1001 (W=3, 2496 ms)
  - **Figure 3 (UI):** `03_Real_Java_UI_Eventual_Stale_Read.png` -- Fast Write (5 ms), Stale Read on Node 3, & Queue Convergence
  - **Figure 4 (UI):** `04_Real_Java_UI_Quorum_Resolution.png` -- Quorum EMI Payment (1012 ms) & R=2 Quorum Overlap Resolution to v3
  - **Figure 5 (Console):** `01_Strong_Consistency_W3.png` -- Strong Consistency Execution & SHA-256 Hash 7A8C23BE8F24
  - **Figure 6 (Console):** `02_Eventual_Consistency_Stale_Read.png` -- Stale Read Detection during in-flight replication
  - **Figure 7 (Console):** `03_Eventual_Consistency_Convergence.png` -- Queue Drained after 3000ms & Hash 719A65EE7038
  - **Figure 8 (Console):** `04_Quorum_Consistency_W2_R2.png` -- Quorum Read Overlap Resolution ($R+W=4>3$)
  - **Figure 9 (Console):** `05_Update_Ordering_Sequence_Buffering.png` -- Out-of-order Packet Buffering & Sequential Drain
  - **Figure 10 (Console):** `06_Primary_Crash_Durability_Loss.png` -- Primary Crash & Durability Loss of HL1004/HL1005
  - **Figure 11 (Console):** `07_Replication_Model_Benchmark_Results.png` -- Full Benchmark Summary Results
