package com.quizorus.backend.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {

    @NonNull
    private Long contentId;

    @NotBlank
    @Size(max = 255)
    private String text;
    
}
