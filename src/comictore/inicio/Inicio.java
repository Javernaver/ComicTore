
package comictore.inicio;

import comictore.clases.clasesSQL.AutorSQL;
import comictore.clases.clasesSQL.ComicSQL;
import comictore.clases.clasesSQL.EditorialSQL;
import comictore.clases.*;
import comictore.clases.clasesSQL.ClienteSQL;
import comictore.colecciones.*;




/**
 *
 * @author javie
 */
public class Inicio {
    
    
    public static void guardarComics() {
        
        ComicSQL comicSQL = new ComicSQL();
        Comic comic;
        
        for (int i = 0; i < Colecciones.getComics().largo(); i++) {
            
            comic = Colecciones.getComics().getComic(i);
            // agregar comic a la base de datos si este no se encuentra en esta
            if (comicSQL.getComic(comic.getCodigoI()) == null) {
                comicSQL.insert(comic.getCodigoI(), comic.getNombre(), comic.getAnio(), comic.getPais(), comic.getEditorial().getCodigoI(), comic.getAutor().getIDI(), comic.getIdioma(), comic.getPrecio(), comic.getVentas(), comic.getStock());                             
            }
        }
           
    }
    
    public static void guardarAutores() {
        
        AutorSQL autorSQL = new AutorSQL();
        Autor autor;
        
        for (int i = 0; i < Colecciones.getAutores().largo(); i++) {
            
            autor = Colecciones.getAutores().getAutor(i);
            
            if (autorSQL.getAutor(autor.getIDI()) == null) {
                autorSQL.insert(autor.getIDI(), autor.getNombre(), autor.getNacionalidad(), autor.getFechaNacStr() );
            }
            
        }
        
    }
    
    public static void guardarEditoriales() {
        
        EditorialSQL editorialSQL = new EditorialSQL();
        Editorial editorial;
        
        for (int i = 0; i < Colecciones.getEditoriales().largo(); i++) {
            
            editorial = Colecciones.getEditoriales().getEditorial(i);
            
            if (editorialSQL.getEditorial(editorial.getCodigoI()) == null) {
                editorialSQL.insert(editorial.getCodigoI(), editorial.getNombre(), editorial.getPais(), editorial.getAnioFundacion() );
            }
            
        }
        
    }
    
    public static void guardarClientes() {
        
        ClienteSQL clienteSQL = new ClienteSQL();
        Cliente cliente;
        
        for (int i = 0; i < Colecciones.getClientes().largo(); i++) {
            
            cliente = Colecciones.getClientes().getCliente(i);
            
            if (clienteSQL.getCliente(cliente.getIDI()) == null) {
                clienteSQL.insert(cliente.getIDI(), cliente.getNombre(), cliente.getNacionalidad(), cliente.getFechaNacStr(), cliente.getEmail(), cliente.getComicsFav(), cliente.getComicsVistos());
            }
            
        }
        
        
    }
    
    public static void guardar() {
        guardarAutores();
        guardarEditoriales();
        guardarComics();
        guardarClientes();
    }
    
    
    public static void cargarAutores() {
        
        AutorSQL autorSQL = new AutorSQL();
        
        ListaAutores listaAutores = new ListaAutores();
        
        listaAutores.setAutores(autorSQL.select());
        
        Colecciones.setAutores(listaAutores);
                
    }
    
    public static void cargarEditoriales() {
        
        EditorialSQL editorialSQL = new EditorialSQL();
        
        ListaEditoriales listaEditoriales = new ListaEditoriales();
        
        listaEditoriales.setEditoriales(editorialSQL.select());
        
        Colecciones.setEditoriales(listaEditoriales);
        
    }
    
    public static void cargarComics() {
        
        ComicSQL comicSQL = new ComicSQL();
        
        ListaComics listaComics = new ListaComics();
        
        listaComics.setComics(comicSQL.select());
        
        Colecciones.setComics(listaComics);
        
    }
    
    public static void cargarClientes() {
        
        ClienteSQL clienteSQL = new ClienteSQL();
        
        ListaClientes listaClientes = new ListaClientes();
        
        listaClientes.setClientes(clienteSQL.select());
        
        Colecciones.setClientes(listaClientes);
        
    }
    
    public static void cargar() {
        
        cargarAutores();
        cargarEditoriales();
        cargarComics();
        cargarClientes();
        
       /* ListaClientes listaClientes = new ListaClientes();
        
        try {
            listaClientes.addCliente(new Cliente("Juan", "Chile"));
        } catch (ParseException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Colecciones.setClientes(listaClientes);*/
        
        
        Colecciones.getEditoriales().actualizarEditoriales();
        Colecciones.getAutores().actualizarAutores();
    
     
    }
    
    
    
    
}
