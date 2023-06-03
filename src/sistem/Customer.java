/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package sistem;

import com.formdev.flatlaf.FlatClientProperties;
import java.util.HashSet;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thowie
 */
public final class Customer extends javax.swing.JPanel {

    private String selectedID = null;
    private static HashSet<String> existingIDs;
    private static HashSet<String> existingPhones;
    private static HashSet<String> existingFirstNames;
    private static HashSet<String> existingLastNames;
    private static HashSet<String> existingEmails;
    private static HashSet<String> existingAddresses;

    // retrieve customer id on the database to prevent adding the same id
    private void existingValuesLoader() {
        // Clearing the exixtingValues
        if (existingIDs != null) {
            existingIDs.clear();
        }
        if (existingPhones != null) {
            existingPhones.clear();
        }
        if (existingFirstNames != null) {
            existingFirstNames.clear();
        }
        if (existingLastNames != null) {
            existingLastNames.clear();
        }
        if (existingEmails != null) {
            existingEmails.clear();
        }
        if (existingAddresses != null) {
            existingAddresses.clear();
        }

        // Initialize the existingValues set by loading the IDs from the database
        existingIDs = new HashSet<>();
        existingPhones = new HashSet<>();
        existingFirstNames = new HashSet<>();
        existingLastNames = new HashSet<>();
        existingEmails = new HashSet<>();
        existingAddresses = new HashSet<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = (Connection) koneksi.database.dbConfig()) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM customers");

                // Membaca hasil dari ResultSet dan menyimpan hasilnya ke dalam ArrayList
                while (rs.next()) {
                    existingIDs.add(rs.getString("customer_id"));
                    existingFirstNames.add(rs.getString("first_name"));
                    existingLastNames.add(rs.getString("last_name"));
                    existingPhones.add(rs.getString("phone"));
                    existingEmails.add(rs.getString("email"));
                    existingAddresses.add(rs.getString("address"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Generate the next customer ID
    public String generateCustomerID() {
        String prefix = "CS";
        int num = 1;
        while (true) {
            String code = prefix + String.format("%03d", num);
            if (!existingIDs.contains(code)) {
                existingIDs.add(code);
                return code;
            }
            num++;
        }
    }

    // customer counter
    public void startCounting(JTable customerTable) {
        populateCustomerTable(customerTable);

        try (Connection conn = (Connection) koneksi.database.dbConfig(); 
                PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS customerCount FROM customers"); 
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int count = rs.getInt("customerCount");
                custCountDisplay.setText("Customer Count: " + String.valueOf(count));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // loading cutomer into table
    public static void populateCustomerTable(JTable customerTable) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Create the "customers" table if it does not exist
            try (Connection conn = (Connection) koneksi.database.dbConfig()) {
                // Create the "customers" table if it does not exist
                String sql = """
                                         CREATE TABLE IF NOT EXISTS customers (
                                             customer_id VARCHAR(10) NOT NULL PRIMARY KEY,
                                             first_name VARCHAR(50) NOT NULL,
                                             last_name VARCHAR(50) NOT NULL,
                                             phone VARCHAR(100) NOT NULL,
                                             email VARCHAR(100) NOT NULL,
                                             address VARCHAR(200) NOT NULL
                                         );""";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                // Load the customer data from the "customers" table
                sql = "SELECT * FROM customers";
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                // Create a table model with the customer data
                DefaultTableModel model = new DefaultTableModel();
                String[] columnNames = {"Cutomer ID", "First Name", "Last Name", "Phone", "Email", "Address"};
                for (int i = 0; i <= meta.getColumnCount() - 1; i++) {
                    model.addColumn(columnNames[i]);
                }
                while (rs.next()) {
                    Object[] rowData = new Object[meta.getColumnCount()];
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        rowData[i - 1] = rs.getObject(i);
                    }
                    model.addRow(rowData);
                }   // Set the model of the customerTable
                customerTable.setModel(model);
                // Close the connection
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    // insert customer data into database 
    public static void insertCustomerData(String customerID, String firstName, String lastName, String phone, String email, String address, JTable customerTable) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = (Connection) koneksi.database.dbConfig()) {
                // Insert the customer data into the "customers" table
                String sql = "INSERT INTO customers (customer_id, first_name, last_name, phone, email, address) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, customerID);
                pstmt.setString(2, firstName);
                pstmt.setString(3, lastName);
                pstmt.setString(4, email);
                pstmt.setString(5, phone);
                pstmt.setString(6, address);
                pstmt.executeUpdate();
                // Load the customer data from the "customers" table
                sql = "SELECT * FROM customers";
                ResultSet rs = pstmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                // Create a table model with the customer data
                DefaultTableModel model = new DefaultTableModel();
                String[] columnNames = {"Customer ID", "First Name", "Last Name", "Phone", "Email", "Address"};
                for (int i = 0; i <= meta.getColumnCount() - 1; i++) {
                    model.addColumn(columnNames[i]);
                }
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Customer berhasil ditambahkan!");
                }
                while (rs.next()) {
                    Object[] rowData = new Object[meta.getColumnCount()];
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        rowData[i - 1] = rs.getObject(i);
                    }
                    model.addRow(rowData);
                }
                // Set the model of the customerTable
                customerTable.setModel(model);
                // Close the connection
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    // update customer data in database
    public static void updateCustomerData(String customerID, String firstName, String lastName, String phone, String email, String address, JTable customerTable) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = (Connection) koneksi.database.dbConfig()) {
                // Update the customer data in the "customers" table
                String sql = "UPDATE customers SET first_name = ?, last_name = ?, phone = ?, email = ?, address = ? WHERE customer_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, phone);
                pstmt.setString(4, email);
                pstmt.setString(5, address);
                pstmt.setString(6, customerID);
                pstmt.executeUpdate();
                // Load the customer data from the "customers" table
                sql = "SELECT * FROM customers";
                ResultSet rs = pstmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                // Create a table model with the customer data
                DefaultTableModel model = new DefaultTableModel();
                String[] columnNames = {"Customer ID", "First Name", "Last Name", "Phone", "Email", "Address"};
                for (int i = 0; i <= meta.getColumnCount() - 1; i++) {
                    model.addColumn(columnNames[i]);
                }
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Customer berhasil diupdate!");
                }
                while (rs.next()) {
                    Object[] rowData = new Object[meta.getColumnCount()];
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        rowData[i - 1] = rs.getObject(i);
                    }
                    model.addRow(rowData);
                }
                // Set the model of the customerTable
                customerTable.setModel(model);
                // Close the connection
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    public static void deleteCustomerData(String customerID, JTable customerTable) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = (Connection) koneksi.database.dbConfig()) {
                // Delete the customer data from the "customers" table
                String sql = "DELETE FROM customers WHERE customer_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, customerID);
                pstmt.executeUpdate();
                // Load the customer data from the "customers" table
                sql = "SELECT * FROM customers";
                ResultSet rs = pstmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                // Create a table model with the customer data
                DefaultTableModel model = new DefaultTableModel();
                String[] columnNames = {"Customer ID", "First Name", "Last Name", "Phone", "Email", "Address"};
                for (int i = 0; i <= meta.getColumnCount() - 1; i++) {
                    model.addColumn(columnNames[i]);
                }
                while (rs.next()) {
                    Object[] rowData = new Object[meta.getColumnCount()];
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        rowData[i - 1] = rs.getObject(i);
                    }
                    model.addRow(rowData);
                }
                // Set the model of the customerTable
                customerTable.setModel(model);
                // Show message dialog to indicate that a customer has been deleted
                JOptionPane.showMessageDialog(null, "Customer berhasil dihapus!");
                // Close the connection
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    // cek jika ada data
    public static boolean checkIfDataExists() {
        boolean dataExists = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = (Connection) koneksi.database.dbConfig()) {
                String sql = "SELECT * FROM customers";
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

    // mencari customer
    public static void searchCustomerData(String searchQuery, JTable customerTable) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = (Connection) koneksi.database.dbConfig()) {
                // Search for the customer data in the "customers" table
                String sql = "SELECT * FROM customers WHERE customer_id LIKE ? "
                        + "OR first_name LIKE ? OR last_name LIKE ? "
                        + "OR phone LIKE ? OR email LIKE ? OR address LIKE ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + searchQuery + "%");
                pstmt.setString(2, "%" + searchQuery + "%");
                pstmt.setString(3, "%" + searchQuery + "%");
                pstmt.setString(4, "%" + searchQuery + "%");
                pstmt.setString(5, "%" + searchQuery + "%");
                pstmt.setString(6, "%" + searchQuery + "%");
                ResultSet rs = pstmt.executeQuery();
                ResultSetMetaData meta = rs.getMetaData();
                // Create a table model with the customer data
                DefaultTableModel model = new DefaultTableModel();
                String[] columnNames = {"Customer ID", "First Name", "Last Name", "Phone", "Email", "Address"};
                for (int i = 0; i <= meta.getColumnCount() - 1; i++) {
                    model.addColumn(columnNames[i]);
                }
                while (rs.next()) {
                    Object[] rowData = new Object[meta.getColumnCount()];
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        rowData[i - 1] = rs.getObject(i);
                    }
                    model.addRow(rowData);
                }
                // Set the model of the customerTable
                customerTable.setModel(model);
                // Close the connection
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    /**
     * Creates new form Customer
     */
    public Customer() {
        initComponents();
        startCounting(customerTable);
        existingValuesLoader();
        new customization.cResetter().setPhoneAddress(new JTextField[]{csPhone});

        // Set the permission settings
        new customization.cResetter().setBlockedButton(new JButton[]{csAdd, csUp, csDel});
        
        title.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:$SalesTracking;"
                + "font: 70% bold $h00.font");
        custCountDisplay.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:$SalesTracking;");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        csfName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        csAddress = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        cslName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        csPhone = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        csAdd = new customization.cButton();
        csUp = new customization.cButton();
        csDel = new customization.cButton();
        csClear = new customization.cButton();
        csSearch = new javax.swing.JTextField();
        custSearch = new javax.swing.JLabel();
        custCountDisplay = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        csEmail = new javax.swing.JTextField();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        title.setBackground(new java.awt.Color(241, 255, 255));
        title.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("CUSTOMER");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addGap(243, 243, 243))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel3.setText("Customer Information");

        jLabel2.setText("First Name");

        jLabel5.setText("Address");

        csAddress.setColumns(10);
        csAddress.setRows(2);
        csAddress.setMaximumSize(new java.awt.Dimension(250, 100));
        jScrollPane1.setViewportView(csAddress);

        jLabel6.setText("Last Name");

        jLabel7.setText("Phone");

        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        customerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(customerTable);

        csAdd.setText("Add");
        csAdd.setCustomStrokeWidth(1);
        csAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csAddActionPerformed(evt);
            }
        });

        csUp.setText("Update");
        csUp.setCustomCurrentFill(new java.awt.Color(0, 153, 0));
        csUp.setCustomHovering(new java.awt.Color(0, 102, 0));
        csUp.setCustomStrokeWidth(1);
        csUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csUpActionPerformed(evt);
            }
        });

        csDel.setText("Delete");
        csDel.setCustomCurrentFill(new java.awt.Color(255, 0, 51));
        csDel.setCustomHovering(new java.awt.Color(153, 0, 0));
        csDel.setCustomStrokeWidth(1);
        csDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csDelActionPerformed(evt);
            }
        });

        csClear.setText("Clear");
        csClear.setCustomCurrentFill(new java.awt.Color(255, 102, 0));
        csClear.setCustomHovering(new java.awt.Color(204, 51, 0));
        csClear.setCustomStrokeWidth(1);
        csClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csClearActionPerformed(evt);
            }
        });

        csSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                csSearchKeyReleased(evt);
            }
        });

        custSearch.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        custSearch.setText("Search for customer data");

        custCountDisplay.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        custCountDisplay.setText("Customer Count : 0");

        jLabel8.setText("Email");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(custCountDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(custSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(csSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                                    .addComponent(csEmail)
                                    .addComponent(csPhone)
                                    .addComponent(cslName)
                                    .addComponent(csfName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(csAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(csUp, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(csDel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(csClear, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE))))
                        .addGap(10, 10, 10))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(csSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(custSearch))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(csfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cslName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(csPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(csEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(csClear, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csDel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csUp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(custCountDisplay))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void csAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csAddActionPerformed
        if (existingPhones.contains(csPhone.getText()) && existingEmails.contains(csEmail.getText()) && existingFirstNames.contains(csfName.getText())
                && existingLastNames.contains(cslName.getText()) && existingAddresses.contains(csAddress.getText())) {
            JOptionPane.showMessageDialog(null, "Customer dengan data yang sama persis sudah ada!");
        } else if (existingPhones.contains(csPhone.getText())) {
            JOptionPane.showMessageDialog(null, "Customer dengan telephone " + csPhone.getText() + " sudah ada!");
        } else if (existingEmails.contains(csEmail.getText())) {
            JOptionPane.showMessageDialog(null, "Customer dengan email " + csEmail.getText() + " sudah ada!");
        } else {
            // Add the new customer to the database
            if (csfName.getText().isEmpty() || csPhone.getText().isEmpty() || csEmail.getText().isEmpty() || csAddress.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Isi semua data informasi terlebih dahulu!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                insertCustomerData(generateCustomerID(), csfName.getText(), cslName.getText(), csEmail.getText(), csPhone.getText(), csAddress.getText(), customerTable);
            }
        }
        startCounting(customerTable);
        existingValuesLoader();
    }//GEN-LAST:event_csAddActionPerformed

    private void csUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csUpActionPerformed
        if (selectedID != null) {
            if (csfName.getText().isEmpty() || csPhone.getText().isEmpty() || csEmail.getText().isEmpty() || csAddress.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Isi semua data informasi terlebih dahulu!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                updateCustomerData(selectedID, csfName.getText(), cslName.getText(), csPhone.getText(), csEmail.getText(), csAddress.getText(), customerTable);
            }
            selectedID = null;
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        startCounting(customerTable);
        existingValuesLoader();
    }//GEN-LAST:event_csUpActionPerformed

    private void csDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csDelActionPerformed
        if (selectedID != null) {
            deleteCustomerData(selectedID, customerTable);
            selectedID = null;
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        startCounting(customerTable);
        existingValuesLoader();
    }//GEN-LAST:event_csDelActionPerformed

    private void csClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csClearActionPerformed
        var fields = new JTextField[]{csfName, cslName, csPhone, csEmail, csSearch};
        for (var field : fields) {
            field.setText(null);
        }
        selectedID = null;
        csAddress.setText(null);
        startCounting(customerTable);
    }//GEN-LAST:event_csClearActionPerformed

    private void customerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerTableMouseClicked
        if (checkIfDataExists()) {
            int baris = customerTable.rowAtPoint(evt.getPoint());
            String id = customerTable.getValueAt(baris, 0).toString();
            selectedID = id;
            String fname = customerTable.getValueAt(baris, 1).toString();
            csfName.setText(fname);
            String lname = customerTable.getValueAt(baris, 2).toString();
            cslName.setText(lname);
            String phone = customerTable.getValueAt(baris, 3).toString();
            csPhone.setText(phone);
            String email = customerTable.getValueAt(baris, 4).toString();
            csEmail.setText(email);
            String addr = customerTable.getValueAt(baris, 5).toString();
            csAddress.setText(addr);
        }
    }//GEN-LAST:event_customerTableMouseClicked

    private void csSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_csSearchKeyReleased
        String srch = csSearch.getText();
        searchCustomerData(srch, customerTable);
    }//GEN-LAST:event_csSearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private customization.cButton csAdd;
    private javax.swing.JTextArea csAddress;
    private customization.cButton csClear;
    private customization.cButton csDel;
    private javax.swing.JTextField csEmail;
    private javax.swing.JTextField csPhone;
    private javax.swing.JTextField csSearch;
    private customization.cButton csUp;
    private javax.swing.JTextField csfName;
    private javax.swing.JTextField cslName;
    private javax.swing.JLabel custCountDisplay;
    private javax.swing.JLabel custSearch;
    private javax.swing.JTable customerTable;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
