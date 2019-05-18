package com.quizorus.backend.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequest {

    @NonNull
    private Long topicId;

    @NotBlank
    private String username;

}
