/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.colecciones.*;
import comictore.inicio.Colecciones;
import comictore.inicio.Inicio;
import comictore.utilidades.GenerarExcel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author javie
 */
public class RootLayoutController implements Initializable {

   
    private int modo; /*
                        1-Comics
                        2-Editoriales
                        3-Autores
                        4-Clientes
                        */
    @FXML
    public void mostrarEstadisticas() {
        switch (modo) {
            case 1 : ventanaEstadisticas("/comictore/pantallas/EstadisticasComics.fxml", "Estadisticas de Comics");                     
                     break;
            case 2 : ventanaEstadisticas("/comictore/pantallas/EstadisticasEditoriales.fxml", "Estadisticas de Editoriales");                     
                     break;
            case 3 : ventanaEstadisticas("/comictore/pantallas/EstadisticasAutores.fxml", "Estadisticas de Autores");                     
                     break;
            case 4 : ventanaEstadisticas("/comictore/pantallas/EstadisticasClientes.fxml", "Estadisticas de Clientes");
                     break;
        }
    }
    
    public void ventanaEstadisticas(String rutaFXML, String titulo) {
        
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(rutaFXML));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle(titulo);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.initOwner(FXMLMenuController.getStageMenuPrincipal());
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.show();
        }
        catch (IOException e) {
               System.err.print(e);
        }
       
    }
    
 
    
    @FXML
    public void expExcel() {
        
        GenerarExcel excel = new GenerarExcel();
        
        switch (modo) {
            
            case 1 : excel.generarExcel(exportarComicsExcel(), getRuta());
                    break;
            case 2 : excel.generarExcel(exportarEditorialesExcel(), getRuta());
                    break;
            case 3 : excel.generarExcel(exportarAutoresExcel(), getRuta());
                    break;
            case 4 : excel.generarExcel(exportarClientesExcel(), getRuta());
                    break;
            
            
        }
    }
    
    
    
    
    /**
     * generar matriz con los comics para generar el excel
     * @return comics a guardar en el excel
     */    
    public String[][] exportarComicsExcel(){
        
        ListaComics comics = Colecciones.getComics();
        
        String[][] datos = new String[comics.largo() + 1][10];
        
        // definir Cabezeras de las celdas
        datos[0][0] = "Codigo Comic";
        datos[0][1] = "Nombre Comic";
        datos[0][2] = "Autor";
        datos[0][3] = "Pais";
        datos[0][4] = "Año";
        datos[0][5] = "Editorial";
        datos[0][6] = "Idioma";
        datos[0][7] = "Stock";
        datos[0][8] = "Ventas";
        datos[0][9] = "Precio";
        
        for (int i = 0; i < comics.largo(); i++) {
            // guardar los datos en la matriz
            datos[i+1][0] = comics.getComic(i).getCodigo();
            datos[i+1][1] = comics.getComic(i).getNombre();
            datos[i+1][2] = comics.getComic(i).getAutor().getNombre();
            datos[i+1][3] = comics.getComic(i).getPais();
            datos[i+1][4] = Integer.toString(comics.getComic(i).getAnio());
            datos[i+1][5] = comics.getComic(i).getEditorial().getNombre();
            datos[i+1][6] = comics.getComic(i).getIdioma();
            datos[i+1][7] = Integer.toString(comics.getComic(i).getStock());
            datos[i+1][8] = Integer.toString(comics.getComic(i).getVentas());            
            datos[i+1][9] = Integer.toString(comics.getComic(i).getPrecio());
      
            
        }
     
        return datos;
        
    }
    
    public String[][] exportarEditorialesExcel() {
        
        ListaEditoriales editoriales = Colecciones.getEditoriales();
        
        String[][] datos = new String[editoriales.largo()][editoriales.getEditorialMasComics().getComics().largo() + 1];
        
        
        for (int i = 0; i < editoriales.largo(); i++) {
            
            datos[i][0] = editoriales.getEditorial(i).getNombre();
            
            for (int j = 0; j < editoriales.getEditorial(i).getComics().largo(); j++) {
              
                datos[i][j+1] = editoriales.getEditorial(i).getComics().getComic(j).getNombre();
                
            }
            
        }
    
        return datos;
    }
    
    public String[][] exportarAutoresExcel() {
        
        ListaAutores autores = Colecciones.getAutores();
        
        String[][] datos = new String[autores.largo()][autores.getAutorMasComics().getComics().largo() + 1];
        
        for (int i = 0; i < autores.largo(); i++) {
            
            datos[i][0] = autores.getAutor(i).getNombre();
            
            for (int j = 0; j < autores.getAutor(i).getComics().largo(); j++) {
                
                datos[i][j+1] = autores.getAutor(i).getComics().getComic(j).getNombre();
            }
            
        }
              
        
        return datos;
    }
    
    public String[][] exportarClientesExcel() {
        
        ListaClientes clientes = Colecciones.getClientes();
        
        String[][] datos = new String[clientes.largo() + 1][2];
        
        datos[0][0] = "Nombre Cliente";
        datos[0][1] = "e-mail";
        
        for (int i = 0; i < clientes.largo(); i++) {
            
            for (int j = 0; j < 2; j++) {
                datos[i+1][0] = clientes.getCliente(i).getNombre();
                datos[i+1][1] = clientes.getCliente(i).getEmail();
            }
            
        }
        
        
        return datos;
    }
    
    
    public String getRuta() {
        
        String ruta = null;
        FileChooser fileChooser = new FileChooser();
        
        // definir los filtros
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Archivo Excel (*.xls)", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);                

        // mostrar ventana para elegir la ruta
        File file = fileChooser.showSaveDialog(FXMLMenuController.getStageMenuPrincipal());
        
        if (file != null) {
            // Corregir la extension de ser necesario
            if (!file.getPath().endsWith(".xls")) {
                file = new File(file.getPath() + ".xls");
            }
            
            ruta = file.toString();            
            
        }
        
        return ruta;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    

    public void setModo(int modo) {
        this.modo = modo;
    }

    @FXML
    public void acercaDe() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	alert.setTitle("ComicTore");
    	alert.setHeaderText("Acerca de...");
    	alert.setContentText("Autores: Javier del Canto - Nicolas Figuroa");

    	alert.showAndWait();
    }
    
    
    @FXML
    public void salir() {
        Inicio.guardar();
        
        System.exit(0);
    }

    
}
