# Experiment 8: MapReduce Job in Distributed Systems
## Distributed Banking Home Loan Management System

### Laboratory Information
- **Institution:** Sardar Patel Institute of Technology (SPIT), Munshi Nagar, Andheri West, Mumbai
- **Course:** Distributed Computing Laboratory (D.C. Lab)
- **Experiment No.:** 8
- **Topic:** MapReduce Distributed Parallel Data Processing & Large-Scale Analytics
- **Student Details:**
  - Atharv Patil (UID: 2024300165)
  - Tanish Parakh (UID: 2024300157)
  - Gitesh Patil (UID: 2024300166)
- **Branch / Division / Batch:** Computer Engineering | Division C | Batch C1
- **Date:** 12/10/2026

---

### 1. Aim
To implement and execute a distributed MapReduce parallel computation architecture using Java multi-threading (Hadoop paradigm) and Apache Spark Resilient Distributed Datasets (RDDs) for the Distributed Home Loan Banking System, evaluating input data splitting, concurrent mapper task execution, hash partitioning, shuffle-and-sort mechanics, and distributed reduction across banking underwriting audit logs, loan portfolio product aggregations, regional branch exposure calculations, and stopword-filtered financial risk token analysis.

---

### 2. Multi-Stage Distributed MapReduce Architecture
The system models an enterprise big data analytics pipeline for loan origination:
1. **HDFS Ingestion & Input Splitting**: Ingests raw banking underwriting and loan application streams. Datasets are partitioned into 64 MB logical input splits distributed across virtual DataNodes (`DataNode-01`, `DataNode-02`, `DataNode-03`) adhering to the data locality principle.
2. **Concurrent Mapper Workers (M1..M3)**: Independent worker threads execute map functions in parallel, extracting structured fields and emitting intermediate `(Key, Value)` pairs without lock contention.
3. **In-Memory Combiner (Mini-Reducer)**: Pre-aggregates intermediate outputs locally on mapper nodes before network transfer, reducing shuffle bandwidth by over 47%.
4. **Hash Partitioner & Shuffle-Sort**: Uses `(hash(Key) & Integer.MAX_VALUE) % numReducers` to route identical keys to the same Reducer worker. The framework sorts keys to provide grouped `(Key, Iterable<Value>)` streams.
5. **Distributed Reducers (R1, R2)**: Concurrent workers compute aggregations and emit formal HDFS partitions (`part-r-00000`, `part-r-00001`).

---

### 3. Core MapReduce Jobs Implemented

| MapReduce Job | Input Domain | Map Transformation | Reduce Aggregation | Output Result |
| :--- | :--- | :--- | :--- | :--- |
| **Job 1: Banking Audit Log WordCount** | Raw mortgage underwriting event logs | Emits `(keyword, 1)` per log token | Computes $\sum \text{occurrences}$ per event | Top events: `APPROVED` (8), `HOME_LOAN` (6), `INCOME_STABLE` (5), `REJECTED` (3) |
| **Job 2: Portfolio Aggregation by Product** | Multi-million loan applications | Emits `(ProductType, LoanStats)` | Computes Count, Total Volume ($\sum$), Average Loan Amount | Luxury Villa: Rs 4.05 Cr (Avg Rs 2.02 Cr); Standard Home Loan: Rs 2.87 Cr (Avg Rs 71.75 L) |
| **Job 3: Regional Branch Exposure & Peak Loan** | City-wise applicant data | Emits `(CityName, RegionalStats)` | Computes Total City Exposure & $\max(\text{LoanAmount})$ | Bangalore Peak: Rs 2.20 Cr; Mumbai Exposure: Rs 3.42 Cr |
| **Job 4: Stopword-Filtered Risk Tokens** | Free-form underwriting notes | Filters English stopwords (`the`, `is`, `and`, etc.), emits `(RiskToken, 1)` | Computes frequency of high-impact credit signals | `HIGH` (2), `CIBIL` (2), `AFFORDABLE` (1), `EXCELLENT` (1) |
| **Job 5: Apache Spark RDD MapReduce** | Underwriting logs | `textFile -> flatMap -> map -> reduceByKey` | In-memory DAG execution | Zero disk spill, 40 ms runtime |

---

### 4. Empirical Benchmark Performance Matrix

| Framework / Paradigm | 10K Records | 100K Records | 1M Records | Fault Tolerance Policy |
| :--- | :---: | :---: | :---: | :---: |
| **Sequential (1 CPU)** | 142 ms | 1,240 ms | 14,800 ms | None (Single node crash aborts job) |
| **Hadoop MapReduce (Java)** | 45 ms | 310 ms | 2,840 ms | Disk Spills & Task Re-execution on Backup Node |
| **Apache Spark (PySpark)** | 18 ms | 85 ms | 720 ms | In-Memory RDD Lineage Graph Recomputation |

---

### 5. Deliverables & Artifacts
- **Official SPIT Document (Word):** [`DC_Lab_Exp_8_MapReduce.docx`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp8/DC_Lab_Exp_8_MapReduce.docx) *(1.32 MB)*
- **Official SPIT Document (PDF):** [`DC_Lab_Exp_8_MapReduce.pdf`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp8/DC_Lab_Exp_8_MapReduce.pdf) *(1.53 MB, 17 pages)*
- **Backend Java Code:** [`DistributedLoanMapReduceSystem.java`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp8/DistributedLoanMapReduceSystem.java) (**Strictly backend code only in the report**)
- **Architecture Diagram:** [`architecture_diagram.png`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp8/architecture_diagram.png) *(300 DPI high-resolution)*
- **Frontend Engine (Java Swing):** [`BankMapReduceGUI.java`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp8/BankMapReduceGUI.java)
- **11 Output Figures Included in the Report:**
  - **Figure 1 (UI):** `01_Real_Java_UI_MapReduce_Dashboard.png` -- Real-time Cluster State, HDFS DataNode Topology (3 DataNodes), Input Split Controls, and Multi-Job Selector
  - **Figure 2 (UI):** `02_Real_Java_UI_Audit_WordCount_Job.png` -- Banking Audit Log WordCount Interface with Multi-Threaded Mappers and HDFS Output
  - **Figure 3 (UI):** `03_Real_Java_UI_Portfolio_Aggregation_Job.png` -- Home Loan Portfolio Aggregation by Product Type (Count, Volume, Avg Applied Loan)
  - **Figure 4 (UI):** `04_Real_Java_UI_Regional_Max_Exposure_Job.png` -- Regional Branch Exposure and Peak Disbursed Loan MapReduce Analytics
  - **Figure 5 (Console):** `01_Cluster_Init_MapReduce.png` -- MapReduce Engine Cluster Initialization & HDFS 64MB Block Allocation
  - **Figure 6 (Console):** `02_Job1_AuditLog_WordCount.png` -- Job 1 Banking Audit Log WordCount Execution (3 Mappers, 2 Reducers, `part-r-*` output)
  - **Figure 7 (Console):** `03_Job2_LoanPortfolio_Aggregation.png` -- Job 2 Loan Portfolio Aggregation by Product Type Execution
  - **Figure 8 (Console):** `04_Job3_Regional_Max_Exposure.png` -- Job 3 Regional Branch Exposure & Peak Disbursed Loan Execution
  - **Figure 9 (Console):** `05_Job4_Stopword_Filtered_Analysis.png` -- Job 4 Stopword-Filtered Underwriting Risk Token Analysis Execution
  - **Figure 10 (Console):** `06_Spark_Python_Execution.png` -- Apache Spark (PySpark RDD) In-Memory MapReduce Pipeline Execution
  - **Figure 11 (Console):** `07_Benchmark_Performance_Matrix.png` -- Full Empirical Benchmark Summary Results & Framework Comparison Matrix
