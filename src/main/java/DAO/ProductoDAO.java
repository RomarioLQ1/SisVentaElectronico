/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Producto;
import java.util.List;

public interface ProductoDAO {
    boolean insertar(Producto p);
    Producto buscarPorId(int id);
    List<Producto> obtenerTodos();
    boolean actualizar(Producto p);
    boolean eliminar(int id);
}
