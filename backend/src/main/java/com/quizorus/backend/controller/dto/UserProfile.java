package com.quizorus.backend.controller.dto;

import com.quizorus.backend.model.LearningStep;
import com.quizorus.backend.model.Topic;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    private String username;
    private String name;
    private String email;
    private List<Topic> createdTopics;
    private List<Topic> enrolledTopics;
    private List<LearningStep> answers;

}
