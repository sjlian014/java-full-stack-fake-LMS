package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.Util.ChoiceBoxUtil;
import com.github.sjlian014.jlmsclient.model.PhoneNumber;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PhoneNumberForm extends InputForm<PhoneNumber> {

    private final TextField phoneNumber;
    private final ChoiceBox<Pair<String, PhoneNumber.PhoneNumberType>> phoneNumberType;

    public PhoneNumberForm() {
        super("Modify Phone Number");

        phoneNumber = new TextField();
        phoneNumberType = ChoiceBoxUtil.mapEnum(PhoneNumber.PhoneNumberType.class);

        addRow(phoneNumber, "Phone Number: ");
        addRow(phoneNumberType, "Phone Number Type: ");
    }

    @Override
    public List<Consumer<PhoneNumber>> submitConvertorTasks() {
        return List.of(
                (number) -> number.setPhoneNum(Integer.parseInt(phoneNumber.getText())),
                (number) -> number.setType(phoneNumberType.getValue().getValue())
        );
    }

    @Override
    public void readInitialValue(PhoneNumber initialValue) {
        phoneNumber.setText(Optional.ofNullable(initialValue.getPhoneNum()).map(v -> "" + v).orElse(""));
        Optional.ofNullable(initialValue.getType()).ifPresentOrElse((v) -> {
            phoneNumberType.getItems().stream().filter(pair -> pair.getValue() == v).findFirst().ifPresent(match -> {
                phoneNumberType.getSelectionModel().select(match);
            });
        }, () -> phoneNumberType.getSelectionModel().clearSelection());
    }

    @Override
    public void clearComponents() {
        phoneNumber.clear();
        phoneNumberType.getSelectionModel().clearSelection();
    }

    @Override
    protected Supplier<PhoneNumber> submitObjectCreationStrategy() {
        return PhoneNumber::new;
    }
}
