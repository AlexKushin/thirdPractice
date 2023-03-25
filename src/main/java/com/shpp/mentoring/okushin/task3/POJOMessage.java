package com.shpp.mentoring.okushin.task3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@JsonPropertyOrder({"name", "count"})
public class POJOMessage implements Serializable {
    @NotNull(message = "Name cannot be null")
    @Size(min = 7, message = "Name must be more than 7 characters")
    @Pattern(regexp = ".*a.*")
    private String name;


    private LocalDateTime createdAtTime;
    private static int total = 0;


    @Min(value = 10, message = "Count should not be less than 10")
    private int count;

    public POJOMessage() {
    }

    public POJOMessage(String name, LocalDateTime createdAtTime) {
        if (!name.equals("poison pill")) {
            total++;
        }
        this.name = name;
        this.createdAtTime = createdAtTime;

        this.count = total;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getCount() {
        return count;
    }

    public static int getTotal() {
        return total;
    }

    @JsonIgnore
    public LocalDateTime getCreatedAtTime() {
        return createdAtTime;
    }

    public static void setTotalToZero() {
        total = 0;
    }
}
