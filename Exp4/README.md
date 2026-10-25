# Experiment 4 — Election Algorithm in Distributed Systems (The Bully Algorithm)

## Project Title
**Implementation of Leader / Coordinator Election in a Distributed Banking System using The Bully Algorithm**

---

## Experiment Aim
To design, implement, and evaluate **The Bully Algorithm** for leader/coordinator election in a distributed banking system, demonstrating how active processes dynamically elect the highest priority node as the Central Master Coordinator upon node failure or recovery.

---

## Case Study & Background Theory

### Scenario
In a distributed banking environment (e.g. SBI or HDFC regional cluster with branch servers in Mumbai, Delhi, Bangalore, and Central Master Server), a single **Coordinator Node** coordinates critical operations like loan approval transactions, account ledger updates, and synchronized loan ID allocation.
- **Problem**: If the active Coordinator Node (e.g. `BankNode-4`) crashes or suffers a hardware outage, the distributed banking cluster cannot process centralized operations.
- **Solution (The Bully Algorithm - Garcia-Molina)**:
  1. When any node $P_i$ notices that the Coordinator is non-responsive, it initiates an **Election** by sending an `ELECTION` message to all nodes with **higher Process IDs** ($P_{i+1}, \dots, P_N$).
  2. If no higher process responds within a set timeout, $P_i$ wins the election and broadcasts a `COORDINATOR` announcement to all lower active processes ("I am the new master!").
  3. If a higher node responds with `OK` / `ANSWER`, $P_i$ steps down and lets the higher node complete the election.
  4. **Node Recovery ("Bully" behavior)**: If a crashed node with a higher ID (e.g. `BankNode-4`) comes back online, it immediately initiates an election and reclaims the coordinator role from lower nodes.

### Message Complexity Analysis
- **Best-case message complexity**: $O(N)$ (when the highest ID process detects coordinator failure).
- **Worst-case message complexity**: $O(N^2)$ (when the lowest ID process detects failure and $N-1$ processes participate).

---

## Folder Structure

```text
Exp4/
├── BullyElectionBanking.java        # The Bully Algorithm Java implementation
│
├── screenshots/                     # Real desktop header execution screenshots
│   ├── 01_Initial_Cluster_State.png
│   ├── 02_Coordinator_Crash_Event.png
│   ├── 03_Election_Initiation_P3_Wins.png
│   └── 04_Higher_Node_Recovery_Bully.png
│
├── README.md                        # Lab manual & theory guide
├── VERIFICATION_REPORT.md           # Execution verification matrix
├── SCREENSHOT_RECOMMENDATION.md     # Screenshot submission guide
├── DC_Lab_Exp_4_ElectionAlgo.docx   # Word report in SPIT template
└── DC_Lab_Exp_4_ElectionAlgo.pdf    # PDF report in SPIT template
```

---

## Compilation & Run Commands

```bash
javac BullyElectionBanking.java
java BullyElectionBanking
```
