<%-- 
    Document   : index
    Created on : 16 nov 2025
    Author     : 
    Versión    : Animación Automática (1.5s)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- CORRECTO PARA JAKARTA EE 10 / TOMCAT 10+ --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Login Interactivo</title>
    
    <script src="https://cdn.tailwindcss.com"></script> 
    <script>
      tailwind.config = { darkMode: 'class' }
    </script>
    
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;800&display=swap" rel="stylesheet">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body class="antialiased text-slate-800 bg-white dark:bg-slate-900 dark:text-slate-200" data-context-path="${pageContext.request.contextPath}">

    <main>

        <%-- PANTALLA DE BIENVENIDA (SPLASH SCREEN) --%>
        <%-- Ya no tiene botón, solo muestra el logo y texto y desaparece sola --%>
        <section id="welcome-screen" class="h-screen w-full flex flex-col items-center justify-center p-8 bg-gradient-to-br from-indigo-100 via-white to-sky-100 dark:from-indigo-900 dark:via-slate-900 dark:to-sky-900">
            
            <div class="animate-fade-in mb-6 text-indigo-600 dark:text-indigo-400" style="animation-delay: 0.1s;">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-24 h-24 md:w-32 md:h-32">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M4.26 10.147a60.436 60.436 0 00-.491 6.347A48.627 48.627 0 0112 20.904a48.627 48.627 0 018.232-4.41 60.46 60.46 0 00-.491-6.347m-15.482 0a50.57 50.57 0 00-2.658-.813A59.905 59.905 0 0112 3.493a59.902 59.902 0 0110.499 5.221 50.57 50.57 0 00-2.658.814m-15.482 0A50.697 50.697 0 0112 13.489a50.702 50.702 0 017.74-3.342M6.75 15a.75.75 0 100-1.5.75.75 0 000 1.5zm0 0v-3.675A55.378 55.378 0 0112 8.443m-7.007 11.55A5.981 5.981 0 006.75 15.75v-1.5" />
                </svg>
            </div>

            <h1 class="text-6xl md:text-8xl font-bold text-slate-800 dark:text-slate-100 animate-fade-in" style="animation-delay: 0.2s;">
                Bienvenido
            </h1>
            <p class="text-lg text-slate-600 dark:text-slate-400 mt-4 animate-fade-in" style="animation-delay: 0.4s;">
                Cargando plataforma...
            </p>
            
        </section>

        <%-- PANTALLA DE LOGIN --%>
        <section id="login-screen" class="h-screen w-full flex items-center justify-center p-8 bg-cover bg-center hidden relative" style="background-image: url('https://source.unsplash.com/random/1920x1080?university,library');">
            
            <div class="absolute top-6 right-6 z-20">
                <button class="theme-toggle-btn p-2 rounded-full text-white bg-black/20 backdrop-blur-sm hover:bg-black/40 transition-colors" title="Cambiar tema">
                    <svg class="theme-icon-sun w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" d="M12 3v2.25m6.364.386-1.591 1.591M21 12h-2.25m-.386 6.364-1.591-1.591M12 18.75V21m-6.364-.386 1.591-1.591M3 12H.75m.386-6.364 1.591 1.591" /></svg>
                    <svg class="theme-icon-moon w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" d="M21.752 15.002A9.72 9.72 0 0 1 18 15.75c-5.385 0-9.75-4.365-9.75-9.75 0-1.33.266-2.597.748-3.752A9.753 9.753 0 0 0 3 11.25c0 5.385 4.365 9.75 9.75 9.75 2.671 0 5.1-1.057 6.9-2.798Z" /></svg>
                </button>
            </div>

            <div class="w-full max-w-4xl bg-white/50 backdrop-blur-xl dark:bg-slate-900/50 dark:backdrop-blur-xl rounded-2xl shadow-2xl overflow-hidden grid grid-cols-1 md:grid-cols-2 animate-fade-in">
                
                <div class="hidden md:block h-full">
                    <img src="https://source.unsplash.com/random/800x600?students,learning" alt="Estudiantes aprendiendo" class="w-full h-full object-cover" onerror="this.onerror=null;this.src='https://placehold.co/800x600/60a5fa/FFFFFF?text=Imagen+Estudiantes';">
                </div>

                <div class="p-8 md:p-12">
                    <div class="flex justify-center mb-8">
                        <img src="https://via.placeholder.com/150x150.png?text=Logo+Institucion" alt="Logo Institución" class="w-32 h-32 object-contain" onerror="this.onerror=null;this.src='https://placehold.co/150x150/3b82f6/FFFFFF?text=Logo';">
                    </div>

                    <h2 class="text-3xl font-bold text-slate-900 dark:text-white mb-4 text-center md:text-left">Iniciar Sesión</h2>
                    <p class="text-slate-700 dark:text-slate-300 mb-8 text-center md:text-left">Ingresa tus credenciales para acceder.</p>
                    
                    <%-- FORMULARIO DE LOGIN --%>
<form action="${pageContext.request.contextPath}/LoginControlador" method="POST" class="space-y-6">                        
                        <div>
                            <label for="username" class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">Usuario</label>
                            <input type="text" id="username" name="username"
                                   class="w-full px-4 py-3 rounded-lg border border-slate-300 focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition dark:bg-slate-800 dark:border-slate-600 dark:text-white dark:focus:ring-blue-400"
                                   placeholder="ej. a.garcia" required>
                        </div>
                        
                        <div>
                            <div class="flex justify-between items-center mb-2">
                                <label for="password" class="block text-sm font-semibold text-slate-700 dark:text-slate-300">Contraseña</label>
                            </div>
                            <input type="password" id="password" name="password"
                                   class="w-full px-4 py-3 rounded-lg border border-slate-300 focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition dark:bg-slate-800 dark:border-slate-600 dark:text-white dark:focus:ring-blue-400"
                                   placeholder="••••••••" required>
                        </div>

                        <%-- Alerta de errores de Login --%>
                        <c:if test="${not empty error}">
                            <div class="p-4 text-sm text-red-700 bg-red-100 rounded-lg dark:bg-red-200 dark:text-red-800" role="alert">
                                ${error}
                            </div>
                        </c:if>
                        <%-- Alerta de errores de Sesión (Filtro) --%>
                        <c:if test="${param.error eq 'DebeIniciarSesion'}">
                             <div class="p-4 text-sm text-yellow-700 bg-yellow-100 rounded-lg" role="alert">
                                Su sesión ha expirado o no ha iniciado sesión.
                            </div>
                        </c:if>
                        <c:if test="${param.error eq 'AccesoDenegado'}">
                             <div class="p-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                                No tiene permisos para acceder a esa sección.
                            </div>
                        </c:if>

                        <button type="submit" class="w-full py-3 px-6 bg-blue-600 hover:bg-blue-700 text-white font-semibold rounded-lg shadow-md transition-transform transform hover:scale-105 flex items-center justify-center">
                            Ingresar
                        </button>
                        
                    </form>

                </div>
            </div>

        </section>
        
    </main>
    
    <%-- El Modal y JS siguen igual --%>
    <div id="alert-modal" class="fixed inset-0 bg-black/50 backdrop-blur-sm flex items-center justify-center p-4 z-50 hidden" style="animation-duration: 0.3s;">
        <div class="bg-white dark:bg-slate-800 rounded-lg shadow-2xl p-6 max-w-sm w-full animate-fade-in" style="animation-duration: 0.3s;">
            <h3 id="alert-modal-title" class="text-lg font-bold text-slate-900 dark:text-white mb-4">Aviso</h3>
            <p id="alert-modal-message" class="text-slate-600 dark:text-slate-300 mb-6">Mensaje de alerta.</p>
            <button id="alert-modal-close" class="btn-fill-solid w-full" style="background-color: #3b82f6; border-color: #3b82f6;">Entendido</button>
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/app.js"></script>

</body>
</html>