package main;

import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import Vista.Login;

public class MainSistema {
    public static void main(String[] args) {
        try {
            // Aplicar el estilo visual del sistema operativo (Look & Feel)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo aplicar Look & Feel.");
        }

        // Ejecutar la ventana de Login en el hilo de eventos de Swing (recomendado)
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
