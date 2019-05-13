package com.quizorus.backend.controller;

import com.quizorus.backend.controller.dto.TopicRequest;
import com.quizorus.backend.controller.dto.TopicResponse;
import com.quizorus.backend.model.Content;
import com.quizorus.backend.model.Topic;
import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics(@CurrentUser UserPrincipal currentUser){
        return topicService.getAllTopics(currentUser);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TopicResponse>> getCreatedTopicsByUsername(@CurrentUser UserPrincipal currentUser, @PathVariable String username){
        return topicService.getTopicsCreatedByUsername(currentUser, username);
    }

    @GetMapping("/topic/{topicId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TopicResponse> getCreatedTopicById(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId){
        return topicService.getCreatedTopicById(currentUser, topicId);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TopicResponse> createTopic(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody TopicRequest topicRequest){
        return topicService.createOrUpdateTopic(currentUser, topicRequest);
    }

    @PostMapping("/{topicId}/contents")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> createContentByTopicId(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId, @Valid @RequestBody Content contentRequest){
        return topicService.createOrUpdateContentByTopicId(currentUser,topicId, contentRequest);
    }

    @DeleteMapping("/topic/{topicId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> deleteTopicById(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId){
        return topicService.deleteTopicById(topicId, currentUser);
    }

    @PostMapping("/{topicId}/enroll/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> enrollToTopicByUsername(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId, @PathVariable String username){
        return topicService.enrollToTopicByUsername(currentUser, topicId, username);
    }

    @GetMapping("/enrolled/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TopicResponse>> getEnrolledTopics(@CurrentUser UserPrincipal currentUser , @PathVariable Long userId){
        return topicService.getTopicsByEnrolledUserId(currentUser, userId);
    }

}
