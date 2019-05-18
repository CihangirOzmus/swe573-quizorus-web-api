package com.quizorus.backend.controller.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceRequest {

    @NotBlank
    private Long questionId;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String text;

    @NotNull
    private Boolean correct;
}
