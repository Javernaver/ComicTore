/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Comic;
import comictore.clases.Editorial;
import comictore.colecciones.ListaComics;
import comictore.inicio.Colecciones;
import comictore.interfaces.BotonesTipicos;
import comictore.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author javie
 */
public class EditorialesController implements Initializable, BotonesTipicos {
    
    
    @FXML
    private TableView<Editorial> tablaEditoriales;
    @FXML
    private TableColumn<Editorial, String> columnaCodigoEditorial;
    @FXML
    private TableColumn<Editorial, String> columnaNombreEditorial;
    
    
    @FXML
    private TableView<Comic> tablaComics;
    @FXML
    private TableColumn<Comic, String> columnaCodigoComics;
    @FXML
    private TableColumn<Comic, String> columnaNombreComics;

    @FXML
    private Label nombre;
    @FXML
    private Label pais;
    @FXML
    private Label anio;
    
    private final ObservableList<Editorial> editorialesData = Colecciones.getEditoriales().getEditorialesObs();
    private ObservableList<Comic> comicsData;
    
    
    public void mostrarDetEditorial(Editorial editorial){
        
        if (editorial != null) {
            nombre.setText(editorial.getNombre());
            pais.setText(editorial.getPais());
            anio.setText(Integer.toString(editorial.getAnioFundacion()) );
        }
        else {
            nombre.setText("");
            pais.setText("");
            anio.setText("");            
        }                
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // preparar la asignacion de los valores para la tabla de editoriales
       columnaNombreEditorial.setCellValueFactory(CellData ->  CellData.getValue().getNombreP());
       columnaCodigoEditorial.setCellValueFactory(CellData -> CellData.getValue().getCodigoP());
       
       // preparar asignacion de los valores para la tabla de comics
       columnaNombreComics.setCellValueFactory(CellData ->  CellData.getValue().getNombreP());
       columnaCodigoComics.setCellValueFactory(CellData -> CellData.getValue().getCodigoP());
                     
       // llenar la tabla con las editoriales
       tablaEditoriales.setItems(editorialesData);
       
       // limpiar las labels
       mostrarDetEditorial(null);
       
       // crear el listener para que se vaya actualizando la informacion de las editoras al seleccionar una
       tablaEditoriales.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> mostrarDetEditorial(newValue));
       
       // crear listener para que al seleccionar una editorial se muestren los comics de esta editorial
       tablaEditoriales.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> {
                   
                   if (newValue != null) {
                       
                        comicsData = newValue.getComics().getComicsObs();
                        
                        if (comicsData != null) {
                            tablaComics.setItems(comicsData);
                        }
                        else {     
                            tablaComics.getItems().clear();
                        }
                   }
                   
               } );
       
       
    }    
    
    public boolean ventanaEdicion(Editorial editorial) {
        try {
                      
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/comictore/pantallas/EditEditoriales.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // dialog Stage para agregar o editar un comic.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editorial");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(FXMLMenuController.getStageMenuPrincipal());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            //dialogStage.initStyle(StageStyle.UNDECORATED);
            
            // mandar el comic al controlador de la ventana para agregar y editar 
            EditEditorialesController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEditorial(editorial);
 
            dialogStage.showAndWait();
            
   
            return controller.isEstaBien();
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
    }
    
    
    
    @FXML
    @Override
    public void btnAgregar() {
        Colecciones.getEditoriales().setEditorialesObs(editorialesData);
        Editorial editorial = new Editorial();
        if ( ventanaEdicion(editorial) ){
            tablaEditoriales.getItems().add(editorial);       
        }  
    }
    
    
    @FXML
    @Override
    public void btnEliminar() {
        
        int indice = tablaEditoriales.getSelectionModel().getSelectedIndex();
        
        if (indice >= 0) {
            if (Utilidades.estaSeguro("Editorial")) {
                tablaEditoriales.getItems().remove(indice);
            }
        }
        else {
            Utilidades.sinSeleccionar("Editorial");
        }
        
        
    }
    
    @FXML
    @Override
    public void btnEditar() {
        Editorial editorial = tablaEditoriales.getSelectionModel().getSelectedItem();
        Colecciones.getEditoriales().setEditorialesObs(editorialesData);
        
        if (editorial != null) {
            if (ventanaEdicion(editorial)) {
                mostrarDetEditorial(editorial);
            }
        }
        else{
            Utilidades.sinSeleccionar("Editorial");
        }
    }
    
    
    @FXML
    @Override
    public void btnVolver(ActionEvent event) throws IOException {
        
        if (tablaEditoriales.getSelectionModel().getSelectedItem() != null)           
            tablaEditoriales.getSelectionModel().getSelectedItem().setComicsObs(comicsData);
        
        Colecciones.getEditoriales().setEditorialesObs(editorialesData);
       
        Parent root1 = FXMLLoader.load(getClass().getResource("/comictore/pantallas/FXMLMenu.fxml"));
        
        
        Scene menuScene = new Scene(root1);
        
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        app_Stage.setScene(menuScene);
        app_Stage.show();
    
     // FXMLMenuController.getStageMenuPrincipal().close();

    }
    
   
    public boolean ventanaAgregarComic(ListaComics comics) {
        try {
                      
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/comictore/pantallas/AddComic.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // dialog Stage para agregar o editar un comic.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Comics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(FXMLMenuController.getStageMenuPrincipal());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            //dialogStage.initStyle(StageStyle.UNDECORATED);
            
            // mandar el comic al controlador de la ventana para agregar y editar 
            AddComicController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setComics(comics);
           
            
            dialogStage.showAndWait();
            
   
            return controller.isEstaBien();
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
    }
    
    
    
    @FXML
    public void btnAgregarComic() {
        Colecciones.getEditoriales().setEditorialesObs(editorialesData);
        
        
        if (tablaEditoriales.getSelectionModel().getSelectedItem() != null) {
            
            ListaComics comics = tablaEditoriales.getSelectionModel().getSelectedItem().getComics();
           
            if ( ventanaAgregarComic(comics) ){
          
                comicsData = tablaEditoriales.getSelectionModel().getSelectedItem().getComics().getComicsObs();
                tablaComics.setItems(comicsData);
             
            } 
        }
        else {
            Utilidades.sinSeleccionar("Editorial");
        }
       
        
    }
    
    @FXML
    public void btnEliminarComic() {
        
        int indice = tablaComics.getSelectionModel().getSelectedIndex();
        
        if (indice >= 0) {
            if (Utilidades.estaSeguro("Comic")) {
                tablaComics.getItems().remove(indice);
            }
        }
        else {
            Utilidades.sinSeleccionar("Comic");
        }
        
    }
}
