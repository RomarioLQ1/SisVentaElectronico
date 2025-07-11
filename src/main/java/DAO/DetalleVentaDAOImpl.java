/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.CConexion;
import Modelo.DetalleVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DetalleVentaDAOImpl implements DetalleVentaDAO {

    CConexion conector = new CConexion();

    @Override
    public boolean insertarDetallesVenta(List<DetalleVenta> detalles, int idVenta) {
        String sql = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = conector.estableceConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            for (DetalleVenta d : detalles) {
                int idProducto = obtenerIdProductoPorNombre(d.getProducto(), con);
                if (idProducto == -1) {
                    System.err.println("Producto no encontrado: " + d.getProducto());
                    continue;
                }

                ps.setInt(1, idVenta);
                ps.setInt(2, idProducto);
                ps.setInt(3, d.getCantidad());
                ps.setDouble(4, d.getPrecioUnitario());
                ps.setDouble(5, d.getSubtotal());
                ps.executeUpdate();

                actualizarStockProducto(idProducto, d.getCantidad(), con); // ✅ Descuenta stock
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar detalles de venta: " + e.getMessage());
            return false;
        }
    }

// ✅ Obtener ID del producto por su nombre
    private int obtenerIdProductoPorNombre(String nombre, Connection con) throws SQLException {
        String sql = "SELECT id_producto FROM productos WHERE nombre_producto = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_producto");
            }
        }
        return -1;
    }

// ✅ Actualizar el stock restando la cantidad vendida
    private void actualizarStockProducto(int idProducto, int cantidadVendida, Connection con) throws SQLException {
        String sql = "UPDATE productos SET stock = stock - ? WHERE id_producto = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidadVendida);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        }
}
}
