package com.quizorus.backend.service;

import com.quizorus.backend.model.Choice;
import com.quizorus.backend.repository.ChoiceRepository;
import com.quizorus.backend.repository.QuestionRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChoiceService {

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private static final Logger logger = LoggerFactory.getLogger(ChoiceService.class);

    public boolean deleteChoiceById(Long choiceId, UserPrincipal currentUser) {
        Choice choice = choiceRepository.findById(choiceId).orElse(null);
        if (choice != null && currentUser.getId().equals(choice.getCreatedBy())){
            questionRepository.deleteQuestionById(choiceId);
            return true;
        }
        return false;
    }
}
