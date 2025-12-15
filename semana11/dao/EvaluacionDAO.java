/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mitesis.dao;

import com.mitesis.modelos.Evaluacion;
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
public class EvaluacionDAO {
    
    /**
     * Inserta una nueva evaluación de Asesor (Anexo 4).
     */
    public boolean insertarEvaluacion(Evaluacion eval) {
        StringBuilder sql = new StringBuilder("INSERT INTO evaluaciones (id_tesis, codigo_docente, comentarios, puntaje_total, condicion, fecha_evaluacion");
        for(int i=1; i<=38; i++) sql.append(", item_").append(i);
        sql.append(") VALUES (?, ?, ?, ?, ?, NOW()");
        for(int i=1; i<=38; i++) sql.append(", ?");
        sql.append(")");

        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            ps.setInt(1, eval.getIdTesis());
            ps.setString(2, eval.getCodigoDocente());
            ps.setString(3, eval.getComentarios());
            ps.setBigDecimal(4, eval.getPuntajeTotal());
            ps.setString(5, eval.getCondicion());

            int paramIndex = 6;
            for (int i = 1; i <= 38; i++) {
                BigDecimal valor = eval.getItem(i);
                
                if (!esValorValido(valor)) {
                    System.err.println("Error EvaluacionDAO: Valor inválido en ítem " + i + ": " + valor);
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

    public Evaluacion obtenerPorTesis(int idTesis) {
        Evaluacion eval = null;
        String sql = "SELECT * FROM evaluaciones WHERE id_tesis = ?";

        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idTesis);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    eval = new Evaluacion();
                    eval.setIdEvaluacion(rs.getInt("id_evaluacion"));
                    eval.setIdTesis(rs.getInt("id_tesis"));
                    eval.setCodigoDocente(rs.getString("codigo_docente"));
                    eval.setComentarios(rs.getString("comentarios"));
                    eval.setPuntajeTotal(rs.getBigDecimal("puntaje_total"));
                    eval.setCondicion(rs.getString("condicion"));
                    eval.setFechaEvaluacion(rs.getTimestamp("fecha_evaluacion"));

                    for (int i = 1; i <= 38; i++) {
                        eval.setItem(i, rs.getBigDecimal("item_" + i));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eval;
    }
    
    private boolean esValorValido(BigDecimal valor) {
        if (valor == null) return false;
        double v = valor.doubleValue();
        return v == 1.0 || v == 0.5 || v == 0.0;
    }
}
