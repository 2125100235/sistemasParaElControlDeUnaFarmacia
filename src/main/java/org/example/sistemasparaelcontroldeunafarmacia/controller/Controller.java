package org.example.sistemasparaelcontroldeunafarmacia.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.sistemasparaelcontroldeunafarmacia.dao.ProductoDAO;
import org.example.sistemasparaelcontroldeunafarmacia.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.example.sistemasparaelcontroldeunafarmacia.db.ConexionBD;

import java.net.URL;

public class Controller {

    //Campos para el inicio de sesion nombre y Apellido
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;



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
    private Button btnNuevoProducto;

    @FXML
    private Button btnProductosRegresarPrincipal;

    @FXML
    private TableView<?> tablaProductos;

    @FXML
    private TableColumn<?, ?> colCaducidad;

    @FXML
    private TableColumn<?, ?> colCodigo;

    @FXML
    private TableColumn<?, ?> colExistencia;

    @FXML
    private TableColumn<?, ?> colNombre;

    @FXML
    private TableColumn<?, ?> colPrecio;

    @FXML
    private Button btnNuevoProductoRegresarProductos;
    @FXML
    private TableView<?> tablaAbastecimiento;

    //ClientesMenu
    @FXML
    private Button btnNuevoCliente;
    @FXML
    private Button btnNuevoClienteRegresarClientes;
    //Acciones en BD
    private ProductoDAO productoDAO;
    private Producto productoSeleccionado;
    private ObservableList<Producto> listaProductos;

 //Método principal para la navegación entre ventanas, se utiliza de forma universal para toda la navegación, por botón se le pasan los parámetros de la URL de la ventana hacia la que va y el evento desde el cuál fue accionado (el botón)
    /*
    @FXML
    public void initialize(){
        productoDAO= new ProductoDAO();
        configurarTabla();
        cargarProductos();
        tablaProductos
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, anterior, seleccionado) -> {
                            if(seleccionado != null){
                                productoSeleccionado = seleccionado;
                                mostrarProductoSeleccionado();
                            }
                        }
                );
    }
    private void mostrarProductoSeleccionado(){
        txtNombre.setText(
                productoSeleccionado.getNombre()
        );
        txtCantidad.setText(
                productoSeleccionado.getCantidad()
        );
        txtPrecioVenta.setText(
                String.valueOf(
                        productoSeleccionado.getPrecioVenta()
                )
        );

        txtFechaCaducidad.setText(
                productoSeleccionado.getFechacaducidad()
        );
    }
    @FXML
    public void onActualizarClick(){
        if(productoSeleccionado == null){
            lblResultado.setText(
                    "Seleccione un producto"
            );
            return;
        }
        productoSeleccionado.setNombre(
                txtNombre.getText()
        );
        productoSeleccionado.setCantidad(
                Integer.parseInt(
                        txtCantidad.getText();
                )
        )
        productoSeleccionado.setPrecioVenta(
                Integer.parseInt(
                        txtPrecioVenta.getText()
                )
        );
        productoSeleccionado.setFechacaducidad(
                txtFechaCaducidad.getText()
        );

        productoDAO.actualizar(
                productoSeleccionado
        );
        lblResultado.setText(
                "Producto actualizado"
        );
        cargarProductos();
        limpiar();
    }
    @FXML
    public void onEliminarClick(){
        if(productoSeleccionado == null){
            lblResultado.setText(
                    "Seleccione un producto"
            );
            return;

        }
        Alert alerta =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );
        alerta.setTitle(
                "Eliminar producto"
        );
        alerta.setHeaderText(null);
        alerta.setContentText(
                "¿Desea eliminar este producto?"
        );
        if(
                alerta.showAndWait()
                        .get()
                        ==
                        ButtonType.OK
        ){
            productoDAO.eliminar(
                    productoSeleccionado.getCodigo()
            );
            lblResultado.setText(
                    "Producto eliminado"
            );
            cargarProductos();
            limpiar();
        }
    }
    private void configurarTabla(){
        colCodigo.setCellValueFactory(
                dato ->
                        new SimpleIntegerProperty(
                                dato.getValue().getCodigo()
                        ).asObject()
        );
        colNombre.setCellValueFactory(
                dato ->
                        new SimpleStringProperty(
                                dato.getValue().getNombre()
                        )
        );

        colCantidad.setCellValueFactory(
                dato ->
                        new SimpleIntegerProperty(
                                dato.getValue().getCantidad()
                        ).asObject()
        );
        colExistencia.setCellValueFactory(
                dato ->
                        new SimpleIntegerProperty(
                                dato.getValue().getExistencia()
                        )
        );
        colPrecio.setCellValueFactory(
                dato ->
                new SimpleFloatProperty(
                        dato.getValue.getPrecio()
                )
        );
        colCaducidad.setCellValueFactory(
                dato ->
                        new SimpleDateFormat(
                                dato.getValue().getFechaCaducidad
                        )
        );
    }
    private void cargarProductos(){
        listaProductos =
                FXCollections.observableArrayList(
                        productoDAO.listar()
                );
        tablaProductos.setItems(listaProductos);
    }
    */
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
            System.out.println("(Error: " + e.getMessage() +")");
        }
    }
    //Navegacion entre ventanas, se le pasan los parámetros de URL y evento al método de navegación
    @FXML
    void navPrincipal(MouseEvent event){
        //Comienza a validar los datos
        if (txtNombre != null && txtApellido != null) {
            String nombreIngresado = txtNombre.getText().trim();
            String apellidoIngresado = txtApellido.getText().trim();

            //Si los datos estan vacios, manda una alerta
            if (nombreIngresado.isEmpty() || apellidoIngresado.isEmpty()) {
                mostrarAlerta("Error de inicio de sesión", "Por favor ingresa tu nombre y apellido paterno.");
                return;
            }

            //Guarda en una cadena de texto lo que se pedira en MariaDB
            //Los signos ? son comodines donde enviaremos los datos reales mas adelante, esto por seguridad
            String sql = "select * from empleado where nombre = ? and apellidopaterno = ?";

            try {
                // obtenemos la conexión compartida de tu amigo sin cerrarla
                Connection cn = ConexionBD.getInstancia().getConexion();

                // preparamos la consulta y nos aseguramos de cerrar solo la sentencia y el resultado
                //cambiamos los ? por el nombre y apellidos ingresados
                try (PreparedStatement ps = cn.prepareStatement(sql)) {
                    ps.setString(1, nombreIngresado);
                    ps.setString(2, apellidoIngresado);

                    //ResultSet es una tabla temporal donde MariaDB devuelve los datos
                    //ps.executeQuery() envía la pregunta terminada a MariaDB
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String puesto = rs.getString("puesto");
                            System.out.println("¡bienvenido " + nombreIngresado + "! puesto: " + puesto);

                            // cambiamos a la pantalla principal
                            navegacion("/org/example/sistemasparaelcontroldeunafarmacia/principal.fxml", event);
                        } else {
                            mostrarAlerta("Acceso denegado", "El empleado no se encuentra registrado.");
                        }
                    }
                }
            } catch (Exception e) {
                // imprimimos el error exacto en la consola de intellij para verlo
                e.printStackTrace();
                mostrarAlerta("Error de conexión", "Ocurrió un error al consultar la base de datos.");
            }
        } else {
            navegacion("/org/example/sistemasparaelcontroldeunafarmacia/principal.fxml", event);
        }
    }

    @FXML
    void navRegistro(MouseEvent event){
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/registro.fxml", event);
    }

    @FXML
    void navRegresarPrincipal(MouseEvent event){
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
    void navProductosMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/productosMenu.fxml", event);
    }

    @FXML
    void navRegistroMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/registroMenu.fxml", event);
    }

    @FXML
    void navVentasMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/ventasDiaMenu.fxml", event);
    }

    @FXML
    void navVentasMesMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/ventasMesMenu.fxml", event);
    }

    @FXML
    void navVentasSemanaMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/ventasSemanaMenu.fxml", event);
    }

    @FXML
    void navAyuda(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/ayuda.fxml", event);
    }
    @FXML
    void navNuevoProducto(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/nuevoProducto.fxml", event);
    }
    @FXML
    void navRegresarProductosMenu(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/productosMenu.fxml", event);
    }

    @FXML
    void navNuevoCliente(MouseEvent event) {
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/nuevoCliente.fxml", event);
    }
    @FXML
    void navRegresarClientesMenu(MouseEvent event) {
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
