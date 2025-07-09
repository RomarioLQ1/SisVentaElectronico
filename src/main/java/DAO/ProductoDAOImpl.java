



package DAO;

import Conexion.CConexion;
import Modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    private final Connection conn;

    public ProductoDAOImpl() {
        this.conn = new CConexion().estableceConexion();
    }

    // ===================== OBTENER TODAS LAS CATEGORÍAS =====================
    @Override
    public List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT nombre_categoria FROM categorias";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categorias.add(rs.getString("nombre_categoria"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

    // ===================== BUSCAR PRODUCTOS POR FILTRO Y CATEGORÍA =====================
    @Override
    public List<Producto> buscarProductos(String texto, String categoria) {
        List<Producto> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT p.id_producto, p.nombre_producto, p.descripcion, p.precio, p.stock, c.nombre_categoria " +
            "FROM productos p INNER JOIN categorias c ON p.id_categoria = c.id_categoria WHERE 1=1");

        // Parámetros dinámicos
        List<Object> parametros = new ArrayList<>();

        if (texto != null && !texto.isEmpty()) {
            sql.append(" AND (p.nombre_producto LIKE ? OR p.descripcion LIKE ?)");
            parametros.add("%" + texto + "%");
            parametros.add("%" + texto + "%");
        }

        if (categoria != null && !categoria.isEmpty()) {
            sql.append(" AND c.nombre_categoria = ?");
            parametros.add(categoria);
        }

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Seteamos los parámetros dinámicamente
            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombreProducto(rs.getString("nombre_producto"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                p.setNombreCategoria(rs.getString("nombre_categoria"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
