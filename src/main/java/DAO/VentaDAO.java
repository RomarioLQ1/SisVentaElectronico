/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Venta;
import java.util.List;

public interface VentaDAO {
    boolean insertar(Venta venta);
    Venta buscarPorId(int id);
    List<Venta> obtenerTodas();
}
