package com.mitesis.modelos;

import java.io.Serializable;
import java.util.Date;

/**
 * Modelo (JavaBean) que representa un Evento del Calendario general.
 * Resuelve la función "sección de calendario".
 */
public class EventoCalendario implements Serializable {
    
    private int idEvento;
    private String titulo;
    private String descripcion;
    private Date fechaEvento;
    private String tipo; // "General", "Sustentacion", "Entrega Límite"

    // Constructor vacío
    public EventoCalendario() {
    }

    // Constructor completo
    public EventoCalendario(int idEvento, String titulo, String descripcion, Date fechaEvento, String tipo) {
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaEvento = fechaEvento;
        this.tipo = tipo;
    }
    
    // --- Getters y Setters ---

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}