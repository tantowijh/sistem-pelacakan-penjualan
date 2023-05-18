/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package sistem;

import java.sql.*;
import java.time.LocalDate;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author thowie
 */
public class Penjualan extends javax.swing.JPanel {

    private int selectedID = 0;
    private int selectedQuantity = 0;
    private String selectedDate = null;

    private void loadCustomerIDs(JComboBox<String> customerAndID) {
        customerAndID.removeAllItems();
        try (Connection conn = koneksi.database.dbConfig()) {
            String query = "SELECT customer_id FROM customers";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        customerAndID.addItem(rs.getString("customer_id"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getCustomerName(JComboBox<String> customerAndID) {
        String name = "";
        Object selectedItem = customerAndID.getSelectedItem();
        if (selectedItem != null) {
            try (Connection conn = koneksi.database.dbConfig()) {
                String query = "SELECT first_name, last_name FROM customers WHERE customer_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, selectedItem.toString());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            name = rs.getString("first_name") + " " + rs.getString("last_name");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return name;
    }

    private void loadProductKodes(JComboBox<String> kodeAndProduct) {
        kodeAndProduct.removeAllItems();
        try (Connection conn = koneksi.database.dbConfig()) {
            String query = "SELECT kode FROM stock_penjualan";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        kodeAndProduct.addItem(rs.getString("kode"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getProductName(JComboBox<String> kodeAndProduct) {
        String produk = "";
        Object selectedItem = kodeAndProduct.getSelectedItem();
        if (selectedItem != null) {
            try (Connection conn = koneksi.database.dbConfig()) {
                String query = "SELECT produk FROM stock_penjualan WHERE kode = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, selectedItem.toString());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            produk = rs.getString("produk");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return produk;
    }

    public int loadStocks() {
        int stock = 0;
        Object selectedItem = kodeAndProduct.getSelectedItem();
        if (selectedItem != null) {
            try (Connection conn = koneksi.database.dbConfig()) {
                String query = "SELECT stock FROM stock_penjualan WHERE kode = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, selectedItem.toString());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            stock = Integer.parseInt(rs.getString("stock"));
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return stock;
    }

    public double loadHarga() {
        double harga = 0;
        Object selectedItem = kodeAndProduct.getSelectedItem();
        if (selectedItem != null) {
            try (Connection conn = koneksi.database.dbConfig()) {
                String query = "SELECT harga FROM stock_penjualan WHERE kode = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, selectedItem.toString());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            harga = Double.parseDouble(rs.getString("harga"));
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return harga;
    }

    public String loadPhone() {
        String phone = null;
        Object selectedItem = customerAndID.getSelectedItem();
        if (selectedItem != null) {
            try (Connection conn = koneksi.database.dbConfig()) {
                String query = "SELECT phone FROM customers WHERE customer_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, selectedItem.toString());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            phone = rs.getString("phone");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return phone;
    }

    public String loadEmail() {
        String email = null;
        Object selectedItem = customerAndID.getSelectedItem();
        if (selectedItem != null) {
            try (Connection conn = koneksi.database.dbConfig()) {
                String query = "SELECT email FROM customers WHERE customer_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, selectedItem.toString());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            email = rs.getString("email");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return email;
    }

    public String loadAddress() {
        String address = null;
        Object selectedItem = customerAndID.getSelectedItem();
        if (selectedItem != null) {
            try (Connection conn = koneksi.database.dbConfig()) {
                String query = "SELECT address FROM customers WHERE customer_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, selectedItem.toString());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            address = rs.getString("address");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return address;
    }

    SalesDatabase db = new SalesDatabase();

    private static DefaultTableModel setModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Date");
        model.addColumn("Amount");
        model.addColumn("Product");
        model.addColumn("Quantity");
        model.addColumn("Customer");
        model.addColumn("Payment Status");
        return model;
    }

    // cek jika ada data
    public static boolean checkIfDataExists() {
        boolean dataExists = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = (Connection) koneksi.database.dbConfig()) {
                String sql = "SELECT * FROM sales";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    dataExists = true;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
        return dataExists;
    }

    /**
     * Creates new form Penjualan
     */
    public Penjualan() {
        initComponents();
        loadCustomerIDs(customerAndID);
        loadProductKodes(kodeAndProduct);

        loadSales.setModel(setModel());
        // Set the width of the first column to 50 pixels
        TableColumnModel columnModel = loadSales.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMaxWidth(60);
        columnModel.getColumn(4).setMaxWidth(60);

        db.loadSalesTable((DefaultTableModel) loadSales.getModel());

        jumlahPenjualan.setText(String.valueOf(db.countRows()));
        totalPenjualan.setText(String.valueOf(db.calculateTotalAmount()));

        // Set the permission settings
        new customization.cResetter().setBlockedButton(new JButton[]{insertToDB, updateToDB, removeInDB});
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        loadSales = new javax.swing.JTable();
        customerAndID = new javax.swing.JComboBox<>();
        kodeAndProduct = new javax.swing.JComboBox<>();
        statusPembayaran = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jumlahPenjualan = new javax.swing.JLabel();
        totalPenjualan = new javax.swing.JLabel();
        insertToDB = new customization.cButton();
        updateToDB = new customization.cButton();
        removeInDB = new customization.cButton();
        productQuantity = new javax.swing.JTextField();
        productname = new javax.swing.JTextField();
        csfullname = new javax.swing.JTextField();
        searchVal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        hargaSatuan = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        hargaTotal = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        totalPenjualan1 = new javax.swing.JLabel();

        setBackground(java.awt.Color.orange);

        header.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(20, 108, 148));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PENJUALAN");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(243, 243, 243))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(20, 108, 148));
        jLabel2.setText("Nama Customer");

        loadSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        loadSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadSalesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(loadSales);
        if (loadSales.getColumnModel().getColumnCount() > 0) {
            loadSales.getColumnModel().getColumn(0).setMinWidth(50);
            loadSales.getColumnModel().getColumn(0).setPreferredWidth(50);
            loadSales.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        customerAndID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        customerAndID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerAndIDActionPerformed(evt);
            }
        });

        kodeAndProduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        kodeAndProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeAndProductActionPerformed(evt);
            }
        });

        statusPembayaran.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Paid", "Pending" }));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(20, 108, 148));
        jLabel3.setText("Nama Produk");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(20, 108, 148));
        jLabel4.setText("Quantity");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(20, 108, 148));
        jLabel5.setText("Status");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(20, 108, 148));
        jLabel6.setText("Total Penjualan: ");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(20, 108, 148));
        jLabel7.setText("Total Harga Penjualan: ");

        jumlahPenjualan.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jumlahPenjualan.setForeground(new java.awt.Color(20, 108, 148));
        jumlahPenjualan.setText("0");

        totalPenjualan.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        totalPenjualan.setForeground(new java.awt.Color(20, 108, 148));
        totalPenjualan.setText("0");

        insertToDB.setText("Insert");
        insertToDB.setCustomStrokeWidth(1);
        insertToDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertToDBActionPerformed(evt);
            }
        });

        updateToDB.setText("Update");
        updateToDB.setCustomCurrentFill(new java.awt.Color(0, 153, 0));
        updateToDB.setCustomHovering(new java.awt.Color(0, 102, 0));
        updateToDB.setCustomStrokeWidth(1);
        updateToDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateToDBActionPerformed(evt);
            }
        });

        removeInDB.setText("Remove");
        removeInDB.setCustomCurrentFill(new java.awt.Color(255, 0, 51));
        removeInDB.setCustomHovering(new java.awt.Color(153, 0, 0));
        removeInDB.setCustomStrokeWidth(1);
        removeInDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeInDBActionPerformed(evt);
            }
        });

        productQuantity.setText("1");
        productQuantity.setFocusTraversalKeysEnabled(false);
        productQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                productQuantityFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                productQuantityFocusLost(evt);
            }
        });
        productQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                productQuantityKeyReleased(evt);
            }
        });

        productname.setEditable(false);
        productname.setFocusable(false);

        csfullname.setEditable(false);
        csfullname.setFocusable(false);

        searchVal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchValKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(20, 108, 148));
        jLabel10.setText("Cari data");

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(20, 108, 148));
        jLabel11.setText("Harga Produk");

        hargaSatuan.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        hargaSatuan.setForeground(new java.awt.Color(20, 108, 148));
        hargaSatuan.setText("0");

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(20, 108, 148));
        jLabel12.setText("Harga Total");

        hargaTotal.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        hargaTotal.setForeground(new java.awt.Color(20, 108, 148));
        hargaTotal.setText("0");

        totalPenjualan1.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        totalPenjualan1.setForeground(new java.awt.Color(153, 0, 153));
        totalPenjualan1.setText("Reset Selection");
        totalPenjualan1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        totalPenjualan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                totalPenjualan1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(customerAndID, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(csfullname, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(kodeAndProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(productname, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(statusPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jumlahPenjualan)
                                .addGap(76, 76, 76)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalPenjualan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(totalPenjualan1))
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(insertToDB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(updateToDB, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .addComponent(removeInDB, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .addComponent(jLabel10)
                            .addComponent(searchVal)
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator3)
                            .addComponent(jLabel11)
                            .addComponent(hargaSatuan)
                            .addComponent(jSeparator5)
                            .addComponent(jLabel12)
                            .addComponent(hargaTotal)
                            .addComponent(jSeparator6))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(customerAndID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kodeAndProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csfullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hargaSatuan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hargaTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertToDB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateToDB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeInDB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jumlahPenjualan)
                    .addComponent(totalPenjualan)
                    .addComponent(totalPenjualan1))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void kodeAndProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeAndProductActionPerformed
        productname.setText(getProductName(kodeAndProduct));
        productQuantity.setToolTipText("Maksimal stock adalah " + loadStocks());
        hargaSatuan.setText(String.format("%,.2f", loadHarga()));
        hargaTotal.setText(String.format("%,.2f", loadHarga() * Integer.parseInt(productQuantity.getText())));
    }//GEN-LAST:event_kodeAndProductActionPerformed

    private void customerAndIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerAndIDActionPerformed
        csfullname.setText(getCustomerName(customerAndID));
    }//GEN-LAST:event_customerAndIDActionPerformed

    private void productQuantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_productQuantityKeyReleased
        int stock = loadStocks();
        if (!productQuantity.getText().isEmpty() && Integer.parseInt(productQuantity.getText()) > stock) {
            JOptionPane.showMessageDialog(null,
                    "Anda memasukkan jumlah stock yang melebihi " + stock + " stock tersedia!", "Peringatan", 1);
            productQuantity.setText("1");
        }
        if (!productQuantity.getText().isEmpty()) {
            hargaTotal.setText(String.format("%,.2f", loadHarga() * Integer.parseInt(productQuantity.getText())));
        }
    }//GEN-LAST:event_productQuantityKeyReleased

    private void productQuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_productQuantityFocusLost
        if (productQuantity.getText().isEmpty()) {
            productQuantity.setText("1");
        }
    }//GEN-LAST:event_productQuantityFocusLost

    private void productQuantityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_productQuantityFocusGained
        new customization.cResetter().setQuantity(productQuantity);
    }//GEN-LAST:event_productQuantityFocusGained

    private void insertToDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertToDBActionPerformed
        if (productQuantity.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pastikan mengisi jumlah produk dibeli!");
            return;
        }
        double amount = loadHarga() * Integer.parseInt(productQuantity.getText());
        String product = getProductName(kodeAndProduct);
        String product_id = kodeAndProduct.getSelectedItem().toString();
        int quantity = Integer.parseInt(productQuantity.getText());
        String customer = getCustomerName(customerAndID);
        String customer_id = customerAndID.getSelectedItem().toString();
        String status = statusPembayaran.getSelectedItem().toString();
        String currentDate = String.valueOf(LocalDate.now());
        
        db.insertSale(currentDate, amount, loadHarga(), product, product_id, 
                quantity, customer, customer_id, loadPhone(), 
                loadEmail(), loadAddress(), status);        

        DefaultTableModel model = (DefaultTableModel) loadSales.getModel();
        model.setRowCount(0);
        db.loadSalesTable(model);

        // mengurangi jumlah stock pada database
        db.decreaseStock(product_id, quantity);

        jumlahPenjualan.setText(String.valueOf(db.countRows()));
        totalPenjualan.setText(String.valueOf(db.calculateTotalAmount()));
    }//GEN-LAST:event_insertToDBActionPerformed

    private void updateToDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateToDBActionPerformed
        if (selectedID == 0) {
            JOptionPane.showMessageDialog(null, "Anda belum memilih penjualan!");
            return;
        }

        if (productQuantity.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pastikan mengisi jumlah produk dibeli!");
            return;
        }
        double amount = loadHarga() * Integer.parseInt(productQuantity.getText());
        String product = getProductName(kodeAndProduct);
        String product_id = kodeAndProduct.getSelectedItem().toString();
        int quantity = Integer.parseInt(productQuantity.getText());
        String customer = getCustomerName(customerAndID);
        String customer_id = customerAndID.getSelectedItem().toString();
        String status = statusPembayaran.getSelectedItem().toString();
        // String currentDate = String.valueOf(LocalDate.now());

        db.updateSale(selectedID, selectedDate, amount, loadHarga(), product, 
                product_id, quantity, customer, customer_id, loadPhone(), 
                loadEmail(), loadAddress(), status);

        DefaultTableModel model = (DefaultTableModel) loadSales.getModel();
        model.setRowCount(0);
        db.loadSalesTable(model);

        selectedID = 0;
        selectedDate = null;

        // pengurangan atau penambahan stock
        if (selectedQuantity < quantity) {
            int qtyToSubstract = quantity - selectedQuantity;
            db.decreaseStock(product_id, qtyToSubstract);
        }
        if (selectedQuantity > quantity) {
            int qtyToAdd = selectedQuantity - quantity;
            db.addStock(product_id, qtyToAdd);
        }
        selectedQuantity = 0;

        jumlahPenjualan.setText(String.valueOf(db.countRows()));
        totalPenjualan.setText(String.valueOf(db.calculateTotalAmount()));
    }//GEN-LAST:event_updateToDBActionPerformed

    private void loadSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loadSalesMouseClicked
        if (checkIfDataExists()) {
            int baris = loadSales.rowAtPoint(evt.getPoint());
            String id = loadSales.getValueAt(baris, 0).toString();
            selectedID = Integer.parseInt(id);

            Map<String, String> result = db.retrieveProductAndCustomerId(selectedID);
            String customerId = result.get("customer_id");
            String productId = result.get("product_id");
            String quantity = result.get("quantity");
            String date = result.get("date");
            selectedQuantity = Integer.parseInt(quantity);
            selectedDate = date;

            customerAndID.setSelectedItem(customerId);
            kodeAndProduct.setSelectedItem(productId);
            productQuantity.setText(quantity);
        }
    }//GEN-LAST:event_loadSalesMouseClicked

    private void removeInDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeInDBActionPerformed
        if (selectedID == 0) {
            JOptionPane.showMessageDialog(null, "Anda belum memilih penjualan!");
            return;
        }

        db.deleteSale(selectedID);

        DefaultTableModel model = (DefaultTableModel) loadSales.getModel();
        model.setRowCount(0);
        db.loadSalesTable(model);

        selectedID = 0;

        // menambah jumlah stock pada database
        String product_id = kodeAndProduct.getSelectedItem().toString();
        db.addStock(product_id, selectedQuantity);
        selectedQuantity = 0;

        jumlahPenjualan.setText(String.valueOf(db.countRows()));
        totalPenjualan.setText(String.valueOf(db.calculateTotalAmount()));
    }//GEN-LAST:event_removeInDBActionPerformed

    private void searchValKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchValKeyReleased
        String srch = searchVal.getText();
        db.searchSalesTable(srch, (DefaultTableModel) loadSales.getModel());
    }//GEN-LAST:event_searchValKeyReleased

    private void totalPenjualan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalPenjualan1MouseClicked
        selectedID = 0;
        selectedQuantity = 0;
        searchVal.setText(null);
        DefaultTableModel model = (DefaultTableModel) loadSales.getModel();
        model.setRowCount(0);
        db.loadSalesTable(model);
    }//GEN-LAST:event_totalPenjualan1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField csfullname;
    private javax.swing.JComboBox<String> customerAndID;
    private javax.swing.JLabel hargaSatuan;
    private javax.swing.JLabel hargaTotal;
    private javax.swing.JPanel header;
    private customization.cButton insertToDB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel jumlahPenjualan;
    private javax.swing.JComboBox<String> kodeAndProduct;
    private javax.swing.JTable loadSales;
    private javax.swing.JTextField productQuantity;
    private javax.swing.JTextField productname;
    private customization.cButton removeInDB;
    private javax.swing.JTextField searchVal;
    private javax.swing.JComboBox<String> statusPembayaran;
    private javax.swing.JLabel totalPenjualan;
    private javax.swing.JLabel totalPenjualan1;
    private customization.cButton updateToDB;
    // End of variables declaration//GEN-END:variables
}
