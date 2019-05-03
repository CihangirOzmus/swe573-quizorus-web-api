package com.quizorus.backend.service;

import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.TopicEntity;
import com.quizorus.backend.model.UserEntity;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(TopicService.class);

    public List<TopicEntity> getAllTopics(UserPrincipal currentUser){
        List<TopicEntity> topics = topicRepository.findAll();
        return topics;
    }

    public List<TopicEntity> getTopicsCreatedBy(String username, UserPrincipal currentUser) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "username", username));

        List<TopicEntity> topics = topicRepository.findByCreatedBy(user.getId());

        return topics;
    }

    public TopicEntity createTopic(TopicEntity topicRequest) {
        TopicEntity topic = new TopicEntity();
        topic.setTitle(topicRequest.getTitle());
        topic.setDescription(topicRequest.getDescription());
        topic.setWikiData(topicRequest.getWikiData());
        topic.setImageUrl(topicRequest.getImageUrl());

        return topicRepository.save(topic);
    }

    public TopicEntity getTopicById(Long topicId, UserPrincipal currentUser) {
        TopicEntity topic = topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("TopicEntity", "id", topicId));

        return topic;
    }

    public boolean deleteTopicById(Long topicId, UserPrincipal currentUser){
        TopicEntity topic = topicRepository.findById(topicId).orElse(null);
        if (topic != null && currentUser.getId().equals(topic.getCreatedBy())){
            topicRepository.deleteById(topicId);
            return true;
        }
        return false;
    }

}
