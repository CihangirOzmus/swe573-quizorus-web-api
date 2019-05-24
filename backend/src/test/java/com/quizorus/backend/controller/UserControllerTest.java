package com.quizorus.backend.controller;

import com.quizorus.backend.DummyTestData;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private static UserPrincipal currentUser;

    @BeforeClass
    public static void setCurrentUser(){
        currentUser = DummyTestData.createDummyUser();
    }

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController = new UserController(userService);

    @Test
    public void getCurrentUser() {
        userController.getCurrentUser(currentUser);
        verify(userService, times(1)).getCurrentUser(currentUser);
    }

    @Test
    public void checkUsernameAvailability() {
        userController.checkUsernameAvailability("username");
        verify(userService, times(1)).checkUsernameAvailability("username");
    }

    @Test
    public void checkEmailAvailability() {
        userController.checkEmailAvailability("email");
        verify(userService, times(1)).checkEmailAvailability("email");
    }

    @Test
    public void getUserProfile() {
        userController.getUserProfile("username");
        verify(userService, times(1)).getUserProfileByUsername("username");
    }
}