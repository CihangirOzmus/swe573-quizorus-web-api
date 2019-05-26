package com.quizorus.backend.service;

import com.quizorus.backend.DummyTestData;
import com.quizorus.backend.controller.dto.TopicRequest;
import com.quizorus.backend.controller.dto.TopicResponse;
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
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
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
    }

    @Test
    public void deleteTopicById() {
    }

    @Test
    public void enrollToTopicByUsername() {
    }

    @Test
    public void getTopicsByEnrolledUserId() {
    }
}