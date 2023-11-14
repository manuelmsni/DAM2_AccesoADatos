/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.utils;

/**
 *
 * @author Vespertino
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import java.sql.Statement;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DBConection {
    
    static String bdUrl = Constants.DBURL;
    static Connection con;
    
    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                String bdUrl = "jdbc:derby:" + Constants.DBURL;
                try {
                    con = DriverManager.getConnection(bdUrl);
                    System.out.println("Connected!");
                } catch (SQLException ex) {
                    // Si la base de datos no existe, solicitar la ubicación
                    if (ex.getSQLState().equals("XJ004")) {
                        String newDbUrl = chooseDatabaseLocation();
                        if (newDbUrl != null) {
                            createDatabase(newDbUrl);
                            con = DriverManager.getConnection(newDbUrl);
                            System.out.println("Connected!");
                        } else {
                            System.out.println("Database connection canceled by the user.");
                        }
                    } else {
                        ex.printStackTrace();
                    }
                }
            }
            return con;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static String chooseDatabaseLocation() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Database Location");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Derby Database Files (*.derby)", "derby"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return "jdbc:derby:" + fileChooser.getSelectedFile().getAbsolutePath() + ";create=true";
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
