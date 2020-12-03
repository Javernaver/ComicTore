/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Comic;
import comictore.colecciones.ListaComics;
import comictore.inicio.Colecciones;
import comictore.utilidades.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author javie
 */
public class AddComicController implements Initializable {

    
    private Stage dialogStage;
    private ListaComics comics;
    private boolean estaBien = false;
    
    
    @FXML
    private TableView<Comic> tablaComics;
    @FXML
    private TableColumn<Comic, String> columnaCodigo;
    @FXML
    private TableColumn<Comic, String> columnaNombre;

    
    private final ObservableList<Comic> comicsData = Colecciones.getComics().getComicsObs();
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        columnaNombre.setCellValueFactory(CellData ->  CellData.getValue().getNombreP());
        columnaCodigo.setCellValueFactory(CellData -> CellData.getValue().getCodigoP());
        
        tablaComics.setItems(comicsData);
        
    }    

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isEstaBien() {
        return estaBien;
    }

    public void setComics(ListaComics comics) {
        this.comics = comics;
    }
    
    
    
    @FXML
    public void btnAgregar() {
        
        if (tablaComics.getSelectionModel().getSelectedItem() != null) {
            Utilidades.agregadoComic();

            comics.addComic(tablaComics.getSelectionModel().getSelectedItem());
        }
        else {
            Utilidades.sinSeleccionar("Comic");
        }
    }
   
    @FXML
    public void btnVolver() {
        estaBien = true;
        dialogStage.close();
    }
    
}
