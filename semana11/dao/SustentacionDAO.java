/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mitesis.dao;

import com.mitesis.modelos.Sustentacion;
import com.mitesis.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author palac
 */
public class SustentacionDAO {
    public boolean programarSustentacion(Sustentacion sust) {
        String sql = "INSERT INTO sustentaciones (id_tramite, codigo_miembro1, codigo_miembro2, codigo_miembro3, codigo_suplente, fecha_hora, lugar_enlace) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, sust.getIdTramite());
            ps.setString(2, sust.getCodigoMiembro1());
            ps.setString(3, sust.getCodigoMiembro2());
            ps.setString(4, sust.getCodigoMiembro3());
            ps.setString(5, sust.getCodigoSuplente());
            ps.setTimestamp(6, sust.getFechaHora());
            ps.setString(7, sust.getLugarEnlace());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Método para obtener la sustentación asociada a un trámite
    public Sustentacion obtenerPorTramite(int idTramite) {
        Sustentacion s = null;
        String sql = "SELECT * FROM sustentaciones WHERE id_tramite = ?";
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTramite);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s = new Sustentacion();
                s.setIdSustentacion(rs.getInt("id_sustentacion"));
                s.setIdTramite(rs.getInt("id_tramite"));
                s.setCodigoMiembro1(rs.getString("codigo_miembro1"));
                s.setCodigoMiembro2(rs.getString("codigo_miembro2"));
                s.setCodigoMiembro3(rs.getString("codigo_miembro3"));
                s.setCodigoSuplente(rs.getString("codigo_suplente"));
                s.setFechaHora(rs.getTimestamp("fecha_hora"));
                s.setLugarEnlace(rs.getString("lugar_enlace"));
                s.setNotaFinal(rs.getBigDecimal("nota_final"));
                s.setResultadoDefensa(rs.getString("resultado_defensa"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return s;
    }
}
