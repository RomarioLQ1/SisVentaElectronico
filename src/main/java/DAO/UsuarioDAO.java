/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Usuario;
import java.util.List;

public interface UsuarioDAO {
    Usuario verificarUsuario(String usuario, String contrasena);
    boolean insertar(Usuario usuario);
    List<Usuario> obtenerTodos();
    boolean actualizar(Usuario cliente);
    boolean eliminar(int id);
}
