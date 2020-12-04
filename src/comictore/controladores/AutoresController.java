/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Autor;
import comictore.clases.Comic;
import comictore.clases.clasesSQL.AutorSQL;
import comictore.colecciones.ListaComics;
import comictore.inicio.Colecciones;
import comictore.interfaces.BotonesTipicos;
import comictore.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public class AutoresController implements Initializable, BotonesTipicos {

    @FXML
    private TableView<Autor> tablaAutores;
    @FXML
    private TableColumn<Autor, String> columnaIDAutores;
    @FXML
    private TableColumn<Autor, String> columnaNombreAutores;
    
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
    private Label fechaNac;
    
    
    private final ObservableList<Autor> autoresData = Colecciones.getAutores().getAutoresObs();
    private ObservableList<Comic> comicsData;
    
    private final AutorSQL autorSQL = new AutorSQL();
    
    public void mostrarDetAutor(Autor autor) {
        
        if (autor != null) {
            nombre.setText(autor.getNombre());
            pais.setText(autor.getNacionalidad());
            
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");   
            fechaNac.setText(dateFormat.format(autor.getFechaNac()));
            
        }
        else {
            nombre.setText("");
            pais.setText("");
            fechaNac.setText("");
        }
        
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       
       columnaIDAutores.setCellValueFactory(CellData -> CellData.getValue().getIDP());
       columnaNombreAutores.setCellValueFactory(CellData -> CellData.getValue().getNombreP()); 
        
        // preparar asignacion de los valores para la tabla de comics
       columnaNombreComics.setCellValueFactory(CellData ->  CellData.getValue().getNombreP());
       columnaCodigoComics.setCellValueFactory(CellData -> CellData.getValue().getCodigoP());
       
       
       
       tablaAutores.setItems(autoresData);
        
       mostrarDetAutor(null);
      
       
       tablaAutores.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> mostrarDetAutor(newValue));
        
       
       tablaAutores.getSelectionModel().selectedItemProperty().addListener(
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
   
    
    public boolean ventanaEdicion(Autor autor, boolean esEdicion) {
        try {
                      
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/comictore/pantallas/EditAutores.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // dialog Stage para agregar o editar un comic.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Autor");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(FXMLMenuController.getStageMenuPrincipal());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            //dialogStage.initStyle(StageStyle.UNDECORATED);
            
            // mandar el autor al controlador de la ventana para agregar y editar 
            EditAutoresController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAutor(autor);
            controller.setEdicion(esEdicion);
 
            dialogStage.showAndWait();
            
   
            return controller.isEstaBien();
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
    }
    
    
    
    
    
    @Override
    @FXML
    public void btnAgregar() {
        
        Colecciones.getAutores().setAutoresObs(autoresData);
        Autor autor = new Autor();
        if (ventanaEdicion(autor, false) ) {
            autorSQL.insert(autor.getIDI(), autor.getNombre(), autor.getNacionalidad(), autor.getFechaNacStr() );   
            tablaAutores.getItems().add(autor);
        }
        
    }

    @Override
    @FXML
    public void btnEditar() {
        
        Autor autor = tablaAutores.getSelectionModel().getSelectedItem();
        Colecciones.getAutores().setAutoresObs(autoresData);
        
        if (autor != null) {
            
            int codigo = autor.getIDI();
            
            if (ventanaEdicion(autor, true)) {
                
                if (codigo != autor.getIDI()) {
                    
                    
                    autorSQL.delete(codigo);
                    autorSQL.insert(autor.getIDI(), autor.getNombre(), autor.getNacionalidad(), autor.getFechaNacStr() );   
                    
                }
 
                mostrarDetAutor(autor);
            }
            
        }
        else {
            Utilidades.sinSeleccionar("Autor");
        }
        
    }

    @Override
    @FXML
    public void btnEliminar() {
        
        int indice = tablaAutores.getSelectionModel().getSelectedIndex();
        
        if (indice >= 0) {
            if (Utilidades.estaSeguro("Autor")) {
                
                autorSQL.delete(tablaAutores.getSelectionModel().getSelectedItem().getIDI());
                
                tablaAutores.getItems().remove(indice);
            }
        }
        else {
            Utilidades.sinSeleccionar("Autor");
        }
        
    }

    @Override
    @FXML
    public void btnVolver(ActionEvent event) throws IOException {
        
        if (tablaAutores.getSelectionModel().getSelectedItem() != null)           
            tablaAutores.getSelectionModel().getSelectedItem().getComics().setComicsObs(comicsData);
        
        Colecciones.getAutores().setAutoresObs(autoresData);
        
        Parent root1 = FXMLLoader.load(getClass().getResource("/comictore/pantallas/FXMLMenu.fxml"));
        
        
        Scene menuScene = new Scene(root1);
        
        Stage nuevaStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        nuevaStage.setScene(menuScene);
        nuevaStage.show();
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
        Colecciones.getAutores().setAutoresObs(autoresData);
        
        
        if (tablaAutores.getSelectionModel().getSelectedItem() != null) {
            
            ListaComics comics = tablaAutores.getSelectionModel().getSelectedItem().getComics();
            
            if ( ventanaAgregarComic(comics) ){
                
                comicsData = tablaAutores.getSelectionModel().getSelectedItem().getComics().getComicsObs();
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
