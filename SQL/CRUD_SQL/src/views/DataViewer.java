/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author manuelmsni
 */
public class DataViewer extends javax.swing.JFrame {

    private EventManager events;

    private Map<String, JTextField> fields;
    private int selectedEventRow;
    private Event selectedEvent;

    private int selectedUserRow;
    private User selectedUser;

    private String selectedRowId;

    private MouseAdapter clickEventsTable = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            selectedEventRow = table.getSelectedRow();
            selectedRowId = (String) events.getValueAt(selectedEventRow, 0);
            selectedEvent = events.getEventById(selectedRowId);
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
            selectedRowId = (String) selectedEvent.getValueAt(selectedEventRow, 0);
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
                    events.addEvent(temp);
                    buttonNewMode();
                    repaint();
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
            repaint();
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
                    events.fireTableDataChanged();
                    buttonNewMode();
                    repaint();
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
                repaint();
            }
        }
    };
    
    private ActionListener deleteEvent = (ActionEvent e) -> {
        if(selectedEvent != null){
            events.removeEvent(selectedEvent);
            buttonNewMode();
            repaint();
        }
    };
    
    private ActionListener deleteUser = (ActionEvent e) -> {
        if(selectedUser != null){
            selectedEvent.removeUser(selectedUser);
            buttonNewMode();
            repaint();
        }
    };
    
    private void buttonSelectedMode(){
        setButtonState(false, true, true, true, false);
        setFieldsEnabled(false);
    }
    
    private void buttonNewMode(){
        setButtonState(true, false, false, false, false);
        setFieldsEnabled(true);
        for(JTextField tf: fields.values()){
            tf.setText("");
        }
    }
    
    private void buttonEditMode(){
        setButtonState(false, false, false, true, true);
        setFieldsEnabled(true);
    }
    
    private void setButtonState(boolean btnAdd, boolean btnNew, boolean btnEdit, boolean btnDelete, boolean btnSave) {
        btnAñadir.setEnabled(btnAdd);
        btnNuevo.setEnabled(btnNew);
        btnEditar.setEnabled(btnEdit);
        btnBorrar.setEnabled(btnDelete);
        btnGuardar.setEnabled(btnSave);
    }
    
    private void setFieldsEnabled(boolean enabled) {
        for(JTextField tf: fields.values()){
            if(!tf.equals(fields.get("id"))){
                tf.setEnabled(enabled);
            }
        }
    }

    /**
     * Creates new form DataViewer
     */
    public DataViewer() {
        initComponents();
        initCustom();
        loadTestData();
    }
    
    private void loadTestData(){
        Event temp = new Event("sedfg", LocalDate.parse("1997-12-04"));
        temp.setId(UUID.randomUUID().toString());
        User u = new User("awf","awf");
        u.setId(UUID.randomUUID().toString());
        temp.addUser(u);
        u = new User("rqae","afh");
        u.setId(UUID.randomUUID().toString());
        temp.addUser(u);
        events.addEvent(temp);
        temp = new Event("awf", LocalDate.parse("1621-02-02"));
        temp.setId(UUID.randomUUID().toString());
        events.addEvent(temp);
    }

    private void initCustom() {
        events = new EventManager();
        table.setModel(events);
        loadEventsTable(events);
        btnNuevo.addActionListener((ActionEvent e) -> {
            buttonNewMode();
        });
        btnEditar.addActionListener((ActionEvent e) -> {
            buttonEditMode();
        });
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
    
    private JTextField createField(String text) {
        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(1, 4));
        inputContainer.add(inputs);
        inputs.add(new JPanel());
        inputs.add(new JLabel(text + ":"));
        JTextField output = new JTextField();
        inputs.add(output);
        inputs.add(new JPanel());
        fields.put(text, output);
        return output;
    }

    private void initEventsInputs() {
        inputContainer.removeAll();
        fields = new HashMap();
        createField("id").setEnabled(false);
        createField("nombre");
        createField("fecha");
    }
    
    private void loadEventsTable(EventManager em) {
        setTitle("Lista de eventos");
        removeMouseListeners();
        initEventsInputs();
        table.setModel(em);
        table.addMouseListener(clickEventsTable);
        buttonNewMode();
        btnAñadir.addActionListener(saveEvent);
        btnBorrar.addActionListener(deleteEvent);
        btnGuardar.addActionListener(editEvent);
        repaint();
    }

    private void loadEventData() {
        fields.get("id").setText(selectedEvent.getId());
        fields.get("nombre").setText(selectedEvent.getName());
        fields.get("fecha").setText(selectedEvent.getDate().toString());
    }

    private void initUserInputs() {
        inputContainer.removeAll();
        fields = new HashMap();
        createField("id").setEnabled(false);
        createField("nombre");
        createField("apellido");
    }

    private void loadUsersTable(Event e) {
        setTitle("Evento: " + selectedEvent.getName() + " (Lista de eventos)");
        removeMouseListeners();
        initUserInputs();
        table.setModel(e);
        table.addMouseListener(clickUsersTable);
        buttonNewMode();
        btnAñadir.addActionListener(saveUser);
        btnBorrar.addActionListener(deleteUser);
        btnGuardar.addActionListener(editUser);
        initGoBack();
        repaint();
    }
    
    private void initGoBack(){
        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(1, 4));
        inputContainer.add(inputs);
        inputs.add(new JPanel());
        inputs.add(new JPanel());
        JButton goBack = new JButton("Volver");
        goBack.addActionListener((ActionEvent ev) -> {
            loadEventsTable(events);
        });
        inputs.add(goBack);
        inputs.add(new JPanel());
    }

    private void loadUserData() {
        fields.get("id").setText(selectedUser.getId());
        fields.get("nombre").setText(selectedUser.getName());
        fields.get("apellido").setText(selectedUser.getSurname());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dataInputContainer = new javax.swing.JPanel();
        buttonsContainer = new javax.swing.JPanel();
        btnAñadir = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        inputContainer = new javax.swing.JPanel();
        dataContainer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dataInputContainer.setPreferredSize(new java.awt.Dimension(155, 200));
        dataInputContainer.setVerifyInputWhenFocusTarget(false);
        dataInputContainer.setLayout(new java.awt.BorderLayout());

        buttonsContainer.setMinimumSize(new java.awt.Dimension(82, 33));
        buttonsContainer.setLayout(new java.awt.GridLayout(5, 1, 5, 5));

        btnAñadir.setText("Añadir");
        btnAñadir.setMargin(new java.awt.Insets(5, 14, 5, 14));
        buttonsContainer.add(btnAñadir);

        btnNuevo.setText("Nuevo");
        buttonsContainer.add(btnNuevo);

        btnEditar.setText("Editar");
        buttonsContainer.add(btnEditar);

        btnGuardar.setText("Guardar");
        buttonsContainer.add(btnGuardar);

        btnBorrar.setText("Borrar");
        buttonsContainer.add(btnBorrar);

        dataInputContainer.add(buttonsContainer, java.awt.BorderLayout.WEST);

        inputContainer.setLayout(new java.awt.GridLayout(5, 1));
        dataInputContainer.add(inputContainer, java.awt.BorderLayout.CENTER);

        getContentPane().add(dataInputContainer, java.awt.BorderLayout.NORTH);

        dataContainer.setLayout(new java.awt.BorderLayout());

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table);

        dataContainer.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(dataContainer, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DataViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataViewer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAñadir;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JPanel buttonsContainer;
    private javax.swing.JPanel dataContainer;
    private javax.swing.JPanel dataInputContainer;
    private javax.swing.JPanel inputContainer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
