// app.js - BANKCORE Distributed Banking Platform Engine
document.addEventListener("DOMContentLoaded", () => {

  // =========================================================
  // 1. REALISTIC DISTRIBUTED LOAN PORTFOLIO DATASET (18 LOANS)
  // =========================================================
  let loans = [
    {
      id: "HL1001",
      customer: "Rahul Sharma",
      customerId: "CUS1001",
      amount: 4500000,
      paidAmount: 150000,
      tenure: 20,
      rate: 8.50,
      emi: 39045,
      cibil: 785,
      city: "Mumbai Central",
      type: "Home Purchase Loan",
      status: "Active"
    },
    {
      id: "HL1002",
      customer: "Atharv Patil",
      customerId: "CUS1002",
      amount: 6500000,
      paidAmount: 0,
      tenure: 25,
      rate: 8.65,
      emi: 53020,
      cibil: 760,
      city: "Delhi NCR",
      type: "Home Purchase Loan",
      status: "Pending"
    },
    {
      id: "HL1003",
      customer: "Tanish Parakh",
      customerId: "CUS1003",
      amount: 8500000,
      paidAmount: 425000,
      tenure: 20,
      rate: 8.40,
      emi: 73250,
      cibil: 810,
      city: "Bangalore South",
      type: "Plot & Construction",
      status: "Active"
    },
    {
      id: "HL1004",
      customer: "Gitesh Patil",
      customerId: "CUS1004",
      amount: 3800000,
      paidAmount: 210000,
      tenure: 15,
      rate: 8.55,
      emi: 37560,
      cibil: 742,
      city: "Pune East",
      type: "Home Improvement",
      status: "Active"
    },
    {
      id: "HL1005",
      customer: "Priya Nair",
      customerId: "CUS1005",
      amount: 5500000,
      paidAmount: 0,
      tenure: 20,
      rate: 8.45,
      emi: 47560,
      cibil: 795,
      city: "Kochi Marine Drive",
      type: "Home Purchase Loan",
      status: "Pending"
    },
    {
      id: "HL1006",
      customer: "Aditya Kulkarni",
      customerId: "CUS1006",
      amount: 11000000,
      paidAmount: 1250000,
      tenure: 20,
      rate: 8.50,
      emi: 95440,
      cibil: 825,
      city: "Pune Wakad",
      type: "Home Purchase Loan",
      status: "Active"
    },
    {
      id: "HL1007",
      customer: "Vikramaditya Rao",
      customerId: "CUS1007",
      amount: 14500000,
      paidAmount: 1800000,
      tenure: 25,
      rate: 8.60,
      emi: 117860,
      cibil: 815,
      city: "Hyderabad Hitec City",
      type: "Balance Transfer",
      status: "Active"
    },
    {
      id: "HL1008",
      customer: "Neha Sengupta",
      customerId: "CUS1008",
      amount: 3200000,
      paidAmount: 0,
      tenure: 15,
      rate: 8.75,
      emi: 31980,
      cibil: 710,
      city: "Kolkata Salt Lake",
      type: "Home Purchase Loan",
      status: "Pending"
    },
    {
      id: "HL1009",
      customer: "Rohan Mehta",
      customerId: "CUS1009",
      amount: 9200000,
      paidAmount: 640000,
      tenure: 20,
      rate: 8.40,
      emi: 79280,
      cibil: 780,
      city: "Ahmedabad SG Highway",
      type: "Home Purchase Loan",
      status: "Active"
    },
    {
      id: "HL1010",
      customer: "Ananya Deshmukh",
      customerId: "CUS1010",
      amount: 4800000,
      paidAmount: 4800000,
      tenure: 15,
      rate: 8.50,
      emi: 47270,
      cibil: 840,
      city: "Nagpur Civil Lines",
      type: "Home Purchase Loan",
      status: "Closed"
    },
    {
      id: "HL1011",
      customer: "Siddharth Verma",
      customerId: "CUS1011",
      amount: 7200000,
      paidAmount: 550000,
      tenure: 20,
      rate: 8.55,
      emi: 62740,
      cibil: 775,
      city: "Gurugram Cyber Hub",
      type: "Home Construction",
      status: "Active"
    },
    {
      id: "HL1012",
      customer: "Kavita Iyer",
      customerId: "CUS1012",
      amount: 5800000,
      paidAmount: 0,
      tenure: 20,
      rate: 8.50,
      emi: 50320,
      cibil: 755,
      city: "Chennai Anna Nagar",
      type: "Home Purchase Loan",
      status: "Pending"
    },
    {
      id: "HL1013",
      customer: "Arjun Reddy",
      customerId: "CUS1013",
      amount: 12500000,
      paidAmount: 1520000,
      tenure: 20,
      rate: 8.40,
      emi: 107720,
      cibil: 830,
      city: "Hyderabad Banjara Hills",
      type: "Home Purchase Loan",
      status: "Active"
    },
    {
      id: "HL1014",
      customer: "Pooja Hegde",
      customerId: "CUS1014",
      amount: 4200000,
      paidAmount: 310000,
      tenure: 15,
      rate: 8.60,
      emi: 41610,
      cibil: 735,
      city: "Mangalore Kadri",
      type: "Home Extension",
      status: "Active"
    },
    {
      id: "HL1015",
      customer: "Deepak Chawla",
      customerId: "CUS1015",
      amount: 18500000,
      paidAmount: 2200000,
      tenure: 25,
      rate: 8.70,
      emi: 151920,
      cibil: 805,
      city: "Chandigarh Sector 17",
      type: "Balance Transfer",
      status: "Active"
    },
    {
      id: "HL1016",
      customer: "Sneha Joshi",
      customerId: "CUS1016",
      amount: 2800000,
      paidAmount: 0,
      tenure: 15,
      rate: 8.65,
      emi: 27840,
      cibil: 720,
      city: "Indore Vijay Nagar",
      type: "Home Improvement",
      status: "Pending"
    },
    {
      id: "HL1017",
      customer: "Harish Natarajan",
      customerId: "CUS1017",
      amount: 6200000,
      paidAmount: 780000,
      tenure: 20,
      rate: 8.50,
      emi: 53790,
      cibil: 790,
      city: "Coimbatore RS Puram",
      type: "Home Purchase Loan",
      status: "Active"
    },
    {
      id: "HL1018",
      customer: "Rajeshwari Gupta",
      customerId: "CUS1018",
      amount: 3500000,
      paidAmount: 3500000,
      tenure: 10,
      rate: 8.45,
      emi: 43350,
      cibil: 818,
      city: "Lucknow Gomti Nagar",
      type: "Home Purchase Loan",
      status: "Closed"
    }
  ];

  // =========================================================
  // 2. VERIFIED CUSTOMERS KYC DIRECTORY (18 ACCOUNTS)
  // =========================================================
  let customers = [
    { id: "CUS1001", name: "Rahul Sharma", branch: "Mumbai Central", pan: "AAAPS8192K", income: "₹24,50,000 / yr", cibil: 785, loans: 1, kyc: "Verified (Aadhaar e-KYC)" },
    { id: "CUS1002", name: "Atharv Patil", branch: "Delhi Connaught Place", pan: "BCTPP1029M", income: "₹32,00,000 / yr", cibil: 760, loans: 1, kyc: "Verified (DigiLocker)" },
    { id: "CUS1003", name: "Tanish Parakh", branch: "Bangalore Koramangala", pan: "AHKPP4921E", income: "₹42,00,000 / yr", cibil: 810, loans: 1, kyc: "Verified (Aadhaar e-KYC)" },
    { id: "CUS1004", name: "Gitesh Patil", branch: "Pune Shivaji Nagar", pan: "BNMPP9301R", income: "₹18,00,000 / yr", cibil: 742, loans: 1, kyc: "Verified (Aadhaar e-KYC)" },
    { id: "CUS1005", name: "Priya Nair", branch: "Kochi Marine Drive", pan: "CNPNP2301B", income: "₹22,50,000 / yr", cibil: 795, loans: 1, kyc: "Biometric Scheduled" },
    { id: "CUS1006", name: "Aditya Kulkarni", branch: "Pune Wakad", pan: "DKKPK4019Q", income: "₹48,00,000 / yr", cibil: 825, loans: 1, kyc: "Verified (Aadhaar e-KYC)" },
    { id: "CUS1007", name: "Vikramaditya Rao", branch: "Hyderabad Hitec City", pan: "ELRPR5820N", income: "₹58,00,000 / yr", cibil: 815, loans: 1, kyc: "Verified (DigiLocker)" },
    { id: "CUS1008", name: "Neha Sengupta", branch: "Kolkata Salt Lake", pan: "FMPSP3910J", income: "₹16,50,000 / yr", cibil: 710, loans: 1, kyc: "Biometric Scheduled" },
    { id: "CUS1009", name: "Rohan Mehta", branch: "Ahmedabad SG Highway", pan: "GNMPM9283L", income: "₹38,00,000 / yr", cibil: 780, loans: 1, kyc: "Verified (Aadhaar e-KYC)" },
    { id: "CUS1010", name: "Ananya Deshmukh", branch: "Nagpur Civil Lines", pan: "HPDPA7721C", income: "₹25,00,000 / yr", cibil: 840, loans: 1, kyc: "Verified (DigiLocker)" },
    { id: "CUS1011", name: "Siddharth Verma", branch: "Gurugram Cyber Hub", pan: "IQVPV1109S", income: "₹36,00,000 / yr", cibil: 775, loans: 1, kyc: "Verified (Aadhaar e-KYC)" },
    { id: "CUS1012", name: "Kavita Iyer", branch: "Chennai Anna Nagar", pan: "JRIPK3928X", income: "₹26,00,000 / yr", cibil: 755, loans: 1, kyc: "Biometric Scheduled" },
    { id: "CUS1013", name: "Arjun Reddy", branch: "Hyderabad Banjara Hills", pan: "KSRPG8810P", income: "₹52,00,000 / yr", cibil: 830, loans: 1, kyc: "Verified (Aadhaar e-KYC)" },
    { id: "CUS1014", name: "Pooja Hegde", branch: "Mangalore Kadri", pan: "LTHPH4912Y", income: "₹20,00,000 / yr", cibil: 735, loans: 1, kyc: "Verified (DigiLocker)" },
    { id: "CUS1015", name: "Deepak Chawla", branch: "Chandigarh Sector 17", pan: "MUKPC5019V", income: "₹72,00,000 / yr", cibil: 805, loans: 1, kyc: "Verified (Aadhaar e-KYC)" },
    { id: "CUS1016", name: "Sneha Joshi", branch: "Indore Vijay Nagar", pan: "NVJPJ3190T", income: "₹15,00,000 / yr", cibil: 720, loans: 1, kyc: "Biometric Scheduled" },
    { id: "CUS1017", name: "Harish Natarajan", branch: "Coimbatore RS Puram", pan: "OWVPN6821A", income: "₹29,00,000 / yr", cibil: 790, loans: 1, kyc: "Verified (Aadhaar e-KYC)" },
    { id: "CUS1018", name: "Rajeshwari Gupta", branch: "Lucknow Gomti Nagar", pan: "PXXPG9182D", income: "₹21,00,000 / yr", cibil: 818, loans: 1, kyc: "Verified (DigiLocker)" }
  ];

  // =========================================================
  // 3. REAL-TIME DISTRIBUTED LEDGER TRANSACTIONS (18 ENTRIES)
  // =========================================================
  let transactions = [
    { txnId: "TXN-9081", loanId: "HL1001", customer: "Rahul Sharma", amount: 50000, type: "Monthly EMI Auto-Debit", channel: "NACH ECS Mandate", time: "Today, 11:42 AM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9082", loanId: "HL1003", customer: "Tanish Parakh", amount: 73250, type: "Monthly EMI Auto-Debit", channel: "NACH ECS Mandate", time: "Today, 10:15 AM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9083", loanId: "HL1006", customer: "Aditya Kulkarni", amount: 150000, type: "Principal Pre-payment", channel: "RTGS Corporate NetBanking", time: "Today, 09:30 AM", status: "SUCCESS (Strong W=3)" },
    { txnId: "TXN-9084", loanId: "HL1007", customer: "Vikramaditya Rao", amount: 117860, type: "Monthly EMI Auto-Debit", channel: "NACH ECS Mandate", time: "Today, 08:45 AM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9085", loanId: "HL1009", customer: "Rohan Mehta", amount: 79280, type: "Monthly EMI Auto-Debit", channel: "UPI AutoPay Mandate", time: "Yesterday, 04:30 PM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9086", loanId: "HL1004", customer: "Gitesh Patil", amount: 37560, type: "Monthly EMI Auto-Debit", channel: "NACH ECS Mandate", time: "Yesterday, 02:15 PM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9087", loanId: "HL1011", customer: "Siddharth Verma", amount: 62740, type: "Monthly EMI Auto-Debit", channel: "IMPS Instant Banking", time: "Yesterday, 11:20 AM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9088", loanId: "HL1013", customer: "Arjun Reddy", amount: 200000, type: "Principal Pre-payment", channel: "NEFT Branch Counter", time: "Yesterday, 10:05 AM", status: "SUCCESS (Strong W=3)" },
    { txnId: "TXN-9089", loanId: "HL1014", customer: "Pooja Hegde", amount: 41610, type: "Monthly EMI Auto-Debit", channel: "NACH ECS Mandate", time: "24 Oct, 03:50 PM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9090", loanId: "HL1015", customer: "Deepak Chawla", amount: 151920, type: "Monthly EMI Auto-Debit", channel: "RTGS NetBanking", time: "24 Oct, 01:10 PM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9091", loanId: "HL1017", customer: "Harish Natarajan", amount: 53790, type: "Monthly EMI Auto-Debit", channel: "NACH ECS Mandate", time: "24 Oct, 11:35 AM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9092", loanId: "HL1001", customer: "Rahul Sharma", amount: 50000, type: "Monthly EMI Auto-Debit", channel: "NACH ECS Mandate", time: "23 Oct, 10:14 AM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9093", loanId: "HL1003", customer: "Tanish Parakh", amount: 73250, type: "Monthly EMI Auto-Debit", channel: "NACH ECS Mandate", time: "23 Oct, 09:40 AM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9094", loanId: "HL1010", customer: "Ananya Deshmukh", amount: 4800000, type: "Full Loan Closure Settlement", channel: "RTGS Certified Remittance", time: "22 Oct, 02:45 PM", status: "SUCCESS (Strong W=3)" },
    { txnId: "TXN-9095", loanId: "HL1018", customer: "Rajeshwari Gupta", amount: 3500000, type: "Full Loan Closure Settlement", channel: "RTGS Certified Remittance", time: "21 Oct, 04:15 PM", status: "SUCCESS (Strong W=3)" },
    { txnId: "TXN-9096", loanId: "HL1006", customer: "Aditya Kulkarni", amount: 95440, type: "Monthly EMI Auto-Debit", channel: "NACH ECS Mandate", time: "20 Oct, 10:30 AM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9097", loanId: "HL1013", customer: "Arjun Reddy", amount: 107720, type: "Monthly EMI Auto-Debit", channel: "NACH ECS Mandate", time: "19 Oct, 11:00 AM", status: "SUCCESS (Quorum W=2)" },
    { txnId: "TXN-9098", loanId: "HL1007", customer: "Vikramaditya Rao", amount: 117860, type: "Monthly EMI Auto-Debit", channel: "NACH ECS Mandate", time: "18 Oct, 09:15 AM", status: "SUCCESS (Quorum W=2)" }
  ];

  let currentSelectedLoan = loans[0];

  // =========================================================
  // 4. TAB NAVIGATION
  // =========================================================
  const navItems = document.querySelectorAll(".nav-item");
  const tabViews = document.querySelectorAll(".tab-view");

  function switchTab(targetTab) {
    navItems.forEach(item => {
      if (item.getAttribute("data-tab") === targetTab) {
        item.classList.add("active");
      } else {
        item.classList.remove("active");
      }
    });

    tabViews.forEach(view => {
      if (view.id === `view-${targetTab}`) {
        view.classList.add("active");
      } else {
        view.classList.remove("active");
      }
    });
  }

  navItems.forEach(item => {
    item.addEventListener("click", (e) => {
      e.preventDefault();
      const tab = item.getAttribute("data-tab");
      if (tab) switchTab(tab);
    });
  });

  const linkBackToLoans = document.getElementById("linkBackToLoans");
  const btnBackToLoansList = document.getElementById("btnBackToLoansList");
  if (linkBackToLoans) linkBackToLoans.addEventListener("click", (e) => { e.preventDefault(); switchTab("loans"); });
  if (btnBackToLoansList) btnBackToLoansList.addEventListener("click", () => switchTab("loans"));

  function checkHash() {
    const hash = window.location.hash.replace("#", "");
    if (hash) switchTab(hash);
  }
  checkHash();
  window.addEventListener("hashchange", checkHash);

  // =========================================================
  // 5. RENDER LOANS TABLE & KPI METRICS
  // =========================================================
  function renderLoansTable(filterQuery = "") {
    const tbody = document.getElementById("loansTableBody");
    if (!tbody) return;
    tbody.innerHTML = "";

    const q = filterQuery.toLowerCase().trim();
    const filtered = loans.filter(l => 
      !q ||
      l.id.toLowerCase().includes(q) ||
      l.customer.toLowerCase().includes(q) ||
      l.customerId.toLowerCase().includes(q) ||
      l.city.toLowerCase().includes(q) ||
      l.type.toLowerCase().includes(q) ||
      l.status.toLowerCase().includes(q)
    );

    let total = loans.length;
    let active = loans.filter(l => l.status === "Active" || l.status === "Approved").length;
    let pending = loans.filter(l => l.status === "Pending").length;
    let completed = loans.filter(l => l.status === "Completed" || l.status === "Closed").length;
    let totalPortfolioVal = loans.reduce((acc, l) => acc + l.amount, 0);

    // Update Counts in Loans View
    const elTotal = document.getElementById("loansCountTotal");
    const elActive = document.getElementById("loansCountActive");
    const elPending = document.getElementById("loansCountPending");
    const elCompleted = document.getElementById("loansCountCompleted");
    const elTableCount = document.getElementById("tableLoansCount");

    if (elTotal) elTotal.innerText = total;
    if (elActive) elActive.innerText = active;
    if (elPending) elPending.innerText = pending;
    if (elCompleted) elCompleted.innerText = completed;
    if (elTableCount) elTableCount.innerText = `${filtered.length} loan portfolios displayed`;

    // Synchronize Dashboard KPI Cards
    const elDashActive = document.getElementById("dashActiveLoans");
    const elDashPortfolio = document.getElementById("dashPortfolioValue");
    const elDashPending = document.getElementById("dashPendingLoans");
    if (elDashActive) elDashActive.innerText = active;
    if (elDashPortfolio) elDashPortfolio.innerText = `₹${(totalPortfolioVal / 10000000).toFixed(1)} Cr`;
    if (elDashPending) elDashPending.innerText = pending;

    filtered.forEach(loan => {
      const tr = document.createElement("tr");
      const cibilBadge = loan.cibil >= 750 ? "active" : (loan.cibil >= 700 ? "pending" : "closed");
      tr.innerHTML = `
        <td><strong>${loan.id}</strong></td>
        <td>
          <div class="customer-cell-name">${loan.customer}</div>
          <div class="customer-cell-sub">${loan.customerId} • ${loan.type}</div>
        </td>
        <td><strong>₹${loan.amount.toLocaleString('en-IN')}</strong></td>
        <td>${loan.tenure} yrs @ ${loan.rate}%</td>
        <td>${loan.city}</td>
        <td><span class="status-badge-sm ${cibilBadge}">${loan.cibil}</span></td>
        <td><span class="status-badge-sm ${loan.status.toLowerCase()}">${loan.status}</span></td>
        <td>
          <button class="btn btn-secondary btn-sm btn-view-loan" data-id="${loan.id}">View Details</button>
        </td>
      `;
      tbody.appendChild(tr);
    });

    document.querySelectorAll(".btn-view-loan").forEach(btn => {
      btn.addEventListener("click", () => {
        const id = btn.getAttribute("data-id");
        viewLoanDetails(id);
      });
    });
  }

  // Search input for Loans
  const searchLoansInput = document.getElementById("searchLoansInput");
  if (searchLoansInput) {
    searchLoansInput.addEventListener("input", (e) => {
      renderLoansTable(e.target.value);
    });
  }

  const btnRefreshLoans = document.getElementById("btnRefreshLoans");
  if (btnRefreshLoans) {
    btnRefreshLoans.addEventListener("click", () => {
      if (searchLoansInput) searchLoansInput.value = "";
      renderLoansTable("");
    });
  }

  // =========================================================
  // 6. RENDER CUSTOMERS DIRECTORY
  // =========================================================
  function renderCustomersTable(filterQuery = "") {
    const tbody = document.getElementById("customersTableBody");
    if (!tbody) return;
    tbody.innerHTML = "";

    const q = filterQuery.toLowerCase().trim();
    const filtered = customers.filter(c =>
      !q ||
      c.id.toLowerCase().includes(q) ||
      c.name.toLowerCase().includes(q) ||
      c.branch.toLowerCase().includes(q) ||
      c.pan.toLowerCase().includes(q)
    );

    const elCount = document.getElementById("tableCustomersCount");
    if (elCount) elCount.innerText = `${filtered.length} verified accounts displayed`;

    filtered.forEach(c => {
      const tr = document.createElement("tr");
      const kycClass = c.kyc.includes("Verified") ? "active" : "pending";
      tr.innerHTML = `
        <td><strong>${c.id}</strong></td>
        <td>
          <div class="customer-cell-name">${c.name}</div>
        </td>
        <td>${c.branch}</td>
        <td><code>${c.pan}</code></td>
        <td>${c.income}</td>
        <td><span class="status-badge-sm active">${c.cibil}</span></td>
        <td>${c.loans} Portfolio(s)</td>
        <td><span class="status-badge-sm ${kycClass}">${c.kyc}</span></td>
      `;
      tbody.appendChild(tr);
    });
  }

  const searchCustomersInput = document.getElementById("searchCustomersInput");
  if (searchCustomersInput) {
    searchCustomersInput.addEventListener("input", (e) => {
      renderCustomersTable(e.target.value);
    });
  }

  const btnRefreshCustomers = document.getElementById("btnRefreshCustomers");
  if (btnRefreshCustomers) {
    btnRefreshCustomers.addEventListener("click", () => {
      if (searchCustomersInput) searchCustomersInput.value = "";
      renderCustomersTable("");
    });
  }

  // =========================================================
  // 7. RENDER TRANSACTIONS LEDGER
  // =========================================================
  function renderTransactionsTable(filterQuery = "") {
    const tbody = document.getElementById("txnTableBody");
    if (!tbody) return;
    tbody.innerHTML = "";

    const q = filterQuery.toLowerCase().trim();
    const filtered = transactions.filter(t =>
      !q ||
      t.txnId.toLowerCase().includes(q) ||
      t.loanId.toLowerCase().includes(q) ||
      t.customer.toLowerCase().includes(q) ||
      t.type.toLowerCase().includes(q) ||
      t.channel.toLowerCase().includes(q)
    );

    const elCount = document.getElementById("tableTxnCount");
    if (elCount) elCount.innerText = `${filtered.length} ledger entries displayed`;

    filtered.forEach(t => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td><strong>${t.txnId}</strong></td>
        <td><span style="color:#2563eb; font-weight:600;">${t.loanId}</span></td>
        <td>
          <div class="customer-cell-name">${t.customer}</div>
        </td>
        <td><strong>₹${t.amount.toLocaleString('en-IN')}</strong></td>
        <td>${t.type}</td>
        <td><span style="font-size:12px; color:#64748b;">${t.channel}</span></td>
        <td>${t.time}</td>
        <td><span class="status-badge-sm active">${t.status}</span></td>
      `;
      tbody.appendChild(tr);
    });
  }

  const searchTxnInput = document.getElementById("searchTxnInput");
  if (searchTxnInput) {
    searchTxnInput.addEventListener("input", (e) => {
      renderTransactionsTable(e.target.value);
    });
  }

  const btnRefreshTxns = document.getElementById("btnRefreshTxns");
  if (btnRefreshTxns) {
    btnRefreshTxns.addEventListener("click", () => {
      if (searchTxnInput) searchTxnInput.value = "";
      renderTransactionsTable("");
    });
  }

  // =========================================================
  // 8. VIEW LOAN DETAILS & SIMULATE DISTRIBUTED TRANSACTIONS
  // =========================================================
  function viewLoanDetails(loanId) {
    const loan = loans.find(l => l.id === loanId);
    if (!loan) return;
    currentSelectedLoan = loan;

    document.getElementById("detailBreadcrumbId").innerText = loan.id;
    document.getElementById("detailHeaderSub").innerText = `${loan.id} • ${loan.customer}`;
    document.getElementById("detailCustomerName").innerText = loan.customer;
    document.getElementById("detailCustomerId").innerText = `Customer ID: ${loan.customerId}`;
    document.getElementById("detailLoanId").innerText = loan.id;
    document.getElementById("detailAmount").innerText = `₹${loan.amount.toLocaleString('en-IN')}`;
    document.getElementById("detailTenure").innerText = `${loan.tenure} years (${loan.tenure * 12} EMIs)`;
    
    const elType = document.getElementById("detailType");
    if (elType) elType.innerText = loan.type;

    const elRoi = document.getElementById("detailRoi");
    if (elRoi) elRoi.innerText = `${loan.rate}% p.a. (Floating)`;

    const elEmi = document.getElementById("detailEmi");
    if (elEmi) elEmi.innerText = `₹${loan.emi.toLocaleString('en-IN')} / mo`;

    const elCity = document.getElementById("detailCity");
    if (elCity) elCity.innerText = loan.city;

    const elCibil = document.getElementById("detailCibil");
    if (elCibil) elCibil.innerText = `${loan.cibil} (${loan.cibil >= 750 ? "Prime / Low Risk" : "Standard Risk"})`;

    const elPaid = document.getElementById("detailPaid");
    if (elPaid) elPaid.innerText = `₹${loan.paidAmount.toLocaleString('en-IN')}`;

    const balance = Math.max(0, loan.amount - loan.paidAmount);
    const elBalance = document.getElementById("detailBalance");
    if (elBalance) elBalance.innerText = `₹${balance.toLocaleString('en-IN')}`;

    const badge = document.getElementById("detailStatusBadge");
    badge.innerText = loan.status.toUpperCase();
    badge.className = `status-badge-lg ${loan.status.toLowerCase()}`;

    document.getElementById("paymentStatusFeedback").innerText = "";
    switchTab("loan-details");
  }

  // Simulate EMI Payment
  const btnSimulateEmiPayment = document.getElementById("btnSimulateEmiPayment");
  if (btnSimulateEmiPayment) {
    btnSimulateEmiPayment.addEventListener("click", () => {
      const amtInput = document.getElementById("payEmiAmount");
      const amt = parseFloat(amtInput.value) || 50000;
      currentSelectedLoan.paidAmount += amt;
      currentSelectedLoan.status = "Active";

      const remainingBal = Math.max(0, currentSelectedLoan.amount - currentSelectedLoan.paidAmount);
      const elBalance = document.getElementById("detailBalance");
      if (elBalance) elBalance.innerText = `₹${remainingBal.toLocaleString('en-IN')}`;

      const elPaid = document.getElementById("detailPaid");
      if (elPaid) elPaid.innerText = `₹${currentSelectedLoan.paidAmount.toLocaleString('en-IN')}`;

      // Append real transaction
      const newTxnId = `TXN-${9100 + transactions.length}`;
      const newTxn = {
        txnId: newTxnId,
        loanId: currentSelectedLoan.id,
        customer: currentSelectedLoan.customer,
        amount: amt,
        type: "Monthly EMI Repayment",
        channel: "NACH ECS Mandate (Online)",
        time: "Just now",
        status: "SUCCESS (Quorum W=2)"
      };
      transactions.unshift(newTxn);
      renderTransactionsTable();

      document.getElementById("paymentStatusFeedback").innerText = 
        `✓ Repayment of ₹${amt.toLocaleString('en-IN')} recorded in distributed ledger (${newTxnId})! Balance updated to ₹${remainingBal.toLocaleString('en-IN')}.`;
      
      const badge = document.getElementById("detailStatusBadge");
      badge.innerText = "ACTIVE";
      badge.className = "status-badge-lg active";

      renderLoansTable();
      addActivity(`Repayment of ₹${amt.toLocaleString('en-IN')} recorded for ${currentSelectedLoan.id} (${currentSelectedLoan.customer})`);
    });
  }

  // Simulate Loan Approval
  const btnApproveLoan = document.getElementById("btnApproveLoan");
  if (btnApproveLoan) {
    btnApproveLoan.addEventListener("click", () => {
      currentSelectedLoan.status = "Active";
      const badge = document.getElementById("detailStatusBadge");
      badge.innerText = "ACTIVE";
      badge.className = "status-badge-lg active";
      document.getElementById("paymentStatusFeedback").innerText = "✓ Loan Application Sanctioned and Disbursed across Distributed Cluster!";
      renderLoansTable();
      addActivity(`Loan ${currentSelectedLoan.id} sanctioned & disbursed for ${currentSelectedLoan.customer}.`);
    });
  }

  // Modal: + New Loan Application
  const modalNewLoan = document.getElementById("modalNewLoan");
  const btnOpenNewLoanModal = document.getElementById("btnOpenNewLoanModal");
  const btnCloseNewLoanModal = document.getElementById("btnCloseNewLoanModal");
  const btnCancelNewLoan = document.getElementById("btnCancelNewLoan");
  const formNewLoan = document.getElementById("formNewLoan");

  if (btnOpenNewLoanModal) btnOpenNewLoanModal.addEventListener("click", () => modalNewLoan.classList.add("open"));
  if (btnCloseNewLoanModal) btnCloseNewLoanModal.addEventListener("click", () => modalNewLoan.classList.remove("open"));
  if (btnCancelNewLoan) btnCancelNewLoan.addEventListener("click", () => modalNewLoan.classList.remove("open"));

  if (formNewLoan) {
    formNewLoan.addEventListener("submit", (e) => {
      e.preventDefault();
      const cid = document.getElementById("inputCustId").value.trim();
      const cname = document.getElementById("inputCustName").value.trim();
      const amount = parseFloat(document.getElementById("inputAmount").value) || 5000000;
      const tenure = parseInt(document.getElementById("inputTenure").value) || 20;
      const rate = parseFloat(document.getElementById("inputRate").value) || 8.5;

      const monthlyRate = (rate / 12) / 100;
      const totalMonths = tenure * 12;
      const emi = Math.round((amount * monthlyRate * Math.pow(1 + monthlyRate, totalMonths)) / (Math.pow(1 + monthlyRate, totalMonths) - 1));

      const newId = `HL${1001 + loans.length}`;
      const newLoan = {
        id: newId,
        customer: cname,
        customerId: cid,
        amount: amount,
        paidAmount: 0,
        tenure: tenure,
        rate: rate,
        emi: emi,
        cibil: 765,
        city: "Mumbai Central",
        type: "Home Purchase Loan",
        status: "Pending"
      };

      loans.push(newLoan);

      // Add to customers if not exists
      if (!customers.find(c => c.id === cid)) {
        customers.push({
          id: cid,
          name: cname,
          branch: "Mumbai Central",
          pan: "AXCPP8821Z",
          income: "₹28,00,000 / yr",
          cibil: 765,
          loans: 1,
          kyc: "Verified (Aadhaar e-KYC)"
        });
        renderCustomersTable();
      }

      renderLoansTable();
      modalNewLoan.classList.remove("open");

      addActivity(`New Loan Application ${newId} submitted by ${cname} (₹${amount.toLocaleString('en-IN')})`);
      appendReplLog(`[REPLICATE] -> Propagated new application ${newId} to Cluster Replicas.`);
      viewLoanDetails(newId);
    });
  }

  // Activity Feed Helper
  function addActivity(text) {
    const list = document.getElementById("activityList");
    if (!list) return;
    const item = document.createElement("div");
    item.className = "activity-item";
    item.innerHTML = `
      <div class="activity-icon green">✓</div>
      <div class="activity-body">
        <span class="activity-text"><strong>${text}</strong></span>
        <span class="activity-time">Just now</span>
      </div>
    `;
    list.prepend(item);
  }

  function appendReplLog(msg, type = "info") {
    const body = document.getElementById("replConsoleBody");
    if (!body) return;
    const div = document.createElement("div");
    div.className = `log-line ${type}`;
    div.innerText = msg;
    body.appendChild(div);
    body.scrollTop = body.scrollHeight;
  }

  // =========================================================
  // 9. HIGH AVAILABILITY & ACTIVE-PASSIVE FAILOVER MONITOR
  // =========================================================
  const btnCrashPrimary = document.getElementById("btnCrashPrimary");
  const btnRecoverPrimary = document.getElementById("btnRecoverPrimary");

  if (btnCrashPrimary) {
    btnCrashPrimary.addEventListener("click", () => {
      const boxPrimary = document.getElementById("boxPrimary");
      const boxBackup = document.getElementById("boxBackup");
      const pillPrimaryState = document.getElementById("pillPrimaryState");
      const pillBackupState = document.getElementById("pillBackupState");
      const lblBackupTag = document.getElementById("lblBackupTag");

      boxPrimary.classList.remove("primary");
      boxPrimary.style.borderColor = "#ef4444";
      boxPrimary.style.backgroundColor = "#fef2f2";
      pillPrimaryState.innerText = "CRASHED (Unresponsive)";
      pillPrimaryState.style.backgroundColor = "#ef4444";

      appendReplLog("[CRASH SIMULATED] Primary process terminated via SIGINT.", "err");
      appendReplLog("[HEARTBEAT MONITOR] Backup detected missing heartbeat PING signals on port 6000...", "warn");

      setTimeout(() => {
        appendReplLog("[FAILOVER TIMEOUT] 6000ms elapsed without PING -- Primary confirmed DOWN.", "err");
        lblBackupTag.innerText = "ACTIVE PRIMARY (PROMOTED)";
        lblBackupTag.style.color = "#ea580c";
        pillBackupState.innerText = "ACTIVE (Port 5000 Opened - 0 Data Loss)";
        pillBackupState.className = "node-state-pill";
        pillBackupState.style.backgroundColor = "#ea580c";
        boxBackup.style.borderColor = "#ea580c";
        boxBackup.style.backgroundColor = "#fff7ed";
        appendReplLog("[PROMOTION COMPLETE] Bank Node 02 is now serving client traffic on port 5000.", "succ");
      }, 1500);
    });
  }

  if (btnRecoverPrimary) {
    btnRecoverPrimary.addEventListener("click", () => {
      location.reload();
    });
  }

  // =========================================================
  // 10. DISTRIBUTED CONSENSUS & BULLY ELECTION SIMULATION
  // =========================================================
  const btnCrashCoordinator = document.getElementById("btnCrashCoordinator");
  const btnTriggerElection = document.getElementById("btnTriggerElection");
  const btnRecoverCoordinator = document.getElementById("btnRecoverCoordinator");
  const bullyConsole = document.getElementById("bullyConsoleBody");

  function logBully(msg, type = "info") {
    if (!bullyConsole) return;
    const div = document.createElement("div");
    div.className = `log-line ${type}`;
    div.innerText = msg;
    bullyConsole.appendChild(div);
    bullyConsole.scrollTop = bullyConsole.scrollHeight;
  }

  if (btnCrashCoordinator) {
    btnCrashCoordinator.addEventListener("click", () => {
      const bnode4 = document.getElementById("bnode4");
      bnode4.classList.add("crashed");
      document.getElementById("roleNode4").innerText = "DOWN";
      document.getElementById("roleNode4").style.backgroundColor = "#ef4444";
      logBully("[CRASH EVENT] Cluster Coordinator (BankNode 04) has crashed/gone offline!", "err");
    });
  }

  if (btnTriggerElection) {
    btnTriggerElection.addEventListener("click", () => {
      logBully(">>> [ELECTION INITIATED] BankNode 02 detected failure & broadcasts ELECTION to Node 3 & Node 4.", "warn");
      setTimeout(() => {
        logBully("  [RECEIVE OK] BankNode 03 responds OK to BankNode 02.", "info");
        logBully("  [TIMEOUT] No response from BankNode 04 (Node is DOWN).", "err");
        logBully("  [ELECTION] BankNode 03 sends ELECTION to BankNode 04 (Timeout).", "warn");
        setTimeout(() => {
          document.getElementById("roleNode3").innerText = "COORDINATOR";
          document.getElementById("roleNode3").className = "badge-role coord";
          document.getElementById("bnode3").classList.add("coordinator");
          logBully("[ELECTION RESULT] BankNode 03 WON THE ELECTION!", "succ");
          logBully("  [BROADCAST] BankNode 03 announces COORDINATOR leadership to Nodes 1 & 2.", "succ");
        }, 800);
      }, 600);
    });
  }

  if (btnRecoverCoordinator) {
    btnRecoverCoordinator.addEventListener("click", () => {
      const bnode4 = document.getElementById("bnode4");
      bnode4.classList.remove("crashed");
      document.getElementById("roleNode4").innerText = "COORDINATOR";
      document.getElementById("roleNode4").style.backgroundColor = "#ea580c";
      
      const bnode3 = document.getElementById("bnode3");
      bnode3.classList.remove("coordinator");
      document.getElementById("roleNode3").innerText = "SLAVE";
      document.getElementById("roleNode3").className = "badge-role slave";

      logBully("[RECOVERY EVENT] BankNode 04 (Highest ID = 4) recovered online!", "succ");
      logBully("[BULLY BEHAVIOR] BankNode 04 sends ELECTION, asserts seniority, and reclaims COORDINATOR.", "succ");
    });
  }

  // =========================================================
  // 11. MONOTONIC CLOCK SYNCHRONIZATION (CRISTIAN & BERKELEY)
  // =========================================================
  const btnRunCristianSync = document.getElementById("btnRunCristianSync");
  if (btnRunCristianSync) {
    btnRunCristianSync.addEventListener("click", () => {
      const t0 = Date.now();
      const rtt = Math.floor(Math.random() * 4) + 2; // 2 to 5 ms
      const tServer = t0 + 2;
      const t1 = t0 + rtt;
      const tSync = tServer + Math.floor(rtt / 2);

      document.getElementById("lblT0").innerText = `${t0} ms`;
      document.getElementById("lblTServer").innerText = `${tServer} ms`;
      document.getElementById("lblT1").innerText = `${t1} ms`;
      document.getElementById("lblRtt").innerText = `${rtt} ms`;
      document.getElementById("lblTSync").innerText = `${tSync} ms`;
    });
  }

  // =========================================================
  // 12. END-TO-END AUTOMATED MORTGAGE PIPELINE JOURNEY
  // =========================================================
  const btnRunFullPipeline = document.getElementById("btnRunFullPipeline");
  const btnResetPipeline = document.getElementById("btnResetPipeline");
  const pipelineConsole = document.getElementById("pipelineConsoleBody");
  const pipelineResultCard = document.getElementById("pipelineResultCard");

  function logPipeline(msg, type = "info") {
    if (!pipelineConsole) return;
    const div = document.createElement("div");
    div.className = `log-line ${type}`;
    div.innerText = msg;
    pipelineConsole.appendChild(div);
    pipelineConsole.scrollTop = pipelineConsole.scrollHeight;
  }

  if (btnRunFullPipeline) {
    btnRunFullPipeline.addEventListener("click", () => {
      btnRunFullPipeline.disabled = true;
      pipelineResultCard.style.display = "none";
      
      const steps = [
        { id: "step1", msg: ">>> [GATEWAY] Token-Bucket rate limiter passed (Borrower CUS1001 allowed, 9/10 tokens remaining).", type: "info" },
        { id: "step2", msg: ">>> [LOAD BALANCER] Weighted Round-Robin routed application to Server Node 01 (Weight 5:3:2).", type: "info" },
        { id: "step3", msg: ">>> [CONSENSUS] Bully Coordinator BankNode 04 confirmed active & healthy.", type: "succ" },
        { id: "step4", msg: ">>> [CLOCK SYNC] Cristian RTT sync applied: T0=1787502888060ms, RTT=3ms, Skew=+1ms timestamped.", type: "info" },
        { id: "step5", msg: ">>> [MUTEX] Ricart-Agrawala timestamped request acknowledged by 3 nodes -> Critical Section lock acquired.", type: "succ" },
        { id: "step6", msg: ">>> [REPLICATION] Quorum write committed to Node 1 & Node 2 (W=2 >= 2). SHA-256 state hash verified.", type: "succ" },
        { id: "step7", msg: ">>> [MPI COLLECTIVES] Root Rank 0 Bcast policy -> Scatter mortgage pool -> Parallel EMI compute -> Gather sanctions -> Reduce totals.", type: "succ" },
        { id: "step8", msg: ">>> [MAPREDUCE] Parallel MapReduce aggregated delinquency risk profile & produced certified ledger audit.", type: "succ" }
      ];

      steps.forEach((s, idx) => {
        setTimeout(() => {
          const el = document.getElementById(s.id);
          if (el) {
            el.classList.add("active");
            if (idx > 0) {
              const prev = document.getElementById(steps[idx - 1].id);
              if (prev) { prev.classList.remove("active"); prev.classList.add("completed"); }
            }
          }
          logPipeline(s.msg, s.type);

          if (idx === steps.length - 1) {
            setTimeout(() => {
              const last = document.getElementById(s.id);
              if (last) { last.classList.remove("active"); last.classList.add("completed"); }
              logPipeline("[PIPELINE COMPLETE] Loan application HL-E2E-2026 fully sanctioned across all core distributed banking modules in 42.6 ms!", "succ");
              pipelineResultCard.style.display = "block";
              btnRunFullPipeline.disabled = false;
            }, 500);
          }
        }, (idx + 1) * 450);
      });
    });
  }

  if (btnResetPipeline) {
    btnResetPipeline.addEventListener("click", () => {
      for (let i = 1; i <= 8; i++) {
        const el = document.getElementById(`step${i}`);
        if (el) el.className = "pipeline-step-card";
      }
      pipelineResultCard.style.display = "none";
      btnRunFullPipeline.disabled = false;
      logPipeline("[RESET] Pipeline state reset to initial idle.", "warn");
    });
  }

  // =========================================================
  // 13. MULTI-NODE LEDGER REPLICATION & QUORUM CONSISTENCY
  // =========================================================
  let consistencyState = {
    seq1: 1042, seq2: 1042, seq3: 1042,
    bal1: 4350000, bal2: 4350000, bal3: 4350000,
    hash1: "6a8f1e29", hash2: "6a8f1e29", hash3: "6a8f1e29",
    status1: "Committed (0ms)",
    status2: "Synchronized",
    status3: "Synchronized"
  };

  const consistencyConsole = document.getElementById("consistencyConsoleBody");
  function logConsistency(msg, type = "info") {
    if (!consistencyConsole) return;
    const div = document.createElement("div");
    div.className = `log-line ${type}`;
    div.innerText = msg;
    consistencyConsole.appendChild(div);
    consistencyConsole.scrollTop = consistencyConsole.scrollHeight;
  }

  function updateConsistencyUI() {
    const v1 = document.getElementById("vNode1");
    const v2 = document.getElementById("vNode2");
    const v3 = document.getElementById("vNode3");
    if (v1) v1.innerText = `#TXN-${consistencyState.seq1}`;
    if (v2) v2.innerText = `#TXN-${consistencyState.seq2}`;
    if (v3) v3.innerText = `#TXN-${consistencyState.seq3}`;

    const b1 = document.getElementById("balNode1");
    const b2 = document.getElementById("balNode2");
    const b3 = document.getElementById("balNode3");
    if (b1) b1.innerText = `₹${consistencyState.bal1.toLocaleString('en-IN')}`;
    if (b2) b2.innerText = `₹${consistencyState.bal2.toLocaleString('en-IN')}`;
    if (b3) b3.innerText = `₹${consistencyState.bal3.toLocaleString('en-IN')}`;

    const h1 = document.getElementById("hashNode1");
    const h2 = document.getElementById("hashNode2");
    const h3 = document.getElementById("hashNode3");
    if (h1) h1.innerText = `${consistencyState.hash1}...`;
    if (h2) h2.innerText = `${consistencyState.hash2}...`;
    if (h3) h3.innerText = `${consistencyState.hash3}...`;

    const s1 = document.getElementById("statusNode1");
    const s2 = document.getElementById("statusNode2");
    const s3 = document.getElementById("statusNode3");
    if (s1) { s1.innerText = consistencyState.status1; s1.style.color = "#059669"; }
    if (s2) {
      s2.innerText = consistencyState.status2;
      s2.style.color = consistencyState.status2.includes("Lag") ? "#ea580c" : "#059669";
    }
    if (s3) {
      s3.innerText = consistencyState.status3;
      s3.style.color = consistencyState.status3.includes("Lag") ? "#ef4444" : "#059669";
    }
  }

  const btnConsistencyWrite = document.getElementById("btnConsistencyWrite");
  if (btnConsistencyWrite) {
    btnConsistencyWrite.addEventListener("click", () => {
      const mode = document.getElementById("selConsistencyMode").value;
      const delay = parseInt(document.getElementById("selReplicaDelay").value) || 0;

      consistencyState.seq1++;
      consistencyState.bal1 -= 50000;
      consistencyState.hash1 = Math.random().toString(16).substring(2, 10);
      consistencyState.status1 = "Committed (0ms)";

      logConsistency(`[TXN SUBMITTED] Mode: ${mode} | Repayment -₹50,000 for Loan HL1001. New Txn #${consistencyState.seq1}.`, "warn");

      if (mode === "STRONG") {
        logConsistency(`[STRONG WRITE] Primary (Mumbai) coordinating two-phase commit with Node 2 & Node 3 (W=3) before ACK...`, "info");
        consistencyState.status2 = `Replication In-Flight (${delay}ms)...`;
        consistencyState.status3 = `Replication In-Flight (${delay}ms)...`;
        updateConsistencyUI();

        setTimeout(() => {
          consistencyState.seq2 = consistencyState.seq1;
          consistencyState.bal2 = consistencyState.bal1;
          consistencyState.hash2 = consistencyState.hash1;
          consistencyState.status2 = "Synchronized";

          consistencyState.seq3 = consistencyState.seq1;
          consistencyState.bal3 = consistencyState.bal1;
          consistencyState.hash3 = consistencyState.hash1;
          consistencyState.status3 = "Synchronized";

          updateConsistencyUI();
          logConsistency(`[ACK RECEIVED] All 3 replicas committed Txn #${consistencyState.seq1} (₹${consistencyState.bal1.toLocaleString('en-IN')}). Write latency: ${delay + 14} ms (Zero stale reads, strong linearizability).`, "succ");
        }, delay);
      } else if (mode === "QUORUM") {
        logConsistency(`[QUORUM WRITE] Primary waiting for majority quorum (W=2, R=2, N=3)...`, "info");
        consistencyState.status2 = `Syncing (${Math.floor(delay / 2)}ms)...`;
        consistencyState.status3 = `Replication Lag (${delay}ms)...`;
        updateConsistencyUI();

        setTimeout(() => {
          consistencyState.seq2 = consistencyState.seq1;
          consistencyState.bal2 = consistencyState.bal1;
          consistencyState.hash2 = consistencyState.hash1;
          consistencyState.status2 = "Synchronized (Quorum Member)";
          updateConsistencyUI();

          logConsistency(`[QUORUM ACK] Quorum satisfied! Primary & Node 02 committed Txn #${consistencyState.seq1} (₹${consistencyState.bal1.toLocaleString('en-IN')}). Node 03 updating in background.`, "succ");

          // Background replica 3 catch-up
          setTimeout(() => {
            consistencyState.seq3 = consistencyState.seq1;
            consistencyState.bal3 = consistencyState.bal1;
            consistencyState.hash3 = consistencyState.hash1;
            consistencyState.status3 = "Synchronized (Converged)";
            updateConsistencyUI();
            logConsistency(`[ASYNC REPLICATION COMPLETE] Backup Node 03 caught up to Txn #${consistencyState.seq3}.`, "info");
          }, Math.max(200, delay - Math.floor(delay / 2)));

        }, Math.floor(delay / 2));
      } else {
        // EVENTUAL
        consistencyState.status2 = `Replication In-Flight (${delay}ms lag)`;
        consistencyState.status3 = `Replication In-Flight (${delay}ms lag)`;
        updateConsistencyUI();
        logConsistency(`[EVENTUAL ACK] Primary ACKed client immediately in 2 ms (Txn #${consistencyState.seq1}). Replicas 2 & 3 are lagging! Stale reads are possible until catch-up.`, "warn");
      }
    });
  }

  const btnReadPrimary = document.getElementById("btnReadPrimary");
  if (btnReadPrimary) {
    btnReadPrimary.addEventListener("click", () => {
      logConsistency(`[READ PRIMARY] Node 01 returned Txn #${consistencyState.seq1} (Balance: ₹${consistencyState.bal1.toLocaleString('en-IN')}, Hash: ${consistencyState.hash1}). State: CERTIFIED FRESH.`, "succ");
    });
  }

  const btnReadBackup = document.getElementById("btnReadBackup");
  if (btnReadBackup) {
    btnReadBackup.addEventListener("click", () => {
      if (consistencyState.seq2 < consistencyState.seq1) {
        logConsistency(`[STALE READ DETECTED!] Backup Node 02 returned outdated Txn #${consistencyState.seq2} (Balance: ₹${consistencyState.bal2.toLocaleString('en-IN')}) while Primary is at Txn #${consistencyState.seq1} (Balance: ₹${consistencyState.bal1.toLocaleString('en-IN')})!`, "err");
      } else {
        logConsistency(`[READ BACKUP] Backup Node 02 returned Txn #${consistencyState.seq2} (Balance: ₹${consistencyState.bal2.toLocaleString('en-IN')}). State: SYNCHRONIZED.`, "succ");
      }
    });
  }

  const btnReadQuorum = document.getElementById("btnReadQuorum");
  if (btnReadQuorum) {
    btnReadQuorum.addEventListener("click", () => {
      const highestSeq = Math.max(consistencyState.seq1, consistencyState.seq2);
      const freshestBal = consistencyState.seq1 >= consistencyState.seq2 ? consistencyState.bal1 : consistencyState.bal2;
      logConsistency(`[QUORUM READ (R=2)] Contacted Node 01 (Txn #${consistencyState.seq1}) & Node 02 (Txn #${consistencyState.seq2}). Quorum resolved freshest Txn #${highestSeq} (Balance: ₹${freshestBal.toLocaleString('en-IN')}). Guaranteed fresh by W + R > N (2 + 2 > 3).`, "succ");
    });
  }

  const btnCatchUpReplicas = document.getElementById("btnCatchUpReplicas");
  if (btnCatchUpReplicas) {
    btnCatchUpReplicas.addEventListener("click", () => {
      consistencyState.seq2 = consistencyState.seq1;
      consistencyState.bal2 = consistencyState.bal1;
      consistencyState.hash2 = consistencyState.hash1;
      consistencyState.status2 = "Synchronized";

      consistencyState.seq3 = consistencyState.seq1;
      consistencyState.bal3 = consistencyState.bal1;
      consistencyState.hash3 = consistencyState.hash1;
      consistencyState.status3 = "Synchronized";

      updateConsistencyUI();
      logConsistency(`[CONVERGENCE EVENT] Replication catch-up complete. All 3 nodes converged to Txn #${consistencyState.seq1} with matching state digests!`, "succ");
    });
  }

  // =========================================================
  // 14. API GATEWAY: TOKEN BUCKET LIMITER & LOAD BALANCER
  // =========================================================
  let lbTokens = 10;
  const maxTokens = 10;
  let reqAccepted = 0;
  let reqDropped = 0;
  let nodeConns = [0, 0, 0];
  let nodeRouted = [0, 0, 0];
  const nodeWeights = [5, 3, 2];
  let wrrIndex = 0;

  const lbConsole = document.getElementById("lbConsoleBody");
  function logLb(msg, type = "info") {
    if (!lbConsole) return;
    const div = document.createElement("div");
    div.className = `log-line ${type}`;
    div.innerText = msg;
    lbConsole.appendChild(div);
    lbConsole.scrollTop = lbConsole.scrollHeight;
  }

  // Token Bucket Refill: 2 tokens/sec (1 token every 500ms)
  setInterval(() => {
    if (lbTokens < maxTokens) {
      lbTokens = Math.min(maxTokens, lbTokens + 1);
      updateLbUI();
    }
  }, 500);

  function updateLbUI() {
    const lblTokens = document.getElementById("lblTokensAvail");
    const barTokens = document.getElementById("barBucketTokens");
    const lblAccepted = document.getElementById("lblReqAccepted");
    const lblDropped = document.getElementById("lblReqDropped");
    const lblStatus = document.getElementById("lblBucketStatus");

    if (lblTokens) lblTokens.innerText = `${lbTokens} / ${maxTokens}`;
    if (barTokens) barTokens.style.width = `${(lbTokens / maxTokens) * 100}%`;
    if (lblAccepted) lblAccepted.innerText = reqAccepted;
    if (lblDropped) lblDropped.innerText = reqDropped;

    if (lblStatus) {
      if (lbTokens > 0) {
        lblStatus.innerText = "ALLOWING TRAFFIC";
        lblStatus.className = "live-badge active";
      } else {
        lblStatus.innerText = "THROTTLING (BUCKET EXHAUSTED)";
        lblStatus.className = "live-badge crashed";
      }
    }

    for (let i = 1; i <= 3; i++) {
      const c = document.getElementById(`connsNode${i}`);
      const r = document.getElementById(`routedNode${i}`);
      if (c) c.innerText = nodeConns[i - 1];
      if (r) r.innerText = nodeRouted[i - 1];
    }
  }

  function routeLoanRequest() {
    if (lbTokens <= 0) {
      reqDropped++;
      logLb(`[HTTP 429 TOO MANY REQUESTS] Client rate limit exceeded (Token Bucket empty: 0 tokens). Request throttled with Retry-After: 1s.`, "err");
      updateLbUI();
      return;
    }

    lbTokens--;
    reqAccepted++;

    const algo = document.getElementById("selLbAlgo") ? document.getElementById("selLbAlgo").value : "WRR";
    let selectedNode = 0;

    if (algo === "WRR") {
      const totalWeight = nodeWeights.reduce((a, b) => a + b, 0);
      const rand = wrrIndex % totalWeight;
      if (rand < 5) selectedNode = 0;
      else if (rand < 8) selectedNode = 1;
      else selectedNode = 2;
      wrrIndex++;
    } else {
      selectedNode = nodeConns.indexOf(Math.min(...nodeConns));
    }

    nodeConns[selectedNode]++;
    nodeRouted[selectedNode]++;
    logLb(`[ROUTED] Loan Request -> Server Node 0${selectedNode + 1} (Algo: ${algo}, Active Conns: ${nodeConns[selectedNode]}). Available tokens: ${lbTokens}.`, "succ");
    updateLbUI();

    setTimeout(() => {
      nodeConns[selectedNode] = Math.max(0, nodeConns[selectedNode] - 1);
      updateLbUI();
    }, 1200);
  }

  const btnSendSingleReq = document.getElementById("btnSendSingleReq");
  if (btnSendSingleReq) btnSendSingleReq.addEventListener("click", routeLoanRequest);

  const btnSendBurstReq = document.getElementById("btnSendBurstReq");
  if (btnSendBurstReq) {
    btnSendBurstReq.addEventListener("click", () => {
      logLb(">>> [BURST TRAFFIC] Dispatching 10 concurrent mortgage evaluation requests...", "warn");
      for (let i = 0; i < 10; i++) {
        setTimeout(routeLoanRequest, i * 75);
      }
    });
  }

  const btnSendExcessSpike = document.getElementById("btnSendExcessSpike");
  if (btnSendExcessSpike) {
    btnSendExcessSpike.addEventListener("click", () => {
      logLb(">>> [TRAFFIC SPIKE] Simulating sudden 20-request burst (Exceeds bucket capacity of 10 tokens)...", "err");
      for (let i = 0; i < 20; i++) {
        setTimeout(routeLoanRequest, i * 35);
      }
    });
  }

  const btnResetLb = document.getElementById("btnResetLb");
  if (btnResetLb) {
    btnResetLb.addEventListener("click", () => {
      lbTokens = maxTokens;
      reqAccepted = 0;
      reqDropped = 0;
      nodeConns = [0, 0, 0];
      nodeRouted = [0, 0, 0];
      wrrIndex = 0;
      updateLbUI();
      logLb("[RESET] Rate limiter bucket refilled and router metrics cleared.", "info");
    });
  }

  // =========================================================
  // 15. DISTRIBUTED MAPREDUCE PORTFOLIO ANALYTICS
  // =========================================================
  const btnRunMrJob = document.getElementById("btnRunMrJob");
  const mrConsole = document.getElementById("mrConsoleBody");
  const mrTableBody = document.getElementById("mrTableBody");
  const mrTableHeader = document.getElementById("mrTableHeader");
  const lblMrResultsTitle = document.getElementById("lblMrResultsTitle");

  function logMr(msg, type = "info") {
    if (!mrConsole) return;
    const div = document.createElement("div");
    div.className = `log-line ${type}`;
    div.innerText = msg;
    mrConsole.appendChild(div);
    mrConsole.scrollTop = mrConsole.scrollHeight;
  }

  if (btnRunMrJob) {
    btnRunMrJob.addEventListener("click", () => {
      const job = document.getElementById("selMrJob").value;
      btnRunMrJob.disabled = true;
      logMr(`>>> [JOB SUBMITTED] Initializing Distributed MapReduce Job: ${job}...`, "warn");

      setTimeout(() => {
        logMr(`  [INPUT SPLIT] 1,248 Loan records partitioned into 4 splits (312 records/split).`, "info");
        setTimeout(() => {
          logMr(`  [MAP WORKERS] 4 Mappers executed in parallel. Emitted key-value tuples in 12 ms.`, "succ");
          setTimeout(() => {
            logMr(`  [SHUFFLE & SORT] HashPartitioned intermediate tuples across 2 Reducers grouped by Key.`, "info");
            setTimeout(() => {
              logMr(`  [REDUCE AGGREGATION] Reducer 1 & 2 completed parallel aggregation in 5 ms. Total Wall-Clock: 25 ms.`, "succ");
              logMr(`  [BENCHMARK] Sequential: 95.5 ms vs MapReduce: 25.0 ms -> Speedup: 3.82x!`, "succ");
              btnRunMrJob.disabled = false;

              if (job === "REGIONAL") {
                lblMrResultsTitle.innerText = "Regional Portfolio Exposure (MapReduce Aggregation Output)";
                mrTableHeader.innerHTML = `<th>REGION</th><th>LOAN COUNT</th><th>TOTAL DISBURSED (₹)</th><th>AVG LOAN SIZE (₹)</th><th>RISK TIER</th>`;
                mrTableBody.innerHTML = `
                  <tr><td><strong>West (Mumbai HQ)</strong></td><td>492</td><td>₹3,62,10,000</td><td>₹73,597</td><td><span class="status-badge-sm active">LOW RISK</span></td></tr>
                  <tr><td><strong>North (Delhi NCR)</strong></td><td>384</td><td>₹2,84,50,000</td><td>₹74,088</td><td><span class="status-badge-sm active">LOW RISK</span></td></tr>
                  <tr><td><strong>South (Bangalore)</strong></td><td>248</td><td>₹1,85,40,000</td><td>₹74,758</td><td><span class="status-badge-sm active">LOW RISK</span></td></tr>
                  <tr><td><strong>East (Kolkata)</strong></td><td>124</td><td>₹88,00,000</td><td>₹70,967</td><td><span class="status-badge-sm active">LOW RISK</span></td></tr>
                `;
              } else if (job === "RISK") {
                lblMrResultsTitle.innerText = "Delinquency Risk Category Classification (MapReduce Output)";
                mrTableHeader.innerHTML = `<th>RISK CATEGORY</th><th>LOAN COUNT</th><th>PORTFOLIO SHARE</th><th>AGGREGATE EXPOSURE</th><th>PROVISIONING TIER</th>`;
                mrTableBody.innerHTML = `
                  <tr><td><strong>Low Risk (CIBIL > 750)</strong></td><td>920</td><td>73.7%</td><td>₹6,48,20,000</td><td><span class="status-badge-sm active">STANDARD 0.4%</span></td></tr>
                  <tr><td><strong>Medium Risk (650 - 750)</strong></td><td>248</td><td>19.9%</td><td>₹1,85,40,000</td><td><span class="status-badge-sm pending">MONITORED 2.0%</span></td></tr>
                  <tr><td><strong>High Risk (CIBIL < 650)</strong></td><td>80</td><td>6.4%</td><td>₹48,40,000</td><td><span class="status-badge-sm closed" style="background:#fee2e2; color:#ef4444;">WATCHLIST 15.0%</span></td></tr>
                `;
              } else {
                lblMrResultsTitle.innerText = "Audit Log Word Count & Stopword Filter (MapReduce Output)";
                mrTableHeader.innerHTML = `<th>KEYWORD</th><th>FREQUENCY</th><th>LOG TYPE</th><th>COMPLIANCE FLAG</th>`;
                mrTableBody.innerHTML = `
                  <tr><td><strong>SANCTIONED</strong></td><td>847</td><td>Status Transition</td><td><span class="status-badge-sm active">AUDIT OK</span></td></tr>
                  <tr><td><strong>CIBIL_VERIFIED</strong></td><td>1,102</td><td>Credit Verification</td><td><span class="status-badge-sm active">AUDIT OK</span></td></tr>
                  <tr><td><strong>QUORUM_COMMITTED</strong></td><td>945</td><td>Consensus Log</td><td><span class="status-badge-sm active">AUDIT OK</span></td></tr>
                  <tr><td><strong>EMI_PROCESSED</strong></td><td>2,410</td><td>Financial Ledger</td><td><span class="status-badge-sm active">AUDIT OK</span></td></tr>
                `;
              }
            }, 300);
          }, 300);
        }, 300);
      }, 300);
    });
  }

  // =========================================================
  // 16. MPI COLLECTIVE HIGH-PERFORMANCE BATCH UNDERWRITING
  // =========================================================
  const mpiConsole = document.getElementById("mpiConsoleBody");
  function logMpi(msg, type = "info") {
    if (!mpiConsole) return;
    const div = document.createElement("div");
    div.className = `log-line ${type}`;
    div.innerText = msg;
    mpiConsole.appendChild(div);
    mpiConsole.scrollTop = mpiConsole.scrollHeight;
  }

  const btnMpiBcast = document.getElementById("btnMpiBcast");
  if (btnMpiBcast) {
    btnMpiBcast.addEventListener("click", () => {
      logMpi(`>>> [MPI_Bcast] Root Rank 0 broadcasting policy: Repo Rate = 6.50%, Spread = 2.15%, Min CIBIL = 700 to Ranks 1..3.`, "warn");
      setTimeout(() => {
        logMpi(`  [BCAST DELIVERED] All 4 ranks received policy via Binomial Tree topology in 1.8 ms.`, "succ");
      }, 300);
    });
  }

  const btnMpiScatter = document.getElementById("btnMpiScatter");
  if (btnMpiScatter) {
    btnMpiScatter.addEventListener("click", () => {
      logMpi(`>>> [MPI_Scatter] Root Rank 0 scattering 8 mortgage loans (₹5.12 Cr) into 4 disjoint chunks (2 loans/rank)...`, "warn");
      setTimeout(() => {
        document.getElementById("mpiLoansRank0").innerText = "2 Loans (₹1.25 Cr)";
        document.getElementById("mpiLoansRank1").innerText = "2 Loans (₹0.70 Cr)";
        document.getElementById("mpiLoansRank2").innerText = "2 Loans (₹1.32 Cr)";
        document.getElementById("mpiLoansRank3").innerText = "2 Loans (₹1.85 Cr)";
        logMpi(`  [SCATTER COMPLETE] Ranks 0..3 received disjoint mortgage portfolios for parallel underwriting.`, "succ");
      }, 300);
    });
  }

  const btnMpiCompute = document.getElementById("btnMpiCompute");
  if (btnMpiCompute) {
    btnMpiCompute.addEventListener("click", () => {
      logMpi(`>>> [PARALLEL COMPUTE] All 4 ranks calculating EMI, DTI ratios, and underwriting verdicts locally without locks...`, "info");
      setTimeout(() => {
        document.getElementById("mpiStatusRank0").innerText = "Evaluated (2/2 Approved)";
        document.getElementById("mpiStatusRank1").innerText = "Evaluated (2/2 Approved)";
        document.getElementById("mpiStatusRank2").innerText = "Evaluated (2/2 Approved)";
        document.getElementById("mpiStatusRank3").innerText = "Evaluated (2/2 Approved)";
        logMpi(`  [COMPUTE COMPLETE] 8/8 loans evaluated across regional worker nodes in 3.4 ms.`, "succ");
      }, 350);
    });
  }

  const btnMpiGather = document.getElementById("btnMpiGather");
  if (btnMpiGather) {
    btnMpiGather.addEventListener("click", () => {
      logMpi(`>>> [MPI_Gather] Root Rank 0 gathering sanction decisions into consolidated register...`, "warn");
      setTimeout(() => {
        logMpi(`  [GATHER COMPLETE] 8 sanction records consolidated into Root approval register.`, "succ");
      }, 300);
    });
  }

  const btnMpiReduce = document.getElementById("btnMpiReduce");
  if (btnMpiReduce) {
    btnMpiReduce.addEventListener("click", () => {
      logMpi(`>>> [MPI_Reduce] Executing MPI_SUM (Total Disbursed) and MPI_MAX (Peak Single Exposure)...`, "warn");
      setTimeout(() => {
        document.getElementById("lblMpiSum").innerText = "₹5.12 Cr";
        document.getElementById("lblMpiMax").innerText = "₹1.85 Cr";
        logMpi(`  [REDUCE RESULTS] MPI_SUM = ₹5,12,00,000 | MPI_MAX = ₹1,85,00,000 (Delhi Rank 3).`, "succ");
      }, 300);
    });
  }

  const btnMpiAllgather = document.getElementById("btnMpiAllgather");
  if (btnMpiAllgather) {
    btnMpiAllgather.addEventListener("click", () => {
      logMpi(`>>> [MPI_Allgather] Synchronizing branch active health & liquidity reserves across all ranks...`, "info");
      setTimeout(() => {
        logMpi(`  [ALLGATHER SYNCED] All 4 ranks possess identical cluster view: 4 active workers, ₹15.0 Cr liquidity buffer.`, "succ");
      }, 300);
    });
  }

  const btnMpiRunAll = document.getElementById("btnMpiRunAll");
  if (btnMpiRunAll) {
    btnMpiRunAll.addEventListener("click", () => {
      btnMpiRunAll.disabled = true;
      if (btnMpiBcast) btnMpiBcast.click();
      setTimeout(() => {
        if (btnMpiScatter) btnMpiScatter.click();
        setTimeout(() => {
          if (btnMpiCompute) btnMpiCompute.click();
          setTimeout(() => {
            if (btnMpiGather) btnMpiGather.click();
            setTimeout(() => {
              if (btnMpiReduce) btnMpiReduce.click();
              setTimeout(() => {
                if (btnMpiAllgather) btnMpiAllgather.click();
                logMpi(`[WORKFLOW COMPLETE] Full MPI Collective Underwriting Pipeline completed with 6.8x speedup!`, "succ");
                btnMpiRunAll.disabled = false;
              }, 400);
            }, 400);
          }, 400);
        }, 400);
      }, 400);
    });
  }

  // Initial table renders on boot
  renderLoansTable();
  renderCustomersTable();
  renderTransactionsTable();
});
