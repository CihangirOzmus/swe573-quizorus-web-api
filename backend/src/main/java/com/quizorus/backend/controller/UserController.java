package com.quizorus.backend.controller;

import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.User;
import com.quizorus.backend.payload.*;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.TopicService;
import com.quizorus.backend.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicService topicService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser){
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "email") String email){
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long topicCount = topicRepository.countByCreatedBy(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), topicCount);

        return userProfile;
    }

    @GetMapping("/users/{username}/polls")
    public PagedResponse<TopicResponse> getTopicsCreatedBy(@PathVariable(value = "username") String username,
                                                           @CurrentUser UserPrincipal currentUser,
                                                           @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return topicService.getTopicsCreatedBy(username, currentUser, page, size);
    }

}
