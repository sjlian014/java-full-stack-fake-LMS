package com.github.sjlian014.jlmsclient.restclient;

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

    public final static String generateConnStr(String domain, int port) {
        return "http://" + domain + ":" + port;
    }

}
