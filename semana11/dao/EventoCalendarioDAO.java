package com.mitesis.dao;

import com.mitesis.modelos.EventoCalendario;
import com.mitesis.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para las operaciones de la tabla 'evento_calendario'.
 */
public class EventoCalendarioDAO {

    /**
     * Obtiene todos los eventos del calendario, ordenados por fecha.
     * @return Una lista de objetos EventoCalendario.
     */
    public List<EventoCalendario> obtenerEventos() {
        List<EventoCalendario> eventos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM evento_calendario ORDER BY fechaEvento ASC";

        try {
            conn = ConexionDB.getInstancia().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                EventoCalendario evento = new EventoCalendario();
                evento.setIdEvento(rs.getInt("idEvento"));
                evento.setTitulo(rs.getString("titulo"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setFechaEvento(rs.getDate("fechaEvento"));
                evento.setTipo(rs.getString("tipo"));
                eventos.add(evento);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener eventos del calendario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(conn, ps, rs);
        }
        return eventos;
    }

    /**
     * Crea un nuevo evento (solo Admin).
     * @param evento El objeto EventoCalendario a guardar.
     * @return true si fue exitoso, false si no.
     */
    public boolean crearEvento(EventoCalendario evento) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;
        
        String sql = "INSERT INTO evento_calendario (titulo, descripcion, fechaEvento, tipo) VALUES (?, ?, ?, ?)";
        
        try {
            conn = ConexionDB.getInstancia().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, evento.getTitulo());
            ps.setString(2, evento.getDescripcion());
            ps.setDate(3, new java.sql.Date(evento.getFechaEvento().getTime()));
            ps.setString(4, evento.getTipo());
            
            exito = ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al crear evento: " + e.getMessage());
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