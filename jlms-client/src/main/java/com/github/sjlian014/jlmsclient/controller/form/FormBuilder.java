package com.github.sjlian014.jlmsclient.controller.form;

import com.github.sjlian014.jlmsclient.exception.InternalDesignConstraintException;

import java.nio.file.OpenOption;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FormBuilder<T, F extends BasicForm<T>> {

    private final F form;
    private Consumer setter; // this could either consumer <T> or List<T> depending on the field
    private boolean isListSetter = false;
    private ListSetterStrategy setStrategy;
    private T thing2r;
    private Optional<T> initialValue;
    public enum ListSetterStrategy {
        APPEND, REPLACE;
    }

    private Supplier getter; // this could either consumer <T> or List<T> depending on the field
    private Optional<Runnable> onSucceed, onFailure;


    private FormBuilder(F form) {
        this.form = form;
        onSucceed = Optional.empty();
        onFailure = Optional.empty();
        initialValue = Optional.empty();
    }

    public  static <T2, F2 extends BasicForm<T2>> FormBuilder<T2, F2> buildA(F2 form) {
        return new FormBuilder<T2, F2>(form);
    }

    public FormBuilder<T, F> useGetter(Supplier getter) {
        this.getter = getter;
        return this;
    }

    public FormBuilder<T, F> useSetter(Consumer<T> setter) {
        this.setter = setter;
        return this;
    };

    public FormBuilder<T, F> useListTypeSetter(Consumer<List<T>> listSetter) {
        this.isListSetter = true;
        this.setter = listSetter;
        return this;
    }

    public FormBuilder<T, F> useListTypeSetterStrategy(ListSetterStrategy strategy) {
        this.setStrategy = strategy;
        return this;
    }

    public FormBuilder<T, F> replace(T thing2r) {
        this.thing2r = thing2r;
        return this;
    }

    public FormBuilder<T, F> onSucceed(Runnable onSucceedTask) {
        this.onSucceed = Optional.ofNullable(onSucceedTask);
        return this;
    }

    public FormBuilder<T, F> onFailure(Runnable onFailureTask) {
        this.onSucceed = Optional.ofNullable(onFailureTask);
        return this;
    }

    public FormBuilder<T, F> initialValue(T initialValue) {
        this.initialValue = Optional.ofNullable(initialValue);
        return this;
    }

    public boolean buildAndShow() {
        initialValue.ifPresentOrElse((iv) -> {
            form.readInitialValue(iv);
        }, form::clearComponents);

        checkForLogicErrors();

        var flag = new Object() {
            public boolean succeeded = false;
        };

        form.showAndWait().ifPresentOrElse((input) -> {
            if(isListSetter) setter.accept(buildNewListBasedOnStrategy(input));
            else setter.accept(input);

            onSucceed.ifPresent(Runnable::run);
            flag.succeeded = true;
        }, () -> onFailure.ifPresent(Runnable::run));

        return flag.succeeded;
    }

    private List<T> buildNewListBasedOnStrategy(T input) {
        var tmp = Optional.ofNullable((List<T>)getter.get()).orElse(List.of()).stream();
        tmp = switch (setStrategy) {
            case APPEND -> tmp;
            case REPLACE -> tmp.filter((element) -> element != thing2r);
        };

        ArrayList<T> result = new ArrayList<>(tmp.toList());
        result.add(input);

        return result;
    }

    private void checkForLogicErrors() {
        if(setter == null)
            throw new InternalDesignConstraintException("setter not set for form");
        if(getter == null) // no data source
            throw new InternalDesignConstraintException("getter not set for form.");

        if(isListSetter) {
            if(setStrategy == null) // no strategy (ie. undefined behavior) (could have done a default strat but it's good to be explicit to avoid unwanted behaviors)
                throw new InternalDesignConstraintException("list setter strategy not set for form with a list setter. Cannot deduce the desired behavior.");
        }

        if(setStrategy == ListSetterStrategy.REPLACE && thing2r == null) {
            throw new InternalDesignConstraintException("replace target not set for replace strategy");
        }
    }

}
