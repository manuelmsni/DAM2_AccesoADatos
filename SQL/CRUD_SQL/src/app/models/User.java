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
    
    private String id;
    private String name;
    private String surname;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
    
    public User(String name, String surname){
        id = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
    }
}
