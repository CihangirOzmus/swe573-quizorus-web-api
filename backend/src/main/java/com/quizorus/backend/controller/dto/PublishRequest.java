package com.quizorus.backend.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishRequest {

    private Long topicId;

    private boolean publish;

}
