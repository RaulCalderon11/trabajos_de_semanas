package Clases;


import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class CConexion {
    Connection conectar;
    
    String usuario="admin";
    String contrasenia="admin";
    String bd="login";
    String ip="localhost";
    String puerto="3306";
    
    String cadena = "jdbc:mysql://localhost:3306/login?serverTimezone=UTC";
    
    public Connection estableceConexion(){
    
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,contrasenia);
            //JOptionPane.showMessageDialog(null,"Se conectó correcta a la Base de DATOS");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Problemas en la conexion"+ e.toString());
        }
        return conectar;
    }
}
