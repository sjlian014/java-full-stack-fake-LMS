package com.github.sjlian014.jlmsclient.restclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sjlian014.jlmsclient.Properties;
import com.github.sjlian014.jlmsclient.exception.UnfulfilledRequestException;
import com.github.sjlian014.jlmsclient.model.Student;

public abstract class RestClient<T> {

    // private static RestClient defaultClient = new RestClient();
    protected final String serverUrl; // cached server url
    protected final Version httpVersion;
    protected final Duration timeout;
    protected HttpClient backend;
    protected final URI uri;
    protected final SerializationEngine<T> serializationEngine;

    protected RestClient(final String serverDomain, final int port, final HttpClient.Version httpVersion, Duration timeoutDuration, String pathToUri, SerializationEngine serializationEngine) {
        this.httpVersion = httpVersion;
        this.timeout = timeoutDuration;
        serverUrl = generateConnStr(serverDomain, port);
        this.uri = URI.create(serverUrl + pathToUri);
        this.serializationEngine = serializationEngine;
        backend = HttpClient.newBuilder().version(httpVersion).connectTimeout(timeoutDuration).build();
    }

    protected RestClient(String pathToUri, SerializationEngine serializationEngine) {
        this.httpVersion = Properties.RestClient.HTTP_VERSION;
        this.timeout = Properties.RestClient.TIMEOUT_DURATION;
        serverUrl = Properties.RestClient.SERVER_URL;
        this.uri = URI.create(serverUrl + pathToUri);
        this.serializationEngine = serializationEngine;
        backend = HttpClient.newBuilder().version(httpVersion).connectTimeout(timeout).build();
    }

    public SerializationEngine<T> getSerializationEngine() {
        return serializationEngine;
    }

    public List<T> fetchAll() throws UnfulfilledRequestException {
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

    public T postOne(T obj) throws UnfulfilledRequestException {
        String serialized = serializationEngine.serializeOne(obj);
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

    private final static String generateConnStr(String domain, int port) {
        return "http://" + domain + ":" + port;
    }

    public final String getRootEndPoint() {
        return uri.toString();
    }

    protected void handleException(Exception e) {
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
