<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Docente</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        body { font-family: 'Inter', sans-serif; }
        .hidden-section { display: none !important; }
        
        /* Sidebar Azul Oscuro Fijo */
        .bg-sidebar { background-color: #0f172a; } 
        
        .sidebar-active {
            background-color: rgba(255, 255, 255, 0.1);
            border-left: 4px solid #3b82f6; /* Borde azul brillante */
            color: white !important;
        }
        
        /* Asegurar altura completa y scroll interno */
        html, body { height: 100%; margin: 0; overflow: hidden; }
        
        /* Ocultar input radio nativo */
        .sr-only-input { position: absolute; opacity: 0; cursor: pointer; height: 0; width: 0; }

        /* Scroll personalizado */
        .custom-scroll::-webkit-scrollbar { width: 6px; }
        .custom-scroll::-webkit-scrollbar-track { background: transparent; }
        .custom-scroll::-webkit-scrollbar-thumb { background: #cbd5e1; border-radius: 3px; }
    </style>
</head>
<body class="bg-slate-50 flex h-screen w-full">
    
    <!-- SIDEBAR (Izquierda - Fija) -->
    <aside class="w-64 bg-sidebar text-white flex flex-col shadow-2xl z-20 shrink-0 h-full">
        <div class="p-6 flex flex-col items-center border-b border-white/10">
            <div class="w-20 h-20 rounded-full bg-blue-600 flex items-center justify-center mb-3 shadow-lg overflow-hidden border-4 border-blue-500/30">
                 <img src="${sessionScope.usuario.fotoPerfilUrl}" class="w-full h-full object-cover" onerror="this.src='https://ui-avatars.com/api/?name=${sessionScope.usuario.nombre}&background=2563eb&color=fff&size=128';">
            </div>
            <h3 class="font-bold text-sm text-center text-white">${sessionScope.usuario.nombre} ${sessionScope.usuario.apellido}</h3>
            <p class="text-blue-200 text-xs text-center uppercase tracking-wide mt-1">Docente</p>
            <div class="mt-3 px-3 py-1 rounded-full bg-green-500/20 text-green-400 text-[10px] font-bold border border-green-500/30 flex items-center gap-2">
                <span class="w-1.5 h-1.5 bg-green-400 rounded-full animate-pulse"></span> EN LÍNEA
            </div>
        </div>

        <!-- Menú Scrollable -->
        <nav class="flex-1 py-4 px-3 space-y-1 overflow-y-auto custom-scroll">
            <button onclick="mostrarSeccion('dashboard')" id="nav-dashboard" class="sidebar-link w-full text-left px-4 py-3 rounded-lg text-sm font-medium text-slate-300 hover:bg-white/5 hover:text-white transition flex items-center gap-3 sidebar-active">
                <i class="fas fa-home w-5 text-center"></i> Dashboard
            </button>
            <button onclick="mostrarSeccion('asesor')" id="nav-asesor" class="sidebar-link w-full text-left px-4 py-3 rounded-lg text-sm font-medium text-slate-300 hover:bg-white/5 hover:text-white transition flex items-center gap-3">
                <i class="fas fa-user-tie w-5 text-center"></i> Mis Asesorados
            </button>
            <button onclick="mostrarSeccion('jurado')" id="nav-jurado" class="sidebar-link w-full text-left px-4 py-3 rounded-lg text-sm font-medium text-slate-300 hover:bg-white/5 hover:text-white transition flex items-center gap-3">
                <i class="fas fa-gavel w-5 text-center"></i> Jurado de Tesis
            </button>
            <button onclick="mostrarSeccion('historial')" id="nav-historial" class="sidebar-link w-full text-left px-4 py-3 rounded-lg text-sm font-medium text-slate-300 hover:bg-white/5 hover:text-white transition flex items-center gap-3">
                <i class="fas fa-history w-5 text-center"></i> Historial
            </button>
        </nav>
        
        <div class="p-4 border-t border-white/10">
            <a href="${pageContext.request.contextPath}/LogoutControlador" class="w-full bg-blue-600/20 hover:bg-blue-600 text-blue-200 hover:text-white px-3 py-2 rounded-lg font-bold transition flex items-center justify-center gap-2 text-xs border border-blue-500/30">
                <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
            </a>
        </div>
    </aside>

    <!-- CONTENIDO PRINCIPAL (Derecha - Scrollable) -->
    <main class="flex-1 flex flex-col min-h-0 bg-slate-50 relative h-full">
        <!-- Header -->
        <header class="bg-white shadow-sm border-b border-slate-200 px-8 py-4 flex justify-between items-center shrink-0 h-16">
            <div>
                <h1 class="text-xl font-bold text-slate-800">Portal del Docente</h1>
                <p class="text-xs text-slate-500 hidden sm:block">Gestión de Tesis y Evaluaciones</p>
            </div>
            <div class="flex items-center gap-4">
                <span class="text-xs font-bold text-slate-400 uppercase tracking-wider hidden sm:block">${sessionScope.config.periodoAcademico}</span>
                <div class="relative cursor-pointer">
                    <i class="fas fa-bell text-slate-400 text-xl hover:text-slate-600 transition"></i>
                    <span class="absolute top-0 right-0 w-2 h-2 bg-blue-500 rounded-full border border-white"></span>
                </div>
            </div>
        </header>

        <!-- Área de Contenido con Scroll -->
        <div class="flex-1 overflow-auto p-6 md:p-8 custom-scroll">
            
            <!-- 1. DASHBOARD -->
            <div id="seccion-dashboard" class="seccion-app space-y-8">
                <!-- Banner -->
                <div class="bg-gradient-to-r from-blue-600 to-indigo-600 rounded-2xl p-8 text-white shadow-lg relative overflow-hidden">
                    <div class="relative z-10">
                        <h2 class="text-3xl font-bold mb-2">¡Bienvenido, ${fn:split(sessionScope.usuario.nombre, ' ')[0]}!</h2>
                        <p class="text-blue-100 text-lg">Tienes <strong>${tesisPendientesCount}</strong> revisiones pendientes esta semana.</p>
                    </div>
                    <i class="fas fa-chalkboard-teacher absolute -bottom-6 -right-6 text-9xl text-white opacity-10"></i>
                </div>

                <!-- Stats Cards -->
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                    <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
                        <p class="text-xs font-bold text-blue-500 uppercase mb-1">Asignadas</p>
                        <h3 class="text-3xl font-extrabold text-slate-800">${fn:length(tesisAsignadas)}</h3>
                    </div>
                    <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
                        <p class="text-xs font-bold text-yellow-500 uppercase mb-1">Pendientes</p>
                        <h3 class="text-3xl font-extrabold text-slate-800">${tesisPendientesCount}</h3>
                    </div>
                    <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
                        <p class="text-xs font-bold text-green-500 uppercase mb-1">Aprobadas</p>
                        <h3 class="text-3xl font-extrabold text-slate-800">${fn:length(tesisRevisadas)}</h3>
                    </div>
                     <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
                        <p class="text-xs font-bold text-purple-500 uppercase mb-1">Jurado</p>
                        <h3 class="text-3xl font-extrabold text-slate-800">${fn:length(tesisJurado)}</h3>
                    </div>
                </div>

                <!-- Lista Pendientes -->
                <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-6">
                    <h3 class="font-bold text-slate-800 mb-6 flex items-center gap-2"><i class="fas fa-clock text-yellow-500"></i> Pendientes de Revisión</h3>
                    <div class="space-y-4">
                        <c:forEach var="t" items="${tesisAsignadas}">
                            <c:if test="${t.estado != 'Aprobado' && t.estado != 'Rechazado' && t.estado != 'Aprobado por Asesor'}">
                                <div class="border border-yellow-100 bg-yellow-50/30 rounded-lg p-4 flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 hover:shadow-md transition">
                                    <div>
                                        <h4 class="font-bold text-slate-800 text-sm mb-1">${t.titulo}</h4>
                                        <p class="text-xs text-slate-500">Estudiante: <span class="font-semibold">${t.nombreAutor}</span></p>
                                    </div>
                                    <button onclick="abrirEvaluacion('asesor', '${t.idTesis}', '${t.titulo}')" class="bg-blue-600 hover:bg-blue-700 text-white px-5 py-2 rounded-lg text-xs font-bold shadow-sm transition whitespace-nowrap">
                                        Revisar
                                    </button>
                                </div>
                            </c:if>
                        </c:forEach>
                        <c:if test="${tesisPendientesCount == 0}">
                             <div class="text-center py-8 text-slate-400 text-sm italic border-2 border-dashed border-slate-100 rounded-lg">No hay revisiones pendientes.</div>
                        </c:if>
                    </div>
                </div>
            </div>

            <!-- 2. SECCIÓN ASESOR -->
            <div id="seccion-asesor" class="seccion-app hidden-section space-y-6 animate-fade-in">
                 <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
                    <h3 class="font-bold text-slate-800 mb-6 border-b border-slate-100 pb-4">Mis Asesorados</h3>
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <c:forEach var="t" items="${tesisAsignadas}">
                            <div class="bg-white border border-slate-200 rounded-xl p-5 hover:border-blue-400 transition shadow-sm group">
                                <div class="flex justify-between mb-3">
                                    <span class="bg-blue-50 text-blue-700 text-[10px] font-bold px-2 py-1 rounded uppercase">Asesor</span>
                                    <span class="text-xs text-slate-400">ID: ${t.idTesis}</span>
                                </div>
                                <h4 class="font-bold text-sm text-slate-800 mb-2 line-clamp-2 h-10 group-hover:text-blue-700 transition">${t.titulo}</h4>
                                <p class="text-xs text-slate-500 mb-4">Estudiante: <strong>${t.nombreAutor}</strong></p>
                                <button onclick="abrirEvaluacion('asesor', '${t.idTesis}', '${t.titulo}')" class="w-full bg-blue-600 text-white py-2.5 rounded-lg text-xs font-bold hover:bg-blue-700 transition shadow-sm">
                                    Evaluar (Anexo 4)
                                </button>
                            </div>
                        </c:forEach>
                        <c:if test="${empty tesisAsignadas}">
                            <p class="col-span-2 text-center text-slate-400 text-sm py-10">No tienes tesis asignadas actualmente.</p>
                        </c:if>
                    </div>
                 </div>
            </div>
            
            <!-- 3. SECCIÓN JURADO -->
            <div id="seccion-jurado" class="seccion-app hidden-section space-y-6 animate-fade-in">
                 <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
                    <h3 class="font-bold text-slate-800 mb-6 border-b border-slate-100 pb-4">Designaciones como Jurado</h3>
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <c:forEach var="t" items="${tesisJurado}">
                             <div class="bg-white border border-slate-200 rounded-xl p-5 hover:border-purple-400 transition shadow-sm border-l-4 border-l-purple-500">
                                <div class="flex justify-between mb-3">
                                    <span class="bg-purple-50 text-purple-700 text-[10px] font-bold px-2 py-1 rounded uppercase">Jurado Calificador</span>
                                </div>
                                <h4 class="font-bold text-sm text-slate-800 mb-2 line-clamp-2 h-10">${t.titulo}</h4>
                                <p class="text-xs text-slate-500 mb-4">Estudiante: <strong>${t.nombreAutor}</strong></p>
                                <%-- Botón que abre el Anexo 5 con el ID de sustentación --%>
                                <button onclick="abrirEvaluacion('jurado', '${t.idTesis}', '${t.titulo}', '${t.idSustentacion}')" class="w-full bg-purple-600 text-white py-2.5 rounded-lg text-xs font-bold hover:bg-purple-700 transition shadow-sm">
                                    Evaluar (Anexo 5)
                                </button>
                            </div>
                        </c:forEach>
                         <c:if test="${empty tesisJurado}">
                             <div class="col-span-2 text-center py-12 text-slate-400 text-sm border-2 border-dashed border-slate-200 rounded-xl bg-slate-50">
                                Aún no tienes designaciones de jurado activas.
                             </div>
                        </c:if>
                    </div>
                 </div>
            </div>

            <!-- 4. SECCIÓN HISTORIAL -->
            <div id="seccion-historial" class="seccion-app hidden-section space-y-6 animate-fade-in">
                <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
                    <h3 class="text-lg font-bold text-slate-800 mb-6">Historial de Evaluaciones Realizadas</h3>
                    <div class="overflow-x-auto">
                        <table class="w-full text-sm text-left">
                            <thead class="bg-slate-50 text-slate-500 uppercase text-xs font-bold">
                                <tr>
                                    <th class="px-6 py-3 rounded-l-lg">Título de Tesis</th>
                                    <th class="px-6 py-3">Estudiante</th>
                                    <th class="px-6 py-3">Fecha</th>
                                    <th class="px-6 py-3 text-right rounded-r-lg">Dictamen</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-slate-100">
                                <c:forEach var="tesis" items="${tesisRevisadas}">
                                    <tr class="hover:bg-slate-50 transition">
                                        <td class="px-6 py-4 font-medium text-slate-800 max-w-xs truncate" title="${tesis.titulo}">${tesis.titulo}</td>
                                        <td class="px-6 py-4 text-slate-600">${tesis.nombreAutor}</td>
                                        <td class="px-6 py-4 text-slate-500 text-xs">${tesis.fechaSubida}</td>
                                        <td class="px-6 py-4 text-right">
                                            <span class="px-3 py-1 rounded-full text-[10px] font-bold uppercase 
                                                ${tesis.estado == 'Aprobado' || tesis.estado == 'Aprobado por Asesor' ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}">
                                                ${tesis.estado}
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty tesisRevisadas}">
                                    <tr><td colspan="4" class="px-6 py-8 text-center text-slate-400 italic">No hay historial disponible.</td></tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            
        </div>
    </main>

    <!-- MODAL EVALUACIÓN (RÚBRICA 38 ÍTEMS) -->
    <div id="modal-evaluacion" class="fixed inset-0 bg-slate-900/60 z-50 hidden flex items-center justify-center p-4 backdrop-blur-sm transition-opacity">
        <div class="bg-white rounded-xl shadow-2xl w-full max-w-5xl h-[90vh] flex flex-col overflow-hidden animate-fade-in transform scale-100">
            <!-- Header Modal -->
            <div class="px-6 py-4 border-b border-slate-200 flex justify-between items-center bg-slate-50 shrink-0">
                <div>
                    <h3 class="font-bold text-lg text-slate-800" id="eval-modal-title">Evaluación</h3>
                    <p class="text-xs text-slate-500 truncate max-w-xl" id="eval-modal-tesis">...</p>
                </div>
                <button type="button" onclick="document.getElementById('modal-evaluacion').classList.add('hidden')" class="text-slate-400 hover:text-slate-600 font-bold text-2xl leading-none">&times;</button>
            </div>
            
            <!-- Body Modal -->
            <div class="flex-1 overflow-y-auto p-6 bg-slate-50 custom-scroll">
                <form id="form-evaluacion" action="${pageContext.request.contextPath}/dashboardDocente" method="POST" class="bg-white p-8 rounded-xl shadow-sm border border-slate-200">
                    <!-- Inputs Ocultos Críticos -->
                    <input type="hidden" name="accion" id="input-accion" value="">
                    <input type="hidden" name="idTesis" id="input-id-ref" value="">
                    <input type="hidden" name="nuevoEstado" id="input-nuevo-estado" value="">
                    <input type="hidden" name="idSustentacion" id="input-id-sustentacion" value="0"> 
                    
                    <div class="mb-8 bg-blue-50 border border-blue-100 text-blue-800 px-6 py-4 rounded-lg text-sm flex gap-3">
                        <i class="fas fa-info-circle text-blue-500 text-lg mt-0.5"></i>
                        <div>
                            <p class="font-bold mb-1">Instrucciones Oficiales</p>
                            <p>Califique cada uno de los 38 ítems del reglamento. El sistema calculará el puntaje automáticamente y determinará la condición final (Aprobado/Observado).</p>
                        </div>
                    </div>

                    <!-- AQUÍ SE GENERAN LAS 38 PREGUNTAS REALES CON JS -->
                    <div id="tabla-rubrica-body" class="space-y-0 mb-8 border border-slate-200 rounded-lg overflow-hidden divide-y divide-slate-100">
                        <!-- Inyección dinámica -->
                    </div>

                    <!-- Resultados en Tiempo Real -->
                    <div class="flex items-center justify-between bg-slate-900 text-white p-6 rounded-xl shadow-lg mb-8 sticky bottom-0 z-10 border border-slate-800">
                        <div>
                            <p class="text-[10px] font-bold text-slate-400 uppercase tracking-wider mb-1">Puntaje Total</p>
                            <p class="text-3xl font-bold"><span id="lbl-puntaje">0.0</span> <span class="text-lg text-slate-500 font-normal">/ 38</span></p>
                        </div>
                        <div class="text-right">
                            <p class="text-[10px] font-bold text-slate-400 uppercase tracking-wider mb-1">Condición Final</p>
                            <p class="text-xl font-bold text-slate-200" id="lbl-condicion">Pendiente</p>
                        </div>
                    </div>

                    <div class="mb-6">
                        <label class="block text-sm font-bold text-slate-700 mb-2">Comentarios y Observaciones Finales</label>
                        <textarea name="comentarios" rows="4" class="w-full p-4 border border-slate-300 rounded-lg text-sm focus:ring-2 focus:ring-blue-500 outline-none shadow-sm transition" required placeholder="Ingrese las observaciones detalladas para el estudiante..."></textarea>
                    </div>

                    <div class="flex justify-end gap-3 pt-6 border-t border-slate-100">
                        <button type="button" onclick="document.getElementById('modal-evaluacion').classList.add('hidden')" class="px-6 py-2.5 text-slate-600 text-sm font-bold hover:bg-slate-100 rounded-lg transition">Cancelar</button>
                        <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white px-8 py-2.5 rounded-lg text-sm font-bold shadow-md hover:shadow-lg transition transform active:scale-95">Guardar Informe</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        function mostrarSeccion(id) {
            document.querySelectorAll('.seccion-app').forEach(el => el.classList.add('hidden-section'));
            document.querySelectorAll('.sidebar-link').forEach(el => {
                el.classList.remove('sidebar-active');
                el.classList.add('text-slate-300', 'hover:bg-white/5');
            });
            const target = document.getElementById('seccion-' + id);
            if(target) target.classList.remove('hidden-section');
            const navBtn = document.getElementById('nav-' + id);
            if(navBtn) navBtn.classList.add('sidebar-active');
        }

        // Abre el modal y configura si es para Asesor o Jurado
        function abrirEvaluacion(tipo, id, titulo, idSustentacion = 0) {
            const modal = document.getElementById('modal-evaluacion');
            modal.classList.remove('hidden');
            
            // Títulos
            document.getElementById('eval-modal-title').innerText = tipo === 'asesor' ? "Anexo 4: Informe de Asesor" : "Anexo 5: Informe de Jurado";
            document.getElementById('eval-modal-tesis').innerText = titulo;
            
            // IDs Ocultos
            document.getElementById('input-id-ref').value = id;
            document.getElementById('input-id-sustentacion').value = idSustentacion;
            
            // Acción del Controlador
            document.getElementById('input-accion').value = tipo === 'asesor' ? 'calificarTesis' : 'calificarSustentacion';
            
            // Generar Preguntas
            generarRubrica(tipo); 
            
            // Resetear UI
            document.getElementById('lbl-puntaje').innerText = "0.0";
            document.getElementById('lbl-condicion').innerText = "Pendiente";
            document.getElementById('form-evaluacion').reset();
        }

        function generarRubrica(tipo) {
            const container = document.getElementById('tabla-rubrica-body');
            let htmlContent = '';
            
            // Prefijo dinámico: 'item_' para asesor, 'jurado_item_' para jurado
            const prefix = tipo === 'jurado' ? 'jurado_item_' : 'item_';

            // LISTA COMPLETA DE 38 PREGUNTAS (Anexo 4 y 5)
            const preguntas = [
                "1. Es concordante con las variables de estudio, nivel y alcance de la investigación.",
                "2. El resumen contempla objetivo, metodología, resultado principal y conclusión general.",
                "3. El resumen no excede las 250 palabras y presenta palabras clave.",
                "4. Sintetiza el tema de investigación de forma clara y resumida.",
                "5. Describe el problema desde el punto de vista científico.",
                "6. La formulación del problema considera variables y dimensiones.",
                "7. La justificación social determina la contribución hacia la sociedad.",
                "8. La justificación teórica determina la generalización de resultados.",
                "9. La justificación metodológica considera las razones de los métodos.",
                "10. El objetivo general tiene relación con el problema y el título.",
                "11. Los objetivos específicos están en relación con los problemas específicos.",
                "12. Describe las implicancias éticas del estudio.",
                "13. Los antecedentes consideran objetivo, metodología y resultados.",
                "14. Los antecedentes tienen una antigüedad no mayor de cinco años.",
                "15. Las bases teóricas consideran información de las variables.",
                "16. El marco conceptual considera una descripción breve de variables.",
                "17. Las hipótesis son claras y dan respuesta a los problemas.",
                "18. Identifica, clasifica y describe las variables del estudio.",
                "19. Operacionaliza las variables considerando dimensiones e indicadores.",
                "20. Identifica y describe el método general y específico.",
                "21. Identifica y describe el tipo de investigación con claridad.",
                "22. Identifica y describe el nivel de investigación correctamente.",
                "23. Describe el diseño de investigación y su alcance.",
                "24. Identifica y describe características de la población.",
                "25. Identifica la muestra y establece el cálculo muestral.",
                "26. Describe e identifica la técnica e instrumento de investigación.",
                "27. Establece las técnicas de procesamiento de datos.",
                "28. Establece el procedimiento de contrastación de hipótesis.",
                "29. Los resultados son presentados mediante tablas y/o gráficos.",
                "30. Se presenta la contrastación de hipótesis (si aplica).",
                "31. Se establece y redacta de forma ordenada por cada objetivo.",
                "32. Contrasta la similitud o discrepancias teóricas.",
                "33. Establece de forma breve el nivel de alcance hallado.",
                "34. Deben derivarse de las conclusiones de la investigación.",
                "35. Están establecidas de acuerdo al estilo de la norma bibliográfica.",
                "36. Considera los anexos exigidos en la estructura de forma ordenada.",
                "37. Considera el formato correspondiente señalado por el Reglamento.",
                "38. Establece un documento ordenado, párrafos congruentes y formato adecuado."
            ];

            preguntas.forEach((pregunta, index) => {
                const i = index + 1;
                const bgClass = i % 2 === 0 ? 'bg-white' : 'bg-slate-50';
                
                htmlContent += `
                    <div class="flex items-center justify-between py-3 px-6 ${bgClass} hover:bg-blue-50 transition-colors">
                        <div class="w-2/3 pr-4">
                            <span class="text-xs font-bold text-slate-400 mr-2">#${i}</span>
                            <span class="text-sm text-slate-700 font-medium leading-snug">${pregunta}</span>
                        </div>
                        <div class="flex gap-2 shrink-0">
                            <!-- SI (1.0) -->
                            <label class="cursor-pointer group relative">
                                <input type="radio" name="${prefix}${i}" value="1" onchange="calc('${prefix}')" required class="sr-only-input peer">
                                <span class="px-3 py-1.5 rounded-md text-[10px] font-bold border border-slate-200 text-slate-500 bg-white peer-checked:bg-green-600 peer-checked:text-white peer-checked:border-green-600 transition-all block w-16 text-center shadow-sm">SI (1)</span>
                            </label>
                            
                            <!-- PAR (0.5) -->
                            <label class="cursor-pointer group relative">
                                <input type="radio" name="${prefix}${i}" value="0.5" onchange="calc('${prefix}')" class="sr-only-input peer">
                                <span class="px-3 py-1.5 rounded-md text-[10px] font-bold border border-slate-200 text-slate-500 bg-white peer-checked:bg-yellow-500 peer-checked:text-white peer-checked:border-yellow-500 transition-all block w-16 text-center shadow-sm">PAR (0.5)</span>
                            </label>
                            
                            <!-- NO (0.0) -->
                            <label class="cursor-pointer group relative">
                                <input type="radio" name="${prefix}${i}" value="0" onchange="calc('${prefix}')" class="sr-only-input peer">
                                <span class="px-3 py-1.5 rounded-md text-[10px] font-bold border border-slate-200 text-slate-500 bg-white peer-checked:bg-red-500 peer-checked:text-white peer-checked:border-red-500 transition-all block w-16 text-center shadow-sm">NO (0)</span>
                            </label>
                        </div>
                    </div>`;
            });
            container.innerHTML = htmlContent;
        }

        function calc(prefix) {
            let total = 0.0;
            // CORRECCIÓN CLAVE: Buscar inputs por name usando startsWith para el prefijo actual
            // Esto asegura que sumamos item_1...item_38 o jurado_item_1...jurado_item_38
            const form = document.getElementById('form-evaluacion');
            const inputs = form.querySelectorAll(`input[type="radio"]:checked`);
            
            inputs.forEach(r => {
                if (r.name.startsWith(prefix)) {
                    const val = parseFloat(r.value);
                    if (!isNaN(val)) total += val;
                }
            });
            
            document.getElementById('lbl-puntaje').innerText = total.toFixed(1); 
            
            let estado = "Rechazado";
            let colorClass = "text-red-400";
            let estadoInput = "Rechazado";

            if(total >= 25) { 
                estado = "Aprobado"; 
                colorClass = "text-green-400"; 
                estadoInput = "Aprobado";
            } else if(total >= 13) { 
                estado = "Aprobado con Observaciones"; 
                colorClass = "text-yellow-400"; 
                estadoInput = "Requiere Correcciones";
            }
            
            const lbl = document.getElementById('lbl-condicion');
            if(lbl) {
                lbl.innerText = estado;
                lbl.className = "text-xl font-bold " + colorClass;
            }
            
            document.getElementById('input-nuevo-estado').value = estadoInput;
        }
        
        document.addEventListener('DOMContentLoaded', () => {
            mostrarSeccion('dashboard');
        });
    </script>
</body>
</html>