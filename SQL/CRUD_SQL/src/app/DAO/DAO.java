/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.DAO;

/**
 *
 * @author manuelmsni
 */

import app.connection.DBConection;
import java.sql.Connection;
import java.util.List;

public interface DAO<T> {
    public List<T> getAll(Object parent);
    public boolean addObject(T object);
    public boolean updateObject(T object);
    public boolean deleteObject(String id);
}
