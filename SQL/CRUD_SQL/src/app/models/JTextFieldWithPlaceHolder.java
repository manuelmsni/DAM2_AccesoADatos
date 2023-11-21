/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.models;

import javax.swing.JTextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 *
 * @author Vespertino
 */

public class JTextFieldWithPlaceHolder extends JTextField{
    private String text;

    public JTextFieldWithPlaceHolder(String text) {
        super();
        this.text = text;
        init();
    }

    private void init() {
        
        setText(text);
        
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(text)) {
                    setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(text);
                }
            }
        });
        
    }
    
    public void setPlaceHolder(){
        setText(text);
    }

}

