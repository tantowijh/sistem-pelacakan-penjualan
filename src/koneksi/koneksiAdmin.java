/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

import customization.cResetter;
import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author thowie
 */
public class koneksiAdmin {

    Connection conn = (Connection) database.dbConfig();
    PreparedStatement stmt;
    String sql;
    cResetter resetter = new cResetter();
    public boolean haveAData = false;

    // method untuk mengubah password dengan param textField
    public void changePass(JTextField[] textFields) {
        loginSession.setAccess(true);
        if (!loginSession.getPassword().equals(textFields[0].getText())) {
            JOptionPane.showMessageDialog(null,
                    "Password sekarang tidak sesuai!",
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (textFields[1].getText().isEmpty() || textFields[1].getText().equals("Masukkan password baru")) {
            JOptionPane.showMessageDialog(null,
                    "Password tidak boleh kosong!",
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            sql = "UPDATE users SET password = ? WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, textFields[1].getText());
            stmt.setString(2, loginSession.getUsername());
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                JOptionPane.showMessageDialog(null,
                        "Gagal mengubah password!",
                        "Kesalahan sistem", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Silakan untuk melakukan login ulang.",
                        "Update berhasil!", 1);

                resetter.resetFill(new JTextField[]{textFields[0], textFields[1]});

                loginSession.setAccess(false);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal! " + e.getMessage(),
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
        }

        if (!loginSession.getAccess()) {
            System.exit(0);

        }
    }

    // method untuk menampilkan users pada table admin
    public void loadingUsers(JTable table) {
        DefaultTableModel model = new DefaultTableModel();

        try {
            // Create a PreparedStatement to select all rows from the specified table
            stmt = conn.prepareStatement("SELECT * FROM users");

            // Execute the query and get the ResultSet
            ResultSet resultSet = stmt.executeQuery();

            // Get the ResultSetMetaData to determine the number of columns in the result set
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Add the column names to the model
            for (int i = 1; i <= columnCount; i++) {
                String str = resetter.setCapitalized(metaData.getColumnLabel(i));
                model.addColumn(str);
            }

            // Add the rows to the model
            while (resultSet.next()) {
                haveAData = true;
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    // jika bukan owner makan password untuk owner disembunyikan
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    if (!loginSession.getRole().equals("owner")) {
                        if (columnName.equals("password") && resultSet.getString("role").equals("owner")) {
                            // If the role is owner, hide the password column
                            row[i - 1] = "*".repeat(resultSet.getString("password").length()); // Replace the actual password value with asterisks
                        } else {
                            if (!loginSession.getUsername().equals(resultSet.getString("username"))) {
                                if (columnName.equals("password") && resultSet.getString("role").equals(loginSession.getRole())) {
                                    // If the role is owner, hide the password column
                                    row[i - 1] = "*".repeat(resultSet.getString("password").length()); // Replace the actual password value with asterisks
                                } else {
                                    row[i - 1] = resultSet.getObject(i);
                                }
                            } else {
                                row[i - 1] = resultSet.getObject(i);
                            }
                        }

                    } else {
                        row[i - 1] = resultSet.getObject(i);
                    }
                }
                model.addRow(row);
            }

            // Set the model as the data model for the JTable
            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // method untuk mengatur besar kolom pada pencarian error
    public void setInvalidWidth(JTable Tabel) {
        Tabel.getColumnModel().getColumn(0).setPreferredWidth(57);
        Tabel.getColumnModel().getColumn(0).setMaxWidth(80);
        Tabel.getColumnModel().getColumn(1).setPreferredWidth(200);
        Tabel.getColumnModel().getColumn(2).setPreferredWidth(350);
    }

    // method untuk mencari pada database dengan param tabel dan text field
    public void tableSearch(JTable loadingTable, JTextField[] fields) {
        DefaultTableModel model = new DefaultTableModel();
        loadingUsers(loadingTable);
        try {
            String querySearch = fields[0].getText();

            if (querySearch.equals("")) {
                loadingUsers(loadingTable);
                return;
            }

            // Check if the kode value already exists
            String selectQuery = "SELECT * FROM users "
                    + "WHERE id LIKE ? OR username LIKE ? OR password LIKE ? OR role LIKE ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, "%" + querySearch + "%");
            selectStmt.setString(2, "%" + querySearch + "%");
            selectStmt.setString(3, "%" + querySearch + "%");
            selectStmt.setString(4, "%" + querySearch + "%");
            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                DefaultTableModel invalidSearch;
                if (haveAData) {
                    // If no results found, fill the table with a "no valid search" message
                    invalidSearch = new DefaultTableModel(
                            new Object[][]{{"null", "User tidak ditemukan!",
                                "Pastikan menulis data dengan benar!"}},
                            new Object[]{"Id.", "Message", "Pemberitahuan"});
                    haveAData = false;
                } else {
                    invalidSearch = new DefaultTableModel(
                            new Object[][]{{"null", "User tidak ditemukan!",
                                "Data masih kosong!"}},
                            new Object[]{"Id", "Message", "Pemberitahuan"});
                    haveAData = false;
                }
                loadingTable.setModel(invalidSearch);
                setInvalidWidth(loadingTable);
                return;
            }

            // Insert the new record
            String insertQuery = "SELECT * FROM users "
                    + "WHERE id LIKE ? OR username LIKE ? OR password LIKE ? OR role LIKE ?";
            stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, "%" + querySearch + "%");
            stmt.setString(2, "%" + querySearch + "%");
            stmt.setString(3, "%" + querySearch + "%");
            stmt.setString(4, "%" + querySearch + "%");
            ResultSet res = stmt.executeQuery();

            ResultSetMetaData metaData = res.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String str = resetter.setCapitalized(metaData.getColumnLabel(i));
                model.addColumn(str);
            }

            while (res.next()) {
                haveAData = true;

                model.addRow(new Object[]{res.getString(1), res.getString(2),
                    res.getString(3), res.getString(4)});
            }

            loadingTable.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(loadingTable,
                    "Gagal memuat user. Silakan coba lagi atau hubungi administrator.");
        }
    }

    // method untuk mengedit database pada tabel user yang hanya dipanggil oleh tableUpdater
    public void updateTableAndDatabase(TableModel model, JTable table, int row, int column, Object newValue, String... sqlTable) throws SQLException {
        // restart or not
        boolean restartYA = false;

        // Update the JTable
        table.setValueAt(newValue, row, column);

        // Get the primary key value for the updated row
        int id = (int) table.getValueAt(row, 0);

        // Update the database
        Connection myconn = null;
        PreparedStatement mystmt = null;

        try {
            // mengecek user login
            Connection cekKon = (Connection) database.dbConfig();
            String cekSesi = "SELECT * FROM users WHERE username = ?";
            PreparedStatement cekStmt = cekKon.prepareStatement(cekSesi);
            cekStmt.setString(1, loginSession.getUsername());
            ResultSet rs = cekStmt.executeQuery();

            int cekId = 0;
            if (rs.next()) {
                cekId = rs.getInt("id");
            }

            PreparedStatement kkk = cekKon.prepareStatement("SELECT * FROM users WHERE id =" + id);
            ResultSet how = kkk.executeQuery();
            String theRole = null;
            String theUser = null;
            if (how.next()) {
                theRole = how.getString("role");
                theUser = how.getString("username");
            }

            if (loginSession.getRole().equals("owner")) {

            } else {

                if (theRole != null && (theRole.equals("owner"))) {
                    JOptionPane.showMessageDialog(null,
                            "Anda tidak bisa mengubah role yang lebih tinggi dari " + loginSession.getRole() + "!");
                    return;
                } else if (theUser != null && (!theUser.equals(loginSession.getUsername()))) {
                    if (theRole != null && (theRole.equals(loginSession.getRole()))) {
                        JOptionPane.showMessageDialog(null,
                                "Anda tidak bisa mengubah role yang sama dengan " + loginSession.getRole() + "!");
                        return;
                    }
                }

                // Check if the column being updated is the "role" column and if the new value is "owner"
                if (model.getColumnName(column).equalsIgnoreCase("role") && newValue.equals("owner")) {
                    // If so, prevent the update and show an error message
                    JOptionPane.showMessageDialog(null, "Anda tidak memiliki hak untuk mengubah role menjadi 'owner'.");
                    return;
                }
                
                if (model.getColumnName(column).equalsIgnoreCase("role") && newValue.equals("admin")){
                    int ubahYaTidak = JOptionPane.showConfirmDialog(null, "Hati-hati Anda akan mengubah "
                            + "role menjadi 'admin' Anda tidak akan bisa melakukan perubahan atau menghapus role yang sama!",
                            "Peringatan!", JOptionPane.YES_NO_OPTION);
                    if (ubahYaTidak == JOptionPane.NO_OPTION){
                        return;
                    }
                }
            }

            // Establish a connection to the database
            myconn = (Connection) database.dbConfig();

            // Prepare the SQL statement to update the row with the new value
            String mysql = "UPDATE " + sqlTable[0] + " SET " + sqlTable[1] + " = ? WHERE id = ?";
            mystmt = myconn.prepareStatement(mysql);
            mystmt.setObject(1, newValue);
            mystmt.setInt(2, id);

            // Execute the SQL statement
            int rowsUpdated = mystmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Row updated successfully at column " + sqlTable[1] + ".");
            }

            if (cekId != 0 && cekId == id) {
                JOptionPane.showMessageDialog(null,
                        "Anda mengubah " + loginSession.getUsername()
                        + " aplikasi direstart!");
                restartYA = true;
            }

        } catch (SQLException e) {
            System.out.println("Error updating row at column " + sqlTable[1] + ": " + e.getMessage());
        } finally {
            if (mystmt != null) {
                mystmt.close();
            }
            if (myconn != null) {
                myconn.close();
            }
        }

        if (restartYA) {
            System.exit(0);
        }
    }

    // method yang meminta param tabel untuk mengoper parameter dan value yang dibutuhkan kepada updateTableAndDatabase
    public void tableUpdater(JTable tbUser) {
        DefaultTableModel model = (DefaultTableModel) tbUser.getModel();

        model.addTableModelListener(new TableModelListener() {
            // Add a flag to indicate whether the method is already executing
            boolean isExecuting = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                // If the method is already executing, return without doing anything
                if (isExecuting) {
                    return;
                }

                // Set the flag to indicate that the method is now executing
                isExecuting = true;

                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel) e.getSource();
                Object newValue = model.getValueAt(row, column);

                try {
                    // mengatur posisi yang akan diupdate dan nama tabel di database serta kolom
                    updateTableAndDatabase(model, tbUser, row, column, newValue, new String[]{"users", model.getColumnName(column)});
                } catch (SQLException ex) {
                    ex.getMessage();
                }

                // Set the flag to indicate that the method is finished executing
                isExecuting = false;
            }
        });
    }

    // method perantara untuk menambahkan user ke insertDataToDatabase
    public void userInsert(JComboBox comb, JTextField user, JTextField pass) throws SQLException {
        String username = user.getText();
        String password = pass.getText();
        String role = comb.getSelectedItem().toString();
        if (username.isEmpty() && password.isEmpty() && role.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pastikan semua fields terisi");
            return;
        } else if (username.isEmpty() && password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pastikan username dan password terisi");
            return;
        } else if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pastikan username terisi");
            return;
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pastikan password terisi");
            return;
        }
        // insert the data to the database
        insertDataToDatabase(username, password, role);
    }

    // method untuk menambahkan user ke dalam database
    public static void insertDataToDatabase(String username, String password, String role) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Connection conn = (Connection) database.dbConfig();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();
        result.next();
        int count = result.getInt(1);
        if (count > 0) {
            JOptionPane.showMessageDialog(null, "User dengan username " + username + " sudah tersedia!");
        } else {
            sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "User telah berhasil ditambahkan!");
            }
        }
    }

    // method untuk menghapus user dari database
    public static void deleteUserFromDatabase(int userID) throws SQLException {

        if (userID == 0) {
            JOptionPane.showMessageDialog(null,
                    "Mohon pilih user untuk dihapus!");
            return;
        }

        String usrSession = "SELECT * FROM users WHERE id = ?";
        Connection connSession = (Connection) database.dbConfig();
        PreparedStatement stmtSession = connSession.prepareStatement(usrSession);
        stmtSession.setInt(1, userID);
        ResultSet rs = stmtSession.executeQuery();
        String username = null;
        if (rs.next()) {
            username = rs.getString("username");
        }

        Connection cekKon = (Connection) koneksi.database.dbConfig();
        PreparedStatement kkk = cekKon.prepareStatement("SELECT * FROM users WHERE id =" + userID);
        ResultSet how = kkk.executeQuery();
        String theRole = null;
        String theUser = null;
        if (how.next()) {
            theRole = how.getString("role");
            theUser = how.getString("username");
        }

        if (loginSession.getRole().equals("owner")) {

        } else {
            if (theRole != null && (theRole.equals("owner"))) {
                JOptionPane.showMessageDialog(null,
                        "Anda tidak bisa menghapus role yang lebih tinggi dari " + loginSession.getUsername() + "!");
                return;
            } else if (theUser != null && (!theUser.equals(loginSession.getUsername()))) {
                if (theRole != null && (theRole.equals(loginSession.getRole()))) {
                    JOptionPane.showMessageDialog(null,
                            "Anda tidak bisa menghapus role yang sama dengan " + loginSession.getRole() + "!");
                    return;
                }
            }
        }

        if (username != null && username.equals(loginSession.getUsername())) {
            JOptionPane.showMessageDialog(null,
                    "Tidak bisa menghapus diri Anda sendiri! ("
                    + loginSession.getUsername() + ")");
            return;
        }

        String sql = "DELETE FROM users WHERE id = ?";

        Connection conn = (Connection) database.dbConfig();
        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setInt(1, userID);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(null, "User dengan ID " + userID + " sukses dihapus!");
        } else {
            JOptionPane.showMessageDialog(null, "User dengan ID " + userID + " tidak ditemukan!");
        }
    }

}
