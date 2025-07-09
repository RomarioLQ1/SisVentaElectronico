/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.DetalleVenta;
import java.sql.*;
import java.util.List;
import Conexion.CConexion;

public class DetalleVentaDAOImpl implements DetalleVentaDAO {

    private CConexion conector = new CConexion();

    @Override
    public boolean insertarDetallesVenta(List<DetalleVenta> detalles, int idVenta) {
        String sql = "INSERT INTO detalle_venta (id_venta, producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (DetalleVenta d : detalles) {
                ps.setInt(1, idVenta);
                ps.setString(2, d.getProducto());
                ps.setInt(3, d.getCantidad());
                ps.setDouble(4, d.getPrecioUnitario());
                ps.setDouble(5, d.getSubtotal());
                ps.addBatch();
            }

            ps.executeBatch();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
