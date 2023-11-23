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
import static app.connection.MariaDBConection.con;
import static app.connection.MariaDBConection.dbLocation;
import static app.connection.MariaDBConection.dbName;
import static app.connection.MariaDBConection.prefix;
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

public class SQLiteDBConection extends DBConection{
    
    protected PropertyManager properties;
    protected static String prefix;
    protected static String dbLocation;
    protected static String dbName;
    protected static Connection con;
    
    public SQLiteDBConection(String prefix, String dbLocation, String dbName){
        this.prefix = prefix;
        this.dbLocation = dbLocation;
        this.dbName = dbName;
        
        properties = Main.getProperties();
    }
    
    private String getDBPATH(){
        return prefix + dbLocation + dbName;
    }
    
    public Connection getConnection() {
        if(!checkDatabaseExists()){
            if(!setNewDatabaseLocation()) return null;
              
            if(!createDatabase()) return null;
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

    private boolean checkDatabaseExists(){
        File dbFile = new File(dbLocation + File.separator + dbName);
        if (dbFile.exists()) return true;
        return false;
    }
    
    private boolean setNewDatabaseLocation(){
        
        dbLocation = chooseDirectory("Elige la ubicación para crear la base de datos:");
        if(dbLocation == null || dbLocation.isBlank()){
            System.out.println("Database file does not exist. It may not have been created.");
            return false;
        }
        properties.setProperty("dbLocation", dbLocation);
        return true;
    }
    
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
                return selectedDirectory.getAbsolutePath() + File.separator;
            }
        }
        return null;
    }
    
    protected boolean createDatabase() {
        try {
            con = DriverManager.getConnection(getDBPATH());
           
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
    
        public ArrayList<Event> getEventos() {
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
