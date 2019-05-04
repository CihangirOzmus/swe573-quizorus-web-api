package com.quizorus.backend.service;

import com.quizorus.backend.model.QuestionEntity;
import com.quizorus.backend.payload.ApiResponse;
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

    //private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    public ResponseEntity<ApiResponse> deleteQuestionById(Long questionId, UserPrincipal currentUser){
        QuestionEntity question = questionRepository.findById(questionId).orElse(null);
        if (question != null && currentUser.getId().equals(question.getCreatedBy())){
            questionRepository.deleteQuestionById(questionId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Question deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete question"));
    }

}
