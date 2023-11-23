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
import java.util.ArrayList;
/**
 *
 * @author manuelmsni
 */
public class UserDAO implements DAO<User>{
    
    DBConection conManager;
    
    public UserDAO(){
        this.conManager = Main.getConManager();
    }
    
    public boolean addObject(User usuario) {
        try {

            // Utiliza una sentencia preparada para evitar problemas de seguridad con la entrada del usuario
            String insertUsuarioQuery = "INSERT INTO Usuario (UUID_Usuario, UUID_Evento, Nombre, Apellido) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conManager.getConnection().prepareStatement(insertUsuarioQuery);

            // Establecer los valores de los parámetros
            preparedStatement.setString(1, usuario.getId());
            preparedStatement.setString(2, usuario.getEventId());
            preparedStatement.setString(3, usuario.getName());
            preparedStatement.setString(4, usuario.getSurname());

            // Ejecutar la inserción
            preparedStatement.executeUpdate();

            System.out.println("Usuario insertado correctamente");

            conManager.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateObject(User usuario) {
        try{

            // Utiliza una sentencia preparada para evitar problemas de seguridad con la entrada del usuario
            String updateUsuarioQuery = "UPDATE Usuario SET Nombre=?, Apellido=? WHERE UUID_Usuario=?";
            PreparedStatement preparedStatement = conManager.getConnection().prepareStatement(updateUsuarioQuery);

            // Establecer los valores de los parámetros
            preparedStatement.setString(1, usuario.getName());
            preparedStatement.setString(2, usuario.getSurname());
            preparedStatement.setString(3, usuario.getId());

            // Ejecutar la actualización
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Usuario actualizado correctamente");
            } else {
                return false;
            }

            conManager.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteObject(String uuidUsuario) {
        try {

            // Utiliza una sentencia preparada para evitar problemas de seguridad con la entrada del usuario
            String deleteUsuarioQuery = "DELETE FROM Usuario WHERE UUID_Usuario=?";
            PreparedStatement preparedStatement = conManager.getConnection().prepareStatement(deleteUsuarioQuery);

            // Establecer el valor del parámetro
            preparedStatement.setString(1, uuidUsuario);

            // Ejecutar la eliminación
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Usuario eliminado correctamente");
            } else {
                System.out.println("No se encontró el usuario con UUID: " + uuidUsuario);
            }

            conManager.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<User> getAll(Object parent) {
        if(parent == null) return null;
        if(!(parent instanceof Event)) return null;
        Event castedEvent = (Event) parent;
        
        ArrayList<User> users = new ArrayList<>();

        try {
            Connection con = conManager.getConnection();
            Statement statement = con.createStatement();

            // Obtener usuarios para este evento
            String selectUsuariosQuery = "SELECT * FROM Usuario WHERE UUID_Evento = '" + castedEvent.getId() + "'";
            ResultSet usuariosResult = statement.executeQuery(selectUsuariosQuery);

            while (usuariosResult.next()) {
                User usuario = new User(usuariosResult.getString("Nombre"), usuariosResult.getString("Apellido"));
                usuario.setId(usuariosResult.getString("UUID_Usuario"));
                users.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conManager.close();
        }
        return users;
    }
    
}
