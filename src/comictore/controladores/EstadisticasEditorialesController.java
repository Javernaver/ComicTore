/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.inicio.Colecciones;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author javie
 */
public class EstadisticasEditorialesController implements Initializable {

    
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private final ObservableList<String> categorias = FXCollections.observableArrayList();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categorias.addAll("DC Comics", "Marvel Comics");
        
        xAxis.setCategories(categorias);
        
        asignarValores();
    }   
    
    public void asignarValores() {
        
        int[] valores = new int[2];
        
        if (Colecciones.getEditoriales().getEditorial("DC Comics") != null)
            valores[0] = Colecciones.getEditoriales().getEditorial("DC Comics").getComics().largo();
        if (Colecciones.getEditoriales().getEditorial("Marvel Comics") != null)
            valores[1] = Colecciones.getEditoriales().getEditorial("Marvel Comics").getComics().largo();
        
        
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        for (int i = 0; i < valores.length; i++) {
            series.getData().add(new XYChart.Data<>(categorias.get(i), valores[i]));
        }
       barChart.getData().add(series);
    }
}
