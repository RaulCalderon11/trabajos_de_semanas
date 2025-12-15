package com.mitesis.dao;

import com.mitesis.modelos.ConfiguracionSistema;
import com.mitesis.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO para leer y actualizar la tabla 'configuracion_sistema'.
 * Esta tabla solo tiene una fila (idConfig = 1).
 */
public class ConfiguracionSistemaDAO {

    /**
     * Obtiene la configuración actual del sistema.
     * @return El objeto ConfiguracionSistema.
     */
    public ConfiguracionSistema obtenerConfiguracion() {
        ConfiguracionSistema config = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM configuracion_sistema WHERE idConfig = 1";

        try {
            conn = ConexionDB.getInstancia().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                config = new ConfiguracionSistema();
                config.setIdConfig(rs.getInt("idConfig"));
                config.setNombreInstitucion(rs.getString("nombreInstitucion"));
                config.setLogoUrl(rs.getString("logoUrl"));
                config.setPeriodoAcademico(rs.getString("periodoAcademico"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener configuración del sistema: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(conn, ps, rs);
        }
        return config;
    }

    /**
     * Actualiza la configuración del sistema (solo Admin).
     * @param config El objeto con los nuevos datos.
     * @return true si fue exitoso, false si no.
     */
    public boolean actualizarConfiguracion(ConfiguracionSistema config) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;
        
        String sql = "UPDATE configuracion_sistema SET nombreInstitucion = ?, logoUrl = ?, periodoAcademico = ? WHERE idConfig = 1";
        
        try {
            conn = ConexionDB.getInstancia().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, config.getNombreInstitucion());
            ps.setString(2, config.getLogoUrl());
            ps.setString(3, config.getPeriodoAcademico());
            
            exito = ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar configuración: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(conn, ps, null);
        }
        return exito;
    }
    
    /**
     * Cierra la conexión, PreparedStatement y ResultSet de forma segura.
     */
    private void cerrarRecursos(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close(); // Devuelve la conexión al pool
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos JDBC: " + e.getMessage());
            e.printStackTrace();
        }
    }
}