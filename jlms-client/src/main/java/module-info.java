module com.github.sjlian014.jlmsclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;

    opens com.github.sjlian014.jlmsclient to com.fasterxml.jackson.databind;
    opens com.github.sjlian014.jlmsclient.model to com.fasterxml.jackson.databind;
    opens com.github.sjlian014.jlmsclient.controller to javafx.fxml;
    exports com.github.sjlian014.jlmsclient;
    opens com.github.sjlian014.jlmsclient.controller.form to javafx.fxml;
}