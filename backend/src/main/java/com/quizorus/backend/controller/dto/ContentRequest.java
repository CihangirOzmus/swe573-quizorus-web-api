package com.quizorus.backend.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentRequest {

    @NonNull
    private Long id = 0L;

    @NonNull
    private Long topicId;

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 8000)
    private String text;


}
