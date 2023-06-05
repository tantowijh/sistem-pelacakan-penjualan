package sistem;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SalesDatabase {

    private Connection conn;
    private Statement stmt;

    public SalesDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = (Connection) koneksi.database.dbConfig();
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS sales "
                    + "(id INTEGER PRIMARY KEY AUTO_INCREMENT,"
                    + " `date` TEXT NOT NULL, "
                    + " amount REAL NOT NULL, "
                    + " price REAL NOT NULL, "
                    + " product TEXT NOT NULL, "
                    + " product_id TEXT NOT NULL, "
                    + " quantity INTEGER NOT NULL, "
                    + " customer TEXT NOT NULL, "
                    + " customer_id TEXT NOT NULL, "
                    + " customer_phone TEXT NOT NULL, "
                    + " customer_email TEXT NOT NULL, "
                    + " customer_address TEXT NOT NULL, "
                    + " payment_status TEXT NOT NULL)";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS stock_penjualan ("
                    + "produk VARCHAR(255) NOT NULL, "
                    + "kode VARCHAR(255) NOT NULL, "
                    + "stock INT NOT NULL, "
                    + "harga DECIMAL(10, 2) NOT NULL, "
                    + "PRIMARY KEY (kode) )";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS customers ("
                    + "customer_id VARCHAR(10) NOT NULL PRIMARY KEY, " 
                    + "first_name VARCHAR(50) NOT NULL, " 
                    + "last_name VARCHAR(50) NOT NULL, " 
                    + "phone VARCHAR(100) NOT NULL, " 
                    + "email VARCHAR(100) NOT NULL, " 
                    + "address VARCHAR(200) NOT NULL)";
            stmt.executeUpdate(sql);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadSalesTable(DefaultTableModel model) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM sales");
            while (rs.next()) {
                int id = rs.getInt("id");
                String date = rs.getString("date");
                double amount = rs.getDouble("amount");
                String product = rs.getString("product");
                int quantity = rs.getInt("quantity");
                String customer = rs.getString("customer");
                String paymentStatus = rs.getString("payment_status");

                // Format the amount column to display non-scientific notation
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                String formattedAmount = decimalFormat.format(amount);

                Object[] row = {id, date, formattedAmount, product, quantity, customer, paymentStatus};
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSale(String date, double amount, double price, String product, String product_id,
            int quantity, String customer, String customer_id, String customer_phone, String customer_email,
            String customer_address, String paymentStatus) {
        try {
            String sql = "INSERT INTO sales (date, amount, price, product, product_id, quantity, customer, customer_id, "
                    + "customer_phone, customer_email, customer_address, payment_status) "
                    + "VALUES ('" + date + "', " + amount + ", " + price + ", '" + product + "', '" + product_id + "', "
                    + quantity + ", '" + customer + "', '" + customer_id + "', '" + customer_phone + "', '"
                    + customer_email + "', '" + customer_address + "', '" + paymentStatus + "')";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data penjualan berhasil disimpan!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSale(int id, String date, double amount, double price, String product, String product_id,
            int quantity, String customer, String customer_id, String customer_phone, String customer_email,
            String customer_address, String paymentStatus) {
        try {
            String sql = "UPDATE sales SET date=?, amount=?, price=?, product=?, product_id=?, quantity=?, customer=?, "
                    + "customer_id=?, customer_phone=?, customer_email=?, customer_address=?, payment_status=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.setDouble(2, amount);
            pstmt.setDouble(3, price);
            pstmt.setString(4, product);
            pstmt.setString(5, product_id);
            pstmt.setInt(6, quantity);
            pstmt.setString(7, customer);
            pstmt.setString(8, customer_id);
            pstmt.setString(9, customer_phone);
            pstmt.setString(10, customer_email);
            pstmt.setString(11, customer_address);
            pstmt.setString(12, paymentStatus);
            pstmt.setInt(13, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data penjualan berhasil diupdate!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> retrieveProductAndCustomerId(int id) {
        Map<String, String> result = new HashMap<>();
        try {
            String sql = "SELECT date, product_id, customer_id, quantity FROM sales WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result.put("product_id", rs.getString("product_id"));
                result.put("customer_id", rs.getString("customer_id"));
                result.put("quantity", rs.getString("quantity"));
                result.put("date", rs.getString("date"));
            } else {
                System.out.println("No row found with ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteSale(int id) {
        try(PreparedStatement stt = conn.prepareStatement("SELECT * FROM sales WHERE id = ?")){
            stt.setInt(1, id);
            ResultSet rs = stt.executeQuery();
            while (rs.next()){
                int delete = JOptionPane.showConfirmDialog(null, 
                        "Anda yakin ingin menghapus penjualan dengan produk "
                                + rs.getString("product") 
                                + " atas nama "
                                + rs.getString("customer"),
                        "Peringatan!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (delete == JOptionPane.NO_OPTION){
                    return;
                }
            }
        } catch (SQLException e){
            e.getMessage();
        }
        try {
            String sql = "DELETE FROM sales WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data penjualan berhasil dihapus!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchSalesTable(String searchText, DefaultTableModel model) {
        try {
            String sql = "SELECT * FROM sales WHERE id LIKE ? OR date LIKE ? OR amount LIKE ? OR product LIKE ? OR quantity LIKE ? OR customer LIKE ? OR payment_status LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            String wildcardSearchText = "%" + searchText + "%";
            for (int i = 1; i <= 7; i++) {
                pstmt.setString(i, wildcardSearchText);
            }
            ResultSet rs = pstmt.executeQuery();
            model.setRowCount(0); // Clear the current table data
            while (rs.next()) {
                int rowID = rs.getInt("id");
                String rowDate = rs.getString("date");
                double rowAmount = rs.getDouble("amount");
                String rowProduct = rs.getString("product");
                int rowQuantity = rs.getInt("quantity");
                String rowCustomer = rs.getString("customer");
                String rowPaymentStatus = rs.getString("payment_status");

                // Format the amount column to display non-scientific notation
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                String formattedAmount = decimalFormat.format(rowAmount);

                Object[] row = {rowID, rowDate, formattedAmount, rowProduct, rowQuantity, rowCustomer, rowPaymentStatus};
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countRows() {
        int rowCount = 0;
        try {
            String sql = "SELECT COUNT(*) FROM sales";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                rowCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public String calculateTotalAmount() {
        double totalAmount = 0;
        try {
            String sql = "SELECT SUM(amount) FROM sales";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalAmount = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(totalAmount);
    }

    // pengurangan stock
    public int decreaseStock(String productCode, int decreaseAmount) {
        int updatedStock = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) koneksi.database.dbConfig();

            // Get the current stock value for the product
            String query = "SELECT stock FROM stock_penjualan WHERE kode = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, productCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                int currentStock = rs.getInt("stock");

                // Calculate the updated stock value
                updatedStock = currentStock - decreaseAmount;
                if (updatedStock < 0) {
                    updatedStock = 0;
                }

                // Update the database with the new stock value
                query = "UPDATE stock_penjualan SET stock = ? WHERE kode = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, updatedStock);
                ps.setString(2, productCode);
                ps.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Return the updated stock value
        return updatedStock;
    }

    // penambahan stock
    public int addStock(String productCode, int addAmount) {
        int updatedStock = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) koneksi.database.dbConfig();

            // Get the current stock value for the product
            String query = "SELECT stock FROM stock_penjualan WHERE kode = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, productCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                int currentStock = rs.getInt("stock");

                // Calculate the updated stock value
                updatedStock = currentStock + addAmount;

                // Update the database with the new stock value
                query = "UPDATE stock_penjualan SET stock = ? WHERE kode = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, updatedStock);
                ps.setString(2, productCode);
                ps.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Return the updated stock value
        return updatedStock;
    }
}
