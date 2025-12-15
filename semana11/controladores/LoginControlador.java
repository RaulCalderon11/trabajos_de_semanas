package com.mitesis.controladores;

import com.mitesis.dao.UsuarioDAO;
import com.mitesis.modelos.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet (Controlador) que maneja el inicio de sesión (Login).
 */
// La anotación @WebServlet mapea este Servlet a una URL
@WebServlet(name = "LoginControlador", urlPatterns = {"/LoginControlador"})
public class LoginControlador extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Maneja las peticiones POST (enviadas desde el formulario de login).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Configurar el tipo de contenido para la respuesta
        response.setContentType("text/html;charset=UTF-8");
        
        // 2. Obtener los parámetros del formulario
        String nombreUsuario = request.getParameter("username");
        String contrasena = request.getParameter("password");
        
        // 3. Mensajes de depuración
        System.out.println("\n=== INTENTO DE INICIO DE SESIÓN ===");
        System.out.println("URL de la solicitud: " + request.getRequestURL());
        System.out.println("Método: " + request.getMethod());
        System.out.println("Usuario: " + nombreUsuario);
        System.out.println("Context Path: " + request.getContextPath());
        
        try {
            // 4. Verificar credenciales
            Usuario usuario = usuarioDAO.verificarLogin(nombreUsuario, contrasena);
            
            if (usuario != null) {
                System.out.println("Autenticación exitosa para: " + usuario.getNombreUsuario() + " (Rol: " + usuario.getRol() + ")");
                
                // 5. Crear o obtener la sesión
                HttpSession session = request.getSession(true);
                System.out.println("ID de sesión: " + session.getId());
                
                // 6. Establecer atributos de sesión
                session.setAttribute("usuario", usuario);
                session.setAttribute("username", usuario.getNombreUsuario());
                session.setAttribute("rol", usuario.getRol());
                
                // 7. Configurar tiempo de inactividad de la sesión (30 minutos)
                session.setMaxInactiveInterval(30 * 60);
                
                // 8. Mensaje de depuración
                System.out.println("Sesión creada - Usuario: " + session.getAttribute("username"));
                System.out.println("Rol en sesión: " + session.getAttribute("rol"));
                
                // 9. Redirigir según el rol
                String targetPage = "";
                switch(usuario.getRol().toLowerCase()) {
                    case "admin":
                        targetPage = "dashboardAdmin";
                        break;
                    case "docente":
                        targetPage = "dashboardDocente";
                        break;
                    case "estudiante":
                        targetPage = "dashboardEstudiante";
                        break;
                    default:
                        targetPage = "index.jsp";
                }
                
                // 10. Redirigir con el contexto de la aplicación
                String redirectURL = request.getContextPath() + "/" + targetPage;
                System.out.println("Redirigiendo a: " + redirectURL);
                response.sendRedirect(redirectURL);
                return;
                
            } else {
                System.out.println("ERROR: Autenticación fallida para el usuario: " + nombreUsuario);
                request.setAttribute("error", "Usuario o contraseña incorrectos");
            }
            
        } catch (Exception e) {
            System.err.println("Error en el proceso de login: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error en el servidor. Por favor, intente nuevamente.");
        }
        
        // Si llegamos aquí, hubo un error o las credenciales son incorrectas
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * Maneja peticiones GET. En este caso, solo redirigimos al POST.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si alguien intenta acceder por GET, lo mandamos al index
        response.sendRedirect("index.jsp");
    }
}