package com.github.sjlian014.jlmsclient.restclient;

import com.github.sjlian014.jlmsclient.Properties;
import com.github.sjlian014.jlmsclient.exception.UnfulfilledRequestException;
import com.github.sjlian014.jlmsclient.model.Major;
import com.github.sjlian014.jlmsclient.model.Student;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MajorClient extends RestClient<Major> {
    public MajorClient() {
        super(Properties.RestClient.MAJOR_ENDPOINT_PATH, MajorSerializationEngine.getInstance());
    }
}
