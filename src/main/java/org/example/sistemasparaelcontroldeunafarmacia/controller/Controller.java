package org.example.sistemasparaelcontroldeunafarmacia.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.sistemasparaelcontroldeunafarmacia.dao.ProductoDAO;
import org.example.sistemasparaelcontroldeunafarmacia.model.Producto;

import java.sql.*;

import org.example.sistemasparaelcontroldeunafarmacia.db.ConexionBD;

import java.net.URL;

public class Controller {

    //Campos para el inicio de sesion nombre y Apellido
    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtClave;


    //Campos para el registro
    @FXML
    private TextField txtRegNombre;

    @FXML
    private TextField txtRegApellidoP;

    @FXML
    private TextField txtRegApellidoM;

    @FXML
    private TextField txtRegClave;

    @FXML
    private TextField txtRegCorreo;

    @FXML
    private TextField txtRegTelefono;


    //Campos para reestablecer contraseña
    //Autorizacion de admin
    @FXML
    private TextField txtAdminNombre;

    @FXML
    private TextField txtAdminCorreo;

    @FXML
    private TextField txtAdminClave;

    //reestablecer nueva contraseña
    @FXML
    private TextField txtCorreoCambio;

    @FXML
    private TextField txtNuevaClave;

    //Tabla productos
    @FXML private TextField txtCodigo;

    @FXML private TextField txtNombre;

    @FXML private TextField txtCantidad;

    @FXML private TextField txtPrecio;

    @FXML private TextField txtFecha;

    @FXML private Button btnNuevoProducto;

    @FXML private TableView<Producto> tablaAbastecimiento;

    @FXML private TableColumn<Producto, Integer> colCodigo;

    @FXML private TableColumn<Producto, String> colNombre;

    @FXML private TableColumn<Producto, Integer> colExistencia;

    // Esta lista especial es la que actualizará la tabla en tiempo real
    private ObservableList<Producto> listaProductos;



    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private Label lblTitulo;

    @FXML
    private Button btnRegresar;

    @FXML
    private Button btnRegistro;

    @FXML
    private Hyperlink linkIniciarSesion;

    @FXML
    private Button btnIngreso;

    @FXML
    private FontAwesomeIconView btnAyuda;

    @FXML
    private ImageView btnClientesMenu;

    @FXML
    private ImageView btnProductosMenu;

    @FXML
    private ImageView btnRegistroMenu;

    @FXML
    private ImageView btnVentasMenu;

    @FXML
    private FontAwesomeIconView btnusuario;

    //ProductosMenu
    @FXML
    private Button btnActualizarProducto;

    @FXML
    private Button btnEliminarProducto;

    @FXML
    private Button btnProductosRegresarPrincipal;

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<?, ?> colCaducidad;

    @FXML
    private TableColumn<?, ?> colPrecio;

    @FXML
    private Button btnNuevoProductoRegresarProductos;

    //ClientesMenu
    @FXML
    private Button btnNuevoCliente;
    @FXML
    private Button btnNuevoClienteRegresarClientes;
    //Acciones en BD
    private ProductoDAO productoDAO;
    private Producto productoSeleccionado;

 //Método principal para la navegación entre ventanas, se utiliza de forma universal para toda la navegación, por botón se le pasan los parámetros de la URL de la ventana hacia la que va y el evento desde el cuál fue accionado (el botón)
    @FXML
    private void navegacion(String ruta,MouseEvent event) {
        try{
            //Busca archivo FXML
            URL url = getClass().getResource(ruta);
            //Carga la URL
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            //Lee la URL y ve cuál es el contenedor de más alto nivel en la jerarquía de la escena (ventana), en este caso, la VBox, entonces nos devolverá este objeto, esto para saber cuál es el contenedor que tiene todos los elementos de la ventana dentro de el
            Parent root = (Parent) fxmlLoader.load();
            //Crea la escena que se mostrará en la ventana y muestra el elemento padre
            Scene scene = new Scene(root);
            //Detectamos la ventana actual desde la cuál se hizo clic al botón
            Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //Intercambio entre ventanas
            ventanaActual.setScene(scene);
            ventanaActual.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    //Navegacion entre ventanas, se le pasan los parámetros de URL y evento al método de navegación
    @FXML
    public void navPrincipal(MouseEvent event){
        if (txtCorreo != null && txtClave != null) {

            //Lee los datos ingresados y el correo lo hace todo minusculas para evitar errores
            String correoIngresado = txtCorreo.getText().trim().toLowerCase();
            String claveIngresada = txtClave.getText().trim();

            //Si alguna de las casillas quedo vacia lanza un mensaje
            if (correoIngresado.isEmpty() || claveIngresada.isEmpty()) {
                mostrarAlerta("Error de inicio de sesión", "Por favor ingresa tu correo y contraseña.");
                return;
            }

            // Buscamos correo y clave en MariaDB
            String sql = "select * from empleado where correo = ? and clave = ?";

            try {
                Connection cn = ConexionBD.getInstancia().getConexion();

                //PreparedStatement es la plantilla sql y a esa le asigna los datos reales
                //Despues remplaza los ? por el correo y la clave
                try (PreparedStatement ps = cn.prepareStatement(sql)) {
                    ps.setString(1, correoIngresado);
                    ps.setString(2, claveIngresada);

                    //ResultSet es la tabla temporal donde se guardan los resultados de MariaDB
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String nombre = rs.getString("nombre");
                            String puesto = rs.getString("puesto");
                            System.out.println("¡bienvenido " + nombre + "! puesto: " + puesto);

                            // Navegamos al menú principal
                            navegacion("/org/example/sistemasparaelcontroldeunafarmacia/principal.fxml", event);
                        } else {
                            mostrarAlerta("Acceso denegado", "Correo o contraseña incorrectos.");
                        }
                    }
                }
            } catch (Exception e) {

                //Si ocurre cualquier error, muestra este mensaje
                e.printStackTrace();
                mostrarAlerta("Error de conexión", "Ocurrió un error al consultar la base de datos.");
            }
        } else {
            navegacion("/org/example/sistemasparaelcontroldeunafarmacia/principal.fxml", event);
        }
    }

    @FXML
    public void navRegistro(MouseEvent event){
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/registro.fxml", event);
    }

    @FXML
    public void registrarEmpleado(MouseEvent event) {
        // 1. Obtener y limpiar los valores ingresados
        String nombre = txtRegNombre.getText().trim().toLowerCase();
        String apellidoP = txtRegApellidoP.getText().trim().toLowerCase();
        String apellidoM = txtRegApellidoM.getText().trim().toLowerCase();
        String clave = txtRegClave.getText().trim();
        String correo = txtRegCorreo.getText().trim().toLowerCase();
        String telefono = txtRegTelefono.getText().trim();

        // 2. Validar que los campos obligatorios no estén vacíos
        if (nombre.isEmpty() || apellidoP.isEmpty() || clave.isEmpty() || correo.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor completa Nombre, Apellido paterno, Contraseña y Correo.");
            return;
        }

        // 3. Consulta SQL para insertar el nuevo empleado (por defecto le asignamos puesto 'cajero')
        String sql = "insert into empleado (nombre, apellidopaterno, apellidomaterno, clave, correo, telefono, puesto) values (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection cn = ConexionBD.getInstancia().getConexion();

            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, nombre);
                ps.setString(2, apellidoP);
                ps.setString(3, apellidoM);
                ps.setString(4, clave);
                ps.setString(5, correo);
                ps.setString(6, telefono);
                ps.setString(7, "cajero"); // Puesto asignado por defecto al registrarse

                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("¡Empleado registrado con éxito en MariaDB!");

                    // Nos regresa a la pantalla de inicio de sesión para que pruebe entrar
                    navInicioSesion(event);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error al registrar", "No se pudo guardar el empleado en la base de datos.");
        }
    }

    @FXML
    public void autorizar(MouseEvent event) {
        String nombreAdmin = txtAdminNombre.getText().trim().toLowerCase();
        String correoAdmin = txtAdminCorreo.getText().trim().toLowerCase();
        String claveAdmin = txtAdminClave.getText().trim();

        if (nombreAdmin.isEmpty() || correoAdmin.isEmpty() || claveAdmin.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor completa todos los campos del administrador.");
            return;
        }

        // Consultamos si existe un empleado con ese correo, clave y puesto de gerente
        String sql = "select * from empleado where correo = ? and clave = ? and puesto = 'gerente'";

        try {
            Connection cn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, correoAdmin);
                ps.setString(2, claveAdmin);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("¡Autorización concedida por el gerente!");
                        // Redirigimos a la segunda pantalla para cambiar la contraseña
                        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/restablecerContraseñaDos.fxml", event);
                    } else {
                        mostrarAlerta("Acceso Denegado", "Datos de administrador incorrectos o no tienes permisos de gerente.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error de conexión", "Ocurrió un error al verificar los datos del administrador.");
        }
    }

    @FXML
    public void restablecer(MouseEvent event) {
        String correoUsuario = txtCorreoCambio.getText().trim().toLowerCase();
        String nuevaClave = txtNuevaClave.getText().trim();

        if (correoUsuario.isEmpty() || nuevaClave.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor ingresa el correo y la nueva contraseña.");
            return;
        }

        // Actualizamos la clave del empleado que tenga ese correo
        String sql = "update empleado set clave = ? where correo = ?";

        try {
            Connection cn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, nuevaClave);
                ps.setString(2, correoUsuario);

                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas > 0) {
                    mostrarAlerta("Éxito", "La contraseña ha sido actualizada correctamente.");
                    System.out.println("Contraseña actualizada para: " + correoUsuario);

                    // Regresamos a la pantalla de Inicio de Sesión
                    navInicioSesion(event);
                } else {
                    mostrarAlerta("Usuario no encontrado", "No existe ningún empleado registrado con ese correo.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error al actualizar", "No se pudo cambiar la contraseña en la base de datos.");
        }
    }

    @FXML
    public void navRestablecer(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/restablecerContraseña.fxml", event);
    }

    public void cargarProductosBD() {
        if (listaProductos == null) return;
        listaProductos.clear(); // Limpia la lista visual para no duplicar datos

        String sql = "SELECT * FROM producto";

        try {
            Connection cn = ConexionBD.getInstancia().getConexion();
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Obtenemos los datos de cada columna de MySQL
                int codigo = rs.getInt("codigo"); // Si en MySQL se llama "id" o "idProducto", cámbialo aquí
                String nombre = rs.getString("nombre");
                int existencia = rs.getInt("existencia");

                // Creamos el objeto Producto y lo metemos a la lista que ve la tabla
                listaProductos.add(new Producto(codigo, nombre, existencia));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar los productos desde la base de datos.");
        }
    }

    @FXML
    public void initialize() {
        listaProductos = FXCollections.observableArrayList();

        // Vinculamos las columnas
        if (colCodigo != null) {
            colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colExistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        }

        // Vinculamos la tabla (sea cual sea el id que tenga la pantalla activa)
        if (tablaAbastecimiento != null) {
            tablaAbastecimiento.setItems(listaProductos);
        } else if (tablaProductos != null) {
            tablaProductos.setItems(listaProductos);
        }

        // Carga los datos de MySQL en cuanto abre la ventana
        cargarProductosBD();
    }

    @FXML
    public void agregarProducto() {
        // 1. Validar que las cajas no estén vacías para evitar la pantalla negra / error
        if (txtNombre.getText().trim().isEmpty() ||
                txtCantidad.getText().trim().isEmpty() ||
                txtPrecio.getText().trim().isEmpty()) {
            System.out.println("Por favor llena Nombre, Cantidad y Precio.");
            return;
        }

        try {
            // 2. Leemos los datos DENTRO del try (así si ponen letras en vez de números, salta al catch)
            String nombre = txtNombre.getText().trim();
            int existencia = Integer.parseInt(txtCantidad.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            String fecha = txtFecha.getText().trim(); // Formato: YYYY-MM-DD

            String sql = "INSERT INTO producto (nombre, existencia, precioVenta, fechaCaducidad) VALUES (?, ?, ?, ?)";

            // 3. Conexión a la BD
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmacia", "root", "");
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, nombre);
                pstmt.setInt(2, existencia);
                pstmt.setDouble(3, precio);
                pstmt.setString(4, fecha);

                pstmt.executeUpdate();

                System.out.println("¡Producto guardado exitosamente!");

                // Limpiamos las cajitas
                txtNombre.clear();
                txtCantidad.clear();
                txtPrecio.clear();
                txtFecha.clear();

                // Recargamos la tabla automáticamente desde la BD
                cargarProductosBD();
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar en la base de datos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingresa números válidos en cantidad y precio.");
        }
    }

    @FXML
    public void navRegresarPrincipal(MouseEvent event){
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/principal.fxml", event);
    }

    @FXML
    public void navInicioSesion(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/inicioSesion.fxml", event);
    }

    @FXML
    public void navClientesMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/clientesMenu.fxml", event);
    }

    @FXML
    public void navDatosCuenta(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/datosCuenta.fxml", event);
    }

    @FXML
    public void navProductosMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/productosMenu.fxml", event);
    }

    @FXML
    public void navRegistroMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/registroMenu.fxml", event);
    }

    @FXML
    public void navVentasMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/ventasDiaMenu.fxml", event);
    }

    @FXML
    public void navVentasMesMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/ventasMesMenu.fxml", event);
    }

    @FXML
    public void navVentasSemanaMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/ventasSemanaMenu.fxml", event);
    }

    @FXML
    public void navAyuda(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/ayuda.fxml", event);
    }
    @FXML
    public void navNuevoProducto(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/nuevoProducto.fxml", event);
    }
    @FXML
    public void navRegresarProductosMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/productosMenu.fxml", event);
    }

    @FXML
    public void navNuevoCliente(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/nuevoCliente.fxml", event);
    }
    @FXML
    public void navRegresarClientesMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/clientesMenu.fxml", event);
    }


    //Este metodo se estará reutilizando al momento de que queramos mostrar un error.
    // Al parecer, siempre tiene que quedar hasta ABAJO del codigo
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
