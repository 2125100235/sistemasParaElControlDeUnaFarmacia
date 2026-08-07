package org.example.sistemasparaelcontroldeunafarmacia.model;

public class SesionUsuario {
    private static SesionUsuario instancia;
    private Empleado empleadoActual;

    private SesionUsuario() {}

    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    public Empleado getEmpleadoActual() { return empleadoActual; }
    public void setEmpleadoActual(Empleado empleado) { this.empleadoActual = empleado; }

    public void cerrarSesion() {
        this.empleadoActual = null;
    }
}
