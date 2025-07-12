/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Usuario;
import Conexion.CConexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public boolean insertar(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre_usuario, usuario, contrasena, rol) VALUES (?, ?, ?, ?)";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getUsuario());
            ps.setString(3, "123456789");
            ps.setString(4, u.getRol());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre_usuario"),
                    rs.getString("usuario"),
                    rs.getString("contrasena"),
                    rs.getString("rol")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nombre_usuario=?, usuario=? WHERE id_usuario=?";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getUsuario());
            ps.setInt(3, u.getIdUsuario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";
        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}
