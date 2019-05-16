package com.quizorus.backend.controller.dto;

import com.quizorus.backend.model.Topic;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    private Long id;
    private String username;
    private String name;
    private String email;
    private List<Topic> createdTopics;
    private List<Topic> enrolledTopics;
    //private List<?> answeredQuestions;

}
