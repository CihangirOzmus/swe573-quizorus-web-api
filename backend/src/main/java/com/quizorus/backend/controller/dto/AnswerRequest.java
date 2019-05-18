package com.quizorus.backend.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {

    @NonNull
    private Long questionId;

    @NonNull
    private Long choiceId;

}
