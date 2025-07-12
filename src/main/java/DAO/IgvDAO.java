/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Modelo.Igv;
import java.util.List;

/**
 *
 * @author av_ga
 */
public interface IgvDAO {
    boolean desactivarIGVActual();
    boolean insertarNuevoIGV(double porcentaje, int idUsuario);
    double getIGVActual();
    List<Igv> getHistorialIGV();
}
