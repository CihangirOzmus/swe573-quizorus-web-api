package com.quizorus.backend.controller;

import com.quizorus.backend.model.Content;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.repository.ContentRepository;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.service.ContentService;
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
@RequestMapping("/api/contents")
public class ContentController {

    @Autowired
    private ContentService contentService;

    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createContentWithTopicId(@Valid @RequestBody Content content){
        contentService.createContent(content);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{contentId}")
                .buildAndExpand(content.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Content created successfully"));
    }

}
