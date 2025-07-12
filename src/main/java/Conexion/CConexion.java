/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class CConexion {
   // Parámetros de conexión
    private static final String usuario = "root";
    private static final String contrasena = ""; // ← aquí pon tu contraseña real
    private static final String bd = "sistema_ventaselec";
    private static final String ip = "localhost";
    private static final String puerto = "3306";

    // Cadena de conexión
    private static final String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;

    // Método que establece y devuelve la conexión
    public static Connection getConexion() {
        Connection conectar = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contrasena);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.toString());
        }
        return conectar;
    }

    public Connection estableceConexion() {
        return getConexion();
    }
}
