package com.mitesis.dao;

import com.mitesis.modelos.Mensaje;
import com.mitesis.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para todas las operaciones de la tabla 'mensajes'.
 */
public class MensajeDAO {

    /**
     * Obtiene todos los mensajes RECIBIDOS por un usuario.
     * @param idDestinatario El ID del usuario que está logueado.
     * @return Una lista de objetos Mensaje.
     */
    public List<Mensaje> obtenerMensajesRecibidos(int idDestinatario) {
        List<Mensaje> mensajes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // También podríamos hacer un JOIN con usuarios para traer el nombre del remitente
        String sql = "SELECT * FROM mensajes WHERE idDestinatario = ? ORDER BY fechaHoraEnvio DESC";

        try {
            conn = ConexionDB.getInstancia().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idDestinatario);
            rs = ps.executeQuery();

            while (rs.next()) {
                mensajes.add(poblarMensajeDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener mensajes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(conn, ps, rs);
        }
        return mensajes;
    }
    
    // (Aquí podrías crear un método "obtenerMensajesEnviados" si fuera necesario)

    /**
     * Inserta un nuevo mensaje en la BD.
     * @param mensaje El objeto Mensaje a enviar.
     * @return true si fue exitoso, false si no.
     */
    public boolean enviarMensaje(Mensaje mensaje) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;
        
        String sql = "INSERT INTO mensajes (asunto, cuerpo, fechaHoraEnvio, leido, idRemitente, idDestinatario) VALUES (?, ?, NOW(), 0, ?, ?)";
        
        try {
            conn = ConexionDB.getInstancia().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, mensaje.getAsunto());
            ps.setString(2, mensaje.getCuerpo());
            ps.setInt(3, mensaje.getIdRemitente());
            ps.setInt(4, mensaje.getIdDestinatario());
            
            exito = ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al enviar mensaje: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(conn, ps, null);
        }
        return exito;
    }

    /**
     * Marca un mensaje como leído.
     * @param idMensaje El ID del mensaje a marcar.
     * @return true si fue exitoso, false si no.
     */
    public boolean marcarComoLeido(int idMensaje) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;
        
        String sql = "UPDATE mensajes SET leido = 1 WHERE idMensaje = ?";
        
        try {
            conn = ConexionDB.getInstancia().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idMensaje);
            exito = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al marcar mensaje como leído: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(conn, ps, null);
        }
        return exito;
    }
    
    /**
     * Método helper para poblar un objeto Mensaje desde un ResultSet.
     */
    private Mensaje poblarMensajeDesdeResultSet(ResultSet rs) throws SQLException {
        Mensaje msg = new Mensaje();
        msg.setIdMensaje(rs.getInt("idMensaje"));
        msg.setAsunto(rs.getString("asunto"));
        msg.setCuerpo(rs.getString("cuerpo"));
        msg.setFechaHoraEnvio(rs.getTimestamp("fechaHoraEnvio"));
        msg.setLeido(rs.getBoolean("leido"));
        msg.setIdRemitente(rs.getInt("idRemitente"));
        msg.setIdDestinatario(rs.getInt("idDestinatario"));
        return msg;
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