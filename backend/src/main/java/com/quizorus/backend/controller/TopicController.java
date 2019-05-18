package com.quizorus.backend.controller;

import com.quizorus.backend.controller.dto.*;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics(@CurrentUser UserPrincipal currentUser) {
        return topicService.getAllTopics(currentUser);
    }

    @Transactional
    @GetMapping("/{username}")
    public ResponseEntity<List<TopicResponse>> getTopicsByUsername(@PathVariable String username,
                                                                   @CurrentUser UserPrincipal currentUser) {
        return topicService.getTopicsCreatedBy(username, currentUser);
    }

    @Transactional
    @GetMapping("/topic/{topicId}")
    public ResponseEntity<TopicResponse> getTopicById(@CurrentUser UserPrincipal currentUser,
                                                      @PathVariable Long topicId) {
        return topicService.getTopicById(topicId, currentUser);
    }

    @Transactional
    @PostMapping("/publish")
    public ResponseEntity<ApiResponse> publishStatusUpdate(@CurrentUser UserPrincipal currentUser,
                                                           @RequestBody PublishRequest publishRequest) {
        return topicService.publishStatusUpdate(currentUser, publishRequest);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@Valid @RequestBody TopicRequest topicRequest) {
        return topicService.createTopic(topicRequest);
    }

    @Transactional
    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<ApiResponse> deleteTopicById(@CurrentUser UserPrincipal currentUser,
                                                       @PathVariable Long topicId) {
        return topicService.deleteTopicById(topicId, currentUser);
    }

    @Transactional
    @PostMapping("/enroll")
    public ResponseEntity<ApiResponse> enrollToTopicByUsername(@CurrentUser UserPrincipal currentUser,
                                                               @RequestBody EnrollmentRequest enrollmentRequest) {
        return topicService.enrollToTopicByUsername(currentUser, enrollmentRequest);
    }

    @Transactional
    @GetMapping("/enrolled/{userId}")
    public ResponseEntity<List<TopicResponse>> getEnrolledTopics(@CurrentUser UserPrincipal currentUser,
                                                                 @PathVariable Long userId) {
        return topicService.getTopicsByEnrolledUserId(currentUser, userId);
    }

}
