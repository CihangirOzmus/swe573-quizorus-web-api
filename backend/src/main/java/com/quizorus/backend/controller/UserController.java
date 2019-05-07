package com.quizorus.backend.controller;

import com.quizorus.backend.model.TopicEntity;
import com.quizorus.backend.payload.UserIdentityAvailability;
import com.quizorus.backend.payload.UserProfile;
import com.quizorus.backend.payload.UserSummary;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.TopicService;
import com.quizorus.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    private TopicService topicService;

    public UserController(UserService userService, TopicService topicService) {
        this.userService = userService;
        this.topicService = topicService;
    }

    //private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser){
        return userService.getCurrentUser(currentUser);
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam String email){
        return userService.checkUsernameAvailability(email);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable String username) {
        return userService.getUserProfileByUsername(username);
    }

    @GetMapping("/users/{username}/topics")
    public ResponseEntity<List<TopicEntity>> getTopicsCreatedByUsername(@PathVariable String username, @CurrentUser UserPrincipal currentUser) {
        return topicService.getTopicsCreatedByUsername(username, currentUser);
    }

}
