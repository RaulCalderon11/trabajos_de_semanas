package com.mitesis.modelos;

/**
 * Modelo (JavaBean) que representa a un Estudiante.
 * Hereda (extiende) de la clase Usuario.
 */
public class Estudiante extends Usuario {
    
    // Atributos propios de Estudiante
    private String codigoEstudiante;
    private String carrera;

    // Constructor vacío
    public Estudiante() {
        super(); // Llama al constructor vacío de Usuario
    }

    // Constructor completo
    public Estudiante(int idUsuario, String codigo, String nombreUsuario, String contrasena, String nombre, String apellido, String email, String codigoEstudiante, String carrera) {
        // Llama al constructor de Usuario para establecer los campos heredados
        // Se pasa 'codigo' (PK de negocio) y se fija el rol como "estudiante"
        super(idUsuario, codigo, nombreUsuario, contrasena, nombre, apellido, email, "estudiante");
        this.codigoEstudiante = codigoEstudiante;
        this.carrera = carrera;
    }

    // --- Getters y Setters ---
    
    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        // Incluimos el nombre y apellido del padre (Usuario)
        return "Estudiante{" + "codigo=" + codigoEstudiante + ", nombre=" + nombre + " " + apellido + ", carrera=" + carrera + '}';
    }
}