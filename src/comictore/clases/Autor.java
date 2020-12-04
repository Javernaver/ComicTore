
package comictore.clases;


import comictore.colecciones.ListaComics;
import java.text.ParseException;
import java.util.Comparator;

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

    public int ventasAutor() {
        
        int suma = 0;
        
        for (int i = 0; i < comics.largo(); i++) {
            suma += comics.getComic(i).getVentas();
        }
               
        return suma;
    }
    
    public static Comparator<Autor> porVentasMayorMenor = new Comparator<Autor>() {
    
        @Override
        public int compare(Autor a1, Autor a2) {
            int vent1 = a1.ventasAutor();
            int vent2 = a2.ventasAutor();
            
            return vent2-vent1;
        }
    };
   
    
}
