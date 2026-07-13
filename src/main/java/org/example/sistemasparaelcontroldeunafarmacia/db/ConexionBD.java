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


    //Constructor privado para evitar crear objetos.
    private ConexionBD() {
        try{
            conexion = DriverManager.getConnection(URL,USUARIO,PASSWORD);
            System.out.println("Conexión exitosa!");
        }
        catch (SQLException e){
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }


    // Metodo estatico para obtener un solo objeto de esta clase
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }


    // Metodo público para obtener la conexion
    public Connection getConexion() {
        if (conexion == null) {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e) { }
        }
        return conexion;
    }


}
