package org.example.sistemasparaelcontroldeunafarmacia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //Importamos nuestra hoja de estilos CSS
        String css = Objects.requireNonNull(this.getClass().getResource("/style/style.css")).toExternalForm();
        //Importamos la fuente
        Font.loadFont(getClass().getResourceAsStream("/fonts/DMSans-VariableFont_opsz,wght.ttf"), 12);
        scene.getStylesheets().add(css);
        stage.setTitle("Sistema para el control de la farmacia");
        stage.setScene(scene);
        stage.show();
    }
}
