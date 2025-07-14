
package DAO;

import Modelo.Producto;
import java.util.List;

public interface ProductoDAO {
    boolean actualizarStock(int idProducto, int nuevoStock);
    List<String> obtenerCategorias();
    List<Producto> buscarProductos(String texto, String categoria);
    boolean registrarProducto(Producto producto);
    String obtenerUltimoCodigoProducto();

    boolean actualizarProductoPorCodigo(Producto producto);
    Producto buscarPorCodigo(String codigo);
}

