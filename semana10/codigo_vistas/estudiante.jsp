<%-- 
    Document   : estudiante
    Versión    : Diseño Final Verde + Funcionalidad Completa + Menú Limpio
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portal del Estudiante</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        body { font-family: 'Inter', sans-serif; }
        .hidden-section { display: none !important; }
        
        /* Sidebar Verde Oscuro */
        .bg-sidebar { background-color: #064e3b; } /* emerald-900 */
        
        .sidebar-active {
            background-color: rgba(255, 255, 255, 0.1);
            border-left: 4px solid #34d399; /* emerald-400 */
            color: white !important;
        }
        
        /* Asegurar altura completa */
        html, body { height: 100%; margin: 0; overflow: hidden; }
    </style>
</head>
<body class="bg-emerald-50 flex h-screen">
    
    <!-- Sidebar -->
    <aside class="w-64 bg-sidebar text-white flex flex-col shadow-2xl z-20 shrink-0 h-full">
        <div class="p-6 flex flex-col items-center border-b border-white/10">
            <div class="w-16 h-16 rounded-full bg-emerald-600 flex items-center justify-center mb-3 shadow-lg overflow-hidden border-2 border-white/20">
                 <img src="${sessionScope.usuario.fotoPerfilUrl}" class="w-full h-full object-cover" onerror="this.src='https://ui-avatars.com/api/?name=${sessionScope.usuario.nombre}&background=059669&color=fff';">
            </div>
            <h3 class="font-bold text-sm text-center">${sessionScope.usuario.nombre} ${sessionScope.usuario.apellido}</h3>
            <p class="text-emerald-200 text-xs text-center uppercase tracking-wide mt-1">Estudiante</p>
            <div class="mt-3 px-3 py-1 rounded-full bg-green-500/20 text-green-400 text-[10px] font-bold border border-green-500/30 flex items-center gap-2">
                <span class="w-1.5 h-1.5 bg-green-400 rounded-full animate-pulse"></span> ACTIVO
            </div>
        </div>

        <nav class="flex-1 py-4 px-3 space-y-1 overflow-y-auto">
            <button onclick="mostrarSeccion('dashboard')" id="nav-dashboard" class="sidebar-link w-full text-left px-4 py-3 rounded-lg text-sm font-medium text-slate-300 hover:bg-white/5 hover:text-white transition flex items-center gap-3 sidebar-active">
                <i class="fas fa-home w-5 text-center"></i> Mi Dashboard
            </button>
            <button onclick="mostrarSeccion('mitesis')" id="nav-mitesis" class="sidebar-link w-full text-left px-4 py-3 rounded-lg text-sm font-medium text-slate-300 hover:bg-white/5 hover:text-white transition flex items-center gap-3">
                <i class="fas fa-book w-5 text-center"></i> Mi Tesis
            </button>
            <button onclick="mostrarSeccion('documentos')" id="nav-documentos" class="sidebar-link w-full text-left px-4 py-3 rounded-lg text-sm font-medium text-slate-300 hover:bg-white/5 hover:text-white transition flex items-center gap-3">
                <i class="fas fa-folder-open w-5 text-center"></i> Documentos
            </button>
        </nav>
        
        <div class="p-4 border-t border-white/10">
            <a href="${pageContext.request.contextPath}/LogoutControlador" class="w-full bg-emerald-800 hover:bg-emerald-900 text-emerald-100 hover:text-white px-3 py-2 rounded-lg font-bold transition flex items-center justify-center gap-2 text-xs border border-emerald-700">
                <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
            </a>
        </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 flex flex-col min-h-0 bg-emerald-50 relative">
        <header class="bg-white shadow-sm border-b border-emerald-100 px-8 py-4 flex justify-between items-center shrink-0">
            <div>
                <h1 class="text-xl font-bold text-slate-800">Portal del Estudiante</h1>
                <p class="text-xs text-slate-500">Gestiona tu tesis y documentos académicos</p>
            </div>
            <div class="flex items-center gap-3">
                <div class="relative">
                    <span class="absolute top-0 right-0 w-2 h-2 bg-red-500 rounded-full border border-white"></span>
                    <i class="fas fa-bell text-slate-400 text-xl cursor-pointer hover:text-slate-600 transition"></i>
                </div>
            </div>
        </header>

        <div class="flex-1 overflow-auto p-8">
            
            <!-- SECCIÓN DASHBOARD -->
            <div id="seccion-dashboard" class="seccion-app space-y-8">
                
                <!-- Welcome Banner -->
                <div class="bg-gradient-to-r from-emerald-600 to-teal-500 rounded-2xl p-8 text-white shadow-lg relative overflow-hidden">
                    <div class="relative z-10">
                        <h2 class="text-3xl font-bold mb-2">¡Hola, ${fn:split(sessionScope.usuario.nombre, ' ')[0]}!</h2>
                        <c:choose>
                            <c:when test="${not empty miTesis}">
                                <p class="text-emerald-100 text-lg">Tu proyecto: <strong>${miTesis.titulo}</strong></p>
                            </c:when>
                            <c:otherwise>
                                <p class="text-emerald-100 text-lg">Aún no tienes un proyecto registrado. ¡Comencemos!</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <i class="fas fa-graduation-cap absolute -bottom-6 -right-6 text-9xl text-white opacity-10"></i>
                </div>

                <!-- Stats Grid -->
                <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <!-- Estado Actual -->
                    <div class="bg-green-500 text-white p-6 rounded-xl shadow-md relative overflow-hidden">
                        <div class="relative z-10">
                            <p class="text-xs font-bold text-green-100 uppercase mb-1">Estado de Tesis</p>
                            <h3 class="text-2xl font-bold truncate">
                                ${not empty miTesis.estado ? miTesis.estado : 'Sin Registrar'}
                            </h3>
                        </div>
                        <i class="fas fa-file-alt absolute -bottom-4 -right-4 text-7xl text-white opacity-20"></i>
                    </div>

                    <!-- Progreso -->
                    <div class="bg-blue-500 text-white p-6 rounded-xl shadow-md relative overflow-hidden">
                        <div class="relative z-10">
                            <p class="text-xs font-bold text-blue-100 uppercase mb-1">Progreso</p>
                            <c:set var="progreso" value="0"/>
                            <c:if test="${miTesis.estado == 'Pendiente'}"><c:set var="progreso" value="25"/></c:if>
                            <c:if test="${miTesis.estado == 'En Revisión'}"><c:set var="progreso" value="50"/></c:if>
                            <c:if test="${miTesis.estado == 'Apto Sustentación' || miTesis.estado == 'Aprobado por Asesor'}"><c:set var="progreso" value="75"/></c:if>
                            <c:if test="${miTesis.estado == 'Titulado'}"><c:set var="progreso" value="100"/></c:if>
                            <h3 class="text-2xl font-bold">${progreso}%</h3>
                        </div>
                        <i class="fas fa-chart-line absolute -bottom-4 -right-4 text-7xl text-white opacity-20"></i>
                    </div>

                    <!-- Días Restantes (Simulado) -->
                    <div class="bg-orange-500 text-white p-6 rounded-xl shadow-md relative overflow-hidden">
                        <div class="relative z-10">
                            <p class="text-xs font-bold text-orange-100 uppercase mb-1">Días Restantes</p>
                            <h3 class="text-2xl font-bold">45</h3>
                        </div>
                        <i class="fas fa-clock absolute -bottom-4 -right-4 text-7xl text-white opacity-20"></i>
                    </div>
                </div>

                <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
                    <!-- Tarjeta Mi Tesis -->
                    <div class="bg-white p-6 rounded-xl shadow-sm border border-emerald-100">
                        <h3 class="font-bold text-slate-800 mb-6 flex items-center gap-2">
                            <i class="fas fa-file-contract text-emerald-600"></i> Mi Tesis
                        </h3>
                        
                        <c:if test="${not empty miTesis}">
                            <div class="bg-emerald-50 rounded-lg p-4 border border-emerald-200 mb-6">
                                <h4 class="font-bold text-slate-800 text-sm mb-1">${miTesis.titulo}</h4>
                                <p class="text-xs text-slate-500">Última actualización: ${miTesis.fechaSubida}</p>
                                
                                <div class="mt-4">
                                    <div class="flex justify-between text-xs font-bold text-slate-500 mb-1">
                                        <span>Progreso</span>
                                        <span>${progreso}% completado</span>
                                    </div>
                                    <div class="w-full bg-slate-200 rounded-full h-2">
                                        <div class="bg-emerald-500 h-2 rounded-full transition-all duration-1000" style="width: ${progreso}%"></div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="space-y-3">
                                <button onclick="document.getElementById('modal-subir-tesis').classList.remove('hidden')" class="w-full bg-emerald-600 hover:bg-emerald-700 text-white font-bold py-3 rounded-lg text-sm shadow transition">
                                    Subir Nueva Versión
                                </button>
                                
                                <c:if test="${not empty miTesis.descripcion && fn:contains(miTesis.descripcion, 'Nota:')}">
                                    <div class="p-4 bg-yellow-50 border-l-4 border-yellow-400 rounded-r-lg mt-4">
                                        <h5 class="text-sm font-bold text-yellow-800 mb-1">Comentarios del Tutor</h5>
                                        <p class="text-xs text-yellow-700 italic">"${fn:substringAfter(miTesis.descripcion, 'Nota: ')}"</p>
                                    </div>
                                </c:if>
                            </div>
                        </c:if>

                        <c:if test="${empty miTesis}">
                            <div class="text-center py-10">
                                <p class="text-slate-400 text-sm mb-4">No has registrado tu tesis aún.</p>
                                <button onclick="document.getElementById('modal-subir-tesis').classList.remove('hidden')" class="bg-emerald-600 text-white px-6 py-2 rounded-full font-bold shadow hover:bg-emerald-700">Registrar Tesis</button>
                            </div>
                        </c:if>
                    </div>

                    <!-- Actividades Recientes -->
                    <div class="bg-white p-6 rounded-xl shadow-sm border border-emerald-100">
                        <h3 class="font-bold text-slate-800 mb-6 flex items-center gap-2">
                            <i class="fas fa-history text-blue-600"></i> Actividades Recientes
                        </h3>
                        <div class="space-y-4">
                            <!-- Si hay mensajes -->
                            <c:forEach var="msg" items="${mensajes}" begin="0" end="2">
                                <div class="flex items-start space-x-3 p-3 bg-slate-50 rounded-lg border border-slate-100">
                                    <div class="flex-shrink-0 mt-1">
                                        <div class="h-2 w-2 bg-blue-500 rounded-full"></div>
                                    </div>
                                    <div class="min-w-0 flex-1">
                                        <p class="text-sm font-medium text-slate-900">Mensaje del Asesor</p>
                                        <p class="text-xs text-slate-500 line-clamp-1">${msg.cuerpo}</p>
                                        <p class="text-[10px] text-slate-400 mt-1">${msg.fechaHoraEnvio}</p>
                                    </div>
                                </div>
                            </c:forEach>
                            <c:if test="${empty mensajes}">
                                <p class="text-center text-xs text-slate-400 py-4">No hay actividad reciente.</p>
                            </c:if>
                        </div>
                    </div>
                </div>

            </div>

            <!-- SECCIÓN TESIS (Detalle) -->
            <div id="seccion-mitesis" class="seccion-app hidden-section">
                <!-- Reutilizamos la misma vista detallada si quieres, o solo el dashboard -->
                <div class="bg-white p-8 rounded-xl shadow-sm border border-emerald-100">
                    <h2 class="text-2xl font-bold text-slate-800 mb-6">Detalle del Proyecto</h2>
                    <!-- Aquí iría el visor PDF o historial completo -->
                    <p class="text-slate-500">Vista detallada del proyecto (En construcción...)</p>
                </div>
            </div>

            <!-- SECCIÓN DOCUMENTOS -->
            <div id="seccion-documentos" class="seccion-app hidden-section space-y-6">
                <div class="bg-white p-8 rounded-xl shadow-sm border border-emerald-100">
                    <h2 class="text-xl font-bold text-slate-800 mb-6">Documentos Administrativos</h2>
                    <div class="space-y-3">
                        <div class="flex items-center justify-between p-4 bg-slate-50 border border-slate-200 rounded-lg">
                            <div class="flex items-center gap-3">
                                <div class="w-8 h-8 rounded-full bg-slate-200 flex items-center justify-center text-slate-600"><i class="fas fa-file-pdf"></i></div>
                                <span class="text-sm font-bold text-slate-700">Cargo de Recepción</span>
                            </div>
                            <button class="text-xs bg-slate-200 text-slate-600 px-3 py-1 rounded font-bold hover:bg-slate-300">Descargar</button>
                        </div>
                        
                        <c:if test="${miTesis.estado == 'Aprobado' || miTesis.estado == 'Apto Sustentación'}">
                             <div class="flex items-center justify-between p-4 bg-emerald-50 border border-emerald-200 rounded-lg">
                                <div class="flex items-center gap-3">
                                    <div class="w-8 h-8 rounded-full bg-emerald-100 flex items-center justify-center text-emerald-600"><i class="fas fa-check-circle"></i></div>
                                    <span class="text-sm font-bold text-emerald-800">Informe de Conformidad (Anexo 4)</span>
                                </div>
                                <button class="text-xs bg-emerald-600 text-white px-3 py-1 rounded font-bold hover:bg-emerald-700">Descargar</button>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>

        </div>
    </main>

    <!-- Modal Subir Tesis -->
    <div id="modal-subir-tesis" class="fixed inset-0 bg-slate-900/60 z-50 hidden flex items-center justify-center p-4 backdrop-blur-sm">
        <div class="bg-white rounded-xl shadow-2xl p-8 w-full max-w-md animate-fade-in border-t-4 border-emerald-500">
            <h3 class="text-xl font-bold text-slate-800 mb-2">Enviar Nueva Versión</h3>
            <p class="text-sm text-slate-500 mb-6">Sube tu archivo PDF actualizado para revisión.</p>
            
            <form action="${pageContext.request.contextPath}/dashboardEstudiante" method="POST" enctype="multipart/form-data" class="space-y-4">
                <input type="hidden" name="accion" value="subirArchivo">
                
                <div>
                    <label class="block text-xs font-bold text-slate-500 uppercase mb-1">Tipo de Entrega</label>
                    <select name="tipoEntrega" class="w-full p-2 border rounded text-sm bg-slate-50 outline-none focus:border-emerald-500">
                        <option value="Propuesta Inicial">Propuesta Inicial</option>
                        <option value="Correcciones">Subsanación de Observaciones</option>
                        <option value="Documento Final">Versión Final para Sustentación</option>
                    </select>
                </div>
                
                <div>
                    <label class="block text-xs font-bold text-slate-500 uppercase mb-1">Archivo (PDF)</label>
                    <input type="file" name="archivo" accept=".pdf" required class="w-full text-sm text-slate-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-xs file:font-semibold file:bg-emerald-50 file:text-emerald-700 hover:file:bg-emerald-100">
                </div>

                <div>
                    <label class="block text-xs font-bold text-slate-500 uppercase mb-1">Comentarios</label>
                    <textarea name="comentarios" rows="3" class="w-full p-2 border rounded text-sm bg-slate-50 outline-none focus:border-emerald-500" placeholder="Opcional..."></textarea>
                </div>
                
                <div class="flex justify-end gap-3 mt-6 pt-4 border-t border-slate-100">
                    <button type="button" onclick="document.getElementById('modal-subir-tesis').classList.add('hidden')" class="px-4 py-2 text-slate-500 text-sm font-bold hover:bg-slate-100 rounded-lg">Cancelar</button>
                    <button type="submit" class="bg-emerald-600 hover:bg-emerald-700 text-white px-6 py-2 rounded-lg text-sm font-bold shadow-lg">Enviar Tesis</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function mostrarSeccion(id) {
            document.querySelectorAll('.seccion-app').forEach(el => el.classList.add('hidden-section'));
            document.querySelectorAll('.sidebar-link').forEach(el => {
                el.classList.remove('sidebar-active');
                el.classList.add('text-slate-300', 'hover:bg-white/5');
            });
            
            // Si es dashboard, mostramos dashboard, si no, mostramos un placeholder por ahora o la sección correspondiente
            if(id === 'dashboard') {
                document.getElementById('seccion-dashboard').classList.remove('hidden-section');
            } else if (id === 'mitesis') {
                // Podrías redirigir o mostrar contenido específico
                document.getElementById('seccion-dashboard').classList.remove('hidden-section'); // Reutilizamos dashboard por ahora para "Mi Tesis"
            } else if (id === 'documentos') {
                document.getElementById('seccion-documentos').classList.remove('hidden-section');
            }
            
            const navBtn = document.getElementById('nav-' + id);
            if(navBtn) {
                navBtn.classList.add('sidebar-active');
                navBtn.classList.remove('text-slate-300', 'hover:bg-white/5');
            }
        }
        
        // Auto-cierre de alertas (si las hubiera)
        setTimeout(() => {
            const alertas = document.querySelectorAll('.fade-in.border-l-4');
            alertas.forEach(a => a.style.display = 'none');
        }, 5000);
        
        // Inicializar
        document.addEventListener('DOMContentLoaded', () => {
            mostrarSeccion('dashboard');
        });
    </script>
</body>
</html>