# Experiment 7: Load Balancing & Concurrency Control
## Distributed Banking Home Loan Management System

### Laboratory Information
- **Institution:** Sardar Patel Institute of Technology (SPIT), Munshi Nagar, Andheri West, Mumbai
- **Course:** Distributed Computing Laboratory (D.C. Lab)
- **Experiment No.:** 7
- **Topic:** Load Balancing & Concurrency Throttling in Distributed Systems
- **Student Details:**
  - Atharv Patil (UID: 2024300165)
  - Tanish Parakh (UID: 2024300157)
  - Gitesh Patil (UID: 2024300166)
- **Branch / Division / Batch:** Computer Engineering | Division C | Batch C1
- **Date:** 29/09/2026

---

### 1. Aim
To implement and evaluate an Adaptive Load Balancing and Concurrency Throttling Architecture for the Distributed Home Loan Banking System, comparing Round Robin, Weighted Round Robin, and Least Connections scheduling algorithms, and analyzing system behavior under severe traffic stress where concurrency limits (HTTP 503 Capacity Saturation & Load Shedding) and client burst thresholds (HTTP 429 Token Bucket Rate Limiting) are reached, alongside failover resiliency during node crash failures.

---

### 2. Multi-Server Banking Cluster Architecture
The system deploys a high-throughput load balancer proxy gateway and 3 backend banking nodes:
1. **Load Balancer Proxy Gateway (Port 8000)**: Central reverse proxy with Token Bucket Rate Limiting ($B_{\max} = 5, r = 2.0\text{ req/s}$), bounded wait buffer ($Q_{\max} = 5$), multi-algorithm router (RR, WRR, LC, IP Hash), and active health check monitor.
2. **Server 1 (`BankServer-01`, Port 8001)**: High-performance core banking node (Weight 3, Max Concurrency = 4).
3. **Server 2 (`BankServer-02`, Port 8002)**: Mid-tier regional server (Weight 2, Max Concurrency = 4).
4. **Server 3 (`BankServer-03`, Port 8003)**: Edge branch server & standby node (Weight 1, Max Concurrency = 4).

**Total Cluster Concurrency Limit**: 12 active requests (4 per node) + 5 queue buffer slots = 17 max in-flight requests.

---

### 3. Proper Input Limit Demonstrations & Empirical Results

| Limit Demonstration Scenario | Specific Inputs | Observed System Behavior | Response Code & Action |
| :--- | :--- | :--- | :---: |
| **Client Rate Limiting** | Single automated client (`192.168.1.105`) sends burst of 10 requests within 100ms. | Token bucket depleted from 5.0 to 0.0. Requests #1 to #5 pass; requests #6 to #10 hit limit. | **HTTP 429 Too Many Requests** (Client Throttled) |
| **Cluster Capacity Saturation** | Festive surge of 22 concurrent loan applications simultaneously. | All 3 nodes hit 4/4 concurrency limit (12/12 active). Queue fills to 5/5. Subsequent 10 requests hit limit. | **HTTP 503 Service Unavailable** (Load Shedding Triggered) |
| **Heterogeneous Load Rebalancing** | Heavy commercial appraisals (HL-9001, 1800ms) lock Node 1 and Node 2. | Least Connections detects active load and dynamically routes all 6 subsequent retail loans to idle Node 3. | **HTTP 200 OK** (Zero Head-of-Line Blocking) |
| **Server Crash Failover** | Simulated hardware crash on BankServer-03. | Heartbeat timeout detected in 100ms; cluster capacity drops from 12 to 8; traffic rerouted to surviving nodes. | **HTTP 200 OK** (100% Uptime Maintained) |

---

### 4. Generated Deliverables
- **Document (Word):** [`DC_Lab_Exp_7_LoadBalancing.docx`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp7/DC_Lab_Exp_7_LoadBalancing.docx) *(1.46 MB)*
- **Document (PDF):** [`DC_Lab_Exp_7_LoadBalancing.pdf`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp7/DC_Lab_Exp_7_LoadBalancing.pdf) *(1.66 MB, 19 pages)*
- **Backend Java Code:** [`DistributedLoanLoadBalancerSystem.java`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp7/DistributedLoanLoadBalancerSystem.java) (**Strictly backend code only in the report**)
- **Architecture Diagram:** [`architecture_diagram.png`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp7/architecture_diagram.png)
- **Frontend Engine (Java Swing):** [`BankLoadBalancerGUI.java`](file:///c:/Users/Tanish/OneDrive/Desktop/DC%20Home%20Loan/Exp7/BankLoadBalancerGUI.java)
- **11 Output Figures Included in the Report:**
  - **Figure 1 (UI):** `01_Real_Java_UI_LoadBalancer_Dashboard.png` -- Real-time Server Telemetry, Capacity Gauges, and Replicated Loan Application Ledger
  - **Figure 2 (UI):** `02_Real_Java_UI_Rate_Limit_Exceeded.png` -- Client 192.168.1.105 High-Frequency Burst, Token Exhaustion, and HTTP 429 Limit Reached Alert
  - **Figure 3 (UI):** `03_Real_Java_UI_Server_Capacity_Saturated.png` -- Festive Surge (22 Concurrent Applications), 100% Concurrency Limit Reached (12/12 active, 5/5 queue), and HTTP 503 Load Shedding
  - **Figure 4 (UI):** `04_Real_Java_UI_Least_Connections_Rebalance.png` -- Dynamic Offloading during Heavy Commercial Loan Appraisal (HL-9001, 1800ms) with Traffic Shift to Node 3
  - **Figure 5 (Console):** `01_Round_Robin_Execution.png` -- Round Robin Sequential Execution across Ports 8001, 8002, 8003 (~202 ms)
  - **Figure 6 (Console):** `02_Weighted_Round_Robin_Execution.png` -- Weighted Round Robin Proportional Allocation (3:2:1 Ratio matching Server Power)
  - **Figure 7 (Console):** `03_Least_Connections_Dynamic_Execution.png` -- Least Connections Dynamic Load Shifting during Heterogeneous Commercial Processing
  - **Figure 8 (Console):** `04_Rate_Limit_Reached_429_Execution.png` -- PROPER INPUT LIMIT DEMONSTRATION: Token Bucket Rate Limit Reached (HTTP 429)
  - **Figure 9 (Console):** `05_Cluster_Capacity_Saturation_503_Execution.png` -- PROPER INPUT LIMIT DEMONSTRATION: Cluster Concurrency Saturation & Load Shedding (HTTP 503)
  - **Figure 10 (Console):** `06_Server_Failover_Health_Check_Execution.png` -- Server Crash Failover & Reduced Capacity Limit Stress Execution
  - **Figure 11 (Console):** `07_Benchmark_Performance_Matrix.png` -- Full Benchmark Summary Results & Algorithm Comparison Matrix
