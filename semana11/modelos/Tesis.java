package com.mitesis.modelos;

import java.io.Serializable;
import java.util.Date;

public class Tesis implements Serializable {
    
    private int idTesis;
    private String titulo;
    private String descripcion;
    private Date fechaSubida;
    private String estado;
    private String urlArchivo;
    private Date fechaProximaRevision;
    
    private int idEstudianteAutor;
    private int idDocenteRevisor;
    
    private String nombreAutor;
    private String nombreRevisor;
    
    // CAMBIO: Campo auxiliar para cuando el docente es Jurado
    private int idSustentacion; 

    public Tesis() {
    }

    public int getIdTesis() { return idTesis; }
    public void setIdTesis(int idTesis) { this.idTesis = idTesis; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(Date fechaSubida) { this.fechaSubida = fechaSubida; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getUrlArchivo() { return urlArchivo; }
    public void setUrlArchivo(String urlArchivo) { this.urlArchivo = urlArchivo; }

    public Date getFechaProximaRevision() { return fechaProximaRevision; }
    public void setFechaProximaRevision(Date fechaProximaRevision) { this.fechaProximaRevision = fechaProximaRevision; }

    public int getIdEstudianteAutor() { return idEstudianteAutor; }
    public void setIdEstudianteAutor(int idEstudianteAutor) { this.idEstudianteAutor = idEstudianteAutor; }

    public int getIdDocenteRevisor() { return idDocenteRevisor; }
    public void setIdDocenteRevisor(int idDocenteRevisor) { this.idDocenteRevisor = idDocenteRevisor; }

    public String getNombreAutor() { return nombreAutor; }
    public void setNombreAutor(String nombreAutor) { this.nombreAutor = nombreAutor; }

    public String getNombreRevisor() { return nombreRevisor; }
    public void setNombreRevisor(String nombreRevisor) { this.nombreRevisor = nombreRevisor; }

    public int getIdSustentacion() { return idSustentacion; }
    public void setIdSustentacion(int idSustentacion) { this.idSustentacion = idSustentacion; }
}