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

    public List<Student> getStudents() throws UnfulfilledRequestException {
        var request = HttpRequest.newBuilder().uri(uri).GET().build();

        try {
            HttpResponse<String> response = backend.send(request, BodyHandlers.ofString());
            validateIfRequestIsFulfilled(request, response, null);
            return serializationEngine.deserializeMany(response.body());
        } catch (InterruptedException | IOException e) {
            handleException(e);
        }
        return null;
    }

    public Student postStudent(Student student) throws UnfulfilledRequestException {
        String serialized = serializationEngine.serializeOne(student);
        var request = HttpRequest
            .newBuilder()
            .uri(uri)
            .headers("Content-Type", "application/json;charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofString(serialized))
            .build();

        try {
            HttpResponse<String> response = backend.sendAsync(request, BodyHandlers.ofString()).get();
            validateIfRequestIsFulfilled(request, response, serialized);
            return serializationEngine.deserializeOne(response.body());
        } catch (InterruptedException | ExecutionException e) {
            handleException(e);
        }

        return null;
    }

    private void handleException(Exception e) {
        // TODO proper exception handling
        // scream and die loudly
        System.out.println("[ERROR] failed to get/parse response from server! See stack trace for more info.");
        e.printStackTrace();
        System.exit(10);
    }

    public void validateIfRequestIsFulfilled(HttpRequest request, HttpResponse<String> response, String requestBody) throws UnfulfilledRequestException {
            if(response.statusCode() > 399 || response.statusCode() < 200) {
                System.out.println("[ERROR] unfulfilled request with the following response body: ");
                System.out.println(response.body());
                throw new UnfulfilledRequestException("a request was not fulfilled!", request, response, requestBody);
            }
    }

}
