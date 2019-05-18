package com.quizorus.backend.controller.dto;

import com.quizorus.backend.model.Choice;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
    private Long id;

    private String text;

    private List<Choice> choiceList;

    private Choice userAnswer;
}
