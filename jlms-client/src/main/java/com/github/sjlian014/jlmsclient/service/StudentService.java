package com.github.sjlian014.jlmsclient.service;

import com.github.sjlian014.jlmsclient.App;
import com.github.sjlian014.jlmsclient.model.Student;
import com.github.sjlian014.jlmsclient.restclient.StudentClient;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.function.Consumer;

/**
 * StudentService
 *
 * proxy to student client. provide asynchronous functionalities and caches the students since the last call to the server.
 *
 * generally the methods of this class will be wrappers to underlying functions of a StudentClient with additional
 * parameters that takes in callback functions to update the UI
 */
public class StudentService {

    private StudentClient source = new StudentClient();
    private ReadOnlyObjectProperty<ObservableList<Student>> students =
            new SimpleObjectProperty<>(FXCollections.observableArrayList());

    public StudentService(ObjectProperty<ObservableList<Student>> bindTarget) {
        bindTarget.bind(students);
    }

    public void asyncGetAllStudents(Runnable callback) {
        students.get().clear();
        Runnable getAllStudentTask = () -> {
            var tmp = source.getStudents();

            Platform.runLater(() -> {
                students.get().addAll(tmp);
                callback.run();
            });
        };

        App.executor.execute(getAllStudentTask);
    }

    public void asyncPostStudent(Student student, Consumer<Student> callbackWithReturnedStudent) {
        Runnable postStudentTask = () -> {
            var studentInServer = source.postStudent(student);

            Platform.runLater(() -> callbackWithReturnedStudent.accept(student));
        };

        App.executor.execute(postStudentTask);
    }

}
