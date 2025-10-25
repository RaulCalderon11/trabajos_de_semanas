package com.mycompany.loginn;

import Clases.CConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormProgresoEstudiante extends javax.swing.JFrame {

    private int estudianteId;
    private String nombreEstudiante;
    
    public FormProgresoEstudiante(int estudianteId, String nombreEstudiante) {
        initComponents();
        this.estudianteId = estudianteId;
        this.nombreEstudiante = nombreEstudiante;
        this.setLocationRelativeTo(null);
        this.setTitle("Mi Progreso - " + nombreEstudiante);
        
        configurarInterfaz();
        cargarEstadisticas();
        cargarProgresoDetallado();
        cargarHabilidades();
    }
    
    private void configurarInterfaz() {
        lblTitulo.setText("Mi Progreso Académico - " + nombreEstudiante);
    }
    
    private void cargarEstadisticas() {
        try {
            CConexion conexion = new CConexion();
            
            // Estadísticas generales
            String sqlGeneral = "SELECT " +
                               "COUNT(*) as total_calificaciones, " +
                               "AVG(puntaje) as promedio_general, " +
                               "MIN(puntaje) as minima, " +
                               "MAX(puntaje) as maxima, " +
                               "COUNT(DISTINCT curso_id) as cursos_diferentes " +
                               "FROM Calificaciones WHERE estudiante_id = ?";
            PreparedStatement psGeneral = conexion.estableceConexion().prepareStatement(sqlGeneral);
            psGeneral.setInt(1, estudianteId);
            ResultSet rsGeneral = psGeneral.executeQuery();
            
            if (rsGeneral.next()) {
                lblTitulo.setText("Total Calificaciones: " + rsGeneral.getInt("total_calificaciones"));
                
                double promedio = rsGeneral.getDouble("promedio_general");
                if (!rsGeneral.wasNull()) {
                    lblPromedioGeneral.setText("Promedio General: " + String.format("%.2f", promedio));
                } else {
                    lblPromedioGeneral.setText("Promedio General: Sin calificaciones");
                }
                
                lblNotaMinima.setText("Nota Mínima: " + rsGeneral.getDouble("minima"));
                lblNotaMaxima.setText("Nota Máxima: " + rsGeneral.getDouble("maxima"));
                lblCursosDiferentes.setText("Cursos Diferentes: " + rsGeneral.getInt("cursos_diferentes"));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando estadísticas: " + e.toString());
        }
    }
    
    private void cargarProgresoDetallado() {
        try {
            CConexion conexion = new CConexion();
            
            String sql = "SELECT c.nombre_curso, " +
                        "AVG(cal.puntaje) as promedio_curso, " +
                        "COUNT(cal.id) as total_evaluaciones, " +
                        "MIN(cal.puntaje) as minima, " +
                        "MAX(cal.puntaje) as maxima " +
                        "FROM Calificaciones cal " +
                        "JOIN Cursos c ON cal.curso_id = c.id " +
                        "WHERE cal.estudiante_id = ? " +
                        "GROUP BY c.nombre_curso " +
                        "ORDER BY promedio_curso DESC";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setInt(1, estudianteId);
            ResultSet rs = ps.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) tablaProgresoCursos.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("nombre_curso"),
                    String.format("%.2f", rs.getDouble("promedio_curso")),
                    rs.getInt("total_evaluaciones"),
                    rs.getDouble("minima"),
                    rs.getDouble("maxima")
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando progreso: " + e.toString());
        }
    }
    
    private void cargarHabilidades() {
        try {
            CConexion conexion = new CConexion();
            
            String sql = "SELECT habilidad, " +
                        "AVG(puntaje) as promedio, " +
                        "COUNT(*) as total, " +
                        "MIN(puntaje) as minima, " +
                        "MAX(puntaje) as maxima " +
                        "FROM Calificaciones " +
                        "WHERE estudiante_id = ? " +
                        "GROUP BY habilidad " +
                        "ORDER BY promedio DESC";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setInt(1, estudianteId);
            ResultSet rs = ps.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) tablaHabilidades.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("habilidad"),
                    String.format("%.2f", rs.getDouble("promedio")),
                    rs.getInt("total"),
                    rs.getDouble("minima"),
                    rs.getDouble("maxima")
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando habilidades: " + e.toString());
        }
    }
    
    private void cargarProgresoMensual() {
        try {
            CConexion conexion = new CConexion();
            
            String sql = "SELECT MONTH(fecha_evaluacion) as mes, " +
                        "AVG(puntaje) as promedio, " +
                        "COUNT(*) as total " +
                        "FROM Calificaciones " +
                        "WHERE estudiante_id = ? AND YEAR(fecha_evaluacion) = YEAR(CURDATE()) " +
                        "GROUP BY MONTH(fecha_evaluacion) " +
                        "ORDER BY mes";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setInt(1, estudianteId);
            ResultSet rs = ps.executeQuery();
            
            StringBuilder mensual = new StringBuilder("PROGRESO MENSUAL " + java.time.Year.now().getValue() + ":\n\n");
            while (rs.next()) {
                String mes = obtenerNombreMes(rs.getInt("mes"));
                mensual.append(mes).append(":\n")
                      .append("  Promedio: ").append(String.format("%.2f", rs.getDouble("promedio"))).append("\n")
                      .append("  Evaluaciones: ").append(rs.getInt("total"))
                      .append("\n------------------------\n");
            }
            lblA.setText(mensual.toString());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando progreso mensual: " + e.toString());
        }
    }
    
    private String obtenerNombreMes(int mes) {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                         "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return (mes >= 1 && mes <= 12) ? meses[mes - 1] : "Mes " + mes;
    }                                                                                                      
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        lblTotalCalificaciones = new javax.swing.JLabel();
        lblPromedioGeneral = new javax.swing.JLabel();
        lblNotaMinima = new javax.swing.JLabel();
        lblNotaMaxima = new javax.swing.JLabel();
        lblCursosDiferentes = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProgresoCursos = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaHabilidades = new javax.swing.JTable();
        lblA = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtProgresoMensual = new javax.swing.JTextArea();
        btnCargarMensual = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        lblTitulo.setText("jLabel1");

        lblTotalCalificaciones.setText("jLabel2");

        lblPromedioGeneral.setText("jLabel3");

        lblNotaMinima.setText("jLabel4");

        lblNotaMaxima.setText("jLabel5");

        lblCursosDiferentes.setText("jLabel6");

        jLabel1.setFont(new java.awt.Font("Snap ITC", 0, 18)); // NOI18N
        jLabel1.setText("Estadísticas Generales");

        jLabel2.setFont(new java.awt.Font("Snap ITC", 0, 18)); // NOI18N
        jLabel2.setText("Progreso por Curso");

        jLabel3.setFont(new java.awt.Font("Snap ITC", 0, 18)); // NOI18N
        jLabel3.setText("Análisis por Habilidad");

        tablaProgresoCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Curso", "Promedio", "Evaluaciones", "Mínima", "Máxima"
            }
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jScrollPane1.setViewportView(tablaProgresoCursos);

        jScrollPane3.setViewportView(jScrollPane1);

        tablaHabilidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Habilidad", "Promedio", "Evaluaciones", "Mínima", "Máxima"
            }
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jScrollPane2.setViewportView(tablaHabilidades);

        jScrollPane4.setViewportView(jScrollPane2);

        lblA.setFont(new java.awt.Font("Snap ITC", 0, 18)); // NOI18N
        lblA.setText("Progreso Mensual");

        txtProgresoMensual.setColumns(20);
        txtProgresoMensual.setRows(5);
        jScrollPane5.setViewportView(txtProgresoMensual);

        btnCargarMensual.setText("Cargar");
        btnCargarMensual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarMensualActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblA)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitulo)
                            .addComponent(lblTotalCalificaciones)
                            .addComponent(lblPromedioGeneral))
                        .addGap(256, 256, 256)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCursosDiferentes)
                            .addComponent(lblNotaMaxima)
                            .addComponent(lblNotaMinima)))
                    .addComponent(jLabel1)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(69, 69, 69))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addGap(27, 27, 27)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCargarMensual)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVolver))))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(lblNotaMinima))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalCalificaciones)
                    .addComponent(lblNotaMaxima))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPromedioGeneral)
                    .addComponent(lblCursosDiferentes))
                .addGap(55, 55, 55)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addComponent(lblA)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                        .addGap(55, 55, 55))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnCargarMensual)
                        .addGap(30, 30, 30)
                        .addComponent(btnVolver)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void btnCargarMensualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarMensualActionPerformed
        // TODO add your handling code here:
        cargarProgresoMensual();
        JOptionPane.showMessageDialog(this, "Progreso mensual actualizado");
    }//GEN-LAST:event_btnCargarMensualActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCargarMensual;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblA;
    private javax.swing.JLabel lblCursosDiferentes;
    private javax.swing.JLabel lblNotaMaxima;
    private javax.swing.JLabel lblNotaMinima;
    private javax.swing.JLabel lblPromedioGeneral;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotalCalificaciones;
    private javax.swing.JTable tablaHabilidades;
    private javax.swing.JTable tablaProgresoCursos;
    private javax.swing.JTextArea txtProgresoMensual;
    // End of variables declaration//GEN-END:variables
}
