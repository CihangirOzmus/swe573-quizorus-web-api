package com.quizorus.backend.payload;

import java.time.Instant;

public class UserProfile {

    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;
    private Long createdTopicCount;
    private Long enrolledTopicCount;

    public UserProfile(Long id, String username, String name, Instant joinedAt, Long createdTopicCount, Long enrolledTopicCount) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
        this.createdTopicCount = createdTopicCount;
        this.enrolledTopicCount = enrolledTopicCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getCreatedTopicCount() {
        return createdTopicCount;
    }

    public void setCreatedTopicCount(Long createdTopicCount) {
        this.createdTopicCount = createdTopicCount;
    }

    public Long getEnrolledTopicCount() {
        return enrolledTopicCount;
    }

    public void setEnrolledTopicCount(Long enrolledTopicCount) {
        this.enrolledTopicCount = enrolledTopicCount;
    }
}
