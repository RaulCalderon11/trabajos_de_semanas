package com.mycompany.loginn;

import Clases.CConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormAdminDashboard extends javax.swing.JFrame {

    private String nombreAdmin;
    
    public FormAdminDashboard(String nombreAdmin) {
        initComponents();
        this.nombreAdmin = nombreAdmin;
        this.setLocationRelativeTo(null);
        this.setTitle("Dashboard Administrativo - " + nombreAdmin);
        lblBienvenida.setText("Bienvenido, " + nombreAdmin);
        
        cargarEstadisticas();
        cargarUltimosRegistros();
        cargarResumenUsuarios();
    }
    
    private void cargarEstadisticas() {
        try {
            CConexion conexion = new CConexion();
            
            // Total administradores
            String sqlAdmin = "SELECT COUNT(*) as total FROM Usuarios WHERE rol = 'admin' AND activo = TRUE";
            PreparedStatement psAdmin = conexion.estableceConexion().prepareStatement(sqlAdmin);
            ResultSet rsAdmin = psAdmin.executeQuery();
            if (rsAdmin.next()) {
                lblTotalAdmins.setText("Admins: " + rsAdmin.getInt("total"));
            }
            
            // Total profesores
            String sqlProfesores = "SELECT COUNT(*) as total FROM Usuarios WHERE rol = 'profesor' AND activo = TRUE";
            PreparedStatement psProfesores = conexion.estableceConexion().prepareStatement(sqlProfesores);
            ResultSet rsProfesores = psProfesores.executeQuery();
            if (rsProfesores.next()) {
                lblTotalProfesores.setText("Profesores: " + rsProfesores.getInt("total"));
            }
            
            // Total estudiantes
            String sqlEstudiantes = "SELECT COUNT(*) as total FROM Usuarios WHERE rol = 'estudiante' AND activo = TRUE";
            PreparedStatement psEstudiantes = conexion.estableceConexion().prepareStatement(sqlEstudiantes);
            ResultSet rsEstudiantes = psEstudiantes.executeQuery();
            if (rsEstudiantes.next()) {
                lblTotalEstudiantes.setText("Estudiantes: " + rsEstudiantes.getInt("total"));
            }
            
            // Total cursos
            String sqlCursos = "SELECT COUNT(*) as total FROM Cursos WHERE activo = TRUE";
            PreparedStatement psCursos = conexion.estableceConexion().prepareStatement(sqlCursos);
            ResultSet rsCursos = psCursos.executeQuery();
            if (rsCursos.next()) {
                lblTotalCursos.setText("Cursos: " + rsCursos.getInt("total"));
            }
            
            // Total calificaciones
            String sqlCalificaciones = "SELECT COUNT(*) as total FROM Calificaciones";
            PreparedStatement psCalificaciones = conexion.estableceConexion().prepareStatement(sqlCalificaciones);
            ResultSet rsCalificaciones = psCalificaciones.executeQuery();
            if (rsCalificaciones.next()) {
                lblTotalCalificaciones.setText("Calificaciones: " + rsCalificaciones.getInt("total"));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando estadísticas: " + e.toString());
        }
    }
    
    private void cargarUltimosRegistros() {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT id, nombre, ingresoUsuario, email, rol, fecha_registro " +
                        "FROM Usuarios " +
                        "ORDER BY fecha_registro DESC LIMIT 10";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) tablaUltimosRegistros.getModel();
            model.setRowCount(0); // Limpiar tabla
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("ingresoUsuario"),
                    rs.getString("email"),
                    rs.getString("rol"),
                    rs.getTimestamp("fecha_registro")
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando últimos registros: " + e.toString());
        }
    }
    
    private void cargarResumenUsuarios() {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT rol, COUNT(*) as total, " +
                        "MAX(fecha_registro) as ultimo_registro " +
                        "FROM Usuarios " +
                        "WHERE activo = TRUE " +
                        "GROUP BY rol " +
                        "ORDER BY total DESC";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) tablaResumenUsuarios.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("rol"),
                    rs.getInt("total"),
                    rs.getTimestamp("ultimo_registro")
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando resumen de usuarios: " + e.toString());
        }
    }                                                                                                        
    
    private int obtenerIdAdmin() {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT id FROM Usuarios WHERE nombre = ? AND rol = 'admin' LIMIT 1";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setString(1, nombreAdmin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error obteniendo ID admin: " + e.toString());
        }
        return -1;
    }
                                                                                                                                         
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelUltimosRegistros = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        line1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaUltimosRegistros = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaResumenUsuarios = new javax.swing.JTable();
        panelEstadisticas = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        line5 = new javax.swing.JPanel();
        line6 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblTotalEstudiantes = new javax.swing.JLabel();
        lblTotalCursos = new javax.swing.JLabel();
        lblTotalCalificaciones = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        lblTotalAdmins = new javax.swing.JLabel();
        lblTotalProfesores = new javax.swing.JLabel();
        lblBienvenida = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnGestionarProfesores = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        btnGestionarEstudiantes = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pnlSide = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel9.setFont(new java.awt.Font("Snap ITC", 0, 14)); // NOI18N
        jLabel9.setText("Resumen por Rol");

        line1.setBackground(new java.awt.Color(100, 100, 100));
        line1.setPreferredSize(new java.awt.Dimension(100, 2));

        javax.swing.GroupLayout line1Layout = new javax.swing.GroupLayout(line1);
        line1.setLayout(line1Layout);
        line1Layout.setHorizontalGroup(
            line1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 253, Short.MAX_VALUE)
        );
        line1Layout.setVerticalGroup(
            line1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        tablaUltimosRegistros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Usuario", "Email", "Rol", "Fecha Registro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaUltimosRegistros);

        jScrollPane1.setViewportView(jScrollPane2);

        jLabel10.setFont(new java.awt.Font("Snap ITC", 0, 14)); // NOI18N
        jLabel10.setText("Últimos registros");

        tablaResumenUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Rol", "Total", "Último Registro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablaResumenUsuarios);

        jScrollPane4.setViewportView(jScrollPane3);

        javax.swing.GroupLayout panelUltimosRegistrosLayout = new javax.swing.GroupLayout(panelUltimosRegistros);
        panelUltimosRegistros.setLayout(panelUltimosRegistrosLayout);
        panelUltimosRegistrosLayout.setHorizontalGroup(
            panelUltimosRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUltimosRegistrosLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(panelUltimosRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(line1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelUltimosRegistrosLayout.createSequentialGroup()
                        .addGroup(panelUltimosRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(26, 26, 26)
                        .addGroup(panelUltimosRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelUltimosRegistrosLayout.setVerticalGroup(
            panelUltimosRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUltimosRegistrosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelUltimosRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUltimosRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Snap ITC", 0, 12)); // NOI18N
        jLabel8.setText("Miembros del Sistema");

        jPanel12.setPreferredSize(new java.awt.Dimension(250, 265));

        line5.setBackground(new java.awt.Color(100, 100, 100));
        line5.setForeground(new java.awt.Color(100, 100, 100));
        line5.setPreferredSize(new java.awt.Dimension(100, 2));

        line6.setBackground(new java.awt.Color(100, 100, 100));
        line6.setPreferredSize(new java.awt.Dimension(100, 2));

        javax.swing.GroupLayout line6Layout = new javax.swing.GroupLayout(line6);
        line6.setLayout(line6Layout);
        line6Layout.setHorizontalGroup(
            line6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 212, Short.MAX_VALUE)
        );
        line6Layout.setVerticalGroup(
            line6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout line5Layout = new javax.swing.GroupLayout(line5);
        line5.setLayout(line5Layout);
        line5Layout.setHorizontalGroup(
            line5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
            .addGroup(line5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(line5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(line6, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(100, Short.MAX_VALUE)))
        );
        line5Layout.setVerticalGroup(
            line5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(line5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(line5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(line6, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon("C:\\Users\\FLOR NIDIA\\Downloads\\Loginn\\src\\main\\java\\Icon\\icons8-profesor-64.png")); // NOI18N

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon("C:\\Users\\FLOR NIDIA\\Downloads\\Loginn\\src\\main\\java\\Icon\\icons8-usuarios-30.png")); // NOI18N

        lblTotalEstudiantes.setText("\"Estudiantes: 0\"");

        lblTotalCursos.setText("\"Cursos: 0\"");

        lblTotalCalificaciones.setText("\"Calificaciones: 0\"");

        jPanel14.setBackground(new java.awt.Color(100, 100, 100));
        jPanel14.setForeground(new java.awt.Color(100, 100, 100));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setIcon(new javax.swing.ImageIcon("C:\\Users\\FLOR NIDIA\\Downloads\\Loginn\\src\\main\\java\\Icon\\icons8-estudiante-50.png")); // NOI18N

        lblTotalAdmins.setText("\"Admins: 0\"");

        lblTotalProfesores.setText("\"Profesores: 0\"");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(line5, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTotalAdmins)
                                .addComponent(lblTotalProfesores, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTotalCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTotalEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalCalificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 35, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTotalAdmins)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotalProfesores)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)))
                .addComponent(line5, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(lblTotalEstudiantes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotalCursos))
                    .addComponent(jLabel37))
                .addGap(9, 9, 9)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(lblTotalCalificaciones)
                        .addGap(16, 16, 16)))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        lblBienvenida.setFont(new java.awt.Font("Snap ITC", 0, 18)); // NOI18N
        lblBienvenida.setText("Bienvenido, Admin");

        btnVolver.setText("Volver al Menú Principal");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnGestionarProfesores.setText("Gestionar");
        btnGestionarProfesores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarProfesoresActionPerformed(evt);
            }
        });

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon("C:\\Users\\FLOR NIDIA\\Downloads\\Loginn\\src\\main\\java\\Icon\\icons8-profesor-64.png")); // NOI18N

        btnGestionarEstudiantes.setText("Gestionar");
        btnGestionarEstudiantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarEstudiantesActionPerformed(evt);
            }
        });

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon("C:\\Users\\FLOR NIDIA\\Downloads\\Loginn\\src\\main\\java\\Icon\\icons8-estudiante-50.png")); // NOI18N

        javax.swing.GroupLayout panelEstadisticasLayout = new javax.swing.GroupLayout(panelEstadisticas);
        panelEstadisticas.setLayout(panelEstadisticasLayout);
        panelEstadisticasLayout.setHorizontalGroup(
            panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEstadisticasLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEstadisticasLayout.createSequentialGroup()
                        .addComponent(lblBienvenida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(198, 198, 198))
                    .addGroup(panelEstadisticasLayout.createSequentialGroup()
                        .addGroup(panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEstadisticasLayout.createSequentialGroup()
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnVolver, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelEstadisticasLayout.createSequentialGroup()
                                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnGestionarProfesores))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEstadisticasLayout.createSequentialGroup()
                                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnGestionarEstudiantes)))))
                            .addComponent(jLabel8))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelEstadisticasLayout.setVerticalGroup(
            panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEstadisticasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblBienvenida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(12, 12, 12)
                .addGroup(panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelEstadisticasLayout.createSequentialGroup()
                        .addGroup(panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addGroup(panelEstadisticasLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(btnGestionarProfesores)))
                        .addGap(18, 18, 18)
                        .addGroup(panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEstadisticasLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(btnGestionarEstudiantes)
                                .addGap(18, 18, 18)))
                        .addGap(18, 18, 18)
                        .addComponent(btnVolver)))
                .addContainerGap())
        );

        pnlSide.setBackground(new java.awt.Color(215, 215, 215));
        pnlSide.setForeground(new java.awt.Color(215, 215, 215));
        pnlSide.setPreferredSize(new java.awt.Dimension(80, 0));

        jPanel2.setBackground(new java.awt.Color(215, 215, 215));
        jPanel2.setForeground(new java.awt.Color(215, 215, 215));
        jPanel2.setPreferredSize(new java.awt.Dimension(50, 150));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 40));

        jButton6.setBackground(new java.awt.Color(215, 215, 215));
        jButton6.setForeground(new java.awt.Color(215, 215, 215));
        jButton6.setIcon(new javax.swing.ImageIcon("C:\\Users\\FLOR NIDIA\\Downloads\\Loginn\\src\\main\\java\\Icon\\Upla (1).png")); // NOI18N
        jButton6.setBorder(null);
        jButton6.setPreferredSize(new java.awt.Dimension(50, 50));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6);

        pnlSide.add(jPanel2);

        jButton1.setBackground(new java.awt.Color(215, 215, 215));
        jButton1.setForeground(new java.awt.Color(215, 215, 215));
        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\FLOR NIDIA\\Downloads\\Loginn\\src\\main\\java\\Icon\\icono de inicio.png")); // NOI18N
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(45, 45));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pnlSide.add(jButton1);

        jButton2.setBackground(new java.awt.Color(60, 63, 65));
        jButton2.setIcon(new javax.swing.ImageIcon("C:\\Users\\FLOR NIDIA\\Downloads\\Loginn\\src\\main\\java\\Icon\\icoCirculo.png")); // NOI18N
        jButton2.setBorder(null);
        jButton2.setPreferredSize(new java.awt.Dimension(45, 45));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pnlSide.add(jButton2);

        jButton3.setBackground(new java.awt.Color(60, 63, 65));
        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\FLOR NIDIA\\Downloads\\Loginn\\src\\main\\java\\Icon\\icoDocumen.png")); // NOI18N
        jButton3.setBorder(null);
        jButton3.setPreferredSize(new java.awt.Dimension(45, 45));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        pnlSide.add(jButton3);

        jButton4.setBackground(new java.awt.Color(60, 63, 65));
        jButton4.setIcon(new javax.swing.ImageIcon("C:\\Users\\FLOR NIDIA\\Downloads\\Loginn\\src\\main\\java\\Icon\\icoEnfo.png")); // NOI18N
        jButton4.setBorder(null);
        jButton4.setPreferredSize(new java.awt.Dimension(45, 45));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        pnlSide.add(jButton4);

        jButton5.setBackground(new java.awt.Color(60, 63, 65));
        jButton5.setIcon(new javax.swing.ImageIcon("C:\\Users\\FLOR NIDIA\\Downloads\\Loginn\\src\\main\\java\\Icon\\IcoParrafo.png")); // NOI18N
        jButton5.setBorder(null);
        jButton5.setPreferredSize(new java.awt.Dimension(45, 45));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        pnlSide.add(jButton5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 93, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlSide, javax.swing.GroupLayout.PREFERRED_SIZE, 93, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 611, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(pnlSide, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelUltimosRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelEstadisticas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelEstadisticas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelUltimosRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGestionarEstudiantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionarEstudiantesActionPerformed
        FormGestionEstudiantes gestionEstudiantes = new FormGestionEstudiantes();
        gestionEstudiantes.setVisible(true);
        gestionEstudiantes.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnGestionarEstudiantesActionPerformed

    private void btnGestionarProfesoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionarProfesoresActionPerformed
        FormGestionProfesores gestionProfesores = new FormGestionProfesores();
        gestionProfesores.setVisible(true);
        gestionProfesores.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnGestionarProfesoresActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // Necesitamos obtener el ID del admin
        int adminId = obtenerIdAdmin();
        FormMenuPrincipal menuPrincipal = new FormMenuPrincipal("admin", nombreAdmin, adminId);
        menuPrincipal.setVisible(true);
        menuPrincipal.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGestionarEstudiantes;
    private javax.swing.JButton btnGestionarProfesores;
    private javax.swing.JButton btnVolver;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblTotalAdmins;
    private javax.swing.JLabel lblTotalCalificaciones;
    private javax.swing.JLabel lblTotalCursos;
    private javax.swing.JLabel lblTotalEstudiantes;
    private javax.swing.JLabel lblTotalProfesores;
    private javax.swing.JPanel line1;
    private javax.swing.JPanel line5;
    private javax.swing.JPanel line6;
    private javax.swing.JPanel panelEstadisticas;
    private javax.swing.JPanel panelUltimosRegistros;
    private javax.swing.JPanel pnlSide;
    private javax.swing.JTable tablaResumenUsuarios;
    private javax.swing.JTable tablaUltimosRegistros;
    // End of variables declaration//GEN-END:variables
}
