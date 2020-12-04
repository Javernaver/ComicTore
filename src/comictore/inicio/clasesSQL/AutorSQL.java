/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.inicio.clasesSQL;

import comictore.clases.Autor;
import comictore.inicio.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author javie
 */
public class AutorSQL {
    
    private final String SQL_INSERT = "INSERT INTO autor(idautor, nombreAutor, pais, fechaNac) VALUES(?,?,?,?)";
    
    private final String SQL_UPDATE = "UPDATE autor nombreAutor=?, pais=?, fechaNac=? WHERE idautor=?";
    
    private final String SQL_DELETE = "DELETE FROM autor WHERE idautor=?";
    
    private final String SQL_SELECT = "SELECT idautor, nombreAutor, pais, fechaNac FROM autor";
      
    
    public int insert(int idautor, String nombreAutor, String pais, String fechaNac) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        int rows = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1; // contador de columnas
            stmt.setInt(index++, idautor);
            stmt.setString(index++, nombreAutor);
            stmt.setString(index++, pais);
            stmt.setString(index++, fechaNac);
            
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
    
    
    public int update(int idautor, String nombreAutor, String pais, String fechaNac) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1; // contador de columnas
            stmt.setString(index++, nombreAutor);
            stmt.setString(index++, pais);
            stmt.setString(index++, fechaNac);
            stmt.setInt(index, idautor);
            
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
    
    public int delete(int idautor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            
            stmt.setInt(1, idautor);
            
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
    
    public ArrayList<Autor> select() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Autor autor;
        
        ArrayList<Autor> autores = new ArrayList<>();
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            
            // mientras hallan resultados
            while (rs.next()) {
                autor = new Autor();
                autor.setID(rs.getInt(1));
                autor.setNombre(rs.getString(2));
                autor.setNacionalidad(rs.getString(3));
                autor.setFechaNac(rs.getString(4));
                
                autores.add(autor);
            
            }
        }
        catch (SQLException | ParseException e) {
            System.err.println(e);
        }
        finally {
            ConexionBD.close(rs);
            ConexionBD.close(stmt);
            ConexionBD.close(conn);
            
        }
        
        return autores;
    }
    
    
}
