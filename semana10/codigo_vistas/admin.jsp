<%-- 
    Document   : admin
    Versión    : Diseño Azul Oscuro + Funcionalidad Completa de Registro
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="es" class="h-full">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Administrativo</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        body { font-family: 'Inter', sans-serif; }
        .hidden-section { display: none !important; }
        /* Sidebar Azul Oscuro */
        .bg-sidebar { background-color: #0f172a; } 
        .sidebar-active {
            background-color: rgba(255, 255, 255, 0.1);
            border-left: 4px solid #3b82f6; /* Borde azul brillante */
            color: white !important;
        }
    </style>
</head>
<body class="h-full bg-slate-50 flex overflow-hidden">
    
    <!-- Sidebar Azul Oscuro -->
    <aside class="w-64 bg-sidebar text-white flex flex-col shadow-2xl z-20 shrink-0 h-full">
        <div class="p-6 flex flex-col items-center border-b border-white/10">
            <div class="w-16 h-16 rounded-full bg-blue-600 flex items-center justify-center mb-3 shadow-lg overflow-hidden border-2 border-white/20">
                 <img src="${sessionScope.usuario.fotoPerfilUrl}" class="w-full h-full object-cover" onerror="this.src='https://placehold.co/100x100/2563eb/FFFFFF?text=A';">
            </div>
            <h3 class="font-bold text-sm text-center">${sessionScope.usuario.nombre} ${sessionScope.usuario.apellido}</h3>
            <p class="text-blue-200 text-xs text-center uppercase tracking-wide mt-1">Administrador</p>
            <div class="mt-3 px-3 py-1 rounded-full bg-green-500/20 text-green-400 text-[10px] font-bold border border-green-500/30 flex items-center gap-2">
                <span class="w-1.5 h-1.5 bg-green-400 rounded-full animate-pulse"></span> SISTEMA ACTIVO
            </div>
        </div>

        <nav class="flex-1 py-4 px-3 space-y-1 overflow-y-auto">
            <button onclick="mostrarSeccion('dashboard')" id="nav-dashboard" class="w-full text-left px-4 py-3 rounded-lg text-sm font-medium text-slate-300 hover:bg-white/5 hover:text-white transition flex items-center gap-3 sidebar-active">
                <i class="fas fa-th-large w-5"></i> Dashboard
            </button>
            <button onclick="mostrarSeccion('usuarios')" id="nav-usuarios" class="w-full text-left px-4 py-3 rounded-lg text-sm font-medium text-slate-300 hover:bg-white/5 hover:text-white transition flex items-center gap-3">
                <i class="fas fa-users w-5"></i> Usuarios
            </button>
            <button onclick="mostrarSeccion('tesis')" id="nav-tesis" class="w-full text-left px-4 py-3 rounded-lg text-sm font-medium text-slate-300 hover:bg-white/5 hover:text-white transition flex items-center gap-3">
                <i class="fas fa-file-alt w-5"></i> Tesis
            </button>
            <button onclick="mostrarSeccion('asignacion')" id="nav-asignacion" class="w-full text-left px-4 py-3 rounded-lg text-sm font-medium text-slate-300 hover:bg-white/5 hover:text-white transition flex items-center gap-3">
                <i class="fas fa-user-check w-5"></i> Asignación
            </button>
        </nav>
        
        <div class="p-4 border-t border-white/10">
            <a href="${pageContext.request.contextPath}/LogoutControlador" class="w-full bg-rose-600/20 hover:bg-rose-600 text-rose-300 hover:text-white px-3 py-2 rounded-lg font-bold transition flex items-center justify-center gap-2 text-xs border border-rose-500/30">
                <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
            </a>
        </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 flex flex-col min-h-0 bg-slate-50">
        <!-- Header -->
        <header class="bg-white shadow-sm border-b border-slate-200 px-8 py-4 flex justify-between items-center shrink-0">
            <div>
                <h1 class="text-xl font-bold text-slate-800">Panel Administrativo</h1>
                <p class="text-xs text-slate-500">Sistema de Gestión Académica</p>
            </div>
            <div class="flex items-center gap-3">
                <div class="relative">
                    <span class="absolute top-0 right-0 w-2 h-2 bg-red-500 rounded-full border border-white"></span>
                    <i class="fas fa-bell text-slate-400 text-xl cursor-pointer hover:text-slate-600 transition"></i>
                </div>
            </div>
        </header>

        <div class="flex-1 overflow-auto p-8">
            
            <!-- SECCIÓN 1: DASHBOARD (Vista General) -->
            <div id="seccion-dashboard" class="seccion-app space-y-8">
                <h2 class="text-lg font-bold text-slate-700">Vista General</h2>
                
                <!-- KPIs -->
                <div class="grid grid-cols-1 md:grid-cols-4 gap-6">
                    <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-100 flex items-center gap-4 hover:shadow-md transition">
                        <div class="p-3 bg-red-50 text-red-500 rounded-lg"><i class="fas fa-users text-2xl"></i></div>
                        <div>
                            <p class="text-xs font-bold text-slate-400 uppercase">Estudiantes</p>
                            <h3 class="text-2xl font-bold text-slate-800">${conteoEstudiantes}</h3>
                        </div>
                    </div>
                    <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-100 flex items-center gap-4 hover:shadow-md transition">
                        <div class="p-3 bg-blue-50 text-blue-500 rounded-lg"><i class="fas fa-chalkboard-teacher text-2xl"></i></div>
                        <div>
                            <p class="text-xs font-bold text-slate-400 uppercase">Docentes</p>
                            <h3 class="text-2xl font-bold text-slate-800">${conteoDocentes}</h3>
                        </div>
                    </div>
                    <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-100 flex items-center gap-4 hover:shadow-md transition">
                        <div class="p-3 bg-yellow-50 text-yellow-500 rounded-lg"><i class="fas fa-clock text-2xl"></i></div>
                        <div>
                            <p class="text-xs font-bold text-slate-400 uppercase">Tesis Activas</p>
                            <h3 class="text-2xl font-bold text-slate-800">${totalTesis}</h3>
                        </div>
                    </div>
                    <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-100 flex items-center gap-4 hover:shadow-md transition">
                        <div class="p-3 bg-green-50 text-green-500 rounded-lg"><i class="fas fa-check-circle text-2xl"></i></div>
                        <div>
                            <p class="text-xs font-bold text-slate-400 uppercase">Titulados</p>
                            <h3 class="text-2xl font-bold text-slate-800">${conteoAprobadas}</h3>
                        </div>
                    </div>
                </div>

                <h2 class="text-lg font-bold text-slate-700 mt-8">Accesos Directos</h2>
                <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <button onclick="mostrarSeccion('usuarios')" class="bg-white p-6 rounded-xl shadow-sm border border-slate-100 text-left hover:shadow-md transition group border-l-4 border-l-red-500">
                        <i class="fas fa-users-cog text-red-500 text-xl mb-3 group-hover:scale-110 transition-transform"></i>
                        <h3 class="font-bold text-slate-800">Gestionar Usuarios</h3>
                        <p class="text-xs text-slate-400 mt-1">Altas, bajas y modificaciones</p>
                    </button>
                    <button onclick="document.getElementById('modal-subir-tesis').classList.remove('hidden')" class="bg-white p-6 rounded-xl shadow-sm border border-slate-100 text-left hover:shadow-md transition group border-l-4 border-l-yellow-500">
                        <i class="fas fa-file-medical text-yellow-500 text-xl mb-3 group-hover:scale-110 transition-transform"></i>
                        <h3 class="font-bold text-slate-800">Nueva Tesis</h3>
                        <p class="text-xs text-slate-400 mt-1">Registrar y asignar proyecto</p>
                    </button>
                    <button onclick="mostrarSeccion('usuarios')" class="bg-white p-6 rounded-xl shadow-sm border border-slate-100 text-left hover:shadow-md transition group border-l-4 border-l-blue-500">
                        <i class="fas fa-id-card text-blue-500 text-xl mb-3 group-hover:scale-110 transition-transform"></i>
                        <h3 class="font-bold text-slate-800">Gestionar Docentes</h3>
                        <p class="text-xs text-slate-400 mt-1">Administrar plana docente</p>
                    </button>
                </div>
            </div>

            <!-- SECCIÓN 2: USUARIOS -->
            <div id="seccion-usuarios" class="seccion-app hidden-section space-y-6 animate-fade-in">
                <div class="bg-white rounded-xl shadow-sm p-6 border border-slate-200">
                    <div class="flex justify-between items-center mb-6">
                        <h3 class="font-bold text-slate-800">Directorio de Usuarios</h3>
                        <button onclick="document.getElementById('modal-nuevo-usuario').classList.remove('hidden')" class="bg-blue-600 text-white px-4 py-2 rounded-lg text-sm font-bold hover:bg-blue-700 flex items-center gap-2 shadow-md">
                            <i class="fas fa-plus"></i> Nuevo
                        </button>
                    </div>
                    <div class="overflow-x-auto">
                        <table class="w-full text-sm text-left border-collapse">
                            <thead class="bg-slate-50 text-slate-500 uppercase text-xs font-bold border-b border-slate-200">
                                <tr><th class="px-4 py-3">Nombre</th><th class="px-4 py-3">Rol</th><th class="px-4 py-3">Email</th></tr>
                            </thead>
                            <tbody class="divide-y divide-slate-100">
                                <c:forEach var="usr" items="${listaUsuarios}">
                                    <tr class="hover:bg-slate-50">
                                        <td class="px-4 py-3 font-medium text-slate-800 flex items-center gap-2">
                                            <div class="w-8 h-8 rounded-full bg-slate-200 text-slate-600 flex items-center justify-center text-xs font-bold">${fn:substring(usr.nombre, 0, 1)}</div>
                                            ${usr.nombre} ${usr.apellido}
                                        </td>
                                        <td class="px-4 py-3"><span class="px-2 py-1 rounded text-xs font-bold bg-slate-100 text-slate-600 uppercase border border-slate-200">${usr.rol}</span></td>
                                        <td class="px-4 py-3 text-slate-500">${usr.email}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- SECCIÓN 3: TESIS (Lista) -->
            <div id="seccion-tesis" class="seccion-app hidden-section space-y-6 animate-fade-in">
                <div class="bg-white rounded-xl shadow-sm p-6 border border-slate-200">
                    <h3 class="font-bold text-slate-800 mb-4 text-lg">Listado General de Tesis</h3>
                    <div class="overflow-x-auto">
                        <table class="w-full text-sm text-left border-collapse">
                            <thead class="bg-slate-50 text-slate-500 font-bold text-xs uppercase border-b border-slate-200">
                                <tr>
                                    <th class="px-4 py-3">Título</th>
                                    <th class="px-4 py-3">Autor</th>
                                    <th class="px-4 py-3">Asesor</th>
                                    <th class="px-4 py-3">Estado</th>
                                    <th class="px-4 py-3 text-right">Acción</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-slate-100">
                                <c:forEach var="t" items="${listaTesisDetallada}">
                                    <tr class="hover:bg-slate-50">
                                        <td class="px-4 py-3 font-medium text-slate-800 max-w-xs truncate" title="${t.titulo}">${t.titulo}</td>
                                        <td class="px-4 py-3 text-slate-600">${t.nombreAutor}</td>
                                        <td class="px-4 py-3 text-slate-600">${t.nombreRevisor != null ? t.nombreRevisor : '-'}</td>
                                        <td class="px-4 py-3">
                                            <span class="px-2 py-0.5 rounded-full bg-blue-50 text-blue-600 text-xs font-bold border border-blue-100">${t.estado}</span>
                                        </td>
                                        <td class="px-4 py-3 text-right"><a href="#" class="text-blue-600 hover:underline font-bold text-xs">Ver Detalle</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- SECCIÓN 4: ASIGNACIÓN (Asesores y Jurados) -->
            <div id="seccion-asignacion" class="seccion-app hidden-section space-y-8 animate-fade-in">
                
                <!-- Asignar Asesor -->
                <div class="bg-white p-6 rounded-xl shadow-sm border border-yellow-200">
                    <h3 class="font-bold text-slate-800 mb-4 flex items-center gap-2 pb-2 border-b border-slate-100">
                        <span class="w-8 h-8 rounded bg-yellow-100 text-yellow-600 flex items-center justify-center"><i class="fas fa-user-tie"></i></span>
                        Pendientes de Asesor
                    </h3>
                    <div class="space-y-3">
                         <c:forEach var="t" items="${tesisSinAsignar}">
                            <form action="${pageContext.request.contextPath}/dashboardAdmin" method="POST" class="bg-slate-50 p-4 rounded-lg border border-slate-200 flex flex-col md:flex-row gap-4 items-center transition hover:border-yellow-300">
                                <input type="hidden" name="accion" value="asignarRevision">
                                <input type="hidden" name="idTesis" value="${t.idTesis}">
                                <div class="flex-1">
                                    <div class="flex items-center gap-2 mb-1">
                                        <span class="text-[10px] font-bold bg-amber-100 text-amber-700 px-2 py-0.5 rounded uppercase">Nuevo</span>
                                        <h4 class="font-bold text-sm text-slate-800 line-clamp-1" title="${t.titulo}">${t.titulo}</h4>
                                    </div>
                                    <p class="text-xs text-slate-500">Autor: <strong>${t.nombreAutor}</strong></p>
                                </div>
                                <div class="flex gap-2 w-full md:w-auto">
                                    <select name="idDocente" required class="bg-white border border-slate-300 text-xs rounded p-2 w-48 focus:border-yellow-500 outline-none">
                                        <option value="">Seleccionar Asesor...</option>
                                        <c:forEach var="d" items="${listaDocentes}"><option value="${d.idUsuario}">${d.nombre} ${d.apellido}</option></c:forEach>
                                    </select>
                                    <button class="bg-yellow-500 hover:bg-yellow-600 text-white px-4 py-2 rounded text-xs font-bold shadow-sm transition">Asignar</button>
                                </div>
                            </form>
                        </c:forEach>
                        <c:if test="${empty tesisSinAsignar}"><div class="p-8 text-center border-2 border-dashed border-slate-200 rounded-lg text-slate-400 text-sm">No hay tesis pendientes de asesor.</div></c:if>
                    </div>
                </div>

                <!-- Asignar Jurados -->
                <div class="bg-white p-6 rounded-xl shadow-sm border border-purple-200">
                    <h3 class="font-bold text-slate-800 mb-4 flex items-center gap-2 pb-2 border-b border-slate-100">
                        <span class="w-8 h-8 rounded bg-purple-100 text-purple-600 flex items-center justify-center"><i class="fas fa-gavel"></i></span>
                        Designar Jurado (3 Miembros)
                    </h3>
                    <div class="space-y-4">
                        <c:forEach var="t" items="${tesisParaJurado}">
                             <form action="${pageContext.request.contextPath}/dashboardAdmin" method="POST" class="bg-slate-50 p-4 rounded-lg border border-slate-200 hover:border-purple-300 transition">
                                <input type="hidden" name="accion" value="asignarJurados">
                                <input type="hidden" name="idTesis" value="${t.idTesis}">
                                
                                <div class="mb-3 pb-2 border-b border-slate-200">
                                    <h4 class="font-bold text-sm text-slate-800">${t.titulo}</h4>
                                    <p class="text-xs text-slate-500">Aprobado por: ${t.nombreRevisor}</p>
                                </div>
                                
                                <div class="grid grid-cols-1 md:grid-cols-4 gap-2 mb-3">
                                    <select name="presidente" required class="text-xs p-2 border rounded bg-white"><option value="">Presidente...</option><c:forEach var="d" items="${listaDocentes}"><option value="${d.codigo}">${d.nombre} ${d.apellido}</option></c:forEach></select>
                                    <select name="secretario" required class="text-xs p-2 border rounded bg-white"><option value="">Secretario...</option><c:forEach var="d" items="${listaDocentes}"><option value="${d.codigo}">${d.nombre} ${d.apellido}</option></c:forEach></select>
                                    <select name="vocal" required class="text-xs p-2 border rounded bg-white"><option value="">Vocal...</option><c:forEach var="d" items="${listaDocentes}"><option value="${d.codigo}">${d.nombre} ${d.apellido}</option></c:forEach></select>
                                    <select name="suplente" required class="text-xs p-2 border rounded bg-white"><option value="">Suplente...</option><c:forEach var="d" items="${listaDocentes}"><option value="${d.codigo}">${d.nombre} ${d.apellido}</option></c:forEach></select>
                                </div>
                                <div class="flex gap-2">
                                    <input type="datetime-local" name="fechaHora" required class="text-xs p-2 border rounded w-1/2 bg-white">
                                    <input type="text" name="lugar" placeholder="Lugar" required class="text-xs p-2 border rounded w-1/2 bg-white">
                                </div>
                                <button class="w-full mt-3 bg-purple-600 hover:bg-purple-700 text-white py-2 rounded text-xs font-bold shadow-md">Emitir Resolución</button>
                            </form>
                        </c:forEach>
                        <c:if test="${empty tesisParaJurado}"><div class="p-8 text-center border-2 border-dashed border-slate-200 rounded-lg text-slate-400 text-sm">No hay tesis listas para sustentación.</div></c:if>
                    </div>
                </div>

            </div>

        </div>
    </main>

    <!-- Modal Nuevo Usuario -->
    <div id="modal-nuevo-usuario" class="fixed inset-0 bg-slate-900/50 z-50 hidden flex items-center justify-center p-4 backdrop-blur-sm">
        <div class="bg-white rounded-lg shadow-xl p-6 w-full max-w-md animate-fade-in">
            <h3 class="font-bold text-lg mb-4 text-slate-800">Registrar Usuario</h3>
            <form action="${pageContext.request.contextPath}/dashboardAdmin" method="POST" class="space-y-3">
                <input type="hidden" name="accion" value="crearUsuario">
                <input name="nombre" placeholder="Nombre" required class="w-full p-2 border rounded text-sm bg-slate-50 focus:bg-white transition">
                <input name="apellido" placeholder="Apellidos" required class="w-full p-2 border rounded text-sm bg-slate-50 focus:bg-white transition">
                <input name="email" type="email" placeholder="Email" required class="w-full p-2 border rounded text-sm bg-slate-50 focus:bg-white transition">
                <select name="rol" class="w-full p-2 border rounded text-sm bg-slate-50 focus:bg-white transition">
                    <option value="estudiante">Estudiante</option>
                    <option value="docente">Docente</option>
                    <option value="admin">Admin</option>
                </select>
                <input name="password" type="password" placeholder="Contraseña" required class="w-full p-2 border rounded text-sm bg-slate-50 focus:bg-white transition">
                <div class="flex justify-end gap-2 mt-4">
                    <button type="button" onclick="document.getElementById('modal-nuevo-usuario').classList.add('hidden')" class="px-4 py-2 text-slate-500 text-sm font-bold hover:bg-slate-100 rounded">Cancelar</button>
                    <button class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded text-sm font-bold shadow-md">Guardar</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Modal Subir Tesis -->
    <div id="modal-subir-tesis" class="fixed inset-0 bg-slate-900/50 z-50 hidden flex items-center justify-center p-4 backdrop-blur-sm">
        <div class="bg-white rounded-lg shadow-xl p-6 w-full max-w-md animate-fade-in border-t-4 border-orange-500">
            <h3 class="font-bold text-lg mb-4 text-slate-800">Nueva Tesis</h3>
            <p class="text-xs text-slate-500 mb-4">Registro administrativo de expediente.</p>
            
            <form action="${pageContext.request.contextPath}/dashboardAdmin" method="POST" enctype="multipart/form-data" class="space-y-4">
                <input type="hidden" name="accion" value="subirTesisAdmin">
                
                <div>
                    <label class="block text-xs font-bold text-slate-500 uppercase mb-1">Título del Proyecto</label>
                    <input name="titulo" placeholder="Ingrese el título completo" required class="w-full p-2 border rounded text-sm bg-slate-50 focus:border-orange-500 focus:bg-white outline-none transition">
                </div>
                
                <div>
                    <label class="block text-xs font-bold text-slate-500 uppercase mb-1">Estudiante</label>
                    <select name="idEstudiante" class="w-full p-2 border rounded text-sm bg-slate-50 focus:border-orange-500 focus:bg-white outline-none transition">
                        <c:forEach var="est" items="${listaUsuarios}">
                            <c:if test="${est.rol == 'estudiante'}"><option value="${est.idUsuario}">${est.nombre} ${est.apellido}</option></c:if>
                        </c:forEach>
                    </select>
                </div>
                
                <div>
                    <label class="block text-xs font-bold text-slate-500 uppercase mb-1">Archivo PDF</label>
                    <input type="file" name="archivo" accept=".pdf" required class="w-full text-sm border rounded p-1 file:mr-4 file:py-1 file:px-4 file:rounded-full file:border-0 file:text-xs file:font-semibold file:bg-orange-50 file:text-orange-700 hover:file:bg-orange-100">
                </div>
                
                <div class="flex justify-end gap-2 mt-6 pt-4 border-t border-slate-100">
                    <button type="button" onclick="document.getElementById('modal-subir-tesis').classList.add('hidden')" class="px-4 py-2 text-slate-500 text-sm font-bold hover:bg-slate-100 rounded transition">Cancelar</button>
                    <button type="submit" class="bg-orange-600 hover:bg-orange-700 text-white px-6 py-2 rounded text-sm font-bold shadow-lg transform active:scale-95 transition">Registrar Expediente</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function mostrarSeccion(id) {
            // Ocultar todas las secciones
            document.querySelectorAll('.seccion-app').forEach(el => el.classList.add('hidden-section'));
            // Quitar clase activa de todos los links
            document.querySelectorAll('.sidebar-link').forEach(el => {
                el.classList.remove('sidebar-active');
                el.classList.add('text-slate-300', 'hover:bg-white/5');
            });
            
            // Mostrar sección
            const target = document.getElementById('seccion-' + id);
            if(target) target.classList.remove('hidden-section');
            
            // Activar link
            const navBtn = document.getElementById('nav-' + id);
            if(navBtn) {
                navBtn.classList.add('sidebar-active');
                navBtn.classList.remove('text-slate-300', 'hover:bg-white/5');
            }
        }
        function abrirModalUsuario() { document.getElementById('modal-nuevo-usuario').classList.remove('hidden'); }
    </script>
</body>
</html>