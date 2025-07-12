/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.CConexion;
import Modelo.Igv;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author av_ga
 */
public class IgvDAOlmpl implements IgvDAO{

    @Override
    public boolean desactivarIGVActual() {
        String sql = "UPDATE igv_configuracion SET estado = 'inactivo' WHERE estado = 'activo'";
        try (Connection con = CConexion.getConexion(); 
            PreparedStatement ps = con.prepareStatement(sql)) {
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al desactivar IGV actual: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean insertarNuevoIGV(double porcentaje, int idUsuario) {
        String sql = "INSERT INTO igv_configuracion (porcentaje, id_usuario, estado) VALUES (?, ?, 'activo')";
        try (Connection con = CConexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, porcentaje);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar nuevo IGV: " + e.getMessage());
            return false;
        }
    }

    @Override
    public double getIGVActual() {
        String sql = "SELECT porcentaje FROM igv_configuracion WHERE estado = 'activo' LIMIT 1";
        try (Connection con = CConexion.getConexion(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("porcentaje");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener IGV actual: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public List<Igv> getHistorialIGV() {
        List<Igv> lista = new ArrayList<>();
        String sql = "SELECT igv.id, igv.porcentaje, igv.fecha_cambio, u.nombre_usuario AS usuario_modifico, igv.estado " +
             "FROM igv_configuracion igv " +
             "JOIN usuarios u ON igv.id_usuario = u.id_usuario " +
             "ORDER BY igv.fecha_cambio DESC";
        
        try (Connection con = CConexion.getConexion(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Igv igv = new Igv();
                igv.setId(rs.getInt("id"));
                igv.setPorcentaje(rs.getDouble("porcentaje"));
                igv.setFechaCambio(rs.getTimestamp("fecha_cambio"));
                igv.setUsuarioModifico(rs.getString("usuario_modifico"));
                igv.setEstado(rs.getString("estado"));
                lista.add(igv);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener historial de IGV: " + e.getMessage());
        }
        return lista;
    }
}
