/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Cliente;
import comictore.inicio.Colecciones;
import comictore.interfaces.InterfaceVentanas;
import comictore.utilidades.Utilidades;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author javie
 */
public class EditClientesController implements Initializable, InterfaceVentanas {

    private Stage dialogStage;
    private Cliente cliente;
    private boolean estaBien = false;
        
    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox selectorPais;
    @FXML
    private TextField txtFechaNac;
    @FXML
    private TextField txtEmail;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectorPais.setItems(Utilidades.setPaises());
    }  
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        
        
        txtNombre.setText(cliente.getNombre());
        
        if (cliente.getNacionalidad() == null)
            selectorPais.getSelectionModel().select(233);
        else
            selectorPais.getSelectionModel().select(cliente.getNacionalidad());
        
        if (cliente.getFechaNac() != null)
            txtFechaNac.setText(cliente.getFechaNacStr());        
        else 
            txtFechaNac.setText("01/01/1900");        
        
        txtEmail.setText(cliente.getEmail());
        
        txtNombre.requestFocus();
        txtNombre.selectAll();
        
        
        
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    
    public boolean isEstaBien() {
        return estaBien;
    }
    
    
    @Override
    public void btnAceptar() {
        
        Cliente cliente2 = new Cliente();
        
        if (estaValidado()) {
            
            cliente2.setNombre(txtNombre.getText());
            cliente2.setNacionalidad((String) selectorPais.getSelectionModel().getSelectedItem());
            
            try {
                cliente2.setFechaNac(txtFechaNac.getText());
            } catch (ParseException ex) {
                Logger.getLogger(EditClientesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            cliente2.asignarID();
            
            if (!Colecciones.getClientes().clienteRepetido(cliente2)) {
                
                cliente.clonar((Object) cliente2);
                estaBien = true;
                dialogStage.close();
            }
            else {
                Utilidades.repetido("Cliente");
            }
                
            
            
            
        }
        
    }
    
    
    @Override
    public void btnCancelar() {
        dialogStage.close();
    }

    @Override
    public boolean estaValidado() {
        String mensajeError = "";
        if (txtNombre.getText() == null || txtNombre.getText().length() == 0)
            mensajeError += "Debe ingresar un nombre para la Editorial!\n";
        
        if (txtEmail.getText() != null ) {
            if ( !Utilidades.emailValido(txtEmail.getText() ) ){
                mensajeError += "Debe ingresar un email valido (ejemplo@ejemplo.com)";
            } 
        }
        
        try { 
            Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(txtFechaNac.getText());
        } catch (ParseException ex) {
            mensajeError += "Fecha incorrecta, debe estar en formato dia/mes/año";
        }
        
        
        if (mensajeError.length() == 0) {
            return true;
        }
        else {
            Utilidades.datosErroneos(mensajeError);
            return false;
        }
        
        
    }
    
}
