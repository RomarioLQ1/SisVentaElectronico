

package DAO;

import Modelo.Producto;
import java.util.List;

public interface ProductoDAO {
    List<String> obtenerCategorias();
    List<Producto> buscarProductos(String texto, String categoria);
}
