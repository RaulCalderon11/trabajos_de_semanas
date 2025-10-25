package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class CConexion {
    Connection conectar;
    
    // DATOS DE CONEXIÓN
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_gestion_academica";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // USAR EL NOMBRE ORIGINAL que esperan las otras clases
    public Connection estableceConexion() {
        try {
            Class.forName(DRIVER);
            conectar = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                "Error: No se encontró el driver de MySQL\n" +
                "Verifica que la dependencia esté en pom.xml\n" +
                "Error: " + e.getMessage(), 
                "Error de Driver", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error de conexión a la base de datos:\n" +
                "URL: " + URL + "\n" +
                "Usuario: " + USER + "\n" +
                "Error: " + e.getMessage(), 
                "Error de Conexión", 
                JOptionPane.ERROR_MESSAGE);
        }
        return conectar;
    }
    
    // Método para probar la conexión
    public static void probarConexion() {
        CConexion con = new CConexion();
        Connection conexion = con.estableceConexion();
        
        if (conexion != null) {
            JOptionPane.showMessageDialog(null, 
                "✅ CONEXIÓN EXITOSA\n\n" +
                "Base de datos: sistema_gestion_academica\n" +
                "Servidor: localhost:3306\n" +
                "Usuario: admin", 
                "Prueba de Conexión", 
                JOptionPane.INFORMATION_MESSAGE);
            try {
                conexion.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ ERROR DE CONEXIÓN\n\n" +
                "Verifica que:\n" +
                "1. MySQL esté ejecutándose\n" +
                "2. La base de datos exista\n" +
                "3. El usuario y contraseña sean correctos", 
                "Error de Conexión", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
