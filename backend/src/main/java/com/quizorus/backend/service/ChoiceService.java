package com.quizorus.backend.service;

import com.quizorus.backend.controller.dto.ChoiceRequest;
import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.Choice;
import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.model.Question;
import com.quizorus.backend.repository.ChoiceRepository;
import com.quizorus.backend.repository.QuestionRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChoiceService {

    private ChoiceRepository choiceRepository;

    private QuestionRepository questionRepository;

    private ConfigurableConversionService quizorusConversionService;

    public ChoiceService(ChoiceRepository choiceRepository, QuestionRepository questionRepository, ConfigurableConversionService quizorusConversionService) {
        this.choiceRepository = choiceRepository;
        this.questionRepository = questionRepository;
        this.quizorusConversionService = quizorusConversionService;
    }

    public ResponseEntity<ApiResponse> createChoiceByQuestionId(UserPrincipal currentUser,
                                                                ChoiceRequest choiceRequest) {

        final Question question = questionRepository.findById(choiceRequest.getQuestionId()).orElseThrow(
                () -> new ResourceNotFoundException("Question", "id", choiceRequest.getQuestionId().toString()));

        QuizorusUtils.checkCreatedBy("Question", currentUser.getId(), question.getCreatedBy());

        final Choice choice = quizorusConversionService.convert(choiceRequest, Choice.class);
        choice.setQuestion(question);
        choiceRepository.save(choice);
        return ResponseEntity.ok().body(new ApiResponse(true, "Choice created successfully"));
    }

    public ResponseEntity<ApiResponse> deleteChoiceById(UserPrincipal currentUser, Long choiceId) {

        final Choice choice = choiceRepository.findById(choiceId).orElseThrow(
                () -> new ResourceNotFoundException("Choice", "id", choiceId.toString()));

        QuizorusUtils.checkCreatedBy("Choice", currentUser.getId(), choice.getCreatedBy());

        choiceRepository.delete(choice);
        return ResponseEntity.ok().body(new ApiResponse(true, "Choice deleted successfully"));
    }
}
