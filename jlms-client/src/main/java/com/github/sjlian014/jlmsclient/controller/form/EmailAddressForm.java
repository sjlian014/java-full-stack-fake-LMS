package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.Util.ChoiceBoxUtil;
import com.github.sjlian014.jlmsclient.model.EmailAddress;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class EmailAddressForm extends BasicForm<EmailAddress> {

    private final TextField emailAddress;
    private final ChoiceBox<Pair<String, EmailAddress.EmailAddressType>> emailAddressType;

    public EmailAddressForm() {
        super("Modify Email Address", EmailAddress::new);

        emailAddress = new TextField();
        emailAddressType = ChoiceBoxUtil.mapEnum(EmailAddress.EmailAddressType.class);

        addRow(emailAddress, "Email Address: ");
        addRow(emailAddressType, "Email Type: ");
    }

    @Override
    List<Consumer<EmailAddress>> submitConvertorTasks() {
        return List.of(
                (email) -> email.seteAddr(emailAddress.getText()),
                (email) -> email.setType(emailAddressType.getValue().getValue())
        );
    }

    @Override
    public void readInitialValue(EmailAddress initialValue) {
        emailAddress.setText(Optional.ofNullable(initialValue.geteAddr()).orElse(""));
        Optional.ofNullable(initialValue.getType()).ifPresentOrElse((v) -> {
            emailAddressType.getItems().stream().filter(pair -> pair.getValue() == v).findFirst().ifPresent(match -> {
                emailAddressType.getSelectionModel().select(match);
            });
        }, () -> emailAddressType.getSelectionModel().clearSelection());
    }

    @Override
    public void clearComponents() {
        emailAddress.clear();
        emailAddressType.getSelectionModel().clearSelection();
    }
}
