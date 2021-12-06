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

    public List<Student> getStudents() {
        var request = HttpRequest.newBuilder().uri(uri).GET().build();

        try {
            HttpResponse<String> response = backend.send(request, BodyHandlers.ofString());
            return serializationEngine.deserializeMany(response.body());
        } catch (InterruptedException | IOException e) {
            handleException(e);
        }
        return null;
    }

    public Student postStudent(Student student) {
        var request = HttpRequest
            .newBuilder()
            .uri(uri)
            .headers("Content-Type", "application/json;charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofString(serializationEngine.serializeOne(student)))
            .build();

        try {
            HttpResponse<String> response = backend.sendAsync(request, BodyHandlers.ofString()).get();
            validateIfRequestIsFulfilled(response);
            return serializationEngine.deserializeOne(response.body());
        } catch (InterruptedException | ExecutionException e) {
            handleException(e);
        }

        return null;
    }

    private void handleException(Exception e) {
        // TODO proper exception handling
        // scream and die loudly
        e.printStackTrace();
        System.out.println("[ERROR] failed to get/parse response! See stack trace for more info.");
        System.exit(10);
    }

    public void validateIfRequestIsFulfilled(HttpResponse<String> response) {
            if(response.statusCode() > 299 || response.statusCode() < 200) {
                System.out.println(response.body());
                throw new RuntimeException("a request was not fulfilled!");
            }
    }

}
