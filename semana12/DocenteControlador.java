package com.mitesis.controladores;

import com.mitesis.dao.EvaluacionDAO; 
import com.mitesis.dao.EvaluacionJuradoDAO;
import com.mitesis.dao.TesisDAO;
import com.mitesis.dao.UsuarioDAO;
import com.mitesis.modelos.Docente;
import com.mitesis.modelos.Evaluacion;
import com.mitesis.modelos.EvaluacionJurado;
import com.mitesis.modelos.Tesis;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "DocenteControlador", urlPatterns = {"/dashboardDocente"})
public class DocenteControlador extends HttpServlet {

    private TesisDAO tesisDAO = new TesisDAO();
    private EvaluacionDAO evaluacionDAO = new EvaluacionDAO();
    private EvaluacionJuradoDAO evaluacionJuradoDAO = new EvaluacionJuradoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Docente docente = (Docente) session.getAttribute("usuario");

        // 1. Tesis como Asesor (Solo pendientes de revisión)
        // El DAO ahora filtra por 'En Revisión', así que las 'Aprobado por Asesor' ya no salen aquí
        List<Tesis> tesisAsignadas = tesisDAO.obtenerTesisPorDocente(docente.getIdUsuario());
        
        // 2. Tesis como Jurado
        List<Tesis> tesisJurado = tesisDAO.obtenerTesisComoJurado(docente.getCodigo());

        // 3. Historial (Aquí sí vemos las aprobadas)
        List<Tesis> tesisRevisadas = tesisDAO.obtenerTesisRevisadasPorDocente(docente.getIdUsuario());

        request.setAttribute("tesisAsignadas", tesisAsignadas);
        request.setAttribute("tesisJurado", tesisJurado);
        request.setAttribute("tesisRevisadas", tesisRevisadas);
        request.setAttribute("tesisPendientesCount", tesisAsignadas.size());

        RequestDispatcher dispatcher = request.getRequestDispatcher("docente.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        Docente docente = (Docente) request.getSession().getAttribute("usuario");
        
        if ("calificarTesis".equals(accion)) {
            handleCalificarTesis(request, docente);
        } else if ("calificarSustentacion".equals(accion)) {
            handleCalificarSustentacion(request, docente);
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboardDocente");
    }

    private void handleCalificarTesis(HttpServletRequest request, Docente docente) {
        try {
            int idTesis = Integer.parseInt(request.getParameter("idTesis"));
            String comentarios = request.getParameter("comentarios");
            BigDecimal puntajeTotal = new BigDecimal(request.getParameter("nuevoEstado").equals("Aprobado") ? "25" : "0"); // Simplificado para ejemplo
            // En realidad aquí recoges los 38 ítems y calculas...
            
            // Determinar estado final
            String condicion = request.getParameter("nuevoEstado"); // Viene del JS calc()
            String nuevoEstadoBD = "En Revisión";
            
            if ("Aprobado".equals(condicion)) {
                nuevoEstadoBD = "Aprobado por Asesor"; // ¡CLAVE! Este estado dispara el flujo de Jurados
            } else if ("Requiere Correcciones".equals(condicion)) {
                nuevoEstadoBD = "Requiere Correcciones";
            }
            
            // Guardar evaluación (lógica DAO)
            Evaluacion eval = new Evaluacion();
            eval.setIdTesis(idTesis);
            eval.setCodigoDocente(docente.getCodigo());
            eval.setCondicion(condicion);
            eval.setPuntajeTotal(puntajeTotal);
            eval.setComentarios(comentarios);
            // ... setear items ...
            evaluacionDAO.insertarEvaluacion(eval);

            // Actualizar Tesis
            tesisDAO.actualizarEstadoPorDocente(idTesis, docente.getIdUsuario(), nuevoEstadoBD, comentarios);
            
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void handleCalificarSustentacion(HttpServletRequest request, Docente docente) {
        // Lógica similar para jurado (Anexo 5)
        // No cambiamos el estado de la tesis principal inmediatamente, 
        // salvo que sea el último jurado o el presidente.
        // Por simplicidad, solo guardamos el voto.
        try {
             String idSustentacionStr = request.getParameter("idSustentacion");
             if(idSustentacionStr != null) {
                 EvaluacionJurado eval = new EvaluacionJurado();
                 eval.setIdSustentacion(Integer.parseInt(idSustentacionStr));
                 eval.setCodigoJurado(docente.getCodigo());
                 // ... items ...
                 evaluacionJuradoDAO.insertar(eval);
             }
        } catch(Exception e) { e.printStackTrace(); }
    }
}