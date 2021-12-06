package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.model.EmailAddress;
import com.github.sjlian014.jlmsclient.model.MailingAddress;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EmailAddressForm extends BasicForm<EmailAddress> {

    private final TextField emailAddress;
    private final ChoiceBox<Pair<String, EmailAddress.EmailAddressType>> emailAddressType;

    public EmailAddressForm() {
        super("Modify Email Address");

        // initialize the fields
        emailAddress = new TextField();
        emailAddress.setPromptText("email address");

        emailAddressType = new ChoiceBox<>();
        emailAddressType.getItems().addAll(
                Arrays.stream(EmailAddress.EmailAddressType.values())
                        .map((v) -> new Pair<>(v.name(), v))
                        .collect(Collectors.toList()));
        emailAddressType.setConverter(new StringConverter<>() {
            @Override
            public String toString(Pair<String, EmailAddress.EmailAddressType> pair) {
                return (pair != null) ? pair.getKey() : "";
            }

            @Override
            public Pair<String, EmailAddress.EmailAddressType> fromString(String string) {
                return null;
            }
        });

        addRow(emailAddress, "Email Address: ");
        addRow(emailAddressType, "Email Type: ");

        this.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                EmailAddress tmp = new EmailAddress();
                boolean encounteredError = countExceptions(List.of(() -> tmp.seteAddr(emailAddress.getText()),
                        () -> tmp.setType(emailAddressType.getValue().getValue())
                ));
                if(!encounteredError)
                    return tmp;

                showErrorDialog();
                throw new RuntimeException("please ignore:" +
                        " this is a place holder exception to keep the dialog alive");
            }
            return null;
        });
    }
}
