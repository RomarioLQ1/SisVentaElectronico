/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.CConexion;
import Modelo.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    CConexion conector = new CConexion();

    @Override
    public List<Categoria> obtenerTodas() {
        List<Categoria> lista = new ArrayList<>();

        String sql = "SELECT id_categoria, nombre_categoria FROM categorias";

        try (Connection con = conector.estableceConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_categoria")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener categorías: " + e.getMessage());
        }

        return lista;
    }
}
