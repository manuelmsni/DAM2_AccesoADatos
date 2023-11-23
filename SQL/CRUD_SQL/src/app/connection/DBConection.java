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
import static app.connection.SQLiteDBConection.dbLocation;
import static app.connection.SQLiteDBConection.dbName;
import static app.connection.SQLiteDBConection.prefix;
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

public abstract class DBConection {
    
    protected PropertyManager properties;
    protected static String prefix;
    protected static String dbLocation;
    protected static String dbName;
    protected static Connection con;
    
    public DBConection(String prefix, String dbLocation, String dbName){
        this.prefix = prefix;
        this.dbLocation = dbLocation;
        this.dbName = dbName;
        properties = Main.getProperties();
    }
    
    protected String getDBPATH(){
        return prefix + dbLocation + dbName;
    }
    
    public abstract Connection getConnection();
    
    public abstract void close();
    
}
