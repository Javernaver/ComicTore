/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Comic;
import comictore.inicio.Colecciones;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
public class TopVentasComicsController implements Initializable {
    
    @FXML
    private TableView<Comic> tablaComics;
    @FXML
    private TableColumn<Comic, String> colPos;
    @FXML
    private TableColumn<Comic, String> colNombre;
    @FXML
    private TableColumn<Comic, String> colVentas;
    
    private Stage stageVentana;
    
    private final ObservableList<Comic> comicsData = Colecciones.getComics().getComicsObs();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colNombre.setCellValueFactory(CellData -> CellData.getValue().getNombreP());
        colPos.setCellValueFactory(CellData -> new SimpleStringProperty( Integer.toString(comicsData.indexOf(CellData.getValue()) + 1) ));
        colVentas.setCellValueFactory(CellData -> new SimpleStringProperty(Integer.toString(CellData.getValue().getVentas())));
        
        
        definirTop();
    }    

    public void setStage(Stage dialogStage) {
        this.stageVentana = dialogStage;
    }
    
    @FXML
    public void volver(){
        stageVentana.close();
    }

    public void definirTop() {
        Collections.sort(comicsData, Comic.porVentasMayorMenor);
        tablaComics.setItems(comicsData);
    }
}
