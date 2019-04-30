package com.quizorus.backend.controller;

import com.quizorus.backend.model.Choice;
import com.quizorus.backend.model.Question;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.repository.QuestionRepository;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import com.quizorus.backend.service.ChoiceService;
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
@RequestMapping("api/choices")
public class ChoiceController {

    @Autowired
    private ChoiceService choiceService;

    @Autowired
    private QuestionRepository questionRepository;

    private static final Logger logger = LoggerFactory.getLogger(ChoiceController.class);

    @PostMapping("/{questionId}")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<?> createChoiceWithQuestionId(@PathVariable Long questionId, @CurrentUser UserPrincipal currentUser, @Valid @RequestBody Choice choice){

        Question question = questionRepository.findById(questionId).orElse(null);

        if ( question != null && currentUser.getId().equals(question.getId())){
            choice.setQuestion(question);
            question.getChoiceList().add(choice);
            questionRepository.save(question);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{choiceId}")
                    .buildAndExpand(question.getId()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiResponse(true, "Choice created successfully"));
        }

        return ResponseEntity.badRequest().body(new ApiResponse(false, "Question does not exist"));
    }

    @DeleteMapping("/{choiceId}")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<ApiResponse> deleteChoiceById(@CurrentUser UserPrincipal currentUser, @PathVariable Long choiceId){
        boolean result = choiceService.deleteChoiceById(choiceId, currentUser);
        if (result){
            return ResponseEntity.ok().body(new ApiResponse(true, "Choice deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Choice can be deleted by its owner"));
    }
}
