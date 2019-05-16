package com.quizorus.backend.controller;


import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/{questionId}/choice")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> giveAnswer(@CurrentUser UserPrincipal currentUser, @PathVariable Long questionId, @RequestParam Long choiceId){
        return answerService.giveAnswer(currentUser, questionId, choiceId);
    }
}
