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
                return rs.getBytes("pdf");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔁 CAMBIADO: ahora retorna el ID de la venta
    public int registrarVenta(Venta venta) {
        int idVenta = ventaDAO.registrarVenta(venta); // este método ya lo devuelve
        if (idVenta > 0) {
            boolean detallesOK = detalleDAO.insertarDetallesVenta(venta.getDetalles(), idVenta);

            if (detallesOK) {
                try {
                    byte[] pdfBytes = GeneradorPDF.generarPDFComoBytes(venta, idVenta);

                    String sql = "UPDATE ventas SET pdf = ? WHERE id_venta = ?";
                    try (Connection con = CConexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
                        ps.setBytes(1, pdfBytes);
                        ps.setInt(2, idVenta);
                        ps.executeUpdate();
                    }

                    return idVenta; // ✅ Retorna el ID si todo fue OK
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return -1; // ❌ Error
    }
}
