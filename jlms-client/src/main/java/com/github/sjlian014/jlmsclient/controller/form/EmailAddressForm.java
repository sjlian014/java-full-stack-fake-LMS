package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.Util.ChoiceBoxUtil;
import com.github.sjlian014.jlmsclient.model.EmailAddress;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.List;
import java.util.function.Consumer;

public class EmailAddressForm extends BasicForm<EmailAddress> {

    private final TextField emailAddress;
    private final ChoiceBox<Pair<String, EmailAddress.EmailAddressType>> emailAddressType;

    public EmailAddressForm() {
        super("Modify Email Address", EmailAddress::new);

        emailAddress = new TextField();
        emailAddressType = ChoiceBoxUtil.mapEnum(EmailAddress.EmailAddressType.values());

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
}
