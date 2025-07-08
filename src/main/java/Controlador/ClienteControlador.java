/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.ClienteDAOImpl;
import Modelo.Cliente;
import java.util.List;

//El controlador va manejar el tema de las solicitudes y va a llamar a los dao
public class ClienteControlador {

    private ClienteDAOImpl clienteDAO = new ClienteDAOImpl();

    public List<Cliente> obtenerClientesFiltrados(String textoBusqueda) {
        List<Cliente> clientes = clienteDAO.obtenerTodos();

        return clientes.stream().filter(c -> {
            return textoBusqueda.isEmpty()
                    || c.getNombre().toLowerCase().contains(textoBusqueda.toLowerCase())
                    || c.getDni().toLowerCase().contains(textoBusqueda.toLowerCase())
                    || c.getTelefono().toLowerCase().contains(textoBusqueda.toLowerCase());
        }).toList();
    }

    public boolean registrarCliente(Cliente cliente) {
        if (cliente.getNombre().isBlank()
                || cliente.getDni().isBlank()
                || cliente.getTelefono().isBlank()) {
            return false;
        }
        //metodo insertarcliente 
        return clienteDAO.insertar(cliente);
    }
    //metodo eliminar

    public boolean eliminarClientePorId(int idCliente) {
        return clienteDAO.eliminar(idCliente);
    }

    public boolean actualizarCliente(Cliente cliente) {
        if (cliente.getId() <= 0
                || cliente.getNombre().isBlank()
                || cliente.getDni().isBlank()
                || cliente.getTelefono().isBlank()) {
            return false;
        }

        return clienteDAO.actualizar(cliente);
    }
}
