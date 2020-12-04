/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.inicio.clasesSQL;

import comictore.clases.Editorial;
import comictore.inicio.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author javie
 */
public class EditorialSQL {
    private final String SQL_INSERT = "INSERT INTO editorial(ideditorial, nombreEditorial, pais, anio) VALUES(?,?,?,?)";
    
    private final String SQL_UPDATE = "UPDATE editorial nombreEditorial=?, pais=?, anio=? WHERE ideditorial=?";
    
    private final String SQL_DELETE = "DELETE FROM editorial WHERE ideditorial=?";
    
    private final String SQL_SELECT = "SELECT ideditorial, nombreEditorial, pais, anio FROM editorial";
    
    
    public int insert(int ideditorial, String nombreEditorial, String pais, int anio) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        int rows = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1; // contador de columnas
            
            stmt.setInt(index++, ideditorial);
            stmt.setString(index++, nombreEditorial);
            stmt.setString(index++, pais);
            stmt.setInt(index++, anio);
            
            rows = stmt.executeUpdate();
            
            
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        finally {
            ConexionBD.close(conn);
            ConexionBD.close(stmt);
        }
        
        return rows;
    }
    
    
    public int update(int ideditorial, String nombreEditorial, String pais, int anio) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1; // contador de columnas
            stmt.setString(index++, nombreEditorial);
            stmt.setString(index++, pais);
            stmt.setInt(index++, anio);
            stmt.setInt(index, ideditorial);
            
            rows = stmt.executeUpdate();
        
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        finally {
            ConexionBD.close(conn);
            ConexionBD.close(stmt);
        }
        
        return rows;    
    }
    
    public int delete(int ideditorial) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            
            stmt.setInt(1, ideditorial);
            
            rows = stmt.executeUpdate();
            
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        finally {
            ConexionBD.close(conn);
            ConexionBD.close(stmt);
        }
        
        return rows;
        
    }
    
    public ArrayList<Editorial> select() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Editorial editorial;
        
        ArrayList<Editorial> editoriales = new ArrayList<>();
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            
            // mientras hallan resultados
            while (rs.next()) {
                editorial = new Editorial();
                editorial.setCodigo(rs.getInt(1));
                editorial.setNombre(rs.getString(2));
                editorial.setPais(rs.getString(3));
                editorial.setAnioFundacion(rs.getInt(4));                                
                
                editoriales.add(editorial);
            
            }
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        finally {
            ConexionBD.close(rs);
            ConexionBD.close(stmt);
            ConexionBD.close(conn);
            
        }
        
        return editoriales;
                
    }
       
}
