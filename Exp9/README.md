# Experiment 9: MPI Collective Communication
## Distributed Banking Home Loan Management System

### Laboratory Information
- **Institution:** Sardar Patel Institute of Technology (SPIT), Munshi Nagar, Andheri West, Mumbai
- **Course:** Distributed Computing Laboratory (D.C. Lab)
- **Experiment No.:** 9
- **Topic:** Collective Communication Operations (Broadcast, Scatter, Gather, Reduce) using Message Passing Interface (MPI)
- **Student Details:**
  - Atharv Patil (UID: 2024300165)
  - Tanish Parakh (UID: 2024300157)
  - Gitesh Patil (UID: 2024300166)
- **Branch / Division / Batch:** Computer Engineering | Division C | Batch C1
- **Date:** 12/10/2026

---

### 1. Aim
To implement and demonstrate MPI collective communication operations — broadcast, scatter, gather, and reduce — for efficient, synchronized, and low-latency data sharing among distributed computing processes in the Distributed Home Loan Banking System, comparing point-to-point unicast messaging against tree-structured collective algorithms for high-performance mortgage underwriting, parallel EMI calculations, and portfolio risk reduction.

---

### 2. MPI Process Communicator Topology
The system models a high-performance computing banking cluster using `MPI_COMM_WORLD` across 4 processes:
- **Rank 0 (Headquarters Master Node, Mumbai Core)**: Root coordinator holding global underwriting policies and master loan application batches.
- **Rank 1 (Regional Branch Node, Pune IT Corridor)**: Parallel worker evaluating assigned mortgage applications.
- **Rank 2 (Regional Branch Node, Bangalore Tech Corridor)**: Parallel worker evaluating assigned mortgage applications.
- **Rank 3 (Regional Branch Node, Delhi NCR Hub)**: Parallel worker evaluating assigned mortgage applications.

---

### 3. Collective Communication Operations Implemented

| MPI Operation | Direction | Banking Functional Role | Algorithmic Complexity | Observed Latency (4 Ranks) |
| :--- | :--- | :--- | :--- | :---: |
| **`MPI_Bcast`** | Root $\to$ All | Broadcasts global Repo Rate (6.50%), Spread (2.15%), Effective ROI (8.65%), and Min CIBIL (700) | Binomial Spanning Tree $\mathcal{O}(\log_2 N)$ | **3.2 ms** (vs 14.8 ms P2P) |
| **`MPI_Scatter`** | Root $\to$ Disjoint Parts | Equipartitions 8 mortgage applications (Rs 5.12 Cr) into 4 chunks (2 loans/rank) | Hypercube Scatter $\mathcal{O}(\log_2 N)$ | **7.1 ms** (vs 28.4 ms P2P) |
| **Local Compute** | Internal | Evaluates monthly EMI amortization formula $E = P \cdot r \cdot \frac{(1+r)^n}{(1+r)^n - 1}$ and DTI ratio | Parallel CPU cores | **12 ms** (Zero Lock Contention) |
| **`MPI_Gather`** | All $\to$ Root | Aggregates local underwriting decisions and EMI schedules into master approval ledger | Inverted Hypercube $\mathcal{O}(\log_2 N)$ | **2.8 ms** (Zero Race Conditions) |
| **`MPI_Reduce`** | All $\to$ Root | Computes total disbursed loan capital (`MPI_SUM` = Rs 5.12 Cr) and peak loan (`MPI_MAX` = Rs 1.85 Cr) | Tree Reduction $\mathcal{O}(\log_2 N)$ | **2.1 ms** (Scalar Reduction) |
| **`MPI_Allgather`**| All $\to$ All | Synchronizes active worker counts, liquidity reserve buffers, and operational state across every rank | Ring / Recursive Doubling | **3.8 ms** (100% Cluster Mesh Sync) |

---

### 4. Empirical Benchmark Performance Matrix: Point-to-Point vs. Collective

| Process Scale | P2P Broadcast (ms) | MPI_Bcast Tree (ms) | P2P Scatter/Gather (ms) | MPI_Scatter/Gather (ms) | Speedup Ratio |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **4 Processes** | 14.8 ms | 3.2 ms | 28.4 ms | 7.1 ms | **4.0x** |
| **8 Processes** | 32.1 ms | 5.8 ms | 64.2 ms | 12.4 ms | **5.2x** |
| **16 Processes** | 74.5 ms | 9.4 ms | 148.6 ms | 23.8 ms | **6.2x** |
| **32 Processes** | 168.2 ms | 14.1 ms | 326.0 ms | 47.9 ms | **6.8x** |

---

### 5. Deliverables & Artifacts
- **Official SPIT Document (Word):** [`DC_Lab_Exp_9_MPICollective.docx`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp9/DC_Lab_Exp_9_MPICollective.docx) *(1.23 MB)*
- **Official SPIT Document (PDF):** [`DC_Lab_Exp_9_MPICollective.pdf`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp9/DC_Lab_Exp_9_MPICollective.pdf) *(1.44 MB, 16 pages)*
- **Backend Java Code:** [`DistributedLoanMPICollectiveSystem.java`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp9/DistributedLoanMPICollectiveSystem.java) (**Strictly backend code only in the report**)
- **Python MPI Script:** [`mpi_loan_collective.py`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp9/mpi_loan_collective.py)
- **Architecture Diagram:** [`architecture_diagram.png`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp9/architecture_diagram.png) *(300 DPI high-resolution)*
- **Frontend Engine (Java Swing):** [`BankMPICollectiveGUI.java`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp9/BankMPICollectiveGUI.java) (Authentic clean BANKCORE design matching reference)
- **11 Output Figures Included in the Report:**
  - **Figure 1 (UI):** `01_Real_Java_UI_MPI_Cluster_Dashboard.png` -- Process Rank Topology (Ranks 0..3), Communicator State, and Staged Workload Queue
  - **Figure 2 (UI):** `02_Real_Java_UI_Policy_Broadcast.png` -- Root Node (Rank 0) MPI_Bcast Binomial Tree Distribution of Repo Rate (6.50%) and Effective ROI (8.65%)
  - **Figure 3 (UI):** `03_Real_Java_UI_Loan_Scatter.png` -- MPI_Scatter Workload Partitioning Distributing 8 Loan Applications into Equipartitioned Batches
  - **Figure 4 (UI):** `04_Real_Java_UI_Decision_Gather_Reduce.png` -- Consolidated Sanction Register (MPI_Gather) & Risk Reduction (MPI_Reduce)
  - **Figure 5 (Console):** `01_MPI_Cluster_Init.png` -- MPI Communicator Cluster Initialization & Process Topology Allocation
  - **Figure 6 (Console):** `02_MPI_Bcast_Execution.png` -- MPI_Bcast Underwriting Policy Broadcast Execution (3.2 ms)
  - **Figure 7 (Console):** `03_MPI_Scatter_Execution.png` -- MPI_Scatter Loan Application Workload Partitioning Execution (7.1 ms)
  - **Figure 8 (Console):** `04_MPI_Gather_Execution.png` -- Parallel Local Computation & MPI_Gather Decision Collection Execution
  - **Figure 9 (Console):** `05_MPI_Reduce_Execution.png` -- MPI_Reduce Portfolio Aggregation Execution (SUM Rs 5.12 Cr, MAX Rs 1.85 Cr)
  - **Figure 10 (Console):** `06_MPI_Allgather_Execution.png` -- Python mpi4py Collective Pipeline & MPI_Allgather State Synchronization
  - **Figure 11 (Console):** `07_P2P_vs_Collective_Benchmark.png` -- Benchmark Summary Results: Point-to-Point vs Collective Communication (4.0x to 6.8x Speedup)
