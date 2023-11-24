/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.connection;

/**
 *
 * @author Vespertino
 */


import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLSyntaxErrorException;

public class MariaDBConection extends DBConection{
    
    public MariaDBConection(String prefix, String dbLocation, String dbName){
        super(prefix, dbLocation, dbName);
    }

    public Connection getConnection() {
        try {
            con = DriverManager.getConnection(getDBPATH(), "root", "docker");
            System.out.println("Connected to the database!");
            return con;
        } catch (SQLNonTransientConnectionException ntce) { // No existe la conexión
            ntce.printStackTrace();
        } catch (SQLSyntaxErrorException synte) { // No existe la base de datos
            synte.printStackTrace();
            if (createDatabase())return getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //DatabaseMetaData dbmd = con.getMetaData();
        return null;
    }
    
    private boolean createDatabase() {
        try {
            Connection conn = DriverManager.getConnection(prefix + dbLocation, "root", "docker");
            System.out.println("Creando la base de datos...");
            Statement statement = conn.createStatement();
            
            String createDatabase = "CREATE DATABASE " + dbName;
            statement.executeUpdate(createDatabase);
           
            String useDatabase = "USE " + dbName;
            statement.executeUpdate(useDatabase);

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

            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
