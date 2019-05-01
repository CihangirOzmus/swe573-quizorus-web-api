package com.quizorus.backend.service;

import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.Topic;
import com.quizorus.backend.model.User;
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

    public List<Topic> getAllTopics(UserPrincipal currentUser){
        List<Topic> topics = topicRepository.findAll();
        return topics;
    }

    public List<Topic> getTopicsCreatedBy(String username, UserPrincipal currentUser) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        List<Topic> topics = topicRepository.findByCreatedBy(user.getId());

        return topics;
    }

    public Topic createTopic(Topic topicRequest) {
        Topic topic = new Topic();
        topic.setTitle(topicRequest.getTitle());
        topic.setDescription(topicRequest.getDescription());
        topic.setWikiData(topicRequest.getWikiData());
        topic.setImageUrl(topicRequest.getImageUrl());

        return topicRepository.save(topic);
    }

    public Topic getTopicById(Long topicId, UserPrincipal currentUser) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("Topic", "id", topicId));

        return topic;
    }

    public boolean deleteTopicById(Long topicId, UserPrincipal currentUser){
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic != null && currentUser.getId().equals(topic.getCreatedBy())){
            topicRepository.deleteById(topicId);
            return true;
        }
        return false;
    }

}
