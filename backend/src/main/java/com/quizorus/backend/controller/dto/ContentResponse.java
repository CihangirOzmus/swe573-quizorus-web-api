package com.quizorus.backend.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponse {

    private Long id;
    private Long nextContentId;
    private Long questionCount;
    private String title;
    private String topicTitle;
    private String text;
    private Long topicId;

}
