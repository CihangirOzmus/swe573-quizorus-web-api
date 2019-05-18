package com.quizorus.backend.controller;

import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.controller.dto.ChoiceRequest;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.ChoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/choices")
public class ChoiceController {

    private ChoiceService choiceService;

    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<ApiResponse> createChoiceByQuestionId(@CurrentUser UserPrincipal currentUser,
                                                                @Valid @RequestBody ChoiceRequest choiceRequest) {
        return choiceService.createChoiceByQuestionId(currentUser, choiceRequest);
    }

    @Transactional
    @DeleteMapping("/{choiceId}")
    public ResponseEntity<ApiResponse> deleteChoiceById(@CurrentUser UserPrincipal currentUser,
                                                        @PathVariable Long choiceId) {
        return choiceService.deleteChoiceById(currentUser, choiceId);
    }
}
