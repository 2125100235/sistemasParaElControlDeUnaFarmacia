module org.example.sistemasparaelcontroldeunafarmacia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires de.jensd.fx.glyphs.fontawesome;


    opens org.example.sistemasparaelcontroldeunafarmacia to javafx.fxml;
    opens org.example.sistemasparaelcontroldeunafarmacia.model to javafx.base, javafx.fxml;
    exports org.example.sistemasparaelcontroldeunafarmacia;
    exports org.example.sistemasparaelcontroldeunafarmacia.controller;
    opens org.example.sistemasparaelcontroldeunafarmacia.controller to javafx.fxml;
}