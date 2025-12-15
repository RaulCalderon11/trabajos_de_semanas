package com.mitesis.modelos;

import java.io.Serializable;

/**
 * Modelo (JavaBean) que representa la entidad Usuario.
 * Esta es una clase base para Estudiante, Docente y Admin.
 */
public class Usuario implements Serializable {
    
    // Atributos (coincidirán con las columnas de la tabla 'usuarios')
    protected int idUsuario;        // ID autoincremental (PK técnica)
    protected String codigo;        // CÓDIGO ÚNICO (PK de negocio: ADM001, ALU001, etc.)
    protected String nombreUsuario; // El 'username' para login
    protected String contrasena;    // IMPORTANTE: En la BD debe guardarse hasheada (SHA-256)
    protected String nombre;
    protected String apellido;
    protected String email;
    protected String rol;           // ("estudiante", "docente", "admin")
    protected String fotoPerfilUrl; // Para guardar la ruta de la imagen de perfil
    protected boolean activo;       // Para la Gestión de Usuarios del Admin

    // Constructor vacío (requerido por JavaBean)
    public Usuario() {
    }

    // Constructor completo actualizado
    public Usuario(int idUsuario, String codigo, String nombreUsuario, String contrasena, String nombre, String apellido, String email, String rol) {
        this.idUsuario = idUsuario;
        this.codigo = codigo;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
        this.fotoPerfilUrl = "default.png"; // Valor por defecto
        this.activo = true; // Por defecto, un usuario nuevo está activo
    }
    
    // --- Getters y Setters ---

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", codigo=" + codigo + ", nombreUsuario=" + nombreUsuario + ", nombre=" + nombre + ", rol=" + rol + '}';
    }
}