package org.example.sistemasparaelcontroldeunafarmacia.dao;

import org.example.sistemasparaelcontroldeunafarmacia.db.ConexionBD;
import org.example.sistemasparaelcontroldeunafarmacia.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // CREATE (INSERTAR)
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO producto (nombre, existencia, precioVenta) VALUES (?, ?, ?)";

        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, producto.getNombre());
                ps.setInt(2, producto.getExistencia());
                ps.setFloat(3, producto.getPrecioVenta());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    // READ (LISTAR TODOS)
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE estado = true";

        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                while (rs.next()) {
                    Producto p = new Producto(
                            rs.getInt("codigo"),
                            rs.getString("nombre"),
                            rs.getInt("existencia"),
                            rs.getFloat("precioVenta")
                    );
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }

        return lista;
    }

    // READ (BUSCAR POR CÓDIGO)
    public Producto buscarPorId(int codigo) {
        String sql = "SELECT * FROM producto WHERE codigo = ?";

        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, codigo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Producto(
                                rs.getInt("codigo"),
                                rs.getString("nombre"),
                                rs.getInt("existencia"),
                                rs.getFloat("precioVenta")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }

        return null;
    }

    // UPDATE (ACTUALIZAR)
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre=?, existencia=?, precioVenta=? WHERE codigo=?";

        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, producto.getNombre());
                ps.setInt(2, producto.getExistencia());
                ps.setFloat(3, producto.getPrecioVenta());
                ps.setInt(4, producto.getCodigo());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    // DELETE (ELIMINAR)
    public boolean eliminar(int codigo) {
        String sql = "UPDATE producto SET estado = FALSE WHERE codigo = ?";
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Producto buscarPorNombreOCodigo(String busqueda) {
        String sql = "SELECT * FROM producto WHERE codigo = ? OR nombre LIKE ?";

        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                int codigo = -1;
                try {
                    codigo = Integer.parseInt(busqueda);
                } catch (NumberFormatException ignored) {
                }

                ps.setInt(1, codigo);
                ps.setString(2, "%" + busqueda + "%");

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Producto(
                                rs.getInt("codigo"),
                                rs.getString("nombre"),
                                rs.getInt("existencia"),
                                rs.getFloat("precioVenta")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }

        return null;
    }
}