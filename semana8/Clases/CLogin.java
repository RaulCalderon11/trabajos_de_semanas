package Clases;

import com.mycompany.loginn.FormMenuPrincipal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CLogin {
    private String rolUsuario;
    private String nombreUsuario;
    private int idUsuario;
    
    public String getRolUsuario() { return rolUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public int getIdUsuario() { return idUsuario; }
    
    // CAMBIAR A boolean para saber si el login fue exitoso
    public boolean validaUsuario(JTextField usuario, JPasswordField contrasenia) {
        try {
            ResultSet rs = null;           
            PreparedStatement ps = null;
            
            Clases.CConexion objetoConexion = new Clases.CConexion();
            
            String consulta = "SELECT id, nombre, rol FROM Usuarios " +
                            "WHERE ingresoUsuario = ? AND ingresoContrasenia = ? " +
                            "AND activo = TRUE";
            
            ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            
            String contra = String.valueOf(contrasenia.getPassword());
            
            ps.setString(1, usuario.getText().trim());
            ps.setString(2, contra);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                rolUsuario = rs.getString("rol");
                nombreUsuario = rs.getString("nombre");
                idUsuario = rs.getInt("id");
                
                JOptionPane.showMessageDialog(null, 
                    "Bienvenido: " + nombreUsuario + " - Rol: " + rolUsuario,
                    "Login Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
                
                FormMenuPrincipal objetoMenu = new FormMenuPrincipal(rolUsuario, nombreUsuario, idUsuario);
                objetoMenu.setVisible(true);
                
                javax.swing.JFrame loginFrame = (javax.swing.JFrame) 
                    javax.swing.SwingUtilities.getWindowAncestor(usuario);
                loginFrame.dispose();
                
                return true; // LOGIN EXITOSO
                
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Usuario INCORRECTO, VUELVA A INTENTAR",
                    "Error de Login",
                    JOptionPane.ERROR_MESSAGE);
                return false; // LOGIN FALLIDO
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.toString());
            return false; // LOGIN FALLIDO
        }
    }
}