package com.github.sjlian014.jlmsclient.restclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RestClient {

    private static RestClient defaultClient = new RestClient();
    private final String serverUrl; // cached server url
    private final Version httpVersion;
    private final Duration timeout;
    private HttpClient conn;

    private RestClient(final String serverDomain, final int port, final HttpClient.Version httpVersion, Duration timeoutDuration) {
        this.httpVersion = httpVersion;
        this.timeout = timeoutDuration;
        serverUrl = generateConnStr(serverDomain, port);
        conn = HttpClient.newBuilder().version(httpVersion).connectTimeout(timeoutDuration).build();
    }

    private RestClient() {
        String domain = "localhost";
        int port = 8080;
        this.httpVersion = Version.HTTP_1_1;
        this.timeout = Duration.ofSeconds(3);
        serverUrl = generateConnStr(domain, port);
        conn = HttpClient.newBuilder().version(httpVersion).connectTimeout(timeout).build();
    }

    public String getResponse(List<String> path) {
        final URI uri = URI.create(path.stream().reduce(serverUrl, (acc, token) -> acc + "/" + token));
        System.out.println(uri.toString());
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        try {
            HttpResponse<String> response = conn.sendAsync(request, BodyHandlers.ofString()).get();
            return response.body();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RestClient getDefaultClient() {
        return defaultClient;
    }

    private static String generateConnStr(String domain, int port) {
        return "http://" + domain + ":" + port;
    }

}
