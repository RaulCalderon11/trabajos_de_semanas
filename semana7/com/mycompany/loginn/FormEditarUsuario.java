package com.mycompany.loginn;

import Clases.CConexion;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class FormEditarUsuario extends javax.swing.JFrame {

    private int idUsuario;
    private String tipoUsuario;
    private javax.swing.JFrame formularioPadre;
    
    // CONSTRUCTOR PARA EDITAR (con parámetros del usuario existente)
    public FormEditarUsuario(int id, String nombre, String usuario, String email, String tipoUsuario, javax.swing.JFrame padre) {
        initComponents();
        this.idUsuario = id;
        this.tipoUsuario = tipoUsuario;
        this.formularioPadre = padre;
        
        // Llenar campos con datos existentes
        txtNombre.setText(nombre);
        txtUsuario.setText(usuario);
        txtEmail.setText(email);
        
        this.setLocationRelativeTo(null);
        this.setTitle("Editar " + (tipoUsuario.equals("profesor") ? "Profesor" : "Estudiante"));
        lblTitulo.setText("Editar " + (tipoUsuario.equals("profesor") ? "Profesor" : "Estudiante"));
        
        // Opcional: cambiar texto del botón
        btnGuardar.setText("Actualizar");
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() ||
            txtUsuario.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return false;
        }
        
        // La contraseña es opcional en edición
        String contrasenia = String.valueOf(txtContrasenia.getPassword());
        if (!contrasenia.isEmpty() && contrasenia.length() < 6) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres");
            return false;
        }
        
        return true;
    }
    
    private void actualizarUsuario() {
        try {
            CConexion conexion = new CConexion();
            
            // Verificar si el usuario o email ya existen (excluyendo el actual)
            String verificar = "SELECT * FROM Usuarios WHERE (ingresoUsuario = ? OR email = ?) AND id != ?";
            PreparedStatement psVerificar = conexion.estableceConexion().prepareStatement(verificar);
            psVerificar.setString(1, txtUsuario.getText().trim());
            psVerificar.setString(2, txtEmail.getText().trim());
            psVerificar.setInt(3, idUsuario);
            
            if (psVerificar.executeQuery().next()) {
                JOptionPane.showMessageDialog(this, "El usuario o email ya existen");
                return;
            }
            
            // Actualizar usuario (con o sin contraseña)
            String sql;
            PreparedStatement ps;
            
            String contrasenia = String.valueOf(txtContrasenia.getPassword());
            if (!contrasenia.isEmpty()) {
                // Actualizar con nueva contraseña
                sql = "UPDATE Usuarios SET nombre = ?, ingresoUsuario = ?, ingresoContrasenia = ?, email = ? WHERE id = ?";
                ps = conexion.estableceConexion().prepareStatement(sql);
                ps.setString(1, txtNombre.getText().trim());
                ps.setString(2, txtUsuario.getText().trim());
                ps.setString(3, contrasenia);
                ps.setString(4, txtEmail.getText().trim());
                ps.setInt(5, idUsuario);
            } else {
                // Actualizar sin cambiar contraseña
                sql = "UPDATE Usuarios SET nombre = ?, ingresoUsuario = ?, email = ? WHERE id = ?";
                ps = conexion.estableceConexion().prepareStatement(sql);
                ps.setString(1, txtNombre.getText().trim());
                ps.setString(2, txtUsuario.getText().trim());
                ps.setString(3, txtEmail.getText().trim());
                ps.setInt(4, idUsuario);
            }
            
            int resultado = ps.executeUpdate();
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, 
                    (tipoUsuario.equals("profesor") ? "Profesor" : "Estudiante") + " actualizado exitosamente");
                
                // Actualizar tabla en el formulario padre
                if (formularioPadre instanceof FormGestionProfesores) {
                    ((FormGestionProfesores) formularioPadre).actualizarTabla();
                } else if (formularioPadre instanceof FormGestionEstudiantes) {
                    ((FormGestionEstudiantes) formularioPadre).actualizarTabla();
                }
                
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se realizaron cambios");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.toString());
        }
    }
                                         
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        txtContrasenia = new javax.swing.JPasswordField();
        txtEmail = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Snap ITC", 0, 24)); // NOI18N
        lblTitulo.setText("\"Editar ...\"");

        jLabel1.setText("Nombre:");

        jLabel2.setText("Usuario:");

        jLabel3.setText("Contraseña:");

        jLabel4.setText("Email:");

        jLabel5.setText("(*) Dejar contraseña vacía para no cambiarla");

        btnGuardar.setText("Actualizar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(41, 41, 41))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                    .addComponent(txtUsuario)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtContrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                    .addComponent(txtEmail))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCancelar)
                            .addComponent(jLabel5)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(lblTitulo)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblTitulo)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jLabel5)
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addContainerGap(19, Short.MAX_VALUE))
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

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (validarCampos()) {
            actualizarUsuario();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPasswordField txtContrasenia;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
