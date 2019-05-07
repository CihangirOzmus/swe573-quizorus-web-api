package com.quizorus.backend.controller;

import com.quizorus.backend.model.ContentEntity;
import com.quizorus.backend.model.TopicEntity;
import com.quizorus.backend.payload.ApiResponse;
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

    //private static final Logger logger = LoggerFactory.getLogger(TopicController.class);

    @GetMapping
    public ResponseEntity<List<TopicEntity>> getAllTopics(@CurrentUser UserPrincipal currentUser){
        return topicService.getAllTopics(currentUser);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TopicEntity>> getTopicsByUsername(@PathVariable String username,
                                                                 @CurrentUser UserPrincipal currentUser){
        return topicService.getTopicsCreatedByUsername(username, currentUser);
    }

    @GetMapping("/topic/{topicId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TopicEntity> getTopicById(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId){
        return topicService.getTopicById(topicId, currentUser);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TopicEntity> createTopic(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody TopicEntity topicRequest){
        return topicService.createTopic(currentUser, topicRequest);
    }

    @PostMapping("/{topicId}/contents")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> createContentByTopicId(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId, @Valid @RequestBody ContentEntity contentRequest){
        return topicService.createContentByTopicId(currentUser,topicId, contentRequest);
    }

    @DeleteMapping("/topic/{topicId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> deleteTopicById(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId){
        return topicService.deleteTopicById(topicId, currentUser);
    }

    @PostMapping("{topicId}/enroll/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> enrollToTopicByUsername(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId, @PathVariable String username){
        return topicService.enrollToTopicByUsername(currentUser, topicId, username);
    }

}
