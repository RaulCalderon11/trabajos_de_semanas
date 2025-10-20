package com.mycompany.loginn;

import Clases.CConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormDashboardEstudiante extends javax.swing.JFrame {

    private int estudianteId;
    private String nombreEstudiante;
    
    public FormDashboardEstudiante(int estudianteId, String nombreEstudiante) {
        initComponents();
        this.estudianteId = estudianteId;
        this.nombreEstudiante = nombreEstudiante;
        this.setLocationRelativeTo(null);
        this.setTitle("Dashboard Estudiante - " + nombreEstudiante);
        
        configurarInterfaz();
        cargarCalificacionesRecientes();
        cargarPromedios();
    }
    
    private void configurarInterfaz() {
        lblBienvenida.setText("Bienvenido, " + nombreEstudiante);
    }
    
    private void cargarCalificacionesRecientes() {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT c.nombre_curso, cal.habilidad, cal.puntaje, cal.fecha_evaluacion, cal.comentario " +
                        "FROM Calificaciones cal " +
                        "JOIN Cursos c ON cal.curso_id = c.id " +
                        "WHERE cal.estudiante_id = ? " +
                        "ORDER BY cal.fecha_evaluacion DESC LIMIT 10";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setInt(1, estudianteId);
            ResultSet rs = ps.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) tablaCalificaciones.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("nombre_curso"),
                    rs.getString("habilidad"),
                    rs.getDouble("puntaje"),
                    rs.getDate("fecha_evaluacion"),
                    rs.getString("comentario")
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando calificaciones: " + e.toString());
        }
    }
    
    private void cargarPromedios() {
        try {
            CConexion conexion = new CConexion();
            
            // Promedio general
            String sqlPromedio = "SELECT AVG(puntaje) FROM Calificaciones WHERE estudiante_id = ?";
            PreparedStatement psPromedio = conexion.estableceConexion().prepareStatement(sqlPromedio);
            psPromedio.setInt(1, estudianteId);
            ResultSet rsPromedio = psPromedio.executeQuery();
            if (rsPromedio.next()) {
                double promedio = rsPromedio.getDouble(1);
                if (!rsPromedio.wasNull()) {
                    lblPromedioGeneral.setText("Promedio General: " + String.format("%.2f", promedio));
                } else {
                    lblPromedioGeneral.setText("Promedio General: Sin calificaciones");
                }
            }
            
            // Mejor habilidad
            String sqlMejorHabilidad = "SELECT habilidad, AVG(puntaje) as promedio " +
                                      "FROM Calificaciones WHERE estudiante_id = ? " +
                                      "GROUP BY habilidad ORDER BY promedio DESC LIMIT 1";
            PreparedStatement psMejor = conexion.estableceConexion().prepareStatement(sqlMejorHabilidad);
            psMejor.setInt(1, estudianteId);
            ResultSet rsMejor = psMejor.executeQuery();
            if (rsMejor.next()) {
                lblMejorHabilidad.setText("Mejor Habilidad: " + 
                    rsMejor.getString("habilidad") + " (" + 
                    String.format("%.2f", rsMejor.getDouble("promedio")) + ")");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando promedios: " + e.toString());
        }
    }                                                                                                                                         
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblBienvenida = new javax.swing.JLabel();
        lblPromedioGeneral = new javax.swing.JLabel();
        lblMejorHabilidad = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCalificaciones = new javax.swing.JTable();
        btnProgreso = new javax.swing.JButton();
        btnVerTodasCalificaciones = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblBienvenida.setFont(new java.awt.Font("Snap ITC", 0, 24)); // NOI18N
        lblBienvenida.setText("\"Bienvenido...\"");

        lblPromedioGeneral.setText("...");

        lblMejorHabilidad.setText("...");

        tablaCalificaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                " Curso", "Habilidad", "Puntaje", "Fecha", "Comentario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaCalificaciones);

        jScrollPane2.setViewportView(jScrollPane1);

        btnProgreso.setText("Progreso");
        btnProgreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProgresoActionPerformed(evt);
            }
        });

        btnVerTodasCalificaciones.setText("Calificaciones");
        btnVerTodasCalificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerTodasCalificacionesActionPerformed(evt);
            }
        });

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(213, 213, 213)
                .addComponent(lblBienvenida)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblPromedioGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(lblMejorHabilidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(btnProgreso)
                .addGap(87, 87, 87)
                .addComponent(btnVerTodasCalificaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addComponent(btnVolver)
                .addGap(129, 129, 129))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblBienvenida)
                .addGap(32, 32, 32)
                .addComponent(lblPromedioGeneral)
                .addGap(24, 24, 24)
                .addComponent(lblMejorHabilidad)
                .addGap(45, 45, 45)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProgreso)
                    .addComponent(btnVerTodasCalificaciones)
                    .addComponent(btnVolver))
                .addGap(0, 129, Short.MAX_VALUE))
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

    private void btnProgresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProgresoActionPerformed
        // TODO add your handling code here:
        FormProgresoEstudiante progreso = new FormProgresoEstudiante(estudianteId, nombreEstudiante);
        progreso.setVisible(true);
        progreso.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnProgresoActionPerformed

    private void btnVerTodasCalificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerTodasCalificacionesActionPerformed
        // TODO add your handling code here:
        FormTodasCalificaciones todasCalificaciones = new FormTodasCalificaciones(estudianteId, nombreEstudiante);
        todasCalificaciones.setVisible(true);
        todasCalificaciones.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnVerTodasCalificacionesActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        FormMenuPrincipal menuPrincipal = new FormMenuPrincipal("estudiante", nombreEstudiante, estudianteId);
        menuPrincipal.setVisible(true);
        menuPrincipal.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProgreso;
    private javax.swing.JButton btnVerTodasCalificaciones;
    private javax.swing.JButton btnVolver;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblMejorHabilidad;
    private javax.swing.JLabel lblPromedioGeneral;
    private javax.swing.JTable tablaCalificaciones;
    // End of variables declaration//GEN-END:variables
}
