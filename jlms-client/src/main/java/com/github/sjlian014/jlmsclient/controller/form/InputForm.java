package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.exception.ValidationException;
import com.github.sjlian014.jlmsclient.model.EmailAddress;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class InputForm<T> extends BasicForm<T, T> {

    private final GridPane grid;

    protected InputForm(String title) {
        super(title);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        this.getDialogPane().setContent(grid);
    }

    // macro for adding a row
    protected void addRow(Node component, String prompt) {
        int rowCount = grid.getRowCount();
        if (rowCount == 0)
            // Request focus on the first editable component by default.
            Platform.runLater(component::requestFocus);

        grid.addRow(rowCount, new Label(prompt), component);
    }

}