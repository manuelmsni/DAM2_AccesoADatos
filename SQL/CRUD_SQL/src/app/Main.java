/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import app.connection.MariaDBConection;
import app.connection.SQLiteDBConection;
import app.controllers.DataWiewController;
import app.models.EventManager;
import app.models.Event;
import app.utils.PropertyManager;
import views.DataViewer;
import java.util.List;
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
        
        MariaDBConection dbConnection = new MariaDBConection();
        List<Event> eventos = MariaDBConection.getEventos(); 
        //SQLiteDBConection dbConnection = new SQLiteDBConection();
        //List<Event> eventos = SQLiteDBConection.getEventos(); 
        
        EventManager model = new EventManager(eventos);
        DataViewer view = new DataViewer();
        DataWiewController controller = new DataWiewController(view, model);
        view.setVisible(true);
        
        dbConnection.close();
        //SQLiteDBConection.close();
    }
    
}
