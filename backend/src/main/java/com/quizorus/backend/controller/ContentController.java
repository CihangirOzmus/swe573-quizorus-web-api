package com.quizorus.backend.controller;

import com.quizorus.backend.model.Content;
import com.quizorus.backend.model.Topic;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/contents")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private TopicRepository topicRepository;

    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

    @PostMapping("/{topicId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createContentWithTopicId(@PathVariable Long topicId, @CurrentUser UserPrincipal currentUser, @Valid @RequestBody Content content){

        Topic topic = topicRepository.findById(topicId).orElse(null);

        if (topic != null && currentUser.getId().equals(topic.getCreatedBy())){
            content.setTopic(topic);
            topic.getContentList().add(content);
            topicRepository.save(topic);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{contentId}")
                    .buildAndExpand(content.getId()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "Content created successfully"));
        }

        return ResponseEntity.badRequest().body(new ApiResponse(false, "Topic does not exist"));

    }

    @DeleteMapping("/{contentId}")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<ApiResponse> deleteContentById(@CurrentUser UserPrincipal currentUser, @PathVariable Long contentId){
        boolean result = contentService.deleteContentById(contentId, currentUser);

        if (result){
            return ResponseEntity.ok().body(new ApiResponse(true, "Content deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Content can be deleted by its owner"));
    }

}
