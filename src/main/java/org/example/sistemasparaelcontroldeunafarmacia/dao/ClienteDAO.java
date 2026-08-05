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

    public boolean eliminar(int codigo) {
        String sql = "DELETE FROM cliente WHERE codigo = ?";
        try {
            Connection cn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setInt(1, codigo);
                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
