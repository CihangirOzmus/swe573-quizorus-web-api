package com.quizorus.backend.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceRequest {

    @NotBlank
    private Long questionId;

    @NotBlank
    @Size(max = 255)
    private String text;

    @NotNull
    private Boolean correct;
}
