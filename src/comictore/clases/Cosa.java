/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.clases;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author javie
 */
public abstract class Cosa implements Serializable {
    
    private final StringProperty codigo = new SimpleStringProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private String pais;
    
    
    public abstract void clonar(Object obj);
    
    public Cosa() {
        
    }
    
    public Cosa(String nombre, String pais) {
                
        this.nombre.set(nombre);
        this.pais = pais;
        asignarCodigo();
  
    }
    
    public String getCodigo() {
        return codigo.get();
    }
    
    public StringProperty getCodigoP() {
        return codigo;
    }
    
    public void setCodigo(int codigo) {
      if (codigo > 0)  
        this.codigo.set(Integer.toString(codigo));
      else
        this.codigo.set(Integer.toString(-codigo));  
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
    * @return the pais
    */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }
    
    
    @Override
    public String toString() {
        return  codigo + "\n" + nombre.get() + "\n" + pais;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) 
            return false;
        
        if (obj instanceof Cosa) {
            Cosa cos = (Cosa) obj;
            return  cos.getCodigo().equals(codigo.get());
        } else 
            return false;
        
    }

    @Override
    public int hashCode() {

        int hash = 7;
        hash = 13 + hash + this.nombre.get().hashCode() + this.pais.hashCode();
        if (hash < 0)
            return -hash;
        return hash;
            
    }
    
    public void asignarCodigo() {
        this.codigo.set(Integer.toString(hashCode()) );
    }
    
  
}
