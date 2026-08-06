package org.example.sistemasparaelcontroldeunafarmacia.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.sistemasparaelcontroldeunafarmacia.dao.ClienteDAO;
import org.example.sistemasparaelcontroldeunafarmacia.dao.ProductoDAO;
import org.example.sistemasparaelcontroldeunafarmacia.model.Cliente;
import org.example.sistemasparaelcontroldeunafarmacia.model.Producto;
import org.example.sistemasparaelcontroldeunafarmacia.dao.ProductoDAO;
import org.example.sistemasparaelcontroldeunafarmacia.model.Producto;
import javafx.scene.control.Alert;

import java.sql.*;

import org.example.sistemasparaelcontroldeunafarmacia.db.ConexionBD;
import org.example.sistemasparaelcontroldeunafarmacia.model.ProductoVenta;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Controller {

    private final ProductoDAO productoDAO = new ProductoDAO();
    private Producto productoSeleccionado = null;

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

    //Búsqueda de elementos
    @FXML
    private TextField barraBusqueda;

    @FXML
    private FontAwesomeIconView btnBuscar;

    @FXML
    private FilteredList<Producto> listaFiltrada;

    @FXML
    private TextField barraBusquedaClientes;

    @FXML
    private FontAwesomeIconView btnBuscarClientes;

    @FXML
    private FilteredList<Cliente> listaFiltradaClientes;
    //Tabla productos
    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtFecha;

    @FXML
    private Button btnNuevoProducto;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<Producto> tablaAbastecimiento;

    @FXML
    private TableColumn<Producto, Integer> colCodigo;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Integer> colExistencia;

    //Tabla Clientes
    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, Integer> colClienteCodigo;

    @FXML
    private TableColumn<Cliente, String> colClienteNombre;

    @FXML
    private TableColumn<Cliente, String> colClienteDireccion;

    @FXML
    private TableColumn<Cliente, String> colClienteRFC;

    @FXML
    private TableColumn<Cliente, String> colClienteTelefono;

    @FXML
    private TextField txtCliNombre;

    @FXML
    private TextField txtCliDireccion;

    @FXML
    private TextField txtCliRFC;

    @FXML
    private TextField txtCliTelefono;

    private ObservableList<Cliente> listaClientes;

    private ClienteDAO clienteDAO;

    private Cliente clienteSeleccionado;


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
    private TableColumn<Producto, String> colCaducidad;

    @FXML
    private TableColumn<Producto, Float> colPrecio;

    @FXML
    private Button btnNuevoProductoRegresarProductos;

    private final ObservableList<ProductoVenta> listaVentas = FXCollections.observableArrayList();

    //CONTROL DE VENTAS
    @FXML private TextField barraBusquedaVentas;

    @FXML private TextField txtPrecioVentas;

    @FXML private TextField txtCantidadVentas;

    @FXML private Label lblTotalVentas;

    @FXML private Button btnRegistrarVentas;

    @FXML private Button btnMenuRegistroVentas;

    @FXML private Button btnMenuPrincipalVentas;

    @FXML private FontAwesomeIconView btnAyudaVentas;

    @FXML private FontAwesomeIconView btnBuscarVentas;

    @FXML private FontAwesomeIconView btnUsuarioVentas;

    @FXML private TableView<ProductoVenta> tablaVentas;

    @FXML private TableColumn<ProductoVenta, String> colProductoVentas;

    @FXML private TableColumn<ProductoVenta, Integer> colCantidadVentas;

    @FXML private TableColumn<ProductoVenta, Double> colPrecioVentas;

    //ClientesMenu
    @FXML
    private Button btnNuevoCliente;
    @FXML
    private Button btnNuevoClienteRegresarClientes;

    //Método principal para la navegación entre ventanas, se utiliza de forma universal para toda la navegación, por botón se le pasan los parámetros de la URL de la ventana hacia la que va y el evento desde el cuál fue accionado (el botón)
    @FXML
    private void navegacion(String ruta, Event event) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Navegacion entre ventanas, se le pasan los parámetros de URL y evento al método de navegación
    @FXML
    public void navPrincipal(MouseEvent event) {
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
    public void navRegistro(MouseEvent event) {
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
        // Como parte de lo anterior, validamos que el correo tenga el uso de arroba
        if (!correo.contains("@")) {
            mostrarAlerta("Correo invalido", "El correo tiene que contener un arroba.");
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

        listaProductos.clear(); // Limpia la lista actual

        // El DAO hace toda la consulta SQL por nosotros y nos regresa la lista llena
        List<Producto> productosBD = productoDAO.listar();
        listaProductos.addAll(productosBD);
    }

    @FXML
    public void initialize() {
        listaProductos = FXCollections.observableArrayList();
        // Envolvemos la lista original dentro de un FilteredList (Lista filtrada)
        listaFiltrada = new FilteredList<>(listaProductos, p -> true);

        // Vinculamos cada columna de forma independiente
        if (colCodigo != null) {
            colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        }
        if (colNombre != null) {
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        }
        if (colExistencia != null) {
            colExistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        }
        if (colPrecio != null) {
            colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        }
        if (colCaducidad != null) {
            colCaducidad.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        }

        //Vinculamos la lista filtrada a las tablas
        if (tablaAbastecimiento != null) {
            tablaAbastecimiento.setItems(listaFiltrada);
        } else if (tablaProductos != null) {
            tablaProductos.setItems(listaFiltrada);
        }
        //Escucha lo que el usuario ingresa en la barra de búsqueda
        if (barraBusqueda != null) {
            barraBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
                filtrarProductos(newValue);
            });
        }

        //Escucha cuándo el usuario selecciona una fila de la tabla
        if (tablaProductos != null) {
            tablaProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    productoSeleccionado = newSelection;
                }
            });
        }
        // Carga los datos de MySQL en cuanto abre la ventana
        cargarProductosBD();


        //FALTA REVISAR
        clienteDAO = new ClienteDAO();
        listaClientes = FXCollections.observableArrayList();
        // Envolvemos la lista original dentro de un FilteredList (Lista filtrada)
        listaFiltradaClientes = new FilteredList<>(listaClientes, p -> true);

        if (colClienteCodigo != null) colClienteCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        if (colClienteNombre != null) colClienteNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colClienteDireccion != null)
            colClienteDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        if (colClienteRFC != null) colClienteRFC.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        if (colClienteTelefono != null) colClienteTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        if (tablaClientes != null) {
            tablaClientes.setItems(listaFiltradaClientes);
            tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                if (newSel != null) clienteSeleccionado = newSel;
            });
        }

        //Escucha lo que el usuario ingresa en la barra de búsqueda de los clientes
        if (barraBusquedaClientes != null) {
            barraBusquedaClientes.textProperty().addListener((observable, oldValue, newValue) -> {
                filtrarClientes(newValue);
            });
        }
        cargarClientesBD();

        if (tablaVentas != null) {
            colProductoVentas.setCellValueFactory(new PropertyValueFactory<>("producto"));
            colCantidadVentas.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            colPrecioVentas.setCellValueFactory(new PropertyValueFactory<>("precio"));

            tablaVentas.setItems(listaVentas);
        }
    }

    // Método que realiza el filtro en tiempo real por Nombre o Código de los clientes
    private void filtrarClientes(String texto) {
        if (listaFiltradaClientes == null) return;

        listaFiltradaClientes.setPredicate(cliente -> {
            // Si la barra está vacía, mostramos todos los productos
            if (texto == null || texto.trim().isEmpty()) {
                return true;
            }

            String filtro = texto.toLowerCase().trim();

            // Coincidencia por NOMBRE del producto
            if (cliente.getNombre() != null && cliente.getNombre().toLowerCase().contains(filtro)) {
                return true;
            }

            // Coincidencia por CÓDIGO del cliente
            if (String.valueOf(cliente.getCodigo()).contains(filtro)) {
                return true;
            }

            return false; // Si no coincide con nada, se oculta de la tabla
        });
    }

    @FXML
    public void buscarClientes(MouseEvent event) {
        if (barraBusquedaClientes != null) {
            String texto = barraBusquedaClientes.getText();
            filtrarClientes(texto);
        }
    }


    // Método que realiza el filtro en tiempo real por Nombre o Código
    private void filtrarProductos(String texto) {
        if (listaFiltrada == null) return;

        listaFiltrada.setPredicate(producto -> {
            // Si la barra está vacía, mostramos todos los productos
            if (texto == null || texto.trim().isEmpty()) {
                return true;
            }

            String filtro = texto.toLowerCase().trim();

            // Coincidencia por NOMBRE del producto
            if (producto.getNombre() != null && producto.getNombre().toLowerCase().contains(filtro)) {
                return true;
            }

            // Coincidencia por CÓDIGO del producto
            if (String.valueOf(producto.getCodigo()).contains(filtro)) {
                return true;
            }

            return false; // Si no coincide con nada, se oculta de la tabla
        });
    }

    @FXML
    public void buscarProducto(MouseEvent event) {
        if (barraBusqueda != null) {
            String texto = barraBusqueda.getText();
            filtrarProductos(texto);
        }
    }

    @FXML
    public void actualizarProducto() {
        if (productoSeleccionado == null) {
            mostrarAlerta("Fallo al actualizar", "No ha seleccionado ningún producto de la tabla.");
            return;
        }
        // Todo se muestra dentro de un dialog (que es una ventana emergente que nos permite ingresar campos, esto no se puede editar en SceneBuilder, es únicamente código.
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Actualizar Producto");
        dialog.setHeaderText("Editar información para: " + productoSeleccionado.getNombre());

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

        // Campos de texto precargados
        TextField txtEditNombre = new TextField(productoSeleccionado.getNombre());
        TextField txtEditCantidad = new TextField(String.valueOf(productoSeleccionado.getExistencia()));
        TextField txtEditPrecio = new TextField(String.valueOf(productoSeleccionado.getPrecioVenta()));

        // Selector de Fecha (DatePicker) precargado con la fecha actual del producto
        DatePicker dpEditFecha = new DatePicker();
        if (productoSeleccionado.getFechaCaducidad() != null && !productoSeleccionado.getFechaCaducidad().isEmpty()) {
            try {
                dpEditFecha.setValue(LocalDate.parse(productoSeleccionado.getFechaCaducidad()));
            } catch (Exception e) {
                // Si la fecha en BD tuviera un formato raro, la ignoramos para evitar que falle
            }
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtEditNombre, 1, 0);
        grid.add(new Label("Existencia:"), 0, 1);
        grid.add(txtEditCantidad, 1, 1);
        grid.add(new Label("Precio de Venta:"), 0, 2);
        grid.add(txtEditPrecio, 1, 2);
        grid.add(new Label("Fecha Caducidad:"), 0, 3);
        grid.add(dpEditFecha, 1, 3); // Integramos el selector de fecha

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == btnGuardar) {
            String nombre = txtEditNombre.getText().trim();
            String cantidadStr = txtEditCantidad.getText().trim();
            String precioStr = txtEditPrecio.getText().trim();
            LocalDate fechaSeleccionada = dpEditFecha.getValue();

            // Validar que no dejen campos vacíos
            if (nombre.isEmpty() || cantidadStr.isEmpty() || precioStr.isEmpty() || fechaSeleccionada == null) {
                mostrarAlerta("Campos incompletos", "Por favor completa Nombre, Cantidad, Precio y Fecha de Caducidad.");
                return;
            }

            try {
                int existencia = Integer.parseInt(cantidadStr);
                float precio = Float.parseFloat(precioStr);
                String fechaFormatted = fechaSeleccionada.toString(); // Convierte automáticamente a "YYYY-MM-DD"

                // Creamos el objeto Producto actualizado
                Producto prodActualizado = new Producto(productoSeleccionado.getCodigo(), nombre, existencia, precio, fechaFormatted);

                // Guardamos mediante el DAO
                if (productoDAO.actualizar(prodActualizado)) {
                    mostrarAlertaInfo("Éxito", "Producto actualizado correctamente.");
                    cargarProductosBD(); // Recarga la tabla de JavaFX
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar el producto en la base de datos.");
                }

            } catch (NumberFormatException e) {
                mostrarAlerta("Formato incorrecto", "La cantidad debe ser entero y el precio decimal.");
            }
        }
    }

    @FXML
    public void eliminarProducto() {
        // 1. Validar que exista un producto seleccionado en la tabla
        if (productoSeleccionado == null) {
            mostrarAlerta("Sin selección", "Por favor, selecciona un producto de la tabla para eliminar.");
            return;
        }

        // 2. Ventana emergente de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar el producto '" + productoSeleccionado.getNombre() + "'?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // 3. Delegamos la eliminación a la capa DAO
            if (productoDAO.eliminar(productoSeleccionado.getCodigo())) {
                mostrarAlertaInfo("Éxito", "Producto eliminado correctamente.");
                limpiarCampos();
                cargarProductosBD(); // Recarga la lista y la tabla
            } else {
                mostrarAlerta("Error de eliminación", "No se pudo eliminar el producto. Verifica que no tenga registros o ventas vinculadas.");
            }
        }
    }

    @FXML
    public void agregarProducto() {
        if (txtNombre.getText().trim().isEmpty() ||
                txtCantidad.getText().trim().isEmpty() ||
                txtPrecio.getText().trim().isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor llena Nombre, Cantidad y Precio.");
            return;
        }

        try {
            String nombre = txtNombre.getText().trim();
            int existencia = Integer.parseInt(txtCantidad.getText().trim());
            float precio = Float.parseFloat(txtPrecio.getText().trim());
            String fecha = (txtFecha != null) ? txtFecha.getText().trim() : "";

            // Creamos el producto con 5 datos
            Producto nuevoProducto = new Producto(0, nombre, existencia, precio, fecha);

            // Guardamos mediante el DAO
            if (productoDAO.insertar(nuevoProducto)) {
                mostrarAlertaInfo("Éxito", "¡Producto guardado exitosamente!");
                limpiarCampos();
                cargarProductosBD(); // Recarga la tabla en la interfaz
            } else {
                mostrarAlerta("Error", "No se pudo guardar el producto en la base de datos.");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Formato incorrecto", "Por favor, ingresa números válidos en cantidad y precio.");
        }
    }

    // Método auxiliar para pasar los datos del producto seleccionado a los campos de texto
    private void cargarDatosEnCampos(Producto producto) {
        if (txtCodigo != null) txtCodigo.setText(String.valueOf(producto.getCodigo()));
        if (txtNombre != null) txtNombre.setText(producto.getNombre());
        if (txtCantidad != null) txtCantidad.setText(String.valueOf(producto.getExistencia()));
        if (txtPrecio != null) txtPrecio.setText(String.valueOf(producto.getPrecioVenta()));
    }


    @FXML
    public void navRegresarPrincipal(MouseEvent event) {
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

    @FXML
    public void navControlVentas(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/controlVentas.fxml", event);
    }

    @FXML
    public void navMenuRegistro(ActionEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/registroMenu.fxml", event);
    }

    @FXML
    public void navMenuPrincipal(ActionEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/principal.fxml", event);
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

    // Limpia los campos de texto tras guardar, actualizar o eliminar
    private void limpiarCampos() {
        if (txtCodigo != null) txtCodigo.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtCantidad != null) txtCantidad.clear();
        if (txtPrecio != null) txtPrecio.clear();
        if (txtFecha != null) txtFecha.clear();
        productoSeleccionado = null; // Reiniciamos la selección
    }

    // Muestra mensajes de éxito / información al usuario
    private void mostrarAlertaInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cargarClientesBD() {
        if (listaClientes == null) return;
        listaClientes.clear();
        List<Cliente> clientesBD = clienteDAO.listar();
        listaClientes.addAll(clientesBD);
    }

    @FXML
    private void buscarProductoVentas(MouseEvent event) {
        String busqueda = barraBusquedaVentas.getText().trim();

        if (busqueda.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor ingresa un nombre o código de producto.");
            return;
        }

        // Búsqueda real en la base de datos
        productoSeleccionado = productoDAO.buscarPorNombreOCodigo(busqueda);

        if (productoSeleccionado != null) {
            txtPrecioVentas.setText(String.format("$%.2f", productoSeleccionado.getPrecioVenta()));
            txtCantidadVentas.setText("1");
        } else {
            txtPrecioVentas.clear();
            mostrarAlerta("No encontrado", "El producto '" + busqueda + "' no existe en el inventario.");
        }
    }

    @FXML
    private void registrarProductoVentas(ActionEvent event) {
        if (productoSeleccionado == null) {
            mostrarAlerta("Atención", "Primero debes buscar y seleccionar un producto válido.");
            return;
        }

        try {
            int cantidadSolicitada = Integer.parseInt(txtCantidadVentas.getText().trim());

            if (cantidadSolicitada <= 0) {
                mostrarAlerta("Cantidad inválida", "La cantidad debe ser mayor a 0.");
                return;
            }

            // Validación de Stock
            if (cantidadSolicitada > productoSeleccionado.getExistencia()) {
                mostrarAlerta("Stock insuficiente", "Solo quedan " + productoSeleccionado.getExistencia() + " unidades de " + productoSeleccionado.getNombre());
                return;
            }

            // Agrega el producto a la tabla
            listaVentas.add(new ProductoVenta(
                    productoSeleccionado.getNombre(),
                    cantidadSolicitada,
                    productoSeleccionado.getPrecioVenta()
            ));

            // ACTUALIZA EL TOTAL EN PANTALLA (AQUÍ)
            actualizarTotal();

            // Limpiar para la siguiente búsqueda
            barraBusquedaVentas.clear();
            txtPrecioVentas.clear();
            txtCantidadVentas.clear();
            productoSeleccionado = null;

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingresa una cantidad numérica válida.");
        }
    }


    private void actualizarTotalVentas() {
        double sumaTotal = 0.0;
        for (ProductoVenta item : listaVentas) {
            sumaTotal += item.getSubtotal();
        }
        lblTotalVentas.setText(String.format("$%.2f", sumaTotal));
    }

    private void actualizarTotal() {
        double subtotal = 0.0;

        // Sumamos (precio * cantidad) de cada producto en la tabla
        for (ProductoVenta p : listaVentas) {
            subtotal += p.getPrecio() * p.getCantidad();
        }

        // Calculamos el total con el 16% de IVA
        double totalConIVA = subtotal * 1.16;

        // Formateamos a dos decimales y lo mostramos en la pantalla
        lblTotal.setText(String.format("$%.2f", totalConIVA));
    }

    @FXML
    public void agregarCliente() {
        if (txtCliNombre.getText().trim().isEmpty() ||
                txtCliDireccion.getText().trim().isEmpty() ||
                txtCliTelefono.getText().trim().isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor llena Nombre, Dirección y Teléfono.");
            return;
        }

        String nombre = txtCliNombre.getText().trim();
        String direccion = txtCliDireccion.getText().trim();
        String rfc = (txtCliRFC != null) ? txtCliRFC.getText().trim() : "";
        String telefono = txtCliTelefono.getText().trim();

        Cliente nuevoCliente = new Cliente(0, nombre, direccion, rfc, telefono);

        if (clienteDAO.insertar(nuevoCliente)) {
            mostrarAlertaInfo("Éxito", "¡Cliente guardado exitosamente!");
            if (txtCliNombre != null) txtCliNombre.clear();
            if (txtCliDireccion != null) txtCliDireccion.clear();
            if (txtCliRFC != null) txtCliRFC.clear();
            if (txtCliTelefono != null) txtCliTelefono.clear();
            cargarClientesBD();
        } else {
            mostrarAlerta("Error", "No se pudo guardar el cliente en la base de datos.");
        }
    }
    @FXML
    public void eliminarCliente() {
        // 1. Validar que exista un producto seleccionado en la tabla
        if (clienteSeleccionado == null) {
            mostrarAlerta("Sin selección", "Por favor, selecciona un cliente de la tabla para eliminar.");
            return;
        }

        // 2. Ventana emergente de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar a '" + clienteSeleccionado.getNombre() + "'?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // 3. Delegamos la eliminación a la capa DAO
            if (clienteDAO.eliminar(clienteSeleccionado.getCodigo())) {
                mostrarAlertaInfo("Éxito", "Cliente eliminado correctamente.");
                limpiarCampos();
                cargarClientesBD(); // Recarga la lista y la tabla
            } else {
                mostrarAlerta("Error de eliminación", "No se pudo eliminar el cliente. Verifica que este relacionado a alguna venta.");
            }
        }
    }

    @FXML
    public void actualizarCliente() {
        if (clienteSeleccionado == null) {
            mostrarAlerta("Fallo al actualizar", "No ha seleccionado ningún cliente de la tabla.");
            return;
        }
        // Todo se muestra dentro de un dialog (que es una ventana emergente que nos permite ingresar campos, esto no se puede editar en SceneBuilder, es únicamente código.
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Actualizar Cliente");
        dialog.setHeaderText("Editar información para: " + clienteSeleccionado.getNombre());

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, btnCancelar);

        // Campos de texto precargados
        TextField txtEditNombre = new TextField(clienteSeleccionado.getNombre());
        TextField txtEditDireccion = new TextField(clienteSeleccionado.getDireccion());
        TextField txtEditRfc = new TextField(clienteSeleccionado.getRfc());
        TextField txtEditTelefono = new TextField(clienteSeleccionado.getTelefono());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtEditNombre, 1, 0);
        grid.add(new Label("Direccion:"), 0, 1);
        grid.add(txtEditDireccion, 1, 1);
        grid.add(new Label("RFC:"), 0, 2);
        grid.add(txtEditRfc, 1, 2);
        grid.add(new Label("Telefono:"), 0, 3);
        grid.add(txtEditTelefono, 1, 3);
        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == btnGuardar) {
            String nombre = txtEditNombre.getText().trim();
            String direccion = txtEditDireccion.getText().trim();
            String rfc = txtEditRfc.getText().trim();
            String telefono = txtEditTelefono.getText().trim();

            // Validar que no dejen campos vacíos
            if (nombre.isEmpty() || direccion.isEmpty() || rfc.isEmpty() || telefono == null) {
                mostrarAlerta("Campos incompletos", "Por favor completa todos los campos.");
                return;
            }

            try {
                // Creamos el objeto del cliente actualizado
                Cliente clienteActualizado = new Cliente(clienteSeleccionado.getCodigo(), nombre, direccion, rfc, telefono);

                // Guardamos mediante el DAO
                if (clienteDAO.actualizar(clienteActualizado)) {
                    mostrarAlertaInfo("Éxito", "Cliente actualizado correctamente.");
                    cargarClientesBD(); // Recarga la tabla de JavaFX
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar el cliente en la base de datos.");
                }

            } catch (Exception e) {
                mostrarAlerta("Error", "Hubo un error al actualizar el cliente, revise bien los campos y vuelva a intentarlo.");
            }
        }
    }
}
