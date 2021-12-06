package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.exception.ValidationException;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicForm<T> extends Dialog<T> {

    private ArrayList<Exception> exceptions = new ArrayList<>();
    private final GridPane grid;
    private int rowCount = 0;
    protected final ButtonType confirmButtonType;

    protected BasicForm(String title) {
        // Set Confirm button and Cancel button
        confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        this.getDialogPane().setContent(grid);
    }

    // macro for adding a row
    protected void addRow(Node component, String prompt) {
        int rowCount = grid.getRowCount();
        if(rowCount == 0)
            // Request focus on the first editable component by default.
            Platform.runLater(component::requestFocus);

        grid.addRow(rowCount, new Label(prompt), component);
    }

    protected boolean countExceptions(List<Runnable> actions){

        actions.forEach((action) -> {
                    try {
                        action.run();
                    } catch (NumberFormatException e) {
                        exceptions.add(new ValidationException("found a string in place of a number"));
                    } catch (Exception e) {
                        exceptions.add(e);
                    }
                });

        return exceptions.size() > 0;
    }

    protected void showErrorDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Encountered %d problems when trying to parse input".formatted(exceptions.size()));
        String context = exceptions.stream()
                .reduce("", (acc, v) -> acc + "\n" + v.getMessage() + "\n", (_ignore1, _ignore2) -> "");
        alert.setContentText(context);

        exceptions.clear();

        alert.showAndWait();
    }
}
