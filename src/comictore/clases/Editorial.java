
package comictore.clases;

import comictore.colecciones.ListaComics;
import javafx.collections.ObservableList;


public class Editorial extends Cosa {
    
    private ListaComics comics = new ListaComics();
    private int anioFundacion;
    
    
    public Editorial() {        
    }
    
    public Editorial(String nombre, String pais, int anioFundacion) {
        super(nombre, pais);
        this.anioFundacion = anioFundacion;
    }

 

  
    /**
     * @return the comics
     */
    public ListaComics getComics() {
        if (comics == null)
            setComics(new ListaComics());
        return comics;
    }

    /**
     * @param comics the comics to set
     */
    public void setComics(ListaComics comics) {
        this.comics = comics;
    }

    /**
     * @return the anioFundacion
     */
    public int getAnioFundacion() {
        return anioFundacion;
    }

    /**
     * @param anioFundacion the anioFundacion to set
     */
    public void setAnioFundacion(int anioFundacion) {
        this.anioFundacion = anioFundacion;
    }

    @Override
    public String toString() {
        return super.getNombre();
    }
    
    @Override
    public void clonar(Object obj){
        Editorial edit = (Editorial) obj;
        
        if (edit != null) {
            super.setCodigo(Integer.parseInt(edit.getCodigo()));
            super.setNombre(edit.getNombre());
            super.setPais(edit.getPais());
            setAnioFundacion(edit.getAnioFundacion());
            comics = edit.getComics();
        }
        
    }

    public void setComicsObs(ObservableList<Comic> comicsObs) {        
         this.comics.setComicsObs(comicsObs);
    }
    
    
}
