/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author david
 */
public class Cliente {

    private int idCliente;
    private String nombre;
    private String dni;
    private String telefono;
    private String direccion;

    // Constructor vacío
    public Cliente() {}

    // Constructor completo
    public Cliente(int idCliente, String nombre, String dni, String telefono, String direccion) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Constructor adicional para usar desde DAO (sin dirección)
    public Cliente(int idCliente, String dni, String nombre, String telefono) {
        this.idCliente = idCliente;
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Alias para compatibilidad con DAO
    public int getId() {
        return idCliente;
    }

    // Getters y Setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
