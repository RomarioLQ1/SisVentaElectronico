/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.VentaDAO;
import DAO.VentaDAOImpl;
import DAO.DetalleVentaDAO;
import DAO.DetalleVentaDAOImpl;
import Modelo.Venta;

public class VentaControlador {

    private VentaDAO ventaDAO = new VentaDAOImpl();
    private DetalleVentaDAO detalleDAO = new DetalleVentaDAOImpl();

    public boolean registrarVenta(Venta venta) {
        int idVenta = ventaDAO.registrarVenta(venta);
        if (idVenta > 0) {
            return detalleDAO.insertarDetallesVenta(venta.getDetalles(), idVenta);
        }
        return false;
    }
}
