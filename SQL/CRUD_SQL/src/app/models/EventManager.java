/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author manuelmsni
 */
public class EventManager extends AbstractTableModel{
    private final String[] COLUMNNAMES = {"Id", "Nombre", "Fecha"};
    private List<Event> events;
    
    public EventManager(){
        events = new ArrayList();
    }
    
    public Event getEventById(String id){
        for(Event event: events){
            if(event.getId() == id) return event;
        }
        return null;
    }
    
    public boolean addEvent(Event e){
        if(e == null) return false;
        if(!events.contains(e)){
            events.add(e);
            fireTableDataChanged();
            return true;
        }
        return false;
    }
    
    public boolean removeEvent(Event e){
        if(e == null) return false;
        if(events.remove(e)){
            fireTableDataChanged();
            return true;
        }
        return false;
    }

    @Override
    public int getRowCount() {
        return events.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNNAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Event e = events.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> e.getId();
            case 1 -> e.getName();
            case 2 -> e.getDate();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNNAMES[column];
    }
    
}
