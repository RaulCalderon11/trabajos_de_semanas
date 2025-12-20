<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acceso Institucional | Gestión Tesis</title>
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    
    <style>
        :root {
            --primary-color: #1e3a8a; /* Azul Institucional */
            --primary-dark: #172554;
            --accent-color: #f59e0b;
            --bg-color: #f8fafc;
            --text-muted: #64748b;
        }

        body { 
            background-color: var(--bg-color); 
            height: 100vh; /* Altura fija para centrado vertical */
            width: 100vw;
            display: flex; 
            align-items: center; 
            justify-content: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            overflow: hidden; /* Evita scrollbars en desktop */
            margin: 0;
        }

        .main-wrapper {
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-card {
            width: 900px;
            max-width: 90%;
            height: auto;
            min-height: 550px;
            background: white;
            border-radius: 24px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            overflow: hidden;
            display: flex;
            flex-direction: row;
        }

        /* --- SECCIÓN IZQUIERDA (IMAGEN) --- */
        .image-section {
            flex: 1;
            /* Imagen de fondo académica/institucional */
            background: linear-gradient(135deg, rgba(30, 58, 138, 0.85), rgba(23, 37, 84, 0.9)), 
                        url('https://images.unsplash.com/photo-1523050854058-8df90110c9f1?q=80&w=1000&auto=format&fit=crop');
            background-size: cover;
            background-position: center;
            color: white;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 40px;
            text-align: center;
            position: relative;
        }

        .brand-content {
            z-index: 2;
        }

        .logo-box {
            width: 80px;
            height: 80px;
            background: rgba(255,255,255,0.15);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2.5rem;
            margin-bottom: 1.5rem;
            border: 1px solid rgba(255,255,255,0.2);
        }

        /* --- SECCIÓN DERECHA (LOGIN) --- */
        .form-section {
            flex: 1;
            padding: 50px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            background: white;
        }

        /* Inputs Modernos */
        .form-floating > .form-control {
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            background-color: #f8fafc;
            transition: all 0.3s;
        }
        
        .form-floating > .form-control:focus {
            background-color: #fff;
            border-color: var(--primary-color);
            box-shadow: 0 0 0 4px rgba(30, 58, 138, 0.1);
        }

        .btn-primary-custom {
            background-color: var(--primary-color);
            border: none;
            padding: 14px;
            font-weight: 600;
            border-radius: 12px;
            font-size: 1rem;
            width: 100%;
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .btn-primary-custom:hover {
            background-color: var(--primary-dark);
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(30, 58, 138, 0.15);
        }

        .forgot-link {
            color: var(--text-muted);
            font-size: 0.9rem;
            transition: color 0.2s;
        }
        .forgot-link:hover { color: var(--primary-color); }

        /* --- RESPONSIVE --- */
        @media (max-width: 768px) {
            body { overflow-y: auto; height: auto; padding: 20px; } /* Permitir scroll en móviles */
            .login-card { flex-direction: column; height: auto; min-height: auto; }
            .image-section { padding: 40px 20px; min-height: 200px; }
            .form-section { padding: 40px 30px; }
            .logo-box { width: 60px; height: 60px; font-size: 2rem; }
        }
    </style>
</head>
<body>

<div class="main-wrapper animate__animated animate__fadeIn">
    <div class="login-card">
        
        <!-- SECCIÓN IZQUIERDA: IMAGEN Y BRANDING -->
        <div class="image-section">
            <div class="brand-content">
                <div class="d-flex justify-content-center">
                    <div class="logo-box">
                        <i class="bi bi-mortarboard-fill"></i>
                    </div>
                </div>
                <h2 class="fw-bold mb-2">Plataforma Académica</h2>
                <p class="mb-0 opacity-75" style="font-weight: 300; line-height: 1.6;">Gestión integral de tesis y proyectos de investigación.</p>
                
                <div class="mt-4 pt-4 border-top border-white border-opacity-25 w-100 d-none d-md-block">
                    <small class="text-uppercase" style="letter-spacing: 2px; font-size: 0.7rem;">Sistema Seguro V2.0</small>
                </div>
            </div>
        </div>

        <!-- SECCIÓN DERECHA: FORMULARIO LOGIN -->
        <div class="form-section">
            <div class="mb-4">
                <h3 class="fw-bold text-dark mb-1">Bienvenido</h3>
                <p class="text-muted small">Por favor, ingrese sus credenciales.</p>
            </div>

            <% if(request.getAttribute("error") != null) { %>
                <div class="alert alert-danger d-flex align-items-center mb-4 p-3 border-0 bg-danger bg-opacity-10 text-danger rounded-3 small">
                    <i class="bi bi-exclamation-circle-fill me-2 fs-5"></i>
                    <div><%= request.getAttribute("error") %></div>
                </div>
            <% } %>

            <form action="LoginServlet" method="post">
                <div class="form-floating mb-3">
                    <input type="text" name="codigo" class="form-control" id="floatingInput" placeholder="Usuario" required autocomplete="username">
                    <label for="floatingInput" class="text-muted"><i class="bi bi-person me-2"></i>Usuario / DNI</label>
                </div>
                
                <div class="form-floating mb-4">
                    <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Contraseña" required>
                    <label for="floatingPassword" class="text-muted"><i class="bi bi-lock-fill me-2"></i>Contraseña</label>
                </div>

                <div class="mb-4">
                    <button type="submit" class="btn btn-primary btn-primary-custom text-white">
                        Iniciar Sesión
                    </button>
                </div>

                <div class="text-center">
                    <a href="#" onclick="notificarAdmin()" class="text-decoration-none forgot-link">
                        ¿Olvidaste tu contraseña?
                    </a>
                </div>
            </form>
            
            <div class="text-center mt-auto pt-4">
                <small class="text-muted opacity-50" style="font-size: 0.7rem;">&copy; 2024 Dirección Académica</small>
            </div>
        </div>
        
    </div>
</div>

<script>
    function notificarAdmin() {
        let link = document.querySelector('.forgot-link');
        let originalText = link.innerHTML;
        link.innerHTML = '<span class="text-success fw-bold"><i class="bi bi-check2"></i> Solicitud enviada</span>';
        link.style.pointerEvents = 'none';
        setTimeout(() => {
            link.innerHTML = originalText;
            link.style.pointerEvents = 'auto';
        }, 2000);
    }
</script>

</body>
</html>