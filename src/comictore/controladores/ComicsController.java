/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Autor;
import comictore.clases.Comic;
import comictore.clases.Editorial;
import comictore.clases.clasesSQL.ComicSQL;
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
public class ComicsController implements Initializable, BotonesTipicos {
    @FXML
    private TableView<Comic> tablaComics;
    @FXML
    private TableColumn<Comic, String> columnaCodigo;
    @FXML
    private TableColumn<Comic, String> columnaNombre;
    
    

    @FXML
    private Label nombre;
    @FXML
    private Label autor;
    @FXML
    private Label pais;
    @FXML
    private Label anio;
    @FXML
    private Label editorial;
    @FXML
    private Label idioma;
    @FXML
    private Label stock;
    @FXML
    private Label ventas;
    @FXML
    private Label precio;
    
    private final ObservableList<Comic> comicsData = Colecciones.getComics().getComicsObs();
    
    private final ComicSQL comicSQL = new ComicSQL();

    
    @FXML
    @Override
    public void btnVolver(ActionEvent event) throws IOException {
            
        
        Colecciones.getComics().setComicsObs(comicsData);
        //Colecciones.getEditoriales().actualizarEditoriales();
       
        Parent root1 = FXMLLoader.load(getClass().getResource("/comictore/pantallas/FXMLMenu.fxml"));
        
        
        Scene menuScene = new Scene(root1);
        
        Stage nuevaStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        nuevaStage.setScene(menuScene);
        nuevaStage.show();
        
       
 
       

    }
    

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnaNombre.setCellValueFactory(CellData ->  CellData.getValue().getNombreP());
        columnaCodigo.setCellValueFactory(CellData -> CellData.getValue().getCodigoP());
        
        tablaComics.setItems(comicsData);
         mostrarDetComic(null);      
        // listener para mostrar la informacion de un comic cuando esta seleccionado
        tablaComics.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> mostrarDetComic(newValue));

        
    }    
    
    
    private void mostrarDetComic(Comic comic) {
        
        if (comic != null) {
            nombre.setText(comic.getNombre());
            autor.setText(comic.getAutor().getNombre());
            pais.setText(comic.getPais());
            anio.setText(String.valueOf(comic.getAnio()));
            editorial.setText(comic.getEditorial().getNombre());
            idioma.setText(comic.getIdioma());
            stock.setText(String.valueOf(comic.getStock()));
            ventas.setText(String.valueOf(comic.getVentas()));
            precio.setText(String.valueOf(comic.getPrecio()));
        }
        else {
            nombre.setText("");
            autor.setText("");
            pais.setText("");
            anio.setText("");
            editorial.setText("");
            idioma.setText("");
            stock.setText("");
            ventas.setText("");
            precio.setText("");
        }
        
    }
    
    /**
     * 
     * @param comic 
     * @param esEdicion 
     * @return 
     */
    public boolean ventanaEdicion(Comic comic, boolean esEdicion) {
        try {
                      
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/comictore/pantallas/EditComic.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // dialog Stage para agregar o editar un comic.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Comic");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(FXMLMenuController.getStageMenuPrincipal());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            //dialogStage.initStyle(StageStyle.UNDECORATED);
            
            // mandar el comic al controlador de la ventana para agregar y editar 
            EditComicController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setComic(comic);
            controller.setEdicion(esEdicion);
            
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
        Colecciones.getComics().setComicsObs(comicsData);
        Comic comic = new Comic();
        if ( ventanaEdicion(comic, false) ){
            comicSQL.insert(comic.getCodigoI(), comic.getNombre(), comic.getAnio(), comic.getPais(), comic.getEditorial().getCodigoI(), comic.getAutor().getIDI(), comic.getIdioma(), comic.getPrecio(), comic.getVentas(), comic.getStock()); 
            tablaComics.getItems().add(comic);
            Colecciones.getEditoriales().addComicEditorial(comic.getEditorial() , comic);
        }      
       
    }
   
    @FXML
    @Override
    public void btnEditar() {
        
        Comic comic = tablaComics.getSelectionModel().getSelectedItem();
        
        Colecciones.getComics().setComicsObs(comicsData);
        
        if (comic != null) {
            
            Editorial edi = comic.getEditorial();
            Autor aut = comic.getAutor();
            int codigo = comic.getCodigoI();
            
            if ( ventanaEdicion(comic, true) ) {
                
                // actualizar informacion de la editorial si cambio
                if (edi != comic.getEditorial()){
                    
                    edi.getComics().getComics().remove(comic);
                    
                    Colecciones.getEditoriales().addComicEditorial(comic.getEditorial(), comic);
                }
                
                // actualizar informacion del autor si cambio
                if (aut != comic.getAutor()) {
                    
                    aut.getComics().getComics().remove(comic);
                    
                    Colecciones.getAutores().addComicAutor(comic.getAutor(), comic);
                    
                }
                
                // actualizar BD
               // if (codigo != comic.getCodigoI()) {
                    
                    //comicSQL.update(comic.getCodigoI(), comic.getNombre(), comic.getAnio(), comic.getPais(), comic.getEditorial().getCodigoI(), comic.getAutor().getIDI(), comic.getIdioma(), comic.getPrecio(), comic.getVentas(), comic.getStock() );
                    comicSQL.delete(comic.getCodigoI());
                    comicSQL.insert(comic.getCodigoI(), comic.getNombre(), comic.getAnio(), comic.getPais(), comic.getEditorial().getCodigoI(), comic.getAutor().getIDI(), comic.getIdioma(), comic.getPrecio(), comic.getVentas(), comic.getStock()); 
               // }
                
                mostrarDetComic(comic);   
            }
            
        } else {    
            Utilidades.sinSeleccionar("Comic");
        }
    
    }
    
    @FXML
    @Override
    public void btnEliminar() {
        
        int indice = tablaComics.getSelectionModel().getSelectedIndex();
       
        if (indice >= 0) {
            if ( Utilidades.estaSeguro("Comic") ) {
                
                comicSQL.delete(tablaComics.getSelectionModel().getSelectedItem().getCodigoI());
                
                tablaComics.getItems().remove(indice);
                
            }
        }
        else {
            Utilidades.sinSeleccionar("Comic");
        }
        
    }

    
    
}
