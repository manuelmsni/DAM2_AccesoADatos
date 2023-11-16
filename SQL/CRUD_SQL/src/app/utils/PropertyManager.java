/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Vespertino
 */
public class PropertyManager {
    
    private Properties properties;
    private String propertiesFile;

    public PropertyManager() {
        this.propertiesFile = Constants.PROPERTYFILE;
        this.properties = new Properties();
        loadPropertys();
    }

    private void loadPropertys() {
        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    private void saveProperties() {
        try (FileOutputStream fos = new FileOutputStream(propertiesFile)) {
            properties.store(fos, "Archivo de propiedades actualizado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
