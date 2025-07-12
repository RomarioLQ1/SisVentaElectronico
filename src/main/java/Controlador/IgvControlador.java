/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.IgvDAOlmpl;
import Modelo.Igv;
import java.util.List;

/**
 *
 * @author av_ga
 */
public class IgvControlador {
    
    private IgvDAOlmpl igvDAO = new IgvDAOlmpl();

    public boolean actualizarIGV(double nuevoPorcentaje, int idUsuario) {
        boolean desactivado = igvDAO.desactivarIGVActual();
        boolean insertado = igvDAO.insertarNuevoIGV(nuevoPorcentaje, idUsuario);
        return desactivado && insertado;
    }

    public double obtenerIGVActual() {
        return igvDAO.getIGVActual();
    }

    public List<Igv> obtenerHistorialIGV() {
        return igvDAO.getHistorialIGV();
    }
}
