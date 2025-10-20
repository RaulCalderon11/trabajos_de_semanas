package com.mycompany.loginn;

import Clases.CConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormDashboardProfesor extends javax.swing.JFrame {

    private int profesorId;
    private String nombreProfesor;
    
    public FormDashboardProfesor(int profesorId, String nombreProfesor) {
        initComponents();
        this.profesorId = profesorId;
        this.nombreProfesor = nombreProfesor;
        this.setLocationRelativeTo(null);
        this.setTitle("Dashboard Profesor - " + nombreProfesor);
        lblBienvenida.setText("Bienvenido, Prof. " + nombreProfesor);

        cargarCursos();
        cargarEstadisticas();
    }
    
    private void cargarCursos() {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT c.id, c.nombre_curso, c.nivel, c.descripcion, " +
                        "c.fecha_inicio, c.fecha_fin, " +
                        "COUNT(DISTINCT i.estudiante_id) as total_estudiantes, " +
                        "COUNT(DISTINCT cal.id) as total_calificaciones " +
                        "FROM Cursos c " +
                        "LEFT JOIN Inscripciones i ON c.id = i.curso_id AND i.estado = 'activo' " +
                        "LEFT JOIN Calificaciones cal ON c.id = cal.curso_id " +
                        "WHERE c.profesor_id = ? AND c.activo = TRUE " +
                        "GROUP BY c.id, c.nombre_curso, c.nivel, c.descripcion, c.fecha_inicio, c.fecha_fin " +
                        "ORDER BY c.nombre_curso";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setInt(1, profesorId);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tablaCursos.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("nombre_curso"),
                    rs.getString("nivel"),
                    rs.getInt("total_estudiantes"),
                    rs.getInt("total_calificaciones"),
                    rs.getDate("fecha_inicio"),
                    rs.getDate("fecha_fin")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando cursos: " + e.toString());
        }
    }
    
    private void cargarEstadisticas() {
        try {
            CConexion conexion = new CConexion();
            
            // Total estudiantes
            String sqlEstudiantes = "SELECT COUNT(DISTINCT c.estudiante_id) FROM Calificaciones c " +
                                   "JOIN Cursos cur ON c.curso_id = cur.id WHERE cur.profesor_id = ?";
            PreparedStatement psEstudiantes = conexion.estableceConexion().prepareStatement(sqlEstudiantes);
            psEstudiantes.setInt(1, profesorId);
            ResultSet rsEstudiantes = psEstudiantes.executeQuery();
            if (rsEstudiantes.next()) {
                lblTotalEstudiantes.setText("Estudiantes: " + rsEstudiantes.getInt(1));
            }
            
            // Total calificaciones
            String sqlCalificaciones = "SELECT COUNT(*) FROM Calificaciones c " +
                                      "JOIN Cursos cur ON c.curso_id = cur.id WHERE cur.profesor_id = ?";
            PreparedStatement psCalificaciones = conexion.estableceConexion().prepareStatement(sqlCalificaciones);
            psCalificaciones.setInt(1, profesorId);
            ResultSet rsCalificaciones = psCalificaciones.executeQuery();
            if (rsCalificaciones.next()) {
                lblTotalCalificaciones.setText("Calificaciones: " + rsCalificaciones.getInt(1));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando estadísticas: " + e.toString());
        }
    }                                                                                                                                                 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCursos = new javax.swing.JTable();
        lblTotalEstudiantes = new javax.swing.JLabel();
        lblTotalCalificaciones = new javax.swing.JLabel();
        btnGestionarCalificaciones = new javax.swing.JButton();
        btnVerEstudiantes = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        lblBienvenida = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre_Curso", "Nivel", "Total_Estudiantes", "Total_calificaciones", "Fecha_inicio", "Fecha_fin"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaCursos);

        lblTotalEstudiantes.setText("...");

        lblTotalCalificaciones.setText("...");

        btnGestionarCalificaciones.setText("Gestionar");
        btnGestionarCalificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarCalificacionesActionPerformed(evt);
            }
        });

        btnVerEstudiantes.setText("Ver Estudiantes");
        btnVerEstudiantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerEstudiantesActionPerformed(evt);
            }
        });

        btnReportes.setText("Reportes");
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        lblBienvenida.setFont(new java.awt.Font("Snap ITC", 0, 24)); // NOI18N
        lblBienvenida.setText("\"Bienvenido, ...\"");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTotalCalificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(63, 63, 63)
                            .addComponent(lblTotalEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblBienvenida)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionarCalificaciones))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(122, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnReportes)
                            .addComponent(btnVerEstudiantes)
                            .addComponent(btnVolver))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblBienvenida)
                .addGap(35, 35, 35)
                .addComponent(lblTotalEstudiantes)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(btnGestionarCalificaciones)
                        .addGap(52, 52, 52)
                        .addComponent(btnVerEstudiantes)
                        .addGap(46, 46, 46)
                        .addComponent(btnReportes)
                        .addGap(36, 36, 36)
                        .addComponent(btnVolver)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lblTotalCalificaciones)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // CORREGIR: Pasar el tercer parámetro (idUsuario)
        FormMenuPrincipal menuPrincipal = new FormMenuPrincipal("profesor", nombreProfesor, profesorId);
        menuPrincipal.setVisible(true);
        menuPrincipal.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Módulo de Reportes - Próximamente");
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnVerEstudiantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerEstudiantesActionPerformed
        // TODO add your handling code here:
        FormListaEstudiantes listaEstudiantes = new FormListaEstudiantes(profesorId);
        listaEstudiantes.setVisible(true);
        listaEstudiantes.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnVerEstudiantesActionPerformed

    private void btnGestionarCalificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionarCalificacionesActionPerformed
        // TODO add your handling code here:
        FormGestionCalificaciones gestionCalificaciones = new FormGestionCalificaciones(profesorId, nombreProfesor);
        gestionCalificaciones.setVisible(true);
        gestionCalificaciones.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnGestionarCalificacionesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGestionarCalificaciones;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnVerEstudiantes;
    private javax.swing.JButton btnVolver;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblTotalCalificaciones;
    private javax.swing.JLabel lblTotalEstudiantes;
    private javax.swing.JTable tablaCursos;
    // End of variables declaration//GEN-END:variables
}
