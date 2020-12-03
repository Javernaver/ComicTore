
package comictore.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
        
        initRootLayout();

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
        
        stageMenuPrincipal =  (Stage) ((Node) event.getSource()).getScene().getWindow();
            
        
        initRootLayout();
        
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
            
        
        initRootLayout();
        
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
            
        
        initRootLayout();
        
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
    
    public void initRootLayout() {
            
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/comictore/pantallas/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();                    
            
            Scene scene = new Scene(rootLayout);
            stageMenuPrincipal.setTitle("Comics");
            stageMenuPrincipal.setResizable(false);
            stageMenuPrincipal.setScene(scene);
            stageMenuPrincipal.show();
            
        } catch (IOException e) {
            System.err.println(e);
        }
    }


   


    public static Stage getStageMenuPrincipal() {
        return stageMenuPrincipal;
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
