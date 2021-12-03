module com.github.sjlian014.jlmsclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.github.sjlian014.jlmsclient.model to com.fasterxml.jackson.databind;
    opens com.github.sjlian014.jlmsclient.controller to javafx.fxml;
    exports com.github.sjlian014.jlmsclient;
}

// module com.github.sjlian014.jlmsclient.httpclient {
//     requires java.net.http;

//     exports com.github.sjlian014.jlmsclient.httpclient;
// }
