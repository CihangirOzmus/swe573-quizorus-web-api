package com.quizorus.backend.controller;

import com.quizorus.backend.model.Question;
import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.controller.dto.ContentResponse;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/{contentId}")
    public ResponseEntity<ContentResponse> getContentById(@CurrentUser UserPrincipal currentUser, @PathVariable Long contentId){
        return contentService.getContentById(currentUser, contentId);
    }

    @PostMapping("/{contentId}/questions")
    @Transactional
    public ResponseEntity<?> createQuestionByContentId(@CurrentUser UserPrincipal currentUser, @PathVariable Long contentId, @Valid @RequestBody Question questionRequest){
        return contentService.createQuestionByContentId(currentUser, contentId, questionRequest);
    }

    @DeleteMapping("/{contentId}")
    @Transactional
    public ResponseEntity<ApiResponse> deleteContentById(@CurrentUser UserPrincipal currentUser, @PathVariable Long contentId){
        return contentService.deleteContentById(currentUser, contentId);
    }

}
