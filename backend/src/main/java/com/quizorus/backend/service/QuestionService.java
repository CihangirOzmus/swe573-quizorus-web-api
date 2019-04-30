package com.quizorus.backend.service;

import com.quizorus.backend.model.Question;
import com.quizorus.backend.repository.ContentRepository;
import com.quizorus.backend.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);


    public Question createQuestion(Question questionRequest) {
        Question question = new Question();
        question.setText(questionRequest.getText());
        return questionRepository.save(question);
    }


}
