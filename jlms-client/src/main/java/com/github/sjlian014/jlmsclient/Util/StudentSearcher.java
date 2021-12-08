package com.github.sjlian014.jlmsclient.Util;

import com.github.sjlian014.jlmsclient.model.EmailAddress;
import com.github.sjlian014.jlmsclient.model.PhoneNumber;
import com.github.sjlian014.jlmsclient.model.Student;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentSearcher {

    private StudentSearcher() {
    }

    // provide the interface for convenient method Optional<String> get(String fieldName, Student student), otherwise the type of lookupTable could just be a hashTable
    private static abstract class FuzzyLookupTable extends HashMap<List<String>, Function<Student, String>> {
        abstract public Optional<String> get(String fieldName, Student student);
    }

    public static FuzzyLookupTable lookupTable = new FuzzyLookupTable() {// acceptableNames -> data of the field
        { // init block
            this.put(toAcceptableNames(List.of("first name", "forename", "given name")), student -> {
                return student.getFirstName();
            });
            this.put(toAcceptableNames(List.of("last name", "family name", "surname")), student -> {
                return student.getLastName();
            });
            this.put(toAcceptableNames(List.of("middle name")), student -> {
                return student.getMiddleName();
            });
            this.put(toAcceptableNames(List.of("doa", "date of acceptance", "acceptance date", "acceptance")), student -> {
                return Util.date2str(student.getDoa());
            });
            this.put(toAcceptableNames(List.of("dob", "date of birth", "birth date", "birthday", "acceptance")), student -> {
                return Util.date2str(student.getDob());
            });
            this.put(toAcceptableNames(List.of("street", "mailing address street", "mailingaddress.street")), student -> {
                return Util.toNull(() -> student.getMailingAddress().getStreet());
            });
            this.put(toAcceptableNames(List.of("city", "mailing address city", "mailingaddress.city")), student -> {
                return Util.toNull(() -> student.getMailingAddress().getCity());
            });
            this.put(toAcceptableNames(List.of("state", "mailing address state", "mailingaddress.state")), student -> {
                return Util.toNull(() -> student.getMailingAddress().getState());
            });
            this.put(toAcceptableNames(List.of("zip", "mailing address zip", "mailingaddress.zip")), student -> {
                return Util.toNull(() -> "" + student.getMailingAddress().getZip());
            });
            this.put(toAcceptableNames(List.of("semester", "start semester", "starting semester")), student -> {
                return Util.toNull(() -> student.getStartSemester().toString());
            });
            this.put(toAcceptableNames(List.of("status", "enrollment status", "current status", "current enrollment status")), student -> {
                return Util.toNull(() -> student.getCurrentStatus().toString());
            });
            this.put(toAcceptableNames(List.of("email", "emails", "email address", "email addresses")), student -> {
                return Util.toNull(() -> student.getEmailAddresses().stream().map(EmailAddress::geteAddr).collect(Collectors.joining("\n")));
            });
            this.put(toAcceptableNames(List.of("phone number", "number")), student -> {
                return Util.toNull(() -> student.getPhoneNumbers().stream().map(PhoneNumber::getPhoneNum).map(Object::toString).collect(Collectors.joining("\n")));
            });
            this.put(toAcceptableNames(List.of("major")), student -> {
                return Util.toNull(() -> student.getMajor().toString());
            });
            this.put(toAcceptableNames(List.of("minor")), student -> {
                return Util.toNull(() -> student.getMinor().toString());
            });
        }

        public Optional<String> get(String fieldName, Student student) {
            final String lowerCaseFieldName = fieldName.toLowerCase();
            return keySet()
                    .stream()
                    .filter(acceptableNames -> acceptableNames.contains(lowerCaseFieldName))
                    .findAny()
                    .map(key -> this.get(key).apply(student));
        }

        // listOfFieldNamesDelimitedByWhiteSpace -> combinationsAcceptableNames
        private static List<String> toAcceptableNames(List<String> fieldName) {
            ArrayList<String> acceptableNames = new ArrayList();
            return fieldName.stream()
                    .map(String::toLowerCase) // conver to lowercase
                    .map(name -> name.split(" ")) // split into tokens
                    .mapMulti((tokens, intoThisStream) -> { // map tokens to the the four combinations
                        intoThisStream.accept(String.join(" ", tokens));// combination 1: first second
                        intoThisStream.accept(String.join("-", tokens));// combination 2: first-second
                        intoThisStream.accept(String.join("", tokens));// combination 3: firstsecond
                        intoThisStream.accept(String.join("_", tokens));// combination 4: first_second
                    }).map(Object::toString).toList();

        }
    };

    public final static int FUZZY_FIND_RATIO_THRESHOLD = 80;

    public static boolean matchStudent(Student student, String searchTerm) {
        final var identifiableInfo = List.of(
                Optional.ofNullable(student.getFirstName()),
                Optional.ofNullable(student.getLastName()),
                Optional.ofNullable(student.getMiddleName()),
                Optional.ofNullable(student.getId()).map((value) -> value + "")
        );


        if (searchTerm.isBlank()) return true; // show all

        // ------- preprocess --------
        searchTerm = searchTerm.stripIndent(); // strip leading and trailing white space

        // ------- matching ----------
        switch (searchTerm.charAt(0)) {
            case '^': // exact match against student's identifiable information
                final String finalSearchTerm = searchTerm.substring(1).stripIndent();
                System.out.println(identifiableInfo.stream()
                        .filter(Optional::isPresent).count());
                if (searchTerm.isBlank()) return false;
                return identifiableInfo.stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .anyMatch(info -> info.equals(finalSearchTerm));
            case '@': // fuzzy match against a particular field
                int split = searchTerm.indexOf('|'); // use pipe as delimiter
                if (split == -1) { // delimiter not found
                    System.out.println("[WARNING] invalid search syntax, use '|' to delimit filed name " +
                            "and search term when using '@' symbol to match a string against a field");
                    return false;
                }
                final String fieldName = searchTerm.substring(1, split).stripIndent();
                final String queryString = searchTerm.substring(split + 1).stripIndent();
                if (queryString.isBlank()) return false;

                return lookupTable // create the table now lazily
                        .get(fieldName, student)
                        .map((value) -> FuzzySearch.partialRatio(queryString.toLowerCase(), value.toLowerCase()) > FUZZY_FIND_RATIO_THRESHOLD)
                        .orElse(false);
            default: // fuzzy match against student's toString() value
                final String lowerCaseStudent = student.toString().toLowerCase();
                final String lowerCaseSearchTerm = searchTerm.toLowerCase();

                return FuzzySearch.partialRatio(lowerCaseSearchTerm, lowerCaseStudent) > FUZZY_FIND_RATIO_THRESHOLD
                        || lowerCaseStudent.contains(lowerCaseSearchTerm);
        }

    }
}
