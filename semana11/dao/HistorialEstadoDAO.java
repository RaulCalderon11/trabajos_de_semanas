package com.mitesis.dao;

import com.mitesis.modelos.HistorialEstado;
import com.mitesis.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para las operaciones de la tabla 'historial_estado'.
 * Esta clase es usada por TesisDAO para registrar cambios.
 */
public class HistorialEstadoDAO {

    /**
     * Obtiene la lista de todos los cambios de estado para una tesis específica,
     * ordenados del más reciente al más antiguo.
     * @param idTesis El ID de la tesis a consultar.
     * @return Una lista de objetos HistorialEstado.
     */
    public List<HistorialEstado> obtenerHistorialPorTesis(int idTesis) {
        List<HistorialEstado> historial = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM historial_estado WHERE idTesis = ? ORDER BY fechaCambio DESC";

        try {
            conn = ConexionDB.getInstancia().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idTesis);
            rs = ps.executeQuery();

            while (rs.next()) {
                HistorialEstado evento = new HistorialEstado();
                evento.setIdHistorial(rs.getInt("idHistorial"));
                evento.setFechaCambio(rs.getTimestamp("fechaCambio"));
                evento.setEstadoNuevo(rs.getString("estadoNuevo"));
                evento.setNotas(rs.getString("notas"));
                evento.setIdTesis(rs.getInt("idTesis"));
                evento.setIdUsuarioModificador(rs.getInt("idUsuarioModificador"));
                historial.add(evento);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener historial de tesis: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(conn, ps, rs);
        }
        return historial;
    }

    /**
     * Crea un nuevo registro en la tabla 'historial_estado'.
     * Este método está diseñado para ser llamado DENTRO de una transacción
     * iniciada por otro DAO (como TesisDAO).
     * * @param historial El objeto HistorialEstado a guardar.
     * @param conn La conexión existente de la transacción.
     * @return true si fue exitoso, false si no.
     * @throws SQLException Si algo falla, lanza la excepción para que la transacción haga rollback.
     */
    public boolean crearHistorial(HistorialEstado historial, Connection conn) throws SQLException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO historial_estado (fechaCambio, estadoNuevo, notas, idTesis, idUsuarioModificador) VALUES (NOW(), ?, ?, ?, ?)";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, historial.getEstadoNuevo());
            ps.setString(2, historial.getNotas());
            ps.setInt(3, historial.getIdTesis());
            ps.setInt(4, historial.getIdUsuarioModificador());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } finally {
            // ¡IMPORTANTE! NO cerramos la conexión aquí.
            // La conexión la maneja el método que inició la transacción (TesisDAO).
            if (ps != null) {
                ps.close();
            }
        }
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