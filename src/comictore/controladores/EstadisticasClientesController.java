/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.controladores;

import comictore.clases.Cliente;
import comictore.inicio.Colecciones;
import java.net.URL;
import java.util.ArrayList;
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
public class EstadisticasClientesController implements Initializable {

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
        categorias.addAll("0-9", "10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70+");
        
        xAxis.setCategories(categorias);
        
        asignarValores();
        
        
    }

    
    public void asignarValores() {
         
        ArrayList<Cliente> clientes = Colecciones.getClientes().getClientes();
                
        int[] valores = new int[8];
        
        
        for (Cliente cliente : clientes) {
            
            switch ((int)cliente.getComicsFav().largo() / 10 ) {
                
                case 0: valores[0]++; // entre 0-9
                        break;
                case 1: valores[1]++; // entre 10-19
                        break;
                case 2: valores[2]++; // entre 20-29
                        break;
                case 3: valores[3]++; // entre 30-39
                        break;
                case 4: valores[4]++; // entre 40-49
                        break;
                case 5: valores[5]++; // entre 50-59
                        break;
                case 6: valores[6]++; // entre 60-69
                        break;
                case 7: valores[7]++; // entre 70+
                        break;        
                
                default: valores[7]++;
            }
    
        }
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        for (int i = 0; i < valores.length; i++) {
            series.getData().add(new XYChart.Data<>(categorias.get(i), valores[i]));
        }
        barChart.getData().add(series); 
    }
}
