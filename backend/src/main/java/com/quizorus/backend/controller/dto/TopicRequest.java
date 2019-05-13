package com.quizorus.backend.controller.dto;

import com.quizorus.backend.model.Content;
import com.quizorus.backend.model.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicRequest {
    private Long id = 0L;
    private String title;
    private String description;
    private String imageUrl;
    private ArrayList<String> wikiData;
    private List<Content> contentList;
    private List<User> enrolledUserList;
}
