
package com.mycompany.loginn;
import Clases.CConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormGestionProfesores extends javax.swing.JFrame {


    public FormGestionProfesores() {
        initComponents();
        this.setLocationRelativeTo(null);
        cargarProfesores();
    }
    
    private void cargarProfesores() {
        try {
            CConexion conexion = new CConexion();
            String sql = "SELECT u.id, u.nombre, u.ingresoUsuario, u.email, " +
                        "u.fecha_registro, u.telefono, " +
                        "COUNT(DISTINCT c.id) as total_cursos " +
                        "FROM Usuarios u " +
                        "LEFT JOIN Cursos c ON u.id = c.profesor_id " +
                        "WHERE u.rol = 'profesor' AND u.activo = TRUE " +
                        "GROUP BY u.id, u.nombre, u.ingresoUsuario, u.email, u.fecha_registro, u.telefono " +
                        "ORDER BY u.nombre";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tablaProfesores.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("ingresoUsuario"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getInt("total_cursos"),
                    rs.getTimestamp("fecha_registro")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando profesores: " + e.toString());
        }
    }                                                                                                                                               

    private void eliminarProfesor(int id) {
        try {
            CConexion conexion = new CConexion();
            String sql = "DELETE FROM Usuarios WHERE id = ? AND rol = 'profesor'";
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setInt(1, id);
            
            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Profesor eliminado exitosamente");
                cargarProfesores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar profesor");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.toString());
        }
    }
    
    public void actualizarTabla() {
        cargarProfesores();
    }                                                                              
                                                     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProfesores = new javax.swing.JTable();
        btnAgregarProfesor = new javax.swing.JButton();
        btnEditarProfesor = new javax.swing.JButton();
        btnEliminarProfesor = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Snap ITC", 0, 24)); // NOI18N
        jLabel1.setText("\"Gestión de Profesores\"");

        tablaProfesores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Usuario", "Email", "Teléfono", "Total Cursos", "Fecha Registro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaProfesores);

        jScrollPane1.setViewportView(jScrollPane2);

        btnAgregarProfesor.setText("Agregar Profesor");
        btnAgregarProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProfesorActionPerformed(evt);
            }
        });

        btnEditarProfesor.setText("Editar Profesor");
        btnEditarProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProfesorActionPerformed(evt);
            }
        });

        btnEliminarProfesor.setText("Eliminar Profesor");
        btnEliminarProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProfesorActionPerformed(evt);
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAgregarProfesor)
                            .addComponent(btnEditarProfesor)
                            .addComponent(btnEliminarProfesor)
                            .addComponent(btnVolver)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(btnAgregarProfesor)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditarProfesor)
                        .addGap(29, 29, 29)
                        .addComponent(btnEliminarProfesor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnEliminarProfesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProfesorActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaProfesores.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un profesor");
            return;
        }
        
        int idProfesor = (int) tablaProfesores.getValueAt(filaSeleccionada, 0);
        String nombreProfesor = (String) tablaProfesores.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar al profesor: " + nombreProfesor + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            eliminarProfesor(idProfesor);
        }
    }//GEN-LAST:event_btnEliminarProfesorActionPerformed

    private void btnEditarProfesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProfesorActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaProfesores.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un profesor");
            return;
        }

        int idProfesor = (int) tablaProfesores.getValueAt(filaSeleccionada, 0);
        String nombre = (String) tablaProfesores.getValueAt(filaSeleccionada, 1);
        String usuario = (String) tablaProfesores.getValueAt(filaSeleccionada, 2);
        String email = (String) tablaProfesores.getValueAt(filaSeleccionada, 3);

        // CAMBIAR A FormEditarUsuario
        FormEditarUsuario editarUsuario = new FormEditarUsuario(idProfesor, nombre, usuario, email, "profesor", this);
        editarUsuario.setVisible(true);
        editarUsuario.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnEditarProfesorActionPerformed

    private void btnAgregarProfesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProfesorActionPerformed
        // TODO add your handling code here:
        FormAgregarUsuario agregarUsuario = new FormAgregarUsuario("profesor", this);
        agregarUsuario.setVisible(true);
        agregarUsuario.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnAgregarProfesorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProfesor;
    private javax.swing.JButton btnEditarProfesor;
    private javax.swing.JButton btnEliminarProfesor;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaProfesores;
    // End of variables declaration//GEN-END:variables
}
