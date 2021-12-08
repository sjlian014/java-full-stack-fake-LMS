package com.github.sjlian014.jlmsclient.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.sjlian014.jlmsclient.exception.InternalDesignConstraintException;
import com.github.sjlian014.jlmsclient.model.Student;

import java.util.List;

public abstract class SerializationEngine<T> {

    protected static final ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    abstract public T deserializeOne(String json);

    abstract public List<T> deserializeMany(String json);

    abstract public String serializeOne(T obj);

    public String serializeMany(List<T> obj) {
        throw new InternalDesignConstraintException("serializeMany() is not implemented");
    }

    protected void handleException(Exception e) {
        System.out.println("[ERROR] failed to serialize/deserialize obj.");
        e.printStackTrace();
        System.exit(10);
    }

}
