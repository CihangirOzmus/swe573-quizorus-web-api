package com.quizorus.backend.service;

import com.quizorus.backend.controller.dto.TopicRequest;
import com.quizorus.backend.controller.dto.TopicResponse;
import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.Content;
import com.quizorus.backend.model.Topic;
import com.quizorus.backend.model.User;
import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.repository.WikiDataRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private TopicRepository topicRepository;
    private UserRepository userRepository;
    private WikiDataRepository wikiDataRepository;
    private ConfigurableConversionService quizorusConversionService;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository, WikiDataRepository wikiDataRepository, ConfigurableConversionService quizorusConversionService) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.wikiDataRepository = wikiDataRepository;
        this.quizorusConversionService = quizorusConversionService;
    }

    public ResponseEntity<List<TopicResponse>> getAllTopics(UserPrincipal currentUser) {
        List<TopicResponse> topicResponseList = topicRepository.findAll()
                .stream()
                .map(topic -> quizorusConversionService.convert(topic, TopicResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(topicResponseList);
    }

    public ResponseEntity<List<TopicResponse>> getTopicsCreatedByUsername(UserPrincipal currentUser, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        List<TopicResponse> topicResponseList = topicRepository.findByCreatedBy(user.getId())
                .stream()
                .map(topic -> quizorusConversionService.convert(topic, TopicResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(topicResponseList);
    }

    public ResponseEntity<TopicResponse> getCreatedTopicById(UserPrincipal currentUser, Long topicId) {
        Topic topicById = topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("Topic", "id", topicId));
        return ResponseEntity.ok().body(quizorusConversionService.convert(topicById, TopicResponse.class));
    }

    public ResponseEntity<TopicResponse> createTopic(UserPrincipal currentUser, TopicRequest topicRequest) {
        topicRequest.getWikiData().forEach(wikiData -> wikiDataRepository.save(wikiData));
        Topic topic = topicRepository.save(quizorusConversionService.convert(topicRequest, Topic.class));
        return ResponseEntity.ok().body(quizorusConversionService.convert(topic, TopicResponse.class));
    }

    public ResponseEntity<ApiResponse> updateTopic(UserPrincipal currentUser, TopicRequest topicRequest) {
        Topic topic = topicRepository.findById(topicRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("Topic", "id", topicRequest.getId()));

        if (currentUser.getId().equals(topic.getCreatedBy())){
            topicRequest.setWikiData(topic.getWikiData());
            topicRepository.save(quizorusConversionService.convert(topicRequest, Topic.class));
            return ResponseEntity.ok().body(new ApiResponse(true,"Topic updated successfully"));
        }

        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to update topic"));
    }

    public ResponseEntity<ApiResponse> createOrUpdateContentByTopicId(UserPrincipal currentUser, Long topicId, Content contentRequest) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic", "id", topicId));
        if (currentUser.getId().equals(topic.getCreatedBy())) {
            contentRequest.setTopic(topic);
            topic.getContentList().add(contentRequest);
            topicRepository.save(topic);
            return ResponseEntity.ok().body(new ApiResponse(true, "Content created successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to create content"));
    }

    public ResponseEntity<ApiResponse> deleteTopicById(Long topicId, UserPrincipal currentUser) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic", "id", topicId));
        if (currentUser.getId().equals(topic.getCreatedBy())) {
            topicRepository.deleteById(topicId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Topic deleted"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete topic"));
    }

    public ResponseEntity<ApiResponse> enrollToTopicByUsername(UserPrincipal currentUser, Long topicId, String username) {
        Topic topicToEnroll = topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic", "topicId", topicId));
        User userToEnroll = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        List<Topic> enrolledTopicList = topicRepository.findTopicEntitiesByEnrolledUserListContains(userToEnroll);
        if (enrolledTopicList.contains(topicToEnroll)) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Already enrolled to topic"));
        }

        topicToEnroll.getEnrolledUserList().add(userToEnroll);
        topicRepository.save(topicToEnroll);
        return ResponseEntity.ok().body(new ApiResponse(true, "Enrolled to topic successfully"));
    }

    public ResponseEntity<List<TopicResponse>> getTopicsByEnrolledUserId(UserPrincipal currentUser, Long userId) {
        User userById = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Topic> enrolledTopicList = topicRepository.findTopicEntitiesByEnrolledUserListContains(userById);
        List<TopicResponse> enrolledTopicDTOList = enrolledTopicList.stream()
                .map(topic -> quizorusConversionService.convert(topic, TopicResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(enrolledTopicDTOList);
    }
}
