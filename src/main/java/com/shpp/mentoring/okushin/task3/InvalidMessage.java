package com.shpp.mentoring.okushin.task3;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonPropertyOrder({"name", "count", "error"})
public class InvalidMessage {
    String name;
    int count;
    String error;


    public InvalidMessage(String name, int count, String error) {
        this.name = name;
        this.count = count;
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public String getError() {
        return error;
    }
}
