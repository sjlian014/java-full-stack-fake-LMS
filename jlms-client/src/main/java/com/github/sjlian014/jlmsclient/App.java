package com.github.sjlian014.jlmsclient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sjlian014.jlmsclient.model.EmailAddress;
import com.github.sjlian014.jlmsclient.model.EmailAddress.EmailAddressType;
import com.github.sjlian014.jlmsclient.model.Student;
import com.github.sjlian014.jlmsclient.restclient.StudentClient;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    // shared executor
    public static ExecutorService executor = Executors.newFixedThreadPool(4);
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 1366, 768);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> executor.shutdown());
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws Exception {
        launch();
    }


}
