/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mitesis.dao;

import com.mitesis.modelos.EvaluacionJurado;
import com.mitesis.util.ConexionDB;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author palac
 */
public class EvaluacionJuradoDAO {
    
    public boolean insertar(EvaluacionJurado eval) {
        StringBuilder sql = new StringBuilder("INSERT INTO evaluaciones_jurado (id_sustentacion, codigo_jurado, puntaje_total, condicion, observaciones, fecha_evaluacion");
        for(int i=1; i<=38; i++) sql.append(", item_").append(i);
        sql.append(") VALUES (?, ?, ?, ?, ?, NOW()");
        for(int i=1; i<=38; i++) sql.append(", ?");
        sql.append(")");

        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            ps.setInt(1, eval.getIdSustentacion());
            ps.setString(2, eval.getCodigoJurado());
            ps.setBigDecimal(3, eval.getPuntajeTotal());
            ps.setString(4, eval.getCondicion());
            ps.setString(5, eval.getObservaciones());

            int paramIndex = 6;
            for (int i = 1; i <= 38; i++) {
                BigDecimal valor = eval.getItem(i);
                
                // Validación estricta
                if (!esValorValido(valor)) {
                    System.err.println("Error EvaluacionJuradoDAO: Valor inválido en ítem " + i + ": " + valor);
                    return false; 
                }
                
                ps.setBigDecimal(paramIndex++, valor);
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean esValorValido(BigDecimal valor) {
        if (valor == null) return false;
        double v = valor.doubleValue();
        return v == 1.0 || v == 0.5 || v == 0.0;
    }

    public List<EvaluacionJurado> listarPorSustentacion(int idSustentacion) {
        List<EvaluacionJurado> lista = new ArrayList<>();
        String sql = "SELECT * FROM evaluaciones_jurado WHERE id_sustentacion = ?";
        
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSustentacion);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                EvaluacionJurado ev = new EvaluacionJurado();
                ev.setIdEvaluacionJurado(rs.getInt("id_evaluacion_jurado"));
                ev.setCodigoJurado(rs.getString("codigo_jurado"));
                ev.setPuntajeTotal(rs.getBigDecimal("puntaje_total"));
                ev.setCondicion(rs.getString("condicion"));
                ev.setObservaciones(rs.getString("observaciones"));
                ev.setFechaEvaluacion(rs.getTimestamp("fecha_evaluacion"));
                
                for (int i = 1; i <= 38; i++) {
                    ev.setItem(i, rs.getBigDecimal("item_" + i));
                }
                
                lista.add(ev);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}
