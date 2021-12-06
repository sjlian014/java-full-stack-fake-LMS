package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.model.MailingAddress;
import javafx.scene.control.*;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MailingAddressForm extends BasicForm<MailingAddress> {

    private final TextField street;
    private final TextField city;
    private final TextField state;
    private final TextField zip;
    private final ChoiceBox<Pair<String, MailingAddress.AddrType>> addressType;

    public MailingAddressForm() {
        super("Modify Mailing Address");

        // initialize the fields
        street = new TextField();
        street.setPromptText("street");
        city = new TextField();
        city.setPromptText("city");
        state = new TextField();
        state.setPromptText("state");
        zip = new TextField();
        zip.setPromptText("zip");

        addressType = new ChoiceBox<>();
        addressType.getItems().addAll(
                Arrays.stream(MailingAddress.AddrType.values())
                        .map((v) -> new Pair<>(v.name(), v))
                        .collect(Collectors.toList()));
        addressType.setConverter(new StringConverter<>() {
            @Override
            public String toString(Pair<String, MailingAddress.AddrType> pair) {
                return (pair != null) ? pair.getKey() : "";
            }

            @Override
            public Pair<String, MailingAddress.AddrType> fromString(String string) {
                return null;
            }
        });

        // add components to dialog
        addRow(street, "Street: ");
        addRow(city, "City: ");
        addRow(state, "State: ");
        addRow(zip, "Zip: ");
        addRow(addressType, "Address Type:");

        // Convert the result to a MailingAddress when the confirm button is clicked.
        this.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                MailingAddress tmp = new MailingAddress();
                boolean encounteredError = countExceptions(List.of(() -> tmp.setStreet(street.getText()),
                        () -> tmp.setCity(city.getText()),
                        () -> tmp.setState(state.getText()),
                        () -> tmp.setZip(Integer.parseInt(zip.getText())),
                        () -> tmp.setType(addressType.getValue().getValue())
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
