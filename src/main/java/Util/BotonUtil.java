/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonUtil {

    public static void aplicarEfectoHover(JButton boton) {
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setBorderPainted(false);

        Color fondoOriginal = boton.getBackground();

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(fondoOriginal.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(fondoOriginal);
            }
        });
    }
}
