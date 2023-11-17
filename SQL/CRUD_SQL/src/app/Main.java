/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import app.controllers.DataWiewController;
import app.models.EventManager;
import app.connection.SQLiteDBConection;
import app.utils.PropertyManager;
import views.DataViewer;
import java.sql.Connection;
/**
 *
 * @author Vespertino
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static PropertyManager properties;

    public static PropertyManager getProperties() {
        return properties;
    }
    
    public static void main(String[] args) {
        
        properties = new PropertyManager();
        
        SQLiteDBConection dbConnection = new SQLiteDBConection();
        
        EventManager model = new EventManager(SQLiteDBConection.getEventos());
        DataViewer view = new DataViewer();
        DataWiewController controller = new DataWiewController(view, model);
        view.setVisible(true);
        
        dbConnection.close();
    }
    
}
