/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.CConexion;
import Modelo.DetalleVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DetalleVentaDAOImpl implements DetalleVentaDAO {

    CConexion conector = new CConexion();

    @Override
    public boolean insertarDetallesVenta(List<DetalleVenta> detalles, int idVenta) {
        String sql = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (DetalleVenta dv : detalles) {
                int idProducto = obtenerIdProductoPorNombre(dv.getProducto());

                ps.setInt(1, idVenta);
                ps.setInt(2, idProducto);
                ps.setInt(3, dv.getCantidad());
                ps.setDouble(4, dv.getPrecioUnitario());
                ps.setDouble(5, dv.getSubtotal());
                ps.addBatch();
            }

            ps.executeBatch();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int obtenerIdProductoPorNombre(String nombre) throws SQLException {
        String sql = "SELECT id_producto FROM productos WHERE nombre_producto = ?";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_producto");
            } else {
                throw new SQLException("Producto no encontrado: " + nombre);
            }
        }
    }
}
