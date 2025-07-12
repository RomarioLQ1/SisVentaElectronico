/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author david
 */
public class Usuario {
    private int idUsuario;
    private String nombreUsuario;  // Nombre completo del usuario
    private String usuario;        // Nombre de usuario (login)
    private String contrasena;
    private String rol;

    // Constructor completo
    public Usuario(int idUsuario, String nombreUsuario, String usuario, String contrasena, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // Constructor vacío
    public Usuario() {
    }

    // Getters y setters

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
