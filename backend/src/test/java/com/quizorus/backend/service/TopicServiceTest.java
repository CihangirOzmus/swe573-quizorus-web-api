package com.quizorus.backend.service;

import com.quizorus.backend.DummyTestData;
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

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest {

    private static UserPrincipal currentUser;

    @Before
    public void setUp() throws Exception {
        currentUser = DummyTestData.createDummyUser();
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

    }

    @Test
    public void getTopicsCreatedBy() {
    }

    @Test
    public void getTopicById() {
    }

    @Test
    public void createTopic() {
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