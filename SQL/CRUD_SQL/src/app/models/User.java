package app.models;

import java.util.UUID;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author manuelmsni
 */
public class User {
    
    private String eventId;
    private String id;
    private String name;
    private String surname;

    public String getId() {
        return id;
    }
    
    public String getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
    
    public User(String name, String surname){
        this.name = name;
        this.surname = surname;
    }
    
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    
}
