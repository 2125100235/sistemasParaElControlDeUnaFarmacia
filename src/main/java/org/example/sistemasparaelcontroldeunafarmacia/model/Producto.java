package org.example.sistemasparaelcontroldeunafarmacia.model;

import java.util.Date;

public class Producto {
    private int codigo;
    private String nombre;
    private int cantidad;
    private int existencia;
    private float precioVenta;
    private Date fechaCaducidad;

    // Constructor vacío
    public Producto() {}

    // Constructor reducido (3 parámetros)
    public Producto(int codigo, String nombre, int existencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.existencia = existencia;
    }

    // Constructor completo (6 parámetros)
    public Producto(int codigo, String nombre, int cantidad, int existencia, float precioVenta, Date fechaCaducidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.existencia = existencia;
        this.precioVenta = precioVenta;
        this.fechaCaducidad = fechaCaducidad;
    }

    // --- GETTERS Y SETTERS ---
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }
}
