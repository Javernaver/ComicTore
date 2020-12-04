/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.clases.clasesSQL;

import comictore.clases.Cliente;
import comictore.colecciones.ListaComics;
import comictore.inicio.Colecciones;
import comictore.utilidades.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javie
 */
public class ClienteSQL {
    
    private final String SQL_INSERT = "INSERT INTO cliente(idcliente, nombreCliente, pais, fechaNac, email, comicsFav, comicsVistos) VALUES(?,?,?,?,?,?,?)";
    
    private final String SQL_UPDATE = "UPDATE cliente nombreCliente=?, pais=?, fechaNac=?, email=?, comicsFav=?, comicsVistos=? WHERE idcliente=?";
    
    private final String SQL_DELETE = "DELETE FROM cliente WHERE idcliente=?";
    
    private final String SQL_SELECT = "SELECT idcliente, nombreCliente, pais, fechaNac, email, comicsFav, comicsVistos FROM cliente";
    
    private final String SQL_GET_CLIENTE = "SELECT * FROM cliente WHERE idcliente=?";
    
    
    public int insert(int idcliente, String nombreCliente, String pais, String fechaNac, String email, ListaComics comicsFav, ListaComics comicsVistos) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        int rows = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1; // contador de columnas
            stmt.setInt(index++, idcliente);
            stmt.setString(index++, nombreCliente);
            stmt.setString(index++, pais);
            stmt.setString(index++, fechaNac);
            stmt.setString(index++, email);
            stmt.setString(index++, convStr(comicsFav));
            stmt.setString(index++, convStr(comicsVistos));
            
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
    
    public int update(int idcliente, String nombreCliente, String pais, String fechaNac, String email, ListaComics comicsFav, ListaComics comicsVistos) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1; // contador de columnas
            stmt.setString(index++, nombreCliente);
            stmt.setString(index++, pais);
            stmt.setString(index++, fechaNac);
            stmt.setString(index++, email);
            stmt.setString(index++, convStr(comicsFav));
            stmt.setString(index++, convStr(comicsVistos));           
            stmt.setInt(index, idcliente);
            
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
    
    public int delete(int idcliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            
            stmt.setInt(1, idcliente);
            
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
    
    public ArrayList<Cliente> select() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Cliente cliente;
        
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            
            // mientras hallan resultados
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setID(rs.getInt(1));
                cliente.setNombre(rs.getString(2));
                cliente.setNacionalidad(rs.getString(3));
                cliente.setFechaNac(rs.getString(4));
                cliente.setEmail(rs.getString(5));
                cliente.setComicsFav(convLista(rs.getString(6)));
                cliente.setComicsVistos(convLista(rs.getString(7)));
                
                
                clientes.add(cliente);
            
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
        
        return clientes;
        
    }

    private ListaComics convLista(String listacomics) {
        
        ListaComics lista = new ListaComics();
        if (listacomics.contains("-")) {
            String[] comics = listacomics.split("-");
            
            for (String comic : comics) {
                lista.addComic(Colecciones.getComics().getComicCod(Integer.parseInt(comic)));
            }
            
        }
        else {
            
            if (listacomics.length() > 0) {
                lista.addComic(Colecciones.getComics().getComicCod(Integer.parseInt(listacomics)));
            }
        }
        
        return lista;        
    }

    private String convStr(ListaComics listacomics) {
//        System.out.println(listacomics.getComic(0).getNombre());
        String comics = "";
        if (listacomics.largo() > 0) {
            int i;
            for (i = 0; i < listacomics.largo() - 1; i++) {
                comics += listacomics.getComic(i).getCodigo() + "-";
            }
            comics += listacomics.getComic(i).getCodigo();
        }
        
        return comics;
    }
    
    public Cliente getCliente(int idcliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Cliente cliente;
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.prepareStatement(SQL_GET_CLIENTE);
            stmt.setInt(1, idcliente);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setID(rs.getInt(1));
                cliente.setNombre(rs.getString(2));
                cliente.setNacionalidad(rs.getString(3));
                cliente.setFechaNac(rs.getString(4));
                cliente.setEmail(rs.getString(5));
                cliente.setComicsFav(convLista(rs.getString(6)));
                cliente.setComicsVistos(convLista(rs.getString(7)));
                
                return cliente;
           }
            
        }
        catch (SQLException e) {
            System.err.println(e);
        } 
        catch (ParseException ex) {
            Logger.getLogger(AutorSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            ConexionBD.close(rs);
            ConexionBD.close(stmt);
            ConexionBD.close(conn);
            
        }
        
        return null;
        
    }
    
}
