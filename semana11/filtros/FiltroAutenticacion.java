package com.mitesis.filtros;

import com.mitesis.modelos.Usuario;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filtro de seguridad que intercepta todas las peticiones.
 * Se asegura de que un usuario haya iniciado sesión y tenga el rol correcto.
 */
@WebFilter("/*") // Intercepta TODAS las peticiones
public class FiltroAutenticacion implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        
        // Convertimos la URI a minúsculas para facilitar la comparación de seguridad
        String uriLowerCase = requestURI.toLowerCase();

        boolean estaLogueado = (session != null && session.getAttribute("usuario") != null);
        
        // 1. Recursos PÚBLICOS (Login, estáticos, imágenes, uploads)
        // Se agregaron 'images' y 'uploads' para que se vean los logos y PDFs
        boolean esLogin = requestURI.equals(contextPath + "/") || 
                          requestURI.equals(contextPath + "/index.jsp") || 
                          requestURI.equals(contextPath + "/LoginControlador");
        
        boolean esRecursoEstatico = uriLowerCase.contains("/css/") || 
                                    uriLowerCase.contains("/js/") || 
                                    uriLowerCase.contains("/images/") ||
                                    uriLowerCase.contains("/uploads/"); 

        if (esLogin || esRecursoEstatico) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Verificación de SESIÓN y ROL
        if (estaLogueado) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            String rol = usuario.getRol(); // 'admin', 'docente', 'estudiante'
            
            // Lógica de protección de rutas basada en palabras clave
            // Usamos 'uriLowerCase' para que detecte "/dashboardAdmin" aunque busquemos "admin"
            boolean intentaEntrarAdmin = uriLowerCase.contains("admin");
            boolean intentaEntrarDocente = uriLowerCase.contains("docente");
            boolean intentaEntrarEstudiante = uriLowerCase.contains("estudiante");

            if (intentaEntrarAdmin && !rol.equals("admin")) {
                httpResponse.sendRedirect(contextPath + "/index.jsp?error=AccesoDenegadoAdmin");
            } 
            else if (intentaEntrarDocente && !rol.equals("docente")) {
                httpResponse.sendRedirect(contextPath + "/index.jsp?error=AccesoDenegadoDocente");
            } 
            else if (intentaEntrarEstudiante && !rol.equals("estudiante")) {
                httpResponse.sendRedirect(contextPath + "/index.jsp?error=AccesoDenegadoEstudiante");
            } 
            else {
                // Rol correcto o ruta común (como Logout), dejar pasar
                chain.doFilter(request, response);
            }
            
        } else {
            // 3. Usuario NO logueado intentando entrar a ruta protegida
            httpResponse.sendRedirect(contextPath + "/index.jsp?error=DebeIniciarSesion");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}