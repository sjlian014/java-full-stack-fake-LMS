package com.github.sjlian014.jlmsclient.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.sjlian014.jlmsclient.model.Student;

import java.util.List;

public abstract class SerializationEngine<T> {

    protected static final ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    public abstract T deserializeOne(String json);

    public abstract List<T> deserializeMany(String json);

    public abstract String serializeOne(T obj);

    public abstract String serializeMany(List<T> obj);

    protected void handleException(Exception e) {
        System.out.println("[ERROR] failed to serialize/deserialize.");
        e.printStackTrace();
        System.exit(10);
    }

}
