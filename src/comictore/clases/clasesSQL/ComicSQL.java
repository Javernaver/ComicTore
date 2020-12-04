/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.clases.clasesSQL;


import comictore.clases.Comic;
import comictore.inicio.Colecciones;
import comictore.utilidades.ConexionBD;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author javie
 */
public class ComicSQL {
    
    private final String SQL_INSERT = "INSERT INTO comic(idcomic, nombreComic, anio, pais, editorial, autor, idioma, precio, ventas, stock) VALUES(?,?,?,?,?,?,?,?,?,?)";
    
    private final String SQL_UPDATE = "UPDATE comic nombreComic=?, anio=?, pais=?, editorial=?, autor=?, idioma=?, precio=?, ventas=?, stock=? WHERE idcomic=?";
    
    private final String SQL_DELETE = "DELETE FROM comic WHERE idComic=?";
    
    private final String SQL_SELECT = "SELECT idcomic, nombreComic, anio, pais, editorial, autor, idioma, precio, ventas, stock FROM comic";
    
    private final String SQL_GET_COMIC = "SELECT * FROM comic WHERE idcomic=?";
    
    
    public int insert(int idcomic, String nombreComic, int anio, String pais, int editorial, int autor, String idioma, int precio, int ventas, int stock ){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        int rows = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1; // contador de columnas
            stmt.setInt(index++, idcomic);
            stmt.setString(index++, nombreComic);
            stmt.setInt(index++, anio);
            stmt.setString(index++, pais);
            stmt.setInt(index++, editorial);
            stmt.setInt(index++, autor);
            stmt.setString(index++, idioma);
            stmt.setInt(index++, precio);
            stmt.setInt(index++, ventas);
            stmt.setInt(index++, stock);
            
            System.out.println(SQL_INSERT);
            
            rows = stmt.executeUpdate();
            
            System.out.println("registros afectados = " + rows);
             
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
    
    public int update(int idcomic, String nombreComic, int anio, String pais, int editorial, int autor, String idioma, int precio, int ventas, int stock ){
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1; // contador de columnas
            
            stmt.setString(index++, nombreComic);
            stmt.setInt(index++, anio);
            stmt.setString(index++, pais);
            stmt.setInt(index++, editorial);
            stmt.setInt(index++, autor);
            stmt.setString(index++, idioma);
            stmt.setInt(index++, precio);
            stmt.setInt(index++, ventas);
            stmt.setInt(index++, stock);
            stmt.setInt(index, idcomic);
     
            
            rows = stmt.executeUpdate();
 
            System.out.println("Esto es un update registros afectados = " + rows);
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
    
    public int delete(int idcomic) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            
            stmt.setInt(1, idcomic);           
            
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
    
    public ArrayList<Comic> select() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Comic comic;
        
        ArrayList<Comic> comics = new ArrayList<>();
        
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            
            // mientras hallan resultados
            while (rs.next()) {
                comic = new Comic();
                comic.setCodigo(rs.getInt(1));
                comic.setNombre(rs.getString(2));
                comic.setAnio(rs.getInt(3));
                comic.setPais(rs.getString(4));
                comic.setEditorial(Colecciones.getEditoriales().getEditorialCod(rs.getInt(5)));
                comic.setAutor(Colecciones.getAutores().getAutorCod(rs.getInt(6)));
                comic.setIdioma(rs.getString(7));
                comic.setPrecio(rs.getInt(8));
                comic.setVentas(rs.getInt(9));
                comic.setStock(rs.getInt(10));
                
                comics.add(comic);
                
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
        
        return comics;
    }
    
    public Comic getComic(int idcomic) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Comic comic;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_GET_COMIC);
            stmt.setInt(1, idcomic);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                comic = new Comic();
                comic.setCodigo(rs.getInt(1));
                comic.setNombre(rs.getString(2));
                comic.setAnio(rs.getInt(3));
                comic.setPais(rs.getString(4));
                comic.setEditorial(Colecciones.getEditoriales().getEditorialCod(rs.getInt(5)));
                comic.setAutor(Colecciones.getAutores().getAutorCod(rs.getInt(6)));
                comic.setIdioma(rs.getString(7));
                comic.setPrecio(rs.getInt(8));
                comic.setVentas(rs.getInt(9));
                comic.setStock(rs.getInt(10));
                return comic;
            
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
        
        return null;
        
    }
    
    
    
}
