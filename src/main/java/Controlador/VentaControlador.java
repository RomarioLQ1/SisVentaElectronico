/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Conexion.CConexion;
import DAO.VentaDAO;
import DAO.VentaDAOImpl;
import DAO.DetalleVentaDAO;
import DAO.DetalleVentaDAOImpl;
import Modelo.Venta;
import Util.GeneradorPDF;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VentaControlador {

    private VentaDAO ventaDAO = new VentaDAOImpl();
    private DetalleVentaDAO detalleDAO = new DetalleVentaDAOImpl();
    
    public byte[] obtenerPDFVenta(int idVenta) {
        String sql = "SELECT pdf FROM ventas WHERE id_venta = ?";
        try (Connection con = CConexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBytes("pdf"); // ← aquí se obtiene el PDF como byte[]
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    
    public boolean registrarVenta(Venta venta) {
        int idVenta = ventaDAO.registrarVenta(venta);
        if (idVenta > 0) {
            boolean detallesOK = detalleDAO.insertarDetallesVenta(venta.getDetalles(), idVenta);

            if (detallesOK) {
                // Generar PDF en memoria (en lugar de en archivo)
                try {
                    // Generar el PDF como byte[]
                    byte[] pdfBytes = GeneradorPDF.generarPDFComoBytes(venta, idVenta);

                    // Guardar el PDF en la columna `pdf`
                    String sql = "UPDATE ventas SET pdf = ? WHERE id_venta = ?";
                    try (Connection con = CConexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
                        ps.setBytes(1, pdfBytes);
                        ps.setInt(2, idVenta);
                        ps.executeUpdate();
                    }

                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
 }

