package org.example.sistemasparaelcontroldeunafarmacia.dao;

import org.example.sistemasparaelcontroldeunafarmacia.db.ConexionBD;
import org.example.sistemasparaelcontroldeunafarmacia.model.ProductoVenta;
import org.example.sistemasparaelcontroldeunafarmacia.model.ReporteVentaDia;
import org.example.sistemasparaelcontroldeunafarmacia.model.ReporteVentaMes;
import org.example.sistemasparaelcontroldeunafarmacia.model.ReporteVentaSemana;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    public boolean registrarVenta(int codigoCliente, int idEmpleado, double total, List<ProductoVenta> items) {
        String sqlVenta = "INSERT INTO venta (totalpagar, codigoCliente, idEmpleado) VALUES (?, ?, ?)";
        String sqlBuscarCodigo = "SELECT codigo FROM producto WHERE nombre = ?";
        String sqlContiene = "INSERT INTO contiene (numeroNota, codigoProducto, cantidadsalida, precioventa) VALUES (?, ?, ?, ?)";
        String sqlRestarStock = "UPDATE producto SET existencia = existencia - ? WHERE codigo = ?";

        Connection cn = null;
        try {
            cn = ConexionBD.getInstancia().getConexion();
            cn.setAutoCommit(false);

            int numeroNota = -1;
            try (PreparedStatement psVenta = cn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                psVenta.setDouble(1, total);
                psVenta.setInt(2, codigoCliente);
                psVenta.setInt(3, idEmpleado);
                psVenta.executeUpdate();

                try (ResultSet rs = psVenta.getGeneratedKeys()) {
                    if (rs.next()) numeroNota = rs.getInt(1);
                }
            }

            if (numeroNota == -1) {
                cn.rollback();
                return false;
            }

            try (PreparedStatement psBuscar = cn.prepareStatement(sqlBuscarCodigo);
                 PreparedStatement psContiene = cn.prepareStatement(sqlContiene);
                 PreparedStatement psStock = cn.prepareStatement(sqlRestarStock)) {

                for (ProductoVenta item : items) {
                    psBuscar.setString(1, item.getProducto());
                    int codigoProducto = -1;
                    try (ResultSet rs = psBuscar.executeQuery()) {
                        if (rs.next()) {
                            codigoProducto = rs.getInt("codigo");
                        }
                    }

                    if (codigoProducto != -1) {
                        psContiene.setInt(1, numeroNota);
                        psContiene.setInt(2, codigoProducto);
                        psContiene.setInt(3, item.getCantidad());
                        psContiene.setDouble(4, item.getPrecio());
                        psContiene.addBatch();

                        psStock.setInt(1, item.getCantidad());
                        psStock.setInt(2, codigoProducto);
                        psStock.addBatch();
                    }
                }
                psContiene.executeBatch();
                psStock.executeBatch();
            }

            cn.commit();
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

    public List<ReporteVentaDia> obtenerVentasDia(LocalDate fecha) {
        List<ReporteVentaDia> lista = new ArrayList<>();
        String sql = "SELECT v.numeroNota, COALESCE(p.nombre, 'Sin nombre') AS nombre, " +
                "c.cantidadsalida, c.precioventa, (c.cantidadsalida * c.precioventa) AS total " +
                "FROM venta v JOIN contiene c ON v.numeroNota = c.numeroNota " +
                "LEFT JOIN producto p ON c.codigoProducto = p.codigo " +
                "WHERE DATE(v.fecha) = ?";
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            // Se usa el String directo (YYYY-MM-DD) para evitar desfases de zona horaria JDBC
            ps.setString(1, fecha.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ReporteVentaDia(
                        rs.getInt("numeroNota"), rs.getString("nombre"),
                        rs.getInt("cantidadsalida"), rs.getDouble("precioventa"),
                        rs.getDouble("total")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<ReporteVentaSemana> obtenerVentasSemana() {
        List<ReporteVentaSemana> lista = new ArrayList<>();
        String sql = "SELECT CONCAT('Semana ', WEEK(v.fecha)) AS semana, DATE(v.fecha) AS fecha, " +
                "SUM(c.cantidadsalida) AS piezas, SUM(c.cantidadsalida * c.precioventa) AS total " +
                "FROM venta v JOIN contiene c ON v.numeroNota = c.numeroNota " +
                "GROUP BY WEEK(v.fecha), DATE(v.fecha)";
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new ReporteVentaSemana(
                        rs.getString("semana"), rs.getString("fecha"),
                        rs.getInt("piezas"), rs.getDouble("total")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<ReporteVentaMes> obtenerVentasMes() {
        List<ReporteVentaMes> lista = new ArrayList<>();
        // ELT fuerzan el nombre del mes a español independientemente del locale de MySQL
        String sql = "SELECT ELT(MONTH(v.fecha), 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre') AS mes, " +
                "SUM(c.cantidadsalida) AS piezas, " +
                "SUM(c.cantidadsalida * c.precioventa) AS total " +
                "FROM venta v JOIN contiene c ON v.numeroNota = c.numeroNota " +
                "GROUP BY MONTH(v.fecha)";
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new ReporteVentaMes(
                        rs.getString("mes"), rs.getInt("piezas"), rs.getDouble("total")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}