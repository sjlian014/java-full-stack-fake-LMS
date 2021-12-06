package com.github.sjlian014.jlmsclient;

import com.github.sjlian014.jlmsclient.exception.InvalidDateException;

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
        String dateFormat = "MM-dd-uuuu";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat)
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate date = null;
        try {
            date = LocalDate.parse(source, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("\"%s\" not a valid date!".formatted(source));
        }

        return date;
    }

}
