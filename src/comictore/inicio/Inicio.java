
package comictore.inicio;

import comictore.colecciones.*;
import comictore.clases.*;
import java.text.ParseException;
/**
 *
 * @author javie
 */
public class Inicio {
    
    
    
    public static void cargar() throws ParseException {
    
        ListaClientes clientes = new ListaClientes();
        ListaComics comics = new ListaComics();
        ListaEditoriales editoriales = new ListaEditoriales();
        ListaAutores autores = new ListaAutores();

        Cliente cliente = new Cliente("Juan", "Chilena");
        Autor autor = new Autor("Alan Moore", "USA");
        Editorial editorial = new Editorial("DC", "USA", 1920);
        Comic comic = new Comic("Batman", autor, "USA", editorial, 1980, "Español", 10, 10);
        
        autores.addAutor(autor);
        autores.addAutor(new Autor("Stan Lee", "USA"));
        autores.addAutor(new Autor("asdsad", "Estados Unidos"));
        
        editoriales.addEditorial(editorial);
        editoriales.addEditorial(new Editorial("Marvel", "USA", 1929));
        editoriales.getEditoriales().get(0).setComics(comics);
        
        
        comics.addComic(comic);
        comics.addComic(new Comic("Batman", autor, "USA", editorial, 1980, "Español", 10, 10));
        comics.addComic(new Comic("Superman", autor, "USA", editorial, 1980, "Español", 10, 10));
        comics.addComic(new Comic("Wonder Woman", autor, "USA", editorial, 1980, "Español", 10, 10));
        comics.addComic(new Comic("Watchmen", autor, "USA", editorial, 1980, "Español", 10, 10));
        comics.addComic(new Comic("Flash", autor, "USA", editorial, 1980, "Español", 10, 10));
        comics.addComic(new Comic("Spiderman", autores.getAutor(1), "USA", editoriales.getEditorial(1), 1980, "Español", 10, 10));
        comics.addComic(new Comic("Spiderman 2", autores.getAutor(2), "USA", editoriales.getEditorial(1), 1980, "Español", 10, 10));
        
        ListaComics comics2 = new ListaComics();
        comics2.addComic(comic);
        comics2.addComic(new Comic("Wonder Woman", autor, "USA", editorial, 1980, "Español", 10, 10));
        comics2.addComic(new Comic("Watchmen", autor, "USA", editorial, 1980, "Español", 10, 10));
        comics2.addComic(new Comic("Flash", autor, "USA", editorial, 1980, "Español", 10, 10));
        comics2.addComic(new Comic("Flash", autor, "USA", editorial, 1980, "Español", 10, 10));
        
        editoriales.getEditoriales().get(0).setComics(comics2);
        
        clientes.addCliente(cliente);
        
        autores.getAutor(0).setComics(comics);
        
        Colecciones.setClientes(clientes);
        Colecciones.setComics(comics);
        Colecciones.setAutores(autores);
        Colecciones.setEditoriales(editoriales);
        
        Colecciones.getEditoriales().actualizarEditoriales();
        Colecciones.getAutores().actualizarAutores();
       
    }
    
    
    
    
}
