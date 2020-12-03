
package comictore.inicio;

import comictore.clases.*;
import comictore.colecciones.*;
import java.util.ArrayList;

/**
 *
 * @author javie
 */
public class Colecciones {
    
    private static ListaComics comics;
    private static ListaAutores autores;
    private static ListaClientes clientes;
    private static ListaEditoriales editoriales;

    /**
     * @return the comics
     */
    public static ListaComics getComics() {
        return comics;
    }

    /**
     * @param aComics the comics to set
     */
    public static void setComics(ListaComics aComics) {
        comics = aComics;
    }
    
    public static void setComics(ArrayList<Comic> com){ 
        comics.setComics(com);
    }
    

    /**
     * @return the autores
     */
    public static ListaAutores getAutores() {
        return autores;
    }

    /**
     * @param aAutores the autores to set
     */
    public static void setAutores(ListaAutores aAutores) {
        autores = aAutores;
    }
    
    public static void setAutores(ArrayList<Autor> aut) {
        autores.setAutores(aut);
    }
    /**
     * @return the clientes
     */
    public static ListaClientes getClientes() {
        return clientes;
    }

    /**
     * @param aClientes the clientes to set
     */
    public static void setClientes(ListaClientes aClientes) {
        clientes = aClientes;
    }
    
    public static void setClientes(ArrayList<Cliente> cli) {
        clientes.setClientes(cli);
    }
    /**
     * @return the editoriales
     */
    public static ListaEditoriales getEditoriales() {
        return editoriales;
    }

    /**
     * @param aEditoriales the editoriales to set
     */
    public static void setEditoriales(ListaEditoriales aEditoriales) {
        editoriales = aEditoriales;
    }
    
    public static void setEditoriales(ArrayList<Editorial> edi) {
        editoriales.setEditoriales(edi);
    }
    
    
    
}
