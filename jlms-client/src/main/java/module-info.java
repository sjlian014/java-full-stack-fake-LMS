module com.github.sjlian014.jlmsclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.github.sjlian014.jlmsclient to javafx.fxml;
    opens com.github.sjlian014.jlmsclient.restclient to javafx.fxml, com.fasterxml.jackson.databind;
    opens com.github.sjlian014.jlmsclient.model to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.github.sjlian014.jlmsclient;
    exports com.github.sjlian014.jlmsclient.restclient;
    exports com.github.sjlian014.jlmsclient.model;
}

// module com.github.sjlian014.jlmsclient.httpclient {
//     requires java.net.http;

//     exports com.github.sjlian014.jlmsclient.httpclient;
// }
