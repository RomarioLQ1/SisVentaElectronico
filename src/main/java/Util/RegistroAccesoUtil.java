/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import DAO.RegistroAccesoDAOImpl;
import Modelo.RegistroAcceso;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Melqui
 */
public class RegistroAccesoUtil {

    /**
     * Registra un evento de acceso en la base de datos.
     *
     * @param idUsuario ID del usuario
     * @param nombreUsuario Nombre del usuario
     * @param modulo Módulo desde donde se realiza la acción
     * @param accion Descripción de la acción
     * @param ip Dirección IP del cliente (puede ser localhost si es una app local)
     */
    public static void registrarAcceso(int idUsuario, String nombreUsuario, String modulo, String accion, String ip) {
        RegistroAcceso acceso = new RegistroAcceso();
        acceso.setIdUsuario(idUsuario);
        acceso.setNombreUsuario(nombreUsuario);
        acceso.setFecha(LocalDate.now().toString()); // yyyy-MM-dd
        acceso.setHora(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        acceso.setModulo(modulo);
        acceso.setAccion(accion);
        acceso.setIp(ip);

        RegistroAccesoDAOImpl dao = new RegistroAccesoDAOImpl();
        dao.registrarAcceso(acceso);
    }
}