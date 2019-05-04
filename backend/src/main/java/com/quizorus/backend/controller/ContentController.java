package com.quizorus.backend.controller;

import com.quizorus.backend.model.ContentEntity;
import com.quizorus.backend.model.QuestionEntity;
import com.quizorus.backend.model.TopicEntity;
import com.quizorus.backend.payload.ApiResponse;
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

    @Autowired
    private ContentService contentService;

    @Autowired
    private TopicRepository topicRepository;

    //private static final Logger logger = LoggerFactory.getLogger(ContentController.class);
    // need to refactor that one
    @GetMapping("/{topicId}/{contentId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ContentEntity> getContent(@PathVariable Long topicId, @PathVariable Long contentId, @CurrentUser UserPrincipal currentUser){
        TopicEntity topic = topicRepository.findById(topicId).orElse(null);
        if (topic != null){
            ContentEntity content = contentService.getContentById(contentId);
            return ResponseEntity.ok().body(content);
        }
        return ResponseEntity.notFound().build();
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
