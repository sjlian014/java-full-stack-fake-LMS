package com.github.sjlian014.jlmsclient.exception;

/**
 * InternalDesignConstraintException
 *
 * this is used where I want to remind myself to do certain things to prevent logic errors that is otherwise not caught
 * by the compiler.
 */
public class InternalDesignConstraintException extends RuntimeException {

    public InternalDesignConstraintException(String errMsg) {
        super(errMsg);
    }
}
