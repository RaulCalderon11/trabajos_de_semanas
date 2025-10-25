package com.mycompany.loginn;

import Clases.CConexion;
import javax.swing.JOptionPane;

public class TestConexion {
    public static void main(String[] args) {
        // Probar la conexión
        CConexion.probarConexion();
        
        // También puedes probar consultas simples
        probarConsultaSimple();
    }
    
    public static void probarConsultaSimple() {
        try {
            CConexion conexion = new CConexion();
            java.sql.Connection con = conexion.estableceConexion();
            
            if (con != null) {
                String sql = "SELECT COUNT(*) as total FROM Usuarios";
                java.sql.PreparedStatement ps = con.prepareStatement(sql);
                java.sql.ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    int totalUsuarios = rs.getInt("total");
                    JOptionPane.showMessageDialog(null, 
                        "✅ CONEXIÓN Y CONSULTA EXITOSAS\n\n" +
                        "Total de usuarios en la base de datos: " + totalUsuarios, 
                        "Prueba Completa", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
                
                rs.close();
                ps.close();
                conexion.probarConexion();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error en consulta: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
