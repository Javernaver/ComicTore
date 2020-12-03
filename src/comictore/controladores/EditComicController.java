/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Autor;
import comictore.clases.Comic;
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
public class EditComicController implements Initializable, InterfaceVentanas {

    
    
    private Stage dialogStage;
    private Comic comic;
    private boolean estaBien = false;
    
    @FXML
    private ComboBox selectorAutor;
    @FXML
    private ComboBox selectorEditorial;
    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox selectorPais;
    @FXML
    private TextField txtAnio;
    @FXML
    private ComboBox selectorIdioma;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtVentas;
    @FXML
    private TextField txtPrecio;
    
    
    public void setComic(Comic comic) {
        this.comic = comic;
                       
        selectorAutor.getSelectionModel().select(comic.getAutor());
        selectorEditorial.getSelectionModel().select(comic.getEditorial());
        
        if (comic.getPais() == null)
            selectorPais.getSelectionModel().select(233); // seleccionar Estados Unidos como pais por defecto
        else
            selectorPais.getSelectionModel().select(comic.getPais());
        
        txtNombre.setText(comic.getNombre());

        txtAnio.setText(String.valueOf(comic.getAnio()));
        
        if (comic.getIdioma() == null)
            selectorIdioma.getSelectionModel().select(0);
        else
            selectorIdioma.getSelectionModel().select(comic.getIdioma());
        
        txtStock.setText(String.valueOf(comic.getStock()));
        txtVentas.setText(String.valueOf(comic.getVentas()));
        txtPrecio.setText(String.valueOf(comic.getPrecio()));
       
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
        Comic comic2 = new Comic();
        if (estaValidado()) {
            
            comic2.setNombre(txtNombre.getText());
            comic2.setAnio(Integer.parseInt(txtAnio.getText()));

            comic2.setPais((String) selectorPais.getSelectionModel().getSelectedItem());

            comic2.setAutor((Autor) selectorAutor.getSelectionModel().getSelectedItem());

            comic2.setIdioma((String) selectorIdioma.getSelectionModel().getSelectedItem());

            comic2.setEditorial((Editorial) selectorEditorial.getSelectionModel().getSelectedItem());

            comic2.setPrecio(Integer.parseInt(txtPrecio.getText()));
            comic2.setVentas(Integer.parseInt(txtVentas.getText()));
            comic2.setStock(Integer.parseInt(txtStock.getText()));

            comic2.setCodigo();
            
            
            if (!Colecciones.getComics().comicRepetido(comic2)) { // si el comic no esta en la coleccion se guarda
                comic.clonar((Object) comic2); // guardar el comic en el objeto vinculado a la lista
                estaBien = true;
                dialogStage.close();
            }
            else{ // ventana de alerta por si el comic ya esta en la lista
                Utilidades.repetido("Comic");
            }
          
           
        } 
      
    }
    
    @FXML
    @Override
    public void btnCancelar() {
        dialogStage.close();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        iniciaSelectores();
        
    }    
    
    public void iniciaSelectores() {
        selectorAutor.setPromptText("Seleccione un Autor...");
        selectorAutor.setItems(Colecciones.getAutores().getAutoresObs());              
        selectorEditorial.setPromptText("Seleccione una Editorial...");
        selectorEditorial.setItems(Colecciones.getEditoriales().getEditorialesObs());

        selectorPais.setItems(Utilidades.setPaises());

        selectorIdioma.setItems(Utilidades.setIdiomas());
        
    }

    @Override
    public boolean estaValidado() {
        String mensajeError = "";
        
        if (txtNombre.getText() == null || txtNombre.getText().length() == 0)
            mensajeError += "Debe ingresar un nombre para el comic!\n";
        
        if (selectorAutor.getSelectionModel().getSelectedItem() == null)
            mensajeError += "Debe seleccionar un Autor!\n";
        
        if (selectorEditorial.getSelectionModel().getSelectedItem() == null)
            mensajeError += "Debe seleccionar una Editorial!\n";
        
        if (txtAnio.getText().equals("0") || txtAnio.getText() == null || txtAnio.getText().length() == 0)
            mensajeError += "Debe ingresar un año!\n";
        else
            try {
                Integer.parseInt(txtAnio.getText());
            } catch (NumberFormatException e) {
                mensajeError += "Debe ingresar un valor numerico en el Año\n";
            }
        
        if (txtStock.getText() == null || txtStock.getText().length() == 0)
            mensajeError += "El valor minimo para el stock es 0\n";
        else
            try {
                Integer.parseInt(txtStock.getText());
            } catch (NumberFormatException e) {
                mensajeError += "Debe ingresar un valor numerico en el Stock\n";
            }
        
        if (txtVentas.getText() == null || txtVentas.getText().length() == 0)
            mensajeError += "El valor minimo para las ventas es 0\n";
        else
            try {
                Integer.parseInt(txtVentas.getText());
            } catch (NumberFormatException e) {
                mensajeError += "Debe ingresar un valor numerico en las Ventas\n";
            }
        
        if (txtPrecio.getText() == null || txtPrecio.getText().length() == 0)
            mensajeError += "El valor minimo para el precio es 0\n";
        else
            try {
                Integer.parseInt(txtPrecio.getText());
            } catch (NumberFormatException e) {
                mensajeError += "Debe ingresar un valor numerico en el Precio\n";
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
