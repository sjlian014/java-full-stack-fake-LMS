package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.Util.ChoiceBoxUtil;
import com.github.sjlian014.jlmsclient.model.MailingAddress;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.util.List;
import java.util.function.Consumer;

public class MailingAddressForm extends BasicForm<MailingAddress> {

    private final TextField street;
    private final TextField city;
    private final TextField state;
    private final TextField zip;
    private final ChoiceBox<Pair<String, MailingAddress.AddrType>> addressType;

    public MailingAddressForm() {
        super("Modify Mailing Address", MailingAddress::new);

        street = new TextField();
        city = new TextField();
        state = new TextField();
        zip = new TextField();
        addressType = ChoiceBoxUtil.mapEnum(MailingAddress.AddrType.values());

        addRow(street, "Street: ");
        addRow(city, "City: ");
        addRow(state, "State: ");
        addRow(zip, "Zip: ");
        addRow(addressType, "Address Type:");
    }

    @Override
    List<Consumer<MailingAddress>> submitConvertorTasks() {
        return List.of(
                (address) -> address.setStreet(street.getText()),
                (address) -> address.setCity(city.getText()),
                (address) -> address.setState(state.getText()),
                (address) -> address.setZip(Integer.parseInt(zip.getText())),
                (address) -> address.setType(addressType.getValue().getValue())
        );
    }
}
