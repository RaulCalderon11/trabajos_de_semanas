package com.mycompany.loginn;

import Clases.CConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormGestionCalificaciones extends javax.swing.JFrame {

    private int profesorId;
    private String nombreProfesor;
    
    public FormGestionCalificaciones(int profesorId, String nombreProfesor) {
        initComponents();
        this.profesorId = profesorId;
        this.nombreProfesor = nombreProfesor;
        this.setLocationRelativeTo(null);
        this.setTitle("Gestión de Calificaciones - " + nombreProfesor);
        
        configurarInterfaz();
        configurarCombos();
        cargarEstudiantes();
        cargarCursos();
        cargarCalificacionesRecientes();
    }
    
    private void configurarInterfaz() {
        lblTitulo.setText("Gestión de Calificaciones - Prof. " + nombreProfesor);
    }
    
    private void configurarCombos() {
        // Configurar habilidades
        comboHabilidad.removeAllItems();
        comboHabilidad.addItem("speaking");
        comboHabilidad.addItem("writing");
        comboHabilidad.addItem("reading");
        comboHabilidad.addItem("listening");
        comboHabilidad.addItem("grammar");
        comboHabilidad.addItem("vocabulary");
        
        // Configurar tipo evaluación
        comboTipo.removeAllItems();
        comboTipo.addItem("examen");
        comboTipo.addItem("tarea");
        comboTipo.addItem("proyecto");
        comboTipo.addItem("participacion");
        
        // Configurar periodos
        comboPeriodo.removeAllItems();
        comboPeriodo.addItem("Q1");
        comboPeriodo.addItem("Q2");
        comboPeriodo.addItem("Q3");
        comboPeriodo.addItem("Q4");
    }
    
    private void cargarEstudiantes() {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT id, nombre FROM Usuarios WHERE rol = 'estudiante' AND activo = TRUE ORDER BY nombre";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            comboEstudiante.removeAllItems();
            comboEstudiante.addItem("Seleccionar estudiante");
            
            while (rs.next()) {
                comboEstudiante.addItem(rs.getString("nombre") + " | ID:" + rs.getInt("id"));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando estudiantes: " + e.toString());
        }
    }
    
    private void cargarCursos() {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT id, nombre_curso FROM Cursos WHERE profesor_id = ? AND activo = TRUE ORDER BY nombre_curso";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setInt(1, profesorId);
            ResultSet rs = ps.executeQuery();
            
            comboCurso.removeAllItems();
            comboCurso.addItem("Seleccionar curso");
            
            while (rs.next()) {
                comboCurso.addItem(rs.getString("nombre_curso") + " | ID:" + rs.getInt("id"));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando cursos: " + e.toString());
        }
    }
    
    private void cargarCalificacionesRecientes() {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT u.nombre as estudiante, c.nombre_curso as curso, " +
                        "cal.habilidad, cal.puntaje, cal.fecha_evaluacion, cal.tipo_evaluacion " +
                        "FROM Calificaciones cal " +
                        "JOIN Usuarios u ON cal.estudiante_id = u.id " +
                        "JOIN Cursos c ON cal.curso_id = c.id " +
                        "WHERE c.profesor_id = ? " +
                        "ORDER BY cal.fecha_evaluacion DESC LIMIT 20";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setInt(1, profesorId);
            ResultSet rs = ps.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) tablaCalificaciones.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("estudiante"),
                    rs.getString("curso"),
                    rs.getString("habilidad"),
                    rs.getDouble("puntaje"),
                    rs.getString("tipo_evaluacion"),
                    rs.getDate("fecha_evaluacion")
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando calificaciones: " + e.toString());
        }
    }
    
    private void guardarCalificacion() {
        if (!validarCampos()) {
            return;
        }
        
        try {
            // Obtener IDs de los combos
            String estudianteInfo = comboEstudiante.getSelectedItem().toString();
            String cursoInfo = comboCurso.getSelectedItem().toString();
            
            int estudianteId = Integer.parseInt(estudianteInfo.split("ID:")[1].trim());
            int cursoId = Integer.parseInt(cursoInfo.split("ID:")[1].trim());
            
            CConexion conexion = new CConexion();
            String sql = "INSERT INTO Calificaciones (estudiante_id, curso_id, habilidad, puntaje, " +
                        "fecha_evaluacion, comentario, tipo_evaluacion, periodo) " +
                        "VALUES (?, ?, ?, ?, CURDATE(), ?, ?, ?)";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            
            ps.setInt(1, estudianteId);
            ps.setInt(2, cursoId);
            ps.setString(3, comboHabilidad.getSelectedItem().toString());
            ps.setDouble(4, Double.parseDouble(txtPuntaje.getText().trim()));
            ps.setString(5, txtComentario.getText().trim());
            ps.setString(6, comboTipo.getSelectedItem().toString());
            ps.setString(7, comboPeriodo.getSelectedItem().toString());
            
            int resultado = ps.executeUpdate();
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "✅ Calificación guardada exitosamente");
                limpiarCampos();
                cargarCalificacionesRecientes();
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Error en el formato de los IDs");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error guardando calificación: " + e.getMessage());
        }
    }
    
    private boolean validarCampos() {
        // Validar selección de estudiante
        if (comboEstudiante.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "❌ Debe seleccionar un estudiante");
            comboEstudiante.requestFocus();
            return false;
        }
        
        // Validar selección de curso
        if (comboCurso.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "❌ Debe seleccionar un curso");
            comboCurso.requestFocus();
            return false;
        }
        
        // Validar puntaje
        if (txtPuntaje.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ El puntaje es obligatorio");
            txtPuntaje.requestFocus();
            return false;
        }
        
        try {
            double puntaje = Double.parseDouble(txtPuntaje.getText().trim());
            if (puntaje < 0 || puntaje > 20) {
                JOptionPane.showMessageDialog(this, "❌ El puntaje debe estar entre 0 y 20");
                txtPuntaje.requestFocus();
                txtPuntaje.selectAll();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ El puntaje debe ser un número válido");
            txtPuntaje.requestFocus();
            txtPuntaje.selectAll();
            return false;
        }
        
        return true;
    }
    
    private void limpiarCampos() {
        comboEstudiante.setSelectedIndex(0);
        comboCurso.setSelectedIndex(0);
        comboHabilidad.setSelectedIndex(0);
        comboTipo.setSelectedIndex(0);
        comboPeriodo.setSelectedIndex(0);
        txtPuntaje.setText("");
        txtComentario.setText("");
    }                                                                                                                   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        comboEstudiante = new javax.swing.JComboBox<>();
        comboCurso = new javax.swing.JComboBox<>();
        comboHabilidad = new javax.swing.JComboBox<>();
        txtPuntaje = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComentario = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        comboTipo = new javax.swing.JComboBox<>();
        comboPeriodo = new javax.swing.JComboBox<>();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCalificaciones = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        comboEstudiante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboCurso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboHabilidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Puntaje:");

        txtComentario.setColumns(20);
        txtComentario.setRows(5);
        jScrollPane1.setViewportView(txtComentario);

        jLabel2.setText("Comentario: ");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Snap ITC", 0, 24)); // NOI18N
        lblTitulo.setText("\"Criterios a Evaluar\"");

        comboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        tablaCalificaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Estudiante", "Curso", "Habilidad", "Puntaje", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaCalificaciones);

        jScrollPane3.setViewportView(jScrollPane2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(comboEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(comboCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(comboHabilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(70, 70, 70)
                                            .addComponent(jLabel1))
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(btnVolver))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPuntaje, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(comboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(140, 140, 140)
                                        .addComponent(btnGuardar)
                                        .addGap(59, 59, 59)
                                        .addComponent(btnLimpiar)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboHabilidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPuntaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar)
                    .addComponent(btnLimpiar))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver)
                        .addGap(18, 18, 18))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        FormDashboardProfesor dashboard = new FormDashboardProfesor(profesorId, nombreProfesor);
        dashboard.setVisible(true);
        dashboard.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (validarCampos()) {
            guardarCalificacion();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboCurso;
    private javax.swing.JComboBox<String> comboEstudiante;
    private javax.swing.JComboBox<String> comboHabilidad;
    private javax.swing.JComboBox<String> comboPeriodo;
    private javax.swing.JComboBox<String> comboTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tablaCalificaciones;
    private javax.swing.JTextArea txtComentario;
    private javax.swing.JTextField txtPuntaje;
    // End of variables declaration//GEN-END:variables
}
