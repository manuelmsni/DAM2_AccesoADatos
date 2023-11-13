/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import app.models.Event;
import app.models.EventManager;
import java.time.LocalDate;

/**
 *
 * @author manuelmsni
 */
public class DataViewer extends javax.swing.JFrame {

    EventManager events;
    /**
     * Creates new form DataViewer
     */
    public DataViewer() {
        initComponents();
        initCustom();
    }
    
    private void initCustom(){
        events = new EventManager();
        table.setModel(events);
        loadEventTable(new Event("Evento", LocalDate.of(1997, 4, 12)));
    }
    
    private void loadEventTable(Event e){
        table.setModel(e);
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
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        inputContainer = new javax.swing.JPanel();
        dataContainer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dataInputContainer.setVerifyInputWhenFocusTarget(false);
        dataInputContainer.setLayout(new java.awt.BorderLayout());

        buttonsContainer.setMinimumSize(new java.awt.Dimension(82, 33));
        buttonsContainer.setLayout(new java.awt.GridLayout(4, 1, 5, 5));

        jButton2.setText("Guardar");
        jButton2.setMargin(new java.awt.Insets(5, 14, 5, 14));
        buttonsContainer.add(jButton2);

        jButton1.setText("Nuevo");
        buttonsContainer.add(jButton1);

        jButton3.setText("Editar");
        buttonsContainer.add(jButton3);

        jButton4.setText("Borrar");
        buttonsContainer.add(jButton4);

        dataInputContainer.add(buttonsContainer, java.awt.BorderLayout.WEST);

        inputContainer.setLayout(null);
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
    private javax.swing.JPanel buttonsContainer;
    private javax.swing.JPanel dataContainer;
    private javax.swing.JPanel dataInputContainer;
    private javax.swing.JPanel inputContainer;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
