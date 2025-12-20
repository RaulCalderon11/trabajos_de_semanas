<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.gestiontesis.modelo.*" %>
<%
    // ==========================================
    // LOGICA ORIGINAL INTACTA
    // ==========================================
    
    // Verificación de sesión/datos
    if (request.getAttribute("stats") == null) { response.sendRedirect("AdminController"); return; }

    Map<String, Integer> stats = (Map<String, Integer>) request.getAttribute("stats");
    
    // Listas
    List<Usuario> listaAdmins = (List<Usuario>) request.getAttribute("listaAdmins");
    List<Usuario> listaDocentes = (List<Usuario>) request.getAttribute("listaDocentes");
    List<Usuario> listaAlumnos = (List<Usuario>) request.getAttribute("listaAlumnos");
    List<Tesis> listaTesis = (List<Tesis>) request.getAttribute("listaTesis");
    
    // Mapas para selects
    Map<Integer, String> mapAlumnos = (Map<Integer, String>) request.getAttribute("mapAlumnos");
    Map<Integer, String> mapDocentes = (Map<Integer, String>) request.getAttribute("mapDocentes");

    // Datos para el Acta (si existen, enviado por el controlador)
    Map<String, Object> acta = (Map<String, Object>) request.getAttribute("datosActa");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Administrativo | Gestión Tesis</title>
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    
    <style>
        :root {
            --primary-color: #1e3a8a; /* Azul Institucional Profundo */
            --primary-light: #3b82f6;
            --accent-color: #f59e0b; /* Dorado suave para detalles */
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
            background: linear-gradient(180deg, var(--primary-color) 0%, #172554 100%);
            width: var(--sidebar-width);
            min-height: 100vh;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 100;
            transition: all 0.3s;
            box-shadow: 4px 0 10px rgba(0,0,0,0.1);
        }

        .sidebar-header {
            padding: 2rem 1.5rem;
            color: white;
            border-bottom: 1px solid rgba(255,255,255,0.1);
        }

        .sidebar .nav-link {
            color: rgba(255,255,255,0.7);
            padding: 12px 20px;
            margin: 5px 15px;
            border-radius: 8px;
            font-weight: 500;
            transition: all 0.2s;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .sidebar .nav-link:hover {
            color: white;
            background: rgba(255,255,255,0.1);
            transform: translateX(5px);
        }

        .sidebar .nav-link.active {
            color: white;
            background: var(--primary-light);
            box-shadow: 0 4px 6px rgba(0,0,0,0.2);
        }

        .sidebar .btn-logout {
            color: #fca5a5;
            margin-top: auto;
        }
        .sidebar .btn-logout:hover {
            background: rgba(239, 68, 68, 0.2);
            color: #fca5a5;
        }

        /* --- MAIN CONTENT --- */
        .main-content {
            margin-left: var(--sidebar-width);
            padding: 2rem;
            transition: margin-left 0.3s;
        }

        /* --- CARDS & STATS --- */
        .card-custom {
            border: none;
            border-radius: 12px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.05);
            background: white;
            transition: transform 0.2s;
        }
        
        .card-stat {
            position: relative;
            overflow: hidden;
        }
        
        .card-stat .icon-bg {
            position: absolute;
            right: -10px;
            bottom: -10px;
            font-size: 5rem;
            opacity: 0.15;
            transform: rotate(-15deg);
        }

        /* --- TABLES --- */
        .table-custom thead {
            background-color: var(--primary-color);
            color: white;
        }
        .table-custom th {
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.85rem;
            letter-spacing: 0.5px;
            padding: 12px;
            border: none;
        }
        .table-custom td {
            vertical-align: middle;
            padding: 12px;
            border-bottom: 1px solid #e5e7eb;
        }
        .table-hover tbody tr:hover {
            background-color: #f8fafc;
        }

        /* --- UTILS --- */
        .search-box {
            border-radius: 20px;
            border: 1px solid #ced4da;
            padding-left: 15px;
            background: #fff;
        }
        
        .section-title {
            color: var(--primary-color);
            font-weight: 700;
            margin-bottom: 1.5rem;
            position: relative;
            display: inline-block;
        }
        
        .section-title::after {
            content: '';
            position: absolute;
            bottom: -5px;
            left: 0;
            width: 50px;
            height: 3px;
            background: var(--accent-color);
            border-radius: 2px;
        }

        /* --- BADGES --- */
        .badge-status {
            padding: 6px 12px;
            border-radius: 20px;
            font-weight: 500;
            font-size: 0.75rem;
        }

        /* --- RESPONSIVE --- */
        @media (max-width: 768px) {
            .sidebar { transform: translateX(-100%); width: 100%; z-index: 999; }
            .sidebar.show { transform: translateX(0); }
            .main-content { margin-left: 0; }
        }

        /* Estilos Originales para el Acta (Preservados para impresión) */
        .acta-paper { font-family: "Times New Roman", serif; padding: 40px; line-height: 1.8; color: #000; background: #fff; border: 1px solid #ddd; }
        .acta-header { text-align: center; margin-bottom: 40px; border-bottom: 3px double #000; padding-bottom: 20px; }
        .acta-title { font-weight: bold; text-transform: uppercase; margin: 25px 0; text-align: center; font-size: 1.3rem; letter-spacing: 1px; }
        .acta-nota { border: 2px solid #000; padding: 15px; width: 140px; text-align: center; margin: 30px auto; font-weight: bold; font-size: 1.4rem; }
        .acta-firmas { display: flex; justify-content: space-between; margin-top: 100px; }
        .firma-box { text-align: center; width: 30%; border-top: 1px solid #000; padding-top: 10px; font-size: 0.9rem; }
    </style>
</head>
<body>

<!-- Mobile Toggle -->
<button class="btn btn-primary d-md-none position-fixed top-0 start-0 m-3 z-3" type="button" data-bs-toggle="offcanvas" data-bs-target="#sidebarOffcanvas">
    <i class="bi bi-list"></i>
</button>

<div class="d-flex">
    <!-- Sidebar Desktop -->
    <div class="sidebar d-none d-md-flex flex-column">
        <div class="sidebar-header text-center">
            <h4 class="mb-0 fw-bold"><i class="bi bi-mortarboard-fill me-2"></i>GestiónTesis</h4>
            <small class="text-white-50">Panel Administrativo</small>
        </div>
        
        <div class="nav flex-column nav-pills mt-4 px-2" id="v-pills-tab" role="tablist">
            <a class="nav-link active" id="tab-dashboard" data-bs-toggle="pill" href="#content-dashboard">
                <i class="bi bi-grid-1x2-fill"></i> Dashboard
            </a>
            <a class="nav-link" id="tab-usuarios" data-bs-toggle="pill" href="#content-usuarios">
                <i class="bi bi-people-fill"></i> Usuarios
            </a>
            <a class="nav-link" id="tab-tesis" data-bs-toggle="pill" href="#content-tesis">
                <i class="bi bi-journal-bookmark-fill"></i> Tesis
            </a>
        </div>

        <div class="mt-auto px-2 pb-4">
            <hr class="bg-white opacity-25">
            <a href="LoginServlet?logout=true" class="nav-link btn-logout">
                <i class="bi bi-box-arrow-left"></i> Cerrar Sesión
            </a>
        </div>
    </div>

    <!-- Main Content Area -->
    <div class="main-content flex-grow-1 w-100">
        
        <!-- Alertas -->
        <% if(request.getParameter("msg") != null) { %>
            <div class="alert alert-success alert-dismissible fade show shadow-sm border-0 mb-4" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i> <strong>Mensaje del sistema:</strong> <%= request.getParameter("msg") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <div class="tab-content" id="v-pills-tabContent">
            
            <!-- 1. DASHBOARD TAB -->
            <div class="tab-pane fade show active" id="content-dashboard">
                <h3 class="section-title">Resumen General</h3>
                
                <div class="row g-4">
                    <!-- Stat Card 1 -->
                    <div class="col-md-4">
                        <div class="card card-custom card-stat bg-primary text-white h-100 p-3">
                            <div class="card-body">
                                <h6 class="text-uppercase opacity-75 fw-bold">Tesis Registradas</h6>
                                <h2 class="display-4 fw-bold mb-0"><%= stats.getOrDefault("total_tesis", 0) %></h2>
                                <i class="bi bi-journal-text icon-bg"></i>
                            </div>
                        </div>
                    </div>
                    <!-- Stat Card 2 -->
                    <div class="col-md-4">
                        <div class="card card-custom card-stat bg-success text-white h-100 p-3" style="background-color: #059669 !important;">
                            <div class="card-body">
                                <h6 class="text-uppercase opacity-75 fw-bold">Aprobadas/Sust.</h6>
                                <h2 class="display-4 fw-bold mb-0"><%= stats.getOrDefault("tesis_aprobadas", 0) %></h2>
                                <i class="bi bi-award-fill icon-bg"></i>
                            </div>
                        </div>
                    </div>
                    <!-- Stat Card 3 -->
                    <div class="col-md-4">
                        <div class="card card-custom card-stat bg-secondary text-white h-100 p-3" style="background-color: #475569 !important;">
                            <div class="card-body">
                                <h6 class="text-uppercase opacity-75 fw-bold">Usuarios Totales</h6>
                                <h2 class="display-4 fw-bold mb-0"><%= stats.getOrDefault("total_usuarios", 0) %></h2>
                                <i class="bi bi-people-fill icon-bg"></i>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row mt-4">
                    <div class="col-12">
                        <div class="card card-custom p-4 text-center text-muted">
                            <i class="bi bi-bar-chart-line display-1 mb-3 opacity-25"></i>
                            <h5>Bienvenido al Sistema de Gestión de Tesis</h5>
                            <p>Seleccione una opción del menú lateral para comenzar a administrar.</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 2. USUARIOS TAB -->
            <div class="tab-pane fade" id="content-usuarios">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h3 class="section-title mb-0">Directorio de Usuarios</h3>
                    <button class="btn btn-primary shadow-sm rounded-pill px-4" data-bs-toggle="modal" data-bs-target="#modalCrearUsuario">
                        <i class="bi bi-plus-lg me-1"></i> Nuevo Usuario
                    </button>
                </div>

                <!-- Acordeon o Tarjetas separadas para mejor organización -->
                <div class="row g-4">
                    
                    <!-- ADMINISTRADORES -->
                    <div class="col-12">
                        <div class="card card-custom">
                            <div class="card-header bg-white border-0 pt-4 px-4 d-flex justify-content-between align-items-center flex-wrap gap-2">
                                <h5 class="fw-bold text-dark mb-0"><i class="bi bi-shield-lock text-primary me-2"></i>Administradores</h5>
                                <input type="text" class="form-control form-control-sm search-box" style="max-width: 250px;" placeholder="Buscar admin..." onkeyup="filtrarTabla('tablaAdmins', this.value)">
                            </div>
                            <div class="card-body p-0">
                                <div class="table-responsive">
                                    <table class="table table-custom table-hover mb-0" id="tablaAdmins">
                                        <thead><tr><th>Nombre</th><th>Email</th><th class="text-end pe-4">Acciones</th></tr></thead>
                                        <tbody>
                                            <% if(listaAdmins != null) for(Usuario u : listaAdmins) { %>
                                            <tr>
                                                <td class="px-4 fw-bold text-secondary"><%= u.getNombre() %></td>
                                                <td class="px-4"><%= u.getEmail() %></td>
                                                <td class="text-end px-4">
                                                    <button class="btn btn-sm btn-outline-primary border-0 rounded-circle" title="Editar" onclick="editarUsuario('<%=u.getId()%>','admin','<%=u.getNombre()%>','<%=u.getEmail()%>','','<%=u.getPassword()%>')"><i class="bi bi-pencil-fill"></i></button>
                                                </td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- DOCENTES -->
                    <div class="col-12">
                        <div class="card card-custom">
                            <div class="card-header bg-white border-0 pt-4 px-4 d-flex justify-content-between align-items-center flex-wrap gap-2">
                                <h5 class="fw-bold text-dark mb-0"><i class="bi bi-person-video3 text-success me-2"></i>Docentes</h5>
                                <input type="text" class="form-control form-control-sm search-box" style="max-width: 250px;" placeholder="Buscar docente..." onkeyup="filtrarTabla('tablaDocentes', this.value)">
                            </div>
                            <div class="card-body p-0">
                                <div class="table-responsive" style="max-height: 400px;">
                                    <table class="table table-custom table-hover mb-0" id="tablaDocentes">
                                        <thead><tr><th>DNI</th><th>Nombre</th><th>Email</th><th class="text-end pe-4">Acciones</th></tr></thead>
                                        <tbody>
                                            <% if(listaDocentes != null) for(Usuario u : listaDocentes) { %>
                                            <tr>
                                                <td class="px-4 font-monospace text-muted"><%= u.getCodigo() %></td>
                                                <td class="px-4 fw-bold text-secondary"><%= u.getNombre() %></td>
                                                <td class="px-4"><%= u.getEmail() %></td>
                                                <td class="text-end px-4">
                                                    <button class="btn btn-sm btn-outline-primary border-0 rounded-circle me-1" title="Editar" onclick="editarUsuario('<%=u.getId()%>','docente','<%=u.getNombre()%>','<%=u.getEmail()%>','<%=u.getCodigo()%>','<%=u.getPassword()%>')"><i class="bi bi-pencil-fill"></i></button>
                                                    <button class="btn btn-sm btn-outline-danger border-0 rounded-circle" title="Eliminar" onclick="eliminarUsuario('<%=u.getId()%>','docente')"><i class="bi bi-trash-fill"></i></button>
                                                </td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- ALUMNOS -->
                    <div class="col-12 mb-4">
                        <div class="card card-custom">
                            <div class="card-header bg-white border-0 pt-4 px-4 d-flex justify-content-between align-items-center flex-wrap gap-2">
                                <h5 class="fw-bold text-dark mb-0"><i class="bi bi-backpack4 text-info me-2"></i>Alumnos</h5>
                                <input type="text" class="form-control form-control-sm search-box" style="max-width: 250px;" placeholder="Buscar alumno..." onkeyup="filtrarTabla('tablaAlumnos', this.value)">
                            </div>
                            <div class="card-body p-0">
                                <div class="table-responsive" style="max-height: 400px;">
                                    <table class="table table-custom table-hover mb-0" id="tablaAlumnos">
                                        <thead><tr><th>Código</th><th>Nombre</th><th>Email</th><th class="text-end pe-4">Acciones</th></tr></thead>
                                        <tbody>
                                            <% if(listaAlumnos != null) for(Usuario u : listaAlumnos) { %>
                                            <tr>
                                                <td class="px-4 font-monospace text-muted"><%= u.getCodigo() %></td>
                                                <td class="px-4 fw-bold text-secondary"><%= u.getNombre() %></td>
                                                <td class="px-4"><%= u.getEmail() %></td>
                                                <td class="text-end px-4">
                                                    <button class="btn btn-sm btn-outline-primary border-0 rounded-circle me-1" title="Editar" onclick="editarUsuario('<%=u.getId()%>','alumno','<%=u.getNombre()%>','<%=u.getEmail()%>','<%=u.getCodigo()%>','<%=u.getPassword()%>')"><i class="bi bi-pencil-fill"></i></button>
                                                    <button class="btn btn-sm btn-outline-danger border-0 rounded-circle" title="Eliminar" onclick="eliminarUsuario('<%=u.getId()%>','alumno')"><i class="bi bi-trash-fill"></i></button>
                                                </td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <!-- 3. TESIS TAB -->
            <div class="tab-pane fade" id="content-tesis">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h3 class="section-title mb-0">Gestión de Tesis</h3>
                    <div class="d-flex gap-2">
                        <input type="text" class="form-control search-box" placeholder="Filtrar tesis..." onkeyup="filtrarTabla('tablaTesisMain', this.value)">
                        <button class="btn btn-success shadow-sm text-nowrap rounded-pill px-3" data-bs-toggle="modal" data-bs-target="#modalAsignarTesis">
                            <i class="bi bi-plus-circle-fill me-1"></i> Asignar
                        </button>
                    </div>
                </div>

                <div class="card card-custom mb-5">
                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table table-custom table-hover align-middle mb-0" id="tablaTesisMain">
                                <thead>
                                    <tr>
                                        <th class="ps-4">ID</th>
                                        <th style="width: 30%;">Título</th>
                                        <th>Alumno</th>
                                        <th>Asesor</th>
                                        <th>Estado</th>
                                        <th>Detalles</th>
                                        <th class="text-end pe-4">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% if(listaTesis != null) for(Tesis t : listaTesis) { 
                                         String estado = t.getEstado();
                                         String badgeClass = "bg-secondary";
                                         if("Aprobado".equals(estado)) badgeClass = "bg-success";
                                         else if("En Jurado".equals(estado)) badgeClass = "bg-warning text-dark";
                                         else if("Apto para Sustentar".equals(estado)) badgeClass = "bg-info text-white";
                                         else if("Sustentación Programada".equals(estado)) badgeClass = "bg-primary";
                                         else if("Sustentado".equals(estado)) badgeClass = "bg-dark";
                                         else if("Rechazado".equals(estado)) badgeClass = "bg-danger";
                                    %>
                                    <tr>
                                        <td class="ps-4 fw-bold text-muted">#<%= t.getId() %></td>
                                        <td>
                                            <span class="fw-bold text-dark d-block text-truncate" style="max-width: 300px;" title="<%= t.getTitulo() %>"><%= t.getTitulo() %></span>
                                        </td>
                                        <td class="text-muted"><i class="bi bi-person me-1"></i><%= t.getNombreAlumno() %></td>
                                        <td class="text-muted"><i class="bi bi-eyeglasses me-1"></i><%= t.getDescripcion() %></td>
                                        <td><span class="badge badge-status <%=badgeClass%>"><%= estado %></span></td>
                                        <td>
                                            <% if(t.getFechaSustentacion() != null) { %>
                                                <div class="small">
                                                    <div class="fw-bold text-primary"><i class="bi bi-calendar-event me-1"></i><%= t.getFechaSustentacion().replace("T", " ") %></div>
                                                    <div class="text-muted"><i class="bi bi-geo-alt me-1"></i><%= t.getLugarSustentacion() %></div>
                                                </div>
                                            <% } else { %>
                                                <span class="text-muted small opacity-50">-</span>
                                            <% } %>
                                        </td>
                                        <td class="text-end pe-4">
                                            <div class="btn-group" role="group">
                                                <!-- Editar -->
                                                <button class="btn btn-sm btn-outline-secondary border-0" onclick="editarTesis('<%=t.getId()%>', '<%=t.getTitulo()%>')" data-bs-toggle="tooltip" title="Editar Tesis">
                                                    <i class="bi bi-pencil-square fs-6"></i>
                                                </button>
                                                
                                                <!-- Fase 2 -->
                                                <% if ("Aprobado".equals(estado)) { %>
                                                    <button class="btn btn-sm btn-dark" onclick="abrirDesignarJurado('<%=t.getId()%>')" title="Designar Jurado">
                                                        <i class="bi bi-gavel"></i>
                                                    </button>
                                                <% } %>

                                                <!-- Fase 3 -->
                                                <% if ("Apto para Sustentar".equals(estado)) { %>
                                                    <button class="btn btn-sm btn-outline-warning border-0" onclick="abrirProgramarSustentacion('<%=t.getId()%>')" data-bs-toggle="tooltip" title="Programar">
                                                        <i class="bi bi-calendar-check fs-6"></i>
                                                    </button>
                                                <% } %>

                                                <!-- Fase 4 -->
                                                <% if ("Sustentación Programada".equals(estado)) { %>
                                                    <button class="btn btn-sm btn-outline-success border-0" onclick="abrirRegistrarResultado('<%=t.getId()%>')" data-bs-toggle="tooltip" title="Resultado">
                                                        <i class="bi bi-check-circle fs-6"></i>
                                                    </button>
                                                <% } %>

                                                <!-- Acta -->
                                                <% if ("Sustentado".equals(estado)) { %>
                                                    <a href="AdminController?action=verActa&idTesis=<%=t.getId()%>" class="btn btn-sm btn-outline-dark border-0" data-bs-toggle="tooltip" title="Ver Acta">
                                                        <i class="bi bi-file-earmark-pdf fs-6"></i>
                                                    </a>
                                                <% } %>

                                                <!-- Eliminar -->
                                                <form action="AdminController" method="post" class="d-inline" onsubmit="return confirm('¿Seguro que deseas ELIMINAR esta tesis y todo su historial?');">
                                                    <input type="hidden" name="action" value="eliminarTesis">
                                                    <input type="hidden" name="idTesis" value="<%= t.getId() %>">
                                                    <button type="submit" class="btn btn-sm btn-outline-danger border-0" data-bs-toggle="tooltip" title="Eliminar"><i class="bi bi-trash fs-6"></i></button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            
        </div> <!-- End Tab Content -->
    </div>
</div>

<!-- ================= MODALES (ESTILO MEJORADO) ================= -->

<!-- FORMULARIO ELIMINAR (Oculto) -->
<form id="formEliminarUsuario" action="AdminController" method="post" style="display:none;">
    <input type="hidden" name="action" value="eliminarUsuario">
    <input type="hidden" name="idUsuario" id="delId">
    <input type="hidden" name="rolUsuario" id="delRol">
</form>

<!-- MODAL CREAR USUARIO -->
<div class="modal fade" id="modalCrearUsuario" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <form action="AdminController" method="post" class="w-100">
            <div class="modal-content border-0 shadow">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title fw-bold">Nuevo Usuario</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4 bg-light">
                    <input type="hidden" name="action" value="crearUsuario">
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Tipo de Usuario</label>
                        <select name="tipoUsuario" class="form-select" onchange="toggleCodigo(this.value)">
                            <option value="alumno">Alumno</option>
                            <option value="docente">Docente</option>
                            <option value="admin">Administrador</option>
                        </select>
                    </div>
                    <div class="mb-3" id="divCodigo">
                        <label class="form-label fw-bold small text-muted">DNI / Código</label>
                        <input type="text" name="codigo" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Nombre Completo</label>
                        <input type="text" name="nombre" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Email Institucional</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Contraseña</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>
                </div>
                <div class="modal-footer bg-light">
                    <button type="button" class="btn btn-link text-muted text-decoration-none" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-primary px-4">Guardar Usuario</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- MODAL EDITAR USUARIO -->
<div class="modal fade" id="modalEditarUsuario" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <form action="AdminController" method="post" class="w-100">
            <div class="modal-content border-0 shadow">
                <div class="modal-header bg-warning">
                    <h5 class="modal-title fw-bold text-dark">Editar Usuario</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4">
                    <input type="hidden" name="action" value="editarUsuario">
                    <input type="hidden" name="idUsuario" id="editId">
                    <input type="hidden" name="rolUsuario" id="editRol">
                    
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Nombre</label>
                        <input type="text" name="nombre" id="editNombre" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Email</label>
                        <input type="email" name="email" id="editEmail" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">DNI / Código</label>
                        <input type="text" name="codigo" id="editCodigo" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Contraseña</label>
                        <input type="text" name="password" id="editPass" class="form-control" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-warning px-4 fw-bold">Actualizar Datos</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- MODAL ASIGNAR TESIS (Fase 1) -->
<div class="modal fade" id="modalAsignarTesis" tabindex="-1">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <form action="AdminController" method="post" enctype="multipart/form-data" class="w-100">
            <div class="modal-content border-0 shadow">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title fw-bold">Asignar Nueva Tesis</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4 bg-light">
                    <input type="hidden" name="action" value="asignarTesis">
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Título del Proyecto</label>
                        <input type="text" name="titulo" class="form-control form-control-lg" placeholder="Ingrese el título oficial..." required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Descripción / Resumen</label>
                        <textarea name="descripcion" class="form-control" rows="2"></textarea>
                    </div>
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label fw-bold small text-muted">Alumno</label>
                            <select name="alumnoId" class="form-select" required>
                                <% if(mapAlumnos != null) for(Map.Entry<Integer, String> e : mapAlumnos.entrySet()) { %>
                                    <option value="<%=e.getKey()%>"><%=e.getValue()%></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-bold small text-muted">Docente Asesor</label>
                            <select name="docenteId" class="form-select" required>
                                <% if(mapDocentes != null) for(Map.Entry<Integer, String> e : mapDocentes.entrySet()) { %>
                                    <option value="<%=e.getKey()%>"><%=e.getValue()%></option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="mt-4 p-3 bg-white border rounded">
                        <label class="form-label fw-bold text-success"><i class="bi bi-file-earmark-pdf-fill"></i> Archivo Plan (PDF)</label>
                        <input type="file" name="archivoInicial" class="form-control" accept="application/pdf">
                    </div>
                </div>
                <div class="modal-footer bg-light">
                    <button type="button" class="btn btn-link text-decoration-none text-muted" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-success px-4">Registrar Tesis</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- MODAL EDITAR TESIS -->
<div class="modal fade" id="modalEditarTesis" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <form action="AdminController" method="post" enctype="multipart/form-data" class="w-100">
            <div class="modal-content border-0 shadow">
                <div class="modal-header bg-info text-white">
                    <h5 class="modal-title fw-bold">Editar Tesis</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4">
                    <input type="hidden" name="action" value="editarTesis">
                    <input type="hidden" name="idTesis" id="editTesisId">
                    
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Título</label>
                        <input type="text" name="titulo" id="editTesisTitulo" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Reasignar Docente Asesor</label>
                        <select name="docenteId" class="form-select" required>
                            <% if(mapDocentes != null) for(Map.Entry<Integer, String> e : mapDocentes.entrySet()) { %>
                                <option value="<%=e.getKey()%>"><%=e.getValue()%></option>
                            <% } %>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Actualizar Archivo (Opcional)</label>
                        <input type="file" name="archivoNuevo" class="form-control" accept="application/pdf">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-info text-white px-4">Guardar Cambios</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- MODAL DESIGNAR JURADO -->
<div class="modal fade" id="modalDesignarJurado" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <form action="AdminController" method="post" class="w-100">
            <div class="modal-content border-0 shadow">
                <div class="modal-header bg-dark text-white">
                    <h5 class="modal-title"><i class="bi bi-gavel me-2"></i>Designar Jurado Calificador</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4 bg-light">
                    <input type="hidden" name="action" value="designarJurado">
                    <input type="hidden" name="idTesis" id="juradoTesisId">
                    
                    <div class="alert alert-secondary small">
                        Seleccione 3 docentes distintos para evaluar la sustentación.
                    </div>
                    
                    <% 
                    String optionsDocentes = "";
                    if(mapDocentes != null) for(Map.Entry<Integer, String> e : mapDocentes.entrySet()) { 
                        optionsDocentes += "<option value='"+e.getKey()+"'>"+e.getValue()+"</option>";
                    } 
                    %>

                    <div class="form-floating mb-3">
                        <select name="jurado1" class="form-select" id="floatingJ1" required>
                            <option value="">Seleccione...</option>
                            <%= optionsDocentes %>
                        </select>
                        <label for="floatingJ1">Presidente de Jurado</label>
                    </div>
                    <div class="form-floating mb-3">
                        <select name="jurado2" class="form-select" id="floatingJ2" required>
                            <option value="">Seleccione...</option>
                            <%= optionsDocentes %>
                        </select>
                        <label for="floatingJ2">Secretario</label>
                    </div>
                    <div class="form-floating mb-3">
                        <select name="jurado3" class="form-select" id="floatingJ3" required>
                            <option value="">Seleccione...</option>
                            <%= optionsDocentes %>
                        </select>
                        <label for="floatingJ3">Vocal</label>
                    </div>
                </div>
                <div class="modal-footer bg-light">
                    <button type="submit" class="btn btn-dark w-100">Confirmar Designación</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- MODAL PROGRAMAR SUSTENTACIÓN -->
<div class="modal fade" id="modalProgramarSustentacion" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <form action="AdminController" method="post" class="w-100">
            <div class="modal-content border-0 shadow">
                <div class="modal-header bg-warning">
                    <h5 class="modal-title fw-bold text-dark"><i class="bi bi-calendar-check me-2"></i>Programar Sustentación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4">
                    <input type="hidden" name="action" value="programarSustentacion">
                    <input type="hidden" name="idTesis" id="sustTesisId">
                    
                    <p class="mb-4 text-muted small">La tesis ha sido aprobada por el jurado. Ingrese los datos para el acto.</p>

                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Fecha y Hora</label>
                        <input type="datetime-local" name="fechaHora" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold small text-muted">Lugar / Enlace</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-geo-alt"></i></span>
                            <input type="text" name="lugar" class="form-control" placeholder="Ej: Auditorio A" required>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-warning w-100 fw-bold">Programar Evento</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- MODAL REGISTRAR RESULTADO -->
<div class="modal fade" id="modalRegistrarResultado" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <form action="AdminController" method="post" class="w-100">
            <div class="modal-content border-0 shadow">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title fw-bold"><i class="bi bi-check2-circle me-2"></i>Resultado Final</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4">
                    <input type="hidden" name="action" value="registrarResultado">
                    <input type="hidden" name="idTesis" id="resTesisId">
                    
                    <div class="row g-3">
                        <div class="col-md-4">
                            <label class="form-label fw-bold small text-muted">Nota (0-20)</label>
                            <input type="number" name="notaSustentacion" class="form-control form-control-lg text-center fw-bold" min="0" max="20" step="0.5" required>
                        </div>
                        <div class="col-md-8">
                            <label class="form-label fw-bold small text-muted">Veredicto</label>
                            <select name="veredicto" class="form-select form-select-lg" required>
                                <option value="Aprobado por Unanimidad">Aprobado (Unanimidad)</option>
                                <option value="Aprobado por Mayoría">Aprobado (Mayoría)</option>
                                <option value="Desaprobado">Desaprobado</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success w-100">Cerrar Acta de Tesis</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- MODAL VER ACTA -->
<div class="modal fade" id="modalVerActa" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content border-0 shadow-lg">
            <div class="modal-header bg-dark text-white">
                <h5 class="modal-title">Vista Previa de Acta</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body bg-secondary bg-opacity-10 d-flex justify-content-center" id="contenidoActa">
                <% if(acta != null) { 
                    List<String> jurados = (List<String>) acta.get("jurados");
                %>
                    <div class="acta-paper shadow">
                        <div class="acta-header">
                            <h5 style="margin-bottom: 5px;">UNIVERSIDAD NACIONAL DEL CENTRO</h5>
                            <h6 class="text-muted" style="font-weight: normal;">FACULTAD DE INGENIERÍA DE SISTEMAS</h6>
                        </div>
                        <div class="text-center" style="margin-bottom: 30px;">
                            <strong>ACTA DE SUSTENTACIÓN N° <%= request.getAttribute("tesisIdActa") %></strong>
                        </div>
                        
                        <p style="text-align: justify; margin-bottom: 15px;">
                            En la ciudad de Huancayo, siendo las <strong><%= acta.get("fecha") != null ? acta.get("fecha").toString().replace("T", " horas del día ") : "____" %></strong>, 
                            se reunió el jurado calificador para evaluar la tesis titulada:
                        </p>
                        <div class="acta-title">"<%= acta.get("titulo") %>"</div>
                        <p style="text-align: justify; margin-bottom: 30px;">
                            Presentada por el bachiller <strong><%= acta.get("alumno") %></strong> (Código: <%= acta.get("codigo") %>), 
                            asesorado por el docente <strong><%= acta.get("asesor") %></strong>.
                        </p>
                        <p>Tras la deliberación, el jurado otorgó el siguiente resultado:</p>
                        
                        <div class="acta-nota">
                            NOTA: <%= acta.get("nota") %>
                        </div>
                        <p class="text-center" style="margin-bottom: 50px;">Veredicto: <strong><%= acta.get("veredicto").toString().toUpperCase() %></strong></p>

                        <div class="acta-firmas">
                             <% if(jurados != null && jurados.size() >= 3) { %>
                                <div class="firma-box"><%= jurados.get(0) %><br>Presidente</div>
                                <div class="firma-box"><%= jurados.get(1) %><br>Secretario</div>
                                <div class="firma-box"><%= jurados.get(2) %><br>Vocal</div>
                            <% } %>
                        </div>
                    </div>
                <% } %>
            </div>
            <div class="modal-footer bg-white">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cerrar</button>
                <button type="button" class="btn btn-primary" onclick="imprimirActa()"><i class="bi bi-printer-fill me-2"></i> Imprimir Oficial</button>
            </div>
        </div>
    </div>
</div>

<!-- SCRIPTS (Lógica intacta) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Tooltips Initialization
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
      return new bootstrap.Tooltip(tooltipTriggerEl)
    })

    function toggleCodigo(rol) {
        const div = document.getElementById('divCodigo');
        if (rol === 'admin') div.style.display = 'none';
        else div.style.display = 'block';
    }

    function editarUsuario(id, rol, nombre, email, codigo, pass) {
        document.getElementById('editId').value = id;
        document.getElementById('editRol').value = rol;
        document.getElementById('editNombre').value = nombre;
        document.getElementById('editEmail').value = email;
        document.getElementById('editCodigo').value = codigo;
        document.getElementById('editPass').value = pass;
        
        document.getElementById('editCodigo').disabled = (rol === 'admin');
        new bootstrap.Modal(document.getElementById('modalEditarUsuario')).show();
    }
    
    function eliminarUsuario(id, rol) {
        if(confirm('¿Está seguro de eliminar este usuario?')) {
            document.getElementById('delId').value = id;
            document.getElementById('delRol').value = rol;
            document.getElementById('formEliminarUsuario').submit();
        }
    }

    function editarTesis(id, titulo) {
        document.getElementById('editTesisId').value = id;
        document.getElementById('editTesisTitulo').value = titulo;
        new bootstrap.Modal(document.getElementById('modalEditarTesis')).show();
    }

    function abrirDesignarJurado(id) {
        document.getElementById('juradoTesisId').value = id;
        new bootstrap.Modal(document.getElementById('modalDesignarJurado')).show();
    }

    function abrirProgramarSustentacion(id) {
        document.getElementById('sustTesisId').value = id;
        new bootstrap.Modal(document.getElementById('modalProgramarSustentacion')).show();
    }

    function abrirRegistrarResultado(id) {
        document.getElementById('resTesisId').value = id;
        new bootstrap.Modal(document.getElementById('modalRegistrarResultado')).show();
    }

    function filtrarTabla(tablaId, texto) {
        const tabla = document.getElementById(tablaId);
        const filas = tabla.getElementsByTagName('tr');
        texto = texto.toLowerCase();

        for (let i = 1; i < filas.length; i++) {
            let mostrar = false;
            const celdas = filas[i].getElementsByTagName('td');
            for (let j = 0; j < celdas.length - 1; j++) { 
                if (celdas[j].innerText.toLowerCase().includes(texto)) {
                    mostrar = true;
                    break;
                }
            }
            filas[i].style.display = mostrar ? '' : 'none';
        }
    }

    function imprimirActa() {
        var contenido = document.getElementById('contenidoActa').innerHTML;
        var ventana = window.open('', '', 'height=600,width=800');
        ventana.document.write('<html><head><title>Imprimir Acta</title>');
        ventana.document.write('<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">');
        ventana.document.write('<style>body{padding:40px; font-family:"Times New Roman";} .acta-paper{width:100%; border:none;} .acta-header{text-align:center; border-bottom:2px double black; padding-bottom:10px; margin-bottom:30px;} .acta-title{text-align:center;font-weight:bold;text-transform:uppercase;margin:20px 0;} .acta-nota{border:2px solid black;width:150px;text-align:center;margin:20px auto;font-weight:bold;font-size:1.4em;padding:10px;} .acta-firmas{display:flex;justify-content:space-between;margin-top:100px;} .firma-box{border-top:1px solid black;width:30%;text-align:center;padding-top:5px;}</style>');
        ventana.document.write('</head><body>');
        ventana.document.write(contenido);
        ventana.document.write('</body></html>');
        ventana.document.close();
        ventana.focus();
        setTimeout(function(){ ventana.print(); ventana.close(); }, 500);
    }
    
    <% if(request.getAttribute("showActaModal") != null && (Boolean)request.getAttribute("showActaModal")) { %>
        window.onload = function() {
            var myModal = new bootstrap.Modal(document.getElementById('modalVerActa'));
            myModal.show();
        }
    <% } %>
</script>

<!-- Offcanvas Sidebar para Mobile -->
<div class="offcanvas offcanvas-start" tabindex="-1" id="sidebarOffcanvas" style="background: #1e3a8a;">
    <div class="offcanvas-header text-white">
        <h5 class="offcanvas-title">Gestión Tesis</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas"></button>
    </div>
    <div class="offcanvas-body">
        <!-- Copia del menú para mobile -->
         <div class="nav flex-column nav-pills">
            <a class="nav-link text-white mb-2" onclick="bootstrap.Offcanvas.getInstance(document.getElementById('sidebarOffcanvas')).hide()" data-bs-toggle="pill" href="#content-dashboard"><i class="bi bi-grid-1x2-fill"></i> Dashboard</a>
            <a class="nav-link text-white mb-2" onclick="bootstrap.Offcanvas.getInstance(document.getElementById('sidebarOffcanvas')).hide()" data-bs-toggle="pill" href="#content-usuarios"><i class="bi bi-people-fill"></i> Usuarios</a>
            <a class="nav-link text-white mb-2" onclick="bootstrap.Offcanvas.getInstance(document.getElementById('sidebarOffcanvas')).hide()" data-bs-toggle="pill" href="#content-tesis"><i class="bi bi-journal-bookmark-fill"></i> Tesis</a>
            <hr class="bg-white">
            <a href="LoginServlet?logout=true" class="nav-link text-danger"><i class="bi bi-box-arrow-left"></i> Salir</a>
        </div>
    </div>
</div>

</body>
</html>