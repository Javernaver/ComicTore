
package comictore.colecciones;

import comictore.clases.Autor;
import comictore.clases.Comic;
import comictore.inicio.Colecciones;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ListaAutores {
    
    private ArrayList<Autor> autores;

    public ListaAutores() {
        autores = new ArrayList<>();
    }
    
    /**
     * @return the autores
     */
    public ArrayList<Autor> getAutores() {
        return autores;
    }
    
    /**
     * 
     * @return autores como observable list
     */
    public ObservableList<Autor> getAutoresObs() {
        return FXCollections.observableArrayList(autores);
    }

    /**
     * @param autores the autores to set
     */
    public void setAutores(ArrayList<Autor> autores) {
        this.autores = autores;
    }
    
    public void setAutoresObs(ObservableList<Autor> aut) {
       autores.removeAll(autores);
       autores.addAll(aut);
    }
    
    public boolean addAutor(Autor autor) {
        
        if (getAutores() == null)
            setAutores(new ArrayList<>());
            
        if (!autorRepetido(autor)) {    
            getAutores().add(autor);
            return true;
        }
        return false;
    }
    
    public void addComicAutor(Autor autor, Comic comic) {
        for (int i = 0; i < largo(); i++) {
            if (autores.get(i) == autor) {
                autores.get(i).getComics().addComic(comic);
            }
        }
        
        
    }
    
    
    public Autor getAutor(int i) {
        return autores.get(i);
    }
    
    public int indexOfAutores(Autor autor) {
        return autores.indexOf(autor);
    }
    
    
    public boolean autorRepetido(Autor aut) {
        if (aut != null)
            return (autores.stream().anyMatch((auto) -> (auto.equals(aut))) );               
        return false;
    }
    
    public int largo() {
        return autores.size();
    }
    
    
    public void actualizarAutores() {
        
        Comic comic;
        boolean esta;
        
        for (int i = 0; i < Colecciones.getComics().largo(); i++) {
            
            comic = Colecciones.getComics().getComic(i);
            
                
            for (int j = 0; j < largo(); j++) {
                
                 if (comic.getAutor() == autores.get(j)) {
                     
                     esta = false;
                     for (int k = 0; k < autores.get(j).getComics().largo(); k++) {
                         if (comic.equals(autores.get(j).getComics().getComic(k) ) ) {
                             esta = true;
                         }
                     }
                     
                    if (!esta) {
                        
                           autores.get(j).getComics().addComic(comic);
                           
                    }

                 }   

            }
            
            
        }        
    }
    
    public Autor getAutorCod(int codigo) {
        
        for (Autor aut : autores) {
            if (Integer.parseInt(aut.getID()) == codigo) {
                return aut;
            }
        }
        
        return null;
    }
    
    public Autor getAutorMasComics() {
        if (autores.isEmpty()) return null;
        Autor mayor = autores.get(0);
        int masComic = mayor.getComics().largo();
        
        for (Autor autor : autores) {
            
            if (autor.getComics().largo() > masComic) {
                mayor = autor;
                masComic = mayor.getComics().largo();
            }
            
        }
        
        return mayor;
    }
}
