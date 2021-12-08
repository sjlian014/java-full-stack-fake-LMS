package com.github.sjlian014.jlmsclient.service;

import com.github.sjlian014.jlmsclient.model.Minor;
import com.github.sjlian014.jlmsclient.restclient.MinorClient;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

public class MinorService extends Service<Minor> {
    public MinorService(ObjectProperty<ObservableList<Minor>> bindTarget) {
        super(bindTarget, new MinorClient());
    }
}
