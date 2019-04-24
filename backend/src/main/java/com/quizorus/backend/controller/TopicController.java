package com.quizorus.backend.controller;

import com.quizorus.backend.model.Topic;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.payload.PagedResponse;
import com.quizorus.backend.payload.TopicRequest;
import com.quizorus.backend.payload.TopicResponse;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;

import com.quizorus.backend.service.TopicService;
import com.quizorus.backend.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicService topicService;


    private static final Logger logger = LoggerFactory.getLogger(TopicController.class);

    @GetMapping
    public PagedResponse<TopicResponse> getTopics(@CurrentUser UserPrincipal currentUser,
                                                  @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
        return topicService.getAllTopics(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createTopic(@Valid @RequestBody TopicRequest topicRequest){
        Topic topic = topicService.createTopic(topicRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{topicId}")
                .buildAndExpand(topic.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Topic created successfully"));

    }

    @GetMapping("/topic/{topicId}")
    public TopicResponse getTopicById(@CurrentUser UserPrincipal currentUser, @PathVariable Long topicId){
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
    public PagedResponse<TopicResponse> getTopicsByUsername(@PathVariable String username,
                                                  @CurrentUser UserPrincipal currentUser,
                                                  @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
        return topicService.getTopicsCreatedBy(username, currentUser, page, size);
    }

}
