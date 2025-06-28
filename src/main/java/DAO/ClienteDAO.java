/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Cliente;
import java.util.List;

public interface ClienteDAO {
    boolean insertar(Cliente cliente);
    Cliente buscarPorId(int id);
    List<Cliente> obtenerTodos();
    boolean actualizar(Cliente cliente);
    boolean eliminar(int id);
}
