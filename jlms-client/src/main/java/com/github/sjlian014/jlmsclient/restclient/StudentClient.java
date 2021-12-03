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

    public StudentClient() {
        super();
        uri = URI.create(serverUrl + path);
    }

    public List<Student> getStudents() {
        System.out.println(uri.toString());
        var request = buildRequest();
        var objectMapper = new ObjectMapper();
        HttpResponse<String> response = null;
        try {
            response = backend.sendAsync(request, BodyHandlers.ofString()).get();
            var results = objectMapper.readValue(response.body(), new TypeReference<List<Student>>(){});
            return results;
        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            // scream and die loudly
            e.printStackTrace();
            System.out.println("[ERROR] failed to get/parse response! See stack trace for more info.");
            System.exit(1);
        }
        return null;
    }

    private HttpRequest buildRequest() {
        return HttpRequest.newBuilder().uri(uri).build();
    }

}
