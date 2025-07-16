/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.CConexion;
import Modelo.RegistroAcceso;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Melqui
 */
public class RegistroAccesoDAOImpl implements RegistroAccesoDAO {

    private final CConexion conector = new CConexion();

    @Override
    public List<RegistroAcceso> obtenerTodos() {
        List<RegistroAcceso> lista = new ArrayList<>();
        String sql = "SELECT * FROM registro_accesos";

        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroAcceso ra = new RegistroAcceso();
                ra.setIdRegistro(rs.getInt("id_registro"));
                ra.setIdUsuario(rs.getInt("id_usuario"));
                ra.setNombreUsuario(rs.getString("nombre_usuario"));
                ra.setFecha(rs.getString("fecha"));
                ra.setHora(rs.getString("hora"));
                ra.setModulo(rs.getString("modulo"));
                ra.setAccion(rs.getString("accion"));
                ra.setIp(rs.getString("ip"));
                lista.add(ra);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener registros de acceso: " + e.getMessage());
        }

        return lista;
    }
    
    @Override
    public void registrarAcceso(RegistroAcceso ra) {
        String sql = "INSERT INTO registro_accesos (id_usuario, nombre_usuario, fecha, hora, modulo, accion, ip) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conector.estableceConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ra.getIdUsuario());
            ps.setString(2, ra.getNombreUsuario());
            ps.setString(3, ra.getFecha());
            ps.setString(4, ra.getHora());
            ps.setString(5, ra.getModulo());
            ps.setString(6, ra.getAccion());
            ps.setString(7, ra.getIp());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al registrar acceso: " + e.getMessage());
        }
    }


    @Override
    public List<RegistroAcceso> buscarRegistros(String texto, String usuario, String modulo, String fecha) {
        List<RegistroAcceso> lista = new ArrayList<>();
        String sql = "SELECT * FROM registro_accesos WHERE 1=1";

        boolean tieneTexto = texto != null && !texto.isEmpty();
        boolean tieneUsuario = usuario != null && !usuario.equals("Todos los usuarios");
        boolean tieneModulo = modulo != null && !modulo.equals("Todos los modulos");
        boolean tieneFecha = fecha != null && !fecha.isEmpty();

        if (tieneTexto) {
            sql += " AND (nombre_usuario LIKE ? OR accion LIKE ?)";
        }
        if (tieneUsuario) {
            sql += " AND nombre_usuario = ?";
        }
        if (tieneModulo) {
            sql += " AND modulo = ?";
        }
        if (tieneFecha) {
            sql += " AND fecha = ?";
        }

        try (Connection con = conector.estableceConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;
            if (tieneTexto) {
                ps.setString(i++, "%" + texto + "%");
                ps.setString(i++, "%" + texto + "%");
            }
            if (tieneUsuario) {
                ps.setString(i++, usuario);
            }
            if (tieneModulo) {
                ps.setString(i++, modulo);
            }
            if (tieneFecha) {
                ps.setString(i++, fecha);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RegistroAcceso ra = new RegistroAcceso();
                ra.setIdRegistro(rs.getInt("id_registro"));
                ra.setIdUsuario(rs.getInt("id_usuario"));
                ra.setNombreUsuario(rs.getString("nombre_usuario"));
                ra.setFecha(rs.getString("fecha"));
                ra.setHora(rs.getString("hora"));
                ra.setModulo(rs.getString("modulo"));
                ra.setAccion(rs.getString("accion"));
                ra.setIp(rs.getString("ip"));
                lista.add(ra);
            }

        } catch (SQLException e) {
            System.err.println("Error al filtrar registros: " + e.getMessage());
        }

        return lista;
    }
}