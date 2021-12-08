package com.github.sjlian014.jlmsclient.service;

import com.github.sjlian014.jlmsclient.App;
import com.github.sjlian014.jlmsclient.controller.form.ConfirmDialogBuilder;
import com.github.sjlian014.jlmsclient.exception.UnfulfilledRequestException;
import com.github.sjlian014.jlmsclient.restclient.RestClient;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.function.Consumer;

public abstract class Service<T> {

    private RestClient<T> source;
    private ReadOnlyObjectProperty<ObservableList<T>> dataStore =
            new SimpleObjectProperty<>(FXCollections.observableArrayList());

    public Service(ObjectProperty<ObservableList<T>> bindTarget, RestClient<T> source) {
        this.source = source;
        bindTarget.bind(dataStore);
    }

    public void asyncGetAll(Runnable callback) {
        dataStore.get().clear();
        Runnable task = () -> {
            try {
                var tmp = source.fetchAll();
                Platform.runLater(() -> {
                    dataStore.get().addAll(tmp);
                    callback.run();
                });
            } catch (UnfulfilledRequestException e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    new ConfirmDialogBuilder().setAlertType(Alert.AlertType.ERROR)
                            .setTitle("Error!")
                            .setHeaderText("An error has occurred while trying to fetch data from the server!")
                            .setContentText(e.prettyMessage())
                            .buildAndShow();
                });
            }
        };

        App.executor.execute(task);
    }

    public void asyncPostOne(T obj2p, Consumer<T> callbackWithReturnedData) {
        Runnable task = () -> {
            try {
                var returnedObject = source.postOne(obj2p);
                Platform.runLater(() -> callbackWithReturnedData.accept(returnedObject));
            } catch (UnfulfilledRequestException e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    new ConfirmDialogBuilder().setAlertType(Alert.AlertType.ERROR)
                            .setTitle("Error!")
                            .setHeaderText("An error has occurred while trying to upload submit data to the server!")
                            .setContentText(e.prettyMessage())
                            .buildAndShow();
                });
            }
        };

        App.executor.execute(task);
    }

    public String getPathToURI() {
        return source.getRootEndPoint();
    }
}
