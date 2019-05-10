package com.quizorus.backend.conventer;

import com.quizorus.backend.controller.dto.QuestionRequest;
import com.quizorus.backend.model.Question;
import org.springframework.core.convert.converter.Converter;

public class QuestionRequestToQuestion implements Converter<QuestionRequest, Question> {

    @Override
    public Question convert(QuestionRequest source) {
        return Question.builder()
                .text(source.getText())
                .build();
    }
}
