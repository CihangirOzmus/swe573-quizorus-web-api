package com.quizorus.backend.exception;

public class NotValidTopicException extends RuntimeException {

    public NotValidTopicException(String title, String message) {

        super(String
                .format("Topic '%s' is not published. '%s'", title, message));
    }
}
