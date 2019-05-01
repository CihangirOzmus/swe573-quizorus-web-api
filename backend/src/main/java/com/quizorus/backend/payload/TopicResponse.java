package com.quizorus.backend.payload;

import com.quizorus.backend.model.Content;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TopicResponse {

    private Long id;
    private String title;
    private String description;
    private UserSummary createdBy;
    private Instant creationDateTime;
    private ArrayList<String> wikiData;
    private List<Content> contentList;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }

    public ArrayList<String> getWikiData() {
        return wikiData;
    }

    public void setWikiData(ArrayList<String> wikiData) {
        this.wikiData = wikiData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserSummary getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserSummary createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

}
