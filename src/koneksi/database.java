package koneksi;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thowie
 */
public class database {
    private static java.sql.Connection sqlConfig;
    public static java.sql.Connection dbConfig(){
        try{
            String url = "jdbc:mysql://localhost/sistem_pelacakan";
            setDbhost(url);
            String user = "username";
            setUsername(user);
            String pass = "password";
            setPassword(pass);
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            sqlConfig = DriverManager.getConnection(url, user, pass);
        } catch(SQLException e){
            System.err.println("Koneksi gagal " + e.getMessage());
        }
        return sqlConfig;
    }
    
    private static String dbhost;
    private static String username;
    private static String password;
    
    public static String getDbhost(){
        return dbhost;
    }
    
    public static void setDbhost(String dbhost){
        database.dbhost = dbhost;
    }
    
    public static String getUsername(){
        return username;
    }
    
    public static void setUsername(String username){
        database.username = username;
    }
    
    public static String getPassword(){
        return password;
    }
    
    public static void setPassword(String password){
        database.password = password;
    }
    
}
