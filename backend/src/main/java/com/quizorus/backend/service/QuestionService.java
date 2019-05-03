package com.quizorus.backend.service;

import com.quizorus.backend.model.QuestionEntity;
import com.quizorus.backend.repository.QuestionRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    public QuestionEntity createQuestion(QuestionEntity questionRequest) {
        QuestionEntity question = new QuestionEntity();
        question.setText(questionRequest.getText());
        return questionRepository.save(question);
    }

    public boolean deleteQuestionById(Long questionId, UserPrincipal currentUser){
        QuestionEntity question = questionRepository.findById(questionId).orElse(null);
        if (question != null && currentUser.getId().equals(question.getCreatedBy())){
            questionRepository.deleteQuestionById(questionId);
            return true;
        }
        return false;
    }

}
