/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.CConexion;
import Modelo.Venta;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VentaDAOImpl implements VentaDAO {

    CConexion conector = new CConexion();

    @Override
    public boolean insertar(Venta v) {
        String sql = "INSERT INTO venta (cliente_id, fecha, total) VALUES (?, ?, ?)";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, v.getClienteId());
            ps.setTimestamp(2, Timestamp.valueOf(v.getFecha()));
            ps.setDouble(3, v.getTotal());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar venta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Venta> obtenerTodas() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM venta";

        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Venta(
                    rs.getInt("id"),
                    rs.getInt("cliente_id"),
                    rs.getTimestamp("fecha").toLocalDateTime(),
                    rs.getDouble("total")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener ventas: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Venta buscarPorId(int id) {
        String sql = "SELECT * FROM venta WHERE id = ?";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Venta(
                    rs.getInt("id"),
                    rs.getInt("cliente_id"),
                    rs.getTimestamp("fecha").toLocalDateTime(),
                    rs.getDouble("total")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar venta: " + e.getMessage());
        }
        return null;
    }
}
