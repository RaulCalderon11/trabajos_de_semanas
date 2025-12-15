package com.mitesis.controladores;

import com.mitesis.dao.ConfiguracionSistemaDAO;
import com.mitesis.dao.SustentacionDAO;
import com.mitesis.dao.TesisDAO;
import com.mitesis.dao.UsuarioDAO;
import com.mitesis.dao.TramiteDAO;
import com.mitesis.modelos.ConfiguracionSistema;
import com.mitesis.modelos.Docente;
import com.mitesis.modelos.Sustentacion;
import com.mitesis.modelos.Tesis;
import com.mitesis.modelos.Usuario;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;

@WebServlet(name = "AdminControlador", urlPatterns = {"/dashboardAdmin"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, maxFileSize = 1024 * 1024 * 20, maxRequestSize = 1024 * 1024 * 25)
public class AdminControlador extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private TesisDAO tesisDAO = new TesisDAO();
    private ConfiguracionSistemaDAO configDAO = new ConfiguracionSistemaDAO();
    private SustentacionDAO sustentacionDAO = new SustentacionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Usuario> listaUsuarios = usuarioDAO.listarTodosLosUsuarios();
        List<Tesis> listaTesisDetallada = tesisDAO.obtenerTodasLasTesisDetallado();
        List<Docente> listaDocentes = usuarioDAO.listarDocentesActivos();
        ConfiguracionSistema config = configDAO.obtenerConfiguracion();
        
        // 1. Tesis sin Asesor (Pendiente de Asignación)
        List<Tesis> tesisSinAsignar = tesisDAO.obtenerTesisSinAsignar();
        
        // 2. Tesis listas para Jurado (Solo las aprobadas por asesor)
        // Usamos el método específico del DAO para mayor eficiencia
        List<Tesis> tesisParaJurado = tesisDAO.obtenerTesisAprobadasPorAsesor();

        request.setAttribute("listaUsuarios", listaUsuarios);
        request.setAttribute("listaTesisDetallada", listaTesisDetallada);
        request.setAttribute("tesisSinAsignar", tesisSinAsignar);
        request.setAttribute("tesisParaJurado", tesisParaJurado);
        request.setAttribute("listaDocentes", listaDocentes);
        request.setAttribute("config", config);
        
        // Stats
        request.setAttribute("totalTesis", listaTesisDetallada.size());
        request.setAttribute("totalUsuariosActivos", usuarioDAO.contarUsuariosActivos());

        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        if (accion == null) accion = "";

        try {
            switch (accion) {
                case "asignarRevision": 
                    // Asignar asesor cambia estado a "En Revisión"
                    // Esto hace que desaparezca de "Pendiente Asesor"
                    int idTesis = Integer.parseInt(request.getParameter("idTesis"));
                    int idDocente = Integer.parseInt(request.getParameter("idDocente"));
                    tesisDAO.asignarRevisor(idTesis, idDocente, null, 1);
                    break;
                    
                case "asignarJurados": 
                    // Asignar jurados cambia estado a "Jurado Asignado"
                    // Esto hace que desaparezca de "Pendiente Jurado"
                    handleAsignarJurados(request);
                    break;
                    
                case "subirTesisAdmin":
                    handleSubirTesisAdmin(request);
                    break;
                    
                case "crearUsuario":
                    // Lógica crear usuario...
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/dashboardAdmin");
    }

    private void handleAsignarJurados(HttpServletRequest request) throws Exception {
        int idTesis = Integer.parseInt(request.getParameter("idTesis"));
        String idPresidente = request.getParameter("presidente");
        String idSecretario = request.getParameter("secretario");
        String idVocal = request.getParameter("vocal");
        String idSuplente = request.getParameter("suplente");
        String fechaStr = request.getParameter("fechaHora");
        String lugar = request.getParameter("lugar");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Timestamp fechaHora = new Timestamp(sdf.parse(fechaStr).getTime());

        Sustentacion sust = new Sustentacion();
        sust.setIdTramite(idTesis);
        sust.setCodigoMiembro1(idPresidente);
        sust.setCodigoMiembro2(idSecretario);
        sust.setCodigoMiembro3(idVocal);
        sust.setCodigoSuplente(idSuplente);
        sust.setFechaHora(fechaHora);
        sust.setLugarEnlace(lugar);

        if(sustentacionDAO.programarSustentacion(sust)) {
            // ¡IMPORTANTE! Cambiar estado para que salga de la lista de pendientes
            tesisDAO.actualizarEstado(idTesis, "Jurado Asignado");
        }
    }

    private void handleSubirTesisAdmin(HttpServletRequest request) throws IOException, ServletException {
        // Lógica de subida (igual que antes)
        // ...
         try {
            String titulo = request.getParameter("titulo");
            int idEstudiante = Integer.parseInt(request.getParameter("idEstudiante"));
            Part filePart = request.getPart("archivo");
            
            if(filePart != null && filePart.getSize() > 0) {
                 String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                 String dbPath = "uploads/" + System.currentTimeMillis() + "_" + fileName;
                 // Guardar archivo...
                 // Insertar en BD
                 tesisDAO.insertarTesis(titulo, idEstudiante, dbPath);
            }
         } catch(Exception e) { e.printStackTrace(); }
    }
}