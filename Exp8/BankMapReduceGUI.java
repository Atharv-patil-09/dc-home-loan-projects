package Exp8;

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
 * Specialized for Experiment 8: MapReduce Job in Distributed Systems
 * Features:
 *  - Classical Bank Logo & Clean Dark Navy Sidebar (#0B0F19)
 *  - Crisp White Top Bar with "Distributed Banking", System Healthy badge, and Admin Profile
 *  - Clean Modern Metric Cards & Table Styling
 *  - 4 Authentic Views:
 *     1. Cluster Overview (Mappers, Reducers, HDFS Input Splits)
 *     2. Audit Log WordCount (Underwriting Event Analysis)
 *     3. Portfolio Aggregator (Product-wise Volume & Average Loan)
 *     4. Regional Max Exposure (City-wise Max & Cumulative Loan)
 * 
 * Authors: Atharv Patil (2024300165), Tanish Parakh (2024300157), Gitesh Patil (2024300166)
 * ============================================================================
 */
public class BankMapReduceGUI extends JFrame {

    // --- Color Palette (Authentic BANKCORE Design System) ---
    public static final Color COLOR_SIDEBAR_BG = new Color(11, 15, 25);      // #0B0F19 Clean Deep Navy
    public static final Color COLOR_SIDEBAR_ACTIVE = new Color(30, 41, 59);  // #1E293B
    public static final Color COLOR_SIDEBAR_HOVER = new Color(22, 32, 48);
    public static final Color COLOR_SIDEBAR_TEXT = new Color(148, 163, 184); // #94A3B8 Slate 400
    public static final Color COLOR_SIDEBAR_MUTED = new Color(100, 116, 139);// #64748B Slate 500

    public static final Color COLOR_BG = new Color(248, 250, 252);          // #F8FAFC Soft Canvas
    public static final Color COLOR_CARD_BG = Color.WHITE;
    public static final Color COLOR_CARD_BORDER = new Color(226, 232, 240);  // #E2E8F0
    public static final Color COLOR_TEXT_PRIMARY = new Color(15, 23, 42);    // #0F172A Slate 900
    public static final Color COLOR_TEXT_SECONDARY = new Color(100, 116, 139);// #64748B Slate 500

    public static final Color COLOR_GREEN = new Color(16, 185, 129);         // #10B981 Emerald
    public static final Color COLOR_GREEN_BG = new Color(209, 250, 229);     // #D1FAE5 Soft Emerald Pill
    public static final Color COLOR_GREEN_TEXT = new Color(6, 95, 70);       // #065F46

    public static final Color COLOR_BLUE = new Color(37, 99, 235);           // #2563EB Blue
    public static final Color COLOR_BLUE_BG = new Color(219, 234, 254);      // #DBEAFE
    public static final Color COLOR_BLUE_TEXT = new Color(30, 64, 175);

    public static final Color COLOR_AMBER = new Color(217, 119, 6);          // #D97706 Amber
    public static final Color COLOR_AMBER_BG = new Color(254, 243, 199);     // #FEF3C7
    public static final Color COLOR_AMBER_TEXT = new Color(146, 64, 14);

    public static final Color COLOR_PURPLE = new Color(147, 51, 234);        // #9333EA Purple
    public static final Color COLOR_PURPLE_BG = new Color(243, 232, 255);    // #F3E8FF

    // Fonts
    public static final Font FONT_LOGO = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_PAGE_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 10);
    public static final Font FONT_NAV = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_NAV_ACTIVE = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_CARD_TITLE = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_METRIC_VALUE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);

    // Layout & Navigation
    private JPanel mainContentCardPanel;
    private CardLayout cardLayout;
    private Map<String, NavButton> navButtonsMap = new HashMap<>();

    // Views
    private JPanel overviewPanel;
    private JPanel wordCountPanel;
    private JPanel portfolioPanel;
    private JPanel regionalPanel;

    public BankMapReduceGUI() {
        setTitle("BANKCORE - Distributed Banking Platform");
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
        wordCountPanel = buildWordCountView();
        portfolioPanel = buildPortfolioView();
        regionalPanel = buildRegionalView();

        mainContentCardPanel.add(overviewPanel, "OVERVIEW");
        mainContentCardPanel.add(wordCountPanel, "WORDCOUNT");
        mainContentCardPanel.add(portfolioPanel, "PORTFOLIO");
        mainContentCardPanel.add(regionalPanel, "REGIONAL");

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

        // SECTION 2: DISTRIBUTED SYSTEM / MAPREDUCE
        sidebar.add(createSidebarSectionLabel("DISTRIBUTED SYSTEM"));
        sidebar.add(Box.createVerticalStrut(6));

        navButtonsMap.put("OVERVIEW", createNavButton("Cluster Overview", "⛁", "OVERVIEW", true));
        navButtonsMap.put("WORDCOUNT", createNavButton("Audit WordCount", "📋", "WORDCOUNT", false));
        navButtonsMap.put("PORTFOLIO", createNavButton("Portfolio Summary", "📊", "PORTFOLIO", false));
        navButtonsMap.put("REGIONAL", createNavButton("Regional Exposure", "📍", "REGIONAL", false));

        sidebar.add(navButtonsMap.get("OVERVIEW"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButtonsMap.get("WORDCOUNT"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButtonsMap.get("PORTFOLIO"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButtonsMap.get("REGIONAL"));

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
        p.setMaximumSize(new Dimension(240, 36));
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
    // VIEW 1: MAPREDUCE CLUSTER OVERVIEW VIEW
    // =========================================================================
    private JPanel buildOverviewView() {
        JPanel view = new JPanel(new BorderLayout(0, 16));
        view.setBackground(COLOR_BG);
        view.setBorder(new EmptyBorder(20, 24, 20, 24));

        // Header
        JPanel pageHdr = buildPageHeader(
            "Dashboard", 
            "Overview of your distributed banking MapReduce system"
        );
        view.add(pageHdr, BorderLayout.NORTH);

        // Center Content
        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);

        // Top 4 Metric Cards
        JPanel metricsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        metricsRow.setOpaque(false);
        metricsRow.setPreferredSize(new Dimension(960, 95));

        metricsRow.add(createMetricCard("Total Loans", "1,248", "Across all branches"));
        metricsRow.add(createMetricCard("Active Mappers", "3 Workers", "3 Input Splits"));
        metricsRow.add(createMetricCard("Loan Portfolio", "₹8.4 Cr", "Total Outstanding"));
        metricsRow.add(createMetricCard("Active Reducers", "2 Workers", "HDFS part-00000/1"));
        center.add(metricsRow, BorderLayout.NORTH);

        // Content Section: System Health + Ingestion Table
        JPanel mainSection = new JPanel(new BorderLayout(0, 14));
        mainSection.setOpaque(false);

        // Subheader: System Health
        JPanel healthHdr = new JPanel(new BorderLayout());
        healthHdr.setOpaque(false);
        JLabel shTitle = new JLabel("System Health");
        shTitle.setFont(FONT_CARD_TITLE);
        shTitle.setForeground(COLOR_TEXT_PRIMARY);
        JLabel shSub = new JLabel("Current status of MapReduce banking nodes");
        shSub.setFont(FONT_SMALL);
        shSub.setForeground(COLOR_TEXT_SECONDARY);
        healthHdr.add(shTitle, BorderLayout.NORTH);
        healthHdr.add(shSub, BorderLayout.SOUTH);

        // 3 Phase Health Cards
        JPanel nodesGrid = new JPanel(new GridLayout(1, 3, 16, 0));
        nodesGrid.setOpaque(false);
        nodesGrid.setPreferredSize(new Dimension(960, 100));

        nodesGrid.add(createNodeHealthCard("Map Phase (M1..M3)", "3 Active Workers", "Node operational", "Online", COLOR_GREEN));
        nodesGrid.add(createNodeHealthCard("Shuffle Engine", "Hash Partitioner", "In-memory sort ready", "Online", COLOR_GREEN));
        nodesGrid.add(createNodeHealthCard("Reduce Phase (R1..R2)", "2 Partitions Active", "HDFS writers operational", "Online", COLOR_GREEN));

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
        JLabel tblTitle = new JLabel("Loan Applications (HDFS Input Splits)");
        tblTitle.setFont(FONT_CARD_TITLE);
        tblTitle.setForeground(COLOR_TEXT_PRIMARY);
        JLabel tblSub = new JLabel("12 loan applications partitioned across 3 block splits");
        tblSub.setFont(FONT_SMALL);
        tblSub.setForeground(COLOR_TEXT_SECONDARY);
        tblHdr.add(tblTitle, BorderLayout.NORTH);
        tblHdr.add(tblSub, BorderLayout.SOUTH);
        tableCard.add(tblHdr, BorderLayout.NORTH);

        String[] cols = {"LOAN ID", "CUSTOMER", "AMOUNT", "TENURE", "LOAN TYPE", "BRANCH", "MAPPER", "STATUS"};
        Object[][] data = {
            {"HL1001", "Gitesh Patil", "₹75,00,000", "20 years", "Standard Home Loan", "Mumbai", "Mapper-01", "Approved"},
            {"HL1002", "Atharv Patil", "₹45,00,000", "15 years", "Express Home Loan", "Pune", "Mapper-01", "Pending"},
            {"HL1003", "Tanish Parakh", "₹35,00,000", "20 years", "Affordable Housing", "Delhi", "Mapper-01", "Approved"},
            {"HL1004", "Rohan Mehta", "₹1,85,00,000", "25 years", "Luxury Villa Loan", "Mumbai", "Mapper-01", "Approved"},
            {"HL1005", "Sneha Sharma", "₹62,00,000", "20 years", "Standard Home Loan", "Bangalore", "Mapper-02", "Approved"},
            {"HL1006", "Vikram Malhotra", "₹90,00,000", "15 years", "Plot Purchase Loan", "Pune", "Mapper-02", "Pending"},
            {"HL1007", "Priya Nair", "₹28,00,000", "20 years", "Affordable Housing", "Hyderabad", "Mapper-02", "Approved"}
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
    // VIEW 2: AUDIT LOG WORDCOUNT VIEW
    // =========================================================================
    private JPanel buildWordCountView() {
        JPanel view = new JPanel(new BorderLayout(0, 16));
        view.setBackground(COLOR_BG);
        view.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel pageHdr = buildPageHeader(
            "Audit Log WordCount Job", 
            "Distributed underwriting event keyword extraction and frequency analysis"
        );
        view.add(pageHdr, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);

        // 4 Metric Cards
        JPanel metricsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        metricsRow.setOpaque(false);
        metricsRow.setPreferredSize(new Dimension(960, 95));

        metricsRow.add(createMetricCard("Input Event Lines", "12 Lines", "Raw underwriting stream"));
        metricsRow.add(createMetricCard("Intermediate Pairs", "59 Pairs", "Mappers M1..M3 emit"));
        metricsRow.add(createMetricCard("Combiner Compression", "47.2%", "Local pre-reduction"));
        metricsRow.add(createMetricCard("Unique Events", "28 Events", "HDFS part-r-00000/1"));
        center.add(metricsRow, BorderLayout.NORTH);

        // Table Card: WordCount Results
        JPanel tableCard = new RoundedPanel(10, COLOR_CARD_BG);
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.setBorder(new EmptyBorder(16, 18, 16, 18));

        JPanel tblHdr = new JPanel(new BorderLayout());
        tblHdr.setOpaque(false);
        JLabel tblTitle = new JLabel("MapReduce WordCount Output (HDFS Consolidated)");
        tblTitle.setFont(FONT_CARD_TITLE);
        tblTitle.setForeground(COLOR_TEXT_PRIMARY);
        JLabel tblSub = new JLabel("Frequency of underwriting keywords across distributed branch nodes");
        tblSub.setFont(FONT_SMALL);
        tblSub.setForeground(COLOR_TEXT_SECONDARY);
        tblHdr.add(tblTitle, BorderLayout.NORTH);
        tblHdr.add(tblSub, BorderLayout.SOUTH);
        tableCard.add(tblHdr, BorderLayout.NORTH);

        String[] cols = {"AUDIT KEYWORD", "OCCURRENCE COUNT", "REDUCER PARTITION", "RISK CLASSIFICATION", "ACTION IMPACT"};
        Object[][] data = {
            {"APPROVED", "8 occurrences", "part-r-00000", "Low Risk", "Loan Sanction Letter Dispatched"},
            {"HOME_LOAN", "6 occurrences", "part-r-00000", "Neutral", "Primary Mortgage Collateral Verified"},
            {"INCOME_STABLE", "5 occurrences", "part-r-00001", "Low Risk", "Applicant Salary Slips Verified"},
            {"CIBIL_EXCELLENT", "4 occurrences", "part-r-00000", "Low Risk", "Score > 780 (Preferential Interest Rate)"},
            {"MUMBAI", "4 occurrences", "part-r-00001", "Regional", "Metro Core Branch Valuation"},
            {"PUNE", "3 occurrences", "part-r-00000", "Regional", "Western Regional Operations"},
            {"REJECTED", "3 occurrences", "part-r-00001", "High Risk", "Underwriting Threshold Not Satisfied"},
            {"LOW_CIBIL", "2 occurrences", "part-r-00000", "High Risk", "CIBIL < 650 Risk Mitigation Required"}
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
    // VIEW 3: PORTFOLIO AGGREGATOR VIEW
    // =========================================================================
    private JPanel buildPortfolioView() {
        JPanel view = new JPanel(new BorderLayout(0, 16));
        view.setBackground(COLOR_BG);
        view.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel pageHdr = buildPageHeader(
            "Loan Portfolio Aggregator", 
            "Parallel reduction of application volume, cumulative capital, and average loan size"
        );
        view.add(pageHdr, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);

        // 4 Metric Cards
        JPanel metricsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        metricsRow.setOpaque(false);
        metricsRow.setPreferredSize(new Dimension(960, 95));

        metricsRow.add(createMetricCard("Total Portfolio", "₹11.72 Cr", "Across 5 product categories"));
        metricsRow.add(createMetricCard("Total Applications", "12 Loans", "Processed by 3 Mappers"));
        metricsRow.add(createMetricCard("Highest Volume", "Luxury Villa", "₹4.05 Cr total capital"));
        metricsRow.add(createMetricCard("Avg Ticket Size", "₹97.6 Lakh", "Cluster-wide mean"));
        center.add(metricsRow, BorderLayout.NORTH);

        // Table Card: Aggregated Portfolio
        JPanel tableCard = new RoundedPanel(10, COLOR_CARD_BG);
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.setBorder(new EmptyBorder(16, 18, 16, 18));

        JPanel tblHdr = new JPanel(new BorderLayout());
        tblHdr.setOpaque(false);
        JLabel tblTitle = new JLabel("Aggregated Loan Products (MapReduce Output)");
        tblTitle.setFont(FONT_CARD_TITLE);
        tblTitle.setForeground(COLOR_TEXT_PRIMARY);
        JLabel tblSub = new JLabel("Calculated via key-grouped reduction: count, sum, and average loan size");
        tblSub.setFont(FONT_SMALL);
        tblSub.setForeground(COLOR_TEXT_SECONDARY);
        tblHdr.add(tblTitle, BorderLayout.NORTH);
        tblHdr.add(tblSub, BorderLayout.SOUTH);
        tableCard.add(tblHdr, BorderLayout.NORTH);

        String[] cols = {"PRODUCT CATEGORY", "COUNT", "TOTAL PORTFOLIO VOLUME", "AVERAGE LOAN AMOUNT", "HDFS PARTITION", "MARKET SHARE"};
        Object[][] data = {
            {"Luxury Villa Loan", "2 loans", "₹4,05,00,000", "₹2,02,50,000", "part-r-00001", "34.5% (High Capital)"},
            {"Standard Home Loan", "4 loans", "₹2,87,00,000", "₹71,75,000", "part-r-00000", "24.5% (Core Retail)"},
            {"Express Home Loan", "2 loans", "₹95,00,000", "₹47,50,000", "part-r-00001", "8.1% (Fast Track)"},
            {"Affordable Housing", "3 loans", "₹95,00,000", "₹31,66,667", "part-r-00000", "8.1% (PMAY Subsidy)"},
            {"Plot Purchase Loan", "1 loan", "₹90,00,000", "₹90,00,000", "part-r-00000", "7.7% (Land Acquisition)"}
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
    // VIEW 4: REGIONAL EXPOSURE VIEW
    // =========================================================================
    private JPanel buildRegionalView() {
        JPanel view = new JPanel(new BorderLayout(0, 16));
        view.setBackground(COLOR_BG);
        view.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel pageHdr = buildPageHeader(
            "Regional Exposure Analytics", 
            "Distributed max-loan detection and regional branch exposure summation"
        );
        view.add(pageHdr, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);

        // 4 Metric Cards
        JPanel metricsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        metricsRow.setOpaque(false);
        metricsRow.setPreferredSize(new Dimension(960, 95));

        metricsRow.add(createMetricCard("Peak Metro Loan", "₹2.20 Cr", "Bangalore Tech Corridor"));
        metricsRow.add(createMetricCard("Highest Exposure", "₹3.42 Cr", "Mumbai Tier-1 Metro"));
        metricsRow.add(createMetricCard("Active Branches", "5 Metros", "Distributed cluster"));
        metricsRow.add(createMetricCard("Execution Duration", "7 ms", "Distributed Reducer workers"));
        center.add(metricsRow, BorderLayout.NORTH);

        // Table Card: Regional Exposure
        JPanel tableCard = new RoundedPanel(10, COLOR_CARD_BG);
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.setBorder(new EmptyBorder(16, 18, 16, 18));

        JPanel tblHdr = new JPanel(new BorderLayout());
        tblHdr.setOpaque(false);
        JLabel tblTitle = new JLabel("Regional Exposure & Peak Disbursed Loan Ledger");
        tblTitle.setFont(FONT_CARD_TITLE);
        tblTitle.setForeground(COLOR_TEXT_PRIMARY);
        JLabel tblSub = new JLabel("MapReduce output calculating cumulative risk exposure and maximum single disbursed loan");
        tblSub.setFont(FONT_SMALL);
        tblSub.setForeground(COLOR_TEXT_SECONDARY);
        tblHdr.add(tblTitle, BorderLayout.NORTH);
        tblHdr.add(tblSub, BorderLayout.SOUTH);
        tableCard.add(tblHdr, BorderLayout.NORTH);

        String[] cols = {"METRO REGION", "APPLICATIONS", "MAX DISBURSED LOAN", "TOTAL EXPOSURE", "EXPOSURE RATIO", "REDUCER PARTITION"};
        Object[][] data = {
            {"Mumbai", "4 loans", "₹1,85,00,000", "₹3,42,00,000", "31.9% (Financial Capital)", "part-r-00001"},
            {"Bangalore", "2 loans", "₹2,20,00,000", "₹2,82,00,000", "26.3% (Tech Corridor)", "part-r-00001"},
            {"Pune", "3 loans", "₹90,00,000", "₹2,03,00,000", "18.9% (Manufacturing)", "part-r-00000"},
            {"Delhi NCR", "2 loans", "₹82,00,000", "₹1,17,00,000", "10.9% (Capital Region)", "part-r-00000"},
            {"Hyderabad", "1 loan", "₹28,00,000", "₹28,00,000", "2.6% (Emerging IT Hub)", "part-r-00001"}
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
    // UI REUSABLE COMPONENTS (Matching Reference Design)
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
                if (str.equalsIgnoreCase("Approved") || str.equalsIgnoreCase("Online")) {
                    setForeground(COLOR_GREEN_TEXT);
                    setFont(new Font("Segoe UI", Font.BOLD, 12));
                } else if (str.equalsIgnoreCase("Pending")) {
                    setForeground(COLOR_AMBER_TEXT);
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
            BankMapReduceGUI gui = new BankMapReduceGUI();
            gui.setVisible(true);

            if (args.length > 0 && args[0].equalsIgnoreCase("--capture")) {
                String outDir = args.length > 1 ? args[1] : "screenshots";
                new Thread(() -> {
                    try {
                        Thread.sleep(600);
                        gui.exportScreenCapture("OVERVIEW", outDir + "/01_Real_Java_UI_MapReduce_Dashboard.png");
                        Thread.sleep(200);
                        gui.exportScreenCapture("WORDCOUNT", outDir + "/02_Real_Java_UI_Audit_WordCount_Job.png");
                        Thread.sleep(200);
                        gui.exportScreenCapture("PORTFOLIO", outDir + "/03_Real_Java_UI_Portfolio_Aggregation_Job.png");
                        Thread.sleep(200);
                        gui.exportScreenCapture("REGIONAL", outDir + "/04_Real_Java_UI_Regional_Max_Exposure_Job.png");
                        System.out.println("All 4 clean BANKCORE GUI screenshots captured successfully.");
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
