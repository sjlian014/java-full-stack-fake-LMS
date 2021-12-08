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

public abstract class BasicForm<T> extends Dialog<T> {

    private final ArrayList<Exception> exceptions = new ArrayList<>();
    private final GridPane grid;
    private final ButtonType confirmButtonType;
    private final Supplier<T> returnObjectInstantiationStrategy;

    protected BasicForm(String title, Supplier<T> returnObjectInstantiationStrategy) {
        this.returnObjectInstantiationStrategy = returnObjectInstantiationStrategy;
        // Set Confirm button and Cancel button
        confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        this.getDialogPane().setContent(grid);

        createConvertor();
    }

    // macro for adding a row
    protected void addRow(Node component, String prompt) {
        int rowCount = grid.getRowCount();
        if (rowCount == 0)
            // Request focus on the first editable component by default.
            Platform.runLater(component::requestFocus);

        grid.addRow(rowCount, new Label(prompt), component);
    }

    // use by sub-classes to specify the tasks that's going to be run in the converter
    // this needs to be set (even with an empty list) to prevent logic error. It is better to put this in the
    // constructor parameter but sadly compiler would complain if the lambda contain any reference to instance variable
    // of a sub-class
    abstract List<Consumer<T>> submitConvertorTasks();

    private void createConvertor() {
        this.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                T returnObject = returnObjectInstantiationStrategy.get();
                submitConvertorTasks().forEach((task) -> countException(task, returnObject));
                boolean encounteredError = exceptions.size() > 0;

                if(!encounteredError)
                    return returnObject;

                showErrorDialog();
                throw new RuntimeException("please ignore:" +
                        " this is a place holder exception to keep the dialog alive");
            }
            return null;
        });
    }

    private void countException(Consumer<T> task, T target) {
        try {
            task.accept(target);
        } catch (NumberFormatException e) {
            exceptions.add(new ValidationException("found a string in place of a number"));
        }  catch (Exception e) {
            exceptions.add(e);
        }
    }

    private void showErrorDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Encountered %d problems when trying to parse input".formatted(exceptions.size()));
        String context = exceptions.stream()
                .reduce("", (acc, v) -> acc + "\n" + v.getMessage() + "\n", (_ignore1, _ignore2) -> "");
        alert.setContentText(context);

        exceptions.clear();

        alert.showAndWait();
    }

    public abstract void readInitialValue(T initialValue);
    public abstract void clearComponents();

}