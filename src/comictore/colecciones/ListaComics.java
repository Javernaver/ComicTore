
package comictore.colecciones;

import comictore.clases.Comic;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ListaComics {
    
    private ArrayList<Comic> comics;

    public ListaComics() {
        comics = new ArrayList<>();
    }
  
    
    /**
     * @return the comics
     */
    public ArrayList<Comic> getComics() {
        return comics;
    }
    /**
     * 
     * @return la lista de comics como observable list para asi usarla en la interfaz grafica
     */
    public ObservableList<Comic> getComicsObs() {
        return FXCollections.observableArrayList(comics);
    }
    /**
     * @param comics the comics to set
     */
    public void setComics(ArrayList<Comic> comics) {
        this.comics = comics;
    }
    
    /**
     * 
     * @param comicsObs cambia los comics por los guardados en una observable list
     */
    public void setComicsObs(ObservableList<Comic> comicsObs) {
        
        comics.removeAll(comics);
        comics.addAll(comicsObs);
    }
    
    public boolean addComic(Comic comic) {
        if (getComics() == null)
            setComics(new ArrayList<>());
        if (!comicRepetido(comic)) {
            getComics().add(comic);
            return true;
        }
        return false;
    }
    
    public Comic getComic(int i) {
        return comics.get(i);
    }
    
    public int indexOfComic(Comic co) {
        return comics.indexOf(co);
    }

    public boolean comicRepetido(Comic comic) {
        
        return comics.stream().anyMatch( (com) -> (com.equals(comic)) );
        
    }
    
    public int largo() {
        return comics.size();
    }
}
