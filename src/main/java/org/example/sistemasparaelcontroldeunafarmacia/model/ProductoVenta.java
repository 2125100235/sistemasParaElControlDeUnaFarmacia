package org.example.sistemasparaelcontroldeunafarmacia.model;

public class ProductoVenta {
    private String producto;
    private int cantidad;
    private double precio;
    private double subtotal;

    public ProductoVenta(String producto, int cantidad, double precio) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = precio * cantidad;
    }

    // Getters y Setters necesarios para JavaFX TableView
    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public double getSubtotal() { return subtotal; }
}
