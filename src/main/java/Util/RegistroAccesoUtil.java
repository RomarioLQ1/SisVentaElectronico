/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Conexion.CConexion;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

/**
 *
 * @author Melqui
 */
public class RegistroAccesoUtil {

    /**
     * Registra un evento de acceso en la base de datos.
     *
     * @param idUsuario      ID del usuario
     * @param nombreUsuario  Nombre del usuario
     * @param modulo         Módulo desde donde se realiza la acción
     * @param accion         Descripción de la acción
     */
    public static void registrarAcceso(int idUsuario, String nombreUsuario, String modulo, String accion) {
        Connection conexion = CConexion.getConexion();

        if (conexion != null) {
            try {
                String sql = "INSERT INTO registro_accesos (id_usuario, nombre_usuario, fecha, hora, modulo, accion, ip) "
                           + "VALUES (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement stmt = conexion.prepareStatement(sql);

                // Fecha y hora actuales
                LocalDateTime now = LocalDateTime.now();
                String fecha = now.toLocalDate().toString();
                String hora = now.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                // Obtener IP automáticamente (mejor método)
                String ip = obtenerIpLocal();

                stmt.setInt(1, idUsuario);
                stmt.setString(2, nombreUsuario);
                stmt.setString(3, fecha);
                stmt.setString(4, hora);
                stmt.setString(5, modulo);
                stmt.setString(6, accion);
                stmt.setString(7, ip);

                stmt.executeUpdate();
                stmt.close();
                conexion.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Obtiene la IP local no loopback (no 127.0.0.1)
     */
    private static String obtenerIpLocal() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) continue;

                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLoopbackAddress() && addr.getHostAddress().indexOf(":") == -1) {
                        return addr.getHostAddress(); // Retorna IP válida IPv4
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "127.0.0.1"; // Si no encuentra otra, retorna localhost
    }
}