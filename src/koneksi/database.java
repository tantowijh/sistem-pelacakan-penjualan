package koneksi;

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
        String password;

        loadConfigProperties();

        // Check if the configuration file has the database connection details
        if (configProps.containsKey("database.url") && configProps.containsKey("database.username")
                && configProps.containsKey("database.password")) {
            url = configProps.getProperty("database.url");
            username = configProps.getProperty("database.username");
            password = configProps.getProperty("database.password");
        } else {
            // Prompt the user to enter the database connection details
            while (url == null || url.isBlank()) {
                url = JOptionPane.showInputDialog(null, "Masukkan URL database "
                        + "(contoh: localhost/sistem_pelacakan):", "Database Configuration!", 
                        JOptionPane.INFORMATION_MESSAGE);
                if (!url.startsWith("jdbc:mysql://")) {
                    url = "jdbc:mysql://" + url;
                }
            }
            while (username == null || username.isBlank()) {
                username = JOptionPane.showInputDialog(null, "Masukkan username:", 
                        "Database Configuration!", JOptionPane.INFORMATION_MESSAGE);
            }
            password = JOptionPane.showInputDialog(null, "Masukkan password:", 
                    "Database Configuration!", JOptionPane.INFORMATION_MESSAGE);

            // Save the details in the configuration file
            configProps.setProperty("database.url", url);
            configProps.setProperty("database.username", username);
            configProps.setProperty("database.password", password);
            saveConfigProperties();
        }

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghubungkan ke database.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return connection;
    }

    private static void loadConfigProperties() {
        configProps = new Properties();
        try (FileInputStream in = new FileInputStream(".config.properties")) {
            configProps.load(in);
        } catch (IOException e) {
        }
    }

    private static void saveConfigProperties() {
        try (FileOutputStream out = new FileOutputStream(".config.properties")) {
            configProps.store(out, "Database configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
