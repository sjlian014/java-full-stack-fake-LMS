package com.github.sjlian014.jlmsclient.controller;

import com.github.sjlian014.jlmsclient.Properties;
import com.github.sjlian014.jlmsclient.Util;
import com.github.sjlian014.jlmsclient.controller.form.*;
import com.github.sjlian014.jlmsclient.exception.InvalidDateException;
import com.github.sjlian014.jlmsclient.model.*;
import com.github.sjlian014.jlmsclient.model.Student.EnrollmentStatus;
import com.github.sjlian014.jlmsclient.restclient.StudentSerializationEngine;
import com.github.sjlian014.jlmsclient.service.StudentService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MainController {

    private StudentService source;
    @FXML private ListView<Student> studentListView;
    @FXML private SplitPane editorPane;
    @FXML private TextArea rawTextArea;
    @FXML private TextField editorFirstNameTF, editorLastNameTF, editorMiddleNameTF, editorDobTF, editorDoaTF;
    @FXML private Label editorIDLabel;
    @FXML private Label dobPromptLabel, doaPromptLabel;
    @FXML private Label mailingAddressLabel, semesterLabel;
    @FXML private Button editorModifyMailingAddressButton, editorModifySemesterButton, editorModifyEmailAddressButton,
            editorAddEmailAddressButton, editorModifyPhoneNumberButton, editorAddPhoneNumberButton;
    @FXML private ChoiceBox<Pair<String, EnrollmentStatus>> editorEnrollmentCB;
    @FXML private ChoiceBox<Pair<String, EmailAddress>> editorEmailAddressesCB;
    @FXML private ChoiceBox<Pair<String, PhoneNumber>> editorPhoneNumbersCB;

    @FXML private  Label leftStatus;

    Editor editor;

    public void initialize() {
        source = new StudentService(studentListView.itemsProperty());
        editor = new Editor();

        studentListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> ov, Student oldValue, Student newValue) {
                if(ov != null && ov.getValue() != null) {
                    editor.setupEditor(ov.getValue());
                }
            }
        });

        syncAllStudents();
    }

    @FXML
    public void syncAllStudents() {
        studentListView.setEditable(false);
        leftStatus.setText("waiting for response from server[%s]...".formatted(Properties.RestClient.SERVER_URL));
        editor.destroyEditor();
        source.asyncGetAllStudents(()->{
            studentListView.setEditable(false); leftStatus.setText("done.");});
    }

    @FXML
    private void editNewStudent() {
        studentListView.getSelectionModel().clearSelection();
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
        editor.showMailingAddresssForm();
    }

    @FXML
    private void editorModifySemester() {
        editor.showSemesterForm();
    }

    @FXML
    private void editorAddEmailAddress() {
        editor.showEmailAddressFormNew();
    }

    @FXML
    private void editorModifyEmailAddress() {
        editor.showEmailAddressFormReplace();
    }

    @FXML
    private void editorModifyPhoneNumber() {
        editor.showPhoneNumberFormReplace();
    }

    @FXML
    private void editorAddPhoneNumber() {
        editor.showPhoneNumberFormNew();
    }

    // a collection of methods used for the editor pane and the element within
    private class Editor {

        // FXML elements
        private final SplitPane container;
        private final TextField firstNameInput, lastNameInput, middleNameInput, dobInput, doaInput;
        private final Label dobPrompt, doaPrompt;
        private final Label idView;
        private final Label mailingAddrView, semesterView;
        private final TextArea rawView;
        private final Button mailingAddrMod, semesterMod, emailAddrAdd, emailAddrMod, phoneNumAdd, phoneNumMod;
        private final ChoiceBox<Pair<String, EnrollmentStatus>> enrollmentStatus;
        private final ChoiceBox<Pair<String, EmailAddress>> emailAddressView;
        private final ChoiceBox<Pair<String, PhoneNumber>> phoneNumberView;

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
            this.idView = editorIDLabel; // the id is never going to change for a given student

            // ---------------- editables -------------------
            // editable text components; they are also viewables
            this.firstNameInput = editorFirstNameTF;
            this.lastNameInput = editorLastNameTF;
            this.middleNameInput = editorMiddleNameTF;
            this.dobInput = editorDobTF;
            this.doaInput = editorDoaTF;

            // clickables
            this.enrollmentStatus = editorEnrollmentCB;
            initEnrollmentStatusChoiceBox();
            this.mailingAddrMod = editorModifyMailingAddressButton;
            this.semesterMod = editorModifySemesterButton;
            this.phoneNumAdd = editorAddPhoneNumberButton;
            this.phoneNumMod = editorModifyPhoneNumberButton;
            this.emailAddrAdd = editorAddEmailAddressButton;
            this.emailAddrMod = editorModifyEmailAddressButton;


            // ---------------- viewables -------------------
            // object property labels
            this.mailingAddrView = mailingAddressLabel;
            this.semesterView = semesterLabel;
            this.rawView = rawTextArea;

            // prompts
            this.doaPrompt = doaPromptLabel;
            this.dobPrompt = dobPromptLabel;

            // selectors
            this.emailAddressView = editorEmailAddressesCB;
            initEmailAddressChoiceBox();
            this.phoneNumberView = editorPhoneNumbersCB;
            initPhoneNumberChoiceBox();

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
                                    editor.applyUpdate(rawView);
                                }
                            }));
            autoSaveTimer.setCycleCount(Timeline.INDEFINITE);
        }

        private void initPhoneNumberChoiceBox() {
            phoneNumberView.setConverter(new StringConverter<>() {
                @Override
                public String toString(Pair<String, PhoneNumber> pair) {
                    return (pair != null) ? pair.getKey() : "";
                }

                @Override
                public Pair<String, PhoneNumber> fromString(String s) {
                    return null;
                }
            });
        }

        private void initEmailAddressChoiceBox() {
            emailAddressView.setConverter(new StringConverter<>() {
                @Override
                public String toString(Pair<String, EmailAddress> pair) {
                    return (pair != null) ? pair.getKey() : "";
                }

                @Override
                public Pair<String, EmailAddress> fromString(String s) {
                    return null;
                }
            });
            emailAddressView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pair<String, EmailAddress>>() {
                    @Override
                    public void changed(ObservableValue<? extends Pair<String, EmailAddress>> ov, Pair<String, EmailAddress> oldV, Pair<String, EmailAddress> newV) {
                        if(ov != null)
                            emailAddrMod.setDisable(false);
                        else
                            emailAddrMod.setDisable(true);
                    }
                }

            );
        }

        private void initEnrollmentStatusChoiceBox() {
            enrollmentStatus.getItems().addAll(
                    Arrays.stream(EnrollmentStatus.values())
                            .map((v) -> new Pair<>(v.name(), v))
                            .collect(Collectors.toList()));
            enrollmentStatus.setConverter(new StringConverter<>() {
                @Override
                public String toString(Pair<String, EnrollmentStatus> pair) {
                    return (pair != null) ? pair.getKey() : "";
                }

                @Override
                public Pair<String, EnrollmentStatus> fromString(String s) {
                    return null;
                }
            });
        }

        private void initOperationMap() {
            operationMap.put(firstNameInput, (self) -> student.setFirstName(((TextField)self).getText()));
            operationMap.put(lastNameInput, (self) -> student.setLastName(((TextField)self).getText()));
            operationMap.put(middleNameInput, (self) -> student.setMiddleName(((TextField)self).getText()));
            operationMap.put(doaInput, (self) -> {
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
            operationMap.put(dobInput, (self) -> {
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
            operationMap.put(mailingAddrMod, NO_OP); // handled by listener
            operationMap.put(semesterMod, NO_OP); // handled by listener
            operationMap.put(enrollmentStatus, (self) -> {
                var tmp = ((ChoiceBox<Pair<String, EnrollmentStatus>>) self).getValue();
                student.setCurrentStatus((tmp == null) ? null : tmp.getValue());
            });
            operationMap.put(emailAddrAdd, NO_OP); // handled by listener
            operationMap.put(emailAddrMod, NO_OP); // handled by listener
            operationMap.put(phoneNumAdd, NO_OP); // handled by listener
            operationMap.put(phoneNumMod, NO_OP); // handled by listener
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

            updateMap.put(phoneNumberView, (self) -> {
                var selfCasted = ((ChoiceBox<Pair<String, PhoneNumber>>) self);
                selfCasted.getItems().setAll(
                        (student.getPhoneNumbers() != null) ? student.getPhoneNumbers().stream()
                                .map((v) -> new Pair<>("%d [%s]".formatted(v.getPhoneNum(), v.getType()), v))
                                .collect(Collectors.toList()) : List.of());

                if(selfCasted.getItems().size() > 0) selfCasted.getSelectionModel().select(0);
            });
            updateMap.put(emailAddressView, (self) -> {
                var selfCasted = ((ChoiceBox<Pair<String, EmailAddress>>) self);
                selfCasted.getItems().setAll(
                        (student.getEmailAddresses() != null) ? student.getEmailAddresses().stream()
                                .map((v) -> new Pair<>("%s [%s]".formatted(v.geteAddr(), v.getType()), v))
                                .collect(Collectors.toList()) : List.of());
                if(selfCasted.getItems().size() > 0) selfCasted.getSelectionModel().select(0);
            });
            updateMap.put(firstNameInput, (self) -> {
                final String firstName = student.getFirstName();
                ((TextField) self).setText((firstName == null) ? "" : firstName);
            });
            updateMap.put(lastNameInput, (self) -> {
                final String lastName = student.getLastName();
                ((TextField) self).setText((lastName == null) ? "" : lastName);
            });
            updateMap.put(middleNameInput, (self) -> {
                final String middleName = student.getMiddleName();
                ((TextField) self).setText((middleName == null) ? "" : middleName);
            });
            updateMap.put(doaInput, (self) -> {
                final LocalDate doa = student.getDoa();
                ((TextField) self).setText((doa == null) ? "" : Util.date2str(doa));
            });
            updateMap.put(dobInput, (self) -> {
                final LocalDate dob = student.getDoa();
                ((TextField) self).setText((dob == null) ? "" : Util.date2str(dob));
            });
        }

        public void setupEditor(Student stu2e) {
            if(student != null) destroyEditor();

            boolean isNewStudent = (stu2e.getId() == null);
            this.student = stu2e;

            idView.setText((isNewStudent) ? "TBA" : String.valueOf(stu2e.getId()));
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

            this.updateMap.keySet().forEach((component) -> {
                if(component instanceof Label) ((Label) component).setText("");
                if(component instanceof ChoiceBox) ((ChoiceBox) component).getItems().clear();
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

        public void showMailingAddresssForm() {// event based update
            Optional<MailingAddress> input = new MailingAddressForm().showAndWait();

            input.ifPresent((addr) -> {
                student.setMailingAddress(addr);
                applyUpdate(mailingAddrView);
            });
        }

        public void showSemesterForm() {// event based update
            Optional<Semester> input = new SemesterAddressForm().showAndWait();

            input.ifPresent((semester) -> {
                student.setStartSemester(semester);
                applyUpdate(semesterView);
            });
        }

        public void showEmailAddressFormNew() {
            showEmailAddressForm(() -> student.getEmailAddresses());
        }

        public void showEmailAddressFormReplace() {
            if(emailAddressView.getSelectionModel().getSelectedItem() == null) {
                leftStatus.setText("no email selected.");
                return;
            }

            var email2r = emailAddressView.getValue().getValue();

            showEmailAddressForm(() ->
                student.getEmailAddresses()
                        .stream()
                        .filter((v) -> v != email2r)
                        .collect(Collectors.toList())
            );
        }

        private void showEmailAddressForm(Supplier<List<EmailAddress>> listBuildStrategy) {
            Optional<EmailAddress> input = new EmailAddressForm().showAndWait();

            input.ifPresent((email) -> {
                ArrayList<EmailAddress> tmp = new ArrayList<>();
                if(student.getEmailAddresses() != null)
                    tmp.addAll(listBuildStrategy.get());
                tmp.add(email);
                student.setEmailAddresses(tmp);
                applyUpdate(emailAddressView);
            });
        }

        private void showPhoneNumberForm(Supplier<List<PhoneNumber>> listBuildStrategy) {
            Optional<PhoneNumber> input = new PhoneNumberForm().showAndWait();

            input.ifPresent((phoneNumber) -> {
                ArrayList<PhoneNumber> tmp = new ArrayList<>();
                if(student.getPhoneNumbers() != null)
                    tmp.addAll(listBuildStrategy.get());
                tmp.add(phoneNumber);
                student.setPhoneNumbers(tmp);
                applyUpdate(phoneNumberView);
            });
        }

        public void showPhoneNumberFormReplace() {
            if(phoneNumberView.getSelectionModel().getSelectedItem() == null) {
                leftStatus.setText("no phone number selected.");
                return;
            }

            var num2r = phoneNumberView.getValue().getValue();

            showPhoneNumberForm(() ->
                    student.getPhoneNumbers()
                            .stream()
                            .filter((v) -> v != num2r)
                            .collect(Collectors.toList())
            );
        }

        public void showPhoneNumberFormNew() {
            showPhoneNumberForm(() -> student.getPhoneNumbers());
        }

        public void applyUpdate(Control component) {
            updateMap.get(component).accept(component);
        }

        public void applyOperation(Control component) {
            operationMap.get(component).accept(component);
        }

    }

}
