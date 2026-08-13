package org.example.sistemasparaelcontroldeunafarmacia.dao;

import org.example.sistemasparaelcontroldeunafarmacia.db.ConexionBD;
import org.example.sistemasparaelcontroldeunafarmacia.model.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpleadoDAO {

    public Empleado autenticar(String correo, String clave) {
        String sql = "SELECT * FROM empleado WHERE correo = ? AND clave = ?";
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, clave);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Empleado(
                            rs.getInt("idEmpleado"),
                            rs.getString("nombre"),
                            rs.getString("apellidoPaterno"),
                            rs.getString("apellidoMaterno"),
                            rs.getString("clave"),
                            rs.getString("correo"),
                            rs.getString("telefono"),
                            rs.getString("puesto")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertar(Empleado emp) {
        String sql = "INSERT INTO empleado (nombre, apellidoPaterno, apellidoMaterno, clave, correo, telefono, puesto) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getApellidoPaterno());
            ps.setString(3, emp.getApellidoMaterno());
            ps.setString(4, emp.getClave());
            ps.setString(5, emp.getCorreo());
            ps.setString(6, emp.getTelefono());
            ps.setString(7, emp.getPuesto());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verificarAdministrador(String correo, String clave) {
        String sql = "SELECT * FROM empleado WHERE correo = ? AND clave = ? AND puesto = 'Admin' or puesto = 'Administrador'";
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, clave);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarClave(String correo, String nuevaClave) {
        String sql = "UPDATE empleado SET clave = ? WHERE correo = ?";
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nuevaClave);
            ps.setString(2, correo);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Empleado emp) {
        String sql = "UPDATE empleado SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, clave = ?, correo = ?, telefono = ?, puesto = ? WHERE idEmpleado = ?";
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getApellidoPaterno());
            ps.setString(3, emp.getApellidoMaterno());
            ps.setString(4, emp.getClave());
            ps.setString(5, emp.getCorreo());
            ps.setString(6, emp.getTelefono());
            ps.setString(7, emp.getPuesto());
            ps.setInt(8, emp.getIdEmpleado());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idEmpleado) {
        String sql = "DELETE FROM empleado WHERE idEmpleado = ?";
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}