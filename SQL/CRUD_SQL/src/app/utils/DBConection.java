/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.utils;

/**
 *
 * @author Vespertino
 */
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DBConection {
    
    static String bdUrl = Constants.DBURL;
    static Connection con;
    
    private static boolean checkDatabaseExists(){
        File dbFile = new File(Constants.DBURL);
        if (dbFile.exists()) {
            System.out.println("Database file exists.");
            return true;
        } else {
            System.out.println("Database file does not exist. It may not have been created.");
            String bdUrl = chooseDatabaseLocation() + File.separator + "Database";
            if (bdUrl != null) {
                createDatabase(bdUrl);
                System.out.println("Created database!");
            } else {
                System.out.println("Database connection canceled by the user.");
                System.exit(1);
            }
        }
        return false;
    }
    
    public static Connection getConnection() {
        checkDatabaseExists();
        try {
            if (con == null || con.isClosed()) {
                String bdUrl = "jdbc:sqlite:" + Constants.DBURL;
                con = DriverManager.getConnection(bdUrl);
                System.out.println("Connected to the file!");
            }
            return con;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(2);
        }
        return null;
    }

    private static String chooseDatabaseLocation() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Database Location");

        // Configura el modo de selección para directorios
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();

            // Verifica si el usuario seleccionó un directorio
            if (selectedDirectory.isDirectory()) {
                // Devuelve la ruta del directorio seleccionado
                return "jdbc:sqlite:" + selectedDirectory.getAbsolutePath() + File.separator + "your_database_name.db";
            } else {
                // Muestra un mensaje de error si no se seleccionó un directorio
                JOptionPane.showMessageDialog(null, "Please select a valid directory.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (userSelection == JFileChooser.CANCEL_OPTION) {
            // El usuario ha cancelado la operación
            System.exit(3);
        }
        return null;
    }

    private static void createDatabase(String dbUrl) {
        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement()) {
            // Agrega aquí el código para crear las tablas si es necesario
            System.out.println("Database created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void close(){
        if(con == null) return;
        try {
            con.close();
            con = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
