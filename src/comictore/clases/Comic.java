
package comictore.clases;

import java.util.Comparator;


public class Comic extends Cosa {
    
    private Autor autor;
    private Editorial editorial;
    private int anio;
    private String idioma;
    private int precio;
    private int ventas;
    private int stock;
    
    public Comic() { 
        
    }
    
    public Comic( String nombre, Autor autor, String pais, Editorial editorial, int anio, String idioma, int precio, int stock){
         
        super(nombre, pais);
        
        this.autor = autor;
        
        this.editorial = editorial;
        this.anio = anio;
        this.idioma = idioma;
        this.precio = precio;
        this.editorial = editorial;
        this.stock = stock;
//        super.setCodigo(Integer.parseInt(super.getCodigo()) + autor.hashCode());
    }
    

   
    /**
     * @return the autor
     */
    public Autor getAutor() {
        
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(Autor autor) {
        this.autor = autor;
    }


    /**
     * @return the editorial
     */
    public Editorial getEditorial() {
        return editorial;
    }

    /**
     * @param editorial the editorial to set
     */
    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    /**
     * @return the anio
     */
    public int getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * @return the idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * @param idioma the idioma to set
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * @return the precio
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * @return the ventas
     */
    public int getVentas() {
        return ventas;
    }

    /**
     * @param ventas the ventas to set
     */
    public void setVentas(int ventas) {
        this.ventas = ventas;
    }
    
    
    public void venta(){
        ventas++;
        stock--;
    }
    
    public void cancelarVenta() {
        ventas--;
        stock++;
    }
    

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCodigo() {
        super.asignarCodigo();
        
        long num = Integer.parseInt(super.getCodigo()) + getAutor().getNombre().hashCode() + getEditorial().getNombre().hashCode();
        int num2;
        if (num < 0) {
            num = -num;
        }
        if (num > Integer.MAX_VALUE || (Math.log10(num) + 1) > 7) {
           
            num /= 1000;            
        }
        if ((Math.log10(num) + 1) < 7) {
            num *= 10;
        }
        
        num2 = (int) num;
        super.setCodigo(num2);
    }
    /**
     * 
     * @param obj objeto a clonar, para este caso el objeto se castea a comic para poder clonarlo
     */
    @Override
    public void clonar(Object obj) {
       Comic comic2 = (Comic) obj; 
       if (comic2 != null) {
           super.setCodigo(Integer.parseInt(comic2.getCodigo()));
           super.setNombre(comic2.getNombre());
           super.setPais(comic2.getPais());
           setAutor(comic2.getAutor());
           setEditorial(comic2.getEditorial());
           setAnio(comic2.getAnio());
           setIdioma(comic2.getIdioma());
           setPrecio(comic2.getPrecio());
           setVentas(comic2.getVentas());
           setStock(comic2.getStock());
       }
    }
    
    public static Comparator<Comic> porVentasMayorMenor = new Comparator<Comic>() {
        
        @Override
        public int compare(Comic c1, Comic c2) {
            int vent1 = c1.getVentas();
            int vent2 = c2.getVentas();
            
            return vent2-vent1;
        }
        
        
    };
    
}
