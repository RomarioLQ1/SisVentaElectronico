/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import DAO.ClienteDAO;
import DAO.ClienteDAOImpl;
import Modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClientesForm extends JFrame {

    private JTable tblClientes;
    private JButton btnCargar;
    private DefaultTableModel modelo;

    public ClientesForm() {
        setTitle("Lista de Clientes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear tabla y botón
        modelo = new DefaultTableModel(new Object[]{"ID", "DNI", "Nombre", "Teléfono", "Dirección"}, 0);
        tblClientes = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tblClientes);

        btnCargar = new JButton("Cargar Clientes");
        btnCargar.addActionListener(e -> cargarClientes());

        add(scroll, BorderLayout.CENTER);
        add(btnCargar, BorderLayout.SOUTH);
    }

    private void cargarClientes() {
        ClienteDAO dao = new ClienteDAOImpl();
        List<Cliente> lista = dao.obtenerTodos();

        modelo.setRowCount(0); // Limpiar la tabla

        for (Cliente c : lista) {
            modelo.addRow(new Object[]{
                c.getIdCliente(),
                c.getDni(),
                c.getNombre(),
                c.getTelefono(),
                c.getDireccion()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientesForm().setVisible(true));
    }
}
