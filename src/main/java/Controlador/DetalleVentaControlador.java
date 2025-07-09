/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.DetalleVentaDAO;
import DAO.DetalleVentaDAOImpl;
import Modelo.DetalleVenta;
import java.util.List;

public class DetalleVentaControlador {
    private DetalleVentaDAO detalleDAO = new DetalleVentaDAOImpl();

    public boolean insertarDetalleVenta(List<DetalleVenta> detalles, int idVenta) {
        return detalleDAO.insertarDetallesVenta(detalles, idVenta);
    }
}
