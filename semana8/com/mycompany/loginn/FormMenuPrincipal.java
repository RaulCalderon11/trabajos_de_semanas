package com.mycompany.loginn;
import Clases.CConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class FormMenuPrincipal extends javax.swing.JFrame {
    private String rolUsuario;
    private String nombreUsuario;
    private int idUsuario;

    public FormMenuPrincipal(String rol, String nombre, int idUsuario) {
        initComponents();
        this.rolUsuario = rol;
        this.nombreUsuario = nombre;
        this.idUsuario = idUsuario;
        
        configurarVistaSegunRol();
        this.setLocationRelativeTo(null);
    }
    
    private void configurarVistaSegunRol() {
        // Ocultar todos los paneles primero
        pnlAdmin.setVisible(false);
        pnlProfesor.setVisible(false);
        pnlEstudiante.setVisible(false);
        
        // Configurar según el rol
        switch(rolUsuario) {
            case "admin":
                this.setTitle("Sistema de Gestión - Panel Administrativo");
                configurarVistaAdmin();
                break;
            case "profesor":
                this.setTitle("Sistema de Gestión - Vista Profesor");
                configurarVistaProfesor();
                break;
            case "estudiante":
                this.setTitle("Sistema de Gestión - Vista Estudiante");
                configurarVistaEstudiante();
                break;
        }
        
        jLabelBienvenida.setText("Bienvenido: " + nombreUsuario + " - Rol: " + rolUsuario);
    }
    
    private void configurarVistaAdmin() {
        // Mostrar solo panel de admin
        pnlAdmin.setVisible(true);
        pnlProfesor.setVisible(false);
        pnlEstudiante.setVisible(false);
        
        // Centrar el panel
        centrarPanel(pnlAdmin);
    }
    
    private void configurarVistaProfesor() {
        // Mostrar solo panel de profesor
        pnlAdmin.setVisible(false);
        pnlProfesor.setVisible(true);
        pnlEstudiante.setVisible(false);
        
        // Centrar el panel
        centrarPanel(pnlProfesor);
        
        // Configurar textos específicos para profesor
        lblTituloProfesor.setText("Panel del Profesor - " + nombreUsuario);
        btnDashboardProfesor.setText("Mi Dashboard");
        btnGestionCalificaciones.setText("Gestión de Calificaciones");
        btnListaEstudiantes.setText("Lista de Estudiantes");
    }

    private void configurarVistaEstudiante() {
        // Mostrar solo panel de estudiante
        pnlAdmin.setVisible(false);
        pnlProfesor.setVisible(false);
        pnlEstudiante.setVisible(true);
        
        // Centrar el panel
        centrarPanel(pnlEstudiante);
        
        // Configurar textos específicos para estudiante
        lblTituloEstudiante.setText("Panel del Estudiante - " + nombreUsuario);
        btnDashboardEstudiante.setText("Mi Dashboard");
        btnVerCalificaciones.setText("Mis Calificaciones");
        btnMiProgreso.setText("Mi Progreso");
        
        // DEBUG: Mostrar información del estudiante
        System.out.println("DEBUG ESTUDIANTE - ID: " + idUsuario + ", Nombre: " + nombreUsuario);
    }
    
    /**
     * Método para centrar cualquier panel en la ventana
     */
    private void centrarPanel(javax.swing.JPanel panel) {
        // Remover layout actual para centrar manualmente
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.removeAll();
        jPanel1.add(panel, java.awt.BorderLayout.CENTER);
        jPanel1.revalidate();
        jPanel1.repaint();
    }
    
    private int obtenerIdUsuario(String nombreUsuario) {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT id FROM Usuarios WHERE nombre = ?";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error obteniendo ID: " + e.toString());
        }
        return -1;
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabelBienvenida = new javax.swing.JLabel();
        pnlAdmin = new javax.swing.JPanel();
        btnAdminDashboard = new javax.swing.JButton();
        btnGestionProfesores = new javax.swing.JButton();
        btnGestionEstudiantes = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnConfiguracion = new javax.swing.JButton();
        pnlProfesor = new javax.swing.JPanel();
        lblTituloProfesor = new javax.swing.JLabel();
        btnDashboardProfesor = new javax.swing.JButton();
        btnGestionCalificaciones = new javax.swing.JButton();
        btnListaEstudiantes = new javax.swing.JButton();
        pnlEstudiante = new javax.swing.JPanel();
        lblTituloEstudiante = new javax.swing.JLabel();
        btnDashboardEstudiante = new javax.swing.JButton();
        btnVerCalificaciones = new javax.swing.JButton();
        btnMiProgreso = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 683, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Gestión");

        jPanel1.setPreferredSize(new java.awt.Dimension(550, 100));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabelBienvenida.setFont(new java.awt.Font("Snap ITC", 0, 24)); // NOI18N
        jLabelBienvenida.setText("BIENVENIDO");
        jPanel1.add(jLabelBienvenida, java.awt.BorderLayout.PAGE_START);

        pnlAdmin.setPreferredSize(new java.awt.Dimension(80, 50));
        pnlAdmin.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnAdminDashboard.setText("Panel Principal");
        btnAdminDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminDashboardActionPerformed(evt);
            }
        });
        pnlAdmin.add(btnAdminDashboard);

        btnGestionProfesores.setText("Gestionar Profesores");
        btnGestionProfesores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionProfesoresActionPerformed(evt);
            }
        });
        pnlAdmin.add(btnGestionProfesores);

        btnGestionEstudiantes.setText("Gestionar Estudiantes");
        btnGestionEstudiantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionEstudiantesActionPerformed(evt);
            }
        });
        pnlAdmin.add(btnGestionEstudiantes);

        btnReportes.setText("Reportes");
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });
        pnlAdmin.add(btnReportes);

        btnConfiguracion.setText("Configuración");
        btnConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracionActionPerformed(evt);
            }
        });
        pnlAdmin.add(btnConfiguracion);

        jPanel1.add(pnlAdmin, java.awt.BorderLayout.CENTER);

        lblTituloProfesor.setText("Panel del Profesor");

        btnDashboardProfesor.setText("Dashboard");
        btnDashboardProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardProfesorActionPerformed(evt);
            }
        });

        btnGestionCalificaciones.setText("Calificaciones");
        btnGestionCalificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionCalificacionesActionPerformed(evt);
            }
        });

        btnListaEstudiantes.setText("Lista de estudiantes");
        btnListaEstudiantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaEstudiantesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlProfesorLayout = new javax.swing.GroupLayout(pnlProfesor);
        pnlProfesor.setLayout(pnlProfesorLayout);
        pnlProfesorLayout.setHorizontalGroup(
            pnlProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfesorLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProfesorLayout.createSequentialGroup()
                        .addComponent(btnDashboardProfesor)
                        .addGap(36, 36, 36)
                        .addComponent(btnGestionCalificaciones)
                        .addGap(28, 28, 28)
                        .addComponent(btnListaEstudiantes))
                    .addComponent(lblTituloProfesor))
                .addContainerGap(552, Short.MAX_VALUE))
        );
        pnlProfesorLayout.setVerticalGroup(
            pnlProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfesorLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblTituloProfesor)
                .addGap(18, 18, 18)
                .addGroup(pnlProfesorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDashboardProfesor)
                    .addComponent(btnGestionCalificaciones)
                    .addComponent(btnListaEstudiantes))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(pnlProfesor, java.awt.BorderLayout.PAGE_END);

        lblTituloEstudiante.setText("Panel del Estudiante");

        btnDashboardEstudiante.setText("Dashboard");
        btnDashboardEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardEstudianteActionPerformed(evt);
            }
        });

        btnVerCalificaciones.setText("Calificaciones");
        btnVerCalificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCalificacionesActionPerformed(evt);
            }
        });

        btnMiProgreso.setText("Mi progreso");
        btnMiProgreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMiProgresoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEstudianteLayout = new javax.swing.GroupLayout(pnlEstudiante);
        pnlEstudiante.setLayout(pnlEstudianteLayout);
        pnlEstudianteLayout.setHorizontalGroup(
            pnlEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEstudianteLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnlEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEstudianteLayout.createSequentialGroup()
                        .addComponent(lblTituloEstudiante)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlEstudianteLayout.createSequentialGroup()
                        .addComponent(btnDashboardEstudiante)
                        .addGap(27, 27, 27)
                        .addComponent(btnMiProgreso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(btnVerCalificaciones)))
                .addContainerGap())
        );
        pnlEstudianteLayout.setVerticalGroup(
            pnlEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEstudianteLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblTituloEstudiante)
                .addGap(18, 18, 18)
                .addGroup(pnlEstudianteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDashboardEstudiante)
                    .addComponent(btnMiProgreso)
                    .addComponent(btnVerCalificaciones))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jPanel1.add(pnlEstudiante, java.awt.BorderLayout.LINE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jButtonSalir.setText("Salir");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSalir, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Módulo de Reportes - Próximamente");
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracionActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Configuración del Sistema - Próximamente");
    }//GEN-LAST:event_btnConfiguracionActionPerformed

    private void btnAdminDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminDashboardActionPerformed
        if (rolUsuario.equals("admin")) {
            FormAdminDashboard adminDashboard = new FormAdminDashboard(nombreUsuario);
            adminDashboard.setVisible(true);
            adminDashboard.setLocationRelativeTo(null);
            this.dispose();
        }
    }//GEN-LAST:event_btnAdminDashboardActionPerformed

    private void btnGestionCalificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionCalificacionesActionPerformed
        if (rolUsuario.equals("profesor")) {
            FormGestionCalificaciones gestionCalificaciones = new FormGestionCalificaciones(idUsuario, nombreUsuario);
            gestionCalificaciones.setVisible(true);
            gestionCalificaciones.setLocationRelativeTo(null);
            this.dispose();
        }
    }//GEN-LAST:event_btnGestionCalificacionesActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar Cierre de Sesión",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            FormLogin loginFrame = new FormLogin();
            loginFrame.setVisible(true);
            loginFrame.setLocationRelativeTo(null);
            this.dispose();
        }
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void btnGestionEstudiantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionEstudiantesActionPerformed
        if (rolUsuario.equals("admin")) {
            FormGestionEstudiantes gestionEstudiantes = new FormGestionEstudiantes();
            gestionEstudiantes.setVisible(true);
            gestionEstudiantes.setLocationRelativeTo(null);
            this.dispose();
        }
    }//GEN-LAST:event_btnGestionEstudiantesActionPerformed

    private void btnGestionProfesoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionProfesoresActionPerformed
        if (rolUsuario.equals("admin")) {
            FormGestionProfesores gestionProfesores = new FormGestionProfesores();
            gestionProfesores.setVisible(true);
            gestionProfesores.setLocationRelativeTo(null);
            this.dispose();
        }
    }//GEN-LAST:event_btnGestionProfesoresActionPerformed

    private void btnMiProgresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMiProgresoActionPerformed
        System.out.println("DEBUG: Botón Mi Progreso presionado");
        
        if (rolUsuario.equals("estudiante")) {
            try {
                FormProgresoEstudiante progresoEstudiante = new FormProgresoEstudiante(idUsuario, nombreUsuario);
                progresoEstudiante.setVisible(true);
                progresoEstudiante.setLocationRelativeTo(null);
                this.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al abrir Mi Progreso: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnMiProgresoActionPerformed

    private void btnVerCalificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCalificacionesActionPerformed
        System.out.println("DEBUG: Botón Ver Calificaciones presionado");
        
        if (rolUsuario.equals("estudiante")) {
            try {
                FormTodasCalificaciones todasCalificaciones = new FormTodasCalificaciones(idUsuario, nombreUsuario);
                todasCalificaciones.setVisible(true);
                todasCalificaciones.setLocationRelativeTo(null);
                this.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al abrir Calificaciones: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnVerCalificacionesActionPerformed

    private void btnDashboardEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardEstudianteActionPerformed
        System.out.println("DEBUG: Botón Dashboard Estudiante presionado");
        System.out.println("DEBUG - Rol: " + rolUsuario + ", ID: " + idUsuario);
        
        if (rolUsuario.equals("estudiante")) {
            try {
                FormDashboardEstudiante dashboardEstudiante = new FormDashboardEstudiante(idUsuario, nombreUsuario);
                dashboardEstudiante.setVisible(true);
                dashboardEstudiante.setLocationRelativeTo(null);
                this.dispose();
                System.out.println("DEBUG: FormDashboardEstudiante creado exitosamente");
            } catch (Exception e) {
                System.out.println("DEBUG ERROR: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Error al abrir el Dashboard: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Acceso denegado. Rol incorrecto: " + rolUsuario, 
                "Error de Acceso", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDashboardEstudianteActionPerformed

    private void btnListaEstudiantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaEstudiantesActionPerformed
        if (rolUsuario.equals("profesor")) {
            FormListaEstudiantes listaEstudiantes = new FormListaEstudiantes(idUsuario);
            listaEstudiantes.setVisible(true);
            listaEstudiantes.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_btnListaEstudiantesActionPerformed

    private void btnDashboardProfesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardProfesorActionPerformed
        if (rolUsuario.equals("profesor")) {
            FormDashboardProfesor dashboardProfesor = new FormDashboardProfesor(idUsuario, nombreUsuario);
            dashboardProfesor.setVisible(true);
            dashboardProfesor.setLocationRelativeTo(null);
            this.dispose();
        }
    }//GEN-LAST:event_btnDashboardProfesorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdminDashboard;
    private javax.swing.JButton btnConfiguracion;
    private javax.swing.JButton btnDashboardEstudiante;
    private javax.swing.JButton btnDashboardProfesor;
    private javax.swing.JButton btnGestionCalificaciones;
    private javax.swing.JButton btnGestionEstudiantes;
    private javax.swing.JButton btnGestionProfesores;
    private javax.swing.JButton btnListaEstudiantes;
    private javax.swing.JButton btnMiProgreso;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnVerCalificaciones;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JLabel jLabelBienvenida;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblTituloEstudiante;
    private javax.swing.JLabel lblTituloProfesor;
    private javax.swing.JPanel pnlAdmin;
    private javax.swing.JPanel pnlEstudiante;
    private javax.swing.JPanel pnlProfesor;
    // End of variables declaration//GEN-END:variables
}
