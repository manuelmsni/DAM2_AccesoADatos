/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.DAO;

/**
 *
 * @author manuelmsni
 */
public interface DAO<T> {
    public boolean addObject(T object);
    public boolean updateObject(T object);
    public boolean deleteObject(String id);
}
