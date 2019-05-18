package com.quizorus.backend.controller;

import com.quizorus.backend.controller.dto.AnswerRequest;
import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class AnswerController {

    private AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> answerQuestionBy(@CurrentUser UserPrincipal currentUser, @RequestBody AnswerRequest answerRequest){
        return ResponseEntity.ok().body(answerService.giveAnswer(currentUser, answerRequest));
    }

    @DeleteMapping("/{contentId}")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<ApiResponse> restartQuiz(@CurrentUser UserPrincipal currentUser, @PathVariable Long contentId){
        return ResponseEntity.ok().body(answerService.restartQuiz(currentUser, contentId));
    }
    
}
