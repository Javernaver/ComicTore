
package comictore.clases;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Persona implements Serializable {
    
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private String nacionalidad;
    private Date fechaNac;
    
    
    public Persona()  {        
       
    }
    
    public Persona(String nombre, String nacionalidad) throws ParseException {
        
        this.nombre.set(nombre);
        this.nacionalidad = nacionalidad;
        asignarID();
        setFechaNac("01/01/1900");
    }
    
    public abstract void clonar(Object obj);
    
    
    public String getID() {
        return id.get();
    }
    
    public StringProperty getIDP() {
        return id;
    }
    
    public int getIDI() {
        return Integer.parseInt(id.get());
    }
    
    public void setID(int id) {
      if (id > 0)  
        this.id.set(Integer.toString(id));
      else
        this.id.set(Integer.toString(-id));  
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre.get();
    }
    public StringProperty getNombreP() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    /**
     * @return the nacionalidad
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * @param nacionalidad the nacionalidad to set
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * @return the fechaNac
     */
    public Date getFechaNac() {
        return fechaNac;
    }

    public String getFechaNacStr() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
        String strDate = dateFormat.format(getFechaNac()); 
        return strDate;
    }
    
    /**
     * @param fechaNac the fechaNac to set
     */
    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }
    /**
     * @param fechaNac the fechaNac to set
     * @throws java.text.ParseException
     */
    public void setFechaNac(String fechaNac) throws ParseException {
        
        this.fechaNac = new SimpleDateFormat("dd/MM/yyyy").parse(fechaNac);  
        
    }

    @Override
    public String toString() {
        return  nombre.get();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) 
            return false;
        
        if (obj instanceof Persona) {
            Persona cos = (Persona) obj;
            return  cos.getID().equals(id.get());
        } else 
            return false;
        
    }

    @Override
    public int hashCode() {

        int hash;
        long hash2 = 7;
        hash2 = 13 + hash2 + this.nombre.get().hashCode() + this.nacionalidad.hashCode();
        if (hash2 < 0)
            hash2 = -hash2;        
        if (hash2 > Integer.MAX_VALUE || (Math.log10(hash2) + 1) > 7) {          
            hash2 /= 1000;            
        }
        if ((Math.log10(hash2) + 1) < 7) {
            hash2 *= 10;
        }
        
        hash = (int) hash2;
       
        return hash;
            
    }
    
    public void asignarID() {
        this.id.set(Integer.toString(hashCode()) );
    }
    
    
}
