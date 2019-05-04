package com.quizorus.backend.controller;

import com.quizorus.backend.model.ChoiceEntity;
import com.quizorus.backend.model.QuestionEntity;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.repository.QuestionRepository;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.ChoiceService;
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
@RequestMapping("api/choices")
public class ChoiceController {

    @Autowired
    private ChoiceService choiceService;

    @Autowired
    private QuestionRepository questionRepository;

    //private static final Logger logger = LoggerFactory.getLogger(ChoiceController.class);

    @PostMapping("/{questionId}")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<?> createChoiceWithQuestionId(@PathVariable Long questionId, @CurrentUser UserPrincipal currentUser, @Valid @RequestBody ChoiceEntity choice){

        QuestionEntity question = questionRepository.findById(questionId).orElse(null);

        if ( question != null && currentUser.getId().equals(question.getCreatedBy())){
            choice.setQuestion(question);
            question.getChoiceList().add(choice);
            questionRepository.save(question);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{choiceId}")
                    .buildAndExpand(question.getId()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "ChoiceEntity created successfully"));
        }

        return ResponseEntity.badRequest().body(new ApiResponse(false, "QuestionEntity does not exist"));
    }

    @DeleteMapping("/{choiceId}")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<ApiResponse> deleteChoiceById(@CurrentUser UserPrincipal currentUser, @PathVariable Long choiceId){
        return choiceService.deleteChoiceById(currentUser, choiceId);
    }
}
