package com.quizorus.backend.service;

import com.quizorus.backend.exception.CreatedByException;

public class QuizorusUtils {
    public QuizorusUtils() {
    }
    public static void checkCreatedBy(String entity, Long userId, Long createdBy) {

        if (!userId.equals(createdBy)) {
            throw new CreatedByException(entity, userId.toString(), createdBy.toString());
        }
    }
}
