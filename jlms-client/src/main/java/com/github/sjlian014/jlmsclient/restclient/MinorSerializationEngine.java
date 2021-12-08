package com.github.sjlian014.jlmsclient.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.sjlian014.jlmsclient.model.Major;
import com.github.sjlian014.jlmsclient.model.Minor;

import java.util.List;

public class MinorSerializationEngine extends SerializationEngine<Minor> {

    private static final MinorSerializationEngine instance = new MinorSerializationEngine();

    private MinorSerializationEngine() {}

    public static MinorSerializationEngine getInstance() {
        return instance;
    }

    @Override
    public Minor deserializeOne(String json) {
        try {
            return mapper.readValue(json, Minor.class);
        } catch (JsonProcessingException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public List<Minor> deserializeMany(String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<Minor>>(){});
        } catch (JsonProcessingException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public String serializeOne(Minor minor) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(minor);
        } catch (JsonProcessingException e) {
            handleException(e);
        }
        return null;
    }

}
