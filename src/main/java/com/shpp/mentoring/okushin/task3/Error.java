package com.shpp.mentoring.okushin.task3;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple class which contains message errors after validating
 */
public class Error {

    private final ArrayList<String> errors;

    public Error(List<String> errors) {
        this.errors = (ArrayList<String>) errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
