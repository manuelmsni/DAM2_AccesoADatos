/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import app.DAO.EventDAO;
import app.DAO.UserDAO;
import app.connection.DBConection;
import app.connection.MariaDBConection;
import app.connection.SQLiteDBConection;
import app.controllers.DataWiewController;
import app.models.EventManager;
import app.models.Event;
import app.utils.DatabaseSelector;
import app.utils.PropertyManager;
import app.views.DataViewer;
import java.util.List;
import javax.swing.JOptionPane;
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
    
    private static EventDAO eventDao;
    
    private static UserDAO userDao;

    public static PropertyManager getProperties() {
        return properties;
    }
    
    public static DBConection getConManager(){
        return conManager;
    }
    
    public static UserDAO getUserDao(){
        return userDao;
    }
    
    public static EventDAO getEventDao(){
        return eventDao;
    }
    
    public static void main(String[] args) {
        
        properties = new PropertyManager();
        
        conManager = null;
        
        DatabaseSelector dbsv = new DatabaseSelector();
        switch(dbsv.getOption()){
            case DatabaseSelector.SQLITE:
                conManager = new SQLiteDBConection("jdbc:sqlite:", properties.getProperty("dbLocation"), properties.getProperty("dbName"));
                break;
            case DatabaseSelector.MARIADB:
                conManager = new MariaDBConection("jdbc:mariadb:", "//localhost:3306/", "program");
                break;
            case DatabaseSelector.CANCELLED:
                System.exit(1);
                break;
        }
        
        userDao = new UserDAO();
        eventDao = new EventDAO();
        
        List<Event> eventos = eventDao.getAll(null);
        
        EventManager model = new EventManager(eventos);
        DataViewer view = new DataViewer();
        DataWiewController controller = new DataWiewController(view, model);
        view.setVisible(true);
        
        conManager.close();
    }
    

}
