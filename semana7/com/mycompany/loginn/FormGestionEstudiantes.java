
package com.mycompany.loginn;
import Clases.CConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormGestionEstudiantes extends javax.swing.JFrame {

    public FormGestionEstudiantes() {
        initComponents();
        this.setLocationRelativeTo(null);
        cargarEstudiantes();
    }

    private void cargarEstudiantes() {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT u.id, u.nombre, u.ingresoUsuario, u.email, " +
                        "u.telefono, u.fecha_nacimiento, u.fecha_registro, " +
                        "COUNT(DISTINCT cal.id) as total_calificaciones, " +
                        "AVG(cal.puntaje) as promedio_general " +
                        "FROM Usuarios u " +
                        "LEFT JOIN Calificaciones cal ON u.id = cal.estudiante_id " +
                        "WHERE u.rol = 'estudiante' AND u.activo = TRUE " +
                        "GROUP BY u.id, u.nombre, u.ingresoUsuario, u.email, u.telefono, u.fecha_nacimiento, u.fecha_registro " +
                        "ORDER BY u.nombre";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tablaEstudiantes.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                double promedio = rs.getDouble("promedio_general");
                String promedioStr = rs.wasNull() ? "Sin calificaciones" : String.format("%.2f", promedio);

                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("ingresoUsuario"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getDate("fecha_nacimiento"),
                    rs.getInt("total_calificaciones"),
                    promedioStr,
                    rs.getTimestamp("fecha_registro")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando estudiantes: " + e.toString());
        }
    }                                                                                                                                                     

    private void eliminarEstudiante(int id) {
        try {
            CConexion conexion = new CConexion();
            String sql = "DELETE FROM Usuarios WHERE id = ? AND rol = 'estudiante'";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setInt(1, id);
            
            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Estudiante eliminado exitosamente");
                cargarEstudiantes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar estudiante");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.toString());
        }
    }
    
    public void actualizarTabla() {
        cargarEstudiantes();
    }                                               
                                        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEstudiantes = new javax.swing.JTable();
        btnAgregarEstudiante = new javax.swing.JButton();
        btnEditarEstudiante = new javax.swing.JButton();
        btnEliminarEstudiante = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Snap ITC", 0, 24)); // NOI18N
        jLabel1.setText("\"Gestión de Estudiantes\"");

        tablaEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Usuario", "Email", "Telefono", "Fech.Nacim", "Total.Calif", "Fecha.Regis"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaEstudiantes);

        jScrollPane1.setViewportView(jScrollPane2);

        btnAgregarEstudiante.setText("Agregar Estudiante");
        btnAgregarEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEstudianteActionPerformed(evt);
            }
        });

        btnEditarEstudiante.setText("Editar Estudiante");
        btnEditarEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarEstudianteActionPerformed(evt);
            }
        });

        btnEliminarEstudiante.setText("Eliminar Estudiante");
        btnEliminarEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEstudianteActionPerformed(evt);
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
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnAgregarEstudiante)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(24, 24, 24)
                                    .addComponent(btnEditarEstudiante))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(26, 26, 26)
                                    .addComponent(btnVolver))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(24, 24, 24)
                                    .addComponent(btnEliminarEstudiante)))
                            .addGap(2, 2, 2))))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)
                        .addGap(43, 43, 43)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(btnAgregarEstudiante)
                        .addGap(32, 32, 32)
                        .addComponent(btnEditarEstudiante)
                        .addGap(27, 27, 27)
                        .addComponent(btnEliminarEstudiante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver)))
                .addContainerGap(17, Short.MAX_VALUE))
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
        FormAdminDashboard adminDashboard = new FormAdminDashboard("Administrador");
        adminDashboard.setVisible(true);
        adminDashboard.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnEliminarEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEstudianteActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un estudiante");
            return;
        }
        
        int idEstudiante = (int) tablaEstudiantes.getValueAt(filaSeleccionada, 0);
        String nombreEstudiante = (String) tablaEstudiantes.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar al estudiante: " + nombreEstudiante + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            eliminarEstudiante(idEstudiante);
        }
    }//GEN-LAST:event_btnEliminarEstudianteActionPerformed

    private void btnEditarEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarEstudianteActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un estudiante");
            return;
        }

        int idEstudiante = (int) tablaEstudiantes.getValueAt(filaSeleccionada, 0);
        String nombre = (String) tablaEstudiantes.getValueAt(filaSeleccionada, 1);
        String usuario = (String) tablaEstudiantes.getValueAt(filaSeleccionada, 2);
        String email = (String) tablaEstudiantes.getValueAt(filaSeleccionada, 3);

        // CAMBIAR A FormEditarUsuario
        FormEditarUsuario editarUsuario = new FormEditarUsuario(idEstudiante, nombre, usuario, email, "estudiante", this);
        editarUsuario.setVisible(true);
        editarUsuario.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnEditarEstudianteActionPerformed

    private void btnAgregarEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEstudianteActionPerformed
        // TODO add your handling code here:
        FormAgregarUsuario agregarUsuario = new FormAgregarUsuario("estudiante", this);
        agregarUsuario.setVisible(true);
        agregarUsuario.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnAgregarEstudianteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormGestionEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormGestionEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormGestionEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormGestionEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormGestionEstudiantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarEstudiante;
    private javax.swing.JButton btnEditarEstudiante;
    private javax.swing.JButton btnEliminarEstudiante;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaEstudiantes;
    // End of variables declaration//GEN-END:variables
}
