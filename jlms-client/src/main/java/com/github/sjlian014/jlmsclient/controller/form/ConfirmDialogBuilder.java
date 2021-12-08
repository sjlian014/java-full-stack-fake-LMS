package com.github.sjlian014.jlmsclient.controller.form;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;

public class ConfirmDialogBuilder {

    private final Alert confirmDialog;

    public ConfirmDialogBuilder() {
        confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
    }

    private ConfirmDialogBuilder(Alert alert2c) {
        confirmDialog = alert2c;
    }

    public ConfirmDialogBuilder setAlertType(Alert.AlertType type){
        confirmDialog.setAlertType(type);
        return new ConfirmDialogBuilder(confirmDialog);
    }

    public ConfirmDialogBuilder setTitle(String title) {
         confirmDialog.setTitle(title);
         return new ConfirmDialogBuilder(confirmDialog);
    }

    public ConfirmDialogBuilder setHeaderText(String header) {
        confirmDialog.setHeaderText(header);
        return new ConfirmDialogBuilder(confirmDialog);
    }

    public ConfirmDialogBuilder setContentText(String content) {
        confirmDialog.setContentText(content);
        return new ConfirmDialogBuilder(confirmDialog);
    }

   public Alert build() {
        return confirmDialog;
   }

   public boolean buildAndShow() {
       Optional<ButtonType> result = build().showAndWait();
       return result.map((buttonType) -> { if(buttonType == ButtonType.OK) return true; else return false; })
               .orElse(false);
    }

}
