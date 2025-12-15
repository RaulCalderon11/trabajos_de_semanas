package com.mitesis.modelos;

import java.io.Serializable;
import java.util.Date;

/**
 * Modelo (JavaBean) que representa un Mensaje (chat/email).
 * Resuelve la función "sección de mensajes".
 */
public class Mensaje implements Serializable {
    
    private int idMensaje;
    private String asunto;
    private String cuerpo; // El texto del mensaje
    private Date fechaHoraEnvio;
    private boolean leido;
    
    // --- Relaciones (Llaves Foráneas) ---
    private int idRemitente; // ID del Usuario que envía (Estudiante)
    private int idDestinatario; // ID del Usuario que recibe (Docente)

    // Constructor vacío
    public Mensaje() {
    }

    // Constructor completo
    public Mensaje(int idMensaje, String asunto, String cuerpo, Date fechaHoraEnvio, boolean leido, int idRemitente, int idDestinatario) {
        this.idMensaje = idMensaje;
        this.asunto = asunto;
        this.cuerpo = cuerpo;
        this.fechaHoraEnvio = fechaHoraEnvio;
        this.leido = leido;
        this.idRemitente = idRemitente;
        this.idDestinatario = idDestinatario;
    }
    
    // --- Getters y Setters ---

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Date getFechaHoraEnvio() {
        return fechaHoraEnvio;
    }

    public void setFechaHoraEnvio(Date fechaHoraEnvio) {
        this.fechaHoraEnvio = fechaHoraEnvio;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public int getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(int idRemitente) {
        this.idRemitente = idRemitente;
    }

    public int getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(int idDestinatario) {
        this.idDestinatario = idDestinatario;
    }
}