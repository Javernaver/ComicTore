/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Autor;
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
public class TopAutoresMasVentasController implements Initializable {

    @FXML
    private TableView<Autor> tablaAutores;
    @FXML
    private TableColumn<Autor, String> colPos;
    @FXML
    private TableColumn<Autor, String> colNombre;
    @FXML
    private TableColumn<Autor, String> colVentas;
    
    
    private final ObservableList autoresData = Colecciones.getAutores().getAutoresObs();
    
    private Stage stageVentana;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colNombre.setCellValueFactory(CellData -> CellData.getValue().getNombreP());
        colPos.setCellValueFactory(CellData -> new SimpleStringProperty( Integer.toString(autoresData.indexOf(CellData.getValue()) + 1) ));
        colVentas.setCellValueFactory(CellData -> new SimpleStringProperty(Integer.toString(CellData.getValue().ventasAutor())));
        
        definirTop();
    }    

    public void setStage(Stage dialogStage) {
        this.stageVentana = dialogStage;
    }
    
    @FXML
    public void btnVolver() {
        stageVentana.close();
    }
    
    public void definirTop() {
        Collections.sort(autoresData, Autor.porVentasMayorMenor);
        tablaAutores.setItems(autoresData);
    }
}
