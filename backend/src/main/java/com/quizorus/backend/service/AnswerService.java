package com.quizorus.backend.service;

import com.quizorus.backend.controller.dto.AnswerRequest;
import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.*;
import com.quizorus.backend.repository.*;
import com.quizorus.backend.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AnswerService {

    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private ChoiceRepository choiceRepository;
    private UserRepository userRepository;
    private ContentRepository contentRepository;

    Logger logger = LoggerFactory.getLogger(Answer.class);

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository, UserRepository userRepository, ContentRepository contentRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
    }

    public ApiResponse giveAnswer(UserPrincipal currentUser, AnswerRequest answerRequest) {
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));
        Question question = questionRepository.findById(answerRequest.getQuestionId()).orElseThrow(() -> new ResourceNotFoundException("Question", "questionId", answerRequest.getQuestionId()));
        Choice choice = choiceRepository.findById(answerRequest.getChoiceId()).orElseThrow(() -> new ResourceNotFoundException("Choice", "choiceId", answerRequest.getChoiceId()));
        Content content = contentRepository.findById(answerRequest.getContentId()).orElseThrow(() -> new ResourceNotFoundException("Content", "contentId", answerRequest.getContentId()));

        if (answerRepository.findByQuestionIdAndUserId(answerRequest.getQuestionId(), currentUser.getId()) == null){
            Answer answer = Answer.builder()
                    .question(question)
                    .choice(choice)
                    .content(content)
                    .user(user)
                    .build();
            answerRepository.save(answer);
        } else {
            Answer existingAnswer = answerRepository.findByQuestionIdAndUserId(answerRequest.getQuestionId(), currentUser.getId());
            existingAnswer.setChoice(choice);
            answerRepository.save(existingAnswer);
        }

        return new ApiResponse(true, "Question answered");
    }

    public ApiResponse restartQuiz(UserPrincipal currentUser, Long contentId) {
        answerRepository.deleteAnswersByContentId(contentId);
        return new ApiResponse(true, "Quiz restarted");
    }
}
