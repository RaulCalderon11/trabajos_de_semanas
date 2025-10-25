
package PQT_Form;

import java.awt.Dimension;
import javax.swing.JFrame;

public class FPrincipal extends javax.swing.JFrame {

    public FPrincipal() {
        initComponents();
        //maximizado
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        Panel_Contenedor = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        mnu_Gestionar = new javax.swing.JMenu();
        smn_geslibros = new javax.swing.JMenuItem();
        smn_lectores = new javax.swing.JMenuItem();
        mnu_prestamo = new javax.swing.JMenu();
        smn_prestamo = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        jMenu2.setText("File");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PQT_Form/logo3.jpg"))); // NOI18N

        Panel_Contenedor.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout Panel_ContenedorLayout = new javax.swing.GroupLayout(Panel_Contenedor);
        Panel_Contenedor.setLayout(Panel_ContenedorLayout);
        Panel_ContenedorLayout.setHorizontalGroup(
            Panel_ContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ContenedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_ContenedorLayout.setVerticalGroup(
            Panel_ContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setText("File");

        jMenuItem1.setText("jMenuItem1");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        mnu_Gestionar.setText("Gestionar");

        smn_geslibros.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        smn_geslibros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PQT_Src/lib.png"))); // NOI18N
        smn_geslibros.setText("Gestión de StockLibros");
        smn_geslibros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smn_geslibrosActionPerformed(evt);
            }
        });
        mnu_Gestionar.add(smn_geslibros);

        smn_lectores.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        smn_lectores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PQT_Src/lecors.png"))); // NOI18N
        smn_lectores.setText("Gestón de Clientes");
        smn_lectores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smn_lectoresActionPerformed(evt);
            }
        });
        mnu_Gestionar.add(smn_lectores);

        jMenuBar1.add(mnu_Gestionar);

        mnu_prestamo.setText("Prestamo");

        smn_prestamo.setText("Prestamo Libro");
        smn_prestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smn_prestamoActionPerformed(evt);
            }
        });
        mnu_prestamo.add(smn_prestamo);

        jMenuBar1.add(mnu_prestamo);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Contenedor)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Contenedor)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void smn_geslibrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smn_geslibrosActionPerformed
        FLibros internoForm = new FLibros();
        this.Panel_Contenedor.add(internoForm);
        Dimension desktopSize = Panel_Contenedor.getSize();
        Dimension FrameSize = internoForm.getSize();
        internoForm.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
       
        internoForm.show();    }//GEN-LAST:event_smn_geslibrosActionPerformed

    private void smn_lectoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smn_lectoresActionPerformed
        FCliente internoForm = new FCliente();
        this.Panel_Contenedor.add(internoForm);
        Dimension desktopSize = Panel_Contenedor.getSize();
        Dimension FrameSize = internoForm.getSize();
        internoForm.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
         internoForm.show();
        // TODO add your handling code here:
    }//GEN-LAST:event_smn_lectoresActionPerformed

    private void smn_prestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smn_prestamoActionPerformed

    }//GEN-LAST:event_smn_prestamoActionPerformed

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
            java.util.logging.Logger.getLogger(FPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane Panel_Contenedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenu mnu_Gestionar;
    private javax.swing.JMenu mnu_prestamo;
    private javax.swing.JMenuItem smn_geslibros;
    private javax.swing.JMenuItem smn_lectores;
    private javax.swing.JMenuItem smn_prestamo;
    // End of variables declaration//GEN-END:variables
}
