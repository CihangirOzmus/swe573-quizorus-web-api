package com.quizorus.backend.payload;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;

public class TopicRequest {

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotBlank
    @Size(max = 255)
    private String description;

    @Nullable
    private ArrayList<String> wikiData;

    @Nullable
    public ArrayList<String> getWikiData() {
        return wikiData;
    }

    public void setWikiData(@Nullable ArrayList<String> wikiData) {
        this.wikiData = wikiData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
