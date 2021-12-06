package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.model.MailingAddress;
import com.github.sjlian014.jlmsclient.model.PhoneNumber;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PhoneNumberForm extends BasicForm<PhoneNumber> {

    private final TextField phoneNumber;
    private final ChoiceBox<Pair<String, PhoneNumber.PhoneNumberType>> phoneNumberType;

    public PhoneNumberForm() {
        super("Modify Phone Number");

        phoneNumber = new TextField();
        phoneNumber.setPromptText("phone number");
        phoneNumberType = new ChoiceBox<>();
        phoneNumberType.getItems().addAll(
                Arrays.stream(PhoneNumber.PhoneNumberType.values())
                        .map((v) -> new Pair<>(v.name(), v))
                        .collect(Collectors.toList()));
        phoneNumberType.setConverter(new StringConverter<>() {
            @Override
            public String toString(Pair<String, PhoneNumber.PhoneNumberType> pair) {
                return (pair != null) ? pair.getKey() : "";
            }

            @Override
            public Pair<String, PhoneNumber.PhoneNumberType> fromString(String string) {
                return null;
            }
        });

        addRow(phoneNumber, "Phone Number: ");
        addRow(phoneNumberType, "Phone Number Type: ");

        this.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                PhoneNumber tmp = new PhoneNumber();
                boolean encounteredError = countExceptions(List.of(
                        () -> tmp.setPhoneNum(Integer.parseInt(phoneNumber.getText())),
                        () -> tmp.setType(phoneNumberType.getValue().getValue()))
                );

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
