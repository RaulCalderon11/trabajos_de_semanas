package com.mitesis.dao;

import com.mitesis.modelos.Tesis;
import com.mitesis.modelos.HistorialEstado;
import com.mitesis.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para todas las operaciones
 * relacionadas con la tabla 'tesis'.
 */
public class TesisDAO {
    
     private HistorialEstadoDAO historialDAO = new HistorialEstadoDAO();

    // Actualiza SOLO el estado (útil para Admin y flujos simples)
    public boolean actualizarEstado(int idTesis, String nuevoEstado) {
        String sql = "UPDATE tesis SET estado = ? WHERE idTesis = ?";
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idTesis);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Insertar nueva tesis
    public boolean insertarTesis(String titulo, int idEstudiante, String urlArchivo) {
        String sql = "INSERT INTO tesis (titulo, descripcion, fechaSubida, estado, urlArchivo, idEstudianteAutor) VALUES (?, 'Tesis registrada por Administración', NOW(), 'Pendiente', ?, ?)";
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setString(2, urlArchivo);
            ps.setInt(3, idEstudiante);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener tesis asignadas a un docente (para revisar)
    public List<Tesis> obtenerTesisPorDocente(int idDocenteRevisor) {
        List<Tesis> lista = new ArrayList<>();
        // Solo traemos las que NO están terminadas
        String sql = "SELECT t.*, CONCAT(u.nombre, ' ', u.apellido) as nombreAutor FROM tesis t " +
                     "JOIN usuarios u ON t.idEstudianteAutor = u.idUsuario " +
                     "WHERE idDocenteRevisor = ? AND estado IN ('En Revisión', 'Requiere Correcciones') " +
                     "ORDER BY fechaProximaRevision ASC";
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDocenteRevisor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tesis t = poblarTesisResult(rs);
                t.setNombreAutor(rs.getString("nombreAutor"));
                lista.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
    
    // Obtener tesis para asignar Jurado (Aprobadas por asesor)
    public List<Tesis> obtenerTesisAprobadasPorAsesor() {
        List<Tesis> lista = new ArrayList<>();
        String sql = "SELECT t.*, CONCAT(u_aut.nombre, ' ', u_aut.apellido) AS nombreAutor, CONCAT(u_rev.nombre, ' ', u_rev.apellido) AS nombreRevisor " +
                     "FROM tesis t " +
                     "JOIN usuarios u_aut ON t.idEstudianteAutor = u_aut.idUsuario " +
                     "LEFT JOIN usuarios u_rev ON t.idDocenteRevisor = u_rev.idUsuario " +
                     "WHERE t.estado = 'Aprobado por Asesor'"; // Filtro estricto
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Tesis t = poblarTesisResult(rs);
                t.setNombreAutor(rs.getString("nombreAutor"));
                t.setNombreRevisor(rs.getString("nombreRevisor"));
                lista.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    // Actualizar estado tras revisión del docente
    public boolean actualizarEstadoPorDocente(int idTesis, int idDocente, String nuevoEstado, String notas) throws SQLException {
        Connection conn = null;
        PreparedStatement psTesis = null;
        boolean exito = false;
        String sql = "UPDATE tesis SET estado = ? WHERE idTesis = ? AND idDocenteRevisor = ?";
        try {
            conn = ConexionDB.getInstancia().getConnection();
            conn.setAutoCommit(false);
            psTesis = conn.prepareStatement(sql);
            psTesis.setString(1, nuevoEstado);
            psTesis.setInt(2, idTesis);
            psTesis.setInt(3, idDocente);
            
            if (psTesis.executeUpdate() > 0) {
                // Registrar en historial
                HistorialEstado h = new HistorialEstado();
                h.setIdTesis(idTesis);
                h.setIdUsuarioModificador(idDocente);
                h.setEstadoNuevo(nuevoEstado);
                h.setNotas(notas);
                if (historialDAO.crearHistorial(h, conn)) {
                    conn.commit();
                    exito = true;
                } else { conn.rollback(); }
            } else { conn.rollback(); }
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (psTesis != null) psTesis.close();
            if (conn != null) { conn.setAutoCommit(true); conn.close(); }
        }
        return exito;
    }

    // Métodos auxiliares y otros gets...
    public Tesis obtenerTesisPorEstudiante(int id) {
        // Implementación básica (omitiendo try/catch detallado para brevedad en este ejemplo)
        Tesis t = null;
        try(Connection c = ConexionDB.getInstancia().getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM tesis WHERE idEstudianteAutor=?")) {
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if(rs.next()) t = poblarTesisResult(rs);
        } catch(Exception e) { e.printStackTrace(); }
        return t;
    }
    
    public List<Tesis> obtenerTodasLasTesisDetallado() {
        List<Tesis> lista = new ArrayList<>();
        String sql = "SELECT t.*, CONCAT(u.nombre, ' ', u.apellido) as nombreAutor FROM tesis t JOIN usuarios u ON t.idEstudianteAutor = u.idUsuario ORDER BY fechaSubida DESC";
        try(Connection c = ConexionDB.getInstancia().getConnection(); PreparedStatement p = c.prepareStatement(sql); ResultSet rs = p.executeQuery()) {
            while(rs.next()) {
                Tesis t = poblarTesisResult(rs);
                t.setNombreAutor(rs.getString("nombreAutor"));
                lista.add(t);
            }
        } catch(Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<Tesis> obtenerTesisSinAsignar() {
        List<Tesis> lista = new ArrayList<>();
        String sql = "SELECT t.*, CONCAT(u.nombre, ' ', u.apellido) as nombreAutor FROM tesis t JOIN usuarios u ON t.idEstudianteAutor = u.idUsuario WHERE t.idDocenteRevisor IS NULL AND t.estado = 'Pendiente'";
        try(Connection c = ConexionDB.getInstancia().getConnection(); PreparedStatement p = c.prepareStatement(sql); ResultSet rs = p.executeQuery()) {
            while(rs.next()) {
                Tesis t = poblarTesisResult(rs);
                t.setNombreAutor(rs.getString("nombreAutor"));
                lista.add(t);
            }
        } catch(Exception e) { e.printStackTrace(); }
        return lista;
    }
    
    public List<Tesis> obtenerTesisComoJurado(String codigoDocente) {
         List<Tesis> lista = new ArrayList<>();
         // Lógica para obtener tesis donde el docente es jurado
         // (Implementación simplificada)
         return lista;
    }
    
    public List<Tesis> obtenerTesisRevisadasPorDocente(int idDocente) {
        List<Tesis> lista = new ArrayList<>();
        String sql = "SELECT t.*, CONCAT(u.nombre, ' ', u.apellido) as nombreAutor FROM tesis t JOIN usuarios u ON t.idEstudianteAutor = u.idUsuario WHERE idDocenteRevisor = ? AND estado IN ('Aprobado por Asesor', 'Rechazado')";
        try(Connection c = ConexionDB.getInstancia().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idDocente);
            ResultSet rs = p.executeQuery();
            while(rs.next()) {
                Tesis t = poblarTesisResult(rs);
                t.setNombreAutor(rs.getString("nombreAutor"));
                lista.add(t);
            }
        } catch(Exception e) { e.printStackTrace(); }
        return lista;
    }
    
    public boolean registrarEntrega(int idEstudiante, String tipoEntrega, String comentarios, String urlArchivo) {
         String sql = "UPDATE tesis SET urlArchivo = ?, fechaSubida = NOW(), descripcion = ?, estado = 'Pendiente' WHERE idEstudianteAutor = ?";
         try(Connection c = ConexionDB.getInstancia().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
             p.setString(1, urlArchivo);
             p.setString(2, comentarios);
             p.setInt(3, idEstudiante);
             return p.executeUpdate() > 0;
         } catch(Exception e) { e.printStackTrace(); return false; }
    }
    
    public boolean asignarRevisor(int idTesis, int idDocente, java.sql.Date fecha, int idAdmin) {
        String sql = "UPDATE tesis SET idDocenteRevisor = ?, estado = 'En Revisión' WHERE idTesis = ?";
        try(Connection c = ConexionDB.getInstancia().getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idDocente);
            p.setInt(2, idTesis);
            return p.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); return false; }
    }

    private Tesis poblarTesisResult(ResultSet rs) throws SQLException {
        Tesis t = new Tesis();
        t.setIdTesis(rs.getInt("idTesis"));
        t.setTitulo(rs.getString("titulo"));
        t.setDescripcion(rs.getString("descripcion"));
        t.setEstado(rs.getString("estado"));
        t.setFechaSubida(rs.getTimestamp("fechaSubida"));
        t.setUrlArchivo(rs.getString("urlArchivo"));
        t.setIdEstudianteAutor(rs.getInt("idEstudianteAutor"));
        t.setIdDocenteRevisor(rs.getInt("idDocenteRevisor"));
        return t;
    }
    
    private void cerrarRecursos(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if(rs!=null)rs.close(); if(ps!=null)ps.close(); if(conn!=null)conn.close(); } catch(Exception e){}
    }
}