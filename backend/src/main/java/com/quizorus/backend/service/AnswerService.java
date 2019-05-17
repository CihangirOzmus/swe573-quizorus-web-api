package com.quizorus.backend.service;

import com.quizorus.backend.controller.dto.AnswerRequest;
import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.Answer;
import com.quizorus.backend.model.Choice;
import com.quizorus.backend.model.Question;
import com.quizorus.backend.model.User;
import com.quizorus.backend.repository.AnswerRepository;
import com.quizorus.backend.repository.ChoiceRepository;
import com.quizorus.backend.repository.QuestionRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AnswerService {

    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private ChoiceRepository choiceRepository;
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(Answer.class);

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
        this.userRepository = userRepository;
    }

    public ApiResponse giveAnswer(UserPrincipal currentUser, AnswerRequest answerRequest) {
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));
        Question question = questionRepository.findById(answerRequest.getQuestionId()).orElseThrow(() -> new ResourceNotFoundException("Question", "questionId", answerRequest.getQuestionId()));
        Choice choice = choiceRepository.findById(answerRequest.getChoiceId()).orElseThrow(() -> new ResourceNotFoundException("Choice", "choiceId", answerRequest.getChoiceId()));

        if (answerRepository.findByQuestionIdAndUserId(answerRequest.getQuestionId(), currentUser.getId()) == null){
            Answer answer = Answer.builder()
                    .question(question)
                    .choice(choice)
                    .user(user)
                    .build();
            answerRepository.save(answer);
        } else {
            logger.info("<<== implement already answered question ==>>");

            Answer existingAnswer = answerRepository.findByQuestionIdAndUserId(answerRequest.getQuestionId(), currentUser.getId());
            existingAnswer.setChoice(choice);
            answerRepository.save(existingAnswer);
        }

        return new ApiResponse(true, "Question answered");
    }
}
