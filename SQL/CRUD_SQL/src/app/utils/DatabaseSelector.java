/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.utils;

import javax.swing.JOptionPane;

/**
 *
 * @author manuelmsni
 */
public class DatabaseSelector {
    public static final int SQLITE = 1;
    public static final int MARIADB = 2;
    public static final int CANCELLED = 0;
    public static int getOption(){
        // Array de opciones para los botones
        Object[] opciones = {"SQLite", "MariaDB"};

        // Muestra el cuadro de diálogo y guarda la opción seleccionada por el usuario
        int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,  // Componente padre (en este caso, ninguno)
                "Elige la base de datos:",  // Mensaje dentro del cuadro de diálogo
                "Título del cuadro de diálogo",  // Título del cuadro de diálogo
                JOptionPane.DEFAULT_OPTION,  // Tipo de opciones (por defecto)
                JOptionPane.QUESTION_MESSAGE,  // Tipo de mensaje (en este caso, Pregunta)
                null,  // Icono personalizado (en este caso, ninguno)
                opciones,  // Array de opciones para los botones
                opciones[0]);  // Opción predeterminada seleccionada

        // Verifica la opción seleccionada y realiza la acción correspondiente
        if (opcionSeleccionada >= 0) {
            // El usuario seleccionó una opción válida
            System.out.println("Has seleccionado: " + opciones[opcionSeleccionada]);
            // Hacer algo con el valor asociado
            switch (opcionSeleccionada) {
                case 0:
                    // Opción "SQLite" seleccionada
                    return 1;
                case 1:
                    // Opción "MariaDB" seleccionada
                    return 2;
                default:
                    // No debería llegar aquí, pero por si acaso
                    return 0;
            }
        } else {
            // Cerró el cuadro de diálogo sin seleccionar ninguna opción
            return 0;
        }
    }
}
