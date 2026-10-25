# Screenshot Recommendation Guide — Experiment 1 (DC Lab)

This guide provides a structured breakdown of the captured screenshots in `screenshots/` and recommends the optimal selection for inclusion in your college practical submission file.

---

## 1. Complete Captured Screenshots List

### Part A — Socket RPC Screenshots
- **`01_RPC_Server.png`**: Socket server initialization on port 5000 (`Home Loan Server Started...`).
- **`02_RPC_Client1.png`**: Client 1 connection and interactive menu.
- **`03_RPC_Client2.png`**: Client 2 simultaneous connection.
- **`04_RPC_Submit1.png`**: Client 1 submitting loan application (Generates ID `HL1001`).
- **`05_RPC_Submit2.png`**: Client 2 submitting loan application (Generates ID `HL1002`).
- **`06_RPC_Status.png`**: Querying status for loan `HL1001` (`UNDER_REVIEW`).
- **`07_RPC_Details.png`**: Retrieving full loan details for `HL1001`.
- **`08_RPC_MultipleClients.png`**: Server-side output showing multiple connected clients and requests.

### Part B — Java RMI Screenshots
- **`09_RMI_Server.png`**: RMI Registry creation on port 1099 and `LoanService` rebind.
- **`10_RMI_Client.png`**: RMI Client startup after `Naming.lookup()` lookup.
- **`11_RMI_Submit.png`**: Remote `submitLoan()` method execution output.
- **`12_RMI_Status.png`**: Remote `getLoanStatus()` method execution output.
- **`13_RMI_Details.png`**: Remote `getLoanDetails()` method execution output.
- **`14_RMI_MultipleClients.png`**: Multiple RMI clients executing remote method calls concurrently.

---

## 2. Selection Recommendations for Lab Practical File

### A. Minimal Recommended Set (4 Screenshots)
*Use this set if your lab manual requires a compact submission with limited page count:*

1. **`04_RPC_Submit1.png`** (Demonstrates Part A Socket Client application & server response).
2. **`08_RPC_MultipleClients.png`** (Demonstrates multithreaded socket server handling multiple clients).
3. **`09_RMI_Server.png`** (Demonstrates Part B RMI Server starting registry and binding service).
4. **`11_RMI_Submit.png`** (Demonstrates Part B RMI Client invoking remote `submitLoan()` method).

---

### B. Standard Recommended Set (6 Screenshots — Highly Recommended)
*Ideal balance for a comprehensive submission:*

1. **`01_RPC_Server.png`** — Socket Server started.
2. **`04_RPC_Submit1.png`** — Socket Client submitting loan application.
3. **`07_RPC_Details.png`** — Socket Client viewing loan details.
4. **`09_RMI_Server.png`** — RMI Registry and `LoanService` started.
5. **`11_RMI_Submit.png`** — RMI Client submitting remote loan application.
6. **`14_RMI_MultipleClients.png`** — Multiple RMI clients connected to shared Remote Object.

---

### C. Best Screenshots by Category

- **Best Single RPC Screenshot**: `04_RPC_Submit1.png`
- **Best Single RMI Screenshot**: `11_RMI_Submit.png`
- **Best Multi-Client Evidence**: `08_RPC_MultipleClients.png` (Socket) & `14_RMI_MultipleClients.png` (RMI)
