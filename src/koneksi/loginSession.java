/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

/**
 *
 * @author thowie
 */
public class loginSession {
    private static boolean access;
    private static String username = "";
    private static String password = "";
    private static String id = "";
    private static String role = "";
    
    public static String getUsername(){
        return username;
    }
    
    public static void setUsername(String username){
        loginSession.username = username;
    }
    
    public static String getPassword(){
        return password;
    }
    
    public static void setPassword(String password){
        loginSession.password = password;
    }
    
    public static String getId(){
        return id;
    }
    
    public static void setId(String id){
        loginSession.id = id;
    }
    
    public static String getRole(){
        return role;
    }
    
    public static void setRole(String role){
        loginSession.role = role;
    }
    
    public static boolean getAccess(){
        return access;
    }
    
    public static void setAccess(boolean access){
        loginSession.access = access;
    }
    
}
