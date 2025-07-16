/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.RegistroAcceso;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Melqui
 */
public interface RegistroAccesoDAO {
    List<RegistroAcceso> obtenerTodos();
    List<RegistroAcceso> buscarRegistros(String texto, String usuario, String modulo, String fecha);
    public void registrarAcceso(RegistroAcceso ra);

}
