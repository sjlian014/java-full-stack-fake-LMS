package com.github.sjlian014.jlmsclient;

import com.github.sjlian014.jlmsclient.controller.form.FormBuilder;
import com.github.sjlian014.jlmsclient.controller.form.MajorForm;
import com.github.sjlian014.jlmsclient.model.Major;
import com.github.sjlian014.jlmsclient.restclient.MajorClient;
import com.github.sjlian014.jlmsclient.restclient.MinorClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {

    // shared executor
    public static ExecutorService executor = Executors.newFixedThreadPool(4);
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 1600, 1000);
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
