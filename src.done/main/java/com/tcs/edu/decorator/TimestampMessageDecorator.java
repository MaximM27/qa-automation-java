package com.tinkoff.edu.decorator;

import java.time.Instant;

public class TimestampMessageDecorator {
    public static String decorate(String message) {
        final var decoratedMessage = Instant.now() + " " + message;
        return decoratedMessage;
    }
}
