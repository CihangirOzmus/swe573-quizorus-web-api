package com.quizorus.backend;

import com.quizorus.backend.controller.dto.TopicRequest;
import com.quizorus.backend.controller.dto.TopicResponse;
import com.quizorus.backend.model.Content;
import com.quizorus.backend.model.Topic;
import com.quizorus.backend.model.User;
import com.quizorus.backend.model.WikiData;
import com.quizorus.backend.security.UserPrincipal;

import java.util.ArrayList;
import java.util.List;

public class DummyTestData {

    public static UserPrincipal createDummyUserPrincipal(){
        UserPrincipal user = new UserPrincipal();
        user.setId(0L);
        user.setName("name");
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");
        return user;
    }

    public static User createDummyUser() {
        final User user = new User();
        user.setEmail("email");
        user.setId(0L);
        user.setName("name");
        user.setPassword("pass");
        user.setUsername("userName");
        return user;
    }

    public static List<User> createDummyUserSet() {
        final List<User> userSet = new ArrayList<>();
        userSet.add(createDummyUser());
        return userSet;
    }

    public static WikiData createDummyWikiData() {
        final WikiData wikiData = new WikiData();
        wikiData.setId("id");
        wikiData.setDescription("someDescription");
        wikiData.setConceptUri("someConceptUri");
        wikiData.setLabel("someLabel");
        return wikiData;
    }

    public static List<WikiData> createDummyWikiDataSet() {
        final List<WikiData> wikiDataSet = new ArrayList<>();
        wikiDataSet.add(createDummyWikiData());
        return wikiDataSet;
    }

    public static Topic createDummyTopic() {
        final Topic topic = new Topic();
        topic.setId(0L);
        topic.setDescription("someDescription");
        topic.setTitle("someTitle");
        topic.setImageUrl("someImgUrl");
        topic.setWikiDataList(createDummyWikiDataSet());
        topic.setEnrolledUsers(createDummyUserSet());
        return topic;
    }

    public static List<Topic> createDummyTopicList() {
        final List<Topic> topicList = new ArrayList<>();
        topicList.add(createDummyTopic());
        return topicList;
    }

    public static Content createDummyContent() {
        final Content content = new Content();
        content.setId(0L);
        content.setTitle("someTitle");
        content.setText("someText");
        content.setTopic(createDummyTopic());
        return content;
    }

    public static List<Content> createDummyContentList() {
        final List<Content> contentList = new ArrayList<>();
        contentList.add(createDummyContent());
        return contentList;
    }

    public static TopicRequest createDummyTopicRequest() {
        final TopicRequest topicRequest = new TopicRequest();
        topicRequest.setEnrolledUsers(createDummyUserSet());
        topicRequest.setWikiData(createDummyWikiDataSet());
        topicRequest.setContentList(createDummyContentList());
        topicRequest.setId(0L);
        topicRequest.setDescription("description");
        topicRequest.setImageUrl("imageUrl");
        topicRequest.setTitle("title");
        return topicRequest;
    }

    public static TopicResponse createDummyTopicResponse() {
        final TopicResponse topicResponse = new TopicResponse();
        topicResponse.setWikiData(createDummyWikiDataSet());
        topicResponse.setContentList(createDummyContentList());
        topicResponse.setId(0L);
        topicResponse.setDescription("someDescription");
        topicResponse.setImageUrl("someImageUrl");
        topicResponse.setTitle("someTitle");
        return topicResponse;
    }

}
