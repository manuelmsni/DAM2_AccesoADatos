/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.connection;

/**
 *
 * @author Vespertino
 */
import app.Main;
import app.models.Event;
import app.models.User;
import app.utils.PropertyManager;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.time.LocalDate;

public class SQLiteDBConection {
    
    private static PropertyManager properties = Main.getProperties();
    private static String prefix = "jdbc:sqlite:";
    private static String dbLocation = properties.getProperty("dbLocation");
    private static String dbName = properties.getProperty("dbName");
    private static Connection con;
    
    private static String getDBPATH(){
        return prefix + dbLocation + File.separator + dbName;
    }
    
    public static Connection getConnection() {
        if(!checkDatabaseExists()){
            setNewDatabaseLocation();
            if(!createDatabase(getDBPATH())) return null;
        }
        
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(getDBPATH());
                System.out.println("Connected to the file!");
            }
            return con;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static boolean checkDatabaseExists(){
        
        File dbFile = new File(dbLocation + File.separator + dbName);
        
        if (dbFile.exists()) return true;

        return false;
    }
    
    private static boolean setNewDatabaseLocation(){
        
        dbLocation = chooseDirectory("Elige la ubicación para crear la base de datos:");
        
        if(dbLocation == null || dbLocation.isBlank()){
            System.out.println("Database file does not exist. It may not have been created.");
            return false;
        }
        
        properties.setProperty("dbLocation", dbLocation);
        
        return true;
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
    
    private static String chooseDirectory(String messaje) {
        JFileChooser fileChooser = new JFileChooser();
        
        String currentDirectory = System.getProperty("user.dir");
        fileChooser.setCurrentDirectory(new File(currentDirectory));
        
        fileChooser.setDialogTitle(messaje);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();

            if (selectedDirectory.isDirectory()) {
                return selectedDirectory.getAbsolutePath();
            }
        }
        return null;
    }

    private static boolean createDatabase(String dbUrl) {
        
        try {
            con = DriverManager.getConnection(dbUrl);
           
            Statement statement = con.createStatement();

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
                    + "Apellido VARCHAR(255), "
                    + "FOREIGN KEY (UUID_Evento) REFERENCES Evento(UUID))";
            statement.executeUpdate(createUsuarioTableQuery);
            
            System.out.println("Database created successfully");
            
            close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static ArrayList<Event> getEventos() {
        ArrayList<Event> eventos = new ArrayList<>();

        try (Connection connection = getConnection();
                
            Statement statement = connection.createStatement()) {

            String selectEventosQuery = "SELECT * FROM Evento";
            ResultSet eventosResult = statement.executeQuery(selectEventosQuery);
            
            while (eventosResult.next()) {
                Event evento = new Event(eventosResult.getString("Nombre"), LocalDate.parse(eventosResult.getString("Fecha")));
                evento.setId(eventosResult.getString("UUID"));

                eventos.add(evento);
            }
            
            for(Event evento: eventos){
                // Obtener usuarios para este evento
                String selectUsuariosQuery = "SELECT * FROM Usuario WHERE UUID_Evento = '" + evento.getId() + "'";
                ResultSet usuariosResult = statement.executeQuery(selectUsuariosQuery);

                while (usuariosResult.next()) {
                    User usuario = new User(usuariosResult.getString("Nombre"), usuariosResult.getString("Apellido"));
                    usuario.setId(usuariosResult.getString("UUID_Usuario"));
                    evento.addUser(usuario);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();
        return eventos;
    }
    
    public static void close(){
        if(con == null) return;
        try {
            con.close();
            con = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
