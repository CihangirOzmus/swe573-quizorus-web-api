package com.quizorus.backend.controller;

import com.quizorus.backend.model.Content;
import com.quizorus.backend.model.Question;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.repository.ContentRepository;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.QuestionService;
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
@RequestMapping("api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ContentRepository contentRepository;

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @PostMapping("/{contentId}")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<?> createQuestionWithContentId(@PathVariable Long contentId, @CurrentUser UserPrincipal currentUser, @Valid @RequestBody Question question){

        Content content = contentRepository.findById(contentId).orElse(null);

        if (content != null && currentUser.getId().equals(content.getCreatedBy())){
            question.setContent(content);
            content.getQuestionList().add(question);
            contentRepository.save(content);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{questionId}")
                    .buildAndExpand(question.getId()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "Question created successfully"));
        }

        return ResponseEntity.badRequest().body(new ApiResponse(false, "Content does not exist"));
    }

    @DeleteMapping("/{questionId}")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<ApiResponse> deleteQuestionById(@CurrentUser UserPrincipal currentUser, @PathVariable Long questionId){
        boolean result = questionService.deleteQuestionById(questionId, currentUser);
        if (result){
            return ResponseEntity.ok().body(new ApiResponse(true, "Question deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Question can be deleted by its owner"));
    }
}
