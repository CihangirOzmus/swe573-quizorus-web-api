package com.quizorus.backend.controller;

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

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TopicEntity> createTopic(@Valid @RequestBody TopicEntity topicRequest){
        return topicService.createTopic(topicRequest);
    }

    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<ApiResponse> deleteTopicById(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId){
        return topicService.deleteTopicById(topicId, currentUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<TopicEntity>> getTopicsByUsername(@PathVariable String username,
                                                 @CurrentUser UserPrincipal currentUser){
        return topicService.getTopicsCreatedBy(username, currentUser);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<TopicEntity> getTopicById(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId){
        return topicService.getTopicById(topicId, currentUser);
    }

}
