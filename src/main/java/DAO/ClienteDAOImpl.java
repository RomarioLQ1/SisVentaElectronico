/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.CConexion;
import Modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    CConexion conector = new CConexion();

    @Override
    public boolean insertar(Cliente c) {
        String sql = "INSERT INTO clientes (dni_cliente, nombre_cliente, telefono, direccion) VALUES (?, ?, ?, ?)";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getDireccion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre_cliente"),
                    rs.getString("dni_cliente"),
                    rs.getString("telefono"),
                    rs.getString("direccion")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre_cliente"),
                    rs.getString("dni_cliente"),
                    rs.getString("telefono"),
                    rs.getString("direccion")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Cliente c) {
        String sql = "UPDATE clientes SET nombre_cliente=?, dni_cliente=?, telefono=?, direccion=? WHERE id_cliente=?";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDni());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getDireccion());
            ps.setInt(5, c.getIdCliente());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id_cliente=?";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}
