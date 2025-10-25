package com.mycompany.loginn;

import Clases.CConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormTodasCalificaciones extends javax.swing.JFrame {

    private int estudianteId;
    private String nombreEstudiante;
    
    public FormTodasCalificaciones(int estudianteId, String nombreEstudiante) {
        initComponents();
        this.estudianteId = estudianteId;
        this.nombreEstudiante = nombreEstudiante;
        this.setLocationRelativeTo(null);
        this.setTitle("Todas las Calificaciones - " + nombreEstudiante);
        lblTitulo.setText("Calificaciones de " + nombreEstudiante);
        
        cargarTodasCalificaciones();
        cargarResumen();
    }
    
    private void cargarTodasCalificaciones() {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT c.nombre_curso, cal.habilidad, cal.puntaje, " +
                        "cal.fecha_evaluacion, cal.comentario " +
                        "FROM Calificaciones cal " +
                        "JOIN Cursos c ON cal.curso_id = c.id " +
                        "WHERE cal.estudiante_id = ? " +
                        "ORDER BY cal.fecha_evaluacion DESC, c.nombre_curso";
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
    
    private void cargarResumen() {
        try {
            CConexion conexion = new CConexion();
            
            // Promedio por habilidad
            String sqlHabilidades = "SELECT habilidad, AVG(puntaje) as promedio, COUNT(*) as total " +
                                   "FROM Calificaciones WHERE estudiante_id = ? " +
                                   "GROUP BY habilidad ORDER BY promedio DESC";
            PreparedStatement psHabilidades = conexion.estableceConexion().prepareStatement(sqlHabilidades);
            psHabilidades.setInt(1, estudianteId);
            ResultSet rsHabilidades = psHabilidades.executeQuery();
            
            DefaultTableModel modelHabilidades = (DefaultTableModel) tablaResumen.getModel();
            modelHabilidades.setRowCount(0);
            
            while (rsHabilidades.next()) {
                Object[] row = {
                    rsHabilidades.getString("habilidad"),
                    String.format("%.2f", rsHabilidades.getDouble("promedio")),
                    rsHabilidades.getInt("total")
                };
                modelHabilidades.addRow(row);
            }
            
            // Estadísticas generales
            String sqlGeneral = "SELECT COUNT(*) as total_calificaciones, " +
                               "AVG(puntaje) as promedio_general, " +
                               "MIN(puntaje) as minima, MAX(puntaje) as maxima " +
                               "FROM Calificaciones WHERE estudiante_id = ?";
            PreparedStatement psGeneral = conexion.estableceConexion().prepareStatement(sqlGeneral);
            psGeneral.setInt(1, estudianteId);
            ResultSet rsGeneral = psGeneral.executeQuery();
            
            if (rsGeneral.next()) {
                lblTotalCalificaciones.setText("Total Calificaciones: " + rsGeneral.getInt("total_calificaciones"));
                lblPromedioGeneral.setText("Promedio General: " + String.format("%.2f", rsGeneral.getDouble("promedio_general")));
                lblNotaMinima.setText("Nota Mínima: " + rsGeneral.getDouble("minima"));
                lblNotaMaxima.setText("Nota Máxima: " + rsGeneral.getDouble("maxima"));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando resumen: " + e.toString());
        }
    }
                                       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCalificaciones = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaResumen = new javax.swing.JTable();
        lblTitulo = new javax.swing.JLabel();
        lblPromedioGeneral = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblNotaMinima = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblNotaMaxima = new javax.swing.JLabel();
        lblTotalCalificaciones = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaCalificaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaCalificaciones);

        tablaResumen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaResumen);

        lblTitulo.setFont(new java.awt.Font("Snap ITC", 0, 24)); // NOI18N
        lblTitulo.setText("Calificaciones de ...");

        lblPromedioGeneral.setText("...");

        jLabel1.setText("Promedio general");

        lblNotaMinima.setText("...");

        jLabel3.setText("Nota Minima");

        jLabel4.setText("Nota Maxima");

        lblNotaMaxima.setText("...");

        lblTotalCalificaciones.setText("...");

        jLabel2.setText("Total Calificaciones");

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(lblTitulo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPromedioGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(lblNotaMinima, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNotaMaxima, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnVolver)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblTotalCalificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblPromedioGeneral))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblNotaMinima))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblNotaMaxima))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblTotalCalificaciones))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnVolver)
                .addGap(16, 16, 16))
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
        // TODO add your handling code here:
        FormDashboardEstudiante dashboard = new FormDashboardEstudiante(estudianteId, nombreEstudiante);
        dashboard.setVisible(true);
        dashboard.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblNotaMaxima;
    private javax.swing.JLabel lblNotaMinima;
    private javax.swing.JLabel lblPromedioGeneral;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotalCalificaciones;
    private javax.swing.JTable tablaCalificaciones;
    private javax.swing.JTable tablaResumen;
    // End of variables declaration//GEN-END:variables
}
