package com.github.sjlian014.jlmsclient.restclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.sjlian014.jlmsclient.Properties;
import com.github.sjlian014.jlmsclient.exception.UnfulfilledRequestException;
import com.github.sjlian014.jlmsclient.model.Student;

/**
 * StudentConsumer
 *
 *
 *
 */
public class StudentClient extends RestClient<Student> {

    public StudentClient() {
        super(Properties.RestClient.STUDENT_ENDPOINT_PATH, StudentSerializationEngine.getInstance());
    }

}
