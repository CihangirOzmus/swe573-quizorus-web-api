package com.quizorus.backend.controller.dto;

import com.quizorus.backend.model.Content;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponse {

    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String createdBy;
    private ArrayList<String> wikiData;
    private List<Content> contentList;

}
