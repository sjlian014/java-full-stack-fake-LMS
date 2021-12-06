package com.github.sjlian014.jlmsclient.controller;

import com.github.sjlian014.jlmsclient.Properties;
import com.github.sjlian014.jlmsclient.Util;
import com.github.sjlian014.jlmsclient.controller.form.ConfirmDialogBuilder;
import com.github.sjlian014.jlmsclient.controller.form.MailingAddressForm;
import com.github.sjlian014.jlmsclient.controller.form.SemesterAddressForm;
import com.github.sjlian014.jlmsclient.exception.InvalidDateException;
import com.github.sjlian014.jlmsclient.model.MailingAddress;
import com.github.sjlian014.jlmsclient.model.Semester;
import com.github.sjlian014.jlmsclient.model.Student;
import com.github.sjlian014.jlmsclient.restclient.StudentSerializationEngine;
import com.github.sjlian014.jlmsclient.service.StudentService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.util.*;
import java.util.function.Consumer;

public class MainController {

    private StudentService source;
    @FXML private ListView<Student> StudentListView;
    @FXML private SplitPane editorPane;
    @FXML private TextArea rawTextArea;
    @FXML private TextField editorFirstNameTF, editorLastNameTF, editorMiddleNameTF, editorDobTF, editorDoaTF;
    @FXML private Label editorIDLabel;
    @FXML private Label dobPromptLabel, doaPromptLabel;
    @FXML private Label mailingAddressLabel, semesterLabel;
    @FXML private Button editorModifyMailingAddressButton, editorModifySemesterButton;

    @FXML private  Label leftStatus;

    Editor editor;

    public void initialize() {
        source = new StudentService(StudentListView.itemsProperty());
        editor = new Editor();

        syncAllStudents();
    }

    @FXML
    public void syncAllStudents() {
        StudentListView.setEditable(false);
        leftStatus.setText("waiting for response from server[%s]...".formatted(Properties.RestClient.SERVER_URL));
        editor.destroyEditor();
        source.asyncGetAllStudents(()->{StudentListView.setEditable(false); leftStatus.setText("done.");});
    }

    @FXML
    private void editNewStudent() {
        editor.setupEditor(new Student());
    }

    @FXML
    private void editorToggleReadOnly() {
        editor.toggleReadOnly();
    }

    @FXML
    // post a new student to the server
    private void postStudent() {
        // generate dialog window for confirmation
        boolean input = new ConfirmDialogBuilder()
                .setTitle("Confirm Action")
                .setContentText("do you really want to save the student at its current state to server?")
                .buildAndRun();

        if(input) {
            editor.saveChangesLocally();
            source.asyncPostStudent(editor.getCurrentStateOfStudent(), (_ignore) -> {});
            syncAllStudents();
        }
    }

    @FXML
    private void editorModifyMailingAddress() {
        editor.showMailingAddresssDialog();
    }

    @FXML
    private void editorModifySemester() {
        editor.showSemesterDialog();
    }

    // a collection of methods used for the editor pane and the element within
    private class Editor {

        // FXML elements
        private final SplitPane container;
        private final TextField firstName, lastName, middleName, dob, doa;
        private final Label dobPrompt, doaPrompt;
        private final Label id;
        private final Label mailingAddrView, semesterView;
        private final TextArea rawView;
        private final Button mailingAddr, semester;

        // since we cannot make existing nodes implement interfaces, the next best thing is to have a map to
        // function that sorta acts like a common interface
        // operationMap: editable -> function that takes that editable and specifies how it modifies the student
        private final HashMap<Control, Consumer<Control>> operationMap;
        // updateMap: viewable -> function that takes that viewable and specifies how to update it
        private final HashMap<Control, Consumer<Control>> updateMap;
        private final Consumer<Control> NO_OP = (_ignore) -> {}; // TODO eliminate all occurrence of NO_OP

        // editor state
        private Student student;
        private boolean readOnly;
        private final Timeline autoSaveTimer;

        public Editor() {
            this.container = editorPane;
            container.setVisible(false);

            // ---------------- immutables ------------------
            this.id = editorIDLabel; // the id is never going to change for a given student

            // ---------------- editables -------------------
            // editable text components
            this.firstName = editorFirstNameTF;
            this.lastName = editorLastNameTF;
            this.middleName = editorMiddleNameTF;
            this.dob = editorDobTF;
            this.doa = editorDoaTF;
            // clickables
            this.mailingAddr = editorModifyMailingAddressButton;
            this.semester = editorModifySemesterButton;


            // ---------------- viewables -------------------
            // object property labels
            this.mailingAddrView = mailingAddressLabel;
            this.semesterView = semesterLabel;
            this.rawView = rawTextArea;

            // prompts
            this.doaPrompt = doaPromptLabel;
            this.dobPrompt = dobPromptLabel;

            operationMap = new HashMap<>();
            initOperationMap();
            updateMap = new HashMap<>();
            initUpdateMap();

            // interval timer to update viewables (with polling)
            autoSaveTimer = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    saveChangesLocally();
                                    updateViewableComponents();
                                }
                            }));
            autoSaveTimer.setCycleCount(Timeline.INDEFINITE);
        }

        private void initOperationMap() {
            operationMap.put(firstName, (self) -> student.setFirstName(((TextField)self).getText()));
            operationMap.put(lastName, (self) -> student.setLastName(((TextField)self).getText()));
            operationMap.put(middleName, (self) -> student.setMiddleName(((TextField)self).getText()));
            operationMap.put(doa, (self) -> {
                String input = ((TextField)self).getText();
                if(input.isEmpty()) {
                    doaPrompt.setText("");
                    return;
                }
                try {
                    student.setDoa(Util.str2date(input));
                    doaPrompt.setText("");
                } catch (InvalidDateException e) {
                    doaPrompt.setText("invalid doa!");
                }
            });
            operationMap.put(dob, (self) -> {
                String input = ((TextField)self).getText();
                if(input.isEmpty()) {
                    dobPrompt.setText("");
                    return;
                }
                try {
                    student.setDob(Util.str2date(input));
                    dobPrompt.setText("");
                } catch (InvalidDateException e) {
                    dobPrompt.setText("invalid dob!");
                }
            });
            operationMap.put(mailingAddr, NO_OP); // handled by listener
            operationMap.put(semester, NO_OP); // handled by listener
        }

        private void initUpdateMap() {
            updateMap.put(mailingAddrView, (self) -> {
                final MailingAddress tmp = student.getMailingAddress();
                ((Label)self).setText((tmp != null) ? tmp.toString() : "");
            });
            updateMap.put(semesterView, (self) -> {
                final Semester tmp = student.getStartSemester();
                ((Label)self).setText((tmp != null) ? tmp.toString() : "");
            });
            updateMap.put(rawView,
                    (self) -> rawView.setText(StudentSerializationEngine.getInstance().serializeOne(student)));
            updateMap.put(doaPrompt, NO_OP); // event based
            updateMap.put(dobPrompt, NO_OP); // event based
        }

        public void setupEditor(Student stu2e) {
            this.student = stu2e;

            id.setText((stu2e.getId() != null) ? String.valueOf(stu2e.getId()) : "TBA");
            updateViewableComponents();

            this.container.setVisible(true);

            autoSaveTimer.play();

            setReadOnly();
        }

        public boolean destroyEditor() {
            return destroyEditor(true);
        }

        public boolean destroyEditor(boolean skipConfirmDialog) {
            if(skipConfirmDialog);

            autoSaveTimer.stop();

            this.student = null;
            this.operationMap.keySet().forEach((component) -> {
                if(component instanceof TextInputControl) ((TextInputControl) component).clear();
            });

            this.container.setVisible(false);

            rawTextArea.clear();

            return true;
        }

        public void setReadOnly() {
            readOnly = true;
            this.operationMap.keySet().forEach((component) -> component.setDisable(readOnly));
        }

        public void unsetReadOnly() {
            readOnly = false;
            this.operationMap.keySet().forEach((component) -> component.setDisable(readOnly));
        }

        public void toggleReadOnly() {
            if(readOnly) unsetReadOnly();
            else setReadOnly();
        }

        public void saveChangesLocally() {
            this.operationMap.forEach((component, operation) -> operation.accept(component));
        }

        private void updateViewableComponents() {
            updateMap.forEach((component, operation) -> operation.accept(component));
        }


        public Student getCurrentStateOfStudent() {
            return student;
        }

        public void showMailingAddresssDialog() {
            Optional<MailingAddress> input = new MailingAddressForm().showAndWait();

            input.ifPresent((addr) -> {
                mailingAddrView.setText(addr.toString());
                student.setMailingAddress(addr);
            });
        }

        public void showSemesterDialog() {
            Optional<Semester> input = new SemesterAddressForm().showAndWait();

            input.ifPresent((semester) -> {
                semesterView.setText(semester.toString());
                student.setStartSemester(semester);
            });
        }
    }

}
