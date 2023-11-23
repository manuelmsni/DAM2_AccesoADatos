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

        // Muestra el cuadro de di�logo y guarda la opci�n seleccionada por el usuario
        int opcionSeleccionada = JOptionPane.showOptionDialog(
                null,  // Componente padre (en este caso, ninguno)
                "Elige la base de datos:",  // Mensaje dentro del cuadro de di�logo
                "T�tulo del cuadro de di�logo",  // T�tulo del cuadro de di�logo
                JOptionPane.DEFAULT_OPTION,  // Tipo de opciones (por defecto)
                JOptionPane.QUESTION_MESSAGE,  // Tipo de mensaje (en este caso, Pregunta)
                null,  // Icono personalizado (en este caso, ninguno)
                opciones,  // Array de opciones para los botones
                opciones[0]);  // Opci�n predeterminada seleccionada

        // Verifica la opci�n seleccionada y realiza la acci�n correspondiente
        if (opcionSeleccionada >= 0) {
            // El usuario seleccion� una opci�n v�lida
            System.out.println("Has seleccionado: " + opciones[opcionSeleccionada]);
            // Hacer algo con el valor asociado
            switch (opcionSeleccionada) {
                case 0:
                    // Opci�n "SQLite" seleccionada
                    return 1;
                case 1:
                    // Opci�n "MariaDB" seleccionada
                    return 2;
                default:
                    // No deber�a llegar aqu�, pero por si acaso
                    return 0;
            }
        } else {
            // Cerr� el cuadro de di�logo sin seleccionar ninguna opci�n
            return 0;
        }
    }
}
