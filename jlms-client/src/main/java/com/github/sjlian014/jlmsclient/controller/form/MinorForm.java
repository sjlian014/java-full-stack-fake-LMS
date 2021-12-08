package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.model.Major;
import com.github.sjlian014.jlmsclient.model.Minor;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MinorForm extends ListViewForm<List<Minor>, Minor> {

    public MinorForm() {
        super("Choose a Major");

        this.setHeaderText("Choose a major: ");
        list = new ListView<>();

        pane.setCenter(list);
/*
        list.prefHeightProperty().bind(pane.widthProperty());
        list.prefWidthProperty().bind(pane.heightProperty());
*/
    }

    @Override
    public List<Consumer<Minor>> submitConvertorTasks() {
        return List.of((Minor) -> list.getSelectionModel().selectedItemProperty());
    }

    @Override
    public void readInitialValue(List<Minor> initialValue) {
        list.getItems().setAll(initialValue);
    }

    @Override
    public void clearComponents() {
        list.getItems().clear();
    }

    @Override
    protected Supplier<Minor> submitObjectCreationStrategy() {
        return () -> list.getSelectionModel().getSelectedItem();
    }
}
