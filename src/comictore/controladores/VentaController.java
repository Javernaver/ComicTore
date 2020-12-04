/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Comic;
import comictore.clases.clasesSQL.ComicSQL;
import comictore.inicio.Colecciones;
import comictore.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author javie
 */
public class VentaController implements Initializable {
    
    @FXML
    private TableView<Comic> tablaComics;
    @FXML
    private TableColumn<Comic, String> columnaCodigo;
    @FXML
    private TableColumn<Comic, String> columnaNombre;

        
    @FXML
    private TableView<Comic> tablaVentas;
    @FXML
    private TableColumn<Comic, String> colCodVentas;
    @FXML
    private TableColumn<Comic, String> colNomVentas;
    @FXML
    private TableColumn<Comic, String> colPrecioVentas;
    @FXML
    Label total;
    
    int valorTotal = 0;
    
    private final ObservableList<Comic> comicsData = Colecciones.getComics().getComicsObs();    

    
    @FXML
    public void btnAceptar(ActionEvent event) throws IOException {
        
        actualizarComicsVendidos();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ComicTore");
        alert.setHeaderText("Se ha efectuado la venta!");
        alert.setContentText("Se han vendido los comics con un valor total de: " + valorTotal);

        alert.showAndWait();
        
        Parent root1 = FXMLLoader.load(getClass().getResource("/comictore/pantallas/FXMLMenu.fxml"));
        
        
        Scene menuScene = new Scene(root1);
        
        Stage nuevaStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        nuevaStage.setScene(menuScene);
        nuevaStage.show();
        
    }
    
    
    @FXML
    public void btnCancelar(ActionEvent event) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/comictore/pantallas/FXMLMenu.fxml"));
        
        
        Scene menuScene = new Scene(root1);
        
        Stage nuevaStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        nuevaStage.setScene(menuScene);
        nuevaStage.show();
        
    }
    
    @FXML
    public void btnAgregar() {
        Comic comic = tablaComics.getSelectionModel().getSelectedItem();
        if (comic != null) {
            if (comic.getStock() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ComicTore");
                alert.setHeaderText("Comic sin Stock");
                alert.setContentText("El Comic seleccionado no tiene stock, no se agregara");

                alert.showAndWait();
            }
            else {
                valorTotal += comic.getPrecio();
                total.setText(Integer.toString(valorTotal));                
                comic.venta();
                tablaVentas.getItems().add(comic);                
            }
        }
        else {
            Utilidades.sinSeleccionar("Comic");
        }
        
    }
    
    @FXML
    public void btnEliminar() {
        int indice = tablaVentas.getSelectionModel().getSelectedIndex();
        if (indice >= 0) {
            Comic comic = tablaVentas.getSelectionModel().getSelectedItem();
            comic.cancelarVenta();
            valorTotal -= comic.getPrecio();
            tablaVentas.getItems().remove(indice);
            total.setText(Integer.toString(valorTotal)); 
        }
        else {
            Utilidades.sinSeleccionar("Comic");
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       columnaNombre.setCellValueFactory(CellData ->  CellData.getValue().getNombreP());
       columnaCodigo.setCellValueFactory(CellData -> CellData.getValue().getCodigoP());
       
       colNomVentas.setCellValueFactory(CellData ->  CellData.getValue().getNombreP());
       colCodVentas.setCellValueFactory(CellData -> CellData.getValue().getCodigoP());
       colPrecioVentas.setCellValueFactory(CellData -> new SimpleStringProperty(Integer.toString(CellData.getValue().getPrecio())));
       
       tablaComics.setItems(comicsData);                
       
       total.setText("");
    }    

    private void actualizarComicsVendidos() {
        ComicSQL comicSQL = new ComicSQL();
        for (int i = 0; i < tablaVentas.getItems().size(); i++) {
            Comic comic = tablaVentas.getItems().get(i);
            comicSQL.delete(comic.getCodigoI());
            comicSQL.insert(comic.getCodigoI(), comic.getNombre(), comic.getAnio(), comic.getPais(), comic.getEditorial().getCodigoI(), comic.getAutor().getIDI(), comic.getIdioma(), comic.getPrecio(), comic.getVentas(), comic.getStock());
        }
    }
    
}
