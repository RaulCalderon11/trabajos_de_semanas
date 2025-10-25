package com.mycompany.loginn;

import Clases.CLogin;
import javax.swing.JOptionPane;

public class FormLogin extends javax.swing.JFrame {
    // Límite de intentos
    private int intentosFallidos = 0;
    private static final int MAX_INTENTOS = 3;

    public FormLogin() {
        initComponents();
        this.setLocationRelativeTo(null); // Centrar ventana
        agregarValidacionTiempoReal();
        agregarPlaceholders(); // AGREGAR PLACEHOLDERS
    }

    /**
     * MÉTODO PARA VALIDAR CAMPOS DEL LOGIN
     */
    private boolean validarCamposLogin() {
        // Validar que el usuario no esté vacío o sea el placeholder
        String usuario = txtUsuario.getText().trim();
        if (usuario.isEmpty() || usuario.equals("Ingrese su usuario")) {
            JOptionPane.showMessageDialog(this, 
                "❌ El campo USUARIO es obligatorio", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            txtUsuario.requestFocus();
            return false;
        }
        
        // Validar que la contraseña no esté vacía
        String contrasenia = String.valueOf(txtContrasenia.getPassword());
        if (contrasenia.isEmpty() || contrasenia.equals("••••")) {
            JOptionPane.showMessageDialog(this, 
                "❌ El campo CONTRASEÑA es obligatorio", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            txtContrasenia.requestFocus();
            return false;
        }
        
        // Validar longitud mínima de contraseña
        if (contrasenia.length() < 4 && !contrasenia.equals("••••")) {
            JOptionPane.showMessageDialog(this, 
                "❌ La contraseña debe tener al menos 4 caracteres", 
                "Contraseña Inválida", 
                JOptionPane.WARNING_MESSAGE);
            txtContrasenia.requestFocus();
            txtContrasenia.selectAll();
            return false;
        }
        
        // Validar que el usuario no tenga espacios
        if (usuario.contains(" ")) {
            JOptionPane.showMessageDialog(this, 
                "❌ El usuario no puede contener espacios", 
                "Usuario Inválido", 
                JOptionPane.WARNING_MESSAGE);
            txtUsuario.requestFocus();
            txtUsuario.selectAll();
            return false;
        }
        
        // Si pasa todas las validaciones
        return true;
    }
    
    /**
     * MÉTODO PARA LIMPIAR CAMPOS (útil para reutilizar)
     */
    public void limpiarCampos() {
        // Restaurar placeholders
        txtUsuario.setText("Ingrese su usuario");
        txtUsuario.setForeground(java.awt.Color.GRAY);
        
        txtContrasenia.setText("••••");
        txtContrasenia.setEchoChar((char) 0); // Mostrar texto temporalmente
        txtContrasenia.setForeground(java.awt.Color.GRAY);
        
        txtUsuario.requestFocus();
    }
    
    /**
     * AGREGAR PLACEHOLDERS MEJORADOS
     */
    private void agregarPlaceholders() {
        // Placeholder para usuario
        agregarPlaceholderUsuario();
        
        // Placeholder para contraseña
        agregarPlaceholderContrasenia();
    }
    
    private void agregarPlaceholderUsuario() {
        txtUsuario.setText("Ingrese su usuario");
        txtUsuario.setForeground(java.awt.Color.GRAY);
        
        txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtUsuario.getText().equals("Ingrese su usuario")) {
                    txtUsuario.setText("");
                    txtUsuario.setForeground(java.awt.Color.BLACK);
                }
            }
            
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtUsuario.getText().isEmpty()) {
                    txtUsuario.setText("Ingrese su usuario");
                    txtUsuario.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }
    
    private void agregarPlaceholderContrasenia() {
        txtContrasenia.setText("••••");
        txtContrasenia.setEchoChar((char) 0); // Mostrar texto temporalmente
        txtContrasenia.setForeground(java.awt.Color.GRAY);
        
        txtContrasenia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (String.valueOf(txtContrasenia.getPassword()).equals("••••")) {
                    txtContrasenia.setText("");
                    txtContrasenia.setEchoChar('•'); // Ocultar con puntos
                    txtContrasenia.setForeground(java.awt.Color.BLACK);
                }
            }
            
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (String.valueOf(txtContrasenia.getPassword()).isEmpty()) {
                    txtContrasenia.setText("••••");
                    txtContrasenia.setEchoChar((char) 0); // Mostrar texto
                    txtContrasenia.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }
    
    // Método para agregar validación en tiempo real
    private void agregarValidacionTiempoReal() {
        // Validar usuario mientras escribe
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validarUsuarioTiempoReal();
            }
        });

        // Validar contraseña mientras escribe
        txtContrasenia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validarContraseniaTiempoReal();
            }
        });
    }

    private void validarUsuarioTiempoReal() {
        String usuario = txtUsuario.getText().trim();
        // Solo validar si no es el placeholder
        if (!usuario.equals("Ingrese su usuario") && !usuario.isEmpty()) {
            if (usuario.contains(" ")) {
                txtUsuario.setToolTipText("El usuario no puede contener espacios");
                txtUsuario.setBackground(new java.awt.Color(255, 230, 230)); // Rojo claro
            } else {
                txtUsuario.setToolTipText(null);
                txtUsuario.setBackground(java.awt.Color.WHITE);
            }
        } else {
            txtUsuario.setBackground(java.awt.Color.WHITE);
        }
    }

    private void validarContraseniaTiempoReal() {
        String contrasenia = String.valueOf(txtContrasenia.getPassword());
        // Solo validar si no es el placeholder
        if (!contrasenia.equals("••••") && !contrasenia.isEmpty()) {
            if (contrasenia.length() > 0 && contrasenia.length() < 4) {
                txtContrasenia.setToolTipText("Mínimo 4 caracteres");
                txtContrasenia.setBackground(new java.awt.Color(255, 230, 230));
            } else {
                txtContrasenia.setToolTipText(null);
                txtContrasenia.setBackground(java.awt.Color.WHITE);
            }
        } else {
            txtContrasenia.setBackground(java.awt.Color.WHITE);
        }
    }                                                                                                               

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Right = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Left = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtContrasenia = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");
        setPreferredSize(new java.awt.Dimension(800, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        Right.setBackground(new java.awt.Color(30, 90, 172));
        Right.setPreferredSize(new java.awt.Dimension(400, 500));

        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\FLOR NIDIA\\Downloads\\Loginn\\src\\main\\java\\Icon\\UplaING.png")); // NOI18N

        jLabel6.setFont(new java.awt.Font("Magneto", 1, 24)); // NOI18N
        jLabel6.setText("UPLA");

        javax.swing.GroupLayout RightLayout = new javax.swing.GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightLayout.createSequentialGroup()
                .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel5))
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(jLabel6)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        RightLayout.setVerticalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jLabel5)
                .addGap(28, 28, 28)
                .addComponent(jLabel6)
                .addContainerGap(158, Short.MAX_VALUE))
        );

        jPanel1.add(Right);
        Right.setBounds(0, 0, 380, 530);

        Left.setBackground(new java.awt.Color(255, 255, 255));
        Left.setMinimumSize(new java.awt.Dimension(400, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(30, 90, 172));
        jLabel1.setText("LOGIN");

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Usuario: ");

        txtUsuario.setBackground(new java.awt.Color(102, 102, 102));
        txtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(255, 255, 255));
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(102, 102, 102));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Contraseña:");

        txtContrasenia.setBackground(new java.awt.Color(102, 102, 102));
        txtContrasenia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContrasenia.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(30, 90, 172));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("No tengo una cuenta!!");

        jButton2.setForeground(new java.awt.Color(255, 0, 0));
        jButton2.setText("Sign Up");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LeftLayout = new javax.swing.GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(LeftLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2))
                            .addComponent(jButton1)
                            .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtContrasenia)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))))
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel1)))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        LeftLayout.setVerticalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel1)
                .addGap(46, 46, 46)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButton2))
                .addContainerGap(135, Short.MAX_VALUE))
        );

        jPanel1.add(Left);
        Left.setBounds(380, 0, 440, 530);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
         // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // VALIDAR CAMPOS ANTES DE PROCESAR EL LOGIN
        if (validarCamposLogin()) {
            if (intentosFallidos < MAX_INTENTOS) {
                Clases.CLogin objetoLogin = new Clases.CLogin();
                
                // CORRECCIÓN: Solo incrementar intentos si el login falla
                // Creamos una variable para saber si el login fue exitoso
                boolean loginExitoso = objetoLogin.validaUsuario(txtUsuario, txtContrasenia);
                
                // SOLO INCREMENTAR SI EL LOGIN FALLÓ
                if (!loginExitoso) {
                    intentosFallidos++;
                    
                    if (intentosFallidos >= MAX_INTENTOS) {
                        JOptionPane.showMessageDialog(this,
                            "❌ Demasiados intentos fallidos\n" +
                            "La aplicación se cerrará por seguridad.",
                            "Seguridad",
                            JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "❌ Credenciales incorrectas\n" +
                            "Intentos restantes: " + (MAX_INTENTOS - intentosFallidos),
                            "Error de Login",
                            JOptionPane.WARNING_MESSAGE);
                    }
                }
                // Si el login fue exitoso, NO incrementar intentosFallidos
            } else {
                JOptionPane.showMessageDialog(this,
                    "❌ Demasiados intentos fallidos\n" +
                    "La aplicación se cerrará por seguridad.",
                    "Seguridad",
                    JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        FormSignUp SignupFrame = new FormSignUp();
        SignupFrame.setVisible(true);
        SignupFrame.pack();
        SignupFrame.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormLogin().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Left;
    private javax.swing.JPanel Right;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtContrasenia;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
