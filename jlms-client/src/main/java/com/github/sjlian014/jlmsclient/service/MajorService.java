package com.github.sjlian014.jlmsclient.service;

import com.github.sjlian014.jlmsclient.model.Major;
import com.github.sjlian014.jlmsclient.restclient.MajorClient;
import com.github.sjlian014.jlmsclient.restclient.RestClient;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

public class MajorService extends Service<Major> {

    public MajorService(ObjectProperty<ObservableList<Major>> bindTarget) {
        super(bindTarget, new MajorClient());
    }
}
