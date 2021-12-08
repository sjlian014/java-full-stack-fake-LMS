package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.Util.ChoiceBoxUtil;
import com.github.sjlian014.jlmsclient.model.Semester;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class SemesterForm extends BasicForm<Semester> {

    private final TextField year;
    private final ChoiceBox<Pair<String, Semester.SemesterType>> semesterType;

    public SemesterForm() {
        super("Modify Mailing Address", Semester::new);

        year = new TextField();
        year.setPromptText("year");

        semesterType = ChoiceBoxUtil.mapEnum(Semester.SemesterType.class);

        // add components to dialog
        addRow(year, "Year: ");
        addRow(semesterType, "Semester Type: ");
    }

    @Override
    List<Consumer<Semester>> submitConvertorTasks() {
        return List.of(
                (semester) -> semester.setYear(Integer.parseInt(year.getText())),
                (semester) -> semester.setSemester(semesterType.getValue().getValue())
        );
    }

    @Override
    public void readInitialValue(Semester initialValue) {
        year.setText(Optional.ofNullable(initialValue.getYear()).map(v -> "" + v).orElse(""));
        Optional.ofNullable(initialValue.getSemester()).ifPresentOrElse((v) -> {
            semesterType.getItems().stream().filter(pair -> pair.getValue() == v).findFirst().ifPresent(match -> {
                semesterType.getSelectionModel().select(match);
            });
        }, () -> semesterType.getSelectionModel().clearSelection());
    }

    @Override
    public void clearComponents() {
        year.clear();
        semesterType.getSelectionModel().clearSelection();
    }
}
