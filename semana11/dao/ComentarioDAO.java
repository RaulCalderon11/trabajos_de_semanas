package com.mitesis.dao;

import com.mitesis.modelos.Comentario;
import com.mitesis.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para todas las operaciones relacionadas con la tabla 'comentarios'.
 */
public class ComentarioDAO {

    /**
     * Obtiene todos los comentarios de una tesis específica, ordenados del más nuevo al más viejo.
     * @param idTesis El ID de la tesis.
     * @return Una lista de objetos Comentario.
     */
    public List<Comentario> obtenerComentariosPorTesis(int idTesis) {
        List<Comentario> comentarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM comentarios WHERE idTesis = ? ORDER BY fechaHora DESC";

        try {
            conn = ConexionDB.getInstancia().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idTesis);
            rs = ps.executeQuery();

            while (rs.next()) {
                Comentario comentario = new Comentario();
                comentario.setIdComentario(rs.getInt("idComentario"));
                comentario.setTexto(rs.getString("texto"));
                comentario.setFechaHora(rs.getTimestamp("fechaHora"));
                comentario.setIdTesis(rs.getInt("idTesis"));
                comentario.setIdDocenteAutor(rs.getInt("idDocenteAutor"));
                comentarios.add(comentario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener comentarios: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(conn, ps, rs);
        }
        return comentarios;
    }
    
    /**
     * Crea un nuevo comentario en la base de datos.
     * Este método puede ser llamado por sí solo o como parte de una transacción
     * (por eso existe la versión que recibe una 'Connection').
     * @param comentario El objeto Comentario a guardar.
     * @return true si fue exitoso, false si no.
     */
    public boolean crearComentario(Comentario comentario) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;
        
        String sql = "INSERT INTO comentarios (texto, fechaHora, idTesis, idDocenteAutor) VALUES (?, NOW(), ?, ?)";
        
        try {
            conn = ConexionDB.getInstancia().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, comentario.getTexto());
            ps.setInt(2, comentario.getIdTesis());
            ps.setInt(3, comentario.getIdDocenteAutor());
            
            exito = ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al crear comentario: " + e.getMessage());
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