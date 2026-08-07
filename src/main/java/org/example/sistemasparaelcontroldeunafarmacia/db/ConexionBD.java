package org.example.sistemasparaelcontroldeunafarmacia.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private Connection conexion;
    private static ConexionBD instancia;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/farmacia";
    private static final String URL2 = "jdbc:mysql://localhost:3306/farmacia?serverTimezone=UTC&useSSL=false";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    // Constructor privado que registra el driver de MySQL una sola vez
    private ConexionBD() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver JDBC: " + e.getMessage());
        }
    }

    // Método estático para obtener un solo objeto de esta clase (Singleton)
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    // Método público para obtener la conexión (valida si está nula o cerrada)
    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL2, USUARIO, PASSWORD);
                System.out.println("Conexión activa obtenida correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return conexion;
    }
}