/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Venta;
import java.sql.*;
import Conexion.CConexion;

public class VentaDAOImpl implements VentaDAO {

    private CConexion conector = new CConexion();

    @Override
    public int registrarVenta(Venta venta) {
        int idGenerado = -1;
        String sql = "INSERT INTO ventas (nombre_cliente, subtotal, igv, total) VALUES (?, ?, ?, ?)";

        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, venta.getNombreCliente());
            ps.setDouble(2, venta.getSubtotal());
            ps.setDouble(3, venta.getIgv());
            ps.setDouble(4, venta.getTotal());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGenerado;
    }
}
