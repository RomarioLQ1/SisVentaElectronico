/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.CConexion;
import Modelo.Venta;

import java.sql.*;

public class VentaDAOImpl implements VentaDAO {

    CConexion conector = new CConexion();

    @Override
    public int registrarVenta(Venta venta) {
        int idGenerado = -1;

        String sql = "INSERT INTO ventas (id_cliente, id_usuario, total, tipo_comprobante) VALUES (?, ?, ?, ?)";

        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int idCliente = obtenerIdClientePorNombre(venta.getNombreCliente(), con);

            if (idCliente == -1) {
                System.err.println("Cliente no encontrado");
                return -1;
            }

            ps.setInt(1, idCliente);
            ps.setInt(2, 1); // Asumimos usuario 1 por defecto
            ps.setDouble(3, venta.getTotal());
            ps.setString(4, venta.getTipoComprobante());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar venta: " + e.getMessage());
        }

        return idGenerado;
    }

    private int obtenerIdClientePorNombre(String nombre, Connection con) throws SQLException {
        String sql = "SELECT id_cliente FROM clientes WHERE nombre_cliente = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_cliente");
            }
        }
        return -1;
    }
}
