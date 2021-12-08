package com.github.sjlian014.jlmsclient.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.sjlian014.jlmsclient.model.Major;
import com.github.sjlian014.jlmsclient.model.Student;

import java.util.List;

public class MajorSerializationEngine extends SerializationEngine<Major> {
    private static final MajorSerializationEngine instance = new MajorSerializationEngine();

    private MajorSerializationEngine() {}

    public static MajorSerializationEngine getInstance() {
        return instance;
    }

    @Override
    public Major deserializeOne(String json) {
        try {
            return mapper.readValue(json, Major.class);
        } catch (JsonProcessingException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public List<Major> deserializeMany(String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<Major>>(){});
        } catch (JsonProcessingException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public String serializeOne(Major major) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(major);
        } catch (JsonProcessingException e) {
            handleException(e);
        }
        return null;
    }

}
