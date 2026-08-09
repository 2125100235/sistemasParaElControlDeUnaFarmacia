package org.example.sistemasparaelcontroldeunafarmacia.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.event.ActionEvent;
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
import org.example.sistemasparaelcontroldeunafarmacia.dao.*;
import org.example.sistemasparaelcontroldeunafarmacia.model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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

    private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();

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

    @FXML private Button btnFinalizarVenta;

    @FXML private FontAwesomeIconView btnAyudaVentas;

    @FXML private FontAwesomeIconView btnBuscarVentas;

    @FXML private FontAwesomeIconView btnUsuarioVentas;

    @FXML private Button btnEliminarControlVenta;

    @FXML private TableView<ProductoVenta> tablaVentas;

    @FXML private TableColumn<ProductoVenta, String> colProductoVentas;

    @FXML private TableColumn<ProductoVenta, Integer> colCantidadVentas;

    @FXML private TableColumn<ProductoVenta, Double> colPrecioVentas;

    @FXML private ComboBox<Cliente> cbClienteVentas;

    private final VentaDAO ventaDAO = new VentaDAO();

    private final ContextMenu menuSugerenciasVentas = new ContextMenu();

    //Ventas al dia, semanales y mensuales

    @FXML private DatePicker dpFechaVentas;

    // Vista: Ventas por Día
    @FXML private TableView tablaVentasDia;
    @FXML private TableColumn<?, ?> colNotaVentaDia;
    @FXML private TableColumn<?, ?> colNombreVentaDia;
    @FXML private TableColumn<?, ?> colPiezasVentaDia;
    @FXML private TableColumn<?, ?> colPrecioVentaDia;
    @FXML private TableColumn<?, ?> colTotalVentaDia;
    @FXML private TableColumn<?, ?> colClienteVentaDia;
    @FXML private TableColumn<?, ?> colEmpleadoVentaDia;

    // Vista: Ventas por Semana
    @FXML private TableView tablaVentasSemana;
    @FXML private TableColumn<?, ?> colNombreVentaSemana;
    @FXML private TableColumn<?, ?> colFechaVentaSemana;
    @FXML private TableColumn<?, ?> colPiezasVentaSemana;
    @FXML private TableColumn<?, ?> colTotalVentaSemana;

    // Vista: Ventas por Mes
    @FXML private TableView tablaVentasMes;
    @FXML private TableColumn<?, ?> colNombreVentaMes;
    @FXML private TableColumn<?, ?> colPiezasVentaMes;
    @FXML private TableColumn<?, ?> colTotalVentaMes;

    //ClientesMenu
    @FXML
    private Button btnNuevoCliente;
    @FXML
    private Button btnNuevoClienteRegresarClientes;

    //Datos de la cuenta
    @FXML
    private Button btnAplicarCuenta;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnEditarCuenta;

    @FXML
    private Button btnEliminarCuenta;

    @FXML
    private Button btnMenuPrincipalDatosCuenta;


    @FXML
    private TextField txtCuentaApellidoM;

    @FXML
    private TextField txtCuentaApellidoP;

    @FXML
    private TextField txtCuentaClave;

    @FXML
    private TextField txtCuentaCorreo;

    @FXML
    private TextField txtCuentaNombre;

    @FXML
    private TextField txtCuentaTelefono;

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
            String correoIngresado = txtCorreo.getText().trim().toLowerCase();
            String claveIngresada = txtClave.getText().trim();

            if (correoIngresado.isEmpty() || claveIngresada.isEmpty()) {
                mostrarAlerta("Error de inicio de sesión", "Por favor ingresa tu correo y contraseña.");
                return;
            }

            if (!correoIngresado.contains("@")) {
                mostrarAlerta("Correo invalido", "El correo tiene que contener un arroba.");
                return;
            }

            // Consulta desacoplada a través de EmpleadoDAO
            Empleado empleadoLogueado = empleadoDAO.autenticar(correoIngresado, claveIngresada);

            if (empleadoLogueado != null) {
                // Guardamos la sesión activa globalmente
                SesionUsuario.getInstancia().setEmpleadoActual(empleadoLogueado);
                mostrarAlertaInfo("Bienvenido","¡Bienvenido " + empleadoLogueado.getNombre() + "! Puesto: " + empleadoLogueado.getPuesto());
                System.out.println("¡Bienvenido " + empleadoLogueado.getNombre() + "! Puesto: " + empleadoLogueado.getPuesto());

                navegacion("/org/example/sistemasparaelcontroldeunafarmacia/principal.fxml", event);
            } else {
                mostrarAlerta("Acceso denegado", "Correo o contraseña incorrectos.");
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
        String nombre = txtRegNombre.getText().trim().toLowerCase();
        String apellidoP = txtRegApellidoP.getText().trim().toLowerCase();
        String apellidoM = txtRegApellidoM.getText().trim().toLowerCase();
        String clave = txtRegClave.getText().trim();
        String correo = txtRegCorreo.getText().trim().toLowerCase();
        String telefono = txtRegTelefono.getText().trim();

        if (nombre.isEmpty() || apellidoP.isEmpty() || clave.isEmpty() || correo.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor completa Nombre, Apellido paterno, Contraseña y Correo.");
            return;
        }

        if (!correo.contains("@")) {
            mostrarAlerta("Correo invalido", "El correo tiene que contener un arroba.");
            return;
        }

        Empleado nuevoEmp = new Empleado(0, nombre, apellidoP, apellidoM, clave, correo, telefono, "cajero");

        if (empleadoDAO.insertar(nuevoEmp)) {
            mostrarAlertaInfo("Éxito", "Empleado registrado correctamente.");
            navInicioSesion(event);
        } else {
            mostrarAlerta("Error al registrar", "No se pudo guardar el empleado en la base de datos.");
        }
    }

    @FXML
    public void autorizar(MouseEvent event) {
        String correoAdmin = txtAdminCorreo.getText().trim().toLowerCase();
        String claveAdmin = txtAdminClave.getText().trim();

        if (correoAdmin.isEmpty() || claveAdmin.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor completa los campos del administrador.");
            return;
        }

        if (empleadoDAO.verificarGerente(correoAdmin, claveAdmin)) {
            navegacion("/org/example/sistemasparaelcontroldeunafarmacia/restablecerContraseñaDos.fxml", event);
        } else {
            mostrarAlerta("Acceso Denegado", "Datos de administrador incorrectos o no tienes permisos de gerente.");
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

        if (empleadoDAO.actualizarClave(correoUsuario, nuevaClave)) {
            mostrarAlertaInfo("Éxito", "La contraseña ha sido actualizada correctamente.");
            navInicioSesion(event);
        } else {
            mostrarAlerta("Usuario no encontrado", "No existe ningún empleado registrado con ese correo.");
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

        //Vinculamos la lista filtrada a las tablas
        if (tablaAbastecimiento != null) {
            tablaAbastecimiento.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    productoSeleccionado = newSelection;
                    cargarDatosEnCampos(newSelection); // Dispara el autocompletado
                }
            });
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
            if (colProductoVentas != null) colProductoVentas.setCellValueFactory(new PropertyValueFactory<>("producto"));
            if (colCantidadVentas != null) colCantidadVentas.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            if (colPrecioVentas != null) colPrecioVentas.setCellValueFactory(new PropertyValueFactory<>("precio"));

            tablaVentas.setItems(listaVentas);
        }

        if (lblTitulo != null && lblTitulo.getText() != null) {
            String titulo = lblTitulo.getText();

            switch (titulo) {
                case "Ventas por día":
                    if (colNotaVentaDia != null) colNotaVentaDia.setCellValueFactory(new PropertyValueFactory<>("nota"));
                    if (colClienteVentaDia != null) colClienteVentaDia.setCellValueFactory(new PropertyValueFactory<>("cliente"));
                    if (colEmpleadoVentaDia != null) colEmpleadoVentaDia.setCellValueFactory(new PropertyValueFactory<>("empleado"));
                    if (colNombreVentaDia != null) colNombreVentaDia.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                    if (colPiezasVentaDia != null) colPiezasVentaDia.setCellValueFactory(new PropertyValueFactory<>("piezas"));
                    if (colPrecioVentaDia != null) colPrecioVentaDia.setCellValueFactory(new PropertyValueFactory<>("precio"));
                    if (colTotalVentaDia != null) colTotalVentaDia.setCellValueFactory(new PropertyValueFactory<>("total"));

                    LocalDate hoy = LocalDate.now();
                    if (dpFechaVentas != null) {
                        dpFechaVentas.setValue(hoy);
                        dpFechaVentas.setOnAction(e -> cargarVentasDia(dpFechaVentas.getValue()));
                    }
                    cargarVentasDia(hoy);
                    break;

                case "Ventas por semana":
                    if (colNombreVentaSemana != null) colNombreVentaSemana.setCellValueFactory(new PropertyValueFactory<>("semana"));
                    if (colFechaVentaSemana != null) colFechaVentaSemana.setCellValueFactory(new PropertyValueFactory<>("fecha"));
                    if (colPiezasVentaSemana != null) colPiezasVentaSemana.setCellValueFactory(new PropertyValueFactory<>("piezas"));
                    if (colTotalVentaSemana != null) colTotalVentaSemana.setCellValueFactory(new PropertyValueFactory<>("total"));

                    LocalDate hoySemana = LocalDate.now();
                    if (dpFechaVentas != null) {
                        dpFechaVentas.setValue(hoySemana);
                        dpFechaVentas.setOnAction(e -> cargarVentasSemana(dpFechaVentas.getValue()));
                    }
                    cargarVentasSemana(hoySemana);
                    break;

                case "Ventas por mes":
                    if (colNombreVentaMes != null) colNombreVentaMes.setCellValueFactory(new PropertyValueFactory<>("mes"));
                    if (colPiezasVentaMes != null) colPiezasVentaMes.setCellValueFactory(new PropertyValueFactory<>("piezas"));
                    if (colTotalVentaMes != null) colTotalVentaMes.setCellValueFactory(new PropertyValueFactory<>("total"));

                    LocalDate hoyMes = LocalDate.now();
                    if (dpFechaVentas != null) {
                        dpFechaVentas.setValue(hoyMes);
                        dpFechaVentas.setOnAction(e -> cargarVentasMes(dpFechaVentas.getValue()));
                    }
                    cargarVentasMes(hoyMes);
                    break;
            }
        }
        // Cargar clientes en el control de ventas para asociar cada venta a un cliente
        if (cbClienteVentas != null) {
            cbClienteVentas.setItems(listaClientes);

            // Formateador para mostrar solo el nombre del cliente en las opciones desplegables
            cbClienteVentas.setCellFactory(param -> new ListCell<Cliente>() {
                @Override
                protected void updateItem(Cliente item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? "" : item.getNombre());
                }
            });

            // Formateador para mostrar el nombre del cliente seleccionado
            cbClienteVentas.setButtonCell(new ListCell<Cliente>() {
                @Override
                protected void updateItem(Cliente item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? "" : item.getNombre());
                }
            });
        }

        configurarAutocompletadoVentas();
    }

    @FXML
    void eliminarControlVenta(ActionEvent event) {
        // Obtener el elemento seleccionado de la tabla de ventas
        ProductoVenta seleccionado = tablaVentas.getSelectionModel().getSelectedItem();

        // Validar que el usuario haya seleccionado una fila
        if (seleccionado == null) {
            mostrarAlerta("Sin selección", "Por favor, selecciona un producto de la lista para eliminarlo de la venta.");
            return;
        }

        // Remover el producto de la lista observable
        listaVentas.remove(seleccionado);

        // Recalcular y actualizar el total mostrado en pantalla
        actualizarTotal();

        // Si utilizas lblTotalVentas en tu interfaz, descomenta la siguiente línea:
        actualizarTotalVentas();
    }

    // Esto es para la barra de búsqueda del control de ventas
    private List<Producto> buscarProductosSugeridos(String query) {
        String filtro = query.toLowerCase().trim();
        if (filtro.isEmpty()) return Collections.emptyList();

        List<Producto> empiezanCon = new ArrayList<>();
        List<Producto> contienen = new ArrayList<>();

        for (Producto p : listaProductos) {
            String nombreLower = p.getNombre().toLowerCase();
            String codigoStr = String.valueOf(p.getCodigo());

            // Prioridad 1: Empiezan con el texto o código
            if (nombreLower.startsWith(filtro) || codigoStr.startsWith(filtro)) {
                empiezanCon.add(p);
            }
            // Prioridad 2: Contienen el texto en medio de la palabra
            else if (nombreLower.contains(filtro) || codigoStr.contains(filtro)) {
                contienen.add(p);
            }
        }

        // Une ambas listas manteniendo la prioridad
        empiezanCon.addAll(contienen);
        return empiezanCon;
    }

    private void configurarAutocompletadoVentas() {
        if (barraBusquedaVentas == null) return;

        // Escucha cada tecla escrita en la barra de ventas
        barraBusquedaVentas.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                menuSugerenciasVentas.hide();
            } else {
                mostrarMenuSugerencias(newValue.trim());
            }
        });
    }

    private void mostrarMenuSugerencias(String filtro) {
        List<Producto> coincidencias = buscarProductosSugeridos(filtro);

        if (coincidencias.isEmpty()) {
            menuSugerenciasVentas.hide();
            return;
        }

        menuSugerenciasVentas.getItems().clear();
        int maxResultados = 8; // Muestra un máximo de 8 opciones
        int contador = 0;

        for (Producto p : coincidencias) {
            if (contador++ >= maxResultados) break;

            // Formato visual para el cajero: Nombre | Stock | Precio
            String etiqueta = String.format("%s  |  Existencia: %d",
                    p.getNombre(), p.getExistencia(), p.getPrecioVenta());

            MenuItem item = new MenuItem(etiqueta);
            item.setOnAction(e -> seleccionarProductoAutoCompletado(p));
            menuSugerenciasVentas.getItems().add(item);
        }

        // Despliega el menú debajo de la barra de búsqueda
        if (!menuSugerenciasVentas.isShowing()) {
            menuSugerenciasVentas.show(barraBusquedaVentas, javafx.geometry.Side.BOTTOM, 0, 0);
        }
    }

    private void seleccionarProductoAutoCompletado(Producto p) {
        this.productoSeleccionado = p;
        barraBusquedaVentas.setText(p.getNombre());
        txtPrecioVentas.setText(String.format("$%.2f", p.getPrecioVenta()));
        txtCantidadVentas.setText("1");
        txtCantidadVentas.requestFocus(); // Pasa el foco directamente a la cantidad
        menuSugerenciasVentas.hide();
    }

    // Carga los datos del usuario en sesión a los campos de texto
    private void cargarDatosCuenta() {
        Empleado actual = SesionUsuario.getInstancia().getEmpleadoActual();
        if (actual != null && txtCuentaNombre != null) {
            txtCuentaNombre.setText(actual.getNombre());
            txtCuentaApellidoP.setText(actual.getApellidoPaterno());
            txtCuentaApellidoM.setText(actual.getApellidoMaterno());
            txtCuentaClave.setText(actual.getClave());
            txtCuentaCorreo.setText(actual.getCorreo());
            txtCuentaTelefono.setText(actual.getTelefono());

            // Deshabilitar edición por defecto
            setCamposEdicionCuenta(false);
        }
    }

    // Activa o desactiva la edición de los TextFields
    private void setCamposEdicionCuenta(boolean editable) {
        if (txtCuentaNombre != null) txtCuentaNombre.setEditable(editable);
        if (txtCuentaApellidoP != null) txtCuentaApellidoP.setEditable(editable);
        if (txtCuentaApellidoM != null) txtCuentaApellidoM.setEditable(editable);
        if (txtCuentaClave != null) txtCuentaClave.setEditable(editable);
        if (txtCuentaCorreo != null) txtCuentaCorreo.setEditable(editable);
        if (txtCuentaTelefono != null) txtCuentaTelefono.setEditable(editable);


    }

    @FXML
    public void habilitarEdicionCuenta(ActionEvent event) {
        setCamposEdicionCuenta(true);
        mostrarAlertaInfo("Modo Edición", "Ya puedes modificar la información de tu cuenta.");

    }

    @FXML
    public void guardarDatosCuenta(ActionEvent event) {
        Empleado actual = SesionUsuario.getInstancia().getEmpleadoActual();
        if (actual == null) return;

        String nombre = txtCuentaNombre.getText().trim();
        String apellidoP = txtCuentaApellidoP.getText().trim();
        String apellidoM = txtCuentaApellidoM.getText().trim();
        String clave = txtCuentaClave.getText().trim();
        String correo = txtCuentaCorreo.getText().trim();
        String telefono = txtCuentaTelefono.getText().trim();

        if (nombre.isEmpty() || apellidoP.isEmpty() || clave.isEmpty() || correo.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Nombre, Apellido Paterno, Contraseña y Correo no pueden estar vacíos.");
            return;
        }

        Empleado actualizado = new Empleado(actual.getIdEmpleado(), nombre, apellidoP, apellidoM, clave, correo, telefono, actual.getPuesto());

        if (empleadoDAO.actualizar(actualizado)) {
            SesionUsuario.getInstancia().setEmpleadoActual(actualizado);
            setCamposEdicionCuenta(false);
            mostrarAlertaInfo("Éxito", "Tus datos han sido actualizados correctamente.");
        } else {
            mostrarAlerta("Error", "No se pudo actualizar la información en la base de datos.");
        }
    }

    @FXML
    public void cerrarSesion(ActionEvent event) {
        SesionUsuario.getInstancia().cerrarSesion();
        mostrarAlertaInfo("Sesión finalizada", "Has cerrado sesión exitosamente.");
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/inicioSesion.fxml", event);
    }

    @FXML
    public void eliminarCuenta(ActionEvent event) {
        Empleado actual = SesionUsuario.getInstancia().getEmpleadoActual();
        if (actual == null) return;

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Deseas eliminar tu cuenta?");
        confirmacion.setContentText("Esta acción eliminará tu usuario del sistema y no se podrá deshacer.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            if (empleadoDAO.eliminar(actual.getIdEmpleado())) {
                SesionUsuario.getInstancia().cerrarSesion();
                mostrarAlertaInfo("Cuenta eliminada", "Tu cuenta ha sido removida del sistema.");
                navegacion("/org/example/sistemasparaelcontroldeunafarmacia/inicioSesion.fxml", event);
            } else {
                mostrarAlerta("Error", "No se pudo eliminar la cuenta de la base de datos.");
            }
        }
    }

    private void cargarVentasDia(LocalDate fecha) {
        if (fecha != null && tablaVentasDia != null) {
            tablaVentasDia.getItems().setAll(ventaDAO.obtenerVentasDia(fecha));
        }
    }

    private void cargarVentasSemana(LocalDate fecha) {
        if (fecha != null && tablaVentasSemana != null) {
            tablaVentasSemana.getItems().setAll(ventaDAO.obtenerVentasSemana(fecha.getYear()));
        }
    }

    private void cargarVentasMes(LocalDate fecha) {
        if (fecha != null && tablaVentasMes != null) {
            tablaVentasMes.getItems().setAll(ventaDAO.obtenerVentasMes(fecha.getYear()));
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

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == btnGuardar) {
            String nombre = txtEditNombre.getText().trim();
            String cantidadStr = txtEditCantidad.getText().trim();
            String precioStr = txtEditPrecio.getText().trim();

            // Validar que no dejen campos vacíos
            if (nombre.isEmpty() || cantidadStr.isEmpty() || precioStr.isEmpty()) {
                mostrarAlerta("Campos incompletos", "Por favor completa Nombre, Cantidad y Precio.");
                return;
            }

            try {
                int existencia = Integer.parseInt(cantidadStr);
                float precio = Float.parseFloat(precioStr);

                // Creamos el objeto Producto actualizado
                Producto prodActualizado = new Producto(productoSeleccionado.getCodigo(), nombre, existencia, precio);

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
            int cantidadAñadir = Integer.parseInt(txtCantidad.getText().trim());
            float precio = Float.parseFloat(txtPrecio.getText().trim());

            if (cantidadAñadir <= 0) {
                mostrarAlerta("Cantidad inválida", "La cantidad ingresada debe ser mayor a 0.");
                return;
            }

            // 1. Verificamos si hay un producto seleccionado o si coincide por nombre en la lista
            Producto productoExistente = productoSeleccionado;

            if (productoExistente == null) {
                for (Producto p : listaProductos) {
                    if (p.getNombre().equalsIgnoreCase(nombre)) {
                        productoExistente = p;
                        break;
                    }
                }
            }

            // 2. Si el producto ya existe, actualizamos su existencia sumando la cantidad
            if (productoExistente != null) {
                int nuevaExistencia = productoExistente.getExistencia() + cantidadAñadir;
                Producto prodActualizado = new Producto(productoExistente.getCodigo(), nombre, nuevaExistencia, precio);

                if (productoDAO.actualizar(prodActualizado)) {
                    mostrarAlertaInfo("Stock actualizado", "Se añadieron " + cantidadAñadir + " unidades a '" + nombre + "'. Nueva existencia: " + nuevaExistencia);
                    limpiarCampos();
                    cargarProductosBD();
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar la existencia del producto en la base de datos.");
                }
            } else {
                // 3. Si no existe, creamos el registro nuevo
                Producto nuevoProducto = new Producto(0, nombre, cantidadAñadir, precio);

                if (productoDAO.insertar(nuevoProducto)) {
                    mostrarAlertaInfo("Éxito", "¡Producto registrado exitosamente!");
                    limpiarCampos();
                    cargarProductosBD();
                } else {
                    mostrarAlerta("Error", "No se pudo guardar el nuevo producto en la base de datos.");
                }
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Formato incorrecto", "Por favor, ingresa números válidos en cantidad y precio.");
        }
    }

    // Método auxiliar para pasar los datos del producto seleccionado a los campos de texto
    private void cargarDatosEnCampos(Producto producto) {
        if(producto == null){
            return;
        }
        if (txtCodigo != null) txtCodigo.setText(String.valueOf(producto.getCodigo()));
        if (txtNombre != null) txtNombre.setText(producto.getNombre());
        if (txtCantidad != null) txtCantidad.clear();
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
        productoSeleccionado = null; // Reiniciamos la selección
    }

    // Muestra mensajes de éxito / información al usuario
    private void mostrarAlertaInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        // 1. Obtener el DialogPane de la alerta
        DialogPane dialogPane = alert.getDialogPane();

        // 2. Cargar tu archivo CSS (ajusta la ruta según tu proyecto)
        dialogPane.getStylesheets().add(
                getClass().getResource("/style/style.css").toExternalForm()
        );

        // 3. (Opcional) Agregar una clase personalizada para este tipo de alerta
        dialogPane.getStyleClass().add("mi-alerta-custom");

        alert.showAndWait();
    }

    public void cargarClientesBD() {
        if (listaClientes == null) return;
        listaClientes.clear();
        List<Cliente> clientesBD = clienteDAO.listar();
        listaClientes.addAll(clientesBD);
    }

    @FXML
    public void finalizarVenta(ActionEvent event) {
        if (listaVentas.isEmpty()) {
            mostrarAlerta("Tabla vacía", "No hay productos agregados a la venta actual.");
            return;
        }

        // Validar selección obligatoria de cliente
        Cliente clienteSeleccionadoVenta = (cbClienteVentas != null) ? cbClienteVentas.getValue() : null;
        if (clienteSeleccionadoVenta == null) {
            mostrarAlerta("Cliente no seleccionado", "Por favor selecciona un cliente antes de finalizar la venta.");
            return;
        }

        double totalConIVA = 0.0;
        for (ProductoVenta p : listaVentas) {
            totalConIVA += p.getPrecio() * p.getCantidad();
        }
        totalConIVA *= 1.16;

        // Recuperar ID de empleado y ID de cliente dinámico
        Empleado empActivo = SesionUsuario.getInstancia().getEmpleadoActual();
        int idEmpleado = (empActivo != null) ? empActivo.getIdEmpleado() : 1;
        int idCliente = clienteSeleccionadoVenta.getCodigo();

        if (ventaDAO.registrarVenta(idCliente, idEmpleado, totalConIVA, listaVentas)) {
            mostrarAlertaInfo("Venta completada", "La venta se registró exitosamente a nombre de " + clienteSeleccionadoVenta.getNombre() + ".");
            listaVentas.clear();
            if (cbClienteVentas != null) cbClienteVentas.getSelectionModel().clearSelection();
            lblTotal.setText("$0.00");
            cargarProductosBD();
        } else {
            mostrarAlerta("Error", "No se pudo procesar la venta en la base de datos.");
        }
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

            // ACTUALIZA EL TOTAL EN PANTALLA
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