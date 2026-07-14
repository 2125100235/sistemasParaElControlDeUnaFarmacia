module org.example.sistemasparaelcontroldeunafarmacia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens org.example.sistemasparaelcontroldeunafarmacia to javafx.fxml;
    exports org.example.sistemasparaelcontroldeunafarmacia;
    exports org.example.sistemasparaelcontroldeunafarmacia.controller;
    opens org.example.sistemasparaelcontroldeunafarmacia.controller to javafx.fxml;
}