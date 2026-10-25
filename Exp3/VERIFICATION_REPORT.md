# Verification Report — Experiment 3 (DC Lab)

## Environment Specification
- **System**: Windows 11 (64-bit)
- **Java Platform**: OpenJDK 21
- **Experiment Title**: Implementation of Clock Synchronization (Logical & Physical)
- **Date of Execution**: August 22, 2026

---

## Execution Matrix

| Test ID | Feature / Test Case | Expected Behavior | Status |
| :--- | :--- | :--- | :---: |
| **EX3-COMP** | Compilation of `LamportClockBanking`, `CristianClockServer`, `CristianClockClient` | Clean compilation (`0` errors, `.class` files generated) | **PASS** |
| **EX3-LAMP** | Lamport Logical Clock Execution | $L(e) = \max(L_{\text{local}}, L_{\text{msg}}) + 1$ rules enforced; causal event ordering preserved | **PASS** |
| **EX3-CRIS-SRV**| Cristian Physical Time Server Initialization | Server binds to TCP port 6000 and responds with `System.currentTimeMillis()` | **PASS** |
| **EX3-CRIS-CLI**| Cristian Physical Time Client Synchronization | Client measures $RTT = T_1 - T_0$, computes $T_{\text{sync}} = T_{\text{server}} + (RTT/2)$, and corrects clock drift | **PASS** |
| **EX3-ORDER**| Event Sequence Verification | Distributed events across Branch 1, Branch 2 & Central Server maintain total ordering | **PASS** |

---

## Verification Summary
- **Total Tests**: 5
- **Passed**: 5
- **Failed**: 0
- **Result**: **PASS**
