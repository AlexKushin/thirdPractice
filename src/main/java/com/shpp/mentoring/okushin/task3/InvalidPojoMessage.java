package com.shpp.mentoring.okushin.task3;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Simple class which describes pojo message which didn't pass validation
 */

@JsonPropertyOrder({"name", "count", "error"})
public class InvalidPojoMessage {
    private String name;
    private int count;
    private String errors;


    public InvalidPojoMessage() {
    }

    public InvalidPojoMessage(String name, int count, String error) {
        this.name = name;
        this.count = count;
        this.errors = error;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public String getErrors() {
        return errors;
    }

}
