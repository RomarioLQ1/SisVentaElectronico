/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Usuario;
import Conexion.CConexion;
import java.sql.*;

public class UsuarioDAOImpl implements UsuarioDAO {

    CConexion conector = new CConexion();

    @Override
    public Usuario verificarUsuario(String usuario, String contrasena) {
        Usuario user = null;
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";

        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new Usuario();
                user.setIdUsuario(rs.getInt("id_usuario"));
                user.setNombreUsuario(rs.getString("nombre_usuario"));
                user.setUsuario(rs.getString("usuario"));
                user.setContrasena(rs.getString("contrasena"));
                user.setRol(rs.getString("rol"));
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar usuario: " + e.getMessage());
        }

        return user;
    }
}
