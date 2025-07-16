/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.RegistroAccesoDAO;
import DAO.RegistroAccesoDAOImpl;
import Modelo.RegistroAcceso;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Melqui
 */
public class RegistroAccesoControlador {

    private final RegistroAccesoDAO dao = new RegistroAccesoDAOImpl();

    public List<RegistroAcceso> obtenerTodos() {
        return dao.obtenerTodos();
    }

    public List<RegistroAcceso> buscarRegistros(String texto, String usuario, String modulo, String fecha) {
        return dao.buscarRegistros(texto != null ? texto : "",
                                   usuario != null ? usuario : "Todos los usuarios",
                                   modulo != null ? modulo : "Todos los modulos",
                                   fecha != null ? fecha : "");
    }
}