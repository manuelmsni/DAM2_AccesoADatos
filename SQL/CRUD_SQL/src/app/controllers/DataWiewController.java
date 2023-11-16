/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.controllers;

import app.models.Event;
import app.models.EventManager;
import app.models.User;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.UUID;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import views.DataViewer;

/**
 *
 * @author Vespertino
 */
public class DataWiewController {
    
    private DataViewer view;
    private EventManager model;
    
    private int selectedEventRow;
    private Event selectedEvent;

    private int selectedUserRow;
    private User selectedUser;
    
    private String selectedRowId;
    
    // referencias
    private JTable table;
    private Map<String, JTextField> fields;
    private JButton btnAñadir;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnBorrar;
    private JButton btnGuardar;
    private JPanel inputContainer;
    
    
    private MouseAdapter clickEventsTable = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            selectedEventRow = table.getSelectedRow();
            selectedRowId = (String) model.getValueAt(selectedEventRow, 0);
            selectedEvent = model.getEventById(selectedRowId);
            buttonSelectedMode();
            loadEventData();
            if (e.getClickCount() == 2) {
                loadUsersTable(selectedEvent);
            }
        }
    };

    private MouseAdapter clickUsersTable = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            selectedUserRow = table.getSelectedRow();
            selectedRowId = (String) selectedEvent.getValueAt(selectedUserRow, 0);
            selectedUser = selectedEvent.getUserById(selectedRowId);
            buttonSelectedMode();
            loadUserData();
        }
    };
    
    private ActionListener saveEvent = (ActionEvent e) -> {
            String name = fields.get("nombre").getText();
            String fecha = fields.get("fecha").getText();
            if (!(name.isBlank() || fecha.isBlank())) {
                try {
                    LocalDate date = LocalDate.parse(fecha);
                    Event temp = new Event(name, date);
                    temp.setId(UUID.randomUUID().toString());
                    model.addEvent(temp);
                    buttonNewMode();
                } catch (DateTimeParseException ex) {
                    ex.printStackTrace();
                }
            }
    };
    
    private ActionListener saveUser = (ActionEvent e) -> {
        String name = fields.get("nombre").getText();
        String surname = fields.get("apellido").getText();
        if (!(name.isBlank() || surname.isBlank())) {
            User temp = new User(name, surname);
            temp.setId(UUID.randomUUID().toString());
            selectedEvent.addUser(temp);
            buttonNewMode();
        }
    };
    
    private ActionListener editEvent = (ActionEvent e) -> {
        if(selectedEvent != null){
            String name = fields.get("nombre").getText();
            String fecha = fields.get("fecha").getText();
            if (!(name.isBlank() || fecha.isBlank())) {
                try {
                    LocalDate date = LocalDate.parse(fecha);
                    selectedEvent.setName(name);
                    selectedEvent.setDate(date);
                    model.fireTableDataChanged();
                    buttonNewMode();
                } catch (DateTimeParseException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };
    
    private ActionListener editUser = (ActionEvent e) -> {
        if(selectedUser != null){
            String name = fields.get("nombre").getText();
            String surname = fields.get("apellido").getText();
            if (!(name.isBlank() || surname.isBlank())) {
                selectedUser.setName(name);
                selectedUser.setSurname(surname);
                selectedEvent.fireTableDataChanged();
                buttonNewMode();
            }
        }
    };
    
    private ActionListener deleteEvent = (ActionEvent e) -> {
        if(selectedEvent != null){
            model.removeEvent(selectedEvent);
            buttonNewMode();
        }
    };
    
    private ActionListener deleteUser = (ActionEvent e) -> {
        if(selectedUser != null){
            selectedEvent.removeUser(selectedUser);
            buttonNewMode();
        }
    };
    
    public DataWiewController(DataViewer view, EventManager model){
        this.model = model;
        loadTestData();
        
        this.view = view;
        
        this.fields = view.getFields();
        view.printInputs(this.fields);
        
        table = view.getTable();
        table.setModel(model);
        
        btnAñadir = view.getBtnAñadir();
        btnNuevo = view.getBtnNuevo();
        btnEditar = view.getBtnEditar();
        btnBorrar = view.getBtnBorrar();
        btnGuardar = view.getBtnGuardar();
        inputContainer = view.getInputContainer();
        
        loadEventsTable(model);
        btnNuevo.addActionListener((ActionEvent e) -> {
            buttonNewMode();
        });
        
        btnEditar.addActionListener((ActionEvent e) -> {
            buttonEditMode();
        });
    }

    private void buttonSelectedMode(){
        setButtonState(false, true, true, true, false);
        view.setFieldsEnabled(false);
    }
    
    private void buttonNewMode(){
        setButtonState(true, false, false, false, false);
        view.setFieldsEnabled(true);
        for(JTextField tf: fields.values()){
            tf.setText("");
        }
    }
    
    private void buttonEditMode(){
        setButtonState(false, false, false, true, true);
        view.setFieldsEnabled(true);
    }
    
    private void setButtonState(boolean btnAdd, boolean btnNew, boolean btnEdit, boolean btnDelete, boolean btnSave) {
        btnAñadir.setEnabled(btnAdd);
        btnNuevo.setEnabled(btnNew);
        btnEditar.setEnabled(btnEdit);
        btnBorrar.setEnabled(btnDelete);
        btnGuardar.setEnabled(btnSave);
    }
    
    private void loadTestData(){
        Event temp = new Event("Concierto", LocalDate.parse("1997-12-04"));
        temp.setId(UUID.randomUUID().toString());
        User u = new User("Pepe","Martín");
        u.setId(UUID.randomUUID().toString());
        temp.addUser(u);
        u = new User("Jose","María");
        u.setId(UUID.randomUUID().toString());
        temp.addUser(u);
        model.addEvent(temp);
        temp = new Event("Fiestas del pueblo", LocalDate.parse("1621-02-02"));
        temp.setId(UUID.randomUUID().toString());
        model.addEvent(temp);
    }
    
    private void loadEventsTable(EventManager em) {
        view.setTitle("Lista de eventos");
        removeMouseListeners();
        view.resetFields();
        view.initEventsInputs();
        fields = view.getFields();
        table.setModel(em);
        table.addMouseListener(clickEventsTable);
        buttonNewMode();
        btnAñadir.addActionListener(saveEvent);
        btnBorrar.addActionListener(deleteEvent);
        btnGuardar.addActionListener(editEvent);
        inputContainer.validate();
        inputContainer.repaint();
    }
    
    private void loadUsersTable(Event e) {
        view.setTitle("Evento: " + selectedEvent.getName() + " (Lista de eventos)");
        removeMouseListeners();
        view.resetFields();
        view.initUserInputs();
        fields = view.getFields();
        
        table.setModel(e);
        table.addMouseListener(clickUsersTable);
        buttonNewMode();
        btnAñadir.addActionListener(saveUser);
        btnBorrar.addActionListener(deleteUser);
        btnGuardar.addActionListener(editUser);
        initGoBack();
        inputContainer.validate();
        inputContainer.repaint();
    }
    
    private void initGoBack(){
        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(1, 4));
        inputContainer.add(inputs);
        inputs.add(new JPanel());
        inputs.add(new JPanel());
        JButton goBack = new JButton("Volver");
        goBack.addActionListener((ActionEvent ev) -> {
            loadEventsTable(model);
        });
        inputs.add(goBack);
        inputs.add(new JPanel());
    }
    
    private void loadEventData() {
        fields.get("id").setText(selectedEvent.getId());
        fields.get("nombre").setText(selectedEvent.getName());
        fields.get("fecha").setText(selectedEvent.getDate().toString());
    }
    
    private void loadUserData() {
        fields.get("id").setText(selectedUser.getId());
        fields.get("nombre").setText(selectedUser.getName());
        fields.get("apellido").setText(selectedUser.getSurname());
    }
    
    private void removeMouseListeners() {
        table.removeMouseListener(clickEventsTable);
        table.removeMouseListener(clickUsersTable);
        btnAñadir.removeActionListener(saveEvent);
        btnAñadir.removeActionListener(saveUser);
        btnBorrar.removeActionListener(deleteEvent);
        btnBorrar.removeActionListener(deleteUser);
        btnGuardar.removeActionListener(editEvent);
        btnGuardar.removeActionListener(editUser);
    }
}
