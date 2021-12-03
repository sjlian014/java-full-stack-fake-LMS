package com.github.sjlian014.jlmsclient;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sjlian014.jlmsclient.restclient.RestClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws Exception {
        RestClient conn = RestClient.getDefaultClient();
        var result = conn.getResponse(List.of("courses"));

        ObjectMapper om = new ObjectMapper();
        JsonNode json = om.readTree(result);
        System.out.println(json.get(0).get("name").asText());
        json.forEach((course) -> System.out.println(course.toString()));;

        // launch();
    }

}
