/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.interfaces;

import java.io.IOException;
import javafx.event.ActionEvent;

/**
 *
 * @author javie
 */
public interface BotonesTipicos {
    
    public abstract void btnAgregar();
    public abstract void btnEditar();
    public abstract void btnEliminar();
    public abstract void btnVolver(ActionEvent event) throws IOException;
    
}
