package com.mitesis.dao;

import com.mitesis.modelos.Tramite;
import com.mitesis.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TramiteDAO {

    public Tramite obtenerPorEstudiante(String codigoEstudiante) {
        Tramite t = null;
        String sql = "SELECT * FROM tramites WHERE codigo_estudiante = ?";
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigoEstudiante);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                t = new Tramite();
                t.setIdTramite(rs.getInt("id_tramite"));
                t.setCodigoEstudiante(rs.getString("codigo_estudiante"));
                t.setEstadoActual(rs.getString("estado_actual"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return t;
    }

    public boolean iniciarTramite(String codigoEstudiante) {
        String sql = "INSERT INTO tramites (codigo_estudiante, estado_actual) VALUES (?, 'Iniciado')";
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigoEstudiante);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /**
     * Actualiza el estado actual de un trámite.
     * @param idTramite El ID del trámite a actualizar.
     * @param nuevoEstado El nuevo estado (ej. "Sustentación Programada").
     * @return true si se actualizó correctamente.
     */
    public boolean actualizarEstado(int idTramite, String nuevoEstado) {
        String sql = "UPDATE tramites SET estado_actual = ?, fecha_actualizacion = NOW() WHERE id_tramite = ?";
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idTramite);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}