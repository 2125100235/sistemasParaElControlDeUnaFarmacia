package org.example.sistemasparaelcontroldeunafarmacia.model;

public class ReporteVentaMes {
    private String mes;
    private int piezas;
    private double total;

    public ReporteVentaMes(String mes, int piezas, double total) {
        this.mes = mes;
        this.piezas = piezas;
        this.total = total;
    }

    public String getMes() { return mes; }
    public int getPiezas() { return piezas; }
    public double getTotal() { return total; }
}
