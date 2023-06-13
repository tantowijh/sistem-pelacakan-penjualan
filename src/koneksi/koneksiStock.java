/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thowie
 */
public class koneksiStock {

    private boolean tabelTersedia;
    public String selectedKode;
    public boolean haveAData;

    // Field resetter
    public void resetFields(JTextField[] fields) {
        selectedKode = null;
        for (JTextField field : fields) {
            field.setText(null);
        }
    }

    public void setColumnWidth(JTable Tabel) {
        Tabel.getColumnModel().getColumn(0).setPreferredWidth(57);
        Tabel.getColumnModel().getColumn(0).setMaxWidth(80);
        Tabel.getColumnModel().getColumn(1).setPreferredWidth(267);
        Tabel.getColumnModel().getColumn(2).setPreferredWidth(102);
        Tabel.getColumnModel().getColumn(2).setMaxWidth(200);
        Tabel.getColumnModel().getColumn(3).setPreferredWidth(102);
        Tabel.getColumnModel().getColumn(3).setMaxWidth(200);
        Tabel.getColumnModel().getColumn(4).setPreferredWidth(210);
    }

    public void setInvalidWidth(JTable Tabel) {
        Tabel.getColumnModel().getColumn(0).setPreferredWidth(57);
        Tabel.getColumnModel().getColumn(0).setMaxWidth(80);
        Tabel.getColumnModel().getColumn(1).setPreferredWidth(200);
        Tabel.getColumnModel().getColumn(2).setPreferredWidth(350);
    }

    public boolean loadStockPenjualan(JTable loadingTable) throws ParseException {

        haveAData = false;

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Nama Produk");
        model.addColumn("Kode Produk");
        model.addColumn("Stock Produk");
        model.addColumn("Harga Produk");

        try (Connection cons = koneksi.database.dbConfig(); PreparedStatement stmts = cons.prepareStatement("SELECT * FROM stock_penjualan"); ResultSet res = stmts.executeQuery()) {

            int no = 1;
            while (res.next()) {
                haveAData = true;

                BigDecimal priceValue = new BigDecimal(res.getDouble(4));
                DecimalFormat df = new DecimalFormat("#,##0.00");
                String formattedPrice = df.format(priceValue);

                model.addRow(new Object[]{no++, res.getString(1), res.getString(2),
                    res.getString(3), formattedPrice});
            }
            loadingTable.setModel(model);

        } catch (SQLException e) {
            System.err.println("Gagal memuat table: " + e.getMessage());
        }
        return tabelTersedia = true;
    }

    /* Untuk memuat method ini, pastikan untuk mengisi semua textField
       yang diinginkan agar tidak kosong saat mengisi data
       pengisian juga harus sesuai urutan nama produk, kode produk, jumlah produk, harga produk
     */
    private boolean loadToEmpty(JTextField[] fields, JTable Tabel) {
        String[] toBeEmpty = new String[]{"Nama produk", "Jumlah produk", "Harga produk"};
        boolean anyFieldSetToNull = false;
        for (int i = 0; i < fields.length; i++) {
            JTextField field = fields[i];
            if (field.getText().isEmpty() || field.getText().equals(toBeEmpty[i])) {
                setColumnWidth(Tabel);
                JOptionPane.showMessageDialog(null, toBeEmpty[i] + " belum terisi");
                anyFieldSetToNull = true;
                break;
            }
        }
        return anyFieldSetToNull;
    }

    // method untuk menambah data stock penjualan
    public void tableInsert(JTable loadingTable, String kode, JTextField[] fields) throws ParseException {
        if (loadStockPenjualan(loadingTable)) {
            JTextField[] checkFields = new JTextField[]{fields[0], fields[1], fields[2]};
            if (!loadToEmpty(checkFields, loadingTable)) {
                String selectQuery = "SELECT COUNT(*) FROM stock_penjualan WHERE kode = ?";
                try (Connection cons = koneksi.database.dbConfig(); PreparedStatement selectStmt = cons.prepareStatement(selectQuery);) {
                    loadStockPenjualan(loadingTable);
                    setColumnWidth(loadingTable);
                    String productName = fields[0].getText().trim();
                    String productCode = kode;
                    int stockQuantity = fields[1].getText().trim().isEmpty() ? 0 : Integer.parseInt(fields[1].getText().trim());
                    String productPrice = fields[2].getText().trim();

                    // Check if the kode value already exists
                    selectStmt.setString(1, productCode);
                    ResultSet rs = selectStmt.executeQuery();
                    rs.next();
                    int count = rs.getInt(1);
                    if (count > 0) {
                        JOptionPane.showMessageDialog(loadingTable,
                                "Kode sudah digunakan. Silakan gunakan kode lain.");
                        return;
                    }

                    // Insert the new record
                    String insertQuery = "INSERT INTO stock_penjualan (produk, kode, stock, harga) VALUES (?, ?, ?, ?)";
                    if (tabelTersedia) {
                        PreparedStatement stmts = null;
                        try {
                            stmts = cons.prepareStatement(insertQuery);
                            stmts.setString(1, productName);
                            stmts.setString(2, productCode);
                            stmts.setInt(3, stockQuantity);
                            stmts.setString(4, productPrice);
                            stmts.executeUpdate();
                            JOptionPane.showMessageDialog(loadingTable, "Penyimpanan Data Berhasil");
                            resetFields(fields);
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(loadingTable,
                                    "Gagal menyimpan data. Silakan coba lagi atau hubungi administrator.");
                        } finally {
                            if (stmts != null) {
                                try {
                                    stmts.close();
                                } catch (SQLException e) {
                                    // handle the exception
                                }
                            }
                            try {
                                cons.close();
                            } catch (SQLException e) {
                                // handle the exception
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(loadingTable, "Maaf jumlah atau harga harus berupa angka " + e.getMessage());
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(loadingTable,
                            "Gagal menyimpan data. Silakan coba lagi atau hubungi administrator.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(loadingTable,
                    "Gagal menyimpan data. Silakan coba lagi atau hubungi administrator.");
        }
        loadStockPenjualan(loadingTable);
        setColumnWidth(loadingTable);
    }

    // method untuk memperbarui data stock penjualan
    public void tableUpdate(JTable loadingTable, JTextField[] fields) throws ParseException {
        if (loadStockPenjualan(loadingTable)) {
            JTextField[] checkFields = new JTextField[]{fields[0], fields[1], fields[2]};
            if (!loadToEmpty(checkFields, loadingTable)) {
                String selectQuery = "SELECT COUNT(*) FROM stock_penjualan WHERE kode = ?";
                try (Connection cons = koneksi.database.dbConfig(); PreparedStatement selectStmt = cons.prepareStatement(selectQuery);) {
                    loadStockPenjualan(loadingTable);
                    setColumnWidth(loadingTable);
                    String productName = fields[0].getText().trim();
                    String productCode = fields[1].getText().trim();
                    int stockQuantity = fields[2].getText().trim().isEmpty() ? 0 : Integer.parseInt(fields[2].getText().trim());
                    String productPrice = fields[3].getText().trim();

                    if (!productCode.equals(selectedKode)) {
                        JOptionPane.showMessageDialog(loadingTable,
                                "Belum memilih produk.", "Peringatan",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Check if the kode value already exists
                    selectStmt.setString(1, selectedKode);
                    ResultSet rs = selectStmt.executeQuery();
                    rs.next();
                    int count = rs.getInt(1);
                    if (count == 0) {
                        JOptionPane.showMessageDialog(loadingTable,
                                "Kode produk belum terdaftar. Silakan gunakan kode lain.");
                        return;
                    }

                    // Insert the new record
                    String insertQuery = "UPDATE stock_penjualan SET produk = ?, "
                            + "kode = ?, stock = ?, harga = ?"
                            + "WHERE kode = ?";
                    if (tabelTersedia) {
                        PreparedStatement stmts = null;
                        try {
                            stmts = cons.prepareStatement(insertQuery);
                            stmts.setString(1, productName);
                            stmts.setString(2, productCode);
                            stmts.setInt(3, stockQuantity);
                            stmts.setString(4, productPrice);
                            stmts.setString(5, productCode);
                            stmts.executeUpdate();
                            JOptionPane.showMessageDialog(loadingTable, "Penyimpanan Data Berhasil");
                            selectedKode = null;
                            resetFields(fields);
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(loadingTable,
                                    "Gagal menyimpan data. Silakan coba lagi atau hubungi administrator.");
                        } finally {
                            if (stmts != null) {
                                try {
                                    stmts.close();
                                } catch (SQLException e) {
                                    // handle the exception
                                }
                            }
                            try {
                                cons.close();
                            } catch (SQLException e) {
                                // handle the exception
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(loadingTable, "Maaf jumlah atau harga harus berupa angka " + e.getMessage());
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(loadingTable,
                            "Gagal menyimpan data. Silakan coba lagi atau hubungi administrator.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(loadingTable,
                    "Gagal menyimpan data. Silakan coba lagi atau hubungi administrator.");
        }
        loadStockPenjualan(loadingTable);
        setColumnWidth(loadingTable);
    }

    // method untuk menghapus data stock penjualan
    public void tableDelete(JTable loadingTable, String kode, JTextField[] fields) throws ParseException {
        if (loadStockPenjualan(loadingTable)) {
            String selectQuery = "SELECT COUNT(*) FROM stock_penjualan WHERE kode = ?";
            try (Connection cons = koneksi.database.dbConfig(); PreparedStatement selectStmt = cons.prepareStatement(selectQuery);) {
                loadStockPenjualan(loadingTable);
                setColumnWidth(loadingTable);
                String productName = fields[0].getText();

                // Check if the kode value already exists
                selectStmt.setString(1, selectedKode);
                ResultSet rs = selectStmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);
                if (count == 0) {
                    JOptionPane.showMessageDialog(loadingTable,
                            "Belum memilih produk.", "Peringatan",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Insert the new record
                String insertQuery = "DELETE FROM stock_penjualan WHERE kode = ?";
                if (tabelTersedia) {
                    PreparedStatement stmts = null;
                    try {
                        stmts = cons.prepareStatement(insertQuery);
                        stmts.setString(1, selectedKode);
                        int yesDelete = JOptionPane.showConfirmDialog(loadingTable,
                                "Anda yakin ingin menghapus " + productName
                                + " dengan kode penjualan " + selectedKode + " ?",
                                "Peringatan", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (yesDelete == JOptionPane.YES_OPTION) {
                            stmts.executeUpdate();
                            JOptionPane.showMessageDialog(loadingTable, "Hapus Stock Berhasil");
                            selectedKode = null;
                            resetFields(fields);
                        } else {
                            selectedKode = null;
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(loadingTable,
                                "Gagal menghapus data. Silakan coba lagi atau hubungi administrator.");
                    } finally {
                        if (stmts != null) {
                            try {
                                stmts.close();
                            } catch (SQLException e) {
                                // handle the exception
                            }
                        }
                        try {
                            cons.close();
                        } catch (SQLException e) {
                            // handle the exception
                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(loadingTable,
                        "Gagal menghapus data. Silakan coba lagi atau hubungi administrator.");
            }
        } else {
            JOptionPane.showMessageDialog(loadingTable,
                    "Gagal menghapus data. Silakan coba lagi atau hubungi administrator.");
        }
        loadStockPenjualan(loadingTable);
        setColumnWidth(loadingTable);
    }

    // method untuk query data stock penjualan
    public void tableSearch(JTable loadingTable, JTextField[] fields) throws ParseException {
        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet res = null;
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Nama Produk");
        model.addColumn("Kode Produk");
        model.addColumn("Stock Produk");
        model.addColumn("Harga Produk");

        try {
            conn = database.dbConfig();
            String querySearch = fields[0].getText();

            if (querySearch.equals("")) {
                loadStockPenjualan(loadingTable);
                setColumnWidth(loadingTable);
                return;
            }

            // Check if the kode value already exists
            String selectQuery = "SELECT * FROM stock_penjualan "
                    + "WHERE produk LIKE ? OR kode LIKE ? OR stock LIKE ? OR harga LIKE ?";
            selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, "%" + querySearch + "%");
            selectStmt.setString(2, "%" + querySearch + "%");
            selectStmt.setString(3, "%" + querySearch + "%");
            selectStmt.setString(4, "%" + querySearch + "%");
            rs = selectStmt.executeQuery();

            if (!rs.next()) {
                DefaultTableModel invalidSearch;
                if (haveAData) {
                    // If no results found, fill the table with a "no valid search" message
                    invalidSearch = new DefaultTableModel(
                            new Object[][]{{1, "Stock produk tidak ditemukan!",
                                "Pastikan menulis produk, kode, stock atau harga dengan benar!"}},
                            new Object[]{"No.", "Message", "Pemberitahuan"});
                } else {
                    invalidSearch = new DefaultTableModel(
                            new Object[][]{{1, "Stock produk tidak ditemukan!",
                                "Data stock penjualan masih kosong!"}},
                            new Object[]{"No.", "Message", "Pemberitahuan"});
                }
                loadingTable.setModel(invalidSearch);
                setInvalidWidth(loadingTable);
                return;
            }

            // Insert the new record
            String insertQuery = "SELECT * FROM stock_penjualan "
                    + "WHERE produk LIKE ? OR kode LIKE ? OR stock LIKE ? OR harga LIKE ?";
            stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, "%" + querySearch + "%");
            stmt.setString(2, "%" + querySearch + "%");
            stmt.setString(3, "%" + querySearch + "%");
            stmt.setString(4, "%" + querySearch + "%");
            res = stmt.executeQuery();

            int no = 1;
            while (res.next()) {
                haveAData = true;

                BigDecimal priceValue = new BigDecimal(res.getDouble(4));
                DecimalFormat df = new DecimalFormat("#,##0.00");
                String formattedPrice = df.format(priceValue);

                model.addRow(new Object[]{no++, res.getString(1), res.getString(2),
                    res.getString(3), formattedPrice});
            }

            loadingTable.setModel(model);
            setColumnWidth(loadingTable);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(loadingTable,
                    "Gagal memuat data. Silakan coba lagi atau hubungi administrator.");
        } finally {
            // Close all resources in reverse order of creation
            try {
                if (res != null) {
                    res.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (selectStmt != null) {
                    selectStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // Log the exception or display an error message
                e.printStackTrace();
            }
        }
    }

}
