module com.example.stockscoinviewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.stockscoinviewer to javafx.fxml;
    exports com.example.stockscoinviewer.app;

    requires org.kordamp.ikonli.fontawesome5;
    requires org.json;
    requires java.net.http;
}