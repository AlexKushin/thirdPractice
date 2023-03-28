package com.shpp.mentoring.okushin.exceptions;

import com.shpp.mentoring.okushin.task3.Receiver;

public class ReceiveMessageException  extends RuntimeException{
    public ReceiveMessageException(String message) {
        super(message);
    }
}
