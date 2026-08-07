package org.example.sistemasparaelcontroldeunafarmacia.model;

public class ReporteVentaDia {
    private int nota;
    private String nombre;
    private int piezas;
    private double precio;
    private double total;

    public ReporteVentaDia(int nota, String nombre, int piezas, double precio, double total) {
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
}
