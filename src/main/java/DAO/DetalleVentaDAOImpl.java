/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.CConexion;
import Modelo.DetalleVenta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDAOImpl implements DetalleVentaDAO {

    CConexion conector = new CConexion();

    @Override
    public boolean insertarDetalle(DetalleVenta d) {
        String sql = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, d.getIdVenta());
            ps.setInt(2, d.getIdProducto());
            ps.setInt(3, d.getCantidad());
            ps.setDouble(4, d.getPrecioUnitario());
            ps.setDouble(5, d.getSubtotal());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar detalle de venta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<DetalleVenta> listarPorVenta(int idVenta) {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_venta WHERE id_venta = ?";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DetalleVenta d = new DetalleVenta();
                d.setIdDetalle(rs.getInt("id_detalle"));
                d.setIdVenta(rs.getInt("id_venta"));
                d.setIdProducto(rs.getInt("id_producto"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecioUnitario(rs.getDouble("precio_unitario"));
                d.setSubtotal(rs.getDouble("subtotal"));
                lista.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar detalles de venta: " + e.getMessage());
        }
        return lista;
    }
}
