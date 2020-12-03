/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Editorial;
import comictore.inicio.Colecciones;
import comictore.interfaces.InterfaceVentanas;
import comictore.utilidades.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
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
public class EditEditorialesController implements Initializable, InterfaceVentanas {
    
    
    private Stage dialogStage;
    private Editorial editorial;
    private boolean estaBien = false;
    
    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox selectorPais;
    @FXML
    private TextField txtAnio;
    

    
    
    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
        
        txtNombre.setText(editorial.getNombre());
        
        if (editorial.getPais() == null)
            selectorPais.getSelectionModel().select(233);
        else
            selectorPais.getSelectionModel().select(editorial.getPais());
        
        
        txtAnio.setText(Integer.toString(editorial.getAnioFundacion()));
        
        txtNombre.requestFocus();
        txtNombre.selectAll();

    }
    
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    
    public boolean isEstaBien() {
        return estaBien;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        selectorPais.setItems(Utilidades.setPaises());
    }    
    
    @FXML
    @Override
    public void btnAceptar() {
        
        Editorial editorial2 = new Editorial();
        
        if (estaValidado()) {
            
            editorial2.setNombre(txtNombre.getText());
            editorial2.setPais((String) selectorPais.getSelectionModel().getSelectedItem());
            editorial2.setAnioFundacion(Integer.parseInt(txtAnio.getText()));
            
            editorial2.asignarCodigo();
            
            if (!Colecciones.getEditoriales().editorialRepetida(editorial2)){
                
                editorial.clonar((Object) editorial2);
                estaBien = true;
                dialogStage.close();
                
            }
            else {
                Utilidades.repetido("Editorial");
            }
            
            
            
        }
        
        
    }
    
    @FXML
    @Override
    public void btnCancelar() {
        dialogStage.close();
    }
    
    @FXML
    @Override
    public boolean estaValidado() {
        
        String mensajeError = "";
        if (txtNombre.getText() == null || txtNombre.getText().length() == 0)
            mensajeError += "Debe ingresar un nombre para la Editorial!\n";
        
        if (txtAnio.getText().equals("0") || txtAnio.getText() == null || txtAnio.getText().length() == 0)
            mensajeError += "Debe ingresar un año!\n";
        else
            try {
                Integer.parseInt(txtAnio.getText());
            } catch (NumberFormatException e) {
                mensajeError += "Debe ingresar un valor numerico en el Año\n";
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
