package com.quizorus.backend.payload;

import com.quizorus.backend.model.Content;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class TopicRequest {

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotBlank
    @Size(max = 255)
    private String description;

    @Nullable
    private String imageUrl;

    @Nullable
    private ArrayList<String> wikiData;

    @Nullable
    private List<Content> contentList;

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Nullable
    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(@Nullable List<Content> contentList) {
        this.contentList = contentList;
    }

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
