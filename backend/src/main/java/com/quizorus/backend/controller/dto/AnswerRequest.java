package com.quizorus.backend.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {

    @NotBlank
    private Long questionId;

    @NotBlank
    private Long choiceId;
}
