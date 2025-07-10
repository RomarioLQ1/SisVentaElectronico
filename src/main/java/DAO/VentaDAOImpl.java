/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.CConexion;
import Modelo.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentaDAOImpl implements VentaDAO {

    CConexion conector = new CConexion();

    @Override
    public int registrarVenta(Venta venta) {
        int idGenerado = -1;
        String sql = "INSERT INTO ventas (id_cliente, id_usuario, total, tipo_comprobante) VALUES (?, ?, ?, ?)";

        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // IMPORTANTE: necesitas ID del cliente y del usuario
            ps.setInt(1, obtenerIdClientePorNombre(venta.getNombreCliente()));
            ps.setInt(2, 1); // ID de usuario fijo (puedes cambiarlo si hay sesión)
            ps.setDouble(3, venta.getTotal());
            ps.setString(4, "boleta"); // o "factura"

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGenerado;
    }

    private int obtenerIdClientePorNombre(String nombre) throws SQLException {
        String sql = "SELECT id_cliente FROM clientes WHERE nombre_cliente = ?";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_cliente");
            } else {
                throw new SQLException("Cliente no encontrado con nombre: " + nombre);
            }
        }
    }
}
