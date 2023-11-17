/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.DAO;

import app.connection.MariaDBConection;
import app.connection.SQLiteDBConection;
import app.models.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author manuelmsni
 */
public class EventDAO implements DAO<Event>{
    
    public boolean addObject(Event evento){
        try {

            // Utiliza una sentencia preparada para evitar problemas de seguridad con la entrada del usuario
            String insertEventoQuery = "INSERT INTO Evento (UUID, Nombre, Fecha) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connect().prepareStatement(insertEventoQuery);

            // Establecer los valores de los parámetros
            preparedStatement.setString(1, evento.getId());
            preparedStatement.setString(2, evento.getName());
            preparedStatement.setString(3, evento.getDate().toString());

            // Ejecutar la inserción
            preparedStatement.executeUpdate();

            System.out.println("Evento insertado correctamente");

            disconnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateObject(Event evento) {
        try {
            // Utiliza una sentencia preparada para evitar problemas de seguridad con la entrada del usuario
            String updateEventoQuery = "UPDATE Evento SET Nombre=?, Fecha=? WHERE UUID=?";
            PreparedStatement preparedStatement = connect().prepareStatement(updateEventoQuery);

            // Establecer los valores de los parámetros
            preparedStatement.setString(1, evento.getName());
            preparedStatement.setString(2, evento.getDate().toString());
            preparedStatement.setString(3, evento.getId());

            // Ejecutar la actualización
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Evento actualizado correctamente");
                disconnect();
                return true;
            } else {
                System.out.println("No se encontró el evento con UUID: " + evento.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteObject(String eventId) {
        try {

            // Utiliza una sentencia preparada para evitar problemas de seguridad con la entrada del usuario
            String deleteEventoQuery = "DELETE FROM Evento WHERE UUID=?";
            PreparedStatement preparedStatement = connect().prepareStatement(deleteEventoQuery);

            // Establecer el valor del parámetro
            preparedStatement.setString(1, eventId);

            // Ejecutar la eliminación
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Evento eliminado correctamente");
                disconnect();
                return true;
            } else {
                System.out.println("No se encontró el evento con UUID: " + eventId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Connection connect() {
        return MariaDBConection.getConnection();
        //return SQLiteDBConection.getConnection();
    }

    public void disconnect() {
        MariaDBConection.close();
        //SQLiteDBConection.close();
    }
    
}
