package com.quizorus.backend.service;

import com.quizorus.backend.payload.PagedResponse;
import com.quizorus.backend.payload.TopicResponse;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(TopicService.class);

    public PagedResponse<TopicResponse> getAllTopics(UserPrincipal currentUser, int page, int size){

        validatePageNumberAndSize(page, size);

        


        return new PagedResponse<>(topicResponses, topics.getNumber(),
                topics.getSize(), topics.getTotalElements(), topics.getTotalPages(), topics.isLast());
    }

}
