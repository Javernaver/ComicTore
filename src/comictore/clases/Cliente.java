
package comictore.clases;

import comictore.colecciones.ListaComics;
import java.text.ParseException;


public class Cliente extends Persona {
    
   
    private String email;
  
    private ListaComics comicsFav;
    private ListaComics comicsVistos;
    
    public Cliente() {  
        
    }
    
    public Cliente(String nombre, String nacionalidad) throws ParseException {
        super(nombre, nacionalidad);
    }


    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the comicsFav
     */
    public ListaComics getComicsFav() {
        if (comicsFav == null)
            setComicsFav(new ListaComics());
        return comicsFav;
    }

    /**
     * @param comicsFav the comicsFav to set
     */
    public void setComicsFav(ListaComics comicsFav) {
        
        this.comicsFav = comicsFav;
    }

    /**
     * @return the comicsVistos
     */
    public ListaComics getComicsVistos() {
        if (comicsVistos == null)
            setComicsVistos(new ListaComics());
        return comicsVistos;
    }

    /**
     * @param comicsVistos the comicsVistos to set
     */
    public void setComicsVistos(ListaComics comicsVistos) {
        this.comicsVistos = comicsVistos;
    }

    @Override
    public void clonar(Object obj) {
        
        Cliente cliente = (Cliente) obj;
        if (cliente != null) {
            super.setID(Integer.parseInt(cliente.getID()));
            super.setNombre(cliente.getNombre());
            super.setFechaNac(cliente.getFechaNac());
            super.setNacionalidad(cliente.getNacionalidad());
            setEmail(cliente.getEmail());
        }
        
        
    }

   
    
}
