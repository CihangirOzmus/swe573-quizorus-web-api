package com.quizorus.backend.service;

import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.Answer;
import com.quizorus.backend.model.Choice;
import com.quizorus.backend.model.Question;
import com.quizorus.backend.repository.AnswerRepository;
import com.quizorus.backend.repository.QuestionRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public ResponseEntity<ApiResponse> createChoiceByQuestionId(UserPrincipal currentUser, Long questionId, Choice choiceRequest){
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
        if (currentUser.getId().equals(question.getCreatedBy()) && question.getChoiceList().size() < 5){
            choiceRequest.setQuestion(question);
            question.getChoiceList().add(choiceRequest);
            questionRepository.save(question);
            return ResponseEntity.ok().body(new ApiResponse(true, "Choice created successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to create choice"));
    }

    public ResponseEntity<ApiResponse> deleteQuestionById(Long questionId, UserPrincipal currentUser){
        answerRepository.findByQuestionId(questionId).ifPresent(answer1 -> answerRepository.delete(answer1));
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question != null && currentUser.getId().equals(question.getCreatedBy())){
            questionRepository.deleteQuestionById(questionId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Question deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete question"));
    }

    public ResponseEntity<List<Question>> getQuestionsByContentId(UserPrincipal currentUser, Long contentId) {
        List<Question> questions = questionRepository.findAllByContentId(contentId);
        return ResponseEntity.ok().body(questions);
    }
}
