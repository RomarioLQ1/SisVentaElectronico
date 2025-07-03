/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.CConexion;
import Modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    CConexion conector = new CConexion();

    @Override
    public boolean insertar(Producto p) {
        String sql = "INSERT INTO productos (nombre_producto, descripcion, precio, stock, id_categoria) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setInt(5, obtenerIdCategoriaPorNombre(p.getCategoria(), con));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Producto buscarPorId(int id) {
        String sql = "SELECT p.*, c.nombre_categoria FROM productos p " +
                     "LEFT JOIN categorias c ON p.id_categoria = c.id_categoria WHERE p.id_producto = ?";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getString("nombre_categoria")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.id_producto, p.nombre_producto, p.descripcion, p.precio, p.stock, c.nombre_categoria " +
                     "FROM productos p " +
                     "LEFT JOIN categorias c ON p.id_categoria = c.id_categoria";

        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getString("nombre_categoria")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Producto p) {
        String sql = "UPDATE productos SET nombre_producto=?, descripcion=?, precio=?, stock=?, id_categoria=? WHERE id_producto=?";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setInt(5, obtenerIdCategoriaPorNombre(p.getCategoria(), con));
            ps.setInt(6, p.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id_producto=?";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método auxiliar para obtener el ID de una categoría por su nombre.
     */
    private int obtenerIdCategoriaPorNombre(String nombreCategoria, Connection con) throws SQLException {
        String sql = "SELECT id_categoria FROM categorias WHERE nombre_categoria = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreCategoria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_categoria");
            } else {
                throw new SQLException("Categoría no encontrada: " + nombreCategoria);
            }
        }
    }
}
