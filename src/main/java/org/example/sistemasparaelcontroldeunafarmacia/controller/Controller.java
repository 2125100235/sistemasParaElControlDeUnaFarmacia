package org.example.sistemasparaelcontroldeunafarmacia.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;

public class Controller {

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private Label lblTitulo;

    @FXML
    private Button btnRegresar;

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
            System.out.println("(Error: " + e.getMessage() +")\nError al intentar navegar. Cierre el programa y vuelvalo a intentar." );
        }
    }
    //Navegacion entre ventanas, se le pesan los parámetros de URL y evento al método de navegación
    @FXML
    void navPrincipal(MouseEvent event){
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/principal.fxml", event);
    }

    @FXML
    void navRegistro(MouseEvent event){
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/registro.fxml", event);
    }

    @FXML
    void navRegresar(MouseEvent event){
        navegacion("/org/example/sistemasparaelcontroldeunafarmacia/inicio.fxml", event);
    }
}
