package com.shpp.mentoring.okushin.task3;

import java.util.ArrayList;
import java.util.List;

public class Error {

    private final ArrayList<String> errors;

    public Error(List<String> errors) {
        this.errors = (ArrayList<String>) errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
