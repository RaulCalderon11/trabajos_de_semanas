package com.mitesis.controladores;

import com.mitesis.dao.DocumentoRequisitoDAO; // NUEVO
import com.mitesis.dao.EventoCalendarioDAO;
import com.mitesis.dao.MensajeDAO;
import com.mitesis.dao.TesisDAO;
import com.mitesis.dao.TramiteDAO; // NUEVO
import com.mitesis.dao.UsuarioDAO;

import com.mitesis.modelos.DocumentoRequisito; // NUEVO
import com.mitesis.modelos.Estudiante;
import com.mitesis.modelos.EventoCalendario;
import com.mitesis.modelos.Mensaje;
import com.mitesis.modelos.Tesis;
import com.mitesis.modelos.Tramite; // NUEVO

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(name = "EstudianteControlador", urlPatterns = {"/dashboardEstudiante"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, maxFileSize = 1024 * 1024 * 20, maxRequestSize = 1024 * 1024 * 25)
public class EstudianteControlador extends HttpServlet {

    private TesisDAO tesisDAO = new TesisDAO();
    private MensajeDAO mensajeDAO = new MensajeDAO();
    private EventoCalendarioDAO calendarioDAO = new EventoCalendarioDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private TramiteDAO tramiteDAO = new TramiteDAO(); // NUEVO
    private DocumentoRequisitoDAO documentoDAO = new DocumentoRequisitoDAO(); // NUEVO

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Estudiante estudiante = (Estudiante) session.getAttribute("usuario");

        Tesis miTesis = tesisDAO.obtenerTesisPorEstudiante(estudiante.getIdUsuario());
        Tramite miTramite = tramiteDAO.obtenerPorEstudiante(estudiante.getCodigo()); // NUEVO
        List<DocumentoRequisito> misDocumentos = null;
        
        if (miTramite != null) {
            misDocumentos = documentoDAO.listarPorTramite(miTramite.getIdTramite());
        }

        List<Mensaje> mensajes = mensajeDAO.obtenerMensajesRecibidos(estudiante.getIdUsuario());
        List<EventoCalendario> eventos = calendarioDAO.obtenerEventos();
        
        long mensajesNoLeidos = mensajes.stream()
                                    .filter(m -> !m.isLeido())
                                    .count();

        request.setAttribute("miTesis", miTesis);
        request.setAttribute("miTramite", miTramite); // Enviar trámite al JSP
        request.setAttribute("misDocumentos", misDocumentos); // Enviar documentos al JSP
        request.setAttribute("mensajes", mensajes);
        request.setAttribute("eventos", eventos);
        request.setAttribute("mensajesNoLeidosCount", mensajesNoLeidos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("estudiante.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        HttpSession session = request.getSession(false);
        Estudiante estudiante = (Estudiante) session.getAttribute("usuario");
        
        if (accion == null) accion = "";

        try {
            switch(accion) {
                case "subirArchivo": // Subir el PDF de tesis (ya existía)
                    handleSubirTesis(request, estudiante);
                    break;
                case "subirRequisito": // Subir DNI, Bachiller, etc. - NUEVO
                    handleSubirRequisito(request, estudiante);
                    break;
                case "enviarMensaje":
                    handleEnviarMensaje(request, estudiante);
                    break;
                case "actualizarPerfil":
                    handleActualizarPerfil(request, estudiante);
                    break;
                default:
                    System.out.println("Acción POST desconocida: " + accion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/dashboardEstudiante");
    }

    private void handleSubirTesis(HttpServletRequest request, Estudiante estudiante) throws IOException, ServletException {
        try {
            String tipoEntrega = request.getParameter("tipoEntrega");
            String comentarios = request.getParameter("comentarios");
            Part filePart = request.getPart("archivo");
            
            if (filePart == null || filePart.getSize() == 0) return;

            String originalName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uniqueName = System.currentTimeMillis() + "_" + estudiante.getIdUsuario() + "_" + originalName;
            
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String savePath = uploadPath + File.separator + uniqueName;
            filePart.write(savePath);

            String dbPath = "uploads/" + uniqueName; 
            tesisDAO.registrarEntrega(estudiante.getIdUsuario(), tipoEntrega, comentarios, dbPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSubirRequisito(HttpServletRequest request, Estudiante estudiante) throws IOException, ServletException {
        try {
            String tipoDoc = request.getParameter("tipoDocumento"); // "DNI", "Bachiller", etc.
            Part filePart = request.getPart("archivoRequisito");
            
            if (filePart != null && filePart.getSize() > 0) {
                Tramite tramite = tramiteDAO.obtenerPorEstudiante(estudiante.getCodigo());
                if (tramite == null) {
                    tramiteDAO.iniciarTramite(estudiante.getCodigo());
                    tramite = tramiteDAO.obtenerPorEstudiante(estudiante.getCodigo());
                }

                String fileName = "REQ_" + tipoDoc + "_" + System.currentTimeMillis() + ".pdf";
                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "requisitos";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                filePart.write(uploadPath + File.separator + fileName);

                DocumentoRequisito doc = new DocumentoRequisito();
                doc.setIdTramite(tramite.getIdTramite());
                doc.setTipoDocumento(tipoDoc);
                doc.setRutaArchivo("uploads/requisitos/" + fileName);
                
                documentoDAO.guardarDocumento(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void handleEnviarMensaje(HttpServletRequest request, Estudiante estudiante) {
        try {
            int idDestinatario = Integer.parseInt(request.getParameter("destinatarioId"));
            String cuerpoMensaje = request.getParameter("contenido"); 
            int idRemitente = estudiante.getIdUsuario();
            
            Mensaje nuevoMensaje = new Mensaje();
            nuevoMensaje.setAsunto("Mensaje de Estudiante"); 
            nuevoMensaje.setCuerpo(cuerpoMensaje); 
            nuevoMensaje.setIdRemitente(idRemitente);
            nuevoMensaje.setIdDestinatario(idDestinatario);
            
            mensajeDAO.enviarMensaje(nuevoMensaje);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void handleActualizarPerfil(HttpServletRequest request, Estudiante estudiante) {
         try {
            String email = request.getParameter("email");
            String nuevaClave = request.getParameter("nuevaClave");
            
            usuarioDAO.actualizarPerfil(estudiante.getIdUsuario(), email, nuevaClave);

            estudiante.setEmail(email);
            request.getSession().setAttribute("usuario", estudiante);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}