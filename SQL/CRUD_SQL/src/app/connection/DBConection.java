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

public abstract class DBConection {
    
    public abstract Connection getConnection();
    
    public abstract ArrayList<Event> getEventos();
    
    public abstract void close();
    
}
