package com.quizorus.backend.controller;

import com.quizorus.backend.controller.dto.AnswerRequest;
import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.controller.dto.LearningStepsResponse;
import com.quizorus.backend.controller.dto.QuestionRequest;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/questions")
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<ApiResponse> createQuestionByContentId(@CurrentUser UserPrincipal currentUser,
                                                                 @Valid @RequestBody QuestionRequest questionRequest) {
        return questionService.createQuestionByContentId(currentUser, questionRequest);
    }

    @Transactional
    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse> deleteQuestionById(@CurrentUser UserPrincipal currentUser,
                                                          @PathVariable Long questionId) {
        return questionService.deleteQuestionById(questionId, currentUser);
    }

    @Transactional
    @GetMapping("/{contentId}")
    public ResponseEntity<LearningStepsResponse> getLearningStepsByContentId(@CurrentUser UserPrincipal currentUser,
                                                                             @PathVariable Long contentId) {
        return questionService.getLearningSteps(currentUser, contentId);
    }

    @Transactional
    @PostMapping("/answer")
    public ResponseEntity<ApiResponse> giveAnswer(@CurrentUser UserPrincipal currentUser,
                                                  @Valid @RequestBody AnswerRequest answerRequest) {
        return questionService.giveAnswer(currentUser, answerRequest);
    }

}
