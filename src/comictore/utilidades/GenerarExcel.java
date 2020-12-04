/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comictore.utilidades;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author javie
 */
public class GenerarExcel {
    
    public void generarExcel(String[][] entrada, String ruta){
       
        if (ruta == null) // si se calcelo el guardado la ruta queda en null
            return;
        
        try {
            WorkbookSettings conf = new WorkbookSettings();
            conf.setEncoding("ISO-8859-1");
            WritableWorkbook workBook = Workbook.createWorkbook(new File(ruta), conf);
            
            WritableSheet sheet = workBook.createSheet("Elementos", 0);
            
            WritableFont f = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD);
            WritableCellFormat formato = new WritableCellFormat(f);
            
            for (int i = 0; i < entrada.length; i++) { // filas 
                
                for (int j = 0; j < entrada[i].length; j++) { // columnas
                    
                    sheet.addCell(new jxl.write.Label(j, i, entrada[i][j], formato)); // agregar la celda
                }
                
            }
            
            workBook.write();
            workBook.close();            
            
        }
        catch(IOException e) {
            System.err.println(e);
        } catch (WriteException ex) {
            Logger.getLogger(GenerarExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    
}
