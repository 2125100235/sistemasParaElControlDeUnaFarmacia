package org.example.sistemasparaelcontroldeunafarmacia.model;

public class ReporteVentaSemana {
    private String semana;
    private String fecha;
    private int piezas;
    private double total;

    public ReporteVentaSemana(String semana, String fecha, int piezas, double total) {
        this.semana = semana;
        this.fecha = fecha;
        this.piezas = piezas;
        this.total = total;
    }

    public String getSemana() { return semana; }
    public String getFecha() { return fecha; }
    public int getPiezas() { return piezas; }
    public double getTotal() { return total; }
}
