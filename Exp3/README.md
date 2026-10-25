# Experiment 3 — Clock Synchronization (Logical & Physical) in Distributed Systems

## Project Title
**Implementation of Clock Synchronization (Lamport's Logical Clocks & Cristian's Physical Clock Algorithm) in a Distributed Banking Home Loan System**

---

## Experiment Aim
To design, implement, and analyze clock synchronization algorithms in a distributed banking system, demonstrating **Lamport's Logical Clocks** for causal event ordering and **Cristian's Physical Clock Algorithm** for physical clock drift synchronization with Round Trip Time (RTT) delay compensation.

---

## Case Study & Background Theory

### Scenario
In a distributed banking environment (such as SBI or HDFC regional servers in Mumbai, Delhi, and Bangalore), transactions must occur in a globally consistent order.
- **Problem**: Physical clocks on different computers drift due to hardware oscillator variance (frequency drift). If a customer applies for a loan in Mumbai at 10:00:00.005 and Delhi queries it at 10:00:00.001 (due to clock skew), out-of-order execution or double-spending could occur.
- **Solution 1 (Logical Clocks — Lamport's Algorithm)**: Assigns monotonically increasing integer timestamps $L(e)$ to establish a partial or total causal ordering of events without requiring physical clock agreement:
  $$L(e) = \max(L_{\text{local}}, L_{\text{msg}}) + 1$$
- **Solution 2 (Physical Clocks — Cristian's Algorithm)**: A client process queries a central time server ($T_{\text{server}}$), measures the Round Trip Time ($RTT = T_1 - T_0$), and compensates for network propagation delay:
  $$T_{\text{sync}} = T_{\text{server}} + \frac{RTT}{2}$$

---

## Folder Structure

```text
Exp3/
├── LamportClockBanking.java         # Lamport's Logical Clock implementation
├── CristianClockServer.java         # Cristian's Physical Time Server (Port 6000)
├── CristianClockClient.java         # Cristian's Physical Time Client with RTT compensation
│
├── screenshots/                     # Real execution output screenshots
│   ├── 01_Lamport_Logical_Clock_Execution.png
│   ├── 02_Cristian_Server_Started.png
│   ├── 03_Cristian_Client_Clock_Sync.png
│   └── 04_Distributed_Event_Ordering_Log.png
│
├── README.md                        # Complete lab manual & theory guide
├── VERIFICATION_REPORT.md           # Execution verification matrix
├── SCREENSHOT_RECOMMENDATION.md     # Screenshot submission guide
├── DC_Lab_Exp_3_ClockSync.docx      # Word report in SPIT template
└── DC_Lab_Exp_3_ClockSync.pdf       # PDF report in SPIT template
```

---

## Compilation & Run Commands

### 1. Compile
In `Exp3/` directory:
```bash
javac *.java
```

### 2. Run Lamport's Logical Clock Simulation
```bash
java LamportClockBanking
```

### 3. Run Cristian's Physical Clock Server & Client
- **Terminal 1 (Server)**:
  ```bash
  java CristianClockServer
  ```
- **Terminal 2 (Client)**:
  ```bash
  java CristianClockClient
  ```
