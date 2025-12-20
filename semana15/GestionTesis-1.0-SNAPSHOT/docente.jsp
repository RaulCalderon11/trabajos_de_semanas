<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList, com.gestiontesis.modelo.Tesis, com.gestiontesis.modelo.Revision, com.gestiontesis.modelo.Notificacion" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portal Docente | Gestión Tesis</title>
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    
    <style>
        :root {
            --primary-color: #1e3a8a; /* Azul Institucional (Coherente con Admin) */
            --secondary-color: #059669; /* Verde Académico para Docentes */
            --accent-color: #f59e0b;
            --bg-color: #f3f4f6;
            --text-dark: #1f2937;
            --sidebar-width: 260px;
        }

        body {
            background-color: var(--bg-color);
            color: var(--text-dark);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            overflow-x: hidden;
        }

        /* --- SIDEBAR --- */
        .sidebar {
            background: linear-gradient(180deg, var(--primary-color) 0%, #111827 100%);
            width: var(--sidebar-width);
            min-height: 100vh;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1000;
            transition: transform 0.3s ease-in-out;
            box-shadow: 4px 0 10px rgba(0,0,0,0.1);
        }

        .sidebar-header {
            padding: 2rem 1.5rem;
            border-bottom: 1px solid rgba(255,255,255,0.1);
        }

        .sidebar a.nav-link {
            color: rgba(255,255,255,0.8);
            padding: 12px 20px;
            margin: 8px 15px;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.2s;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .sidebar a.nav-link:hover, .sidebar a.nav-link.active {
            background: rgba(255,255,255,0.1);
            color: white;
            transform: translateX(5px);
        }

        /* --- MAIN CONTENT --- */
        .main-content {
            margin-left: var(--sidebar-width);
            padding: 2rem;
            transition: margin-left 0.3s ease-in-out;
        }

        /* --- CARDS & STATS --- */
        .card-custom {
            border: none;
            border-radius: 12px;
            background: white;
            box-shadow: 0 2px 12px rgba(0,0,0,0.04);
            transition: transform 0.2s;
        }
        
        .card-custom:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
        }

        .stat-card {
            border-left: 5px solid var(--secondary-color);
            position: relative;
            overflow: hidden;
        }
        
        .stat-icon {
            position: absolute;
            right: 15px;
            bottom: 15px;
            font-size: 3rem;
            opacity: 0.1;
            color: var(--secondary-color);
        }

        /* --- TABLES --- */
        .table-custom thead {
            background-color: var(--secondary-color); /* Diferenciador Docente */
            color: white;
        }
        .table-custom th {
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.8rem;
            padding: 15px;
            border: none;
        }
        .table-custom td {
            padding: 15px;
            vertical-align: middle;
            border-bottom: 1px solid #f0f0f0;
        }
        .table-hover tbody tr:hover {
            background-color: #f0fdf4; /* Verde muy suave al hover */
        }

        /* --- BADGES --- */
        .badge-status {
            padding: 6px 12px;
            border-radius: 20px;
            font-weight: 500;
            font-size: 0.75rem;
            letter-spacing: 0.3px;
        }

        /* --- UTILS --- */
        .section-title {
            color: var(--primary-color);
            font-weight: 700;
            margin: 2rem 0 1rem;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .section-title::after {
            content: '';
            flex-grow: 1;
            height: 2px;
            background: #e5e7eb;
            margin-left: 15px;
        }

        /* --- RESPONSIVE --- */
        @media (max-width: 768px) {
            .sidebar { transform: translateX(-100%); width: 100%; }
            .sidebar.show { transform: translateX(0); }
            .main-content { margin-left: 0; padding: 1rem; }
        }
    </style>
</head>
<body>

<%
    // ==========================================
    // LOGICA ORIGINAL INTACTA
    // ==========================================
    List<Tesis> listaTotal = (List<Tesis>) request.getAttribute("listaTesisObj");
    List<Tesis> listaAsesor = new ArrayList<>();
    List<Tesis> listaJurado = new ArrayList<>();

    if(listaTotal != null) {
        for(Tesis t : listaTotal) {
            if("Asesor".equals(t.getRolDocente())) {
                listaAsesor.add(t);
            } else {
                listaJurado.add(t);
            }
        }
    }
    
    // Contadores para las tarjetas de resumen (Lógica visual basada en datos existentes)
    int countAsesor = listaAsesor.size();
    int countJurado = listaJurado.size();
    List<Notificacion> notifsObj = (List<Notificacion>) request.getAttribute("notificaciones");
    long countNotif = notifsObj != null ? notifsObj.stream().filter(n -> !n.isLeido()).count() : 0;
%>

<!-- Mobile Toggle -->
<button class="btn btn-primary d-md-none position-fixed top-0 start-0 m-3 z-3 shadow" type="button" data-bs-toggle="offcanvas" data-bs-target="#sidebarOffcanvas">
    <i class="bi bi-list"></i>
</button>

<div class="d-flex">
    <!-- Sidebar Desktop -->
    <div class="sidebar d-none d-md-flex flex-column">
        <div class="sidebar-header text-center text-white">
            <h4 class="mb-0 fw-bold"><i class="bi bi-mortarboard-fill me-2"></i>GestiónTesis</h4>
            <small class="opacity-75">Portal Docente</small>
        </div>
        <div class="mt-4">
            <a href="DocenteController?action=dashboard" class="nav-link active">
                <i class="bi bi-speedometer2"></i> Dashboard
            </a>
            <!-- Acciones rápidas en menú -->
            <a href="#seccion-asesor" class="nav-link">
                <i class="bi bi-person-video3"></i> Mis Tesistas
            </a>
            <a href="#seccion-jurado" class="nav-link">
                <i class="bi bi-gavel"></i> Jurado
            </a>
        </div>
        <div class="mt-auto p-3">
            <a href="LoginServlet?logout=true" class="nav-link text-danger bg-dark bg-opacity-25">
                <i class="bi bi-box-arrow-left"></i> Cerrar Sesión
            </a>
        </div>
    </div>

    <!-- Main Content -->
    <div class="main-content flex-grow-1 w-100">
        
        <!-- Top Bar -->
        <header class="d-flex justify-content-between align-items-center mb-5 pb-3 border-bottom">
            <div>
                <h3 class="fw-bold text-dark mb-0">Panel Académico</h3>
                <small class="text-muted">Bienvenido, <%= session.getAttribute("nombre") %></small>
            </div>
            
            <div class="d-flex align-items-center gap-3">
                <!-- Notifications Dropdown -->
                <div class="dropdown">
                    <button class="btn btn-light position-relative shadow-sm rounded-circle p-2" type="button" data-bs-toggle="dropdown">
                        <i class="bi bi-bell-fill text-secondary fs-5"></i>
                        <% if(countNotif > 0) { %>
                            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger border border-light">
                                <%= countNotif %>
                            </span>
                        <% } %>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end shadow-lg border-0 p-2" style="width: 300px;">
                        <li class="dropdown-header text-uppercase small fw-bold text-muted">Notificaciones</li>
                        <% 
                        List<Notificacion> notifs = (List<Notificacion>) request.getAttribute("notificaciones");
                        if(notifs != null && !notifs.isEmpty()) { 
                            for(Notificacion n : notifs) { %>
                            <li><a class="dropdown-item rounded small py-2 mb-1" href="#"><i class="bi bi-dot text-primary me-1"></i><%= n.getMensaje() %></a></li>
                        <% } %>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item text-center text-primary small fw-bold" href="DocenteController?action=leerNotificaciones">Marcar todo como leído</a></li>
                        <% } else { %>
                            <li class="p-3 text-center text-muted small"><i class="bi bi-inbox fs-4 d-block mb-2"></i>Sin novedades recientes</li>
                        <% } %>
                    </ul>
                </div>
                
                <div class="d-flex align-items-center gap-2">
                    <div class="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center fw-bold" style="width: 40px; height: 40px;">
                        <%= session.getAttribute("nombre").toString().substring(0,1) %>
                    </div>
                </div>
            </div>
        </header>

        <!-- ACCIONES RÁPIDAS / RESUMEN (NUEVO) -->
        <div class="row g-4 mb-5">
            <div class="col-md-4">
                <div class="card card-custom stat-card p-4">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <p class="text-muted text-uppercase small fw-bold mb-1">Tesistas a cargo</p>
                            <h2 class="mb-0 fw-bold text-dark"><%= countAsesor %></h2>
                        </div>
                        <div class="bg-success bg-opacity-10 p-3 rounded-circle">
                            <i class="bi bi-people-fill text-success fs-4"></i>
                        </div>
                    </div>
                    <a href="#seccion-asesor" class="stretched-link"></a>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card card-custom stat-card p-4" style="border-left-color: #1e3a8a;">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <p class="text-muted text-uppercase small fw-bold mb-1">Jurado Asignado</p>
                            <h2 class="mb-0 fw-bold text-dark"><%= countJurado %></h2>
                        </div>
                        <div class="bg-primary bg-opacity-10 p-3 rounded-circle">
                            <i class="bi bi-gavel text-primary fs-4"></i>
                        </div>
                    </div>
                    <a href="#seccion-jurado" class="stretched-link"></a>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card card-custom stat-card p-4" style="border-left-color: #f59e0b;">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <p class="text-muted text-uppercase small fw-bold mb-1">Pendientes</p>
                            <h2 class="mb-0 fw-bold text-dark"><%= countNotif %></h2>
                        </div>
                        <div class="bg-warning bg-opacity-10 p-3 rounded-circle">
                            <i class="bi bi-exclamation-circle-fill text-warning fs-4"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- SECCIÓN 1: MIS TESISTAS -->
        <h5 class="section-title" id="seccion-asesor">
            <i class="bi bi-person-video3 text-success"></i> Mis Tesistas (Asesoría)
        </h5>
        
        <div class="card card-custom mb-5 overflow-hidden">
            <div class="card-body p-0">
                <% if(listaAsesor.isEmpty()) { %>
                    <div class="p-5 text-center text-muted">
                        <i class="bi bi-folder2-open display-4 mb-3 d-block opacity-25"></i>
                        No asesoras ninguna tesis actualmente.
                    </div>
                <% } else { %>
                <div class="table-responsive">
                    <table class="table table-custom table-hover mb-0">
                        <thead>
                            <tr>
                                <th class="ps-4">ID</th>
                                <th>Proyecto</th>
                                <th>Alumno</th>
                                <th>Estado</th>
                                <th class="text-end pe-4">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for(Tesis t : listaAsesor) { 
                                 boolean puedeCalificar = !"Aprobado".equals(t.getEstado()) && 
                                                          !"En Jurado".equals(t.getEstado()) && 
                                                          !"Apto para Sustentar".equals(t.getEstado());
                                 
                                 String badgeClass = "bg-secondary";
                                 if("Aprobado".equals(t.getEstado())) badgeClass = "bg-success";
                                 else if("En Revision".equals(t.getEstado())) badgeClass = "bg-warning text-dark";
                            %>
                            <tr>
                                <td class="ps-4 fw-bold text-muted">#<%= t.getId() %></td>
                                <td class="fw-bold text-dark"><%= t.getTitulo() %></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <div class="bg-light rounded-circle p-2 me-2"><i class="bi bi-person text-muted"></i></div>
                                        <%= t.getNombreAlumno() %>
                                    </div>
                                </td>
                                <td><span class="badge badge-status <%= badgeClass %>"><%= t.getEstado() %></span></td>
                                <td class="text-end pe-4">
                                    <div class="btn-group">
                                        <% if(t.getArchivoUrl() != null && !t.getArchivoUrl().isEmpty()) { %>
                                            <a href="DownloadServlet?path=<%= java.net.URLEncoder.encode(t.getArchivoUrl(), "UTF-8") %>" target="_blank" class="btn btn-sm btn-outline-danger border-0" data-bs-toggle="tooltip" title="Ver PDF"><i class="bi bi-file-earmark-pdf fs-6"></i></a>
                                        <% } %>
                                        
                                        <a href="DocenteController?action=dashboard&verHistorial=<%= t.getId() %>" class="btn btn-sm btn-outline-secondary border-0" data-bs-toggle="tooltip" title="Ver Historial"><i class="bi bi-clock-history fs-6"></i></a>
                                        
                                        <% if(puedeCalificar) { %>
                                            <button class="btn btn-sm btn-success ms-2 rounded-pill px-3" onclick="abrirCalificar('<%= t.getId() %>', 'Asesor')">
                                                <i class="bi bi-pencil-square me-1"></i> Calificar
                                            </button>
                                        <% } %>
                                    </div>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
                <% } %>
            </div>
        </div>

        <!-- SECCIÓN 2: JURADO -->
        <h5 class="section-title" id="seccion-jurado">
            <i class="bi bi-gavel text-primary"></i> Designaciones como Jurado
        </h5>
        
        <div class="card card-custom mb-5 overflow-hidden">
            <div class="card-body p-0">
                <% if(listaJurado.isEmpty()) { %>
                    <div class="p-5 text-center text-muted">
                        <i class="bi bi-briefcase display-4 mb-3 d-block opacity-25"></i>
                        No has sido designado como jurado en ninguna tesis.
                    </div>
                <% } else { %>
                <div class="table-responsive">
                    <table class="table table-custom table-hover mb-0">
                        <thead style="background-color: #1e3a8a;"> <!-- Header Azul para Jurado -->
                            <tr>
                                <th class="ps-4">ID</th>
                                <th>Tesis</th>
                                <th>Alumno</th>
                                <th>Estado Global</th>
                                <th>Mi Voto</th>
                                <th class="text-end pe-4">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for(Tesis t : listaJurado) { 
                                 boolean puedeVotar = "En Jurado".equals(t.getEstado());
                                 String colorVoto = "secondary";
                                 if(t.getNotaJurado() >= 20) colorVoto = "success";
                                 else if(t.getNotaJurado() > 0 && t.getNotaJurado() < 11) colorVoto = "danger";
                                 else if(t.getNotaJurado() >= 11) colorVoto = "warning text-dark";
                            %>
                            <tr>
                                <td class="ps-4 fw-bold text-muted">#<%= t.getId() %></td>
                                <td class="fw-bold text-dark"><%= t.getTitulo() %></td>
                                <td><%= t.getNombreAlumno() %></td>
                                <td><span class="badge bg-light text-dark border"><%= t.getEstado() %></span></td>
                                <td>
                                    <% if(t.getNotaJurado() > 0) { %>
                                        <span class="badge bg-<%= colorVoto %>"><%= t.getNotaJurado() %> pts</span>
                                    <% } else { %>
                                        <span class="badge bg-secondary opacity-50">Pendiente</span>
                                    <% } %>
                                </td>
                                <td class="text-end pe-4">
                                    <% if(t.getArchivoUrl() != null && !t.getArchivoUrl().isEmpty()) { %>
                                        <a href="DownloadServlet?path=<%= java.net.URLEncoder.encode(t.getArchivoUrl(), "UTF-8") %>" target="_blank" class="btn btn-sm btn-outline-dark border-0" title="Leer Documento"><i class="bi bi-book fs-6"></i></a>
                                    <% } %>
                                    
                                    <% if(puedeVotar) { %>
                                        <button class="btn btn-sm btn-primary ms-2 rounded-pill px-3" onclick="abrirCalificar('<%= t.getId() %>', 'Jurado')">
                                            <i class="bi bi-check-circle me-1"></i> Emitir Voto
                                        </button>
                                    <% } else if(t.getNotaJurado() > 0) { %>
                                        <span class="text-success small fw-bold ms-2"><i class="bi bi-check-all"></i> Votado</span>
                                    <% } %>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
                <% } %>
            </div>
        </div>
        
        <!-- SECCIÓN HISTORIAL -->
        <% if(request.getAttribute("historialSeleccionado") != null) { 
            List<Revision> historial = (List<Revision>) request.getAttribute("historialSeleccionado");
        %>
        <div class="card card-custom border-0 shadow-lg mt-4 animate__animated animate__fadeIn" id="seccionHistorial">
            <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center py-3">
                <span class="fw-bold"><i class="bi bi-clock-history me-2"></i>Historial de Revisiones (Tesis #<%= request.getAttribute("idTesisHistorial") %>)</span>
                <button type="button" class="btn-close btn-close-white" onclick="document.getElementById('seccionHistorial').style.display='none'"></button>
            </div>
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-striped mb-0">
                        <thead class="bg-light text-dark"><tr><th class="ps-4">Versión</th><th>Fecha</th><th>Estado</th><th>Puntaje</th><th>Rúbrica</th><th>Comentarios</th><th class="text-end pe-4">Archivo</th></tr></thead>
                        <tbody>
                            <% for(Revision r : historial) { %>
                            <tr>
                                <td class="ps-4 fw-bold">v<%= r.getNumeroVersion() %></td>
                                <td><%= r.getFecha() %></td>
                                <td><%= r.getEstado() %></td>
                                <td><%= r.getPuntaje() %></td>
                                <td><small class="text-muted"><%= r.getDetalleRubrica() != null ? r.getDetalleRubrica() : "-" %></small></td>
                                <td><small><%= r.getComentarios() %></small></td>
                                <td class="text-end pe-4">
                                    <% if(r.getArchivoUrl() != null && !r.getArchivoUrl().isEmpty()) { %>
                                        <a href="DownloadServlet?path=<%= java.net.URLEncoder.encode(r.getArchivoUrl(), "UTF-8") %>" class="btn btn-sm btn-outline-primary"><i class="bi bi-download"></i></a>
                                    <% } else { %>
                                        <span class="text-muted small">-</span>
                                    <% } %>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <% } %>

    </div>
</div>

<!-- Modal Calificar -->
<div class="modal fade" id="modalCalificar" tabindex="-1">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <form action="DocenteController" method="post" id="formCalificar" class="w-100">
            <div class="modal-content border-0 shadow-lg">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title fw-bold"><i class="bi bi-clipboard-check me-2"></i>Evaluación Académica</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4 bg-light">
                    <input type="hidden" name="action" value="calificar">
                    <input type="hidden" name="tesisId" id="inputTesisId">
                    <input type="hidden" name="rolDocente" id="inputRolDocente">
                    <input type="hidden" name="alumnoId" value="1"> 
                    
                    <div class="card border-0 shadow-sm mb-4">
                        <div class="card-body">
                            <h6 class="text-success fw-bold text-uppercase small mb-3">Rúbrica de Evaluación</h6>
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <label class="form-label small fw-bold text-muted">Fundamentación (0-10)</label>
                                    <input type="number" name="crit_fundamentacion" class="form-control text-center fw-bold" max="10" min="0" step="0.5" required onchange="calcularTotal()">
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label small fw-bold text-muted">Metodología (0-10)</label>
                                    <input type="number" name="crit_metodologia" class="form-control text-center fw-bold" max="10" min="0" step="0.5" required onchange="calcularTotal()">
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label small fw-bold text-muted">Resultados (0-20)</label>
                                    <input type="number" name="crit_resultados" class="form-control text-center fw-bold" max="20" min="0" step="0.5" required onchange="calcularTotal()">
                                </div>
                            </div>
                        </div>
                        <div class="card-footer bg-white border-top-0 d-flex justify-content-between align-items-center">
                            <span id="lblEstado" class="badge bg-secondary">Estado: Pendiente</span>
                            <div class="fs-5">Total: <strong id="spanTotal" class="text-dark">0</strong> / 40</div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold text-muted small">Comentarios y Observaciones</label>
                        <textarea name="comentarios" class="form-control" rows="4" required placeholder="Ingrese el feedback detallado para el estudiante..."></textarea>
                    </div>
                </div>
                <div class="modal-footer bg-white">
                    <button type="button" class="btn btn-link text-muted text-decoration-none" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-success px-4 fw-bold">Registrar Evaluación</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- SCRIPTS (Lógica Original Intacta) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Inicializar Tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
      return new bootstrap.Tooltip(tooltipTriggerEl)
    })

    function abrirCalificar(id, rol) {
        document.getElementById('inputTesisId').value = id;
        document.getElementById('inputRolDocente').value = rol;
        document.getElementById('formCalificar').reset();
        document.getElementById('spanTotal').innerText = "0";
        document.getElementById('lblEstado').innerText = "Estado: Pendiente";
        document.getElementById('lblEstado').className = "badge bg-secondary";
        new bootstrap.Modal(document.getElementById('modalCalificar')).show();
    }
    
    function calcularTotal() {
        let c1 = parseFloat(document.getElementsByName('crit_fundamentacion')[0].value) || 0;
        let c2 = parseFloat(document.getElementsByName('crit_metodologia')[0].value) || 0;
        let c3 = parseFloat(document.getElementsByName('crit_resultados')[0].value) || 0;
        
        let total = c1 + c2 + c3;
        document.getElementById('spanTotal').innerText = total;
        
        let lbl = document.getElementById('lblEstado');
        if(total >= 21) {
            lbl.innerText = "Estado: APROBADO";
            lbl.className = "badge bg-success";
        } else if (total >= 13) {
            lbl.innerText = "Estado: OBSERVADO";
            lbl.className = "badge bg-warning text-dark";
        } else {
            lbl.innerText = "Estado: RECHAZADO";
            lbl.className = "badge bg-danger";
        }
    }
</script>

<!-- Offcanvas Sidebar para Mobile -->
<div class="offcanvas offcanvas-start" tabindex="-1" id="sidebarOffcanvas" style="background: #1e3a8a;">
    <div class="offcanvas-header text-white">
        <h5 class="offcanvas-title">Gestión Docente</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas"></button>
    </div>
    <div class="offcanvas-body">
         <div class="nav flex-column">
            <a href="DocenteController?action=dashboard" class="nav-link text-white mb-3">
                <i class="bi bi-speedometer2 me-2"></i> Dashboard
            </a>
            <a href="#seccion-asesor" class="nav-link text-white-50 mb-2" onclick="bootstrap.Offcanvas.getInstance(document.getElementById('sidebarOffcanvas')).hide()">
                <i class="bi bi-person-video3 me-2"></i> Mis Tesistas
            </a>
            <a href="#seccion-jurado" class="nav-link text-white-50 mb-2" onclick="bootstrap.Offcanvas.getInstance(document.getElementById('sidebarOffcanvas')).hide()">
                <i class="bi bi-gavel me-2"></i> Jurado
            </a>
            <hr class="bg-white">
            <a href="LoginServlet?logout=true" class="nav-link text-danger"><i class="bi bi-box-arrow-left me-2"></i> Salir</a>
        </div>
    </div>
</div>

</body>
</html>