package org.example.sistemasparaelcontroldeunafarmacia.dao;

import org.example.sistemasparaelcontroldeunafarmacia.db.ConexionBD;
import org.example.sistemasparaelcontroldeunafarmacia.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductoDAO {


    private final Connection conn;


    public ProductoDAO() {
        conn = ConexionBD.getInstancia().getConexion();
    }


    // CREATE


    public void insertar(Producto producto) {
        String sql = "INSERT INTO articulo (nombre, cantidad, existencia, precioVenta, fechaCaducidad) VALUES (?, ?, ?)";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setString(1, producto.getNombre());
            ps.setInt(2, producto.getCantidad());
            ps.setFloat(3, producto.getPrecioVenta());
            ps.setDate(4, (Date) producto.getFechaCaducidad());

            ps.executeUpdate();
            System.out.println("Producto insertado");


        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }


    // READ (LISTAR)


    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM articulo";


        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {


            while (rs.next()) {
                Producto a = new Producto(
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad"),
                        rs.getInt("existencia"),
                        rs.getFloat("precioVenta"),
                        rs.getDate("fechaCaducidad"));
                lista.add(a);
            }


        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }


        return lista;
    }


    // READ (POR ID)


    public Producto buscarPorId(int codigo) {
        String sql = "SELECT * FROM articulo WHERE codigo = ?";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                return new Producto(
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad"),
                        rs.getInt("existencia"),
                        rs.getFloat("precioVenta"),
                        rs.getDate("fechaCaducidad"));
            }


        } catch (SQLException e) {
            System.out.println(" Error al buscar: " + e.getMessage());
        }


        return null;
    }


    // UPDATE


    public void actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre=?, cantidad=?, existencia=?, precioVenta=?, fechaCaducidad=? WHERE codigo=?";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setString(1, producto.getNombre());
            ps.setInt(2, producto.getCantidad());
            ps.setInt(3,producto.getExistencia());
            ps.setFloat(4, producto.getPrecioVenta());
            ps.setDate(5, (Date) producto.getFechaCaducidad());
            ps.setInt(6, producto.getCodigo());


            ps.executeUpdate();
            System.out.println("Producto actualizado");


        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }


    // DELETE


    public void eliminar(int codigo) {
        String sql = "DELETE FROM articulo WHERE codigo=?";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setInt(1, codigo);
            ps.executeUpdate();


            System.out.println("Producto eliminado");


        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
}
