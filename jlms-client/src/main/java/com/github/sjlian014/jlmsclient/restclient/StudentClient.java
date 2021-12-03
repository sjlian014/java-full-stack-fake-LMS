package com.github.sjlian014.jlmsclient.restclient;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sjlian014.jlmsclient.Properties;
import com.github.sjlian014.jlmsclient.model.Student;

/**
 * StudentConsumer
 *
 *
 *
 */
public class StudentClient extends BasicRestClient {

    public final String path = Properties.RestClient.STUDENT_ENDPOINT_PATH;
    public final URI uri;
    private final ObjectMapper mapper;

    public StudentClient() {
        super();
        mapper = new ObjectMapper();
        uri = URI.create(serverUrl + path);
    }

    public List<Student> getStudents() {
        var request = HttpRequest.newBuilder().uri(uri).GET().build();

        HttpResponse<String> response = null;
        try {
            response = backend.sendAsync(request, BodyHandlers.ofString()).get();
            var results = mapper.readValue(response.body(), new TypeReference<List<Student>>(){});
            return results;
        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            // scream and die loudly
            e.printStackTrace();
            System.out.println("[ERROR] failed to get/parse response! See stack trace for more info.");
            System.exit(10);
        }
        return null;
    }

    public void postStudents(Student student) {
        var request = HttpRequest
            .newBuilder()
            .uri(uri)
            .headers("Content-Type", "application/json;charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofString(studentToJsonString(student)))
            .build();

        HttpResponse<String> response = null;
        try {
            response = backend.sendAsync(request, BodyHandlers.ofString()).get();
            validateIfRequestIsFulfilled(response);
        } catch (InterruptedException | ExecutionException e) {
            // scream and die loudly
            e.printStackTrace();
            System.out.println("[ERROR] failed to get/parse response! See stack trace for more info.");
            System.exit(10);
        }

    }

    public void validateIfRequestIsFulfilled(HttpResponse<String> response) {
            if(response.statusCode() > 299 || response.statusCode() < 200) {
                System.out.println(response.body());
                throw new RuntimeException("a request was not fulfilled!");
            }
    }
    public String studentToJsonString(Student student) {
        try {
            return mapper.writeValueAsString(student);
        } catch (JsonProcessingException e) {
            System.out.println("[ERROR] failed to serialize student object.");
            e.printStackTrace();
            System.exit(10);
        }
        return null;
    }

}
