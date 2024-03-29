package sistem;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import customization.cResetter;
import koneksi.database;
import koneksi.loginSession;
import java.awt.Color;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author thowie
 */
public final class Login extends javax.swing.JFrame {

    int xx, yy;

    private Connection myconn;
    private Statement mystmt;
    private PreparedStatement pstmt;

    public void loadDBUsers() {
        try {
            myconn = (Connection) koneksi.database.dbConfig();
            mystmt = myconn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(50) NOT NULL UNIQUE, "
                    + "password VARCHAR(50) NOT NULL, "
                    + "role VARCHAR(50) NOT NULL)";
            mystmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (mystmt != null) {
                    mystmt.close();
                }
                if (myconn != null) {
                    myconn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasUsers() {
        boolean hasRows = false;
        try {
            myconn = (Connection) koneksi.database.dbConfig();
            mystmt = myconn.createStatement();

            String sql = "SELECT COUNT(*) FROM users";
            ResultSet rs = mystmt.executeQuery(sql);

            if (rs.next()) {
                int rowCount = rs.getInt(1);
                hasRows = rowCount > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (mystmt != null) {
                    mystmt.close();
                }
                if (myconn != null) {
                    myconn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return hasRows;
    }

    public void createFirstUser() {
        JOptionPane.showMessageDialog(null, """
                    Sepertinya ini pertama kalinya Anda menggunakan aplikasi 'Sales Tracking System' ini. 
                    Mari buat akun master (owner) terlebih dahulu!""", "Selamat datang!", JOptionPane.INFORMATION_MESSAGE);

        String username = "";
        String password = "";
        boolean validUsername = false;
        boolean validPassword = false;

        while (!validUsername) {
            username = JOptionPane.showInputDialog(null, "Masukkan username:").trim();

            if (username == null || username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username tidak boleh kosong. "
                        + "Silakan coba lagi.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (username.contains(" ")) {
                JOptionPane.showMessageDialog(null, "Username tidak boleh mengandung spasi. "
                        + "Silakan coba lagi.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                validUsername = true;
            }
        }

        while (!validPassword) {
            password = JOptionPane.showInputDialog(null, "Masukkan password:").trim();

            if (password == null || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Password tidak boleh kosong. "
                        + "Silakan coba lagi.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                validPassword = true;
            }
        }

        try {
            myconn = (Connection) koneksi.database.dbConfig();
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            pstmt = myconn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, "owner");
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "User berhasil dibuat.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat membuat user.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (myconn != null) {
                    myconn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void logIn() {
        boolean usernameInvalid;
        boolean passwordInvalid;

        char[] passChar = fieldPassword.getPassword();
        String pass = new String(passChar);

        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            java.sql.Connection conn = (java.sql.Connection) database.dbConfig();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fieldUsername.getText());
            stmt.setString(2, pass);
            java.sql.ResultSet res = stmt.executeQuery();

            if (res.next()) {
                loginSession.setUsername(res.getString("username"));
                loginSession.setPassword(res.getString("password"));
                loginSession.setId(res.getString("id"));
                loginSession.setRole(res.getString("role"));

                setVisible(false);
                Home main = new Home();
                main.setVisible(true);

                JOptionPane.showMessageDialog(this,
                        "Selamat Datang " + fieldUsername.getText() + "!");

            } else {
                sql = "SELECT * FROM users WHERE username = ?";
                conn = (java.sql.Connection) database.dbConfig();
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, fieldUsername.getText());
                res = stmt.executeQuery();
                String pswd;
                if (res.next()) {
                    usernameInvalid = false;
                    pswd = res.getString("password");
                    passwordInvalid = !pswd.equals(pass);
                } else {
                    usernameInvalid = true;
                    passwordInvalid = true;
                }

                if (!usernameInvalid && passwordInvalid) {
                    // If the username is valid but the password is invalid, set passwordInvalid to true
                    sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                    conn = (java.sql.Connection) database.dbConfig();
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, fieldUsername.getText());
                    stmt.setString(2, pass);
                    res = stmt.executeQuery();
                    passwordInvalid = !res.next();
                }

                if (usernameInvalid && passwordInvalid || usernameInvalid && !passwordInvalid) {
                    JOptionPane.showMessageDialog(this, "Username tidak valid!");
                }
                if (passwordInvalid && !usernameInvalid) {
                    JOptionPane.showMessageDialog(this, "Password tidak valid!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Koneksi gagal " + e.getMessage());
        }
    }

    private void glassyLoad() {
        this.hidePass.setVisible(false);
        this.exitNow.setBackground(new Color(0, 0, 0, 0));
        this.eyeContainer.setOpaque(false);
        this.showPass.setBackground(new Color(0, 0, 0, 0));
        this.hidePass.setBackground(new Color(0, 0, 0, 0));
    }

    public void init() {
        loginPanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Login.background;");
        fieldPassword.putClientProperty(FlatClientProperties.STYLE, ""
                + "showCapsLock:true;");
        btn_login.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Login.loginButton.background;"
                + "foreground:$Login.loginButton.foreground;"
                + "hoverBackground:$Login.loginButton.hover;"
                + "pressedBackground:$Login.loginButton.pressed;"
                + "arc:10;");
        JButton[] btns = new JButton[]{exitNow, hidePass, showPass};
        for (var btn : btns) {
            btn.putClientProperty(FlatClientProperties.STYLE, ""
                    + "background:$Button.default.background;"
                    + "foreground:$Button.default.foreground;"
                    + "hoverBackground:$Button.default.hover;"
                    + "pressedBackground:$Button.default.pressed;"
                    + "borderWidth:0;");
        }
        fieldUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Username...");
        fieldPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password...");
    }

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        init();
        glassyLoad();
        loadDBUsers();
        if (!hasUsers()) {
            createFirstUser();
        }
        new cResetter().setUsername(new JTextField[]{fieldUsername});
        fieldUsername.requestFocus();
        customization.cSVG svg = new customization.cSVG();
        svg.menuIconSet(usernameIcon, "icon/login/Username.svg");
        svg.menuIconSet(passwordIcon, "icon/login/Password.svg");
        svg.menuIconSet(profileIcon, "icon/login/Profile.svg");
        svg.btnIconSet(btn_login, "icon/login/Login.svg");
        svg.btnIconSet(exitNow, "icon/login/Cancel.svg");
        svg.btnIconColorSet(hidePass, "icon/login/Invisible.svg", btn_login.getBackground());
        hidePass.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Login.hidePassActive;"
                + "hoverBackground:$Login.hidePassActive;"
                + "pressedBackground:$Login.hidePassActive;"
                + "arc:5;");
        svg.btnIconColorSet(showPass, "icon/login/Visible.svg", btn_login.getBackground());
        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(rootPane);
        svg.frameIcon(window, "icon/Towdio.svg");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginPanel = new javax.swing.JPanel();
        exitNow = new javax.swing.JButton();
        profileIcon = new javax.swing.JLabel();
        fieldUsername = new javax.swing.JTextField();
        btn_login = new javax.swing.JButton();
        usernameIcon = new javax.swing.JLabel();
        passwordIcon = new javax.swing.JLabel();
        fieldPassword = new javax.swing.JPasswordField();
        eyeContainer = new javax.swing.JPanel();
        hidePass = new javax.swing.JButton();
        showPass = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        exitNow.setBackground(new java.awt.Color(241, 255, 255));
        exitNow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/cancel.png"))); // NOI18N
        exitNow.setBorder(null);
        exitNow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitNow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitNowActionPerformed(evt);
            }
        });

        profileIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profileIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/profile.png"))); // NOI18N

        fieldUsername.setOpaque(true);
        fieldUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldUsernameActionPerformed(evt);
            }
        });

        btn_login.setBackground(new java.awt.Color(0, 75, 173));
        btn_login.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        btn_login.setForeground(new java.awt.Color(255, 255, 255));
        btn_login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/login.png"))); // NOI18N
        btn_login.setText("LOGIN");
        btn_login.setBorderPainted(false);
        btn_login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_login.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        usernameIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/username.png"))); // NOI18N

        passwordIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        passwordIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/password.png"))); // NOI18N

        fieldPassword.setOpaque(true);
        fieldPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldPasswordActionPerformed(evt);
            }
        });

        eyeContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        hidePass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/hidePass.png"))); // NOI18N
        hidePass.setBorderPainted(false);
        hidePass.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hidePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hidePassActionPerformed(evt);
            }
        });
        eyeContainer.add(hidePass, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 25, 25));

        showPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/showPass.png"))); // NOI18N
        showPass.setBorderPainted(false);
        showPass.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPassActionPerformed(evt);
            }
        });
        eyeContainer.add(showPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 25, 25));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Selamat Datang Orang Sukses!");

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, loginPanelLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(profileIcon)
                    .addComponent(jLabel2)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordIcon)
                            .addComponent(usernameIcon))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eyeContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60))
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(exitNow)
                .addContainerGap())
        );

        loginPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {fieldPassword, fieldUsername});

        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(exitNow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginPanelLayout.createSequentialGroup()
                        .addContainerGap(33, Short.MAX_VALUE)
                        .addComponent(profileIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12)))
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(usernameIcon)
                    .addComponent(fieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(eyeContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        loginPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {fieldPassword, fieldUsername});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - yy);
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xx = evt.getX();
        yy = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void exitNowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitNowActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitNowActionPerformed

    private void fieldPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldPasswordActionPerformed
        logIn();
    }//GEN-LAST:event_fieldPasswordActionPerformed

    private void showPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPassActionPerformed
        this.hidePass.setVisible(true);
        this.showPass.setVisible(false);
        fieldPassword.setEchoChar((char) 0);
    }//GEN-LAST:event_showPassActionPerformed

    private void hidePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hidePassActionPerformed
        this.showPass.setVisible(true);
        this.hidePass.setVisible(false);
        fieldPassword.setEchoChar('*');
    }//GEN-LAST:event_hidePassActionPerformed

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        logIn();
    }//GEN-LAST:event_btn_loginActionPerformed

    private void fieldUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldUsernameActionPerformed
        fieldPassword.requestFocus();
    }//GEN-LAST:event_fieldUsernameActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightLaf.registerCustomDefaultsSource("customization");
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JButton exitNow;
    private javax.swing.JPanel eyeContainer;
    private javax.swing.JPasswordField fieldPassword;
    private javax.swing.JTextField fieldUsername;
    private javax.swing.JButton hidePass;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JLabel passwordIcon;
    private javax.swing.JLabel profileIcon;
    private javax.swing.JButton showPass;
    private javax.swing.JLabel usernameIcon;
    // End of variables declaration//GEN-END:variables
}
