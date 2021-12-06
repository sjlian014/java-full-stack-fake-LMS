package com.github.sjlian014.jlmsclient.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sjlian014.jlmsclient.model.Student;

import java.util.List;

public class StudentSerializationEngine extends SerializationEngine<Student> {

    private static final StudentSerializationEngine instance = new StudentSerializationEngine();

    private StudentSerializationEngine() {}

    public static StudentSerializationEngine getInstance() {
        return instance;
    }

    @Override
    public Student deserializeOne(String json) {
        try {
            return mapper.readValue(json, Student.class);
        } catch (JsonProcessingException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public List<Student> deserializeMany(String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<Student>>(){});
        } catch (JsonProcessingException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public String serializeOne(Student student) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);
        } catch (JsonProcessingException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public String serializeMany(List<Student> students) {
        // TODO implement serializeMany(List<Student> students)
        throw new UnsupportedOperationException("serializeMany(List<Student> students) is not implemented");
    }
}
