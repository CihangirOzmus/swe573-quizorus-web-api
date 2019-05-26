package com.quizorus.backend.service;

import com.quizorus.backend.DummyTestData;
import com.quizorus.backend.controller.dto.UserIdentityAvailability;
import com.quizorus.backend.controller.dto.UserResponse;
import com.quizorus.backend.repository.LearningStepRepository;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.support.ConfigurableConversionService;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static UserPrincipal currentUser;

    @BeforeClass
    public static void setCurrentUser(){
        currentUser = DummyTestData.createDummyUserPrincipal();
    }

    @Mock
    private UserRepository userRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private LearningStepRepository learningStepRepository;

    @Mock
    private ConfigurableConversionService quizorusConversionService;

    @InjectMocks
    private final UserService userService = new UserService(userRepository, topicRepository, learningStepRepository, quizorusConversionService);

    @Test
    public void getCurrentUser() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(0L);
        userResponse.setName("name");
        userResponse.setUsername("username");

        when(quizorusConversionService.convert(currentUser, UserResponse.class)).thenReturn(userResponse);

        UserResponse userResponse1 = userService.getCurrentUser(currentUser);
        assertEquals(userResponse1.getId(), currentUser.getId());
        assertEquals(userResponse1.getName(), currentUser.getName());
        assertEquals(userResponse1.getUsername(), currentUser.getUsername());
    }

    @Test
    public void checkUsernameAvailability() {
        when(userRepository.existsByUsername("username")).thenReturn(false);
        UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability("username");
        assertEquals(userIdentityAvailability.getAvailable(), true);
    }

    @Test
    public void checkEmailAvailability() {
        when(userRepository.existsByEmail("email")).thenReturn(false);
        UserIdentityAvailability userIdentityAvailability = userService.checkEmailAvailability("email");
        assertEquals(userIdentityAvailability.getAvailable(), true);
    }

    @Test
    public void getUserProfileByUsername() {
    }
}