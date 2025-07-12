/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Timestamp;

/**
 *
 * @author av_ga
 */
public class Igv {
    
    private int id;
    private double porcentaje;
    private Timestamp fechaCambio;
    private String usuarioModifico;
    private String estado;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getPorcentaje() { return porcentaje; }
    public void setPorcentaje(double porcentaje) { this.porcentaje = porcentaje; }

    public Timestamp getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(Timestamp fechaCambio) { this.fechaCambio = fechaCambio; }

    public String getUsuarioModifico() { return usuarioModifico; }
    public void setUsuarioModifico(String usuarioModifico) { this.usuarioModifico = usuarioModifico; }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}
