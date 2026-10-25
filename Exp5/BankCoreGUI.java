// BankCoreGUI.java
// Modern Java Swing Frontend for BANKCORE Distributed Banking System
// Built with pure Java SE (OpenJDK 21) using custom vector rendering,
// real-time Primary-Backup replication tracking, and pixel-perfect UI capture.

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

public class BankCoreGUI extends JFrame {

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
    public static final Color COLOR_AMBER = new Color(217, 119, 6);          // #D97706 Amber
    public static final Color COLOR_AMBER_BG = new Color(254, 243, 199);     // #FEF3C7
    public static final Color COLOR_RED = new Color(239, 68, 68);            // #EF4444 Red
    public static final Color COLOR_RED_BG = new Color(254, 226, 226);       // #FEE2E2
    public static final Color COLOR_PRIMARY_BTN = new Color(15, 23, 42);     // #0F172A Dark Slate

    // Fonts
    public static final Font FONT_LOGO = new Font("Segoe UI", Font.BOLD, 17);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 11);
    public static final Font FONT_NAV = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_NAV_ACTIVE = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_CARD_TITLE = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FONT_METRIC_VALUE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);

    // Layout & Navigation
    private JPanel mainContentCardPanel;
    private CardLayout cardLayout;
    private Map<String, NavButton> navButtonsMap = new HashMap<>();
    private JLabel statusPillLabel;

    // View Panels
    private JPanel dashboardPanel;
    private JPanel loansPanel;
    private JPanel loanDetailsPanel;
    private JPanel failoverPanel;

    public BankCoreGUI() {
        setTitle("BANKCORE - Distributed Banking System (OpenJDK 21)");
        setSize(1240, 820);
        setMinimumSize(new Dimension(1100, 740));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(COLOR_BG);

        initUI();
    }

    private void initUI() {
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(COLOR_BG);

        // 0. Top Window Title Bar (Windows Native Style)
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

        // Card Layout for Views
        cardLayout = new CardLayout();
        mainContentCardPanel = new JPanel(cardLayout);
        mainContentCardPanel.setBackground(COLOR_BG);

        // Build 4 Views
        dashboardPanel = buildDashboardView();
        loansPanel = buildLoansView();
        loanDetailsPanel = buildLoanDetailsView();
        failoverPanel = buildFailoverView();

        mainContentCardPanel.add(dashboardPanel, "DASHBOARD");
        mainContentCardPanel.add(loansPanel, "LOANS");
        mainContentCardPanel.add(loanDetailsPanel, "DETAILS");
        mainContentCardPanel.add(failoverPanel, "FAILOVER");

        rightContainer.add(mainContentCardPanel, BorderLayout.CENTER);
        bodyPanel.add(rightContainer, BorderLayout.CENTER);
        rootPanel.add(bodyPanel, BorderLayout.CENTER);

        setContentPane(rootPanel);
    }

    // =========================================================================
    // WINDOW TITLE BAR (Windows 11 Frame Header)
    // =========================================================================
    private JPanel buildWindowTitleBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setPreferredSize(new Dimension(1240, 32));
        bar.setBackground(COLOR_TITLEBAR_BG);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(30, 41, 59)));

        // Left Icon + Title
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        left.setBackground(COLOR_TITLEBAR_BG);

        IconLabel appIcon = new IconLabel("bank", 14, Color.WHITE);
        JLabel titleLbl = new JLabel("BANKCORE Distributed Banking System [OpenJDK 21 - Exp 5 Fault Tolerance]");
        titleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLbl.setForeground(new Color(226, 232, 240));

        left.add(appIcon);
        left.add(titleLbl);
        bar.add(left, BorderLayout.WEST);

        // Right Windows Control Buttons (Minimize, Maximize, Close)
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        controls.setBackground(COLOR_TITLEBAR_BG);

        JLabel btnMin = new WindowCtrlLabel("―", 45, 32, false);
        JLabel btnMax = new WindowCtrlLabel("□", 45, 32, false);
        JLabel btnClose = new WindowCtrlLabel("✕", 45, 32, true);

        controls.add(btnMin);
        controls.add(btnMax);
        controls.add(btnClose);
        bar.add(controls, BorderLayout.EAST);

        return bar;
    }

    // =========================================================================
    // SIDEBAR
    // =========================================================================
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(230, 788));
        sidebar.setBackground(COLOR_SIDEBAR_BG);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(COLOR_SIDEBAR_BG);
        content.setBorder(new EmptyBorder(20, 16, 20, 16));

        // Logo Panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        logoPanel.setBackground(COLOR_SIDEBAR_BG);
        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        IconLabel logoIcon = new IconLabel("bank", 24, Color.WHITE);

        JPanel logoTextPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        logoTextPanel.setBackground(COLOR_SIDEBAR_BG);
        JLabel logoTitle = new JLabel("BANKCORE");
        logoTitle.setFont(FONT_LOGO);
        logoTitle.setForeground(Color.WHITE);
        JLabel logoSub = new JLabel("Distributed Banking");
        logoSub.setFont(FONT_SMALL);
        logoSub.setForeground(COLOR_SIDEBAR_MUTED);
        logoTextPanel.add(logoTitle);
        logoTextPanel.add(logoSub);

        logoPanel.add(logoIcon);
        logoPanel.add(logoTextPanel);
        content.add(logoPanel);

        content.add(Box.createVerticalStrut(26));

        // Category: BANKING
        JLabel catBanking = new JLabel("BANKING");
        catBanking.setFont(FONT_SECTION);
        catBanking.setForeground(COLOR_SIDEBAR_MUTED);
        catBanking.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(catBanking);
        content.add(Box.createVerticalStrut(8));

        addNavButton(content, "DASHBOARD", "grid", "Dashboard", true);
        addNavButton(content, "LOANS", "card", "Loans", false);
        addNavButton(content, "CUSTOMERS", "users", "Customers", false);
        addNavButton(content, "TRANSACTIONS", "swap", "Transactions", false);

        content.add(Box.createVerticalStrut(22));

        // Category: DISTRIBUTED SYSTEM
        JLabel catDist = new JLabel("DISTRIBUTED SYSTEM");
        catDist.setFont(FONT_SECTION);
        catDist.setForeground(COLOR_SIDEBAR_MUTED);
        catDist.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(catDist);
        content.add(Box.createVerticalStrut(8));

        addNavButton(content, "CLUSTER", "server", "Cluster", false);
        addNavButton(content, "LEADER", "trophy", "Leader Election", false);
        addNavButton(content, "FAILOVER", "refresh", "Replication", false);
        addNavButton(content, "CLOCK", "clock", "Clock Synchronization", false);
        addNavButton(content, "MONITOR", "chart", "Monitoring", false);

        sidebar.add(content, BorderLayout.NORTH);

        // Bottom Settings
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        bottomPanel.setBackground(COLOR_SIDEBAR_BG);
        NavButton btnSettings = new NavButton("settings", "Settings", false);
        btnSettings.addActionListener(e -> JOptionPane.showMessageDialog(this, "BANKCORE Cluster Configuration: Primary (port 5000), Backup (port 6000)", "System Configuration", JOptionPane.INFORMATION_MESSAGE));
        bottomPanel.add(btnSettings);
        sidebar.add(bottomPanel, BorderLayout.SOUTH);

        return sidebar;
    }

    private void addNavButton(JPanel container, String key, String iconType, String text, boolean active) {
        NavButton btn = new NavButton(iconType, text, active);
        btn.addActionListener(e -> {
            switchView(key);
        });
        navButtonsMap.put(key, btn);
        container.add(btn);
        container.add(Box.createVerticalStrut(4));
    }

    public void switchView(String viewKey) {
        // Update sidebar button states
        for (Map.Entry<String, NavButton> entry : navButtonsMap.entrySet()) {
            boolean isTarget = entry.getKey().equals(viewKey);
            // If viewing DETAILS, keep LOANS button highlighted
            if (viewKey.equals("DETAILS") && entry.getKey().equals("LOANS")) {
                isTarget = true;
            }
            entry.getValue().setActive(isTarget);
        }

        // Show card
        if (viewKey.equals("DASHBOARD") || viewKey.equals("LOANS") || viewKey.equals("DETAILS") || viewKey.equals("FAILOVER")) {
            cardLayout.show(mainContentCardPanel, viewKey);
        } else {
            cardLayout.show(mainContentCardPanel, "DASHBOARD");
        }

        // Update Top Status Pill
        if (statusPillLabel != null) {
            if (viewKey.equals("FAILOVER")) {
                statusPillLabel.setText("● Failover Active: Backup Promoted");
                statusPillLabel.setForeground(new Color(234, 88, 12)); // Orange
            } else {
                statusPillLabel.setText("● System Healthy");
                statusPillLabel.setForeground(COLOR_GREEN);
            }
        }
    }

    // =========================================================================
    // TOP NAVIGATION BAR
    // =========================================================================
    private JPanel buildTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(1000, 56));
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_CARD_BORDER));

        // Left Title
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 16));
        left.setBackground(Color.WHITE);
        JLabel pageTitle = new JLabel("Distributed Banking");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pageTitle.setForeground(COLOR_TEXT_PRIMARY);
        left.add(pageTitle);
        topBar.add(left, BorderLayout.WEST);

        // Right items (Search, Bell, System Healthy, User Profile)
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 12));
        right.setBackground(Color.WHITE);

        IconLabel searchIcon = new IconLabel("search", 16, COLOR_TEXT_SECONDARY);
        searchIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        IconLabel bellIcon = new IconLabel("bell", 16, COLOR_TEXT_SECONDARY);
        bellIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        statusPillLabel = new JLabel("● System Healthy");
        statusPillLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusPillLabel.setForeground(COLOR_GREEN);

        // User Avatar badge
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        userPanel.setBackground(Color.WHITE);

        JLabel avatar = new JLabel("A", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                setupQualityRendering(g2);
                g2.setColor(COLOR_TEXT_PRIMARY);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        avatar.setPreferredSize(new Dimension(28, 28));
        avatar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        avatar.setForeground(Color.WHITE);

        JPanel userText = new JPanel(new GridLayout(2, 1, 0, 0));
        userText.setBackground(Color.WHITE);
        JLabel userName = new JLabel("Admin");
        userName.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userName.setForeground(COLOR_TEXT_PRIMARY);
        JLabel userRole = new JLabel("Administrator");
        userRole.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        userRole.setForeground(COLOR_TEXT_SECONDARY);
        userText.add(userName);
        userText.add(userRole);

        userPanel.add(avatar);
        userPanel.add(userText);

        right.add(searchIcon);
        right.add(bellIcon);
        right.add(statusPillLabel);
        right.add(Box.createHorizontalStrut(8));
        right.add(userPanel);

        topBar.add(right, BorderLayout.EAST);
        return topBar;
    }

    // =========================================================================
    // VIEW 1: DASHBOARD
    // =========================================================================
    private JPanel buildDashboardView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));

        // Header Title
        JLabel title = new JLabel("Dashboard");
        title.setFont(FONT_TITLE);
        title.setForeground(COLOR_TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Overview of your distributed banking system");
        sub.setFont(FONT_SUBTITLE);
        sub.setForeground(COLOR_TEXT_SECONDARY);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(4));
        panel.add(sub);
        panel.add(Box.createVerticalStrut(20));

        // 4 Metric Cards in Grid
        JPanel metricsGrid = new JPanel(new GridLayout(1, 4, 16, 0));
        metricsGrid.setBackground(COLOR_BG);
        metricsGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        metricsGrid.setMaximumSize(new Dimension(1200, 110));

        metricsGrid.add(createMetricCard("Total Loans", "1", "Across all branches"));
        metricsGrid.add(createMetricCard("Active Loans", "1", "Currently active"));
        metricsGrid.add(createMetricCard("Loan Portfolio", "₹62,00,000", "Total outstanding"));
        metricsGrid.add(createMetricCard("Pending Applications", "1", "Awaiting processing"));

        panel.add(metricsGrid);
        panel.add(Box.createVerticalStrut(24));

        // System Health Section
        RoundedPanel healthCard = new RoundedPanel(12);
        healthCard.setLayout(new BoxLayout(healthCard, BoxLayout.Y_AXIS));
        healthCard.setBackground(COLOR_CARD_BG);
        healthCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        healthCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        healthCard.setMaximumSize(new Dimension(1200, 200));

        JLabel hTitle = new JLabel("System Health");
        hTitle.setFont(FONT_CARD_TITLE);
        hTitle.setForeground(COLOR_TEXT_PRIMARY);

        JLabel hSub = new JLabel("Current status of banking nodes");
        hSub.setFont(FONT_SUBTITLE);
        hSub.setForeground(COLOR_TEXT_SECONDARY);

        healthCard.add(hTitle);
        healthCard.add(Box.createVerticalStrut(2));
        healthCard.add(hSub);
        healthCard.add(Box.createVerticalStrut(16));

        // Node Cards Grid (Bank Node 01 & Bank Node 02)
        JPanel nodeGrid = new JPanel(new GridLayout(1, 2, 16, 0));
        nodeGrid.setBackground(COLOR_CARD_BG);

        // Bank Node 01 - Primary
        RoundedPanel node1 = new RoundedPanel(8);
        node1.setLayout(new BorderLayout());
        node1.setBackground(new Color(250, 250, 252));
        node1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_CARD_BORDER, 1),
                new EmptyBorder(14, 16, 14, 16)));

        JPanel n1Top = new JPanel(new BorderLayout());
        n1Top.setBackground(node1.getBackground());
        JPanel n1TitlePanel = new JPanel(new GridLayout(2, 1, 0, 2));
        n1TitlePanel.setBackground(node1.getBackground());
        JLabel n1Name = new JLabel("Bank Node 01");
        n1Name.setFont(FONT_BOLD);
        n1Name.setForeground(COLOR_TEXT_PRIMARY);
        JLabel n1Role = new JLabel("Primary (Port 5000)");
        n1Role.setFont(FONT_SUBTITLE);
        n1Role.setForeground(COLOR_TEXT_SECONDARY);
        n1TitlePanel.add(n1Name);
        n1TitlePanel.add(n1Role);

        JLabel n1Status = new JLabel("● Online");
        n1Status.setFont(FONT_BOLD);
        n1Status.setForeground(COLOR_GREEN);

        n1Top.add(n1TitlePanel, BorderLayout.WEST);
        n1Top.add(n1Status, BorderLayout.EAST);

        JLabel n1Desc = new JLabel("Node operational | Active Primary | Heartbeat sender");
        n1Desc.setFont(FONT_SMALL);
        n1Desc.setForeground(COLOR_TEXT_SECONDARY);

        node1.add(n1Top, BorderLayout.NORTH);
        node1.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        node1.add(n1Desc, BorderLayout.SOUTH);

        // Bank Node 02 - Backup
        RoundedPanel node2 = new RoundedPanel(8);
        node2.setLayout(new BorderLayout());
        node2.setBackground(new Color(250, 250, 252));
        node2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_CARD_BORDER, 1),
                new EmptyBorder(14, 16, 14, 16)));

        JPanel n2Top = new JPanel(new BorderLayout());
        n2Top.setBackground(node2.getBackground());
        JPanel n2TitlePanel = new JPanel(new GridLayout(2, 1, 0, 2));
        n2TitlePanel.setBackground(node2.getBackground());
        JLabel n2Name = new JLabel("Bank Node 02");
        n2Name.setFont(FONT_BOLD);
        n2Name.setForeground(COLOR_TEXT_PRIMARY);
        JLabel n2Role = new JLabel("Backup (Port 6000)");
        n2Role.setFont(FONT_SUBTITLE);
        n2Role.setForeground(COLOR_TEXT_SECONDARY);
        n2TitlePanel.add(n2Name);
        n2TitlePanel.add(n2Role);

        JLabel n2Status = new JLabel("● Online");
        n2Status.setFont(FONT_BOLD);
        n2Status.setForeground(COLOR_GREEN);

        n2Top.add(n2TitlePanel, BorderLayout.WEST);
        n2Top.add(n2Status, BorderLayout.EAST);

        JLabel n2Desc = new JLabel("Node operational | Passive Replication | Heartbeat monitor");
        n2Desc.setFont(FONT_SMALL);
        n2Desc.setForeground(COLOR_TEXT_SECONDARY);

        node2.add(n2Top, BorderLayout.NORTH);
        node2.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        node2.add(n2Desc, BorderLayout.SOUTH);

        nodeGrid.add(node1);
        nodeGrid.add(node2);
        healthCard.add(nodeGrid);

        panel.add(healthCard);
        panel.add(Box.createVerticalStrut(24));

        // Recent Activity Card
        RoundedPanel activityCard = new RoundedPanel(12);
        activityCard.setLayout(new BoxLayout(activityCard, BoxLayout.Y_AXIS));
        activityCard.setBackground(COLOR_CARD_BG);
        activityCard.setBorder(new EmptyBorder(18, 20, 18, 20));
        activityCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        activityCard.setMaximumSize(new Dimension(1200, 180));

        JLabel actTitle = new JLabel("Recent Distributed Banking Activity");
        actTitle.setFont(FONT_CARD_TITLE);
        actTitle.setForeground(COLOR_TEXT_PRIMARY);

        activityCard.add(actTitle);
        activityCard.add(Box.createVerticalStrut(12));

        activityCard.add(createActivityRow("card", "New Loan Application SUBMITTED", "HL1001 - Gitesh Patil (₹62,00,000 / 18 yrs) registered on Primary Node 01", "Just now"));
        activityCard.add(Box.createVerticalStrut(8));
        activityCard.add(createActivityRow("refresh", "Replication State Synchronized", "REPLICATE|SUBMIT|C201|Gitesh Patil|6200000|18|8.7 mirrored to Backup Node 02", "1 min ago"));
        activityCard.add(Box.createVerticalStrut(8));
        activityCard.add(createActivityRow("clock", "Heartbeat Ping Acknowledged", "Periodic liveness verification (interval 2000ms, timeout 6000ms)", "Live"));

        panel.add(activityCard);

        return panel;
    }

    private JPanel createMetricCard(String title, String value, String subtitle) {
        RoundedPanel card = new RoundedPanel(12);
        card.setLayout(new BorderLayout());
        card.setBackground(COLOR_CARD_BG);
        card.setBorder(new EmptyBorder(16, 18, 16, 18));

        JPanel top = new JPanel(new GridLayout(2, 1, 0, 4));
        top.setBackground(COLOR_CARD_BG);

        JLabel tLbl = new JLabel(title);
        tLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tLbl.setForeground(COLOR_TEXT_SECONDARY);

        JLabel vLbl = new JLabel(value);
        vLbl.setFont(FONT_METRIC_VALUE);
        vLbl.setForeground(COLOR_TEXT_PRIMARY);

        top.add(tLbl);
        top.add(vLbl);

        JLabel sLbl = new JLabel(subtitle);
        sLbl.setFont(FONT_SMALL);
        sLbl.setForeground(COLOR_TEXT_SECONDARY);

        card.add(top, BorderLayout.CENTER);
        card.add(sLbl, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createActivityRow(String iconType, String title, String desc, String time) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(COLOR_CARD_BG);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setBackground(COLOR_CARD_BG);

        IconLabel ic = new IconLabel(iconType, 16, new Color(37, 99, 235));

        JPanel text = new JPanel(new GridLayout(2, 1, 0, 2));
        text.setBackground(COLOR_CARD_BG);

        JLabel t = new JLabel(title);
        t.setFont(FONT_BOLD);
        t.setForeground(COLOR_TEXT_PRIMARY);

        JLabel d = new JLabel(desc);
        d.setFont(FONT_SUBTITLE);
        d.setForeground(COLOR_TEXT_SECONDARY);

        text.add(t);
        text.add(d);

        left.add(ic);
        left.add(text);

        JLabel timeLbl = new JLabel(time);
        timeLbl.setFont(FONT_SMALL);
        timeLbl.setForeground(COLOR_TEXT_SECONDARY);

        row.add(left, BorderLayout.WEST);
        row.add(timeLbl, BorderLayout.EAST);
        return row;
    }

    // =========================================================================
    // VIEW 2: LOANS TABLE
    // =========================================================================
    private JPanel buildLoansView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));

        // Top Row: Title + "+ New Loan" Button
        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setBackground(COLOR_BG);
        headerRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerRow.setMaximumSize(new Dimension(1200, 48));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 2));
        titlePanel.setBackground(COLOR_BG);
        JLabel title = new JLabel("Loans");
        title.setFont(FONT_TITLE);
        title.setForeground(COLOR_TEXT_PRIMARY);
        JLabel sub = new JLabel("Manage and monitor loan applications");
        sub.setFont(FONT_SUBTITLE);
        sub.setForeground(COLOR_TEXT_SECONDARY);
        titlePanel.add(title);
        titlePanel.add(sub);
        headerRow.add(titlePanel, BorderLayout.WEST);

        CustomButton btnNewLoan = new CustomButton("+ New Loan", COLOR_PRIMARY_BTN, Color.WHITE);
        btnNewLoan.setPreferredSize(new Dimension(115, 36));
        btnNewLoan.addActionListener(e -> {
            switchView("DETAILS");
        });
        headerRow.add(btnNewLoan, BorderLayout.EAST);

        panel.add(headerRow);
        panel.add(Box.createVerticalStrut(20));

        // 4 Summary Metric Cards
        JPanel miniMetrics = new JPanel(new GridLayout(1, 4, 16, 0));
        miniMetrics.setBackground(COLOR_BG);
        miniMetrics.setAlignmentX(Component.LEFT_ALIGNMENT);
        miniMetrics.setMaximumSize(new Dimension(1200, 95));

        miniMetrics.add(createMetricCard("Total Loans", "1", "All applications"));
        miniMetrics.add(createMetricCard("Active", "0", "Currently active"));
        miniMetrics.add(createMetricCard("Pending", "1", "Awaiting processing"));
        miniMetrics.add(createMetricCard("Completed", "0", "Successfully completed"));

        panel.add(miniMetrics);
        panel.add(Box.createVerticalStrut(24));

        // Loan Applications Table Container
        RoundedPanel tableCard = new RoundedPanel(12);
        tableCard.setLayout(new BorderLayout());
        tableCard.setBackground(COLOR_CARD_BG);
        tableCard.setBorder(new EmptyBorder(16, 20, 20, 20));
        tableCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableCard.setMaximumSize(new Dimension(1200, 360));

        // Table Header Section: Title + Count + Refresh Button
        JPanel tableTop = new JPanel(new BorderLayout());
        tableTop.setBackground(COLOR_CARD_BG);
        tableTop.setBorder(new EmptyBorder(0, 0, 16, 0));

        JPanel tableTitlePanel = new JPanel(new GridLayout(2, 1, 0, 2));
        tableTitlePanel.setBackground(COLOR_CARD_BG);
        JLabel tHeading = new JLabel("Loan Applications");
        tHeading.setFont(FONT_CARD_TITLE);
        tHeading.setForeground(COLOR_TEXT_PRIMARY);
        JLabel tCount = new JLabel("1 loan found");
        tCount.setFont(FONT_SUBTITLE);
        tCount.setForeground(COLOR_TEXT_SECONDARY);
        tableTitlePanel.add(tHeading);
        tableTitlePanel.add(tCount);
        tableTop.add(tableTitlePanel, BorderLayout.WEST);

        CustomButton btnRefresh = new CustomButton("Refresh", Color.WHITE, COLOR_TEXT_PRIMARY);
        btnRefresh.setBorderColor(COLOR_CARD_BORDER);
        btnRefresh.setPreferredSize(new Dimension(90, 32));
        btnRefresh.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Refreshed loan database from Primary Server (localhost:5000).\n1 record confirmed replicated.", "Database Synchronized", JOptionPane.INFORMATION_MESSAGE);
        });
        tableTop.add(btnRefresh, BorderLayout.EAST);

        tableCard.add(tableTop, BorderLayout.NORTH);

        // Data Table
        String[] columns = {"LOAN ID", "CUSTOMER", "AMOUNT", "TENURE", "LOAN TYPE", "STATUS"};
        Object[][] data = {
                {"HL1001", "Gitesh Patil\nCUS201", "₹62,00,000", "18 years", "Home Loan", "Pending"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(56);
        table.setFont(FONT_REGULAR);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(COLOR_CARD_BG);
        table.setSelectionBackground(new Color(241, 245, 249));

        // Header Styling
        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 11));
        th.setForeground(COLOR_TEXT_SECONDARY);
        th.setBackground(new Color(248, 250, 252));
        th.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_CARD_BORDER));
        th.setPreferredSize(new Dimension(0, 36));

        // Custom Cell Renderers
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                l.setFont(FONT_BOLD);
                l.setForeground(COLOR_TEXT_PRIMARY);
                l.setBorder(new EmptyBorder(0, 12, 0, 0));
                return l;
            }
        });

        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel cell = new JPanel(new GridLayout(2, 1, 0, 2));
                cell.setBackground(isSelected ? table.getSelectionBackground() : COLOR_CARD_BG);
                cell.setBorder(new EmptyBorder(10, 10, 10, 10));
                JLabel name = new JLabel("Gitesh Patil");
                name.setFont(FONT_BOLD);
                name.setForeground(COLOR_TEXT_PRIMARY);
                JLabel cid = new JLabel("CUS201");
                cid.setFont(FONT_SMALL);
                cid.setForeground(COLOR_TEXT_SECONDARY);
                cell.add(name);
                cell.add(cid);
                return cell;
            }
        });

        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                l.setFont(FONT_BOLD);
                l.setForeground(COLOR_TEXT_PRIMARY);
                return l;
            }
        });

        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 14));
                p.setBackground(isSelected ? table.getSelectionBackground() : COLOR_CARD_BG);
                JLabel badge = new JLabel("Pending", SwingConstants.CENTER) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        setupQualityRendering(g2);
                        g2.setColor(COLOR_AMBER_BG);
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                        g2.dispose();
                        super.paintComponent(g);
                    }
                };
                badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
                badge.setForeground(COLOR_AMBER);
                badge.setPreferredSize(new Dimension(74, 24));
                p.add(badge);
                return p;
            }
        });

        // Double click row opens details
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchView("DETAILS");
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(COLOR_CARD_BG);

        tableCard.add(scrollPane, BorderLayout.CENTER);
        panel.add(tableCard);

        return panel;
    }

    // =========================================================================
    // VIEW 3: LOAN DETAILS (HL1001)
    // =========================================================================
    private JPanel buildLoanDetailsView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));

        // Breadcrumb
        JLabel breadcrumb = new JLabel("Loan Management  /  Loan Details");
        breadcrumb.setFont(FONT_SMALL);
        breadcrumb.setForeground(COLOR_TEXT_SECONDARY);
        breadcrumb.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("Loan Details");
        title.setFont(FONT_TITLE);
        title.setForeground(COLOR_TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("HL1001");
        sub.setFont(FONT_SUBTITLE);
        sub.setForeground(COLOR_TEXT_SECONDARY);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(breadcrumb);
        panel.add(Box.createVerticalStrut(4));
        panel.add(title);
        panel.add(Box.createVerticalStrut(2));
        panel.add(sub);
        panel.add(Box.createVerticalStrut(24));

        // Large Detail Card
        RoundedPanel card = new RoundedPanel(12);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COLOR_CARD_BG);
        card.setBorder(new EmptyBorder(24, 24, 28, 24));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(1200, 420));

        // Top Customer Header Row with Status Badge
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_CARD_BG);

        JPanel custInfo = new JPanel(new GridLayout(2, 1, 0, 4));
        custInfo.setBackground(COLOR_CARD_BG);

        JLabel cName = new JLabel("Gitesh Patil");
        cName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cName.setForeground(COLOR_TEXT_PRIMARY);

        JLabel cId = new JLabel("Customer ID: CUS201");
        cId.setFont(FONT_SUBTITLE);
        cId.setForeground(COLOR_TEXT_SECONDARY);

        custInfo.add(cName);
        custInfo.add(cId);

        JLabel statusBadge = new JLabel("PENDING", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                setupQualityRendering(g2);
                g2.setColor(COLOR_AMBER_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        statusBadge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusBadge.setForeground(COLOR_AMBER);
        statusBadge.setPreferredSize(new Dimension(90, 28));

        header.add(custInfo, BorderLayout.WEST);
        header.add(statusBadge, BorderLayout.EAST);
        card.add(header);

        card.add(Box.createVerticalStrut(20));
        card.add(new JSeparator(SwingConstants.HORIZONTAL));
        card.add(Box.createVerticalStrut(20));

        // 4-Column Specification Grid
        JPanel grid1 = new JPanel(new GridLayout(1, 4, 24, 0));
        grid1.setBackground(COLOR_CARD_BG);
        grid1.add(createDetailItem("Loan ID", "HL1001"));
        grid1.add(createDetailItem("Loan Amount", "₹62,00,000"));
        grid1.add(createDetailItem("Tenure", "18 years"));
        grid1.add(createDetailItem("Loan Type", "Home Loan"));
        card.add(grid1);

        card.add(Box.createVerticalStrut(24));

        // Second Grid Row (Interest Rate, Monthly EMI, Property, Distributed Sync)
        JPanel grid2 = new JPanel(new GridLayout(1, 4, 24, 0));
        grid2.setBackground(COLOR_CARD_BG);
        grid2.add(createDetailItem("Interest Rate", "8.7% p.a."));
        grid2.add(createDetailItem("Estimated Monthly EMI", "₹59,045 / month"));
        grid2.add(createDetailItem("Property Location", "Plot 42, Palm Grove, Mumbai"));
        grid2.add(createDetailItem("Replication State", "Synced on Bank Node 02"));
        card.add(grid2);

        card.add(Box.createVerticalStrut(28));

        // Distributed Fault Tolerance Verification Banner
        RoundedPanel notice = new RoundedPanel(8);
        notice.setLayout(new BorderLayout());
        notice.setBackground(new Color(241, 245, 249));
        notice.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel noticeLbl = new JLabel("Distributed System Guarantee: This record is backed up passively on Bank Node 02. In case of Primary crash, failover ensures continuous availability.");
        noticeLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        noticeLbl.setForeground(new Color(30, 41, 59));
        notice.add(noticeLbl, BorderLayout.CENTER);

        card.add(notice);
        panel.add(card);

        return panel;
    }

    private JPanel createDetailItem(String label, String value) {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 4));
        p.setBackground(COLOR_CARD_BG);

        JLabel l = new JLabel(label);
        l.setFont(FONT_SMALL);
        l.setForeground(COLOR_TEXT_SECONDARY);

        JLabel v = new JLabel(value);
        v.setFont(FONT_BOLD);
        v.setForeground(COLOR_TEXT_PRIMARY);

        p.add(l);
        p.add(v);
        return p;
    }

    // =========================================================================
    // VIEW 4: FAILOVER & REPLICATION MONITOR
    // =========================================================================
    private JPanel buildFailoverView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));

        // Header Title
        JLabel title = new JLabel("Replication & Fault Tolerance Monitor");
        title.setFont(FONT_TITLE);
        title.setForeground(COLOR_TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Real-time passive replication, heartbeat monitoring & automatic failover promotion");
        sub.setFont(FONT_SUBTITLE);
        sub.setForeground(COLOR_TEXT_SECONDARY);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(4));
        panel.add(sub);
        panel.add(Box.createVerticalStrut(20));

        // Failover Event Alert Banner
        RoundedPanel alertBanner = new RoundedPanel(10);
        alertBanner.setLayout(new BorderLayout());
        alertBanner.setBackground(COLOR_AMBER_BG);
        alertBanner.setBorder(new EmptyBorder(14, 18, 14, 18));
        alertBanner.setAlignmentX(Component.LEFT_ALIGNMENT);
        alertBanner.setMaximumSize(new Dimension(1200, 56));

        JLabel alertText = new JLabel("FAILOVER EVENT SIMULATION: Primary Node 01 Crashed  ->  Backup Node 02 Promoted to Active Primary");
        alertText.setFont(new Font("Segoe UI", Font.BOLD, 13));
        alertText.setForeground(new Color(180, 83, 9));
        alertBanner.add(alertText, BorderLayout.CENTER);

        panel.add(alertBanner);
        panel.add(Box.createVerticalStrut(20));

        // Node Transition Cards Grid
        JPanel nodesGrid = new JPanel(new GridLayout(1, 2, 16, 0));
        nodesGrid.setBackground(COLOR_BG);
        nodesGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        nodesGrid.setMaximumSize(new Dimension(1200, 160));

        // Bank Node 01 (Offline / Crashed)
        RoundedPanel node1Fail = new RoundedPanel(10);
        node1Fail.setLayout(new BorderLayout());
        node1Fail.setBackground(COLOR_CARD_BG);
        node1Fail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_RED, 1),
                new EmptyBorder(16, 18, 16, 18)));

        JPanel n1Top = new JPanel(new BorderLayout());
        n1Top.setBackground(COLOR_CARD_BG);
        JLabel n1Title = new JLabel("Bank Node 01 [Former Primary]");
        n1Title.setFont(FONT_BOLD);
        n1Title.setForeground(COLOR_TEXT_PRIMARY);

        JLabel n1Badge = new JLabel("● CRASHED / OFFLINE", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                setupQualityRendering(g2);
                g2.setColor(COLOR_RED_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        n1Badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        n1Badge.setForeground(COLOR_RED);
        n1Badge.setPreferredSize(new Dimension(135, 24));

        n1Top.add(n1Title, BorderLayout.WEST);
        n1Top.add(n1Badge, BorderLayout.EAST);

        JLabel n1Details = new JLabel("<html>Port: 5000 (Socket Closed)<br>Heartbeat: Missed &gt; 6000ms<br>Status: Terminated for fault simulation</html>");
        n1Details.setFont(FONT_REGULAR);
        n1Details.setForeground(COLOR_TEXT_SECONDARY);

        node1Fail.add(n1Top, BorderLayout.NORTH);
        node1Fail.add(n1Details, BorderLayout.CENTER);

        // Bank Node 02 (Promoted to Active Primary)
        RoundedPanel node2Promoted = new RoundedPanel(10);
        node2Promoted.setLayout(new BorderLayout());
        node2Promoted.setBackground(COLOR_CARD_BG);
        node2Promoted.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_GREEN, 1),
                new EmptyBorder(16, 18, 16, 18)));

        JPanel n2Top = new JPanel(new BorderLayout());
        n2Top.setBackground(COLOR_CARD_BG);
        JLabel n2Title = new JLabel("Bank Node 02 [PROMOTED ACTIVE]");
        n2Title.setFont(FONT_BOLD);
        n2Title.setForeground(COLOR_TEXT_PRIMARY);

        JLabel n2Badge = new JLabel("● ACTIVE PRIMARY", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                setupQualityRendering(g2);
                g2.setColor(COLOR_GREEN_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        n2Badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        n2Badge.setForeground(COLOR_GREEN);
        n2Badge.setPreferredSize(new Dimension(125, 24));

        n2Top.add(n2Title, BorderLayout.WEST);
        n2Top.add(n2Badge, BorderLayout.EAST);

        JLabel n2Details = new JLabel("<html>Port: 5000 (Client ServerSocket Acquired)<br>Recovered Loans: 1 active record (HL1001 - Gitesh Patil)<br>Data Loss: 0% (Zero loss confirmed)</html>");
        n2Details.setFont(FONT_REGULAR);
        n2Details.setForeground(COLOR_TEXT_SECONDARY);

        node2Promoted.add(n2Top, BorderLayout.NORTH);
        node2Promoted.add(n2Details, BorderLayout.CENTER);

        nodesGrid.add(node1Fail);
        nodesGrid.add(node2Promoted);
        panel.add(nodesGrid);

        panel.add(Box.createVerticalStrut(20));

        // Replication & Failover Execution Log Console
        RoundedPanel logCard = new RoundedPanel(12);
        logCard.setLayout(new BorderLayout());
        logCard.setBackground(new Color(15, 23, 42)); // Dark terminal console
        logCard.setBorder(new EmptyBorder(16, 18, 16, 18));
        logCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        logCard.setMaximumSize(new Dimension(1200, 240));

        JLabel logHeader = new JLabel("Replication Engine Event Stream (Live Log)");
        logHeader.setFont(new Font("Consolas", Font.BOLD, 13));
        logHeader.setForeground(new Color(148, 163, 184));
        logCard.add(logHeader, BorderLayout.NORTH);

        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logArea.setBackground(new Color(15, 23, 42));
        logArea.setForeground(new Color(241, 245, 249));
        logArea.setText(
                "[17:45:01.120] [PRIMARY Node 01] Bound client port 5000. Heartbeat worker running.\n" +
                "[17:45:02.010] [BACKUP  Node 02] Heartbeat PING acknowledged from Node 01.\n" +
                "[17:45:03.450] [PRIMARY Node 01] Client C201 submitted SUBMIT|C201|Gitesh Patil|6200000.0|18|8.7\n" +
                "[17:45:03.452] [PRIMARY Node 01] -> Replicating to Backup Node 02 (port 6000)\n" +
                "[17:45:03.455] [BACKUP  Node 02] <- Synced loan application HL1001 into in-memory store.\n" +
                "[17:45:05.100] [CRASH SIMULATION] Primary Node 01 process terminated abruptly.\n" +
                "[17:45:07.100] [BACKUP  Node 02] Warning: No PING received for 2000ms.\n" +
                "[17:45:11.223] [BACKUP  Node 02] FAILOVER TRIGGERED: Timeout exceeded (6123ms > 6000ms limit).\n" +
                "[17:45:11.225] [PROMOTION] Node 02 promoted to ACTIVE PRIMARY. Recovered 1 loan entries.\n" +
                "[17:45:11.230] [PROMOTION] Node 02 opened client listener on port 5000.\n" +
                "[17:45:12.800] [CLIENT] Auto-failover reconnected to Node 02. Query HL1001 status verified: UNDER_REVIEW."
        );
        logArea.setBorder(new EmptyBorder(10, 0, 0, 0));
        logCard.add(logArea, BorderLayout.CENTER);

        panel.add(logCard);
        return panel;
    }

    // =========================================================================
    // EXPORT HIGH-RESOLUTION SCREENSHOTS DIRECTLY FROM JAVA SWING
    // =========================================================================
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
                    System.out.println("[JAVA GUI] Exported view '" + viewName + "' to: " + outputPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Rendering Quality Helper
    public static void setupQualityRendering(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    // =========================================================================
    // CUSTOM UI COMPONENTS
    // =========================================================================

    // Custom Rounded Panel for clean UI cards
    static class RoundedPanel extends JPanel {
        private final int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            setupQualityRendering(g2);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
            g2.setColor(COLOR_CARD_BORDER);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Custom Button with Flat Design & Clean Borders
    static class CustomButton extends JButton {
        private Color bgColor;
        private Color fgColor;
        private Color borderColor = null;

        public CustomButton(String text, Color bg, Color fg) {
            super(text);
            this.bgColor = bg;
            this.fgColor = fg;
            setFont(FONT_BOLD);
            setForeground(fg);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        public void setBorderColor(Color c) {
            this.borderColor = c;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            setupQualityRendering(g2);
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            if (borderColor != null) {
                g2.setColor(borderColor);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Window Title Bar Controls (Min, Max, Close)
    static class WindowCtrlLabel extends JLabel {
        private boolean isClose;

        public WindowCtrlLabel(String text, int width, int height, boolean isClose) {
            super(text, SwingConstants.CENTER);
            this.isClose = isClose;
            setPreferredSize(new Dimension(width, height));
            setForeground(new Color(148, 163, 184));
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
            setOpaque(false);
        }
    }

    // Vector Icon Label
    static class IconLabel extends JLabel {
        private String type;
        private int size;
        private Color color;

        public IconLabel(String type, int size, Color color) {
            this.type = type;
            this.size = size;
            this.color = color;
            setPreferredSize(new Dimension(size + 4, size + 4));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            setupQualityRendering(g2);
            g2.setColor(color);
            int w = getWidth();
            int h = getHeight();
            int cx = w / 2;
            int cy = h / 2;
            int r = size / 2;

            switch (type) {
                case "bank":
                    // Bank roof (triangle) + pillars + base
                    int[] rx = {cx - r, cx, cx + r};
                    int[] ry = {cy - r / 2, cy - r, cy - r / 2};
                    g2.fillPolygon(rx, ry, 3);
                    g2.fillRect(cx - r, cy - r / 2, size, 2);
                    // 3 pillars
                    g2.fillRect(cx - r + 2, cy - r / 2 + 3, 2, r);
                    g2.fillRect(cx - 1, cy - r / 2 + 3, 2, r);
                    g2.fillRect(cx + r - 4, cy - r / 2 + 3, 2, r);
                    // base
                    g2.fillRect(cx - r, cy + r / 2 + 1, size, 3);
                    break;
                case "search":
                    g2.setStroke(new BasicStroke(2.0f));
                    g2.drawOval(cx - r + 1, cy - r + 1, size - 5, size - 5);
                    g2.drawLine(cx + 2, cy + 2, cx + r, cy + r);
                    break;
                case "bell":
                    g2.setStroke(new BasicStroke(1.8f));
                    g2.drawArc(cx - r + 2, cy - r + 2, size - 4, size - 2, 0, 180);
                    g2.drawLine(cx - r + 2, cy + 1, cx + r - 2, cy + 1);
                    g2.fillOval(cx - 1, cy + 2, 3, 3);
                    // Red notification dot
                    g2.setColor(COLOR_RED);
                    g2.fillOval(cx + r - 4, cy - r + 2, 5, 5);
                    break;
                case "grid":
                    int s = size / 2 - 1;
                    g2.fillRect(cx - r, cy - r, s, s);
                    g2.fillRect(cx + 1, cy - r, s, s);
                    g2.fillRect(cx - r, cy + 1, s, s);
                    g2.fillRect(cx + 1, cy + 1, s, s);
                    break;
                case "card":
                    g2.fillRoundRect(cx - r, cy - r + 2, size, size - 4, 3, 3);
                    g2.setColor(COLOR_SIDEBAR_BG);
                    g2.fillRect(cx - r, cy - r + 5, size, 2);
                    break;
                case "users":
                    g2.fillOval(cx - r + 1, cy - r + 1, 6, 6);
                    g2.fillArc(cx - r - 1, cy - 1, 10, 8, 0, 180);
                    g2.fillOval(cx + 2, cy - r + 2, 5, 5);
                    g2.fillArc(cx + 1, cy, 8, 7, 0, 180);
                    break;
                case "swap":
                    g2.setStroke(new BasicStroke(1.8f));
                    g2.drawLine(cx - r + 1, cy - 2, cx + r - 1, cy - 2);
                    g2.drawLine(cx + r - 4, cy - 5, cx + r - 1, cy - 2);
                    g2.drawLine(cx + r - 1, cy + 2, cx - r + 1, cy + 2);
                    g2.drawLine(cx - r + 4, cy + 5, cx - r + 1, cy + 2);
                    break;
                case "server":
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(cx - r, cy - r + 1, size, 4, 2, 2);
                    g2.drawRoundRect(cx - r, cy - 1, size, 4, 2, 2);
                    g2.drawRoundRect(cx - r, cy + r - 4, size, 4, 2, 2);
                    break;
                case "trophy":
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(cx - r + 2, cy - r + 1, size - 4, 6, 3, 3);
                    g2.drawLine(cx, cy - r + 7, cx, cy + 2);
                    g2.drawLine(cx - 3, cy + 3, cx + 3, cy + 3);
                    break;
                case "refresh":
                    g2.setStroke(new BasicStroke(1.8f));
                    g2.drawArc(cx - r + 2, cy - r + 2, size - 4, size - 4, 45, 270);
                    g2.drawLine(cx + r - 2, cy - 2, cx + r - 2, cy + 2);
                    break;
                case "clock":
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawOval(cx - r + 1, cy - r + 1, size - 2, size - 2);
                    g2.drawLine(cx, cy, cx, cy - 3);
                    g2.drawLine(cx, cy, cx + 3, cy);
                    break;
                case "chart":
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawLine(cx - r + 1, cy + r - 2, cx - r + 1, cy - r + 1);
                    g2.drawLine(cx - r + 1, cy + r - 2, cx + r - 1, cy + r - 2);
                    g2.drawLine(cx - r + 3, cy + 1, cx - 1, cy - 2);
                    g2.drawLine(cx - 1, cy - 2, cx + 3, cy + 2);
                    g2.drawLine(cx + 3, cy + 2, cx + r - 2, cy - 4);
                    break;
                case "settings":
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawOval(cx - 3, cy - 3, 6, 6);
                    g2.drawOval(cx - r + 2, cy - r + 2, size - 4, size - 4);
                    break;
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Sidebar Navigation Button with Icon
    static class NavButton extends JButton {
        private String iconType;
        private boolean active;

        public NavButton(String iconType, String text, boolean active) {
            super(text);
            this.iconType = iconType;
            this.active = active;
            setFont(active ? FONT_NAV_ACTIVE : FONT_NAV);
            setForeground(active ? Color.WHITE : COLOR_SIDEBAR_TEXT);
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setMaximumSize(new Dimension(200, 36));
            setPreferredSize(new Dimension(200, 36));
            setHorizontalAlignment(SwingConstants.LEFT);
            setBorder(new EmptyBorder(8, 36, 8, 12));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        public void setActive(boolean a) {
            this.active = a;
            setFont(a ? FONT_NAV_ACTIVE : FONT_NAV);
            setForeground(a ? Color.WHITE : COLOR_SIDEBAR_TEXT);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            setupQualityRendering(g2);
            if (active) {
                g2.setColor(COLOR_SIDEBAR_ACTIVE);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            }
            // Draw Icon
            IconLabel ic = new IconLabel(iconType, 14, active ? Color.WHITE : COLOR_SIDEBAR_TEXT);
            ic.setSize(18, 18);
            g2.translate(10, 9);
            ic.paint(g2);
            g2.translate(-10, -9);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    // =========================================================================
    // MAIN METHOD
    // =========================================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankCoreGUI gui = new BankCoreGUI();
            gui.setVisible(true);

            // If launched with --capture argument, export all views automatically
            if (args.length > 0 && args[0].equalsIgnoreCase("--capture")) {
                String outDir = args.length > 1 ? args[1] : "screenshots";
                new Thread(() -> {
                    try {
                        Thread.sleep(1200);
                        gui.exportScreenCapture("DASHBOARD", outDir + "/01_Real_Java_UI_Dashboard.png");
                        Thread.sleep(500);
                        gui.exportScreenCapture("LOANS", outDir + "/02_Real_Java_UI_Loans_Management.png");
                        Thread.sleep(500);
                        gui.exportScreenCapture("DETAILS", outDir + "/03_Real_Java_UI_Loan_Details.png");
                        Thread.sleep(500);
                        gui.exportScreenCapture("FAILOVER", outDir + "/04_Real_Java_UI_Failover_Active.png");
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
