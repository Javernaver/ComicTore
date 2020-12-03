
package comictore.colecciones;

import comictore.clases.Comic;
import comictore.clases.Editorial;
import comictore.inicio.Colecciones;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ListaEditoriales {
    
    private ArrayList<Editorial> editoriales;
    
    public ListaEditoriales() {
        editoriales = new ArrayList<>();
    }

    /**
     * @return the editoriales
     */
    public ArrayList<Editorial> getEditoriales() {
        return editoriales;
    }
    /**
     * 
     * @return editoriales como observable list
     */
    public ObservableList<Editorial> getEditorialesObs() {
        return FXCollections.observableArrayList(editoriales);
    }
    
    /**
     * @param editoriales the editoriales to set
     */
    public void setEditoriales(ArrayList<Editorial> editoriales) {
        this.editoriales = editoriales;
    }
    
    public void setEditorialesObs(ObservableList<Editorial> edits ) {
        editoriales.removeAll(editoriales);
        editoriales.addAll(edits);
    }
    
    public void addEditorial(Editorial editorial) {
        if (getEditoriales() == null)
            setEditoriales(new ArrayList<>());
        
        getEditoriales().add(editorial);
    }
    
    public Editorial getEditorial(int i) {
        return editoriales.get(i);
    }
    
    public int indexOfEditorial(Editorial edi) {
        return editoriales.indexOf(edi);
    }
    
    public int largo() {
        return editoriales.size();
    }
    
    public void addComicEditorial(Editorial editorial, Comic comic) {
        
        for (int i = 0; i < largo(); i++) {
            if (editoriales.get(i) == editorial) {
                editoriales.get(i).getComics().addComic(comic);
            }
        }
       /* editoriales.stream().filter((edi) -> (edi == editorial)).forEachOrdered((edi) -> {
            edi.getComics().addComic(comic);
        });*/
        
    }
    
    public boolean editorialRepetida(Editorial editorial) {
        
        return editoriales.stream().anyMatch( (edi) -> (edi.equals(editorial)) );
        
    }
    
    /**
     *  recorrer todos los comics en busca de alguno que no este registrado en su respecita editorial
     */
    public void actualizarEditoriales() {
        
        Comic comic;
        boolean esta;
        
        for (int i = 0; i < Colecciones.getComics().largo(); i++) {
            
            comic = Colecciones.getComics().getComic(i);
            
                
            for (int j = 0; j < largo(); j++) {
                
                 if (comic.getEditorial() == editoriales.get(j)) {
                     
                     esta = false;
                     for (int k = 0; k < editoriales.get(j).getComics().largo(); k++) {
                         if (comic.equals(editoriales.get(j).getComics().getComic(k) ) ) {
                             esta = true;
                         }
                     }
                     
                    if (!esta) {
                        
                           editoriales.get(j).getComics().addComic(comic);
                           
                    }

                 }   

            }
            
            
        }        
    }
    
    
    
}
