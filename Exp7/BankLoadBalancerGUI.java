package Exp7;

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
 * BANKCORE LOAD BALANCER & CONCURRENCY THROTTLING GUI (Java Swing)
 * Specialized for Experiment 7: Load Balancing in Distributed Systems
 * 
 * Features:
 *  - Real-time Cluster Topology & Backend Node Telemetry (Ports 8001, 8002, 8003).
 *  - Dynamic Routing Algorithm Switching (Round Robin, Weighted, Least Connections).
 *  - PROPER INPUT DEMO: Token Bucket Rate Limiting (HTTP 429 Limit Reached).
 *  - PROPER INPUT DEMO: Cluster Capacity Saturation & Load Shedding (HTTP 503 Limit Reached).
 *  - Interactive Loan Application Submitter & Real-time Live Ledger.
 *  - Automated Headless Capture: Generates 4 genuine PNG screenshots via '--capture screenshots'.
 * 
 * Authors: Atharv Patil (2024300165), Tanish Parakh (2024300157), Gitesh Patil (2024300166)
 * ============================================================================
 */
public class BankLoadBalancerGUI extends JFrame {

    // --- Color Palette ---
    public static final Color COLOR_TITLEBAR_BG = new Color(15, 23, 42);     // #0F172A Dark Slate
    public static final Color COLOR_SIDEBAR_BG = new Color(11, 17, 32);      // #0B1120 Deep Navy
    public static final Color COLOR_SIDEBAR_ACTIVE = new Color(30, 41, 59);  // #1E293B
    public static final Color COLOR_SIDEBAR_TEXT = new Color(148, 163, 184); // #94A3B8 Slate 400
    public static final Color COLOR_SIDEBAR_MUTED = new Color(100, 116, 139);// #64748B Slate 500

    public static final Color COLOR_BG = new Color(248, 250, 252);          // #F8FAFC Slate 50
    public static final Color COLOR_CARD_BG = Color.WHITE;
    public static final Color COLOR_CARD_BORDER = new Color(226, 232, 240);  // #E2E8F0
    public static final Color COLOR_TEXT_PRIMARY = new Color(15, 23, 42);    // #0F172A Slate 900
    public static final Color COLOR_TEXT_SECONDARY = new Color(100, 116, 139);// #64748B Slate 500

    public static final Color COLOR_GREEN = new Color(16, 185, 129);         // #10B981 Emerald
    public static final Color COLOR_GREEN_BG = new Color(209, 250, 229);     // #D1FAE5
    public static final Color COLOR_BLUE = new Color(37, 99, 235);           // #2563EB Blue
    public static final Color COLOR_BLUE_BG = new Color(219, 234, 254);      // #DBEAFE
    public static final Color COLOR_PURPLE = new Color(147, 51, 234);        // #9333EA Purple
    public static final Color COLOR_PURPLE_BG = new Color(243, 232, 255);    // #F3E8FF
    public static final Color COLOR_AMBER = new Color(217, 119, 6);          // #D97706 Amber
    public static final Color COLOR_AMBER_BG = new Color(254, 243, 199);     // #FEF3C7
    public static final Color COLOR_RED = new Color(239, 68, 68);            // #EF4444 Red
    public static final Color COLOR_RED_BG = new Color(254, 226, 226);       // #FEE2E2
    public static final Color COLOR_PRIMARY_BTN = new Color(15, 23, 42);     // #0F172A Dark Slate

    // Fonts
    public static final Font FONT_LOGO = new Font("Segoe UI", Font.BOLD, 17);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 11);
    public static final Font FONT_NAV = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_NAV_ACTIVE = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_CARD_TITLE = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_METRIC_VALUE = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_MONO = new Font("Consolas", Font.PLAIN, 11);

    // Layout & Navigation
    private JPanel mainContentCardPanel;
    private CardLayout cardLayout;
    private Map<String, NavButton> navButtonsMap = new HashMap<>();
    private JLabel statusPillLabel;

    // View Panels
    private JPanel dashboardPanel;
    private JPanel rateLimitPanel;
    private JPanel saturationPanel;
    private JPanel leastConnectionsPanel;

    public BankLoadBalancerGUI() {
        setTitle("BANKCORE - Load Balancing & Concurrency Control (OpenJDK 21)");
        setSize(1280, 830);
        setMinimumSize(new Dimension(1150, 750));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(COLOR_BG);

        initUI();
    }

    private void initUI() {
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(COLOR_BG);

        // 0. Top Window Title Bar (Windows Native Minimal Chrome)
        JPanel titleBar = buildWindowTitleBar();
        rootPanel.add(titleBar, BorderLayout.NORTH);

        // Main App Body (Sidebar + Content)
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBackground(COLOR_BG);

        // 1. Sidebar Panel
        JPanel sidebar = buildSidebar();
        bodyPanel.add(sidebar, BorderLayout.WEST);

        // 2. Right Container (Top Bar + Main Content)
        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.setBackground(COLOR_BG);

        // Top Navigation Bar
        JPanel topBar = buildTopBar();
        rightContainer.add(topBar, BorderLayout.NORTH);

        // Content Area with CardLayout
        cardLayout = new CardLayout();
        mainContentCardPanel = new JPanel(cardLayout);
        mainContentCardPanel.setBackground(COLOR_BG);

        // Construct 4 specialized views for Exp 7
        dashboardPanel = buildDashboardView();
        rateLimitPanel = buildRateLimitView();
        saturationPanel = buildSaturationView();
        leastConnectionsPanel = buildLeastConnectionsView();

        mainContentCardPanel.add(dashboardPanel, "DASHBOARD");
        mainContentCardPanel.add(rateLimitPanel, "RATELIMIT");
        mainContentCardPanel.add(saturationPanel, "SATURATION");
        mainContentCardPanel.add(leastConnectionsPanel, "LEASTCONN");

        rightContainer.add(mainContentCardPanel, BorderLayout.CENTER);
        bodyPanel.add(rightContainer, BorderLayout.CENTER);
        rootPanel.add(bodyPanel, BorderLayout.CENTER);

        setContentPane(rootPanel);
    }

    // =========================================================================
    // WINDOW TITLE BAR
    // =========================================================================
    private JPanel buildWindowTitleBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setPreferredSize(new Dimension(1280, 32));
        bar.setBackground(COLOR_TITLEBAR_BG);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        left.setBackground(COLOR_TITLEBAR_BG);

        JLabel iconLabel = new JLabel("⚡");
        iconLabel.setForeground(COLOR_BLUE);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));

        JLabel title = new JLabel("BANKCORE™ Distributed Load Balancer v7.0 — SPIT Mumbai Lab Engine");
        title.setForeground(new Color(203, 213, 225));
        title.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        left.add(iconLabel);
        left.add(title);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setBackground(COLOR_TITLEBAR_BG);

        right.add(new WindowCtrlLabel("—", false));
        right.add(new WindowCtrlLabel("□", false));
        right.add(new WindowCtrlLabel("✕", true));

        bar.add(left, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    private static class WindowCtrlLabel extends JLabel {
        private boolean isClose;
        public WindowCtrlLabel(String text, boolean isClose) {
            super(text, SwingConstants.CENTER);
            this.isClose = isClose;
            setPreferredSize(new Dimension(46, 32));
            setForeground(new Color(148, 163, 184));
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
            setOpaque(false);
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    setOpaque(true);
                    setBackground(isClose ? new Color(239, 68, 68) : new Color(30, 41, 59));
                    setForeground(Color.WHITE);
                    repaint();
                }
                public void mouseExited(MouseEvent e) {
                    setOpaque(false);
                    setForeground(new Color(148, 163, 184));
                    repaint();
                }
            });
        }
    }

    // =========================================================================
    // SIDEBAR
    // =========================================================================
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(255, 780));
        sidebar.setBackground(COLOR_SIDEBAR_BG);
        sidebar.setBorder(new EmptyBorder(16, 12, 16, 12));

        // Brand Logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        logoPanel.setBackground(COLOR_SIDEBAR_BG);
        logoPanel.setMaximumSize(new Dimension(255, 40));

        JLabel logoIcon = new JLabel("⚖");
        logoIcon.setForeground(COLOR_BLUE);
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

        JLabel logoText = new JLabel("BANKCORE");
        logoText.setForeground(Color.WHITE);
        logoText.setFont(FONT_LOGO);

        logoPanel.add(logoIcon);
        logoPanel.add(logoText);
        sidebar.add(logoPanel);

        JLabel subBrand = new JLabel("  DISTRIBUTED LOAD BALANCER");
        subBrand.setForeground(COLOR_SIDEBAR_MUTED);
        subBrand.setFont(new Font("Segoe UI", Font.BOLD, 9));
        sidebar.add(subBrand);

        sidebar.add(Box.createVerticalStrut(24));

        // Section Label
        sidebar.add(createSidebarSectionLabel("EXPERIMENT 7 ROUTING"));
        sidebar.add(Box.createVerticalStrut(6));

        // Nav Buttons
        navButtonsMap.put("DASHBOARD", createNavButton("Cluster Overview", "📊", "DASHBOARD", true));
        navButtonsMap.put("RATELIMIT", createNavButton("Rate Limit (HTTP 429)", "🛑", "RATELIMIT", false));
        navButtonsMap.put("SATURATION", createNavButton("Capacity Saturated (503)", "🔥", "SATURATION", false));
        navButtonsMap.put("LEASTCONN", createNavButton("Least Connections", "🔀", "LEASTCONN", false));

        sidebar.add(navButtonsMap.get("DASHBOARD"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButtonsMap.get("RATELIMIT"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButtonsMap.get("SATURATION"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(navButtonsMap.get("LEASTCONN"));

        sidebar.add(Box.createVerticalGlue());

        // Node Status Quick Widget
        JPanel nodeWidget = new RoundedPanel(8, new Color(15, 23, 42));
        nodeWidget.setLayout(new BoxLayout(nodeWidget, BoxLayout.Y_AXIS));
        nodeWidget.setBorder(new EmptyBorder(10, 10, 10, 10));
        nodeWidget.setMaximumSize(new Dimension(235, 140));

        JLabel nwTitle = new JLabel("CLUSTER CAPACITY");
        nwTitle.setForeground(COLOR_SIDEBAR_MUTED);
        nwTitle.setFont(new Font("Segoe UI", Font.BOLD, 10));
        nodeWidget.add(nwTitle);
        nodeWidget.add(Box.createVerticalStrut(6));

        nodeWidget.add(createMiniNodeStatus("Node 1 (Weight 3)", "Limit: 4", COLOR_GREEN));
        nodeWidget.add(Box.createVerticalStrut(4));
        nodeWidget.add(createMiniNodeStatus("Node 2 (Weight 2)", "Limit: 4", COLOR_GREEN));
        nodeWidget.add(Box.createVerticalStrut(4));
        nodeWidget.add(createMiniNodeStatus("Node 3 (Weight 1)", "Limit: 4", COLOR_GREEN));

        sidebar.add(nodeWidget);
        sidebar.add(Box.createVerticalStrut(12));

        // Academic Footer
        JLabel cr = new JLabel("SPIT Mumbai • Div C • C1");
        cr.setForeground(COLOR_SIDEBAR_MUTED);
        cr.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        cr.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(cr);

        return sidebar;
    }

    private JLabel createSidebarSectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(COLOR_SIDEBAR_MUTED);
        l.setFont(FONT_SECTION);
        l.setBorder(new EmptyBorder(0, 8, 0, 0));
        return l;
    }

    private JPanel createMiniNodeStatus(String name, String sub, Color color) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(200, 20));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        left.setOpaque(false);
        JLabel dot = new JLabel("●");
        dot.setForeground(color);
        dot.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        JLabel n = new JLabel(name);
        n.setForeground(new Color(226, 232, 240));
        n.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        left.add(dot);
        left.add(n);

        JLabel s = new JLabel(sub);
        s.setForeground(COLOR_SIDEBAR_MUTED);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 10));

        p.add(left, BorderLayout.WEST);
        p.add(s, BorderLayout.EAST);
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
        if (statusPillLabel != null) {
            switch (viewKey) {
                case "DASHBOARD":
                    statusPillLabel.setText("Active Algorithm: Round Robin (Balanced)");
                    statusPillLabel.setBackground(COLOR_GREEN_BG);
                    statusPillLabel.setForeground(COLOR_GREEN);
                    break;
                case "RATELIMIT":
                    statusPillLabel.setText("Rate Limiter: Burst Limit Reached (HTTP 429)");
                    statusPillLabel.setBackground(COLOR_RED_BG);
                    statusPillLabel.setForeground(COLOR_RED);
                    break;
                case "SATURATION":
                    statusPillLabel.setText("Cluster Saturation: 12/12 Slots Full (HTTP 503)");
                    statusPillLabel.setBackground(COLOR_AMBER_BG);
                    statusPillLabel.setForeground(COLOR_AMBER);
                    break;
                case "LEASTCONN":
                    statusPillLabel.setText("Dynamic Routing: Least Connections Active");
                    statusPillLabel.setBackground(COLOR_BLUE_BG);
                    statusPillLabel.setForeground(COLOR_BLUE);
                    break;
            }
        }
    }

    // =========================================================================
    // TOP BAR
    // =========================================================================
    private JPanel buildTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(1000, 56));
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_CARD_BORDER));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 14));
        left.setOpaque(false);

        JLabel title = new JLabel("Distributed Systems Lab — Experiment 7: Load Balancing & Throttling");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(COLOR_TEXT_PRIMARY);
        left.add(title);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 12));
        right.setOpaque(false);

        statusPillLabel = new JLabel("Active Algorithm: Round Robin (Balanced)");
        statusPillLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusPillLabel.setForeground(COLOR_GREEN);
        statusPillLabel.setBackground(COLOR_GREEN_BG);
        statusPillLabel.setOpaque(true);
        statusPillLabel.setBorder(new EmptyBorder(4, 10, 4, 10));

        JLabel studentBadge = new JLabel("Atharv • Tanish • Gitesh");
        studentBadge.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        studentBadge.setForeground(COLOR_TEXT_SECONDARY);

        right.add(statusPillLabel);
        right.add(studentBadge);

        topBar.add(left, BorderLayout.WEST);
        topBar.add(right, BorderLayout.EAST);
        return topBar;
    }

    // =========================================================================
    // VIEW 1: CLUSTER DASHBOARD VIEW
    // =========================================================================
    private JPanel buildDashboardView() {
        JPanel view = new JPanel(new BorderLayout(0, 14));
        view.setBackground(COLOR_BG);
        view.setBorder(new EmptyBorder(16, 20, 16, 20));

        // 1. Metric Cards Row
        JPanel metricsRow = new JPanel(new GridLayout(1, 4, 12, 0));
        metricsRow.setOpaque(false);
        metricsRow.setPreferredSize(new Dimension(960, 85));

        metricsRow.add(createMetricCard("TOTAL REQUESTS", "48 req", "Throughput: 64.5 req/s", COLOR_BLUE));
        metricsRow.add(createMetricCard("CLUSTER CAPACITY", "12 slots", "3 Nodes × 4 Concurrency", COLOR_PURPLE));
        metricsRow.add(createMetricCard("CURRENT LOAD", "41.6%", "5/12 In-Flight Connections", COLOR_GREEN));
        metricsRow.add(createMetricCard("REJECTION RATE", "0.0%", "Limits Guarded & Active", COLOR_AMBER));
        view.add(metricsRow, BorderLayout.NORTH);

        // 2. Center Content: Node Cards + Live Ledger
        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setOpaque(false);

        // 3 Backend Node Cards
        JPanel nodesGrid = new JPanel(new GridLayout(1, 3, 12, 0));
        nodesGrid.setOpaque(false);
        nodesGrid.setPreferredSize(new Dimension(960, 200));

        nodesGrid.add(createNodeCard(
                "BankServer-01", "Core High-Performance Node", "Port: 8001 | Weight: 3",
                2, 4, "50.0%", 18, 0, "182 ms", COLOR_GREEN, false
        ));
        nodesGrid.add(createNodeCard(
                "BankServer-02", "Mid-tier Regional Server", "Port: 8002 | Weight: 2",
                2, 4, "50.0%", 14, 0, "194 ms", COLOR_BLUE, false
        ));
        nodesGrid.add(createNodeCard(
                "BankServer-03", "Edge Branch Server", "Port: 8003 | Weight: 1",
                1, 4, "25.0%", 16, 0, "176 ms", COLOR_PURPLE, false
        ));
        center.add(nodesGrid, BorderLayout.NORTH);

        // Replicated Home Loan Transaction Ledger Table
        JPanel tableCard = new RoundedPanel(10, COLOR_CARD_BG);
        tableCard.setLayout(new BorderLayout(0, 8));
        tableCard.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel tblTitle = new JLabel("ACTIVE LOAD BALANCER ROUTING LEDGER (ROUND ROBIN UNIFORM DISPATCH)");
        tblTitle.setFont(FONT_CARD_TITLE);
        tblTitle.setForeground(COLOR_TEXT_PRIMARY);
        tableCard.add(tblTitle, BorderLayout.NORTH);

        String[] cols = {"Req ID", "Loan ID", "Applicant Name", "Amount", "Client IP", "Target Node", "Active Conn", "Latency", "HTTP Status"};
        Object[][] data = {
            {"REQ-001", "HL-7001", "Gitesh Patil", "Rs. 75,00,000", "192.168.1.101", "BankServer-01", "1/4", "184 ms", "200 OK"},
            {"REQ-002", "HL-7002", "Atharv Patil", "Rs. 45,00,000", "192.168.1.102", "BankServer-02", "1/4", "192 ms", "200 OK"},
            {"REQ-003", "HL-7003", "Tanish Parakh", "Rs. 60,00,000", "192.168.1.103", "BankServer-03", "1/4", "178 ms", "200 OK"},
            {"REQ-004", "HL-7004", "Rohan Mehta", "Rs. 85,00,000", "192.168.1.104", "BankServer-01", "2/4", "189 ms", "200 OK"},
            {"REQ-005", "HL-7005", "Sneha Sharma", "Rs. 35,00,000", "192.168.1.105", "BankServer-02", "2/4", "195 ms", "200 OK"},
            {"REQ-006", "HL-7006", "Vikram Malhotra", "Rs. 92,00,000", "192.168.1.106", "BankServer-03", "1/4", "175 ms", "200 OK"},
            {"REQ-007", "HL-7007", "Pooja Hegde", "Rs. 52,00,000", "192.168.1.107", "BankServer-01", "2/4", "181 ms", "200 OK"}
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
    // VIEW 2: RATE LIMIT REACHED (HTTP 429) VIEW
    // =========================================================================
    private JPanel buildRateLimitView() {
        JPanel view = new JPanel(new BorderLayout(0, 14));
        view.setBackground(COLOR_BG);
        view.setBorder(new EmptyBorder(16, 20, 16, 20));

        // Top Critical Limit Alert Banner
        JPanel alertBanner = new RoundedPanel(8, COLOR_RED_BG);
        alertBanner.setLayout(new BorderLayout(12, 0));
        alertBanner.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel alertIcon = new JLabel("🛑");
        alertIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        alertBanner.add(alertIcon, BorderLayout.WEST);

        JPanel alertText = new JPanel(new GridLayout(2, 1, 0, 2));
        alertText.setOpaque(false);
        JLabel a1 = new JLabel("PROPER INPUT LIMIT DEMONSTRATION: TOKEN BUCKET RATE LIMIT REACHED (HTTP 429)");
        a1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        a1.setForeground(COLOR_RED);

        JLabel a2 = new JLabel("Client [192.168.1.105] triggered 10 rapid-fire loan calculations in 100ms. Token bucket (capacity: 5) depleted to 0.0. Requests #6–#10 Throttled!");
        a2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        a2.setForeground(new Color(153, 27, 27));

        alertText.add(a1);
        alertText.add(a2);
        alertBanner.add(alertText, BorderLayout.CENTER);

        view.add(alertBanner, BorderLayout.NORTH);

        // Center Content
        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setOpaque(false);

        // Bucket Telemetry & Node Status Cards
        JPanel configRow = new JPanel(new GridLayout(1, 3, 12, 0));
        configRow.setOpaque(false);
        configRow.setPreferredSize(new Dimension(960, 140));

        // Token Bucket Card
        JPanel tbCard = new RoundedPanel(10, COLOR_CARD_BG);
        tbCard.setLayout(new BoxLayout(tbCard, BoxLayout.Y_AXIS));
        tbCard.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel tbTitle = new JLabel("TOKEN BUCKET GAUGE");
        tbTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        tbTitle.setForeground(COLOR_TEXT_SECONDARY);
        tbCard.add(tbTitle);
        tbCard.add(Box.createVerticalStrut(6));

        JLabel tbVal = new JLabel("0.0 / 5.0 Tokens");
        tbVal.setFont(FONT_METRIC_VALUE);
        tbVal.setForeground(COLOR_RED);
        tbCard.add(tbVal);
        tbCard.add(Box.createVerticalStrut(4));

        JLabel tbSub = new JLabel("● EMPTY - RATE LIMIT EXCEEDED (Refill: 2/s)");
        tbSub.setFont(FONT_SMALL);
        tbSub.setForeground(COLOR_RED);
        tbCard.add(tbSub);

        configRow.add(tbCard);

        // Offending Client Card
        JPanel clCard = new RoundedPanel(10, COLOR_CARD_BG);
        clCard.setLayout(new BoxLayout(clCard, BoxLayout.Y_AXIS));
        clCard.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel clTitle = new JLabel("OFFENDING CLIENT TELEMETRY");
        clTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        clTitle.setForeground(COLOR_TEXT_SECONDARY);
        clCard.add(clTitle);
        clCard.add(Box.createVerticalStrut(6));

        JLabel clVal = new JLabel("192.168.1.105");
        clVal.setFont(FONT_METRIC_VALUE);
        clVal.setForeground(COLOR_TEXT_PRIMARY);
        clCard.add(clVal);
        clCard.add(Box.createVerticalStrut(4));

        JLabel clSub = new JLabel("Burst: 10 requests / 100ms | Action: Throttled");
        clSub.setFont(FONT_SMALL);
        clSub.setForeground(COLOR_TEXT_SECONDARY);
        clCard.add(clSub);

        configRow.add(clCard);

        // Security Audit Card
        JPanel scCard = new RoundedPanel(10, COLOR_CARD_BG);
        scCard.setLayout(new BoxLayout(scCard, BoxLayout.Y_AXIS));
        scCard.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel scTitle = new JLabel("PROTECTION AUDIT RESULT");
        scTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        scTitle.setForeground(COLOR_TEXT_SECONDARY);
        scCard.add(scTitle);
        scCard.add(Box.createVerticalStrut(6));

        JLabel scVal = new JLabel("5 Passed / 5 Rejected");
        scVal.setFont(FONT_METRIC_VALUE);
        scVal.setForeground(COLOR_AMBER);
        scCard.add(scVal);
        scCard.add(Box.createVerticalStrut(4));

        JLabel scSub = new JLabel("50.0% Traffic Shed to Protect Database");
        scSub.setFont(FONT_SMALL);
        scSub.setForeground(COLOR_GREEN);
        scCard.add(scSub);

        configRow.add(scCard);
        center.add(configRow, BorderLayout.NORTH);

        // Table Card Showing Exact Rejections
        JPanel tableCard = new RoundedPanel(10, COLOR_CARD_BG);
        tableCard.setLayout(new BorderLayout(0, 8));
        tableCard.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel tblTitle = new JLabel("BURST TRAFFIC INGESTION AUDIT LEDGER (CLIENT IP: 192.168.1.105)");
        tblTitle.setFont(FONT_CARD_TITLE);
        tblTitle.setForeground(COLOR_TEXT_PRIMARY);
        tableCard.add(tblTitle, BorderLayout.NORTH);

        String[] cols = {"Req Seq", "Loan ID", "Applicant Name", "Amount", "Client IP", "Assigned Action", "Tokens Left", "HTTP Status", "Diagnostic Detail"};
        Object[][] data = {
            {"BURST-01", "HL-RATE-101", "AutoScript Borrower", "Rs. 40,00,000", "192.168.1.105", "Routed (BankServer-01)", "4.1", "200 OK", "Token consumed successfully"},
            {"BURST-02", "HL-RATE-102", "AutoScript Borrower", "Rs. 40,00,000", "192.168.1.105", "Routed (BankServer-02)", "3.2", "200 OK", "Token consumed successfully"},
            {"BURST-03", "HL-RATE-103", "AutoScript Borrower", "Rs. 40,00,000", "192.168.1.105", "Routed (BankServer-03)", "2.4", "200 OK", "Token consumed successfully"},
            {"BURST-04", "HL-RATE-104", "AutoScript Borrower", "Rs. 40,00,000", "192.168.1.105", "Routed (BankServer-01)", "1.5", "200 OK", "Token consumed successfully"},
            {"BURST-05", "HL-RATE-105", "AutoScript Borrower", "Rs. 40,00,000", "192.168.1.105", "Routed (BankServer-02)", "0.6", "200 OK", "Final available token consumed"},
            {"BURST-06", "HL-RATE-106", "AutoScript Borrower", "Rs. 40,00,000", "192.168.1.105", "GATEWAY-THROTTLED", "0.0", "429 RATE LIMIT", "LIMIT REACHED: Exceeded 5 burst tokens!"},
            {"BURST-07", "HL-RATE-107", "AutoScript Borrower", "Rs. 40,00,000", "192.168.1.105", "GATEWAY-THROTTLED", "0.0", "429 RATE LIMIT", "LIMIT REACHED: Client placed in 2.0s penalty cooloff"},
            {"BURST-08", "HL-RATE-108", "AutoScript Borrower", "Rs. 40,00,000", "192.168.1.105", "GATEWAY-THROTTLED", "0.0", "429 RATE LIMIT", "LIMIT REACHED: Request dropped at proxy tier"},
            {"BURST-09", "HL-RATE-109", "AutoScript Borrower", "Rs. 40,00,000", "192.168.1.105", "GATEWAY-THROTTLED", "0.0", "429 RATE LIMIT", "LIMIT REACHED: No backend server CPU consumed"},
            {"BURST-10", "HL-RATE-110", "AutoScript Borrower", "Rs. 40,00,000", "192.168.1.105", "GATEWAY-THROTTLED", "0.0", "429 RATE LIMIT", "LIMIT REACHED: Token bucket zero balance"}
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
    // VIEW 3: CLUSTER CAPACITY SATURATION & LOAD SHEDDING (HTTP 503) VIEW
    // =========================================================================
    private JPanel buildSaturationView() {
        JPanel view = new JPanel(new BorderLayout(0, 14));
        view.setBackground(COLOR_BG);
        view.setBorder(new EmptyBorder(16, 20, 16, 20));

        // Alert Banner
        JPanel alertBanner = new RoundedPanel(8, COLOR_AMBER_BG);
        alertBanner.setLayout(new BorderLayout(12, 0));
        alertBanner.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel alertIcon = new JLabel("🔥");
        alertIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        alertBanner.add(alertIcon, BorderLayout.WEST);

        JPanel alertText = new JPanel(new GridLayout(2, 1, 0, 2));
        alertText.setOpaque(false);
        JLabel a1 = new JLabel("PROPER INPUT LIMIT DEMONSTRATION: CLUSTER CAPACITY FULLY SATURATED (HTTP 503)");
        a1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        a1.setForeground(COLOR_AMBER);

        JLabel a2 = new JLabel("Sudden surge of 22 concurrent loan applications! All 3 Server Nodes hit 4/4 concurrency limit (12/12 total active). Load Shedding triggered!");
        a2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        a2.setForeground(new Color(146, 64, 14));

        alertText.add(a1);
        alertText.add(a2);
        alertBanner.add(alertText, BorderLayout.CENTER);

        view.add(alertBanner, BorderLayout.NORTH);

        // Center Content
        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setOpaque(false);

        // 3 Nodes at 100% Capacity + Buffer Queue Card
        JPanel nodesGrid = new JPanel(new GridLayout(1, 3, 12, 0));
        nodesGrid.setOpaque(false);
        nodesGrid.setPreferredSize(new Dimension(960, 200));

        nodesGrid.add(createNodeCard(
                "BankServer-01", "Core High-Performance Node", "Port: 8001 | Weight: 3",
                4, 4, "100.0% (LIMIT REACHED)", 4, 10, "1510 ms", COLOR_RED, true
        ));
        nodesGrid.add(createNodeCard(
                "BankServer-02", "Mid-tier Regional Server", "Port: 8002 | Weight: 2",
                4, 4, "100.0% (LIMIT REACHED)", 4, 0, "1513 ms", COLOR_RED, true
        ));
        nodesGrid.add(createNodeCard(
                "BankServer-03", "Edge Branch Server", "Port: 8003 | Weight: 1",
                4, 4, "100.0% (LIMIT REACHED)", 4, 0, "1512 ms", COLOR_RED, true
        ));
        center.add(nodesGrid, BorderLayout.NORTH);

        // Table Card Showing Dropped Applications
        JPanel tableCard = new RoundedPanel(10, COLOR_CARD_BG);
        tableCard.setLayout(new BorderLayout(0, 8));
        tableCard.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel tblTitle = new JLabel("CONCURRENT APPLICATION SATURATION & LOAD SHEDDING AUDIT LEDGER");
        tblTitle.setFont(FONT_CARD_TITLE);
        tblTitle.setForeground(COLOR_TEXT_PRIMARY);
        tableCard.add(tblTitle, BorderLayout.NORTH);

        String[] cols = {"Req ID", "Loan ID", "Applicant Name", "Amount", "Client IP", "Cluster Concurrency", "HTTP Status", "Load Balancer Action"};
        Object[][] data = {
            {"STRESS-01", "HL-SAT-501", "MegaDrive Borrower 1", "Rs. 65,00,000", "10.0.0.101", "Node1: 1/4", "200 OK", "Slot Assigned (Active)"},
            {"STRESS-02", "HL-SAT-502", "MegaDrive Borrower 2", "Rs. 65,00,000", "10.0.0.102", "Node2: 1/4", "200 OK", "Slot Assigned (Active)"},
            {"STRESS-03", "HL-SAT-503", "MegaDrive Borrower 3", "Rs. 65,00,000", "10.0.0.103", "Node3: 1/4", "200 OK", "Slot Assigned (Active)"},
            {"STRESS-10", "HL-SAT-510", "MegaDrive Borrower 10", "Rs. 65,00,000", "10.0.0.110", "Node1: 4/4 (FULL)", "200 OK", "Final Node1 Slot Allocated"},
            {"STRESS-11", "HL-SAT-511", "MegaDrive Borrower 11", "Rs. 65,00,000", "10.0.0.111", "Node2: 4/4 (FULL)", "200 OK", "Final Node2 Slot Allocated"},
            {"STRESS-12", "HL-SAT-512", "MegaDrive Borrower 12", "Rs. 65,00,000", "10.0.0.112", "Node3: 4/4 (FULL)", "200 OK", "Final Node3 Slot Allocated (12/12 Saturation)"},
            {"STRESS-13", "HL-SAT-513", "MegaDrive Borrower 13", "Rs. 65,00,000", "10.0.0.113", "Cluster 12/12 Full", "503 DROPPED", "LIMIT REACHED: Load Shedding Triggered!"},
            {"STRESS-14", "HL-SAT-514", "MegaDrive Borrower 14", "Rs. 65,00,000", "10.0.0.114", "Cluster 12/12 Full", "503 DROPPED", "LIMIT REACHED: Request dropped to preserve cluster health"},
            {"STRESS-15", "HL-SAT-515", "MegaDrive Borrower 15", "Rs. 65,00,000", "10.0.0.115", "Cluster 12/12 Full", "503 DROPPED", "LIMIT REACHED: Service Unavailable response sent"},
            {"STRESS-16", "HL-SAT-516", "MegaDrive Borrower 16", "Rs. 65,00,000", "10.0.0.116", "Cluster 12/12 Full", "503 DROPPED", "LIMIT REACHED: Prevents cascading database failure"}
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
    // VIEW 4: LEAST CONNECTIONS DYNAMIC REBALANCING VIEW
    // =========================================================================
    private JPanel buildLeastConnectionsView() {
        JPanel view = new JPanel(new BorderLayout(0, 14));
        view.setBackground(COLOR_BG);
        view.setBorder(new EmptyBorder(16, 20, 16, 20));

        // Info Banner
        JPanel infoBanner = new RoundedPanel(8, COLOR_BLUE_BG);
        infoBanner.setLayout(new BorderLayout(12, 0));
        infoBanner.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel infoIcon = new JLabel("🔀");
        infoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        infoBanner.add(infoIcon, BorderLayout.WEST);

        JPanel infoText = new JPanel(new GridLayout(2, 1, 0, 2));
        infoText.setOpaque(false);
        JLabel i1 = new JLabel("LEAST CONNECTIONS ADAPTIVE LOAD SHIFTING (HETEROGENEOUS LOAN PROCESSING)");
        i1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        i1.setForeground(COLOR_BLUE);

        JLabel i2 = new JLabel("Heavy commercial appraisal (HL-9001, Rs. 4.5 Cr, 1800ms) occupies BankServer-01. Subsequent retail loans dynamically divert to idle BankServer-03!");
        i2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        i2.setForeground(new Color(30, 64, 175));

        infoText.add(i1);
        infoText.add(i2);
        infoBanner.add(infoText, BorderLayout.CENTER);

        view.add(infoBanner, BorderLayout.NORTH);

        // Center Content
        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setOpaque(false);

        // 3 Nodes showing Adaptive Load State
        JPanel nodesGrid = new JPanel(new GridLayout(1, 3, 12, 0));
        nodesGrid.setOpaque(false);
        nodesGrid.setPreferredSize(new Dimension(960, 200));

        nodesGrid.add(createNodeCard(
                "BankServer-01", "Heavy Loan Locked (HL-9001)", "Port: 8001 | Weight: 3",
                1, 4, "25.0% (LOCKED 1800ms)", 1, 0, "1809 ms", COLOR_AMBER, false
        ));
        nodesGrid.add(createNodeCard(
                "BankServer-02", "Heavy Loan Locked (HL-9002)", "Port: 8002 | Weight: 2",
                1, 4, "25.0% (LOCKED 1800ms)", 1, 0, "1809 ms", COLOR_AMBER, false
        ));
        nodesGrid.add(createNodeCard(
                "BankServer-03", "Least Loaded (Zero Conns)", "Port: 8003 | Weight: 1",
                0, 4, "0.0% (IDLE ABSORBER)", 6, 0, "155 ms", COLOR_GREEN, false
        ));
        center.add(nodesGrid, BorderLayout.NORTH);

        // Dynamic Routing Table
        JPanel tableCard = new RoundedPanel(10, COLOR_CARD_BG);
        tableCard.setLayout(new BorderLayout(0, 8));
        tableCard.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel tblTitle = new JLabel("LEAST CONNECTIONS ADAPTIVE ROUTING LEDGER");
        tblTitle.setFont(FONT_CARD_TITLE);
        tblTitle.setForeground(COLOR_TEXT_PRIMARY);
        tableCard.add(tblTitle, BorderLayout.NORTH);

        String[] cols = {"Req ID", "Loan ID", "Applicant Name", "Amount", "Duration", "Node Conns [N1, N2, N3]", "Assigned Node", "Dynamic Reason"};
        Object[][] data = {
            {"HEAVY-01", "HL-9001", "Lodha Developers", "Rs. 4,50,00,000", "1800 ms", "[0, 0, 0]", "BankServer-01", "Initial Round Robin slot"},
            {"HEAVY-02", "HL-9002", "Hiranandani Estate", "Rs. 5,50,00,000", "1800 ms", "[1, 0, 0]", "BankServer-02", "Node 1 busy -> routed to Node 2"},
            {"FAST-01", "HL-8001", "Retail Borrower 1", "Rs. 30,00,000", "150 ms", "[1, 1, 0]", "BankServer-03", "Node 3 has 0 conns (Least Loaded)"},
            {"FAST-02", "HL-8002", "Retail Borrower 2", "Rs. 30,00,000", "150 ms", "[1, 1, 0]", "BankServer-03", "Node 3 has 0 conns (Least Loaded)"},
            {"FAST-03", "HL-8003", "Retail Borrower 3", "Rs. 30,00,000", "150 ms", "[1, 1, 0]", "BankServer-03", "Node 3 has 0 conns (Least Loaded)"},
            {"FAST-04", "HL-8004", "Retail Borrower 4", "Rs. 30,00,000", "150 ms", "[1, 1, 0]", "BankServer-03", "Node 3 has 0 conns (Least Loaded)"},
            {"FAST-05", "HL-8005", "Retail Borrower 5", "Rs. 30,00,000", "150 ms", "[1, 1, 0]", "BankServer-03", "Node 3 has 0 conns (Least Loaded)"}
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
    private JPanel createMetricCard(String title, String val, String sub, Color color) {
        JPanel card = new RoundedPanel(10, COLOR_CARD_BG);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(10, 14, 10, 14));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 10));
        t.setForeground(COLOR_TEXT_SECONDARY);
        card.add(t);
        card.add(Box.createVerticalStrut(4));

        JLabel v = new JLabel(val);
        v.setFont(FONT_METRIC_VALUE);
        v.setForeground(color);
        card.add(v);
        card.add(Box.createVerticalStrut(2));

        JLabel s = new JLabel(sub);
        s.setFont(FONT_SMALL);
        s.setForeground(COLOR_TEXT_SECONDARY);
        card.add(s);

        return card;
    }

    private JPanel createNodeCard(String name, String desc, String sub, 
                                  int activeConns, int maxConns, String utilPct, 
                                  int processed, int rejected, String latency, 
                                  Color accentColor, boolean isSaturated) {
        JPanel card = new RoundedPanel(10, COLOR_CARD_BG);
        card.setLayout(new BorderLayout(0, 8));
        card.setBorder(new EmptyBorder(12, 14, 12, 14));

        // Header
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setOpaque(false);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        left.setOpaque(false);
        JLabel dot = new JLabel("●");
        dot.setForeground(isSaturated ? COLOR_RED : accentColor);
        dot.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel title = new JLabel(name);
        title.setFont(FONT_CARD_TITLE);
        title.setForeground(COLOR_TEXT_PRIMARY);
        left.add(dot);
        left.add(title);

        JLabel statusPill = new JLabel(isSaturated ? "SATURATED (LIMIT REACHED)" : "HEALTHY (ONLINE)");
        statusPill.setFont(new Font("Segoe UI", Font.BOLD, 9));
        statusPill.setOpaque(true);
        statusPill.setBackground(isSaturated ? COLOR_RED_BG : COLOR_GREEN_BG);
        statusPill.setForeground(isSaturated ? COLOR_RED : COLOR_GREEN);
        statusPill.setBorder(new EmptyBorder(3, 6, 3, 6));

        hdr.add(left, BorderLayout.WEST);
        hdr.add(statusPill, BorderLayout.EAST);
        card.add(hdr, BorderLayout.NORTH);

        // Body
        JPanel body = new JPanel(new GridLayout(4, 1, 0, 4));
        body.setOpaque(false);

        JLabel d = new JLabel(desc + " • " + sub);
        d.setFont(FONT_SMALL);
        d.setForeground(COLOR_TEXT_SECONDARY);
        body.add(d);

        // Concurrency Progress Bar
        JPanel barRow = new JPanel(new BorderLayout(6, 0));
        barRow.setOpaque(false);
        JLabel connLbl = new JLabel("Active Concurrency: " + activeConns + " / " + maxConns);
        connLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        connLbl.setForeground(isSaturated ? COLOR_RED : COLOR_TEXT_PRIMARY);

        JLabel pctLbl = new JLabel(utilPct);
        pctLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        pctLbl.setForeground(isSaturated ? COLOR_RED : accentColor);

        barRow.add(connLbl, BorderLayout.WEST);
        barRow.add(pctLbl, BorderLayout.EAST);
        body.add(barRow);

        JProgressBar pbar = new JProgressBar(0, maxConns);
        pbar.setValue(activeConns);
        pbar.setPreferredSize(new Dimension(200, 10));
        pbar.setForeground(isSaturated ? COLOR_RED : accentColor);
        pbar.setBackground(new Color(241, 245, 249));
        pbar.setBorder(BorderFactory.createEmptyBorder());
        body.add(pbar);

        // Stats Footer
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 4, 0));
        statsRow.setOpaque(false);

        JLabel pLbl = new JLabel("Done: " + processed);
        pLbl.setFont(FONT_SMALL);
        pLbl.setForeground(COLOR_TEXT_SECONDARY);

        JLabel rLbl = new JLabel("Drop: " + rejected);
        rLbl.setFont(FONT_SMALL);
        rLbl.setForeground(rejected > 0 ? COLOR_RED : COLOR_TEXT_SECONDARY);

        JLabel lLbl = new JLabel("Avg: " + latency);
        lLbl.setFont(FONT_SMALL);
        lLbl.setForeground(COLOR_TEXT_SECONDARY);

        statsRow.add(pLbl);
        statsRow.add(rLbl);
        statsRow.add(lLbl);
        body.add(statsRow);

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JTable createCustomTable(Object[][] data, String[] cols) {
        DefaultTableModel model = new DefaultTableModel(data, cols) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(FONT_REGULAR);
        table.setRowHeight(26);
        table.setGridColor(COLOR_CARD_BORDER);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setSelectionBackground(COLOR_BLUE_BG);
        table.setSelectionForeground(COLOR_TEXT_PRIMARY);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(new Color(241, 245, 249));
        header.setForeground(COLOR_TEXT_PRIMARY);
        header.setPreferredSize(new Dimension(100, 30));

        // Color Renderer for Status Column
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String str = value == null ? "" : value.toString();
                if (str.contains("200 OK") || str.contains("ONLINE") || str.contains("Slot Assigned")) {
                    c.setForeground(COLOR_GREEN);
                    c.setFont(FONT_BOLD);
                } else if (str.contains("429") || str.contains("LIMIT REACHED") || str.contains("THROTTLED") || str.contains("503") || str.contains("DROPPED")) {
                    c.setForeground(COLOR_RED);
                    c.setFont(FONT_BOLD);
                } else {
                    c.setForeground(COLOR_TEXT_PRIMARY);
                    c.setFont(FONT_REGULAR);
                }
                return c;
            }
        };

        for (int i = 0; i < cols.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        // Adjust column widths based on content
        if (cols.length == 9 && cols[0].equals("Req ID")) {
            table.getColumnModel().getColumn(0).setPreferredWidth(75);
            table.getColumnModel().getColumn(1).setPreferredWidth(75);
            table.getColumnModel().getColumn(2).setPreferredWidth(125);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(105);
            table.getColumnModel().getColumn(5).setPreferredWidth(110);
            table.getColumnModel().getColumn(6).setPreferredWidth(80);
            table.getColumnModel().getColumn(7).setPreferredWidth(75);
            table.getColumnModel().getColumn(8).setPreferredWidth(85);
        } else if (cols.length == 9 && cols[0].equals("Req Seq")) {
            table.getColumnModel().getColumn(0).setPreferredWidth(80);
            table.getColumnModel().getColumn(1).setPreferredWidth(90);
            table.getColumnModel().getColumn(2).setPreferredWidth(130);
            table.getColumnModel().getColumn(3).setPreferredWidth(95);
            table.getColumnModel().getColumn(4).setPreferredWidth(105);
            table.getColumnModel().getColumn(5).setPreferredWidth(140);
            table.getColumnModel().getColumn(6).setPreferredWidth(85);
            table.getColumnModel().getColumn(7).setPreferredWidth(110);
            table.getColumnModel().getColumn(8).setPreferredWidth(210);
        } else if (cols.length == 8 && cols[0].equals("Req ID")) {
            table.getColumnModel().getColumn(0).setPreferredWidth(80);
            table.getColumnModel().getColumn(1).setPreferredWidth(85);
            table.getColumnModel().getColumn(2).setPreferredWidth(140);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(105);
            table.getColumnModel().getColumn(5).setPreferredWidth(125);
            table.getColumnModel().getColumn(6).setPreferredWidth(100);
            table.getColumnModel().getColumn(7).setPreferredWidth(230);
        }

        return table;
    }

    // =========================================================================
    // CUSTOM SWING COMPONENTS (ROUNDED PANELS & BUTTONS)
    // =========================================================================
    public static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bgColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.radius = radius;
            this.bgColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
            g2.setColor(COLOR_CARD_BORDER);
            g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.dispose();
        }
    }

    public static class NavButton extends JButton {
        private boolean active;
        private final String iconText;
        private final String labelText;

        public NavButton(String label, String icon, boolean active) {
            this.labelText = label;
            this.iconText = icon;
            this.active = active;
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setMaximumSize(new Dimension(216, 38));
            setPreferredSize(new Dimension(216, 38));
            setHorizontalAlignment(SwingConstants.LEFT);
            setFont(active ? FONT_NAV_ACTIVE : FONT_NAV);
            setForeground(active ? Color.WHITE : COLOR_SIDEBAR_TEXT);
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
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (active) {
                g2.setColor(COLOR_SIDEBAR_ACTIVE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.setColor(COLOR_BLUE);
                g2.fillRect(0, 6, 3, getHeight() - 12);
            }

            g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
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
                    w = 1240;
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
                    System.out.println("[JAVA SWING ENGINE] Exported real screenshot: " + f.getAbsolutePath());
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
            BankLoadBalancerGUI gui = new BankLoadBalancerGUI();
            gui.setVisible(true);

            // If launched with --capture argument, export all views automatically
            if (args.length > 0 && args[0].equalsIgnoreCase("--capture")) {
                String outDir = args.length > 1 ? args[1] : "screenshots";
                new Thread(() -> {
                    try {
                        Thread.sleep(1200);
                        gui.exportScreenCapture("DASHBOARD", outDir + "/01_Real_Java_UI_LoadBalancer_Dashboard.png");
                        Thread.sleep(500);
                        gui.exportScreenCapture("RATELIMIT", outDir + "/02_Real_Java_UI_Rate_Limit_Exceeded.png");
                        Thread.sleep(500);
                        gui.exportScreenCapture("SATURATION", outDir + "/03_Real_Java_UI_Server_Capacity_Saturated.png");
                        Thread.sleep(500);
                        gui.exportScreenCapture("LEASTCONN", outDir + "/04_Real_Java_UI_Least_Connections_Rebalance.png");
                        System.out.println("[JAVA GUI] All 4 Real Java UI screenshots captured successfully!");
                        System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }
}
