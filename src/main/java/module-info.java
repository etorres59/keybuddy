module com.evantorreaudio {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens com.evantorreaudio to javafx.fxml;
    exports com.evantorreaudio;
}
