package com.mitesis.modelos;

/**
 * Modelo (JavaBean) que representa a un Docente (Revisor).
 * Hereda (extiende) de la clase Usuario.
 */
public class Docente extends Usuario {
    
    // Atributos propios de Docente
    private String departamento;
    private String especialidad; // Ej. "Inteligencia Artificial"

    // Constructor vacío
    public Docente() {
        super();
    }

    // Constructor completo
    public Docente(int idUsuario, String codigo, String nombreUsuario, String contrasena, String nombre, String apellido, String email, String departamento, String especialidad) {
        // Llamada al constructor de Usuario (id, codigo, user, pass, nom, ape, email, rol)
        super(idUsuario, codigo, nombreUsuario, contrasena, nombre, apellido, email, "docente");
        this.departamento = departamento;
        this.especialidad = especialidad;
    }

    // --- Getters y Setters ---
    
    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    @Override
    public String toString() {
        return "Docente{" + "nombre=" + nombre + " " + apellido + ", especialidad=" + especialidad + '}';
    }
}