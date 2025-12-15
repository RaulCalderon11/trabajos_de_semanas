/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mitesis.modelos;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author palac
 */
public class Sustentacion {
    private int idSustentacion;
    private int idTramite;
    
    // Códigos de los 3 Miembros del Jurado + Suplente
    private String codigoMiembro1; // Presidente
    private String codigoMiembro2; // Secretario
    private String codigoMiembro3; // Vocal
    private String codigoSuplente;

    private Timestamp fechaHora;
    private String lugarEnlace; // 'Auditorio 1', etc.
    private BigDecimal notaFinal; // Promedio de los 3 jurados
    private String resultadoDefensa; // 'Pendiente', 'Aprobado', 'Desaprobado'
    private String observaciones;

    public Sustentacion() {}

    // Getters y Setters
    public int getIdSustentacion() { return idSustentacion; }
    public void setIdSustentacion(int idSustentacion) { this.idSustentacion = idSustentacion; }

    public int getIdTramite() { return idTramite; }
    public void setIdTramite(int idTramite) { this.idTramite = idTramite; }

    public String getCodigoMiembro1() { return codigoMiembro1; }
    public void setCodigoMiembro1(String codigoMiembro1) { this.codigoMiembro1 = codigoMiembro1; }

    public String getCodigoMiembro2() { return codigoMiembro2; }
    public void setCodigoMiembro2(String codigoMiembro2) { this.codigoMiembro2 = codigoMiembro2; }

    public String getCodigoMiembro3() { return codigoMiembro3; }
    public void setCodigoMiembro3(String codigoMiembro3) { this.codigoMiembro3 = codigoMiembro3; }

    public String getCodigoSuplente() { return codigoSuplente; }
    public void setCodigoSuplente(String codigoSuplente) { this.codigoSuplente = codigoSuplente; }

    public Timestamp getFechaHora() { return fechaHora; }
    public void setFechaHora(Timestamp fechaHora) { this.fechaHora = fechaHora; }

    public String getLugarEnlace() { return lugarEnlace; }
    public void setLugarEnlace(String lugarEnlace) { this.lugarEnlace = lugarEnlace; }

    public BigDecimal getNotaFinal() { return notaFinal; }
    public void setNotaFinal(BigDecimal notaFinal) { this.notaFinal = notaFinal; }

    public String getResultadoDefensa() { return resultadoDefensa; }
    public void setResultadoDefensa(String resultadoDefensa) { this.resultadoDefensa = resultadoDefensa; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
