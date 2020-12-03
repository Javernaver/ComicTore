/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Cliente;
import comictore.clases.Comic;
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
public class ClientesController implements Initializable, BotonesTipicos {
    
    
    @FXML
    private TableView<Cliente> tablaClientes;
    @FXML
    private TableColumn<Cliente, String> columnaIDClientes;
    @FXML
    private TableColumn<Cliente, String> columnaNombreClientes;
    
    
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
    @FXML
    private Label email;
    
    
    
    private final ObservableList<Cliente> clientesData = Colecciones.getClientes().getClientesObs();
    private ObservableList<Comic> comicsData;
    
    
    public void mostrarDetCliente(Cliente cliente) {
        
        if (cliente != null) {
            nombre.setText(cliente.getNombre());
            pais.setText(cliente.getNacionalidad());
            
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");   
            fechaNac.setText(dateFormat.format(cliente.getFechaNac()));
            email.setText(cliente.getEmail());
            
        }
        else {
            nombre.setText("");
            pais.setText("");
            fechaNac.setText("");
            email.setText("");
        }    
        
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       columnaIDClientes.setCellValueFactory(CellData -> CellData.getValue().getIDP());
       columnaNombreClientes.setCellValueFactory(CellData -> CellData.getValue().getNombreP());
        
        // preparar asignacion de los valores para la tabla de comics
       columnaNombreComics.setCellValueFactory(CellData ->  CellData.getValue().getNombreP());
       columnaCodigoComics.setCellValueFactory(CellData -> CellData.getValue().getCodigoP());
       
       tablaClientes.setItems(clientesData);
       
       mostrarDetCliente(null);
       
       tablaClientes.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> mostrarDetCliente(newValue));
        
       
       tablaClientes.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> {
                   
                   if (newValue != null) {
                       
                        comicsData = newValue.getComicsFav().getComicsObs();
                        
                        if (comicsData != null) {
                            tablaComics.setItems(comicsData);
                        }
                        else {     
                            tablaComics.getItems().clear();
                        }
                   }
                   
               } );
       
    }    

    public boolean ventanaEdicion(Cliente cliente) {
        try {
                      
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/comictore/pantallas/EditClientes.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // dialog Stage para agregar o editar un comic.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cliente");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(FXMLMenuController.getStageMenuPrincipal());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            //dialogStage.initStyle(StageStyle.UNDECORATED);
            
            // mandar el cliente al controlador de la ventana para agregar y editar 
            EditClientesController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCliente(cliente);
 
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
        Colecciones.getClientes().setClientesObs(clientesData);
        
        Cliente cliente = new Cliente();
        if (ventanaEdicion(cliente)) {
            tablaClientes.getItems().add(cliente);
        }
        
    }

    @Override
    @FXML
    public void btnEditar() {
        
        Cliente cliente = tablaClientes.getSelectionModel().getSelectedItem();
        Colecciones.getClientes().setClientesObs(clientesData);
        
        if (cliente != null) {
            if (ventanaEdicion(cliente)) {
                mostrarDetCliente(cliente);
            }
        }
        else {
            Utilidades.sinSeleccionar("Cliente");
        }
        
        
    }

    @Override
    @FXML
    public void btnEliminar() {
        
        int indice = tablaClientes.getSelectionModel().getSelectedIndex();
        
        if (indice >= 0) {
            if (Utilidades.estaSeguro("Cliente")) {
                tablaClientes.getItems().remove(indice);
            }
        }
        else {
            Utilidades.sinSeleccionar("Cliente");
        }
        
    }

    @Override
    @FXML
    public void btnVolver(ActionEvent event) throws IOException {
        
        
        if (tablaClientes.getSelectionModel().getSelectedItem() != null)           
            tablaClientes.getSelectionModel().getSelectedItem().getComicsFav().setComicsObs(comicsData);
        
        Colecciones.getClientes().setClientesObs(clientesData);
        
   
        Parent root1 = FXMLLoader.load(getClass().getResource("/comictore/pantallas/FXMLMenu.fxml"));
        
        
        Scene menuScene = new Scene(root1);
        
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        app_Stage.setScene(menuScene);
        app_Stage.show();
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
    public void btnAgregarComics() {
        Colecciones.getClientes().setClientesObs(clientesData);
        
        if (tablaClientes.getSelectionModel().getSelectedItem() != null) {
            
            ListaComics comics = tablaClientes.getSelectionModel().getSelectedItem().getComicsFav();
            
            if ( ventanaAgregarComic(comics) ){
                comicsData = tablaClientes.getSelectionModel().getSelectedItem().getComicsFav().getComicsObs();
                tablaComics.setItems(comicsData);
            } 
        }
        else {
            Utilidades.sinSeleccionar("Cliente");
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
