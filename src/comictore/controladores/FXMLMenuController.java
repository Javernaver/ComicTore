
package comictore.controladores;

import comictore.inicio.Inicio;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author javie
 */
public class FXMLMenuController implements Initializable {
    
    private static Stage stageMenuPrincipal;
    private BorderPane rootLayout;
   
    
    @FXML
    private void btnComics(ActionEvent event) throws IOException {
        
        stageMenuPrincipal =  (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        initRootLayout(1);

        mostrarComics();
        
    }
    
    public void mostrarComics() {
        
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/comictore/pantallas/Comics.fxml"));
            AnchorPane verComic = (AnchorPane) loader.load();

        
            rootLayout.setCenter(verComic);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    @FXML
    private void btnEditoriales(ActionEvent event) throws IOException {
        
        stageMenuPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
        
        initRootLayout(2);
        
        mostrarEditoriales();        
    }
    
    public void mostrarEditoriales() {
        
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/comictore/pantallas/Editoriales.fxml"));
            AnchorPane verEditorial = (AnchorPane) loader.load();

        
            rootLayout.setCenter(verEditorial);
        } catch (IOException e) {
            System.err.println(e);
        }
        
        
        
    }
    
    @FXML
    public void btnAutores(ActionEvent event){
        
        stageMenuPrincipal =  (Stage) ((Node) event.getSource()).getScene().getWindow();
            
        
        initRootLayout(3);
        
        mostrarAutores();
        
    }
    
    public void mostrarAutores() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/comictore/pantallas/Autores.fxml"));
            AnchorPane verAutores = (AnchorPane) loader.load();

        
            rootLayout.setCenter(verAutores);
        } catch (IOException e) {
            System.out.print("no cargue");
            System.err.println(e);
        }
    }
    
    @FXML
    public void btnClientes(ActionEvent event) {
        
        stageMenuPrincipal =  (Stage) ((Node) event.getSource()).getScene().getWindow();
            
        
        initRootLayout(4);
        
        mostrarClientes();
        
    }
    
    public void mostrarClientes() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/comictore/pantallas/Clientes.fxml"));
            AnchorPane verClientes = (AnchorPane) loader.load();

        
            rootLayout.setCenter(verClientes);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public void initRootLayout(int modo) {
            
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/comictore/pantallas/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();                    
            RootLayoutController controller = loader.getController();
            controller.setModo(modo);
            
            Scene scene = new Scene(rootLayout);
            stageMenuPrincipal.setTitle("ComicTore");
            stageMenuPrincipal.setResizable(false);
            stageMenuPrincipal.setScene(scene);
            stageMenuPrincipal.show();
            
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    @FXML
    public void btnSalir() {
        
        Inicio.guardar();
        
        System.exit(0);
    }
    
    
    private void nuevaVentana(String rutaFXML, String titulo, int tipo) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(rutaFXML));
        AnchorPane page = (AnchorPane) loader.load();
 
        Stage dialogStage = new Stage();
        dialogStage.setTitle(titulo);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stageMenuPrincipal);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        
        switch (tipo) {
            case 1 : EditorialMasVendidaController controller = loader.getController();
                     controller.setStage(dialogStage);
                     break;
            case 2 : TopVentasComicsController controller2 = loader.getController();
                     controller2.setStage(dialogStage);
                     break;
            case 3 : TopAutoresMasVentasController controller3 = loader.getController();
                     controller3.setStage(dialogStage);
                     break;
        }

        dialogStage.showAndWait();
        
    }
    
    
    @FXML
    public void btnEditorialMasVendida(ActionEvent event) throws IOException {
          
        nuevaVentana("/comictore/pantallas/EditorialMasVendida.fxml", "Editorial con más Ventas", 1);
      
        
    }
    
    @FXML
    public void btnVentasComics() throws IOException {
        
        nuevaVentana("/comictore/pantallas/TopVentasComics.fxml", "Top de Ventas de Comics", 2);
    
    }
    
    @FXML
    public void btnAutoresMasVentas() throws IOException {
        
        nuevaVentana("/comictore/pantallas/TopAutoresMasVentas.fxml", "Top de Autores con más ventas", 3);
 
    }

    public static Stage getStageMenuPrincipal() {
        return stageMenuPrincipal;
    }
    
    @FXML
    public void btnVenta(ActionEvent event) throws IOException {
        
        Parent root1 = FXMLLoader.load(getClass().getResource("/comictore/pantallas/Venta.fxml"));
      
        Scene menuScene = new Scene(root1);
        
        Stage nuevaStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        nuevaStage.setScene(menuScene);
        nuevaStage.setResizable(false);
        nuevaStage.show();
    }    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

 
    
}
