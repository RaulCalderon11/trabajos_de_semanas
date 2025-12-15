package com.mitesis.modelos;

import java.io.Serializable;
import java.util.Date;

/**
 * Modelo (JavaBean) que representa un Comentario del Docente.
 * Resuelve la función "sección de comentarios".
 */
public class Comentario implements Serializable {
    
    private int idComentario;
    private String texto;
    private Date fechaHora;
    
    // --- Relaciones (Llaves Foráneas) ---
    private int idTesis; // A qué tesis pertenece este comentario
    private int idDocenteAutor; // Quién hizo el comentario
    
    // Constructor vacío
    public Comentario() {
    }

    // Constructor completo
    public Comentario(int idComentario, String texto, Date fechaHora, int idTesis, int idDocenteAutor) {
        this.idComentario = idComentario;
        this.texto = texto;
        this.fechaHora = fechaHora;
        this.idTesis = idTesis;
        this.idDocenteAutor = idDocenteAutor;
    }

    // --- Getters y Setters ---
    
    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getIdTesis() {
        return idTesis;
    }

    public void setIdTesis(int idTesis) {
        this.idTesis = idTesis;
    }

    public int getIdDocenteAutor() {
        return idDocenteAutor;
    }

    public void setIdDocenteAutor(int idDocenteAutor) {
        this.idDocenteAutor = idDocenteAutor;
    }
}