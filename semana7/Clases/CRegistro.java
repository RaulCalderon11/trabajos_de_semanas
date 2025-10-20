package Clases;

import java.sql.PreparedStatement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CRegistro {
    
    public boolean registrarUsuario(JTextField nombre, JTextField usuario, 
                                   JPasswordField contrasenia, JTextField email, 
                                   JComboBox<String> comboRol) {
        
        try {
            // Validar campos vacíos
            if (nombre.getText().isEmpty() || usuario.getText().isEmpty() || 
                String.valueOf(contrasenia.getPassword()).isEmpty() || email.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                return false;
            }
            
            // Validar longitud de contraseña
            String contra = String.valueOf(contrasenia.getPassword());
            if (contra.length() < 6) {
                JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 6 caracteres");
                return false;
            }
            
            Clases.CConexion objetoConexion = new Clases.CConexion();
            
            // Verificar si el usuario ya existe
            String verificar = "SELECT * FROM Usuarios WHERE ingresoUsuario = ? OR email = ?";
            PreparedStatement psVerificar = objetoConexion.estableceConexion().prepareStatement(verificar);
            psVerificar.setString(1, usuario.getText());
            psVerificar.setString(2, email.getText());
            
            if (psVerificar.executeQuery().next()) {
                JOptionPane.showMessageDialog(null, "El usuario o email ya existen");
                return false;
            }
            
            // Insertar nuevo usuario
            String consulta = "INSERT INTO Usuarios (nombre, ingresoUsuario, ingresoContrasenia, email, rol) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            
            ps.setString(1, nombre.getText());
            ps.setString(2, usuario.getText());
            ps.setString(3, contra);
            ps.setString(4, email.getText());
            ps.setString(5, comboRol.getSelectedItem().toString());
            
            int resultado = ps.executeUpdate();
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "¡Registro exitoso! Ahora puedes iniciar sesión");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Error en el registro");
                return false;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.toString());
            return false;
        }
    }
}
