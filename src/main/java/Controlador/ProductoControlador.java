


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

    // Llama al método DAO para buscar productos
    public List<Producto> buscarProductos(String filtro, String categoria) {
        return productoDAO.buscarProductos(filtro, categoria);
    }

    // Llama al método DAO para obtener categorías
    public List<String> obtenerCategorias() {
        return productoDAO.obtenerCategorias();
    }
}
