/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.utils;

/**
 *
 * @author Vespertino
 */
import app.Main;
import app.models.Event;
import app.models.User;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.time.LocalDate;

public class DBConection {
    
    static PropertyManager properties = Main.getProperties();
    static String dbUrl = properties.getProperty("dbURL");
    static Connection con;
    
    private static void checkDatabaseExists(){
        File dbFile = new File(dbUrl);
        
        if (dbFile.exists()) return;

        System.out.println("Database file does not exist. It may not have been created.");
        
        String dbLocation = chooseDatabaseLocation();
        
        if(dbLocation == null || dbLocation.isBlank()){
            System.exit(1);
        }
        
        String bdUrl = dbLocation + File.separator + Constants.DATABASENAME;
        
        properties.setProperty("bdUrl", bdUrl);

        createDatabase(bdUrl);

        return;
    }
    
    /*
    public static void MariadbConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // jdbc:mysql://localhost/
            con = DriverManager.getConnection(dbUrl, "root","secret");
            DatabaseMetaData dbmd = con.getMetaData();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    public static Connection getConnection() {
        checkDatabaseExists();
        try {
            if (con == null || con.isClosed()) {
                String bdUrl = "jdbc:sqlite:" + dbUrl;
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
                return selectedDirectory.getAbsolutePath() + File.separator + Constants.DATABASENAME;
            } else {
                // Muestra un mensaje de error si no se seleccionó un directorio
                JOptionPane.showMessageDialog(null, "Please select a valid directory.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (userSelection == JFileChooser.CANCEL_OPTION) {
            // El usuario ha cancelado la operación
            return null;
        }
        return null;
    }

    private static void createDatabase(String dbUrl) {
        try (Connection connection = DriverManager.getConnection(dbUrl, "root", "secret");
             Statement statement = connection.createStatement()) {
            
            // Crear la base de datos
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS ProgramDatabase";
            statement.executeUpdate(createDatabaseQuery);

            // Seleccionar la base de datos recién creada
            String useDatabaseQuery = "USE ProgramDatabase";
            statement.executeUpdate(useDatabaseQuery);

            // Crear la tabla Evento
            String createEventoTableQuery = "CREATE TABLE IF NOT EXISTS Evento ("
                    + "UUID VARCHAR(36) PRIMARY KEY, "
                    + "Nombre VARCHAR(255), "
                    + "Fecha DATE)";
            statement.executeUpdate(createEventoTableQuery);

            // Crear la tabla Usuario
            String createUsuarioTableQuery = "CREATE TABLE IF NOT EXISTS Usuario ("
                    + "UUID_Usuario VARCHAR(36) PRIMARY KEY, "
                    + "UUID_Evento VARCHAR(36), "
                    + "Nombre VARCHAR(255), "
                    + "Apellido VARCHAR(255))"
                    + "FOREIGN KEY (UUID_Evento) REFERENCES Evento(UUID), ";
            statement.executeUpdate(createUsuarioTableQuery);
            
            System.out.println("Database created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static ArrayList<Event> getEventos(String dbUrl) {
        ArrayList<Event> eventos = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbUrl, "root", "secret");
             Statement statement = connection.createStatement()) {

            String selectEventosQuery = "SELECT * FROM Evento";
            ResultSet eventosResult = statement.executeQuery(selectEventosQuery);

            while (eventosResult.next()) {
                Event evento = new Event(eventosResult.getString("Nombre"), LocalDate.parse(eventosResult.getString("Fecha")));
                evento.setId(eventosResult.getString("UUID"));

                // Obtener usuarios para este evento
                String selectUsuariosQuery = "SELECT * FROM Usuario WHERE UUID_Evento = '" + evento.getId() + "'";
                ResultSet usuariosResult = statement.executeQuery(selectUsuariosQuery);

                while (usuariosResult.next()) {
                    User usuario = new User(usuariosResult.getString("Nombre"), usuariosResult.getString("Apellido"));
                    usuario.setId(usuariosResult.getString("UUID_Usuario"));
                    evento.addUser(usuario);
                }

                eventos.add(evento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventos;
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
