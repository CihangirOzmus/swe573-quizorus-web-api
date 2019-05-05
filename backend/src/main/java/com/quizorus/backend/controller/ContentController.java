package com.quizorus.backend.controller;

import com.quizorus.backend.model.ContentEntity;
import com.quizorus.backend.model.QuestionEntity;
import com.quizorus.backend.model.TopicEntity;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.payload.ContentResponse;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //private static final Logger logger = LoggerFactory.getLogger(ContentController.class);
    @GetMapping("/{contentId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ContentResponse> getContentById(@CurrentUser UserPrincipal currentUser, @PathVariable Long contentId){
        return contentService.getContentById(currentUser, contentId);
    }

    @PostMapping("/{contentId}/questions")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<?> createQuestionByContentId(@CurrentUser UserPrincipal currentUser, @PathVariable Long contentId, @Valid @RequestBody QuestionEntity questionRequest){
        return contentService.createQuestionByContentId(currentUser, contentId, questionRequest);
    }

    @DeleteMapping("/{contentId}")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<ApiResponse> deleteContentById(@CurrentUser UserPrincipal currentUser, @PathVariable Long contentId){
        return contentService.deleteContentById(currentUser, contentId);
    }

}
