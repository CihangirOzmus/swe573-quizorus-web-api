package com.quizorus.backend.controller;

import com.quizorus.backend.controller.dto.UserIdentityAvailability;
import com.quizorus.backend.controller.dto.UserProfile;
import com.quizorus.backend.controller.dto.UserResponse;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/me")
    public UserResponse getCurrentUser(@CurrentUser UserPrincipal currentUser){
        return userService.getCurrentUser(currentUser);
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam String username){
        return userService.checkUsernameAvailability(username);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam String email){
        return userService.checkEmailAvailability(email);
    }

    @GetMapping("/users/{username}")
    @PreAuthorize("hasRole('USER')")
    public UserProfile getUserProfile(@PathVariable String username) {
        return userService.getUserProfileByUsername(username);
    }

//    @GetMapping("/users/{username}/topics")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<List<TopicResponse>> getTopicsCreatedByUsername(@PathVariable String username, @CurrentUser UserPrincipal currentUser) {
//        return topicService.getTopicsCreatedByUsername(currentUser, username);
//    }

}
