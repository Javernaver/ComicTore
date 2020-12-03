
package comictore.clases;


import comictore.colecciones.ListaComics;
import java.text.ParseException;

public class Autor extends Persona {
    

    private ListaComics comics;
        
    public Autor() {
        
    }
    
    public Autor (String nombre, String nacionalidad) throws ParseException {
        super(nombre, nacionalidad);
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

    @Override
    public void clonar(Object obj) {
        
        Autor autor = (Autor) obj;
        if (autor != null) {
            super.setID(Integer.parseInt(autor.getID()));
            super.setNombre(autor.getNombre());
            super.setFechaNac(autor.getFechaNac());
            super.setNacionalidad(autor.getNacionalidad());
        }
        
    }
    

   
    
}
