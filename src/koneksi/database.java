package koneksi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class database {

    private static Connection connection;
    private static Properties configProps;

    public static Connection dbConfig() {
        String url = "";
        String username = "";
        String password = "";

        loadConfigProperties();

        // Check if the configuration file has the database connection details
        if (configProps.containsKey("database.url") && configProps.containsKey("database.username")
                && configProps.containsKey("database.password")) {
            url = configProps.getProperty("database.url");
            username = configProps.getProperty("database.username");
            password = configProps.getProperty("database.password");
        } else {
            // Prompt the user to enter the database connection details
            while (true) {
                url = JOptionPane.showInputDialog(null, "Masukkan URL database "
                        + "(contoh: localhost/sistem_pelacakan):", "Database Configuration!",
                        JOptionPane.INFORMATION_MESSAGE).trim();
                if (url == null){
                    System.exit(0);
                }
                if (!url.isBlank() && !url.startsWith("jdbc:mysql://")) {
                    url = "jdbc:mysql://" + url;
                    break;
                }
            }
            while (username.isBlank()) {
                username = JOptionPane.showInputDialog(null, "Masukkan username:",
                        "Database Configuration!", JOptionPane.INFORMATION_MESSAGE).trim();
                if (username == null){
                    System.exit(0);
                }
            }
            while (password.isBlank()){
                password = JOptionPane.showInputDialog(null, "Masukkan password:",
                    "Database Configuration!", JOptionPane.INFORMATION_MESSAGE).trim();
                if (password == null){
                    System.exit(0);
                }
                if (password.isBlank()){
                    int yesEmpty = JOptionPane.showConfirmDialog(null, 
                            "Anda yakin password kosong?", 
                            "Peringatan!", JOptionPane.OK_CANCEL_OPTION);
                    if (yesEmpty == JOptionPane.OK_OPTION){
                        break;
                    }
                }
            }

            // Save the details in the configuration file
            configProps.setProperty("database.url", url);
            configProps.setProperty("database.username", username);
            configProps.setProperty("database.password", password);
            saveConfigProperties();
        }
        
        
        // Menghubungkan ke database menggunakan konfigurasi yang disiapkan
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            File configFile = new File(".env");
            configFile.delete();
            JOptionPane.showMessageDialog(null, 
                    "Terjadi kesalahan saat menghubungkan ke database.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        return connection;
    }

    private static void loadConfigProperties() {
        configProps = new Properties();
        try (FileInputStream in = new FileInputStream(".env")) {
            configProps.load(in);
        } catch (IOException e) {
        }
    }

    private static void saveConfigProperties() {
        try (FileOutputStream out = new FileOutputStream(".env")) {
            configProps.store(out, "Database configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

