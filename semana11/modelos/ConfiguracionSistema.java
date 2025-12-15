package com.mitesis.modelos;

import java.io.Serializable;

/**
 * Modelo (JavaBean) que representa la configuración global del sistema.
 * Resuelve la función "Configuración de Admin".
 * Esta tabla en la BD probablemente tendrá una sola fila.
 */
public class ConfiguracionSistema implements Serializable {
    
    // Usamos un ID simple, aunque la tabla solo tendrá 1 fila (ej. id=1)
    private int idConfig; 
    
    private String nombreInstitucion;
    private String logoUrl;
    private String periodoAcademico;

    // Constructor vacío
    public ConfiguracionSistema() {
    }

    // Constructor completo
    public ConfiguracionSistema(int idConfig, String nombreInstitucion, String logoUrl, String periodoAcademico) {
        this.idConfig = idConfig;
        this.nombreInstitucion = nombreInstitucion;
        this.logoUrl = logoUrl;
        this.periodoAcademico = periodoAcademico;
    }

    // --- Getters y Setters ---
    
    public int getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(int idConfig) {
        this.idConfig = idConfig;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPeriodoAcademico() {
        return periodoAcademico;
    }

    public void setPeriodoAcademico(String periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }
}