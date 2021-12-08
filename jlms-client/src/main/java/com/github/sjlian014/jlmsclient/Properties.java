package com.github.sjlian014.jlmsclient;

import java.net.http.HttpClient.Version;
import java.time.Duration;
import java.util.HashMap;

/**
 * Properties
 *
 * public namespace for shared constant variables
 *
 */
public final class Properties {

    private Properties() {} // prevent any instances to be created

    // sub-namespace for constants used by the restclient layer
    public static final class RestClient {
        public static final String SERVER_URL = "http://localhost:8080";
        public static final Version HTTP_VERSION = Version.HTTP_1_1;
        public static final Duration TIMEOUT_DURATION = Duration.ofSeconds(5);

        public static final String STUDENT_ENDPOINT_PATH = "/students";
        public static final String MAJOR_ENDPOINT_PATH = "/majors";
        public static final String MINOR_ENDPOINT_PATH = "/minors";
    }
}
