package com.github.sjlian014.jlmsclient.controller;

import com.github.sjlian014.jlmsclient.model.Student;
import com.github.sjlian014.jlmsclient.restclient.StudentClient;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * MainController
 */
public class MainController {

    private StudentClient source;
    @FXML
    private ListView<Student> StudentListView;

    public void initialize() {
        source = new StudentClient();
        StudentListView.getItems().addAll(source.getStudents());
    }
}
