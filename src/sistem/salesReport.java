/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package sistem;

import com.toedter.calendar.JDateChooser;
import java.io.InputStream;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author thowie
 */
public final class salesReport extends javax.swing.JPanel {

    private Connection conn;
    private Statement stmt;

    public void loadSalesData(JTable table) {
        try {
            conn = (Connection) koneksi.database.dbConfig();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM sales";
            ResultSet rs = stmt.executeQuery(sql);

            // create a table model with column names and data from the result set
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Date");
            model.addColumn("Amount");
            model.addColumn("Price");
            model.addColumn("Product");
            model.addColumn("Product ID");
            model.addColumn("Quantity");
            model.addColumn("Customer");
            model.addColumn("Customer ID");
            model.addColumn("Customer Phone");
            model.addColumn("Customer Email");
            model.addColumn("Customer Address");
            model.addColumn("Payment Status");
            while (rs.next()) {
                Object[] row = new Object[13];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("date");
                String amount = new DecimalFormat("#,##0").format(rs.getDouble("amount"));
                row[2] = amount;
                String price = new DecimalFormat("#,##0").format(rs.getDouble("price"));
                row[3] = price;
                row[4] = rs.getString("product");
                row[5] = rs.getString("product_id");
                row[6] = rs.getInt("quantity");
                row[7] = rs.getString("customer");
                row[8] = rs.getString("customer_id");
                row[9] = rs.getString("customer_phone");
                row[10] = rs.getString("customer_email");
                row[11] = rs.getString("customer_address");
                row[12] = rs.getString("payment_status");
                model.addRow(row);
            }

            // set the table model to the table
            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // filter sales
    public void filterSalesData(JTable table, JDateChooser startDateChooser, JDateChooser endDateChooser, String paymentStatus) {
        try {
            conn = (Connection) koneksi.database.dbConfig();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startDateString = null;
            String endDateString = null;
            if (startDateChooser.getDate() != null) {
                startDateString = dateFormat.format(startDateChooser.getDate());
            }
            if (endDateChooser.getDate() != null) {
                endDateString = dateFormat.format(endDateChooser.getDate());
            }
            String sql = "SELECT * FROM sales";
            if (startDateString != null && endDateString != null) {
                sql += " WHERE date BETWEEN ? AND ?";
            } else if (startDateString != null) {
                sql += " WHERE date >= ?";
            } else if (endDateString != null) {
                sql += " WHERE date <= ?";
            }
            if (paymentStatus != null && !paymentStatus.isEmpty()) {
                if (startDateString != null || endDateString != null) {
                    sql += " AND payment_status=?";
                } else {
                    sql += " WHERE payment_status=?";
                }
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int paramIndex = 1;
            if (startDateString != null && endDateString != null) {
                pstmt.setString(paramIndex++, startDateString);
                pstmt.setString(paramIndex++, endDateString);
            } else if (startDateString != null) {
                pstmt.setString(paramIndex++, startDateString);
            } else if (endDateString != null) {
                pstmt.setString(paramIndex++, endDateString);
            }
            if (paymentStatus != null && !paymentStatus.isEmpty()) {
                pstmt.setString(paramIndex++, paymentStatus);
            }
            ResultSet rs = pstmt.executeQuery();

            // create a table model with column names and data from the result set
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Date");
            model.addColumn("Amount");
            model.addColumn("Price");
            model.addColumn("Product");
            model.addColumn("Product ID");
            model.addColumn("Quantity");
            model.addColumn("Customer");
            model.addColumn("Customer ID");
            model.addColumn("Customer Phone");
            model.addColumn("Customer Email");
            model.addColumn("Customer Address");
            model.addColumn("Payment Status");
            while (rs.next()) {
                Object[] row = new Object[13];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("date");
                String amount = new DecimalFormat("#,##0").format(rs.getDouble("amount"));
                row[2] = amount;
                String price = new DecimalFormat("#,##0").format(rs.getDouble("price"));
                row[3] = price;
                row[4] = rs.getString("product");
                row[5] = rs.getString("product_id");
                row[6] = rs.getInt("quantity");
                row[7] = rs.getString("customer");
                row[8] = rs.getString("customer_id");
                row[9] = rs.getString("customer_phone");
                row[10] = rs.getString("customer_email");
                row[11] = rs.getString("customer_address");
                row[12] = rs.getString("payment_status");
                model.addRow(row);
            }

            // set the table model to the table
            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Returns the earliest date in the sales table
    public static Date getEarliestDate(Connection conn) throws SQLException {
        String sql = "SELECT MIN(date) FROM sales";
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDate(1);
            } else {
                return null;
            }
        }
    }

    // Returns the latest date in the sales table
    public static Date getLatestDate(Connection conn) throws SQLException {
        String sql = "SELECT MAX(date) FROM sales";
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDate(1);
            } else {
                return null;
            }
        }
    }

    // set the start date to the earliest date in the sales table
// and the end date to the latest date in the sales table
    private void initialDate() {
        try {
            Date earliestDate = getEarliestDate(conn);
            Date latestDate = getLatestDate(conn);
            if (earliestDate != null && latestDate != null) {
                Calendar startCal = Calendar.getInstance();
                startCal.setTime(earliestDate);
                startDate.setCalendar(startCal);
                Calendar endCal = Calendar.getInstance();
                endCal.setTime(latestDate);
                endDate.setCalendar(endCal);
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                startDate.setCalendar(calendar);
                endDate.setCalendar(calendar);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            startDate.setCalendar(calendar);
            endDate.setCalendar(calendar);
        }
    }
    
    /**
     * Creates new form salesReport
     */
    public salesReport() {
        initComponents();
        loadSalesData(loadSalesReport);
        initialDate();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        loadSalesReport = new javax.swing.JTable();
        btnPrint = new customization.cButton();
        startDate = new com.toedter.calendar.JDateChooser();
        endDate = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        statusFilter = new javax.swing.JComboBox<>();
        btnFilter = new customization.cButton();
        btnReset = new customization.cButton();
        jLabel4 = new javax.swing.JLabel();

        setBackground(java.awt.Color.orange);

        header.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(20, 108, 148));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LAPORAN");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addGap(243, 243, 243))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        loadSalesReport.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(loadSalesReport);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnPrint.setText("Print Reports");
        btnPrint.setCustomCurrentFill(new java.awt.Color(153, 0, 204));
        btnPrint.setCustomHovering(new java.awt.Color(102, 0, 153));
        btnPrint.setCustomStrokeWidth(1);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(20, 108, 148));
        jLabel2.setText("From Date");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(20, 108, 148));
        jLabel3.setText("To Date");

        statusFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Paid", "Pending" }));

        btnFilter.setText("Filter");
        btnFilter.setCustomCurrentFill(new java.awt.Color(0, 153, 0));
        btnFilter.setCustomHovering(new java.awt.Color(51, 102, 0));
        btnFilter.setCustomStrokeWidth(1);
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        btnReset.setText("Reset Filter");
        btnReset.setCustomCurrentFill(new java.awt.Color(255, 102, 51));
        btnReset.setCustomHovering(new java.awt.Color(204, 51, 0));
        btnReset.setCustomStrokeWidth(1);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(20, 108, 148));
        jLabel4.setText("Status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(header, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(statusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = null;
        String endDateString = null;
        String status = null;
        if (startDate.getDate() != null) {
            startDateString = dateFormat.format(startDate.getDate());
        }
        if (endDate.getDate() != null) {
            endDateString = dateFormat.format(endDate.getDate());
        }
        if (!statusFilter.getSelectedItem().toString().equals("None")) {
            status = statusFilter.getSelectedItem().toString();
        }

        filterSalesData(loadSalesReport, startDate, endDate, status);

        String query = "SELECT * FROM sales";

        if (startDateString != null && endDateString != null) {
            query += " WHERE date BETWEEN '" + startDateString + "' AND '" + endDateString + "'";
        }

        if (status != null && !status.isEmpty()) {
            if (startDateString != null && endDateString != null) {
                query += " AND payment_status='" + status + "'";
            } else {
                query += " WHERE payment_status='" + status + "'";
            }
        }

        String path = "reports/salesreport.jrxml";
        InputStream jasperStream = getClass().getClassLoader().getResourceAsStream(path);
        try {
            JasperDesign jdesign = JRXmlLoader.load(jasperStream);

            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(query);

            jdesign.setQuery(updateQuery);

            JasperReport jreport = JasperCompileManager.compileReport(jdesign);

            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);

            // JasperViewer.viewReport(jprint);
            JasperViewer jviewer = new JasperViewer(jprint, false);
            jviewer.setTitle("Report - Sistem Pelacakan Penjualan");
            jviewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jviewer.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(salesReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        String status = statusFilter.getSelectedItem().toString();
        if (status.equals("None")) {
            status = null;
        }

        filterSalesData(loadSalesReport, startDate, endDate, status);
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        statusFilter.setSelectedItem("None");
        initialDate();
        loadSalesData(loadSalesReport);
    }//GEN-LAST:event_btnResetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private customization.cButton btnFilter;
    private customization.cButton btnPrint;
    private customization.cButton btnReset;
    private com.toedter.calendar.JDateChooser endDate;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable loadSalesReport;
    private com.toedter.calendar.JDateChooser startDate;
    private javax.swing.JComboBox<String> statusFilter;
    // End of variables declaration//GEN-END:variables
}
