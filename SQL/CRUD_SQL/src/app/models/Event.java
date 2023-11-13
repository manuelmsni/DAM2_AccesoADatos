/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author manuelmsni
 */
public class Event extends AbstractTableModel{
    private final String[] COLUMNNAMES = {"Id", "Nombre", "Apellido"};
    private List<User> users;
    private String id;
    public String name;
    public LocalDate date;
    
    public Event(String name, LocalDate date){
        users = new ArrayList();
        this.name = name;
        this.date = date;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public boolean addUser(User u){
        if(u == null) return false;
        if(!users.contains(u)){
            users.add(u);
            fireTableDataChanged();
            return true;
        }
        return false;
    }
    
    public boolean removeUser(User u){
        if(u == null) return false;
        if(users.remove(u)){
            return true;
        }
        return false;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNNAMES.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User u = users.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> users.indexOf(u);
            case 1 -> u.getName();
            case 2 -> u.getSurname();
            default -> null;
        };
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMNNAMES[column];
    }
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }
}
