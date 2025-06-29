/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.DetalleVenta;
import java.util.List;

public interface DetalleVentaDAO {
    boolean insertarDetalle(DetalleVenta detalle);
    List<DetalleVenta> listarPorVenta(int idVenta);
}