package com.github.sjlian014.jlmsclient;

import java.net.http.HttpClient.Version;
import java.time.Duration;

/**
 * Properties
 *
 * public namespace for sahred constant variables
 *
 */
public final class Properties {

    // subnamespace for restclient package
    public static final class RestClient {
        public static final String SERVER_URL = "http://localhost:8080";
        public static final Version HTTP_VERSION = Version.HTTP_1_1;
        public static final Duration TIMEOUT_DURATION = Duration.ofSeconds(5);

        public static final String STUDENT_ENDPOINT_PATH = "/students";
    }
}
