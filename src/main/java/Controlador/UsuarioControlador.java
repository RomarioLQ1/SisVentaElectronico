/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.UsuarioDAOImpl;
import Modelo.Usuario;
import java.util.List;

/**
 *
 * @author av_ga
 */
public class UsuarioControlador {
    
    private UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();

    public List<Usuario> obtenerUsuariosFiltrados() {
        List<Usuario> usuarios = usuarioDAO.obtenerTodos();

        return usuarios;
    }

    public boolean registrarUsuario(Usuario usuario) {
        if (usuario.getNombreUsuario().isBlank()
                || usuario.getUsuario().isBlank()
                || usuario.getRol().isBlank()) {
            return false;
        }
        //metodo insertarcliente 
        return usuarioDAO.insertar(usuario);
    }
    
    //metodo eliminar
    public boolean eliminarUsuarioPorId(int idUsuario) {
        return usuarioDAO.eliminar(idUsuario);
    }

    public boolean actualizarUsuario(Usuario usuario) {
        if (usuario.getIdUsuario() <= 0
                || usuario.getNombreUsuario().isBlank()
                || usuario.getUsuario().isBlank()
                || usuario.getRol().isBlank()) {
            return false;
        }

        return usuarioDAO.actualizar(usuario);
    }
}
