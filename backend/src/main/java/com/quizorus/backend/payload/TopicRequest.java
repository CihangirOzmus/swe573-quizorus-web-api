package com.quizorus.backend.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TopicRequest {

    @NotBlank
    @Size(max = 150)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
