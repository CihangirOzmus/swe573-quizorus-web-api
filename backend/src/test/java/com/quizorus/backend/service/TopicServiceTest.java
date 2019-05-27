package com.quizorus.backend.service;

import com.quizorus.backend.DummyTestData;
import com.quizorus.backend.controller.dto.*;
import com.quizorus.backend.model.Content;
import com.quizorus.backend.model.Question;
import com.quizorus.backend.model.Topic;
import com.quizorus.backend.model.User;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.repository.WikiDataRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest {

    private static UserPrincipal currentUser;

    @Before
    public void setUp() throws Exception {
        currentUser = DummyTestData.createDummyUserPrincipal();
    }

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WikiDataRepository wikiDataRepository;

    @Mock
    private ConfigurableConversionService quizorusConversionService;

    @InjectMocks
    private TopicService topicService = new TopicService(topicRepository, userRepository, wikiDataRepository, quizorusConversionService);

    @Test
    public void getAllTopics() {
        List<Topic> topicList = DummyTestData.createDummyTopicList();
        when(topicRepository.findByPublished(true)).thenReturn(topicList);
        when(quizorusConversionService.convert(topicList.get(0), TopicResponse.class))
                .thenReturn(DummyTestData.createDummyTopicResponse());
        ResponseEntity<List<TopicResponse>> responseEntity = topicService.getAllTopics(currentUser);
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).size() > 0);
    }

    @Test
    public void getTopicsCreatedBy() {
        User user = DummyTestData.createDummyUser();
        List<Topic> topicList = DummyTestData.createDummyTopicList();
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        when(topicRepository.findByCreatedBy(user.getId())).thenReturn(topicList);
        when(quizorusConversionService.convert(topicList.get(0), TopicResponse.class))
                .thenReturn(DummyTestData.createDummyTopicResponse());
        ResponseEntity<List<TopicResponse>> responseEntity = topicService.getTopicsCreatedBy("username", currentUser);
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).size() > 0);
    }

    @Test
    public void getTopicById() {
        Topic topic = DummyTestData.createDummyTopic();
        when(topicRepository.findById(0L)).thenReturn(Optional.of(topic));
        when(quizorusConversionService.convert(topic, TopicResponse.class)).thenReturn(DummyTestData.createDummyTopicResponse());
        final ResponseEntity<TopicResponse> responseEntity = topicService.getTopicById(0L, currentUser);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void createTopic() {
        TopicRequest topicRequest = DummyTestData.createDummyTopicRequest();
        Topic topic = DummyTestData.createDummyTopic();
        TopicResponse topicResponse = DummyTestData.createDummyTopicResponse();
        when(topicRepository.findById(topicRequest.getId())).thenReturn(Optional.of(topic));
        when(quizorusConversionService.convert(topicRequest, Topic.class)).thenReturn(topic);
        when(topicRepository.save(topic)).thenReturn(topic);
        when(quizorusConversionService.convert(topic, TopicResponse.class)).thenReturn(topicResponse);
        ResponseEntity<TopicResponse> responseEntity = topicService.createTopic(topicRequest);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void publishStatusUpdate() {
        PublishRequest publishRequest = DummyTestData.createDummyPublishRequest();
        Topic topic = DummyTestData.createDummyTopic();
        List<Content> contentList = DummyTestData.createDummyContentList();
        List<Question> questionList = DummyTestData.createDummyQuestionList();
        contentList.get(0).setQuestionList(questionList);
        topic.setContentList(contentList);
        topic.setCreatedBy(currentUser.getId());
        when(topicRepository.findById(publishRequest.getTopicId())).thenReturn(Optional.of(topic));
        ResponseEntity<ApiResponse> responseEntity = topicService.publishStatusUpdate(currentUser, publishRequest);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }

    @Test
    public void deleteTopicById() {
        Topic topic = DummyTestData.createDummyTopic();
        topic.setCreatedBy(currentUser.getId());
        when(topicRepository.findById(topic.getId())).thenReturn(Optional.of(topic));
        ResponseEntity<ApiResponse> responseEntity = topicService.deleteTopicById(0L, currentUser);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }

    @Test
    public void enrollToTopicByUsername() {
        EnrollmentRequest enrollmentRequest = DummyTestData.createDummyEnrollmentRequest();
        Topic topic = DummyTestData.createDummyTopic();
        User user = DummyTestData.createDummyUser();
        when(topicRepository.findById(enrollmentRequest.getTopicId())).thenReturn(Optional.of(topic));
        when(userRepository.findByUsername(enrollmentRequest.getUsername())).thenReturn(Optional.of(user));
        ResponseEntity<ApiResponse> responseEntity = topicService.enrollToTopicByUsername(currentUser, enrollmentRequest);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getSuccess(), true);
    }

    @Test
    public void getTopicsByEnrolledUserId() {
        User user = DummyTestData.createDummyUser();
        List<Topic> enrolledTopics = DummyTestData.createDummyTopicList();
        TopicResponse topicResponse = DummyTestData.createDummyTopicResponse();
        when(userRepository.findById(0L)).thenReturn(Optional.of(user));
        when(topicRepository.findTopicByEnrolledUsersContainsAndPublished(user, true)).thenReturn(enrolledTopics);
        when(quizorusConversionService.convert(enrolledTopics.get(0), TopicResponse.class)).thenReturn(topicResponse);
        final ResponseEntity<List<TopicResponse>> responseEntity = topicService.getTopicsByEnrolledUserId(currentUser, 0L);
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).size() > 0);
    }
}