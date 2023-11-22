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
import app.utils.PropertyManager;
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
   
}
