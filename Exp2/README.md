# Experiment 2 — Multithreading & Concurrency Control in Distributed Systems

## Project Title
**Implementation of Multithreading & Concurrency Control in a Distributed Banking Home Loan Management System**

---

## Experiment Aim
To implement a multithreaded distributed banking server where multiple clients can connect, execute home loan applications, and perform concurrent EMI account transactions simultaneously without race conditions, demonstrating multithreading and thread synchronization in distributed systems.

---

## Case Study & Background Theory

### Scenario
Modern distributed banking systems (such as SBI, HDFC, or ICICI digital portals) process thousands of loan applications and payment transactions concurrently. Each user's request (loan application, EMI payment, balance update) must be handled in an independent execution path (thread) so that no single user blocks the entire system.

### Why Multithreading & Concurrency Control?
- **Without Multithreading**: A single-threaded server processes clients sequentially. If Client 1 is entering details or executing a long calculation, Client 2 must wait indefinitely, causing poor responsiveness.
- **With Multithreading**: For every client connection (`serverSocket.accept()`), the server spawns a dedicated `ClientHandler` thread (`new Thread(handler).start()`).
- **With Concurrency Control**: When multiple threads update shared banking state (e.g. loan IDs, remaining balances, synchronized client writer streams), Java `synchronized` blocks prevent race conditions and data corruption.

---

## Folder Structure

```text
Exp2/
├── MultiThreadedLoanServer.java     # Multithreaded TCP Server with thread synchronization
├── LoanClient.java                  # Multithreaded CLI Client (Main thread + Listener thread)
│
├── screenshots/                     # Real execution output screenshots
│   ├── 01_Server_Started.png
│   ├── 02_Client1_Connected.png
│   ├── 03_Client2_Connected.png
│   ├── 04_Concurrent_Loan_Submission.png
│   ├── 05_Concurrent_Transaction_Payment.png
│   ├── 06_RealTime_Broadcast_Notification.png
│   └── 07_Server_Multithread_Log.png
│
├── README.md                        # Lab manual and experiment guide
├── VERIFICATION_REPORT.md           # Execution verification matrix
└── SCREENSHOT_RECOMMENDATION.md     # Screenshot selection guide for lab file
```

---

## Technical Architecture

### Server Multithreading & Synchronization Model
- **`ServerSocket`**: Listens on TCP port `5000`.
- **`ClientHandler` Thread**: Created per client connection.
- **Thread Synchronization**:
  ```java
  synchronized (MultiThreadedLoanServer.class) {
      // Thread-safe loan ID generation & balance updates
  }
  ```
- **Real-Time Broadcast**: Server notifies all connected clients when a new loan is registered or a transaction payment occurs.

### Client Dual-Thread Architecture
1. **Background Listener Thread**: Dedicated thread reading real-time server messages and broadcasts asynchronously (`in.readLine()`).
2. **Main Thread**: Manages interactive user menu and sends request strings to server (`out.println()`).

---

## Compilation & Run Commands

### 1. Compile
In the `Exp2/` directory:
```bash
javac MultiThreadedLoanServer.java LoanClient.java
```

### 2. Start Server (Terminal 1)
```bash
java MultiThreadedLoanServer
```
*Output:*
```text
==================================================
 Multithreaded Home Loan Server Started on Port 5000
 Waiting for client connections...
==================================================
```

### 3. Start Client 1 (Terminal 2)
```bash
java LoanClient
```

### 4. Start Client 2 (Terminal 3)
```bash
java LoanClient
```

---

## Test Scenarios

| Test Case | Inputs | Expected Multithreaded Behavior |
| :--- | :--- | :--- |
| **Concurrent Submission** | Client 1 submits `C101` (₹50,00,000), Client 2 submits `C102` (₹35,00,000) | Server assigns distinct IDs (`HL1001`, `HL1002`) concurrently without race condition |
| **Concurrent EMI Payment** | Client 1 pays ₹5,00,000 EMI for `HL1001` | Remaining balance updated thread-safely from ₹50,00,000 to ₹45,00,000 |
| **Async Broadcast** | Client 1 submits loan | Client 2 receives real-time broadcast notification on listener thread |
