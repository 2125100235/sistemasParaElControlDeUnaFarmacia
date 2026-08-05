package org.example.sistemasparaelcontroldeunafarmacia.model;

public class Cliente {
    private int codigo;
    private String nombre;
    private String direccion;
    private String rfc;
    private String telefono;

    public Cliente() {}

    public Cliente(int codigo, String nombre, String direccion, String rfc, String telefono) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.rfc = rfc;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
