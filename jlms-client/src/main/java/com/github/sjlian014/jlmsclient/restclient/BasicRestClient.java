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

import com.github.sjlian014.jlmsclient.Properties;

public abstract class BasicRestClient {

    // private static RestClient defaultClient = new RestClient();
    protected final String serverUrl; // cached server url
    protected final Version httpVersion;
    protected final Duration timeout;
    protected HttpClient backend;

    protected BasicRestClient(final String serverDomain, final int port, final HttpClient.Version httpVersion, Duration timeoutDuration) {
        this.httpVersion = httpVersion;
        this.timeout = timeoutDuration;
        serverUrl = generateConnStr(serverDomain, port);
        backend = HttpClient.newBuilder().version(httpVersion).connectTimeout(timeoutDuration).build();
    }

    protected BasicRestClient() {
        this.httpVersion = Properties.RestClient.HTTP_VERSION;
        this.timeout = Properties.RestClient.TIMEOUT_DURATION;
        serverUrl = Properties.RestClient.SERVER_URL;
        backend = HttpClient.newBuilder().version(httpVersion).connectTimeout(timeout).build();
    }

    // public String getResponse(List<String> path) {
    //     final URI uri = URI.create(path.stream().reduce(serverUrl, (acc, token) -> acc + "/" + token));
    //     System.out.println(uri.toString());
    //     HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
    //     try {
    //         HttpResponse<String> response = backend.sendAsync(request, BodyHandlers.ofString()).get();
    //         return response.body();
    //     } catch (InterruptedException | ExecutionException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // public static RestClient getDefaultClient() {
    //     return defaultClient;
    // }

    private static String generateConnStr(String domain, int port) {
        return "http://" + domain + ":" + port;
    }

}
