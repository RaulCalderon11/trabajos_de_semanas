/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mitesis.dao;

import com.mitesis.modelos.DocumentoRequisito;
import com.mitesis.util.ConexionDB;
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
public class DocumentoRequisitoDAO {
    public boolean guardarDocumento(DocumentoRequisito doc) {
        String sql = "INSERT INTO documentos_requisito (id_tramite, tipo_documento, ruta_archivo, estado_validacion) VALUES (?, ?, ?, 'Pendiente')";
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doc.getIdTramite());
            ps.setString(2, doc.getTipoDocumento());
            ps.setString(3, doc.getRutaArchivo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<DocumentoRequisito> listarPorTramite(int idTramite) {
        List<DocumentoRequisito> lista = new ArrayList<>();
        String sql = "SELECT * FROM documentos_requisito WHERE id_tramite = ?";
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTramite);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                DocumentoRequisito d = new DocumentoRequisito();
                d.setIdDocumento(rs.getInt("id_documento"));
                d.setTipoDocumento(rs.getString("tipo_documento"));
                d.setRutaArchivo(rs.getString("ruta_archivo"));
                d.setEstadoValidacion(rs.getString("estado_validacion"));
                lista.add(d);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}
