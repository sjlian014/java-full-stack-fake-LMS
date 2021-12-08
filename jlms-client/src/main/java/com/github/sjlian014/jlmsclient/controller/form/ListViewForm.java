package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.model.Major;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.function.Supplier;

public abstract class ListViewForm<I, T> extends BasicForm<I, T> {

    protected BorderPane pane;
    protected ListView<T> list;

    public ListViewForm(String title) {
        super(title);
        this.setWidth(600);
        this.setHeight(800);

        pane = new BorderPane();
        pane.setPadding(new Insets(20, 20, 10, 10));


        this.getDialogPane().setContent(pane);
    }

}
