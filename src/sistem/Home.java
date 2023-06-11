/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistem;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import koneksi.loginSession;
import customization.cSVG;
import java.awt.Color;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author thowie
 */
public final class Home extends javax.swing.JFrame {

    public void loadUserInfo() {

        infoName.setText("Username: " + loginSession.getUsername());
        infoRole.setText("Role: " + loginSession.getRole());
    }

    public void loadDateAndTime() {
        dateAndTime loading = new dateAndTime();
        loading.startClock(infoDate, "date");
        loading.startClock(infoTime, "time");
    }

    public void setMainContainer(String panelName) throws ParseException {
        panelLoader loader = new panelLoader();
        JPanel panel;

        // Load panel based on the value of the panelName variable
        panel = switch (panelName) {
            case "home" ->
                homePanel;
            case "customer" ->
                new Customer();
            case "stock" ->
                new stockPenjualan();
            case "penjualan" ->
                new Penjualan();
            case "laporan" ->
                new salesReport();
            case "users" ->
                new appAdministrator();
            default ->
                new JPanel();
        }; // Add additional cases for other panels as needed

        // Load the panel into the main container using the panelLoader class
        loader.loadPanel(mainContainer, panel);
    }

    private void keluarAplikasi() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogType = JOptionPane.WARNING_MESSAGE;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Anda yakin ingin keluar?", 
                "Peringatan", dialogButton, dialogType);
        if (dialogResult == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void exitButton() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                keluarAplikasi();
            }
        });
    }
    
    public Home() {
        initComponents();
        init();
        exitButton();
        setTitle("Sales Tracking System by Group Six");
        loadDateAndTime();
        loadUserInfo();
        cSVG setSVG = new cSVG();
        setSVG.menuIconSet(menuHome, "icon/menu/Home.svg");
        setSVG.menuIconSet(menuCustomer, "icon/menu/Customer.svg");
        setSVG.menuIconSet(menuStock, "icon/menu/Stocks.svg");
        setSVG.menuIconSet(menuPenjualan, "icon/menu/Sales.svg");
        setSVG.menuIconSet(menuLaporan, "icon/menu/Reports.svg");
        setSVG.menuIconSet(menuUser, "icon/menu/User.svg");
        Color logout = UIManager.getColor("SalesTracking");
        setSVG.labelIconColorSet(logOut, "icon/menu/Logout.svg", logout);
        Color logname = UIManager.getColor("Main.form.nameColor");
        setSVG.labelIconColorSet(LoginSession, "icon/menu/Name.svg", logname);
        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(rootPane);
        setSVG.frameIcon(window, "icon/Towdio.svg");
    }
    
    public void init(){
        
        jLabel2.putClientProperty(FlatClientProperties.STYLE,""
                + "foreground:$SalesTracking;");
        jLabel3.putClientProperty(FlatClientProperties.STYLE,""
                + "foreground:$SalesTracking;");
        jLabel4.putClientProperty(FlatClientProperties.STYLE,""
                + "foreground:$Main.form.session;");
        
        mainForm.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:@background");
        
        JLabel[] menus = new JLabel[]{menuHome, menuCustomer, menuStock, menuPenjualan, menuLaporan, menuUser};
        for (var menu : menus){
            menu.putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:$Menu.background;"
                    + "foreground:$Menu.foreground;");
            // Seting border menu color
            menu.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Menu.borderColor")));
        }
        
        LoginSession.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:$Main.form.nameColor;");
        logOut.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:$Logout.color;");
        
        JLabel[] infos = new JLabel[]{infoDate, infoName, infoRole, infoTime};
        for (var info : infos){
            info.putClientProperty(FlatClientProperties.STYLE, ""
                    + "foreground:$Main.form.session;");
        }
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainForm = new javax.swing.JPanel();
        menuBox = new javax.swing.JPanel();
        menuHome = new javax.swing.JLabel();
        menuCustomer = new javax.swing.JLabel();
        menuStock = new javax.swing.JLabel();
        menuPenjualan = new javax.swing.JLabel();
        menuLaporan = new javax.swing.JLabel();
        menuUser = new javax.swing.JLabel();
        mainContainer = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        footer = new javax.swing.JPanel();
        LoginSession = new javax.swing.JLabel();
        infoName = new javax.swing.JLabel();
        infoRole = new javax.swing.JLabel();
        infoDate = new javax.swing.JLabel();
        infoTime = new javax.swing.JLabel();
        logOut = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 920, 650));
        setMinimumSize(new java.awt.Dimension(920, 650));
        setPreferredSize(new java.awt.Dimension(920, 650));
        setSize(new java.awt.Dimension(920, 650));

        mainForm.setPreferredSize(new java.awt.Dimension(800, 620));

        menuBox.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        menuBox.setOpaque(false);

        menuHome.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menuHome.setForeground(new java.awt.Color(0, 75, 173));
        menuHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicon/home.png"))); // NOI18N
        menuHome.setText("HOME");
        menuHome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        menuHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuHome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuHome.setOpaque(true);
        menuHome.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuHomeMouseClicked(evt);
            }
        });

        menuCustomer.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menuCustomer.setForeground(new java.awt.Color(0, 75, 173));
        menuCustomer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicon/customer.png"))); // NOI18N
        menuCustomer.setText("CUSTOMER");
        menuCustomer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        menuCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuCustomer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuCustomer.setOpaque(true);
        menuCustomer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuCustomerMouseClicked(evt);
            }
        });

        menuStock.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menuStock.setForeground(new java.awt.Color(0, 75, 173));
        menuStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicon/stock.png"))); // NOI18N
        menuStock.setText("STOCK");
        menuStock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        menuStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuStock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuStock.setOpaque(true);
        menuStock.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuStockMouseClicked(evt);
            }
        });

        menuPenjualan.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menuPenjualan.setForeground(new java.awt.Color(0, 75, 173));
        menuPenjualan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicon/penjualan.png"))); // NOI18N
        menuPenjualan.setText("SALES");
        menuPenjualan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        menuPenjualan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPenjualan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuPenjualan.setOpaque(true);
        menuPenjualan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuPenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPenjualanMouseClicked(evt);
            }
        });

        menuLaporan.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menuLaporan.setForeground(new java.awt.Color(0, 75, 173));
        menuLaporan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicon/laporan.png"))); // NOI18N
        menuLaporan.setText("REPORTS");
        menuLaporan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        menuLaporan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuLaporan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuLaporan.setOpaque(true);
        menuLaporan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuLaporanMouseClicked(evt);
            }
        });

        menuUser.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menuUser.setForeground(new java.awt.Color(0, 75, 173));
        menuUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicon/users.png"))); // NOI18N
        menuUser.setText("USER");
        menuUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        menuUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuUser.setOpaque(true);
        menuUser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuUserMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menuBoxLayout = new javax.swing.GroupLayout(menuBox);
        menuBox.setLayout(menuBoxLayout);
        menuBoxLayout.setHorizontalGroup(
            menuBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        menuBoxLayout.setVerticalGroup(
            menuBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menuHome, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuStock, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuUser, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mainContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        mainContainer.setOpaque(false);

        homePanel.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 48)); // NOI18N
        jLabel1.setText("JAVA");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 64)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(243, 114, 33));
        jLabel2.setText("SALES TRACKING");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 64)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(243, 114, 33));
        jLabel3.setText("SYSTEM");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel4.setForeground(java.awt.Color.gray);
        jLabel4.setText("BY GROUP 6");

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(19, 19, 19))
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(368, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout mainContainerLayout = new javax.swing.GroupLayout(mainContainer);
        mainContainer.setLayout(mainContainerLayout);
        mainContainerLayout.setHorizontalGroup(
            mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainContainerLayout.setVerticalGroup(
            mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        footer.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        footer.setOpaque(false);

        LoginSession.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        LoginSession.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Name.png"))); // NOI18N
        LoginSession.setText("Login Session");

        infoName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        infoName.setForeground(new java.awt.Color(0, 75, 173));
        infoName.setText("Username:");

        infoRole.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        infoRole.setForeground(new java.awt.Color(0, 75, 173));
        infoRole.setText("Role:");

        infoDate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        infoDate.setForeground(new java.awt.Color(0, 75, 173));
        infoDate.setText("Date:");

        infoTime.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        infoTime.setForeground(new java.awt.Color(0, 75, 173));
        infoTime.setText("Time:");

        logOut.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        logOut.setForeground(new java.awt.Color(230, 57, 70));
        logOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Logout.png"))); // NOI18N
        logOut.setText("Log-Out");
        logOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout footerLayout = new javax.swing.GroupLayout(footer);
        footer.setLayout(footerLayout);
        footerLayout.setHorizontalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LoginSession)
                    .addGroup(footerLayout.createSequentialGroup()
                        .addComponent(infoName)
                        .addGap(18, 18, 18)
                        .addComponent(infoRole)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logOut, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, footerLayout.createSequentialGroup()
                        .addComponent(infoDate)
                        .addGap(18, 18, 18)
                        .addComponent(infoTime)))
                .addContainerGap())
        );
        footerLayout.setVerticalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LoginSession)
                    .addComponent(logOut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(infoName)
                    .addComponent(infoRole)
                    .addComponent(infoDate)
                    .addComponent(infoTime))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainFormLayout = new javax.swing.GroupLayout(mainForm);
        mainForm.setLayout(mainFormLayout);
        mainFormLayout.setHorizontalGroup(
            mainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(footer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainFormLayout.setVerticalGroup(
            mainFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(footer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainForm, javax.swing.GroupLayout.DEFAULT_SIZE, 1039, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainForm, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuHomeMouseClicked
        try {
            setMainContainer("home");
        } catch (ParseException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuHomeMouseClicked

    private void menuCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCustomerMouseClicked
        try {
            setMainContainer("customer");
        } catch (ParseException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuCustomerMouseClicked

    private void menuStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuStockMouseClicked
        try {
            setMainContainer("stock");
        } catch (ParseException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuStockMouseClicked

    private void menuPenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPenjualanMouseClicked
        try {
            setMainContainer("penjualan");
        } catch (ParseException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuPenjualanMouseClicked

    private void menuLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuLaporanMouseClicked
        try {
            setMainContainer("laporan");
        } catch (ParseException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuLaporanMouseClicked

    private void menuUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuUserMouseClicked
        try {
            setMainContainer("users");
        } catch (ParseException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuUserMouseClicked

    private void logOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutMouseClicked
        int logout = JOptionPane.showConfirmDialog(null,
            "Anda akan logout dari sesi sekarang! Lanjutkan?",
            "Peringatan!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (logout == JOptionPane.OK_OPTION) {
            // Hide the current JFrame
            this.setVisible(false);

            // Show the login again
            Login login = new Login();
            login.setVisible(true);
        }
    }//GEN-LAST:event_logOutMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightLaf.registerCustomDefaultsSource( "customization" );
        FlatLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LoginSession;
    private javax.swing.JPanel footer;
    private javax.swing.JPanel homePanel;
    private javax.swing.JLabel infoDate;
    private javax.swing.JLabel infoName;
    private javax.swing.JLabel infoRole;
    private javax.swing.JLabel infoTime;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel logOut;
    private javax.swing.JPanel mainContainer;
    private javax.swing.JPanel mainForm;
    private javax.swing.JPanel menuBox;
    private javax.swing.JLabel menuCustomer;
    private javax.swing.JLabel menuHome;
    private javax.swing.JLabel menuLaporan;
    private javax.swing.JLabel menuPenjualan;
    private javax.swing.JLabel menuStock;
    private javax.swing.JLabel menuUser;
    // End of variables declaration//GEN-END:variables
}
