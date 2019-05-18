package com.quizorus.backend.controller;

import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.controller.dto.ContentRequest;
import com.quizorus.backend.controller.dto.ContentResponse;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/contents")
public class ContentController {

    private ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<ApiResponse> createContentByTopicId(@CurrentUser UserPrincipal currentUser,
                                                              @Valid @RequestBody ContentRequest contentRequest) {
        return contentService.createContentByTopicId(currentUser, contentRequest);
    }

    @Transactional
    @GetMapping("/{contentId}")
    public ResponseEntity<ContentResponse> getContentById(@CurrentUser UserPrincipal currentUser,
                                                          @PathVariable Long contentId) {
        return contentService.getContentById(currentUser, contentId);
    }

    @Transactional
    @DeleteMapping("/{contentId}")
    public ResponseEntity<ApiResponse> deleteContentById(@CurrentUser UserPrincipal currentUser,
                                                         @PathVariable Long contentId) {
        return contentService.deleteContentById(currentUser, contentId);
    }

}
