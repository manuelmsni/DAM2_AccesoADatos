/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import app.connection.DBConection;
import app.connection.MariaDBConection;
import app.connection.SQLiteDBConection;
import app.controllers.DataWiewController;
import app.models.EventManager;
import app.models.Event;
import app.utils.PropertyManager;
import app.views.DataViewer;
import app.views.DatabaseSelectorView;
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
    
    private static DBConection conManager;

    public static PropertyManager getProperties() {
        return properties;
    }
    
    public static DBConection getConManager(){
        return conManager;
    }
    
    public static void main(String[] args) {
        
        properties = new PropertyManager();
        
        conManager = null;
        
        DatabaseSelectorView dbsv = new DatabaseSelectorView();
        switch(dbsv.getSelectedOption()){
            case DatabaseSelectorView.SQLITE:
                conManager = new SQLiteDBConection("jdbc:sqlite:", "dbLocation", "dbName");
                break;
            case DatabaseSelectorView.MARIADB:
                conManager = new MariaDBConection("jdbc:mariadb:", "//localhost:3306/", "program");
                break;
            case DatabaseSelectorView.CANCELLED:
                System.exit(1);
                break;
        }
        
        List<Event> eventos = conManager.getEventos(); 
        
        EventManager model = new EventManager(eventos);
        DataViewer view = new DataViewer();
        DataWiewController controller = new DataWiewController(view, model);
        view.setVisible(true);
        
        conManager.close();
    }
    
}
