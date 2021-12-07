package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.Util.ChoiceBoxUtil;
import com.github.sjlian014.jlmsclient.model.PhoneNumber;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.List;
import java.util.function.Consumer;

public class PhoneNumberForm extends BasicForm<PhoneNumber> {

    private final TextField phoneNumber;
    private final ChoiceBox<Pair<String, PhoneNumber.PhoneNumberType>> phoneNumberType;

    public PhoneNumberForm() {
        super("Modify Phone Number", PhoneNumber::new);

        phoneNumber = new TextField();
        phoneNumber.setPromptText("phone number");
        phoneNumberType = ChoiceBoxUtil.mapEnum(PhoneNumber.PhoneNumberType.values());

        addRow(phoneNumber, "Phone Number: ");
        addRow(phoneNumberType, "Phone Number Type: ");
    }

    @Override
    List<Consumer<PhoneNumber>> submitConvertorTasks() {
        return List.of(
                (number) -> number.setPhoneNum(Integer.parseInt(phoneNumber.getText())),
                (number) -> number.setType(phoneNumberType.getValue().getValue())
        );
    }
}
