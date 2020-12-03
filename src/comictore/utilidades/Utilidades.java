/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.utilidades;

import comictore.controladores.FXMLMenuController;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author javie
 */
public class Utilidades {
    
    
    public static ObservableList<String> setPaises() {
        
       ObservableList<String> paises = FXCollections.observableArrayList();
        
        String[] listaPaises = Locale.getISOCountries();
        for (String pais : listaPaises) {
            Locale local = new Locale("", pais);
            String[] totalPaises = { local.getDisplayCountry() };
            for (int x = 0; x < totalPaises.length; x++) {
                paises.add(local.getDisplayCountry());
            }
        }
        return paises;
    
    }
    
    public static ObservableList<String> setIdiomas(){
                
        return FXCollections.observableArrayList("Español", "Inglés");
        
    }
    
    public static void sinSeleccionar(String cosa) {
        
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.initOwner(FXMLMenuController.getStageMenuPrincipal());
        alerta.setTitle("No hay Seleccion");
        alerta.setHeaderText("No hay ningun(a) " + cosa + " seleccionado(a)");
        alerta.setContentText("Seleccione un(a) " + cosa);

        alerta.showAndWait();
        
    }
    
    public static boolean estaSeguro(String cosa) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar " + cosa);
            alert.setHeaderText("¿Esta seguro que desea eliminar este(a) " + cosa + "?");
            alert.setContentText("Confirme para continuar");

            Optional<ButtonType> result = alert.showAndWait();
            
            return result.get() == ButtonType.OK;
    }
    
    public static void repetido(String cosa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);

        alerta.setTitle("Datos Erroneos");        
        alerta.setHeaderText("Por favor revise la informacion");
        alerta.setContentText("Este(a) " + cosa + " ya se encuentra agregado(a)!");

        alerta.showAndWait(); 

    }
    
    public static void datosErroneos(String mensajeError) {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        alert.setTitle("Datos Erroneos");
        alert.setHeaderText("Por favor revise la informacion");
        alert.setContentText(mensajeError);

        alert.showAndWait();
    }
    
    public static void agregadoComic() {
        
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informacion");
        alerta.setHeaderText("Se agregara el Comic!");
        alerta.setContentText("Si el comic ya se encotraba en la lista, este no se agregara");

        alerta.showAndWait();
    }
    
    public static final Pattern VALIDEMAIL = 
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    public static boolean emailValido(String emailStr) {
                Matcher matcher = VALIDEMAIL.matcher(emailStr);
            return matcher.find();
    }
    
    
    
    
}
