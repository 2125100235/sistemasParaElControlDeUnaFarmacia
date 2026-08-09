package org.example.sistemasparaelcontroldeunafarmacia.model;

public class ReporteVentaDia {
    private int nota;
    private String cliente;
    private String empleado;
    private String nombre;
    private int piezas;
    private double precio;
    private double total;

    public ReporteVentaDia() {
        this.nota = nota;
        this.nombre = nombre;
        this.piezas = piezas;
        this.precio = precio;
        this.total = total;
    }

    public int getNota() { return nota; }
    public String getNombre() { return nombre; }
    public int getPiezas() { return piezas; }
    public double getPrecio() { return precio; }
    public double getTotal() { return total; }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPiezas(int piezas) {
        this.piezas = piezas;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCliente() { return cliente; }

    public String getEmpleado() { return empleado; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setEmpleado(String empleado) { this.empleado = empleado; }
}
