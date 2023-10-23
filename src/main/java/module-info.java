module org.ssc {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.ssc.gui to javafx.fxml;
    exports org.ssc.gui;
    exports org.ssc;
    opens org.ssc to javafx.fxml;
}
