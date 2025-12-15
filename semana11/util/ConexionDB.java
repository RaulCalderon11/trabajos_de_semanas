package com.mitesis.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión a la BBDD.
 * (MODIFICADO: Ahora usa DriverManager directamente, SIN JNDI/context.xml)
 */
public class ConexionDB {

    // 1. Datos de la conexión (hardcodeados)
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_tesis_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "admin123";

    // 2. Patrón Singleton (para que tus DAOs no fallen)
    private static ConexionDB instancia;

    /**
     * Constructor privado que registra el driver de MySQL
     * una sola vez cuando se crea la instancia.
     */
    private ConexionDB() {
        try {
            // Registramos el driver
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            // Esto es un error fatal, si no hay driver, la app no puede funcionar
            throw new RuntimeException("Error: No se pudo cargar el driver de MySQL (com.mysql.cj.jdbc.Driver). ¿Falta el JAR?", e);
        }
    }

    /**
     * Método público para obtener la instancia (Singleton)
     */
    public static synchronized ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    /**
     * Método para obtener la conexión (¡Ahora usa DriverManager!)
     * @return Una nueva conexión a la base de datos.
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        // Cada vez que se llama, crea una nueva conexión.
        // Los DAOs son responsables de cerrarla (lo cual ya hacen).
        return DriverManager.getConnection(URL, USER, PASS);
    }
}