package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.model.Semester;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SemesterAddressForm extends BasicForm<Semester> {

    private final TextField year;
    private final ChoiceBox<Pair<String, Semester.SemesterType>> semesterType;

    public SemesterAddressForm() {
        super("Modify Mailing Address");

        year = new TextField();
        year.setPromptText("year");

        semesterType = new ChoiceBox<>();
        semesterType.getItems().addAll(
                Arrays.stream(Semester.SemesterType.values())
                        .map(v -> new Pair<>(v.name(), v))
                        .collect(Collectors.toList())
        );
        semesterType.setConverter(new StringConverter<>() {
            @Override public String toString(Pair<String, Semester.SemesterType> pair) {
                return (pair != null) ? pair.getKey() : "";
            }
            @Override public Pair<String, Semester.SemesterType> fromString(String s) {
                return null;
            }
        });

        // add components to dialog
        addRow(year, "Year: ");
        addRow(semesterType, "Semester Type: ");


        // convert the result to a MailingAddress when the confirm button is clicked.
        this.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                Semester tmp = new Semester();
                boolean encounteredError = countExceptions(List.of(() -> tmp.setYear(Integer.parseInt(year.getText())),
                        () -> tmp.setSemester(semesterType.getValue().getValue())));

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
