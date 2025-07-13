


package Controlador;

import DAO.ProductoDAO;
import DAO.ProductoDAOImpl;
import Modelo.Producto;
import java.util.List;

public class ProductoControlador {

    private final ProductoDAO productoDAO;

    public ProductoControlador() {
        this.productoDAO = new ProductoDAOImpl();
    }

    public String generarCodigoProducto() {
        return productoDAO.obtenerUltimoCodigoProducto();
    }

    // Buscar productos por filtro y categoría
    public List<Producto> buscarProductos(String filtro, String categoria) {
        return productoDAO.buscarProductos(filtro, categoria);
    }

    // Obtener lista de categorías
    public List<String> obtenerCategorias() {
        return productoDAO.obtenerCategorias();
    }

    // Registrar nuevo producto
    public boolean registrarProducto(Producto producto) {
        return productoDAO.registrarProducto(producto);
    }

}
