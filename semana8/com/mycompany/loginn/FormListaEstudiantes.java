package com.mycompany.loginn;

import Clases.CConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormListaEstudiantes extends javax.swing.JFrame {

    private int profesorId;
    
    public FormListaEstudiantes(int profesorId) {
        initComponents();
        this.profesorId = profesorId;
        this.setLocationRelativeTo(null);
        this.setTitle("Lista de Estudiantes por Curso");
        
        cargarCursos();
        cargarEstudiantes();
    }
    
    private void cargarCursos() {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT id, nombre_curso FROM Cursos WHERE profesor_id = ?";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setInt(1, profesorId);
            ResultSet rs = ps.executeQuery();
            
            comboCurso.removeAllItems();
            comboCurso.addItem("Todos los cursos");
            
            while (rs.next()) {
                comboCurso.addItem(rs.getString("nombre_curso") + "|" + rs.getInt("id"));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando cursos: " + e.toString());
        }
    }
    
    private void cargarEstudiantes() {
        try {
            CConexion conexion = new CConexion();
            String sql;
            PreparedStatement ps;
            
            if (comboCurso.getSelectedIndex() == 0) {
                // Todos los estudiantes que han tenido calificaciones con este profesor
                sql = "SELECT DISTINCT u.id, u.nombre, u.email, " +
                      "COUNT(cal.id) as total_calificaciones, " +
                      "AVG(cal.puntaje) as promedio " +
                      "FROM Usuarios u " +
                      "JOIN Calificaciones cal ON u.id = cal.estudiante_id " +
                      "JOIN Cursos c ON cal.curso_id = c.id " +
                      "WHERE u.rol = 'estudiante' AND c.profesor_id = ? " +
                      "GROUP BY u.id, u.nombre, u.email " +
                      "ORDER BY u.nombre";
                ps = conexion.estableceConexion().prepareStatement(sql);
                ps.setInt(1, profesorId);
            } else {
                // Estudiantes de un curso específico
                String cursoInfo = comboCurso.getSelectedItem().toString();
                int cursoId = Integer.parseInt(cursoInfo.split("\\|")[1]);
                
                sql = "SELECT DISTINCT u.id, u.nombre, u.email, " +
                      "COUNT(cal.id) as total_calificaciones, " +
                      "AVG(cal.puntaje) as promedio " +
                      "FROM Usuarios u " +
                      "JOIN Calificaciones cal ON u.id = cal.estudiante_id " +
                      "WHERE u.rol = 'estudiante' AND cal.curso_id = ? " +
                      "GROUP BY u.id, u.nombre, u.email " +
                      "ORDER BY u.nombre";
                ps = conexion.estableceConexion().prepareStatement(sql);
                ps.setInt(1, cursoId);
            }
            
            ResultSet rs = ps.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) tablaEstudiantes.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getInt("total_calificaciones"),
                    String.format("%.2f", rs.getDouble("promedio"))
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando estudiantes: " + e.toString());
        }
    }

    private void comboCursoActionPerformed(java.awt.event.ActionEvent evt) {                                           
        cargarEstudiantes();
    }                                                                                  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        comboCurso = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEstudiantes = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        comboCurso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tablaEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaEstudiantes);

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Snap ITC", 0, 24)); // NOI18N
        jLabel1.setText("Lista de cursos del profesor");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(comboCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnVolver)
                .addGap(20, 20, 20))
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
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboCurso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaEstudiantes;
    // End of variables declaration//GEN-END:variables
}
