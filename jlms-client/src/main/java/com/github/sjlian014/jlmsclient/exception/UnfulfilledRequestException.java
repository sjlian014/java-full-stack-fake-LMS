package com.github.sjlian014.jlmsclient.exception;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class UnfulfilledRequestException extends Exception {
    final HttpRequest request;
    final HttpResponse<String> response;
    final Optional<String> requestBody;

    public UnfulfilledRequestException(String errMsg, HttpRequest request, HttpResponse<String> response, String requestBody) {
        super(errMsg);
        this.request = request;
        this.response = response;
        this.requestBody = Optional.ofNullable(requestBody);
    }

    public String prettyMessage() {
        String template = """
                        Request: %s
                        body: 
                        
                        %s
                        
                        Response: %s
                        headers: %s
                        body:
                        
                        %s
                        
                        Internal Message:
                        
                        %s
                        
                        """;

        return template.formatted(
                request, requestBody.orElse("no body (expected)"), // could be null
                response, response.headers().map().toString(), response.body(),
                getMessage()
        );
    }

}
