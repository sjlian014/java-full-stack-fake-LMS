package com.github.sjlian014.jlmsclient.Util;

import javafx.scene.control.ChoiceBox;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ChoiceBoxUtil {

    public static <E> ChoiceBox<Pair<String, E>> mapEnum(E[] instances) {
        ChoiceBox<Pair<String, E>> choiceBox = new ChoiceBox<>();
        mapEnum(instances, choiceBox);
        return choiceBox;
    }

    public static <E> void mapEnum(E[] instances, ChoiceBox<Pair<String, E>> target) {
        target.getItems().addAll(Arrays.stream(instances)
                .map((v) -> new Pair<>(v.toString(), v))
                .collect(Collectors.toList()));
        target.setConverter(getCommonPairConverter());
    }

    public static <T> StringConverter<Pair<String, T>> getCommonPairConverter(){
        return new StringConverter<>() {
            @Override
            public String toString(Pair<String, T> pair) {
                return (pair != null) ? pair.getKey() : "";
            }

            @Override
            public Pair<String, T> fromString(String s) {
                return null;
            }
        };
    }

}
