package com.quizorus.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quizorus.backend.model.audit.UserDateAudit;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics")
public class TopicEntity extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
    private List<ContentEntity> contentList;

    @Nullable
    @ManyToMany
    @JoinTable(name = "enrolled_topic_list",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private List<UserEntity> enrolledUserList;

    public List<UserEntity> getEnrolledUserList() {
        return enrolledUserList;
    }

    public void setEnrolledUserList(List<UserEntity> enrolledUserList) {
        this.enrolledUserList = enrolledUserList;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Nullable
    public List<ContentEntity> getContentList() {
        return contentList;
    }

    public void setContentList(@Nullable List<ContentEntity> contentList) {
        this.contentList = contentList;
    }

    @Nullable
    @Lob
    public ArrayList<String> getWikiData() {
        return wikiData;
    }

    public void setWikiData(@Nullable ArrayList<String> wikiData) {
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
}
