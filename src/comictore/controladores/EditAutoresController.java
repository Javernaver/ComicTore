/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Autor;
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
public class EditAutoresController implements Initializable, InterfaceVentanas {
    
    private Stage dialogStage;
    private Autor autor;
    private boolean estaBien = false;
    private boolean esEdicion;
    
    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox selectorPais;
    @FXML
    private TextField txtFechaNac;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectorPais.setItems(Utilidades.setPaises());
    }    

    
    public void setAutor(Autor autor) {
        this.autor = autor;
        txtNombre.setText(autor.getNombre());
        
        if (autor.getNacionalidad() == null)
            selectorPais.getSelectionModel().select(233);
        else
            selectorPais.getSelectionModel().select(autor.getNacionalidad());
       
        if (autor.getFechaNac() != null) {
     
            txtFechaNac.setText(autor.getFechaNacStr());          
        }
        else {
            txtFechaNac.setText("01/01/1900");
        }
        
        txtNombre.requestFocus();
        txtNombre.selectAll();

        
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    
    public boolean isEstaBien() {
        return estaBien;
    }
    
    @FXML
    @Override
    public void btnAceptar() {
        
        Autor autor2 = new Autor();
        
        if (estaValidado()) {
            if (esEdicion)
                autor2.setID(autor.getIDI());
            autor2.setNombre(txtNombre.getText());
            autor2.setNacionalidad((String) selectorPais.getSelectionModel().getSelectedItem());
            
            try {
                autor2.setFechaNac(txtFechaNac.getText());
            } catch (ParseException ex) {
                Logger.getLogger(EditAutoresController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (!esEdicion)
                autor2.asignarID();
            
            boolean esRepetido = Colecciones.getAutores().autorRepetido(autor2);
            
            if (!esRepetido || esEdicion) {
                
                autor.clonar((Object) autor2);
                estaBien = true;
                dialogStage.close();
            }
            else {
                Utilidades.repetido("Autor");
            }
            
        }
        
        
    }
    
    @FXML
    @Override
    public void btnCancelar() {
        dialogStage.close();
    }

    @Override
    public boolean estaValidado() {
        
        String mensajeError = "";
        if (txtNombre.getText() == null || txtNombre.getText().length() == 0)
            mensajeError += "Debe ingresar un nombre para la Editorial!\n";
        
        
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

    public void setEdicion(boolean esEdicion) {
        this.esEdicion = esEdicion;
    }
}
