package Exp9;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

/**
 * ============================================================================
 * BANKCORE DISTRIBUTED BANKING PLATFORM (OpenJDK 21)
 * Clean Enterprise UI matching the official BANKCORE Design System
 * 
 * Specialized for Experiment 9: MPI Collective Communication
 * Features:
 *  - Classical Temple Bank Logo & Clean Dark Navy Sidebar (#0B0F19)
 *  - Crisp White Top Bar with "Distributed Banking", System Healthy badge, and Admin Profile
 *  - Clean Modern Metric Cards & Table Styling
 *  - 4 Authentic Views:
 *     1. Cluster Overview (MPI_COMM_WORLD, 4 Ranks, Process Health)
 *     2. Policy Broadcast (MPI_Bcast of Repo Rate & Underwriting Constraints)
 *     3. Workload Scatter (MPI_Scatter Partitioning of Loan Batches)
 *     4. Decision Gather & Reduce (MPI_Gather & MPI_Reduce Consolidated Register)
 * 
 * Authors: Atharv Patil (2024300165), Tanish Parakh (2024300157), Gitesh Patil (2024300166)
 * ============================================================================
 */
public class BankMPICollectiveGUI extends JFrame {

    // --- Color Palette (Authentic BANKCORE Design System) ---
    public static final Color COLOR_SIDEBAR_BG = new Color(11, 15, 25);      // #0B0F19 Clean Deep Navy
    public static final Color COLOR_SIDEBAR_ACTIVE = new Color(30, 41, 59);  // #1E293B
    public static final Color COLOR_SIDEBAR_TEXT = new Color(148, 163, 184); // #94A3B8 Slate 400
    public static final Color COLOR_SIDEBAR_MUTED = new Color(100, 116, 139);// #64748B Slate 500

    public static final Color COLOR_BG = new Color(248, 250, 252);          // #F8FAFC Soft Canvas
    public static final Color COLOR_CARD_BG = Color.WHITE;
    public static final Color COLOR_CARD_BORDER = new Color(226, 232, 240);  // #E2E8F0
    public static final Color COLOR_TEXT_PRIMARY = new Color(15, 23, 42);    // #0F172A Slate 900
    public static final Color COLOR_TEXT_SECONDARY = new Color(100, 116, 139);// #64748B Slate 500

    public static final Color COLOR_GREEN = new Color(16, 185, 129);         // #10B981 Emerald
    public static final Color COLOR_GREEN_TEXT = new Color(6, 95, 70);       // #065F46
    public static final Color COLOR_AMBER = new Color(217, 119, 6);          // #D97706 Amber
    public static final Color COLOR_AMBER_TEXT = new Color(146, 64, 14);
    public static final Color COLOR_RED = new Color(239, 68, 68);            // #EF4444 Red
    public static final Color COLOR_RED_TEXT = new Color(153, 27, 27);

    // Fonts
    public static final Font FONT_LOGO = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_PAGE_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 10);
    public static final Font FONT_NAV = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_NAV_ACTIVE = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_CARD_TITLE = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_METRIC_VALUE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);

    // Layout & Navigation
    private JPanel mainContentCardPanel;
    private CardLayout cardLayout;
    private Map<String, NavButton> navButtonsMap = new HashMap<>();

    // Views
    private JPanel overviewPanel;
    private JPanel broadcastPanel;
    private JPanel scatterPanel;
    private JPanel gatherPanel;

    public BankMPICollectiveGUI() {
        setTitle("BANKCORE - Distributed Banking Platform (MPI Collective)");
        setSize(1280, 800);
        setMinimumSize(new Dimension(1150, 750));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(COLOR_BG);

        initUI();
    }

    private void initUI() {
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(COLOR_BG);

        // 1. Sidebar Panel (Left)
        JPanel sidebar = buildSidebar();
        rootPanel.add(sidebar, BorderLayout.WEST);

        // 2. Right Container (Top Bar + Main Card Content)
        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.setBackground(COLOR_BG);

        // Top Header Bar
        JPanel topBar = buildTopBar();
        rightContainer.add(topBar, BorderLayout.NORTH);

        // Content Area with CardLayout
        cardLayout = new CardLayout();
        mainContentCardPanel = new JPanel(cardLayout);
        mainContentCardPanel.setBackground(COLOR_BG);

        // Construct 4 views
        overviewPanel = buildOverviewView();
        broadcastPanel = buildBroadcastView();
        scatterPanel = buildScatterView();
        gatherPanel = buildGatherView();

        mainContentCardPanel.add(overviewPanel, "OVERVIEW");
        mainContentCardPanel.add(broadcastPanel, "BROADCAST");
        mainContentCardPanel.add(scatterPanel, "SCATTER");
        mainContentCardPanel.add(gatherPanel, "GATHER");

        rightContainer.add(mainContentCardPanel, BorderLayout.CENTER);
        rootPanel.add(rightContainer, BorderLayout.CENTER);

        setContentPane(rootPanel);
    }

    // =========================================================================
    // TOP BAR (Matching user's reference image exactly)
    // =========================================================================
    private JPanel buildTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(1000, 60));
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_CARD_BORDER));

        // Left title
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 18));
        left.setOpaque(false);

        JLabel brandTitle = new JLabel("Distributed Banking");
        brandTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        brandTitle.setForeground(COLOR_TEXT_PRIMARY);
        left.add(brandTitle);

        // Right actions: Search, Bell, System Healthy, Avatar, Admin
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 14));
        right.setOpaque(false);

        // Search icon
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        searchIcon.setForeground(COLOR_TEXT_SECONDARY);
        right.add(searchIcon);

        // Notification Bell
        JPanel bellPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        bellPanel.setOpaque(false);
        JLabel bell = new JLabel("🔔");
        bell.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        bell.setForeground(COLOR_TEXT_SECONDARY);
        JLabel redDot = new JLabel("●");
        redDot.setFont(new Font("Segoe UI", Font.BOLD, 9));
        redDot.setForeground(new Color(239, 68, 68));
        bellPanel.add(bell);
        bellPanel.add(redDot);
        right.add(bellPanel);

        // System Healthy pill
        JPanel healthPill = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 2));
        healthPill.setOpaque(false);
        JLabel greenDot = new JLabel("●");
        greenDot.setFont(new Font("Segoe UI", Font.BOLD, 10));
        greenDot.setForeground(COLOR_GREEN);
        JLabel healthText = new JLabel("System Healthy");
        healthText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        healthText.setForeground(new Color(71, 85, 105));
        healthPill.add(greenDot);
        healthPill.add(healthText);
        right.add(healthPill);

        // User Avatar Circle
        JPanel avatarCircle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                setupQualityRendering(g2);
                g2.setColor(new Color(15, 23, 42));
                g2.fillOval(0, 0, 28, 28);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                int tx = (28 - fm.stringWidth("A")) / 2;
                int ty = (28 - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString("A", tx, ty);
                g2.dispose();
            }
        };
        avatarCircle.setPreferredSize(new Dimension(28, 28));
        avatarCircle.setOpaque(false);
        right.add(avatarCircle);

        // User Name & Role
        JPanel userText = new JPanel();
        userText.setLayout(new BoxLayout(userText, BoxLayout.Y_AXIS));
        userText.setOpaque(false);
        JLabel userName = new JLabel("Admin");
        userName.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userName.setForeground(COLOR_TEXT_PRIMARY);
        JLabel userRole = new JLabel("Administrator");
        userRole.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        userRole.setForeground(COLOR_TEXT_SECONDARY);
        userText.add(userName);
        userText.add(userRole);
        right.add(userText);

        topBar.add(left, BorderLayout.WEST);
        topBar.add(right, BorderLayout.EAST);
        return topBar;
    }

    // =========================================================================
    // SIDEBAR (Matching user's reference image exactly)
    // =========================================================================
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(260, 800));
        sidebar.setBackground(COLOR_SIDEBAR_BG);
        sidebar.setBorder(new EmptyBorder(20, 16, 20, 16));

        // Brand Logo (Greek Temple Bank Icon + BANKCORE)
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        logoPanel.setOpaque(false);
        logoPanel.setMaximumSize(new Dimension(260, 44));

        JLabel logoIcon = new JLabel("🏛");
        logoIcon.setForeground(Color.WHITE);
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

        JPanel brandTextPanel = new JPanel();
        brandTextPanel.setLayout(new BoxLayout(brandTextPanel, BoxLayout.Y_AXIS));
        brandTextPanel.setOpaque(false);

        JLabel logoText = new JLabel("BANKCORE");
        logoText.setForeground(Color.WHITE);
        logoText.setFont(FONT_LOGO);

        JLabel subBrand = new JLabel("Distributed Banking");
        subBrand.setForeground(COLOR_SIDEBAR_MUTED);
        subBrand.setFont(new Font("Segoe UI", Font.PLAIN, 10));

        brandTextPanel.add(logoText);
        brandTextPanel.add(subBrand);

        logoPanel.add(logoIcon);
        logoPanel.add(brandTextPanel);
        sidebar.add(logoPanel);

        sidebar.add(Box.createVerticalStrut(24));

        // SECTION 1: BANKING
        sidebar.add(createSidebarSectionLabel("BANKING"));
        sidebar.add(Box.createVerticalStrut(6));

        sidebar.add(createStaticSidebarItem("Dashboard", "▦"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(createStaticSidebarItem("Loans", "💳"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(createStaticSidebarItem("Customers", "👥"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(createStaticSidebarItem("Transactions", "⇄"));

        sidebar.add(Box.createVerticalStrut(20));

        // SECTION 2: DISTRIBUTED SYSTEM / MPI COLLECTIVE
        sidebar.add(createSidebarSectionLabel("DISTRIBUTED SYSTEM"));
        sidebar.add(Box.createVerticalStrut(6));

        navButtonsMap.put("OVERVIEW", createNavButton("MPI Cluster", "⛁", "OVERVIEW", true));
        navButtonsMap.put("BROADCAST", createNavButton("Policy Broadcast", "📢", "BROADCAST", false));
        navButtonsMap.put("SCATTER", createNavButton("Loan Scatter", "📤", "SCATTER", false));
        navButtonsMap.put("GATHER", createNavButton("Decision Gather", "📥", "GATHER", false));

        sidebar.add(navButtonsMap.get("OVERVIEW"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButtonsMap.get("BROADCAST"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButtonsMap.get("SCATTER"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButtonsMap.get("GATHER"));

        sidebar.add(Box.createVerticalGlue());

        // Settings at bottom
        sidebar.add(createStaticSidebarItem("Settings", "⚙"));

        return sidebar;
    }

    private JLabel createSidebarSectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(COLOR_SIDEBAR_MUTED);
        l.setFont(FONT_SECTION);
        l.setBorder(new EmptyBorder(0, 10, 0, 0));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JPanel createStaticSidebarItem(String title, String icon) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(260, 36));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ic = new JLabel(icon);
        ic.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        ic.setForeground(COLOR_SIDEBAR_TEXT);

        JLabel lb = new JLabel(title);
        lb.setFont(FONT_NAV);
        lb.setForeground(COLOR_SIDEBAR_TEXT);

        p.add(ic);
        p.add(lb);
        return p;
    }

    private NavButton createNavButton(String text, String icon, String viewKey, boolean active) {
        NavButton btn = new NavButton(text, icon, active);
        btn.addActionListener(e -> switchView(viewKey));
        return btn;
    }

    private void switchView(String viewKey) {
        cardLayout.show(mainContentCardPanel, viewKey);
        for (Map.Entry<String, NavButton> entry : navButtonsMap.entrySet()) {
            entry.getValue().setActive(entry.getKey().equals(viewKey));
        }
    }

    // =========================================================================
    // VIEW 1: MPI COMMUNICATOR CLUSTER OVERVIEW
    // =========================================================================
    private JPanel buildOverviewView() {
        JPanel view = new JPanel(new BorderLayout(0, 16));
        view.setBackground(COLOR_BG);
        view.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel pageHdr = buildPageHeader(
            "Dashboard", 
            "Overview of your distributed banking MPI collective communication system"
        );
        view.add(pageHdr, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);

        // Top 4 Metric Cards
        JPanel metricsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        metricsRow.setOpaque(false);
        metricsRow.setPreferredSize(new Dimension(960, 95));

        metricsRow.add(createMetricCard("Total Loans", "1,248", "Across all branches"));
        metricsRow.add(createMetricCard("MPI Ranks", "4 Nodes", "MPI_COMM_WORLD Active"));
        metricsRow.add(createMetricCard("Loan Portfolio", "₹8.4 Cr", "Total Outstanding"));
        metricsRow.add(createMetricCard("Queue Size", "8 Loans", "Ready for Scatter"));
        center.add(metricsRow, BorderLayout.NORTH);

        // Main Section: System Health + Applications Table
        JPanel mainSection = new JPanel(new BorderLayout(0, 14));
        mainSection.setOpaque(false);

        // Subheader: System Health
        JPanel healthHdr = new JPanel(new BorderLayout());
        healthHdr.setOpaque(false);
        JLabel shTitle = new JLabel("System Health");
        shTitle.setFont(FONT_CARD_TITLE);
        shTitle.setForeground(COLOR_TEXT_PRIMARY);
        JLabel shSub = new JLabel("Current status of banking MPI nodes");
        shSub.setFont(FONT_SMALL);
        shSub.setForeground(COLOR_TEXT_SECONDARY);
        healthHdr.add(shTitle, BorderLayout.NORTH);
        healthHdr.add(shSub, BorderLayout.SOUTH);

        // 4 Process Health Cards
        JPanel nodesGrid = new JPanel(new GridLayout(1, 4, 14, 0));
        nodesGrid.setOpaque(false);
        nodesGrid.setPreferredSize(new Dimension(960, 100));

        nodesGrid.add(createNodeHealthCard("Rank 0 (Master)", "HQ Mumbai Core", "Root node active", "Online", COLOR_GREEN));
        nodesGrid.add(createNodeHealthCard("Rank 1 (Worker)", "Pune IT Corridor", "Worker operational", "Online", COLOR_GREEN));
        nodesGrid.add(createNodeHealthCard("Rank 2 (Worker)", "Bangalore Branch", "Worker operational", "Online", COLOR_GREEN));
        nodesGrid.add(createNodeHealthCard("Rank 3 (Worker)", "Delhi NCR Hub", "Worker operational", "Online", COLOR_GREEN));

        JPanel healthGroup = new JPanel(new BorderLayout(0, 10));
        healthGroup.setOpaque(false);
        healthGroup.add(healthHdr, BorderLayout.NORTH);
        healthGroup.add(nodesGrid, BorderLayout.CENTER);
        mainSection.add(healthGroup, BorderLayout.NORTH);

        // Table Card: Loan Applications
        JPanel tableCard = new RoundedPanel(10, COLOR_CARD_BG);
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.setBorder(new EmptyBorder(16, 18, 16, 18));

        JPanel tblHdr = new JPanel(new BorderLayout());
        tblHdr.setOpaque(false);
        JLabel tblTitle = new JLabel("Loan Applications (MPI Workload Queue)");
        tblTitle.setFont(FONT_CARD_TITLE);
        tblTitle.setForeground(COLOR_TEXT_PRIMARY);
        JLabel tblSub = new JLabel("8 pending home loan applications staged for collective scatter");
        tblSub.setFont(FONT_SMALL);
        tblSub.setForeground(COLOR_TEXT_SECONDARY);
        tblHdr.add(tblTitle, BorderLayout.NORTH);
        tblHdr.add(tblSub, BorderLayout.SOUTH);
        tableCard.add(tblHdr, BorderLayout.NORTH);

        String[] cols = {"LOAN ID", "CUSTOMER", "AMOUNT", "TENURE", "LOAN TYPE", "BRANCH", "ASSIGNED RANK", "STATUS"};
        Object[][] data = {
            {"HL3001", "Gitesh Patil", "₹75,00,000", "20 years", "Standard Home Loan", "Mumbai", "Rank 0", "Pending"},
            {"HL3002", "Atharv Patil", "₹45,00,000", "15 years", "Express Home Loan", "Pune", "Rank 0", "Pending"},
            {"HL3003", "Tanish Parakh", "₹35,00,000", "20 years", "Affordable Housing", "Delhi", "Rank 1", "Pending"},
            {"HL3004", "Rohan Mehta", "₹1,85,00,000", "25 years", "Luxury Villa Loan", "Mumbai", "Rank 1", "Pending"},
            {"HL3005", "Sneha Sharma", "₹62,00,000", "20 years", "Standard Home Loan", "Bangalore", "Rank 2", "Pending"},
            {"HL3006", "Vikram Malhotra", "₹90,00,000", "15 years", "Plot Purchase Loan", "Pune", "Rank 2", "Pending"},
            {"HL3007", "Priya Nair", "₹28,00,000", "20 years", "Affordable Housing", "Hyderabad", "Rank 3", "Pending"},
            {"HL3008", "Rajesh Gupta", "₹82,00,000", "20 years", "Standard Home Loan", "Delhi", "Rank 3", "Pending"}
        };

        JTable table = createCustomTable(data, cols);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_CARD_BORDER));
        tableCard.add(scroll, BorderLayout.CENTER);

        mainSection.add(tableCard, BorderLayout.CENTER);
        center.add(mainSection, BorderLayout.CENTER);
        view.add(center, BorderLayout.CENTER);
        return view;
    }

    // =========================================================================
    // VIEW 2: POLICY BROADCAST VIEW (MPI_Bcast)
    // =========================================================================
    private JPanel buildBroadcastView() {
        JPanel view = new JPanel(new BorderLayout(0, 16));
        view.setBackground(COLOR_BG);
        view.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel pageHdr = buildPageHeader(
            "Policy Broadcast (MPI_Bcast)", 
            "Synchronizing central interest rates and underwriting policy from Root (Rank 0) to all branch ranks"
        );
        view.add(pageHdr, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);

        // 4 Metric Cards
        JPanel metricsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        metricsRow.setOpaque(false);
        metricsRow.setPreferredSize(new Dimension(960, 95));

        metricsRow.add(createMetricCard("Benchmark Repo Rate", "6.50%", "RBI Statutory Rate"));
        metricsRow.add(createMetricCard("Bank Base Spread", "2.15%", "Operating margin"));
        metricsRow.add(createMetricCard("Effective Loan ROI", "8.65%", "Uniform cluster rate"));
        metricsRow.add(createMetricCard("Broadcast Latency", "3.2 ms", "Binomial tree distribution"));
        center.add(metricsRow, BorderLayout.NORTH);

        // Table Card: Broadcast Status
        JPanel tableCard = new RoundedPanel(10, COLOR_CARD_BG);
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.setBorder(new EmptyBorder(16, 18, 16, 18));

        JPanel tblHdr = new JPanel(new BorderLayout());
        tblHdr.setOpaque(false);
        JLabel tblTitle = new JLabel("MPI_Bcast Synchronization Ledger (Root: Rank 0)");
        tblTitle.setFont(FONT_CARD_TITLE);
        tblTitle.setForeground(COLOR_TEXT_PRIMARY);
        JLabel tblSub = new JLabel("All 4 processes received identical policy parameters in parallel without unicast bottleneck");
        tblSub.setFont(FONT_SMALL);
        tblSub.setForeground(COLOR_TEXT_SECONDARY);
        tblHdr.add(tblTitle, BorderLayout.NORTH);
        tblHdr.add(tblSub, BorderLayout.SOUTH);
        tableCard.add(tblHdr, BorderLayout.NORTH);

        String[] cols = {"RECIPIENT RANK", "BRANCH NODE", "EFFECTIVE ROI", "MIN CIBIL", "MAX DTI", "BUFFER RESERVE", "BROADCAST STATUS"};
        Object[][] data = {
            {"Rank 0 (Root)", "HQ Master Mumbai", "8.65% p.a.", "700 score", "50.0%", "₹50,00,000", "Dispatched (Root)"},
            {"Rank 1", "Pune IT Branch", "8.65% p.a.", "700 score", "50.0%", "₹50,00,000", "Synchronized"},
            {"Rank 2", "Bangalore Tech Corridor", "8.65% p.a.", "700 score", "50.0%", "₹50,00,000", "Synchronized"},
            {"Rank 3", "Delhi NCR Hub", "8.65% p.a.", "700 score", "50.0%", "₹50,00,000", "Synchronized"}
        };

        JTable table = createCustomTable(data, cols);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_CARD_BORDER));
        tableCard.add(scroll, BorderLayout.CENTER);

        center.add(tableCard, BorderLayout.CENTER);
        view.add(center, BorderLayout.CENTER);
        return view;
    }

    // =========================================================================
    // VIEW 3: WORKLOAD SCATTER VIEW (MPI_Scatter)
    // =========================================================================
    private JPanel buildScatterView() {
        JPanel view = new JPanel(new BorderLayout(0, 16));
        view.setBackground(COLOR_BG);
        view.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel pageHdr = buildPageHeader(
            "Workload Scatter (MPI_Scatter)", 
            "Partitioning mortgage applications evenly from Root Master across distributed worker processes"
        );
        view.add(pageHdr, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);

        // 4 Metric Cards
        JPanel metricsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        metricsRow.setOpaque(false);
        metricsRow.setPreferredSize(new Dimension(960, 95));

        metricsRow.add(createMetricCard("Total Ingested", "8 Loans", "Batch payload"));
        metricsRow.add(createMetricCard("Scatter Chunk Size", "2 Loans/Rank", "Equipartitioned workload"));
        metricsRow.add(createMetricCard("Scatter Duration", "7.1 ms", "MPI collective distribution"));
        metricsRow.add(createMetricCard("Worker Load Deviation", "0.0%", "Zero workload skew"));
        center.add(metricsRow, BorderLayout.NORTH);

        // Table Card: Scatter Partitions
        JPanel tableCard = new RoundedPanel(10, COLOR_CARD_BG);
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.setBorder(new EmptyBorder(16, 18, 16, 18));

        JPanel tblHdr = new JPanel(new BorderLayout());
        tblHdr.setOpaque(false);
        JLabel tblTitle = new JLabel("MPI_Scatter Partition Allotment (Disjoint Batches)");
        tblTitle.setFont(FONT_CARD_TITLE);
        tblTitle.setForeground(COLOR_TEXT_PRIMARY);
        JLabel tblSub = new JLabel("Each worker rank receives its designated chunk to evaluate in parallel");
        tblSub.setFont(FONT_SMALL);
        tblSub.setForeground(COLOR_TEXT_SECONDARY);
        tblHdr.add(tblTitle, BorderLayout.NORTH);
        tblHdr.add(tblSub, BorderLayout.SOUTH);
        tableCard.add(tblHdr, BorderLayout.NORTH);

        String[] cols = {"WORKER RANK", "ASSIGNED LOANS", "APPLICANTS", "TOTAL PRINCIPAL", "TARGET BRANCH", "SCATTER STATUS"};
        Object[][] data = {
            {"Rank 0", "HL3001, HL3002", "Gitesh Patil, Atharv Patil", "₹1,20,00,000", "Mumbai HQ", "Partition Received"},
            {"Rank 1", "HL3003, HL3004", "Tanish Parakh, Rohan Mehta", "₹2,20,00,000", "Pune IT Branch", "Partition Received"},
            {"Rank 2", "HL3005, HL3006", "Sneha Sharma, Vikram Malhotra", "₹1,52,00,000", "Bangalore Corridor", "Partition Received"},
            {"Rank 3", "HL3007, HL3008", "Priya Nair, Rajesh Gupta", "₹1,10,00,000", "Delhi NCR Hub", "Partition Received"}
        };

        JTable table = createCustomTable(data, cols);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_CARD_BORDER));
        tableCard.add(scroll, BorderLayout.CENTER);

        center.add(tableCard, BorderLayout.CENTER);
        view.add(center, BorderLayout.CENTER);
        return view;
    }

    // =========================================================================
    // VIEW 4: DECISION GATHER & REDUCE VIEW (MPI_Gather / MPI_Reduce)
    // =========================================================================
    private JPanel buildGatherView() {
        JPanel view = new JPanel(new BorderLayout(0, 16));
        view.setBackground(COLOR_BG);
        view.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel pageHdr = buildPageHeader(
            "Decision Gather & Reduce", 
            "Consolidated aggregation of parallel evaluations and total credit risk reduction"
        );
        view.add(pageHdr, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);

        // 4 Metric Cards
        JPanel metricsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        metricsRow.setOpaque(false);
        metricsRow.setPreferredSize(new Dimension(960, 95));

        metricsRow.add(createMetricCard("Total Disbursed (SUM)", "₹5.12 Cr", "MPI_Reduce sum"));
        metricsRow.add(createMetricCard("Peak Loan (MAX)", "₹1.85 Cr", "MPI_Reduce max"));
        metricsRow.add(createMetricCard("Approval Rate", "87.5%", "7/8 loans approved"));
        metricsRow.add(createMetricCard("Total Monthly EMI", "₹4,77,663", "Central ledger flow"));
        center.add(metricsRow, BorderLayout.NORTH);

        // Table Card: Final Gathered Decisions
        JPanel tableCard = new RoundedPanel(10, COLOR_CARD_BG);
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.setBorder(new EmptyBorder(16, 18, 16, 18));

        JPanel tblHdr = new JPanel(new BorderLayout());
        tblHdr.setOpaque(false);
        JLabel tblTitle = new JLabel("Consolidated Loan Sanction Register (MPI_Gather Output)");
        tblTitle.setFont(FONT_CARD_TITLE);
        tblTitle.setForeground(COLOR_TEXT_PRIMARY);
        JLabel tblSub = new JLabel("Final underwriting results gathered at Root Master (Rank 0) from all distributed workers");
        tblSub.setFont(FONT_SMALL);
        tblSub.setForeground(COLOR_TEXT_SECONDARY);
        tblHdr.add(tblTitle, BorderLayout.NORTH);
        tblHdr.add(tblSub, BorderLayout.SOUTH);
        tableCard.add(tblHdr, BorderLayout.NORTH);

        String[] cols = {"LOAN ID", "CUSTOMER", "PRINCIPAL", "MONTHLY EMI", "DTI %", "RISK TIER", "EVALUATED BY", "SANCTION DECISION"};
        Object[][] data = {
            {"HL3001", "Gitesh Patil", "₹75,00,000", "₹65,801", "36.6%", "LOW_RISK", "Rank 0 (Mumbai)", "Approved"},
            {"HL3002", "Atharv Patil", "₹45,00,000", "₹44,710", "31.9%", "LOW_RISK", "Rank 0 (Mumbai)", "Approved"},
            {"HL3003", "Tanish Parakh", "₹35,00,000", "₹30,707", "27.9%", "LOW_RISK", "Rank 1 (Pune)", "Approved"},
            {"HL3004", "Rohan Mehta", "₹1,85,00,000", "₹1,50,842", "35.9%", "LOW_RISK", "Rank 1 (Pune)", "Approved"},
            {"HL3005", "Sneha Sharma", "₹62,00,000", "₹54,395", "34.0%", "LOW_RISK", "Rank 2 (Bangalore)", "Approved"},
            {"HL3006", "Vikram Malhotra", "₹90,00,000", "₹89,420", "47.1%", "HIGH_RISK", "Rank 2 (Bangalore)", "Rejected"},
            {"HL3007", "Priya Nair", "₹28,00,000", "₹24,566", "25.9%", "LOW_RISK", "Rank 3 (Delhi)", "Approved"},
            {"HL3008", "Rajesh Gupta", "₹82,00,000", "₹71,942", "41.1%", "LOW_RISK", "Rank 3 (Delhi)", "Approved"}
        };

        JTable table = createCustomTable(data, cols);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_CARD_BORDER));
        tableCard.add(scroll, BorderLayout.CENTER);

        center.add(tableCard, BorderLayout.CENTER);
        view.add(center, BorderLayout.CENTER);
        return view;
    }

    // =========================================================================
    // UI REUSABLE COMPONENTS
    // =========================================================================
    private JPanel buildPageHeader(String title, String subtitle) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel t = new JLabel(title);
        t.setFont(FONT_PAGE_TITLE);
        t.setForeground(COLOR_TEXT_PRIMARY);
        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        s.setForeground(COLOR_TEXT_SECONDARY);
        p.add(t, BorderLayout.NORTH);
        p.add(s, BorderLayout.SOUTH);
        return p;
    }

    private JPanel createMetricCard(String title, String val, String sub) {
        JPanel card = new RoundedPanel(10, COLOR_CARD_BG);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(14, 16, 14, 16));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.setForeground(COLOR_TEXT_SECONDARY);
        card.add(t);
        card.add(Box.createVerticalStrut(6));

        JLabel v = new JLabel(val);
        v.setFont(FONT_METRIC_VALUE);
        v.setForeground(COLOR_TEXT_PRIMARY);
        card.add(v);
        card.add(Box.createVerticalStrut(4));

        JLabel s = new JLabel(sub);
        s.setFont(FONT_SMALL);
        s.setForeground(COLOR_TEXT_SECONDARY);
        card.add(s);

        return card;
    }

    private JPanel createNodeHealthCard(String title, String subtitle, String statusDesc, String statusLabel, Color dotColor) {
        JPanel card = new RoundedPanel(10, COLOR_CARD_BG);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(12, 14, 12, 14));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 13));
        t.setForeground(COLOR_TEXT_PRIMARY);
        top.add(t, BorderLayout.WEST);

        JPanel statusPill = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        statusPill.setOpaque(false);
        JLabel dot = new JLabel("●");
        dot.setFont(new Font("Segoe UI", Font.BOLD, 9));
        dot.setForeground(dotColor);
        JLabel st = new JLabel(statusLabel);
        st.setFont(new Font("Segoe UI", Font.BOLD, 11));
        st.setForeground(dotColor);
        statusPill.add(dot);
        statusPill.add(st);
        top.add(statusPill, BorderLayout.EAST);

        card.add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setOpaque(false);
        bottom.add(Box.createVerticalStrut(6));

        JLabel sub = new JLabel(subtitle);
        sub.setFont(FONT_SMALL);
        sub.setForeground(COLOR_TEXT_SECONDARY);
        bottom.add(sub);
        bottom.add(Box.createVerticalStrut(4));

        JLabel desc = new JLabel(statusDesc);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        desc.setForeground(new Color(148, 163, 184));
        bottom.add(desc);

        card.add(bottom, BorderLayout.CENTER);
        return card;
    }

    private JTable createCustomTable(Object[][] data, String[] cols) {
        DefaultTableModel model = new DefaultTableModel(data, cols) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(38);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setForeground(COLOR_TEXT_PRIMARY);
        table.setBackground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(1000, 34));
        header.setBackground(new Color(248, 250, 252));
        header.setForeground(new Color(100, 116, 139));
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_CARD_BORDER));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 250, 250));

                String str = value == null ? "" : value.toString();
                if (str.equalsIgnoreCase("Approved") || str.equalsIgnoreCase("Synchronized") || str.equalsIgnoreCase("Online")) {
                    setForeground(COLOR_GREEN_TEXT);
                    setFont(new Font("Segoe UI", Font.BOLD, 12));
                } else if (str.equalsIgnoreCase("Pending") || str.equalsIgnoreCase("Partition Received")) {
                    setForeground(COLOR_AMBER_TEXT);
                    setFont(new Font("Segoe UI", Font.BOLD, 12));
                } else if (str.equalsIgnoreCase("Rejected")) {
                    setForeground(COLOR_RED_TEXT);
                    setFont(new Font("Segoe UI", Font.BOLD, 12));
                } else if (str.startsWith("₹") || str.startsWith("Rs.")) {
                    setFont(new Font("Segoe UI", Font.BOLD, 12));
                    setForeground(COLOR_TEXT_PRIMARY);
                } else {
                    setForeground(COLOR_TEXT_PRIMARY);
                    setFont(new Font("Segoe UI", Font.PLAIN, 12));
                }
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        return table;
    }

    // =========================================================================
    // ROUNDED PANEL WITH CLEAN BORDER
    // =========================================================================
    public static class RoundedPanel extends JPanel {
        private int radius;
        private Color bgColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.radius = radius;
            this.bgColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            setupQualityRendering(g2);
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.setColor(COLOR_CARD_BORDER);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }

    // =========================================================================
    // NAV BUTTON (Clean authentic rounded button matching reference)
    // =========================================================================
    public static class NavButton extends JButton {
        private boolean active;
        private String labelText;
        private String iconText;

        public NavButton(String text, String icon, boolean active) {
            super();
            this.labelText = text;
            this.iconText = icon;
            this.active = active;
            setPreferredSize(new Dimension(226, 38));
            setMaximumSize(new Dimension(226, 38));
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setFont(active ? FONT_NAV_ACTIVE : FONT_NAV);
            setForeground(active ? Color.WHITE : COLOR_SIDEBAR_TEXT);
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        public void setActive(boolean active) {
            this.active = active;
            setFont(active ? FONT_NAV_ACTIVE : FONT_NAV);
            setForeground(active ? Color.WHITE : COLOR_SIDEBAR_TEXT);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            setupQualityRendering(g2);

            if (active) {
                g2.setColor(COLOR_SIDEBAR_ACTIVE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            }

            g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            g2.setColor(active ? Color.WHITE : COLOR_SIDEBAR_TEXT);
            g2.drawString(iconText, 12, 24);

            g2.setFont(getFont());
            g2.setColor(getForeground());
            g2.drawString(labelText, 36, 24);

            g2.dispose();
        }
    }

    // =========================================================================
    // EXPORT HIGH-RESOLUTION SCREENSHOTS DIRECTLY FROM JAVA SWING
    // =========================================================================
    private static void setupQualityRendering(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    public void exportScreenCapture(String viewName, String outputPath) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                switchView(viewName);
                getContentPane().doLayout();
                getContentPane().validate();

                int w = getContentPane().getWidth();
                int h = getContentPane().getHeight();
                if (w <= 0 || h <= 0) {
                    w = 1280;
                    h = 800;
                    getContentPane().setSize(w, h);
                    getContentPane().doLayout();
                    getContentPane().validate();
                }
                BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = img.createGraphics();
                setupQualityRendering(g2);

                g2.setColor(COLOR_BG);
                g2.fillRect(0, 0, w, h);

                getContentPane().paint(g2);
                g2.dispose();

                try {
                    File f = new File(outputPath);
                    f.getParentFile().mkdirs();
                    ImageIO.write(img, "PNG", f);
                    System.out.println("[JAVA SWING ENGINE] Exported clean screenshot: " + f.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================================
    // MAIN METHOD
    // =========================================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankMPICollectiveGUI gui = new BankMPICollectiveGUI();
            gui.setVisible(true);

            if (args.length > 0 && args[0].equalsIgnoreCase("--capture")) {
                String outDir = args.length > 1 ? args[1] : "screenshots";
                new Thread(() -> {
                    try {
                        Thread.sleep(600);
                        gui.exportScreenCapture("OVERVIEW", outDir + "/01_Real_Java_UI_MPI_Cluster_Dashboard.png");
                        Thread.sleep(200);
                        gui.exportScreenCapture("BROADCAST", outDir + "/02_Real_Java_UI_Policy_Broadcast.png");
                        Thread.sleep(200);
                        gui.exportScreenCapture("SCATTER", outDir + "/03_Real_Java_UI_Loan_Scatter.png");
                        Thread.sleep(200);
                        gui.exportScreenCapture("GATHER", outDir + "/04_Real_Java_UI_Decision_Gather_Reduce.png");
                        System.out.println("All 4 clean BANKCORE MPI GUI screenshots captured successfully.");
                        System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }).start();
            }
        });
    }
}
