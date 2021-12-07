package com.github.sjlian014.jlmsclient.Util;

import com.github.sjlian014.jlmsclient.exception.InvalidDateException;
import com.github.sjlian014.jlmsclient.model.Student;
import com.github.sjlian014.jlmsclient.restclient.StudentSerializationEngine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Util {
    private Util() {}

    public static LocalDate str2date(String source) throws InvalidDateException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            return LocalDate.parse(source, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("\"%s\" not a valid date!".formatted(source));
        }

    }

    public static String date2str(LocalDate source) {
        return source.format(DateTimeFormatter.ofPattern("M/d/uuuu"));
    }

    // clone a student using SerializationEngine, guaranteed deep copy.
    public static Student cloneStudent(Student stu2c) {
        var engine = StudentSerializationEngine.getInstance();

        return engine.deserializeOne(engine.serializeOne(stu2c));
    }

}
