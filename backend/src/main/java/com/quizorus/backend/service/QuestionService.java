package com.quizorus.backend.service;

import com.quizorus.backend.model.Choice;
import com.quizorus.backend.model.Question;
import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.repository.QuestionRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public ResponseEntity<ApiResponse> createChoiceByQuestionId(UserPrincipal currentUser, Long questionId, Choice choiceRequest){
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question != null && currentUser.getId().equals(question.getCreatedBy())){
            choiceRequest.setQuestion(question);
            question.getChoiceList().add(choiceRequest);
            questionRepository.save(question);
            return ResponseEntity.ok().body(new ApiResponse(true, "Choice created successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to create choice"));
    }

    public ResponseEntity<ApiResponse> deleteQuestionById(Long questionId, UserPrincipal currentUser){
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question != null && currentUser.getId().equals(question.getCreatedBy())){
            questionRepository.deleteQuestionById(questionId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Question deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete question"));
    }

}
