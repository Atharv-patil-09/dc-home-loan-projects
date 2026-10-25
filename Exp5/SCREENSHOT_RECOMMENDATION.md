# Screenshot Recommendation Guide — Experiment 5 (DC Lab)

This guide lists the captured execution output screenshots in `Exp5/screenshots/` and recommends the selection for your college lab report.

---

## 1. List of Captured Screenshots

1. **`01_Primary_Backup_Startup.png`**: Primary and Backup nodes starting up with heartbeat monitoring (`PING` signals).
2. **`02_Client_Submit_Replicated.png`**: Client `SUBMIT` request processed by Primary and replicated to Backup.
3. **`03_Primary_Crash_Failover_Promotion.png`**: Primary crash simulated (`Ctrl+C`), Backup detecting heartbeat timeout, and self-promoting to Primary.
4. **`04_Client_Failover_Reconnect.png`**: Client automatically failing over to the newly promoted Primary with all loan data intact.

---

## 2. Selection Recommendation for Lab File

All 4 screenshots are embedded in Section 6 (*Output screenshot*) of your official SPIT lab report.
