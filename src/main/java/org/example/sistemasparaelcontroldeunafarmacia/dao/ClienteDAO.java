package org.example.sistemasparaelcontroldeunafarmacia.dao;
import org.example.sistemasparaelcontroldeunafarmacia.db.ConexionBD;
import org.example.sistemasparaelcontroldeunafarmacia.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try {
            Connection cn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement ps = cn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Cliente c = new Cliente(
                            rs.getInt("codigo"),
                            rs.getString("nombre"),
                            rs.getString("direccion"),
                            rs.getString("rfc"),
                            rs.getString("telefono")
                    );
                    lista.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, direccion, rfc, telefono) VALUES (?, ?, ?, ?)";
        try {
            Connection cn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getDireccion());
                ps.setString(3, cliente.getRfc());
                ps.setString(4, cliente.getTelefono());

                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre = ?, direccion = ?, rfc = ?, telefono = ? WHERE codigo = ?";
        try {
            Connection cn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getDireccion());
                ps.setString(3, cliente.getRfc());
                ps.setString(4, cliente.getTelefono());
                ps.setInt(5, cliente.getCodigo());

                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int codigoCliente) {
        int idClienteGeneral = 1; // ID del cliente genérico "Público en General"

        if (codigoCliente == idClienteGeneral) {
            return false; // Evita eliminar al cliente genérico
        }

        String sqlReasignar = "UPDATE venta SET codigoCliente = ? WHERE codigoCliente = ?";
        String sqlEliminar = "DELETE FROM cliente WHERE codigo = ?";

        Connection cn = null;
        try {
            cn = ConexionBD.getInstancia().getConexion();
            cn.setAutoCommit(false); // Inicia transacción

            // 1. Mueve las ventas al cliente general
            try (PreparedStatement psReasignar = cn.prepareStatement(sqlReasignar)) {
                psReasignar.setInt(1, idClienteGeneral);
                psReasignar.setInt(2, codigoCliente);
                psReasignar.executeUpdate();
            }

            // 2. Elimina al cliente
            try (PreparedStatement psEliminar = cn.prepareStatement(sqlEliminar)) {
                psEliminar.setInt(1, codigoCliente);
                psEliminar.executeUpdate();
            }

            cn.commit(); // Confirma la operación
            return true;

        } catch (SQLException e) {
            if (cn != null) {
                try { cn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (cn != null) {
                try { cn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}
