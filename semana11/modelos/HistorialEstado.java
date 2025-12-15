package com.mitesis.modelos;

import java.io.Serializable;
import java.util.Date;

/**
 * Modelo (JavaBean) que representa el log de cambios de estado.
 * Resuelve la función "sección de estado de tesis (timeline)".
 */
public class HistorialEstado implements Serializable {

    private int idHistorial;
    private Date fechaCambio;
    private String estadoNuevo; // "Registrada", "En Revisión", "Aprobado", etc.
    private String notas; // Un breve comentario sobre el cambio
    
    // --- Relaciones (Llaves Foráneas) ---
    private int idTesis; // A qué tesis pertenece este historial
    private int idUsuarioModificador; // Qué usuario (Admin/Docente) hizo el cambio

    // Constructor vacío
    public HistorialEstado() {
    }

    // Constructor completo
    public HistorialEstado(int idHistorial, Date fechaCambio, String estadoNuevo, String notas, int idTesis, int idUsuarioModificador) {
        this.idHistorial = idHistorial;
        this.fechaCambio = fechaCambio;
        this.estadoNuevo = estadoNuevo;
        this.notas = notas;
        this.idTesis = idTesis;
        this.idUsuarioModificador = idUsuarioModificador;
    }
    
    // --- Getters y Setters ---

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getIdTesis() {
        return idTesis;
    }

    public void setIdTesis(int idTesis) {
        this.idTesis = idTesis;
    }

    public int getIdUsuarioModificador() {
        return idUsuarioModificador;
    }

    public void setIdUsuarioModificador(int idUsuarioModificador) {
        this.idUsuarioModificador = idUsuarioModificador;
    }
}