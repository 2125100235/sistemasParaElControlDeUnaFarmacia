package org.example.sistemasparaelcontroldeunafarmacia.model;

public class Producto {
    private int codigo;
    private String nombre;
    private int existencia;
    private float precioVenta;

    // Constructor completo
    public Producto(int codigo, String nombre, int existencia, float precioVenta) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.existencia = existencia;
        this.precioVenta = precioVenta;
    }

    // Constructor secundario
    public Producto(String nombre, int existencia, float precioVenta) {
        this.nombre = nombre;
        this.existencia = existencia;
        this.precioVenta = precioVenta;
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getExistencia() { return existencia; }
    public void setExistencia(int existencia) { this.existencia = existencia; }

    public float getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(float precioVenta) { this.precioVenta = precioVenta; }
}