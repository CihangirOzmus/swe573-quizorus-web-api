package com.quizorus.backend.service;

import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.model.Answer;
import com.quizorus.backend.model.Choice;
import com.quizorus.backend.model.Question;
import com.quizorus.backend.model.User;
import com.quizorus.backend.repository.AnswerRepository;
import com.quizorus.backend.repository.ChoiceRepository;
import com.quizorus.backend.repository.QuestionRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private ChoiceRepository choiceRepository;
    private UserRepository userRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<ApiResponse> giveAnswer(UserPrincipal currentUser, Long questionId, Long choiceId ){
        Question question = questionRepository.findById(questionId).orElse(null);
        User user = userRepository.findById(currentUser.getId()).orElse(null);
        Choice choice = choiceRepository.findById(choiceId).orElse(null);

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setChoice(choice);
        answer.getUserList().add(user);

        answerRepository.save(answer);

        return ResponseEntity.ok().body(new ApiResponse(true, "Question answered"));
    }
}
