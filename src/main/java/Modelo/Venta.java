/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDateTime;

public class Venta {

    private int id;
    private int clienteId;
    private LocalDateTime fecha;
    private double total;

    // Constructor vacío (requerido por frameworks o JavaBeans)
    public Venta() {}

    // Constructor completo (usado por DAO)
    public Venta(int id, int clienteId, LocalDateTime fecha, double total) {
        this.id = id;
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.total = total;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
