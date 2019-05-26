package com.quizorus.backend.controller;

import com.quizorus.backend.DummyTestData;
import com.quizorus.backend.controller.dto.EnrollmentRequest;
import com.quizorus.backend.controller.dto.PublishRequest;
import com.quizorus.backend.controller.dto.TopicRequest;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.TopicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TopicControllerTest {

    private static UserPrincipal currentUser;

    @Before
    public void setUp() throws Exception {
        currentUser = DummyTestData.createDummyUser();
    }

    @Mock
    private TopicService topicService;

    @InjectMocks
    private TopicController topicController =  new TopicController(topicService);

    @Test
    public void getAllTopics() {
        topicController.getAllTopics(currentUser);
        verify(topicService, times(1)).getAllTopics(currentUser);
    }

    @Test
    public void getTopicsByUsername() {
        topicController.getTopicsByUsername("username", currentUser);
        verify(topicService, times(1)).getTopicsCreatedBy("username", currentUser);
    }

    @Test
    public void getTopicById() {
        topicController.getTopicById(currentUser, 0L);
        verify(topicService, times(1)).getTopicById(0L, currentUser);
    }

    @Test
    public void publishStatusUpdate() {
        PublishRequest publishRequest = PublishRequest.builder().publish(true).topicId(0L).build();
        topicController.publishStatusUpdate(currentUser, publishRequest);
        verify(topicService, times(1)).publishStatusUpdate(currentUser, publishRequest);
    }

    @Test
    public void createTopic() {
        TopicRequest topicRequest = TopicRequest.builder()
                .contentList(new ArrayList<>())
                .description("description")
                .id(0L).imageUrl("imageUrl")
                .title("title")
                .wikiData(new ArrayList<>())
                .build();

        topicController.createTopic(topicRequest);
        verify(topicService, times(1)).createTopic(topicRequest);
    }

    @Test
    public void deleteTopicById() {
        topicController.deleteTopicById(currentUser, 0L);
        verify(topicService, times(1)).deleteTopicById(0L, currentUser);
    }

    @Test
    public void enrollToTopicByUsername() {
        EnrollmentRequest enrollmentRequest = EnrollmentRequest.builder()
                .topicId(0L)
                .username("username")
                .build();
        topicController.enrollToTopicByUsername(currentUser, enrollmentRequest);
        verify(topicService, times(1)).enrollToTopicByUsername(currentUser, enrollmentRequest);
    }

    @Test
    public void getEnrolledTopics() {
        topicController.getEnrolledTopics(currentUser, 0L);
        verify(topicService, times(1)).getTopicsByEnrolledUserId(currentUser,0L);
    }
}