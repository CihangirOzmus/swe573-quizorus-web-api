package com.quizorus.backend.dto;

import com.quizorus.backend.model.ContentEntity;

import java.util.ArrayList;
import java.util.List;

public class TopicResponse {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;

    private ArrayList<String> wikiData;
    private List<ContentEntity> contentList;
    private String createdBy;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getWikiData() {
        return wikiData;
    }

    public void setWikiData(ArrayList<String> wikiData) {
        this.wikiData = wikiData;
    }

    public List<ContentEntity> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentEntity> contentList) {
        this.contentList = contentList;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
