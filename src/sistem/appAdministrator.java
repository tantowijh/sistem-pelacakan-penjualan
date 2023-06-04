/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package sistem;

import com.formdev.flatlaf.FlatClientProperties;
import customization.cResetter;
import java.awt.Color;
import koneksi.koneksiAdmin;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import koneksi.loginSession;

/**
 *
 * @author thowie
 */
public final class appAdministrator extends javax.swing.JPanel {

    String currentText;
    koneksiAdmin adminSession = new koneksiAdmin();
    int deleteUser = 0;

    public void loadAccess() {

        showUsername.setText(loginSession.getUsername().toUpperCase());
        if (!loginSession.getRole().equals("owner") && !loginSession.getRole().equals("admin")) {
            int add = userOptions.indexOfComponent(appUserManagement);
            userOptions.setEnabledAt(add, false);
        }

        if (!loginSession.getRole().equals("owner") && !loginSession.getRole().equals("admin") && !loginSession.getRole().equals("user")) {
            btnUpdate.setEnabled(false);
        }

        new cResetter().setUsername(new JTextField[]{adm_username});

    }

    public void setNull(JTextField textField, String nulledText) {
        textField.setForeground(Color.BLACK);
        currentText = textField.getText();
        if (currentText.equals(nulledText)) {
            textField.setText(null);
        }
    }

    public void setFill(JTextField textField, String textToSet) {
        currentText = textField.getText();
        if (currentText.isEmpty()) {
            textField.setForeground(Color.lightGray);
            textField.setText(textToSet);
        }
    }

    public void exceptEditing(JTable tbl, int columnIndex) {
        TableModel model;
        // Override the isCellEditable method to make the first column uneditable
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the first column uneditable
                return column != columnIndex;
            }
        };

        // Set the new model to the JTable instance
        tbl.setModel(model);
    }

    // Reset table to default state
    public void resetTable() {
        // Remove the listener from the checkbox
        editCheckbox.removeActionListener(editCheckbox.getActionListeners()[0]);
        loadAccess();
        adminSession.loadingUsers(loadUsers);

        // Refreshing edit checkBox
        editCheckbox.setSelected(false);
        editCheckbox.setEnabled(true);

        // Disable editing dari awal
        loadUsers.setEnabled(false);
        adm_delete.setEnabled(false);

        // Tambah listener checkbox untuk enable/disable editing
        editCheckbox.addActionListener((ActionEvent e) -> {
            boolean enabled = editCheckbox.isSelected();
            loadUsers.setEnabled(enabled);
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            adminSession.tableUpdater(loadUsers, frame);
            adm_delete.setEnabled(enabled);
        });
    }

    public void userRelogin() {
        if (!loginSession.getAccess()) {
            // Get the parent JFrame of the JPanel
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

            // Hide the current JFrame
            frame.setVisible(false);

            // Show the login again
            Login login = new Login();
            login.setVisible(true);
        }
    }

    /**
     * Creates new form appEmployee
     */
    public appAdministrator() {
        initComponents();
        // Add a MouseMotionListener to the JTabbedPane to set the cursor to the hand cursor
        userOptions.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int tabCount = userOptions.getTabCount();
                for (int i = 0; i < tabCount; i++) {
                    Rectangle tabBounds = userOptions.getBoundsAt(i);
                    if (tabBounds.contains(e.getPoint())) {
                        userOptions.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        return;
                    }
                }
                userOptions.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        loadAccess();
        adminSession.loadingUsers(loadUsers);

        // Disable editing dari awal
        loadUsers.setEnabled(false);
        adm_delete.setEnabled(false);

        // Tambah listener checkbox untuk enable/disable editing
        editCheckbox.addActionListener((ActionEvent e) -> {
            boolean enabled = editCheckbox.isSelected();
            loadUsers.setEnabled(enabled);
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(loadUsers);
            adminSession.tableUpdater(loadUsers, frame);
            adm_delete.setEnabled(enabled);
        });
        
        customization.cSVG svg = new customization.cSVG();
        svg.tabPanelIcon(userOptions, 0, "icon/user/User.svg");
        svg.tabPanelIcon(userOptions, 1, "icon/user/AddUser.svg");
        svg.labelIconColorSet(searchIcon, "icon/user/Search.svg", new Color(243,114,33));
        
        title.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:$SalesTracking;"
                + "font: 70% bold $h00.font");
        editCheckbox.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:$SalesTracking;");
        JLabel[] infos = new JLabel[]{showUsername,userDetail};
        for (var info : infos){
            info.putClientProperty(FlatClientProperties.STYLE, ""
                + "font: bold $h2.font");
        }
        JLabel[] labels = new JLabel[]{jLabel2,jLabel3,infoName1,infoName2,infoName3};
        for (var label : labels){
            label.putClientProperty(FlatClientProperties.STYLE, ""
                + "font: $semibold.font");
        }
        
        passNow.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan password sekarang...");
        passNew.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukkan password baru...");
        pencarianUser.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pencarian user...");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userOptions = new javax.swing.JTabbedPane();
        appUser = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        passNow = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        passNew = new javax.swing.JTextField();
        btnUpdate = new customization.cButton();
        showUsername = new javax.swing.JLabel();
        appUserManagement = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        loadUsers = new javax.swing.JTable();
        pencarianUser = new javax.swing.JTextField();
        userDetail = new javax.swing.JLabel();
        adm_username = new javax.swing.JTextField();
        userRoleOptions = new javax.swing.JComboBox<>();
        searchIcon = new javax.swing.JLabel();
        infoName1 = new javax.swing.JLabel();
        infoName2 = new javax.swing.JLabel();
        infoName3 = new javax.swing.JLabel();
        adm_password = new javax.swing.JTextField();
        adm_tambah = new customization.cButton();
        adm_reset = new customization.cButton();
        editCheckbox = new javax.swing.JCheckBox();
        adm_delete = new customization.cButton();
        header = new javax.swing.JPanel();
        title = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(756, 435));

        userOptions.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        userOptions.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Current Password");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("New Password");

        btnUpdate.setText("Update");
        btnUpdate.setCustomCurrentFill(new java.awt.Color(0, 51, 255));
        btnUpdate.setCustomHovering(new java.awt.Color(51, 51, 255));
        btnUpdate.setCustomPressedFill(new java.awt.Color(0, 0, 153));
        btnUpdate.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        showUsername.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        showUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        showUsername.setText("USERNAME");

        javax.swing.GroupLayout appUserLayout = new javax.swing.GroupLayout(appUser);
        appUser.setLayout(appUserLayout);
        appUserLayout.setHorizontalGroup(
            appUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, appUserLayout.createSequentialGroup()
                .addGap(274, 274, 274)
                .addComponent(showUsername)
                .addGap(299, 299, 299))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, appUserLayout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addGroup(appUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passNew, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                    .addComponent(passNow, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, appUserLayout.createSequentialGroup()
                        .addGroup(appUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(188, 188, 188))
        );
        appUserLayout.setVerticalGroup(
            appUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, appUserLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(showUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passNow, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passNew, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        appUserLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {passNew, passNow});

        userOptions.addTab("User", new javax.swing.ImageIcon(getClass().getResource("/menuicon/userInfo.png")), appUser); // NOI18N

        loadUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Username", "Password", "Role"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        loadUsers.setToolTipText("Jika editing mode aktif, semua data dapat diubah, kecuali id");
        loadUsers.getTableHeader().setReorderingAllowed(false);
        loadUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadUsersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(loadUsers);
        if (loadUsers.getColumnModel().getColumnCount() > 0) {
            loadUsers.getColumnModel().getColumn(0).setMinWidth(50);
            loadUsers.getColumnModel().getColumn(0).setPreferredWidth(55);
            loadUsers.getColumnModel().getColumn(0).setMaxWidth(70);
            loadUsers.getColumnModel().getColumn(3).setMinWidth(60);
            loadUsers.getColumnModel().getColumn(3).setPreferredWidth(80);
        }

        pencarianUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pencarianUserKeyReleased(evt);
            }
        });

        userDetail.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        userDetail.setText("User Details");

        userRoleOptions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user"}));

        searchIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Search.png"))); // NOI18N
        searchIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchIconMouseClicked(evt);
            }
        });

        infoName1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        infoName1.setText("Role");

        infoName2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        infoName2.setText("Username");

        infoName3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        infoName3.setText("Password");

        adm_tambah.setText("Add");
        adm_tambah.setToolTipText("Tambah user");
        adm_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adm_tambahActionPerformed(evt);
            }
        });

        adm_reset.setText("Reset");
        adm_reset.setToolTipText("Reset text field");
        adm_reset.setCustomCurrentFill(new java.awt.Color(255, 102, 51));
        adm_reset.setCustomHovering(new java.awt.Color(204, 102, 0));
        adm_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adm_resetActionPerformed(evt);
            }
        });

        editCheckbox.setText("Enter editing mode");
        editCheckbox.setToolTipText("Nyalakan fitur editing untuk mengubah data user pada tabel");
        editCheckbox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        adm_delete.setText("Delete");
        adm_delete.setToolTipText("Delete user");
        adm_delete.setCustomCurrentFill(new java.awt.Color(255, 0, 51));
        adm_delete.setCustomHovering(new java.awt.Color(204, 0, 51));
        adm_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adm_deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout appUserManagementLayout = new javax.swing.GroupLayout(appUserManagement);
        appUserManagement.setLayout(appUserManagementLayout);
        appUserManagementLayout.setHorizontalGroup(
            appUserManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, appUserManagementLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(appUserManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(appUserManagementLayout.createSequentialGroup()
                        .addComponent(userDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(appUserManagementLayout.createSequentialGroup()
                        .addGroup(appUserManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(infoName1)
                            .addComponent(infoName2)
                            .addComponent(infoName3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(appUserManagementLayout.createSequentialGroup()
                        .addGroup(appUserManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(adm_username)
                            .addComponent(adm_password)
                            .addGroup(appUserManagementLayout.createSequentialGroup()
                                .addComponent(adm_tambah, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(adm_reset, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(adm_delete, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                            .addComponent(userRoleOptions, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(40, 40, 40)))
                .addGroup(appUserManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                    .addGroup(appUserManagementLayout.createSequentialGroup()
                        .addComponent(editCheckbox)
                        .addGap(18, 18, 18)
                        .addComponent(pencarianUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchIcon)
                        .addGap(11, 11, 11)))
                .addContainerGap())
        );
        appUserManagementLayout.setVerticalGroup(
            appUserManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appUserManagementLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(appUserManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(appUserManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(pencarianUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(userDetail)
                        .addComponent(editCheckbox))
                    .addComponent(searchIcon))
                .addGroup(appUserManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(appUserManagementLayout.createSequentialGroup()
                        .addGroup(appUserManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(appUserManagementLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(infoName1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(userRoleOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(infoName2)
                                .addGap(3, 3, 3)
                                .addComponent(adm_username, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(infoName3)
                                .addGap(3, 3, 3)
                                .addComponent(adm_password, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(appUserManagementLayout.createSequentialGroup()
                                .addGap(201, 201, 201)
                                .addGroup(appUserManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(adm_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(adm_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(adm_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 75, Short.MAX_VALUE))
                    .addGroup(appUserManagementLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        userOptions.addTab("Manage User", new javax.swing.ImageIcon(getClass().getResource("/menuicon/addUser.png")), appUserManagement); // NOI18N

        header.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        title.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("ADMINISTRATOR");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(243, 243, 243))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userOptions, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userOptions)
                .addContainerGap())
        );

        userOptions.getAccessibleContext().setAccessibleName("tab1");
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        adminSession.changePass(new JTextField[]{passNow, passNew});

        // Relogin jika password berubah
        userRelogin();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void pencarianUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pencarianUserKeyReleased
        // Refreshing edit checkBox
        editCheckbox.setSelected(false);
        editCheckbox.setEnabled(false);

        // Disable editing dari awal
        loadUsers.setEnabled(false);
        adm_delete.setEnabled(false);

        adminSession.tableSearch(loadUsers, new JTextField[]{pencarianUser});

        if (pencarianUser.getText().isEmpty()) {
            resetTable();
        }
    }//GEN-LAST:event_pencarianUserKeyReleased

    private void adm_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adm_tambahActionPerformed
        try {
            adminSession.userInsert(userRoleOptions, adm_username, adm_password);
        } catch (SQLException ex) {
            Logger.getLogger(appAdministrator.class.getName()).log(Level.SEVERE, null, ex);
        }
        adminSession.loadingUsers(loadUsers);
    }//GEN-LAST:event_adm_tambahActionPerformed

    private void adm_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adm_resetActionPerformed
        new cResetter().resetFill(new JTextField[]{adm_username, adm_password, pencarianUser});
        resetTable();
    }//GEN-LAST:event_adm_resetActionPerformed

    private void adm_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adm_deleteActionPerformed
        try {
            koneksiAdmin.deleteUserFromDatabase(deleteUser);
        } catch (SQLException ex) {
            Logger.getLogger(appAdministrator.class.getName()).log(Level.SEVERE, null, ex);
        }
        deleteUser = 0;
        adminSession.loadingUsers(loadUsers);
    }//GEN-LAST:event_adm_deleteActionPerformed

    private void loadUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loadUsersMouseClicked
        boolean ya = adminSession.haveAData;
        if(!editCheckbox.isSelected()){
            return;
        }
        if (ya) {
            int baris = loadUsers.rowAtPoint(evt.getPoint());
            String idToDelete = loadUsers.getValueAt(baris, 0).toString();
            deleteUser = Integer.parseInt(idToDelete);
            String user = loadUsers.getValueAt(baris, 1).toString();
            adm_username.setText(user);
            String pass = loadUsers.getValueAt(baris, 2).toString();
            adm_password.setText(pass);
            String role = loadUsers.getValueAt(baris, 3).toString();
            userRoleOptions.setSelectedItem(role);
        }
    }//GEN-LAST:event_loadUsersMouseClicked

    private void searchIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchIconMouseClicked
        pencarianUser.requestFocus();
    }//GEN-LAST:event_searchIconMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private customization.cButton adm_delete;
    private javax.swing.JTextField adm_password;
    private customization.cButton adm_reset;
    private customization.cButton adm_tambah;
    private javax.swing.JTextField adm_username;
    private javax.swing.JPanel appUser;
    private javax.swing.JPanel appUserManagement;
    private customization.cButton btnUpdate;
    private javax.swing.JCheckBox editCheckbox;
    private javax.swing.JPanel header;
    private javax.swing.JLabel infoName1;
    private javax.swing.JLabel infoName2;
    private javax.swing.JLabel infoName3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable loadUsers;
    private javax.swing.JTextField passNew;
    private javax.swing.JTextField passNow;
    private javax.swing.JTextField pencarianUser;
    private javax.swing.JLabel searchIcon;
    private javax.swing.JLabel showUsername;
    private javax.swing.JLabel title;
    private javax.swing.JLabel userDetail;
    private javax.swing.JTabbedPane userOptions;
    private javax.swing.JComboBox<String> userRoleOptions;
    // End of variables declaration//GEN-END:variables
}
