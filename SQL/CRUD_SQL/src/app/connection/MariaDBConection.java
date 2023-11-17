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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLSyntaxErrorException;

public class MariaDBConection {
    
    private static PropertyManager properties = Main.getProperties();
    private static String prefix = "jdbc:mariadb:";
    private static String dbLocation = "//localhost:3306/";
    private static String dbName = "program";
    private static Connection con;
    
    private static String getDBPATH(){
        return prefix + dbLocation  + dbName;
    }
    
    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(getDBPATH(), "root", "docker");
                System.out.println("Connected to the file!");
            }
            return con;
        } catch (SQLNonTransientConnectionException ntce) { // No existe la conexión
            ntce.printStackTrace();
            

        } catch (SQLSyntaxErrorException synte) { // No existe la base de datos
            synte.printStackTrace();
            
            createDatabase();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            

        }
        
        //DatabaseMetaData dbmd = con.getMetaData();
        return null;
    }
    
    private static boolean createDatabase() {
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
