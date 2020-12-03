
package comictore.colecciones;

import comictore.clases.Cliente;
import comictore.clases.Comic;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ListaClientes {
    
    private ArrayList<Cliente> clientes;
    
    public ListaClientes() {
        clientes = new ArrayList<>();
    }

    /**
     * @return the clientes
     */
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    
    /**
     * 
     * @return clientes como obvservable list
     */
    public ObservableList<Cliente> getClientesObs() {
        return FXCollections.observableArrayList(clientes);
    }
    
    /**
     * @param clientes the clientes to set
     */
    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }
    
    public void setClientesObs(ObservableList<Cliente> clien) {
        clientes.removeAll(clientes);
        clientes.addAll(clien);
    }
    
    
    /**
     * @param cliente add a cliente to set
     * @return 
     */
    public boolean addCliente(Cliente cliente) {
        if (getClientes() == null)
            setClientes(new ArrayList<>());
        
        if (!clienteRepetido(cliente)){
            getClientes().add(cliente);
            return true;
        }
                                
        return false;
    }
    
    public Cliente getCliente(int i) {
        return clientes.get(i);
    }
    
    public int indexOfCliente(Cliente cliente) {
        return clientes.indexOf(cliente);
    }
    
    public void addComicFav(Comic comic) {
        
    }
    
    public void addComicVisto(Comic comic ) {
        
    }
    
    public boolean clienteRepetido(Cliente cliente) {
        
        return (clientes.stream().anyMatch((cli) -> (cli.equals(cliente))));  
    }
}
