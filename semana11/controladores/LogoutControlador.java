package com.mitesis.controladores;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet (Controlador) que maneja el cierre de sesión (Logout).
 */
@WebServlet(name = "LogoutControlador", urlPatterns = {"/LogoutControlador"})
public class LogoutControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Obtener la sesión actual (sin crear una nueva si no existe)
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // 2. Invalidar la sesión (borra todos los atributos, incl. el "usuario")
            session.invalidate();
        }
        
        // 3. Redirigir al usuario de vuelta a la página de login
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // La acción de Logout siempre debe ser por GET (un simple enlace)
        doGet(request, response);
    }
}