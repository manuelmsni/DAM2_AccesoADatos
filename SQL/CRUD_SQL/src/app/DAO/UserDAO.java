/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.DAO;

import app.connection.SQLiteDBConection;
import static app.connection.SQLiteDBConection.close;
import static app.connection.SQLiteDBConection.getConnection;
import app.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author manuelmsni
 */
public class UserDAO {
    public boolean addObject(User usuario) {
        try {

            // Utiliza una sentencia preparada para evitar problemas de seguridad con la entrada del usuario
            String insertUsuarioQuery = "INSERT INTO Usuario (UUID_Usuario, UUID_Evento, Nombre, Apellido) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = SQLiteDBConection.getConnection().prepareStatement(insertUsuarioQuery);

            // Establecer los valores de los par�metros
            preparedStatement.setString(1, usuario.getId());
            preparedStatement.setString(2, usuario.getEventId());
            preparedStatement.setString(3, usuario.getName());
            preparedStatement.setString(4, usuario.getSurname());

            // Ejecutar la inserci�n
            preparedStatement.executeUpdate();

            System.out.println("Usuario insertado correctamente");

            SQLiteDBConection.close();
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
            PreparedStatement preparedStatement = SQLiteDBConection.getConnection().prepareStatement(updateUsuarioQuery);

            // Establecer los valores de los par�metros
            preparedStatement.setString(1, usuario.getName());
            preparedStatement.setString(2, usuario.getSurname());
            preparedStatement.setString(3, usuario.getId());

            // Ejecutar la actualizaci�n
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Usuario actualizado correctamente");
            } else {
                return false;
            }

            SQLiteDBConection.close();
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
            PreparedStatement preparedStatement = SQLiteDBConection.getConnection().prepareStatement(deleteUsuarioQuery);

            // Establecer el valor del par�metro
            preparedStatement.setString(1, uuidUsuario);

            // Ejecutar la eliminaci�n
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Usuario eliminado correctamente");
            } else {
                System.out.println("No se encontr� el usuario con UUID: " + uuidUsuario);
            }

            close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
