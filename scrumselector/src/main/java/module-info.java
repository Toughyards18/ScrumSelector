module scrumselector {
    requires javafx.controls;
    requires javafx.fxml;

    opens scrumselector to javafx.fxml;
    exports scrumselector;
}
