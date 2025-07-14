

package DAO;

import Conexion.CConexion;
import Modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    CConexion conector = new CConexion();
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

    // ===================== ACTUALIZAR STOCK =====================
    @Override
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        String sql = "UPDATE productos SET stock = ? WHERE id_producto = ?";
        try (Connection con = conector.estableceConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoStock);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }

    // ===================== BUSCAR PRODUCTOS =====================
    @Override
    public List<Producto> buscarProductos(String texto, String categoria) {
        List<Producto> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT p.id_producto, p.codigo, p.nombre_producto, p.descripcion, p.precio, p.stock, c.nombre_categoria "
                + "FROM productos p INNER JOIN categorias c ON p.id_categoria = c.id_categoria WHERE 1=1"
        );

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
            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setCodigo(rs.getString("codigo")); // <-- Este es el PASO 2
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

    // ===================== OBTENER ÚLTIMO CÓDIGO =====================
    @Override
    public String obtenerUltimoCodigoProducto() {
        String sql = "SELECT MAX(id_producto) AS max_id FROM productos";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int ultimoID = rs.getInt("max_id");
                return String.format("P%03d", ultimoID + 1);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el último código de producto: " + e.getMessage());
        }
        return "P001"; // Valor inicial si no hay productos
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        String sql = "SELECT p.id_producto, p.codigo, p.nombre_producto, p.descripcion, p.precio, p.stock, c.nombre_categoria "
                + "FROM productos p INNER JOIN categorias c ON p.id_categoria = c.id_categoria WHERE p.codigo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setCodigo(rs.getString("codigo"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setNombreCategoria(rs.getString("nombre_categoria"));
                return producto;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar producto por código: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean actualizarProductoPorCodigo(Producto producto) {
        String sql = "UPDATE productos SET nombre_producto = ?, descripcion = ?, id_categoria = (SELECT id_categoria FROM categorias WHERE nombre_categoria = ?), precio = ?, stock = ? WHERE codigo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombreProducto());
            ps.setString(2, producto.getDescripcion());
            ps.setString(3, producto.getNombreCategoria());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());
            ps.setString(6, producto.getCodigo());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar producto por código: " + e.getMessage());
            return false;
        }
    }


    // ===================== REGISTRAR NUEVO PRODUCTO =====================
    @Override
    public boolean registrarProducto(Producto producto) {
        String sql = "INSERT INTO productos (codigo, nombre_producto, descripcion, id_categoria, precio, stock) "
                   + "VALUES (?, ?, ?, (SELECT id_categoria FROM categorias WHERE nombre_categoria = ?), ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombreProducto());
            ps.setString(3, producto.getDescripcion());
            ps.setString(4, producto.getNombreCategoria());
            ps.setDouble(5, producto.getPrecio());
            ps.setInt(6, producto.getStock());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar producto: " + e.getMessage());
            return false;
        }
    }
}
