package com.mitesis.dao;

import com.mitesis.modelos.Docente;
import com.mitesis.modelos.Estudiante;
import com.mitesis.modelos.Usuario;
import com.mitesis.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO optimizado para usuarios.
 * - Contraseñas en texto plano (SIN ENCRIPTACIÓN).
 * - Uso de try-with-resources para gestión automática de cierre de conexiones.
 */
public class UsuarioDAO {

    /**
     * Verifica las credenciales de un usuario contra la BD en texto plano.
     */
    public Usuario verificarLogin(String nombreUsuario, String contrasenaInput) {
        Usuario usuario = null;
        String sql = "SELECT u.*, "
                   + "e.codigoEstudiante, e.carrera, "
                   + "d.departamento, d.especialidad "
                   + "FROM usuarios u "
                   + "LEFT JOIN estudiantes e ON u.idUsuario = e.idEstudiante "
                   + "LEFT JOIN docentes d ON u.idUsuario = d.idDocente "
                   + "WHERE u.nombreUsuario = ? AND u.contrasena = ? AND u.activo = 1";

        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasenaInput);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String rol = rs.getString("rol");
                    if ("estudiante".equals(rol)) {
                        Estudiante est = new Estudiante();
                        poblarUsuarioBase(est, rs);
                        est.setCodigoEstudiante(rs.getString("codigoEstudiante"));
                        est.setCarrera(rs.getString("carrera"));
                        usuario = est;
                    } else if ("docente".equals(rol)) {
                        Docente doc = new Docente();
                        poblarUsuarioBase(doc, rs);
                        doc.setDepartamento(rs.getString("departamento"));
                        doc.setEspecialidad(rs.getString("especialidad"));
                        usuario = doc;
                    } else if ("admin".equals(rol)) {
                        Usuario admin = new Usuario();
                        poblarUsuarioBase(admin, rs);
                        usuario = admin;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public List<Usuario> listarTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.* FROM usuarios u ORDER BY u.apellido, u.nombre";
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                poblarUsuarioBase(u, rs);
                usuarios.add(u);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return usuarios;
    }

    public List<Docente> listarDocentesActivos() {
        List<Docente> docentes = new ArrayList<>();
        String sql = "SELECT u.*, d.departamento, d.especialidad "
                   + "FROM usuarios u "
                   + "JOIN docentes d ON u.idUsuario = d.idDocente "
                   + "WHERE u.rol = 'docente' AND u.activo = 1 "
                   + "ORDER BY u.apellido, u.nombre";
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Docente doc = new Docente();
                poblarUsuarioBase(doc, rs);
                doc.setDepartamento(rs.getString("departamento"));
                doc.setEspecialidad(rs.getString("especialidad"));
                docentes.add(doc);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return docentes;
    }

    public boolean crearUsuario(Usuario usuario) throws SQLException {
        boolean exito = false;
        String sqlUsuario = "INSERT INTO usuarios (codigo, nombreUsuario, contrasena, nombre, apellido, email, rol, fotoPerfilUrl, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlEstudiante = "INSERT INTO estudiantes (idEstudiante, codigoEstudiante, carrera) VALUES (?, ?, ?)";
        String sqlDocente = "INSERT INTO docentes (idDocente, departamento, especialidad) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement psUsuario = null;
        PreparedStatement psRolEspecifico = null;
        ResultSet rsKeys = null;

        try {
            conn = ConexionDB.getInstancia().getConnection();
            conn.setAutoCommit(false);
            psUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            psUsuario.setString(1, usuario.getCodigo()); 
            psUsuario.setString(2, usuario.getNombreUsuario());
            psUsuario.setString(3, usuario.getContrasena());
            psUsuario.setString(4, usuario.getNombre());
            psUsuario.setString(5, usuario.getApellido());
            psUsuario.setString(6, usuario.getEmail());
            psUsuario.setString(7, usuario.getRol());
            psUsuario.setString(8, usuario.getFotoPerfilUrl());
            psUsuario.setBoolean(9, usuario.isActivo());
            psUsuario.executeUpdate();

            rsKeys = psUsuario.getGeneratedKeys();
            int idGenerado;
            if (rsKeys.next()) { idGenerado = rsKeys.getInt(1); } else { throw new SQLException("Error al obtener ID"); }

            if (usuario instanceof Estudiante) {
                Estudiante est = (Estudiante) usuario;
                psRolEspecifico = conn.prepareStatement(sqlEstudiante);
                psRolEspecifico.setInt(1, idGenerado);
                psRolEspecifico.setString(2, est.getCodigoEstudiante());
                psRolEspecifico.setString(3, est.getCarrera());
            } else if (usuario instanceof Docente) {
                Docente doc = (Docente) usuario;
                psRolEspecifico = conn.prepareStatement(sqlDocente);
                psRolEspecifico.setInt(1, idGenerado);
                psRolEspecifico.setString(2, doc.getDepartamento());
                psRolEspecifico.setString(3, doc.getEspecialidad());
            }
            if (psRolEspecifico != null) { psRolEspecifico.executeUpdate(); }
            conn.commit(); exito = true;
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { }
            e.printStackTrace();
        } finally {
            if (psUsuario != null) psUsuario.close();
            if (conn != null) { conn.setAutoCommit(true); conn.close(); }
        }
        return exito;
    }

    public boolean actualizarPerfil(int idUsuario, String email, String nuevaClave) throws SQLException {
        String sql;
        boolean cambiarClave = (nuevaClave != null && !nuevaClave.trim().isEmpty());
        if (cambiarClave) sql = "UPDATE usuarios SET email = ?, contrasena = ? WHERE idUsuario = ?";
        else sql = "UPDATE usuarios SET email = ? WHERE idUsuario = ?";

        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            if (cambiarClave) {
                ps.setString(2, nuevaClave);
                ps.setInt(3, idUsuario);
            } else {
                ps.setInt(2, idUsuario);
            }
            return ps.executeUpdate() > 0;
        } 
    }
    
    public int contarUsuariosActivos() {
        int total = 0;
        try (Connection conn = ConexionDB.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE activo = 1");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) total = rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return total;
    }

    private void poblarUsuarioBase(Usuario usuario, ResultSet rs) throws SQLException {
        usuario.setIdUsuario(rs.getInt("idUsuario"));
        usuario.setCodigo(rs.getString("codigo"));
        usuario.setNombreUsuario(rs.getString("nombreUsuario"));
        usuario.setContrasena(rs.getString("contrasena"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setEmail(rs.getString("email"));
        usuario.setRol(rs.getString("rol"));
        usuario.setFotoPerfilUrl(rs.getString("fotoPerfilUrl"));
        usuario.setActivo(rs.getBoolean("activo"));
    }
}