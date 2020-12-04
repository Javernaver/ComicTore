/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore;

import comictore.inicio.Inicio;
import java.io.IOException;
import java.text.ParseException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author javie
 */
public class ComicTore extends Application {
    
    
    @Override
    public void start(Stage stage) throws Exception {
        try{

           Parent root = FXMLLoader.load(getClass().getResource("/comictore/pantallas/FXMLMenu.fxml"));
            
            
            Scene scene = new Scene(root);
            
            stage.setScene(scene);  
            stage.show();
            
        }
        catch(IOException ex){
            System.err.println(ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        
        Inicio.cargar();
               
        
        launch(args);
    }
    
    
   
}
