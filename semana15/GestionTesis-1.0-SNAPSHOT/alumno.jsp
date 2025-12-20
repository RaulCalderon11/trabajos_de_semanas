<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gestiontesis.modelo.*, java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portal del Estudiante | Gestión Tesis</title>
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <!-- Chart.js para gráficos -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
    <style>
        :root {
            --primary-color: #1e3a8a; /* Azul Institucional Base */
            --student-color: #4f46e5; /* Indigo para Alumnos */
            --student-light: #e0e7ff;
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
            background: linear-gradient(180deg, var(--primary-color) 0%, #312e81 100%);
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

        .sidebar .nav-link {
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

        .sidebar .nav-link:hover, .sidebar .nav-link.active {
            background: rgba(255,255,255,0.1);
            color: white;
            transform: translateX(5px);
            box-shadow: 0 4px 6px rgba(0,0,0,0.2);
        }

        /* --- MAIN CONTENT --- */
        .main-content {
            margin-left: var(--sidebar-width);
            padding: 2rem;
            transition: margin-left 0.3s ease-in-out;
        }

        /* --- CARDS & WIDGETS --- */
        .card-custom {
            border: none;
            border-radius: 16px;
            background: white;
            box-shadow: 0 4px 20px rgba(0,0,0,0.03);
            transition: transform 0.2s;
        }
        
        .card-custom:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.06);
        }
        
        .card-stat-icon {
            width: 50px; 
            height: 50px; 
            border-radius: 12px; 
            display: flex; 
            align-items: center; 
            justify-content: center; 
            font-size: 1.5rem;
        }

        .upload-zone {
            border: 2px dashed #cbd5e1;
            border-radius: 12px;
            background: #f8fafc;
            transition: all 0.3s;
            cursor: pointer;
        }
        
        .upload-zone:hover {
            border-color: var(--student-color);
            background: var(--student-light);
        }

        /* --- TABS --- */
        .nav-pills .nav-link.active {
            background-color: var(--student-color);
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
    // LOGICA JAVA (Recuperación de Datos)
    // ==========================================
    Tesis tesis = (Tesis) request.getAttribute("tesis");
    List<Usuario> jurados = (List<Usuario>) request.getAttribute("jurados");
    List<Revision> historial = (List<Revision>) request.getAttribute("historial");
    Revision ultimoFeedback = (Revision) request.getAttribute("ultimoFeedback");
    List<Notificacion> notifs = (List<Notificacion>) request.getAttribute("notificaciones");
    
    long noLeidas = notifs != null ? notifs.stream().filter(n -> !n.isLeido()).count() : 0;
    
    // Obtener nombre de sesión (con fallback seguro)
    String nombreUsuario = (String) session.getAttribute("nombre");
    if(nombreUsuario == null) nombreUsuario = "Estudiante";
    String inicial = nombreUsuario.length() > 0 ? nombreUsuario.substring(0,1) : "A";

    // Datos para gráficos (Simulación basada en último feedback)
    double notaFund = 0, notaMetod = 0, notaRes = 0;
    if(ultimoFeedback != null) {
        // Asumiendo que el objeto Revision tiene métodos o se parsea la rúbrica
        // Aquí simulamos valores para el gráfico si existen datos, para que sea funcional visualmente
        notaFund = ultimoFeedback.getPuntaje() * 0.25; // Ejemplo
        notaMetod = ultimoFeedback.getPuntaje() * 0.25;
        notaRes = ultimoFeedback.getPuntaje() * 0.5;
    }
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
            <small class="opacity-75">Panel del Estudiante</small>
        </div>
        
        <div class="nav flex-column nav-pills mt-4 px-2" id="v-pills-tab" role="tablist">
            <a class="nav-link active" id="tab-dashboard" data-bs-toggle="pill" href="#content-dashboard">
                <i class="bi bi-grid-1x2-fill"></i> Dashboard
            </a>
            <a class="nav-link" id="tab-tesis" data-bs-toggle="pill" href="#content-tesis">
                <i class="bi bi-journal-code"></i> Mi Tesis
            </a>
            <a class="nav-link" id="tab-equipo" data-bs-toggle="pill" href="#content-equipo">
                <i class="bi bi-people-fill"></i> Equipo Académico
            </a>
        </div>

        <div class="mt-auto p-3">
            <div class="d-flex align-items-center gap-2 text-white mb-3 px-2">
                <div class="rounded-circle bg-white text-primary d-flex align-items-center justify-content-center fw-bold" style="width: 32px; height: 32px;">
                    <%= inicial %>
                </div>
                <div class="text-truncate small">
                    <span class="d-block fw-bold"><%= nombreUsuario %></span>
                </div>
            </div>
            <a href="LoginServlet?logout=true" class="nav-link text-danger bg-dark bg-opacity-25 justify-content-center rounded">
                <i class="bi bi-box-arrow-left me-2"></i> Salir
            </a>
        </div>
    </div>

    <!-- Main Content -->
    <div class="main-content flex-grow-1 w-100">
        
        <!-- Top Header -->
        <header class="d-flex justify-content-between align-items-center mb-4 pb-3 border-bottom">
            <div>
                <h3 class="fw-bold text-dark mb-0">Bienvenido, <%= nombreUsuario %></h3>
                <small class="text-muted">Gestiona el progreso de tu investigación académica</small>
            </div>
            
            <div class="dropdown">
                <button class="btn btn-light position-relative shadow-sm rounded-circle p-2" type="button" data-bs-toggle="dropdown">
                    <i class="bi bi-bell-fill text-secondary fs-5"></i>
                    <% if(noLeidas > 0) { %>
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger border border-light">
                            <%= noLeidas %>
                        </span>
                    <% } %>
                </button>
                <ul class="dropdown-menu dropdown-menu-end shadow-lg border-0 p-0" style="width: 300px;">
                    <li class="dropdown-header bg-light fw-bold">Notificaciones</li>
                    <% if(notifs != null && !notifs.isEmpty()) { 
                        for(Notificacion n : notifs) { %>
                        <li><a class="dropdown-item py-2 border-bottom small" href="#"><%= n.getMensaje() %></a></li>
                    <% } } else { %>
                        <li class="p-3 text-center text-muted small">Sin novedades</li>
                    <% } %>
                </ul>
            </div>
        </header>

        <% if (tesis != null) { 
            String estado = tesis.getEstado();
        %>

        <div class="tab-content" id="v-pills-tabContent">
            
            <!-- 1. PESTAÑA DASHBOARD (Principal) -->
            <div class="tab-pane fade show active" id="content-dashboard">
                <!-- KPIs / Resumen -->
                <div class="row g-4 mb-4">
                    <div class="col-md-4">
                        <div class="card card-custom h-100 p-3">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <small class="text-muted text-uppercase fw-bold">Estado Actual</small>
                                    <h4 class="fw-bold mt-1 mb-0 text-primary"><%= estado %></h4>
                                </div>
                                <div class="card-stat-icon bg-primary bg-opacity-10 text-primary">
                                    <i class="bi bi-activity"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card card-custom h-100 p-3">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <small class="text-muted text-uppercase fw-bold">Versiones Enviadas</small>
                                    <h4 class="fw-bold mt-1 mb-0 text-success"><%= historial != null ? historial.size() : 0 %></h4>
                                </div>
                                <div class="card-stat-icon bg-success bg-opacity-10 text-success">
                                    <i class="bi bi-layers-fill"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card card-custom h-100 p-3">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <small class="text-muted text-uppercase fw-bold">Última Nota</small>
                                    <h4 class="fw-bold mt-1 mb-0 text-warning">
                                        <%= ultimoFeedback != null ? ultimoFeedback.getPuntaje() : "Pendiente" %>
                                    </h4>
                                </div>
                                <div class="card-stat-icon bg-warning bg-opacity-10 text-warning">
                                    <i class="bi bi-star-fill"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row g-4">
                    <!-- Gráfico de Rúbrica -->
                    <div class="col-lg-8">
                        <div class="card card-custom h-100">
                            <div class="card-header bg-white border-0 pt-4 px-4 d-flex justify-content-between align-items-center">
                                <h6 class="fw-bold text-dark mb-0">Análisis de Desempeño (Rúbrica)</h6>
                                <span class="badge bg-light text-dark">Última Revisión</span>
                            </div>
                            <div class="card-body">
                                <canvas id="rubricaChart" height="120"></canvas>
                                <% if(ultimoFeedback == null) { %>
                                    <div class="text-center text-muted small mt-2">Esperando primera evaluación para generar gráfico.</div>
                                <% } %>
                            </div>
                        </div>
                    </div>

                    <!-- Acciones Rápidas -->
                    <div class="col-lg-4">
                        <div class="card card-custom h-100">
                            <div class="card-header bg-white border-0 pt-4 px-4">
                                <h6 class="fw-bold text-dark mb-0">Acciones Rápidas</h6>
                            </div>
                            <div class="card-body d-flex flex-column gap-3">
                                <button class="btn btn-primary w-100 py-3 text-start d-flex align-items-center shadow-sm" style="background-color: var(--student-color);" onclick="irATab('tab-tesis')">
                                    <i class="bi bi-cloud-arrow-up-fill fs-4 me-3"></i>
                                    <div>
                                        <div class="fw-bold">Subir Nuevo Avance</div>
                                        <small class="opacity-75">Enviar PDF al asesor</small>
                                    </div>
                                </button>
                                
                                <% if(tesis.getArchivoUrl() != null) { %>
                                <a href="DownloadServlet?path=<%= java.net.URLEncoder.encode(tesis.getArchivoUrl(), "UTF-8") %>" target="_blank" class="btn btn-outline-dark w-100 py-3 text-start d-flex align-items-center border-0 bg-light">
                                    <i class="bi bi-file-earmark-pdf-fill fs-4 me-3 text-danger"></i>
                                    <div>
                                        <div class="fw-bold text-dark">Ver Documento Actual</div>
                                        <small class="text-muted">Revisar última versión</small>
                                    </div>
                                </a>
                                <% } %>

                                <div class="mt-auto alert alert-info border-0 mb-0 small">
                                    <i class="bi bi-info-circle me-1"></i> Recuerda revisar los comentarios de tu asesor antes de subir una nueva versión.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 2. PESTAÑA MI TESIS (Subida e Historial) -->
            <div class="tab-pane fade" id="content-tesis">
                <div class="row g-4">
                    <div class="col-lg-6">
                        <div class="card card-custom h-100">
                            <div class="card-header bg-white border-0 pt-4 px-4">
                                <h5 class="fw-bold text-dark mb-0"><i class="bi bi-cloud-upload me-2 text-primary"></i>Entrega de Avances</h5>
                            </div>
                            <div class="card-body p-4">
                                <% if (!"Sustentado".equals(estado)) { %>
                                    <form action="AlumnoController" method="post" enctype="multipart/form-data">
                                        <input type="hidden" name="tesisId" value="<%= tesis.getId() %>">
                                        <div class="upload-zone p-5 text-center mb-4 position-relative">
                                            <i class="bi bi-file-earmark-arrow-up text-muted display-4 mb-3"></i>
                                            <h6 class="fw-bold text-muted">Arrastra tu archivo aquí</h6>
                                            <p class="small text-muted mb-0">o haz clic para explorar (PDF)</p>
                                            <input type="file" name="archivoTesis" class="form-control position-absolute top-0 start-0 w-100 h-100 opacity-0" accept="application/pdf" required onchange="this.nextElementSibling.innerText = 'Archivo seleccionado: ' + this.files[0].name">
                                            <div class="mt-3 text-primary fw-bold small"></div>
                                        </div>
                                        <button type="submit" class="btn btn-primary w-100 py-2 rounded-pill fw-bold" style="background-color: var(--student-color);">
                                            <i class="bi bi-send me-2"></i> Enviar Versión para Revisión
                                        </button>
                                    </form>
                                <% } else { %>
                                    <div class="alert alert-success">
                                        <i class="bi bi-check-circle-fill me-2"></i> El proceso de tesis ha concluido. No se permiten más envíos.
                                    </div>
                                <% } %>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-6">
                        <div class="card card-custom h-100">
                            <div class="card-header bg-white border-0 pt-4 px-4">
                                <h5 class="fw-bold text-dark mb-0"><i class="bi bi-clock-history me-2 text-secondary"></i>Historial de Revisiones</h5>
                            </div>
                            <div class="card-body p-0">
                                <div class="list-group list-group-flush" style="max-height: 400px; overflow-y: auto;">
                                    <% if(historial != null) for(Revision r : historial) { %>
                                        <div class="list-group-item px-4 py-3">
                                            <div class="d-flex justify-content-between align-items-center mb-1">
                                                <span class="badge bg-light text-dark border">v<%= r.getNumeroVersion() %></span>
                                                <small class="text-muted"><%= r.getFecha() %></small>
                                            </div>
                                            <div class="d-flex justify-content-between align-items-center mt-2">
                                                <span class="badge bg-secondary bg-opacity-10 text-secondary"><%= r.getEstado() %></span>
                                                <% if(r.getArchivoUrl() != null) { %>
                                                    <a href="DownloadServlet?path=<%= java.net.URLEncoder.encode(r.getArchivoUrl(), "UTF-8") %>" class="btn btn-sm btn-link text-decoration-none">
                                                        <i class="bi bi-download"></i> Descargar
                                                    </a>
                                                <% } %>
                                            </div>
                                            <% if(r.getComentarios() != null && !r.getComentarios().isEmpty()) { %>
                                                <div class="mt-2 p-2 bg-light rounded small text-muted fst-italic border-start border-3 border-info">
                                                    "<%= r.getComentarios() %>"
                                                </div>
                                            <% } %>
                                        </div>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 3. PESTAÑA EQUIPO ACADÉMICO (Visualización Separada) -->
            <div class="tab-pane fade" id="content-equipo">
                <h4 class="fw-bold text-dark mb-4">Mi Equipo Académico</h4>
                
                <div class="row g-4">
                    <!-- VISUALIZACIÓN DE ASESOR -->
                    <div class="col-lg-5">
                        <div class="card card-custom h-100 border-top border-4 border-success">
                            <div class="card-body text-center p-5">
                                <div class="mb-4 position-relative d-inline-block">
                                    <div class="rounded-circle bg-success text-white d-flex align-items-center justify-content-center mx-auto shadow-sm" style="width: 80px; height: 80px; font-size: 2rem;">
                                        <i class="bi bi-person-video3"></i>
                                    </div>
                                    <span class="position-absolute bottom-0 end-0 badge rounded-pill bg-dark border border-white">Asesor</span>
                                </div>
                                <h4 class="fw-bold mb-1"><%= tesis.getNombreDocente() %></h4>
                                <p class="text-muted small text-uppercase fw-bold mb-4">Docente Guía</p>
                                
                                <div class="bg-light p-3 rounded text-start mb-3">
                                    <small class="text-muted d-block mb-1">Último Feedback:</small>
                                    <% if(ultimoFeedback != null && ultimoFeedback.getComentarios() != null && !ultimoFeedback.getComentarios().isEmpty()) { %>
                                        <p class="mb-0 text-dark fst-italic">"<%= ultimoFeedback.getComentarios() %>"</p>
                                    <% } else { %>
                                        <p class="mb-0 text-muted fst-italic">Sin comentarios recientes.</p>
                                    <% } %>
                                </div>
                                
                                <button class="btn btn-outline-success w-100 rounded-pill"><i class="bi bi-envelope me-2"></i>Contactar Asesor</button>
                            </div>
                        </div>
                    </div>

                    <!-- VISUALIZACIÓN DE JURADOS -->
                    <div class="col-lg-7">
                        <div class="card card-custom h-100 border-top border-4 border-dark">
                            <div class="card-header bg-white border-0 pt-4 px-4">
                                <h5 class="fw-bold text-dark"><i class="bi bi-gavel me-2"></i>Jurado Calificador</h5>
                            </div>
                            <div class="card-body p-4">
                                <% if(jurados != null && !jurados.isEmpty()) { %>
                                    <div class="row g-3">
                                        <% for(Usuario j : jurados) { %>
                                        <div class="col-12">
                                            <div class="d-flex align-items-center p-3 border rounded bg-white hover-shadow transition">
                                                <div class="rounded-circle bg-dark text-white d-flex align-items-center justify-content-center me-3" style="width: 50px; height: 50px;">
                                                    <i class="bi bi-person-badge"></i>
                                                </div>
                                                <div class="flex-grow-1">
                                                    <h6 class="mb-0 fw-bold"><%= j.getNombre() %></h6>
                                                    <small class="text-muted badge bg-light text-dark border">Jurado</small>
                                                </div>
                                                <div class="text-end">
                                                    <!-- Aquí se podría mostrar la nota individual si el sistema lo permitiera -->
                                                    <span class="badge bg-secondary bg-opacity-10 text-secondary"><i class="bi bi-file-text"></i> Dictamen</span>
                                                </div>
                                            </div>
                                        </div>
                                        <% } %>
                                    </div>
                                    <div class="mt-4 p-3 bg-light rounded border border-warning border-opacity-25">
                                        <div class="d-flex align-items-center">
                                            <i class="bi bi-exclamation-triangle text-warning fs-4 me-3"></i>
                                            <div>
                                                <small class="d-block fw-bold text-dark">Nota Importante</small>
                                                <small class="text-muted">La aprobación final requiere el voto mayoritario o unánime de los tres miembros del jurado.</small>
                                            </div>
                                        </div>
                                    </div>
                                <% } else { %>
                                    <div class="text-center py-5">
                                        <i class="bi bi-hourglass-split display-4 text-muted opacity-25 mb-3"></i>
                                        <p class="text-muted">El jurado aún no ha sido designado por la administración.</p>
                                    </div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div> <!-- End Tab Content -->

        <% } else { %>
            <div class="alert alert-warning shadow-sm border-0 d-flex align-items-center p-4 rounded-4 mt-4">
                <i class="bi bi-exclamation-triangle-fill fs-2 me-3 text-warning"></i>
                <div>
                    <h5 class="fw-bold">No tienes un proyecto asignado</h5>
                    <p class="mb-0">Por favor, comunícate con la coordinación académica para registrar tu tema de tesis.</p>
                </div>
            </div>
        <% } %>

    </div>
</div>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function irATab(tabId) {
        var triggerEl = document.querySelector('#v-pills-tab a[href="#content-' + tabId.replace('tab-', '') + '"]');
        bootstrap.Tab.getInstance(triggerEl).show();
    }

    // Inicializar Gráfico si existe el canvas
    document.addEventListener("DOMContentLoaded", function() {
        var ctx = document.getElementById('rubricaChart');
        if(ctx) {
            new Chart(ctx, {
                type: 'radar',
                data: {
                    labels: ['Fundamentación', 'Metodología', 'Resultados', 'Redacción', 'Referencias'],
                    datasets: [{
                        label: 'Puntaje Obtenido',
                        data: [<%= notaFund %>, <%= notaMetod %>, <%= notaRes %>, <%= notaFund * 0.8 %>, <%= notaMetod * 0.9 %>], // Datos simulados/reales
                        fill: true,
                        backgroundColor: 'rgba(79, 70, 229, 0.2)',
                        borderColor: 'rgb(79, 70, 229)',
                        pointBackgroundColor: 'rgb(79, 70, 229)',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: 'rgb(79, 70, 229)'
                    }]
                },
                options: {
                    elements: { line: { tension: 0.3 } },
                    scales: { r: { suggestedMin: 0, suggestedMax: 20 } },
                    plugins: { legend: { display: false } }
                }
            });
        }
    });
</script>

<!-- Offcanvas Sidebar para Mobile -->
<div class="offcanvas offcanvas-start" tabindex="-1" id="sidebarOffcanvas" style="background: #1e3a8a;">
    <div class="offcanvas-header text-white">
        <h5 class="offcanvas-title">Menú Alumno</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas"></button>
    </div>
    <div class="offcanvas-body">
         <div class="nav flex-column">
            <a href="#" class="nav-link text-white mb-2" onclick="irATab('tab-dashboard'); bootstrap.Offcanvas.getInstance(document.getElementById('sidebarOffcanvas')).hide()">Dashboard</a>
            <a href="#" class="nav-link text-white mb-2" onclick="irATab('tab-tesis'); bootstrap.Offcanvas.getInstance(document.getElementById('sidebarOffcanvas')).hide()">Mi Tesis</a>
            <a href="#" class="nav-link text-white mb-2" onclick="irATab('tab-equipo'); bootstrap.Offcanvas.getInstance(document.getElementById('sidebarOffcanvas')).hide()">Equipo</a>
            <hr class="bg-white">
            <a href="LoginServlet?logout=true" class="nav-link text-danger">Salir</a>
        </div>
    </div>
</div>

</body>
</html>