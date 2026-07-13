package org.example.sistemasparaelcontroldeunafarmacia;

import org.example.sistemasparaelcontroldeunafarmacia.db.ConexionBD;

public class TestConexion {
    public static void main(String[] args) {
        if(ConexionBD.getInstancia().getConexion()!=null){
            System.out.println("Conexion exitosa!");
        }
    }
}
