package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.exception.InternalDesignConstraintException;
import com.github.sjlian014.jlmsclient.model.Major;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MajorForm extends ListViewForm<List<Major>, Major> {

    public MajorForm() {
        super("Choose a Major");

        this.setHeaderText("Choose a major: ");
        list = new ListView<>();

        pane.setCenter(list);

        // list.prefHeightProperty().bind(pane.widthProperty());
        // list.prefWidthProperty().bind(pane.heightProperty());
    }

    @Override
    public List<Consumer<Major>> submitConvertorTasks() {
        return List.of((Major) -> list.getSelectionModel().selectedItemProperty());
    }

    @Override
    public void readInitialValue(List<Major> initialValue) {
        list.getItems().setAll(initialValue);
    }

    @Override
    public void clearComponents() {
        list.getItems().clear();
    }

    @Override
    protected Supplier<Major> submitObjectCreationStrategy() {
        return () -> list.getSelectionModel().getSelectedItem();
    }
}
