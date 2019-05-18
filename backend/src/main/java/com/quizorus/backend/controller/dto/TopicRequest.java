package com.quizorus.backend.controller.dto;

import com.quizorus.backend.model.Content;
import com.quizorus.backend.model.User;
import com.quizorus.backend.model.WikiData;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicRequest {

    private Long id = 0L;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    @Nullable
    private List<WikiData> wikiData;

    @Nullable
    private List<User> enrolledUsers;

    @Nullable
    private List<Content> contentList;

}
