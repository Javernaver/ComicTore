/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.utilidades;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author javie
 */
public class ConexionBD {
    
    
    private static String url = "jdbc:mysql://localhost:3306/comictore?useSSL=false&serverTimezone=UTC";
    private static String driverBD = "com.mysql.cj.jdbc.Driver";
    private static String user = "root";
    private static String pass = "comictore0367";  
    private static Driver driver = null;
    
   
    public static synchronized Connection getConnection() throws SQLException{
        if (driver == null) {
            try {
                Class jdbcDriverClass = Class.forName(driverBD);
                driver = (Driver) jdbcDriverClass.newInstance();
                DriverManager.registerDriver(driver);

            }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                System.out.println("Fallo el driver");
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        
        return DriverManager.getConnection(url, user, pass);
    }
    
    
    public static void close(ResultSet rs) {
        try {
            if (rs != null ){
                rs.close();
            } 
        }
        catch (SQLException e) {
            System.err.println(e);
        }
       
    }
    
    public static void close(PreparedStatement sta) {
        try {
            if (sta != null ){
                sta.close();
            }
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        
        
    }
    
    /**
     * 
     * Cerrar la conexion
     * @param conect 
     */    
    public static void close(Connection conect) {
        try {
            if (conect != null) {
                conect.close();
            }
        }
        catch (SQLException e) {
            System.err.println(e);
        }
    }
   
    
    
    
}
