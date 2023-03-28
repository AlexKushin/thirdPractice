package com.shpp.mentoring.okushin.task3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@JsonPropertyOrder({"name", "count"})
public class POJOMessage implements Serializable {
    @NotNull(message = "Name cannot be null")
    @Size(min = 7, message = "Name must be more than 7 characters")
    @Pattern(regexp = ".*a.*")
    private String name;


    private final LocalDateTime createdAtTime;

    @Min(value = 10, message = "Count should not be less than 10")
    private int count;


    public POJOMessage(String name, int count, LocalDateTime createdAtTime) {
        this.name = name;
        this.createdAtTime = createdAtTime;
        this.count = count;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public int getCount() {
        return count;
    }


    @JsonIgnore
    public LocalDateTime getCreatedAtTime() {
        return createdAtTime;
    }


}
