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
        String sql = "SELECT " +
                "v.numeroNota AS nota, " +
                "c.nombre AS cliente, " +
                "CONCAT(e.nombre, ' ', e.apellidoPaterno) AS empleado, " +
                "p.nombre AS producto, " +
                "co.cantidadsalida AS piezas, " +
                "co.precioventa AS precio, " +
                "(co.cantidadsalida * co.precioventa) AS total " +
                "FROM venta v " +
                "INNER JOIN cliente c ON v.codigoCliente = c.codigo " +
                "INNER JOIN empleado e ON v.idEmpleado = e.idEmpleado " +
                "INNER JOIN contiene co ON v.numeroNota = co.numeroNota " +
                "INNER JOIN producto p ON co.codigoProducto = p.codigo " +
                "WHERE DATE(v.fecha) = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ReporteVentaDia report = new ReporteVentaDia();
                report.setNota(rs.getInt("nota"));
                report.setCliente(rs.getString("cliente"));
                report.setEmpleado(rs.getString("empleado"));
                report.setNombre(rs.getString("producto"));
                report.setPiezas(rs.getInt("piezas"));
                report.setPrecio(rs.getDouble("precio"));
                report.setTotal(rs.getDouble("total"));
                lista.add(report);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<ReporteVentaSemana> obtenerVentasSemana(int anio) {
        List<ReporteVentaSemana> lista = new ArrayList<>();
        String sql = "SELECT " +
                "CONCAT('Semana ', WEEK(v.fecha, 1)) AS semana, " +
                "DATE(DATE_SUB(v.fecha, INTERVAL WEEKDAY(v.fecha) DAY)) AS fecha, " +
                "SUM(c.cantidadsalida) AS piezas, " +
                "SUM(c.cantidadsalida * c.precioventa) AS total " +
                "FROM venta v " +
                "JOIN contiene c ON v.numeroNota = c.numeroNota " +
                "WHERE YEAR(v.fecha) = ? " +
                "GROUP BY YEAR(v.fecha), WEEK(v.fecha, 1) " +
                "ORDER BY fecha ASC";

        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, anio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ReporteVentaSemana(
                        rs.getString("semana"),
                        rs.getString("fecha"),
                        rs.getInt("piezas"),
                        rs.getDouble("total")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<ReporteVentaMes> obtenerVentasMes(int anio) {
        List<ReporteVentaMes> lista = new ArrayList<>();
        String sql = "SELECT ELT(MONTH(v.fecha), 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre') AS mes, " +
                "SUM(c.cantidadsalida) AS piezas, " +
                "SUM(c.cantidadsalida * c.precioventa) AS total " +
                "FROM venta v JOIN contiene c ON v.numeroNota = c.numeroNota " +
                "WHERE YEAR(v.fecha) = ? " +
                "GROUP BY MONTH(v.fecha)";

        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, anio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ReporteVentaMes(
                        rs.getString("mes"), rs.getInt("piezas"), rs.getDouble("total")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}