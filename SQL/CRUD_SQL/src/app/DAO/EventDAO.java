/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.DAO;

import app.Main;
import app.connection.DBConection;
import app.models.Event;
import app.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author manuelmsni
 */
public class EventDAO implements DAO<Event>{
    
    DBConection conManager;
    
    public EventDAO(){
        this.conManager = Main.getConManager();
    }
    
    public boolean addObject(Event evento){
        try {

            // Utiliza una sentencia preparada para evitar problemas de seguridad con la entrada del usuario
            String insertEventoQuery = "INSERT INTO Evento (UUID, Nombre, Fecha) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conManager.getConnection().prepareStatement(insertEventoQuery);

            // Establecer los valores de los parámetros
            preparedStatement.setString(1, evento.getId());
            preparedStatement.setString(2, evento.getName());
            preparedStatement.setString(3, evento.getDate().toString());

            // Ejecutar la inserción
            preparedStatement.executeUpdate();

            System.out.println("Evento insertado correctamente");

            conManager.close();
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
            PreparedStatement preparedStatement = conManager.getConnection().prepareStatement(updateEventoQuery);

            // Establecer los valores de los parámetros
            preparedStatement.setString(1, evento.getName());
            preparedStatement.setString(2, evento.getDate().toString());
            preparedStatement.setString(3, evento.getId());

            // Ejecutar la actualización
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Evento actualizado correctamente");
                conManager.close();
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
            PreparedStatement preparedStatement = conManager.getConnection().prepareStatement(deleteEventoQuery);

            // Establecer el valor del parámetro
            preparedStatement.setString(1, eventId);

            // Ejecutar la eliminación
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Evento eliminado correctamente");
                conManager.close();
                return true;
            } else {
                System.out.println("No se encontró el evento con UUID: " + eventId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<Event> getAll(Object parent) {
        ArrayList<Event> eventos = new ArrayList<>();

        try {
            Connection con = conManager.getConnection();
            Statement statement = con.createStatement();

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
        } finally {
            conManager.close();
        }

        return eventos;
    }
}
