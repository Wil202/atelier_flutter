module com.hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires atlantafx.base;

    opens com.hotel to javafx.base;
    opens com.hotel.ui to javafx.graphics, javafx.fxml;

    exports com.hotel;
    exports com.hotel.ui;
}
