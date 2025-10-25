
package PQT_Clases;
//librerias de conexion
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CConexion {
    
    public static final String URL = "jdbc:mysql://localhost:3306/bdfarmacia";
    public static final String USER = "root";
    public static final String CLAVE = "taison";//preguntar por clave
    
    public Connection getConexion(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            System.out.println("Conexion Exitosa");
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return con;
    }
    
    
}
