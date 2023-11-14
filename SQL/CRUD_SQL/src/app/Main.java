/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import app.controllers.DataWiewController;
import app.models.EventManager;
import views.DataViewer;
/**
 *
 * @author Vespertino
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventManager model = new EventManager();
        DataViewer view = new DataViewer();
        DataWiewController controller = new DataWiewController(view, model);
        view.setVisible(true);
    }
    
}
