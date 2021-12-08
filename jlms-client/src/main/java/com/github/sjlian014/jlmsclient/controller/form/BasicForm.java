package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.exception.ValidationException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BasicForm<I, T> extends Dialog<T> {

    private final ArrayList<Exception> exceptions = new ArrayList<>();
    private final ButtonType confirmButtonType;

    public BasicForm(String title) {
        this.setTitle(title);
        // Set Confirm button and Cancel button
        confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        createConvertor();
    }
    private void countException(Consumer<T> task, T target) {
        try {
            task.accept(target);
        } catch (NumberFormatException e) {
            exceptions.add(new ValidationException("invalid number."));
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

    private void createConvertor() {
        this.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                T returnObject = submitObjectCreationStrategy().get();
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
    protected abstract List<Consumer<T>> submitConvertorTasks();
    public abstract void readInitialValue(I initialValue);
    public abstract void clearComponents();
    abstract protected Supplier<T> submitObjectCreationStrategy();
}
