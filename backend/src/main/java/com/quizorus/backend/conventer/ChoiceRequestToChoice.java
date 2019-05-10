package com.quizorus.backend.conventer;

import com.quizorus.backend.controller.dto.ChoiceRequest;
import com.quizorus.backend.model.Choice;
import org.springframework.core.convert.converter.Converter;

public class ChoiceRequestToChoice implements Converter<ChoiceRequest, Choice> {

    @Override
    public Choice convert(ChoiceRequest source) {
        return Choice.builder()
                .text(source.getText())
                .isCorrect(source.getCorrect())
                .build();
    }

}
