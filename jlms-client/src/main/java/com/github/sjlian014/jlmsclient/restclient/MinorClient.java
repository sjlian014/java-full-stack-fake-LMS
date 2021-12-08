package com.github.sjlian014.jlmsclient.restclient;

import com.github.sjlian014.jlmsclient.Properties;
import com.github.sjlian014.jlmsclient.model.Minor;

public class MinorClient extends RestClient<Minor> {

    public MinorClient() {
        super(Properties.RestClient.MINOR_ENDPOINT_PATH, MinorSerializationEngine.getInstance());
    }
}
