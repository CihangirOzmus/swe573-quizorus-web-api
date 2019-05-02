package com.quizorus.backend.controller;

import com.quizorus.backend.model.Topic;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;


    private static final Logger logger = LoggerFactory.getLogger(TopicController.class);

    @GetMapping
    public List<Topic> getTopics(@CurrentUser UserPrincipal currentUser){
        return topicService.getAllTopics(currentUser);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createTopic(@Valid @RequestBody Topic topicRequest){
        Topic topic = topicService.createTopic(topicRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{topicId}")
                .buildAndExpand(topic.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Topic created successfully"));

    }

    @GetMapping("/topic/{topicId}")
    public Topic getTopicById(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId){
        return topicService.getTopicById(topicId, currentUser);
    }

    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<ApiResponse> deleteTopicById(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId){
        boolean result = topicService.deleteTopicById(topicId, currentUser);
        if (result){
            return ResponseEntity.ok().body(new ApiResponse(true, "Topic deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Topic can be deleted by its owner"));
    }

    @GetMapping("/{username}")
    public List<Topic> getTopicsByUsername(@PathVariable String username,
                                                  @CurrentUser UserPrincipal currentUser){
        return topicService.getTopicsCreatedBy(username, currentUser);
    }

}
