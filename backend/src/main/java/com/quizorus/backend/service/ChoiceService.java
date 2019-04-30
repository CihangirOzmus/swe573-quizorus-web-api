package com.quizorus.backend.service;

import com.quizorus.backend.repository.ChoiceRepository;
import com.quizorus.backend.repository.QuestionRepository;
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

}
