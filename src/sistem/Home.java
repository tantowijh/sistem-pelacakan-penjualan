/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistem;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import koneksi.loginSession;

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

    public void setMainContainer(String panelName) {
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
        int dialogButton = JOptionPane.WARNING_MESSAGE;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Anda yakin ingin keluar?", "Peringatan", dialogButton);
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

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        exitButton();
        setResizable(false);
        setTitle("Sistem Pelacakan Penjualan by Group Six");
        loadDateAndTime();
        loadUserInfo();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBox = new javax.swing.JPanel();
        manuHome = new javax.swing.JLabel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));

        menuBox.setBackground(java.awt.Color.orange);
        menuBox.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        manuHome.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        manuHome.setForeground(new java.awt.Color(20, 108, 148));
        manuHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        manuHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicon/home.png"))); // NOI18N
        manuHome.setText("BERANDA");
        manuHome.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        manuHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manuHome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        manuHome.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        manuHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manuHomeMouseClicked(evt);
            }
        });

        menuCustomer.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menuCustomer.setForeground(new java.awt.Color(20, 108, 148));
        menuCustomer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicon/customer.png"))); // NOI18N
        menuCustomer.setText("CUSTOMER");
        menuCustomer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        menuCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuCustomer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuCustomer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuCustomerMouseClicked(evt);
            }
        });

        menuStock.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menuStock.setForeground(new java.awt.Color(20, 108, 148));
        menuStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicon/stock.png"))); // NOI18N
        menuStock.setText("STOCK");
        menuStock.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        menuStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuStock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuStock.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuStockMouseClicked(evt);
            }
        });

        menuPenjualan.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menuPenjualan.setForeground(new java.awt.Color(20, 108, 148));
        menuPenjualan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicon/penjualan.png"))); // NOI18N
        menuPenjualan.setText("PENJUALAN");
        menuPenjualan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        menuPenjualan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPenjualan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuPenjualan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuPenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPenjualanMouseClicked(evt);
            }
        });

        menuLaporan.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menuLaporan.setForeground(new java.awt.Color(20, 108, 148));
        menuLaporan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicon/laporan.png"))); // NOI18N
        menuLaporan.setText("LAPORAN");
        menuLaporan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        menuLaporan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuLaporan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuLaporan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuLaporanMouseClicked(evt);
            }
        });

        menuUser.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menuUser.setForeground(new java.awt.Color(20, 108, 148));
        menuUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuicon/users.png"))); // NOI18N
        menuUser.setText("USER");
        menuUser.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        menuUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
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
            .addGroup(menuBoxLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(manuHome, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuStock, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuUser, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuBoxLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {manuHome, menuCustomer, menuLaporan, menuPenjualan, menuStock, menuUser});

        menuBoxLayout.setVerticalGroup(
            menuBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(manuHome, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuStock, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuUser, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mainContainer.setBackground(new java.awt.Color(255, 255, 255));
        mainContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainContainer.setMinimumSize(new java.awt.Dimension(756, 435));
        mainContainer.setPreferredSize(new java.awt.Dimension(756, 435));
        mainContainer.setLayout(new javax.swing.BoxLayout(mainContainer, javax.swing.BoxLayout.LINE_AXIS));

        homePanel.setBackground(java.awt.Color.orange);
        homePanel.setMinimumSize(new java.awt.Dimension(756, 435));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 48)); // NOI18N
        jLabel1.setForeground(java.awt.Color.darkGray);
        jLabel1.setLabelFor(homePanel);
        jLabel1.setText("JAVA");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 64)); // NOI18N
        jLabel2.setForeground(java.awt.Color.red);
        jLabel2.setLabelFor(homePanel);
        jLabel2.setText("SALES TRACKING");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 64)); // NOI18N
        jLabel3.setForeground(java.awt.Color.red);
        jLabel3.setLabelFor(homePanel);
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
                .addContainerGap(101, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        mainContainer.add(homePanel);

        footer.setBackground(java.awt.Color.orange);
        footer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LoginSession.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        LoginSession.setForeground(new java.awt.Color(20, 108, 148));
        LoginSession.setText("Login Session");

        infoName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        infoName.setForeground(new java.awt.Color(20, 108, 148));
        infoName.setText("Username:");

        infoRole.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        infoRole.setForeground(new java.awt.Color(20, 108, 148));
        infoRole.setText("Role:");

        infoDate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        infoDate.setForeground(new java.awt.Color(20, 108, 148));
        infoDate.setText("Date:");

        infoTime.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        infoTime.setForeground(new java.awt.Color(20, 108, 148));
        infoTime.setText("Time:");

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
                        .addGap(85, 85, 85)
                        .addComponent(infoRole)
                        .addGap(60, 60, 60)
                        .addComponent(infoDate)
                        .addGap(120, 120, 120)
                        .addComponent(infoTime)))
                .addContainerGap())
        );
        footerLayout.setVerticalGroup(
            footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LoginSession)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(footerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(infoName)
                    .addComponent(infoRole)
                    .addComponent(infoDate)
                    .addComponent(infoTime))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
                    .addComponent(menuBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(footer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 374, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(footer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuStockMouseClicked
        setMainContainer("stock");
    }//GEN-LAST:event_menuStockMouseClicked

    private void menuUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuUserMouseClicked
        setMainContainer("users");
    }//GEN-LAST:event_menuUserMouseClicked

    private void menuCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCustomerMouseClicked
        setMainContainer("customer");
    }//GEN-LAST:event_menuCustomerMouseClicked

    private void manuHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manuHomeMouseClicked
        setMainContainer("home");
    }//GEN-LAST:event_manuHomeMouseClicked

    private void menuPenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPenjualanMouseClicked
        setMainContainer("penjualan");
    }//GEN-LAST:event_menuPenjualanMouseClicked

    private void menuLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuLaporanMouseClicked
        setMainContainer("laporan");
    }//GEN-LAST:event_menuLaporanMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

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
    private javax.swing.JPanel mainContainer;
    private javax.swing.JLabel manuHome;
    private javax.swing.JPanel menuBox;
    private javax.swing.JLabel menuCustomer;
    private javax.swing.JLabel menuLaporan;
    private javax.swing.JLabel menuPenjualan;
    private javax.swing.JLabel menuStock;
    private javax.swing.JLabel menuUser;
    // End of variables declaration//GEN-END:variables
}
